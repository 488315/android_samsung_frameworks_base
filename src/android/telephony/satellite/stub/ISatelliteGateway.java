package android.telephony.satellite.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteGateway extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.stub.ISatelliteGateway";

    public static class Default implements ISatelliteGateway {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISatelliteGateway {
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
            attachInterface(this, ISatelliteGateway.DESCRIPTOR);
        }

        public static ISatelliteGateway asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatelliteGateway.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatelliteGateway)) {
                return (ISatelliteGateway) queryLocalInterface;
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
                parcel2.writeString(ISatelliteGateway.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISatelliteGateway {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteGateway.DESCRIPTOR;
            }
        }
    }
}
