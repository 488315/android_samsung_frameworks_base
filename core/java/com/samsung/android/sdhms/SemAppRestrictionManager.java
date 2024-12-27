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

public class SemAppRestrictionManager implements Parcelable {
    public static final Parcelable.Creator<SemAppRestrictionManager> CREATOR =
            new Parcelable.Creator<
                    SemAppRestrictionManager>() { // from class:
                                                  // com.samsung.android.sdhms.SemAppRestrictionManager.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemAppRestrictionManager createFromParcel(Parcel in) {
                    return new SemAppRestrictionManager(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemAppRestrictionManager[] newArray(int size) {
                    return new SemAppRestrictionManager[size];
                }
            };
    public static final String RESTRICTION_REASON_TYPE_ADD_ANOMALY_AUTO = "added_from_anomaly_auto";
    public static final String RESTRICTION_REASON_TYPE_ADD_ANOMALY_MANUAL =
            "added_from_anomaly_manual";
    public static final String RESTRICTION_REASON_TYPE_ADD_MARS_AUTO = "added_from_mars_auto";
    public static final String RESTRICTION_REASON_TYPE_ADD_MARS_AUTO_SPECIFIC =
            "added_from_mars_auto_specific";
    public static final String RESTRICTION_REASON_TYPE_ADD_MARS_MANUAL_SPECIFIC =
            "added_from_mars_manual_specific";
    public static final String RESTRICTION_REASON_TYPE_ADD_POLICY_IN_CHINA =
            "added_from_policy_in_china";
    public static final String RESTRICTION_REASON_TYPE_ADD_PRE_O = "added_from_pre_o";
    public static final String RESTRICTION_REASON_TYPE_ADD_UNKNOWN = "added_from_unknown";
    public static final String RESTRICTION_REASON_TYPE_ADD_USER_MANUAL = "added_from_user_manual";
    public static final String RESTRICTION_REASON_TYPE_DEFAULT = "default";
    public static final String RESTRICTION_REASON_TYPE_DELETE_MARS_AUTO = "deleted_from_mars_auto";
    public static final String RESTRICTION_REASON_TYPE_DELETE_POLICY_IN_CHINA =
            "deleted_from_policy_in_china";
    public static final String RESTRICTION_REASON_TYPE_DELETE_POST_O = "deleted_from_post_o";
    public static final String RESTRICTION_REASON_TYPE_DELETE_UNKNOWN = "deleted_from_unknown";
    public static final String RESTRICTION_REASON_TYPE_DELETE_USER_MANUAL =
            "deleted_from_user_manual";
    public static final int RESTRICTION_STATE_NONE = 0;
    public static final int RESTRICTION_STATE_OFF = 2;
    public static final int RESTRICTION_STATE_ON = 1;
    public static final int RESTRICTION_TYPE_DISABLE = 0;
    public static final int RESTRICTION_TYPE_DISABLE_SPECIFIC = 4;
    public static final int RESTRICTION_TYPE_DISABLE_WITHIN_24_HOUR = 2;
    public static final int RESTRICTION_TYPE_NEVER_SLEEP = 3;
    public static final int RESTRICTION_TYPE_SLEEP = 1;
    private Context mContext;

    public RestrictionInfo getRestrictionInfo(int type, String packageName, int uid) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.getRestrictionInfo(type, packageName, uid);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean canRestrict(int type, String packageName, int uid) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.canRestrict(type, packageName, uid);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restrict(int type, int state, boolean byUser, String packageName, int uid) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.restrict(type, state, byUser, packageName, uid);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AppRestrictionInfo> getRestrictableList(int type) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.getRestrictableList(type);
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
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.getAllRestrictedList();
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AppRestrictionInfo> getRestrictedList(int type) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.getRestrictedList(type);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRestrictionInfo(RestrictionInfo info, List<AppRestrictionInfo> list) {
        try {
            checkPermission(Manifest.permission.SEM_APP_RESTRICTION);
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.updateRestrictionInfo(info, list);
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
            IActivityManager activityManager = ActivityManager.getService();
            if (activityManager != null) {
                return activityManager.clearRestrictionInfo(list);
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
        this.mContext = null;
        this.mContext = context;
    }

    protected SemAppRestrictionManager(Parcel in) {
        this.mContext = null;
    }

    private void checkPermission(String permission) {
        if (this.mContext != null && this.mContext.checkSelfPermission(permission) == -1) {
            throw new SecurityException("Must have permission " + permission);
        }
        int uid = Binder.getCallingUid();
        if (1000 != UserHandle.getAppId(uid)
                && ActivityManager.checkUidPermission(permission, uid) == -1) {
            throw new SecurityException("Must have permission " + permission);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {}

    public static class RestrictionInfo implements Parcelable {
        public static final Parcelable.Creator<RestrictionInfo> CREATOR =
                new Parcelable.Creator<
                        RestrictionInfo>() { // from class:
                                             // com.samsung.android.sdhms.SemAppRestrictionManager.RestrictionInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public RestrictionInfo createFromParcel(Parcel in) {
                        return new RestrictionInfo(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public RestrictionInfo[] newArray(int size) {
                        return new RestrictionInfo[size];
                    }
                };
        private boolean byUser;
        private String reason;
        private int state;
        private int type;

        public RestrictionInfo(int type, int state, String reason) {
            this.type = -1;
            this.state = -1;
            this.byUser = false;
            this.reason = null;
            this.type = type;
            this.state = state;
            this.reason = reason;
            this.byUser = getChangedByUserFromReason(reason);
        }

        public boolean getChangedByUserFromReason(String reason) {
            if (SemAppRestrictionManager.RESTRICTION_REASON_TYPE_ADD_USER_MANUAL.equals(reason)
                    || SemAppRestrictionManager.RESTRICTION_REASON_TYPE_ADD_ANOMALY_MANUAL.equals(
                            reason)
                    || SemAppRestrictionManager.RESTRICTION_REASON_TYPE_DELETE_USER_MANUAL.equals(
                            reason)
                    || "default".equals(reason)) {
                return true;
            }
            return false;
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

        protected RestrictionInfo(Parcel in) {
            this.type = -1;
            this.state = -1;
            this.byUser = false;
            this.reason = null;
            this.type = in.readInt();
            this.state = in.readInt();
            this.reason = in.readString();
            this.byUser = in.readBoolean();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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
        public static final Parcelable.Creator<AppRestrictionInfo> CREATOR =
                new Parcelable.Creator<
                        AppRestrictionInfo>() { // from class:
                                                // com.samsung.android.sdhms.SemAppRestrictionManager.AppRestrictionInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AppRestrictionInfo createFromParcel(Parcel in) {
                        return new AppRestrictionInfo(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AppRestrictionInfo[] newArray(int size) {
                        return new AppRestrictionInfo[size];
                    }
                };
        private String packageName;
        private RestrictionInfo restrictionInfo;
        private int uid;

        public AppRestrictionInfo(String packageName, int uid) {
            this.packageName = null;
            this.uid = -1;
            this.restrictionInfo = null;
            this.packageName = packageName;
            this.uid = uid;
        }

        public AppRestrictionInfo(String packageName, int uid, RestrictionInfo info) {
            this.packageName = null;
            this.uid = -1;
            this.restrictionInfo = null;
            this.packageName = packageName;
            this.uid = uid;
            this.restrictionInfo = info;
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

        protected AppRestrictionInfo(Parcel in) {
            this.packageName = null;
            this.uid = -1;
            this.restrictionInfo = null;
            this.packageName = in.readString();
            this.uid = in.readInt();
            this.restrictionInfo =
                    (RestrictionInfo) in.readParcelable(RestrictionInfo.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.packageName);
            parcel.writeInt(this.uid);
            parcel.writeParcelable(this.restrictionInfo, i);
        }
    }
}
