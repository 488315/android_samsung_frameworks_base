package com.samsung.android.sdk.moneta.preference.entity.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.preference.entity.ContactChannel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class FrequentContactRequestOption implements Parcelable {
    public static final Parcelable.Creator<FrequentContactRequestOption> CREATOR = new Creator();
    private final ContactChannel contactChannel;
    private final String myPhoneNumber;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new FrequentContactRequestOption(parcel.readInt() == 0 ? null : ContactChannel.valueOf(parcel.readString()), parcel.readString(), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new FrequentContactRequestOption[i];
        }
    }

    public /* synthetic */ FrequentContactRequestOption(ContactChannel contactChannel, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(contactChannel, str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final ContactChannel getContactChannel() {
        return this.contactChannel;
    }

    public final String getMyPhoneNumber() {
        return this.myPhoneNumber;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ContactChannel contactChannel = this.contactChannel;
        if (contactChannel == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(contactChannel.name());
        }
        parcel.writeString(this.myPhoneNumber);
    }

    private FrequentContactRequestOption(ContactChannel contactChannel, String str) {
        this.contactChannel = contactChannel;
        this.myPhoneNumber = str;
    }

    public /* synthetic */ FrequentContactRequestOption(ContactChannel contactChannel, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : contactChannel, (i & 2) != 0 ? null : str);
    }
}
