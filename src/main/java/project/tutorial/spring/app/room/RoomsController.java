package project.tutorial.spring.app.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import project.tutorial.spring.domain.model.ReservableRoom;
import project.tutorial.spring.domain.service.room.RoomService;

@Controller
@RequestMapping("rooms")
public class RoomsController {

//	@Autowired
//	RoomService roomService;

	private final RoomService roomService;
	
	public RoomsController(RoomService roomService) {
		this.roomService = roomService;
	}
	
	@GetMapping
	String listRooms(Model model) {
		LocalDate today = LocalDate.now();
		
		// 対象の日付（本日の日付）で予約可能な会議室一覧を取得する
		List<ReservableRoom> rooms = roomService.findReservableRooms(today);
		
		model.addAttribute("date", today);
		model.addAttribute("rooms", rooms);
		return "room/listRooms";
	}
	
	@GetMapping("{date}")
	String listRooms(
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date")
		LocalDate date, Model model) {
		
		// 対象の日付で予約可能な会議室一覧を取得する
		List<ReservableRoom> rooms = roomService.findReservableRooms(date);
		
		model.addAttribute("rooms", rooms);
		return "room/listRooms";
	}
}
