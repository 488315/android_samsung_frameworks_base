package com.samsung.android.knox.ucm.plugin.agent;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IUcmAgentServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.plugin.agent.IUcmAgentServiceCallback";

    void onCredentialStorageChange();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IUcmAgentServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentServiceCallback
        public void onCredentialStorageChange() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IUcmAgentServiceCallback {
        static final int TRANSACTION_onCredentialStorageChange = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IUcmAgentServiceCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUcmAgentServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentServiceCallback
            public void onCredentialStorageChange() {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUcmAgentServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUcmAgentServiceCallback.DESCRIPTOR);
        }

        public static IUcmAgentServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUcmAgentServiceCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUcmAgentServiceCallback)) ? new Proxy(iBinder) : (IUcmAgentServiceCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUcmAgentServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUcmAgentServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onCredentialStorageChange();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
