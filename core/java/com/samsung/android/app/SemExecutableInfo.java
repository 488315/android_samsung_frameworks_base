package com.samsung.android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Debug;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.SystemProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.NtpTrustedTime;
import com.android.internal.C4337R;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.util.SemLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
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
        public SemExecutableInfo createFromParcel(Parcel in) {
            return new SemExecutableInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExecutableInfo[] newArray(int size) {
            return new SemExecutableInfo[size];
        }
    };

    public SemExecutableInfo() {
        this.mUid = null;
        this.mEnabled = false;
        this.mBundle = new Bundle();
        this.mFeatureNames = new ArrayList();
        this.mFeatureValues = new ArrayList();
    }

    SemExecutableInfo(Parcel in) {
        this();
        this.mUid = in.readString();
        this.mEnabled = in.readInt() != 0;
        this.mLabelId = in.readInt();
        this.mIconId = in.readInt();
        this.mSmallIconId = in.readInt();
        this.mLaunchType = in.readInt();
        this.mCategory = in.readString();
        this.mAction = in.readString();
        this.mPackageName = in.readString();
        in.readStringList(this.mFeatureNames);
        in.readStringList(this.mFeatureValues);
        this.mBundle = in.readBundle();
        this.mComponentName = in.readString();
        this.mActivityLaunchMode = in.readString();
    }

    private void setId(String applicaitonPackageName) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(XML_ELEMENT_EXECUTABLE).authority(applicaitonPackageName);
        String baseId = getAction() + getPackageName() + getComponentName() + getLaunchType() + getBundleString();
        String id = String.valueOf(baseId.hashCode() & 4294967295L);
        try {
            long lastId = Long.parseLong(this.mUid);
            SemLog.m478d(LOG_TAG, "Use defined mUid: " + lastId);
            id = this.mUid;
        } catch (Exception e) {
            SemLog.m478d(LOG_TAG, "Not set mUid: " + this.mUid);
        }
        builder.appendPath(id);
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
        String[] categories = this.mCategory.split("\\|");
        return Arrays.asList(categories);
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

    public int getActivityLaunchMode() {
        int flags = 0;
        String str = this.mActivityLaunchMode;
        if (str == null || str.length() == 0) {
            return 0;
        }
        String[] modes = this.mActivityLaunchMode.split("\\|");
        for (String mode : modes) {
            if (XML_ELEMENT_LAUCHMODE_NEWTASK.equals(mode)) {
                flags |= 268435456;
            } else if (XML_ELEMENT_LAUCHMODE_SINGLETOP.equals(mode)) {
                flags |= 536870912;
            }
            if (XML_ELEMENT_LAUCHMODE_CLEARTOP.equals(mode)) {
                flags |= 67108864;
            }
        }
        return flags;
    }

    private static SemExecutableInfo getActivityMetaData(Context context, AttributeSet attr, ComponentName cName) {
        SemExecutableInfo result = new SemExecutableInfo();
        Context activityContext = createActivityContext(context, cName);
        if (activityContext == null) {
            return null;
        }
        TypedArray a = activityContext.obtainStyledAttributes(attr, C4337R.styleable.command);
        result.mUid = a.getString(3);
        result.mEnabled = a.getBoolean(2, true);
        result.mLabelId = a.getResourceId(0, 0);
        result.mIconId = a.getResourceId(1, 0);
        result.mSmallIconId = a.getResourceId(4, 0);
        a.recycle();
        return result;
    }

    private void addExtraAttribute(Context activityContext, AttributeSet attr) {
        TypedArray ta = activityContext.obtainStyledAttributes(attr, C4337R.styleable.extrasCommand);
        String name = ta.getString(0);
        String key = ta.getString(2);
        String value = ta.getString(1);
        if (XML_ELEMENT_EXTRA_ATTR_LAUCHMODE.equals(name)) {
            this.mActivityLaunchMode = value;
        } else if ("type".equals(name)) {
            if ("activity".equals(value)) {
                this.mLaunchType = 0;
            } else if ("service".equals(value)) {
                this.mLaunchType = 1;
            } else if ("broadcast".equals(value)) {
                this.mLaunchType = 2;
            } else if (XML_ELEMENT_EXTRA_ATTR_TYPE_ACTIVITY_FOR_RESULT.equals(value)) {
                this.mLaunchType = 3;
            } else {
                this.mLaunchType = 0;
            }
        } else if ("category".equals(name)) {
            this.mCategory = value;
        } else if ("action".equals(name)) {
            this.mAction = value;
        } else if ("packageName".equals(name)) {
            this.mPackageName = value;
        } else if (XML_ELEMENT_EXTRA_ATTR_COMPONENTNAME.equals(name)) {
            this.mComponentName = value;
        } else if ("feature".equals(name)) {
            this.mFeatureNames.add(key);
            this.mFeatureValues.add(value);
        } else if ("extras".equals(name) && !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            this.mBundle.putString(key, value);
        }
        ta.recycle();
    }

    private static void examineOrderInCategory(SemExecutableInfo info, boolean isSamsungApps) {
        String str;
        StringBuilder sb = new StringBuilder();
        int order = ORDER_INIT_VALUE;
        if (!isSamsungApps) {
            order = ORDER_NOT_ALLOWED;
        }
        if (info.getCategories().isEmpty()) {
            return;
        }
        String[] categories = info.mCategory.split("\\|");
        int length = categories.length;
        char c = 0;
        int order2 = order;
        int i = 0;
        while (i < length) {
            String str2 = categories[i];
            String[] strSplit = str2.split("@");
            int size = strSplit.length;
            switch (size) {
                case 1:
                    sb.append(str2);
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                    break;
                case 2:
                    if (order2 != ORDER_NOT_ALLOWED) {
                        try {
                            try {
                                order2 = Integer.parseInt(strSplit[c]);
                                if (order2 < -1000 || order2 > 1000) {
                                    order2 = -9999;
                                }
                            } catch (NumberFormatException nfe) {
                                order2 = -9998;
                                if (DEBUG) {
                                    SemLog.m478d(LOG_TAG, "Invalid order");
                                    nfe.printStackTrace();
                                }
                                if (-9998 == -9999 || -9998 == ORDER_NOT_ALLOWED || -9998 == -9998) {
                                    str = strSplit[1];
                                }
                            }
                        } catch (Throwable th) {
                            if (order2 == -9999 || order2 == ORDER_NOT_ALLOWED || order2 == -9998) {
                                sb.append(strSplit[1]);
                                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                            } else {
                                sb.append(str2);
                                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                            }
                            throw th;
                        }
                    }
                    if (order2 == -9999 || order2 == ORDER_NOT_ALLOWED || order2 == -9998) {
                        str = strSplit[1];
                        sb.append(str);
                        sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        break;
                    }
                    sb.append(str2);
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                    break;
                default:
                    sb.append(str2);
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                    if (!DEBUG) {
                        break;
                    } else {
                        SemLog.m478d(LOG_TAG, "Invalid category format for category order");
                        break;
                    }
            }
            i++;
            c = 0;
        }
        String resultStr = sb.toString();
        info.mCategory = resultStr.substring(0, resultStr.length() - 1);
    }

    private static Context createActivityContext(Context context, ComponentName componentName) {
        try {
            Context theirContext = context.createPackageContext(componentName.getPackageName(), 0);
            return theirContext;
        } catch (PackageManager.NameNotFoundException e) {
            SemLog.m480e(LOG_TAG, "Package not found " + componentName.getPackageName());
            return null;
        } catch (SecurityException e2) {
            SemLog.m481e(LOG_TAG, "Can't make context for " + componentName.getPackageName(), e2);
            return null;
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public static java.util.List<com.samsung.android.app.SemExecutableInfo> scanExecutableInfos(android.content.Context r38) {
        /*
            Method dump skipped, instructions count: 1184
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemExecutableInfo.scanExecutableInfos(android.content.Context):java.util.List");
    }

    private static boolean checkValidate(SemExecutableInfo info) {
        if (info == null) {
            if (DEBUG) {
                SemLog.m478d(LOG_TAG, "Invalid SemExecutableInfo");
            }
            return false;
        }
        if (!info.mEnabled) {
            if (DEBUG) {
                SemLog.m478d(LOG_TAG, "disabled SemExecutableInfo " + info.toString());
            }
            return false;
        }
        if (info.getLaunchType() != 2 && (info.getPackageName() == null || info.getComponentName() == null)) {
            if (DEBUG) {
                SemLog.m478d(LOG_TAG, "Invalid packageName or componentName = " + info.toString());
            }
            return false;
        }
        if (info.getLabelId() == 0 || info.getIconId() == 0) {
            if (DEBUG) {
                SemLog.m478d(LOG_TAG, "Invalid label or icon = " + info.toString());
            }
            return false;
        }
        for (int i = 0; i < info.mFeatureNames.size(); i++) {
            String featureName = info.mFeatureNames.get(i);
            String featureValue = info.mFeatureValues.get(i);
            if (featureName != null && featureName.length() > 0 && featureValue != null && featureValue.length() > 0) {
                if (featureName.startsWith(CSC_FEATURE_PREFIX)) {
                    String str = SemCscFeature.getInstance().getString(featureName);
                    if (featureValue.startsWith("!")) {
                        String value = featureValue.substring(1);
                        if (str.equalsIgnoreCase(value)) {
                            return false;
                        }
                    } else if (!str.equalsIgnoreCase(featureValue)) {
                        if (DEBUG) {
                            SemLog.m478d(LOG_TAG, featureName + " is not [" + featureValue + "] " + info.toString());
                        }
                        return false;
                    }
                } else if (featureName.startsWith(SEC_FLOATING_FEATURE_PREFIX)) {
                    if (featureValue.startsWith("!")) {
                        String value2 = featureValue.substring(1);
                        if ("".equalsIgnoreCase(value2)) {
                            return false;
                        }
                    } else if (!"".equalsIgnoreCase(featureValue)) {
                        if (DEBUG) {
                            SemLog.m478d(LOG_TAG, featureName + " is not [" + featureValue + "] " + info.toString());
                        }
                        return false;
                    }
                } else {
                    if (featureName.startsWith(SEC_PRODUCT_FEATURE_PREFIX)) {
                        return false;
                    }
                    String str2 = SystemProperties.get(featureName);
                    if (featureValue.startsWith("!")) {
                        String value3 = featureValue.substring(1);
                        if (str2.equalsIgnoreCase(value3)) {
                            return false;
                        }
                    } else if (!str2.equalsIgnoreCase(featureValue)) {
                        if (DEBUG) {
                            SemLog.m478d(LOG_TAG, featureName + " is not [" + featureValue + "] " + info.toString());
                        }
                        return false;
                    }
                }
            } else {
                if (featureName != null && featureName.length() > 0 && (featureValue == null || (featureValue != null && featureValue.length() <= 0))) {
                    if (DEBUG) {
                        SemLog.m478d(LOG_TAG, "No value for " + featureName + " " + info.toString());
                    }
                    return false;
                }
                if (featureValue != null && featureValue.length() > 0 && (featureName == null || (featureName != null && featureName.length() <= 0))) {
                    if (DEBUG) {
                        SemLog.m478d(LOG_TAG, "No feature name is provided for the value " + featureValue + " " + info.toString());
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
        List<String> keyList = new ArrayList<>(this.mBundle.keySet());
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            sb.append("{");
            sb.append(key);
            sb.append("=");
            sb.append(this.mBundle.get(key));
            sb.append("}");
        }
        String result = sb.toString();
        return result;
    }

    public String toString() {
        String retString = "SemExecutableInfo{enabled=" + this.mEnabled + ", id=" + this.mUid + ", labelId=" + this.mLabelId + ", iconIId=" + this.mIconId + ", smallIconIId=" + this.mSmallIconId + ", type=" + this.mLaunchType + ", category=" + this.mCategory + ", action='" + this.mAction + DateFormat.QUOTE + ", packageName='" + this.mPackageName + DateFormat.QUOTE + ", componentName='" + this.mComponentName + DateFormat.QUOTE + ", launchMode='" + this.mActivityLaunchMode + DateFormat.QUOTE;
        StringBuilder sb = new StringBuilder();
        sb.append(retString);
        for (int i = 0; i < this.mFeatureNames.size(); i++) {
            sb.append(", featureName ='");
            sb.append(this.mFeatureNames.get(i));
            sb.append(DateFormat.QUOTE);
            sb.append(", featureValue = '");
            sb.append(this.mFeatureValues.get(i));
            sb.append(DateFormat.QUOTE);
        }
        sb.append(", mBundle ='");
        sb.append(getBundleString());
        sb.append(DateFormat.QUOTE);
        sb.append('}');
        String retString2 = sb.toString();
        return retString2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SemExecutableInfo)) {
            return false;
        }
        SemExecutableInfo that = (SemExecutableInfo) o;
        if (this.mEnabled != that.mEnabled || this.mLabelId != that.mLabelId || this.mIconId != that.mIconId || this.mSmallIconId != that.mSmallIconId || this.mLaunchType != that.mLaunchType) {
            return false;
        }
        String str = this.mUid;
        if (str == null ? that.mUid != null : !str.equals(that.mUid)) {
            return false;
        }
        String str2 = this.mCategory;
        if (str2 == null ? that.mCategory != null : !str2.equals(that.mCategory)) {
            return false;
        }
        String str3 = this.mAction;
        if (str3 == null ? that.mAction != null : !str3.equals(that.mAction)) {
            return false;
        }
        String str4 = this.mPackageName;
        if (str4 == null ? that.mPackageName != null : !str4.equals(that.mPackageName)) {
            return false;
        }
        List<String> list = this.mFeatureNames;
        if (list == null ? that.mFeatureNames != null : !list.equals(that.mFeatureNames)) {
            return false;
        }
        List<String> list2 = this.mFeatureValues;
        if (list2 == null ? that.mFeatureValues != null : !list2.equals(that.mFeatureValues)) {
            return false;
        }
        Bundle bundle = this.mBundle;
        if (bundle == null ? that.mBundle != null : !bundle.equals(that.mBundle)) {
            return false;
        }
        String str5 = this.mComponentName;
        if (str5 == null ? that.mComponentName != null : !str5.equals(that.mComponentName)) {
            return false;
        }
        String str6 = this.mActivityLaunchMode;
        return str6 == null ? that.mActivityLaunchMode == null : str6.equals(that.mActivityLaunchMode);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
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
