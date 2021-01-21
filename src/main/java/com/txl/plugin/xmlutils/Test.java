package com.txl.plugin.xmlutils;

import java.util.Map;

public class Test {
    public static void main(String[] args){
        Map<String,String> map = AndroidDimenXMLParser.readDimensXML("D:\\AndroidStudioProjects\\BuildAdaption\\adaption_plugin\\src\\main\\java\\com\\txl\\plugin\\xmlutils\\dimens.xml");
        System.out.println("map "+map);
    }
}
