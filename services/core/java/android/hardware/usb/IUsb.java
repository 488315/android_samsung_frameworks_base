package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IUsb extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$usb$IUsb".replace('$', '.');

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IUsb {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IUsb {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final void enableContaminantPresenceDetection(String str, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableContaminantPresenceDetection is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void enableUsbData(String str, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUsbData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void enableUsbDataWhileDocked(long j, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUsbDataWhileDocked is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void limitPowerTransfer(String str, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method limitPowerTransfer is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void queryPortStatus(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method queryPortStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void resetUsbPort(long j, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method resetUsbPort is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void setCallback(IUsbCallback iUsbCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbCallback);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            public final void switchRole(String str, PortRole portRole, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(portRole, 0);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method switchRole is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static IUsb asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUsb.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsb)) {
                return (IUsb) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }
    }
}
