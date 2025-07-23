package com.samsung.android.knox.kpcc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKPCCManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kpcc.IKPCCManager";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKPCCManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.kpcc.IKPCCManager
        public int getDrxValue(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.kpcc.IKPCCManager
        public List getPackagesAllowedOnRestrictedNetworks(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kpcc.IKPCCManager
        public int getTelephonyDrxValue() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.kpcc.IKPCCManager
        public int setDrxValue(ContextInfo contextInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.kpcc.IKPCCManager
        public int setPackageOnRestrictedNetworks(ContextInfo contextInfo, int i, String str) throws RemoteException {
            return 0;
        }
    }

    int getDrxValue(ContextInfo contextInfo) throws RemoteException;

    List getPackagesAllowedOnRestrictedNetworks(ContextInfo contextInfo) throws RemoteException;

    int getTelephonyDrxValue() throws RemoteException;

    int setDrxValue(ContextInfo contextInfo, int i) throws RemoteException;

    int setPackageOnRestrictedNetworks(ContextInfo contextInfo, int i, String str) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKPCCManager {
        public static final int TRANSACTION_getDrxValue = 2;
        public static final int TRANSACTION_getPackagesAllowedOnRestrictedNetworks = 5;
        public static final int TRANSACTION_getTelephonyDrxValue = 3;
        public static final int TRANSACTION_setDrxValue = 1;
        public static final int TRANSACTION_setPackageOnRestrictedNetworks = 4;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKPCCManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.kpcc.IKPCCManager
            public int getDrxValue(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKPCCManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKPCCManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.kpcc.IKPCCManager
            public List getPackagesAllowedOnRestrictedNetworks(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKPCCManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpcc.IKPCCManager
            public int getTelephonyDrxValue() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKPCCManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpcc.IKPCCManager
            public int setDrxValue(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKPCCManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpcc.IKPCCManager
            public int setPackageOnRestrictedNetworks(ContextInfo contextInfo, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKPCCManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKPCCManager.DESCRIPTOR);
        }

        public static IKPCCManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKPCCManager.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKPCCManager)) ? new Proxy(iBinder) : (IKPCCManager) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setDrxValue";
            }
            if (i == 2) {
                return "getDrxValue";
            }
            if (i == 3) {
                return "getTelephonyDrxValue";
            }
            if (i == 4) {
                return "setPackageOnRestrictedNetworks";
            }
            if (i != 5) {
                return null;
            }
            return "getPackagesAllowedOnRestrictedNetworks";
        }

        public int getMaxTransactionId() {
            return 4;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKPCCManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKPCCManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int drxValue = setDrxValue(contextInfo, readInt);
                parcel2.writeNoException();
                parcel2.writeInt(drxValue);
            } else if (i == 2) {
                ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                parcel.enforceNoDataAvail();
                int drxValue2 = getDrxValue(contextInfo2);
                parcel2.writeNoException();
                parcel2.writeInt(drxValue2);
            } else if (i == 3) {
                int telephonyDrxValue = getTelephonyDrxValue();
                parcel2.writeNoException();
                parcel2.writeInt(telephonyDrxValue);
            } else if (i == 4) {
                ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                int readInt2 = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                int packageOnRestrictedNetworks = setPackageOnRestrictedNetworks(contextInfo3, readInt2, readString);
                parcel2.writeNoException();
                parcel2.writeInt(packageOnRestrictedNetworks);
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                parcel.enforceNoDataAvail();
                List packagesAllowedOnRestrictedNetworks = getPackagesAllowedOnRestrictedNetworks(contextInfo4);
                parcel2.writeNoException();
                parcel2.writeList(packagesAllowedOnRestrictedNetworks);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
