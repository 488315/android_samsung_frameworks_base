package android.hardware.biometrics.fingerprint;

import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFingerprint extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$fingerprint$IFingerprint".replace('$', '.');
    public static final String HASH = "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
    public static final int VERSION = 4;

    ISession createSession(int i, int i2, ISessionCallback iSessionCallback) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    SensorProps[] getSensorProps() throws RemoteException;

    public static class Default implements IFingerprint {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public ISession createSession(int i, int i2, ISessionCallback iSessionCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public SensorProps[] getSensorProps() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.IFingerprint
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IFingerprint {
        static final int TRANSACTION_createSession = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSensorProps = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprint asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFingerprint)) {
                return (IFingerprint) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSensorProps";
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

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
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
                SensorProps[] sensorProps = getSensorProps();
                parcel2.writeNoException();
                parcel2.writeTypedArray(sensorProps, 1);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                ISessionCallback asInterface = ISessionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ISession createSession = createSession(readInt, readInt2, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(createSession);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFingerprint {
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

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
            public SensorProps[] getSensorProps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSensorProps is unimplemented.");
                    }
                    obtain2.readException();
                    return (SensorProps[]) obtain2.createTypedArray(SensorProps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
            public ISession createSession(int i, int i2, ISessionCallback iSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iSessionCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createSession is unimplemented.");
                    }
                    obtain2.readException();
                    return ISession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
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

            @Override // android.hardware.biometrics.fingerprint.IFingerprint
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
