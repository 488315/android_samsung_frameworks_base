package android.companion.virtual;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes.dex */
public final class VirtualDevice implements Parcelable {
    public static final Parcelable.Creator<VirtualDevice> CREATOR = new Parcelable.Creator<VirtualDevice>() { // from class: android.companion.virtual.VirtualDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDevice createFromParcel(Parcel parcel) {
            return new VirtualDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDevice[] newArray(int i) {
            return new VirtualDevice[i];
        }
    };
    private final CharSequence mDisplayName;
    private final int mId;
    private final String mName;
    private final String mPersistentId;
    private final IVirtualDevice mVirtualDevice;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VirtualDevice(IVirtualDevice iVirtualDevice, int i, String str, String str2) {
        this(iVirtualDevice, i, str, str2, null);
    }

    public VirtualDevice(IVirtualDevice iVirtualDevice, int i, String str, String str2, CharSequence charSequence) {
        if (i <= 0) {
            throw new IllegalArgumentException("VirtualDevice ID must be greater than 0");
        }
        this.mVirtualDevice = iVirtualDevice;
        this.mId = i;
        this.mPersistentId = str;
        this.mName = str2;
        this.mDisplayName = charSequence;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mVirtualDevice.asBinder());
        parcel.writeInt(this.mId);
        parcel.writeString8(this.mPersistentId);
        parcel.writeString8(this.mName);
        parcel.writeCharSequence(this.mDisplayName);
    }

    public String toString() {
        return "VirtualDevice( mId=" + this.mId + " mPersistentId=" + this.mPersistentId + " mName=" + this.mName + " mDisplayName=" + ((Object) this.mDisplayName) + NavigationBarInflaterView.KEY_CODE_END;
    }
}
