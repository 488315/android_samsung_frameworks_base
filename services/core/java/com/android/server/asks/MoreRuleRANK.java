package com.android.server.asks;

import android.os.SystemProperties;
import android.util.Slog;
import android.util.Xml;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class MoreRuleRANK {
    public final void getASKSDataFromXML(int i, HashMap hashMap) {
        ArrayList arrayList = new ArrayList();
        if (i != 53921) {
            return;
        }
        arrayList.add("TARGET");
        arrayList.add("VALUE");
        File file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_RANK.xml");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            file.getParentFile().setReadable(true, false);
        }
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(fileReader);
                    String str = "";
                    ArrayList arrayList2 = null;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        String name = newPullParser.getName();
                        if (eventType == 2) {
                            if (((String) arrayList.get(0)).equals(name)) {
                                if (newPullParser.getAttributeValue(0) != null) {
                                    str = newPullParser.getAttributeValue(0);
                                }
                                arrayList2 = new ArrayList();
                            } else if (arrayList.contains(name) && newPullParser.getAttributeValue(0) != null && arrayList2 != null) {
                                arrayList2.add(newPullParser.getAttributeValue(0));
                            }
                        } else if (eventType == 3 && ((String) arrayList.get(0)).equals(name) && hashMap != null) {
                            hashMap.put(str, arrayList2);
                        }
                    }
                    fileReader.close();
                } catch (IOException e) {
                    try {
                        fileReader.close();
                    } catch (IOException unused) {
                    }
                    e.printStackTrace();
                } catch (XmlPullParserException e2) {
                    try {
                        fileReader.close();
                    } catch (IOException unused2) {
                    }
                    e2.printStackTrace();
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        }
    }

    public int getResult(String str, String str2) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (isDevDevice()) {
            Slog.d("PackageInformation_Famous", "check_moreRule_Famous + :" + str + "::" + str2);
        }
        HashMap hashMap = new HashMap();
        getASKSDataFromXML(53921, hashMap);
        if (hashMap.isEmpty()) {
            return 4;
        }
        if (hashMap.containsKey("SIG") && (arrayList2 = (ArrayList) hashMap.get("SIG")) != null && arrayList2.contains(str)) {
            return 4;
        }
        return (hashMap.containsKey("SIGHASH") && (arrayList = (ArrayList) hashMap.get("SIGHASH")) != null && arrayList.contains(str2)) ? 4 : 0;
    }

    public final boolean isDevDevice() {
        return "0x1".equals(SystemProperties.get("ro.boot.em.status"));
    }
}
