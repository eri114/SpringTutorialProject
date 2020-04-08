package project.tutorial.spring.domain.service.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.tutorial.spring.domain.model.MeetingRoom;
import project.tutorial.spring.domain.model.ReservableRoom;
import project.tutorial.spring.domain.repository.room.MeetingRoomRepository;
import project.tutorial.spring.domain.repository.room.ReservableRoomRepository;

@Service
@Transactional
public class RoomService {

//	@Autowired
//	ReservableRoomRepository reservableRoomRepository;
//	
//	@Autowired
//	MeetingRoomRepository meetingRoomRepository;

	private final ReservableRoomRepository reservableRoomRepository;
	private final MeetingRoomRepository meetingRoomRepository;
	
	public RoomService(ReservableRoomRepository reservableRoomRepository, 
						MeetingRoomRepository meetingRoomRepository) {
		this.reservableRoomRepository = reservableRoomRepository;
		this.meetingRoomRepository = meetingRoomRepository;
	}
	
	public List<ReservableRoom> findReservableRooms(LocalDate reservedDate) {
		return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(reservedDate);
	}
	
	// 会議室情報の取得
	public MeetingRoom findMeetingRoom(Integer roomId) {
		return meetingRoomRepository.findById(roomId).get();
	}
}
