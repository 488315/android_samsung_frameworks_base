package android.telephony;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* loaded from: classes4.dex */
public final class SignalStrengthUpdateRequest implements Parcelable {
    public static final Parcelable.Creator<SignalStrengthUpdateRequest> CREATOR = new Parcelable.Creator<SignalStrengthUpdateRequest>() { // from class: android.telephony.SignalStrengthUpdateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalStrengthUpdateRequest createFromParcel(Parcel parcel) {
            return new SignalStrengthUpdateRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalStrengthUpdateRequest[] newArray(int i) {
            return new SignalStrengthUpdateRequest[i];
        }
    };
    private final boolean mIsReportingRequestedWhileIdle;
    private final boolean mIsSystemThresholdReportingRequestedWhileIdle;
    private final IBinder mLiveToken;
    private final List<SignalThresholdInfo> mSignalThresholdInfos;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SignalStrengthUpdateRequest(List<SignalThresholdInfo> list, boolean z, boolean z2) {
        validate(list, z2);
        this.mSignalThresholdInfos = list;
        this.mIsReportingRequestedWhileIdle = z;
        this.mIsSystemThresholdReportingRequestedWhileIdle = z2;
        this.mLiveToken = new Binder();
    }

    public static final class Builder {
        private List<SignalThresholdInfo> mSignalThresholdInfos = null;
        private boolean mIsReportingRequestedWhileIdle = false;
        private boolean mIsSystemThresholdReportingRequestedWhileIdle = false;

        public Builder setSignalThresholdInfos(Collection<SignalThresholdInfo> collection) {
            Objects.requireNonNull(collection, "SignalThresholdInfo collection must not be null");
            Iterator<SignalThresholdInfo> it = collection.iterator();
            while (it.hasNext()) {
                Objects.requireNonNull(it.next(), "SignalThresholdInfo in the collection must not be null");
            }
            ArrayList arrayList = new ArrayList(collection);
            this.mSignalThresholdInfos = arrayList;
            arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: android.telephony.SignalStrengthUpdateRequest$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((SignalThresholdInfo) obj).getRadioAccessNetworkType();
                }
            }).thenComparing(new Function() { // from class: android.telephony.SignalStrengthUpdateRequest$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((SignalThresholdInfo) obj).getSignalMeasurementType());
                }
            }));
            return this;
        }

        public Builder setReportingRequestedWhileIdle(boolean z) {
            this.mIsReportingRequestedWhileIdle = z;
            return this;
        }

        @SystemApi
        public Builder setSystemThresholdReportingRequestedWhileIdle(boolean z) {
            this.mIsSystemThresholdReportingRequestedWhileIdle = z;
            return this;
        }

        public SignalStrengthUpdateRequest build() {
            return new SignalStrengthUpdateRequest(this.mSignalThresholdInfos, this.mIsReportingRequestedWhileIdle, this.mIsSystemThresholdReportingRequestedWhileIdle);
        }
    }

    private SignalStrengthUpdateRequest(Parcel parcel) {
        this.mSignalThresholdInfos = parcel.createTypedArrayList(SignalThresholdInfo.CREATOR);
        this.mIsReportingRequestedWhileIdle = parcel.readBoolean();
        this.mIsSystemThresholdReportingRequestedWhileIdle = parcel.readBoolean();
        this.mLiveToken = parcel.readStrongBinder();
    }

    public Collection<SignalThresholdInfo> getSignalThresholdInfos() {
        return Collections.unmodifiableList(this.mSignalThresholdInfos);
    }

    public boolean isReportingRequestedWhileIdle() {
        return this.mIsReportingRequestedWhileIdle;
    }

    @SystemApi
    public boolean isSystemThresholdReportingRequestedWhileIdle() {
        return this.mIsSystemThresholdReportingRequestedWhileIdle;
    }

    public IBinder getLiveToken() {
        return this.mLiveToken;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mSignalThresholdInfos);
        parcel.writeBoolean(this.mIsReportingRequestedWhileIdle);
        parcel.writeBoolean(this.mIsSystemThresholdReportingRequestedWhileIdle);
        parcel.writeStrongBinder(this.mLiveToken);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SignalStrengthUpdateRequest)) {
            return false;
        }
        SignalStrengthUpdateRequest signalStrengthUpdateRequest = (SignalStrengthUpdateRequest) obj;
        return this.mSignalThresholdInfos.equals(signalStrengthUpdateRequest.mSignalThresholdInfos) && this.mIsReportingRequestedWhileIdle == signalStrengthUpdateRequest.mIsReportingRequestedWhileIdle && this.mIsSystemThresholdReportingRequestedWhileIdle == signalStrengthUpdateRequest.mIsSystemThresholdReportingRequestedWhileIdle;
    }

    public int hashCode() {
        return Objects.hash(this.mSignalThresholdInfos, Boolean.valueOf(this.mIsReportingRequestedWhileIdle), Boolean.valueOf(this.mIsSystemThresholdReportingRequestedWhileIdle));
    }

    public String toString() {
        return "SignalStrengthUpdateRequest{mSignalThresholdInfos=" + this.mSignalThresholdInfos + " mIsReportingRequestedWhileIdle=" + this.mIsReportingRequestedWhileIdle + " mIsSystemThresholdReportingRequestedWhileIdle=" + this.mIsSystemThresholdReportingRequestedWhileIdle + " mLiveToken" + this.mLiveToken + "}";
    }

    private static void validate(Collection<SignalThresholdInfo> collection, boolean z) {
        if (collection == null || (collection.isEmpty() && !z)) {
            throw new IllegalArgumentException("SignalThresholdInfo collection is null or empty");
        }
        HashMap map = new HashMap(collection.size());
        for (SignalThresholdInfo signalThresholdInfo : collection) {
            int radioAccessNetworkType = signalThresholdInfo.getRadioAccessNetworkType();
            int signalMeasurementType = signalThresholdInfo.getSignalMeasurementType();
            map.putIfAbsent(Integer.valueOf(radioAccessNetworkType), new HashSet());
            if (!((Set) map.get(Integer.valueOf(radioAccessNetworkType))).add(Integer.valueOf(signalMeasurementType))) {
                throw new IllegalArgumentException("SignalMeasurementType " + signalMeasurementType + " for RAN " + radioAccessNetworkType + " is not unique");
            }
        }
    }
}
