package com.samsung.android.knox.profile;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IProfilePolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.profile.IProfilePolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IProfilePolicy {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.profile.IProfilePolicy
        public final boolean getRestrictionPolicy(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.profile.IProfilePolicy
        public final boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }
    }

    boolean getRestrictionPolicy(ContextInfo contextInfo, String str);

    boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IProfilePolicy {
        public static final int TRANSACTION_getRestrictionPolicy = 2;
        public static final int TRANSACTION_setRestrictionPolicy = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IProfilePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IProfilePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.profile.IProfilePolicy
            public final boolean getRestrictionPolicy(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProfilePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.profile.IProfilePolicy
            public final boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProfilePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IProfilePolicy.DESCRIPTOR);
        }

        public static IProfilePolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProfilePolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IProfilePolicy)) ? new Proxy(iBinder) : (IProfilePolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setRestrictionPolicy";
            }
            if (i != 2) {
                return null;
            }
            return "getRestrictionPolicy";
        }

        public final int getMaxTransactionId() {
            return 1;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProfilePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProfilePolicy.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String readString = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean restrictionPolicy = setRestrictionPolicy(contextInfo, readString, readBoolean);
                parcel2.writeNoException();
                parcel2.writeBoolean(restrictionPolicy);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean restrictionPolicy2 = getRestrictionPolicy(contextInfo2, readString2);
                parcel2.writeNoException();
                parcel2.writeBoolean(restrictionPolicy2);
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
