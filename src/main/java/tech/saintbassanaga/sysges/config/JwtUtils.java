package tech.saintbassanaga.sysges.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Configuration
public class JwtUtils {

    // Secret key for signing the JWT. It must be kept secure and private.
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token validity in milliseconds (e.g., 24 hours)
    private static final long TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

    /**
     * Generates a JWT token based on the authenticated user details.
     *
     * @param authentication the authentication object containing user information.
     * @return the generated JWT token.
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Extract user details from authentication
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Set the username as the token's subject
                .claim("roles", userDetails.getAuthorities()) // Add user roles as a claim
                .setIssuedAt(new Date()) // Set the issue time to now
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) // Set expiration time
                .signWith(secretKey) // Sign the token with the secret key
                .compact(); // Build and return the token
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Use a utility method to extract the subject
    }

    /**
     * Extracts a specific claim from the given JWT token using a claim resolver.
     *
     * @param <T>           the type of the claim.
     * @param token         the JWT token.
     * @param claimsResolver a function to extract the desired claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Parse all claims from the token
        return claimsResolver.apply(claims); // Apply the resolver to get the specific claim
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token.
     * @return the claims contained in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Specify the signing key used for validation
                .build()
                .parseClaimsJws(token) // Parse the token and extract claims
                .getBody();
    }

    /**
     * Checks if the token has expired.
     *
     * @param token the JWT token.
     * @return true if the token has expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare the token's expiration date with the current time
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token.
     * @return the expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Use a utility method to extract the expiration date
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extract the username from the token
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); // Check username match and expiration
    }
}
