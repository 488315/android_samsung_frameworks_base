package android.service.rotationresolver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.rotationresolver.IRotationResolverCallback;

/* loaded from: classes3.dex */
public interface IRotationResolverService extends IInterface {
    public static final String DESCRIPTOR = "android.service.rotationresolver.IRotationResolverService";

    public static class Default implements IRotationResolverService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.rotationresolver.IRotationResolverService
        public void resolveRotation(IRotationResolverCallback iRotationResolverCallback, RotationResolutionRequest rotationResolutionRequest) throws RemoteException {
        }
    }

    void resolveRotation(IRotationResolverCallback iRotationResolverCallback, RotationResolutionRequest rotationResolutionRequest) throws RemoteException;

    public static abstract class Stub extends Binder implements IRotationResolverService {
        static final int TRANSACTION_resolveRotation = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRotationResolverService.DESCRIPTOR);
        }

        public static IRotationResolverService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRotationResolverService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRotationResolverService)) {
                return (IRotationResolverService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "resolveRotation";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRotationResolverService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRotationResolverService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IRotationResolverCallback iRotationResolverCallbackAsInterface = IRotationResolverCallback.Stub.asInterface(parcel.readStrongBinder());
                RotationResolutionRequest rotationResolutionRequest = (RotationResolutionRequest) parcel.readTypedObject(RotationResolutionRequest.CREATOR);
                parcel.enforceNoDataAvail();
                resolveRotation(iRotationResolverCallbackAsInterface, rotationResolutionRequest);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRotationResolverService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRotationResolverService.DESCRIPTOR;
            }

            @Override // android.service.rotationresolver.IRotationResolverService
            public void resolveRotation(IRotationResolverCallback iRotationResolverCallback, RotationResolutionRequest rotationResolutionRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRotationResolverService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRotationResolverCallback);
                    parcelObtain.writeTypedObject(rotationResolutionRequest, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
