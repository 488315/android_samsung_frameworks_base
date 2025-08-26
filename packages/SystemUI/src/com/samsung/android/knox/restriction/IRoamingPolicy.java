package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IRoamingPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.IRoamingPolicy";

    public class Default implements IRoamingPolicy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean isRoamingDataEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean isRoamingPushEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean isRoamingSyncEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean setRoamingData(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean setRoamingPush(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean setRoamingSync(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRoamingPolicy
        public boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }
    }

    boolean isRoamingDataEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isRoamingPushEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isRoamingSyncEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean setRoamingData(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setRoamingPush(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setRoamingSync(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IRoamingPolicy {
        public static final int TRANSACTION_isRoamingDataEnabled = 6;
        public static final int TRANSACTION_isRoamingPushEnabled = 4;
        public static final int TRANSACTION_isRoamingSyncEnabled = 2;
        public static final int TRANSACTION_isRoamingVoiceCallsEnabled = 8;
        public static final int TRANSACTION_setRoamingData = 5;
        public static final int TRANSACTION_setRoamingPush = 3;
        public static final int TRANSACTION_setRoamingSync = 1;
        public static final int TRANSACTION_setRoamingVoiceCalls = 7;

        class Proxy implements IRoamingPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRoamingPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean isRoamingDataEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean isRoamingPushEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean isRoamingSyncEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean setRoamingData(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean setRoamingPush(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean setRoamingSync(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRoamingPolicy
            public boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRoamingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRoamingPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRoamingPolicy)) ? new Proxy(iBinder) : (IRoamingPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingSync = setRoamingSync(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingSync);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRoamingSyncEnabled = isRoamingSyncEnabled(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingSyncEnabled);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingPush = setRoamingPush(contextInfo3, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingPush);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRoamingPushEnabled = isRoamingPushEnabled(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingPushEnabled);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingData = setRoamingData(contextInfo5, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingData);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRoamingDataEnabled = isRoamingDataEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingDataEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamingVoiceCalls = setRoamingVoiceCalls(contextInfo7, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamingVoiceCalls);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRoamingVoiceCallsEnabled = isRoamingVoiceCallsEnabled(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingVoiceCallsEnabled);
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
}
