package android.hardware.hdmi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiCecVolumeControlFeatureListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener";

    void onHdmiCecVolumeControlFeature(int i) throws RemoteException;

    public static class Default implements IHdmiCecVolumeControlFeatureListener {
        @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
        public void onHdmiCecVolumeControlFeature(int hdmiCecVolumeControl) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IHdmiCecVolumeControlFeatureListener {
        static final int TRANSACTION_onHdmiCecVolumeControlFeature = 1;

        public Stub() {
            attachInterface(this, IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
        }

        public static IHdmiCecVolumeControlFeatureListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IHdmiCecVolumeControlFeatureListener)) {
                return (IHdmiCecVolumeControlFeatureListener) iin;
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
                    return "onHdmiCecVolumeControlFeature";
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
                data.enforceInterface(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onHdmiCecVolumeControlFeature(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IHdmiCecVolumeControlFeatureListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHdmiCecVolumeControlFeatureListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
            public void onHdmiCecVolumeControlFeature(int hdmiCecVolumeControl) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
                    _data.writeInt(hdmiCecVolumeControl);
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
