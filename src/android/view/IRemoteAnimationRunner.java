package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;

/* loaded from: classes4.dex */
public interface IRemoteAnimationRunner extends IInterface {

    public static class Default implements IRemoteAnimationRunner {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IRemoteAnimationRunner
        public void onAnimationCancelled() throws RemoteException {
        }

        @Override // android.view.IRemoteAnimationRunner
        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
        }
    }

    void onAnimationCancelled() throws RemoteException;

    void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteAnimationRunner {
        public static final String DESCRIPTOR = "android.view.IRemoteAnimationRunner";
        static final int TRANSACTION_onAnimationCancelled = 2;
        static final int TRANSACTION_onAnimationStart = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteAnimationRunner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteAnimationRunner)) {
                return (IRemoteAnimationRunner) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAnimationStart";
            }
            if (i != 2) {
                return null;
            }
            return "onAnimationCancelled";
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
                int readInt = parcel.readInt();
                RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                RemoteAnimationTarget[] remoteAnimationTargetArr2 = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                RemoteAnimationTarget[] remoteAnimationTargetArr3 = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                IRemoteAnimationFinishedCallback asInterface = IRemoteAnimationFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onAnimationStart(readInt, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, asInterface);
            } else if (i == 2) {
                onAnimationCancelled();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteAnimationRunner {
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

            @Override // android.view.IRemoteAnimationRunner
            public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr2, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr3, 0);
                    obtain.writeStrongInterface(iRemoteAnimationFinishedCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IRemoteAnimationRunner
            public void onAnimationCancelled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
