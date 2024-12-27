package vendor.samsung.hardware.security.hermes;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISehHermesCommand extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$security$hermes$ISehHermesCommand".replace('$', '.');

    public abstract class Stub extends Binder implements ISehHermesCommand {

        public final class Proxy implements ISehHermesCommand {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final SehCommandResult getBigdataLog() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final SehCommandResult getSecureHWInfo() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final SehCommandResult provisioning() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final SehCommandResult secnvmPowerOff() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method secnvmPowerOff is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult secnvmPowerOn() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final SehCommandResult selftest() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final SehCommandResult terminateService() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final SehCommandResult updateCryptoFW() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesCommand.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateCryptoFW is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult verifyProvisioning() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
        }

        public static ISehHermesCommand asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(ISehHermesCommand.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehHermesCommand)) {
                return (ISehHermesCommand) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }
    }
}
