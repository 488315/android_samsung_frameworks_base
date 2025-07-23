package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.Flags;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualSensor implements Parcelable {
    public static final Parcelable.Creator<VirtualSensor> CREATOR = new Parcelable.Creator<VirtualSensor>() { // from class: android.companion.virtual.sensor.VirtualSensor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensor createFromParcel(Parcel parcel) {
            return new VirtualSensor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensor[] newArray(int i) {
            return new VirtualSensor[i];
        }
    };
    private final Object mAdditionalInfoLock;
    private final int mFlags;
    private final int mHandle;
    private final String mName;
    private final IBinder mToken;
    private final int mType;
    private final IVirtualDevice mVirtualDevice;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VirtualSensor(int i, int i2, String str, IVirtualDevice iVirtualDevice, IBinder iBinder) {
        this(i, i2, str, 0, iVirtualDevice, iBinder);
    }

    public VirtualSensor(int i, int i2, String str, int i3, IVirtualDevice iVirtualDevice, IBinder iBinder) {
        this.mAdditionalInfoLock = new Object();
        this.mHandle = i;
        this.mType = i2;
        this.mName = str;
        this.mFlags = i3;
        this.mVirtualDevice = iVirtualDevice;
        this.mToken = iBinder;
    }

    public VirtualSensor(int i, int i2, String str) {
        this(i, i2, str, 0, null, null);
    }

    private VirtualSensor(Parcel parcel) {
        this.mAdditionalInfoLock = new Object();
        this.mHandle = parcel.readInt();
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
        this.mFlags = parcel.readInt();
        this.mVirtualDevice = IVirtualDevice.Stub.asInterface(parcel.readStrongBinder());
        this.mToken = parcel.readStrongBinder();
    }

    public int getHandle() {
        return this.mHandle;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public int getDeviceId() {
        try {
            return this.mVirtualDevice.getDeviceId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHandle);
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
        parcel.writeInt(this.mFlags);
        parcel.writeStrongBinder(this.mVirtualDevice.asBinder());
        parcel.writeStrongBinder(this.mToken);
    }

    public String toString() {
        return "VirtualSensor{ mType=" + this.mType + ", mName='" + this.mName + "' }";
    }

    public void sendEvent(VirtualSensorEvent virtualSensorEvent) {
        try {
            this.mVirtualDevice.sendSensorEvent(this.mToken, virtualSensorEvent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendAdditionalInfo(VirtualSensorAdditionalInfo virtualSensorAdditionalInfo) {
        if (!Flags.virtualSensorAdditionalInfo()) {
            throw new UnsupportedOperationException("Sensor additional info not supported.");
        }
        if ((this.mFlags & 64) == 0) {
            throw new UnsupportedOperationException("Sensor additional info not supported.");
        }
        try {
            synchronized (this.mAdditionalInfoLock) {
                this.mVirtualDevice.sendSensorAdditionalInfo(this.mToken, virtualSensorAdditionalInfo);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
