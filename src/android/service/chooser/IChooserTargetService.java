package android.service.chooser;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.chooser.IChooserTargetResult;

/* loaded from: classes3.dex */
public interface IChooserTargetService extends IInterface {

    public static class Default implements IChooserTargetService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.chooser.IChooserTargetService
        public void getChooserTargets(ComponentName componentName, IntentFilter intentFilter, IChooserTargetResult iChooserTargetResult) throws RemoteException {
        }
    }

    void getChooserTargets(ComponentName componentName, IntentFilter intentFilter, IChooserTargetResult iChooserTargetResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IChooserTargetService {
        public static final String DESCRIPTOR = "android.service.chooser.IChooserTargetService";
        static final int TRANSACTION_getChooserTargets = 1;

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

        public static IChooserTargetService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IChooserTargetService)) {
                return (IChooserTargetService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getChooserTargets";
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
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                IChooserTargetResult asInterface = IChooserTargetResult.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getChooserTargets(componentName, intentFilter, asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IChooserTargetService {
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

            @Override // android.service.chooser.IChooserTargetService
            public void getChooserTargets(ComponentName componentName, IntentFilter intentFilter, IChooserTargetResult iChooserTargetResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeStrongInterface(iChooserTargetResult);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
