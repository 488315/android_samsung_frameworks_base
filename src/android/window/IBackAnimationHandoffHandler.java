package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.RemoteAnimationTarget;

/* loaded from: classes5.dex */
public interface IBackAnimationHandoffHandler extends IInterface {
    public static final String DESCRIPTOR = "android.window.IBackAnimationHandoffHandler";

    public static class Default implements IBackAnimationHandoffHandler {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IBackAnimationHandoffHandler
        public void handOffAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
        }
    }

    void handOffAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowAnimationState[] windowAnimationStateArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackAnimationHandoffHandler {
        static final int TRANSACTION_handOffAnimation = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBackAnimationHandoffHandler.DESCRIPTOR);
        }

        public static IBackAnimationHandoffHandler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBackAnimationHandoffHandler.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackAnimationHandoffHandler)) {
                return (IBackAnimationHandoffHandler) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "handOffAnimation";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBackAnimationHandoffHandler.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBackAnimationHandoffHandler.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                WindowAnimationState[] windowAnimationStateArr = (WindowAnimationState[]) parcel.createTypedArray(WindowAnimationState.CREATOR);
                parcel.enforceNoDataAvail();
                handOffAnimation(remoteAnimationTargetArr, windowAnimationStateArr);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBackAnimationHandoffHandler {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBackAnimationHandoffHandler.DESCRIPTOR;
            }

            @Override // android.window.IBackAnimationHandoffHandler
            public void handOffAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBackAnimationHandoffHandler.DESCRIPTOR);
                    obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    obtain.writeTypedArray(windowAnimationStateArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
