package com.android.server.om.wallpapertheme;

import android.content.Context;
import android.content.om.WallpaperThemeConstants;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.pkg.AndroidPackage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class MetaDataManager {
    public String TAG = "SWT_MetaDataManager";
    public ArrayList mPackageList = new ArrayList();
    public HashMap mUidMap = null;
    public MetaData mSeslMetaData = null;

    public void loadStaticMetadata(Context context) {
        this.mSeslMetaData = new MetaData(context.getResources().getXml(18284657), null);
        for (int i : WallpaperThemeConstants.RES_METADATA_LIST) {
            try {
                new MetaData(context.getResources().getXml(i), this.mSeslMetaData);
            } catch (Exception e) {
                SemWallpaperThemeManager.saveSWTLog(this.TAG, "load static metadatas error = " + e);
            }
        }
        this.mUidMap = new HashMap();
        Iterator it = this.mPackageList.iterator();
        while (it.hasNext()) {
            for (Uid uid : ((Package) it.next()).getUidList()) {
                this.mUidMap.put(uid.getUidValue(), uid);
            }
        }
        SemWallpaperThemeManager.saveSWTLog(this.TAG, "load static metadatas, uidMap size: " + this.mUidMap.size());
    }

    public void update(AndroidPackage androidPackage) {
        try {
            String packageName = androidPackage.getPackageName();
            removePackageList(packageName);
            Bundle metaData = androidPackage.getMetaData();
            Resources packageResources = SemWallpaperThemeUtils.getPackageResources(androidPackage);
            if (packageResources == null) {
                return;
            }
            String string = metaData.getString("theming-meta");
            if (string != null && !string.isEmpty()) {
                String[] split = string.split(",|\\s");
                for (String str : split) {
                    int identifier = packageResources.getIdentifier(str, "xml", packageName);
                    if (identifier > 0) {
                        MetaData metaData2 = new MetaData(packageResources.getXml(identifier), this.mSeslMetaData);
                        String rpUID = metaData2.getRpUID();
                        removeUidMap(rpUID);
                        for (Uid uid : metaData2.getCurrentPackage().getUidList()) {
                            this.mUidMap.put(uid.getUidValue(), uid);
                        }
                        SemWallpaperThemeManager.saveSWTLog(this.TAG, "metadata rpUID [" + rpUID + "] replaced by " + packageName);
                    } else {
                        Log.e(this.TAG, "metadata file not found in res/xml : " + str);
                    }
                }
                return;
            }
            int i = metaData.getInt("theming-meta");
            if (i > 0) {
                MetaData metaData3 = new MetaData(packageResources.getXml(i), this.mSeslMetaData);
                String rpUID2 = metaData3.getRpUID();
                removeUidMap(rpUID2);
                for (Uid uid2 : metaData3.getCurrentPackage().getUidList()) {
                    this.mUidMap.put(uid2.getUidValue(), uid2);
                }
                SemWallpaperThemeManager.saveSWTLog(this.TAG, "metadata rpUID [" + rpUID2 + "] replaced by " + packageName);
            }
        } catch (Exception e) {
            SemWallpaperThemeManager.saveSWTLog(this.TAG, "Package : " + androidPackage.getPackageName() + " metadata update error = " + e);
        }
    }

    public ArrayList getPackageList() {
        return this.mPackageList;
    }

    public String getRefUid(String str) {
        Uid uid;
        String reference;
        HashMap hashMap = this.mUidMap;
        if (hashMap == null || (uid = (Uid) hashMap.get(str)) == null || (reference = uid.getReference()) == null || reference.isEmpty()) {
            return null;
        }
        return reference;
    }

    public final void removePackageList(String str) {
        if (str == null) {
            Log.e(this.TAG, "null packageName");
            return;
        }
        Iterator it = this.mPackageList.iterator();
        while (it.hasNext()) {
            if (str.equals(((Package) it.next()).getPackageName())) {
                it.remove();
                return;
            }
        }
    }

    public final void removeUidMap(String str) {
        if (str == null) {
            return;
        }
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : this.mUidMap.entrySet()) {
            if (entry.getKey() != null) {
                if (((String) entry.getKey()).startsWith(str + PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                    hashMap.put((String) entry.getKey(), (Uid) entry.getValue());
                }
            }
        }
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            this.mUidMap.remove(((Map.Entry) it.next()).getKey());
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("- METADATA -");
        Iterator it = this.mPackageList.iterator();
        while (it.hasNext()) {
            Package r0 = (Package) it.next();
            printWriter.println(" [PKG : " + r0.getPackageName() + "]");
            for (Uid uid : r0.mUidList) {
                printWriter.println("  -UID:" + uid.mUidValue + ", REF:" + uid.mValueRef + ", OPA:" + uid.mOpacity + ", TYP:" + uid.mType);
                StringBuilder sb = new StringBuilder();
                sb.append("    res : ");
                sb.append(uid.mDestAttribName);
                printWriter.println(sb.toString());
            }
        }
    }

    public class MetaData {
        public MetaData mSeslMetaData;
        public String TAG = "SWT_MetaData";
        public String TAG_INCLUDE = "Include";
        public String TAG_APP_METADATA = "AppMetaData";
        public String TAG_PROPERTY = "Property";
        public String ATTR_UID = "UID";
        public String ATTR_VALUE_TYPE = "ValueType";
        public String ATTR_DEST_ATTR_NAME = "DestAttribName";
        public String ATTR_DEFAULT_VALUE = "DefaultValue";
        public String ATTR_P_OPTION = "POption";
        public String ATTR_VALUE_REF = "ValueRef";
        public String ATTR_OPACITY = "Opacity";
        public String ATTR_NAME = "Name";
        public String ATTR_TARGET_PKG_NAME = "TargetPackageName";
        public Package mCurrentPackage = null;
        public String mRpUID = null;

        public MetaData(XmlResourceParser xmlResourceParser, MetaData metaData) {
            this.mSeslMetaData = metaData;
            if (xmlResourceParser == null) {
                Log.w("SWT_MetaData", "creating metadata is failed - xmlParser is null");
                return;
            }
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    parseStartTag(xmlResourceParser);
                }
                eventType = xmlResourceParser.next();
            }
        }

        public final void parseStartTag(XmlPullParser xmlPullParser) {
            if (xmlPullParser.getName().equals(this.TAG_APP_METADATA)) {
                String attributeValue = xmlPullParser.getAttributeValue(null, this.ATTR_NAME);
                String attributeValue2 = xmlPullParser.getAttributeValue(null, this.ATTR_TARGET_PKG_NAME);
                if ("Multi window".equals(attributeValue)) {
                    this.mCurrentPackage = getPackage(attributeValue);
                    return;
                } else {
                    this.mCurrentPackage = getPackage(attributeValue2);
                    return;
                }
            }
            if (this.mCurrentPackage != null && xmlPullParser.getName().equals(this.TAG_PROPERTY)) {
                addUID(xmlPullParser);
            } else {
                if (this.mCurrentPackage == null || !xmlPullParser.getName().equals(this.TAG_INCLUDE)) {
                    return;
                }
                addSeslMetaData();
            }
        }

        public final Package getPackage(String str) {
            Iterator it = MetaDataManager.this.mPackageList.iterator();
            while (it.hasNext()) {
                Package r1 = (Package) it.next();
                if (r1.getPackageName().equals(str)) {
                    return r1;
                }
            }
            Package r0 = new Package(str);
            MetaDataManager.this.mPackageList.add(r0);
            return r0;
        }

        public final void addSeslMetaData() {
            this.mCurrentPackage.getUidList().addAll(((Package) MetaDataManager.this.mPackageList.get(0)).getUidList());
        }

        public final void addUID(XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, this.ATTR_UID);
            String attributeValue2 = xmlPullParser.getAttributeValue(null, this.ATTR_VALUE_TYPE);
            String attributeValue3 = xmlPullParser.getAttributeValue(null, this.ATTR_DEST_ATTR_NAME);
            String attributeValue4 = xmlPullParser.getAttributeValue(null, this.ATTR_DEFAULT_VALUE);
            String attributeValue5 = xmlPullParser.getAttributeValue(null, this.ATTR_P_OPTION);
            String attributeValue6 = xmlPullParser.getAttributeValue(null, this.ATTR_VALUE_REF);
            String attributeValue7 = xmlPullParser.getAttributeValue(null, this.ATTR_OPACITY);
            if (attributeValue == null) {
                SemWallpaperThemeManager.saveSWTLog(this.TAG, "Parsing xml error, uid is empty. destAttributeName : " + attributeValue3);
                return;
            }
            this.mCurrentPackage.addUid(new Uid(attributeValue, attributeValue2, attributeValue3, attributeValue4, attributeValue6, attributeValue5, attributeValue7));
            if (this.mRpUID == null) {
                this.mRpUID = attributeValue.split(PackageManagerShellCommandDataLoader.STDIN_PATH)[0];
            }
        }

        public Package getCurrentPackage() {
            return this.mCurrentPackage;
        }

        public String getRpUID() {
            return this.mRpUID;
        }
    }

    public class Package {
        public String mPackageName;
        public List mUidList = new ArrayList();

        public Package(String str) {
            this.mPackageName = str;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public List getUidList() {
            return this.mUidList;
        }

        public void addUid(Uid uid) {
            this.mUidList.add(uid);
        }
    }

    public class Uid {
        public String mDefaultValue;
        public String mDestAttribName;
        public String mOpacity;
        public String mPOption;
        public int mType;
        public String mUidValue;
        public String mValueRef;

        public Uid(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            this.mUidValue = str;
            this.mDestAttribName = str3;
            this.mValueRef = str5;
            this.mPOption = str6;
            this.mOpacity = (str7 == null || str7.isEmpty()) ? null : str7;
            this.mDefaultValue = str4;
            str2.hashCode();
            switch (str2) {
                case "TintColor":
                case "Color":
                case "TextColor":
                case "BgColor":
                    this.mType = 1;
                    break;
                case "Integer":
                    this.mType = 2;
                    break;
                case "Bool":
                    this.mType = 3;
                    break;
                case "Text":
                    this.mType = 4;
                    break;
                default:
                    this.mType = 0;
                    break;
            }
        }

        public String getOpacity() {
            return this.mOpacity;
        }

        public String getUidValue() {
            return this.mUidValue;
        }

        public int getType() {
            return this.mType;
        }

        public String getDestAttribName() {
            return this.mDestAttribName;
        }

        public String getReference() {
            return this.mValueRef;
        }
    }
}
