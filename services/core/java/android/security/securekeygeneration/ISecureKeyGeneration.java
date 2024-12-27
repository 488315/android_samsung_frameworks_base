package android.security.securekeygeneration;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ISecureKeyGeneration extends IInterface {

    public abstract class Stub extends Binder implements ISecureKeyGeneration {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final class Proxy implements ISecureKeyGeneration {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
