package android.apphibernation;

import android.apphibernation.IAppHibernationService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IAppHibernationService extends IInterface {
    public static final String DESCRIPTOR = "android.apphibernation.IAppHibernationService";

    public static class Default implements IAppHibernationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.apphibernation.IAppHibernationService
        public List<String> getHibernatingPackagesForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.apphibernation.IAppHibernationService
        public Map<String, HibernationStats> getHibernationStatsForUser(List<String> list, int i) throws RemoteException {
            return null;
        }

        @Override // android.apphibernation.IAppHibernationService
        public boolean isHibernatingForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.apphibernation.IAppHibernationService
        public boolean isHibernatingGlobally(String str) throws RemoteException {
            return false;
        }

        @Override // android.apphibernation.IAppHibernationService
        public boolean isOatArtifactDeletionEnabled() throws RemoteException {
            return false;
        }

        @Override // android.apphibernation.IAppHibernationService
        public void setHibernatingForUser(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.apphibernation.IAppHibernationService
        public void setHibernatingGlobally(String str, boolean z) throws RemoteException {
        }
    }

    List<String> getHibernatingPackagesForUser(int i) throws RemoteException;

    Map<String, HibernationStats> getHibernationStatsForUser(List<String> list, int i) throws RemoteException;

    boolean isHibernatingForUser(String str, int i) throws RemoteException;

    boolean isHibernatingGlobally(String str) throws RemoteException;

    boolean isOatArtifactDeletionEnabled() throws RemoteException;

    void setHibernatingForUser(String str, int i, boolean z) throws RemoteException;

    void setHibernatingGlobally(String str, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppHibernationService {
        static final int TRANSACTION_getHibernatingPackagesForUser = 5;
        static final int TRANSACTION_getHibernationStatsForUser = 6;
        static final int TRANSACTION_isHibernatingForUser = 1;
        static final int TRANSACTION_isHibernatingGlobally = 3;
        static final int TRANSACTION_isOatArtifactDeletionEnabled = 7;
        static final int TRANSACTION_setHibernatingForUser = 2;
        static final int TRANSACTION_setHibernatingGlobally = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IAppHibernationService.DESCRIPTOR);
        }

        public static IAppHibernationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppHibernationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppHibernationService)) {
                return (IAppHibernationService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isHibernatingForUser";
                case 2:
                    return "setHibernatingForUser";
                case 3:
                    return "isHibernatingGlobally";
                case 4:
                    return "setHibernatingGlobally";
                case 5:
                    return "getHibernatingPackagesForUser";
                case 6:
                    return "getHibernationStatsForUser";
                case 7:
                    return "isOatArtifactDeletionEnabled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppHibernationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppHibernationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isHibernatingForUser = isHibernatingForUser(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHibernatingForUser);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHibernatingForUser(readString2, readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHibernatingGlobally = isHibernatingGlobally(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHibernatingGlobally);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHibernatingGlobally(readString4, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> hibernatingPackagesForUser = getHibernatingPackagesForUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(hibernatingPackagesForUser);
                    return true;
                case 6:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map<String, HibernationStats> hibernationStatsForUser = getHibernationStatsForUser(createStringArrayList, readInt4);
                    parcel2.writeNoException();
                    if (hibernationStatsForUser == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(hibernationStatsForUser.size());
                        hibernationStatsForUser.forEach(new BiConsumer() { // from class: android.apphibernation.IAppHibernationService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAppHibernationService.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (HibernationStats) obj2);
                            }
                        });
                    }
                    return true;
                case 7:
                    boolean isOatArtifactDeletionEnabled = isOatArtifactDeletionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOatArtifactDeletionEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, HibernationStats hibernationStats) {
            parcel.writeString(str);
            parcel.writeTypedObject(hibernationStats, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IAppHibernationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppHibernationService.DESCRIPTOR;
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isHibernatingForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public void setHibernatingForUser(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isHibernatingGlobally(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public void setHibernatingGlobally(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public List<String> getHibernatingPackagesForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public Map<String, HibernationStats> getHibernationStatsForUser(List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.apphibernation.IAppHibernationService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), (HibernationStats) Parcel.this.readTypedObject(HibernationStats.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isOatArtifactDeletionEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
