package com.samsung.android.knox.foresight.framework.system;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKFCommnadService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.foresight.framework.system.IKFCommnadService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKFCommnadService {
        @Override // com.samsung.android.knox.foresight.framework.system.IKFCommnadService
        public final String SendCommand(String str) {
            return null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }
    }

    String SendCommand(String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKFCommnadService {
        public static final int TRANSACTION_SendCommand = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKFCommnadService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.foresight.framework.system.IKFCommnadService
            public final String SendCommand(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKFCommnadService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IKFCommnadService.DESCRIPTOR;
            }
        }

        public Stub() {
            attachInterface(this, IKFCommnadService.DESCRIPTOR);
        }

        public static IKFCommnadService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKFCommnadService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKFCommnadService)) ? new Proxy(iBinder) : (IKFCommnadService) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "SendCommand";
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
                parcel.enforceInterface(IKFCommnadService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKFCommnadService.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            String SendCommand = SendCommand(readString);
            parcel2.writeNoException();
            parcel2.writeString(SendCommand);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
