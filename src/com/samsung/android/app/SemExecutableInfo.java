package com.samsung.android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
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
import com.android.internal.R;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.util.SemLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private void setId(String str) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(XML_ELEMENT_EXECUTABLE).authority(str);
        String valueOf = String.valueOf((getAction() + getPackageName() + getComponentName() + getLaunchType() + getBundleString()).hashCode() & 4294967295L);
        try {
            SemLog.d(LOG_TAG, "Use defined mUid: " + Long.parseLong(this.mUid));
            valueOf = this.mUid;
        } catch (Exception unused) {
            SemLog.d(LOG_TAG, "Not set mUid: " + this.mUid);
        }
        builder.appendPath(valueOf);
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getActivityLaunchMode() {
        /*
            r4 = this;
            java.lang.String r0 = r4.mActivityLaunchMode
            r1 = 0
            if (r0 == 0) goto L42
            int r0 = r0.length()
            if (r0 != 0) goto Lc
            goto L42
        Lc:
            java.lang.String r4 = r4.mActivityLaunchMode
            java.lang.String r0 = "\\|"
            java.lang.String[] r4 = r4.split(r0)
            r0 = r1
        L15:
            int r2 = r4.length
            if (r1 >= r2) goto L41
            r2 = r4[r1]
            java.lang.String r3 = "newTask"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L27
            r3 = 268435456(0x10000000, float:2.524355E-29)
        L25:
            r0 = r0 | r3
            goto L33
        L27:
            java.lang.String r3 = "singleTop"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L33
            r3 = 536870912(0x20000000, float:1.0842022E-19)
            goto L25
        L33:
            java.lang.String r3 = "clearTop"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L3e
            r2 = 67108864(0x4000000, float:1.5046328E-36)
            r0 = r0 | r2
        L3e:
            int r1 = r1 + 1
            goto L15
        L41:
            return r0
        L42:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemExecutableInfo.getActivityLaunchMode():int");
    }

    private static SemExecutableInfo getActivityMetaData(Context context, AttributeSet attributeSet, ComponentName componentName) {
        SemExecutableInfo semExecutableInfo = new SemExecutableInfo();
        Context createActivityContext = createActivityContext(context, componentName);
        if (createActivityContext == null) {
            return null;
        }
        TypedArray obtainStyledAttributes = createActivityContext.obtainStyledAttributes(attributeSet, R.styleable.command);
        semExecutableInfo.mUid = obtainStyledAttributes.getString(3);
        semExecutableInfo.mEnabled = obtainStyledAttributes.getBoolean(2, true);
        semExecutableInfo.mLabelId = obtainStyledAttributes.getResourceId(0, 0);
        semExecutableInfo.mIconId = obtainStyledAttributes.getResourceId(1, 0);
        semExecutableInfo.mSmallIconId = obtainStyledAttributes.getResourceId(4, 0);
        obtainStyledAttributes.recycle();
        return semExecutableInfo;
    }

    private void addExtraAttribute(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.extrasCommand);
        String string = obtainStyledAttributes.getString(0);
        String string2 = obtainStyledAttributes.getString(2);
        String string3 = obtainStyledAttributes.getString(1);
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
        obtainStyledAttributes.recycle();
    }

    private static void examineOrderInCategory(SemExecutableInfo semExecutableInfo, boolean z) {
        StringBuilder sb = new StringBuilder();
        int i = !z ? ORDER_NOT_ALLOWED : ORDER_INIT_VALUE;
        if (semExecutableInfo.getCategories().isEmpty()) {
            return;
        }
        for (String str : semExecutableInfo.mCategory.split("\\|")) {
            String[] split = str.split("@");
            int length = split.length;
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
                        i = Integer.parseInt(split[0]);
                        if (i < -1000 || i > 1000) {
                            i = -9999;
                        }
                    } catch (NumberFormatException e) {
                        try {
                            if (DEBUG) {
                                SemLog.d(LOG_TAG, "Invalid order");
                                e.printStackTrace();
                            }
                            sb.append(split[1]);
                            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        } catch (Throwable th) {
                            th = th;
                            i = -9998;
                            if (i != -9999 || i == ORDER_NOT_ALLOWED || i == -9998) {
                                sb.append(split[1]);
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
                        }
                        sb.append(split[1]);
                        sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        throw th;
                    }
                }
                if (i == -9999 || i == ORDER_NOT_ALLOWED || i == -9998) {
                    sb.append(split[1]);
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                } else {
                    sb.append(str);
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                }
                i2 = i;
                i = i2;
            }
        }
        String sb2 = sb.toString();
        semExecutableInfo.mCategory = sb2.substring(0, sb2.length() - 1);
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

    /* JADX WARN: Removed duplicated region for block: B:91:0x01d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<com.samsung.android.app.SemExecutableInfo> scanExecutableInfos(android.content.Context r19) {
        /*
            Method dump skipped, instructions count: 535
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemExecutableInfo.scanExecutableInfos(android.content.Context):java.util.List");
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
