package android.credentials.selection;

import android.annotation.SystemApi;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class ProviderPendingIntentResponse implements Parcelable {
    public static final Parcelable.Creator<ProviderPendingIntentResponse> CREATOR = new Parcelable.Creator<ProviderPendingIntentResponse>() { // from class: android.credentials.selection.ProviderPendingIntentResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProviderPendingIntentResponse createFromParcel(Parcel parcel) {
            return new ProviderPendingIntentResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProviderPendingIntentResponse[] newArray(int i) {
            return new ProviderPendingIntentResponse[i];
        }
    };
    private final int mResultCode;
    private final Intent mResultData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ProviderPendingIntentResponse(int i, Intent intent) {
        this.mResultCode = i;
        this.mResultData = intent;
    }

    private ProviderPendingIntentResponse(Parcel parcel) {
        this.mResultCode = parcel.readInt();
        this.mResultData = (Intent) parcel.readTypedObject(Intent.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeTypedObject(this.mResultData, i);
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public Intent getResultData() {
        return this.mResultData;
    }
}
