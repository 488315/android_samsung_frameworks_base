package com.samsung.android.knox.zt.config;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.knox.zt.config.IResultListener;
import com.samsung.android.knox.zt.config.ITrustFactorListener;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ITrustFactorService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.config.ITrustFactorService";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ITrustFactorService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public long getTrustScore() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public TrustFactorType getType() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public int init(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public boolean isStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public int start(Map map, ITrustFactorListener iTrustFactorListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public int stop() throws RemoteException {
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

        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    long getTrustScore() throws RemoteException;

    TrustFactorType getType() throws RemoteException;

    int init(IResultListener iResultListener) throws RemoteException;

    boolean isStarted() throws RemoteException;

    int start(Map map, ITrustFactorListener iTrustFactorListener) throws RemoteException;

    int stop() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ITrustFactorService {
        public static final int TRANSACTION_getTrustScore = 6;
        public static final int TRANSACTION_getType = 5;
        public static final int TRANSACTION_init = 1;
        public static final int TRANSACTION_isStarted = 4;
        public static final int TRANSACTION_start = 2;
        public static final int TRANSACTION_stop = 3;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ITrustFactorService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITrustFactorService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public long getTrustScore() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public TrustFactorType getType() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TrustFactorType) _Parcel.readTypedObject(obtain2, TrustFactorType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public int init(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public boolean isStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public int start(Map map, ITrustFactorListener iTrustFactorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    obtain.writeMap(map);
                    obtain.writeStrongInterface(iTrustFactorListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public int stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ITrustFactorService.DESCRIPTOR);
        }

        public static ITrustFactorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITrustFactorService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ITrustFactorService)) ? new Proxy(iBinder) : (ITrustFactorService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITrustFactorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITrustFactorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int init = init(IResultListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(init);
                    return true;
                case 2:
                    int start = start(parcel.readHashMap(getClass().getClassLoader()), ITrustFactorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 3:
                    int stop = stop();
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 4:
                    boolean isStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeInt(isStarted ? 1 : 0);
                    return true;
                case 5:
                    TrustFactorType type = getType();
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, type, 1);
                    return true;
                case 6:
                    long trustScore = getTrustScore();
                    parcel2.writeNoException();
                    parcel2.writeLong(trustScore);
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
