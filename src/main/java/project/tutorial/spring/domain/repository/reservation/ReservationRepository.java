package project.tutorial.spring.domain.repository.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tutorial.spring.domain.model.ReservableRoomId;
import project.tutorial.spring.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	// 指定した会議室の予約一覧を取得するメソッド
	List<Reservation> findByReservableRoom_reservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);
}
