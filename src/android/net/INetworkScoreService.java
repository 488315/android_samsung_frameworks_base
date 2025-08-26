package android.net;

import android.net.INetworkScoreCache;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface INetworkScoreService extends IInterface {

    public static class Default implements INetworkScoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkScoreService
        public boolean clearScores() throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public void disableScoring() throws RemoteException {
        }

        @Override // android.net.INetworkScoreService
        public NetworkScorerAppData getActiveScorer() throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkScoreService
        public String getActiveScorerPackage() throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkScoreService
        public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkScoreService
        public boolean isCallerActiveScorer(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public void registerNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache, int i2) throws RemoteException {
        }

        @Override // android.net.INetworkScoreService
        public boolean requestScores(NetworkKey[] networkKeyArr) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public boolean setActiveScorer(String str) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public void unregisterNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) throws RemoteException {
        }

        @Override // android.net.INetworkScoreService
        public boolean updateScores(ScoredNetwork[] scoredNetworkArr) throws RemoteException {
            return false;
        }
    }

    boolean clearScores() throws RemoteException;

    void disableScoring() throws RemoteException;

    NetworkScorerAppData getActiveScorer() throws RemoteException;

    String getActiveScorerPackage() throws RemoteException;

    List<NetworkScorerAppData> getAllValidScorers() throws RemoteException;

    boolean isCallerActiveScorer(int i) throws RemoteException;

    void registerNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache, int i2) throws RemoteException;

    boolean requestScores(NetworkKey[] networkKeyArr) throws RemoteException;

    boolean setActiveScorer(String str) throws RemoteException;

    void unregisterNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) throws RemoteException;

    boolean updateScores(ScoredNetwork[] scoredNetworkArr) throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkScoreService {
        public static final String DESCRIPTOR = "android.net.INetworkScoreService";
        static final int TRANSACTION_clearScores = 2;
        static final int TRANSACTION_disableScoring = 4;
        static final int TRANSACTION_getActiveScorer = 10;
        static final int TRANSACTION_getActiveScorerPackage = 9;
        static final int TRANSACTION_getAllValidScorers = 11;
        static final int TRANSACTION_isCallerActiveScorer = 8;
        static final int TRANSACTION_registerNetworkScoreCache = 5;
        static final int TRANSACTION_requestScores = 7;
        static final int TRANSACTION_setActiveScorer = 3;
        static final int TRANSACTION_unregisterNetworkScoreCache = 6;
        static final int TRANSACTION_updateScores = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkScoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetworkScoreService)) {
                return (INetworkScoreService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateScores";
                case 2:
                    return "clearScores";
                case 3:
                    return "setActiveScorer";
                case 4:
                    return "disableScoring";
                case 5:
                    return "registerNetworkScoreCache";
                case 6:
                    return "unregisterNetworkScoreCache";
                case 7:
                    return "requestScores";
                case 8:
                    return "isCallerActiveScorer";
                case 9:
                    return "getActiveScorerPackage";
                case 10:
                    return "getActiveScorer";
                case 11:
                    return "getAllValidScorers";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ScoredNetwork[] scoredNetworkArr = (ScoredNetwork[]) parcel.createTypedArray(ScoredNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateScores = updateScores(scoredNetworkArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateScores);
                    return true;
                case 2:
                    boolean zClearScores = clearScores();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearScores);
                    return true;
                case 3:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean activeScorer = setActiveScorer(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeScorer);
                    return true;
                case 4:
                    disableScoring();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    INetworkScoreCache iNetworkScoreCacheAsInterface = INetworkScoreCache.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerNetworkScoreCache(i3, iNetworkScoreCacheAsInterface, i4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    INetworkScoreCache iNetworkScoreCacheAsInterface2 = INetworkScoreCache.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNetworkScoreCache(i5, iNetworkScoreCacheAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    NetworkKey[] networkKeyArr = (NetworkKey[]) parcel.createTypedArray(NetworkKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestScores = requestScores(networkKeyArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestScores);
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCallerActiveScorer = isCallerActiveScorer(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerActiveScorer);
                    return true;
                case 9:
                    String activeScorerPackage = getActiveScorerPackage();
                    parcel2.writeNoException();
                    parcel2.writeString(activeScorerPackage);
                    return true;
                case 10:
                    NetworkScorerAppData activeScorer2 = getActiveScorer();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeScorer2, 1);
                    return true;
                case 11:
                    List<NetworkScorerAppData> allValidScorers = getAllValidScorers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allValidScorers, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INetworkScoreService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkScoreService
            public boolean updateScores(ScoredNetwork[] scoredNetworkArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(scoredNetworkArr, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean clearScores() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean setActiveScorer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public void disableScoring() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public void registerNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iNetworkScoreCache);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public void unregisterNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iNetworkScoreCache);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean requestScores(NetworkKey[] networkKeyArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(networkKeyArr, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean isCallerActiveScorer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public String getActiveScorerPackage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public NetworkScorerAppData getActiveScorer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NetworkScorerAppData) parcelObtain2.readTypedObject(NetworkScorerAppData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(NetworkScorerAppData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
