package ac.dnd.dodal.config.security.filter;

import ac.dnd.dodal.common.enums.CommonResultCode;
import ac.dnd.dodal.common.exception.DodalException;
import ac.dnd.dodal.domain.user.constant.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            log.error("FilterException throw SecurityException Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.AUTH_EXCEPTION);
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            log.error("FilterException throw MalformedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.TOKEN_MALFORMED_ERROR);
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.TOKEN_TYPE_ERROR);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.EXPIRED_TOKEN_ERROR);
            filterChain.doFilter(request, response);
        } catch (UnsupportedJwtException e) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.TOKEN_UNSUPPORTED_ERROR);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("FilterException throw JwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.TOKEN_UNKNOWN_ERROR);
            filterChain.doFilter(request, response);
        } catch (DodalException e) {
            log.error("FilterException throw Exception Exception : {}", e.getMessage());
            request.setAttribute("exception", e.getResultCode());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("FilterException throw Exception Exception : {}", e.getMessage());
            request.setAttribute("exception", CommonResultCode.INTERNAL_SERVER_ERROR);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestURI));
    }
}
