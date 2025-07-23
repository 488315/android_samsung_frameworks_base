package android.hardware.tv.tuner;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFrontendCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IFrontendCallback".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onEvent(int i) throws RemoteException;

    void onScanMessage(int i, FrontendScanMessage frontendScanMessage) throws RemoteException;

    public static class Default implements IFrontendCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontendCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFrontendCallback
        public void onEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontendCallback
        public void onScanMessage(int i, FrontendScanMessage frontendScanMessage) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontendCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IFrontendCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onScanMessage = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IFrontendCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFrontendCallback)) {
                return (IFrontendCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEvent(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                FrontendScanMessage frontendScanMessage = (FrontendScanMessage) parcel.readTypedObject(FrontendScanMessage.CREATOR);
                parcel.enforceNoDataAvail();
                onScanMessage(readInt2, frontendScanMessage);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFrontendCallback {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.tv.tuner.IFrontendCallback
            public void onEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontendCallback
            public void onScanMessage(int i, FrontendScanMessage frontendScanMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(frontendScanMessage, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onScanMessage is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontendCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.tv.tuner.IFrontendCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
