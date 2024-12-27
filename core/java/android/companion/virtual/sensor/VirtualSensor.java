package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

@SystemApi
public final class VirtualSensor implements Parcelable {
    public static final Parcelable.Creator<VirtualSensor> CREATOR =
            new Parcelable.Creator<VirtualSensor>() { // from class:
                // android.companion.virtual.sensor.VirtualSensor.1
                @Override // android.os.Parcelable.Creator
                public VirtualSensor createFromParcel(Parcel in) {
                    return new VirtualSensor(in);
                }

                @Override // android.os.Parcelable.Creator
                public VirtualSensor[] newArray(int size) {
                    return new VirtualSensor[size];
                }
            };
    private final int mHandle;
    private final String mName;
    private final IBinder mToken;
    private final int mType;
    private final IVirtualDevice mVirtualDevice;

    public VirtualSensor(
            int handle, int type, String name, IVirtualDevice virtualDevice, IBinder token) {
        this.mHandle = handle;
        this.mType = type;
        this.mName = name;
        this.mVirtualDevice = virtualDevice;
        this.mToken = token;
    }

    public VirtualSensor(int handle, int type, String name) {
        this(handle, type, name, null, null);
    }

    private VirtualSensor(Parcel parcel) {
        this.mHandle = parcel.readInt();
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mHandle);
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
        parcel.writeStrongBinder(this.mVirtualDevice.asBinder());
        parcel.writeStrongBinder(this.mToken);
    }

    public String toString() {
        return "VirtualSensor{ mType=" + this.mType + ", mName='" + this.mName + "' }";
    }

    public void sendEvent(VirtualSensorEvent event) {
        try {
            this.mVirtualDevice.sendSensorEvent(this.mToken, event);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
