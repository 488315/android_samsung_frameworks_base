package vendor.samsung.hardware.light;

import android.hardware.light.HwLightState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISehLights extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$light$ISehLights".replace('$', '.');

    public abstract class Stub extends Binder implements ISehLights {

        public final class Proxy implements ISehLights {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final SehHwLight[] getLights() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehLights.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getLights is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehHwLight[]) obtain2.createTypedArray(SehHwLight.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final void setLightState(int i, HwLightState hwLightState) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehLights.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hwLightState, 0);
                    obtain.writeInt(-1);
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

        public static ISehLights asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehLights.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehLights)) {
                return (ISehLights) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }
    }
}
