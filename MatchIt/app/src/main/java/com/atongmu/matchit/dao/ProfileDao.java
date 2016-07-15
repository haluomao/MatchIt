package com.atongmu.matchit.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.atongmu.matchit.common.Consts;
import com.atongmu.matchit.entity.Profile;
import com.atongmu.matchit.util.Dao;

/**
 *
 * 用于访问用户信息
 * 2016/7/15
 *
 * @author maofagui
 * @version 1.0
 */
public class ProfileDao {

    private Dao dao;

    public ProfileDao(Context context){
        dao = new Dao(context, Consts.DB_PROFILE);
    }

    public Profile getProfile(){
        String users = dao.getString(Consts.PROFILE_KEY_USERS);
        //TODO
        return null;
    }
}
