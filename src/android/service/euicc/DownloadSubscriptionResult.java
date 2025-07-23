package android.service.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public final class DownloadSubscriptionResult implements Parcelable {
    public static final Parcelable.Creator<DownloadSubscriptionResult> CREATOR = new Parcelable.Creator<DownloadSubscriptionResult>() { // from class: android.service.euicc.DownloadSubscriptionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadSubscriptionResult createFromParcel(Parcel parcel) {
            return new DownloadSubscriptionResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadSubscriptionResult[] newArray(int i) {
            return new DownloadSubscriptionResult[i];
        }
    };
    private final int mCardId;
    private final int mResolvableErrors;
    private final int mResult;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DownloadSubscriptionResult(int i, int i2, int i3) {
        this.mResult = i;
        this.mResolvableErrors = i2;
        this.mCardId = i3;
    }

    public int getResult() {
        return this.mResult;
    }

    public int getResolvableErrors() {
        return this.mResolvableErrors;
    }

    public int getCardId() {
        return this.mCardId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mResolvableErrors);
        parcel.writeInt(this.mCardId);
    }

    private DownloadSubscriptionResult(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mResolvableErrors = parcel.readInt();
        this.mCardId = parcel.readInt();
    }
}
