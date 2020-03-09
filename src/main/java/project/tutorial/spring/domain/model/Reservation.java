package project.tutorial.spring.domain.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Reservation implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id") })
	private ReservableRoom reservableRoom;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public ReservableRoom getReservableRoom() {
		return reservableRoom;
	}

	public void setReservableRoom(ReservableRoom reservableRoom) {
		this.reservableRoom = reservableRoom;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean overlap(Reservation target) {
		
		// 2つの予約の日付・部屋が別であれば重複なし
		if(!Objects.equals(reservableRoom.getReservableRoomId(), target.reservableRoom.getReservableRoomId())) {
			return false;
		}
		
		// 2つの予約の開始時刻と終了時刻が一致する場合は重複
		if(startTime.equals(target.startTime) && endTime.equals(target.endTime)) {
			return true;
		}
		
		// 2つの予約の開始時刻と終了時刻が黄砂しているか、または包含関係にあるかどうかを判定
		return target.endTime.isAfter(startTime) && endTime.isAfter(target.startTime);
	}

}
