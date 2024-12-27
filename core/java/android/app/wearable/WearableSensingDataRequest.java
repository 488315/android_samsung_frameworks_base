package android.app.wearable;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

import java.time.Duration;

@SystemApi
public final class WearableSensingDataRequest implements Parcelable {
    private static final int MAX_REQUEST_SIZE = 200;
    private static final int RATE_LIMIT = 30;
    public static final String REQUEST_BUNDLE_KEY =
            "android.app.wearable.WearableSensingDataRequestBundleKey";
    public static final String REQUEST_STATUS_CALLBACK_BUNDLE_KEY =
            "android.app.wearable.WearableSensingDataRequestStatusCallbackBundleKey";
    private final int mDataType;
    private final PersistableBundle mRequestDetails;
    private static final Duration RATE_LIMIT_WINDOW_SIZE = Duration.ofMinutes(1);
    public static final Parcelable.Creator<WearableSensingDataRequest> CREATOR =
            new Parcelable.Creator<WearableSensingDataRequest>() { // from class:
                // android.app.wearable.WearableSensingDataRequest.1
                @Override // android.os.Parcelable.Creator
                public WearableSensingDataRequest[] newArray(int size) {
                    return new WearableSensingDataRequest[size];
                }

                @Override // android.os.Parcelable.Creator
                public WearableSensingDataRequest createFromParcel(Parcel in) {
                    int dataType = in.readInt();
                    PersistableBundle requestDetails =
                            (PersistableBundle) in.readTypedObject(PersistableBundle.CREATOR);
                    return new WearableSensingDataRequest(dataType, requestDetails);
                }
            };

    private WearableSensingDataRequest(int dataType, PersistableBundle requestDetails) {
        this.mDataType = dataType;
        this.mRequestDetails = requestDetails;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public PersistableBundle getRequestDetails() {
        return this.mRequestDetails;
    }

    public int getDataSize() {
        Parcel parcel = Parcel.obtain();
        try {
            writeToParcel(parcel, describeContents());
            return parcel.dataSize();
        } finally {
            parcel.recycle();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mDataType);
        dest.writeTypedObject(this.mRequestDetails, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "WearableSensingDataRequest { dataType = "
                + this.mDataType
                + ", requestDetails = "
                + this.mRequestDetails
                + " }";
    }

    public String toExpandedString() {
        if (this.mRequestDetails != null) {
            this.mRequestDetails.getBoolean(
                    "PlaceholderForWearableSensingDataRequest#toExpandedString()");
        }
        return toString();
    }

    public static int getMaxRequestSize() {
        return 200;
    }

    public static Duration getRateLimitWindowSize() {
        return RATE_LIMIT_WINDOW_SIZE;
    }

    public static int getRateLimit() {
        return 30;
    }

    public static final class Builder {
        private int mDataType;
        private PersistableBundle mRequestDetails;

        public Builder(int dataType) {
            this.mDataType = dataType;
        }

        public Builder setRequestDetails(PersistableBundle requestDetails) {
            this.mRequestDetails = requestDetails;
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
