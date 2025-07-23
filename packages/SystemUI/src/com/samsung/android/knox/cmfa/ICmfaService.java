package com.samsung.android.knox.cmfa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.cmfa.IEventListener;
import com.samsung.android.knox.cmfa.IResultListener;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ICmfaService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.cmfa.ICmfaService";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ICmfaService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int check(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int disable() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int enable(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public List<AuthFactorType> getFactorsToSetup() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public List<AuthActionType> getValidActions() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public boolean isStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int registerListener(IEventListener iEventListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int start(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int stop(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.ICmfaService
        public int unregisterListener(IEventListener iEventListener) throws RemoteException {
            return 0;
        }
    }

    int check(IResultListener iResultListener) throws RemoteException;

    int disable() throws RemoteException;

    int enable(String str, boolean z) throws RemoteException;

    List<AuthFactorType> getFactorsToSetup() throws RemoteException;

    List<AuthActionType> getValidActions() throws RemoteException;

    boolean isEnabled() throws RemoteException;

    boolean isStarted() throws RemoteException;

    int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException;

    int registerListener(IEventListener iEventListener) throws RemoteException;

    int start(IResultListener iResultListener) throws RemoteException;

    int stop(IResultListener iResultListener) throws RemoteException;

    int unregisterListener(IEventListener iEventListener) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ICmfaService {
        public static final int TRANSACTION_check = 7;
        public static final int TRANSACTION_disable = 6;
        public static final int TRANSACTION_enable = 5;
        public static final int TRANSACTION_getFactorsToSetup = 3;
        public static final int TRANSACTION_getValidActions = 4;
        public static final int TRANSACTION_isEnabled = 1;
        public static final int TRANSACTION_isStarted = 2;
        public static final int TRANSACTION_notifyTestFactorScoreChange = 12;
        public static final int TRANSACTION_registerListener = 10;
        public static final int TRANSACTION_start = 8;
        public static final int TRANSACTION_stop = 9;
        public static final int TRANSACTION_unregisterListener = 11;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ICmfaService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int check(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int disable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int enable(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public List<AuthFactorType> getFactorsToSetup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AuthFactorType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICmfaService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public List<AuthActionType> getValidActions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AuthActionType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public boolean isEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public boolean isStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int registerListener(IEventListener iEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iEventListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int start(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int stop(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int unregisterListener(IEventListener iEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iEventListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICmfaService.DESCRIPTOR);
        }

        public static ICmfaService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICmfaService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICmfaService)) ? new Proxy(iBinder) : (ICmfaService) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isEnabled";
                case 2:
                    return "isStarted";
                case 3:
                    return "getFactorsToSetup";
                case 4:
                    return "getValidActions";
                case 5:
                    return "enable";
                case 6:
                    return "disable";
                case 7:
                    return "check";
                case 8:
                    return NetworkAnalyticsConstants.DataPoints.OPEN_TIME;
                case 9:
                    return "stop";
                case 10:
                    return "registerListener";
                case 11:
                    return "unregisterListener";
                case 12:
                    return "notifyTestFactorScoreChange";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 11;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICmfaService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICmfaService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabled);
                    return true;
                case 2:
                    boolean isStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStarted);
                    return true;
                case 3:
                    List<AuthFactorType> factorsToSetup = getFactorsToSetup();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(factorsToSetup, 1);
                    return true;
                case 4:
                    List<AuthActionType> validActions = getValidActions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(validActions, 1);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int enable = enable(readString, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(enable);
                    return true;
                case 6:
                    int disable = disable();
                    parcel2.writeNoException();
                    parcel2.writeInt(disable);
                    return true;
                case 7:
                    IResultListener asInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int check = check(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(check);
                    return true;
                case 8:
                    IResultListener asInterface2 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int start = start(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 9:
                    IResultListener asInterface3 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stop = stop(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 10:
                    IEventListener asInterface4 = IEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerListener = registerListener(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerListener);
                    return true;
                case 11:
                    IEventListener asInterface5 = IEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int unregisterListener = unregisterListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterListener);
                    return true;
                case 12:
                    String readString2 = parcel.readString();
                    long readLong = parcel.readLong();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int notifyTestFactorScoreChange = notifyTestFactorScoreChange(readString2, readLong, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(notifyTestFactorScoreChange);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
