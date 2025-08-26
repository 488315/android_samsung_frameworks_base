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

/* loaded from: classes4.dex */
public interface ITrustFactorService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.config.ITrustFactorService";

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

    public abstract class Stub extends Binder implements ITrustFactorService {
        public static final int TRANSACTION_getTrustScore = 6;
        public static final int TRANSACTION_getType = 5;
        public static final int TRANSACTION_init = 1;
        public static final int TRANSACTION_isStarted = 4;
        public static final int TRANSACTION_start = 2;
        public static final int TRANSACTION_stop = 3;

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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public TrustFactorType getType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TrustFactorType) _Parcel.readTypedObject(parcelObtain2, TrustFactorType.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public int init(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public boolean isStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public int start(Map map, ITrustFactorListener iTrustFactorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeStrongInterface(iTrustFactorListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorService
            public int stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITrustFactorService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITrustFactorService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITrustFactorService)) ? new Proxy(iBinder) : (ITrustFactorService) iInterfaceQueryLocalInterface;
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
                    int iInit = init(IResultListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iInit);
                    return true;
                case 2:
                    int iStart = start(parcel.readHashMap(getClass().getClassLoader()), ITrustFactorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart);
                    return true;
                case 3:
                    int iStop = stop();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStop);
                    return true;
                case 4:
                    boolean zIsStarted = isStarted();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsStarted ? 1 : 0);
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
