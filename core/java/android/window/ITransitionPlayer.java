package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes4.dex */
public interface ITransitionPlayer extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITransitionPlayer";

    void onTransitionReady(
            IBinder iBinder,
            TransitionInfo transitionInfo,
            SurfaceControl.Transaction transaction,
            SurfaceControl.Transaction transaction2)
            throws RemoteException;

    void requestStartTransition(IBinder iBinder, TransitionRequestInfo transitionRequestInfo)
            throws RemoteException;

    void transitionAborted(IBinder iBinder) throws RemoteException;

    public static class Default implements ITransitionPlayer {
        @Override // android.window.ITransitionPlayer
        public void onTransitionReady(
                IBinder transitionToken,
                TransitionInfo info,
                SurfaceControl.Transaction t,
                SurfaceControl.Transaction finishT)
                throws RemoteException {}

        @Override // android.window.ITransitionPlayer
        public void requestStartTransition(IBinder transitionToken, TransitionRequestInfo request)
                throws RemoteException {}

        @Override // android.window.ITransitionPlayer
        public void transitionAborted(IBinder transitionToken) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITransitionPlayer {
        static final int TRANSACTION_onTransitionReady = 1;
        static final int TRANSACTION_requestStartTransition = 2;
        static final int TRANSACTION_transitionAborted = 3;

        public Stub() {
            attachInterface(this, ITransitionPlayer.DESCRIPTOR);
        }

        public static ITransitionPlayer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITransitionPlayer.DESCRIPTOR);
            if (iin != null && (iin instanceof ITransitionPlayer)) {
                return (ITransitionPlayer) iin;
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
                    return "onTransitionReady";
                case 2:
                    return "requestStartTransition";
                case 3:
                    return "transitionAborted";
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
                data.enforceInterface(ITransitionPlayer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITransitionPlayer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    TransitionInfo _arg1 =
                            (TransitionInfo) data.readTypedObject(TransitionInfo.CREATOR);
                    SurfaceControl.Transaction _arg2 =
                            (SurfaceControl.Transaction)
                                    data.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    SurfaceControl.Transaction _arg3 =
                            (SurfaceControl.Transaction)
                                    data.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    data.enforceNoDataAvail();
                    onTransitionReady(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    TransitionRequestInfo _arg12 =
                            (TransitionRequestInfo)
                                    data.readTypedObject(TransitionRequestInfo.CREATOR);
                    data.enforceNoDataAvail();
                    requestStartTransition(_arg02, _arg12);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    transitionAborted(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITransitionPlayer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransitionPlayer.DESCRIPTOR;
            }

            @Override // android.window.ITransitionPlayer
            public void onTransitionReady(
                    IBinder transitionToken,
                    TransitionInfo info,
                    SurfaceControl.Transaction t,
                    SurfaceControl.Transaction finishT)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransitionPlayer.DESCRIPTOR);
                    _data.writeStrongBinder(transitionToken);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(t, 0);
                    _data.writeTypedObject(finishT, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.ITransitionPlayer
            public void requestStartTransition(
                    IBinder transitionToken, TransitionRequestInfo request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransitionPlayer.DESCRIPTOR);
                    _data.writeStrongBinder(transitionToken);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.ITransitionPlayer
            public void transitionAborted(IBinder transitionToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransitionPlayer.DESCRIPTOR);
                    _data.writeStrongBinder(transitionToken);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
