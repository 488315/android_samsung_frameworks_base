package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.Message;
import com.samsung.android.sdk.moneta.memory.entity.context.Person;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class MessageWrapperV1 extends ContentWrapper {
    private final String contentUri;
    private final String id;
    private final long messageId;
    private final Person senderOrRecipient;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<MessageWrapperV1> CREATOR = new Creator();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MessageWrapperV1(parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readInt() == 0 ? null : Person.CREATOR.createFromParcel(parcel));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MessageWrapperV1[i];
        }
    }

    public MessageWrapperV1(String str, String str2, long j, Person person) {
        this.id = str;
        this.contentUri = str2;
        this.messageId = j;
        this.senderOrRecipient = person;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getContentUri() {
        return this.contentUri;
    }

    public final String getId() {
        return this.id;
    }

    public final long getMessageId() {
        return this.messageId;
    }

    public final Person getSenderOrRecipient() {
        return this.senderOrRecipient;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.contentUri);
        parcel.writeLong(this.messageId);
        Person person = this.senderOrRecipient;
        if (person == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            person.writeToParcel(parcel, i);
        }
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public Message toContent() {
        return new Message(this.id, this.contentUri, this.messageId, this.senderOrRecipient);
    }
}
