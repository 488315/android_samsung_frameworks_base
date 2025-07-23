package com.samsung.android.knox.cmfa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.cmfa.IAuthFactorListener;
import com.samsung.android.knox.cmfa.IResultListener;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IAuthFactorService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.cmfa.IAuthFactorService";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IAuthFactorService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public long getTrustScore() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public AuthFactorType getType() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public int init(IResultListener iResultListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public boolean isStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public int start(Map map, IAuthFactorListener iAuthFactorListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public int stop() throws RemoteException {
            return 0;
        }
    }

    long getTrustScore() throws RemoteException;

    AuthFactorType getType() throws RemoteException;

    int init(IResultListener iResultListener) throws RemoteException;

    boolean isStarted() throws RemoteException;

    int start(Map map, IAuthFactorListener iAuthFactorListener) throws RemoteException;

    int stop() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IAuthFactorService {
        public static final int TRANSACTION_getTrustScore = 6;
        public static final int TRANSACTION_getType = 5;
        public static final int TRANSACTION_init = 1;
        public static final int TRANSACTION_isStarted = 4;
        public static final int TRANSACTION_start = 2;
        public static final int TRANSACTION_stop = 3;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IAuthFactorService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthFactorService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public long getTrustScore() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public AuthFactorType getType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AuthFactorType) obtain2.readTypedObject(AuthFactorType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public int init(IResultListener iResultListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public boolean isStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public int start(Map map, IAuthFactorListener iAuthFactorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    obtain.writeMap(map);
                    obtain.writeStrongInterface(iAuthFactorListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public int stop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
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
            attachInterface(this, IAuthFactorService.DESCRIPTOR);
        }

        public static IAuthFactorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthFactorService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IAuthFactorService)) ? new Proxy(iBinder) : (IAuthFactorService) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "init";
                case 2:
                    return NetworkAnalyticsConstants.DataPoints.OPEN_TIME;
                case 3:
                    return "stop";
                case 4:
                    return "isStarted";
                case 5:
                    return "getType";
                case 6:
                    return "getTrustScore";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 5;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAuthFactorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthFactorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IResultListener asInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int init = init(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(init);
                    return true;
                case 2:
                    HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    IAuthFactorListener asInterface2 = IAuthFactorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int start = start(readHashMap, asInterface2);
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
                    parcel2.writeBoolean(isStarted);
                    return true;
                case 5:
                    AuthFactorType type = getType();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(type, 1);
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
