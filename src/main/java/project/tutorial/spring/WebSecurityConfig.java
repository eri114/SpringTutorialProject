package project.tutorial.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.tutorial.spring.domain.service.user.ReservationUserDetailsService;

// @EnableWebSecurityを付与して、Spring SecurityのWeb連携機能を有効にする
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ReservationUserDetailsService userDetailsService;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		// パスワードのエンコードアルゴリズムとしてBCryptを使用したBCryptPasswordEncoderを使用
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/js/**", "/css/**").permitAll() // /js以下と/css以下へのアクセスは常に許可(permitAll) ・・・❶
				.antMatchers("/**").authenticated()           // ❶以外へのアクセスは認証を要求(authenticated)
				.and()
				.formLogin()                                  // ここからフォーム認証
				.loginPage("/loginForm")
				.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/rooms", true)            // 認証成功時は指定したパスへ遷移
				.failureForwardUrl("/loginForm?error=true").permitAll();  
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// 指定のUserDetailsServiceとPasswordEncoderを使用して認証を行う
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
