package com.platon.browser.util;

import com.platon.browser.dto.keybase.Basics;
import com.platon.browser.dto.keybase.KeyBaseUserInfo;
import com.platon.browser.dto.keybase.Them;

import java.util.List;

/**
 * @Auther: dongqile
 * @Date: 2019/9/28
 * @Description: keyBase解析工具
 */
public class KeyBaseAnalysis {

    private KeyBaseAnalysis(){}

    public static String getKeyBaseUseName(KeyBaseUserInfo keyBaseUser){
        List<Them> thems = keyBaseUser.getThem();
        if (thems == null || thems.isEmpty()) return null;
        // 取最新一条
        Them them = thems.get(0);
        Basics basics = them.getBasics();
        return basics.getUsername();
    }


    public static String getKeyBaseIcon( KeyBaseUserInfo keyBaseUser){
    	List<Them> thems = keyBaseUser.getThem();
        if (thems == null || thems.isEmpty()) return null;
        // 取最新一条
        Them them = thems.get(0);
        //获取头像
        return them.getPictures().getPrimary().getUrl();
    }
}