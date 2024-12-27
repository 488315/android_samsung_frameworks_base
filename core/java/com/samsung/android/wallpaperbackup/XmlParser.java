package com.samsung.android.wallpaperbackup;

import android.net.Uri;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParser extends DefaultHandler {
    private final String TAG = "XmlParser";
    private boolean mCurrentElement = false;
    private String mCurrentValue = "";
    private WallpaperUser mItem = null;
    private ArrayList<WallpaperUser> mItemsList = new ArrayList<>();

    XmlParser(String fileName) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        FileInputStream fis = null;
        try {
            try {
                try {
                    try {
                        try {
                            SAXParser saxParser = saxParserFactory.newSAXParser();
                            XMLReader xmlReader = saxParser.getXMLReader();
                            xmlReader.setContentHandler(this);
                            File file = new File(fileName);
                            if (file.exists() && file.canRead()) {
                                fis = new FileInputStream(file);
                                InputSource objSource = new InputSource(new InputStreamReader(fis));
                                xmlReader.parse(objSource);
                            } else {
                                Log.d("XmlParser", "xml file is not exists. " + fileName);
                            }
                            if (fis != null) {
                                fis.close();
                            }
                        } catch (SAXException e) {
                            e.printStackTrace();
                            if (0 != 0) {
                                fis.close();
                            }
                        }
                    } catch (ParserConfigurationException e2) {
                        e2.printStackTrace();
                        if (0 != 0) {
                            fis.close();
                        }
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                    if (0 != 0) {
                        fis.close();
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fis.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public WallpaperUser getObject() {
        return this.mItem;
    }

    public ArrayList<WallpaperUser> getItemsList() {
        return this.mItemsList;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.mCurrentElement = true;
        this.mCurrentValue = "";
        if (localName.equalsIgnoreCase(GenerateXML.OBJECT_LIST_TAG)) {
            this.mItem = new WallpaperUser();
            this.mItemsList.add(this.mItem);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String uri, String localName, String qName) {
        this.mCurrentElement = false;
        if (localName.equalsIgnoreCase("width")) {
            this.mItem.setWidth(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase("height")) {
            this.mItem.setHeight(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase("transparency")) {
            this.mItem.setTransparency(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.DEVICETYPE)) {
            this.mItem.setDeviceType(this.mCurrentValue);
            return;
        }
        if (localName.equalsIgnoreCase("path")) {
            this.mItem.setPath(this.mCurrentValue);
            return;
        }
        if (localName.equalsIgnoreCase("component")) {
            this.mItem.setComponent(this.mCurrentValue);
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.WPTYPE)) {
            this.mItem.setWpType(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase("uri")) {
            this.mItem.setUri(Uri.parse(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.EXTERNAL_PARAMS)) {
            this.mItem.setExternalParams(this.mCurrentValue);
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.COMPONENT_NAME)) {
            this.mItem.setComponentName(this.mCurrentValue);
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.PAIRED)) {
            this.mItem.setIsHomeAndLockPaired(Boolean.parseBoolean(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase("orientation")) {
            this.mItem.setOrientation(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.TILTSETTING)) {
            this.mItem.setTiltSettingValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase("left")) {
            this.mItem.setLeftValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.TOP)) {
            this.mItem.setTopValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase("right")) {
            this.mItem.setRightValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (localName.equalsIgnoreCase(GenerateXML.BOTTOM)) {
            this.mItem.setBottomValue(Integer.parseInt(this.mCurrentValue));
        } else if (localName.equalsIgnoreCase(GenerateXML.ROTATION)) {
            this.mItem.setRotationValue(Integer.parseInt(this.mCurrentValue));
        } else if (localName.equalsIgnoreCase(GenerateXML.COVERTYPE)) {
            this.mItem.setCoverType(this.mCurrentValue);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] ch, int start, int length) {
        if (this.mCurrentElement) {
            this.mCurrentValue += new String(ch, start, length);
        }
    }
}
