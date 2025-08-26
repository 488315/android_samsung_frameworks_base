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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPackageInstaller)) {
                return (IPackageInstaller) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCreateSession = createSession(sessionParams, string, string2, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateSession);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSessionAppIcon(i4, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateSessionAppLabel(i5, string3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abandonSession(i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IPackageInstallerSession iPackageInstallerSessionOpenSession = openSession(i7);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iPackageInstallerSessionOpenSession);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageInstaller.SessionInfo sessionInfo = getSessionInfo(i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionInfo, 1);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allSessions = getAllSessions(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allSessions, 1);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice mySessions = getMySessions(string4, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mySessions, 1);
                    return true;
                case 9:
                    ParceledListSlice stagedSessions = getStagedSessions();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stagedSessions, 1);
                    return true;
                case 10:
                    IPackageInstallerCallback iPackageInstallerCallbackAsInterface = IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(iPackageInstallerCallbackAsInterface, i11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IPackageInstallerCallback iPackageInstallerCallbackAsInterface2 = IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iPackageInstallerCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    VersionedPackage versionedPackage = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    String string5 = parcel.readString();
                    int i12 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uninstall(versionedPackage, string5, i12, intentSender, i13);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    VersionedPackage versionedPackage2 = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    String string6 = parcel.readString();
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uninstallExistingPackage(versionedPackage2, string6, intentSender2, i14);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string7 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    IntentSender intentSender3 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int i17 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    installExistingPackage(string7, i15, i16, intentSender3, i17, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i18 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPermissionsResult(i18, z);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bypassNextStagedInstallerCheck(z2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bypassNextAllowedApexUpdateCheck(z3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableVerificationForUid(i19);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowUnlimitedSilentUpdates(string8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSilentUpdatesThrottleTime(j);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string9 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    PackageInstaller.InstallConstraints installConstraints = (PackageInstaller.InstallConstraints) parcel.readTypedObject(PackageInstaller.InstallConstraints.CREATOR);
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    checkInstallConstraints(string9, arrayListCreateStringArrayList2, installConstraints, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String string10 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    PackageInstaller.InstallConstraints installConstraints2 = (PackageInstaller.InstallConstraints) parcel.readTypedObject(PackageInstaller.InstallConstraints.CREATOR);
                    IntentSender intentSender4 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    waitForInstallConstraints(string10, arrayListCreateStringArrayList3, installConstraints2, intentSender4, j2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    int i20 = parcel.readInt();
                    IntentSender intentSender5 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestArchive(string11, string12, i20, intentSender5, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    IntentSender intentSender6 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUnarchive(string13, string14, intentSender6, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ArchivedPackageParcel archivedPackageParcel = (ArchivedPackageParcel) parcel.readTypedObject(ArchivedPackageParcel.CREATOR);
                    PackageInstaller.SessionParams sessionParams2 = (PackageInstaller.SessionParams) parcel.readTypedObject(PackageInstaller.SessionParams.CREATOR);
                    IntentSender intentSender7 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    String string15 = parcel.readString();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    installPackageArchived(archivedPackageParcel, sessionParams2, intentSender7, string15, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    long j3 = parcel.readLong();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportUnarchivalStatus(i21, i22, j3, pendingIntent, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i23 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUnknownSourceConfirmResult(i23, z4);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string16 = parcel.readString();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorRequestCopy = requestCopy(string16, j4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorRequestCopy, 1);
                    return true;
                case 29:
                    int i24 = parcel.readInt();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IPackageInstallerSession iPackageInstallerSessionOpenSessionQuick = openSessionQuick(i24, string17);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iPackageInstallerSessionOpenSessionQuick);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sessionParams, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppIcon(int i, Bitmap bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppLabel(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void abandonSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public IPackageInstallerSession openSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IPackageInstallerSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public PackageInstaller.SessionInfo getSessionInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackageInstaller.SessionInfo) parcelObtain2.readTypedObject(PackageInstaller.SessionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getAllSessions(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getMySessions(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getStagedSessions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPackageInstallerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPackageInstallerCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstall(VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(versionedPackage, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstallExistingPackage(VersionedPackage versionedPackage, String str, IntentSender intentSender, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(versionedPackage, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installExistingPackage(String str, int i, int i2, IntentSender intentSender, int i3, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setPermissionsResult(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextStagedInstallerCheck(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextAllowedApexUpdateCheck(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void disableVerificationForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setAllowUnlimitedSilentUpdates(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setSilentUpdatesThrottleTime(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void checkInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedObject(installConstraints, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void waitForInstallConstraints(String str, List<String> list, PackageInstaller.InstallConstraints installConstraints, IntentSender intentSender, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedObject(installConstraints, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestArchive(String str, String str2, int i, IntentSender intentSender, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestUnarchive(String str, String str2, IntentSender intentSender, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installPackageArchived(ArchivedPackageParcel archivedPackageParcel, PackageInstaller.SessionParams sessionParams, IntentSender intentSender, String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(archivedPackageParcel, 0);
                    parcelObtain.writeTypedObject(sessionParams, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void reportUnarchivalStatus(int i, int i2, long j, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setUnknownSourceConfirmResult(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParcelFileDescriptor requestCopy(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public IPackageInstallerSession openSessionQuick(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IPackageInstallerSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setPermissionsResult_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }
    }
}
