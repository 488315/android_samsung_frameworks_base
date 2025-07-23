package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class ThermalMitigationRequest implements Parcelable {
    public static final Parcelable.Creator<ThermalMitigationRequest> CREATOR = new Parcelable.Creator<ThermalMitigationRequest>() { // from class: android.telephony.ThermalMitigationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThermalMitigationRequest createFromParcel(Parcel parcel) {
            return new ThermalMitigationRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThermalMitigationRequest[] newArray(int i) {
            return new ThermalMitigationRequest[i];
        }
    };

    @SystemApi
    public static final int THERMAL_MITIGATION_ACTION_DATA_THROTTLING = 0;

    @SystemApi
    public static final int THERMAL_MITIGATION_ACTION_RADIO_OFF = 2;

    @SystemApi
    public static final int THERMAL_MITIGATION_ACTION_VOICE_ONLY = 1;
    private DataThrottlingRequest mDataThrottlingRequest;
    private int mThermalMitigationAction;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ThermalMitigationAction {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ThermalMitigationRequest(int i, DataThrottlingRequest dataThrottlingRequest) {
        this.mThermalMitigationAction = i;
        this.mDataThrottlingRequest = dataThrottlingRequest;
    }

    private ThermalMitigationRequest(Parcel parcel) {
        this.mThermalMitigationAction = parcel.readInt();
        this.mDataThrottlingRequest = (DataThrottlingRequest) parcel.readParcelable(DataThrottlingRequest.class.getClassLoader(), DataThrottlingRequest.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mThermalMitigationAction);
        parcel.writeParcelable(this.mDataThrottlingRequest, 0);
    }

    public String toString() {
        return "[ThermalMitigationRequest , thermalMitigationAction=" + this.mThermalMitigationAction + ", dataThrottlingRequest=" + this.mDataThrottlingRequest + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int getThermalMitigationAction() {
        return this.mThermalMitigationAction;
    }

    public DataThrottlingRequest getDataThrottlingRequest() {
        return this.mDataThrottlingRequest;
    }

    @SystemApi
    public static final class Builder {
        private DataThrottlingRequest mDataThrottlingRequest;
        private int mThermalMitigationAction = -1;

        public Builder setThermalMitigationAction(int i) {
            this.mThermalMitigationAction = i;
            return this;
        }

        public Builder setDataThrottlingRequest(DataThrottlingRequest dataThrottlingRequest) {
            this.mDataThrottlingRequest = dataThrottlingRequest;
            return this;
        }

        public ThermalMitigationRequest build() {
            int i = this.mThermalMitigationAction;
            if (i < 0) {
                throw new IllegalArgumentException("thermalMitigationAction was  not set");
            }
            if (i == 0) {
                if (this.mDataThrottlingRequest == null) {
                    throw new IllegalArgumentException("dataThrottlingRequest  cannot be null for THERMAL_MITIGATION_ACTION_DATA_THROTTLING");
                }
            } else if (this.mDataThrottlingRequest != null) {
                throw new IllegalArgumentException("dataThrottlingRequest must be null for THERMAL_MITIGATION_ACTION_VOICE_ONLY and THERMAL_MITIGATION_ACTION_RADIO_OFF");
            }
            return new ThermalMitigationRequest(this.mThermalMitigationAction, this.mDataThrottlingRequest);
        }
    }
}
