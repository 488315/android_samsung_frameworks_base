package com.samsung.android.knox.p046zt.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener;
import com.samsung.android.knox.p046zt.service.IServiceMonitoringListener;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKnoxZtService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IKnoxZtService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKnoxZtService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int getAppIdStatus(ParcelableCertificate parcelableCertificate, String[] strArr) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final byte[] getChallenge(ParcelableCertificate parcelableCertificate) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final String getDeviceId(ParcelableCertificate parcelableCertificate) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int getDeviceIdStatus(ParcelableCertificate parcelableCertificate) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int getIntegrityStatus(ParcelableCertificate parcelableCertificate) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int getOrigin(ParcelableCertificate parcelableCertificate) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int getRootOfTrustStatus(ParcelableCertificate parcelableCertificate) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int getSecurityLevel(ParcelableCertificate parcelableCertificate) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final String loadSignals() {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int provisionCert(ParcelableProfile parcelableProfile, IServiceCertProvisionListener iServiceCertProvisionListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int startMonitoringDomains(List<String> list, List<String> list2, IServiceMonitoringListener iServiceMonitoringListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int startMonitoringFiles(List<String> list, List<Bundle> list2, IServiceMonitoringListener iServiceMonitoringListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int startTracing(int i, Bundle bundle, IServiceMonitoringListener iServiceMonitoringListener) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int stopMonitoringDomains() {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int stopMonitoringFiles() {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
        public final int stopTracing(int i, IServiceMonitoringListener iServiceMonitoringListener) {
            return 0;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class _Parcel {
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static <T extends Parcelable> void writeTypedList(Parcel parcel, List<T> list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                writeTypedObject(parcel, list.get(i2), i);
            }
        }

        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    int getAppIdStatus(ParcelableCertificate parcelableCertificate, String[] strArr);

    byte[] getChallenge(ParcelableCertificate parcelableCertificate);

    String getDeviceId(ParcelableCertificate parcelableCertificate);

    int getDeviceIdStatus(ParcelableCertificate parcelableCertificate);

    int getIntegrityStatus(ParcelableCertificate parcelableCertificate);

    int getOrigin(ParcelableCertificate parcelableCertificate);

    int getRootOfTrustStatus(ParcelableCertificate parcelableCertificate);

    int getSecurityLevel(ParcelableCertificate parcelableCertificate);

    String loadSignals();

    int provisionCert(ParcelableProfile parcelableProfile, IServiceCertProvisionListener iServiceCertProvisionListener);

    int startMonitoringDomains(List<String> list, List<String> list2, IServiceMonitoringListener iServiceMonitoringListener);

    int startMonitoringFiles(List<String> list, List<Bundle> list2, IServiceMonitoringListener iServiceMonitoringListener);

    int startTracing(int i, Bundle bundle, IServiceMonitoringListener iServiceMonitoringListener);

    int stopMonitoringDomains();

    int stopMonitoringFiles();

    int stopTracing(int i, IServiceMonitoringListener iServiceMonitoringListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKnoxZtService {
        public static final int TRANSACTION_getAppIdStatus = 1;
        public static final int TRANSACTION_getChallenge = 2;
        public static final int TRANSACTION_getDeviceId = 3;
        public static final int TRANSACTION_getDeviceIdStatus = 4;
        public static final int TRANSACTION_getIntegrityStatus = 5;
        public static final int TRANSACTION_getOrigin = 6;
        public static final int TRANSACTION_getRootOfTrustStatus = 7;
        public static final int TRANSACTION_getSecurityLevel = 8;
        public static final int TRANSACTION_loadSignals = 16;
        public static final int TRANSACTION_provisionCert = 9;
        public static final int TRANSACTION_startMonitoringDomains = 12;
        public static final int TRANSACTION_startMonitoringFiles = 10;
        public static final int TRANSACTION_startTracing = 14;
        public static final int TRANSACTION_stopMonitoringDomains = 13;
        public static final int TRANSACTION_stopMonitoringFiles = 11;
        public static final int TRANSACTION_stopTracing = 15;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKnoxZtService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int getAppIdStatus(ParcelableCertificate parcelableCertificate, String[] strArr) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final byte[] getChallenge(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final String getDeviceId(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int getDeviceIdStatus(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int getIntegrityStatus(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IKnoxZtService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int getOrigin(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int getRootOfTrustStatus(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int getSecurityLevel(ParcelableCertificate parcelableCertificate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableCertificate, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final String loadSignals() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int provisionCert(ParcelableProfile parcelableProfile, IServiceCertProvisionListener iServiceCertProvisionListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelableProfile, 0);
                    obtain.writeStrongInterface(iServiceCertProvisionListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int startMonitoringDomains(List<String> list, List<String> list2, IServiceMonitoringListener iServiceMonitoringListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int startMonitoringFiles(List<String> list, List<Bundle> list2, IServiceMonitoringListener iServiceMonitoringListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    _Parcel.writeTypedList(obtain, list2, 0);
                    obtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int startTracing(int i, Bundle bundle, IServiceMonitoringListener iServiceMonitoringListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    obtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int stopMonitoringDomains() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int stopMonitoringFiles() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IKnoxZtService
            public final int stopTracing(int i, IServiceMonitoringListener iServiceMonitoringListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxZtService.DESCRIPTOR);
        }

        public static IKnoxZtService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxZtService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxZtService)) ? new Proxy(iBinder) : (IKnoxZtService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxZtService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxZtService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int appIdStatus = getAppIdStatus((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR), parcel.createStringArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(appIdStatus);
                    return true;
                case 2:
                    byte[] challenge = getChallenge((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeByteArray(challenge);
                    return true;
                case 3:
                    String deviceId = getDeviceId((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 4:
                    int deviceIdStatus = getDeviceIdStatus((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceIdStatus);
                    return true;
                case 5:
                    int integrityStatus = getIntegrityStatus((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(integrityStatus);
                    return true;
                case 6:
                    int origin = getOrigin((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(origin);
                    return true;
                case 7:
                    int rootOfTrustStatus = getRootOfTrustStatus((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(rootOfTrustStatus);
                    return true;
                case 8:
                    int securityLevel = getSecurityLevel((ParcelableCertificate) _Parcel.readTypedObject(parcel, ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(securityLevel);
                    return true;
                case 9:
                    int provisionCert = provisionCert((ParcelableProfile) _Parcel.readTypedObject(parcel, ParcelableProfile.CREATOR), IServiceCertProvisionListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(provisionCert);
                    return true;
                case 10:
                    int startMonitoringFiles = startMonitoringFiles(parcel.createStringArrayList(), parcel.createTypedArrayList(Bundle.CREATOR), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(startMonitoringFiles);
                    return true;
                case 11:
                    int stopMonitoringFiles = stopMonitoringFiles();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopMonitoringFiles);
                    return true;
                case 12:
                    int startMonitoringDomains = startMonitoringDomains(parcel.createStringArrayList(), parcel.createStringArrayList(), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(startMonitoringDomains);
                    return true;
                case 13:
                    int stopMonitoringDomains = stopMonitoringDomains();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopMonitoringDomains);
                    return true;
                case 14:
                    int startTracing = startTracing(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(startTracing);
                    return true;
                case 15:
                    int stopTracing = stopTracing(parcel.readInt(), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(stopTracing);
                    return true;
                case 16:
                    String loadSignals = loadSignals();
                    parcel2.writeNoException();
                    parcel2.writeString(loadSignals);
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
