package com.intertek.demo.common.authentication;
import com.intertek.demo.common.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:39
 */
@Helper
public class ShiroHelper extends ShiroRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentuserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
