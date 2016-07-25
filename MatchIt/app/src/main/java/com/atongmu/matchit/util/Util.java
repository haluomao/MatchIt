package com.atongmu.matchit.util;

import com.atongmu.matchit.common.Consts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mfg on 16/07/21.
 */
public class Util {
    public static String turnStr(int[] ids) {
        if (null == ids) return null;
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < ids.length; i++) {
            if (i != 0)
                res.append(Consts.SEPARATOR);
            res.append(ids[i]);
        }
        return res.toString();
    }

    public static String turnStr(List<String> list) {
        if (null == list)return "";
        String res="";
        for(int i=0; i<list.size(); i++){
            if(i!=0)
                res += Consts.SEPARATOR;
            res +=list.get(i);
        }
        return res;
    }

    public static int[] parseStr(String ids) {
        if (null == ids) return null;
        String[] idStrArr = ids.split(Consts.SEPARATOR);
        int[] res = new int[idStrArr.length];
        for (int i = 0; i < idStrArr.length; i++) {
            res[i] = Integer.parseInt(idStrArr[i]);
        }
        return res;
    }

    public static List<String> parseList(String ids) {
        if (null == ids) return null;
        String[] idStrArr = ids.split(Consts.SEPARATOR);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < idStrArr.length; i++) {
            res.add(idStrArr[i]);
        }
        return res;
    }
}
