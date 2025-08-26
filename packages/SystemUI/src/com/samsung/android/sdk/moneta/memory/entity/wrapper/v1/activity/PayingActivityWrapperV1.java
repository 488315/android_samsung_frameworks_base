package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.activity;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.PayingActivity;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class PayingActivityWrapperV1 extends ActivityWrapper {
    private final Double amount;
    private final List<Content> contents;
    private final String id;
    private final Place location;
    private final String merchantDisplayName;
    private final String paymentType;
    private final long timestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<PayingActivityWrapperV1> CREATOR = new Creator();

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
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(PayingActivityWrapperV1.class, parcel, arrayList, iM, 1);
            }
            return new PayingActivityWrapperV1(string, arrayList, parcel.readLong(), parcel.readInt() == 0 ? null : Place.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? Double.valueOf(parcel.readDouble()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PayingActivityWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PayingActivityWrapperV1(String str, List<? extends Content> list, long j, Place place, String str2, String str3, Double d) {
        this.id = str;
        this.contents = list;
        this.timestamp = j;
        this.location = place;
        this.paymentType = str2;
        this.merchantDisplayName = str3;
        this.amount = d;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final Double getAmount() {
        return this.amount;
    }

    public final List<Content> getContents() {
        return this.contents;
    }

    public final String getId() {
        return this.id;
    }

    public final Place getLocation() {
        return this.location;
    }

    public final String getMerchantDisplayName() {
        return this.merchantDisplayName;
    }

    public final String getPaymentType() {
        return this.paymentType;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeLong(this.timestamp);
        Place place = this.location;
        if (place == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            place.writeToParcel(parcel, i);
        }
        parcel.writeString(this.paymentType);
        parcel.writeString(this.merchantDisplayName);
        Double d = this.amount;
        if (d == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d.doubleValue());
        }
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper
    public PayingActivity toActivity() {
        return new PayingActivity(this.id, this.contents, this.timestamp, this.location, this.paymentType, this.merchantDisplayName, this.amount);
    }
}
