package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.RemoteAnimationAdapter;
import android.window.IDisplayAreaOrganizerController;
import android.window.ITaskFragmentOrganizerController;
import android.window.ITaskOrganizerController;
import android.window.ITransitionMetricsReporter;
import android.window.ITransitionPlayer;
import android.window.IWindowContainerTransactionCallback;

/* loaded from: classes4.dex */
public interface IWindowOrganizerController extends IInterface {
    public static final String DESCRIPTOR = "android.window.IWindowOrganizerController";

    int applySyncTransaction(WindowContainerTransaction windowContainerTransaction, IWindowContainerTransactionCallback iWindowContainerTransactionCallback) throws RemoteException;

    void applyTransaction(WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    void finishAllTransitions(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, WindowContainerTransaction windowContainerTransaction2) throws RemoteException;

    void finishTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    IBinder getApplyToken() throws RemoteException;

    IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws RemoteException;

    ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws RemoteException;

    ITaskOrganizerController getTaskOrganizerController() throws RemoteException;

    ITransitionMetricsReporter getTransitionMetricsReporter() throws RemoteException;

    void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException;

    int startLegacyTransition(int i, RemoteAnimationAdapter remoteAnimationAdapter, IWindowContainerTransactionCallback iWindowContainerTransactionCallback, WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException;

    public static class Default implements IWindowOrganizerController {
        @Override // android.window.IWindowOrganizerController
        public void applyTransaction(WindowContainerTransaction t) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public int applySyncTransaction(WindowContainerTransaction t, IWindowContainerTransactionCallback callback) throws RemoteException {
            return 0;
        }

        @Override // android.window.IWindowOrganizerController
        public IBinder startNewTransition(int type, WindowContainerTransaction t) throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void startTransition(IBinder transitionToken, WindowContainerTransaction t) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public int startLegacyTransition(int type, RemoteAnimationAdapter adapter, IWindowContainerTransactionCallback syncCallback, WindowContainerTransaction t) throws RemoteException {
            return 0;
        }

        @Override // android.window.IWindowOrganizerController
        public void finishTransition(IBinder transitionToken, WindowContainerTransaction t) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public void finishAllTransitions(IBinder transitionToken, WindowContainerTransaction t, WindowContainerTransaction info) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public ITaskOrganizerController getTaskOrganizerController() throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void registerTransitionPlayer(ITransitionPlayer player) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public void unregisterTransitionPlayer(ITransitionPlayer player) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public ITransitionMetricsReporter getTransitionMetricsReporter() throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public IBinder getApplyToken() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWindowOrganizerController {
        static final int TRANSACTION_applySyncTransaction = 2;
        static final int TRANSACTION_applyTransaction = 1;
        static final int TRANSACTION_finishAllTransitions = 7;
        static final int TRANSACTION_finishTransition = 6;
        static final int TRANSACTION_getApplyToken = 14;
        static final int TRANSACTION_getDisplayAreaOrganizerController = 9;
        static final int TRANSACTION_getTaskFragmentOrganizerController = 10;
        static final int TRANSACTION_getTaskOrganizerController = 8;
        static final int TRANSACTION_getTransitionMetricsReporter = 13;
        static final int TRANSACTION_registerTransitionPlayer = 11;
        static final int TRANSACTION_startLegacyTransition = 5;
        static final int TRANSACTION_startNewTransition = 3;
        static final int TRANSACTION_startTransition = 4;
        static final int TRANSACTION_unregisterTransitionPlayer = 12;

        public Stub() {
            attachInterface(this, IWindowOrganizerController.DESCRIPTOR);
        }

        public static IWindowOrganizerController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWindowOrganizerController.DESCRIPTOR);
            if (iin != null && (iin instanceof IWindowOrganizerController)) {
                return (IWindowOrganizerController) iin;
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
                    return "applyTransaction";
                case 2:
                    return "applySyncTransaction";
                case 3:
                    return "startNewTransition";
                case 4:
                    return "startTransition";
                case 5:
                    return "startLegacyTransition";
                case 6:
                    return "finishTransition";
                case 7:
                    return "finishAllTransitions";
                case 8:
                    return "getTaskOrganizerController";
                case 9:
                    return "getDisplayAreaOrganizerController";
                case 10:
                    return "getTaskFragmentOrganizerController";
                case 11:
                    return "registerTransitionPlayer";
                case 12:
                    return "unregisterTransitionPlayer";
                case 13:
                    return "getTransitionMetricsReporter";
                case 14:
                    return "getApplyToken";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IWindowOrganizerController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWindowOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    WindowContainerTransaction _arg0 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    applyTransaction(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    WindowContainerTransaction _arg02 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    IWindowContainerTransactionCallback _arg1 = IWindowContainerTransactionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = applySyncTransaction(_arg02, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    WindowContainerTransaction _arg12 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    IBinder _result2 = startNewTransition(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result2);
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    WindowContainerTransaction _arg13 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    startTransition(_arg04, _arg13);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    RemoteAnimationAdapter _arg14 = (RemoteAnimationAdapter) data.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    IWindowContainerTransactionCallback _arg2 = IWindowContainerTransactionCallback.Stub.asInterface(data.readStrongBinder());
                    WindowContainerTransaction _arg3 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    int _result3 = startLegacyTransition(_arg05, _arg14, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 6:
                    IBinder _arg06 = data.readStrongBinder();
                    WindowContainerTransaction _arg15 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    finishTransition(_arg06, _arg15);
                    reply.writeNoException();
                    return true;
                case 7:
                    IBinder _arg07 = data.readStrongBinder();
                    WindowContainerTransaction _arg16 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    WindowContainerTransaction _arg22 = (WindowContainerTransaction) data.readTypedObject(WindowContainerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    finishAllTransitions(_arg07, _arg16, _arg22);
                    reply.writeNoException();
                    return true;
                case 8:
                    ITaskOrganizerController _result4 = getTaskOrganizerController();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 9:
                    IDisplayAreaOrganizerController _result5 = getDisplayAreaOrganizerController();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    return true;
                case 10:
                    ITaskFragmentOrganizerController _result6 = getTaskFragmentOrganizerController();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result6);
                    return true;
                case 11:
                    ITransitionPlayer _arg08 = ITransitionPlayer.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerTransitionPlayer(_arg08);
                    reply.writeNoException();
                    return true;
                case 12:
                    ITransitionPlayer _arg09 = ITransitionPlayer.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterTransitionPlayer(_arg09);
                    reply.writeNoException();
                    return true;
                case 13:
                    ITransitionMetricsReporter _result7 = getTransitionMetricsReporter();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result7);
                    return true;
                case 14:
                    IBinder _result8 = getApplyToken();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWindowOrganizerController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWindowOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.IWindowOrganizerController
            public void applyTransaction(WindowContainerTransaction t) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeTypedObject(t, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public int applySyncTransaction(WindowContainerTransaction t, IWindowContainerTransactionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeTypedObject(t, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public IBinder startNewTransition(int type, WindowContainerTransaction t) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(t, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void startTransition(IBinder transitionToken, WindowContainerTransaction t) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeStrongBinder(transitionToken);
                    _data.writeTypedObject(t, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public int startLegacyTransition(int type, RemoteAnimationAdapter adapter, IWindowContainerTransactionCallback syncCallback, WindowContainerTransaction t) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(adapter, 0);
                    _data.writeStrongInterface(syncCallback);
                    _data.writeTypedObject(t, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void finishTransition(IBinder transitionToken, WindowContainerTransaction t) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeStrongBinder(transitionToken);
                    _data.writeTypedObject(t, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void finishAllTransitions(IBinder transitionToken, WindowContainerTransaction t, WindowContainerTransaction info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeStrongBinder(transitionToken);
                    _data.writeTypedObject(t, 0);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public ITaskOrganizerController getTaskOrganizerController() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    ITaskOrganizerController _result = ITaskOrganizerController.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    IDisplayAreaOrganizerController _result = IDisplayAreaOrganizerController.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    ITaskFragmentOrganizerController _result = ITaskFragmentOrganizerController.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void registerTransitionPlayer(ITransitionPlayer player) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeStrongInterface(player);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void unregisterTransitionPlayer(ITransitionPlayer player) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    _data.writeStrongInterface(player);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public ITransitionMetricsReporter getTransitionMetricsReporter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    ITransitionMetricsReporter _result = ITransitionMetricsReporter.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public IBinder getApplyToken() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
