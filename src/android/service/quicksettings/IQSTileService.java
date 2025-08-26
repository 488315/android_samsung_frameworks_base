package android.service.quicksettings;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.widget.RemoteViews;

/* loaded from: classes3.dex */
public interface IQSTileService extends IInterface {

    public static class Default implements IQSTileService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.quicksettings.IQSTileService
        public void onClick(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSTileService
        public void onStartListening() throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSTileService
        public void onStopListening() throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSTileService
        public void onTileAdded() throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSTileService
        public void onTileRemoved() throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSTileService
        public void onUnlockComplete() throws RemoteException {
        }

        @Override // android.service.quicksettings.IQSTileService
        public RemoteViews semGetDetailView() throws RemoteException {
            return null;
        }

        @Override // android.service.quicksettings.IQSTileService
        public CharSequence semGetDetailViewTitle() throws RemoteException {
            return null;
        }

        @Override // android.service.quicksettings.IQSTileService
        public Intent semGetSettingsIntent() throws RemoteException {
            return null;
        }

        @Override // android.service.quicksettings.IQSTileService
        public boolean semIsToggleButtonChecked() throws RemoteException {
            return false;
        }

        @Override // android.service.quicksettings.IQSTileService
        public boolean semIsToggleButtonExists() throws RemoteException {
            return false;
        }

        @Override // android.service.quicksettings.IQSTileService
        public void semSetToggleButtonChecked(boolean z) throws RemoteException {
        }
    }

    void onClick(IBinder iBinder) throws RemoteException;

    void onStartListening() throws RemoteException;

    void onStopListening() throws RemoteException;

    void onTileAdded() throws RemoteException;

    void onTileRemoved() throws RemoteException;

    void onUnlockComplete() throws RemoteException;

    RemoteViews semGetDetailView() throws RemoteException;

    CharSequence semGetDetailViewTitle() throws RemoteException;

    Intent semGetSettingsIntent() throws RemoteException;

    boolean semIsToggleButtonChecked() throws RemoteException;

    boolean semIsToggleButtonExists() throws RemoteException;

    void semSetToggleButtonChecked(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IQSTileService {
        public static final String DESCRIPTOR = "android.service.quicksettings.IQSTileService";
        static final int TRANSACTION_onClick = 5;
        static final int TRANSACTION_onStartListening = 3;
        static final int TRANSACTION_onStopListening = 4;
        static final int TRANSACTION_onTileAdded = 1;
        static final int TRANSACTION_onTileRemoved = 2;
        static final int TRANSACTION_onUnlockComplete = 6;
        static final int TRANSACTION_semGetDetailView = 10;
        static final int TRANSACTION_semGetDetailViewTitle = 7;
        static final int TRANSACTION_semGetSettingsIntent = 11;
        static final int TRANSACTION_semIsToggleButtonChecked = 9;
        static final int TRANSACTION_semIsToggleButtonExists = 8;
        static final int TRANSACTION_semSetToggleButtonChecked = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IQSTileService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IQSTileService)) {
                return (IQSTileService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTileAdded";
                case 2:
                    return "onTileRemoved";
                case 3:
                    return "onStartListening";
                case 4:
                    return "onStopListening";
                case 5:
                    return "onClick";
                case 6:
                    return "onUnlockComplete";
                case 7:
                    return "semGetDetailViewTitle";
                case 8:
                    return "semIsToggleButtonExists";
                case 9:
                    return "semIsToggleButtonChecked";
                case 10:
                    return "semGetDetailView";
                case 11:
                    return "semGetSettingsIntent";
                case 12:
                    return "semSetToggleButtonChecked";
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
                    onTileAdded();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onTileRemoved();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    onStartListening();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onStopListening();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onClick(strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    onUnlockComplete();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    CharSequence charSequenceSemGetDetailViewTitle = semGetDetailViewTitle();
                    parcel2.writeNoException();
                    if (charSequenceSemGetDetailViewTitle != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(charSequenceSemGetDetailViewTitle, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    boolean zSemIsToggleButtonExists = semIsToggleButtonExists();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsToggleButtonExists);
                    return true;
                case 9:
                    boolean zSemIsToggleButtonChecked = semIsToggleButtonChecked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsToggleButtonChecked);
                    return true;
                case 10:
                    RemoteViews remoteViewsSemGetDetailView = semGetDetailView();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(remoteViewsSemGetDetailView, 1);
                    return true;
                case 11:
                    Intent intentSemGetSettingsIntent = semGetSettingsIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentSemGetSettingsIntent, 1);
                    return true;
                case 12:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetToggleButtonChecked(z);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IQSTileService {
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

            @Override // android.service.quicksettings.IQSTileService
            public void onTileAdded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public void onTileRemoved() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public void onStartListening() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public void onStopListening() throws RemoteException {
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

            @Override // android.service.quicksettings.IQSTileService
            public void onClick(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public void onUnlockComplete() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public CharSequence semGetDetailViewTitle() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public boolean semIsToggleButtonExists() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public boolean semIsToggleButtonChecked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public RemoteViews semGetDetailView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RemoteViews) parcelObtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public Intent semGetSettingsIntent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSTileService
            public void semSetToggleButtonChecked(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
