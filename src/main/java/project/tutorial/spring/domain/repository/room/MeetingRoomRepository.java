package project.tutorial.spring.domain.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tutorial.spring.domain.model.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {

}
