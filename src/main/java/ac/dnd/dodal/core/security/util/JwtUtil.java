package ac.dnd.dodal.core.security.util;

import ac.dnd.dodal.common.constant.Constants;
import ac.dnd.dodal.domain.user.enums.UserRole;
import ac.dnd.dodal.ui.auth.response.JwtTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-milli-seconds}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh-token-validity-in-milli-seconds}")
    private Long refreshTokenExpirationPeriod;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email, UserRole role, Long expirationPeriod) {
        Claims claims = Jwts.claims();
        claims.put(Constants.USER_EMAIL_CLAIM_NAME, email);
        claims.put(Constants.USER_ROLE_CLAIM_NAME, role.toString());

        Date now = new Date();
        Date tokenValidity = new Date(now.getTime() + expirationPeriod);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(tokenValidity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createToken(Long id, UserRole role, Long expirationPeriod) {
        Claims claims = Jwts.claims();
        claims.put(Constants.USER_ID_CLAIM_NAME, id);
        claims.put(Constants.USER_ROLE_CLAIM_NAME, role.toString());

        Date now = new Date();
        Date tokenValidity = new Date(now.getTime() + expirationPeriod);    // 토큰의 만료시간 설정

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(tokenValidity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public JwtTokenDto generateToken(String email, UserRole role) {
        return new JwtTokenDto(createToken(email, role, accessTokenExpirationPeriod),
                createToken(email, role, refreshTokenExpirationPeriod));
    }

    public JwtTokenDto generateToken(Long id, UserRole role) {
        return new JwtTokenDto(createToken(id, role, accessTokenExpirationPeriod),
                createToken(id, role, refreshTokenExpirationPeriod));
    }

    public Claims validateAndGetClaimsFromToken(String token) throws JwtException {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER_PREFIX)) {
            return bearerToken.substring(Constants.BEARER_PREFIX.length());
        }
        return null;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = validateAndGetClaimsFromToken(token);
        return claims.get(Constants.USER_ID_CLAIM_NAME, Long.class);
    }
}