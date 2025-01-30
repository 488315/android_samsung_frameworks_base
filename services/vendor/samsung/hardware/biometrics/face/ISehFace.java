package vendor.samsung.hardware.biometrics.face;

import android.hardware.biometrics.face.ISessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.biometrics.face.ISehSession;

/* loaded from: classes2.dex */
public interface ISehFace extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$biometrics$face$ISehFace".replace('$', '.');

    public class Default implements ISehFace {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehFace
        public ISehSession createSession(int i, int i2, ISessionCallback iSessionCallback) {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehFace
        public int getInterfaceVersion() {
            return 0;
        }
    }

    ISehSession createSession(int i, int i2, ISessionCallback iSessionCallback);

    String getInterfaceHash();

    int getInterfaceVersion();

    int getServicePid();

    public abstract class Stub extends Binder implements ISehFace {
        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getServicePid";
            }
            if (i == 2) {
                return "createSession";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehFace.DESCRIPTOR);
        }

        public static ISehFace asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehFace.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehFace)) {
                return (ISehFace) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehFace.DESCRIPTOR;
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
                        int servicePid = getServicePid();
                        parcel2.writeNoException();
                        parcel2.writeInt(servicePid);
                        break;
                    } else if (i == 2) {
                        int readInt = parcel.readInt();
                        int readInt2 = parcel.readInt();
                        ISessionCallback asInterface = ISessionCallback.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        ISehSession createSession = createSession(readInt, readInt2, asInterface);
                        parcel2.writeNoException();
                        parcel2.writeStrongInterface(createSession);
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements ISehFace {
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

            @Override // vendor.samsung.hardware.biometrics.face.ISehFace
            public ISehSession createSession(int i, int i2, ISessionCallback iSessionCallback) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehFace.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iSessionCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createSession is unimplemented.");
                    }
                    obtain2.readException();
                    return ISehSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehFace
            public int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehFace.DESCRIPTOR);
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
        }
    }
}
