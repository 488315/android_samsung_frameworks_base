package com.android.server.am;

import android.content.ComponentName;

/* loaded from: classes.dex */
public final class HostingRecord {
    public final String mAction;
    public final String mDefiningPackageName;
    public final String mDefiningProcessName;
    public final int mDefiningUid;
    public String mFrom;
    public final String mHostingName;
    public final String mHostingType;
    public final int mHostingZygote;
    public final boolean mIsTopApp;
    public final String mTriggerType;

    public HostingRecord(String str) {
        this(str, null, 0, null, -1, false, null, null, "unknown");
    }

    public HostingRecord(String str, ComponentName componentName) {
        this(str, componentName, 0);
    }

    public HostingRecord(String str, ComponentName componentName, String str2, String str3) {
        this(str, componentName.toShortString(), 0, null, -1, false, null, str2, str3);
    }

    public HostingRecord(String str, ComponentName componentName, boolean z) {
        this(str, componentName.toShortString(), 0, null, -1, z, null, null, "unknown");
    }

    public HostingRecord(String str, String str2) {
        this(str, str2, 0);
    }

    public HostingRecord(String str, ComponentName componentName, int i) {
        this(str, componentName.toShortString(), i);
    }

    public HostingRecord(String str, String str2, int i) {
        this(str, str2, i, null, -1, false, null, null, "unknown");
    }

    public HostingRecord(String str, String str2, int i, String str3, int i2, boolean z, String str4, String str5, String str6) {
        this.mHostingType = str;
        this.mHostingName = str2;
        this.mHostingZygote = i;
        this.mDefiningPackageName = str3;
        this.mDefiningUid = i2;
        this.mIsTopApp = z;
        this.mDefiningProcessName = str4;
        this.mAction = str5;
        this.mTriggerType = str6;
    }

    public HostingRecord(String str, ComponentName componentName, String str2) {
        this(str, componentName.toShortString(), 0, null, -1, false, null, null, "unknown", str2);
    }

    public HostingRecord(String str, ComponentName componentName, String str2, String str3, String str4) {
        this(str, componentName.toShortString(), 0, null, -1, false, null, str2, str3, str4);
    }

    public HostingRecord(String str, ComponentName componentName, String str2, int i, String str3, String str4, String str5) {
        this(str, componentName.toShortString(), 0, str2, i, false, str3, null, str4, str5);
    }

    public HostingRecord(String str, String str2, int i, String str3, int i2, boolean z, String str4, String str5, String str6, String str7) {
        this.mHostingType = str;
        this.mHostingName = str2;
        this.mHostingZygote = i;
        this.mDefiningPackageName = str3;
        this.mDefiningUid = i2;
        this.mIsTopApp = z;
        this.mDefiningProcessName = str4;
        this.mAction = str5;
        this.mTriggerType = str6;
        this.mFrom = str7;
    }

    public String toStringForTracker() {
        return hashCode() + "/" + this.mHostingName + "/" + this.mAction;
    }

    public String getType() {
        return this.mHostingType;
    }

    public String getName() {
        return this.mHostingName;
    }

    public boolean isTopApp() {
        return this.mIsTopApp;
    }

    public int getDefiningUid() {
        return this.mDefiningUid;
    }

    public String getDefiningPackageName() {
        return this.mDefiningPackageName;
    }

    public String getDefiningProcessName() {
        return this.mDefiningProcessName;
    }

    public String getAction() {
        return this.mAction;
    }

    public String getTriggerType() {
        return this.mTriggerType;
    }

    public static HostingRecord byWebviewZygote(ComponentName componentName, String str, int i, String str2) {
        return new HostingRecord("", componentName.toShortString(), 1, str, i, false, str2, null, "unknown");
    }

    public static HostingRecord byAppZygote(ComponentName componentName, String str, int i, String str2) {
        return new HostingRecord("", componentName.toShortString(), 2, str, i, false, str2, null, "unknown");
    }

    public boolean usesAppZygote() {
        return this.mHostingZygote == 2;
    }

    public boolean usesWebviewZygote() {
        return this.mHostingZygote == 1;
    }

    public static int getHostingTypeIdStatsd(String str) {
        str.hashCode();
        switch (str) {
            case "top-activity":
                return 13;
            case "link fail":
                return 6;
            case "activity":
                return 1;
            case "broadcast":
                return 4;
            case "next-top-activity":
                return 9;
            case "backup":
                return 3;
            case "on-hold":
                return 7;
            case "next-activity":
                return 8;
            case "system":
                return 12;
            case "":
                return 14;
            case "restart":
                return 10;
            case "content provider":
                return 5;
            case "added application":
                return 2;
            case "service":
                return 11;
            default:
                return 0;
        }
    }

    public static int getTriggerTypeForStatsd(String str) {
        str.hashCode();
        switch (str) {
            case "push_message_over_quota":
                return 3;
            case "job":
                return 4;
            case "alarm":
                return 1;
            case "push_message":
                return 2;
            default:
                return 0;
        }
    }
}
