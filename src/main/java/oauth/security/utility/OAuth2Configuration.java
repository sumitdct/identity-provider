package oauth.security.utility;

import lombok.*;

import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuth2Configuration {
    private Collection<String> resourceIds;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Collection<String> authorizedGrantType;
    private Collection<String> scopes;
    private Collection<String> registeredRedirectUris;
    private Collection<String> autoApproveScopes;
}
