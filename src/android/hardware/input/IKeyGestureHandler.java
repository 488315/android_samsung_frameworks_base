package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyGestureHandler extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IKeyGestureHandler";

    public static class Default implements IKeyGestureHandler {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IKeyGestureHandler
        public void handleKeyGesture(AidlKeyGestureEvent aidlKeyGestureEvent, IBinder iBinder) throws RemoteException {
        }
    }

    void handleKeyGesture(AidlKeyGestureEvent aidlKeyGestureEvent, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyGestureHandler {
        static final int TRANSACTION_handleKeyGesture = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeyGestureHandler.DESCRIPTOR);
        }

        public static IKeyGestureHandler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyGestureHandler.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyGestureHandler)) {
                return (IKeyGestureHandler) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "handleKeyGesture";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyGestureHandler.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyGestureHandler.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AidlKeyGestureEvent aidlKeyGestureEvent = (AidlKeyGestureEvent) parcel.readTypedObject(AidlKeyGestureEvent.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                handleKeyGesture(aidlKeyGestureEvent, strongBinder);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyGestureHandler {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyGestureHandler.DESCRIPTOR;
            }

            @Override // android.hardware.input.IKeyGestureHandler
            public void handleKeyGesture(AidlKeyGestureEvent aidlKeyGestureEvent, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyGestureHandler.DESCRIPTOR);
                    parcelObtain.writeTypedObject(aidlKeyGestureEvent, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
