package android.app;

import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAppTask extends IInterface {

    public static class Default implements IAppTask {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IAppTask
        public void finishAndRemoveTask() throws RemoteException {
        }

        @Override // android.app.IAppTask
        public ActivityManager.RecentTaskInfo getTaskInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.IAppTask
        public void moveToFront(IApplicationThread iApplicationThread, String str) throws RemoteException {
        }

        @Override // android.app.IAppTask
        public void setExcludeFromRecents(boolean z) throws RemoteException {
        }

        @Override // android.app.IAppTask
        public int startActivity(IBinder iBinder, String str, String str2, Intent intent, String str3, Bundle bundle) throws RemoteException {
            return 0;
        }
    }

    void finishAndRemoveTask() throws RemoteException;

    ActivityManager.RecentTaskInfo getTaskInfo() throws RemoteException;

    void moveToFront(IApplicationThread iApplicationThread, String str) throws RemoteException;

    void setExcludeFromRecents(boolean z) throws RemoteException;

    int startActivity(IBinder iBinder, String str, String str2, Intent intent, String str3, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppTask {
        public static final String DESCRIPTOR = "android.app.IAppTask";
        static final int TRANSACTION_finishAndRemoveTask = 1;
        static final int TRANSACTION_getTaskInfo = 2;
        static final int TRANSACTION_moveToFront = 3;
        static final int TRANSACTION_setExcludeFromRecents = 5;
        static final int TRANSACTION_startActivity = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppTask asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppTask)) {
                return (IAppTask) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "finishAndRemoveTask";
            }
            if (i == 2) {
                return "getTaskInfo";
            }
            if (i == 3) {
                return "moveToFront";
            }
            if (i == 4) {
                return "startActivity";
            }
            if (i != 5) {
                return null;
            }
            return "setExcludeFromRecents";
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
            if (i == 1) {
                finishAndRemoveTask();
                parcel2.writeNoException();
            } else if (i == 2) {
                ActivityManager.RecentTaskInfo taskInfo = getTaskInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(taskInfo, 1);
            } else if (i == 3) {
                IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                moveToFront(asInterface, readString);
                parcel2.writeNoException();
            } else if (i == 4) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                String readString4 = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int startActivity = startActivity(readStrongBinder, readString2, readString3, intent, readString4, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(startActivity);
            } else if (i == 5) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setExcludeFromRecents(readBoolean);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAppTask {
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

            @Override // android.app.IAppTask
            public void finishAndRemoveTask() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public ActivityManager.RecentTaskInfo getTaskInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityManager.RecentTaskInfo) obtain2.readTypedObject(ActivityManager.RecentTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public void moveToFront(IApplicationThread iApplicationThread, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public int startActivity(IBinder iBinder, String str, String str2, Intent intent, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public void setExcludeFromRecents(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
