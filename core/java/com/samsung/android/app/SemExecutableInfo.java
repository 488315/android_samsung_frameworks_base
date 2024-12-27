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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private static final String XML_ELEMENT_EXTRA_ATTR_TYPE_ACTIVITY_FOR_RESULT =
            "activityForResult";
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
    public static final Parcelable.Creator<SemExecutableInfo> CREATOR =
            new Parcelable.Creator<
                    SemExecutableInfo>() { // from class:
                                           // com.samsung.android.app.SemExecutableInfo.1
                @Override // android.os.Parcelable.Creator
                public SemExecutableInfo createFromParcel(Parcel in) {
                    return new SemExecutableInfo(in);
                }

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
        String baseId =
                getAction()
                        + getPackageName()
                        + getComponentName()
                        + getLaunchType()
                        + getBundleString();
        String id = String.valueOf(baseId.hashCode() & 4294967295L);
        try {
            long lastId = Long.parseLong(this.mUid);
            SemLog.d(LOG_TAG, "Use defined mUid: " + lastId);
            id = this.mUid;
        } catch (Exception e) {
            SemLog.d(LOG_TAG, "Not set mUid: " + this.mUid);
        }
        builder.appendPath(id);
        this.mUid = builder.toString();
    }

    public String getId() {
        return this.mUid;
    }

    public List<String> getCategories() {
        if (this.mCategory == null || TextUtils.isEmpty(this.mCategory)) {
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
        if (this.mActivityLaunchMode == null || this.mActivityLaunchMode.length() == 0) {
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

    private static SemExecutableInfo getActivityMetaData(
            Context context, AttributeSet attr, ComponentName cName) {
        SemExecutableInfo result = new SemExecutableInfo();
        Context activityContext = createActivityContext(context, cName);
        if (activityContext == null) {
            return null;
        }
        TypedArray a = activityContext.obtainStyledAttributes(attr, R.styleable.command);
        result.mUid = a.getString(3);
        result.mEnabled = a.getBoolean(2, true);
        result.mLabelId = a.getResourceId(0, 0);
        result.mIconId = a.getResourceId(1, 0);
        result.mSmallIconId = a.getResourceId(4, 0);
        a.recycle();
        return result;
    }

    private void addExtraAttribute(Context activityContext, AttributeSet attr) {
        TypedArray ta = activityContext.obtainStyledAttributes(attr, R.styleable.extrasCommand);
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
        } else if (XML_ELEMENT_EXTRA_ATTR_CATEGORY.equals(name)) {
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
                                    SemLog.d(LOG_TAG, "Invalid order");
                                    nfe.printStackTrace();
                                }
                                if (-9998 == -9999
                                        || -9998 == ORDER_NOT_ALLOWED
                                        || -9998 == -9998) {
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
                        SemLog.d(LOG_TAG, "Invalid category format for category order");
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
            SemLog.e(LOG_TAG, "Package not found " + componentName.getPackageName());
            return null;
        } catch (SecurityException e2) {
            SemLog.e(LOG_TAG, "Can't make context for " + componentName.getPackageName(), e2);
            return null;
        }
    }

    public static List<SemExecutableInfo> scanExecutableInfos(Context context) {
        SemExecutableInfo lastSemExecutableInfo;
        PackageItemInfo itemInfo;
        PackageManager pm;
        ApplicationInfo appInfo;
        int queryFlag;
        boolean isDisabled;
        List<ResolveInfo> activityExecutableList;
        boolean isComponentDisabled;
        String str;
        List<ResolveInfo> receiverExecutableList;
        List<ResolveInfo> serviceExecutableList;
        int i;
        List[] executableListArray;
        String str2;
        int i2;
        List[] executableListArray2;
        XmlResourceParser xml;
        ApplicationInfo appInfo2;
        boolean startedCommand;
        SemExecutableInfo lastSemExecutableInfo2;
        Context context2 = context;
        if (DEBUG) {
            SemLog.d(LOG_TAG, "scan scanExecutableInfos start");
        }
        String ACTION_EXECUTABLE = MD_LABEL_EXECUTABLE;
        PackageManager pm2 = context.getPackageManager();
        List<SemExecutableInfo> newSemExecutableInfoList = new ArrayList<>();
        SemExecutableInfo lastSemExecutableInfo3 = null;
        int queryFlag2 = 640;
        String str3 = MD_LABEL_EXECUTABLE;
        List<ResolveInfo> activityExecutableList2 =
                pm2.queryIntentActivities(new Intent(MD_LABEL_EXECUTABLE), 640);
        List<ResolveInfo> serviceExecutableList2 =
                pm2.queryIntentServices(new Intent(MD_LABEL_EXECUTABLE), 640);
        List<ResolveInfo> receiverExecutableList2 =
                pm2.queryBroadcastReceivers(new Intent(MD_LABEL_EXECUTABLE), 640);
        List[] executableListArray3 = {
            activityExecutableList2, serviceExecutableList2, receiverExecutableList2
        };
        int length = executableListArray3.length;
        int i3 = 0;
        while (i3 < length) {
            List list = executableListArray3[i3];
            if (DEBUG) {
                lastSemExecutableInfo = lastSemExecutableInfo3;
                SemLog.d(LOG_TAG, "list size = " + list.size());
            } else {
                lastSemExecutableInfo = lastSemExecutableInfo3;
            }
            for (ResolveInfo info : list) {
                String ACTION_EXECUTABLE2 = ACTION_EXECUTABLE;
                PackageItemInfo itemInfo2 = info.activityInfo;
                if (itemInfo2 != null) {
                    PackageItemInfo itemInfo3 = info.activityInfo;
                    ApplicationInfo appInfo3 = info.activityInfo.applicationInfo;
                    boolean isDisabled2 = !info.activityInfo.applicationInfo.enabled;
                    boolean isComponentDisabled2 = !info.activityInfo.enabled;
                    itemInfo = itemInfo3;
                    pm = pm2;
                    appInfo = appInfo3;
                    queryFlag = queryFlag2;
                    isDisabled = isDisabled2;
                    activityExecutableList = activityExecutableList2;
                    isComponentDisabled = isComponentDisabled2;
                } else if (info.serviceInfo != null) {
                    PackageItemInfo itemInfo4 = info.serviceInfo;
                    ApplicationInfo appInfo4 = info.serviceInfo.applicationInfo;
                    boolean isDisabled3 = !info.serviceInfo.applicationInfo.enabled;
                    boolean isComponentDisabled3 = !info.serviceInfo.enabled;
                    itemInfo = itemInfo4;
                    pm = pm2;
                    appInfo = appInfo4;
                    queryFlag = queryFlag2;
                    isDisabled = isDisabled3;
                    activityExecutableList = activityExecutableList2;
                    isComponentDisabled = isComponentDisabled3;
                } else {
                    itemInfo = null;
                    pm = pm2;
                    appInfo = null;
                    queryFlag = queryFlag2;
                    isDisabled = true;
                    activityExecutableList = activityExecutableList2;
                    isComponentDisabled = true;
                }
                if (isDisabled) {
                    str = str3;
                    receiverExecutableList = receiverExecutableList2;
                    serviceExecutableList = serviceExecutableList2;
                    i = length;
                    executableListArray = executableListArray3;
                } else if (isComponentDisabled) {
                    str = str3;
                    receiverExecutableList = receiverExecutableList2;
                    serviceExecutableList = serviceExecutableList2;
                    i = length;
                    executableListArray = executableListArray3;
                } else {
                    receiverExecutableList = receiverExecutableList2;
                    serviceExecutableList = serviceExecutableList2;
                    ComponentName cName = new ComponentName(itemInfo.packageName, itemInfo.name);
                    try {
                        xml = appInfo.loadXmlMetaData(context.getPackageManager(), str3);
                    } catch (IOException e) {
                        e = e;
                        str2 = str3;
                        i2 = length;
                        executableListArray2 = executableListArray3;
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        str2 = str3;
                        i2 = length;
                        executableListArray2 = executableListArray3;
                    } catch (XmlPullParserException e3) {
                        e = e3;
                        str2 = str3;
                        i2 = length;
                        executableListArray2 = executableListArray3;
                    } catch (Exception e4) {
                        e = e4;
                        str2 = str3;
                        i2 = length;
                        executableListArray2 = executableListArray3;
                    }
                    if (xml != null) {
                        lastSemExecutableInfo = null;
                        boolean startedExecutable = false;
                        boolean startedCommand2 = false;
                        int tagType = xml.next();
                        while (true) {
                            str2 = str3;
                            if (tagType == 1) {
                                i2 = length;
                                executableListArray2 = executableListArray3;
                                break;
                            }
                            try {
                                executableListArray2 = xml.getName();
                                boolean startedExecutable2 = startedExecutable;
                                i2 = length;
                                if (tagType == 2) {
                                    try {
                                        startedExecutable =
                                                XML_ELEMENT_EXECUTABLE.equals(executableListArray2)
                                                        ? true
                                                        : startedExecutable2;
                                        try {
                                            if (!"command".equals(executableListArray2)) {
                                                executableListArray2 = executableListArray3;
                                                lastSemExecutableInfo2 = lastSemExecutableInfo;
                                            } else {
                                                if (!startedExecutable) {
                                                    throw new XmlPullParserException(
                                                            "executable element wasn't started");
                                                }
                                                startedCommand2 = true;
                                                try {
                                                    AttributeSet attr = Xml.asAttributeSet(xml);
                                                    SemExecutableInfo lastSemExecutableInfo4 =
                                                            getActivityMetaData(
                                                                    context2, attr, cName);
                                                    executableListArray2 = executableListArray3;
                                                    lastSemExecutableInfo2 = lastSemExecutableInfo4;
                                                } catch (IOException e5) {
                                                    e = e5;
                                                    executableListArray2 = executableListArray3;
                                                    SemLog.w(
                                                            LOG_TAG,
                                                            "Reading SemExecutableInfo metadata for"
                                                                + " "
                                                                    + cName.flattenToShortString(),
                                                            e);
                                                    context2 = context;
                                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                    pm2 = pm;
                                                    queryFlag2 = queryFlag;
                                                    activityExecutableList2 =
                                                            activityExecutableList;
                                                    receiverExecutableList2 =
                                                            receiverExecutableList;
                                                    serviceExecutableList2 = serviceExecutableList;
                                                    str3 = str2;
                                                    executableListArray3 = executableListArray2;
                                                    length = i2;
                                                } catch (IllegalArgumentException e6) {
                                                    e = e6;
                                                    executableListArray2 = executableListArray3;
                                                    SemLog.w(
                                                            LOG_TAG,
                                                            "Invalid attribute in metadata for "
                                                                    + cName.flattenToShortString()
                                                                    + ": "
                                                                    + e.getMessage());
                                                    context2 = context;
                                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                    pm2 = pm;
                                                    queryFlag2 = queryFlag;
                                                    activityExecutableList2 =
                                                            activityExecutableList;
                                                    receiverExecutableList2 =
                                                            receiverExecutableList;
                                                    serviceExecutableList2 = serviceExecutableList;
                                                    str3 = str2;
                                                    executableListArray3 = executableListArray2;
                                                    length = i2;
                                                } catch (XmlPullParserException e7) {
                                                    e = e7;
                                                    executableListArray2 = executableListArray3;
                                                    SemLog.w(
                                                            LOG_TAG,
                                                            "Reading SemExecutableInfo metadata for"
                                                                + " "
                                                                    + cName.flattenToShortString(),
                                                            e);
                                                    context2 = context;
                                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                    pm2 = pm;
                                                    queryFlag2 = queryFlag;
                                                    activityExecutableList2 =
                                                            activityExecutableList;
                                                    receiverExecutableList2 =
                                                            receiverExecutableList;
                                                    serviceExecutableList2 = serviceExecutableList;
                                                    str3 = str2;
                                                    executableListArray3 = executableListArray2;
                                                    length = i2;
                                                } catch (Exception e8) {
                                                    e = e8;
                                                    executableListArray2 = executableListArray3;
                                                    SemLog.w(
                                                            LOG_TAG,
                                                            "Unknown Exception while Reading"
                                                                + " SemExecutableInfo metadata",
                                                            e);
                                                    context2 = context;
                                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                    pm2 = pm;
                                                    queryFlag2 = queryFlag;
                                                    activityExecutableList2 =
                                                            activityExecutableList;
                                                    receiverExecutableList2 =
                                                            receiverExecutableList;
                                                    serviceExecutableList2 = serviceExecutableList;
                                                    str3 = str2;
                                                    executableListArray3 = executableListArray2;
                                                    length = i2;
                                                }
                                            }
                                            try {
                                                if (!XML_ELEMENT_EXTRA_ATTR.equals(
                                                        executableListArray2)) {
                                                    lastSemExecutableInfo = lastSemExecutableInfo2;
                                                } else {
                                                    if (!startedExecutable || !startedCommand2) {
                                                        break;
                                                    }
                                                    try {
                                                        AttributeSet attr2 =
                                                                Xml.asAttributeSet(xml);
                                                        if (lastSemExecutableInfo2 != null) {
                                                            lastSemExecutableInfo2
                                                                    .addExtraAttribute(
                                                                            context2, attr2);
                                                        }
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo2;
                                                    } catch (IOException e9) {
                                                        e = e9;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo2;
                                                        SemLog.w(
                                                                LOG_TAG,
                                                                "Reading SemExecutableInfo metadata"
                                                                    + " for "
                                                                        + cName
                                                                                .flattenToShortString(),
                                                                e);
                                                        context2 = context;
                                                        ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                        pm2 = pm;
                                                        queryFlag2 = queryFlag;
                                                        activityExecutableList2 =
                                                                activityExecutableList;
                                                        receiverExecutableList2 =
                                                                receiverExecutableList;
                                                        serviceExecutableList2 =
                                                                serviceExecutableList;
                                                        str3 = str2;
                                                        executableListArray3 = executableListArray2;
                                                        length = i2;
                                                    } catch (IllegalArgumentException e10) {
                                                        e = e10;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo2;
                                                        SemLog.w(
                                                                LOG_TAG,
                                                                "Invalid attribute in metadata for "
                                                                        + cName
                                                                                .flattenToShortString()
                                                                        + ": "
                                                                        + e.getMessage());
                                                        context2 = context;
                                                        ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                        pm2 = pm;
                                                        queryFlag2 = queryFlag;
                                                        activityExecutableList2 =
                                                                activityExecutableList;
                                                        receiverExecutableList2 =
                                                                receiverExecutableList;
                                                        serviceExecutableList2 =
                                                                serviceExecutableList;
                                                        str3 = str2;
                                                        executableListArray3 = executableListArray2;
                                                        length = i2;
                                                    } catch (XmlPullParserException e11) {
                                                        e = e11;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo2;
                                                        SemLog.w(
                                                                LOG_TAG,
                                                                "Reading SemExecutableInfo metadata"
                                                                    + " for "
                                                                        + cName
                                                                                .flattenToShortString(),
                                                                e);
                                                        context2 = context;
                                                        ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                        pm2 = pm;
                                                        queryFlag2 = queryFlag;
                                                        activityExecutableList2 =
                                                                activityExecutableList;
                                                        receiverExecutableList2 =
                                                                receiverExecutableList;
                                                        serviceExecutableList2 =
                                                                serviceExecutableList;
                                                        str3 = str2;
                                                        executableListArray3 = executableListArray2;
                                                        length = i2;
                                                    } catch (Exception e12) {
                                                        e = e12;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo2;
                                                        SemLog.w(
                                                                LOG_TAG,
                                                                "Unknown Exception while Reading"
                                                                    + " SemExecutableInfo metadata",
                                                                e);
                                                        context2 = context;
                                                        ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                                        pm2 = pm;
                                                        queryFlag2 = queryFlag;
                                                        activityExecutableList2 =
                                                                activityExecutableList;
                                                        receiverExecutableList2 =
                                                                receiverExecutableList;
                                                        serviceExecutableList2 =
                                                                serviceExecutableList;
                                                        str3 = str2;
                                                        executableListArray3 = executableListArray2;
                                                        length = i2;
                                                    }
                                                }
                                                appInfo2 = appInfo;
                                            } catch (IOException e13) {
                                                e = e13;
                                                lastSemExecutableInfo = lastSemExecutableInfo2;
                                            } catch (IllegalArgumentException e14) {
                                                e = e14;
                                                lastSemExecutableInfo = lastSemExecutableInfo2;
                                            } catch (XmlPullParserException e15) {
                                                e = e15;
                                                lastSemExecutableInfo = lastSemExecutableInfo2;
                                            } catch (Exception e16) {
                                                e = e16;
                                                lastSemExecutableInfo = lastSemExecutableInfo2;
                                            }
                                        } catch (IOException e17) {
                                            e = e17;
                                        } catch (IllegalArgumentException e18) {
                                            e = e18;
                                        } catch (XmlPullParserException e19) {
                                            e = e19;
                                        } catch (Exception e20) {
                                            e = e20;
                                        }
                                    } catch (IOException e21) {
                                        e = e21;
                                        executableListArray2 = executableListArray3;
                                    } catch (IllegalArgumentException e22) {
                                        e = e22;
                                        executableListArray2 = executableListArray3;
                                    } catch (XmlPullParserException e23) {
                                        e = e23;
                                        executableListArray2 = executableListArray3;
                                    } catch (Exception e24) {
                                        e = e24;
                                        executableListArray2 = executableListArray3;
                                    }
                                } else {
                                    executableListArray2 = executableListArray3;
                                    if (tagType == 3) {
                                        try {
                                            startedExecutable =
                                                    XML_ELEMENT_EXECUTABLE.equals(
                                                                    executableListArray2)
                                                            ? false
                                                            : startedExecutable2;
                                            if ("command".equals(executableListArray2)) {
                                                boolean startedCommand3 = false;
                                                if (checkValidate(lastSemExecutableInfo)) {
                                                    SemExecutableWhitelist whiteList =
                                                            SemExecutableWhitelist.getInstance();
                                                    boolean bSamsungApps =
                                                            whiteList.isAllowedToUseOrder(
                                                                    context2, appInfo.packageName);
                                                    SemExecutableInfo lastSemExecutableInfo5 =
                                                            lastSemExecutableInfo;
                                                    try {
                                                        examineOrderInCategory(
                                                                lastSemExecutableInfo5,
                                                                bSamsungApps);
                                                        lastSemExecutableInfo5.setId(
                                                                appInfo.packageName);
                                                        boolean bDuplicatedID = false;
                                                        for (SemExecutableInfo checkInfo :
                                                                newSemExecutableInfoList) {
                                                            ApplicationInfo appInfo5 = appInfo;
                                                            try {
                                                                boolean startedCommand4 =
                                                                        startedCommand3;
                                                                if (TextUtils.equals(
                                                                        checkInfo.getId(),
                                                                        lastSemExecutableInfo5
                                                                                .getId())) {
                                                                    bDuplicatedID = true;
                                                                }
                                                                appInfo = appInfo5;
                                                                startedCommand3 = startedCommand4;
                                                            } catch (IOException e25) {
                                                                e = e25;
                                                                lastSemExecutableInfo =
                                                                        lastSemExecutableInfo5;
                                                                SemLog.w(
                                                                        LOG_TAG,
                                                                        "Reading SemExecutableInfo"
                                                                            + " metadata for "
                                                                                + cName
                                                                                        .flattenToShortString(),
                                                                        e);
                                                                context2 = context;
                                                                ACTION_EXECUTABLE =
                                                                        ACTION_EXECUTABLE2;
                                                                pm2 = pm;
                                                                queryFlag2 = queryFlag;
                                                                activityExecutableList2 =
                                                                        activityExecutableList;
                                                                receiverExecutableList2 =
                                                                        receiverExecutableList;
                                                                serviceExecutableList2 =
                                                                        serviceExecutableList;
                                                                str3 = str2;
                                                                executableListArray3 =
                                                                        executableListArray2;
                                                                length = i2;
                                                            } catch (IllegalArgumentException e26) {
                                                                e = e26;
                                                                lastSemExecutableInfo =
                                                                        lastSemExecutableInfo5;
                                                                SemLog.w(
                                                                        LOG_TAG,
                                                                        "Invalid attribute in"
                                                                            + " metadata for "
                                                                                + cName
                                                                                        .flattenToShortString()
                                                                                + ": "
                                                                                + e.getMessage());
                                                                context2 = context;
                                                                ACTION_EXECUTABLE =
                                                                        ACTION_EXECUTABLE2;
                                                                pm2 = pm;
                                                                queryFlag2 = queryFlag;
                                                                activityExecutableList2 =
                                                                        activityExecutableList;
                                                                receiverExecutableList2 =
                                                                        receiverExecutableList;
                                                                serviceExecutableList2 =
                                                                        serviceExecutableList;
                                                                str3 = str2;
                                                                executableListArray3 =
                                                                        executableListArray2;
                                                                length = i2;
                                                            } catch (XmlPullParserException e27) {
                                                                e = e27;
                                                                lastSemExecutableInfo =
                                                                        lastSemExecutableInfo5;
                                                                SemLog.w(
                                                                        LOG_TAG,
                                                                        "Reading SemExecutableInfo"
                                                                            + " metadata for "
                                                                                + cName
                                                                                        .flattenToShortString(),
                                                                        e);
                                                                context2 = context;
                                                                ACTION_EXECUTABLE =
                                                                        ACTION_EXECUTABLE2;
                                                                pm2 = pm;
                                                                queryFlag2 = queryFlag;
                                                                activityExecutableList2 =
                                                                        activityExecutableList;
                                                                receiverExecutableList2 =
                                                                        receiverExecutableList;
                                                                serviceExecutableList2 =
                                                                        serviceExecutableList;
                                                                str3 = str2;
                                                                executableListArray3 =
                                                                        executableListArray2;
                                                                length = i2;
                                                            } catch (Exception e28) {
                                                                e = e28;
                                                                lastSemExecutableInfo =
                                                                        lastSemExecutableInfo5;
                                                                SemLog.w(
                                                                        LOG_TAG,
                                                                        "Unknown Exception while"
                                                                            + " Reading"
                                                                            + " SemExecutableInfo"
                                                                            + " metadata",
                                                                        e);
                                                                context2 = context;
                                                                ACTION_EXECUTABLE =
                                                                        ACTION_EXECUTABLE2;
                                                                pm2 = pm;
                                                                queryFlag2 = queryFlag;
                                                                activityExecutableList2 =
                                                                        activityExecutableList;
                                                                receiverExecutableList2 =
                                                                        receiverExecutableList;
                                                                serviceExecutableList2 =
                                                                        serviceExecutableList;
                                                                str3 = str2;
                                                                executableListArray3 =
                                                                        executableListArray2;
                                                                length = i2;
                                                            }
                                                        }
                                                        appInfo2 = appInfo;
                                                        startedCommand = startedCommand3;
                                                        if (!bDuplicatedID) {
                                                            newSemExecutableInfoList.add(
                                                                    lastSemExecutableInfo5);
                                                        }
                                                    } catch (IOException e29) {
                                                        e = e29;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo5;
                                                    } catch (IllegalArgumentException e30) {
                                                        e = e30;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo5;
                                                    } catch (XmlPullParserException e31) {
                                                        e = e31;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo5;
                                                    } catch (Exception e32) {
                                                        e = e32;
                                                        lastSemExecutableInfo =
                                                                lastSemExecutableInfo5;
                                                    }
                                                } else {
                                                    appInfo2 = appInfo;
                                                    startedCommand = false;
                                                }
                                                lastSemExecutableInfo = null;
                                                startedCommand2 = startedCommand;
                                            } else {
                                                appInfo2 = appInfo;
                                            }
                                        } catch (IOException e33) {
                                            e = e33;
                                        } catch (IllegalArgumentException e34) {
                                            e = e34;
                                        } catch (XmlPullParserException e35) {
                                            e = e35;
                                        } catch (Exception e36) {
                                            e = e36;
                                        }
                                    } else {
                                        appInfo2 = appInfo;
                                        startedExecutable = startedExecutable2;
                                    }
                                }
                                try {
                                    tagType = xml.next();
                                    context2 = context;
                                    str3 = str2;
                                    executableListArray3 = executableListArray2;
                                    length = i2;
                                    appInfo = appInfo2;
                                } catch (IOException e37) {
                                    e = e37;
                                    SemLog.w(
                                            LOG_TAG,
                                            "Reading SemExecutableInfo metadata for "
                                                    + cName.flattenToShortString(),
                                            e);
                                    context2 = context;
                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                    pm2 = pm;
                                    queryFlag2 = queryFlag;
                                    activityExecutableList2 = activityExecutableList;
                                    receiverExecutableList2 = receiverExecutableList;
                                    serviceExecutableList2 = serviceExecutableList;
                                    str3 = str2;
                                    executableListArray3 = executableListArray2;
                                    length = i2;
                                } catch (IllegalArgumentException e38) {
                                    e = e38;
                                    SemLog.w(
                                            LOG_TAG,
                                            "Invalid attribute in metadata for "
                                                    + cName.flattenToShortString()
                                                    + ": "
                                                    + e.getMessage());
                                    context2 = context;
                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                    pm2 = pm;
                                    queryFlag2 = queryFlag;
                                    activityExecutableList2 = activityExecutableList;
                                    receiverExecutableList2 = receiverExecutableList;
                                    serviceExecutableList2 = serviceExecutableList;
                                    str3 = str2;
                                    executableListArray3 = executableListArray2;
                                    length = i2;
                                } catch (XmlPullParserException e39) {
                                    e = e39;
                                    SemLog.w(
                                            LOG_TAG,
                                            "Reading SemExecutableInfo metadata for "
                                                    + cName.flattenToShortString(),
                                            e);
                                    context2 = context;
                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                    pm2 = pm;
                                    queryFlag2 = queryFlag;
                                    activityExecutableList2 = activityExecutableList;
                                    receiverExecutableList2 = receiverExecutableList;
                                    serviceExecutableList2 = serviceExecutableList;
                                    str3 = str2;
                                    executableListArray3 = executableListArray2;
                                    length = i2;
                                } catch (Exception e40) {
                                    e = e40;
                                    SemLog.w(
                                            LOG_TAG,
                                            "Unknown Exception while Reading SemExecutableInfo"
                                                + " metadata",
                                            e);
                                    context2 = context;
                                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                                    pm2 = pm;
                                    queryFlag2 = queryFlag;
                                    activityExecutableList2 = activityExecutableList;
                                    receiverExecutableList2 = receiverExecutableList;
                                    serviceExecutableList2 = serviceExecutableList;
                                    str3 = str2;
                                    executableListArray3 = executableListArray2;
                                    length = i2;
                                }
                            } catch (IOException e41) {
                                e = e41;
                                i2 = length;
                                executableListArray2 = executableListArray3;
                            } catch (IllegalArgumentException e42) {
                                e = e42;
                                i2 = length;
                                executableListArray2 = executableListArray3;
                            } catch (XmlPullParserException e43) {
                                e = e43;
                                i2 = length;
                                executableListArray2 = executableListArray3;
                            } catch (Exception e44) {
                                e = e44;
                                i2 = length;
                                executableListArray2 = executableListArray3;
                            }
                        }
                        throw new XmlPullParserException(
                                "executable or command element wasn't started");
                    }
                    str = str3;
                    i = length;
                    executableListArray = executableListArray3;
                    context2 = context;
                    ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                    pm2 = pm;
                    queryFlag2 = queryFlag;
                    activityExecutableList2 = activityExecutableList;
                    receiverExecutableList2 = receiverExecutableList;
                    serviceExecutableList2 = serviceExecutableList;
                    str3 = str;
                    executableListArray3 = executableListArray;
                    length = i;
                }
                if (DEBUG) {
                    SemLog.d(
                            LOG_TAG,
                            "skip disable component: " + isDisabled + ", " + isComponentDisabled);
                }
                context2 = context;
                ACTION_EXECUTABLE = ACTION_EXECUTABLE2;
                pm2 = pm;
                queryFlag2 = queryFlag;
                activityExecutableList2 = activityExecutableList;
                receiverExecutableList2 = receiverExecutableList;
                serviceExecutableList2 = serviceExecutableList;
                str3 = str;
                executableListArray3 = executableListArray;
                length = i;
            }
            i3++;
            context2 = context;
            lastSemExecutableInfo3 = lastSemExecutableInfo;
        }
        if (DEBUG) {
            SemLog.d(LOG_TAG, "scan SemExecutableInfo end: " + newSemExecutableInfoList.size());
        }
        return newSemExecutableInfoList;
    }

    private static boolean checkValidate(SemExecutableInfo info) {
        if (info == null) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "Invalid SemExecutableInfo");
            }
            return false;
        }
        if (!info.mEnabled) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "disabled SemExecutableInfo " + info.toString());
            }
            return false;
        }
        if (info.getLaunchType() != 2
                && (info.getPackageName() == null || info.getComponentName() == null)) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "Invalid packageName or componentName = " + info.toString());
            }
            return false;
        }
        if (info.getLabelId() == 0 || info.getIconId() == 0) {
            if (DEBUG) {
                SemLog.d(LOG_TAG, "Invalid label or icon = " + info.toString());
            }
            return false;
        }
        for (int i = 0; i < info.mFeatureNames.size(); i++) {
            String featureName = info.mFeatureNames.get(i);
            String featureValue = info.mFeatureValues.get(i);
            if (featureName != null
                    && !featureName.isEmpty()
                    && featureValue != null
                    && !featureValue.isEmpty()) {
                if (featureName.startsWith(CSC_FEATURE_PREFIX)) {
                    String str = SemCscFeature.getInstance().getString(featureName);
                    if (featureValue.startsWith("!")) {
                        String value = featureValue.substring(1);
                        if (str.equalsIgnoreCase(value)) {
                            return false;
                        }
                    } else if (!str.equalsIgnoreCase(featureValue)) {
                        if (DEBUG) {
                            SemLog.d(
                                    LOG_TAG,
                                    featureName
                                            + " is not ["
                                            + featureValue
                                            + "] "
                                            + info.toString());
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
                            SemLog.d(
                                    LOG_TAG,
                                    featureName
                                            + " is not ["
                                            + featureValue
                                            + "] "
                                            + info.toString());
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
                            SemLog.d(
                                    LOG_TAG,
                                    featureName
                                            + " is not ["
                                            + featureValue
                                            + "] "
                                            + info.toString());
                        }
                        return false;
                    }
                }
            } else {
                if (featureName != null && !featureName.isEmpty()) {
                    if (DEBUG) {
                        SemLog.d(LOG_TAG, "No value for " + featureName + " " + info.toString());
                    }
                    return false;
                }
                if (featureValue != null && !featureValue.isEmpty()) {
                    if (DEBUG) {
                        SemLog.d(
                                LOG_TAG,
                                "No feature name is provided for the value "
                                        + featureValue
                                        + " "
                                        + info.toString());
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
        String retString =
                "SemExecutableInfo{enabled="
                        + this.mEnabled
                        + ", id="
                        + this.mUid
                        + ", labelId="
                        + this.mLabelId
                        + ", iconIId="
                        + this.mIconId
                        + ", smallIconIId="
                        + this.mSmallIconId
                        + ", type="
                        + this.mLaunchType
                        + ", category="
                        + this.mCategory
                        + ", action='"
                        + this.mAction
                        + DateFormat.QUOTE
                        + ", packageName='"
                        + this.mPackageName
                        + DateFormat.QUOTE
                        + ", componentName='"
                        + this.mComponentName
                        + DateFormat.QUOTE
                        + ", launchMode='"
                        + this.mActivityLaunchMode
                        + DateFormat.QUOTE;
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
        if (this.mEnabled != that.mEnabled
                || this.mLabelId != that.mLabelId
                || this.mIconId != that.mIconId
                || this.mSmallIconId != that.mSmallIconId
                || this.mLaunchType != that.mLaunchType) {
            return false;
        }
        if (this.mUid == null ? that.mUid != null : !this.mUid.equals(that.mUid)) {
            return false;
        }
        if (this.mCategory == null
                ? that.mCategory != null
                : !this.mCategory.equals(that.mCategory)) {
            return false;
        }
        if (this.mAction == null ? that.mAction != null : !this.mAction.equals(that.mAction)) {
            return false;
        }
        if (this.mPackageName == null
                ? that.mPackageName != null
                : !this.mPackageName.equals(that.mPackageName)) {
            return false;
        }
        if (this.mFeatureNames == null
                ? that.mFeatureNames != null
                : !this.mFeatureNames.equals(that.mFeatureNames)) {
            return false;
        }
        if (this.mFeatureValues == null
                ? that.mFeatureValues != null
                : !this.mFeatureValues.equals(that.mFeatureValues)) {
            return false;
        }
        if (this.mBundle == null ? that.mBundle != null : !this.mBundle.equals(that.mBundle)) {
            return false;
        }
        if (this.mComponentName == null
                ? that.mComponentName == null
                : this.mComponentName.equals(that.mComponentName)) {
            return this.mActivityLaunchMode == null
                    ? that.mActivityLaunchMode == null
                    : this.mActivityLaunchMode.equals(that.mActivityLaunchMode);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
