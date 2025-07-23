package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Account implements Parcelable {
    public static final String COMPATIBILITY_UUID = "compatibilityUuid";
    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() { // from class: com.samsung.android.knox.accounts.Account.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Account createFromParcel(Parcel parcel) {
            return new Account(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Account[] newArray(int i) {
            return new Account[i];
        }
    };
    public static final String DISPLAY_NAME = "displayName";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String FLAGS = "flags";
    public static final int FLAGS_NOTIFY_NEW_MAIL = 1;
    public static final int FLAGS_VIBRATE_ALWAYS = 2;
    public static final int FLAGS_VIBRATE_WHEN_SILENT = 64;
    public static final String HOST_AUTH_KEY_RECV = "hostAuthKeyRecv";
    public static final String HOST_AUTH_KEY_SEND = "hostAuthKeySend";
    public static final String ID = "_id";
    public static final String IS_DEFAULT = "isDefault";
    public static final String NEW_MESSAGE_COUNT = "newMessageCount";
    public static final String PROTOCOL_VERSION = "protocolVersion";
    public static final String RINGTONE_URI = "ringtoneUri";
    public static final String SECURITY_FLAGS = "securityFlags";
    public static final String SECURITY_SYNC_KEY = "securitySyncKey";
    public static final String SENDER_NAME = "senderName";
    public static final String SIGNATURE = "signature";
    public static final String SYNC_INTERVAL = "syncInterval";
    public static final String SYNC_KEY = "syncKey";
    public static final String SYNC_LOOKBACK = "syncLookback";
    public String compatibilityUuid;
    public String displayName;
    public String emailAddress;
    public int emailBodyTruncationSize;
    public boolean emailNotificationVibrateAlways;
    public boolean emailNotificationVibrateWhenSilent;
    public int emailRoamingBodyTruncationSize;
    public int flags;
    public long hostAuthKeyRecv;
    public long hostAuthKeySend;
    public HostAuth hostAuthRecv;
    public HostAuth hostAuthSend;
    public int id;
    public boolean isDefault;
    public int newMessageCount;
    public int offPeakSyncSchedule;
    public int peakDays;
    public int peakEndMinute;
    public int peakStartMinute;
    public int peakSyncSchedule;
    public String protocolVersion;
    public String ringtoneUri;
    public int roamingSyncSchedule;
    public int securityFlags;
    public String securitySyncKey;
    public String senderName;
    public String signature;
    public boolean syncCalendar;
    public int syncCalendarAge;
    public boolean syncContacts;
    public int syncInterval;
    public String syncKey;
    public int syncLookback;
    public boolean syncNotes;
    public boolean syncTasks;

    public /* synthetic */ Account(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readInt();
        this.displayName = parcel.readString();
        this.emailAddress = parcel.readString();
        this.syncKey = parcel.readString();
        this.syncLookback = parcel.readInt();
        this.syncInterval = parcel.readInt();
        this.hostAuthKeyRecv = parcel.readLong();
        this.hostAuthKeySend = parcel.readLong();
        this.flags = parcel.readInt();
        this.emailNotificationVibrateAlways = parcel.readInt() != 0;
        this.emailNotificationVibrateWhenSilent = parcel.readInt() != 0;
        this.isDefault = parcel.readInt() != 0;
        this.compatibilityUuid = parcel.readString();
        this.senderName = parcel.readString();
        this.ringtoneUri = parcel.readString();
        this.protocolVersion = parcel.readString();
        this.newMessageCount = parcel.readInt();
        this.securityFlags = parcel.readInt();
        this.securitySyncKey = parcel.readString();
        this.signature = parcel.readString();
        this.peakDays = parcel.readInt();
        this.peakStartMinute = parcel.readInt();
        this.peakEndMinute = parcel.readInt();
        this.peakSyncSchedule = parcel.readInt();
        this.offPeakSyncSchedule = parcel.readInt();
        this.roamingSyncSchedule = parcel.readInt();
        this.syncCalendarAge = parcel.readInt();
        this.emailBodyTruncationSize = parcel.readInt();
        this.emailRoamingBodyTruncationSize = parcel.readInt();
        this.syncCalendar = parcel.readInt() != 0;
        this.syncContacts = parcel.readInt() != 0;
        this.syncTasks = parcel.readInt() != 0;
        this.syncNotes = parcel.readInt() != 0;
        Parcelable.Creator<HostAuth> creator = HostAuth.CREATOR;
        this.hostAuthRecv = creator.createFromParcel(parcel);
        this.hostAuthSend = creator.createFromParcel(parcel);
    }

    public String toString() {
        return "_id=" + this.id + " displayName=" + this.displayName + " emailAddress=" + this.emailAddress + " syncKey=" + this.syncKey + " syncLookback=" + this.syncLookback + " syncInterval=" + this.syncInterval + " hostAuthKeyRecv=" + this.hostAuthKeyRecv + " hostAuthKeySend=" + this.hostAuthKeySend + " emailNotificationVibrateAlways = " + this.emailNotificationVibrateAlways + " isDefault=" + this.isDefault + " compatibilityUuid=" + this.compatibilityUuid + " senderName=" + this.senderName + " ringtoneUri=" + this.ringtoneUri + " protocolVersion=" + this.protocolVersion + " newMessageCount=" + this.newMessageCount + " securityFlags=" + this.securityFlags + " securitySyncKey=" + this.securitySyncKey + " signature=" + this.signature + "\nHOST_AUTH_RECV=" + this.hostAuthRecv + "\nHOST_AUTH_SEND=" + this.hostAuthSend;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.displayName);
        parcel.writeString(this.emailAddress);
        parcel.writeString(this.syncKey);
        parcel.writeInt(this.syncLookback);
        parcel.writeInt(this.syncInterval);
        parcel.writeLong(this.hostAuthKeyRecv);
        parcel.writeLong(this.hostAuthKeySend);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.emailNotificationVibrateAlways ? 1 : 0);
        parcel.writeInt(this.emailNotificationVibrateWhenSilent ? 1 : 0);
        parcel.writeInt(this.isDefault ? 1 : 0);
        parcel.writeString(this.compatibilityUuid);
        parcel.writeString(this.senderName);
        parcel.writeString(this.ringtoneUri);
        parcel.writeString(this.protocolVersion);
        parcel.writeInt(this.newMessageCount);
        parcel.writeInt(this.securityFlags);
        parcel.writeString(this.securitySyncKey);
        parcel.writeString(this.signature);
        parcel.writeInt(this.peakDays);
        parcel.writeInt(this.peakStartMinute);
        parcel.writeInt(this.peakEndMinute);
        parcel.writeInt(this.peakSyncSchedule);
        parcel.writeInt(this.offPeakSyncSchedule);
        parcel.writeInt(this.roamingSyncSchedule);
        parcel.writeInt(this.syncCalendarAge);
        parcel.writeInt(this.emailBodyTruncationSize);
        parcel.writeInt(this.emailRoamingBodyTruncationSize);
        parcel.writeInt(this.syncCalendar ? 1 : 0);
        parcel.writeInt(this.syncContacts ? 1 : 0);
        parcel.writeInt(this.syncTasks ? 1 : 0);
        parcel.writeInt(this.syncNotes ? 1 : 0);
        this.hostAuthRecv.writeToParcel(parcel, i);
        this.hostAuthSend.writeToParcel(parcel, i);
    }

    public Account() {
        this.emailNotificationVibrateAlways = false;
        this.emailNotificationVibrateWhenSilent = false;
    }

    private Account(Parcel parcel) {
        this.emailNotificationVibrateAlways = false;
        this.emailNotificationVibrateWhenSilent = false;
        readFromParcel(parcel);
    }
}
