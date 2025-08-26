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

/* loaded from: classes4.dex */
public interface IAuthFactorService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.cmfa.IAuthFactorService";

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

    public abstract class Stub extends Binder implements IAuthFactorService {
        public static final int TRANSACTION_getTrustScore = 6;
        public static final int TRANSACTION_getType = 5;
        public static final int TRANSACTION_init = 1;
        public static final int TRANSACTION_isStarted = 4;
        public static final int TRANSACTION_start = 2;
        public static final int TRANSACTION_stop = 3;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public AuthFactorType getType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AuthFactorType) parcelObtain2.readTypedObject(AuthFactorType.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public int init(IResultListener iResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public boolean isStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public int start(Map map, IAuthFactorListener iAuthFactorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeStrongInterface(iAuthFactorListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IAuthFactorService
            public int stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthFactorService.DESCRIPTOR);
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
            attachInterface(this, IAuthFactorService.DESCRIPTOR);
        }

        public static IAuthFactorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAuthFactorService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAuthFactorService)) ? new Proxy(iBinder) : (IAuthFactorService) iInterfaceQueryLocalInterface;
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
                    IResultListener iResultListenerAsInterface = IResultListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iInit = init(iResultListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInit);
                    return true;
                case 2:
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    IAuthFactorListener iAuthFactorListenerAsInterface = IAuthFactorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStart = start(hashMap, iAuthFactorListenerAsInterface);
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
                    parcel2.writeBoolean(zIsStarted);
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
