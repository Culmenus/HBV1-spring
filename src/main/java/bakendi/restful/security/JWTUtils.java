package bakendi.restful.security;

import bakendi.restful.persistence.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtils implements Serializable {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "oo323kjok2jerlkaldkjp98q2t4pulakfjlkdjhioa3ry223rfweak";
    private final String ISSUER = "theBoys";


    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 600000000;

    //retrieve username from jwt token
    public long getUserIDFromToken(String token) {
        return Long.parseLong(getClaimFromToken(token, DecodedJWT::getSubject));
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, DecodedJWT::getExpiresAt);
    }

    public <T> T getClaimFromToken(String token, Function<DecodedJWT, T> claimsResolver) {
        final DecodedJWT jwt = decodeToken(token);
        return claimsResolver.apply(jwt);
    }
    //for retrieving any information from token we will need the secret key
    private DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).acceptLeeway(1).build();
        return verifier.verify(token);
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(User user) {
        long userID = user.getID();
        Algorithm algorithmHS = Algorithm.HMAC512(SECRET);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                //TODO: Baeta vid user authority fra roles
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        //jwt token issued by the boys, userID can be gotten from token 'sub' claim, expires one week from now.
        String token = JWT.create().withIssuer("theBoys").withClaim("sub", userID)
                .withClaim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY))
                .sign(algorithmHS);
        return token;

    }


    //validate token
    public Boolean validateToken(String token, User userDetails) {
        final long userID = getUserIDFromToken(token);
        return (userID == userDetails.getID() && !isTokenExpired(token));
    }
}
