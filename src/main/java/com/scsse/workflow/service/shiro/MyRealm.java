package com.scsse.workflow.service.shiro;

import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityQuerier authorityQuerier;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Object principal = principals.getPrimaryPrincipal();
        User userDTO = (User) principal;

        int id = userDTO.getId();//user id not student id

        //获取用户的角色、权限信息
        List<String> roleNameList = authorityQuerier.getRoleNameListByUser_id(id);
     //   List<String> permissionNameList = authorityQuerier.getPermissionNameListByUser_id(id);

        //注入角色与权限
        System.out.println(roleNameList);
        if(roleNameList.contains("admin")){
            roleNameList= authorityQuerier.getAllRoleName();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRoles(roleNameList);
       // info.addStringPermissions(permissionNameList);


        return info;
    }

    /**
     * 认证
     * 登录的时候会用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //数据库匹配，认证
        System.out.println(token);
        String username = token.getUsername();
        System.out.println(username);//web端用姓名
        System.out.println(token.getPassword());
        User user = userService.findUserByName(username);
        System.out.println(user);
        if(user == null){
            throw new UnknownAccountException("用户名不存在");
        }
        else if(user.getState()==0)
        {
            throw new LockedAccountException("该账户已被锁定");
        }


        Object pricipal = username;
        Object credentials = user.getPassword();
        ByteSource credentialsSalt=ByteSource.Util.bytes(username);
        AuthenticationInfo info= new SimpleAuthenticationInfo(user,credentials,credentialsSalt,super.getName());
        return info;

    }
}
