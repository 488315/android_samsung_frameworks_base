package android.content.om.wallpapertheme;

import android.content.Context;
import android.content.om.WallpaperThemeConstants;
import android.content.om.WallpaperThemeUtils;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.content.NativeLibraryHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class MetaDataManager {
    public static final int UID_TYPE_BOOL = 3;
    public static final int UID_TYPE_COLOR = 1;
    public static final int UID_TYPE_INTEGER = 2;
    public static final int UID_TYPE_NONE = 0;
    public static final int UID_TYPE_TEXT = 4;
    private final String TAG = "SWT_MetaDataManager";
    private ArrayList<Package> mPackageList = new ArrayList<>();
    private HashMap<String, Uid> mUidMap = new HashMap<>();
    private HashMap<String, String> mRpUidMap = new HashMap<>();

    public void loadStaticMetadata(Context context) throws IOException, XmlPullParserException {
        clearMetadataInfo();
        new MetaData(context.getResources().getXml(18284657));
        for (int metadataXmlId : WallpaperThemeConstants.RES_METADATA_LIST) {
            try {
                new MetaData(context.getResources().getXml(metadataXmlId));
            } catch (Exception e) {
                ThemeUtil.saveSWTLog("SWT_MetaDataManager", "load static metadatas error = " + e);
            }
        }
        Iterator<Package> it = this.mPackageList.iterator();
        while (it.hasNext()) {
            Package pkg = it.next();
            for (Uid uid : pkg.getUidList()) {
                this.mUidMap.put(uid.getUidValue(), uid);
            }
        }
        ThemeUtil.saveSWTLog(
                "SWT_MetaDataManager",
                "load static metadatas, uidMap size: " + this.mUidMap.size());
    }

    private void clearMetadataInfo() {
        this.mPackageList = new ArrayList<>();
        this.mUidMap = new HashMap<>();
        this.mRpUidMap = new HashMap<>();
    }

    public void update(ApplicationInfo appInfo) {
        String[] strArr;
        int metadataResId;
        String metadataXmlNames;
        int i;
        try {
            String pkgName = appInfo.packageName;
            removePackageList(pkgName);
            Bundle pkgMetaData = appInfo.metaData;
            Resources res = WallpaperThemeUtils.getPackageResources(appInfo);
            if (res == null) {
                return;
            }
            String metadataXmlNames2 = pkgMetaData.getString(WallpaperThemeConstants.THEMING_META);
            if (metadataXmlNames2 != null && !metadataXmlNames2.isEmpty()) {
                String[] strArr2 = metadataXmlNames2.split(",\\s*");
                int length = strArr2.length;
                int i2 = 0;
                while (i2 < length) {
                    String str = strArr2[i2];
                    int metadataResId2 = res.getIdentifier(str, "xml", pkgName);
                    if (metadataResId2 > 0) {
                        XmlResourceParser parser = res.getXml(metadataResId2);
                        strArr = strArr2;
                        MetaData metadata = new MetaData(parser);
                        String rpUID = metadata.getRpUID();
                        metadataResId = metadataResId2;
                        removeUidMap(rpUID);
                        Package currentPackage = metadata.getCurrentPackage();
                        if (currentPackage != null && currentPackage.getUidList() != null) {
                            for (Uid uid : currentPackage.getUidList()) {
                                this.mUidMap.put(uid.getUidValue(), uid);
                                metadata = metadata;
                                metadataXmlNames2 = metadataXmlNames2;
                                length = length;
                            }
                            metadataXmlNames = metadataXmlNames2;
                            i = length;
                            ThemeUtil.saveSWTLog(
                                    "SWT_MetaDataManager",
                                    "metadata rpUID [" + rpUID + "] replaced by " + pkgName);
                        } else {
                            metadataXmlNames = metadataXmlNames2;
                            i = length;
                            ThemeUtil.saveSWTLog(
                                    "SWT_MetaDataManager",
                                    "It doesn't include any UID in res/xml : " + str);
                        }
                    } else {
                        strArr = strArr2;
                        metadataResId = metadataResId2;
                        metadataXmlNames = metadataXmlNames2;
                        i = length;
                        Log.e("SWT_MetaDataManager", "metadata file not found in res/xml : " + str);
                    }
                    i2++;
                    strArr2 = strArr;
                    metadataXmlNames2 = metadataXmlNames;
                    length = i;
                }
                return;
            }
            int metadataResId3 = pkgMetaData.getInt(WallpaperThemeConstants.THEMING_META);
            if (metadataResId3 > 0) {
                XmlResourceParser parser2 = res.getXml(metadataResId3);
                MetaData metadata2 = new MetaData(parser2);
                String rpUID2 = metadata2.getRpUID();
                removeUidMap(rpUID2);
                Package currentPackage2 = metadata2.getCurrentPackage();
                if (currentPackage2 != null && currentPackage2.getUidList() != null) {
                    for (Uid uid2 : currentPackage2.getUidList()) {
                        this.mUidMap.put(uid2.getUidValue(), uid2);
                        metadataResId3 = metadataResId3;
                    }
                    ThemeUtil.saveSWTLog(
                            "SWT_MetaDataManager",
                            "metadata rpUID [" + rpUID2 + "] replaced by " + pkgName);
                    return;
                }
                ThemeUtil.saveSWTLog(
                        "SWT_MetaDataManager",
                        "It doesn't include any UID in res/xml : " + pkgName);
            }
        } catch (Exception e) {
            ThemeUtil.saveSWTLog(
                    "SWT_MetaDataManager",
                    "Package : " + appInfo.packageName + " metadata update error = " + e);
        }
    }

    public ArrayList<Package> getPackageList() {
        return this.mPackageList;
    }

    public String getRefUid(String name) {
        Uid uid;
        String ref;
        if (this.mUidMap == null
                || (uid = this.mUidMap.get(name)) == null
                || (ref = uid.getReference()) == null
                || ref.isEmpty()) {
            return null;
        }
        return ref;
    }

    private void removePackageList(String packageName) {
        if (packageName == null) {
            Log.e("SWT_MetaDataManager", "null packageName");
            return;
        }
        Iterator<Package> itr = this.mPackageList.iterator();
        while (itr.hasNext()) {
            Package pkg = itr.next();
            if (packageName.equals(pkg.getPackageName())) {
                itr.remove();
                return;
            }
        }
    }

    private void removeUidMap(String rpUID) {
        if (rpUID == null) {
            return;
        }
        Map<String, Uid> result = new HashMap<>();
        for (Map.Entry<String, Uid> entry : this.mUidMap.entrySet()) {
            if (entry.getKey() != null
                    && entry.getKey().startsWith(rpUID + NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator<Map.Entry<String, Uid>> it = result.entrySet().iterator();
        while (it.hasNext()) {
            this.mUidMap.remove(it.next().getKey());
        }
    }

    public void dump(PrintWriter pw) {
        pw.println("- METADATA -");
        Iterator<Package> it = this.mPackageList.iterator();
        while (it.hasNext()) {
            Package p = it.next();
            pw.println(" [PKG : " + p.getPackageName() + NavigationBarInflaterView.SIZE_MOD_END);
            for (Uid u : p.mUidList) {
                pw.println(
                        "  -UID:"
                                + u.mUidValue
                                + ", REF:"
                                + u.mValueRef
                                + ", OPA:"
                                + u.mOpacity
                                + ", TYP:"
                                + u.mType);
                pw.println("    res : " + u.mDestAttribName);
            }
        }
    }

    public class MetaData {
        private static final String ATTR_DEFAULT_VALUE = "DefaultValue";
        private static final String ATTR_DEST_ATTR_NAME = "DestAttribName";
        private static final String ATTR_NAME = "Name";
        private static final String ATTR_OPACITY = "Opacity";
        private static final String ATTR_TARGET_PKG_NAME = "TargetPackageName";
        private static final String ATTR_UID = "UID";
        private static final String ATTR_VALUE_REF = "ValueRef";
        private static final String ATTR_VALUE_TYPE = "ValueType";
        private static final String TAG = "SWT_MetaData";
        private static final String TAG_APP_METADATA = "AppMetaData";
        private static final String TAG_INCLUDE = "Include";
        private static final String TAG_PROPERTY = "Property";
        private Package mCurrentPackage = null;
        private String mRpUID = null;

        public MetaData(XmlResourceParser xmlParser) throws XmlPullParserException, IOException {
            if (xmlParser == null) {
                Log.w(TAG, "creating metadata is failed - xmlParser is null");
                return;
            }
            int eventType = xmlParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    parseStartTag(xmlParser);
                }
                eventType = xmlParser.next();
            }
        }

        private void parseStartTag(XmlPullParser xmlParser) {
            if (xmlParser.getName().equals(TAG_APP_METADATA)) {
                String name = xmlParser.getAttributeValue(null, ATTR_NAME);
                String targetPackageName = xmlParser.getAttributeValue(null, ATTR_TARGET_PKG_NAME);
                this.mRpUID = null;
                if (WallpaperThemeConstants.METADATA_NAME_MULTIWINDOW.equals(name)) {
                    this.mCurrentPackage = getPackage(name);
                    return;
                } else {
                    this.mCurrentPackage = getPackage(targetPackageName);
                    return;
                }
            }
            if (this.mCurrentPackage != null && xmlParser.getName().equals(TAG_PROPERTY)) {
                addUID(xmlParser);
            } else if (this.mCurrentPackage != null && xmlParser.getName().equals(TAG_INCLUDE)) {
                addSeslMetaData();
            }
        }

        private Package getPackage(String packageName) {
            Iterator it = MetaDataManager.this.mPackageList.iterator();
            while (it.hasNext()) {
                Package pkg = (Package) it.next();
                if (pkg.getPackageName().equals(packageName)) {
                    return pkg;
                }
            }
            Package pkg2 = new Package(packageName);
            MetaDataManager.this.mPackageList.add(pkg2);
            return pkg2;
        }

        private void addSeslMetaData() {
            this.mCurrentPackage
                    .getUidList()
                    .addAll(((Package) MetaDataManager.this.mPackageList.getFirst()).getUidList());
        }

        private void addUID(XmlPullParser xmlParser) {
            String uID = xmlParser.getAttributeValue(null, ATTR_UID);
            String valueType = xmlParser.getAttributeValue(null, ATTR_VALUE_TYPE);
            String destAttribName = xmlParser.getAttributeValue(null, ATTR_DEST_ATTR_NAME);
            String defaultValue = xmlParser.getAttributeValue(null, ATTR_DEFAULT_VALUE);
            String valueRef = xmlParser.getAttributeValue(null, ATTR_VALUE_REF);
            String opacity = xmlParser.getAttributeValue(null, ATTR_OPACITY);
            if (uID == null) {
                ThemeUtil.saveSWTLog(
                        TAG,
                        "Parsing xml error, uid is empty. destAttributeName : " + destAttribName);
                return;
            }
            Uid newUID = new Uid(uID, valueType, destAttribName, defaultValue, valueRef, opacity);
            this.mCurrentPackage.addUid(newUID);
            String packageName = this.mCurrentPackage.getPackageName();
            if (this.mRpUID == null) {
                String[] strArr = uID.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                this.mRpUID = strArr[0];
                if (!MetaDataManager.this.mRpUidMap.containsKey(this.mRpUID)
                        || Objects.equals(
                                MetaDataManager.this.mRpUidMap.get(this.mRpUID), packageName)) {
                    MetaDataManager.this.mRpUidMap.put(this.mRpUID, packageName);
                } else {
                    ThemeUtil.saveSWTLog(
                            TAG,
                            "Abnormal metadata replacement attempts detected, RpUid : "
                                    + this.mRpUID
                                    + ", existed package : "
                                    + ((String) MetaDataManager.this.mRpUidMap.get(this.mRpUID))
                                    + ", requested package : "
                                    + packageName);
                }
            }
        }

        public Package getCurrentPackage() {
            return this.mCurrentPackage;
        }

        String getRpUID() {
            return this.mRpUID;
        }
    }

    public static class Package {
        private String mPackageName;
        private List<Uid> mUidList = new ArrayList();

        public Package(String packageName) {
            this.mPackageName = packageName;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public List<Uid> getUidList() {
            return this.mUidList;
        }

        public void addUid(Uid newUID) {
            this.mUidList.add(newUID);
        }
    }

    public static class Uid {
        private static final String VALUE_TYPE_BGCOLOR = "BgColor";
        private static final String VALUE_TYPE_BOOL = "Bool";
        private static final String VALUE_TYPE_COLOR = "Color";
        private static final String VALUE_TYPE_INTEGER = "Integer";
        private static final String VALUE_TYPE_TEXT = "Text";
        private static final String VALUE_TYPE_TEXTCOLOR = "TextColor";
        private static final String VALUE_TYPE_TINTCOLOR = "TintColor";
        private String mDefaultValue;
        private String mDestAttribName;
        private String mOpacity;
        private int mType;
        private String mUidValue;
        private String mValueRef;

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public Uid(
                String uidValue,
                String type,
                String destAttrName,
                String defaultValue,
                String valueRef,
                String opacity) {
            char c;
            this.mUidValue = uidValue;
            this.mDestAttribName = destAttrName;
            this.mValueRef = valueRef;
            this.mOpacity = (opacity == null || opacity.isEmpty()) ? null : opacity;
            this.mDefaultValue = defaultValue;
            switch (type.hashCode()) {
                case -963399416:
                    if (type.equals(VALUE_TYPE_TINTCOLOR)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -672261858:
                    if (type.equals(VALUE_TYPE_INTEGER)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2076426:
                    if (type.equals(VALUE_TYPE_BOOL)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2603341:
                    if (type.equals(VALUE_TYPE_TEXT)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 65290051:
                    if (type.equals(VALUE_TYPE_COLOR)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 940396054:
                    if (type.equals(VALUE_TYPE_TEXTCOLOR)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1459793406:
                    if (type.equals(VALUE_TYPE_BGCOLOR)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                    this.mType = 1;
                    break;
                case 4:
                    this.mType = 2;
                    break;
                case 5:
                    this.mType = 3;
                    break;
                case 6:
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

        public String getDefaultValue() {
            return this.mDefaultValue;
        }
    }
}
