package android.content.pm;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IPackageInstaller extends IInterface {

    public static class Default implements IPackageInstaller {
        @Override // android.content.pm.IPackageInstaller
        public void abandonSession(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public void bypassNextAllowedApexUpdateCheck(boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void bypassNextStagedInstallerCheck(boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void checkInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public int createSession(PackageInstaller.SessionParams sessionParams, String str, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstaller
        public void disableVerificationForUid(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public ParceledListSlice getAllSessions(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public ParceledListSlice getMySessions(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public PackageInstaller.SessionInfo getSessionInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public ParceledListSlice getStagedSessions() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public void installExistingPackage(String str, int i, int i2, IntentSender intentSender, int i3, List<String> list) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void installPackageArchived(ArchivedPackageParcel archivedPackageParcel, PackageInstaller.SessionParams sessionParams, IntentSender intentSender, String str, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public IPackageInstallerSession openSession(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public IPackageInstallerSession openSessionQuick(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void reportUnarchivalStatus(int i, int i2, long j, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void requestArchive(String str, String str2, int i, IntentSender intentSender, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public ParcelFileDescriptor requestCopy(String str, long j) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public void requestUnarchive(String str, String str2, IntentSender intentSender, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setAllowUnlimitedSilentUpdates(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setPermissionsResult(int i, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setSilentUpdatesThrottleTime(long j) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void setUnknownSourceConfirmResult(int i, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void uninstall(VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void uninstallExistingPackage(VersionedPackage versionedPackage, String str, IntentSender intentSender, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void updateSessionAppIcon(int i, Bitmap bitmap) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void updateSessionAppLabel(int i, String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstaller
        public void waitForInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, IntentSender intentSender, long j) throws RemoteException {
        }
    }

    void abandonSession(int i) throws RemoteException;

    void bypassNextAllowedApexUpdateCheck(boolean z) throws RemoteException;

    void bypassNextStagedInstallerCheck(boolean z) throws RemoteException;

    void checkInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, RemoteCallback remoteCallback) throws RemoteException;

    int createSession(PackageInstaller.SessionParams sessionParams, String str, String str2, int i) throws RemoteException;

    void disableVerificationForUid(int i) throws RemoteException;

    ParceledListSlice getAllSessions(int i) throws RemoteException;

    ParceledListSlice getMySessions(String str, int i) throws RemoteException;

    PackageInstaller.SessionInfo getSessionInfo(int i) throws RemoteException;

    ParceledListSlice getStagedSessions() throws RemoteException;

    void installExistingPackage(String str, int i, int i2, IntentSender intentSender, int i3, List<String> list) throws RemoteException;

    void installPackageArchived(ArchivedPackageParcel archivedPackageParcel, PackageInstaller.SessionParams sessionParams, IntentSender intentSender, String str, UserHandle userHandle) throws RemoteException;

    IPackageInstallerSession openSession(int i) throws RemoteException;

    IPackageInstallerSession openSessionQuick(int i, String str) throws RemoteException;

    void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int i) throws RemoteException;

    void reportUnarchivalStatus(int i, int i2, long j, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException;

    void requestArchive(String str, String str2, int i, IntentSender intentSender, UserHandle userHandle) throws RemoteException;

    ParcelFileDescriptor requestCopy(String str, long j) throws RemoteException;

    void requestUnarchive(String str, String str2, IntentSender intentSender, UserHandle userHandle) throws RemoteException;

    void setAllowUnlimitedSilentUpdates(String str) throws RemoteException;

    void setPermissionsResult(int i, boolean z) throws RemoteException;

    void setSilentUpdatesThrottleTime(long j) throws RemoteException;

    void setUnknownSourceConfirmResult(int i, boolean z) throws RemoteException;

    void uninstall(VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2) throws RemoteException;

    void uninstallExistingPackage(VersionedPackage versionedPackage, String str, IntentSender intentSender, int i) throws RemoteException;

    void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException;

    void updateSessionAppIcon(int i, Bitmap bitmap) throws RemoteException;

    void updateSessionAppLabel(int i, String str) throws RemoteException;

    void waitForInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, IntentSender intentSender, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageInstaller {
        public static final String DESCRIPTOR = "android.content.pm.IPackageInstaller";
        static final int TRANSACTION_abandonSession = 4;
        static final int TRANSACTION_bypassNextAllowedApexUpdateCheck = 17;
        static final int TRANSACTION_bypassNextStagedInstallerCheck = 16;
        static final int TRANSACTION_checkInstallConstraints = 21;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_disableVerificationForUid = 18;
        static final int TRANSACTION_getAllSessions = 7;
        static final int TRANSACTION_getMySessions = 8;
        static final int TRANSACTION_getSessionInfo = 6;
        static final int TRANSACTION_getStagedSessions = 9;
        static final int TRANSACTION_installExistingPackage = 14;
        static final int TRANSACTION_installPackageArchived = 25;
        static final int TRANSACTION_openSession = 5;
        static final int TRANSACTION_openSessionQuick = 29;
        static final int TRANSACTION_registerCallback = 10;
        static final int TRANSACTION_reportUnarchivalStatus = 26;
        static final int TRANSACTION_requestArchive = 23;
        static final int TRANSACTION_requestCopy = 28;
        static final int TRANSACTION_requestUnarchive = 24;
        static final int TRANSACTION_setAllowUnlimitedSilentUpdates = 19;
        static final int TRANSACTION_setPermissionsResult = 15;
        static final int TRANSACTION_setSilentUpdatesThrottleTime = 20;
        static final int TRANSACTION_setUnknownSourceConfirmResult = 27;
        static final int TRANSACTION_uninstall = 12;
        static final int TRANSACTION_uninstallExistingPackage = 13;
        static final int TRANSACTION_unregisterCallback = 11;
        static final int TRANSACTION_updateSessionAppIcon = 2;
        static final int TRANSACTION_updateSessionAppLabel = 3;
        static final int TRANSACTION_waitForInstallConstraints = 22;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
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

        public static IPackageInstaller asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPackageInstaller)) {
                return (IPackageInstaller) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "updateSessionAppIcon";
                case 3:
                    return "updateSessionAppLabel";
                case 4:
                    return "abandonSession";
                case 5:
                    return "openSession";
                case 6:
                    return "getSessionInfo";
                case 7:
                    return "getAllSessions";
                case 8:
                    return "getMySessions";
                case 9:
                    return "getStagedSessions";
                case 10:
                    return "registerCallback";
                case 11:
                    return "unregisterCallback";
                case 12:
                    return "uninstall";
                case 13:
                    return "uninstallExistingPackage";
                case 14:
                    return "installExistingPackage";
                case 15:
                    return "setPermissionsResult";
                case 16:
                    return "bypassNextStagedInstallerCheck";
                case 17:
                    return "bypassNextAllowedApexUpdateCheck";
                case 18:
                    return "disableVerificationForUid";
                case 19:
                    return "setAllowUnlimitedSilentUpdates";
                case 20:
                    return "setSilentUpdatesThrottleTime";
                case 21:
                    return "checkInstallConstraints";
                case 22:
                    return "waitForInstallConstraints";
                case 23:
                    return "requestArchive";
                case 24:
                    return "requestUnarchive";
                case 25:
                    return "installPackageArchived";
                case 26:
                    return "reportUnarchivalStatus";
                case 27:
                    return "setUnknownSourceConfirmResult";
                case 28:
                    return "requestCopy";
                case 29:
                    return "openSessionQuick";
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
                    PackageInstaller.SessionParams sessionParams = (PackageInstaller.SessionParams) parcel.readTypedObject(PackageInstaller.SessionParams.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createSession = createSession(sessionParams, readString, readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(createSession);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSessionAppIcon(readInt2, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateSessionAppLabel(readInt3, readString3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abandonSession(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IPackageInstallerSession openSession = openSession(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSession);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageInstaller.SessionInfo sessionInfo = getSessionInfo(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionInfo, 1);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allSessions = getAllSessions(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allSessions, 1);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice mySessions = getMySessions(readString4, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mySessions, 1);
                    return true;
                case 9:
                    ParceledListSlice stagedSessions = getStagedSessions();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stagedSessions, 1);
                    return true;
                case 10:
                    IPackageInstallerCallback asInterface = IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IPackageInstallerCallback asInterface2 = IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    VersionedPackage versionedPackage = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    String readString5 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uninstall(versionedPackage, readString5, readInt10, intentSender, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    VersionedPackage versionedPackage2 = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    String readString6 = parcel.readString();
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uninstallExistingPackage(versionedPackage2, readString6, intentSender2, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString7 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    IntentSender intentSender3 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int readInt15 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    installExistingPackage(readString7, readInt13, readInt14, intentSender3, readInt15, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPermissionsResult(readInt16, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bypassNextStagedInstallerCheck(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bypassNextAllowedApexUpdateCheck(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableVerificationForUid(readInt17);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowUnlimitedSilentUpdates(readString8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSilentUpdatesThrottleTime(readLong);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString9 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    PackageInstaller.InstallConstraints installConstraints = (PackageInstaller.InstallConstraints) parcel.readTypedObject(PackageInstaller.InstallConstraints.CREATOR);
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    checkInstallConstraints(readString9, createStringArrayList2, installConstraints, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String readString10 = parcel.readString();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    PackageInstaller.InstallConstraints installConstraints2 = (PackageInstaller.InstallConstraints) parcel.readTypedObject(PackageInstaller.InstallConstraints.CREATOR);
                    IntentSender intentSender4 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    waitForInstallConstraints(readString10, createStringArrayList3, installConstraints2, intentSender4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    IntentSender intentSender5 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestArchive(readString11, readString12, readInt18, intentSender5, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    IntentSender intentSender6 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUnarchive(readString13, readString14, intentSender6, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ArchivedPackageParcel archivedPackageParcel = (ArchivedPackageParcel) parcel.readTypedObject(ArchivedPackageParcel.CREATOR);
                    PackageInstaller.SessionParams sessionParams2 = (PackageInstaller.SessionParams) parcel.readTypedObject(PackageInstaller.SessionParams.CREATOR);
                    IntentSender intentSender7 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    String readString15 = parcel.readString();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    installPackageArchived(archivedPackageParcel, sessionParams2, intentSender7, readString15, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportUnarchivalStatus(readInt19, readInt20, readLong3, pendingIntent, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt21 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUnknownSourceConfirmResult(readInt21, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString16 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor requestCopy = requestCopy(readString16, readLong4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(requestCopy, 1);
                    return true;
                case 29:
                    int readInt22 = parcel.readInt();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IPackageInstallerSession openSessionQuick = openSessionQuick(readInt22, readString17);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSessionQuick);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPackageInstaller {
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

            @Override // android.content.pm.IPackageInstaller
            public int createSession(PackageInstaller.SessionParams sessionParams, String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(sessionParams, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppIcon(int i, Bitmap bitmap) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppLabel(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void abandonSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public IPackageInstallerSession openSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return IPackageInstallerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public PackageInstaller.SessionInfo getSessionInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackageInstaller.SessionInfo) obtain2.readTypedObject(PackageInstaller.SessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getAllSessions(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getMySessions(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getStagedSessions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageInstallerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageInstallerCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstall(VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstallExistingPackage(VersionedPackage versionedPackage, String str, IntentSender intentSender, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installExistingPackage(String str, int i, int i2, IntentSender intentSender, int i3, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i3);
                    obtain.writeStringList(list);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setPermissionsResult(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextStagedInstallerCheck(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextAllowedApexUpdateCheck(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void disableVerificationForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setAllowUnlimitedSilentUpdates(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setSilentUpdatesThrottleTime(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void checkInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(installConstraints, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void waitForInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, IntentSender intentSender, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(installConstraints, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestArchive(String str, String str2, int i, IntentSender intentSender, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestUnarchive(String str, String str2, IntentSender intentSender, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installPackageArchived(ArchivedPackageParcel archivedPackageParcel, PackageInstaller.SessionParams sessionParams, IntentSender intentSender, String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(archivedPackageParcel, 0);
                    obtain.writeTypedObject(sessionParams, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void reportUnarchivalStatus(int i, int i2, long j, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setUnknownSourceConfirmResult(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParcelFileDescriptor requestCopy(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public IPackageInstallerSession openSessionQuick(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return IPackageInstallerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setPermissionsResult_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }
    }
}
