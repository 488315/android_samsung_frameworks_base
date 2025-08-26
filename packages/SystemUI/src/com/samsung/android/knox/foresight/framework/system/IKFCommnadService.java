package com.samsung.android.knox.foresight.framework.system;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IKFCommnadService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.foresight.framework.system.IKFCommnadService";

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

    public abstract class Stub extends Binder implements IKFCommnadService {
        public static final int TRANSACTION_SendCommand = 1;

        class Proxy implements IKFCommnadService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.foresight.framework.system.IKFCommnadService
            public String SendCommand(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKFCommnadService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKFCommnadService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKFCommnadService)) ? new Proxy(iBinder) : (IKFCommnadService) iInterfaceQueryLocalInterface;
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
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            String strSendCommand = SendCommand(string);
            parcel2.writeNoException();
            parcel2.writeString(strSendCommand);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
