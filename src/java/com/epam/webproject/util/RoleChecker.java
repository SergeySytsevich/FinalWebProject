
package com.epam.webproject.util;

import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.content.RequestContent;


public class RoleChecker {
     public void checkRole(RequestContent requestContent) {
        String role = requestContent.getSessionAttributes().get(GeneralConstant.UserPageConstant.ROLE).toString();
        if (role == null) {
            requestContent.getRequestAttributes().put(GeneralConstant.ErrorConstant.ERROR, true);
        }
    }
}
