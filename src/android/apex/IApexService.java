package android.apex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IApexService extends IInterface {
    public static final String DESCRIPTOR = "android.apex.IApexService";

    public static class Default implements IApexService {
        @Override // android.apex.IApexService
        public void abortStagedSession(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.apex.IApexService
        public long calculateSizeForCompressedApex(CompressedApexInfoList compressedApexInfoList) throws RemoteException {
            return 0L;
        }

        @Override // android.apex.IApexService
        public void destroyCeSnapshots(int i, int i2) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void destroyDeSnapshots(int i) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public ApexInfo[] getActivePackages() throws RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public ApexInfo[] getAllPackages() throws RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public ApexSessionInfo[] getSessions() throws RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public ApexInfo[] getStagedApexInfos(ApexSessionParams apexSessionParams) throws RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public ApexSessionInfo getStagedSessionInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public ApexInfo installAndActivatePackage(String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.apex.IApexService
        public void markBootCompleted() throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void markStagedSessionReady(int i) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void markStagedSessionSuccessful(int i) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void recollectPreinstalledData() throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void reserveSpaceForCompressedApex(CompressedApexInfoList compressedApexInfoList) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void restoreCeData(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void resumeRevertIfNeeded() throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void revertActiveSessions() throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void snapshotCeData(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void stagePackages(List<String> list) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void submitStagedSession(ApexSessionParams apexSessionParams, ApexInfoList apexInfoList) throws RemoteException {
        }

        @Override // android.apex.IApexService
        public void unstagePackages(List<String> list) throws RemoteException {
        }
    }

    void abortStagedSession(int i) throws RemoteException;

    long calculateSizeForCompressedApex(CompressedApexInfoList compressedApexInfoList) throws RemoteException;

    void destroyCeSnapshots(int i, int i2) throws RemoteException;

    void destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws RemoteException;

    void destroyDeSnapshots(int i) throws RemoteException;

    ApexInfo[] getActivePackages() throws RemoteException;

    ApexInfo[] getAllPackages() throws RemoteException;

    ApexSessionInfo[] getSessions() throws RemoteException;

    ApexInfo[] getStagedApexInfos(ApexSessionParams apexSessionParams) throws RemoteException;

    ApexSessionInfo getStagedSessionInfo(int i) throws RemoteException;

    ApexInfo installAndActivatePackage(String str, boolean z) throws RemoteException;

    void markBootCompleted() throws RemoteException;

    void markStagedSessionReady(int i) throws RemoteException;

    void markStagedSessionSuccessful(int i) throws RemoteException;

    void recollectPreinstalledData() throws RemoteException;

    void reserveSpaceForCompressedApex(CompressedApexInfoList compressedApexInfoList) throws RemoteException;

    void restoreCeData(int i, int i2, String str) throws RemoteException;

    void resumeRevertIfNeeded() throws RemoteException;

    void revertActiveSessions() throws RemoteException;

    void snapshotCeData(int i, int i2, String str) throws RemoteException;

    void stagePackages(List<String> list) throws RemoteException;

    void submitStagedSession(ApexSessionParams apexSessionParams, ApexInfoList apexInfoList) throws RemoteException;

    void unstagePackages(List<String> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IApexService {
        static final int TRANSACTION_abortStagedSession = 9;
        static final int TRANSACTION_calculateSizeForCompressedApex = 21;
        static final int TRANSACTION_destroyCeSnapshots = 14;
        static final int TRANSACTION_destroyCeSnapshotsNotSpecified = 15;
        static final int TRANSACTION_destroyDeSnapshots = 13;
        static final int TRANSACTION_getActivePackages = 7;
        static final int TRANSACTION_getAllPackages = 8;
        static final int TRANSACTION_getSessions = 4;
        static final int TRANSACTION_getStagedApexInfos = 6;
        static final int TRANSACTION_getStagedSessionInfo = 5;
        static final int TRANSACTION_installAndActivatePackage = 23;
        static final int TRANSACTION_markBootCompleted = 20;
        static final int TRANSACTION_markStagedSessionReady = 2;
        static final int TRANSACTION_markStagedSessionSuccessful = 3;
        static final int TRANSACTION_recollectPreinstalledData = 19;
        static final int TRANSACTION_reserveSpaceForCompressedApex = 22;
        static final int TRANSACTION_restoreCeData = 12;
        static final int TRANSACTION_resumeRevertIfNeeded = 18;
        static final int TRANSACTION_revertActiveSessions = 10;
        static final int TRANSACTION_snapshotCeData = 11;
        static final int TRANSACTION_stagePackages = 17;
        static final int TRANSACTION_submitStagedSession = 1;
        static final int TRANSACTION_unstagePackages = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IApexService.DESCRIPTOR);
        }

        public static IApexService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IApexService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IApexService)) {
                return (IApexService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApexService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApexService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ApexSessionParams apexSessionParams = (ApexSessionParams) parcel.readTypedObject(ApexSessionParams.CREATOR);
                    ApexInfoList apexInfoList = new ApexInfoList();
                    submitStagedSession(apexSessionParams, apexInfoList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apexInfoList, 1);
                    return true;
                case 2:
                    markStagedSessionReady(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    markStagedSessionSuccessful(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ApexSessionInfo[] sessions = getSessions();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(sessions, 1);
                    return true;
                case 5:
                    ApexSessionInfo stagedSessionInfo = getStagedSessionInfo(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stagedSessionInfo, 1);
                    return true;
                case 6:
                    ApexInfo[] stagedApexInfos = getStagedApexInfos((ApexSessionParams) parcel.readTypedObject(ApexSessionParams.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(stagedApexInfos, 1);
                    return true;
                case 7:
                    ApexInfo[] activePackages = getActivePackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activePackages, 1);
                    return true;
                case 8:
                    ApexInfo[] allPackages = getAllPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allPackages, 1);
                    return true;
                case 9:
                    abortStagedSession(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    revertActiveSessions();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    snapshotCeData(parcel.readInt(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    restoreCeData(parcel.readInt(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    destroyDeSnapshots(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 14:
                    destroyCeSnapshots(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 15:
                    destroyCeSnapshotsNotSpecified(parcel.readInt(), parcel.createIntArray());
                    parcel2.writeNoException();
                    return true;
                case 16:
                    unstagePackages(parcel.createStringArrayList());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    stagePackages(parcel.createStringArrayList());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    resumeRevertIfNeeded();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    recollectPreinstalledData();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    markBootCompleted();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    long jCalculateSizeForCompressedApex = calculateSizeForCompressedApex((CompressedApexInfoList) parcel.readTypedObject(CompressedApexInfoList.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeLong(jCalculateSizeForCompressedApex);
                    return true;
                case 22:
                    reserveSpaceForCompressedApex((CompressedApexInfoList) parcel.readTypedObject(CompressedApexInfoList.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 23:
                    ApexInfo apexInfoInstallAndActivatePackage = installAndActivatePackage(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apexInfoInstallAndActivatePackage, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IApexService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApexService.DESCRIPTOR;
            }

            @Override // android.apex.IApexService
            public void submitStagedSession(ApexSessionParams apexSessionParams, ApexInfoList apexInfoList) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(apexSessionParams, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        apexInfoList.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void markStagedSessionReady(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void markStagedSessionSuccessful(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public ApexSessionInfo[] getSessions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApexSessionInfo[]) parcelObtain2.createTypedArray(ApexSessionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public ApexSessionInfo getStagedSessionInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApexSessionInfo) parcelObtain2.readTypedObject(ApexSessionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public ApexInfo[] getStagedApexInfos(ApexSessionParams apexSessionParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(apexSessionParams, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApexInfo[]) parcelObtain2.createTypedArray(ApexInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public ApexInfo[] getActivePackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApexInfo[]) parcelObtain2.createTypedArray(ApexInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public ApexInfo[] getAllPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApexInfo[]) parcelObtain2.createTypedArray(ApexInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void abortStagedSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void revertActiveSessions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void snapshotCeData(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void restoreCeData(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void destroyDeSnapshots(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void destroyCeSnapshots(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void unstagePackages(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void stagePackages(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void resumeRevertIfNeeded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void recollectPreinstalledData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void markBootCompleted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public long calculateSizeForCompressedApex(CompressedApexInfoList compressedApexInfoList) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compressedApexInfoList, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public void reserveSpaceForCompressedApex(CompressedApexInfoList compressedApexInfoList) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compressedApexInfoList, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.apex.IApexService
            public ApexInfo installAndActivatePackage(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApexService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApexInfo) parcelObtain2.readTypedObject(ApexInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
