package android.telephony.ims.aidl;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.telephony.ims.stub.ImsFeatureConfiguration;

/* loaded from: classes3.dex */
public interface IImsServiceControllerListener extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsServiceControllerListener";

    void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) throws RemoteException;

    public static class Default implements IImsServiceControllerListener {
        @Override // android.telephony.ims.aidl.IImsServiceControllerListener
        public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration c) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsServiceControllerListener {
        static final int TRANSACTION_onUpdateSupportedImsFeatures = 1;

        public Stub() {
            attachInterface(this, IImsServiceControllerListener.DESCRIPTOR);
        }

        public static IImsServiceControllerListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsServiceControllerListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsServiceControllerListener)) {
                return (IImsServiceControllerListener) iin;
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
                    return "onUpdateSupportedImsFeatures";
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
                data.enforceInterface(IImsServiceControllerListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IImsServiceControllerListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ImsFeatureConfiguration _arg0 = (ImsFeatureConfiguration) data.readTypedObject(ImsFeatureConfiguration.CREATOR);
                            data.enforceNoDataAvail();
                            onUpdateSupportedImsFeatures(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IImsServiceControllerListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsServiceControllerListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsServiceControllerListener
            public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsServiceControllerListener.DESCRIPTOR);
                    _data.writeTypedObject(c, 0);
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
