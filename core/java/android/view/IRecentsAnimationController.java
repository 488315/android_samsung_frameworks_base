package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.TaskSnapshot;
import android.window.WindowAnimationState;

import com.android.internal.os.IResultReceiver;

public interface IRecentsAnimationController extends IInterface {
    void animateNavigationBarToApp(long j) throws RemoteException;

    void cleanupScreenshot() throws RemoteException;

    void detachNavigationBarFromApp(boolean z) throws RemoteException;

    void finish(boolean z, boolean z2, IResultReceiver iResultReceiver) throws RemoteException;

    void handOffAnimation(
            RemoteAnimationTarget[] remoteAnimationTargetArr,
            WindowAnimationState[] windowAnimationStateArr)
            throws RemoteException;

    boolean removeTask(int i) throws RemoteException;

    TaskSnapshot screenshotTask(int i) throws RemoteException;

    void setAnimationTargetsBehindSystemBars(boolean z) throws RemoteException;

    void setDeferCancelUntilNextTransition(boolean z, boolean z2) throws RemoteException;

    void setFinishTaskTransaction(
            int i,
            PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction,
            SurfaceControl surfaceControl)
            throws RemoteException;

    void setInputConsumerEnabled(boolean z) throws RemoteException;

    void setWillFinishToHome(boolean z) throws RemoteException;

    void setWillForceFinishToHome(boolean z) throws RemoteException;

    public static class Default implements IRecentsAnimationController {
        @Override // android.view.IRecentsAnimationController
        public TaskSnapshot screenshotTask(int taskId) throws RemoteException {
            return null;
        }

        @Override // android.view.IRecentsAnimationController
        public void setFinishTaskTransaction(
                int taskId,
                PictureInPictureSurfaceTransaction finishTransaction,
                SurfaceControl overlay)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void finish(
                boolean moveHomeToTop, boolean sendUserLeaveHint, IResultReceiver finishCb)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void setInputConsumerEnabled(boolean enabled) throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void setAnimationTargetsBehindSystemBars(boolean behindSystemBars)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void cleanupScreenshot() throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void setDeferCancelUntilNextTransition(boolean defer, boolean screenshot)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void setWillFinishToHome(boolean willFinishToHome) throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void setWillForceFinishToHome(boolean willForceFinishToHome)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public boolean removeTask(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.view.IRecentsAnimationController
        public void detachNavigationBarFromApp(boolean moveHomeToTop) throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void animateNavigationBarToApp(long duration) throws RemoteException {}

        @Override // android.view.IRecentsAnimationController
        public void handOffAnimation(RemoteAnimationTarget[] targets, WindowAnimationState[] states)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRecentsAnimationController {
        public static final String DESCRIPTOR = "android.view.IRecentsAnimationController";
        static final int TRANSACTION_animateNavigationBarToApp = 12;
        static final int TRANSACTION_cleanupScreenshot = 6;
        static final int TRANSACTION_detachNavigationBarFromApp = 11;
        static final int TRANSACTION_finish = 3;
        static final int TRANSACTION_handOffAnimation = 13;
        static final int TRANSACTION_removeTask = 10;
        static final int TRANSACTION_screenshotTask = 1;
        static final int TRANSACTION_setAnimationTargetsBehindSystemBars = 5;
        static final int TRANSACTION_setDeferCancelUntilNextTransition = 7;
        static final int TRANSACTION_setFinishTaskTransaction = 2;
        static final int TRANSACTION_setInputConsumerEnabled = 4;
        static final int TRANSACTION_setWillFinishToHome = 8;
        static final int TRANSACTION_setWillForceFinishToHome = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecentsAnimationController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRecentsAnimationController)) {
                return (IRecentsAnimationController) iin;
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
                    return "screenshotTask";
                case 2:
                    return "setFinishTaskTransaction";
                case 3:
                    return "finish";
                case 4:
                    return "setInputConsumerEnabled";
                case 5:
                    return "setAnimationTargetsBehindSystemBars";
                case 6:
                    return "cleanupScreenshot";
                case 7:
                    return "setDeferCancelUntilNextTransition";
                case 8:
                    return "setWillFinishToHome";
                case 9:
                    return "setWillForceFinishToHome";
                case 10:
                    return "removeTask";
                case 11:
                    return "detachNavigationBarFromApp";
                case 12:
                    return "animateNavigationBarToApp";
                case 13:
                    return "handOffAnimation";
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
                    data.enforceNoDataAvail();
                    TaskSnapshot _result = screenshotTask(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    PictureInPictureSurfaceTransaction _arg1 =
                            (PictureInPictureSurfaceTransaction)
                                    data.readTypedObject(
                                            PictureInPictureSurfaceTransaction.CREATOR);
                    SurfaceControl _arg2 =
                            (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
                    data.enforceNoDataAvail();
                    setFinishTaskTransaction(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    boolean _arg12 = data.readBoolean();
                    IResultReceiver _arg22 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    finish(_arg03, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInputConsumerEnabled(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _arg05 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAnimationTargetsBehindSystemBars(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    cleanupScreenshot();
                    reply.writeNoException();
                    return true;
                case 7:
                    boolean _arg06 = data.readBoolean();
                    boolean _arg13 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDeferCancelUntilNextTransition(_arg06, _arg13);
                    reply.writeNoException();
                    return true;
                case 8:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWillFinishToHome(_arg07);
                    reply.writeNoException();
                    return true;
                case 9:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWillForceFinishToHome(_arg08);
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = removeTask(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 11:
                    boolean _arg010 = data.readBoolean();
                    data.enforceNoDataAvail();
                    detachNavigationBarFromApp(_arg010);
                    reply.writeNoException();
                    return true;
                case 12:
                    long _arg011 = data.readLong();
                    data.enforceNoDataAvail();
                    animateNavigationBarToApp(_arg011);
                    reply.writeNoException();
                    return true;
                case 13:
                    RemoteAnimationTarget[] _arg012 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    WindowAnimationState[] _arg14 =
                            (WindowAnimationState[])
                                    data.createTypedArray(WindowAnimationState.CREATOR);
                    data.enforceNoDataAvail();
                    handOffAnimation(_arg012, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecentsAnimationController {
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

            @Override // android.view.IRecentsAnimationController
            public TaskSnapshot screenshotTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    TaskSnapshot _result =
                            (TaskSnapshot) _reply.readTypedObject(TaskSnapshot.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setFinishTaskTransaction(
                    int taskId,
                    PictureInPictureSurfaceTransaction finishTransaction,
                    SurfaceControl overlay)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeTypedObject(finishTransaction, 0);
                    _data.writeTypedObject(overlay, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void finish(
                    boolean moveHomeToTop, boolean sendUserLeaveHint, IResultReceiver finishCb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(moveHomeToTop);
                    _data.writeBoolean(sendUserLeaveHint);
                    _data.writeStrongInterface(finishCb);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setInputConsumerEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setAnimationTargetsBehindSystemBars(boolean behindSystemBars)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(behindSystemBars);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void cleanupScreenshot() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setDeferCancelUntilNextTransition(boolean defer, boolean screenshot)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(defer);
                    _data.writeBoolean(screenshot);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setWillFinishToHome(boolean willFinishToHome) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(willFinishToHome);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setWillForceFinishToHome(boolean willForceFinishToHome)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(willForceFinishToHome);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public boolean removeTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void detachNavigationBarFromApp(boolean moveHomeToTop) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(moveHomeToTop);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void animateNavigationBarToApp(long duration) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(duration);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void handOffAnimation(
                    RemoteAnimationTarget[] targets, WindowAnimationState[] states)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(targets, 0);
                    _data.writeTypedArray(states, 0);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
