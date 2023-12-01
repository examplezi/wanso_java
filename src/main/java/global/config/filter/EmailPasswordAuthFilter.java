package global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class EmailPasswordAuthFilter extends AbstractAuthenticationProcessingFilter {


    private final ObjectMapper objectMapper;

    public EmailPasswordAuthFilter(final String loginUrl, final ObjectMapper objectMapper) {
        super(loginUrl);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final EmailPassword emailPassword = objectMapper.readValue(request.getInputStream(), EmailPassword.class);

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                emailPassword.getEmail(),
                emailPassword.getPassword()
        );

        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }

    @Getter
    public static class EmailPassword {
        private String email;
        private String password;
    }
}
