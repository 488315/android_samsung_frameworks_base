package com.samsung.android.knox.foresight.framework.system;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKFCommnadService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.foresight.framework.system.IKFCommnadService";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKFCommnadService {
        @Override // com.samsung.android.knox.foresight.framework.system.IKFCommnadService
        public String SendCommand(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    String SendCommand(String str) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKFCommnadService {
        public static final int TRANSACTION_SendCommand = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKFCommnadService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.foresight.framework.system.IKFCommnadService
            public String SendCommand(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKFCommnadService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKFCommnadService.DESCRIPTOR;
            }
        }

        public Stub() {
            attachInterface(this, IKFCommnadService.DESCRIPTOR);
        }

        public static IKFCommnadService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKFCommnadService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKFCommnadService)) ? new Proxy(iBinder) : (IKFCommnadService) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "SendCommand";
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKFCommnadService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKFCommnadService.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            String SendCommand = SendCommand(readString);
            parcel2.writeNoException();
            parcel2.writeString(SendCommand);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
