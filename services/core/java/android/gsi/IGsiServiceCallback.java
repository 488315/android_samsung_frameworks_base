package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IGsiServiceCallback extends IInterface {

    public class Default implements IGsiServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void onResult(int i);

    public abstract class Stub extends Binder implements IGsiServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "android.gsi.IGsiServiceCallback");
        }

        public static IGsiServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.gsi.IGsiServiceCallback");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGsiServiceCallback)) {
                return (IGsiServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.gsi.IGsiServiceCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.gsi.IGsiServiceCallback");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onResult(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public class Proxy implements IGsiServiceCallback {
            public IBinder mRemote;

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
