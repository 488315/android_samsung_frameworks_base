package android.media.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvRemoteServiceInput extends IInterface {

    public static class Default implements ITvRemoteServiceInput {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void clearInputBridge(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void closeInputBridge(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void openGamepadBridge(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void openInputBridge(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendGamepadAxisValue(IBinder iBinder, int i, float f) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendGamepadKeyDown(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendGamepadKeyUp(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendKeyDown(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendKeyUp(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendPointerDown(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendPointerSync(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendPointerUp(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendTimestamp(IBinder iBinder, long j) throws RemoteException {
        }
    }

    void clearInputBridge(IBinder iBinder) throws RemoteException;

    void closeInputBridge(IBinder iBinder) throws RemoteException;

    void openGamepadBridge(IBinder iBinder, String str) throws RemoteException;

    void openInputBridge(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException;

    void sendGamepadAxisValue(IBinder iBinder, int i, float f) throws RemoteException;

    void sendGamepadKeyDown(IBinder iBinder, int i) throws RemoteException;

    void sendGamepadKeyUp(IBinder iBinder, int i) throws RemoteException;

    void sendKeyDown(IBinder iBinder, int i) throws RemoteException;

    void sendKeyUp(IBinder iBinder, int i) throws RemoteException;

    void sendPointerDown(IBinder iBinder, int i, int i2, int i3) throws RemoteException;

    void sendPointerSync(IBinder iBinder) throws RemoteException;

    void sendPointerUp(IBinder iBinder, int i) throws RemoteException;

    void sendTimestamp(IBinder iBinder, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvRemoteServiceInput {
        public static final String DESCRIPTOR = "android.media.tv.ITvRemoteServiceInput";
        static final int TRANSACTION_clearInputBridge = 3;
        static final int TRANSACTION_closeInputBridge = 2;
        static final int TRANSACTION_openGamepadBridge = 10;
        static final int TRANSACTION_openInputBridge = 1;
        static final int TRANSACTION_sendGamepadAxisValue = 13;
        static final int TRANSACTION_sendGamepadKeyDown = 11;
        static final int TRANSACTION_sendGamepadKeyUp = 12;
        static final int TRANSACTION_sendKeyDown = 5;
        static final int TRANSACTION_sendKeyUp = 6;
        static final int TRANSACTION_sendPointerDown = 7;
        static final int TRANSACTION_sendPointerSync = 9;
        static final int TRANSACTION_sendPointerUp = 8;
        static final int TRANSACTION_sendTimestamp = 4;

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

        public static ITvRemoteServiceInput asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvRemoteServiceInput)) {
                return (ITvRemoteServiceInput) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openInputBridge";
                case 2:
                    return "closeInputBridge";
                case 3:
                    return "clearInputBridge";
                case 4:
                    return "sendTimestamp";
                case 5:
                    return "sendKeyDown";
                case 6:
                    return "sendKeyUp";
                case 7:
                    return "sendPointerDown";
                case 8:
                    return "sendPointerUp";
                case 9:
                    return "sendPointerSync";
                case 10:
                    return "openGamepadBridge";
                case 11:
                    return "sendGamepadKeyDown";
                case 12:
                    return "sendGamepadKeyUp";
                case 13:
                    return "sendGamepadAxisValue";
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
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    openInputBridge(strongBinder, string, i3, i4, i5);
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeInputBridge(strongBinder2);
                    return true;
                case 3:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    clearInputBridge(strongBinder3);
                    return true;
                case 4:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendTimestamp(strongBinder4, j);
                    return true;
                case 5:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendKeyDown(strongBinder5, i6);
                    return true;
                case 6:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendKeyUp(strongBinder6, i7);
                    return true;
                case 7:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendPointerDown(strongBinder7, i8, i9, i10);
                    return true;
                case 8:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendPointerUp(strongBinder8, i11);
                    return true;
                case 9:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    sendPointerSync(strongBinder9);
                    return true;
                case 10:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    openGamepadBridge(strongBinder10, string2);
                    return true;
                case 11:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendGamepadKeyDown(strongBinder11, i12);
                    return true;
                case 12:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendGamepadKeyUp(strongBinder12, i13);
                    return true;
                case 13:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i14 = parcel.readInt();
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    sendGamepadAxisValue(strongBinder13, i14, f);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvRemoteServiceInput {
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

            @Override // android.media.tv.ITvRemoteServiceInput
            public void openInputBridge(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void closeInputBridge(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void clearInputBridge(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendTimestamp(IBinder iBinder, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendKeyDown(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendKeyUp(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendPointerDown(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendPointerUp(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendPointerSync(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void openGamepadBridge(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendGamepadKeyDown(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendGamepadKeyUp(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendGamepadAxisValue(IBinder iBinder, int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
