package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.IDisplayAreaOrganizerController;
import android.window.ITaskFragmentOrganizerController;
import android.window.ITaskOrganizerController;
import android.window.ITransitionMetricsReporter;
import android.window.ITransitionPlayer;
import android.window.IWindowContainerTransactionCallback;

/* loaded from: classes5.dex */
public interface IWindowOrganizerController extends IInterface {
    public static final String DESCRIPTOR = "android.window.IWindowOrganizerController";

    public static class Default implements IWindowOrganizerController {
        @Override // android.window.IWindowOrganizerController
        public int applySyncTransaction(WindowContainerTransaction windowContainerTransaction, IWindowContainerTransactionCallback iWindowContainerTransactionCallback) throws RemoteException {
            return 0;
        }

        @Override // android.window.IWindowOrganizerController
        public void applyTransaction(WindowContainerTransaction windowContainerTransaction) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void finishAllTransitions(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, WindowContainerTransaction windowContainerTransaction2) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public void finishTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public IBinder getApplyToken() throws RemoteException {
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
        public ITaskOrganizerController getTaskOrganizerController() throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public ITransitionMetricsReporter getTransitionMetricsReporter() throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) throws RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException {
        }
    }

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

    IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindowOrganizerController {
        static final int TRANSACTION_applySyncTransaction = 2;
        static final int TRANSACTION_applyTransaction = 1;
        static final int TRANSACTION_finishAllTransitions = 6;
        static final int TRANSACTION_finishTransition = 5;
        static final int TRANSACTION_getApplyToken = 13;
        static final int TRANSACTION_getDisplayAreaOrganizerController = 8;
        static final int TRANSACTION_getTaskFragmentOrganizerController = 9;
        static final int TRANSACTION_getTaskOrganizerController = 7;
        static final int TRANSACTION_getTransitionMetricsReporter = 12;
        static final int TRANSACTION_registerTransitionPlayer = 10;
        static final int TRANSACTION_startNewTransition = 3;
        static final int TRANSACTION_startTransition = 4;
        static final int TRANSACTION_unregisterTransitionPlayer = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IWindowOrganizerController.DESCRIPTOR);
        }

        public static IWindowOrganizerController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWindowOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWindowOrganizerController)) {
                return (IWindowOrganizerController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "applyTransaction";
                case 2:
                    return "applySyncTransaction";
                case 3:
                    return "startNewTransition";
                case 4:
                    return "startTransition";
                case 5:
                    return "finishTransition";
                case 6:
                    return "finishAllTransitions";
                case 7:
                    return "getTaskOrganizerController";
                case 8:
                    return "getDisplayAreaOrganizerController";
                case 9:
                    return "getTaskFragmentOrganizerController";
                case 10:
                    return "registerTransitionPlayer";
                case 11:
                    return "unregisterTransitionPlayer";
                case 12:
                    return "getTransitionMetricsReporter";
                case 13:
                    return "getApplyToken";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWindowOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWindowOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyTransaction(windowContainerTransaction);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    WindowContainerTransaction windowContainerTransaction2 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    IWindowContainerTransactionCallback asInterface = IWindowContainerTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int applySyncTransaction = applySyncTransaction(windowContainerTransaction2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(applySyncTransaction);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    WindowContainerTransaction windowContainerTransaction3 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder startNewTransition = startNewTransition(readInt, windowContainerTransaction3);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(startNewTransition);
                    return true;
                case 4:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    WindowContainerTransaction windowContainerTransaction4 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    startTransition(readStrongBinder, windowContainerTransaction4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    WindowContainerTransaction windowContainerTransaction5 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishTransition(readStrongBinder2, windowContainerTransaction5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    WindowContainerTransaction windowContainerTransaction6 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    WindowContainerTransaction windowContainerTransaction7 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishAllTransitions(readStrongBinder3, windowContainerTransaction6, windowContainerTransaction7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ITaskOrganizerController taskOrganizerController = getTaskOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(taskOrganizerController);
                    return true;
                case 8:
                    IDisplayAreaOrganizerController displayAreaOrganizerController = getDisplayAreaOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(displayAreaOrganizerController);
                    return true;
                case 9:
                    ITaskFragmentOrganizerController taskFragmentOrganizerController = getTaskFragmentOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(taskFragmentOrganizerController);
                    return true;
                case 10:
                    ITransitionPlayer asInterface2 = ITransitionPlayer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTransitionPlayer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ITransitionPlayer asInterface3 = ITransitionPlayer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTransitionPlayer(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ITransitionMetricsReporter transitionMetricsReporter = getTransitionMetricsReporter();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(transitionMetricsReporter);
                    return true;
                case 13:
                    IBinder applyToken = getApplyToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(applyToken);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWindowOrganizerController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWindowOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.IWindowOrganizerController
            public void applyTransaction(WindowContainerTransaction windowContainerTransaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public int applySyncTransaction(WindowContainerTransaction windowContainerTransaction, IWindowContainerTransactionCallback iWindowContainerTransactionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeStrongInterface(iWindowContainerTransactionCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void finishTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void finishAllTransitions(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, WindowContainerTransaction windowContainerTransaction2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeTypedObject(windowContainerTransaction2, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public ITaskOrganizerController getTaskOrganizerController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITaskOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return IDisplayAreaOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITaskFragmentOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransitionPlayer);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransitionPlayer);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public ITransitionMetricsReporter getTransitionMetricsReporter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITransitionMetricsReporter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public IBinder getApplyToken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
