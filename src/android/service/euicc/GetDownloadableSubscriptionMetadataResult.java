package android.service.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.euicc.DownloadableSubscription;

@SystemApi
/* loaded from: classes3.dex */
public final class GetDownloadableSubscriptionMetadataResult implements Parcelable {
    public static final Parcelable.Creator<GetDownloadableSubscriptionMetadataResult> CREATOR = new Parcelable.Creator<GetDownloadableSubscriptionMetadataResult>() { // from class: android.service.euicc.GetDownloadableSubscriptionMetadataResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetDownloadableSubscriptionMetadataResult createFromParcel(Parcel parcel) {
            return new GetDownloadableSubscriptionMetadataResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetDownloadableSubscriptionMetadataResult[] newArray(int i) {
            return new GetDownloadableSubscriptionMetadataResult[i];
        }
    };
    private final DownloadableSubscription mSubscription;

    @Deprecated
    public final int result;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getResult() {
        return this.result;
    }

    public DownloadableSubscription getDownloadableSubscription() {
        return this.mSubscription;
    }

    public GetDownloadableSubscriptionMetadataResult(int i, DownloadableSubscription downloadableSubscription) {
        this.result = i;
        if (i == 0) {
            this.mSubscription = downloadableSubscription;
        } else {
            if (downloadableSubscription != null) {
                throw new IllegalArgumentException("Error result with non-null subscription: " + i);
            }
            this.mSubscription = null;
        }
    }

    private GetDownloadableSubscriptionMetadataResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.mSubscription = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeTypedObject(this.mSubscription, i);
    }
}
