package android.app.wearable;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.time.Duration;

@SystemApi
/* loaded from: classes.dex */
public final class WearableSensingDataRequest implements Parcelable {
    private static final int MAX_REQUEST_SIZE = 200;
    private static final int RATE_LIMIT = 30;
    public static final String REQUEST_BUNDLE_KEY = "android.app.wearable.WearableSensingDataRequestBundleKey";
    public static final String REQUEST_STATUS_CALLBACK_BUNDLE_KEY = "android.app.wearable.WearableSensingDataRequestStatusCallbackBundleKey";
    private final int mDataType;
    private final PersistableBundle mRequestDetails;
    private static final Duration RATE_LIMIT_WINDOW_SIZE = Duration.ofMinutes(1);
    public static final Parcelable.Creator<WearableSensingDataRequest> CREATOR = new Parcelable.Creator<WearableSensingDataRequest>() { // from class: android.app.wearable.WearableSensingDataRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WearableSensingDataRequest[] newArray(int i) {
            return new WearableSensingDataRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WearableSensingDataRequest createFromParcel(Parcel parcel) {
            return new WearableSensingDataRequest(parcel.readInt(), (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR));
        }
    };

    public static int getMaxRequestSize() {
        return 200;
    }

    public static int getRateLimit() {
        return 30;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private WearableSensingDataRequest(int i, PersistableBundle persistableBundle) {
        this.mDataType = i;
        this.mRequestDetails = persistableBundle;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public PersistableBundle getRequestDetails() {
        return this.mRequestDetails;
    }

    public int getDataSize() {
        Parcel parcelObtain = Parcel.obtain();
        try {
            writeToParcel(parcelObtain, describeContents());
            return parcelObtain.dataSize();
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDataType);
        parcel.writeTypedObject(this.mRequestDetails, i);
    }

    public String toString() {
        return "WearableSensingDataRequest { dataType = " + this.mDataType + ", requestDetails = " + this.mRequestDetails + " }";
    }

    public String toExpandedString() {
        PersistableBundle persistableBundle = this.mRequestDetails;
        if (persistableBundle != null) {
            persistableBundle.getBoolean("PlaceholderForWearableSensingDataRequest#toExpandedString()");
        }
        return toString();
    }

    public static Duration getRateLimitWindowSize() {
        return RATE_LIMIT_WINDOW_SIZE;
    }

    public static final class Builder {
        private int mDataType;
        private PersistableBundle mRequestDetails;

        public Builder(int i) {
            this.mDataType = i;
        }

        public Builder setRequestDetails(PersistableBundle persistableBundle) {
            this.mRequestDetails = persistableBundle;
            return this;
        }

        public WearableSensingDataRequest build() {
            if (this.mRequestDetails == null) {
                this.mRequestDetails = PersistableBundle.EMPTY;
            }
            return new WearableSensingDataRequest(this.mDataType, this.mRequestDetails);
        }
    }
}
