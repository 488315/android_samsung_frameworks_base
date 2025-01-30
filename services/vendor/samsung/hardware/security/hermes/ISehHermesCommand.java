package vendor.samsung.hardware.security.hermes;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISehHermesCommand extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$security$hermes$ISehHermesCommand".replace('$', '.');

    public class Default implements ISehHermesCommand {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult getBigdataLog() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult getSecureHWInfo() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult provisioning() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult secnvmPowerOn() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult selftest() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult terminateService() {
            return null;
        }

        @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
        public SehCommandResult verifyProvisioning() {
            return null;
        }
    }

    SehCommandResult getBigdataLog();

    String getInterfaceHash();

    int getInterfaceVersion();

    SehCommandResult getSecureHWInfo();

    SehCommandResult provisioning();

    SehCommandResult saveBigdataLog(byte[] bArr);

    SehCommandResult secnvmPowerOff();

    SehCommandResult secnvmPowerOn();

    SehCommandResult selftest();

    SehCommandResult terminateService();

    SehCommandResult updateCryptoFW();

    SehCommandResult verifyProvisioning();

    public abstract class Stub extends Binder implements ISehHermesCommand {
        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "selftest";
                case 2:
                    return "provisioning";
                case 3:
                    return "verifyProvisioning";
                case 4:
                    return "getSecureHWInfo";
                case 5:
                    return "updateCryptoFW";
                case 6:
                    return "secnvmPowerOn";
                case 7:
                    return "secnvmPowerOff";
                case 8:
                    return "terminateService";
                case 9:
                    return "saveBigdataLog";
                case 10:
                    return "getBigdataLog";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
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
            attachInterface(this, ISehHermesCommand.DESCRIPTOR);
        }

        public static ISehHermesCommand asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehHermesCommand.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehHermesCommand)) {
                return (ISehHermesCommand) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehHermesCommand.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    switch (i) {
                        case 1:
                            SehCommandResult selftest = selftest();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(selftest, 1);
                            return true;
                        case 2:
                            SehCommandResult provisioning = provisioning();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(provisioning, 1);
                            return true;
                        case 3:
                            SehCommandResult verifyProvisioning = verifyProvisioning();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(verifyProvisioning, 1);
                            return true;
                        case 4:
                            SehCommandResult secureHWInfo = getSecureHWInfo();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(secureHWInfo, 1);
                            return true;
                        case 5:
                            SehCommandResult updateCryptoFW = updateCryptoFW();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(updateCryptoFW, 1);
                            return true;
                        case 6:
                            SehCommandResult secnvmPowerOn = secnvmPowerOn();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(secnvmPowerOn, 1);
                            return true;
                        case 7:
                            SehCommandResult secnvmPowerOff = secnvmPowerOff();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(secnvmPowerOff, 1);
                            return true;
                        case 8:
                            SehCommandResult terminateService = terminateService();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(terminateService, 1);
                            return true;
                        case 9:
                            byte[] createByteArray = parcel.createByteArray();
                            parcel.enforceNoDataAvail();
                            SehCommandResult saveBigdataLog = saveBigdataLog(createByteArray);
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(saveBigdataLog, 1);
                            return true;
                        case 10:
                            SehCommandResult bigdataLog = getBigdataLog();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(bigdataLog, 1);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements ISehHermesCommand {
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

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult selftest() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method selftest is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult provisioning() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method provisioning is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult verifyProvisioning() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method verifyProvisioning is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult getSecureHWInfo() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSecureHWInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult secnvmPowerOn() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method secnvmPowerOn is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult terminateService() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method terminateService is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.security.hermes.ISehHermesCommand
            public SehCommandResult getBigdataLog() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getBigdataLog is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
