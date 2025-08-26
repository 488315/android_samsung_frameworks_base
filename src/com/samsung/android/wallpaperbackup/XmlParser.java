package com.samsung.android.wallpaperbackup;

import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes6.dex */
public class XmlParser extends DefaultHandler {
    private final String TAG = "XmlParser";
    private boolean mCurrentElement = false;
    private String mCurrentValue = "";
    private WallpaperUser mItem = null;
    private ArrayList<WallpaperUser> mItemsList = new ArrayList<>();

    XmlParser(String str) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                    xMLReader.setContentHandler(this);
                    File file = new File(str);
                    if (file.exists() && file.canRead()) {
                        FileInputStream fileInputStream2 = new FileInputStream(file);
                        try {
                            xMLReader.parse(new InputSource(new InputStreamReader(fileInputStream2)));
                            fileInputStream = fileInputStream2;
                        } catch (IOException e) {
                            e = e;
                            fileInputStream = fileInputStream2;
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                                return;
                            }
                            return;
                        } catch (ParserConfigurationException e2) {
                            e = e2;
                            fileInputStream = fileInputStream2;
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                                return;
                            }
                            return;
                        } catch (SAXException e3) {
                            e = e3;
                            fileInputStream = fileInputStream2;
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream = fileInputStream2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } else {
                        Log.d("XmlParser", "xml file is not exists. " + str);
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e5) {
                e = e5;
            } catch (ParserConfigurationException e6) {
                e = e6;
            } catch (SAXException e7) {
                e = e7;
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
    }

    public WallpaperUser getObject() {
        return this.mItem;
    }

    public ArrayList<WallpaperUser> getItemsList() {
        return this.mItemsList;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) {
        this.mCurrentElement = true;
        this.mCurrentValue = "";
        if (str2.equalsIgnoreCase(GenerateXML.OBJECT_LIST_TAG)) {
            WallpaperUser wallpaperUser = new WallpaperUser();
            this.mItem = wallpaperUser;
            this.mItemsList.add(wallpaperUser);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) {
        this.mCurrentElement = false;
        if (str2.equalsIgnoreCase("width")) {
            this.mItem.setWidth(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase("height")) {
            this.mItem.setHeight(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase("transparency")) {
            this.mItem.setTransparency(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.DEVICETYPE)) {
            this.mItem.setDeviceType(this.mCurrentValue);
            return;
        }
        if (str2.equalsIgnoreCase("path")) {
            this.mItem.setPath(this.mCurrentValue);
            return;
        }
        if (str2.equalsIgnoreCase("component")) {
            this.mItem.setComponent(this.mCurrentValue);
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.WPTYPE)) {
            this.mItem.setWpType(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase("uri")) {
            this.mItem.setUri(Uri.parse(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.EXTERNAL_PARAMS)) {
            this.mItem.setExternalParams(this.mCurrentValue);
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.COMPONENT_NAME)) {
            this.mItem.setComponentName(this.mCurrentValue);
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.PAIRED)) {
            this.mItem.setIsHomeAndLockPaired(Boolean.parseBoolean(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase("orientation")) {
            this.mItem.setOrientation(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.TILTSETTING)) {
            this.mItem.setTiltSettingValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase("left")) {
            this.mItem.setLeftValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.TOP)) {
            this.mItem.setTopValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase("right")) {
            this.mItem.setRightValue(Integer.parseInt(this.mCurrentValue));
            return;
        }
        if (str2.equalsIgnoreCase(GenerateXML.BOTTOM)) {
            this.mItem.setBottomValue(Integer.parseInt(this.mCurrentValue));
        } else if (str2.equalsIgnoreCase(GenerateXML.ROTATION)) {
            this.mItem.setRotationValue(Integer.parseInt(this.mCurrentValue));
        } else if (str2.equalsIgnoreCase(GenerateXML.COVERTYPE)) {
            this.mItem.setCoverType(this.mCurrentValue);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) {
        if (this.mCurrentElement) {
            this.mCurrentValue += new String(cArr, i, i2);
        }
    }
}
