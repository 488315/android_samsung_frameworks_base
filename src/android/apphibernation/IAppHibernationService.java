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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAppHibernationService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppHibernationService)) {
                return (IAppHibernationService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsHibernatingForUser = isHibernatingForUser(string, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHibernatingForUser);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHibernatingForUser(string2, i4, z);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHibernatingGlobally = isHibernatingGlobally(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHibernatingGlobally);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHibernatingGlobally(string4, z2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> hibernatingPackagesForUser = getHibernatingPackagesForUser(i5);
                    parcel2.writeNoException();
                    parcel2.writeStringList(hibernatingPackagesForUser);
                    return true;
                case 6:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map<String, HibernationStats> hibernationStatsForUser = getHibernationStatsForUser(arrayListCreateStringArrayList, i6);
                    parcel2.writeNoException();
                    if (hibernationStatsForUser == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(hibernationStatsForUser.size());
                        hibernationStatsForUser.forEach(new BiConsumer() { // from class: android.apphibernation.IAppHibernationService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAppHibernationService.Stub.lambda$onTransact$0(parcel2, (String) obj, (HibernationStats) obj2);
                            }
                        });
                    }
                    return true;
                case 7:
                    boolean zIsOatArtifactDeletionEnabled = isOatArtifactDeletionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOatArtifactDeletionEnabled);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public void setHibernatingForUser(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isHibernatingGlobally(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public void setHibernatingGlobally(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public List<String> getHibernatingPackagesForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public Map<String, HibernationStats> getHibernationStatsForUser(List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    final HashMap map = i2 < 0 ? null : new HashMap();
                    IntStream.range(0, i2).forEach(new IntConsumer() { // from class: android.apphibernation.IAppHibernationService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), (HibernationStats) parcel.readTypedObject(HibernationStats.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isOatArtifactDeletionEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppHibernationService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
