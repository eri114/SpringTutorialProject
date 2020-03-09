package project.tutorial.spring.domain.service.reservation;

/**
 * 入力された日付・部屋の組み合わせはすでに予約済みであることを意味するException
 */
public class AlreadyReservedException extends RuntimeException {
	public AlreadyReservedException(String message) {
		super(message);
	}
}
