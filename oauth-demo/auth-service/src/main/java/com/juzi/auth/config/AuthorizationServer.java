package com.juzi.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;

/**
 * @author codejuzi
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Resource(name = "authorizationServerTokenServicesCustom")
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Resource
    private AuthenticationManager authenticationManager;

    //客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        // 使用in-memory存储
        clients.inMemory()
                // client_id
                .withClient("auth-demo")
                .secret("auth-demo-secret")//客户端密钥
//                .secret(new BCryptPasswordEncoder().encode("auth-demo-secret"))//客户端密钥
                .resourceIds("auth-demo")//资源列表
                // 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                // 允许的授权范围
                .scopes("all")
                //false跳转到授权页面
                .autoApprove(false)
                //客户端接收授权码的重定向地址
                .redirectUris("https://www.baidu.com")
        ;
    }


    //令牌端点的访问配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //认证管理器
                .authenticationManager(authenticationManager)
                //令牌管理服务
                .tokenServices(authorizationServerTokenServices)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    //令牌端点的安全配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
                .checkTokenAccess("permitAll()")                  //oauth/check_token公开
                .allowFormAuthenticationForClients()                //表单认证（申请令牌）
        ;
    }
}
