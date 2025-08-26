package android.hardware.fingerprint;

import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUdfpsOverlayController extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.fingerprint.IUdfpsOverlayController";

    public static class Default implements IUdfpsOverlayController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void hideUdfpsOverlay(int i) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void onAcquired(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void onEnrollmentHelp(int i) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void onEnrollmentProgress(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void setDebugMessage(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayController
        public void showUdfpsOverlay(long j, int i, int i2, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) throws RemoteException {
        }
    }

    void hideUdfpsOverlay(int i) throws RemoteException;

    void onAcquired(int i, int i2) throws RemoteException;

    void onEnrollmentHelp(int i) throws RemoteException;

    void onEnrollmentProgress(int i, int i2) throws RemoteException;

    void setDebugMessage(int i, String str) throws RemoteException;

    void showUdfpsOverlay(long j, int i, int i2, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IUdfpsOverlayController {
        static final int TRANSACTION_hideUdfpsOverlay = 2;
        static final int TRANSACTION_onAcquired = 3;
        static final int TRANSACTION_onEnrollmentHelp = 5;
        static final int TRANSACTION_onEnrollmentProgress = 4;
        static final int TRANSACTION_setDebugMessage = 6;
        static final int TRANSACTION_showUdfpsOverlay = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IUdfpsOverlayController.DESCRIPTOR);
        }

        public static IUdfpsOverlayController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUdfpsOverlayController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUdfpsOverlayController)) {
                return (IUdfpsOverlayController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showUdfpsOverlay";
                case 2:
                    return "hideUdfpsOverlay";
                case 3:
                    return "onAcquired";
                case 4:
                    return "onEnrollmentProgress";
                case 5:
                    return "onEnrollmentHelp";
                case 6:
                    return "setDebugMessage";
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
                parcel.enforceInterface(IUdfpsOverlayController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUdfpsOverlayController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallbackAsInterface = IUdfpsOverlayControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    showUdfpsOverlay(j, i3, i4, iUdfpsOverlayControllerCallbackAsInterface);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideUdfpsOverlay(i5);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(i6, i7);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollmentProgress(i8, i9);
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollmentHelp(i10);
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDebugMessage(i11, string);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUdfpsOverlayController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUdfpsOverlayController.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void showUdfpsOverlay(long j, int i, int i2, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayController.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iUdfpsOverlayControllerCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void hideUdfpsOverlay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void onAcquired(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void onEnrollmentProgress(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void onEnrollmentHelp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayController
            public void setDebugMessage(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
