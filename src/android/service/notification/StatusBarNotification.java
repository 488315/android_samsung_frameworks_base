package android.service.notification;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.pm.PackageManager;
import android.metrics.LogMaker;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.NtpTrustedTime;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.nano.MetricsProto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class StatusBarNotification implements Parcelable {
    public static final Parcelable.Creator<StatusBarNotification> CREATOR = new Parcelable.Creator<StatusBarNotification>() { // from class: android.service.notification.StatusBarNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusBarNotification createFromParcel(Parcel parcel) {
            return new StatusBarNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusBarNotification[] newArray(int i) {
            return new StatusBarNotification[i];
        }
    };
    static final int MAX_LOG_TAG_LENGTH = 36;
    private String groupKey;
    private final int id;
    private final int initialPid;
    private final String key;

    @Deprecated
    private Context mContext;
    private final Map<Integer, Context> mContextForDisplayId = Collections.synchronizedMap(new ArrayMap());
    private InstanceId mInstanceId;
    private final Notification notification;
    private final String opPkg;
    private String overrideGroupKey;
    private final String pkg;
    private final long postTime;
    private final String tag;
    private final int uid;
    private final UserHandle user;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StatusBarNotification(String str, String str2, int i, String str3, int i2, int i3, Notification notification, UserHandle userHandle, String str4, long j) {
        str.getClass();
        notification.getClass();
        this.pkg = str;
        this.opPkg = str2;
        this.id = i;
        this.tag = str3;
        this.uid = i2;
        this.initialPid = i3;
        this.notification = notification;
        this.user = userHandle;
        this.postTime = j;
        this.overrideGroupKey = str4;
        this.key = key();
        this.groupKey = groupKey();
    }

    @Deprecated
    public StatusBarNotification(String str, String str2, int i, String str3, int i2, int i3, int i4, Notification notification, UserHandle userHandle, long j) {
        str.getClass();
        notification.getClass();
        this.pkg = str;
        this.opPkg = str2;
        this.id = i;
        this.tag = str3;
        this.uid = i2;
        this.initialPid = i3;
        this.notification = notification;
        this.user = userHandle;
        this.postTime = j;
        this.key = key();
        this.groupKey = groupKey();
    }

    public StatusBarNotification(Parcel parcel) {
        this.pkg = parcel.readString();
        this.opPkg = parcel.readString();
        this.id = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.tag = parcel.readString();
        } else {
            this.tag = null;
        }
        this.uid = parcel.readInt();
        this.initialPid = parcel.readInt();
        this.notification = new Notification(parcel);
        this.user = UserHandle.readFromParcel(parcel);
        this.postTime = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.overrideGroupKey = parcel.readString();
        }
        if (parcel.readInt() != 0) {
            this.mInstanceId = InstanceId.CREATOR.createFromParcel(parcel);
        }
        this.key = key();
        this.groupKey = groupKey();
    }

    public static int getUidFromKey(String str) {
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length >= 5) {
            try {
                return Integer.parseInt(strArrSplit[4]);
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public static String getPkgFromKey(String str) {
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length >= 2) {
            return strArrSplit[1];
        }
        return null;
    }

    private String key() {
        String str = this.user.getIdentifier() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.pkg + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.id + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.tag + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.uid;
        if (this.overrideGroupKey == null || !getNotification().isGroupSummary()) {
            return str;
        }
        return str + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.overrideGroupKey;
    }

    private String groupKey() {
        String str;
        if (this.overrideGroupKey != null) {
            if (Flags.notificationForceGrouping()) {
                return this.overrideGroupKey;
            }
            return this.user.getIdentifier() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.pkg + "|g:" + this.overrideGroupKey;
        }
        String group = getNotification().getGroup();
        String sortKey = getNotification().getSortKey();
        if (group == null && sortKey == null) {
            return this.key;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.user.getIdentifier());
        sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        sb.append(this.pkg);
        sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if (group == null) {
            str = "c:" + this.notification.getChannelId();
        } else {
            str = "g:" + group;
        }
        sb.append(str);
        return sb.toString();
    }

    public boolean isGroup() {
        return this.overrideGroupKey != null || isAppGroup();
    }

    public boolean isAppGroup() {
        return (getNotification().getGroup() == null && getNotification().getSortKey() == null) ? false : true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pkg);
        parcel.writeString(this.opPkg);
        parcel.writeInt(this.id);
        if (this.tag != null) {
            parcel.writeInt(1);
            parcel.writeString(this.tag);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.uid);
        parcel.writeInt(this.initialPid);
        this.notification.writeToParcel(parcel, i);
        this.user.writeToParcel(parcel, i);
        parcel.writeLong(this.postTime);
        if (this.overrideGroupKey != null) {
            parcel.writeInt(1);
            parcel.writeString(this.overrideGroupKey);
        } else {
            parcel.writeInt(0);
        }
        if (this.mInstanceId != null) {
            parcel.writeInt(1);
            this.mInstanceId.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }

    public StatusBarNotification cloneLight() {
        Notification notification = new Notification();
        this.notification.cloneInto(notification, false);
        return cloneShallow(notification);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public StatusBarNotification m4263clone() {
        return cloneShallow(this.notification.m425clone());
    }

    public StatusBarNotification cloneShallow(Notification notification) {
        StatusBarNotification statusBarNotification = new StatusBarNotification(this.pkg, this.opPkg, this.id, this.tag, this.uid, this.initialPid, notification, this.user, this.overrideGroupKey, this.postTime);
        statusBarNotification.setInstanceId(this.mInstanceId);
        return statusBarNotification;
    }

    public String toString() {
        return TextUtils.formatSimple("StatusBarNotification(pkg=%s user=%s id=%d tag=%s key=%s: %s)", this.pkg, this.user, Integer.valueOf(this.id), this.tag, this.key, this.notification);
    }

    public boolean isOngoing() {
        return (this.notification.flags & 2) != 0;
    }

    public boolean isNonDismissable() {
        return (this.notification.flags & 8192) != 0;
    }

    public boolean isClearable() {
        return (this.notification.flags & 2) == 0 && (this.notification.flags & 32) == 0;
    }

    @Deprecated
    public int getUserId() {
        return this.user.getIdentifier();
    }

    public int getNormalizedUserId() {
        int userId = getUserId();
        if (userId == -1) {
            return 0;
        }
        return userId;
    }

    public String getPackageName() {
        return this.pkg;
    }

    public int getId() {
        return this.id;
    }

    public String getTag() {
        return this.tag;
    }

    public int getUid() {
        return this.uid;
    }

    public String getOpPkg() {
        return this.opPkg;
    }

    public int getInitialPid() {
        return this.initialPid;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public UserHandle getUser() {
        return this.user;
    }

    public long getPostTime() {
        return this.postTime;
    }

    public String getKey() {
        return this.key;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public String getGroup() {
        String str = this.overrideGroupKey;
        return str != null ? str : getNotification().getGroup();
    }

    public void setOverrideGroupKey(String str) {
        this.overrideGroupKey = str;
        this.groupKey = groupKey();
    }

    public String getOverrideGroupKey() {
        return this.overrideGroupKey;
    }

    public void clearPackageContext() {
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.enablePerDisplayPackageContextCacheInStatusbarNotif()) {
            this.mContextForDisplayId.clear();
        } else {
            this.mContext = null;
        }
    }

    public InstanceId getInstanceId() {
        return this.mInstanceId;
    }

    public void setInstanceId(InstanceId instanceId) {
        this.mInstanceId = instanceId;
    }

    public Context getPackageContext(final Context context) {
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.enablePerDisplayPackageContextCacheInStatusbarNotif()) {
            if (context == null) {
                return null;
            }
            return this.mContextForDisplayId.computeIfAbsent(Integer.valueOf(context.getDisplayId()), new Function() { // from class: android.service.notification.StatusBarNotification$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.lambda$getPackageContext$0(context, (Integer) obj);
                }
            });
        }
        if (this.mContext == null) {
            try {
                this.mContext = context.createApplicationContext(context.getPackageManager().getApplicationInfoAsUser(this.pkg, 8192, getNormalizedUserId()), 4);
            } catch (PackageManager.NameNotFoundException unused) {
                this.mContext = null;
            }
        }
        if (this.mContext == null) {
            this.mContext = context;
        }
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Context lambda$getPackageContext$0(Context context, Integer num) {
        return createPackageContext(context);
    }

    private Context createPackageContext(Context context) {
        try {
            Trace.beginSection("StatusBarNotification#createPackageContext");
            return context.createApplicationContext(context.getPackageManager().getApplicationInfoAsUser(this.pkg, 8192, getNormalizedUserId()), 4);
        } catch (PackageManager.NameNotFoundException unused) {
            return context;
        } finally {
            Trace.endSection();
        }
    }

    public LogMaker getLogMaker() {
        LogMaker logMakerAddTaggedData = new LogMaker(0).setPackageName(getPackageName()).addTaggedData(MetricsProto.MetricsEvent.NOTIFICATION_ID, Integer.valueOf(getId())).addTaggedData(MetricsProto.MetricsEvent.NOTIFICATION_TAG, getTag()).addTaggedData(MetricsProto.MetricsEvent.FIELD_NOTIFICATION_CHANNEL_ID, getChannelIdLogTag()).addTaggedData(946, getGroupLogTag()).addTaggedData(947, Integer.valueOf(getNotification().isGroupSummary() ? 1 : 0)).addTaggedData(MetricsProto.MetricsEvent.FIELD_NOTIFICATION_CATEGORY, getNotification().category);
        if (getNotification().extras != null) {
            String string = getNotification().extras.getString(Notification.EXTRA_TEMPLATE);
            if (string != null && !string.isEmpty()) {
                logMakerAddTaggedData.addTaggedData(1745, Integer.valueOf(string.hashCode()));
            }
            ArrayList parcelableArrayList = getNotification().extras.getParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, Person.class);
            if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                logMakerAddTaggedData.addTaggedData(1744, Integer.valueOf(parcelableArrayList.size()));
            }
        }
        return logMakerAddTaggedData;
    }

    public String getShortcutId() {
        return getNotification().getShortcutId();
    }

    public String getGroupLogTag() {
        return shortenTag(getGroup());
    }

    public String getChannelIdLogTag() {
        if (this.notification.getChannelId() == null) {
            return null;
        }
        return shortenTag(this.notification.getChannelId());
    }

    private String shortenTag(String str) {
        if (str == null || str.length() <= 36) {
            return str;
        }
        String hexString = Integer.toHexString(str.hashCode());
        return str.substring(0, 35 - hexString.length()) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + hexString;
    }
}
