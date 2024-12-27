package android.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.TaskSnapshot;

/* loaded from: classes4.dex */
public interface IRecentsAnimationRunner extends IInterface {
    void onAnimationCanceled(int[] iArr, TaskSnapshot[] taskSnapshotArr) throws RemoteException;

    void onAnimationStart(
            IRecentsAnimationController iRecentsAnimationController,
            RemoteAnimationTarget[] remoteAnimationTargetArr,
            RemoteAnimationTarget[] remoteAnimationTargetArr2,
            Rect rect,
            Rect rect2,
            Bundle bundle)
            throws RemoteException;

    void onTasksAppeared(RemoteAnimationTarget[] remoteAnimationTargetArr) throws RemoteException;

    public static class Default implements IRecentsAnimationRunner {
        @Override // android.view.IRecentsAnimationRunner
        public void onAnimationCanceled(int[] taskIds, TaskSnapshot[] taskSnapshots)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationRunner
        public void onAnimationStart(
                IRecentsAnimationController controller,
                RemoteAnimationTarget[] apps,
                RemoteAnimationTarget[] wallpapers,
                Rect homeContentInsets,
                Rect minimizedHomeBounds,
                Bundle extras)
                throws RemoteException {}

        @Override // android.view.IRecentsAnimationRunner
        public void onTasksAppeared(RemoteAnimationTarget[] app) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRecentsAnimationRunner {
        public static final String DESCRIPTOR = "android.view.IRecentsAnimationRunner";
        static final int TRANSACTION_onAnimationCanceled = 2;
        static final int TRANSACTION_onAnimationStart = 3;
        static final int TRANSACTION_onTasksAppeared = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecentsAnimationRunner asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRecentsAnimationRunner)) {
                return (IRecentsAnimationRunner) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 2:
                    return "onAnimationCanceled";
                case 3:
                    return "onAnimationStart";
                case 4:
                    return "onTasksAppeared";
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
                case 2:
                    int[] _arg0 = data.createIntArray();
                    TaskSnapshot[] _arg1 =
                            (TaskSnapshot[]) data.createTypedArray(TaskSnapshot.CREATOR);
                    data.enforceNoDataAvail();
                    onAnimationCanceled(_arg0, _arg1);
                    return true;
                case 3:
                    IRecentsAnimationController _arg02 =
                            IRecentsAnimationController.Stub.asInterface(data.readStrongBinder());
                    RemoteAnimationTarget[] _arg12 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    RemoteAnimationTarget[] _arg2 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    Rect _arg3 = (Rect) data.readTypedObject(Rect.CREATOR);
                    Rect _arg4 = (Rect) data.readTypedObject(Rect.CREATOR);
                    Bundle _arg5 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onAnimationStart(_arg02, _arg12, _arg2, _arg3, _arg4, _arg5);
                    return true;
                case 4:
                    RemoteAnimationTarget[] _arg03 =
                            (RemoteAnimationTarget[])
                                    data.createTypedArray(RemoteAnimationTarget.CREATOR);
                    data.enforceNoDataAvail();
                    onTasksAppeared(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecentsAnimationRunner {
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

            @Override // android.view.IRecentsAnimationRunner
            public void onAnimationCanceled(int[] taskIds, TaskSnapshot[] taskSnapshots)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(taskIds);
                    _data.writeTypedArray(taskSnapshots, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationRunner
            public void onAnimationStart(
                    IRecentsAnimationController controller,
                    RemoteAnimationTarget[] apps,
                    RemoteAnimationTarget[] wallpapers,
                    Rect homeContentInsets,
                    Rect minimizedHomeBounds,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    _data.writeTypedArray(apps, 0);
                    _data.writeTypedArray(wallpapers, 0);
                    _data.writeTypedObject(homeContentInsets, 0);
                    _data.writeTypedObject(minimizedHomeBounds, 0);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationRunner
            public void onTasksAppeared(RemoteAnimationTarget[] app) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(app, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
