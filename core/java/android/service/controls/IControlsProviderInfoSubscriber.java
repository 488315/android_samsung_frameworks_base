package android.service.controls;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IControlsProviderInfoSubscriber extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsProviderInfoSubscriber";

    void onNext(IBinder iBinder, ControlsProviderInfo controlsProviderInfo) throws RemoteException;

    public static class Default implements IControlsProviderInfoSubscriber {
        @Override // android.service.controls.IControlsProviderInfoSubscriber
        public void onNext(IBinder token, ControlsProviderInfo cpi) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IControlsProviderInfoSubscriber {
        static final int TRANSACTION_onNext = 1;

        public Stub() {
            attachInterface(this, IControlsProviderInfoSubscriber.DESCRIPTOR);
        }

        public static IControlsProviderInfoSubscriber asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IControlsProviderInfoSubscriber.DESCRIPTOR);
            if (iin != null && (iin instanceof IControlsProviderInfoSubscriber)) {
                return (IControlsProviderInfoSubscriber) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onNext";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IControlsProviderInfoSubscriber.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IControlsProviderInfoSubscriber.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            IBinder _arg0 = data.readStrongBinder();
                            ControlsProviderInfo _arg1 = (ControlsProviderInfo) data.readTypedObject(ControlsProviderInfo.CREATOR);
                            data.enforceNoDataAvail();
                            onNext(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IControlsProviderInfoSubscriber {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsProviderInfoSubscriber.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsProviderInfoSubscriber
            public void onNext(IBinder token, ControlsProviderInfo cpi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsProviderInfoSubscriber.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(cpi, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
