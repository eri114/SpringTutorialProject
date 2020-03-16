package project.tutorial.spring.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.tutorial.spring.domain.model.User;
import project.tutorial.spring.domain.repository.user.UserRepository;

@Service
public class ReservationUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		// ユーザ取得処理はUserRepositoryに移譲
		User user = userRepository.findById(username).get();
		
		if(user == null) {
			throw new UsernameNotFoundException(username + " is not found.");
		}
		
		// ユーザが見つかった場合は、UserDetailsを生成
		return new ReservatoinUserDetails(user);
	}
}
