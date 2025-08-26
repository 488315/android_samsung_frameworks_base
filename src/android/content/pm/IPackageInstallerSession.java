package android.content.pm;

import android.Manifest;
import android.app.ActivityThread;
import android.content.IntentSender;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.PackageInstaller;
import android.content.pm.verify.domain.DomainSet;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IPackageInstallerSession extends IInterface {

    public static class Default implements IPackageInstallerSession {
        @Override // android.content.pm.IPackageInstallerSession
        public void abandon() throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void addChildSessionId(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void addClientProgress(float f) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void addFile(int i, String str, long j, byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void close() throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void commit(IntentSender intentSender, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public List<String> fetchPackageNames() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public ParcelFileDescriptor getAppMetadataFd() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public int[] getChildSessionIds() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public DataLoaderParamsParcel getDataLoaderParams() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public int getInstallFlags() throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public String[] getNames() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public int getParentSessionId() throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public DomainSet getPreVerifiedDomains() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isApplicationEnabledSettingPersistent() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isMultiPackage() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isRequestUpdateOwnership() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public boolean isStaged() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public ParcelFileDescriptor openRead(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public ParcelFileDescriptor openWrite(String str, long j, long j2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public ParcelFileDescriptor openWriteAppMetadata() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeAppMetadata() throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeChildSessionId(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeFile(int i, String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void removeSplit(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void requestChecksums(String str, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void requestUserPreapproval(PackageInstaller.PreapprovalDetails preapprovalDetails, IntentSender intentSender) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void seal() throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void setChecksums(String str, Checksum[] checksumArr, byte[] bArr) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void setClientProgress(float f) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void setPreVerifiedDomains(DomainSet domainSet) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void stageViaHardLink(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void transfer(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerSession
        public void write(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    void abandon() throws RemoteException;

    void addChildSessionId(int i) throws RemoteException;

    void addClientProgress(float f) throws RemoteException;

    void addFile(int i, String str, long j, byte[] bArr, byte[] bArr2) throws RemoteException;

    void close() throws RemoteException;

    void commit(IntentSender intentSender, boolean z) throws RemoteException;

    List<String> fetchPackageNames() throws RemoteException;

    ParcelFileDescriptor getAppMetadataFd() throws RemoteException;

    int[] getChildSessionIds() throws RemoteException;

    DataLoaderParamsParcel getDataLoaderParams() throws RemoteException;

    int getInstallFlags() throws RemoteException;

    String[] getNames() throws RemoteException;

    int getParentSessionId() throws RemoteException;

    DomainSet getPreVerifiedDomains() throws RemoteException;

    boolean isApplicationEnabledSettingPersistent() throws RemoteException;

    boolean isMultiPackage() throws RemoteException;

    boolean isRequestUpdateOwnership() throws RemoteException;

    boolean isStaged() throws RemoteException;

    ParcelFileDescriptor openRead(String str) throws RemoteException;

    ParcelFileDescriptor openWrite(String str, long j, long j2) throws RemoteException;

    ParcelFileDescriptor openWriteAppMetadata() throws RemoteException;

    void removeAppMetadata() throws RemoteException;

    void removeChildSessionId(int i) throws RemoteException;

    void removeFile(int i, String str) throws RemoteException;

    void removeSplit(String str) throws RemoteException;

    void requestChecksums(String str, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener) throws RemoteException;

    void requestUserPreapproval(PackageInstaller.PreapprovalDetails preapprovalDetails, IntentSender intentSender) throws RemoteException;

    void seal() throws RemoteException;

    void setChecksums(String str, Checksum[] checksumArr, byte[] bArr) throws RemoteException;

    void setClientProgress(float f) throws RemoteException;

    void setPreVerifiedDomains(DomainSet domainSet) throws RemoteException;

    void stageViaHardLink(String str) throws RemoteException;

    void transfer(String str) throws RemoteException;

    void write(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageInstallerSession {
        public static final String DESCRIPTOR = "android.content.pm.IPackageInstallerSession";
        static final int TRANSACTION_abandon = 14;
        static final int TRANSACTION_addChildSessionId = 22;
        static final int TRANSACTION_addClientProgress = 2;
        static final int TRANSACTION_addFile = 18;
        static final int TRANSACTION_close = 11;
        static final int TRANSACTION_commit = 12;
        static final int TRANSACTION_fetchPackageNames = 16;
        static final int TRANSACTION_getAppMetadataFd = 30;
        static final int TRANSACTION_getChildSessionIds = 21;
        static final int TRANSACTION_getDataLoaderParams = 17;
        static final int TRANSACTION_getInstallFlags = 26;
        static final int TRANSACTION_getNames = 3;
        static final int TRANSACTION_getParentSessionId = 24;
        static final int TRANSACTION_getPreVerifiedDomains = 34;
        static final int TRANSACTION_isApplicationEnabledSettingPersistent = 28;
        static final int TRANSACTION_isMultiPackage = 20;
        static final int TRANSACTION_isRequestUpdateOwnership = 29;
        static final int TRANSACTION_isStaged = 25;
        static final int TRANSACTION_openRead = 5;
        static final int TRANSACTION_openWrite = 4;
        static final int TRANSACTION_openWriteAppMetadata = 31;
        static final int TRANSACTION_removeAppMetadata = 32;
        static final int TRANSACTION_removeChildSessionId = 23;
        static final int TRANSACTION_removeFile = 19;
        static final int TRANSACTION_removeSplit = 10;
        static final int TRANSACTION_requestChecksums = 9;
        static final int TRANSACTION_requestUserPreapproval = 27;
        static final int TRANSACTION_seal = 15;
        static final int TRANSACTION_setChecksums = 8;
        static final int TRANSACTION_setClientProgress = 1;
        static final int TRANSACTION_setPreVerifiedDomains = 33;
        static final int TRANSACTION_stageViaHardLink = 7;
        static final int TRANSACTION_transfer = 13;
        static final int TRANSACTION_write = 6;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 33;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPackageInstallerSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPackageInstallerSession)) {
                return (IPackageInstallerSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setClientProgress";
                case 2:
                    return "addClientProgress";
                case 3:
                    return "getNames";
                case 4:
                    return "openWrite";
                case 5:
                    return "openRead";
                case 6:
                    return "write";
                case 7:
                    return "stageViaHardLink";
                case 8:
                    return "setChecksums";
                case 9:
                    return "requestChecksums";
                case 10:
                    return "removeSplit";
                case 11:
                    return "close";
                case 12:
                    return "commit";
                case 13:
                    return "transfer";
                case 14:
                    return "abandon";
                case 15:
                    return "seal";
                case 16:
                    return "fetchPackageNames";
                case 17:
                    return "getDataLoaderParams";
                case 18:
                    return "addFile";
                case 19:
                    return "removeFile";
                case 20:
                    return "isMultiPackage";
                case 21:
                    return "getChildSessionIds";
                case 22:
                    return "addChildSessionId";
                case 23:
                    return "removeChildSessionId";
                case 24:
                    return "getParentSessionId";
                case 25:
                    return "isStaged";
                case 26:
                    return "getInstallFlags";
                case 27:
                    return "requestUserPreapproval";
                case 28:
                    return "isApplicationEnabledSettingPersistent";
                case 29:
                    return "isRequestUpdateOwnership";
                case 30:
                    return "getAppMetadataFd";
                case 31:
                    return "openWriteAppMetadata";
                case 32:
                    return "removeAppMetadata";
                case 33:
                    return "setPreVerifiedDomains";
                case 34:
                    return "getPreVerifiedDomains";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setClientProgress(f);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    addClientProgress(f2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String[] names = getNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(names);
                    return true;
                case 4:
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenWrite = openWrite(string, j, j2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenWrite, 1);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenRead = openRead(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenRead, 1);
                    return true;
                case 6:
                    String string3 = parcel.readString();
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    write(string3, j3, j4, parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stageViaHardLink(string4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string5 = parcel.readString();
                    Checksum[] checksumArr = (Checksum[]) parcel.createTypedArray(Checksum.CREATOR);
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setChecksums(string5, checksumArr, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string6 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    IOnChecksumsReadyListener iOnChecksumsReadyListenerAsInterface = IOnChecksumsReadyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestChecksums(string6, i3, i4, arrayList, iOnChecksumsReadyListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSplit(string7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    commit(intentSender, z);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transfer(string8);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    abandon();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    seal();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    List<String> listFetchPackageNames = fetchPackageNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listFetchPackageNames);
                    return true;
                case 17:
                    DataLoaderParamsParcel dataLoaderParams = getDataLoaderParams();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataLoaderParams, 1);
                    return true;
                case 18:
                    int i5 = parcel.readInt();
                    String string9 = parcel.readString();
                    long j5 = parcel.readLong();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    addFile(i5, string9, j5, bArrCreateByteArray2, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i6 = parcel.readInt();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeFile(i6, string10);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean zIsMultiPackage = isMultiPackage();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMultiPackage);
                    return true;
                case 21:
                    int[] childSessionIds = getChildSessionIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(childSessionIds);
                    return true;
                case 22:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addChildSessionId(i7);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeChildSessionId(i8);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int parentSessionId = getParentSessionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(parentSessionId);
                    return true;
                case 25:
                    boolean zIsStaged = isStaged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStaged);
                    return true;
                case 26:
                    int installFlags = getInstallFlags();
                    parcel2.writeNoException();
                    parcel2.writeInt(installFlags);
                    return true;
                case 27:
                    PackageInstaller.PreapprovalDetails preapprovalDetails = (PackageInstaller.PreapprovalDetails) parcel.readTypedObject(PackageInstaller.PreapprovalDetails.CREATOR);
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUserPreapproval(preapprovalDetails, intentSender2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean zIsApplicationEnabledSettingPersistent = isApplicationEnabledSettingPersistent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationEnabledSettingPersistent);
                    return true;
                case 29:
                    boolean zIsRequestUpdateOwnership = isRequestUpdateOwnership();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRequestUpdateOwnership);
                    return true;
                case 30:
                    ParcelFileDescriptor appMetadataFd = getAppMetadataFd();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMetadataFd, 1);
                    return true;
                case 31:
                    ParcelFileDescriptor parcelFileDescriptorOpenWriteAppMetadata = openWriteAppMetadata();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenWriteAppMetadata, 1);
                    return true;
                case 32:
                    removeAppMetadata();
                    parcel2.writeNoException();
                    return true;
                case 33:
                    DomainSet domainSet = (DomainSet) parcel.readTypedObject(DomainSet.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreVerifiedDomains(domainSet);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    DomainSet preVerifiedDomains = getPreVerifiedDomains();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preVerifiedDomains, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPackageInstallerSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void setClientProgress(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void addClientProgress(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public String[] getNames() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public ParcelFileDescriptor openWrite(String str, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public ParcelFileDescriptor openRead(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void write(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void stageViaHardLink(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void setChecksums(String str, Checksum[] checksumArr, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(checksumArr, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void requestChecksums(String str, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeList(list);
                    parcelObtain.writeStrongInterface(iOnChecksumsReadyListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeSplit(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void commit(IntentSender intentSender, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void transfer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void abandon() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void seal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public List<String> fetchPackageNames() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public DataLoaderParamsParcel getDataLoaderParams() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DataLoaderParamsParcel) parcelObtain2.readTypedObject(DataLoaderParamsParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void addFile(int i, String str, long j, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeFile(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isMultiPackage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public int[] getChildSessionIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void addChildSessionId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeChildSessionId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public int getParentSessionId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isStaged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public int getInstallFlags() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void requestUserPreapproval(PackageInstaller.PreapprovalDetails preapprovalDetails, IntentSender intentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(preapprovalDetails, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isApplicationEnabledSettingPersistent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public boolean isRequestUpdateOwnership() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public ParcelFileDescriptor getAppMetadataFd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public ParcelFileDescriptor openWriteAppMetadata() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void removeAppMetadata() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public void setPreVerifiedDomains(DomainSet domainSet) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(domainSet, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerSession
            public DomainSet getPreVerifiedDomains() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DomainSet) parcelObtain2.readTypedObject(DomainSet.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void getDataLoaderParams_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_INSTALLER_V2, getCallingPid(), getCallingUid());
        }

        protected void addFile_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_INSTALLER_V2, getCallingPid(), getCallingUid());
        }

        protected void removeFile_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_INSTALLER_V2, getCallingPid(), getCallingUid());
        }
    }
}
