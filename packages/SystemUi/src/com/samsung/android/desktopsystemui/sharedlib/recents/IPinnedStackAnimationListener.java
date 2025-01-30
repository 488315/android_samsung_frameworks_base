package com.samsung.android.desktopsystemui.sharedlib.recents;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface IPinnedStackAnimationListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopsystemui.sharedlib.recents.IPinnedStackAnimationListener";

    void onPinnedStackAnimationStarted();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IPinnedStackAnimationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.recents.IPinnedStackAnimationListener
        public void onPinnedStackAnimationStarted() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IPinnedStackAnimationListener {
        static final int TRANSACTION_onPinnedStackAnimationStarted = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IPinnedStackAnimationListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPinnedStackAnimationListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.recents.IPinnedStackAnimationListener
            public void onPinnedStackAnimationStarted() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPinnedStackAnimationListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IPinnedStackAnimationListener.DESCRIPTOR);
        }

        public static IPinnedStackAnimationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPinnedStackAnimationListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPinnedStackAnimationListener)) ? new Proxy(iBinder) : (IPinnedStackAnimationListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPinnedStackAnimationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPinnedStackAnimationListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onPinnedStackAnimationStarted();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
