package com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface IMediaDeviceControlCallback extends IInterface {
    void onMuteChanged(String str, boolean z);

    void onVolumeChanged(int i, String str);

    public abstract class Stub extends Binder implements IMediaDeviceControlCallback {

        public class Proxy implements IMediaDeviceControlCallback {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
                return true;
            }
            if (i == 1) {
                onMuteChanged(parcel.readString(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onVolumeChanged(parcel.readInt(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
