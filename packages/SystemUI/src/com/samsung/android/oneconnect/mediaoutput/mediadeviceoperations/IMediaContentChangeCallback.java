package com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface IMediaContentChangeCallback extends IInterface {
    void onChanged(String str, String str2);

    public abstract class Stub extends Binder implements IMediaContentChangeCallback {

        public class Proxy implements IMediaContentChangeCallback {
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
            attachInterface(this, "com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onChanged(parcel.readString(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
