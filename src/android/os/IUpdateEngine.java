package android.os;

import android.os.IUpdateEngineCallback;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes3.dex */
public interface IUpdateEngine extends IInterface {

    public static class Default implements IUpdateEngine {
        @Override // android.os.IUpdateEngine
        public long allocateSpaceForPayload(String str, String[] strArr) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IUpdateEngine
        public void applyPayload(String str, long j, long j2, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IUpdateEngine
        public boolean bind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngine
        public void cancel() throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void cleanupSuccessfulUpdate(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void resetShouldSwitchSlotOnReboot() throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void resetStatus() throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void resume() throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void setShouldSwitchSlotOnReboot(String str) throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void suspend() throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void triggerPostinstall(String str) throws RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public boolean unbind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngine
        public boolean verifyPayloadApplicable(String str) throws RemoteException {
            return false;
        }
    }

    long allocateSpaceForPayload(String str, String[] strArr) throws RemoteException;

    void applyPayload(String str, long j, long j2, String[] strArr) throws RemoteException;

    void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException;

    boolean bind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException;

    void cancel() throws RemoteException;

    void cleanupSuccessfulUpdate(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException;

    void resetShouldSwitchSlotOnReboot() throws RemoteException;

    void resetStatus() throws RemoteException;

    void resume() throws RemoteException;

    void setShouldSwitchSlotOnReboot(String str) throws RemoteException;

    void suspend() throws RemoteException;

    void triggerPostinstall(String str) throws RemoteException;

    boolean unbind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException;

    boolean verifyPayloadApplicable(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IUpdateEngine {
        public static final String DESCRIPTOR = "android.os.IUpdateEngine";
        static final int TRANSACTION_allocateSpaceForPayload = 12;
        static final int TRANSACTION_applyPayload = 1;
        static final int TRANSACTION_applyPayloadFd = 2;
        static final int TRANSACTION_bind = 3;
        static final int TRANSACTION_cancel = 7;
        static final int TRANSACTION_cleanupSuccessfulUpdate = 13;
        static final int TRANSACTION_resetShouldSwitchSlotOnReboot = 10;
        static final int TRANSACTION_resetStatus = 8;
        static final int TRANSACTION_resume = 6;
        static final int TRANSACTION_setShouldSwitchSlotOnReboot = 9;
        static final int TRANSACTION_suspend = 5;
        static final int TRANSACTION_triggerPostinstall = 14;
        static final int TRANSACTION_unbind = 4;
        static final int TRANSACTION_verifyPayloadApplicable = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateEngine asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUpdateEngine)) {
                return (IUpdateEngine) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "applyPayload";
                case 2:
                    return "applyPayloadFd";
                case 3:
                    return "bind";
                case 4:
                    return "unbind";
                case 5:
                    return AudioParameter.VALUE_SUSPEND;
                case 6:
                    return "resume";
                case 7:
                    return "cancel";
                case 8:
                    return "resetStatus";
                case 9:
                    return "setShouldSwitchSlotOnReboot";
                case 10:
                    return "resetShouldSwitchSlotOnReboot";
                case 11:
                    return "verifyPayloadApplicable";
                case 12:
                    return "allocateSpaceForPayload";
                case 13:
                    return "cleanupSuccessfulUpdate";
                case 14:
                    return "triggerPostinstall";
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
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayload(readString, readLong, readLong2, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayloadFd(parcelFileDescriptor, readLong3, readLong4, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IUpdateEngineCallback asInterface = IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean bind = bind(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bind);
                    return true;
                case 4:
                    IUpdateEngineCallback asInterface2 = IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unbind = unbind(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unbind);
                    return true;
                case 5:
                    suspend();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    resume();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    resetStatus();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setShouldSwitchSlotOnReboot(readString2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    resetShouldSwitchSlotOnReboot();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean verifyPayloadApplicable = verifyPayloadApplicable(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(verifyPayloadApplicable);
                    return true;
                case 12:
                    String readString4 = parcel.readString();
                    String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    long allocateSpaceForPayload = allocateSpaceForPayload(readString4, createStringArray3);
                    parcel2.writeNoException();
                    parcel2.writeLong(allocateSpaceForPayload);
                    return true;
                case 13:
                    IUpdateEngineCallback asInterface3 = IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cleanupSuccessfulUpdate(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerPostinstall(readString5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUpdateEngine {
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

            @Override // android.os.IUpdateEngine
            public void applyPayload(String str, long j, long j2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean bind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean unbind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void suspend() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resume() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void cancel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resetStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void setShouldSwitchSlotOnReboot(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resetShouldSwitchSlotOnReboot() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean verifyPayloadApplicable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public long allocateSpaceForPayload(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void cleanupSuccessfulUpdate(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void triggerPostinstall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
