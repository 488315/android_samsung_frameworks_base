package com.samsung.android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.NtpTrustedTime;
import android.util.Xml;
import com.android.internal.R;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.util.SemLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public final class SemExecutableInfo implements Parcelable {
    private static final String CLASSNAME_PREFIX_FOR_SEC_PRODUCT_FEATURE = "SecProductFeature_";
    private static final String CSC_FEATURE_PREFIX = "CscFeature_";
    public static final int LAUNCH_TYPE_ACTIVITY = 0;
    public static final int LAUNCH_TYPE_ACTIVITY_FOR_RESULT = 3;
    public static final int LAUNCH_TYPE_BROADCAST = 2;
    public static final int LAUNCH_TYPE_SERVICE = 1;
    private static final String LOG_TAG = "SemExecutableInfo";
    private static final String MD_LABEL_EXECUTABLE = "com.samsung.android.support.executable";
    private static final int ORDER_INIT_VALUE = -9996;
    private static final int ORDER_INVALID_FORMAT = -9998;
    private static final int ORDER_NOT_ALLOWED = -9997;
    private static final int ORDER_OUT_OF_RANGE = -9999;
    private static final String PACKAGE_PREFIX_FOR_SEC_PRODUCT_FEATURE = "com.sec.android.app.";
    private static final String SEC_FLOATING_FEATURE_PREFIX = "SEC_FLOATING_FEATURE_";
    private static final String SEC_PRODUCT_FEATURE_PREFIX = "SEC_PRODUCT_FEATURE_";
    private static final String XML_ELEMENT_COMMAND = "command";
    private static final String XML_ELEMENT_ENABLED = "enabled";
    private static final String XML_ELEMENT_EXECUTABLE = "executable";
    private static final String XML_ELEMENT_EXTRA_ATTR = "extras-attr";
    private static final String XML_ELEMENT_EXTRA_ATTR_CATEGORY = "category";
    private static final String XML_ELEMENT_EXTRA_ATTR_COMPONENTNAME = "componentName";
    private static final String XML_ELEMENT_EXTRA_ATTR_EXTRAS = "extras";
    private static final String XML_ELEMENT_EXTRA_ATTR_FEATURE = "feature";
    private static final String XML_ELEMENT_EXTRA_ATTR_INTETNACTION = "action";
    private static final String XML_ELEMENT_EXTRA_ATTR_LAUCHMODE = "launchMode";
    private static final String XML_ELEMENT_EXTRA_ATTR_PACKAGENAME = "packageName";
    private static final String XML_ELEMENT_EXTRA_ATTR_TYPE = "type";
    private static final String XML_ELEMENT_EXTRA_ATTR_TYPE_ACTIVITY = "activity";
    private static final String XML_ELEMENT_EXTRA_ATTR_TYPE_ACTIVITY_FOR_RESULT = "activityForResult";
    private static final String XML_ELEMENT_EXTRA_ATTR_TYPE_BROADCAST = "broadcast";
    private static final String XML_ELEMENT_EXTRA_ATTR_TYPE_SERVICE = "service";
    private static final String XML_ELEMENT_ICON = "icon";
    private static final String XML_ELEMENT_LABEL = "label";
    private static final String XML_ELEMENT_LAUCHMODE_CLEARTOP = "clearTop";
    private static final String XML_ELEMENT_LAUCHMODE_NEWTASK = "newTask";
    private static final String XML_ELEMENT_LAUCHMODE_SINGLETOP = "singleTop";
    private static final String XML_ELEMENT_SMALL_ICON = "smallIcon";
    String mAction;
    String mActivityLaunchMode;
    Bundle mBundle;
    String mCategory;
    String mComponentName;
    boolean mEnabled;
    List<String> mFeatureNames;
    List<String> mFeatureValues;
    int mIconId;
    int mLabelId;
    int mLaunchType;
    String mPackageName;
    int mSmallIconId;
    String mUid;
    private static final boolean DEBUG = Debug.semIsProductDev();
    public static final Parcelable.Creator<SemExecutableInfo> CREATOR = new Parcelable.Creator<SemExecutableInfo>() { // from class: com.samsung.android.app.SemExecutableInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExecutableInfo createFromParcel(Parcel parcel) {
            return new SemExecutableInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExecutableInfo[] newArray(int i) {
            return new SemExecutableInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemExecutableInfo() {
        this.mUid = null;
        this.mEnabled = false;
        this.mBundle = new Bundle();
        this.mFeatureNames = new ArrayList();
        this.mFeatureValues = new ArrayList();
    }

    SemExecutableInfo(Parcel parcel) {
        this();
        this.mUid = parcel.readString();
        this.mEnabled = parcel.readInt() != 0;
        this.mLabelId = parcel.readInt();
        this.mIconId = parcel.readInt();
        this.mSmallIconId = parcel.readInt();
        this.mLaunchType = parcel.readInt();
        this.mCategory = parcel.readString();
        this.mAction = parcel.readString();
        this.mPackageName = parcel.readString();
        parcel.readStringList(this.mFeatureNames);
        parcel.readStringList(this.mFeatureValues);
        this.mBundle = parcel.readBundle();
        this.mComponentName = parcel.readString();
        this.mActivityLaunchMode = parcel.readString();
    }

    private void setId(String str) throws NumberFormatException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(XML_ELEMENT_EXECUTABLE).authority(str);
        String strValueOf = String.valueOf((getAction() + getPackageName() + getComponentName() + getLaunchType() + getBundleString()).hashCode() & 4294967295L);
        try {
            SemLog.d(LOG_TAG, "Use defined mUid: " + Long.parseLong(this.mUid));
            strValueOf = this.mUid;
        } catch (Exception unused) {
            SemLog.d(LOG_TAG, "Not set mUid: " + this.mUid);
        }
        builder.appendPath(strValueOf);
        this.mUid = builder.toString();
    }

    public String getId() {
        return this.mUid;
    }

    public List<String> getCategories() {
        String str = this.mCategory;
        if (str == null || TextUtils.isEmpty(str)) {
            return new ArrayList();
        }
        return Arrays.asList(this.mCategory.split("\\|"));
    }

    public String getAction() {
        return this.mAction;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public int getIconId() {
        return this.mIconId;
    }

    public int getSmallIconId() {
        return this.mSmallIconId;
    }

    public int getLabelId() {
        return this.mLabelId;
    }

    public int getLaunchType() {
        return this.mLaunchType;
    }

    public Bundle getExtras() {
        return this.mBundle;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getComponentName() {
        return this.mComponentName;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getActivityLaunchMode() {
        int i;
        String str = this.mActivityLaunchMode;
        if (str == null || str.length() == 0) {
            return 0;
        }
        int i2 = 0;
        for (String str2 : this.mActivityLaunchMode.split("\\|")) {
            if (!XML_ELEMENT_LAUCHMODE_NEWTASK.equals(str2)) {
                i = XML_ELEMENT_LAUCHMODE_SINGLETOP.equals(str2) ? 536870912 : 268435456;
                if (!XML_ELEMENT_LAUCHMODE_CLEARTOP.equals(str2)) {
                    i2 |= 67108864;
                }
            }
            i2 |= i;
            if (!XML_ELEMENT_LAUCHMODE_CLEARTOP.equals(str2)) {
            }
        }
        return i2;
    }

    private static SemExecutableInfo getActivityMetaData(Context context, AttributeSet attributeSet, ComponentName componentName) {
        SemExecutableInfo semExecutableInfo = new SemExecutableInfo();
        Context contextCreateActivityContext = createActivityContext(context, componentName);
        if (contextCreateActivityContext == null) {
            return null;
        }
        TypedArray typedArrayObtainStyledAttributes = contextCreateActivityContext.obtainStyledAttributes(attributeSet, R.styleable.command);
        semExecutableInfo.mUid = typedArrayObtainStyledAttributes.getString(3);
        semExecutableInfo.mEnabled = typedArrayObtainStyledAttributes.getBoolean(2, true);
        semExecutableInfo.mLabelId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        semExecutableInfo.mIconId = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        semExecutableInfo.mSmallIconId = typedArrayObtainStyledAttributes.getResourceId(4, 0);
        typedArrayObtainStyledAttributes.recycle();
        return semExecutableInfo;
    }

    private void addExtraAttribute(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.extrasCommand);
        String string = typedArrayObtainStyledAttributes.getString(0);
        String string2 = typedArrayObtainStyledAttributes.getString(2);
        String string3 = typedArrayObtainStyledAttributes.getString(1);
        if (XML_ELEMENT_EXTRA_ATTR_LAUCHMODE.equals(string)) {
            this.mActivityLaunchMode = string3;
        } else if ("type".equals(string)) {
            if ("activity".equals(string3)) {
                this.mLaunchType = 0;
            } else if ("service".equals(string3)) {
                this.mLaunchType = 1;
            } else if ("broadcast".equals(string3)) {
                this.mLaunchType = 2;
            } else if (XML_ELEMENT_EXTRA_ATTR_TYPE_ACTIVITY_FOR_RESULT.equals(string3)) {
                this.mLaunchType = 3;
            } else {
                this.mLaunchType = 0;
            }
        } else if (XML_ELEMENT_EXTRA_ATTR_CATEGORY.equals(string)) {
            this.mCategory = string3;
        } else if ("action".equals(string)) {
            this.mAction = string3;
        } else if ("packageName".equals(string)) {
            this.mPackageName = string3;
        } else if (XML_ELEMENT_EXTRA_ATTR_COMPONENTNAME.equals(string)) {
            this.mComponentName = string3;
        } else if ("feature".equals(string)) {
            this.mFeatureNames.add(string2);
            this.mFeatureValues.add(string3);
        } else if ("extras".equals(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
            this.mBundle.putString(string2, string3);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void examineOrderInCategory(SemExecutableInfo semExecutableInfo, boolean z) throws Throwable {
        StringBuilder sb = new StringBuilder();
        int i = !z ? ORDER_NOT_ALLOWED : ORDER_INIT_VALUE;
        if (semExecutableInfo.getCategories().isEmpty()) {
            return;
        }
        for (String str : semExecutableInfo.mCategory.split("\\|")) {
            String[] strArrSplit = str.split("@");
            int length = strArrSplit.length;
            if (length == 1) {
                sb.append(str);
                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            } else if (length != 2) {
                sb.append(str);
                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                if (DEBUG) {
                    SemLog.d(LOG_TAG, "Invalid category format for category order");
                }
            } else {
                int i2 = -9998;
                if (i != ORDER_NOT_ALLOWED) {
                    try {
                        i = Integer.parseInt(strArrSplit[0]);
                        if (i < -1000 || i > 1000) {
                            i = -9999;
                        }
                        if (i != -9999 || i == ORDER_NOT_ALLOWED || i == -9998) {
                            sb.append(strArrSplit[1]);
                            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        } else {
                            sb.append(str);
                            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        }
                        i2 = i;
                    } catch (NumberFormatException e) {
                        try {
                            if (DEBUG) {
                                SemLog.d(LOG_TAG, "Invalid order");
                                e.printStackTrace();
                            }
                            sb.append(strArrSplit[1]);
                            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        } catch (Throwable th) {
                            th = th;
                            i = -9998;
                            if (i != -9999 || i == ORDER_NOT_ALLOWED || i == -9998) {
                                sb.append(strArrSplit[1]);
                                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                            } else {
                                sb.append(str);
                                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (i != -9999) {
                            sb.append(strArrSplit[1]);
                            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        }
                        throw th;
                    }
                    i = i2;
                } else if (i != -9999) {
                    sb.append(strArrSplit[1]);
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                    i2 = i;
                    i = i2;
                }
            }
        }
        String string = sb.toString();
        semExecutableInfo.mCategory = string.substring(0, string.length() - 1);
    }

    private static Context createActivityContext(Context context, ComponentName componentName) {
        try {
            return context.createPackageContext(componentName.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.e(LOG_TAG, "Package not found " + componentName.getPackageName());
            return null;
        } catch (SecurityException e) {
            SemLog.e(LOG_TAG, "Can't make context for " + componentName.getPackageName(), e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0171 A[PHI: r16
      0x0171: PHI (r16v2 boolean) = (r16v1 boolean), (r16v3 boolean) binds: [B:53:0x011b, B:58:0x0129] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<SemExecutableInfo> scanExecutableInfos(Context context) throws Throwable {
        boolean z;
        boolean z2;
        PackageItemInfo packageItemInfo;
        ApplicationInfo applicationInfo;
        boolean z3;
        XmlResourceParser xmlResourceParserLoadXmlMetaData;
        if (DEBUG) {
            SemLog.d(LOG_TAG, "scan scanExecutableInfos start");
        }
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        int i = 3;
        boolean z4 = true;
        int i2 = 2;
        List[] listArr = {packageManager.queryIntentActivities(new Intent(MD_LABEL_EXECUTABLE), 640), packageManager.queryIntentServices(new Intent(MD_LABEL_EXECUTABLE), 640), packageManager.queryBroadcastReceivers(new Intent(MD_LABEL_EXECUTABLE), 640)};
        int i3 = 0;
        while (i3 < i) {
            List<ResolveInfo> list = listArr[i3];
            if (DEBUG) {
                SemLog.d(LOG_TAG, "list size = " + list.size());
            }
            for (ResolveInfo resolveInfo : list) {
                if (resolveInfo.activityInfo != null) {
                    packageItemInfo = resolveInfo.activityInfo;
                    applicationInfo = resolveInfo.activityInfo.applicationInfo;
                    z2 = resolveInfo.activityInfo.applicationInfo.enabled ^ z4;
                    z3 = resolveInfo.activityInfo.enabled;
                } else if (resolveInfo.serviceInfo != null) {
                    packageItemInfo = resolveInfo.serviceInfo;
                    applicationInfo = resolveInfo.serviceInfo.applicationInfo;
                    z2 = resolveInfo.serviceInfo.applicationInfo.enabled ^ z4;
                    z3 = resolveInfo.serviceInfo.enabled;
                } else {
                    z = z4;
                    z2 = z;
                    packageItemInfo = null;
                    applicationInfo = null;
                    if (!z2 || z) {
                        if (DEBUG) {
                            SemLog.d(LOG_TAG, "skip disable component: " + z2 + ", " + z);
                        }
                    } else {
                        ComponentName componentName = new ComponentName(packageItemInfo.packageName, packageItemInfo.name);
                        try {
                            xmlResourceParserLoadXmlMetaData = applicationInfo.loadXmlMetaData(context.getPackageManager(), MD_LABEL_EXECUTABLE);
                        } catch (IOException e) {
                            SemLog.w(LOG_TAG, "Reading SemExecutableInfo metadata for " + componentName.flattenToShortString(), e);
                        } catch (IllegalArgumentException e2) {
                            SemLog.w(LOG_TAG, "Invalid attribute in metadata for " + componentName.flattenToShortString() + ": " + e2.getMessage());
                        } catch (XmlPullParserException e3) {
                            SemLog.w(LOG_TAG, "Reading SemExecutableInfo metadata for " + componentName.flattenToShortString(), e3);
                        } catch (Exception e4) {
                            SemLog.w(LOG_TAG, "Unknown Exception while Reading SemExecutableInfo metadata", e4);
                        }
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            int next = xmlResourceParserLoadXmlMetaData.next();
                            boolean z5 = false;
                            SemExecutableInfo activityMetaData = null;
                            boolean z6 = false;
                            while (next != z4) {
                                String name = xmlResourceParserLoadXmlMetaData.getName();
                                if (next == i2) {
                                    if (XML_ELEMENT_EXECUTABLE.equals(name)) {
                                        z5 = true;
                                    }
                                    if ("command".equals(name)) {
                                        if (!z5) {
                                            throw new XmlPullParserException("executable element wasn't started");
                                        }
                                        activityMetaData = getActivityMetaData(context, Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), componentName);
                                        z6 = true;
                                    }
                                    SemExecutableInfo semExecutableInfo = activityMetaData;
                                    if (XML_ELEMENT_EXTRA_ATTR.equals(name)) {
                                        if (!z5 || !z6) {
                                            throw new XmlPullParserException("executable or command element wasn't started");
                                        }
                                        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData);
                                        if (semExecutableInfo != null) {
                                            semExecutableInfo.addExtraAttribute(context, attributeSetAsAttributeSet);
                                        }
                                    }
                                    activityMetaData = semExecutableInfo;
                                } else if (next == 3) {
                                    if (XML_ELEMENT_EXECUTABLE.equals(name)) {
                                        z5 = false;
                                    }
                                    if ("command".equals(name)) {
                                        if (checkValidate(activityMetaData)) {
                                            SemExecutableInfo semExecutableInfo2 = activityMetaData;
                                            examineOrderInCategory(semExecutableInfo2, SemExecutableWhitelist.getInstance().isAllowedToUseOrder(context, applicationInfo.packageName));
                                            semExecutableInfo2.setId(applicationInfo.packageName);
                                            Iterator it = arrayList.iterator();
                                            boolean z7 = false;
                                            while (it.hasNext()) {
                                                if (TextUtils.equals(((SemExecutableInfo) it.next()).getId(), semExecutableInfo2.getId())) {
                                                    z7 = true;
                                                }
                                            }
                                            if (!z7) {
                                                arrayList.add(semExecutableInfo2);
                                            }
                                        }
                                        activityMetaData = null;
                                        z6 = false;
                                    }
                                }
                                next = xmlResourceParserLoadXmlMetaData.next();
                                z4 = true;
                                i2 = 2;
                            }
                        }
                    }
                    z4 = true;
                    i2 = 2;
                }
                z = z3 ^ z4;
                if (!z2) {
                    if (DEBUG) {
                    }
                    z4 = true;
                    i2 = 2;
                }
            }
            i3++;
            z4 = true;
            i = 3;
            i2 = 2;
        }
        if (DEBUG) {
            SemLog.d(LOG_TAG, "scan SemExecutableInfo end: " + arrayList.size());
        }
        return arrayList;
    }

    private static boolean checkValidate(SemExecutableInfo semExecutableInfo) {
        if (semExecutableInfo == null) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "Invalid SemExecutableInfo");
            }
            return false;
        }
        if (!semExecutableInfo.mEnabled) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "disabled SemExecutableInfo " + semExecutableInfo.toString());
            }
            return false;
        }
        if (semExecutableInfo.getLaunchType() != 2 && (semExecutableInfo.getPackageName() == null || semExecutableInfo.getComponentName() == null)) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "Invalid packageName or componentName = " + semExecutableInfo.toString());
            }
            return false;
        }
        if (semExecutableInfo.getLabelId() == 0 || semExecutableInfo.getIconId() == 0) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "Invalid label or icon = " + semExecutableInfo.toString());
            }
            return false;
        }
        for (int i = 0; i < semExecutableInfo.mFeatureNames.size(); i++) {
            String str = semExecutableInfo.mFeatureNames.get(i);
            String str2 = semExecutableInfo.mFeatureValues.get(i);
            if (str != null && !str.isEmpty() && str2 != null && !str2.isEmpty()) {
                if (str.startsWith(CSC_FEATURE_PREFIX)) {
                    String string = SemCscFeature.getInstance().getString(str);
                    if (str2.startsWith("!")) {
                        if (string.equalsIgnoreCase(str2.substring(1))) {
                            return false;
                        }
                    } else if (!string.equalsIgnoreCase(str2)) {
                        if (DEBUG) {
                            SemLog.d(LOG_TAG, str + " is not [" + str2 + "] " + semExecutableInfo.toString());
                        }
                        return false;
                    }
                } else if (str.startsWith(SEC_FLOATING_FEATURE_PREFIX)) {
                    if (str2.startsWith("!")) {
                        if ("".equalsIgnoreCase(str2.substring(1))) {
                            return false;
                        }
                    } else if (!"".equalsIgnoreCase(str2)) {
                        if (DEBUG) {
                            SemLog.d(LOG_TAG, str + " is not [" + str2 + "] " + semExecutableInfo.toString());
                        }
                        return false;
                    }
                } else {
                    if (str.startsWith(SEC_PRODUCT_FEATURE_PREFIX)) {
                        return false;
                    }
                    String str3 = SystemProperties.get(str);
                    if (str2.startsWith("!")) {
                        if (str3.equalsIgnoreCase(str2.substring(1))) {
                            return false;
                        }
                    } else if (!str3.equalsIgnoreCase(str2)) {
                        if (DEBUG) {
                            SemLog.d(LOG_TAG, str + " is not [" + str2 + "] " + semExecutableInfo.toString());
                        }
                        return false;
                    }
                }
            } else {
                if (str != null && !str.isEmpty()) {
                    if (DEBUG) {
                        SemLog.d(LOG_TAG, "No value for " + str + " " + semExecutableInfo.toString());
                    }
                    return false;
                }
                if (str2 != null && !str2.isEmpty()) {
                    if (DEBUG) {
                        SemLog.d(LOG_TAG, "No feature name is provided for the value " + str2 + " " + semExecutableInfo.toString());
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private String getBundleString() {
        if (this.mBundle.isEmpty()) {
            return "";
        }
        ArrayList<String> arrayList = new ArrayList(this.mBundle.keySet());
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (String str : arrayList) {
            sb.append("{");
            sb.append(str);
            sb.append("=");
            sb.append(this.mBundle.get(str));
            sb.append("}");
        }
        return sb.toString();
    }

    public String toString() {
        String str = "SemExecutableInfo{enabled=" + this.mEnabled + ", id=" + this.mUid + ", labelId=" + this.mLabelId + ", iconIId=" + this.mIconId + ", smallIconIId=" + this.mSmallIconId + ", type=" + this.mLaunchType + ", category=" + this.mCategory + ", action='" + this.mAction + "', packageName='" + this.mPackageName + "', componentName='" + this.mComponentName + "', launchMode='" + this.mActivityLaunchMode + DateFormat.QUOTE;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < this.mFeatureNames.size(); i++) {
            sb.append(", featureName ='");
            sb.append(this.mFeatureNames.get(i));
            sb.append("', featureValue = '");
            sb.append(this.mFeatureValues.get(i));
            sb.append(DateFormat.QUOTE);
        }
        sb.append(", mBundle ='");
        sb.append(getBundleString());
        sb.append("'}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SemExecutableInfo)) {
            return false;
        }
        SemExecutableInfo semExecutableInfo = (SemExecutableInfo) obj;
        if (this.mEnabled != semExecutableInfo.mEnabled || this.mLabelId != semExecutableInfo.mLabelId || this.mIconId != semExecutableInfo.mIconId || this.mSmallIconId != semExecutableInfo.mSmallIconId || this.mLaunchType != semExecutableInfo.mLaunchType) {
            return false;
        }
        String str = this.mUid;
        if (str == null ? semExecutableInfo.mUid != null : !str.equals(semExecutableInfo.mUid)) {
            return false;
        }
        String str2 = this.mCategory;
        if (str2 == null ? semExecutableInfo.mCategory != null : !str2.equals(semExecutableInfo.mCategory)) {
            return false;
        }
        String str3 = this.mAction;
        if (str3 == null ? semExecutableInfo.mAction != null : !str3.equals(semExecutableInfo.mAction)) {
            return false;
        }
        String str4 = this.mPackageName;
        if (str4 == null ? semExecutableInfo.mPackageName != null : !str4.equals(semExecutableInfo.mPackageName)) {
            return false;
        }
        List<String> list = this.mFeatureNames;
        if (list == null ? semExecutableInfo.mFeatureNames != null : !list.equals(semExecutableInfo.mFeatureNames)) {
            return false;
        }
        List<String> list2 = this.mFeatureValues;
        if (list2 == null ? semExecutableInfo.mFeatureValues != null : !list2.equals(semExecutableInfo.mFeatureValues)) {
            return false;
        }
        Bundle bundle = this.mBundle;
        if (bundle == null ? semExecutableInfo.mBundle != null : !bundle.equals(semExecutableInfo.mBundle)) {
            return false;
        }
        String str5 = this.mComponentName;
        if (str5 == null ? semExecutableInfo.mComponentName != null : !str5.equals(semExecutableInfo.mComponentName)) {
            return false;
        }
        String str6 = this.mActivityLaunchMode;
        return str6 == null ? semExecutableInfo.mActivityLaunchMode == null : str6.equals(semExecutableInfo.mActivityLaunchMode);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUid);
        parcel.writeInt(this.mEnabled ? 1 : 0);
        parcel.writeInt(this.mLabelId);
        parcel.writeInt(this.mIconId);
        parcel.writeInt(this.mSmallIconId);
        parcel.writeInt(this.mLaunchType);
        parcel.writeString(this.mCategory);
        parcel.writeString(this.mAction);
        parcel.writeString(this.mPackageName);
        parcel.writeStringList(this.mFeatureNames);
        parcel.writeStringList(this.mFeatureValues);
        parcel.writeBundle(this.mBundle);
        parcel.writeString(this.mComponentName);
        parcel.writeString(this.mActivityLaunchMode);
    }
}
