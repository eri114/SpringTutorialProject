package project.tutorial.spring.domain.service.reservation;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.tutorial.spring.domain.model.ReservableRoom;
import project.tutorial.spring.domain.model.ReservableRoomId;
import project.tutorial.spring.domain.model.Reservation;
import project.tutorial.spring.domain.model.RoleName;
import project.tutorial.spring.domain.model.User;
import project.tutorial.spring.domain.repository.reservation.ReservationRepository;
import project.tutorial.spring.domain.repository.room.ReservableRoomRepository;

@Service
@Transactional
public class ReservationService {

//	@Autowired
//	ReservationRepository reservationRepository;
//	
//	@Autowired
//	ReservableRoomRepository reservableRoomRepository;
	
	private final ReservationRepository reservationRepository;
	private final ReservableRoomRepository reservableRoomRepository;
	
	public ReservationService(ReservationRepository reservationRepository, 
								ReservableRoomRepository reservableRoomRepository) {
		this.reservationRepository = reservationRepository;
		this.reservableRoomRepository = reservableRoomRepository;
	}
	
	// 予約処理
	public Reservation reserve(Reservation reservation) {
		ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();
		
		// 対象の部屋が予約可能かをチェック
		ReservableRoom reservable = reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId);
		
		if(reservable == null) {
			throw new UnavailableReservationException("入力の日付・部屋の組み合わせは予約できません。");
		}
		
		// 重複チェック
		boolean overlap = reservationRepository.findByReservableRoom_reservableRoomIdOrderByStartTimeAsc(reservableRoomId)
				.stream()
				.anyMatch(x -> x.overlap(reservation));
		
		if(overlap) {
			throw new AlreadyReservedException("入力の時間帯はすでに予約済みです。");
		}
		
		// 予約情報を登録
		reservationRepository.save(reservation);
		return reservation;
	}
	
//	// 予約情報の取り消し可能な権限を持つかどうかのチェック
//	public void cancel(Integer reservationId, User requestUser) {
//		Reservation reservation = reservationRepository.findById(reservationId).get();
//		
//		// Userロール：自分が予約した情報のみ
//		// ADMIN：全予約削除可能
//		if(RoleName.ADMIN != requestUser.getRoleName() && 
//				!Objects.equals(reservation.getUser().getUserId(), requestUser.getUserId())) {
//			throw new AccessDeniedException("要求されたキャンセルは許可できません。");
//		}
//		reservationRepository.delete(reservation);
//	}
	
	// 指定日付の予約一覧取得処理
	public List<Reservation> findReservations(ReservableRoomId reserableRoomId) {
		return reservationRepository.findByReservableRoom_reservableRoomIdOrderByStartTimeAsc(reserableRoomId);
	}
	
	@PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
	public void cancel(@P("reservation") Reservation reservation) {
		reservationRepository.delete(reservation);
	}
	
	// Reservationオブジェクトを予約IDから取得
	public Reservation findById(Integer reservationId) {
		return reservationRepository.findById(reservationId).get();
	}
}
