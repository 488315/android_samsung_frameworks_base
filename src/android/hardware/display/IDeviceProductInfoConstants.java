package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceProductInfoConstants extends IInterface {
    public static final int CONNECTION_TO_SINK_BUILT_IN = 1;
    public static final int CONNECTION_TO_SINK_DIRECT = 2;
    public static final int CONNECTION_TO_SINK_TRANSITIVE = 3;
    public static final int CONNECTION_TO_SINK_UNKNOWN = 0;
    public static final String DESCRIPTOR = "android.hardware.display.IDeviceProductInfoConstants";

    public static class Default implements IDeviceProductInfoConstants {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDeviceProductInfoConstants {
        public static String getDefaultTransactionName(int i) {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDeviceProductInfoConstants.DESCRIPTOR);
        }

        public static IDeviceProductInfoConstants asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceProductInfoConstants.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceProductInfoConstants)) {
                return (IDeviceProductInfoConstants) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IDeviceProductInfoConstants.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDeviceProductInfoConstants {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceProductInfoConstants.DESCRIPTOR;
            }
        }
    }
}
