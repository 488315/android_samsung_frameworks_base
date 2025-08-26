package android.hardware.location;

import android.hardware.location.IActivityRecognitionHardware;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

@Deprecated
/* loaded from: classes2.dex */
public interface IActivityRecognitionHardwareWatcher extends IInterface {

    public static class Default implements IActivityRecognitionHardwareWatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IActivityRecognitionHardwareWatcher
        public void onInstanceChanged(IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException {
        }
    }

    void onInstanceChanged(IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityRecognitionHardwareWatcher {
        public static final String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareWatcher";
        static final int TRANSACTION_onInstanceChanged = 1;

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

        public static IActivityRecognitionHardwareWatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActivityRecognitionHardwareWatcher)) {
                return (IActivityRecognitionHardwareWatcher) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onInstanceChanged";
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
                IActivityRecognitionHardware iActivityRecognitionHardwareAsInterface = IActivityRecognitionHardware.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onInstanceChanged(iActivityRecognitionHardwareAsInterface);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IActivityRecognitionHardwareWatcher {
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

            @Override // android.hardware.location.IActivityRecognitionHardwareWatcher
            public void onInstanceChanged(IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iActivityRecognitionHardware);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
