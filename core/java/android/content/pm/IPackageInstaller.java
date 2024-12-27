package android.content.pm;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.IntentSender;
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

import java.util.List;

/* loaded from: classes.dex */
public interface IPackageInstaller extends IInterface {
    void abandonSession(int i) throws RemoteException;

    void bypassNextAllowedApexUpdateCheck(boolean z) throws RemoteException;

    void bypassNextStagedInstallerCheck(boolean z) throws RemoteException;

    void checkInstallConstraints(
            String str,
            List<String> list,
            PackageInstaller.InstallConstraints installConstraints,
            RemoteCallback remoteCallback)
            throws RemoteException;

    int createSession(PackageInstaller.SessionParams sessionParams, String str, String str2, int i)
            throws RemoteException;

    void disableVerificationForUid(int i) throws RemoteException;

    ParceledListSlice getAllSessions(int i) throws RemoteException;

    ParceledListSlice getMySessions(String str, int i) throws RemoteException;

    PackageInstaller.SessionInfo getSessionInfo(int i) throws RemoteException;

    ParceledListSlice getStagedSessions() throws RemoteException;

    void installExistingPackage(
            String str, int i, int i2, IntentSender intentSender, int i3, List<String> list)
            throws RemoteException;

    void installPackageArchived(
            ArchivedPackageParcel archivedPackageParcel,
            PackageInstaller.SessionParams sessionParams,
            IntentSender intentSender,
            String str,
            UserHandle userHandle)
            throws RemoteException;

    IPackageInstallerSession openSession(int i) throws RemoteException;

    IPackageInstallerSession openSessionQuick(int i, String str) throws RemoteException;

    void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int i)
            throws RemoteException;

    void reportUnarchivalStatus(
            int i, int i2, long j, PendingIntent pendingIntent, UserHandle userHandle)
            throws RemoteException;

    void requestArchive(
            String str, String str2, int i, IntentSender intentSender, UserHandle userHandle)
            throws RemoteException;

    ParcelFileDescriptor requestCopy(String str, long j) throws RemoteException;

    void requestUnarchive(String str, String str2, IntentSender intentSender, UserHandle userHandle)
            throws RemoteException;

    void setAllowUnlimitedSilentUpdates(String str) throws RemoteException;

    void setPermissionsResult(int i, boolean z) throws RemoteException;

    void setSilentUpdatesThrottleTime(long j) throws RemoteException;

    void setUnknownSourceConfirmResult(int i, boolean z) throws RemoteException;

    void uninstall(
            VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2)
            throws RemoteException;

    void uninstallExistingPackage(
            VersionedPackage versionedPackage, String str, IntentSender intentSender, int i)
            throws RemoteException;

    void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback)
            throws RemoteException;

    void updateSessionAppIcon(int i, Bitmap bitmap) throws RemoteException;

    void updateSessionAppLabel(int i, String str) throws RemoteException;

    void waitForInstallConstraints(
            String str,
            List<String> list,
            PackageInstaller.InstallConstraints installConstraints,
            IntentSender intentSender,
            long j)
            throws RemoteException;

    public static class Default implements IPackageInstaller {
        @Override // android.content.pm.IPackageInstaller
        public int createSession(
                PackageInstaller.SessionParams params,
                String installerPackageName,
                String installerAttributionTag,
                int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageInstaller
        public void updateSessionAppIcon(int sessionId, Bitmap appIcon) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void updateSessionAppLabel(int sessionId, String appLabel) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void abandonSession(int sessionId) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public IPackageInstallerSession openSession(int sessionId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public PackageInstaller.SessionInfo getSessionInfo(int sessionId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public ParceledListSlice getAllSessions(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public ParceledListSlice getMySessions(String installerPackageName, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public ParceledListSlice getStagedSessions() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public void registerCallback(IPackageInstallerCallback callback, int userId)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void unregisterCallback(IPackageInstallerCallback callback) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void uninstall(
                VersionedPackage versionedPackage,
                String callerPackageName,
                int flags,
                IntentSender statusReceiver,
                int userId)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void uninstallExistingPackage(
                VersionedPackage versionedPackage,
                String callerPackageName,
                IntentSender statusReceiver,
                int userId)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void installExistingPackage(
                String packageName,
                int installFlags,
                int installReason,
                IntentSender statusReceiver,
                int userId,
                List<String> whiteListedPermissions)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void setPermissionsResult(int sessionId, boolean accepted) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void bypassNextStagedInstallerCheck(boolean value) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void bypassNextAllowedApexUpdateCheck(boolean value) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void disableVerificationForUid(int uid) throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void setAllowUnlimitedSilentUpdates(String installerPackageName)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void setSilentUpdatesThrottleTime(long throttleTimeInSeconds)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void checkInstallConstraints(
                String installerPackageName,
                List<String> packageNames,
                PackageInstaller.InstallConstraints constraints,
                RemoteCallback callback)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void waitForInstallConstraints(
                String installerPackageName,
                List<String> packageNames,
                PackageInstaller.InstallConstraints constraints,
                IntentSender callback,
                long timeout)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void requestArchive(
                String packageName,
                String callerPackageName,
                int flags,
                IntentSender statusReceiver,
                UserHandle userHandle)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void requestUnarchive(
                String packageName,
                String callerPackageName,
                IntentSender statusReceiver,
                UserHandle userHandle)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void installPackageArchived(
                ArchivedPackageParcel archivedPackageParcel,
                PackageInstaller.SessionParams params,
                IntentSender statusReceiver,
                String installerPackageName,
                UserHandle userHandle)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void reportUnarchivalStatus(
                int unarchiveId,
                int status,
                long requiredStorageBytes,
                PendingIntent userActionIntent,
                UserHandle userHandle)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public void setUnknownSourceConfirmResult(int sessionId, boolean accepted)
                throws RemoteException {}

        @Override // android.content.pm.IPackageInstaller
        public ParcelFileDescriptor requestCopy(String name, long lengthBytes)
                throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageInstaller
        public IPackageInstallerSession openSessionQuick(int sessionId, String tempStagingDirPath)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPackageInstaller {
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

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPackageInstaller asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageInstaller)) {
                return (IPackageInstaller) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PackageInstaller.SessionParams _arg0 =
                            (PackageInstaller.SessionParams)
                                    data.readTypedObject(PackageInstaller.SessionParams.CREATOR);
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = createSession(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    Bitmap _arg12 = (Bitmap) data.readTypedObject(Bitmap.CREATOR);
                    data.enforceNoDataAvail();
                    updateSessionAppIcon(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    updateSessionAppLabel(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    abandonSession(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    IPackageInstallerSession _result2 = openSession(_arg05);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    PackageInstaller.SessionInfo _result3 = getSessionInfo(_arg06);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result4 = getAllSessions(_arg07);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result5 = getMySessions(_arg08, _arg14);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 9:
                    ParceledListSlice _result6 = getStagedSessions();
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 10:
                    IPackageInstallerCallback _arg09 =
                            IPackageInstallerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    registerCallback(_arg09, _arg15);
                    reply.writeNoException();
                    return true;
                case 11:
                    IPackageInstallerCallback _arg010 =
                            IPackageInstallerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg010);
                    reply.writeNoException();
                    return true;
                case 12:
                    VersionedPackage _arg011 =
                            (VersionedPackage) data.readTypedObject(VersionedPackage.CREATOR);
                    String _arg16 = data.readString();
                    int _arg22 = data.readInt();
                    IntentSender _arg32 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    uninstall(_arg011, _arg16, _arg22, _arg32, _arg4);
                    reply.writeNoException();
                    return true;
                case 13:
                    VersionedPackage _arg012 =
                            (VersionedPackage) data.readTypedObject(VersionedPackage.CREATOR);
                    String _arg17 = data.readString();
                    IntentSender _arg23 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    uninstallExistingPackage(_arg012, _arg17, _arg23, _arg33);
                    reply.writeNoException();
                    return true;
                case 14:
                    String _arg013 = data.readString();
                    int _arg18 = data.readInt();
                    int _arg24 = data.readInt();
                    IntentSender _arg34 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    int _arg42 = data.readInt();
                    List<String> _arg5 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    installExistingPackage(_arg013, _arg18, _arg24, _arg34, _arg42, _arg5);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg014 = data.readInt();
                    boolean _arg19 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setPermissionsResult(_arg014, _arg19);
                    reply.writeNoException();
                    return true;
                case 16:
                    boolean _arg015 = data.readBoolean();
                    data.enforceNoDataAvail();
                    bypassNextStagedInstallerCheck(_arg015);
                    reply.writeNoException();
                    return true;
                case 17:
                    boolean _arg016 = data.readBoolean();
                    data.enforceNoDataAvail();
                    bypassNextAllowedApexUpdateCheck(_arg016);
                    reply.writeNoException();
                    return true;
                case 18:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    disableVerificationForUid(_arg017);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg018 = data.readString();
                    data.enforceNoDataAvail();
                    setAllowUnlimitedSilentUpdates(_arg018);
                    reply.writeNoException();
                    return true;
                case 20:
                    long _arg019 = data.readLong();
                    data.enforceNoDataAvail();
                    setSilentUpdatesThrottleTime(_arg019);
                    reply.writeNoException();
                    return true;
                case 21:
                    String _arg020 = data.readString();
                    List<String> _arg110 = data.createStringArrayList();
                    PackageInstaller.InstallConstraints _arg25 =
                            (PackageInstaller.InstallConstraints)
                                    data.readTypedObject(
                                            PackageInstaller.InstallConstraints.CREATOR);
                    RemoteCallback _arg35 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    checkInstallConstraints(_arg020, _arg110, _arg25, _arg35);
                    reply.writeNoException();
                    return true;
                case 22:
                    String _arg021 = data.readString();
                    List<String> _arg111 = data.createStringArrayList();
                    PackageInstaller.InstallConstraints _arg26 =
                            (PackageInstaller.InstallConstraints)
                                    data.readTypedObject(
                                            PackageInstaller.InstallConstraints.CREATOR);
                    IntentSender _arg36 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    long _arg43 = data.readLong();
                    data.enforceNoDataAvail();
                    waitForInstallConstraints(_arg021, _arg111, _arg26, _arg36, _arg43);
                    reply.writeNoException();
                    return true;
                case 23:
                    String _arg022 = data.readString();
                    String _arg112 = data.readString();
                    int _arg27 = data.readInt();
                    IntentSender _arg37 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    UserHandle _arg44 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    requestArchive(_arg022, _arg112, _arg27, _arg37, _arg44);
                    reply.writeNoException();
                    return true;
                case 24:
                    String _arg023 = data.readString();
                    String _arg113 = data.readString();
                    IntentSender _arg28 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    UserHandle _arg38 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    requestUnarchive(_arg023, _arg113, _arg28, _arg38);
                    reply.writeNoException();
                    return true;
                case 25:
                    ArchivedPackageParcel _arg024 =
                            (ArchivedPackageParcel)
                                    data.readTypedObject(ArchivedPackageParcel.CREATOR);
                    PackageInstaller.SessionParams _arg114 =
                            (PackageInstaller.SessionParams)
                                    data.readTypedObject(PackageInstaller.SessionParams.CREATOR);
                    IntentSender _arg29 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    String _arg39 = data.readString();
                    UserHandle _arg45 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    installPackageArchived(_arg024, _arg114, _arg29, _arg39, _arg45);
                    reply.writeNoException();
                    return true;
                case 26:
                    int _arg025 = data.readInt();
                    int _arg115 = data.readInt();
                    long _arg210 = data.readLong();
                    PendingIntent _arg310 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    UserHandle _arg46 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    reportUnarchivalStatus(_arg025, _arg115, _arg210, _arg310, _arg46);
                    reply.writeNoException();
                    return true;
                case 27:
                    int _arg026 = data.readInt();
                    boolean _arg116 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUnknownSourceConfirmResult(_arg026, _arg116);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg027 = data.readString();
                    long _arg117 = data.readLong();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result7 = requestCopy(_arg027, _arg117);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 29:
                    int _arg028 = data.readInt();
                    String _arg118 = data.readString();
                    data.enforceNoDataAvail();
                    IPackageInstallerSession _result8 = openSessionQuick(_arg028, _arg118);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPackageInstaller {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstaller
            public int createSession(
                    PackageInstaller.SessionParams params,
                    String installerPackageName,
                    String installerAttributionTag,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(params, 0);
                    _data.writeString(installerPackageName);
                    _data.writeString(installerAttributionTag);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppIcon(int sessionId, Bitmap appIcon) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(appIcon, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void updateSessionAppLabel(int sessionId, String appLabel)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeString(appLabel);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void abandonSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public IPackageInstallerSession openSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    IPackageInstallerSession _result =
                            IPackageInstallerSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public PackageInstaller.SessionInfo getSessionInfo(int sessionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    PackageInstaller.SessionInfo _result =
                            (PackageInstaller.SessionInfo)
                                    _reply.readTypedObject(PackageInstaller.SessionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getAllSessions(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getMySessions(String installerPackageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(installerPackageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParceledListSlice getStagedSessions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void registerCallback(IPackageInstallerCallback callback, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void unregisterCallback(IPackageInstallerCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstall(
                    VersionedPackage versionedPackage,
                    String callerPackageName,
                    int flags,
                    IntentSender statusReceiver,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(versionedPackage, 0);
                    _data.writeString(callerPackageName);
                    _data.writeInt(flags);
                    _data.writeTypedObject(statusReceiver, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void uninstallExistingPackage(
                    VersionedPackage versionedPackage,
                    String callerPackageName,
                    IntentSender statusReceiver,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(versionedPackage, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(statusReceiver, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installExistingPackage(
                    String packageName,
                    int installFlags,
                    int installReason,
                    IntentSender statusReceiver,
                    int userId,
                    List<String> whiteListedPermissions)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(installFlags);
                    _data.writeInt(installReason);
                    _data.writeTypedObject(statusReceiver, 0);
                    _data.writeInt(userId);
                    _data.writeStringList(whiteListedPermissions);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setPermissionsResult(int sessionId, boolean accepted)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeBoolean(accepted);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextStagedInstallerCheck(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void bypassNextAllowedApexUpdateCheck(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void disableVerificationForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setAllowUnlimitedSilentUpdates(String installerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(installerPackageName);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setSilentUpdatesThrottleTime(long throttleTimeInSeconds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(throttleTimeInSeconds);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void checkInstallConstraints(
                    String installerPackageName,
                    List<String> packageNames,
                    PackageInstaller.InstallConstraints constraints,
                    RemoteCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(installerPackageName);
                    _data.writeStringList(packageNames);
                    _data.writeTypedObject(constraints, 0);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void waitForInstallConstraints(
                    String installerPackageName,
                    List<String> packageNames,
                    PackageInstaller.InstallConstraints constraints,
                    IntentSender callback,
                    long timeout)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(installerPackageName);
                    _data.writeStringList(packageNames);
                    _data.writeTypedObject(constraints, 0);
                    _data.writeTypedObject(callback, 0);
                    _data.writeLong(timeout);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestArchive(
                    String packageName,
                    String callerPackageName,
                    int flags,
                    IntentSender statusReceiver,
                    UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callerPackageName);
                    _data.writeInt(flags);
                    _data.writeTypedObject(statusReceiver, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void requestUnarchive(
                    String packageName,
                    String callerPackageName,
                    IntentSender statusReceiver,
                    UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(statusReceiver, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void installPackageArchived(
                    ArchivedPackageParcel archivedPackageParcel,
                    PackageInstaller.SessionParams params,
                    IntentSender statusReceiver,
                    String installerPackageName,
                    UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(archivedPackageParcel, 0);
                    _data.writeTypedObject(params, 0);
                    _data.writeTypedObject(statusReceiver, 0);
                    _data.writeString(installerPackageName);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void reportUnarchivalStatus(
                    int unarchiveId,
                    int status,
                    long requiredStorageBytes,
                    PendingIntent userActionIntent,
                    UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(unarchiveId);
                    _data.writeInt(status);
                    _data.writeLong(requiredStorageBytes);
                    _data.writeTypedObject(userActionIntent, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public void setUnknownSourceConfirmResult(int sessionId, boolean accepted)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeBoolean(accepted);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public ParcelFileDescriptor requestCopy(String name, long lengthBytes)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeLong(lengthBytes);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstaller
            public IPackageInstallerSession openSessionQuick(
                    int sessionId, String tempStagingDirPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeString(tempStagingDirPath);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    IPackageInstallerSession _result =
                            IPackageInstallerSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setPermissionsResult_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
