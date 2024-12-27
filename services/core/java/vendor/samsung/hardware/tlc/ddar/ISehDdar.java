package vendor.samsung.hardware.tlc.ddar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface ISehDdar extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$tlc$ddar$ISehDdar".replace('$', '.');

    public abstract class Stub extends Binder implements ISehDdar {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final class Proxy implements ISehDdar {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
