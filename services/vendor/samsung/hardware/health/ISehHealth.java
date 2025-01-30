package vendor.samsung.hardware.health;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.health.ISehHealthInfoCallback;

/* loaded from: classes2.dex */
public interface ISehHealth extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$health$ISehHealth".replace('$', '.');

    public class Default implements ISehHealth {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.health.ISehHealth
        public void registerCallback(ISehHealthInfoCallback iSehHealthInfoCallback) {
        }

        @Override // vendor.samsung.hardware.health.ISehHealth
        public void sehWriteEnableToParam(int i, boolean z) {
        }

        @Override // vendor.samsung.hardware.health.ISehHealth
        public void unregisterCallback(ISehHealthInfoCallback iSehHealthInfoCallback) {
        }

        @Override // vendor.samsung.hardware.health.ISehHealth
        public void update() {
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    void registerCallback(ISehHealthInfoCallback iSehHealthInfoCallback);

    void sehWriteEnableToParam(int i, boolean z);

    void unregisterCallback(ISehHealthInfoCallback iSehHealthInfoCallback);

    void update();

    public abstract class Stub extends Binder implements ISehHealth {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehHealth.DESCRIPTOR);
        }

        public static ISehHealth asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehHealth.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehHealth)) {
                return (ISehHealth) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehHealth.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    break;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    break;
                case 1598968902:
                    parcel2.writeString(str);
                    break;
                default:
                    if (i == 1) {
                        ISehHealthInfoCallback asInterface = ISehHealthInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        registerCallback(asInterface);
                        parcel2.writeNoException();
                        break;
                    } else if (i == 2) {
                        ISehHealthInfoCallback asInterface2 = ISehHealthInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        unregisterCallback(asInterface2);
                        parcel2.writeNoException();
                        break;
                    } else if (i == 3) {
                        update();
                        parcel2.writeNoException();
                        break;
                    } else if (i == 4) {
                        int readInt = parcel.readInt();
                        boolean readBoolean = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        sehWriteEnableToParam(readInt, readBoolean);
                        parcel2.writeNoException();
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements ISehHealth {
            public IBinder mRemote;
            public int mCachedVersion = -1;
            public String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // vendor.samsung.hardware.health.ISehHealth
            public void registerCallback(ISehHealthInfoCallback iSehHealthInfoCallback) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHealth.DESCRIPTOR);
                    obtain.writeStrongInterface(iSehHealthInfoCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method registerCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.health.ISehHealth
            public void unregisterCallback(ISehHealthInfoCallback iSehHealthInfoCallback) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHealth.DESCRIPTOR);
                    obtain.writeStrongInterface(iSehHealthInfoCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unregisterCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.health.ISehHealth
            public void update() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method update is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.health.ISehHealth
            public void sehWriteEnableToParam(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHealth.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehWriteEnableToParam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
