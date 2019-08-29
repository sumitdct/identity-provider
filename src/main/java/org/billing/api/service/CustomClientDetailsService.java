package org.billing.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import oauth.security.custom.config.CustomOAuth2Exception;
import oauth.security.utility.OAuth2ConfigProcessor;
import oauth.security.utility.OAuth2Configuration;
import org.billing.api.entities.ClientEntity;
import org.billing.api.repository.IdentityProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.billing.api.utility.ResponseConstant;

@Service
public class CustomClientDetailsService implements ClientDetailsService{

    @Autowired
    private IdentityProviderRepository identityProviderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OAuth2ConfigProcessor oAuth2ConfigProcessor;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if(clientId==null)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.CLIENT_IS_NULL.getStatus(),"400","Bad Request");
        else if(org.apache.commons.lang.StringUtils.isBlank(clientId))
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.CLIENT_IS_EMPTY.getStatus(),"400","Bad Request");
        ClientEntity clientEntity  = identityProviderRepository.getClientEntity(clientId);
        if(clientEntity==null)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.CLIENT_NOT_FOUND.getStatus(),"404","Not Found");

        String configurations  = clientEntity.getConfigurations();
        if(configurations!=null){
            ObjectNode objectNode;
            try{
                objectNode = (ObjectNode) objectMapper.readTree(configurations);
                if(objectNode!=null){
                    ObjectNode oauthNode = (ObjectNode) objectNode.get("oauthConfig");
                    OAuth2Configuration oAuth2Configuration = oAuth2ConfigProcessor.getOAuth2Configuration(oauthNode);
                    if(oAuth2Configuration!=null){
                        BaseClientDetails baseClientDetails  = new BaseClientDetails();
                        baseClientDetails.setClientId(clientId);
                        baseClientDetails.setAccessTokenValiditySeconds(oAuth2Configuration.getAccessTokenValiditySeconds());
                        baseClientDetails.setRefreshTokenValiditySeconds(oAuth2Configuration.getRefreshTokenValiditySeconds());
                        baseClientDetails.setScope(oAuth2Configuration.getScopes());
                        baseClientDetails.setAuthorizedGrantTypes(oAuth2Configuration.getAuthorizedGrantType());
                        return baseClientDetails;
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
