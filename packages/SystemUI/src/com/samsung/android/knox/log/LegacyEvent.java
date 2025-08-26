package com.samsung.android.knox.log;

import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditEvent;
import java.util.Optional;

/* loaded from: classes4.dex */
public class LegacyEvent implements AuditEvent, Parcelable {
    public String component;
    public int group;
    public String message;
    public boolean outcome;
    public String redactedMessage;
    public int severity;
    public int userId;
    public static final String TAG = "LegacyEvent";
    public static final Parcelable.Creator<LegacyEvent> CREATOR = new Parcelable.Creator<LegacyEvent>() { // from class: com.samsung.android.knox.log.LegacyEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LegacyEvent createFromParcel(Parcel parcel) {
            return new LegacyEvent(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LegacyEvent[] newArray(int i) {
            return new LegacyEvent[i];
        }
    };

    public /* synthetic */ LegacyEvent(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getComponent() {
        return this.component;
    }

    public int getGroup() {
        return this.group;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getOutcome() {
        return this.outcome;
    }

    public Optional<Integer> getPrivacy() {
        return Optional.empty();
    }

    public String getRedactedMessage() {
        return this.redactedMessage;
    }

    public int getSeverity() {
        return this.severity;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isPrivileged() {
        return false;
    }

    public final void readFromParcel(Parcel parcel) {
        this.userId = parcel.readInt();
        this.component = parcel.readString();
        this.group = parcel.readInt();
        this.message = parcel.readString();
        this.redactedMessage = parcel.readString();
        this.outcome = parcel.readBoolean();
        this.severity = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.userId);
        parcel.writeString(this.component);
        parcel.writeInt(this.group);
        parcel.writeString(this.message);
        parcel.writeString(this.redactedMessage);
        parcel.writeBoolean(this.outcome);
        parcel.writeInt(this.severity);
    }

    public LegacyEvent(Integer num, String str, Integer num2, String str2, String str3, Boolean bool, Integer num3) {
        this.userId = num != null ? num.intValue() : UserHandle.getUserId(Binder.getCallingUid());
        this.component = str;
        this.group = num2 != null ? num2.intValue() : 5;
        this.message = str2;
        this.redactedMessage = str3;
        this.outcome = bool != null ? bool.booleanValue() : false;
        this.severity = num3 != null ? num3.intValue() : 5;
    }

    private LegacyEvent(Parcel parcel) {
        readFromParcel(parcel);
    }
}
