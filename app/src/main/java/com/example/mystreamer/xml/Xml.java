package com.example.mystreamer.xml;

//https://www.tutorialspoint.com/android/android_xml_parsers.htm

import android.content.Context;
import android.content.SharedPreferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class Xml {


    public Observable<List<ArrayList<String>>> getObservableXml(String ip)
    {
        return Observable.create(new ObservableOnSubscribe<List<ArrayList<String>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArrayList<String>>> emitter) throws Exception {
              //  ArrayList<String> urls=getXML(ip).get(0);
              //  ArrayList<String> chName=getXML(ip).get(1);
                List<ArrayList<String>> list=getXML(ip);
                if (list!=null && !emitter.isDisposed())
                {
                    emitter.onNext(list);
                    emitter.onComplete();
                }
                else
                {
                    //emitter.onError(new Exception());
                    List<ArrayList<String>>listE=new ArrayList<>();
                    ArrayList<String> error=new ArrayList<>();
                    error.add("error");
                    listE.add(error);
                    emitter.onNext(listE);
                }
            }
        });
    }


    public static List<ArrayList<String>> getXML(String ip) {

        try {
            List<ArrayList<String>> file=new ArrayList<>();
            ArrayList<String> urls = new ArrayList<>();
            ArrayList<String> chNames = new ArrayList<>();

           InputStream input = new URL("http://"+ip+"/channel.xml").openStream();
            //InputStream input=new URL("http://saatmedia.ir:3180/test/channels/channel.xml").openStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(input);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("channel");

            int size = nList.getLength();

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element1 = (Element) node;
                    String url = getValue("url", element1);
                    urls.add(url);
                    String chName=getValue("title",element1);
                    chNames.add(chName);
                    System.out.println(url + "majid");
                }
            }
            file.add(urls);
            file.add(chNames);
            return file;
        }
        catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }

    private static String getValue(String tag,Element element)
    {
        NodeList nodeList=element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node=nodeList.item(0);
        if (node==null)
        {
            return "";
        }
        else {
            String a = node.getNodeValue();
            return node.getNodeValue();
        }
    }


}
