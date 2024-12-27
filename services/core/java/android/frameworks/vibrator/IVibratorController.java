package android.frameworks.vibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVibratorController extends IInterface {
    public static final String DESCRIPTOR =
            "android$frameworks$vibrator$IVibratorController".replace('$', '.');

    public abstract class Stub extends Binder implements IVibratorController {

        public final class Proxy implements IVibratorController {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final void requestVibrationParams(IBinder iBinder, long j, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IVibratorController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method requestVibrationParams is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static IVibratorController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IVibratorController.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IVibratorController)) {
                return (IVibratorController) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }
    }
}
