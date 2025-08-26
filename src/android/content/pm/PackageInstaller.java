package android.content.pm;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
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
import com.android.internal.protolog.PerfettoProtoLogImpl;
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

/* loaded from: classes.dex */
public class PackageInstaller {

    @SystemApi
    public static final String ACTION_CONFIRM_INSTALL = "android.content.pm.action.CONFIRM_INSTALL";

    @SystemApi
    public static final String ACTION_CONFIRM_PRE_APPROVAL = "android.content.pm.action.CONFIRM_PRE_APPROVAL";

    @SystemApi
    public static final String ACTION_INSTALL_DEPENDENCY = "android.content.pm.action.INSTALL_DEPENDENCY";
    public static final String ACTION_SESSION_COMMITTED = "android.content.pm.action.SESSION_COMMITTED";
    public static final String ACTION_SESSION_DETAILS = "android.content.pm.action.SESSION_DETAILS";
    public static final String ACTION_SESSION_UPDATED = "android.content.pm.action.SESSION_UPDATED";
    private static final String ACTION_WAIT_INSTALL_CONSTRAINTS = "android.content.pm.action.WAIT_INSTALL_CONSTRAINTS";

    @SystemApi
    public static final int DATA_LOADER_TYPE_INCREMENTAL = 2;

    @SystemApi
    public static final int DATA_LOADER_TYPE_NONE = 0;

    @SystemApi
    public static final int DATA_LOADER_TYPE_STREAMING = 1;
    private static final int DEFAULT_CHECKSUMS = 127;
    public static final boolean ENABLE_REVOCABLE_FD = SystemProperties.getBoolean("fw.revocable_fd", false);

    @SystemApi
    public static final String EXTRA_CALLBACK = "android.content.pm.extra.CALLBACK";

    @SystemApi
    public static final String EXTRA_DATA_LOADER_TYPE = "android.content.pm.extra.DATA_LOADER_TYPE";

    @SystemApi
    public static final String EXTRA_DELETE_FLAGS = "android.content.pm.extra.DELETE_FLAGS";
    public static final String EXTRA_INSTALL_CONSTRAINTS = "android.content.pm.extra.INSTALL_CONSTRAINTS";
    public static final String EXTRA_INSTALL_CONSTRAINTS_RESULT = "android.content.pm.extra.INSTALL_CONSTRAINTS_RESULT";
    public static final String EXTRA_LEGACY_BUNDLE = "android.content.pm.extra.LEGACY_BUNDLE";

    @SystemApi
    public static final String EXTRA_LEGACY_STATUS = "android.content.pm.extra.LEGACY_STATUS";
    public static final String EXTRA_OTHER_PACKAGE_NAME = "android.content.pm.extra.OTHER_PACKAGE_NAME";
    public static final String EXTRA_PACKAGE_NAME = "android.content.pm.extra.PACKAGE_NAME";

    @Deprecated
    public static final String EXTRA_PACKAGE_NAMES = "android.content.pm.extra.PACKAGE_NAMES";
    public static final String EXTRA_PRE_APPROVAL = "android.content.pm.extra.PRE_APPROVAL";

    @SystemApi
    @Deprecated
    public static final String EXTRA_RESOLVED_BASE_PATH = "android.content.pm.extra.RESOLVED_BASE_PATH";
    public static final String EXTRA_SESSION = "android.content.pm.extra.SESSION";
    public static final String EXTRA_SESSION_ID = "android.content.pm.extra.SESSION_ID";
    public static final String EXTRA_STATUS = "android.content.pm.extra.STATUS";
    public static final String EXTRA_STATUS_MESSAGE = "android.content.pm.extra.STATUS_MESSAGE";
    public static final String EXTRA_STORAGE_PATH = "android.content.pm.extra.STORAGE_PATH";
    public static final String EXTRA_UNARCHIVE_ALL_USERS = "android.content.pm.extra.UNARCHIVE_ALL_USERS";
    public static final String EXTRA_UNARCHIVE_ID = "android.content.pm.extra.UNARCHIVE_ID";
    public static final String EXTRA_UNARCHIVE_PACKAGE_NAME = "android.content.pm.extra.UNARCHIVE_PACKAGE_NAME";
    public static final String EXTRA_UNARCHIVE_STATUS = "android.content.pm.extra.UNARCHIVE_STATUS";
    public static final String EXTRA_WARNINGS = "android.content.pm.extra.WARNINGS";

    @SystemApi
    public static final int LOCATION_DATA_APP = 0;

    @SystemApi
    public static final int LOCATION_MEDIA_DATA = 2;

    @SystemApi
    public static final int LOCATION_MEDIA_OBB = 1;
    public static final int PACKAGE_SOURCE_DOWNLOADED_FILE = 4;
    public static final int PACKAGE_SOURCE_LOCAL_FILE = 3;
    public static final int PACKAGE_SOURCE_OTHER = 1;
    public static final int PACKAGE_SOURCE_STORE = 2;
    public static final int PACKAGE_SOURCE_UNSPECIFIED = 0;

    @SystemApi
    public static final int REASON_CONFIRM_PACKAGE_CHANGE = 0;

    @SystemApi
    public static final int REASON_OWNERSHIP_CHANGED = 1;

    @SystemApi
    public static final int REASON_REMIND_OWNERSHIP = 2;
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
    public @interface FileLocation {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface PackageSourceType {
    }

    public static abstract class SessionCallback {
        public abstract void onActiveChanged(int i, boolean z);

        public abstract void onBadgingChanged(int i);

        public abstract void onCreated(int i);

        public abstract void onFinished(int i, boolean z);

        public abstract void onProgressChanged(int i, float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UnarchivalStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserActionReason {
    }

    public PackageInstaller(IPackageInstaller iPackageInstaller, String str, String str2, int i) {
        Objects.requireNonNull(iPackageInstaller, "installer cannot be null");
        this.mInstaller = iPackageInstaller;
        this.mInstallerPackageName = str;
        this.mAttributionTag = str2;
        this.mUserId = i;
    }

    public int createSession(SessionParams sessionParams) throws Throwable {
        try {
            return this.mInstaller.createSession(sessionParams, this.mInstallerPackageName, this.mAttributionTag, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public Session openSession(int i) throws Throwable {
        try {
            try {
                return new Session(this.mInstaller.openSession(i));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public ParcelFileDescriptor requestCopy(String str, long j) throws Throwable {
        try {
            return this.mInstaller.requestCopy(str, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public Session openSessionQuick(int i, String str) throws Throwable {
        try {
            try {
                return new Session(this.mInstaller.openSessionQuick(i, str));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RuntimeException e2) {
            ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public void updateSessionAppIcon(int i, Bitmap bitmap) {
        try {
            this.mInstaller.updateSessionAppIcon(i, bitmap);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateSessionAppLabel(int i, CharSequence charSequence) {
        String string;
        if (charSequence != null) {
            try {
                string = charSequence.toString();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            string = null;
        }
        this.mInstaller.updateSessionAppLabel(i, string);
    }

    public void abandonSession(int i) {
        try {
            this.mInstaller.abandonSession(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SessionInfo getSessionInfo(int i) {
        try {
            return this.mInstaller.getSessionInfo(i);
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
            return this.mInstaller.getMySessions(this.mInstallerPackageName, this.mUserId).getList();
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
        List<SessionInfo> activeStagedSessions = getActiveStagedSessions();
        if (activeStagedSessions.isEmpty()) {
            return null;
        }
        return activeStagedSessions.get(0);
    }

    public List<SessionInfo> getActiveStagedSessions() {
        ArrayList arrayList = new ArrayList();
        List<SessionInfo> stagedSessions = getStagedSessions();
        for (int i = 0; i < stagedSessions.size(); i++) {
            SessionInfo sessionInfo = stagedSessions.get(i);
            if (sessionInfo.isStagedSessionActive()) {
                arrayList.add(sessionInfo);
            }
        }
        return arrayList;
    }

    public void setUnknownSourceConfirmResult(int i, boolean z) {
        try {
            this.mInstaller.setUnknownSourceConfirmResult(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstall(String str, IntentSender intentSender) {
        uninstall(str, 0, intentSender);
    }

    public void uninstall(String str, int i, IntentSender intentSender) {
        uninstall(new VersionedPackage(str, -1), i, intentSender);
    }

    public void uninstall(VersionedPackage versionedPackage, IntentSender intentSender) {
        uninstall(versionedPackage, 0, intentSender);
    }

    public void uninstall(VersionedPackage versionedPackage, int i, IntentSender intentSender) {
        Objects.requireNonNull(versionedPackage, "versionedPackage cannot be null");
        try {
            this.mInstaller.uninstall(versionedPackage, this.mInstallerPackageName, i, intentSender, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installExistingPackage(String str, int i, IntentSender intentSender) {
        Objects.requireNonNull(str, "packageName cannot be null");
        try {
            this.mInstaller.installExistingPackage(str, 4194304, i, intentSender, this.mUserId, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstallExistingPackage(String str, IntentSender intentSender) {
        Objects.requireNonNull(str, "packageName cannot be null");
        try {
            this.mInstaller.uninstallExistingPackage(new VersionedPackage(str, -1), this.mInstallerPackageName, intentSender, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installPackageArchived(ArchivedPackageInfo archivedPackageInfo, SessionParams sessionParams, IntentSender intentSender) {
        Objects.requireNonNull(archivedPackageInfo, "archivedPackageInfo cannot be null");
        Objects.requireNonNull(sessionParams, "sessionParams cannot be null");
        Objects.requireNonNull(intentSender, "statusReceiver cannot be null");
        try {
            this.mInstaller.installPackageArchived(archivedPackageInfo.getParcel(), sessionParams, intentSender, this.mInstallerPackageName, new UserHandle(this.mUserId));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setPermissionsResult(int i, boolean z) {
        try {
            this.mInstaller.setPermissionsResult(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void checkInstallConstraints(List<String> list, InstallConstraints installConstraints, final Executor executor, final Consumer<InstallConstraintsResult> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            this.mInstaller.checkInstallConstraints(this.mInstallerPackageName, list, installConstraints, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.content.pm.PackageInstaller$$ExternalSyntheticLambda1
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    executor.execute(new Runnable() { // from class: android.content.pm.PackageInstaller$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept((PackageInstaller.InstallConstraintsResult) bundle.getParcelable("result", PackageInstaller.InstallConstraintsResult.class));
                        }
                    });
                }
            }));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForInstallConstraints(List<String> list, InstallConstraints installConstraints, IntentSender intentSender, long j) {
        try {
            this.mInstaller.waitForInstallConstraints(this.mInstallerPackageName, list, installConstraints, intentSender, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commitSessionAfterInstallConstraintsAreMet(int i, IntentSender intentSender, InstallConstraints installConstraints, long j) {
        try {
            IPackageInstallerSession iPackageInstallerSessionOpenSession = this.mInstaller.openSession(i);
            iPackageInstallerSessionOpenSession.seal();
            waitForInstallConstraints(iPackageInstallerSessionOpenSession.fetchPackageNames(), installConstraints, new LocalIntentSender(ActivityThread.currentApplication(), i, iPackageInstallerSessionOpenSession, intentSender).getIntentSender(), j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class LocalIntentSender extends BroadcastReceiver {
        private final Context mContext;
        private final IPackageInstallerSession mSession;
        private final int mSessionId;
        private final IntentSender mStatusReceiver;

        LocalIntentSender(Context context, int i, IPackageInstallerSession iPackageInstallerSession, IntentSender intentSender) {
            this.mContext = context;
            this.mSessionId = i;
            this.mSession = iPackageInstallerSession;
            this.mStatusReceiver = intentSender;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public IntentSender getIntentSender() {
            Intent intent = new Intent(PackageInstaller.ACTION_WAIT_INSTALL_CONSTRAINTS).setPackage(this.mContext.getPackageName());
            this.mContext.registerReceiver(this, new IntentFilter(PackageInstaller.ACTION_WAIT_INSTALL_CONSTRAINTS), 2);
            return PendingIntent.getBroadcast(this.mContext, 0, intent, 33554432).getIntentSender();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                if (((InstallConstraintsResult) intent.getParcelableExtra(PackageInstaller.EXTRA_INSTALL_CONSTRAINTS_RESULT, InstallConstraintsResult.class)).areAllConstraintsSatisfied()) {
                    this.mSession.commit(this.mStatusReceiver, false);
                } else {
                    Intent intent2 = new Intent();
                    intent2.putExtra(PackageInstaller.EXTRA_SESSION_ID, this.mSessionId);
                    intent2.putExtra(PackageInstaller.EXTRA_STATUS, 8);
                    intent2.putExtra(PackageInstaller.EXTRA_STATUS_MESSAGE, "Install constraints not satisfied within timeout");
                    this.mStatusReceiver.sendIntent(ActivityThread.currentApplication(), 0, intent2, null, null);
                }
            } catch (Exception unused) {
            } finally {
                unregisterReceiver();
            }
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

        SessionCallbackDelegate(SessionCallback sessionCallback, Executor executor) {
            this.mCallback = sessionCallback;
            this.mExecutor = executor;
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionCreated(int i) {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((PackageInstaller.SessionCallback) obj).onCreated(((Integer) obj2).intValue());
                }
            }, this.mCallback, Integer.valueOf(i)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionBadgingChanged(int i) {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((PackageInstaller.SessionCallback) obj).onBadgingChanged(((Integer) obj2).intValue());
                }
            }, this.mCallback, Integer.valueOf(i)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionActiveChanged(int i, boolean z) {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((PackageInstaller.SessionCallback) obj).onActiveChanged(((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
                }
            }, this.mCallback, Integer.valueOf(i), Boolean.valueOf(z)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionProgressChanged(int i, float f) {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((PackageInstaller.SessionCallback) obj).onProgressChanged(((Integer) obj2).intValue(), ((Float) obj3).floatValue());
                }
            }, this.mCallback, Integer.valueOf(i), Float.valueOf(f)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionFinished(int i, boolean z) {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((PackageInstaller.SessionCallback) obj).onFinished(((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
                }
            }, this.mCallback, Integer.valueOf(i), Boolean.valueOf(z)).recycleOnUse());
        }
    }

    @Deprecated
    public void addSessionCallback(SessionCallback sessionCallback) {
        registerSessionCallback(sessionCallback);
    }

    public void registerSessionCallback(SessionCallback sessionCallback) {
        registerSessionCallback(sessionCallback, new Handler());
    }

    @Deprecated
    public void addSessionCallback(SessionCallback sessionCallback, Handler handler) {
        registerSessionCallback(sessionCallback, handler);
    }

    public void registerSessionCallback(SessionCallback sessionCallback, Handler handler) {
        synchronized (this.mDelegates) {
            SessionCallbackDelegate sessionCallbackDelegate = new SessionCallbackDelegate(sessionCallback, new HandlerExecutor(handler));
            try {
                this.mInstaller.registerCallback(sessionCallbackDelegate, this.mUserId);
                this.mDelegates.add(sessionCallbackDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void removeSessionCallback(SessionCallback sessionCallback) {
        unregisterSessionCallback(sessionCallback);
    }

    public void unregisterSessionCallback(SessionCallback sessionCallback) {
        synchronized (this.mDelegates) {
            Iterator<SessionCallbackDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                SessionCallbackDelegate next = it.next();
                if (next.mCallback == sessionCallback) {
                    try {
                        this.mInstaller.unregisterCallback(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public static class Session implements Closeable {
        protected final IPackageInstallerSession mSession;

        public Session(IPackageInstallerSession iPackageInstallerSession) {
            this.mSession = iPackageInstallerSession;
        }

        @Deprecated
        public void setProgress(float f) {
            setStagingProgress(f);
        }

        public void setStagingProgress(float f) {
            try {
                this.mSession.setClientProgress(f);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void addProgress(float f) {
            try {
                this.mSession.addClientProgress(f);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public OutputStream openWrite(String str, long j, long j2) throws Throwable {
            try {
                if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                    return new ParcelFileDescriptor.AutoCloseOutputStream(this.mSession.openWrite(str, j, j2));
                }
                return new FileBridge.FileBridgeOutputStream(this.mSession.openWrite(str, j, j2));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void write(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws Throwable {
            try {
                this.mSession.write(str, j, j2, parcelFileDescriptor);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void stageViaHardLink(String str) throws Throwable {
            try {
                this.mSession.stageViaHardLink(str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void fsync(OutputStream outputStream) throws IOException, ErrnoException {
            if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                if (outputStream instanceof ParcelFileDescriptor.AutoCloseOutputStream) {
                    try {
                        Os.fsync(((ParcelFileDescriptor.AutoCloseOutputStream) outputStream).getFD());
                        return;
                    } catch (ErrnoException e) {
                        throw e.rethrowAsIOException();
                    }
                }
                throw new IllegalArgumentException("Unrecognized stream");
            }
            if (outputStream instanceof FileBridge.FileBridgeOutputStream) {
                ((FileBridge.FileBridgeOutputStream) outputStream).fsync();
                return;
            }
            throw new IllegalArgumentException("Unrecognized stream");
        }

        public String[] getNames() throws Throwable {
            try {
                return this.mSession.getNames();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public InputStream openRead(String str) throws Throwable {
            try {
                return new ParcelFileDescriptor.AutoCloseInputStream(this.mSession.openRead(str));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void removeSplit(String str) throws Throwable {
            try {
                this.mSession.removeSplit(str);
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
                DataLoaderParamsParcel dataLoaderParams = this.mSession.getDataLoaderParams();
                if (dataLoaderParams == null) {
                    return null;
                }
                return new DataLoaderParams(dataLoaderParams);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void addFile(int i, String str, long j, byte[] bArr, byte[] bArr2) {
            try {
                this.mSession.addFile(i, str, j, bArr, bArr2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void removeFile(int i, String str) {
            try {
                this.mSession.removeFile(i, str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Deprecated
        public void setChecksums(String str, List<Checksum> list, byte[] bArr) throws Throwable {
            Objects.requireNonNull(str);
            Objects.requireNonNull(list);
            try {
                this.mSession.setChecksums(str, (Checksum[]) list.toArray(new Checksum[list.size()]), bArr);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        private static List<byte[]> encodeCertificates(List<Certificate> list) throws CertificateEncodingException {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList(list.size());
            for (Certificate certificate : list) {
                if (!(certificate instanceof X509Certificate)) {
                    throw new CertificateEncodingException("Only X509 certificates supported.");
                }
                arrayList.add(certificate.getEncoded());
            }
            return arrayList;
        }

        public void requestChecksums(String str, int i, List<Certificate> list, Executor executor, PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) throws Throwable {
            Objects.requireNonNull(str);
            Objects.requireNonNull(list);
            Objects.requireNonNull(executor);
            Objects.requireNonNull(onChecksumsReadyListener);
            if (list == PackageManager.TRUST_ALL) {
                list = null;
            } else if (list == PackageManager.TRUST_NONE) {
                list = Collections.EMPTY_LIST;
            } else if (list.isEmpty()) {
                throw new IllegalArgumentException("trustedInstallers has to be one of TRUST_ALL/TRUST_NONE or a non-empty list of certificates.");
            }
            try {
                this.mSession.requestChecksums(str, 127, i, encodeCertificates(list), new AnonymousClass1(this, executor, onChecksumsReadyListener));
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
            final /* synthetic */ PackageManager.OnChecksumsReadyListener val$onChecksumsReadyListener;

            AnonymousClass1(Session session, Executor executor, PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) {
                this.val$executor = executor;
                this.val$onChecksumsReadyListener = onChecksumsReadyListener;
            }

            @Override // android.content.pm.IOnChecksumsReadyListener
            public void onChecksumsReady(final List<ApkChecksum> list) throws RemoteException {
                Executor executor = this.val$executor;
                final PackageManager.OnChecksumsReadyListener onChecksumsReadyListener = this.val$onChecksumsReadyListener;
                executor.execute(new Runnable() { // from class: android.content.pm.PackageInstaller$Session$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        onChecksumsReadyListener.onChecksumsReady(list);
                    }
                });
            }
        }

        public void commit(IntentSender intentSender) {
            try {
                this.mSession.commit(intentSender, false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void commitTransferred(IntentSender intentSender) {
            try {
                this.mSession.commit(intentSender, true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void transfer(String str) throws Throwable {
            Preconditions.checkArgument(!TextUtils.isEmpty(str));
            try {
                this.mSession.transfer(str);
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

        public void addChildSessionId(int i) {
            try {
                this.mSession.addChildSessionId(i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public void removeChildSessionId(int i) {
            try {
                this.mSession.removeChildSessionId(i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public PersistableBundle getAppMetadata() throws IOException {
            PersistableBundle fromStream = null;
            try {
                ParcelFileDescriptor appMetadataFd = this.mSession.getAppMetadataFd();
                if (appMetadataFd != null) {
                    ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(appMetadataFd);
                    try {
                        fromStream = PersistableBundle.readFromStream(autoCloseInputStream);
                        autoCloseInputStream.close();
                    } finally {
                    }
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            return fromStream != null ? fromStream : new PersistableBundle();
        }

        private OutputStream openWriteAppMetadata() throws Throwable {
            try {
                if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                    return new ParcelFileDescriptor.AutoCloseOutputStream(this.mSession.openWriteAppMetadata());
                }
                return new FileBridge.FileBridgeOutputStream(this.mSession.openWriteAppMetadata());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (RuntimeException e2) {
                ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void setAppMetadata(PersistableBundle persistableBundle) throws Throwable {
            if (persistableBundle == null || persistableBundle.isEmpty()) {
                try {
                    this.mSession.removeAppMetadata();
                    return;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            Objects.requireNonNull(persistableBundle);
            OutputStream outputStreamOpenWriteAppMetadata = openWriteAppMetadata();
            try {
                persistableBundle.writeToStream(outputStreamOpenWriteAppMetadata);
                if (outputStreamOpenWriteAppMetadata != null) {
                    outputStreamOpenWriteAppMetadata.close();
                }
            } catch (Throwable th) {
                if (outputStreamOpenWriteAppMetadata != null) {
                    try {
                        outputStreamOpenWriteAppMetadata.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void requestUserPreapproval(PreapprovalDetails preapprovalDetails, IntentSender intentSender) {
            Preconditions.checkArgument(preapprovalDetails != null, "preapprovalDetails cannot be null.");
            Preconditions.checkArgument(intentSender != null, "statusReceiver cannot be null.");
            try {
                this.mSession.requestUserPreapproval(preapprovalDetails, intentSender);
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
        public void setPreVerifiedDomains(Set<String> set) {
            Preconditions.checkArgument((set == null || set.isEmpty()) ? false : true, "Provided pre-verified domains cannot be null or empty.");
            try {
                this.mSession.setPreVerifiedDomains(new DomainSet(set));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public Set<String> getPreVerifiedDomains() {
            try {
                DomainSet preVerifiedDomains = this.mSession.getPreVerifiedDomains();
                return preVerifiedDomains != null ? preVerifiedDomains.getDomains() : Collections.EMPTY_SET;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public InstallInfo readInstallInfo(File file, int i) throws PackageParsingException {
        ParseResult<PackageLite> packageLite = ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing().reset(), file, i);
        if (packageLite.isError()) {
            throw new PackageParsingException(packageLite.getErrorCode(), packageLite.getErrorMessage());
        }
        return new InstallInfo(packageLite);
    }

    @SystemApi
    public InstallInfo readInstallInfo(ParcelFileDescriptor parcelFileDescriptor, String str, int i) throws PackageParsingException {
        ParseResult<PackageLite> monolithicPackageLite = ApkLiteParseUtils.parseMonolithicPackageLite(ParseTypeImpl.forDefaultParsing(), parcelFileDescriptor.getFileDescriptor(), str, i);
        if (monolithicPackageLite.isError()) {
            throw new PackageParsingException(monolithicPackageLite.getErrorCode(), monolithicPackageLite.getErrorMessage());
        }
        return new InstallInfo(monolithicPackageLite);
    }

    public void requestArchive(String str, IntentSender intentSender) throws Throwable {
        try {
            this.mInstaller.requestArchive(str, this.mInstallerPackageName, 0, intentSender, new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void requestUnarchive(String str, IntentSender intentSender) throws Throwable {
        try {
            this.mInstaller.requestUnarchive(str, this.mInstallerPackageName, intentSender, new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void reportUnarchivalStatus(int i, int i2, long j, PendingIntent pendingIntent) throws Throwable {
        try {
            this.mInstaller.reportUnarchivalStatus(i, i2, j, pendingIntent, new UserHandle(this.mUserId));
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void reportUnarchivalState(UnarchivalState unarchivalState) throws Throwable {
        Objects.requireNonNull(unarchivalState);
        try {
            this.mInstaller.reportUnarchivalStatus(unarchivalState.getUnarchiveId(), unarchivalState.getStatus(), unarchivalState.getRequiredStorageBytes(), unarchivalState.getUserActionIntent(), new UserHandle(this.mUserId));
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
        public @interface InstallLocation {
        }

        InstallInfo(ParseResult<PackageLite> parseResult) {
            this.mPkg = parseResult.getResult();
        }

        public String getPackageName() {
            return this.mPkg.getPackageName();
        }

        public int getInstallLocation() {
            return this.mPkg.getInstallLocation();
        }

        public long calculateInstalledSize(SessionParams sessionParams) throws IOException {
            return InstallLocationUtils.calculateInstalledSize(this.mPkg, sessionParams.abiOverride);
        }

        public long calculateInstalledSize(SessionParams sessionParams, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
            return InstallLocationUtils.calculateInstalledSize(this.mPkg, sessionParams.abiOverride, parcelFileDescriptor.getFileDescriptor());
        }
    }

    @SystemApi
    public static class PackageParsingException extends Exception {
        private final int mErrorCode;

        public PackageParsingException(int i, String str) {
            super(str);
            this.mErrorCode = i;
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
        public boolean isAutoInstallDependenciesEnabled;
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
        public static final Parcelable.Creator<SessionParams> CREATOR = new Parcelable.Creator<SessionParams>() { // from class: android.content.pm.PackageInstaller.SessionParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionParams createFromParcel(Parcel parcel) {
                return new SessionParams(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionParams[] newArray(int i) {
                return new SessionParams[i];
            }
        };

        @Retention(RetentionPolicy.SOURCE)
        public @interface PermissionState {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface UserActionRequirement {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SessionParams(int i) {
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
            this.isAutoInstallDependenciesEnabled = true;
            this.mode = i;
            this.mPermissionStates = new ArrayMap<>();
        }

        public SessionParams(Parcel parcel) throws ClassNotFoundException, IOException {
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
            this.isAutoInstallDependenciesEnabled = true;
            this.mode = parcel.readInt();
            this.installFlags = parcel.readInt();
            this.installLocation = parcel.readInt();
            this.installReason = parcel.readInt();
            this.installScenario = parcel.readInt();
            this.sizeBytes = parcel.readLong();
            this.appPackageName = parcel.readString();
            this.appIcon = (Bitmap) parcel.readParcelable(null, Bitmap.class);
            this.appLabel = parcel.readString();
            this.originatingUri = (Uri) parcel.readParcelable(null, Uri.class);
            this.originatingUid = parcel.readInt();
            this.referrerUri = (Uri) parcel.readParcelable(null, Uri.class);
            this.abiOverride = parcel.readString();
            this.volumeUuid = parcel.readString();
            ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
            this.mPermissionStates = arrayMap;
            parcel.readMap(arrayMap, null, String.class, Integer.class);
            this.whitelistedRestrictedPermissions = parcel.createStringArrayList();
            this.autoRevokePermissionsMode = parcel.readInt();
            this.installerPackageName = parcel.readString();
            this.isMultiPackage = parcel.readBoolean();
            this.isStaged = parcel.readBoolean();
            this.forceQueryableOverride = parcel.readBoolean();
            this.requiredInstalledVersionCode = parcel.readLong();
            DataLoaderParamsParcel dataLoaderParamsParcel = (DataLoaderParamsParcel) parcel.readParcelable(DataLoaderParamsParcel.class.getClassLoader(), DataLoaderParamsParcel.class);
            if (dataLoaderParamsParcel != null) {
                this.dataLoaderParams = new DataLoaderParams(dataLoaderParamsParcel);
            }
            this.rollbackDataPolicy = parcel.readInt();
            this.rollbackLifetimeMillis = parcel.readLong();
            this.rollbackImpactLevel = parcel.readInt();
            this.requireUserAction = parcel.readInt();
            this.packageSource = parcel.readInt();
            this.applicationEnabledSettingPersistent = parcel.readBoolean();
            this.developmentInstallFlags = parcel.readInt();
            this.unarchiveId = parcel.readInt();
            this.dexoptCompilerFilter = parcel.readString();
            this.isAutoInstallDependenciesEnabled = parcel.readBoolean();
            this.sessionFlags = parcel.readInt();
        }

        public SessionParams copy() {
            SessionParams sessionParams = new SessionParams(this.mode);
            sessionParams.installFlags = this.installFlags;
            sessionParams.installLocation = this.installLocation;
            sessionParams.installReason = this.installReason;
            sessionParams.installScenario = this.installScenario;
            sessionParams.sizeBytes = this.sizeBytes;
            sessionParams.appPackageName = this.appPackageName;
            sessionParams.appIcon = this.appIcon;
            sessionParams.appLabel = this.appLabel;
            sessionParams.originatingUri = this.originatingUri;
            sessionParams.originatingUid = this.originatingUid;
            sessionParams.referrerUri = this.referrerUri;
            sessionParams.abiOverride = this.abiOverride;
            sessionParams.volumeUuid = this.volumeUuid;
            sessionParams.mPermissionStates.putAll((ArrayMap<? extends String, ? extends Integer>) this.mPermissionStates);
            sessionParams.whitelistedRestrictedPermissions = this.whitelistedRestrictedPermissions;
            sessionParams.autoRevokePermissionsMode = this.autoRevokePermissionsMode;
            sessionParams.installerPackageName = this.installerPackageName;
            sessionParams.isMultiPackage = this.isMultiPackage;
            sessionParams.isStaged = this.isStaged;
            sessionParams.forceQueryableOverride = this.forceQueryableOverride;
            sessionParams.requiredInstalledVersionCode = this.requiredInstalledVersionCode;
            sessionParams.dataLoaderParams = this.dataLoaderParams;
            sessionParams.rollbackDataPolicy = this.rollbackDataPolicy;
            sessionParams.rollbackLifetimeMillis = this.rollbackLifetimeMillis;
            sessionParams.rollbackImpactLevel = this.rollbackImpactLevel;
            sessionParams.requireUserAction = this.requireUserAction;
            sessionParams.packageSource = this.packageSource;
            sessionParams.applicationEnabledSettingPersistent = this.applicationEnabledSettingPersistent;
            sessionParams.developmentInstallFlags = this.developmentInstallFlags;
            sessionParams.unarchiveId = this.unarchiveId;
            sessionParams.dexoptCompilerFilter = this.dexoptCompilerFilter;
            sessionParams.isAutoInstallDependenciesEnabled = this.isAutoInstallDependenciesEnabled;
            sessionParams.sessionFlags = this.sessionFlags;
            return sessionParams;
        }

        public boolean areHiddenOptionsSet() {
            int i = this.installFlags;
            return ((1169536 & i) == i && this.abiOverride == null && this.volumeUuid == null) ? false : true;
        }

        public void setInstallLocation(int i) {
            this.installLocation = i;
        }

        public void setSize(long j) {
            this.sizeBytes = j;
        }

        public void setAppPackageName(String str) {
            this.appPackageName = str;
        }

        public void setAppIcon(Bitmap bitmap) {
            this.appIcon = bitmap;
        }

        public void setAppLabel(CharSequence charSequence) {
            this.appLabel = charSequence != null ? charSequence.toString() : null;
        }

        public void setOriginatingUri(Uri uri) {
            this.originatingUri = uri;
        }

        public void setOriginatingUid(int i) {
            this.originatingUid = i;
        }

        public void setReferrerUri(Uri uri) {
            this.referrerUri = uri;
        }

        @SystemApi
        @Deprecated
        public void setGrantedRuntimePermissions(String[] strArr) {
            if (strArr == null) {
                this.installFlags |= 256;
                this.mPermissionStates.clear();
                return;
            }
            this.installFlags &= -257;
            for (String str : strArr) {
                setPermissionState(str, 1);
            }
        }

        public SessionParams setPermissionState(String str, int i) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Provided permissionName cannot be ".concat(str == null ? PerfettoProtoLogImpl.NULL_STRING : "empty"));
            }
            if (i == 0) {
                this.mPermissionStates.remove(str);
                return this;
            }
            if (i == 1 || i == 2) {
                this.mPermissionStates.put(str, Integer.valueOf(i));
                return this;
            }
            throw new IllegalArgumentException("Unexpected permission state int: " + i);
        }

        public void setPermissionStates(Collection<String> collection, Collection<String> collection2) {
            Iterator<String> it = collection.iterator();
            while (it.hasNext()) {
                this.mPermissionStates.put(it.next(), 1);
            }
            Iterator<String> it2 = collection2.iterator();
            while (it2.hasNext()) {
                this.mPermissionStates.put(it2.next(), 2);
            }
        }

        public void setPackageSource(int i) {
            this.packageSource = i;
        }

        public void setWhitelistedRestrictedPermissions(Set<String> set) {
            if (set == RESTRICTED_PERMISSIONS_ALL) {
                this.installFlags |= 4194304;
                this.whitelistedRestrictedPermissions = null;
            } else {
                this.installFlags &= -4194305;
                this.whitelistedRestrictedPermissions = set != null ? new ArrayList(set) : null;
            }
        }

        @Deprecated
        public void setAutoRevokePermissionsMode(boolean z) {
            this.autoRevokePermissionsMode = !z ? 1 : 0;
        }

        @SystemApi
        public void setEnableRollback(boolean z) {
            setEnableRollback(z, 0);
        }

        @SystemApi
        public void setEnableRollback(boolean z, int i) {
            if (z) {
                this.installFlags |= 262144;
            } else {
                this.installFlags &= -262145;
                this.rollbackLifetimeMillis = 0L;
            }
            this.rollbackDataPolicy = i;
        }

        @SystemApi
        public void setRollbackLifetimeMillis(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("rollbackLifetimeMillis can't be negative.");
            }
            if ((this.installFlags & 262144) == 0) {
                throw new IllegalArgumentException("Can't set rollbackLifetimeMillis when rollback is not enabled");
            }
            this.rollbackLifetimeMillis = j;
        }

        @SystemApi
        public void setRollbackImpactLevel(int i) {
            if ((this.installFlags & 262144) == 0) {
                throw new IllegalArgumentException("Can't set rollbackImpactLevel when rollback is not enabled");
            }
            this.rollbackImpactLevel = i;
        }

        @SystemApi
        @Deprecated
        public void setAllowDowngrade(boolean z) {
            setRequestDowngrade(z);
        }

        @SystemApi
        public void setRequestDowngrade(boolean z) {
            if (z) {
                this.installFlags |= 128;
            } else {
                this.installFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
        }

        public void setRequiredInstalledVersionCode(long j) {
            this.requiredInstalledVersionCode = j;
        }

        public void setInstallFlagsForcePermissionPrompt() {
            this.installFlags |= 1024;
        }

        public void setDontKillApp(boolean z) {
            if (z) {
                this.installFlags |= 4096;
            } else {
                this.installFlags &= -4097;
            }
        }

        @SystemApi
        public void setInstallAsInstantApp(boolean z) {
            if (z) {
                this.installFlags = (this.installFlags | 2048) & (-16385);
            } else {
                this.installFlags = (this.installFlags & (-2049)) | 16384;
            }
        }

        @SystemApi
        public void setInstallAsVirtualPreload() {
            this.installFlags |= 65536;
        }

        public void setInstallReason(int i) {
            this.installReason = i;
        }

        @SystemApi
        public void setAllocateAggressive(boolean z) {
            if (z) {
                this.installFlags |= 32768;
            } else {
                this.installFlags &= -32769;
            }
        }

        public void setInstallFlagAllowTest() {
            this.installFlags |= 4;
        }

        public void setInstallerPackageName(String str) {
            this.installerPackageName = str;
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

        public void setRequireUserAction(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("requireUserAction set as invalid value of " + i + ", but must be one of [USER_ACTION_UNSPECIFIED, USER_ACTION_REQUIRED, USER_ACTION_NOT_REQUIRED]");
            }
            this.requireUserAction = i;
        }

        public void setInstallScenario(int i) {
            this.installScenario = i;
        }

        public void setApplicationEnabledSettingPersistent() {
            this.applicationEnabledSettingPersistent = true;
        }

        public void setRequestUpdateOwnership(boolean z) {
            if (z) {
                this.installFlags |= 33554432;
            } else {
                this.installFlags &= -33554433;
            }
        }

        public void setUnarchiveId(int i) {
            this.unarchiveId = i;
        }

        public void setDexoptCompilerFilter(String str) {
            this.dexoptCompilerFilter = str;
        }

        public ArrayMap<String, Integer> getPermissionStates() {
            return this.mPermissionStates;
        }

        public String[] getLegacyGrantedRuntimePermissions() {
            if ((this.installFlags & 256) != 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mPermissionStates.size(); i++) {
                String strKeyAt = this.mPermissionStates.keyAt(i);
                if (this.mPermissionStates.valueAt(i).intValue() == 1) {
                    arrayList.add(strKeyAt);
                }
            }
            return (String[]) arrayList.toArray((String[]) ArrayUtils.emptyArray(String.class));
        }

        public void setAutoInstallDependenciesEnabled(boolean z) {
            this.isAutoInstallDependenciesEnabled = z;
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair("mode", Integer.valueOf(this.mode));
            indentingPrintWriter.printHexPair("installFlags", this.installFlags);
            indentingPrintWriter.printPair("installLocation", Integer.valueOf(this.installLocation));
            indentingPrintWriter.printPair("installReason", Integer.valueOf(this.installReason));
            indentingPrintWriter.printPair("installScenario", Integer.valueOf(this.installScenario));
            indentingPrintWriter.printPair("sizeBytes", Long.valueOf(this.sizeBytes));
            indentingPrintWriter.printPair("appPackageName", this.appPackageName);
            indentingPrintWriter.printPair("appIcon", Boolean.valueOf(this.appIcon != null));
            indentingPrintWriter.printPair(SemShareConstants.SHARE_STAR_KEY_APPLABEL, this.appLabel);
            indentingPrintWriter.printPair("originatingUri", this.originatingUri);
            indentingPrintWriter.printPair("originatingUid", Integer.valueOf(this.originatingUid));
            indentingPrintWriter.printPair("referrerUri", this.referrerUri);
            indentingPrintWriter.printPair("abiOverride", this.abiOverride);
            indentingPrintWriter.printPair("volumeUuid", this.volumeUuid);
            indentingPrintWriter.printPair("mPermissionStates", this.mPermissionStates);
            indentingPrintWriter.printPair("packageSource", Integer.valueOf(this.packageSource));
            indentingPrintWriter.printPair("whitelistedRestrictedPermissions", this.whitelistedRestrictedPermissions);
            indentingPrintWriter.printPair("autoRevokePermissions", Integer.valueOf(this.autoRevokePermissionsMode));
            indentingPrintWriter.printPair("installerPackageName", this.installerPackageName);
            indentingPrintWriter.printPair("isMultiPackage", Boolean.valueOf(this.isMultiPackage));
            indentingPrintWriter.printPair("isStaged", Boolean.valueOf(this.isStaged));
            indentingPrintWriter.printPair("forceQueryable", Boolean.valueOf(this.forceQueryableOverride));
            indentingPrintWriter.printPair("requireUserAction", SessionInfo.userActionToString(this.requireUserAction));
            indentingPrintWriter.printPair("requiredInstalledVersionCode", Long.valueOf(this.requiredInstalledVersionCode));
            indentingPrintWriter.printPair("dataLoaderParams", this.dataLoaderParams);
            indentingPrintWriter.printPair("rollbackDataPolicy", Integer.valueOf(this.rollbackDataPolicy));
            indentingPrintWriter.printPair("rollbackLifetimeMillis", Long.valueOf(this.rollbackLifetimeMillis));
            indentingPrintWriter.printPair("rollbackImpactLevel", Integer.valueOf(this.rollbackImpactLevel));
            indentingPrintWriter.printPair("applicationEnabledSettingPersistent", Boolean.valueOf(this.applicationEnabledSettingPersistent));
            indentingPrintWriter.printHexPair("developmentInstallFlags", this.developmentInstallFlags);
            indentingPrintWriter.printPair("unarchiveId", Integer.valueOf(this.unarchiveId));
            indentingPrintWriter.printPair("dexoptCompilerFilter", this.dexoptCompilerFilter);
            indentingPrintWriter.printPair("isAutoInstallDependenciesEnabled", Boolean.valueOf(this.isAutoInstallDependenciesEnabled));
            indentingPrintWriter.printPair("sessionFlags", Integer.valueOf(this.sessionFlags == 0 ? 0 : 1));
            indentingPrintWriter.println();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mode);
            parcel.writeInt(this.installFlags);
            parcel.writeInt(this.installLocation);
            parcel.writeInt(this.installReason);
            parcel.writeInt(this.installScenario);
            parcel.writeLong(this.sizeBytes);
            parcel.writeString(this.appPackageName);
            parcel.writeParcelable(this.appIcon, i);
            parcel.writeString(this.appLabel);
            parcel.writeParcelable(this.originatingUri, i);
            parcel.writeInt(this.originatingUid);
            parcel.writeParcelable(this.referrerUri, i);
            parcel.writeString(this.abiOverride);
            parcel.writeString(this.volumeUuid);
            parcel.writeMap(this.mPermissionStates);
            parcel.writeStringList(this.whitelistedRestrictedPermissions);
            parcel.writeInt(this.autoRevokePermissionsMode);
            parcel.writeString(this.installerPackageName);
            parcel.writeBoolean(this.isMultiPackage);
            parcel.writeBoolean(this.isStaged);
            parcel.writeBoolean(this.forceQueryableOverride);
            parcel.writeLong(this.requiredInstalledVersionCode);
            DataLoaderParams dataLoaderParams = this.dataLoaderParams;
            if (dataLoaderParams != null) {
                parcel.writeParcelable(dataLoaderParams.getData(), i);
            } else {
                parcel.writeParcelable(null, i);
            }
            parcel.writeInt(this.rollbackDataPolicy);
            parcel.writeLong(this.rollbackLifetimeMillis);
            parcel.writeInt(this.rollbackImpactLevel);
            parcel.writeInt(this.requireUserAction);
            parcel.writeInt(this.packageSource);
            parcel.writeBoolean(this.applicationEnabledSettingPersistent);
            parcel.writeInt(this.developmentInstallFlags);
            parcel.writeInt(this.unarchiveId);
            parcel.writeString(this.dexoptCompilerFilter);
            parcel.writeBoolean(this.isAutoInstallDependenciesEnabled);
            parcel.writeInt(this.sessionFlags);
        }
    }

    public static class SessionInfo implements Parcelable {
        public static final int INVALID_ID = -1;
        public static final int SESSION_ACTIVATION_FAILED = 2;
        public static final int SESSION_CONFLICT = 4;
        public static final int SESSION_NO_ERROR = 0;
        public static final int SESSION_UNKNOWN_ERROR = 3;
        public static final int SESSION_VERIFICATION_FAILED = 1;

        @Deprecated
        public static final int STAGED_SESSION_ACTIVATION_FAILED = 2;

        @Deprecated
        public static final int STAGED_SESSION_CONFLICT = 4;

        @Deprecated
        public static final int STAGED_SESSION_NO_ERROR = 0;

        @Deprecated
        public static final int STAGED_SESSION_UNKNOWN = 3;

        @Deprecated
        public static final int STAGED_SESSION_VERIFICATION_FAILED = 1;
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
        public boolean isAutoInstallingDependenciesEnabled;
        public boolean isCommitted;
        public boolean isDuplicated;
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
        public static final Parcelable.Creator<SessionInfo> CREATOR = new Parcelable.Creator<SessionInfo>() { // from class: android.content.pm.PackageInstaller.SessionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionInfo createFromParcel(Parcel parcel) {
                return new SessionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionInfo[] newArray(int i) {
                return new SessionInfo[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String userActionToString(int i) {
            if (i == 1) {
                return "REQUIRED";
            }
            if (i == 2) {
                return "NOT_REQUIRED";
            }
            return "UNSPECIFIED";
        }

        public SessionInfo() {
            this.autoRevokePermissionsMode = 3;
            this.parentSessionId = -1;
            this.childSessionIds = NO_SESSIONS;
            this.packageSource = 0;
        }

        public SessionInfo(Parcel parcel) {
            this.autoRevokePermissionsMode = 3;
            this.parentSessionId = -1;
            int[] iArr = NO_SESSIONS;
            this.childSessionIds = iArr;
            this.packageSource = 0;
            this.sessionId = parcel.readInt();
            this.userId = parcel.readInt();
            this.installerPackageName = parcel.readString();
            this.installerAttributionTag = parcel.readString();
            this.resolvedBaseCodePath = parcel.readString();
            this.progress = parcel.readFloat();
            this.sealed = parcel.readInt() != 0;
            this.active = parcel.readInt() != 0;
            this.mode = parcel.readInt();
            this.installReason = parcel.readInt();
            this.installScenario = parcel.readInt();
            this.sizeBytes = parcel.readLong();
            this.appPackageName = parcel.readString();
            this.appIcon = (Bitmap) parcel.readParcelable(null, Bitmap.class);
            this.appLabel = parcel.readString();
            this.installLocation = parcel.readInt();
            this.originatingUri = (Uri) parcel.readParcelable(null, Uri.class);
            this.originatingUid = parcel.readInt();
            this.referrerUri = (Uri) parcel.readParcelable(null, Uri.class);
            this.grantedRuntimePermissions = parcel.readStringArray();
            this.whitelistedRestrictedPermissions = parcel.createStringArrayList();
            this.autoRevokePermissionsMode = parcel.readInt();
            this.installFlags = parcel.readInt();
            this.isMultiPackage = parcel.readBoolean();
            this.isStaged = parcel.readBoolean();
            this.forceQueryable = parcel.readBoolean();
            this.parentSessionId = parcel.readInt();
            int[] iArrCreateIntArray = parcel.createIntArray();
            this.childSessionIds = iArrCreateIntArray;
            if (iArrCreateIntArray == null) {
                this.childSessionIds = iArr;
            }
            this.isSessionApplied = parcel.readBoolean();
            this.isSessionReady = parcel.readBoolean();
            this.isSessionFailed = parcel.readBoolean();
            this.mSessionErrorCode = parcel.readInt();
            this.mSessionErrorMessage = parcel.readString();
            this.isCommitted = parcel.readBoolean();
            this.isPreapprovalRequested = parcel.readBoolean();
            this.rollbackDataPolicy = parcel.readInt();
            this.rollbackLifetimeMillis = parcel.readLong();
            this.rollbackImpactLevel = parcel.readInt();
            this.createdMillis = parcel.readLong();
            this.requireUserAction = parcel.readInt();
            this.installerUid = parcel.readInt();
            this.packageSource = parcel.readInt();
            this.applicationEnabledSettingPersistent = parcel.readBoolean();
            this.pendingUserActionReason = parcel.readInt();
            this.isAutoInstallingDependenciesEnabled = parcel.readBoolean();
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
                    SessionInfo sessionInfo = AppGlobals.getPackageManager().getPackageInstaller().getSessionInfo(this.sessionId);
                    this.appIcon = sessionInfo != null ? sessionInfo.appIcon : null;
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
            return Collections.EMPTY_SET;
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
        public boolean getInstallAsInstantApp(boolean z) {
            return (this.installFlags & 2048) != 0;
        }

        @SystemApi
        public boolean getInstallAsFullApp(boolean z) {
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
            return (!this.isStaged || !this.isCommitted || this.isSessionApplied || this.isSessionFailed || hasParentSessionId()) ? false : true;
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

        public void setSessionErrorCode(int i, String str) {
            this.mSessionErrorCode = i;
            this.mSessionErrorMessage = str;
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

        public boolean isAutoInstallDependenciesEnabled() {
            return this.isAutoInstallingDependenciesEnabled;
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
            CharSequence charSequence = this.appLabel;
            parcel.writeString(charSequence != null ? charSequence.toString() : null);
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
            parcel.writeBoolean(this.isAutoInstallingDependenciesEnabled);
        }
    }

    public static final class PreapprovalDetails implements Parcelable {
        public static final Parcelable.Creator<PreapprovalDetails> CREATOR = new Parcelable.Creator<PreapprovalDetails>() { // from class: android.content.pm.PackageInstaller.PreapprovalDetails.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PreapprovalDetails[] newArray(int i) {
                return new PreapprovalDetails[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PreapprovalDetails createFromParcel(Parcel parcel) {
                return new PreapprovalDetails(parcel);
            }
        };
        private final Bitmap mIcon;
        private final CharSequence mLabel;
        private final ULocale mLocale;
        private final String mPackageName;

        @Deprecated
        private void __metadata() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PreapprovalDetails(Bitmap bitmap, CharSequence charSequence, ULocale uLocale, String str) {
            this.mIcon = bitmap;
            this.mLabel = charSequence;
            Preconditions.checkArgument(!TextUtils.isEmpty(charSequence), "App label cannot be empty.");
            this.mLocale = uLocale;
            Objects.isNull(uLocale);
            Preconditions.checkArgument(uLocale != null, "Locale cannot be null.");
            this.mPackageName = str;
            Preconditions.checkArgument(!TextUtils.isEmpty(str), "Package name cannot be empty.");
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mIcon != null ? (byte) 1 : (byte) 0);
            Bitmap bitmap = this.mIcon;
            if (bitmap != null) {
                bitmap.writeToParcel(parcel, i);
            }
            parcel.writeCharSequence(this.mLabel);
            parcel.writeString8(this.mLocale.toString());
            parcel.writeString8(this.mPackageName);
        }

        PreapprovalDetails(Parcel parcel) {
            Bitmap bitmapCreateFromParcel = (parcel.readByte() & 1) == 0 ? null : Bitmap.CREATOR.createFromParcel(parcel);
            CharSequence charSequence = parcel.readCharSequence();
            ULocale uLocale = new ULocale(parcel.readString8());
            String string8 = parcel.readString8();
            this.mIcon = bitmapCreateFromParcel;
            this.mLabel = charSequence;
            Preconditions.checkArgument(!TextUtils.isEmpty(charSequence), "App label cannot be empty.");
            this.mLocale = uLocale;
            Objects.isNull(uLocale);
            Preconditions.checkArgument(true, "Locale cannot be null.");
            this.mPackageName = string8;
            Preconditions.checkArgument(!TextUtils.isEmpty(string8), "Package name cannot be empty.");
        }

        public static final class Builder {
            private long mBuilderFieldsSet = 0;
            private Bitmap mIcon;
            private CharSequence mLabel;
            private ULocale mLocale;
            private String mPackageName;

            public Builder setIcon(Bitmap bitmap) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 1;
                this.mIcon = bitmap;
                return this;
            }

            public Builder setLabel(CharSequence charSequence) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 2;
                this.mLabel = charSequence;
                return this;
            }

            public Builder setLocale(ULocale uLocale) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 4;
                this.mLocale = uLocale;
                return this;
            }

            public Builder setPackageName(String str) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 8;
                this.mPackageName = str;
                return this;
            }

            public PreapprovalDetails build() {
                checkNotUsed();
                this.mBuilderFieldsSet |= 16;
                return new PreapprovalDetails(this.mIcon, this.mLabel, this.mLocale, this.mPackageName);
            }

            private void checkNotUsed() {
                if ((this.mBuilderFieldsSet & 16) != 0) {
                    throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
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
            return "PreapprovalDetails { icon = " + this.mIcon + ", label = " + ((Object) this.mLabel) + ", locale = " + this.mLocale + ", packageName = " + this.mPackageName + " }";
        }
    }

    public static final class InstallConstraintsResult implements Parcelable {
        public static final Parcelable.Creator<InstallConstraintsResult> CREATOR = new Parcelable.Creator<InstallConstraintsResult>() { // from class: android.content.pm.PackageInstaller.InstallConstraintsResult.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InstallConstraintsResult[] newArray(int i) {
                return new InstallConstraintsResult[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InstallConstraintsResult createFromParcel(Parcel parcel) {
                return new InstallConstraintsResult(parcel);
            }
        };
        private boolean mAllConstraintsSatisfied;

        @Deprecated
        private void __metadata() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean areAllConstraintsSatisfied() {
            return this.mAllConstraintsSatisfied;
        }

        public InstallConstraintsResult(boolean z) {
            this.mAllConstraintsSatisfied = z;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mAllConstraintsSatisfied ? (byte) 1 : (byte) 0);
        }

        InstallConstraintsResult(Parcel parcel) {
            this.mAllConstraintsSatisfied = (parcel.readByte() & 1) != 0;
        }
    }

    public static final class InstallConstraints implements Parcelable {
        private final boolean mAppNotForegroundRequired;
        private final boolean mAppNotInteractingRequired;
        private final boolean mAppNotTopVisibleRequired;
        private final boolean mDeviceIdleRequired;
        private final boolean mNotInCallRequired;
        public static final InstallConstraints GENTLE_UPDATE = new Builder().setAppNotInteractingRequired().build();
        public static final Parcelable.Creator<InstallConstraints> CREATOR = new Parcelable.Creator<InstallConstraints>() { // from class: android.content.pm.PackageInstaller.InstallConstraints.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InstallConstraints[] newArray(int i) {
                return new InstallConstraints[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InstallConstraints createFromParcel(Parcel parcel) {
                return new InstallConstraints(parcel);
            }
        };

        @Deprecated
        private void __metadata() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

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
                return new InstallConstraints(this.mDeviceIdleRequired, this.mAppNotForegroundRequired, this.mAppNotInteractingRequired, this.mAppNotTopVisibleRequired, this.mNotInCallRequired);
            }
        }

        public InstallConstraints(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.mDeviceIdleRequired = z;
            this.mAppNotForegroundRequired = z2;
            this.mAppNotInteractingRequired = z3;
            this.mAppNotTopVisibleRequired = z4;
            this.mNotInCallRequired = z5;
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                InstallConstraints installConstraints = (InstallConstraints) obj;
                if (this.mDeviceIdleRequired == installConstraints.mDeviceIdleRequired && this.mAppNotForegroundRequired == installConstraints.mAppNotForegroundRequired && this.mAppNotInteractingRequired == installConstraints.mAppNotInteractingRequired && this.mAppNotTopVisibleRequired == installConstraints.mAppNotTopVisibleRequired && this.mNotInCallRequired == installConstraints.mNotInCallRequired) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return ((((((((Boolean.hashCode(this.mDeviceIdleRequired) + 31) * 31) + Boolean.hashCode(this.mAppNotForegroundRequired)) * 31) + Boolean.hashCode(this.mAppNotInteractingRequired)) * 31) + Boolean.hashCode(this.mAppNotTopVisibleRequired)) * 31) + Boolean.hashCode(this.mNotInCallRequired);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            byte b = this.mDeviceIdleRequired ? (byte) 1 : (byte) 0;
            if (this.mAppNotForegroundRequired) {
                b = (byte) (b | 2);
            }
            if (this.mAppNotInteractingRequired) {
                b = (byte) (b | 4);
            }
            if (this.mAppNotTopVisibleRequired) {
                b = (byte) (b | 8);
            }
            if (this.mNotInCallRequired) {
                b = (byte) (b | 16);
            }
            parcel.writeByte(b);
        }

        InstallConstraints(Parcel parcel) {
            byte b = parcel.readByte();
            boolean z = (b & 1) != 0;
            boolean z2 = (b & 2) != 0;
            boolean z3 = (b & 4) != 0;
            boolean z4 = (b & 8) != 0;
            boolean z5 = (b & 16) != 0;
            this.mDeviceIdleRequired = z;
            this.mAppNotForegroundRequired = z2;
            this.mAppNotInteractingRequired = z3;
            this.mAppNotTopVisibleRequired = z4;
            this.mNotInCallRequired = z5;
        }
    }

    public static final class UnarchivalState {
        private final long mRequiredStorageBytes;
        private final int mStatus;
        private final int mUnarchiveId;
        private final PendingIntent mUserActionIntent;

        public static UnarchivalState createOkState(int i) {
            return new UnarchivalState(i, 0, -1L, null);
        }

        public static UnarchivalState createUserActionRequiredState(int i, PendingIntent pendingIntent) {
            Objects.requireNonNull(pendingIntent);
            return new UnarchivalState(i, 1, -1L, pendingIntent);
        }

        public static UnarchivalState createInsufficientStorageState(int i, long j, PendingIntent pendingIntent) {
            return new UnarchivalState(i, 2, j, pendingIntent);
        }

        public static UnarchivalState createNoConnectivityState(int i) {
            return new UnarchivalState(i, 3, -1L, null);
        }

        public static UnarchivalState createGenericErrorState(int i) {
            return new UnarchivalState(i, 100, -1L, null);
        }

        private UnarchivalState(int i, int i2, long j, PendingIntent pendingIntent) {
            this.mUnarchiveId = i;
            this.mStatus = i2;
            AnnotationValidations.validate((Class<? extends Annotation>) UnarchivalStatus.class, (Annotation) null, i2);
            this.mRequiredStorageBytes = j;
            this.mUserActionIntent = pendingIntent;
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
