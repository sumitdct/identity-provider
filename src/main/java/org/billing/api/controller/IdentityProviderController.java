package org.billing.api.controller;
import com.sumitchouksey.book.response.json.handler.ResponseJsonHandler;
import com.sumitchouksey.book.response.json.handler.util.ResponseConstants;
import com.sumitchouksey.book.response.json.handler.util.ResponseJsonUtil;
import com.sumitchouksey.book.vos.ClientVo;
import com.sumitchouksey.book.vos.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.billing.api.utility.ResponseConstant;
@RestController
@RequestMapping("/identity")
public class IdentityProviderController {

    /**
     * Return user and client details from oauth2 token which stored in session
     * @return UserVo
     *          - A custom session object contains user details along with client
     * @author SUMIT CHOUKSEY  "sumitchouksey23152gmail.com"
     */
    @GetMapping("/user-vo")
    public UserVo getUserVo()
    {
        try {
            /**
             * Retrieve Spring Authentication from OAuth2Authentication to get session details via token
             */
            OAuth2Authentication auth2Authentication= (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            Authentication authentication=  auth2Authentication.getUserAuthentication();
            UserVo userVo  = (UserVo) authentication.getDetails();
            return userVo;
        }
        catch(Exception e){
        }
        return null;
    }

    /**
     * Return client name from the token after validating token
     * @return String
     *          - A String representing client name
     * @author SUMIT CHOUKSEY  "sumitchouksey23152gmail.com"
     */
    @GetMapping("get-client-from-token")
    public String getClientFromToken(){
        try{
            OAuth2Authentication auth2Authentication= (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            Authentication authentication=  auth2Authentication.getUserAuthentication();
            UserVo userVO  = (UserVo) authentication.getDetails();
            if(userVO!=null)
            {
                List<ClientVo> clientVoCollection= (List<ClientVo>) userVO.getClient();
                if(clientVoCollection!=null){
                    if(!clientVoCollection.isEmpty()){
                        return clientVoCollection.get(0).getClientName();
                    }
                }
            }
        }
        catch(Exception e){
        }
        return null;
    }

    /**
     * Revoke the aouth2 tokens
     * @param httpServletRequest
     *  A HttpServletRequest instance
     * @return ResponseJsonHandler
     *          - Return  ResponseJsonHandler customized JSON response
     * @author SUMIT CHOUKSEY  "sumitchouksey23152gmail.com"
     */
    @GetMapping("/revoke")
    public ResponseJsonHandler revokeTokens(HttpServletRequest httpServletRequest){
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if(authorizationHeader==null)
            return ResponseJsonUtil.getResponse(ResponseConstant.AUTHORIZATION_HEADER_NULL.getStatus(),400,"Bad Request",true);
        String tokenValue= authorizationHeader.split("\\s+")[1];
        boolean _status =this.revokeToken(tokenValue);
        if(_status)
            return ResponseJsonUtil.getResponse(ResponseConstant.SUCCESS.getStatus(),200, ResponseConstants.SUCCESS.getStatus(),false);
        else
            return ResponseJsonUtil.getResponse(ResponseConstant.ERROR_IN_PROCESSING_REQUEST.getStatus(),403,"Forbidden",true);
    }

    @Autowired
    private TokenStore tokenStore;

    /**
     * Returns revoking status of token
     * @param tokenValue
     *            - tokenValue as String which o be revoked or destroy
     * @return boolean
     *          - true if token is successfully revoked otherwise false
     * @author SUMIT CHOUKSEY  "sumitchouksey23152gmail.com"
     */
    public boolean revokeToken(String tokenValue){
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }
}
