package com.nttdocomo.android.screenlockservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface IScreenLockService extends IInterface {
    int getUnreadCount();

    public abstract class Stub extends Binder implements IScreenLockService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IScreenLockService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.nttdocomo.android.screenlockservice.IScreenLockService
            public final int getUnreadCount() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.nttdocomo.android.screenlockservice.IScreenLockService");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.nttdocomo.android.screenlockservice.IScreenLockService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.nttdocomo.android.screenlockservice.IScreenLockService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.nttdocomo.android.screenlockservice.IScreenLockService");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int unreadCount = getUnreadCount();
            parcel2.writeNoException();
            parcel2.writeInt(unreadCount);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
