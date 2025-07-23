package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVoiceInteractionAccessibilitySettingsListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener";

    public static class Default implements IVoiceInteractionAccessibilitySettingsListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener
        public void onAccessibilityDetectionChanged(boolean z) throws RemoteException {
        }
    }

    void onAccessibilityDetectionChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractionAccessibilitySettingsListener {
        static final int TRANSACTION_onAccessibilityDetectionChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
        }

        public static IVoiceInteractionAccessibilitySettingsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractionAccessibilitySettingsListener)) {
                return (IVoiceInteractionAccessibilitySettingsListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAccessibilityDetectionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAccessibilityDetectionChanged(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IVoiceInteractionAccessibilitySettingsListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener
            public void onAccessibilityDetectionChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionAccessibilitySettingsListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
