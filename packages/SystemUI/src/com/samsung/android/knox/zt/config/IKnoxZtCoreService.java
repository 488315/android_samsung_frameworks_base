package com.samsung.android.knox.zt.config;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.knox.zt.config.IEventListener;
import com.samsung.android.knox.zt.config.IResultListener;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKnoxZtCoreService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.config.IKnoxZtCoreService";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKnoxZtCoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int check(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public String configFeature(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int disable() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int disableFeature(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int enable(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int enableFeature(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public String getConfiguration(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public List<TrustFactorType> getFactorsToSetup() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public List<TrustActionType> getValidActions() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public boolean isStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int registerListener(IEventListener iEventListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int start(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int stop(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
        public int unregisterListener(IEventListener iEventListener) throws RemoteException {
            return 0;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static <T extends Parcelable> void writeTypedList(Parcel parcel, List<T> list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                writeTypedObject(parcel, list.get(i2), i);
            }
        }

        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    int check(IResultListener iResultListener) throws RemoteException;

    String configFeature(String str, String str2) throws RemoteException;

    int disable() throws RemoteException;

    int disableFeature(String str) throws RemoteException;

    int enable(String str, boolean z) throws RemoteException;

    int enableFeature(String str) throws RemoteException;

    String getConfiguration(String str) throws RemoteException;

    List<TrustFactorType> getFactorsToSetup() throws RemoteException;

    List<TrustActionType> getValidActions() throws RemoteException;

    boolean isEnabled() throws RemoteException;

    boolean isStarted() throws RemoteException;

    int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException;

    int registerListener(IEventListener iEventListener) throws RemoteException;

    int start(IResultListener iResultListener) throws RemoteException;

    int stop(IResultListener iResultListener) throws RemoteException;

    int unregisterListener(IEventListener iEventListener) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKnoxZtCoreService {
        public static final int TRANSACTION_check = 7;
        public static final int TRANSACTION_configFeature = 12;
        public static final int TRANSACTION_disable = 6;
        public static final int TRANSACTION_disableFeature = 11;
        public static final int TRANSACTION_enable = 5;
        public static final int TRANSACTION_enableFeature = 10;
        public static final int TRANSACTION_getConfiguration = 13;
        public static final int TRANSACTION_getFactorsToSetup = 3;
        public static final int TRANSACTION_getValidActions = 4;
        public static final int TRANSACTION_isEnabled = 1;
        public static final int TRANSACTION_isStarted = 2;
        public static final int TRANSACTION_notifyTestFactorScoreChange = 16;
        public static final int TRANSACTION_registerListener = 14;
        public static final int TRANSACTION_start = 8;
        public static final int TRANSACTION_stop = 9;
        public static final int TRANSACTION_unregisterListener = 15;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKnoxZtCoreService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int check(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public String configFeature(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int disable() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int disableFeature(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int enable(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int enableFeature(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public String getConfiguration(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public List<TrustFactorType> getFactorsToSetup() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TrustFactorType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxZtCoreService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public List<TrustActionType> getValidActions() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TrustActionType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public boolean isEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public boolean isStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int notifyTestFactorScoreChange(String str, long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int registerListener(IEventListener iEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iEventListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int start(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int stop(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.IKnoxZtCoreService
            public int unregisterListener(IEventListener iEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtCoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iEventListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxZtCoreService.DESCRIPTOR);
        }

        public static IKnoxZtCoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxZtCoreService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxZtCoreService)) ? new Proxy(iBinder) : (IKnoxZtCoreService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxZtCoreService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxZtCoreService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isEnabled ? 1 : 0);
                    return true;
                case 2:
                    boolean isStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeInt(isStarted ? 1 : 0);
                    return true;
                case 3:
                    List<TrustFactorType> factorsToSetup = getFactorsToSetup();
                    parcel2.writeNoException();
                    _Parcel.writeTypedList(parcel2, factorsToSetup, 1);
                    return true;
                case 4:
                    List<TrustActionType> validActions = getValidActions();
                    parcel2.writeNoException();
                    _Parcel.writeTypedList(parcel2, validActions, 1);
                    return true;
                case 5:
                    int enable = enable(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(enable);
                    return true;
                case 6:
                    int disable = disable();
                    parcel2.writeNoException();
                    parcel2.writeInt(disable);
                    return true;
                case 7:
                    int check = check(IResultListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(check);
                    return true;
                case 8:
                    int start = start(IResultListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 9:
                    int stop = stop(IResultListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 10:
                    int enableFeature = enableFeature(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(enableFeature);
                    return true;
                case 11:
                    int disableFeature = disableFeature(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(disableFeature);
                    return true;
                case 12:
                    String configFeature = configFeature(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(configFeature);
                    return true;
                case 13:
                    String configuration = getConfiguration(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(configuration);
                    return true;
                case 14:
                    int registerListener = registerListener(IEventListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerListener);
                    return true;
                case 15:
                    int unregisterListener = unregisterListener(IEventListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterListener);
                    return true;
                case 16:
                    int notifyTestFactorScoreChange = notifyTestFactorScoreChange(parcel.readString(), parcel.readLong(), parcel.readInt() != 0);
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
