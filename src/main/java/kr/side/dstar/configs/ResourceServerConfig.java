package kr.side.dstar.configs;

import kr.side.dstar.token.JwtAuthenticationFilter;
import kr.side.dstar.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 리소스 아이디 설정
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("member");
    }

    // 허용 범위 설정
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous()
                    .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/auth/**")
                    .permitAll()
                .mvcMatchers(HttpMethod.GET, "/profile")
                    .permitAll()
                .mvcMatchers(HttpMethod.GET, "/actuator/health")
                    .permitAll()
                .mvcMatchers(HttpMethod.GET, "/")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
