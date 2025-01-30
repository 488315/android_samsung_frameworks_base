package com.skms.android.agent;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface CcmInterface extends IInterface {

    public class Default implements CcmInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.skms.android.agent.CcmInterface
        public int handleCcm(byte[] bArr, int i) {
            return 0;
        }
    }

    int handleCcm(byte[] bArr, int i);

    public abstract class Stub extends Binder implements CcmInterface {
        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "handleCcm";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "com.skms.android.agent.CcmInterface");
        }

        public static CcmInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.skms.android.agent.CcmInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof CcmInterface)) {
                return (CcmInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.skms.android.agent.CcmInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.skms.android.agent.CcmInterface");
                return true;
            }
            if (i == 1) {
                byte[] createByteArray = parcel.createByteArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int handleCcm = handleCcm(createByteArray, readInt);
                parcel2.writeNoException();
                parcel2.writeInt(handleCcm);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public class Proxy implements CcmInterface {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.skms.android.agent.CcmInterface
            public int handleCcm(byte[] bArr, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.skms.android.agent.CcmInterface");
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
