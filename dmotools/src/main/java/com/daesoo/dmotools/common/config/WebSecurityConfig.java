package com.daesoo.dmotools.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.daesoo.dmotools.common.jwt.JwtAuthFilter;
import com.daesoo.dmotools.common.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer{
	
	private final JwtUtil jwtUtil;

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	        .csrf(AbstractHttpConfigurer::disable)
	        .authorizeHttpRequests((authorizeRequests) ->
	        	authorizeRequests.anyRequest().permitAll()
	        .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
	        );



        return http.build();
    }
    
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(
//                            "http://localhost:3838", 
//                            "https://dmo-tools.vercel.app", 
//                            "https://dmo-tools-dev.vercel.app", 
//                            "https://dmo.greuta.org"
//                        );
//                registry.addMapping("/api/alarms/**").allowedOrigins();
//            }
//        };
//    }
    
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // TODO: 배포시, 아래 코드 수정 절대적으로 할 것
//        ArrayList<String> allowedOriginList = new ArrayList<>();
//        for (int port = 3000; port <= 3010; ++port) {
//            allowedOriginList.add("http://localhost:" + port);
//        }
//        allowedOriginList.add("https://open-velog.vercel.app");
//        allowedOriginList.add("https://search-open-velog-elastic-bvtge6dkgdmsr2i3f3kzwo6m2u.ap-northeast-2.es.amazonaws.com");
//
//        registry
//                .addMapping("/**") // 프로그램에서 제공하는 URL
//                .allowedOrigins(allowedOriginList.toArray(new String[0])) // 요청을 허용할 출처를 명시, 전체 허용 (가능하다면 목록을 작성한다.
//                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE") // 어떤 메서드를 허용할 것인지 (GET, POST...)
//                .allowedHeaders("Content-Type", "Authorization") // 어떤 헤더들을 허용할 것인지
//                .exposedHeaders("Content-Type", "Authorization")
//                .allowCredentials(true) // 쿠키 요청을 허용한다(다른 도메인 서버에 인증하는 경우에만 사용해야하며, true 설정시 보안상 이슈가 발생할 수 있다)
//                .maxAge(3600); // preflight 요청에 대한 응답을 브라우저에서 캐싱하는 시간;
//    }




}