package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyboardBacklightListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IKeyboardBacklightListener";

    void onBrightnessChanged(int i, IKeyboardBacklightState iKeyboardBacklightState, boolean z)
            throws RemoteException;

    public static class Default implements IKeyboardBacklightListener {
        @Override // android.hardware.input.IKeyboardBacklightListener
        public void onBrightnessChanged(
                int deviceId, IKeyboardBacklightState state, boolean isTriggeredByKeyPress)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKeyboardBacklightListener {
        static final int TRANSACTION_onBrightnessChanged = 1;

        public Stub() {
            attachInterface(this, IKeyboardBacklightListener.DESCRIPTOR);
        }

        public static IKeyboardBacklightListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeyboardBacklightListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyboardBacklightListener)) {
                return (IKeyboardBacklightListener) iin;
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
                    return "onBrightnessChanged";
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
                data.enforceInterface(IKeyboardBacklightListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeyboardBacklightListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    IKeyboardBacklightState _arg1 =
                            (IKeyboardBacklightState)
                                    data.readTypedObject(IKeyboardBacklightState.CREATOR);
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onBrightnessChanged(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeyboardBacklightListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyboardBacklightListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IKeyboardBacklightListener
            public void onBrightnessChanged(
                    int deviceId, IKeyboardBacklightState state, boolean isTriggeredByKeyPress)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKeyboardBacklightListener.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeTypedObject(state, 0);
                    _data.writeBoolean(isTriggeredByKeyPress);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
