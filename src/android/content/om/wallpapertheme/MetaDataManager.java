package android.content.om.wallpapertheme;

import android.content.Context;
import android.content.om.WallpaperThemeConstants;
import android.content.om.WallpaperThemeUtils;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class MetaDataManager {
    public static final int UID_TYPE_BOOL = 3;
    public static final int UID_TYPE_COLOR = 1;
    public static final int UID_TYPE_INTEGER = 2;
    public static final int UID_TYPE_NONE = 0;
    public static final int UID_TYPE_TEXT = 4;
    public static final int UPDATE_ABNORMAL_METADATA = 2;
    public static final int UPDATE_FAIL = 0;
    public static final int UPDATE_SUCCESS = 1;
    private final String TAG = "SWT_MetaDataManager";
    private ArrayList<Package> mPackageList = new ArrayList<>();
    private HashMap<String, Uid> mUidMap = new HashMap<>();
    private HashMap<String, String> mRpUidMap = new HashMap<>();

    public void loadStaticMetadata(Context context) throws XmlPullParserException, IOException {
        clearMetadataInfo();
        new MetaData(context.getResources().getXml(18284660));
        for (int i : WallpaperThemeConstants.RES_METADATA_LIST) {
            try {
                new MetaData(context.getResources().getXml(i));
            } catch (Exception e) {
                ThemeUtil.saveSWTLog("SWT_MetaDataManager", "load static metadatas error = " + e);
            }
        }
        Iterator<Package> it = this.mPackageList.iterator();
        while (it.hasNext()) {
            for (Uid uid : it.next().getUidList()) {
                this.mUidMap.put(uid.getUidValue(), uid);
            }
        }
        ThemeUtil.saveSWTLog("SWT_MetaDataManager", "load static metadatas, uidMap size: " + this.mUidMap.size());
    }

    private void clearMetadataInfo() {
        this.mPackageList = new ArrayList<>();
        this.mUidMap = new HashMap<>();
        this.mRpUidMap = new HashMap<>();
    }

    public int update(ApplicationInfo applicationInfo) {
        int i;
        int iIntValue;
        int i2;
        int i3;
        int i4 = 0;
        try {
            String str = applicationInfo.packageName;
            removePackageList(str);
            Bundle bundle = applicationInfo.metaData;
            Resources packageResources = WallpaperThemeUtils.getPackageResources(applicationInfo);
            if (packageResources == null) {
                return 0;
            }
            Object obj = bundle.get(WallpaperThemeConstants.THEMING_META);
            int i5 = 2;
            try {
                if (obj instanceof String) {
                    String[] strArrSplit = ((String) obj).split(",\\s*");
                    int length = strArrSplit.length;
                    int i6 = 0;
                    while (i6 < length) {
                        String str2 = strArrSplit[i6];
                        int identifier = packageResources.getIdentifier(str2, "xml", str);
                        if (identifier <= 0) {
                            i2 = i4;
                            i3 = i5;
                            Log.e("SWT_MetaDataManager", "metadata file not found in res/xml : " + str2);
                        } else {
                            MetaData metaData = new MetaData(packageResources.getXml(identifier));
                            Package currentPackage = metaData.getCurrentPackage();
                            if (currentPackage != null) {
                                i2 = i4;
                                if (isAbnormalMetadataDetected(currentPackage.getUidList())) {
                                    removePackageList(currentPackage.getPackageName());
                                    return i5;
                                }
                            } else {
                                i2 = i4;
                            }
                            String rpUID = metaData.getRpUID();
                            removeUidMap(rpUID);
                            if (currentPackage == null || currentPackage.getUidList() == null) {
                                i3 = i5;
                                ThemeUtil.saveSWTLog("SWT_MetaDataManager", "It doesn't include any UID in res/xml : " + str2 + " by " + str);
                            } else {
                                for (Uid uid : currentPackage.getUidList()) {
                                    this.mUidMap.put(uid.getUidValue(), uid);
                                    i5 = i5;
                                }
                                i3 = i5;
                                ThemeUtil.saveSWTLog("SWT_MetaDataManager", "metadata rpUID [" + rpUID + "] replaced by " + str);
                            }
                        }
                        i6++;
                        i4 = i2;
                        i5 = i3;
                    }
                    return 1;
                }
                if (!(obj instanceof Integer) || (iIntValue = ((Integer) obj).intValue()) <= 0) {
                    return 1;
                }
                MetaData metaData2 = new MetaData(packageResources.getXml(iIntValue));
                Package currentPackage2 = metaData2.getCurrentPackage();
                if (currentPackage2 != null && isAbnormalMetadataDetected(currentPackage2.getUidList())) {
                    removePackageList(currentPackage2.getPackageName());
                    return 2;
                }
                String rpUID2 = metaData2.getRpUID();
                removeUidMap(rpUID2);
                if (currentPackage2 != null && currentPackage2.getUidList() != null) {
                    for (Uid uid2 : currentPackage2.getUidList()) {
                        this.mUidMap.put(uid2.getUidValue(), uid2);
                    }
                    ThemeUtil.saveSWTLog("SWT_MetaDataManager", "metadata rpUID [" + rpUID2 + "] replaced by " + str);
                    return 1;
                }
                ThemeUtil.saveSWTLog("SWT_MetaDataManager", "It doesn't include any UID in res/xml : " + str);
                return 1;
            } catch (Exception e) {
                e = e;
                ThemeUtil.saveSWTLog("SWT_MetaDataManager", "Package : " + applicationInfo.packageName + " metadata update error = " + e);
                return i;
            }
        } catch (Exception e2) {
            e = e2;
            i = i4;
        }
    }

    private boolean isAbnormalMetadataDetected(List<Uid> list) {
        HashMap<String, Uid> map = this.mUidMap;
        for (Uid uid : list) {
            map.put(uid.getUidValue(), uid);
        }
        Iterator<Uid> it = list.iterator();
        while (it.hasNext()) {
            if (isRefUidCausesLoop(map, it.next().getUidValue())) {
                return true;
            }
        }
        return false;
    }

    private boolean isRefUidCausesLoop(HashMap<String, Uid> map, String str) {
        if (str == null) {
            return false;
        }
        String virtualRefUid = getVirtualRefUid(map, str);
        for (int i = 0; i < 20 && virtualRefUid != null; i++) {
            if (str.equals(virtualRefUid)) {
                return true;
            }
            virtualRefUid = getVirtualRefUid(map, virtualRefUid);
        }
        return false;
    }

    public String getVirtualRefUid(HashMap<String, Uid> map, String str) {
        String reference;
        Uid uid = map.get(str);
        if (uid == null || (reference = uid.getReference()) == null || reference.isEmpty()) {
            return null;
        }
        return reference;
    }

    public ArrayList<Package> getPackageList() {
        return this.mPackageList;
    }

    public void writeLastPackageList() throws IOException {
        File file = new File(WallpaperThemeConstants.RESID_TABLE_PATH);
        if (!file.exists()) {
            try {
                file.mkdir();
                FileUtils.setPermissions(file, 511, -1, -1);
            } catch (Exception e) {
                Log.w("SWT_MetaDataManager", "Failed to create wallpapertheme/ directory", e);
                return;
            }
        }
        File file2 = new File(file, WallpaperThemeConstants.LAST_PACKAGE_LIST_FILE);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                try {
                    Iterator<Package> it = this.mPackageList.iterator();
                    while (it.hasNext()) {
                        outputStreamWriter.write(it.next().getPackageName() + System.lineSeparator());
                    }
                    file2.setReadable(true, false);
                    outputStreamWriter.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            Log.w("SWT_MetaDataManager", "Failed to write or set permissions for package list", e2);
        }
    }

    public String getRefUid(String str) {
        Uid uid;
        String reference;
        HashMap<String, Uid> map = this.mUidMap;
        if (map == null || (uid = map.get(str)) == null || (reference = uid.getReference()) == null || reference.isEmpty()) {
            return null;
        }
        return reference;
    }

    private void removePackageList(String str) {
        if (str == null) {
            Log.e("SWT_MetaDataManager", "null packageName");
            return;
        }
        Iterator<Package> it = this.mPackageList.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getPackageName())) {
                it.remove();
                return;
            }
        }
    }

    private void removeUidMap(String str) {
        if (str == null) {
            return;
        }
        HashMap map = new HashMap();
        for (Map.Entry<String, Uid> entry : this.mUidMap.entrySet()) {
            if (entry.getKey() != null) {
                if (entry.getKey().startsWith(str + NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            this.mUidMap.remove(((Map.Entry) it.next()).getKey());
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("- METADATA -");
        Iterator<Package> it = this.mPackageList.iterator();
        while (it.hasNext()) {
            Package next = it.next();
            printWriter.println(" [PKG : " + next.getPackageName() + NavigationBarInflaterView.SIZE_MOD_END);
            for (Uid uid : next.mUidList) {
                printWriter.println("  -UID:" + uid.mUidValue + ", REF:" + uid.mValueRef + ", OPA:" + uid.mOpacity + ", TYP:" + uid.mType);
                StringBuilder sb = new StringBuilder("    res : ");
                sb.append(uid.mDestAttribName);
                printWriter.println(sb.toString());
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

        public MetaData(XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
            if (xmlResourceParser == null) {
                Log.w(TAG, "creating metadata is failed - xmlParser is null");
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

        private void parseStartTag(XmlPullParser xmlPullParser) {
            if (xmlPullParser.getName().equals(TAG_APP_METADATA)) {
                String attributeValue = xmlPullParser.getAttributeValue(null, ATTR_NAME);
                String attributeValue2 = xmlPullParser.getAttributeValue(null, ATTR_TARGET_PKG_NAME);
                this.mRpUID = null;
                if (WallpaperThemeConstants.METADATA_NAME_MULTIWINDOW.equals(attributeValue)) {
                    this.mCurrentPackage = getPackage(attributeValue);
                    return;
                } else {
                    this.mCurrentPackage = getPackage(attributeValue2);
                    return;
                }
            }
            if (this.mCurrentPackage != null && xmlPullParser.getName().equals(TAG_PROPERTY)) {
                addUID(xmlPullParser);
            } else {
                if (this.mCurrentPackage == null || !xmlPullParser.getName().equals(TAG_INCLUDE)) {
                    return;
                }
                addSeslMetaData();
            }
        }

        private Package getPackage(String str) {
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

        private void addSeslMetaData() {
            this.mCurrentPackage.getUidList().addAll(((Package) MetaDataManager.this.mPackageList.getFirst()).getUidList());
        }

        private void addUID(XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, ATTR_UID);
            String attributeValue2 = xmlPullParser.getAttributeValue(null, ATTR_VALUE_TYPE);
            String attributeValue3 = xmlPullParser.getAttributeValue(null, ATTR_DEST_ATTR_NAME);
            String attributeValue4 = xmlPullParser.getAttributeValue(null, ATTR_DEFAULT_VALUE);
            String attributeValue5 = xmlPullParser.getAttributeValue(null, ATTR_VALUE_REF);
            String attributeValue6 = xmlPullParser.getAttributeValue(null, ATTR_OPACITY);
            if (attributeValue == null) {
                ThemeUtil.saveSWTLog(TAG, "Parsing xml error, uid is empty. destAttributeName : " + attributeValue3);
                return;
            }
            this.mCurrentPackage.addUid(new Uid(attributeValue, attributeValue2, attributeValue3, attributeValue4, attributeValue5, attributeValue6));
            String packageName = this.mCurrentPackage.getPackageName();
            if (this.mRpUID == null) {
                this.mRpUID = attributeValue.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)[0];
                if (MetaDataManager.this.mRpUidMap.containsKey(this.mRpUID) && !Objects.equals(MetaDataManager.this.mRpUidMap.get(this.mRpUID), packageName)) {
                    ThemeUtil.saveSWTLog(TAG, "Abnormal metadata replacement attempts detected, RpUid : " + this.mRpUID + ", existed package : " + ((String) MetaDataManager.this.mRpUidMap.get(this.mRpUID)) + ", requested package : " + packageName);
                    return;
                }
                MetaDataManager.this.mRpUidMap.put(this.mRpUID, packageName);
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

        public Package(String str) {
            this.mPackageName = str;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public List<Uid> getUidList() {
            return this.mUidList;
        }

        public void addUid(Uid uid) {
            this.mUidList.add(uid);
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

        public Uid(String str, String str2, String str3, String str4, String str5, String str6) {
            this.mUidValue = str;
            this.mDestAttribName = str3;
            this.mValueRef = str5;
            this.mOpacity = (str6 == null || str6.isEmpty()) ? null : str6;
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

        public String getDefaultValue() {
            return this.mDefaultValue;
        }
    }
}
