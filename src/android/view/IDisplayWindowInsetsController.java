package android.view;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.ImeTracker;

/* loaded from: classes4.dex */
public interface IDisplayWindowInsetsController extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDisplayWindowInsetsController";

    public static class Default implements IDisplayWindowInsetsController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void insetsChanged(InsetsState insetsState) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void setImeInputTargetRequestedVisibility(boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowInsetsController
        public void topFocusedWindowChanged(ComponentName componentName, int i) throws RemoteException {
        }
    }

    void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException;

    void insetsChanged(InsetsState insetsState) throws RemoteException;

    void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) throws RemoteException;

    void setImeInputTargetRequestedVisibility(boolean z, ImeTracker.Token token) throws RemoteException;

    void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException;

    void topFocusedWindowChanged(ComponentName componentName, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayWindowInsetsController {
        static final int TRANSACTION_hideInsets = 5;
        static final int TRANSACTION_insetsChanged = 2;
        static final int TRANSACTION_insetsControlChanged = 3;
        static final int TRANSACTION_setImeInputTargetRequestedVisibility = 6;
        static final int TRANSACTION_showInsets = 4;
        static final int TRANSACTION_topFocusedWindowChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IDisplayWindowInsetsController.DESCRIPTOR);
        }

        public static IDisplayWindowInsetsController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDisplayWindowInsetsController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayWindowInsetsController)) {
                return (IDisplayWindowInsetsController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "topFocusedWindowChanged";
                case 2:
                    return "insetsChanged";
                case 3:
                    return "insetsControlChanged";
                case 4:
                    return "showInsets";
                case 5:
                    return "hideInsets";
                case 6:
                    return "setImeInputTargetRequestedVisibility";
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
                parcel.enforceInterface(IDisplayWindowInsetsController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayWindowInsetsController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    topFocusedWindowChanged(componentName, i3);
                    return true;
                case 2:
                    InsetsState insetsState = (InsetsState) parcel.readTypedObject(InsetsState.CREATOR);
                    parcel.enforceNoDataAvail();
                    insetsChanged(insetsState);
                    return true;
                case 3:
                    InsetsState insetsState2 = (InsetsState) parcel.readTypedObject(InsetsState.CREATOR);
                    InsetsSourceControl[] insetsSourceControlArr = (InsetsSourceControl[]) parcel.createTypedArray(InsetsSourceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    insetsControlChanged(insetsState2, insetsSourceControlArr);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    showInsets(i4, z, token);
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideInsets(i5, z2, token2);
                    return true;
                case 6:
                    boolean z3 = parcel.readBoolean();
                    ImeTracker.Token token3 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImeInputTargetRequestedVisibility(z3, token3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDisplayWindowInsetsController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayWindowInsetsController.DESCRIPTOR;
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void topFocusedWindowChanged(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowInsetsController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void insetsChanged(InsetsState insetsState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowInsetsController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(insetsState, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowInsetsController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(insetsState, 0);
                    parcelObtain.writeTypedArray(insetsSourceControlArr, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowInsetsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowInsetsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowInsetsController
            public void setImeInputTargetRequestedVisibility(boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowInsetsController.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
