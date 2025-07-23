package com.samsung.android.knox.net.apn;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IApnSettingsPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.apn.IApnSettingsPolicy";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IApnSettingsPolicy {
        public static final int TRANSACTION_addUpdateApn = 5;
        public static final int TRANSACTION_deleteApn = 2;
        public static final int TRANSACTION_getApnList = 3;
        public static final int TRANSACTION_getApnSettings = 4;
        public static final int TRANSACTION_getPreferredApn = 6;
        public static final int TRANSACTION_setPreferredApn = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IApnSettingsPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public long addUpdateApn(ContextInfo contextInfo, boolean z, ApnSettings apnSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(apnSettings, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public boolean deleteApn(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public List<ApnSettings> getApnList(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ApnSettings.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public ApnSettings getApnSettings(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApnSettings) obtain2.readTypedObject(ApnSettings.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IApnSettingsPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public ApnSettings getPreferredApn(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApnSettings) obtain2.readTypedObject(ApnSettings.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.apn.IApnSettingsPolicy
            public boolean setPreferredApn(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApnSettingsPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
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
            attachInterface(this, IApnSettingsPolicy.DESCRIPTOR);
        }

        public static IApnSettingsPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IApnSettingsPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IApnSettingsPolicy)) ? new Proxy(iBinder) : (IApnSettingsPolicy) queryLocalInterface;
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
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean preferredApn = setPreferredApn(contextInfo, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(preferredApn);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean deleteApn = deleteApn(contextInfo2, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteApn);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ApnSettings> apnList = getApnList(contextInfo3, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(apnList, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ApnSettings apnSettings = getApnSettings(contextInfo4, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apnSettings, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    ApnSettings apnSettings2 = (ApnSettings) parcel.readTypedObject(ApnSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    long addUpdateApn = addUpdateApn(contextInfo5, readBoolean, apnSettings2);
                    parcel2.writeNoException();
                    parcel2.writeLong(addUpdateApn);
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
