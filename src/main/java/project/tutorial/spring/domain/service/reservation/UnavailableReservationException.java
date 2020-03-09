package project.tutorial.spring.domain.service.reservation;

/**
 * 入力された日付・部屋の組み合わせでは予約できないことを意味するException
 */
public class UnavailableReservationException extends RuntimeException {
	public UnavailableReservationException(String message) {
		super(message);
	}

}
