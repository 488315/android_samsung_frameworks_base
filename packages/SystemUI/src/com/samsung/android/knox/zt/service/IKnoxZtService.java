package com.samsung.android.knox.zt.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.knox.zt.service.IChunkedAidlInterface;
import com.samsung.android.knox.zt.service.IServiceCertProvisionListener;
import com.samsung.android.knox.zt.service.IServiceMonitoringListener;
import java.util.List;

/* loaded from: classes4.dex */
public interface IKnoxZtService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IKnoxZtService";

    public class Default implements IKnoxZtService {
        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public String ackSignal(long[] jArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int getAppIdStatus(ParcelableCertificate parcelableCertificate, String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public byte[] getChallenge(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public String getDeviceId(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int getDeviceIdStatus(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int getIntegrityStatus(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public String getMonitoringSnapshot(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int getOrigin(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int getRootOfTrustStatus(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int getSecurityLevel(ParcelableCertificate parcelableCertificate) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public String getVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int provisionCert(ParcelableProfile parcelableProfile, IServiceCertProvisionListener iServiceCertProvisionListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int queryAllSignals(IChunkedAidlInterface iChunkedAidlInterface) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int querySignals(String str, IChunkedAidlInterface iChunkedAidlInterface) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int startMonitoringDomains(List<String> list, List<String> list2, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int startMonitoringFiles(List<String> list, List<Bundle> list2, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int startTracing(int i, Bundle bundle, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int stopMonitoringDomains() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int stopMonitoringFiles() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IKnoxZtService
        public int stopTracing(int i, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
            return 0;
        }
    }

    public class _Parcel {
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

    String ackSignal(long[] jArr) throws RemoteException;

    int getAppIdStatus(ParcelableCertificate parcelableCertificate, String[] strArr) throws RemoteException;

    byte[] getChallenge(ParcelableCertificate parcelableCertificate) throws RemoteException;

    String getDeviceId(ParcelableCertificate parcelableCertificate) throws RemoteException;

    int getDeviceIdStatus(ParcelableCertificate parcelableCertificate) throws RemoteException;

    int getIntegrityStatus(ParcelableCertificate parcelableCertificate) throws RemoteException;

    String getMonitoringSnapshot(int i) throws RemoteException;

    int getOrigin(ParcelableCertificate parcelableCertificate) throws RemoteException;

    int getRootOfTrustStatus(ParcelableCertificate parcelableCertificate) throws RemoteException;

    int getSecurityLevel(ParcelableCertificate parcelableCertificate) throws RemoteException;

    String getVersion() throws RemoteException;

    int provisionCert(ParcelableProfile parcelableProfile, IServiceCertProvisionListener iServiceCertProvisionListener) throws RemoteException;

    int queryAllSignals(IChunkedAidlInterface iChunkedAidlInterface) throws RemoteException;

    int querySignals(String str, IChunkedAidlInterface iChunkedAidlInterface) throws RemoteException;

    int startMonitoringDomains(List<String> list, List<String> list2, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException;

    int startMonitoringFiles(List<String> list, List<Bundle> list2, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException;

    int startTracing(int i, Bundle bundle, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException;

    int stopMonitoringDomains() throws RemoteException;

    int stopMonitoringFiles() throws RemoteException;

    int stopTracing(int i, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException;

    public abstract class Stub extends Binder implements IKnoxZtService {
        public static final int TRANSACTION_ackSignal = 20;
        public static final int TRANSACTION_getAppIdStatus = 1;
        public static final int TRANSACTION_getChallenge = 2;
        public static final int TRANSACTION_getDeviceId = 3;
        public static final int TRANSACTION_getDeviceIdStatus = 4;
        public static final int TRANSACTION_getIntegrityStatus = 5;
        public static final int TRANSACTION_getMonitoringSnapshot = 18;
        public static final int TRANSACTION_getOrigin = 6;
        public static final int TRANSACTION_getRootOfTrustStatus = 7;
        public static final int TRANSACTION_getSecurityLevel = 8;
        public static final int TRANSACTION_getVersion = 19;
        public static final int TRANSACTION_provisionCert = 9;
        public static final int TRANSACTION_queryAllSignals = 16;
        public static final int TRANSACTION_querySignals = 17;
        public static final int TRANSACTION_startMonitoringDomains = 12;
        public static final int TRANSACTION_startMonitoringFiles = 10;
        public static final int TRANSACTION_startTracing = 14;
        public static final int TRANSACTION_stopMonitoringDomains = 13;
        public static final int TRANSACTION_stopMonitoringFiles = 11;
        public static final int TRANSACTION_stopTracing = 15;

        class Proxy implements IKnoxZtService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public String ackSignal(long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeLongArray(jArr);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int getAppIdStatus(ParcelableCertificate parcelableCertificate, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public byte[] getChallenge(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public String getDeviceId(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int getDeviceIdStatus(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int getIntegrityStatus(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxZtService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public String getMonitoringSnapshot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int getOrigin(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int getRootOfTrustStatus(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int getSecurityLevel(ParcelableCertificate parcelableCertificate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableCertificate, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public String getVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int provisionCert(ParcelableProfile parcelableProfile, IServiceCertProvisionListener iServiceCertProvisionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelableProfile, 0);
                    parcelObtain.writeStrongInterface(iServiceCertProvisionListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int queryAllSignals(IChunkedAidlInterface iChunkedAidlInterface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iChunkedAidlInterface);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int querySignals(String str, IChunkedAidlInterface iChunkedAidlInterface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iChunkedAidlInterface);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int startMonitoringDomains(List<String> list, List<String> list2, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int startMonitoringFiles(List<String> list, List<Bundle> list2, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    _Parcel.writeTypedList(parcelObtain, list2, 0);
                    parcelObtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int startTracing(int i, Bundle bundle, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int stopMonitoringDomains() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int stopMonitoringFiles() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IKnoxZtService
            public int stopTracing(int i, IServiceMonitoringListener iServiceMonitoringListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxZtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iServiceMonitoringListener);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxZtService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxZtService)) ? new Proxy(iBinder) : (IKnoxZtService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    int iProvisionCert = provisionCert((ParcelableProfile) _Parcel.readTypedObject(parcel, ParcelableProfile.CREATOR), IServiceCertProvisionListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iProvisionCert);
                    return true;
                case 10:
                    int iStartMonitoringFiles = startMonitoringFiles(parcel.createStringArrayList(), parcel.createTypedArrayList(Bundle.CREATOR), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMonitoringFiles);
                    return true;
                case 11:
                    int iStopMonitoringFiles = stopMonitoringFiles();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopMonitoringFiles);
                    return true;
                case 12:
                    int iStartMonitoringDomains = startMonitoringDomains(parcel.createStringArrayList(), parcel.createStringArrayList(), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMonitoringDomains);
                    return true;
                case 13:
                    int iStopMonitoringDomains = stopMonitoringDomains();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopMonitoringDomains);
                    return true;
                case 14:
                    int iStartTracing = startTracing(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartTracing);
                    return true;
                case 15:
                    int iStopTracing = stopTracing(parcel.readInt(), IServiceMonitoringListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopTracing);
                    return true;
                case 16:
                    int iQueryAllSignals = queryAllSignals(IChunkedAidlInterface.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryAllSignals);
                    return true;
                case 17:
                    int iQuerySignals = querySignals(parcel.readString(), IChunkedAidlInterface.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iQuerySignals);
                    return true;
                case 18:
                    String monitoringSnapshot = getMonitoringSnapshot(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(monitoringSnapshot);
                    return true;
                case 19:
                    String version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(version);
                    return true;
                case 20:
                    String strAckSignal = ackSignal(parcel.createLongArray());
                    parcel2.writeNoException();
                    parcel2.writeString(strAckSignal);
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
