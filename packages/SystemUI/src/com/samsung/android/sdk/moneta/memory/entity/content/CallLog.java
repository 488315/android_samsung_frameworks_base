package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.context.Person;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CallLog extends Content {
    public static final Parcelable.Creator<CallLog> CREATOR = new Creator();
    private final long callId;
    private final String contentUri;
    private final String id;
    private final Person senderOrRecipient;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new CallLog(parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readInt() == 0 ? null : Person.CREATOR.createFromParcel(parcel));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CallLog[i];
        }
    }

    public CallLog(String str, String str2, long j, Person person) {
        this.id = str;
        this.contentUri = str2;
        this.callId = j;
        this.senderOrRecipient = person;
    }

    public static /* synthetic */ CallLog copy$default(CallLog callLog, String str, String str2, long j, Person person, int i, Object obj) {
        if ((i & 1) != 0) {
            str = callLog.id;
        }
        if ((i & 2) != 0) {
            str2 = callLog.contentUri;
        }
        if ((i & 4) != 0) {
            j = callLog.callId;
        }
        if ((i & 8) != 0) {
            person = callLog.senderOrRecipient;
        }
        Person person2 = person;
        return callLog.copy(str, str2, j, person2);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.contentUri;
    }

    public final long component3() {
        return this.callId;
    }

    public final Person component4() {
        return this.senderOrRecipient;
    }

    public final CallLog copy(String str, String str2, long j, Person person) {
        return new CallLog(str, str2, j, person);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CallLog)) {
            return false;
        }
        CallLog callLog = (CallLog) obj;
        return Intrinsics.areEqual(this.id, callLog.id) && Intrinsics.areEqual(this.contentUri, callLog.contentUri) && this.callId == callLog.callId && Intrinsics.areEqual(this.senderOrRecipient, callLog.senderOrRecipient);
    }

    public final long getCallId() {
        return this.callId;
    }

    public final String getContentUri() {
        return this.contentUri;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.content.Content
    public String getId() {
        return this.id;
    }

    public final Person getSenderOrRecipient() {
        return this.senderOrRecipient;
    }

    public int hashCode() {
        int m = MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.contentUri), 31, this.callId);
        Person person = this.senderOrRecipient;
        return m + (person == null ? 0 : person.hashCode());
    }

    public String toString() {
        return "CallLog(id=" + this.id + ", contentUri=" + this.contentUri + ", callId=" + this.callId + ", senderOrRecipient=" + this.senderOrRecipient + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.contentUri);
        parcel.writeLong(this.callId);
        Person person = this.senderOrRecipient;
        if (person == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            person.writeToParcel(parcel, i);
        }
    }
}
