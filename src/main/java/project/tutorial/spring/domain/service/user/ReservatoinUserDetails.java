package project.tutorial.spring.domain.service.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import project.tutorial.spring.domain.model.User;

public class ReservatoinUserDetails implements UserDetails {
	
	// Userクラスを内包
	// 基本的なユーザ情報はこのフィールドが持つ
	private final User user;
	
	public ReservatoinUserDetails(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	// ユーザに与えられている権限リストを返却するメソッド。認可処理で利用される。
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_" + this.user.getRoleName().name());
	}

	// 登録されているパスワードを返却。
	// この返却されたパスワードとクライアントから指定されたパスワードが一致しない場合、
	// DaoAuthenticationProviderはBadCredentialsExceptionをスローする。
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserId();
	}

	// アカウントの有効期限の状態を判定（今回は不使用）
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// アカウントのロック状態を判定（今回は不使用）
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 資格情報の有効期限の状態を判定（今回は不使用）
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 有効なユーザかを判定（今回は不使用）
	@Override
	public boolean isEnabled() {
		return true;
	}

}
