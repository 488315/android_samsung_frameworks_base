package android.content.pm;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.pm.verify.domain.DomainSet;
import android.graphics.Bitmap;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileBridge;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;

import com.android.internal.content.InstallLocationUtils;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

import com.samsung.android.share.SemShareConstants;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PackageInstaller {

    @SystemApi
    public static final String ACTION_CONFIRM_INSTALL = "android.content.pm.action.CONFIRM_INSTALL";

    @SystemApi
    public static final String ACTION_CONFIRM_PRE_APPROVAL =
            "android.content.pm.action.CONFIRM_PRE_APPROVAL";

    public static final String ACTION_SESSION_COMMITTED =
            "android.content.pm.action.SESSION_COMMITTED";
    public static final String ACTION_SESSION_DETAILS = "android.content.pm.action.SESSION_DETAILS";
    public static final String ACTION_SESSION_UPDATED = "android.content.pm.action.SESSION_UPDATED";
    private static final String ACTION_WAIT_INSTALL_CONSTRAINTS =
            "android.content.pm.action.WAIT_INSTALL_CONSTRAINTS";

    @SystemApi public static final int DATA_LOADER_TYPE_INCREMENTAL = 2;

    @SystemApi public static final int DATA_LOADER_TYPE_NONE = 0;

    @SystemApi public static final int DATA_LOADER_TYPE_STREAMING = 1;
    private static final int DEFAULT_CHECKSUMS = 127;
    public static final boolean ENABLE_REVOCABLE_FD =
            SystemProperties.getBoolean("fw.revocable_fd", false);

    @SystemApi public static final String EXTRA_CALLBACK = "android.content.pm.extra.CALLBACK";

    @SystemApi
    public static final String EXTRA_DATA_LOADER_TYPE = "android.content.pm.extra.DATA_LOADER_TYPE";

    @SystemApi
    public static final String EXTRA_DELETE_FLAGS = "android.content.pm.extra.DELETE_FLAGS";

    public static final String EXTRA_INSTALL_CONSTRAINTS =
            "android.content.pm.extra.INSTALL_CONSTRAINTS";
    public static final String EXTRA_INSTALL_CONSTRAINTS_RESULT =
            "android.content.pm.extra.INSTALL_CONSTRAINTS_RESULT";
    public static final String EXTRA_LEGACY_BUNDLE = "android.content.pm.extra.LEGACY_BUNDLE";

    @SystemApi
    public static final String EXTRA_LEGACY_STATUS = "android.content.pm.extra.LEGACY_STATUS";

    public static final String EXTRA_OTHER_PACKAGE_NAME =
            "android.content.pm.extra.OTHER_PACKAGE_NAME";
    public static final String EXTRA_PACKAGE_NAME = "android.content.pm.extra.PACKAGE_NAME";

    @Deprecated
    public static final String EXTRA_PACKAGE_NAMES = "android.content.pm.extra.PACKAGE_NAMES";

    public static final String EXTRA_PRE_APPROVAL = "android.content.pm.extra.PRE_APPROVAL";

    @SystemApi @Deprecated
    public static final String EXTRA_RESOLVED_BASE_PATH =
            "android.content.pm.extra.RESOLVED_BASE_PATH";

    public static final String EXTRA_SESSION = "android.content.pm.extra.SESSION";
    public static final String EXTRA_SESSION_ID = "android.content.pm.extra.SESSION_ID";
    public static final String EXTRA_STATUS = "android.content.pm.extra.STATUS";
    public static final String EXTRA_STATUS_MESSAGE = "android.content.pm.extra.STATUS_MESSAGE";
    public static final String EXTRA_STORAGE_PATH = "android.content.pm.extra.STORAGE_PATH";
    public static final String EXTRA_UNARCHIVE_ALL_USERS =
            "android.content.pm.extra.UNARCHIVE_ALL_USERS";
    public static final String EXTRA_UNARCHIVE_ID = "android.content.pm.extra.UNARCHIVE_ID";
    public static final String EXTRA_UNARCHIVE_PACKAGE_NAME =
            "android.content.pm.extra.UNARCHIVE_PACKAGE_NAME";
    public static final String EXTRA_UNARCHIVE_STATUS = "android.content.pm.extra.UNARCHIVE_STATUS";
    public static final String EXTRA_WARNINGS = "android.content.pm.extra.WARNINGS";

    @SystemApi public static final int LOCATION_DATA_APP = 0;

    @SystemApi public static final int LOCATION_MEDIA_DATA = 2;

    @SystemApi public static final int LOCATION_MEDIA_OBB = 1;
    public static final int PACKAGE_SOURCE_DOWNLOADED_FILE = 4;
    public static final int PACKAGE_SOURCE_LOCAL_FILE = 3;
    public static final int PACKAGE_SOURCE_OTHER = 1;
    public static final int PACKAGE_SOURCE_STORE = 2;
    public static final int PACKAGE_SOURCE_UNSPECIFIED = 0;

    @SystemApi public static final int REASON_CONFIRM_PACKAGE_CHANGE = 0;

    @SystemApi public static final int REASON_OWNERSHIP_CHANGED = 1;

    @SystemApi public static final int REASON_REMIND_OWNERSHIP = 2;
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_FAILURE_ABORTED = 3;
    public static final int STATUS_FAILURE_BLOCKED = 2;
    public static final int STATUS_FAILURE_CONFLICT = 5;
    public static final int STATUS_FAILURE_INCOMPATIBLE = 7;
    public static final int STATUS_FAILURE_INVALID = 4;
    public static final int STATUS_FAILURE_STORAGE = 6;
    public static final int STATUS_FAILURE_TIMEOUT = 8;
    public static final int STATUS_PENDING_STREAMING = -2;
    public static final int STATUS_PENDING_USER_ACTION = -1;
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "PackageInstaller";
    public static final int UNARCHIVAL_ERROR_INSTALLER_DISABLED = 4;
    public static final int UNARCHIVAL_ERROR_INSTALLER_UNINSTALLED = 5;
    public static final int UNARCHIVAL_ERROR_INSUFFICIENT_STORAGE = 2;
    public static final int UNARCHIVAL_ERROR_NO_CONNECTIVITY = 3;
    public static final int UNARCHIVAL_ERROR_USER_ACTION_NEEDED = 1;
    public static final int UNARCHIVAL_GENERIC_ERROR = 100;
    public static final int UNARCHIVAL_OK = 0;
    public static final int UNARCHIVAL_STATUS_UNSET = -1;
    private final String mAttributionTag;
    private final ArrayList<SessionCallbackDelegate> mDelegates = new ArrayList<>();
    private final IPackageInstaller mInstaller;
    private final String mInstallerPackageName;
    private final int mUserId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FileLocation {}

    @Retention(RetentionPolicy.SOURCE)
    @interface PackageSourceType {}

    public abstract static class SessionCallback {
        public abstract void onActiveChanged(int i, boolean z);

        public abstract void onBadgingChanged(int i);

        public abstract void onCreated(int i);

        public abstract void onFinished(int i, boolean z);

        public abstract void onProgressChanged(int i, float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UnarchivalStatus {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserActionReason {}

    public PackageInstaller(
            IPackageInstaller installer,
            String installerPackageName,
            String installerAttributionTag,
            int userId) {
        Objects.requireNonNull(installer, "installer cannot be null");
        this.mInstaller = installer;
        this.mInstallerPackageName = installerPackageName;
        this.mAttributionTag = installerAttributionTag;
        this.mUserId = userId;
    }

    public int createSession(SessionParams params) throws IOException {
        try {
            return this.mInstaller.createSession(
                    params, this.mInstallerPackageName, this.mAttributionTag, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public Session openSession(int sessionId) throws IOException {
        try {
            try {
                return new Session(this.mInstaller.openSession(sessionId));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public ParcelFileDescriptor requestCopy(String name, long lengthBytes) throws IOException {
        try {
            return this.mInstaller.requestCopy(name, lengthBytes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public Session openSessionQuick(int sessionId, String tempStagingDirPath) throws IOException {
        try {
            try {
                return new Session(this.mInstaller.openSessionQuick(sessionId, tempStagingDirPath));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public void updateSessionAppIcon(int sessionId, Bitmap appIcon) {
        try {
            this.mInstaller.updateSessionAppIcon(sessionId, appIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateSessionAppLabel(int sessionId, CharSequence appLabel) {
        String val;
        if (appLabel == null) {
            val = null;
        } else {
            try {
                val = appLabel.toString();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mInstaller.updateSessionAppLabel(sessionId, val);
    }

    public void abandonSession(int sessionId) {
        try {
            this.mInstaller.abandonSession(sessionId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SessionInfo getSessionInfo(int sessionId) {
        try {
            return this.mInstaller.getSessionInfo(sessionId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SessionInfo> getAllSessions() {
        try {
            return this.mInstaller.getAllSessions(this.mUserId).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SessionInfo> getMySessions() {
        try {
            return this.mInstaller
                    .getMySessions(this.mInstallerPackageName, this.mUserId)
                    .getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SessionInfo> getStagedSessions() {
        try {
            return this.mInstaller.getStagedSessions().getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public SessionInfo getActiveStagedSession() {
        List<SessionInfo> activeSessions = getActiveStagedSessions();
        if (activeSessions.isEmpty()) {
            return null;
        }
        return activeSessions.get(0);
    }

    public List<SessionInfo> getActiveStagedSessions() {
        List<SessionInfo> activeStagedSessions = new ArrayList<>();
        List<SessionInfo> stagedSessions = getStagedSessions();
        for (int i = 0; i < stagedSessions.size(); i++) {
            SessionInfo sessionInfo = stagedSessions.get(i);
            if (sessionInfo.isStagedSessionActive()) {
                activeStagedSessions.add(sessionInfo);
            }
        }
        return activeStagedSessions;
    }

    public void setUnknownSourceConfirmResult(int sessionId, boolean accepted) {
        try {
            this.mInstaller.setUnknownSourceConfirmResult(sessionId, accepted);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstall(String packageName, IntentSender statusReceiver) {
        uninstall(packageName, 0, statusReceiver);
    }

    public void uninstall(String packageName, int flags, IntentSender statusReceiver) {
        uninstall(new VersionedPackage(packageName, -1), flags, statusReceiver);
    }

    public void uninstall(VersionedPackage versionedPackage, IntentSender statusReceiver) {
        uninstall(versionedPackage, 0, statusReceiver);
    }

    public void uninstall(
            VersionedPackage versionedPackage, int flags, IntentSender statusReceiver) {
        Objects.requireNonNull(versionedPackage, "versionedPackage cannot be null");
        try {
            this.mInstaller.uninstall(
                    versionedPackage,
                    this.mInstallerPackageName,
                    flags,
                    statusReceiver,
                    this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installExistingPackage(
            String packageName, int installReason, IntentSender statusReceiver) {
        Objects.requireNonNull(packageName, "packageName cannot be null");
        try {
            this.mInstaller.installExistingPackage(
                    packageName, 4194304, installReason, statusReceiver, this.mUserId, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstallExistingPackage(String packageName, IntentSender statusReceiver) {
        Objects.requireNonNull(packageName, "packageName cannot be null");
        try {
            this.mInstaller.uninstallExistingPackage(
                    new VersionedPackage(packageName, -1),
                    this.mInstallerPackageName,
                    statusReceiver,
                    this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installPackageArchived(
            ArchivedPackageInfo archivedPackageInfo,
            SessionParams sessionParams,
            IntentSender statusReceiver) {
        Objects.requireNonNull(archivedPackageInfo, "archivedPackageInfo cannot be null");
        Objects.requireNonNull(sessionParams, "sessionParams cannot be null");
        Objects.requireNonNull(statusReceiver, "statusReceiver cannot be null");
        try {
            this.mInstaller.installPackageArchived(
                    archivedPackageInfo.getParcel(),
                    sessionParams,
                    statusReceiver,
                    this.mInstallerPackageName,
                    new UserHandle(this.mUserId));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setPermissionsResult(int sessionId, boolean accepted) {
        try {
            this.mInstaller.setPermissionsResult(sessionId, accepted);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void checkInstallConstraints(
            List<String> packageNames,
            InstallConstraints constraints,
            final Executor executor,
            final Consumer<InstallConstraintsResult> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            RemoteCallback remoteCallback =
                    new RemoteCallback(
                            new RemoteCallback.OnResultListener() { // from class:
                                // android.content.pm.PackageInstaller$$ExternalSyntheticLambda1
                                @Override // android.os.RemoteCallback.OnResultListener
                                public final void onResult(Bundle bundle) {
                                    executor.execute(
                                            new Runnable() { // from class:
                                                // android.content.pm.PackageInstaller$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    r1.accept(
                                                            (PackageInstaller
                                                                            .InstallConstraintsResult)
                                                                    bundle.getParcelable(
                                                                            "result",
                                                                            PackageInstaller
                                                                                    .InstallConstraintsResult
                                                                                    .class));
                                                }
                                            });
                                }
                            });
            this.mInstaller.checkInstallConstraints(
                    this.mInstallerPackageName, packageNames, constraints, remoteCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForInstallConstraints(
            List<String> packageNames,
            InstallConstraints constraints,
            IntentSender callback,
            long timeoutMillis) {
        try {
            this.mInstaller.waitForInstallConstraints(
                    this.mInstallerPackageName, packageNames, constraints, callback, timeoutMillis);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commitSessionAfterInstallConstraintsAreMet(
            int sessionId,
            IntentSender statusReceiver,
            InstallConstraints constraints,
            long timeoutMillis) {
        try {
            IPackageInstallerSession session = this.mInstaller.openSession(sessionId);
            session.seal();
            List<String> packageNames = session.fetchPackageNames();
            Application context = ActivityThread.currentApplication();
            LocalIntentSender localIntentSender =
                    new LocalIntentSender(context, sessionId, session, statusReceiver);
            waitForInstallConstraints(
                    packageNames, constraints, localIntentSender.getIntentSender(), timeoutMillis);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class LocalIntentSender extends BroadcastReceiver {
        private final Context mContext;
        private final IPackageInstallerSession mSession;
        private final int mSessionId;
        private final IntentSender mStatusReceiver;

        LocalIntentSender(
                Context context,
                int sessionId,
                IPackageInstallerSession session,
                IntentSender statusReceiver) {
            this.mContext = context;
            this.mSessionId = sessionId;
            this.mSession = session;
            this.mStatusReceiver = statusReceiver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public IntentSender getIntentSender() {
            Intent intent =
                    new Intent(PackageInstaller.ACTION_WAIT_INSTALL_CONSTRAINTS)
                            .setPackage(this.mContext.getPackageName());
            this.mContext.registerReceiver(
                    this, new IntentFilter(PackageInstaller.ACTION_WAIT_INSTALL_CONSTRAINTS), 2);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(this.mContext, 0, intent, 33554432);
            return pendingIntent.getIntentSender();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            InstallConstraintsResult result =
                    (InstallConstraintsResult)
                            intent.getParcelableExtra(
                                    PackageInstaller.EXTRA_INSTALL_CONSTRAINTS_RESULT,
                                    InstallConstraintsResult.class);
            try {
                if (result.areAllConstraintsSatisfied()) {
                    this.mSession.commit(this.mStatusReceiver, false);
                } else {
                    Intent fillIn = new Intent();
                    fillIn.putExtra(PackageInstaller.EXTRA_SESSION_ID, this.mSessionId);
                    fillIn.putExtra(PackageInstaller.EXTRA_STATUS, 8);
                    fillIn.putExtra(
                            PackageInstaller.EXTRA_STATUS_MESSAGE,
                            "Install constraints not satisfied within timeout");
                    this.mStatusReceiver.sendIntent(
                            ActivityThread.currentApplication(), 0, fillIn, null, null);
                }
            } catch (Exception e) {
            } catch (Throwable th) {
                unregisterReceiver();
                throw th;
            }
            unregisterReceiver();
        }

        private void unregisterReceiver() {
            this.mContext.unregisterReceiver(this);
        }
    }

    static class SessionCallbackDelegate extends IPackageInstallerCallback.Stub {
        private static final int MSG_SESSION_ACTIVE_CHANGED = 3;
        private static final int MSG_SESSION_BADGING_CHANGED = 2;
        private static final int MSG_SESSION_CREATED = 1;
        private static final int MSG_SESSION_FINISHED = 5;
        private static final int MSG_SESSION_PROGRESS_CHANGED = 4;
        final SessionCallback mCallback;
        final Executor mExecutor;

        SessionCallbackDelegate(SessionCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionCreated(int sessionId) {
            this.mExecutor.execute(
                    PooledLambda.obtainRunnable(
                                    new BiConsumer() { // from class:
                                        // android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda4
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            ((PackageInstaller.SessionCallback) obj)
                                                    .onCreated(((Integer) obj2).intValue());
                                        }
                                    },
                                    this.mCallback,
                                    Integer.valueOf(sessionId))
                            .recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionBadgingChanged(int sessionId) {
            this.mExecutor.execute(
                    PooledLambda.obtainRunnable(
                                    new BiConsumer() { // from class:
                                        // android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda2
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            ((PackageInstaller.SessionCallback) obj)
                                                    .onBadgingChanged(((Integer) obj2).intValue());
                                        }
                                    },
                                    this.mCallback,
                                    Integer.valueOf(sessionId))
                            .recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionActiveChanged(int sessionId, boolean active) {
            this.mExecutor.execute(
                    PooledLambda.obtainRunnable(
                                    new TriConsumer() { // from class:
                                        // android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.function.TriConsumer
                                        public final void accept(
                                                Object obj, Object obj2, Object obj3) {
                                            ((PackageInstaller.SessionCallback) obj)
                                                    .onActiveChanged(
                                                            ((Integer) obj2).intValue(),
                                                            ((Boolean) obj3).booleanValue());
                                        }
                                    },
                                    this.mCallback,
                                    Integer.valueOf(sessionId),
                                    Boolean.valueOf(active))
                            .recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionProgressChanged(int sessionId, float progress) {
            this.mExecutor.execute(
                    PooledLambda.obtainRunnable(
                                    new TriConsumer() { // from class:
                                        // android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.function.TriConsumer
                                        public final void accept(
                                                Object obj, Object obj2, Object obj3) {
                                            ((PackageInstaller.SessionCallback) obj)
                                                    .onProgressChanged(
                                                            ((Integer) obj2).intValue(),
                                                            ((Float) obj3).floatValue());
                                        }
                                    },
                                    this.mCallback,
                                    Integer.valueOf(sessionId),
                                    Float.valueOf(progress))
                            .recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionFinished(int sessionId, boolean success) {
            this.mExecutor.execute(
                    PooledLambda.obtainRunnable(
                                    new TriConsumer() { // from class:
                                        // android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda3
                                        @Override // com.android.internal.util.function.TriConsumer
                                        public final void accept(
                                                Object obj, Object obj2, Object obj3) {
                                            ((PackageInstaller.SessionCallback) obj)
                                                    .onFinished(
                                                            ((Integer) obj2).intValue(),
                                                            ((Boolean) obj3).booleanValue());
                                        }
                                    },
                                    this.mCallback,
                                    Integer.valueOf(sessionId),
                                    Boolean.valueOf(success))
                            .recycleOnUse());
        }
    }

    @Deprecated
    public void addSessionCallback(SessionCallback callback) {
        registerSessionCallback(callback);
    }

    public void registerSessionCallback(SessionCallback callback) {
        registerSessionCallback(callback, new Handler());
    }

    @Deprecated
    public void addSessionCallback(SessionCallback callback, Handler handler) {
        registerSessionCallback(callback, handler);
    }

    public void registerSessionCallback(SessionCallback callback, Handler handler) {
        synchronized (this.mDelegates) {
            SessionCallbackDelegate delegate =
                    new SessionCallbackDelegate(callback, new HandlerExecutor(handler));
            try {
                this.mInstaller.registerCallback(delegate, this.mUserId);
                this.mDelegates.add(delegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void removeSessionCallback(SessionCallback callback) {
        unregisterSessionCallback(callback);
    }

    public void unregisterSessionCallback(SessionCallback callback) {
        synchronized (this.mDelegates) {
            Iterator<SessionCallbackDelegate> i = this.mDelegates.iterator();
            while (i.hasNext()) {
                SessionCallbackDelegate delegate = i.next();
                if (delegate.mCallback == callback) {
                    try {
                        this.mInstaller.unregisterCallback(delegate);
                        i.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public static class Session implements Closeable {
        protected final IPackageInstallerSession mSession;

        public Session(IPackageInstallerSession session) {
            this.mSession = session;
        }

        @Deprecated
        public void setProgress(float progress) {
            setStagingProgress(progress);
        }

        public void setStagingProgress(float progress) {
            try {
                this.mSession.setClientProgress(progress);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void addProgress(float progress) {
            try {
                this.mSession.addClientProgress(progress);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public OutputStream openWrite(String name, long offsetBytes, long lengthBytes)
                throws IOException {
            try {
                if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                    return new ParcelFileDescriptor.AutoCloseOutputStream(
                            this.mSession.openWrite(name, offsetBytes, lengthBytes));
                }
                ParcelFileDescriptor clientSocket =
                        this.mSession.openWrite(name, offsetBytes, lengthBytes);
                return new FileBridge.FileBridgeOutputStream(clientSocket);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void write(String name, long offsetBytes, long lengthBytes, ParcelFileDescriptor fd)
                throws IOException {
            try {
                this.mSession.write(name, offsetBytes, lengthBytes, fd);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void stageViaHardLink(String target) throws IOException {
            try {
                this.mSession.stageViaHardLink(target);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void fsync(OutputStream out) throws IOException {
            if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                if (out instanceof ParcelFileDescriptor.AutoCloseOutputStream) {
                    try {
                        Os.fsync(((ParcelFileDescriptor.AutoCloseOutputStream) out).getFD());
                        return;
                    } catch (ErrnoException e) {
                        throw e.rethrowAsIOException();
                    }
                }
                throw new IllegalArgumentException("Unrecognized stream");
            }
            if (out instanceof FileBridge.FileBridgeOutputStream) {
                ((FileBridge.FileBridgeOutputStream) out).fsync();
                return;
            }
            throw new IllegalArgumentException("Unrecognized stream");
        }

        public String[] getNames() throws IOException {
            try {
                return this.mSession.getNames();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public InputStream openRead(String name) throws IOException {
            try {
                ParcelFileDescriptor pfd = this.mSession.openRead(name);
                return new ParcelFileDescriptor.AutoCloseInputStream(pfd);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void removeSplit(String splitName) throws IOException {
            try {
                this.mSession.removeSplit(splitName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        @SystemApi
        public DataLoaderParams getDataLoaderParams() {
            try {
                DataLoaderParamsParcel data = this.mSession.getDataLoaderParams();
                if (data == null) {
                    return null;
                }
                return new DataLoaderParams(data);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void addFile(
                int location, String name, long lengthBytes, byte[] metadata, byte[] signature) {
            try {
                this.mSession.addFile(location, name, lengthBytes, metadata, signature);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void removeFile(int location, String name) {
            try {
                this.mSession.removeFile(location, name);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Deprecated
        public void setChecksums(String name, List<Checksum> checksums, byte[] signature)
                throws IOException {
            Objects.requireNonNull(name);
            Objects.requireNonNull(checksums);
            try {
                this.mSession.setChecksums(
                        name,
                        (Checksum[]) checksums.toArray(new Checksum[checksums.size()]),
                        signature);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        private static List<byte[]> encodeCertificates(List<Certificate> certs)
                throws CertificateEncodingException {
            if (certs == null) {
                return null;
            }
            List<byte[]> result = new ArrayList<>(certs.size());
            for (Certificate cert : certs) {
                if (!(cert instanceof X509Certificate)) {
                    throw new CertificateEncodingException("Only X509 certificates supported.");
                }
                result.add(cert.getEncoded());
            }
            return result;
        }

        public void requestChecksums(
                String name,
                int required,
                List<Certificate> trustedInstallers,
                Executor executor,
                PackageManager.OnChecksumsReadyListener onChecksumsReadyListener)
                throws CertificateEncodingException, FileNotFoundException {
            Objects.requireNonNull(name);
            Objects.requireNonNull(trustedInstallers);
            Objects.requireNonNull(executor);
            Objects.requireNonNull(onChecksumsReadyListener);
            if (trustedInstallers == PackageManager.TRUST_ALL) {
                trustedInstallers = null;
            } else if (trustedInstallers == PackageManager.TRUST_NONE) {
                trustedInstallers = Collections.emptyList();
            } else if (trustedInstallers.isEmpty()) {
                throw new IllegalArgumentException(
                        "trustedInstallers has to be one of TRUST_ALL/TRUST_NONE or a non-empty"
                                + " list of certificates.");
            }
            try {
                IOnChecksumsReadyListener onChecksumsReadyListenerDelegate =
                        new AnonymousClass1(executor, onChecksumsReadyListener);
                this.mSession.requestChecksums(
                        name,
                        127,
                        required,
                        encodeCertificates(trustedInstallers),
                        onChecksumsReadyListenerDelegate);
            } catch (ParcelableException e) {
                e.maybeRethrow(FileNotFoundException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        /* renamed from: android.content.pm.PackageInstaller$Session$1, reason: invalid class name */
        class AnonymousClass1 extends IOnChecksumsReadyListener.Stub {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ PackageManager.OnChecksumsReadyListener
                    val$onChecksumsReadyListener;

            AnonymousClass1(
                    Executor executor,
                    PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) {
                this.val$executor = executor;
                this.val$onChecksumsReadyListener = onChecksumsReadyListener;
            }

            @Override // android.content.pm.IOnChecksumsReadyListener
            public void onChecksumsReady(final List<ApkChecksum> checksums) throws RemoteException {
                Executor executor = this.val$executor;
                final PackageManager.OnChecksumsReadyListener onChecksumsReadyListener =
                        this.val$onChecksumsReadyListener;
                executor.execute(
                        new Runnable() { // from class:
                            // android.content.pm.PackageInstaller$Session$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                PackageManager.OnChecksumsReadyListener.this.onChecksumsReady(
                                        checksums);
                            }
                        });
            }
        }

        public void commit(IntentSender statusReceiver) {
            try {
                this.mSession.commit(statusReceiver, false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void commitTransferred(IntentSender statusReceiver) {
            try {
                this.mSession.commit(statusReceiver, true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void transfer(String packageName) throws PackageManager.NameNotFoundException {
            Preconditions.checkArgument(!TextUtils.isEmpty(packageName));
            try {
                this.mSession.transfer(packageName);
            } catch (ParcelableException e) {
                e.maybeRethrow(PackageManager.NameNotFoundException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                this.mSession.close();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void abandon() {
            try {
                this.mSession.abandon();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean isMultiPackage() {
            try {
                return this.mSession.isMultiPackage();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean isStaged() {
            try {
                return this.mSession.isStaged();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getInstallFlags() {
            try {
                return this.mSession.getInstallFlags();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getParentSessionId() {
            try {
                return this.mSession.getParentSessionId();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int[] getChildSessionIds() {
            try {
                return this.mSession.getChildSessionIds();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void addChildSessionId(int sessionId) {
            try {
                this.mSession.addChildSessionId(sessionId);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public void removeChildSessionId(int sessionId) {
            try {
                this.mSession.removeChildSessionId(sessionId);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public PersistableBundle getAppMetadata() {
            PersistableBundle data = null;
            try {
                ParcelFileDescriptor pfd = this.mSession.getAppMetadataFd();
                if (pfd != null) {
                    InputStream inputStream = new ParcelFileDescriptor.AutoCloseInputStream(pfd);
                    try {
                        data = PersistableBundle.readFromStream(inputStream);
                        inputStream.close();
                    } finally {
                    }
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            return data != null ? data : new PersistableBundle();
        }

        private OutputStream openWriteAppMetadata() throws IOException {
            try {
                if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                    return new ParcelFileDescriptor.AutoCloseOutputStream(
                            this.mSession.openWriteAppMetadata());
                }
                ParcelFileDescriptor clientSocket = this.mSession.openWriteAppMetadata();
                return new FileBridge.FileBridgeOutputStream(clientSocket);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void setAppMetadata(PersistableBundle data) throws IOException {
            if (data == null || data.isEmpty()) {
                try {
                    this.mSession.removeAppMetadata();
                    return;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            Objects.requireNonNull(data);
            OutputStream outputStream = openWriteAppMetadata();
            try {
                data.writeToStream(outputStream);
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Throwable th) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void requestUserPreapproval(
                PreapprovalDetails details, IntentSender statusReceiver) {
            Preconditions.checkArgument(details != null, "preapprovalDetails cannot be null.");
            Preconditions.checkArgument(statusReceiver != null, "statusReceiver cannot be null.");
            try {
                this.mSession.requestUserPreapproval(details, statusReceiver);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public boolean isApplicationEnabledSettingPersistent() {
            try {
                return this.mSession.isApplicationEnabledSettingPersistent();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean isRequestUpdateOwnership() {
            try {
                return this.mSession.isRequestUpdateOwnership();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void setPreVerifiedDomains(Set<String> preVerifiedDomains) {
            Preconditions.checkArgument(
                    (preVerifiedDomains == null || preVerifiedDomains.isEmpty()) ? false : true,
                    "Provided pre-verified domains cannot be null or empty.");
            try {
                this.mSession.setPreVerifiedDomains(new DomainSet(preVerifiedDomains));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public Set<String> getPreVerifiedDomains() {
            try {
                DomainSet domainSet = this.mSession.getPreVerifiedDomains();
                return domainSet != null ? domainSet.getDomains() : Collections.emptySet();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public InstallInfo readInstallInfo(File file, int flags) throws PackageParsingException {
        ParseTypeImpl input = ParseTypeImpl.forDefaultParsing();
        ParseResult<PackageLite> result =
                ApkLiteParseUtils.parsePackageLite(input.reset(), file, flags);
        if (result.isError()) {
            throw new PackageParsingException(result.getErrorCode(), result.getErrorMessage());
        }
        return new InstallInfo(result);
    }

    @SystemApi
    public InstallInfo readInstallInfo(ParcelFileDescriptor pfd, String debugPathName, int flags)
            throws PackageParsingException {
        ParseTypeImpl input = ParseTypeImpl.forDefaultParsing();
        ParseResult<PackageLite> result =
                ApkLiteParseUtils.parseMonolithicPackageLite(
                        input, pfd.getFileDescriptor(), debugPathName, flags);
        if (result.isError()) {
            throw new PackageParsingException(result.getErrorCode(), result.getErrorMessage());
        }
        return new InstallInfo(result);
    }

    public void requestArchive(String packageName, IntentSender statusReceiver)
            throws PackageManager.NameNotFoundException {
        try {
            this.mInstaller.requestArchive(
                    packageName,
                    this.mInstallerPackageName,
                    0,
                    statusReceiver,
                    new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void requestUnarchive(String packageName, IntentSender statusReceiver)
            throws IOException, PackageManager.NameNotFoundException {
        try {
            this.mInstaller.requestUnarchive(
                    packageName,
                    this.mInstallerPackageName,
                    statusReceiver,
                    new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void reportUnarchivalStatus(
            int unarchiveId, int status, long requiredStorageBytes, PendingIntent userActionIntent)
            throws PackageManager.NameNotFoundException {
        try {
            this.mInstaller.reportUnarchivalStatus(
                    unarchiveId,
                    status,
                    requiredStorageBytes,
                    userActionIntent,
                    new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void reportUnarchivalState(UnarchivalState unarchivalState)
            throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(unarchivalState);
        try {
            this.mInstaller.reportUnarchivalStatus(
                    unarchivalState.getUnarchiveId(),
                    unarchivalState.getStatus(),
                    unarchivalState.getRequiredStorageBytes(),
                    unarchivalState.getUserActionIntent(),
                    new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static class InstallInfo {
        private PackageLite mPkg;

        @Retention(RetentionPolicy.SOURCE)
        public @interface InstallLocation {}

        InstallInfo(ParseResult<PackageLite> result) {
            this.mPkg = result.getResult();
        }

        public String getPackageName() {
            return this.mPkg.getPackageName();
        }

        public int getInstallLocation() {
            return this.mPkg.getInstallLocation();
        }

        public long calculateInstalledSize(SessionParams params) throws IOException {
            return InstallLocationUtils.calculateInstalledSize(this.mPkg, params.abiOverride);
        }

        public long calculateInstalledSize(SessionParams params, ParcelFileDescriptor pfd)
                throws IOException {
            return InstallLocationUtils.calculateInstalledSize(
                    this.mPkg, params.abiOverride, pfd.getFileDescriptor());
        }
    }

    @SystemApi
    public static class PackageParsingException extends Exception {
        private final int mErrorCode;

        public PackageParsingException(int errorCode, String detailedMessage) {
            super(detailedMessage);
            this.mErrorCode = errorCode;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public static class SessionParams implements Parcelable {
        public static final int MAX_PACKAGE_NAME_LENGTH = 255;
        public static final int MODE_FULL_INSTALL = 1;
        public static final int MODE_INHERIT_EXISTING = 2;
        public static final int MODE_INVALID = -1;
        public static final int PERMISSION_STATE_DEFAULT = 0;
        public static final int PERMISSION_STATE_DENIED = 2;
        public static final int PERMISSION_STATE_GRANTED = 1;
        public static final int UID_UNKNOWN = -1;
        public static final int USER_ACTION_NOT_REQUIRED = 2;
        public static final int USER_ACTION_REQUIRED = 1;
        public static final int USER_ACTION_UNSPECIFIED = 0;
        public String abiOverride;
        public Bitmap appIcon;
        public long appIconLastModified;
        public String appLabel;
        public String appPackageName;
        public boolean applicationEnabledSettingPersistent;
        public int autoRevokePermissionsMode;
        public DataLoaderParams dataLoaderParams;
        public int developmentInstallFlags;
        public String dexoptCompilerFilter;
        public boolean forceQueryableOverride;
        public int installFlags;
        public int installLocation;
        public int installReason;
        public int installScenario;
        public String installerPackageName;
        public boolean isMultiPackage;
        public boolean isStaged;
        private final ArrayMap<String, Integer> mPermissionStates;
        public int mode;
        public int originatingUid;
        public Uri originatingUri;
        public int packageSource;
        public Uri referrerUri;
        public int requireUserAction;
        public long requiredInstalledVersionCode;
        public int rollbackDataPolicy;
        public int rollbackImpactLevel;
        public long rollbackLifetimeMillis;
        public int sessionFlags;
        public long sizeBytes;
        public int unarchiveId;
        public String volumeUuid;
        public List<String> whitelistedRestrictedPermissions;
        public static final Set<String> RESTRICTED_PERMISSIONS_ALL = new ArraySet();
        public static final Parcelable.Creator<SessionParams> CREATOR =
                new Parcelable.Creator<SessionParams>() { // from class:
                    // android.content.pm.PackageInstaller.SessionParams.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SessionParams createFromParcel(Parcel p) {
                        return new SessionParams(p);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SessionParams[] newArray(int size) {
                        return new SessionParams[size];
                    }
                };

        @Retention(RetentionPolicy.SOURCE)
        public @interface PermissionState {}

        @Retention(RetentionPolicy.SOURCE)
        public @interface UserActionRequirement {}

        public SessionParams(int mode) {
            this.mode = -1;
            this.installFlags = 4194304;
            this.installLocation = 1;
            this.installReason = 0;
            this.installScenario = 0;
            this.sizeBytes = -1L;
            this.appIconLastModified = -1L;
            this.originatingUid = -1;
            this.autoRevokePermissionsMode = 3;
            this.packageSource = 0;
            this.requiredInstalledVersionCode = -1L;
            this.rollbackDataPolicy = 0;
            this.rollbackLifetimeMillis = 0L;
            this.rollbackImpactLevel = 0;
            this.requireUserAction = 0;
            this.sessionFlags = 0;
            this.applicationEnabledSettingPersistent = false;
            this.developmentInstallFlags = 0;
            this.unarchiveId = -1;
            this.dexoptCompilerFilter = null;
            this.mode = mode;
            this.mPermissionStates = new ArrayMap<>();
        }

        public SessionParams(Parcel source) {
            this.mode = -1;
            this.installFlags = 4194304;
            this.installLocation = 1;
            this.installReason = 0;
            this.installScenario = 0;
            this.sizeBytes = -1L;
            this.appIconLastModified = -1L;
            this.originatingUid = -1;
            this.autoRevokePermissionsMode = 3;
            this.packageSource = 0;
            this.requiredInstalledVersionCode = -1L;
            this.rollbackDataPolicy = 0;
            this.rollbackLifetimeMillis = 0L;
            this.rollbackImpactLevel = 0;
            this.requireUserAction = 0;
            this.sessionFlags = 0;
            this.applicationEnabledSettingPersistent = false;
            this.developmentInstallFlags = 0;
            this.unarchiveId = -1;
            this.dexoptCompilerFilter = null;
            this.mode = source.readInt();
            this.installFlags = source.readInt();
            this.installLocation = source.readInt();
            this.installReason = source.readInt();
            this.installScenario = source.readInt();
            this.sizeBytes = source.readLong();
            this.appPackageName = source.readString();
            this.appIcon = (Bitmap) source.readParcelable(null, Bitmap.class);
            this.appLabel = source.readString();
            this.originatingUri = (Uri) source.readParcelable(null, Uri.class);
            this.originatingUid = source.readInt();
            this.referrerUri = (Uri) source.readParcelable(null, Uri.class);
            this.abiOverride = source.readString();
            this.volumeUuid = source.readString();
            this.mPermissionStates = new ArrayMap<>();
            source.readMap(this.mPermissionStates, null, String.class, Integer.class);
            this.whitelistedRestrictedPermissions = source.createStringArrayList();
            this.autoRevokePermissionsMode = source.readInt();
            this.installerPackageName = source.readString();
            this.isMultiPackage = source.readBoolean();
            this.isStaged = source.readBoolean();
            this.forceQueryableOverride = source.readBoolean();
            this.requiredInstalledVersionCode = source.readLong();
            DataLoaderParamsParcel dataLoaderParamsParcel =
                    (DataLoaderParamsParcel)
                            source.readParcelable(
                                    DataLoaderParamsParcel.class.getClassLoader(),
                                    DataLoaderParamsParcel.class);
            if (dataLoaderParamsParcel != null) {
                this.dataLoaderParams = new DataLoaderParams(dataLoaderParamsParcel);
            }
            this.rollbackDataPolicy = source.readInt();
            this.rollbackLifetimeMillis = source.readLong();
            this.rollbackImpactLevel = source.readInt();
            this.requireUserAction = source.readInt();
            this.packageSource = source.readInt();
            this.applicationEnabledSettingPersistent = source.readBoolean();
            this.developmentInstallFlags = source.readInt();
            this.unarchiveId = source.readInt();
            this.dexoptCompilerFilter = source.readString();
            this.sessionFlags = source.readInt();
        }

        public SessionParams copy() {
            SessionParams ret = new SessionParams(this.mode);
            ret.installFlags = this.installFlags;
            ret.installLocation = this.installLocation;
            ret.installReason = this.installReason;
            ret.installScenario = this.installScenario;
            ret.sizeBytes = this.sizeBytes;
            ret.appPackageName = this.appPackageName;
            ret.appIcon = this.appIcon;
            ret.appLabel = this.appLabel;
            ret.originatingUri = this.originatingUri;
            ret.originatingUid = this.originatingUid;
            ret.referrerUri = this.referrerUri;
            ret.abiOverride = this.abiOverride;
            ret.volumeUuid = this.volumeUuid;
            ret.mPermissionStates.putAll(
                    (ArrayMap<? extends String, ? extends Integer>) this.mPermissionStates);
            ret.whitelistedRestrictedPermissions = this.whitelistedRestrictedPermissions;
            ret.autoRevokePermissionsMode = this.autoRevokePermissionsMode;
            ret.installerPackageName = this.installerPackageName;
            ret.isMultiPackage = this.isMultiPackage;
            ret.isStaged = this.isStaged;
            ret.forceQueryableOverride = this.forceQueryableOverride;
            ret.requiredInstalledVersionCode = this.requiredInstalledVersionCode;
            ret.dataLoaderParams = this.dataLoaderParams;
            ret.rollbackDataPolicy = this.rollbackDataPolicy;
            ret.rollbackLifetimeMillis = this.rollbackLifetimeMillis;
            ret.rollbackImpactLevel = this.rollbackImpactLevel;
            ret.requireUserAction = this.requireUserAction;
            ret.packageSource = this.packageSource;
            ret.applicationEnabledSettingPersistent = this.applicationEnabledSettingPersistent;
            ret.developmentInstallFlags = this.developmentInstallFlags;
            ret.unarchiveId = this.unarchiveId;
            ret.dexoptCompilerFilter = this.dexoptCompilerFilter;
            ret.sessionFlags = this.sessionFlags;
            return ret;
        }

        public boolean areHiddenOptionsSet() {
            return ((this.installFlags & 1169536) == this.installFlags
                            && this.abiOverride == null
                            && this.volumeUuid == null)
                    ? false
                    : true;
        }

        public void setInstallLocation(int installLocation) {
            this.installLocation = installLocation;
        }

        public void setSize(long sizeBytes) {
            this.sizeBytes = sizeBytes;
        }

        public void setAppPackageName(String appPackageName) {
            this.appPackageName = appPackageName;
        }

        public void setAppIcon(Bitmap appIcon) {
            this.appIcon = appIcon;
        }

        public void setAppLabel(CharSequence appLabel) {
            this.appLabel = appLabel != null ? appLabel.toString() : null;
        }

        public void setOriginatingUri(Uri originatingUri) {
            this.originatingUri = originatingUri;
        }

        public void setOriginatingUid(int originatingUid) {
            this.originatingUid = originatingUid;
        }

        public void setReferrerUri(Uri referrerUri) {
            this.referrerUri = referrerUri;
        }

        @SystemApi
        @Deprecated
        public void setGrantedRuntimePermissions(String[] permissions) {
            if (permissions == null) {
                this.installFlags |= 256;
                this.mPermissionStates.clear();
                return;
            }
            this.installFlags &= -257;
            for (String permission : permissions) {
                setPermissionState(permission, 1);
            }
        }

        public SessionParams setPermissionState(String permissionName, int state) {
            if (TextUtils.isEmpty(permissionName)) {
                throw new IllegalArgumentException(
                        "Provided permissionName cannot be "
                                + (permissionName == null ? "null" : "empty"));
            }
            switch (state) {
                case 0:
                    this.mPermissionStates.remove(permissionName);
                    return this;
                case 1:
                case 2:
                    this.mPermissionStates.put(permissionName, Integer.valueOf(state));
                    return this;
                default:
                    throw new IllegalArgumentException("Unexpected permission state int: " + state);
            }
        }

        public void setPermissionStates(
                Collection<String> grantPermissions, Collection<String> denyPermissions) {
            for (String grantPermission : grantPermissions) {
                this.mPermissionStates.put(grantPermission, 1);
            }
            for (String denyPermission : denyPermissions) {
                this.mPermissionStates.put(denyPermission, 2);
            }
        }

        public void setPackageSource(int packageSource) {
            this.packageSource = packageSource;
        }

        public void setWhitelistedRestrictedPermissions(Set<String> permissions) {
            if (permissions == RESTRICTED_PERMISSIONS_ALL) {
                this.installFlags |= 4194304;
                this.whitelistedRestrictedPermissions = null;
            } else {
                this.installFlags &= -4194305;
                this.whitelistedRestrictedPermissions =
                        permissions != null ? new ArrayList(permissions) : null;
            }
        }

        @Deprecated
        public void setAutoRevokePermissionsMode(boolean z) {
            this.autoRevokePermissionsMode = !z ? 1 : 0;
        }

        @SystemApi
        public void setEnableRollback(boolean enable) {
            setEnableRollback(enable, 0);
        }

        @SystemApi
        public void setEnableRollback(boolean enable, int dataPolicy) {
            if (enable) {
                this.installFlags |= 262144;
            } else {
                this.installFlags &= -262145;
                this.rollbackLifetimeMillis = 0L;
            }
            this.rollbackDataPolicy = dataPolicy;
        }

        @SystemApi
        public void setRollbackLifetimeMillis(long lifetimeMillis) {
            if (lifetimeMillis < 0) {
                throw new IllegalArgumentException("rollbackLifetimeMillis can't be negative.");
            }
            if ((this.installFlags & 262144) == 0) {
                throw new IllegalArgumentException(
                        "Can't set rollbackLifetimeMillis when rollback is not enabled");
            }
            this.rollbackLifetimeMillis = lifetimeMillis;
        }

        @SystemApi
        public void setRollbackImpactLevel(int impactLevel) {
            if ((this.installFlags & 262144) == 0) {
                throw new IllegalArgumentException(
                        "Can't set rollbackImpactLevel when rollback is not enabled");
            }
            this.rollbackImpactLevel = impactLevel;
        }

        @SystemApi
        @Deprecated
        public void setAllowDowngrade(boolean allowDowngrade) {
            setRequestDowngrade(allowDowngrade);
        }

        @SystemApi
        public void setRequestDowngrade(boolean requestDowngrade) {
            if (requestDowngrade) {
                this.installFlags |= 128;
            } else {
                this.installFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
        }

        public void setRequiredInstalledVersionCode(long versionCode) {
            this.requiredInstalledVersionCode = versionCode;
        }

        public void setInstallFlagsForcePermissionPrompt() {
            this.installFlags |= 1024;
        }

        public void setDontKillApp(boolean dontKillApp) {
            if (dontKillApp) {
                this.installFlags |= 4096;
            } else {
                this.installFlags &= -4097;
            }
        }

        @SystemApi
        public void setInstallAsInstantApp(boolean isInstantApp) {
            if (isInstantApp) {
                this.installFlags |= 2048;
                this.installFlags &= -16385;
            } else {
                this.installFlags &= -2049;
                this.installFlags |= 16384;
            }
        }

        @SystemApi
        public void setInstallAsVirtualPreload() {
            this.installFlags |= 65536;
        }

        public void setInstallReason(int installReason) {
            this.installReason = installReason;
        }

        @SystemApi
        public void setAllocateAggressive(boolean allocateAggressive) {
            if (allocateAggressive) {
                this.installFlags |= 32768;
            } else {
                this.installFlags &= -32769;
            }
        }

        public void setInstallFlagAllowTest() {
            this.installFlags |= 4;
        }

        public void setInstallerPackageName(String installerPackageName) {
            this.installerPackageName = installerPackageName;
        }

        public void setMultiPackage() {
            this.isMultiPackage = true;
        }

        @SystemApi
        public void setStaged() {
            this.isStaged = true;
        }

        @SystemApi
        public void setInstallAsApex() {
            this.installFlags |= 131072;
        }

        public boolean getEnableRollback() {
            return (this.installFlags & 262144) != 0;
        }

        public void semSetInstallFlagsSkipDexOptimization() {
            this.installFlags |= 67108864;
        }

        public void semSetInstallFlagsDisableVerification() {
            this.sessionFlags |= 33554432;
        }

        @SystemApi
        public void setDataLoaderParams(DataLoaderParams dataLoaderParams) {
            this.dataLoaderParams = dataLoaderParams;
        }

        public void setForceQueryable() {
            this.forceQueryableOverride = true;
        }

        public void setRequireUserAction(int requireUserAction) {
            if (requireUserAction != 0 && requireUserAction != 1 && requireUserAction != 2) {
                throw new IllegalArgumentException(
                        "requireUserAction set as invalid value of "
                                + requireUserAction
                                + ", but must be one of [USER_ACTION_UNSPECIFIED,"
                                + " USER_ACTION_REQUIRED, USER_ACTION_NOT_REQUIRED]");
            }
            this.requireUserAction = requireUserAction;
        }

        public void setInstallScenario(int installScenario) {
            this.installScenario = installScenario;
        }

        public void setApplicationEnabledSettingPersistent() {
            this.applicationEnabledSettingPersistent = true;
        }

        public void setRequestUpdateOwnership(boolean enable) {
            if (enable) {
                this.installFlags |= 33554432;
            } else {
                this.installFlags &= -33554433;
            }
        }

        public void setUnarchiveId(int unarchiveId) {
            this.unarchiveId = unarchiveId;
        }

        public void setDexoptCompilerFilter(String dexoptCompilerFilter) {
            this.dexoptCompilerFilter = dexoptCompilerFilter;
        }

        public ArrayMap<String, Integer> getPermissionStates() {
            return this.mPermissionStates;
        }

        public String[] getLegacyGrantedRuntimePermissions() {
            if ((this.installFlags & 256) != 0) {
                return null;
            }
            ArrayList<String> grantedPermissions = new ArrayList<>();
            for (int index = 0; index < this.mPermissionStates.size(); index++) {
                String permissionName = this.mPermissionStates.keyAt(index);
                Integer state = this.mPermissionStates.valueAt(index);
                if (state.intValue() == 1) {
                    grantedPermissions.add(permissionName);
                }
            }
            return (String[])
                    grantedPermissions.toArray((String[]) ArrayUtils.emptyArray(String.class));
        }

        public void dump(IndentingPrintWriter pw) {
            pw.printPair("mode", Integer.valueOf(this.mode));
            pw.printHexPair("installFlags", this.installFlags);
            pw.printPair("installLocation", Integer.valueOf(this.installLocation));
            pw.printPair("installReason", Integer.valueOf(this.installReason));
            pw.printPair("installScenario", Integer.valueOf(this.installScenario));
            pw.printPair("sizeBytes", Long.valueOf(this.sizeBytes));
            pw.printPair("appPackageName", this.appPackageName);
            pw.printPair("appIcon", Boolean.valueOf(this.appIcon != null));
            pw.printPair(SemShareConstants.SHARE_STAR_KEY_APPLABEL, this.appLabel);
            pw.printPair("originatingUri", this.originatingUri);
            pw.printPair("originatingUid", Integer.valueOf(this.originatingUid));
            pw.printPair("referrerUri", this.referrerUri);
            pw.printPair("abiOverride", this.abiOverride);
            pw.printPair("volumeUuid", this.volumeUuid);
            pw.printPair("mPermissionStates", this.mPermissionStates);
            pw.printPair("packageSource", Integer.valueOf(this.packageSource));
            pw.printPair("whitelistedRestrictedPermissions", this.whitelistedRestrictedPermissions);
            pw.printPair("autoRevokePermissions", Integer.valueOf(this.autoRevokePermissionsMode));
            pw.printPair("installerPackageName", this.installerPackageName);
            pw.printPair("isMultiPackage", Boolean.valueOf(this.isMultiPackage));
            pw.printPair("isStaged", Boolean.valueOf(this.isStaged));
            pw.printPair("forceQueryable", Boolean.valueOf(this.forceQueryableOverride));
            pw.printPair(
                    "requireUserAction", SessionInfo.userActionToString(this.requireUserAction));
            pw.printPair(
                    "requiredInstalledVersionCode",
                    Long.valueOf(this.requiredInstalledVersionCode));
            pw.printPair("dataLoaderParams", this.dataLoaderParams);
            pw.printPair("rollbackDataPolicy", Integer.valueOf(this.rollbackDataPolicy));
            pw.printPair("rollbackLifetimeMillis", Long.valueOf(this.rollbackLifetimeMillis));
            pw.printPair("rollbackImpactLevel", Integer.valueOf(this.rollbackImpactLevel));
            pw.printPair(
                    "applicationEnabledSettingPersistent",
                    Boolean.valueOf(this.applicationEnabledSettingPersistent));
            pw.printHexPair("developmentInstallFlags", this.developmentInstallFlags);
            pw.printPair("unarchiveId", Integer.valueOf(this.unarchiveId));
            pw.printPair("dexoptCompilerFilter", this.dexoptCompilerFilter);
            pw.printPair("sessionFlags", Integer.valueOf(this.sessionFlags == 0 ? 0 : 1));
            pw.println();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mode);
            dest.writeInt(this.installFlags);
            dest.writeInt(this.installLocation);
            dest.writeInt(this.installReason);
            dest.writeInt(this.installScenario);
            dest.writeLong(this.sizeBytes);
            dest.writeString(this.appPackageName);
            dest.writeParcelable(this.appIcon, flags);
            dest.writeString(this.appLabel);
            dest.writeParcelable(this.originatingUri, flags);
            dest.writeInt(this.originatingUid);
            dest.writeParcelable(this.referrerUri, flags);
            dest.writeString(this.abiOverride);
            dest.writeString(this.volumeUuid);
            dest.writeMap(this.mPermissionStates);
            dest.writeStringList(this.whitelistedRestrictedPermissions);
            dest.writeInt(this.autoRevokePermissionsMode);
            dest.writeString(this.installerPackageName);
            dest.writeBoolean(this.isMultiPackage);
            dest.writeBoolean(this.isStaged);
            dest.writeBoolean(this.forceQueryableOverride);
            dest.writeLong(this.requiredInstalledVersionCode);
            if (this.dataLoaderParams != null) {
                dest.writeParcelable(this.dataLoaderParams.getData(), flags);
            } else {
                dest.writeParcelable(null, flags);
            }
            dest.writeInt(this.rollbackDataPolicy);
            dest.writeLong(this.rollbackLifetimeMillis);
            dest.writeInt(this.rollbackImpactLevel);
            dest.writeInt(this.requireUserAction);
            dest.writeInt(this.packageSource);
            dest.writeBoolean(this.applicationEnabledSettingPersistent);
            dest.writeInt(this.developmentInstallFlags);
            dest.writeInt(this.unarchiveId);
            dest.writeString(this.dexoptCompilerFilter);
            dest.writeInt(this.sessionFlags);
        }
    }

    public static class SessionInfo implements Parcelable {
        public static final int INVALID_ID = -1;
        public static final int SESSION_ACTIVATION_FAILED = 2;
        public static final int SESSION_CONFLICT = 4;
        public static final int SESSION_NO_ERROR = 0;
        public static final int SESSION_UNKNOWN_ERROR = 3;
        public static final int SESSION_VERIFICATION_FAILED = 1;

        @Deprecated public static final int STAGED_SESSION_ACTIVATION_FAILED = 2;

        @Deprecated public static final int STAGED_SESSION_CONFLICT = 4;

        @Deprecated public static final int STAGED_SESSION_NO_ERROR = 0;

        @Deprecated public static final int STAGED_SESSION_UNKNOWN = 3;

        @Deprecated public static final int STAGED_SESSION_VERIFICATION_FAILED = 1;
        public boolean active;
        public Bitmap appIcon;
        public CharSequence appLabel;
        public String appPackageName;
        public boolean applicationEnabledSettingPersistent;
        public int autoRevokePermissionsMode;
        public int[] childSessionIds;
        public long createdMillis;
        public boolean forceQueryable;
        public String[] grantedRuntimePermissions;
        public int installFlags;
        public int installLocation;
        public int installReason;
        public int installScenario;
        public String installerAttributionTag;
        public String installerPackageName;
        public int installerUid;
        public boolean isCommitted;
        public boolean isMultiPackage;
        public boolean isPreapprovalRequested;
        public boolean isSessionApplied;
        public boolean isSessionFailed;
        public boolean isSessionReady;
        public boolean isStaged;
        private int mSessionErrorCode;
        private String mSessionErrorMessage;
        public int mode;
        public int originatingUid;
        public Uri originatingUri;
        public int packageSource;
        public int parentSessionId;
        public int pendingUserActionReason;
        public float progress;
        public Uri referrerUri;
        public int requireUserAction;
        public String resolvedBaseCodePath;
        public int rollbackDataPolicy;
        public int rollbackImpactLevel;
        public long rollbackLifetimeMillis;
        public boolean sealed;
        public int sessionId;
        public long sizeBytes;
        public long updatedMillis;
        public int userId;
        public List<String> whitelistedRestrictedPermissions;
        private static final int[] NO_SESSIONS = new int[0];
        public static final Parcelable.Creator<SessionInfo> CREATOR =
                new Parcelable.Creator<SessionInfo>() { // from class:
                    // android.content.pm.PackageInstaller.SessionInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SessionInfo createFromParcel(Parcel p) {
                        return new SessionInfo(p);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SessionInfo[] newArray(int size) {
                        return new SessionInfo[size];
                    }
                };

        /* JADX INFO: Access modifiers changed from: private */
        public static String userActionToString(int requireUserAction) {
            switch (requireUserAction) {
                case 1:
                    return "REQUIRED";
                case 2:
                    return "NOT_REQUIRED";
                default:
                    return "UNSPECIFIED";
            }
        }

        public SessionInfo() {
            this.autoRevokePermissionsMode = 3;
            this.parentSessionId = -1;
            this.childSessionIds = NO_SESSIONS;
            this.packageSource = 0;
        }

        public SessionInfo(Parcel source) {
            this.autoRevokePermissionsMode = 3;
            this.parentSessionId = -1;
            this.childSessionIds = NO_SESSIONS;
            this.packageSource = 0;
            this.sessionId = source.readInt();
            this.userId = source.readInt();
            this.installerPackageName = source.readString();
            this.installerAttributionTag = source.readString();
            this.resolvedBaseCodePath = source.readString();
            this.progress = source.readFloat();
            this.sealed = source.readInt() != 0;
            this.active = source.readInt() != 0;
            this.mode = source.readInt();
            this.installReason = source.readInt();
            this.installScenario = source.readInt();
            this.sizeBytes = source.readLong();
            this.appPackageName = source.readString();
            this.appIcon = (Bitmap) source.readParcelable(null, Bitmap.class);
            this.appLabel = source.readString();
            this.installLocation = source.readInt();
            this.originatingUri = (Uri) source.readParcelable(null, Uri.class);
            this.originatingUid = source.readInt();
            this.referrerUri = (Uri) source.readParcelable(null, Uri.class);
            this.grantedRuntimePermissions = source.readStringArray();
            this.whitelistedRestrictedPermissions = source.createStringArrayList();
            this.autoRevokePermissionsMode = source.readInt();
            this.installFlags = source.readInt();
            this.isMultiPackage = source.readBoolean();
            this.isStaged = source.readBoolean();
            this.forceQueryable = source.readBoolean();
            this.parentSessionId = source.readInt();
            this.childSessionIds = source.createIntArray();
            if (this.childSessionIds == null) {
                this.childSessionIds = NO_SESSIONS;
            }
            this.isSessionApplied = source.readBoolean();
            this.isSessionReady = source.readBoolean();
            this.isSessionFailed = source.readBoolean();
            this.mSessionErrorCode = source.readInt();
            this.mSessionErrorMessage = source.readString();
            this.isCommitted = source.readBoolean();
            this.isPreapprovalRequested = source.readBoolean();
            this.rollbackDataPolicy = source.readInt();
            this.rollbackLifetimeMillis = source.readLong();
            this.rollbackImpactLevel = source.readInt();
            this.createdMillis = source.readLong();
            this.requireUserAction = source.readInt();
            this.installerUid = source.readInt();
            this.packageSource = source.readInt();
            this.applicationEnabledSettingPersistent = source.readBoolean();
            this.pendingUserActionReason = source.readInt();
        }

        public int getSessionId() {
            return this.sessionId;
        }

        public UserHandle getUser() {
            return new UserHandle(this.userId);
        }

        public String getInstallerPackageName() {
            return this.installerPackageName;
        }

        public String getInstallerAttributionTag() {
            return this.installerAttributionTag;
        }

        public float getProgress() {
            return this.progress;
        }

        public boolean isActive() {
            return this.active;
        }

        public boolean isSealed() {
            return this.sealed;
        }

        public int getInstallReason() {
            return this.installReason;
        }

        @Deprecated
        public boolean isOpen() {
            return isActive();
        }

        public String getAppPackageName() {
            return this.appPackageName;
        }

        public Bitmap getAppIcon() {
            if (this.appIcon == null) {
                try {
                    SessionInfo info =
                            AppGlobals.getPackageManager()
                                    .getPackageInstaller()
                                    .getSessionInfo(this.sessionId);
                    this.appIcon = info != null ? info.appIcon : null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return this.appIcon;
        }

        public CharSequence getAppLabel() {
            return this.appLabel;
        }

        public Intent createDetailsIntent() {
            Intent intent = new Intent(PackageInstaller.ACTION_SESSION_DETAILS);
            intent.putExtra(PackageInstaller.EXTRA_SESSION_ID, this.sessionId);
            intent.setPackage(this.installerPackageName);
            intent.setFlags(268435456);
            return intent;
        }

        public int getMode() {
            return this.mode;
        }

        public int getInstallLocation() {
            return this.installLocation;
        }

        public long getSize() {
            return this.sizeBytes;
        }

        public Uri getOriginatingUri() {
            return this.originatingUri;
        }

        public int getOriginatingUid() {
            return this.originatingUid;
        }

        public Uri getReferrerUri() {
            return this.referrerUri;
        }

        @SystemApi
        public String getResolvedBaseApkPath() {
            return this.resolvedBaseCodePath;
        }

        @SystemApi
        public String[] getGrantedRuntimePermissions() {
            return this.grantedRuntimePermissions;
        }

        @SystemApi
        public Set<String> getWhitelistedRestrictedPermissions() {
            if ((this.installFlags & 4194304) != 0) {
                return SessionParams.RESTRICTED_PERMISSIONS_ALL;
            }
            if (this.whitelistedRestrictedPermissions != null) {
                return new ArraySet(this.whitelistedRestrictedPermissions);
            }
            return Collections.emptySet();
        }

        @SystemApi
        public int getAutoRevokePermissionsMode() {
            return this.autoRevokePermissionsMode;
        }

        @SystemApi
        @Deprecated
        public boolean getAllowDowngrade() {
            return getRequestDowngrade();
        }

        @SystemApi
        public boolean getRequestDowngrade() {
            return (this.installFlags & 128) != 0;
        }

        public boolean getDontKillApp() {
            return (this.installFlags & 4096) != 0;
        }

        @SystemApi
        public boolean getInstallAsInstantApp(boolean isInstantApp) {
            return (this.installFlags & 2048) != 0;
        }

        @SystemApi
        public boolean getInstallAsFullApp(boolean isInstantApp) {
            return (this.installFlags & 16384) != 0;
        }

        @SystemApi
        public boolean getInstallAsVirtualPreload() {
            return (this.installFlags & 65536) != 0;
        }

        @SystemApi
        public boolean getEnableRollback() {
            return (this.installFlags & 262144) != 0;
        }

        @SystemApi
        public boolean getAllocateAggressive() {
            return (this.installFlags & 32768) != 0;
        }

        @Deprecated
        public Intent getDetailsIntent() {
            return createDetailsIntent();
        }

        public int getPackageSource() {
            return this.packageSource;
        }

        public boolean isMultiPackage() {
            return this.isMultiPackage;
        }

        public boolean isStaged() {
            return this.isStaged;
        }

        @SystemApi
        public int getRollbackDataPolicy() {
            return this.rollbackDataPolicy;
        }

        public boolean isForceQueryable() {
            return this.forceQueryable;
        }

        public boolean isStagedSessionActive() {
            return (!this.isStaged
                            || !this.isCommitted
                            || this.isSessionApplied
                            || this.isSessionFailed
                            || hasParentSessionId())
                    ? false
                    : true;
        }

        public int getParentSessionId() {
            return this.parentSessionId;
        }

        public boolean hasParentSessionId() {
            return this.parentSessionId != -1;
        }

        public int[] getChildSessionIds() {
            return this.childSessionIds;
        }

        private void checkSessionIsStaged() {
            if (!this.isStaged) {
                throw new IllegalStateException("Session is not marked as staged.");
            }
        }

        public boolean isStagedSessionApplied() {
            checkSessionIsStaged();
            return this.isSessionApplied;
        }

        public boolean isStagedSessionReady() {
            checkSessionIsStaged();
            return this.isSessionReady;
        }

        public boolean isStagedSessionFailed() {
            checkSessionIsStaged();
            return this.isSessionFailed;
        }

        public int getStagedSessionErrorCode() {
            checkSessionIsStaged();
            return this.mSessionErrorCode;
        }

        public String getStagedSessionErrorMessage() {
            checkSessionIsStaged();
            return this.mSessionErrorMessage;
        }

        public void setSessionErrorCode(int errorCode, String errorMessage) {
            this.mSessionErrorCode = errorCode;
            this.mSessionErrorMessage = errorMessage;
        }

        public boolean isCommitted() {
            return this.isCommitted;
        }

        public long getCreatedMillis() {
            return this.createdMillis;
        }

        public long getUpdatedMillis() {
            return this.updatedMillis;
        }

        public int getRequireUserAction() {
            return this.requireUserAction;
        }

        public int getInstallerUid() {
            return this.installerUid;
        }

        public boolean isApplicationEnabledSettingPersistent() {
            return this.applicationEnabledSettingPersistent;
        }

        public boolean isPreApprovalRequested() {
            return this.isPreapprovalRequested;
        }

        public boolean isRequestUpdateOwnership() {
            return (this.installFlags & 33554432) != 0;
        }

        @SystemApi
        public int getPendingUserActionReason() {
            return this.pendingUserActionReason;
        }

        public boolean isUnarchival() {
            return (this.installFlags & 1073741824) != 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.sessionId);
            parcel.writeInt(this.userId);
            parcel.writeString(this.installerPackageName);
            parcel.writeString(this.installerAttributionTag);
            parcel.writeString(this.resolvedBaseCodePath);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.sealed ? 1 : 0);
            parcel.writeInt(this.active ? 1 : 0);
            parcel.writeInt(this.mode);
            parcel.writeInt(this.installReason);
            parcel.writeInt(this.installScenario);
            parcel.writeLong(this.sizeBytes);
            parcel.writeString(this.appPackageName);
            parcel.writeParcelable(this.appIcon, i);
            parcel.writeString(this.appLabel != null ? this.appLabel.toString() : null);
            parcel.writeInt(this.installLocation);
            parcel.writeParcelable(this.originatingUri, i);
            parcel.writeInt(this.originatingUid);
            parcel.writeParcelable(this.referrerUri, i);
            parcel.writeStringArray(this.grantedRuntimePermissions);
            parcel.writeStringList(this.whitelistedRestrictedPermissions);
            parcel.writeInt(this.autoRevokePermissionsMode);
            parcel.writeInt(this.installFlags);
            parcel.writeBoolean(this.isMultiPackage);
            parcel.writeBoolean(this.isStaged);
            parcel.writeBoolean(this.forceQueryable);
            parcel.writeInt(this.parentSessionId);
            parcel.writeIntArray(this.childSessionIds);
            parcel.writeBoolean(this.isSessionApplied);
            parcel.writeBoolean(this.isSessionReady);
            parcel.writeBoolean(this.isSessionFailed);
            parcel.writeInt(this.mSessionErrorCode);
            parcel.writeString(this.mSessionErrorMessage);
            parcel.writeBoolean(this.isCommitted);
            parcel.writeBoolean(this.isPreapprovalRequested);
            parcel.writeInt(this.rollbackDataPolicy);
            parcel.writeLong(this.rollbackLifetimeMillis);
            parcel.writeInt(this.rollbackImpactLevel);
            parcel.writeLong(this.createdMillis);
            parcel.writeInt(this.requireUserAction);
            parcel.writeInt(this.installerUid);
            parcel.writeInt(this.packageSource);
            parcel.writeBoolean(this.applicationEnabledSettingPersistent);
            parcel.writeInt(this.pendingUserActionReason);
        }
    }

    public static final class PreapprovalDetails implements Parcelable {
        public static final Parcelable.Creator<PreapprovalDetails> CREATOR =
                new Parcelable.Creator<PreapprovalDetails>() { // from class:
                    // android.content.pm.PackageInstaller.PreapprovalDetails.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public PreapprovalDetails[] newArray(int size) {
                        return new PreapprovalDetails[size];
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public PreapprovalDetails createFromParcel(Parcel in) {
                        return new PreapprovalDetails(in);
                    }
                };
        private final Bitmap mIcon;
        private final CharSequence mLabel;
        private final ULocale mLocale;
        private final String mPackageName;

        public PreapprovalDetails(
                Bitmap icon, CharSequence label, ULocale locale, String packageName) {
            this.mIcon = icon;
            this.mLabel = label;
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(this.mLabel), "App label cannot be empty.");
            this.mLocale = locale;
            Preconditions.checkArgument(!Objects.isNull(this.mLocale), "Locale cannot be null.");
            this.mPackageName = packageName;
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(this.mPackageName), "Package name cannot be empty.");
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            byte flg = this.mIcon != null ? (byte) (0 | 1) : (byte) 0;
            dest.writeByte(flg);
            if (this.mIcon != null) {
                this.mIcon.writeToParcel(dest, flags);
            }
            dest.writeCharSequence(this.mLabel);
            dest.writeString8(this.mLocale.toString());
            dest.writeString8(this.mPackageName);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        PreapprovalDetails(Parcel in) {
            byte flg = in.readByte();
            Bitmap icon = (flg & 1) == 0 ? null : Bitmap.CREATOR.createFromParcel(in);
            CharSequence label = in.readCharSequence();
            ULocale locale = new ULocale(in.readString8());
            String packageName = in.readString8();
            this.mIcon = icon;
            this.mLabel = label;
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(this.mLabel), "App label cannot be empty.");
            this.mLocale = locale;
            Preconditions.checkArgument(!Objects.isNull(this.mLocale), "Locale cannot be null.");
            this.mPackageName = packageName;
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(this.mPackageName), "Package name cannot be empty.");
        }

        public static final class Builder {
            private long mBuilderFieldsSet = 0;
            private Bitmap mIcon;
            private CharSequence mLabel;
            private ULocale mLocale;
            private String mPackageName;

            public Builder setIcon(Bitmap value) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 1;
                this.mIcon = value;
                return this;
            }

            public Builder setLabel(CharSequence value) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 2;
                this.mLabel = value;
                return this;
            }

            public Builder setLocale(ULocale value) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 4;
                this.mLocale = value;
                return this;
            }

            public Builder setPackageName(String value) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 8;
                this.mPackageName = value;
                return this;
            }

            public PreapprovalDetails build() {
                checkNotUsed();
                this.mBuilderFieldsSet |= 16;
                PreapprovalDetails o =
                        new PreapprovalDetails(
                                this.mIcon, this.mLabel, this.mLocale, this.mPackageName);
                return o;
            }

            private void checkNotUsed() {
                if ((this.mBuilderFieldsSet & 16) != 0) {
                    throw new IllegalStateException(
                            "This Builder should not be reused. Use a new Builder instance"
                                    + " instead");
                }
            }
        }

        public Bitmap getIcon() {
            return this.mIcon;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }

        public ULocale getLocale() {
            return this.mLocale;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String toString() {
            return "PreapprovalDetails { icon = "
                    + this.mIcon
                    + ", label = "
                    + ((Object) this.mLabel)
                    + ", locale = "
                    + this.mLocale
                    + ", packageName = "
                    + this.mPackageName
                    + " }";
        }

        @Deprecated
        private void __metadata() {}
    }

    public static final class InstallConstraintsResult implements Parcelable {
        public static final Parcelable.Creator<InstallConstraintsResult> CREATOR =
                new Parcelable.Creator<InstallConstraintsResult>() { // from class:
                    // android.content.pm.PackageInstaller.InstallConstraintsResult.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public InstallConstraintsResult[] newArray(int size) {
                        return new InstallConstraintsResult[size];
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public InstallConstraintsResult createFromParcel(Parcel in) {
                        return new InstallConstraintsResult(in);
                    }
                };
        private boolean mAllConstraintsSatisfied;

        public boolean areAllConstraintsSatisfied() {
            return this.mAllConstraintsSatisfied;
        }

        public InstallConstraintsResult(boolean allConstraintsSatisfied) {
            this.mAllConstraintsSatisfied = allConstraintsSatisfied;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            byte flg = this.mAllConstraintsSatisfied ? (byte) (0 | 1) : (byte) 0;
            dest.writeByte(flg);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        InstallConstraintsResult(Parcel in) {
            byte flg = in.readByte();
            boolean allConstraintsSatisfied = (flg & 1) != 0;
            this.mAllConstraintsSatisfied = allConstraintsSatisfied;
        }

        @Deprecated
        private void __metadata() {}
    }

    public static final class InstallConstraints implements Parcelable {
        private final boolean mAppNotForegroundRequired;
        private final boolean mAppNotInteractingRequired;
        private final boolean mAppNotTopVisibleRequired;
        private final boolean mDeviceIdleRequired;
        private final boolean mNotInCallRequired;
        public static final InstallConstraints GENTLE_UPDATE =
                new Builder().setAppNotInteractingRequired().build();
        public static final Parcelable.Creator<InstallConstraints> CREATOR =
                new Parcelable.Creator<InstallConstraints>() { // from class:
                    // android.content.pm.PackageInstaller.InstallConstraints.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public InstallConstraints[] newArray(int size) {
                        return new InstallConstraints[size];
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public InstallConstraints createFromParcel(Parcel in) {
                        return new InstallConstraints(in);
                    }
                };

        public static final class Builder {
            private boolean mAppNotForegroundRequired;
            private boolean mAppNotInteractingRequired;
            private boolean mAppNotTopVisibleRequired;
            private boolean mDeviceIdleRequired;
            private boolean mNotInCallRequired;

            public Builder setDeviceIdleRequired() {
                this.mDeviceIdleRequired = true;
                return this;
            }

            public Builder setAppNotForegroundRequired() {
                this.mAppNotForegroundRequired = true;
                return this;
            }

            public Builder setAppNotInteractingRequired() {
                this.mAppNotInteractingRequired = true;
                return this;
            }

            public Builder setAppNotTopVisibleRequired() {
                this.mAppNotTopVisibleRequired = true;
                return this;
            }

            public Builder setNotInCallRequired() {
                this.mNotInCallRequired = true;
                return this;
            }

            public InstallConstraints build() {
                return new InstallConstraints(
                        this.mDeviceIdleRequired,
                        this.mAppNotForegroundRequired,
                        this.mAppNotInteractingRequired,
                        this.mAppNotTopVisibleRequired,
                        this.mNotInCallRequired);
            }
        }

        public InstallConstraints(
                boolean deviceIdleRequired,
                boolean appNotForegroundRequired,
                boolean appNotInteractingRequired,
                boolean appNotTopVisibleRequired,
                boolean notInCallRequired) {
            this.mDeviceIdleRequired = deviceIdleRequired;
            this.mAppNotForegroundRequired = appNotForegroundRequired;
            this.mAppNotInteractingRequired = appNotInteractingRequired;
            this.mAppNotTopVisibleRequired = appNotTopVisibleRequired;
            this.mNotInCallRequired = notInCallRequired;
        }

        public boolean isDeviceIdleRequired() {
            return this.mDeviceIdleRequired;
        }

        public boolean isAppNotForegroundRequired() {
            return this.mAppNotForegroundRequired;
        }

        public boolean isAppNotInteractingRequired() {
            return this.mAppNotInteractingRequired;
        }

        public boolean isAppNotTopVisibleRequired() {
            return this.mAppNotTopVisibleRequired;
        }

        public boolean isNotInCallRequired() {
            return this.mNotInCallRequired;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InstallConstraints that = (InstallConstraints) o;
            if (this.mDeviceIdleRequired == that.mDeviceIdleRequired
                    && this.mAppNotForegroundRequired == that.mAppNotForegroundRequired
                    && this.mAppNotInteractingRequired == that.mAppNotInteractingRequired
                    && this.mAppNotTopVisibleRequired == that.mAppNotTopVisibleRequired
                    && this.mNotInCallRequired == that.mNotInCallRequired) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int _hash = (1 * 31) + Boolean.hashCode(this.mDeviceIdleRequired);
            return (((((((_hash * 31) + Boolean.hashCode(this.mAppNotForegroundRequired)) * 31)
                                                    + Boolean.hashCode(
                                                            this.mAppNotInteractingRequired))
                                            * 31)
                                    + Boolean.hashCode(this.mAppNotTopVisibleRequired))
                            * 31)
                    + Boolean.hashCode(this.mNotInCallRequired);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            byte flg = this.mDeviceIdleRequired ? (byte) (0 | 1) : (byte) 0;
            if (this.mAppNotForegroundRequired) {
                flg = (byte) (flg | 2);
            }
            if (this.mAppNotInteractingRequired) {
                flg = (byte) (flg | 4);
            }
            if (this.mAppNotTopVisibleRequired) {
                flg = (byte) (flg | 8);
            }
            if (this.mNotInCallRequired) {
                flg = (byte) (flg | 16);
            }
            dest.writeByte(flg);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        InstallConstraints(Parcel in) {
            byte flg = in.readByte();
            boolean deviceIdleRequired = (flg & 1) != 0;
            boolean appNotForegroundRequired = (flg & 2) != 0;
            boolean appNotInteractingRequired = (flg & 4) != 0;
            boolean appNotTopVisibleRequired = (flg & 8) != 0;
            boolean notInCallRequired = (flg & 16) != 0;
            this.mDeviceIdleRequired = deviceIdleRequired;
            this.mAppNotForegroundRequired = appNotForegroundRequired;
            this.mAppNotInteractingRequired = appNotInteractingRequired;
            this.mAppNotTopVisibleRequired = appNotTopVisibleRequired;
            this.mNotInCallRequired = notInCallRequired;
        }

        @Deprecated
        private void __metadata() {}
    }

    public static final class UnarchivalState {
        private final long mRequiredStorageBytes;
        private final int mStatus;
        private final int mUnarchiveId;
        private final PendingIntent mUserActionIntent;

        public static UnarchivalState createOkState(int unarchiveId) {
            return new UnarchivalState(unarchiveId, 0, -1L, null);
        }

        public static UnarchivalState createUserActionRequiredState(
                int unarchiveId, PendingIntent userActionIntent) {
            Objects.requireNonNull(userActionIntent);
            return new UnarchivalState(unarchiveId, 1, -1L, userActionIntent);
        }

        public static UnarchivalState createInsufficientStorageState(
                int unarchiveId, long requiredStorageBytes, PendingIntent userActionIntent) {
            return new UnarchivalState(unarchiveId, 2, requiredStorageBytes, userActionIntent);
        }

        public static UnarchivalState createNoConnectivityState(int unarchiveId) {
            return new UnarchivalState(unarchiveId, 3, -1L, null);
        }

        public static UnarchivalState createGenericErrorState(int unarchiveId) {
            return new UnarchivalState(unarchiveId, 100, -1L, null);
        }

        private UnarchivalState(
                int unarchiveId,
                int status,
                long requiredStorageBytes,
                PendingIntent userActionIntent) {
            this.mUnarchiveId = unarchiveId;
            this.mStatus = status;
            AnnotationValidations.validate(
                    (Class<? extends Annotation>) UnarchivalStatus.class,
                    (Annotation) null,
                    this.mStatus);
            this.mRequiredStorageBytes = requiredStorageBytes;
            this.mUserActionIntent = userActionIntent;
        }

        int getUnarchiveId() {
            return this.mUnarchiveId;
        }

        int getStatus() {
            return this.mStatus;
        }

        long getRequiredStorageBytes() {
            return this.mRequiredStorageBytes;
        }

        PendingIntent getUserActionIntent() {
            return this.mUserActionIntent;
        }
    }
}
