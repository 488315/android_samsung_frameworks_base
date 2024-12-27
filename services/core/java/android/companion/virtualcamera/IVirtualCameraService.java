package android.companion.virtualcamera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface IVirtualCameraService extends IInterface {

    public abstract class Stub extends Binder implements IVirtualCameraService {

        public final class Proxy implements IVirtualCameraService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getCameraId(IBinder iBinder) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "android.companion.virtualcamera.IVirtualCameraService");
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final boolean registerCamera(
                    IBinder iBinder, VirtualCameraConfiguration virtualCameraConfiguration, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "android.companion.virtualcamera.IVirtualCameraService");
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualCameraConfiguration, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final void unregisterCamera(IBinder iBinder) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "android.companion.virtualcamera.IVirtualCameraService");
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IVirtualCameraService asInterface(IBinder iBinder) {
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(
                            "android.companion.virtualcamera.IVirtualCameraService");
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IVirtualCameraService)) {
                return (IVirtualCameraService) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }
    }
}
