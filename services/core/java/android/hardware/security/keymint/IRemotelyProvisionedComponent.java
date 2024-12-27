package android.hardware.security.keymint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemotelyProvisionedComponent extends IInterface {
    public static final String DESCRIPTOR =
            "android$hardware$security$keymint$IRemotelyProvisionedComponent".replace('$', '.');

    public abstract class Stub extends Binder implements IRemotelyProvisionedComponent {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final class Proxy implements IRemotelyProvisionedComponent {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final RpcHardwareInfo getHardwareInfo() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemotelyProvisionedComponent.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (RpcHardwareInfo) obtain2.readTypedObject(RpcHardwareInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
