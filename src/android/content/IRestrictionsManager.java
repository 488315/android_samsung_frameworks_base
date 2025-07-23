package android.content;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IRestrictionsManager extends IInterface {

    public static class Default implements IRestrictionsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.IRestrictionsManager
        public Intent createLocalApprovalIntent() throws RemoteException {
            return null;
        }

        @Override // android.content.IRestrictionsManager
        public Bundle getApplicationRestrictions(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.IRestrictionsManager
        public List<Bundle> getApplicationRestrictionsPerAdminForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.content.IRestrictionsManager
        public boolean hasRestrictionsProvider() throws RemoteException {
            return false;
        }

        @Override // android.content.IRestrictionsManager
        public void notifyPermissionResponse(String str, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override // android.content.IRestrictionsManager
        public void requestPermission(String str, String str2, String str3, PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    Intent createLocalApprovalIntent() throws RemoteException;

    Bundle getApplicationRestrictions(String str) throws RemoteException;

    List<Bundle> getApplicationRestrictionsPerAdminForUser(int i, String str) throws RemoteException;

    boolean hasRestrictionsProvider() throws RemoteException;

    void notifyPermissionResponse(String str, PersistableBundle persistableBundle) throws RemoteException;

    void requestPermission(String str, String str2, String str3, PersistableBundle persistableBundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IRestrictionsManager {
        public static final String DESCRIPTOR = "android.content.IRestrictionsManager";
        static final int TRANSACTION_createLocalApprovalIntent = 6;
        static final int TRANSACTION_getApplicationRestrictions = 1;
        static final int TRANSACTION_getApplicationRestrictionsPerAdminForUser = 2;
        static final int TRANSACTION_hasRestrictionsProvider = 3;
        static final int TRANSACTION_notifyPermissionResponse = 5;
        static final int TRANSACTION_requestPermission = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRestrictionsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRestrictionsManager)) {
                return (IRestrictionsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getApplicationRestrictions";
                case 2:
                    return "getApplicationRestrictionsPerAdminForUser";
                case 3:
                    return "hasRestrictionsProvider";
                case 4:
                    return "requestPermission";
                case 5:
                    return "notifyPermissionResponse";
                case 6:
                    return "createLocalApprovalIntent";
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictions = getApplicationRestrictions(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Bundle> applicationRestrictionsPerAdminForUser = getApplicationRestrictionsPerAdminForUser(readInt, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(applicationRestrictionsPerAdminForUser, 1);
                    return true;
                case 3:
                    boolean hasRestrictionsProvider = hasRestrictionsProvider();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRestrictionsProvider);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPermission(readString3, readString4, readString5, persistableBundle);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPermissionResponse(readString6, persistableBundle2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    Intent createLocalApprovalIntent = createLocalApprovalIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createLocalApprovalIntent, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRestrictionsManager {
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

            @Override // android.content.IRestrictionsManager
            public Bundle getApplicationRestrictions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public List<Bundle> getApplicationRestrictionsPerAdminForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public boolean hasRestrictionsProvider() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public void requestPermission(String str, String str2, String str3, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public void notifyPermissionResponse(String str, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRestrictionsManager
            public Intent createLocalApprovalIntent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
