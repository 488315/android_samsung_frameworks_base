package vendor.samsung.hardware.health;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface ISehHealthInfoCallback extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$health$ISehHealthInfoCallback".replace('$', '.');

    public class Default implements ISehHealthInfoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    void healthInfoChanged(SehHealthInfo sehHealthInfo);

    public abstract class Stub extends Binder implements ISehHealthInfoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehHealthInfoCallback.DESCRIPTOR);
        }

        public static ISehHealthInfoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehHealthInfoCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehHealthInfoCallback)) {
                return (ISehHealthInfoCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehHealthInfoCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    if (i == 1) {
                        SehHealthInfo sehHealthInfo = (SehHealthInfo) parcel.readTypedObject(SehHealthInfo.CREATOR);
                        parcel.enforceNoDataAvail();
                        healthInfoChanged(sehHealthInfo);
                        return true;
                    }
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        public class Proxy implements ISehHealthInfoCallback {
            public IBinder mRemote;
            public int mCachedVersion = -1;
            public String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
