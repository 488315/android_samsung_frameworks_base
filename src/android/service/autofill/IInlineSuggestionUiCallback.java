package android.service.autofill;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.IInlineSuggestionUi;
import android.view.SurfaceControlViewHost;

/* loaded from: classes3.dex */
public interface IInlineSuggestionUiCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.IInlineSuggestionUiCallback";

    public static class Default implements IInlineSuggestionUiCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onClick() throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onContent(IInlineSuggestionUi iInlineSuggestionUi, SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onError() throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onLongClick() throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onStartIntentSender(IntentSender intentSender) throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUiCallback
        public void onTransferTouchFocusToImeWindow(IBinder iBinder, int i) throws RemoteException {
        }
    }

    void onClick() throws RemoteException;

    void onContent(IInlineSuggestionUi iInlineSuggestionUi, SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException;

    void onError() throws RemoteException;

    void onLongClick() throws RemoteException;

    void onStartIntentSender(IntentSender intentSender) throws RemoteException;

    void onTransferTouchFocusToImeWindow(IBinder iBinder, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineSuggestionUiCallback {
        static final int TRANSACTION_onClick = 1;
        static final int TRANSACTION_onContent = 3;
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onLongClick = 2;
        static final int TRANSACTION_onStartIntentSender = 6;
        static final int TRANSACTION_onTransferTouchFocusToImeWindow = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IInlineSuggestionUiCallback.DESCRIPTOR);
        }

        public static IInlineSuggestionUiCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInlineSuggestionUiCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInlineSuggestionUiCallback)) {
                return (IInlineSuggestionUiCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onClick";
                case 2:
                    return "onLongClick";
                case 3:
                    return "onContent";
                case 4:
                    return "onError";
                case 5:
                    return "onTransferTouchFocusToImeWindow";
                case 6:
                    return "onStartIntentSender";
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
                parcel.enforceInterface(IInlineSuggestionUiCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineSuggestionUiCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onClick();
                    return true;
                case 2:
                    onLongClick();
                    return true;
                case 3:
                    IInlineSuggestionUi iInlineSuggestionUiAsInterface = IInlineSuggestionUi.Stub.asInterface(parcel.readStrongBinder());
                    SurfaceControlViewHost.SurfacePackage surfacePackage = (SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContent(iInlineSuggestionUiAsInterface, surfacePackage, i3, i4);
                    return true;
                case 4:
                    onError();
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTransferTouchFocusToImeWindow(strongBinder, i5);
                    return true;
                case 6:
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStartIntentSender(intentSender);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInlineSuggestionUiCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineSuggestionUiCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onClick() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUiCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onLongClick() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUiCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onContent(IInlineSuggestionUi iInlineSuggestionUi, SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUiCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInlineSuggestionUi);
                    parcelObtain.writeTypedObject(surfacePackage, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onError() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUiCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onTransferTouchFocusToImeWindow(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUiCallback.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUiCallback
            public void onStartIntentSender(IntentSender intentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUiCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
