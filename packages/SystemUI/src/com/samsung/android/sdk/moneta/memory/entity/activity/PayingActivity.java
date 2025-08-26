package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class PayingActivity extends Activity {
    public static final Parcelable.Creator<PayingActivity> CREATOR = new Creator();
    private final Double amount;
    private final List<Content> contents;
    private final String id;
    private final Place location;
    private final String merchantDisplayName;
    private final String paymentType;
    private final long timestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(PayingActivity.class, parcel, arrayList, iM, 1);
            }
            return new PayingActivity(string, arrayList, parcel.readLong(), parcel.readInt() == 0 ? null : Place.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? Double.valueOf(parcel.readDouble()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PayingActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PayingActivity(String str, List<? extends Content> list, long j, Place place, String str2, String str3, Double d) {
        super(ActivityType.Paying);
        this.id = str;
        this.contents = list;
        this.timestamp = j;
        this.location = place;
        this.paymentType = str2;
        this.merchantDisplayName = str3;
        this.amount = d;
    }

    public static /* synthetic */ PayingActivity copy$default(PayingActivity payingActivity, String str, List list, long j, Place place, String str2, String str3, Double d, int i, Object obj) {
        if ((i & 1) != 0) {
            str = payingActivity.id;
        }
        if ((i & 2) != 0) {
            list = payingActivity.contents;
        }
        if ((i & 4) != 0) {
            j = payingActivity.timestamp;
        }
        if ((i & 8) != 0) {
            place = payingActivity.location;
        }
        if ((i & 16) != 0) {
            str2 = payingActivity.paymentType;
        }
        if ((i & 32) != 0) {
            str3 = payingActivity.merchantDisplayName;
        }
        if ((i & 64) != 0) {
            d = payingActivity.amount;
        }
        long j2 = j;
        return payingActivity.copy(str, list, j2, place, str2, str3, d);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final long component3() {
        return this.timestamp;
    }

    public final Place component4() {
        return this.location;
    }

    public final String component5() {
        return this.paymentType;
    }

    public final String component6() {
        return this.merchantDisplayName;
    }

    public final Double component7() {
        return this.amount;
    }

    public final PayingActivity copy(String str, List<? extends Content> list, long j, Place place, String str2, String str3, Double d) {
        return new PayingActivity(str, list, j, place, str2, str3, d);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PayingActivity)) {
            return false;
        }
        PayingActivity payingActivity = (PayingActivity) obj;
        return Intrinsics.areEqual(this.id, payingActivity.id) && Intrinsics.areEqual(this.contents, payingActivity.contents) && this.timestamp == payingActivity.timestamp && Intrinsics.areEqual(this.location, payingActivity.location) && Intrinsics.areEqual(this.paymentType, payingActivity.paymentType) && Intrinsics.areEqual(this.merchantDisplayName, payingActivity.merchantDisplayName) && Intrinsics.areEqual(this.amount, payingActivity.amount);
    }

    public final Double getAmount() {
        return this.amount;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
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

    public int hashCode() {
        int iM = MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31, this.timestamp);
        Place place = this.location;
        int iHashCode = (iM + (place == null ? 0 : place.hashCode())) * 31;
        String str = this.paymentType;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.merchantDisplayName;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Double d = this.amount;
        return iHashCode3 + (d != null ? d.hashCode() : 0);
    }

    public String toString() {
        return "PayingActivity(id=" + this.id + ", contents=" + this.contents + ", timestamp=" + this.timestamp + ", location=" + this.location + ", paymentType=" + this.paymentType + ", merchantDisplayName=" + this.merchantDisplayName + ", amount=" + this.amount + ')';
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
}
