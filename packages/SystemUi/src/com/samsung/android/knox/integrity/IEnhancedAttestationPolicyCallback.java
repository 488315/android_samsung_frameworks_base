package com.samsung.android.knox.integrity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEnhancedAttestationPolicyCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback";

    void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEnhancedAttestationPolicyCallback {
        public static final int TRANSACTION_onAttestationFinished = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEnhancedAttestationPolicyCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IEnhancedAttestationPolicyCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback
            public final void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(enhancedAttestationResult, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnhancedAttestationPolicyCallback.DESCRIPTOR);
        }

        public static IEnhancedAttestationPolicyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEnhancedAttestationPolicyCallback)) ? new Proxy(iBinder) : (IEnhancedAttestationPolicyCallback) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAttestationFinished";
        }

        public final int getMaxTransactionId() {
            return 0;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            EnhancedAttestationResult enhancedAttestationResult = (EnhancedAttestationResult) parcel.readTypedObject(EnhancedAttestationResult.CREATOR);
            parcel.enforceNoDataAvail();
            onAttestationFinished(enhancedAttestationResult);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IEnhancedAttestationPolicyCallback {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback
        public final void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) {
        }
    }
}
