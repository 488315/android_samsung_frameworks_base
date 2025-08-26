package android.os;

import android.os.IDumpstateListener;
import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public interface IDumpstate extends IInterface {
    public static final int BUGREPORT_FLAG_DEFER_CONSENT = 2;
    public static final int BUGREPORT_FLAG_KEEP_BUGREPORT_ON_RETRIEVAL = 4;
    public static final int BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA = 1;
    public static final int BUGREPORT_MODE_APP_ANR = 16;
    public static final int BUGREPORT_MODE_APP_ERROR = 14;
    public static final int BUGREPORT_MODE_APP_NATIVE = 15;
    public static final int BUGREPORT_MODE_BOOT_DELAY = 8;
    public static final int BUGREPORT_MODE_BOOT_ENOSPC = 9;
    public static final int BUGREPORT_MODE_BY_KEY = 21;
    public static final int BUGREPORT_MODE_DEFAULT = 6;
    public static final int BUGREPORT_MODE_ENOSPC = 18;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_LIGHT = 17;
    public static final int BUGREPORT_MODE_ONBOARDING = 7;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_SHUTDOWN_BROADCAST = 19;
    public static final int BUGREPORT_MODE_SHUTDOWN_DELAY = 20;
    public static final int BUGREPORT_MODE_SVCAGENT = 22;
    public static final int BUGREPORT_MODE_SYS_ERROR = 11;
    public static final int BUGREPORT_MODE_SYS_NATIVE = 12;
    public static final int BUGREPORT_MODE_SYS_RESCUE = 10;
    public static final int BUGREPORT_MODE_SYS_WATCHDOG = 13;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;
    public static final String DESCRIPTOR = "android.os.IDumpstate";

    public static class Default implements IDumpstate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IDumpstate
        public void cancelBugreport(int i, String str) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void preDumpUiData(String str) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) throws RemoteException {
        }

        @Override // android.os.IDumpstate
        public void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) throws RemoteException {
        }
    }

    void cancelBugreport(int i, String str) throws RemoteException;

    void preDumpUiData(String str) throws RemoteException;

    void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) throws RemoteException;

    void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDumpstate {
        static final int TRANSACTION_cancelBugreport = 3;
        static final int TRANSACTION_preDumpUiData = 1;
        static final int TRANSACTION_retrieveBugreport = 4;
        static final int TRANSACTION_startBugreport = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IDumpstate.DESCRIPTOR);
        }

        public static IDumpstate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDumpstate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDumpstate)) {
                return (IDumpstate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "preDumpUiData";
            }
            if (i == 2) {
                return "startBugreport";
            }
            if (i == 3) {
                return "cancelBugreport";
            }
            if (i != 4) {
                return null;
            }
            return "retrieveBugreport";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDumpstate.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDumpstate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                preDumpUiData(string);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i3 = parcel.readInt();
                String string2 = parcel.readString();
                FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                FileDescriptor rawFileDescriptor2 = parcel.readRawFileDescriptor();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                IDumpstateListener iDumpstateListenerAsInterface = IDumpstateListener.Stub.asInterface(parcel.readStrongBinder());
                boolean z = parcel.readBoolean();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                startBugreport(i3, string2, rawFileDescriptor, rawFileDescriptor2, i4, i5, iDumpstateListenerAsInterface, z, z2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i6 = parcel.readInt();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                cancelBugreport(i6, string3);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i7 = parcel.readInt();
                String string4 = parcel.readString();
                int i8 = parcel.readInt();
                FileDescriptor rawFileDescriptor3 = parcel.readRawFileDescriptor();
                String string5 = parcel.readString();
                boolean z3 = parcel.readBoolean();
                boolean z4 = parcel.readBoolean();
                IDumpstateListener iDumpstateListenerAsInterface2 = IDumpstateListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                retrieveBugreport(i7, string4, i8, rawFileDescriptor3, string5, z3, z4, iDumpstateListenerAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDumpstate {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDumpstate.DESCRIPTOR;
            }

            @Override // android.os.IDumpstate
            public void preDumpUiData(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, int i3, IDumpstateListener iDumpstateListener, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iDumpstateListener);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void cancelBugreport(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void retrieveBugreport(int i, String str, int i2, FileDescriptor fileDescriptor, String str2, boolean z, boolean z2, IDumpstateListener iDumpstateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDumpstate.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeStrongInterface(iDumpstateListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
