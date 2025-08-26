package com.android.systemui.screenshot.proxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface IOnDoneCallback extends IInterface {
    void onDone();

    public abstract class Stub extends Binder implements IOnDoneCallback {

        public class Proxy implements IOnDoneCallback {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.proxy.IOnDoneCallback
            public final void onDone() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.screenshot.proxy.IOnDoneCallback");
                    parcelObtain.writeBoolean(true);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.proxy.IOnDoneCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.proxy.IOnDoneCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.proxy.IOnDoneCallback");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.readBoolean();
            parcel.enforceNoDataAvail();
            ((ScreenshotProxyClient$dismissKeyguard$onDoneBinder$1) this).onDone();
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
