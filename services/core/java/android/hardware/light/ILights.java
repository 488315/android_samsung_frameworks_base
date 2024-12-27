package android.hardware.light;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILights extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$light$ILights".replace('$', '.');

    public abstract class Stub extends Binder implements ILights {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final class Proxy implements ILights {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final HwLight[] getLights() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILights.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getLights is unimplemented.");
                    }
                    obtain2.readException();
                    return (HwLight[]) obtain2.createTypedArray(HwLight.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final void setLightState(int i, HwLightState hwLightState) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILights.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hwLightState, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setLightState is unimplemented.");
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
