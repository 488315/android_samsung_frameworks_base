package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class DataThrottlingRequest implements Parcelable {
    public static final Parcelable.Creator<DataThrottlingRequest> CREATOR = new Parcelable.Creator<DataThrottlingRequest>() { // from class: android.telephony.DataThrottlingRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataThrottlingRequest createFromParcel(Parcel parcel) {
            return new DataThrottlingRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataThrottlingRequest[] newArray(int i) {
            return new DataThrottlingRequest[i];
        }
    };

    @SystemApi
    public static final int DATA_THROTTLING_ACTION_HOLD = 3;

    @SystemApi
    public static final int DATA_THROTTLING_ACTION_NO_DATA_THROTTLING = 0;

    @SystemApi
    public static final int DATA_THROTTLING_ACTION_THROTTLE_PRIMARY_CARRIER = 2;

    @SystemApi
    public static final int DATA_THROTTLING_ACTION_THROTTLE_SECONDARY_CARRIER = 1;
    private long mCompletionDurationMillis;
    private int mDataThrottlingAction;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataThrottlingAction {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DataThrottlingRequest(int i, long j) {
        this.mDataThrottlingAction = i;
        this.mCompletionDurationMillis = j;
    }

    private DataThrottlingRequest(Parcel parcel) {
        this.mDataThrottlingAction = parcel.readInt();
        this.mCompletionDurationMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDataThrottlingAction);
        parcel.writeLong(this.mCompletionDurationMillis);
    }

    public String toString() {
        return "[DataThrottlingRequest , DataThrottlingAction=" + this.mDataThrottlingAction + ", completionDurationMillis=" + this.mCompletionDurationMillis + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int getDataThrottlingAction() {
        return this.mDataThrottlingAction;
    }

    public long getCompletionDurationMillis() {
        return this.mCompletionDurationMillis;
    }

    @SystemApi
    public static final class Builder {
        private long mCompletionDurationMillis;
        private int mDataThrottlingAction;

        public Builder setDataThrottlingAction(int i) {
            this.mDataThrottlingAction = i;
            return this;
        }

        public Builder setCompletionDurationMillis(long j) {
            this.mCompletionDurationMillis = j;
            return this;
        }

        public DataThrottlingRequest build() {
            long j = this.mCompletionDurationMillis;
            if (j < 0) {
                throw new IllegalArgumentException("completionDurationMillis cannot be a negative number");
            }
            if (this.mDataThrottlingAction == 3 && j != 0) {
                throw new IllegalArgumentException("completionDurationMillis must be 0 for DataThrottlingRequest.DATA_THROTTLING_ACTION_HOLD");
            }
            return new DataThrottlingRequest(this.mDataThrottlingAction, this.mCompletionDurationMillis);
        }
    }
}
