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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUpdateEngine)) {
                return (IUpdateEngine) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayload(string, j, j2, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayloadFd(parcelFileDescriptor, j3, j4, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IUpdateEngineCallback iUpdateEngineCallbackAsInterface = IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zBind = bind(iUpdateEngineCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBind);
                    return true;
                case 4:
                    IUpdateEngineCallback iUpdateEngineCallbackAsInterface2 = IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnbind = unbind(iUpdateEngineCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnbind);
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
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setShouldSwitchSlotOnReboot(string2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    resetShouldSwitchSlotOnReboot();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zVerifyPayloadApplicable = verifyPayloadApplicable(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zVerifyPayloadApplicable);
                    return true;
                case 12:
                    String string4 = parcel.readString();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    long jAllocateSpaceForPayload = allocateSpaceForPayload(string4, strArrCreateStringArray3);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAllocateSpaceForPayload);
                    return true;
                case 13:
                    IUpdateEngineCallback iUpdateEngineCallbackAsInterface3 = IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cleanupSuccessfulUpdate(iUpdateEngineCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerPostinstall(string5);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean bind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean unbind(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void suspend() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resume() throws RemoteException {
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

            @Override // android.os.IUpdateEngine
            public void cancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resetStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void setShouldSwitchSlotOnReboot(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resetShouldSwitchSlotOnReboot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean verifyPayloadApplicable(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public long allocateSpaceForPayload(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void cleanupSuccessfulUpdate(IUpdateEngineCallback iUpdateEngineCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void triggerPostinstall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
