package android.app;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IActivityController extends IInterface {

    public static class Default implements IActivityController {
        @Override // android.app.IActivityController
        public boolean activityResuming(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityController
        public boolean activityStarting(Intent intent, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityController
        public boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityController
        public int appEarlyNotResponding(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityController
        public int appNotResponding(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IActivityController
        public int systemNotResponding(String str) throws RemoteException {
            return 0;
        }
    }

    boolean activityResuming(String str) throws RemoteException;

    boolean activityStarting(Intent intent, String str) throws RemoteException;

    boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) throws RemoteException;

    int appEarlyNotResponding(String str, int i, String str2) throws RemoteException;

    int appNotResponding(String str, int i, String str2) throws RemoteException;

    int systemNotResponding(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityController {
        public static final String DESCRIPTOR = "android.app.IActivityController";
        static final int TRANSACTION_activityResuming = 2;
        static final int TRANSACTION_activityStarting = 1;
        static final int TRANSACTION_appCrashed = 3;
        static final int TRANSACTION_appEarlyNotResponding = 4;
        static final int TRANSACTION_appNotResponding = 5;
        static final int TRANSACTION_systemNotResponding = 6;

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

        public static IActivityController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IActivityController)) {
                return (IActivityController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "activityStarting";
                case 2:
                    return "activityResuming";
                case 3:
                    return "appCrashed";
                case 4:
                    return "appEarlyNotResponding";
                case 5:
                    return "appNotResponding";
                case 6:
                    return "systemNotResponding";
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
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean activityStarting = activityStarting(intent, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activityStarting);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean activityResuming = activityResuming(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activityResuming);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    long readLong = parcel.readLong();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean appCrashed = appCrashed(readString3, readInt, readString4, readString5, readLong, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appCrashed);
                    return true;
                case 4:
                    String readString7 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int appEarlyNotResponding = appEarlyNotResponding(readString7, readInt2, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(appEarlyNotResponding);
                    return true;
                case 5:
                    String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int appNotResponding = appNotResponding(readString9, readInt3, readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(appNotResponding);
                    return true;
                case 6:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int systemNotResponding = systemNotResponding(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(systemNotResponding);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IActivityController {
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

            @Override // android.app.IActivityController
            public boolean activityStarting(Intent intent, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public boolean activityResuming(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeLong(j);
                    obtain.writeString(str4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int appEarlyNotResponding(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int appNotResponding(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int systemNotResponding(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
