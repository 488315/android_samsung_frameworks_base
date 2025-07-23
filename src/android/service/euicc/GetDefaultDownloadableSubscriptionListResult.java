package android.service.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.euicc.DownloadableSubscription;
import java.util.Arrays;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class GetDefaultDownloadableSubscriptionListResult implements Parcelable {
    public static final Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult> CREATOR = new Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult>() { // from class: android.service.euicc.GetDefaultDownloadableSubscriptionListResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetDefaultDownloadableSubscriptionListResult createFromParcel(Parcel parcel) {
            return new GetDefaultDownloadableSubscriptionListResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetDefaultDownloadableSubscriptionListResult[] newArray(int i) {
            return new GetDefaultDownloadableSubscriptionListResult[i];
        }
    };
    private final DownloadableSubscription[] mSubscriptions;

    @Deprecated
    public final int result;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getResult() {
        return this.result;
    }

    public List<DownloadableSubscription> getDownloadableSubscriptions() {
        DownloadableSubscription[] downloadableSubscriptionArr = this.mSubscriptions;
        if (downloadableSubscriptionArr == null) {
            return null;
        }
        return Arrays.asList(downloadableSubscriptionArr);
    }

    public GetDefaultDownloadableSubscriptionListResult(int i, DownloadableSubscription[] downloadableSubscriptionArr) {
        this.result = i;
        if (i == 0) {
            this.mSubscriptions = downloadableSubscriptionArr;
        } else {
            if (downloadableSubscriptionArr != null) {
                throw new IllegalArgumentException("Error result with non-null subscriptions: " + i);
            }
            this.mSubscriptions = null;
        }
    }

    private GetDefaultDownloadableSubscriptionListResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.mSubscriptions = (DownloadableSubscription[]) parcel.createTypedArray(DownloadableSubscription.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeTypedArray(this.mSubscriptions, i);
    }
}
