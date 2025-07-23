package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.CallLog;
import com.samsung.android.sdk.moneta.memory.entity.context.Person;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CallLogWrapperV1 extends ContentWrapper {
    private final long callId;
    private final String contentUri;
    private final String id;
    private final Person senderOrRecipient;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<CallLogWrapperV1> CREATOR = new Creator();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new CallLogWrapperV1(parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readInt() == 0 ? null : Person.CREATOR.createFromParcel(parcel));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CallLogWrapperV1[i];
        }
    }

    public CallLogWrapperV1(String str, String str2, long j, Person person) {
        this.id = str;
        this.contentUri = str2;
        this.callId = j;
        this.senderOrRecipient = person;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final long getCallId() {
        return this.callId;
    }

    public final String getContentUri() {
        return this.contentUri;
    }

    public final String getId() {
        return this.id;
    }

    public final Person getSenderOrRecipient() {
        return this.senderOrRecipient;
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

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public CallLog toContent() {
        return new CallLog(this.id, this.contentUri, this.callId, this.senderOrRecipient);
    }
}
