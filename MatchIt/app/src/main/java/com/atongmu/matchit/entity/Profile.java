package com.atongmu.matchit.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 2016/7/15
 * User Profile
 *  user_set[user_names]
 *  user_name.finishMission *
 *  user_name.
 * @author maofagui
 * @version 1.0
 */
public class Profile {
    private Set<String> userSet;
    //private Map<String, List<Integer>> userMissions;

    public Set<String> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<String> userSet) {
        this.userSet = userSet;
    }
}
