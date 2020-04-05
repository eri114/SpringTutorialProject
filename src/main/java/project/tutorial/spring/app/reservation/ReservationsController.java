package project.tutorial.spring.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import project.tutorial.spring.domain.model.ReservableRoom;
import project.tutorial.spring.domain.model.ReservableRoomId;
import project.tutorial.spring.domain.model.Reservation;
import project.tutorial.spring.domain.model.User;
import project.tutorial.spring.domain.service.reservation.AlreadyReservedException;
import project.tutorial.spring.domain.service.reservation.ReservationService;
import project.tutorial.spring.domain.service.reservation.UnavailableReservationException;
import project.tutorial.spring.domain.service.room.RoomService;
import project.tutorial.spring.domain.service.user.ReservationUserDetails;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
public class ReservationsController {
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	ReservationService reservationService;
	
	@ModelAttribute
	ReservationForm  setUpForm() {
		ReservationForm form = new ReservationForm();
		
		// デフォルト値
		form.setStartTime(LocalTime.of(9, 0));
		form.setEndTime(LocalTime.of(10, 0));
		
		return form;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	String reserveForm(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {
		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
		
		// 指定日付の予約一覧取得処理呼び出し
		List<Reservation> reservations = reservationService.findReservations(reservableRoomId);
		
		LocalTime baseTime = LocalTime.of(0,  0);
		
		// 00:00~23:30まで30分単位でLocalDateオブジェクトを作成し、リストに48個分格納。
//		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30))
//				.limit(24 * 2)
//				.collect(Collectors.toList());
		List<LocalTime> timeList = IntStream.range(0, 24 * 2)
				.mapToObj(i -> baseTime.plusMinutes(30 * i)).collect(Collectors.toList());
		
		model.addAttribute("room", roomService.findMeetingRoom(roomId));
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		// model.addAttribute("user",dummyUser());
		
		return "reservation/reserveForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	String reserve(@Validated ReservationForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {
		
		// 入力チェックでエラーがある場合はフォーム表示画面へ遷移
		if (bindingResult.hasErrors()) {
			return reserveForm(date, roomId, model);
		}

		ReservableRoom reservableRoom = new ReservableRoom(new ReservableRoomId(roomId, date));
		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setReservableRoom(reservableRoom);
		reservation.setUser(userDetails.getUser());
		
		try {
			// 予約処理
			reservationService.reserve(reservation);
		} catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}
		
		return "redirect:/reservations/{date}/{roomId}";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "cancel")
	String cancel(
			@RequestParam("reservationId") Integer reservationId,
			@PathVariable("roomId") Integer roomId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@PathVariable("date") LocalDate date, Model model) {
		
		try {
			Reservation reservation = reservationService.findById(reservationId); 
			// 取消処理
			reservationService.cancel(reservation);
		} catch (AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}
		return "redirect:/reservations/{date}/{roomId}";
	}
}
