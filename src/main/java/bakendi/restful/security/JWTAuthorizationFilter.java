package bakendi.restful.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "oo323kjok2jerlkaldkjp98q2t4pulakfjlkdjhioa3ry223rfweak";
    private final String ISSUER = "theBoys";

    @Override()
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (checkJWTToken(request, response)) {
                DecodedJWT jwt = validateToken(request);
                if (jwt.getClaim("authorities") != null) {
                    setUpSpringAuthentication(jwt);
                } else {
                    System.out.println("here1");
                    SecurityContextHolder.clearContext();
                }
            }else {
                SecurityContextHolder.clearContext();
                throw new Exception("User not logged in");
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private DecodedJWT validateToken(HttpServletRequest request) throws JWTVerificationException {
            String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
            Algorithm algorithm = Algorithm.HMAC512(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).acceptLeeway(1).build();
            DecodedJWT jwt = verifier.verify(jwtToken);
            return jwt;
    }

    /**
     * Authentication method in Spring flow
     *
     * @param jwt
     */
    private void setUpSpringAuthentication(DecodedJWT jwt) {
        @SuppressWarnings("unchecked")
        List<String> authorities = jwt.getClaim("authorities").asList(String.class);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwt.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX) || authenticationHeader.split(" ")[1].equals(""))
            return false;
        return true;
    }

}
