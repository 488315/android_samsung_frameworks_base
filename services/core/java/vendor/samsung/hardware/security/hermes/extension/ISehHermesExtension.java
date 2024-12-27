package vendor.samsung.hardware.security.hermes.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.security.hermes.SehCommandResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ISehHermesExtension extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$security$hermes$extension$ISehHermesExtension".replace('$', '.');

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements ISehHermesExtension {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements ISehHermesExtension {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final SehCommandResult getSeId() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSeId is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult selftest(SehSelftestParameter[] sehSelftestParameterArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    obtain.writeTypedArray(sehSelftestParameterArr, 0);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method selftest is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult sendAPDU(byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sendAPDU is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult turnOffSecureHardwarePower() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method turnOffSecureHardwarePower is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult turnOnSecureHardwarePower() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method turnOnSecureHardwarePower is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult updateApplet() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateApplet is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult updateCOSpatch() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateCOSpatch is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final SehCommandResult updateCOSpatchTest(byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehHermesExtension.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateCOSpatchTest is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehCommandResult) obtain2.readTypedObject(SehCommandResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static ISehHermesExtension asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehHermesExtension.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehHermesExtension)) {
                return (ISehHermesExtension) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }
    }
}
