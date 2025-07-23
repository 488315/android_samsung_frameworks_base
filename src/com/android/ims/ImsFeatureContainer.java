package com.android.ims;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.ImsService;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.ISipTransport;
import android.telephony.ims.feature.ImsFeature;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ImsFeatureContainer implements Parcelable {
    public static final Parcelable.Creator<ImsFeatureContainer> CREATOR = new Parcelable.Creator<ImsFeatureContainer>() { // from class: com.android.ims.ImsFeatureContainer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsFeatureContainer createFromParcel(Parcel parcel) {
            return new ImsFeatureContainer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsFeatureContainer[] newArray(int i) {
            return new ImsFeatureContainer[i];
        }
    };
    public final IImsConfig imsConfig;
    public final IBinder imsFeature;
    public final IImsRegistration imsRegistration;
    private long mCapabilities;
    private int mState;
    public final ISipTransport sipTransport;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsFeatureContainer(IBinder iBinder, IImsConfig iImsConfig, IImsRegistration iImsRegistration, ISipTransport iSipTransport, long j) {
        this.mState = 0;
        this.imsFeature = iBinder;
        this.imsConfig = iImsConfig;
        this.imsRegistration = iImsRegistration;
        this.sipTransport = iSipTransport;
        this.mCapabilities = j;
    }

    private ImsFeatureContainer(Parcel parcel) {
        this.mState = 0;
        this.imsFeature = parcel.readStrongBinder();
        this.imsConfig = IImsConfig.Stub.asInterface(parcel.readStrongBinder());
        this.imsRegistration = IImsRegistration.Stub.asInterface(parcel.readStrongBinder());
        this.sipTransport = ISipTransport.Stub.asInterface(parcel.readStrongBinder());
        this.mState = parcel.readInt();
        this.mCapabilities = parcel.readLong();
    }

    public long getCapabilities() {
        return this.mCapabilities;
    }

    public void setCapabilities(long j) {
        this.mCapabilities = j;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ImsFeatureContainer imsFeatureContainer = (ImsFeatureContainer) obj;
            if (this.imsFeature.equals(imsFeatureContainer.imsFeature) && this.imsConfig.equals(imsFeatureContainer.imsConfig) && this.imsRegistration.equals(imsFeatureContainer.imsRegistration) && this.sipTransport.equals(imsFeatureContainer.sipTransport) && this.mState == imsFeatureContainer.getState() && this.mCapabilities == imsFeatureContainer.getCapabilities()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.imsFeature, this.imsConfig, this.imsRegistration, this.sipTransport, Integer.valueOf(this.mState), Long.valueOf(this.mCapabilities));
    }

    public String toString() {
        return "FeatureContainer{imsFeature=" + this.imsFeature + ", imsConfig=" + this.imsConfig + ", imsRegistration=" + this.imsRegistration + ", sipTransport=" + this.sipTransport + ", state=" + ImsFeature.STATE_LOG_MAP.get(Integer.valueOf(this.mState)) + ", capabilities = " + ImsService.getCapabilitiesString(this.mCapabilities) + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.imsFeature);
        parcel.writeStrongInterface(this.imsConfig);
        parcel.writeStrongInterface(this.imsRegistration);
        parcel.writeStrongInterface(this.sipTransport);
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mCapabilities);
    }
}
