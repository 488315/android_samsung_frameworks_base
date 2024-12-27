package android.security.samsungattestation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ISamsungAttestation extends IInterface {

    public abstract class Stub extends Binder implements ISamsungAttestation {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final class Proxy implements ISamsungAttestation {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
