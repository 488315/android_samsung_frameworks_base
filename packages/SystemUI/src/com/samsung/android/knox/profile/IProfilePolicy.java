package com.samsung.android.knox.profile;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IProfilePolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.profile.IProfilePolicy";

    public class Default implements IProfilePolicy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.profile.IProfilePolicy
        public boolean getRestrictionPolicy(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.profile.IProfilePolicy
        public boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }
    }

    boolean getRestrictionPolicy(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IProfilePolicy {
        public static final int TRANSACTION_getRestrictionPolicy = 2;
        public static final int TRANSACTION_setRestrictionPolicy = 1;

        class Proxy implements IProfilePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProfilePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.profile.IProfilePolicy
            public boolean getRestrictionPolicy(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProfilePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.profile.IProfilePolicy
            public boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProfilePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IProfilePolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IProfilePolicy)) ? new Proxy(iBinder) : (IProfilePolicy) iInterfaceQueryLocalInterface;
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

        public int getMaxTransactionId() {
            return 1;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProfilePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProfilePolicy.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String string = parcel.readString();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean restrictionPolicy = setRestrictionPolicy(contextInfo, string, z);
                parcel2.writeNoException();
                parcel2.writeBoolean(restrictionPolicy);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean restrictionPolicy2 = getRestrictionPolicy(contextInfo2, string2);
                parcel2.writeNoException();
                parcel2.writeBoolean(restrictionPolicy2);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
