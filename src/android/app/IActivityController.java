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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActivityController)) {
                return (IActivityController) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zActivityStarting = activityStarting(intent, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivityStarting);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zActivityResuming = activityResuming(string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivityResuming);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    long j = parcel.readLong();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAppCrashed = appCrashed(string3, i3, string4, string5, j, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAppCrashed);
                    return true;
                case 4:
                    String string7 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAppEarlyNotResponding = appEarlyNotResponding(string7, i4, string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAppEarlyNotResponding);
                    return true;
                case 5:
                    String string9 = parcel.readString();
                    int i5 = parcel.readInt();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAppNotResponding = appNotResponding(string9, i5, string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAppNotResponding);
                    return true;
                case 6:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSystemNotResponding = systemNotResponding(string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSystemNotResponding);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public boolean activityResuming(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int appEarlyNotResponding(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int appNotResponding(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int systemNotResponding(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
