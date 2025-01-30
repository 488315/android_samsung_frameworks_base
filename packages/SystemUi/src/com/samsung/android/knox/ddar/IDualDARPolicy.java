package com.samsung.android.knox.ddar;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDualDARPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ddar.IDualDARPolicy";

    boolean clearPolicy(ContextInfo contextInfo);

    boolean clearResetPasswordTokenForInner(ContextInfo contextInfo);

    String getClientInfo(int i);

    Bundle getConfig(ContextInfo contextInfo);

    int getPasswordMinimumLengthForInner(ContextInfo contextInfo);

    boolean isActivePasswordSufficientForInner(ContextInfo contextInfo);

    boolean isResetPasswordTokenActiveForInner(ContextInfo contextInfo);

    boolean resetPasswordWithTokenForInner(ContextInfo contextInfo, String str, byte[] bArr);

    void setClientInfo(ContextInfo contextInfo, String str, String str2, String str3);

    int setConfig(ContextInfo contextInfo, Bundle bundle);

    boolean setPasswordMinimumLengthForInner(ContextInfo contextInfo, int i);

    boolean setResetPasswordTokenForInner(ContextInfo contextInfo, byte[] bArr);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDualDARPolicy {
        static final int TRANSACTION_clearPolicy = 3;
        static final int TRANSACTION_clearResetPasswordTokenForInner = 10;
        static final int TRANSACTION_getClientInfo = 5;
        static final int TRANSACTION_getConfig = 1;
        static final int TRANSACTION_getPasswordMinimumLengthForInner = 7;
        static final int TRANSACTION_isActivePasswordSufficientForInner = 8;
        static final int TRANSACTION_isResetPasswordTokenActiveForInner = 11;
        static final int TRANSACTION_resetPasswordWithTokenForInner = 12;
        static final int TRANSACTION_setClientInfo = 4;
        static final int TRANSACTION_setConfig = 2;
        static final int TRANSACTION_setPasswordMinimumLengthForInner = 6;
        static final int TRANSACTION_setResetPasswordTokenForInner = 9;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDualDARPolicy {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean clearPolicy(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean clearResetPasswordTokenForInner(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public String getClientInfo(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public Bundle getConfig(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDualDARPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public int getPasswordMinimumLengthForInner(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean isActivePasswordSufficientForInner(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean isResetPasswordTokenActiveForInner(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean resetPasswordWithTokenForInner(ContextInfo contextInfo, String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public void setClientInfo(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public int setConfig(ContextInfo contextInfo, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean setPasswordMinimumLengthForInner(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ddar.IDualDARPolicy
            public boolean setResetPasswordTokenForInner(ContextInfo contextInfo, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDualDARPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDualDARPolicy.DESCRIPTOR);
        }

        public static IDualDARPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDualDARPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDualDARPolicy)) ? new Proxy(iBinder) : (IDualDARPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDualDARPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDualDARPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle config = getConfig(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(config, 1);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int config2 = setConfig(contextInfo2, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(config2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearPolicy = clearPolicy(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearPolicy);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setClientInfo(contextInfo4, readString, readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String clientInfo = getClientInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(clientInfo);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean passwordMinimumLengthForInner = setPasswordMinimumLengthForInner(contextInfo5, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordMinimumLengthForInner);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumLengthForInner2 = getPasswordMinimumLengthForInner(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumLengthForInner2);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isActivePasswordSufficientForInner = isActivePasswordSufficientForInner(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivePasswordSufficientForInner);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordTokenForInner = setResetPasswordTokenForInner(contextInfo8, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordTokenForInner);
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearResetPasswordTokenForInner = clearResetPasswordTokenForInner(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearResetPasswordTokenForInner);
                    return true;
                case 11:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isResetPasswordTokenActiveForInner = isResetPasswordTokenActiveForInner(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isResetPasswordTokenActiveForInner);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordWithTokenForInner = resetPasswordWithTokenForInner(contextInfo11, readString4, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordWithTokenForInner);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDualDARPolicy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean clearPolicy(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean clearResetPasswordTokenForInner(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public String getClientInfo(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public Bundle getConfig(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public int getPasswordMinimumLengthForInner(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean isActivePasswordSufficientForInner(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean isResetPasswordTokenActiveForInner(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean resetPasswordWithTokenForInner(ContextInfo contextInfo, String str, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public int setConfig(ContextInfo contextInfo, Bundle bundle) {
            return 0;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean setPasswordMinimumLengthForInner(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public boolean setResetPasswordTokenForInner(ContextInfo contextInfo, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.ddar.IDualDARPolicy
        public void setClientInfo(ContextInfo contextInfo, String str, String str2, String str3) {
        }
    }
}
