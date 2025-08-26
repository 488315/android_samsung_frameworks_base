package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyGestureEventListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IKeyGestureEventListener";

    public static class Default implements IKeyGestureEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IKeyGestureEventListener
        public void onKeyGestureEvent(AidlKeyGestureEvent aidlKeyGestureEvent) throws RemoteException {
        }
    }

    void onKeyGestureEvent(AidlKeyGestureEvent aidlKeyGestureEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyGestureEventListener {
        static final int TRANSACTION_onKeyGestureEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeyGestureEventListener.DESCRIPTOR);
        }

        public static IKeyGestureEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyGestureEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyGestureEventListener)) {
                return (IKeyGestureEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onKeyGestureEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyGestureEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyGestureEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AidlKeyGestureEvent aidlKeyGestureEvent = (AidlKeyGestureEvent) parcel.readTypedObject(AidlKeyGestureEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onKeyGestureEvent(aidlKeyGestureEvent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyGestureEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyGestureEventListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IKeyGestureEventListener
            public void onKeyGestureEvent(AidlKeyGestureEvent aidlKeyGestureEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyGestureEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(aidlKeyGestureEvent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
