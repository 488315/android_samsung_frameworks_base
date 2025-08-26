package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.RemoteAnimationTarget;
import android.window.IBackAnimationFinishedCallback;

/* loaded from: classes5.dex */
public interface IBackAnimationRunner extends IInterface {
    public static final String DESCRIPTOR = "android.window.IBackAnimationRunner";

    public static class Default implements IBackAnimationRunner {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IBackAnimationRunner
        public void onAnimationCancelled() throws RemoteException {
        }

        @Override // android.window.IBackAnimationRunner
        public void onAnimationStart(RemoteAnimationTarget[] remoteAnimationTargetArr, IBinder iBinder, IBackAnimationFinishedCallback iBackAnimationFinishedCallback) throws RemoteException {
        }
    }

    void onAnimationCancelled() throws RemoteException;

    void onAnimationStart(RemoteAnimationTarget[] remoteAnimationTargetArr, IBinder iBinder, IBackAnimationFinishedCallback iBackAnimationFinishedCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackAnimationRunner {
        static final int TRANSACTION_onAnimationCancelled = 2;
        static final int TRANSACTION_onAnimationStart = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IBackAnimationRunner.DESCRIPTOR);
        }

        public static IBackAnimationRunner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBackAnimationRunner.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBackAnimationRunner)) {
                return (IBackAnimationRunner) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 2) {
                return "onAnimationCancelled";
            }
            if (i != 3) {
                return null;
            }
            return "onAnimationStart";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBackAnimationRunner.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBackAnimationRunner.DESCRIPTOR);
                return true;
            }
            if (i == 2) {
                onAnimationCancelled();
            } else if (i == 3) {
                RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                IBackAnimationFinishedCallback iBackAnimationFinishedCallbackAsInterface = IBackAnimationFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onAnimationStart(remoteAnimationTargetArr, strongBinder, iBackAnimationFinishedCallbackAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBackAnimationRunner {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBackAnimationRunner.DESCRIPTOR;
            }

            @Override // android.window.IBackAnimationRunner
            public void onAnimationCancelled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBackAnimationRunner.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IBackAnimationRunner
            public void onAnimationStart(RemoteAnimationTarget[] remoteAnimationTargetArr, IBinder iBinder, IBackAnimationFinishedCallback iBackAnimationFinishedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBackAnimationRunner.DESCRIPTOR);
                    parcelObtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iBackAnimationFinishedCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
