package android.hardware.location;

import android.hardware.location.IActivityRecognitionHardware;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IActivityRecognitionHardwareClient extends IInterface {

    public static class Default implements IActivityRecognitionHardwareClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IActivityRecognitionHardwareClient
        public void onAvailabilityChanged(boolean z, IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException {
        }
    }

    void onAvailabilityChanged(boolean z, IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityRecognitionHardwareClient {
        public static final String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareClient";
        static final int TRANSACTION_onAvailabilityChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IActivityRecognitionHardwareClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IActivityRecognitionHardwareClient)) {
                return (IActivityRecognitionHardwareClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAvailabilityChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                IActivityRecognitionHardware asInterface = IActivityRecognitionHardware.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onAvailabilityChanged(readBoolean, asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IActivityRecognitionHardwareClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IActivityRecognitionHardwareClient
            public void onAvailabilityChanged(boolean z, IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iActivityRecognitionHardware);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
