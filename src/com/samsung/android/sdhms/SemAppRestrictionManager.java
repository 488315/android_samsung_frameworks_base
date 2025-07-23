package com.samsung.android.sdhms;

import android.Manifest;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.List;

/* loaded from: classes6.dex */
public class SemAppRestrictionManager implements Parcelable {
    public static final Parcelable.Creator<SemAppRestrictionManager> CREATOR = new Parcelable.Creator<SemAppRestrictionManager>() { // from class: com.samsung.android.sdhms.SemAppRestrictionManager.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemAppRestrictionManager createFromParcel(Parcel parcel) {
            return new SemAppRestrictionManager(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemAppRestrictionManager[] newArray(int i) {
            return new SemAppRestrictionManager[i];
        }
    };
    public static final String RESTRICTION_REASON_TYPE_ADD_ANOMALY_AUTO = "added_from_anomaly_auto";
    public static final String RESTRICTION_REASON_TYPE_ADD_ANOMALY_MANUAL = "added_from_anomaly_manual";
    public static final String RESTRICTION_REASON_TYPE_ADD_MARS_AUTO = "added_from_mars_auto";
    public static final String RESTRICTION_REASON_TYPE_ADD_MARS_AUTO_SPECIFIC = "added_from_mars_auto_specific";
    public static final String RESTRICTION_REASON_TYPE_ADD_MARS_MANUAL_SPECIFIC = "added_from_mars_manual_specific";
    public static final String RESTRICTION_REASON_TYPE_ADD_POLICY_IN_CHINA = "added_from_policy_in_china";
    public static final String RESTRICTION_REASON_TYPE_ADD_PRE_O = "added_from_pre_o";
    public static final String RESTRICTION_REASON_TYPE_ADD_UNKNOWN = "added_from_unknown";
    public static final String RESTRICTION_REASON_TYPE_ADD_USER_MANUAL = "added_from_user_manual";
    public static final String RESTRICTION_REASON_TYPE_DEFAULT = "default";
    public static final String RESTRICTION_REASON_TYPE_DELETE_MARS_AUTO = "deleted_from_mars_auto";
    public static final String RESTRICTION_REASON_TYPE_DELETE_POLICY_IN_CHINA = "deleted_from_policy_in_china";
    public static final String RESTRICTION_REASON_TYPE_DELETE_POST_O = "deleted_from_post_o";
    public static final String RESTRICTION_REASON_TYPE_DELETE_UNKNOWN = "deleted_from_unknown";
    public static final String RESTRICTION_REASON_TYPE_DELETE_USER_MANUAL = "deleted_from_user_manual";
    public static final int RESTRICTION_STATE_NONE = 0;
    public static final int RESTRICTION_STATE_OFF = 2;
    public static final int RESTRICTION_STATE_ON = 1;
    public static final int RESTRICTION_TYPE_DISABLE = 0;
    public static final int RESTRICTION_TYPE_DISABLE_SPECIFIC = 4;
    public static final int RESTRICTION_TYPE_DISABLE_WITHIN_24_HOUR = 2;
    public static final int RESTRICTION_TYPE_NEVER_SLEEP = 3;
    public static final int RESTRICTION_TYPE_SLEEP = 1;
    private Context mContext;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public RestrictionInfo getRestrictionInfo(int i, String str, int i2) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.getRestrictionInfo(i, str, i2);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean canRestrict(int i, String str, int i2) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.canRestrict(i, str, i2);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restrict(int i, int i2, boolean z, String str, int i3) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.restrict(i, i2, z, str, i3);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AppRestrictionInfo> getRestrictableList(int i) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.getRestrictableList(i);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AppRestrictionInfo> getAllList() {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.getAllRestrictedList();
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AppRestrictionInfo> getRestrictedList(int i) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.getRestrictedList(i);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRestrictionInfo(RestrictionInfo restrictionInfo, List<AppRestrictionInfo> list) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.updateRestrictionInfo(restrictionInfo, list);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearRestrictionInfo(List<AppRestrictionInfo> list) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                return service.clearRestrictionInfo(list);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SemAppRestrictionManager() {
        this.mContext = null;
    }

    public SemAppRestrictionManager(Context context) {
        this.mContext = context;
    }

    protected SemAppRestrictionManager(Parcel parcel) {
        this.mContext = null;
    }

    private void checkPermission(String str) {
        Context context = this.mContext;
        if (context != null && context.checkSelfPermission(str) == -1) {
            throw new SecurityException("Must have permission " + str);
        }
        int callingUid = Binder.getCallingUid();
        if (1000 == UserHandle.getAppId(callingUid) || ActivityManager.checkUidPermission(str, callingUid) != -1) {
            return;
        }
        throw new SecurityException("Must have permission " + str);
    }

    public static class RestrictionInfo implements Parcelable {
        public static final Parcelable.Creator<RestrictionInfo> CREATOR = new Parcelable.Creator<RestrictionInfo>() { // from class: com.samsung.android.sdhms.SemAppRestrictionManager.RestrictionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RestrictionInfo createFromParcel(Parcel parcel) {
                return new RestrictionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RestrictionInfo[] newArray(int i) {
                return new RestrictionInfo[i];
            }
        };
        private boolean byUser;
        private String reason;
        private int state;
        private int type;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RestrictionInfo(int i, int i2, String str) {
            this.byUser = false;
            this.type = i;
            this.state = i2;
            this.reason = str;
            this.byUser = getChangedByUserFromReason(str);
        }

        public boolean getChangedByUserFromReason(String str) {
            return SemAppRestrictionManager.RESTRICTION_REASON_TYPE_ADD_USER_MANUAL.equals(str) || SemAppRestrictionManager.RESTRICTION_REASON_TYPE_ADD_ANOMALY_MANUAL.equals(str) || SemAppRestrictionManager.RESTRICTION_REASON_TYPE_DELETE_USER_MANUAL.equals(str) || "default".equals(str);
        }

        public int getType() {
            return this.type;
        }

        public int getState() {
            return this.state;
        }

        public boolean isChangedByUser() {
            return this.byUser;
        }

        public String getReason() {
            return this.reason;
        }

        protected RestrictionInfo(Parcel parcel) {
            this.type = -1;
            this.state = -1;
            this.byUser = false;
            this.reason = null;
            this.type = parcel.readInt();
            this.state = parcel.readInt();
            this.reason = parcel.readString();
            this.byUser = parcel.readBoolean();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.type);
            parcel.writeInt(this.state);
            parcel.writeString(this.reason);
            parcel.writeBoolean(this.byUser);
        }
    }

    public static class AppRestrictionInfo implements Parcelable {
        public static final Parcelable.Creator<AppRestrictionInfo> CREATOR = new Parcelable.Creator<AppRestrictionInfo>() { // from class: com.samsung.android.sdhms.SemAppRestrictionManager.AppRestrictionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppRestrictionInfo createFromParcel(Parcel parcel) {
                return new AppRestrictionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppRestrictionInfo[] newArray(int i) {
                return new AppRestrictionInfo[i];
            }
        };
        private String packageName;
        private RestrictionInfo restrictionInfo;
        private int uid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AppRestrictionInfo(String str, int i) {
            this.restrictionInfo = null;
            this.packageName = str;
            this.uid = i;
        }

        public AppRestrictionInfo(String str, int i, RestrictionInfo restrictionInfo) {
            this.packageName = str;
            this.uid = i;
            this.restrictionInfo = restrictionInfo;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public int getUid() {
            return this.uid;
        }

        public RestrictionInfo getRestrictionInfo() {
            return this.restrictionInfo;
        }

        protected AppRestrictionInfo(Parcel parcel) {
            this.packageName = null;
            this.uid = -1;
            this.restrictionInfo = null;
            this.packageName = parcel.readString();
            this.uid = parcel.readInt();
            this.restrictionInfo = (RestrictionInfo) parcel.readParcelable(RestrictionInfo.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.packageName);
            parcel.writeInt(this.uid);
            parcel.writeParcelable(this.restrictionInfo, i);
        }
    }
}
