package project.tutorial.spring.domain.repository.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tutorial.spring.domain.model.ReservableRoom;
import project.tutorial.spring.domain.model.ReservableRoomId;

public interface ReservableRoomRepository extends JpaRepository<ReservableRoom, ReservableRoomId> {
	
	// 指定日に予約可能な会議室の一覧を取得するメソッド
	List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(
			LocalDate reservedDate);
}
