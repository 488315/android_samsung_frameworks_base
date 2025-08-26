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

/* loaded from: classes4.dex */
public interface ICmfaService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.cmfa.ICmfaService";

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int disable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int enable(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public List<AuthFactorType> getFactorsToSetup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AuthFactorType.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICmfaService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public List<AuthActionType> getValidActions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AuthActionType.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public boolean isEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public boolean isStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int registerListener(IEventListener iEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iEventListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int start(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int stop(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.ICmfaService
            public int unregisterListener(IEventListener iEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmfaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iEventListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICmfaService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICmfaService)) ? new Proxy(iBinder) : (ICmfaService) iInterfaceQueryLocalInterface;
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
                    boolean zIsEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnabled);
                    return true;
                case 2:
                    boolean zIsStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStarted);
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
                    String string = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iEnable = enable(string, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnable);
                    return true;
                case 6:
                    int iDisable = disable();
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisable);
                    return true;
                case 7:
                    IResultListener iResultListenerAsInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iCheck = check(iResultListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheck);
                    return true;
                case 8:
                    IResultListener iResultListenerAsInterface2 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStart = start(iResultListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart);
                    return true;
                case 9:
                    IResultListener iResultListenerAsInterface3 = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStop = stop(iResultListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStop);
                    return true;
                case 10:
                    IEventListener iEventListenerAsInterface = IEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterListener = registerListener(iEventListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterListener);
                    return true;
                case 11:
                    IEventListener iEventListenerAsInterface2 = IEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUnregisterListener = unregisterListener(iEventListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterListener);
                    return true;
                case 12:
                    String string2 = parcel.readString();
                    long j = parcel.readLong();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iNotifyTestFactorScoreChange = notifyTestFactorScoreChange(string2, j, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iNotifyTestFactorScoreChange);
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
