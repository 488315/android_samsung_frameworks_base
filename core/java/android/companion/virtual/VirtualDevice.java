package android.companion.virtual;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class VirtualDevice implements Parcelable {
    public static final Parcelable.Creator<VirtualDevice> CREATOR =
            new Parcelable.Creator<
                    VirtualDevice>() { // from class: android.companion.virtual.VirtualDevice.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualDevice createFromParcel(Parcel in) {
                    return new VirtualDevice(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualDevice[] newArray(int size) {
                    return new VirtualDevice[size];
                }
            };
    private final CharSequence mDisplayName;
    private final int mId;
    private final String mName;
    private final String mPersistentId;
    private final IVirtualDevice mVirtualDevice;

    public VirtualDevice(IVirtualDevice virtualDevice, int id, String persistentId, String name) {
        this(virtualDevice, id, persistentId, name, null);
    }

    public VirtualDevice(
            IVirtualDevice virtualDevice,
            int id,
            String persistentId,
            String name,
            CharSequence displayName) {
        if (id <= 0) {
            throw new IllegalArgumentException("VirtualDevice ID must be greater than 0");
        }
        this.mVirtualDevice = virtualDevice;
        this.mId = id;
        this.mPersistentId = persistentId;
        this.mName = name;
        this.mDisplayName = displayName;
    }

    private VirtualDevice(Parcel parcel) {
        this.mVirtualDevice = IVirtualDevice.Stub.asInterface(parcel.readStrongBinder());
        this.mId = parcel.readInt();
        this.mPersistentId = parcel.readString8();
        this.mName = parcel.readString8();
        this.mDisplayName = parcel.readCharSequence();
    }

    public int getDeviceId() {
        return this.mId;
    }

    public String getPersistentDeviceId() {
        return this.mPersistentId;
    }

    public String getName() {
        return this.mName;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public int[] getDisplayIds() {
        try {
            return this.mVirtualDevice.getDisplayIds();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasCustomSensorSupport() {
        try {
            return this.mVirtualDevice.getDevicePolicy(0) == 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean hasCustomAudioInputSupport() {
        try {
            return this.mVirtualDevice.hasCustomAudioInputSupport();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean hasCustomCameraSupport() {
        try {
            return this.mVirtualDevice.getDevicePolicy(5) == 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mVirtualDevice.asBinder());
        dest.writeInt(this.mId);
        dest.writeString8(this.mPersistentId);
        dest.writeString8(this.mName);
        dest.writeCharSequence(this.mDisplayName);
    }

    public String toString() {
        return "VirtualDevice( mId="
                + this.mId
                + " mPersistentId="
                + this.mPersistentId
                + " mName="
                + this.mName
                + " mDisplayName="
                + ((Object) this.mDisplayName)
                + NavigationBarInflaterView.KEY_CODE_END;
    }
}
