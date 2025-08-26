package com.samsung.android.knox.net.apn;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface IApnSettingsPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.apn.IApnSettingsPolicy";

    public class Default implements IApnSettingsPolicy {
        @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
        public long addUpdateApn(ContextInfo contextInfo, boolean z, ApnSettings apnSettings) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
        public boolean deleteApn(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
        public List<ApnSettings> getApnList(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
        public ApnSettings getApnSettings(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
        public ApnSettings getPreferredApn(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
        public boolean setPreferredApn(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }
    }

    long addUpdateApn(ContextInfo contextInfo, boolean z, ApnSettings apnSettings) throws RemoteException;

    boolean deleteApn(ContextInfo contextInfo, long j) throws RemoteException;

    List<ApnSettings> getApnList(ContextInfo contextInfo, int i) throws RemoteException;

    ApnSettings getApnSettings(ContextInfo contextInfo, long j) throws RemoteException;

    ApnSettings getPreferredApn(ContextInfo contextInfo) throws RemoteException;

    boolean setPreferredApn(ContextInfo contextInfo, long j) throws RemoteException;

    public abstract class Stub extends Binder implements IApnSettingsPolicy {
        public static final int TRANSACTION_addUpdateApn = 5;
        public static final int TRANSACTION_deleteApn = 2;
        public static final int TRANSACTION_getApnList = 3;
        public static final int TRANSACTION_getApnSettings = 4;
        public static final int TRANSACTION_getPreferredApn = 6;
        public static final int TRANSACTION_setPreferredApn = 1;

        class Proxy implements IApnSettingsPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public long addUpdateApn(ContextInfo contextInfo, boolean z, ApnSettings apnSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(apnSettings, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public boolean deleteApn(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public List<ApnSettings> getApnList(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ApnSettings.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public ApnSettings getApnSettings(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApnSettings) parcelObtain2.readTypedObject(ApnSettings.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IApnSettingsPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public ApnSettings getPreferredApn(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApnSettings) parcelObtain2.readTypedObject(ApnSettings.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public boolean setPreferredApn(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
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
            attachInterface(this, IApnSettingsPolicy.DESCRIPTOR);
        }

        public static IApnSettingsPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IApnSettingsPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IApnSettingsPolicy)) ? new Proxy(iBinder) : (IApnSettingsPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApnSettingsPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApnSettingsPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean preferredApn = setPreferredApn(contextInfo, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(preferredApn);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteApn = deleteApn(contextInfo2, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteApn);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ApnSettings> apnList = getApnList(contextInfo3, i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(apnList, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ApnSettings apnSettings = getApnSettings(contextInfo4, j3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apnSettings, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    ApnSettings apnSettings2 = (ApnSettings) parcel.readTypedObject(ApnSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jAddUpdateApn = addUpdateApn(contextInfo5, z, apnSettings2);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddUpdateApn);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ApnSettings preferredApn2 = getPreferredApn(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preferredApn2, 1);
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
