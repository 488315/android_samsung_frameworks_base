package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IRemoteAnimationRunner extends IInterface {
    void onAnimationCancelled() throws RemoteException;

    void onAnimationStart(
            int i,
            RemoteAnimationTarget[] remoteAnimationTargetArr,
            RemoteAnimationTarget[] remoteAnimationTargetArr2,
            RemoteAnimationTarget[] remoteAnimationTargetArr3,
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback)
            throws RemoteException;

    public static class Default implements IRemoteAnimationRunner {
        @Override // android.view.IRemoteAnimationRunner
        public void onAnimationStart(
                int transit,
                RemoteAnimationTarget[] apps,
                RemoteAnimationTarget[] wallpapers,
                RemoteAnimationTarget[] nonApps,
                IRemoteAnimationFinishedCallback finishedCallback)
                throws RemoteException {}

        @Override // android.view.IRemoteAnimationRunner
        public void onAnimationCancelled() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteAnimationRunner {
        public static final String DESCRIPTOR = "android.view.IRemoteAnimationRunner";
        static final int TRANSACTION_onAnimationCancelled = 2;
        static final int TRANSACTION_onAnimationStart = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteAnimationRunner asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteAnimationRunner)) {
                return (IRemoteAnimationRunner) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onAnimationStart";
                case 2:
                    return "onAnimationCancelled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    RemoteAnimationTarget[] _arg1 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    RemoteAnimationTarget[] _arg2 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    RemoteAnimationTarget[] _arg3 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    IRemoteAnimationFinishedCallback _arg4 =
                            IRemoteAnimationFinishedCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onAnimationStart(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 2:
                    onAnimationCancelled();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteAnimationRunner {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IRemoteAnimationRunner
            public void onAnimationStart(
                    int transit,
                    RemoteAnimationTarget[] apps,
                    RemoteAnimationTarget[] wallpapers,
                    RemoteAnimationTarget[] nonApps,
                    IRemoteAnimationFinishedCallback finishedCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(transit);
                    _data.writeTypedArray(apps, 0);
                    _data.writeTypedArray(wallpapers, 0);
                    _data.writeTypedArray(nonApps, 0);
                    _data.writeStrongInterface(finishedCallback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IRemoteAnimationRunner
            public void onAnimationCancelled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
