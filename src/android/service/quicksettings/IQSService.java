package android.service.quicksettings;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IQSService extends IInterface {

    public static class Default implements IQSService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.quicksettings.IQSService
        public Tile getTile(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.service.quicksettings.IQSService
        public boolean isLocked() throws RemoteException {
            return false;
        }

        @Override // android.service.quicksettings.IQSService
        public boolean isSecure() throws RemoteException {
            return false;
        }

        @Override // android.service.quicksettings.IQSService
        public void onDialogHidden(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onShowDialog(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onStartActivity(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onStartSuccessful(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void semFireToggleStateChanged(IBinder iBinder, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void semUpdateDetailView(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void startActivity(IBinder iBinder, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void startUnlockAndRun(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void updateQsTile(Tile tile, IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void updateStatusIcon(IBinder iBinder, Icon icon, String str) throws RemoteException {
        }
    }

    Tile getTile(IBinder iBinder) throws RemoteException;

    boolean isLocked() throws RemoteException;

    boolean isSecure() throws RemoteException;

    void onDialogHidden(IBinder iBinder) throws RemoteException;

    void onShowDialog(IBinder iBinder) throws RemoteException;

    void onStartActivity(IBinder iBinder) throws RemoteException;

    void onStartSuccessful(IBinder iBinder) throws RemoteException;

    void semFireToggleStateChanged(IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    void semUpdateDetailView(IBinder iBinder) throws RemoteException;

    void startActivity(IBinder iBinder, PendingIntent pendingIntent) throws RemoteException;

    void startUnlockAndRun(IBinder iBinder) throws RemoteException;

    void updateQsTile(Tile tile, IBinder iBinder) throws RemoteException;

    void updateStatusIcon(IBinder iBinder, Icon icon, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IQSService {
        public static final String DESCRIPTOR = "android.service.quicksettings.IQSService";
        static final int TRANSACTION_getTile = 1;
        static final int TRANSACTION_isLocked = 7;
        static final int TRANSACTION_isSecure = 8;
        static final int TRANSACTION_onDialogHidden = 10;
        static final int TRANSACTION_onShowDialog = 4;
        static final int TRANSACTION_onStartActivity = 5;
        static final int TRANSACTION_onStartSuccessful = 11;
        static final int TRANSACTION_semFireToggleStateChanged = 13;
        static final int TRANSACTION_semUpdateDetailView = 12;
        static final int TRANSACTION_startActivity = 6;
        static final int TRANSACTION_startUnlockAndRun = 9;
        static final int TRANSACTION_updateQsTile = 2;
        static final int TRANSACTION_updateStatusIcon = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IQSService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IQSService)) {
                return (IQSService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTile";
                case 2:
                    return "updateQsTile";
                case 3:
                    return "updateStatusIcon";
                case 4:
                    return "onShowDialog";
                case 5:
                    return "onStartActivity";
                case 6:
                    return "startActivity";
                case 7:
                    return "isLocked";
                case 8:
                    return "isSecure";
                case 9:
                    return "startUnlockAndRun";
                case 10:
                    return "onDialogHidden";
                case 11:
                    return "onStartSuccessful";
                case 12:
                    return "semUpdateDetailView";
                case 13:
                    return "semFireToggleStateChanged";
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Tile tile = getTile(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tile, 1);
                    return true;
                case 2:
                    Tile tile2 = (Tile) parcel.readTypedObject(Tile.CREATOR);
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateQsTile(tile2, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateStatusIcon(readStrongBinder3, icon, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onShowDialog(readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onStartActivity(readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivity(readStrongBinder6, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isLocked = isLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLocked);
                    return true;
                case 8:
                    boolean isSecure = isSecure();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecure);
                    return true;
                case 9:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startUnlockAndRun(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onDialogHidden(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onStartSuccessful(readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    semUpdateDetailView(readStrongBinder10);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semFireToggleStateChanged(readStrongBinder11, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IQSService {
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

            @Override // android.service.quicksettings.IQSService
            public Tile getTile(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Tile) obtain2.readTypedObject(Tile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void updateQsTile(Tile tile, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tile, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void updateStatusIcon(IBinder iBinder, Icon icon, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onShowDialog(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onStartActivity(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void startActivity(IBinder iBinder, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public boolean isLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public boolean isSecure() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void startUnlockAndRun(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onDialogHidden(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onStartSuccessful(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void semUpdateDetailView(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void semFireToggleStateChanged(IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
