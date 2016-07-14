package com.atongmu.matchit.service;

import android.util.Xml;

import com.atongmu.matchit.entity.Mission;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016/7/6
 *
 * @author maofagui
 * @version 1.0
 */
public class MissionService {
    //保存xml文件
    public static void saveXML(List<Mission> list, Writer write)throws Throwable
    {
        XmlSerializer serializer = Xml.newSerializer();//序列化
        serializer.setOutput(write);//输出流
        serializer.startDocument("UTF-8", true);//开始文档
        serializer.startTag(null, "missions");
        //循环添加
        for (Mission mission : list) {
            serializer.startTag(null, "mission");
            serializer.attribute(null, "id", mission.getId().toString() );//设置id属性及属性值

            serializer.startTag(null, "name");
            serializer.text(mission.getName());//文本节点的文本值--name
            serializer.endTag(null, "name");

            serializer.startTag(null, "width");
            serializer.text(mission.getWidth().toString());
            serializer.endTag(null, "width");

            serializer.startTag(null, "height");
            serializer.text(mission.getHeight().toString());
            serializer.endTag(null, "height");

            serializer.startTag(null, "size");
            serializer.text(mission.getSize().toString());
            serializer.endTag(null, "size");

            serializer.startTag(null, "timeSpan");
            serializer.text(mission.getTime().toString());
            serializer.endTag(null, "timeSpan");

            serializer.startTag(null, "itemIds");
            serializer.text(mission.getItemIds());
            serializer.endTag(null, "itemIds");

            serializer.startTag(null, "bonusId");
            serializer.text(mission.getBonusId().toString());
            serializer.endTag(null, "bonusId");

            serializer.endTag(null, "mission");
        }
        serializer.endTag(null, "missions");
        serializer.endDocument();
        write.flush();
        write.close();
    }

    public List<Mission> getMissions(InputStream stream) throws Throwable
    {
        List<Mission> list =null;
        Mission mission =null;
        XmlPullParser parser =Xml.newPullParser();
        parser.setInput(stream,"UTF-8");
        int type =parser.getEventType();//产生第一个事件
        //只要当前事件类型不是”结束文档“，就去循环
        while(type != XmlPullParser.END_DOCUMENT)
        {
            switch (type) {
                case XmlPullParser.START_DOCUMENT:
                    list = new ArrayList<Mission>();
                    break;
                case XmlPullParser.START_TAG:
                    String name=parser.getName();//获取解析器当前指向的元素名称
                    if("mission".equals(name))
                    {
                        mission =new Mission();
                        mission.setId(new Integer(parser.getAttributeValue(0)));
                    }
                    if(mission != null)
                    {
                        //获取解析器当前指向的元素的下一个文本节点的文本值
                        if("name".equals(name)){
                            mission.setName(parser.nextText());
                        }else if("width".equals(name)){
                            mission.setWidth(new Integer(parser.nextText()));
                        }else if("height".equals(name)){
                            mission.setHeight(new Integer(parser.nextText()));
                        }else if("size".equals(name)){
                            mission.setSize(new Integer(parser.nextText()));
                        }else if("timeSpan".equals(name)){
                            mission.setTime(new Integer(parser.nextText()));
                        }else if("itemIds".equals(name)){
                            mission.setItemIds(parser.nextText());
                        }else if("bonusId".equals(name)){
                            mission.setBonusId(new Integer(parser.nextText()));
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("mission".equals(parser.getName()))
                    {
                        list.add(mission);
                        mission=null;
                    }
                    break;
            }
            type=parser.next();//这句千万别忘了哦
        }
        return list;
    }
}
