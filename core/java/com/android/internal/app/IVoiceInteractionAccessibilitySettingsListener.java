package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVoiceInteractionAccessibilitySettingsListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener";

    void onAccessibilityDetectionChanged(boolean z) throws RemoteException;

    public static class Default implements IVoiceInteractionAccessibilitySettingsListener {
        @Override // com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener
        public void onAccessibilityDetectionChanged(boolean enable) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements IVoiceInteractionAccessibilitySettingsListener {
        static final int TRANSACTION_onAccessibilityDetectionChanged = 1;

        public Stub() {
            attachInterface(this, IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
        }

        public static IVoiceInteractionAccessibilitySettingsListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(
                            IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IVoiceInteractionAccessibilitySettingsListener)) {
                return (IVoiceInteractionAccessibilitySettingsListener) iin;
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
                    return "onAccessibilityDetectionChanged";
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
                data.enforceInterface(IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onAccessibilityDetectionChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVoiceInteractionAccessibilitySettingsListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener
            public void onAccessibilityDetectionChanged(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
                    _data.writeBoolean(enable);
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
