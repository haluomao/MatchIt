package com.atongmu.matchit.dao;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.atongmu.matchit.entity.Mission;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mfg on 16/07/14.
 */
public class MissionDao {
    public static Map<Integer, Mission> getMission(XmlResourceParser xmlParser) throws Exception {
        Map<Integer, Mission> res = null;
        Mission mission = null;
//        XmlPullParser pullParser = Xml.newPullParser();
//        pullParser.setInput(xml, "UTF-8"); //为Pull解释器设置要解析的XML数据
        int event = xmlParser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT){

            switch (event) {

                case XmlPullParser.START_DOCUMENT:
                    res = new HashMap<Integer, Mission>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("mission".equals(xmlParser.getName())){
                        int id = Integer.valueOf(xmlParser.getAttributeValue(0));
                        mission = new Mission();
                        mission.setId(id);
                    }
                    if ("name".equals(xmlParser.getName())){
                        String name = xmlParser.nextText();
                        mission.setName(name);
                    }
                    if ("time".equals(xmlParser.getName())){
                        int time = Integer.valueOf(xmlParser.nextText());
                        mission.setTime(time);
                    }
                    if ("width".equals(xmlParser.getName())){
                        int width = Integer.valueOf(xmlParser.nextText());
                        mission.setWidth(width);
                    }
                    if ("height".equals(xmlParser.getName())){
                        int height = Integer.valueOf(xmlParser.nextText());
                        mission.setHeight(height);
                    }
                    if ("size".equals(xmlParser.getName())){
                        int size = Integer.valueOf(xmlParser.nextText());
                        mission.setSize(size);
                    }
                    if ("bonus_id".equals(xmlParser.getName())){
                        int bonus_id = Integer.valueOf(xmlParser.nextText());
                        mission.setBonusId(bonus_id);
                    }
                    if ("next".equals(xmlParser.getName())){
                        int next = Integer.valueOf(xmlParser.nextText());
                        mission.setNext(next);
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if ("mission".equals(xmlParser.getName())){
                        res.put(mission.getId(), mission);
                        mission = null;
                    }
                    break;

            }

            event = xmlParser.next();
        }


        return res;
    }
}
