package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IRoamingPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.IRoamingPolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IRoamingPolicy {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean isRoamingDataEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean isRoamingPushEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean isRoamingSyncEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean setRoamingData(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean setRoamingPush(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean setRoamingSync(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public final boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) {
            return false;
        }
    }

    boolean isRoamingDataEnabled(ContextInfo contextInfo);

    boolean isRoamingPushEnabled(ContextInfo contextInfo);

    boolean isRoamingSyncEnabled(ContextInfo contextInfo);

    boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo);

    boolean setRoamingData(ContextInfo contextInfo, boolean z);

    boolean setRoamingPush(ContextInfo contextInfo, boolean z);

    boolean setRoamingSync(ContextInfo contextInfo, boolean z);

    boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IRoamingPolicy {
        public static final int TRANSACTION_isRoamingDataEnabled = 6;
        public static final int TRANSACTION_isRoamingPushEnabled = 4;
        public static final int TRANSACTION_isRoamingSyncEnabled = 2;
        public static final int TRANSACTION_isRoamingVoiceCallsEnabled = 8;
        public static final int TRANSACTION_setRoamingData = 5;
        public static final int TRANSACTION_setRoamingPush = 3;
        public static final int TRANSACTION_setRoamingSync = 1;
        public static final int TRANSACTION_setRoamingVoiceCalls = 7;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IRoamingPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IRoamingPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean isRoamingDataEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean isRoamingPushEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean isRoamingSyncEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean setRoamingData(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean setRoamingPush(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean setRoamingSync(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public final boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRoamingPolicy.DESCRIPTOR);
        }

        public static IRoamingPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRoamingPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRoamingPolicy)) ? new Proxy(iBinder) : (IRoamingPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRoamingPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRoamingPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingSync = setRoamingSync(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingSync);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRoamingSyncEnabled = isRoamingSyncEnabled(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingSyncEnabled);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingPush = setRoamingPush(contextInfo3, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingPush);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRoamingPushEnabled = isRoamingPushEnabled(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingPushEnabled);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingData = setRoamingData(contextInfo5, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingData);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRoamingDataEnabled = isRoamingDataEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingDataEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingVoiceCalls = setRoamingVoiceCalls(contextInfo7, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingVoiceCalls);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRoamingVoiceCallsEnabled = isRoamingVoiceCallsEnabled(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingVoiceCallsEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
