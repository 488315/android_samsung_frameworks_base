package com.android.server.pm;

import android.R;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.admin.DevicePolicyResourcesManager;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.Checksum;
import android.content.pm.DataLoaderManager;
import android.content.pm.DataLoaderParams;
import android.content.pm.DataLoaderParamsParcel;
import android.content.pm.FileSystemControlParcel;
import android.content.pm.IDataLoader;
import android.content.pm.IDataLoaderStatusListener;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.IPackageInstallerSessionFileSystemConnector;
import android.content.pm.IPackageLoadingProgressCallback;
import android.content.pm.InstallationFile;
import android.content.pm.InstallationFileParcel;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageParser;
import android.content.pm.SigningDetails;
import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.parsing.ApkLite;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.pm.verify.domain.DomainSet;
import android.content.res.Resources;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.BitmapFactory;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileBridge;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.RevocableFileDescriptor;
import android.os.SELinux;
import android.os.UserHandle;
import android.os.incremental.IStorageHealthListener;
import android.os.incremental.IncrementalFileStorages;
import android.os.incremental.IncrementalManager;
import android.os.incremental.PerUidReadTimeouts;
import android.os.incremental.StorageHealthCheckParams;
import android.os.storage.StorageManager;
import android.provider.DeviceConfig;
import android.security.Flags;
import android.system.ErrnoException;
import android.system.Int64Ref;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.IntArray;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.apk.ApkSignatureVerifier;

import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.SomeArgs;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.utils.RequestThrottle;

import libcore.io.IoUtils;
import libcore.util.EmptyArray;

import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageInstallerSession extends IPackageInstallerSession.Stub {
    public static final int[] EMPTY_CHILD_SESSION_ARRAY = EmptyArray.INT;
    public static final InstallationFile[] EMPTY_INSTALLATION_FILE_ARRAY = new InstallationFile[0];
    public static final AnonymousClass1 sAddedApkFilter = new AnonymousClass1(0);
    public static final AnonymousClass1 sAddedFilter = new AnonymousClass1(1);
    public static final AnonymousClass1 sRemovedFilter = new AnonymousClass1(2);
    public long committedMillis;
    public final long createdMillis;
    public final int defaultContainerGid;
    public final PackageInstallerService.InternalCallback mCallback;
    public final Context mContext;
    public volatile boolean mDestroyed;
    public String mFinalMessage;
    public int mFinalStatus;
    public final Handler mHandler;
    public final AnonymousClass4 mHandlerCallback;
    public boolean mHasDeviceAdminReceiver;
    public IncrementalFileStorages mIncrementalFileStorages;
    public File mInheritedFilesBase;
    public InstallSource mInstallSource;
    public final Installer mInstaller;
    public volatile int mInstallerUid;
    public final String mOriginalInstallerPackageName;
    public final int mOriginalInstallerUid;
    public PackageLite mPackageLite;
    public String mPackageName;
    public int mParentSessionId;
    public PackageInstallerSession$$ExternalSyntheticLambda0 mPendingAbandonCallback;
    public final PackageManagerService mPm;
    public DomainSet mPreVerifiedDomains;
    public PackageInstaller.PreapprovalDetails mPreapprovalDetails;
    public IntentSender mPreapprovalRemoteStatusReceiver;
    public boolean mPrepared;
    public IntentSender mRemoteStatusReceiver;
    public File mResolvedBaseFile;
    public File mResolvedStageDir;
    public boolean mSessionApplied;
    public int mSessionErrorCode;
    public String mSessionErrorMessage;
    public boolean mSessionFailed;
    public final PackageSessionProvider mSessionProvider;
    public boolean mSessionReady;
    public final boolean mShouldBeSealed;
    public SigningDetails mSigningDetails;
    public final SilentUpdatePolicy mSilentUpdatePolicy;
    public final StagedSession mStagedSession;
    public final StagingManager mStagingManager;
    public Boolean mUserActionRequired;
    public int mUserActionRequirement;
    public boolean mVerityFoundForApks;
    public long mVersionCode;
    public final PackageInstaller.SessionParams params;
    public final int sessionId;
    public final String stageCid;
    public final File stageDir;
    public long updatedMillis;
    public final int userId;
    public final AtomicInteger mActiveCount = new AtomicInteger();
    public final Object mLock = new Object();
    public final AtomicBoolean mTransactionLock = new AtomicBoolean(false);
    public final AtomicBoolean mUnknownSourceInstallAccepted = new AtomicBoolean(false);
    public final Object mProgressLock = new Object();
    public float mClientProgress = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mInternalProgress = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mProgress = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mReportedProgress = -1.0f;
    public float mIncrementalProgress = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public boolean mSealed = false;
    public final AtomicBoolean mPreapprovalRequested = new AtomicBoolean(false);
    public final AtomicBoolean mCommitted = new AtomicBoolean(false);
    public boolean mStageDirInUse = false;
    public boolean mVerificationInProgress = false;
    public boolean mPermissionsManuallyAccepted = false;
    public final ArrayList mFds = new ArrayList();
    public final ArrayList mBridges = new ArrayList();
    public final SparseArray mChildSessions = new SparseArray();
    public final ArraySet mFiles = new ArraySet();
    public final ArrayMap mChecksums = new ArrayMap();
    public boolean mHasAppMetadataFile = false;
    public final List mResolvedStagedFiles = new ArrayList();
    public final List mResolvedInheritedFiles = new ArrayList();
    public final List mResolvedInstructionSets = new ArrayList();
    public final List mResolvedNativeLibPaths = new ArrayList();
    public final Set mUnarchivalListeners = new ArraySet();
    public volatile boolean mDataLoaderFinished = false;
    public int mValidatedTargetSdk = Integer.MAX_VALUE;
    public int mUnarchivalStatus = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PackageInstallerSession$1, reason: invalid class name */
    public final class AnonymousClass1 implements FileFilter {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass1(int i) {
            this.$r8$classId = i;
        }

        @Override // java.io.FileFilter
        public final boolean accept(File file) {
            switch (this.$r8$classId) {
                case 0:
                    if (!file.isDirectory()
                            && !file.getName().endsWith(".removed")
                            && !file.getName().endsWith(".idsig")
                            && !file.getName().endsWith("app.metadata")
                            && !DexMetadataHelper.isDexMetadataFile(file)
                            && !VerityUtils.isFsveritySignatureFile(file)) {
                        int i = ApkChecksums.$r8$clinit;
                        String name = file.getName();
                        if (!name.endsWith(".digests") && !name.endsWith(".signature")) {}
                    }
                    break;
                case 1:
                    if (file.isDirectory() || file.getName().endsWith(".removed")) {
                        break;
                    }
                    break;
                default:
                    if (file.isDirectory() || !file.getName().endsWith(".removed")) {
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FileEntry {
        public final InstallationFile mFile;
        public final int mIndex;

        public FileEntry(int i, InstallationFile installationFile) {
            this.mIndex = i;
            this.mFile = installationFile;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof FileEntry)) {
                return false;
            }
            FileEntry fileEntry = (FileEntry) obj;
            return this.mFile.getLocation() == fileEntry.mFile.getLocation()
                    && TextUtils.equals(this.mFile.getName(), fileEntry.mFile.getName());
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mFile.getLocation()), this.mFile.getName());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FileSystemConnector
            extends IPackageInstallerSessionFileSystemConnector.Stub {
        public final Set mAddedFiles = new ArraySet();

        public FileSystemConnector(List list) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                InstallationFileParcel installationFileParcel = (InstallationFileParcel) it.next();
                ((ArraySet) this.mAddedFiles).add(installationFileParcel.name);
            }
        }

        public final void writeData(
                String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
            if (parcelFileDescriptor == null) {
                throw new IllegalArgumentException("incomingFd can't be null");
            }
            if (!((ArraySet) this.mAddedFiles).contains(str)) {
                throw new SecurityException("File name is not in the list of added files.");
            }
            try {
                PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
                int[] iArr = PackageInstallerSession.EMPTY_CHILD_SESSION_ARRAY;
                packageInstallerSession.doWriteInternal(str, j, j2, parcelFileDescriptor);
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InstallResult {
        public final Bundle extras;
        public final PackageInstallerSession session;

        public InstallResult(PackageInstallerSession packageInstallerSession, Bundle bundle) {
            this.session = packageInstallerSession;
            this.extras = bundle;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PerFileChecksum {
        public final Checksum[] mChecksums;
        public final byte[] mSignature;

        public PerFileChecksum(Checksum[] checksumArr, byte[] bArr) {
            this.mChecksums = checksumArr;
            this.mSignature = bArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class StagedSession implements StagingManager.StagedSession {
        public StagedSession() {}

        public final boolean containsApexSession() {
            return PackageInstallerSession.this.sessionContains(
                    new PackageInstallerSession$$ExternalSyntheticLambda10(
                            1, new PackageInstallerSession$$ExternalSyntheticLambda3(2)));
        }

        public final List getChildSessions() {
            ArrayList arrayList;
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            if (!packageInstallerSession.params.isMultiPackage) {
                return Collections.EMPTY_LIST;
            }
            synchronized (packageInstallerSession.mLock) {
                try {
                    int size = PackageInstallerSession.this.mChildSessions.size();
                    arrayList = new ArrayList(size);
                    for (int i = 0; i < size; i++) {
                        arrayList.add(
                                ((PackageInstallerSession)
                                                PackageInstallerSession.this.mChildSessions.valueAt(
                                                        i))
                                        .mStagedSession);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public final CompletableFuture installSession() {
            int[] iArr = PackageInstallerSession.EMPTY_CHILD_SESSION_ARRAY;
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            packageInstallerSession.assertCallerIsOwnerOrRootOrSystem();
            packageInstallerSession.assertNotChild("StagedSession#installSession");
            Preconditions.checkArgument(
                    packageInstallerSession.mCommitted.get() && isSessionReady());
            return packageInstallerSession.install();
        }

        public final boolean isApexSession() {
            return (PackageInstallerSession.this.params.installFlags & 131072) != 0;
        }

        public final boolean isInTerminalState() {
            int[] iArr = PackageInstallerSession.EMPTY_CHILD_SESSION_ARRAY;
            return PackageInstallerSession.this.isInTerminalState();
        }

        public final boolean isSessionReady() {
            boolean z;
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            synchronized (packageInstallerSession.mLock) {
                z = packageInstallerSession.mSessionReady;
            }
            return z;
        }

        public final void setSessionFailed(int i, String str) {
            PackageInstallerSession.this.setSessionFailed(i, str);
        }

        public final void verifySession() {
            boolean z;
            boolean z2;
            String str;
            String formatSimple;
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            int[] iArr = PackageInstallerSession.EMPTY_CHILD_SESSION_ARRAY;
            packageInstallerSession.assertCallerIsOwnerOrRootOrSystem();
            if (PackageInstallerSession.this.mCommitted.get()) {
                PackageInstallerSession packageInstallerSession2 = PackageInstallerSession.this;
                synchronized (packageInstallerSession2.mLock) {
                    z = packageInstallerSession2.mSessionApplied;
                }
                if (z) {
                    str =
                            TextUtils.formatSimple(
                                    "The session %d has applied",
                                    new Object[] {
                                        Integer.valueOf(PackageInstallerSession.this.sessionId)
                                    });
                } else {
                    PackageInstallerSession packageInstallerSession3 = PackageInstallerSession.this;
                    synchronized (packageInstallerSession3.mLock) {
                        z2 = packageInstallerSession3.mSessionFailed;
                    }
                    if (z2) {
                        synchronized (PackageInstallerSession.this.mLock) {
                            formatSimple =
                                    TextUtils.formatSimple(
                                            "The session %d has failed with error: %s",
                                            new Object[] {
                                                Integer.valueOf(
                                                        PackageInstallerSession.this.sessionId),
                                                PackageInstallerSession.this.mSessionErrorMessage
                                            });
                        }
                        str = formatSimple;
                    } else {
                        str = null;
                    }
                }
            } else {
                str =
                        TextUtils.formatSimple(
                                "The session %d should be committed",
                                new Object[] {
                                    Integer.valueOf(PackageInstallerSession.this.sessionId)
                                });
            }
            if (str == null) {
                PackageInstallerSession.this.verify();
                return;
            }
            Slog.e("PackageInstallerSession", "verifySession error: ".concat(str));
            setSessionFailed(-110, str);
            PackageInstallerSession.this.onSessionVerificationFailure(-110, str);
        }
    }

    /* renamed from: -$$Nest$mgetDataLoader, reason: not valid java name */
    public static IDataLoader m755$$Nest$mgetDataLoader(
            PackageInstallerSession packageInstallerSession, int i) {
        DataLoaderManager dataLoaderManager =
                (DataLoaderManager)
                        packageInstallerSession.mContext.getSystemService(DataLoaderManager.class);
        if (dataLoaderManager == null) {
            throw new PackageManagerException(-20, "Failed to find data loader manager service");
        }
        IDataLoader dataLoader = dataLoaderManager.getDataLoader(i);
        if (dataLoader != null) {
            return dataLoader;
        }
        throw new PackageManagerException(-20, "Failure to obtain data loader");
    }

    /* renamed from: -$$Nest$smsendPendingStreaming, reason: not valid java name */
    public static void m756$$Nest$smsendPendingStreaming(
            int i, Context context, IntentSender intentSender, String str) {
        if (intentSender == null) {
            Slog.e("PackageInstallerSession", "Missing receiver for pending streaming status.");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent.putExtra("android.content.pm.extra.STATUS", -2);
        if (TextUtils.isEmpty(str)) {
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", "Staging Image Not Ready");
        } else {
            intent.putExtra(
                    "android.content.pm.extra.STATUS_MESSAGE",
                    "Staging Image Not Ready [" + str + "]");
        }
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public PackageInstallerSession(
            PackageInstallerService.InternalCallback internalCallback,
            Context context,
            PackageManagerService packageManagerService,
            PackageSessionProvider packageSessionProvider,
            SilentUpdatePolicy silentUpdatePolicy,
            Looper looper,
            StagingManager stagingManager,
            int i,
            int i2,
            int i3,
            InstallSource installSource,
            PackageInstaller.SessionParams sessionParams,
            long j,
            long j2,
            File file,
            String str,
            InstallationFile[] installationFileArr,
            ArrayMap arrayMap,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            int[] iArr,
            int i4,
            boolean z5,
            boolean z6,
            boolean z7,
            int i5,
            String str2,
            DomainSet domainSet) {
        this.mPrepared = false;
        this.mShouldBeSealed = false;
        this.mSessionErrorCode = 0;
        this.mDestroyed = false;
        Handler.Callback callback =
                new Handler
                        .Callback() { // from class: com.android.server.pm.PackageInstallerSession.4
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    @Override // android.os.Handler.Callback
                    public final boolean handleMessage(Message message) {
                        String string;
                        IntentSender intentSender;
                        final int i6 = 0;
                        switch (message.what) {
                            case 1:
                                PackageInstallerSession packageInstallerSession =
                                        PackageInstallerSession.this;
                                if (!packageInstallerSession.isSealed()) {
                                    throw new IllegalStateException(
                                            "dispatchSessionSealed".concat(" before sealing"));
                                }
                                RequestThrottle requestThrottle =
                                        PackageInstallerService.this.mSettingsWriteRequest;
                                requestThrottle.mLastRequest.incrementAndGet();
                                requestThrottle.runInternal();
                                packageInstallerSession.mHandler.obtainMessage(2).sendToTarget();
                                return true;
                            case 2:
                                PackageInstallerSession packageInstallerSession2 =
                                        PackageInstallerSession.this;
                                packageInstallerSession2.getClass();
                                try {
                                    Iterator it =
                                            packageInstallerSession2.getChildSessions().iterator();
                                    boolean z8 = true;
                                    while (it.hasNext()) {
                                        z8 &=
                                                ((PackageInstallerSession) it.next())
                                                        .streamValidateAndCommit();
                                    }
                                    if (z8 && packageInstallerSession2.streamValidateAndCommit()) {
                                        packageInstallerSession2
                                                .mHandler
                                                .obtainMessage(3)
                                                .sendToTarget();
                                    }
                                } catch (PackageManagerException e) {
                                    String completeMessage = ExceptionUtils.getCompleteMessage(e);
                                    packageInstallerSession2.destroy(completeMessage);
                                    packageInstallerSession2.dispatchSessionFinished(
                                            e.error, completeMessage, null);
                                    packageInstallerSession2.maybeFinishChildSessions(
                                            e.error, completeMessage);
                                    PackageInstallerSession.notifyKnoxSignatureVerificationFailure(
                                            e.error,
                                            packageInstallerSession2.userId,
                                            TextUtils.isEmpty(packageInstallerSession2.mPackageName)
                                                    ? e.packageName
                                                    : packageInstallerSession2.mPackageName);
                                }
                                return true;
                            case 3:
                                PackageInstallerSession packageInstallerSession3 =
                                        PackageInstallerSession.this;
                                String str3 =
                                        packageInstallerSession3.getInstallSource()
                                                .mInstallerPackageName;
                                List list = SemSamsungThemeUtils.disableOverlayList;
                                if ("com.samsung.android.themecenter".equals(str3)
                                        && packageInstallerSession3.getInstallerUid() != 1000) {
                                    packageInstallerSession3.destroyInternal(
                                            "Install failed with internal error");
                                    packageInstallerSession3.dispatchSessionFinished(
                                            -110, "Install failed with internal error", null);
                                    Slog.e(
                                            "PackageInstallerSession",
                                            "Install failed with internal error");
                                }
                                if (packageInstallerSession3
                                        .isInstallerDeviceOwnerOrAffiliatedProfileOwner()) {
                                    DevicePolicyEventLogger.createEvent(112)
                                            .setAdmin(
                                                    packageInstallerSession3.getInstallSource()
                                                            .mInstallerPackageName)
                                            .write();
                                }
                                boolean sendPendingUserActionIntentIfNeeded =
                                        packageInstallerSession3
                                                .sendPendingUserActionIntentIfNeeded(false);
                                if (packageInstallerSession3.mUserActionRequired == null) {
                                    packageInstallerSession3.mUserActionRequired =
                                            Boolean.valueOf(sendPendingUserActionIntentIfNeeded);
                                }
                                if (sendPendingUserActionIntentIfNeeded) {
                                    packageInstallerSession3.deactivate();
                                } else {
                                    if (packageInstallerSession3.mUserActionRequired.booleanValue()
                                            && packageInstallerSession3.mActiveCount
                                                            .getAndIncrement()
                                                    == 0) {
                                        PackageInstallerService.Callbacks callbacks =
                                                PackageInstallerService.this.mCallbacks;
                                        int i7 = packageInstallerSession3.sessionId;
                                        int i8 = packageInstallerSession3.userId;
                                        int i9 = PackageInstallerService.Callbacks.$r8$clinit;
                                        callbacks
                                                .obtainMessage(3, i7, i8, Boolean.TRUE)
                                                .sendToTarget();
                                    }
                                    if (packageInstallerSession3.mVerificationInProgress) {
                                        HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                                new StringBuilder(
                                                        "Verification is already in progress for"
                                                            + " session "),
                                                packageInstallerSession3.sessionId,
                                                "PackageInstallerSession");
                                    } else {
                                        packageInstallerSession3.mVerificationInProgress = true;
                                        if (packageInstallerSession3.params.isStaged) {
                                            packageInstallerSession3.mStagedSession.verifySession();
                                        } else {
                                            packageInstallerSession3.verify();
                                        }
                                    }
                                }
                                return true;
                            case 4:
                                SomeArgs someArgs = (SomeArgs) message.obj;
                                String str4 = (String) someArgs.arg1;
                                String str5 = (String) someArgs.arg2;
                                Bundle bundle = (Bundle) someArgs.arg3;
                                IntentSender intentSender2 = (IntentSender) someArgs.arg4;
                                int i10 = someArgs.argi1;
                                boolean z9 = someArgs.argi2 == 1;
                                someArgs.recycle();
                                PackageInstallerSession packageInstallerSession4 =
                                        PackageInstallerSession.this;
                                final Context context2 = packageInstallerSession4.mContext;
                                int i11 = packageInstallerSession4.sessionId;
                                boolean isInstallerDeviceOwnerOrAffiliatedProfileOwner =
                                        packageInstallerSession4
                                                .isInstallerDeviceOwnerOrAffiliatedProfileOwner();
                                int i12 = PackageInstallerSession.this.userId;
                                if (1 == i10 && isInstallerDeviceOwnerOrAffiliatedProfileOwner) {
                                    boolean z10 =
                                            bundle != null
                                                    && bundle.getBoolean(
                                                            "android.intent.extra.REPLACING");
                                    DevicePolicyManager devicePolicyManager =
                                            (DevicePolicyManager)
                                                    context2.getSystemService(
                                                            DevicePolicyManager.class);
                                    if (z10) {
                                        string =
                                                devicePolicyManager
                                                        .getResources()
                                                        .getString(
                                                                "Core.PACKAGE_UPDATED_BY_DO",
                                                                new Supplier() { // from class:
                                                                    // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda5
                                                                    @Override // java.util.function.Supplier
                                                                    public final Object get() {
                                                                        int i13 = i6;
                                                                        Context context3 = context2;
                                                                        switch (i13) {
                                                                            case 0:
                                                                                return context3
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .smv_process);
                                                                            default:
                                                                                return context3
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .sms_short_code_confirm_never_allow);
                                                                        }
                                                                    }
                                                                });
                                    } else {
                                        DevicePolicyResourcesManager resources =
                                                devicePolicyManager.getResources();
                                        final int i13 = 1;
                                        string =
                                                resources.getString(
                                                        "Core.PACKAGE_INSTALLED_BY_DO",
                                                        new Supplier() { // from class:
                                                            // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda5
                                                            @Override // java.util.function.Supplier
                                                            public final Object get() {
                                                                int i132 = i13;
                                                                Context context3 = context2;
                                                                switch (i132) {
                                                                    case 0:
                                                                        return context3.getString(
                                                                                R.string
                                                                                        .smv_process);
                                                                    default:
                                                                        return context3.getString(
                                                                                R.string
                                                                                        .sms_short_code_confirm_never_allow);
                                                                }
                                                            }
                                                        });
                                    }
                                    Notification buildSuccessNotification =
                                            PackageInstallerService.buildSuccessNotification(
                                                    i12, context2, string, str4);
                                    if (buildSuccessNotification != null) {
                                        ((NotificationManager)
                                                        context2.getSystemService("notification"))
                                                .notify(str4, 21, buildSuccessNotification);
                                    }
                                }
                                Intent intent = new Intent();
                                intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str4);
                                intent.putExtra("android.content.pm.extra.SESSION_ID", i11);
                                intent.putExtra(
                                        "android.content.pm.extra.STATUS",
                                        PackageManager.installStatusToPublicStatus(i10));
                                intent.putExtra(
                                        "android.content.pm.extra.STATUS_MESSAGE",
                                        PackageManager.installStatusToString(i10, str5));
                                intent.putExtra("android.content.pm.extra.LEGACY_STATUS", i10);
                                intent.putExtra("android.content.pm.extra.PRE_APPROVAL", z9);
                                if (bundle != null) {
                                    String string2 =
                                            bundle.getString(
                                                    "android.content.pm.extra.FAILURE_EXISTING_PACKAGE");
                                    if (!TextUtils.isEmpty(string2)) {
                                        intent.putExtra(
                                                "android.content.pm.extra.OTHER_PACKAGE_NAME",
                                                string2);
                                    }
                                    ArrayList<String> stringArrayList =
                                            bundle.getStringArrayList(
                                                    "android.content.pm.extra.WARNINGS");
                                    if (!ArrayUtils.isEmpty(stringArrayList)) {
                                        intent.putStringArrayListExtra(
                                                "android.content.pm.extra.WARNINGS",
                                                stringArrayList);
                                    }
                                }
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                                i10, i11, "result of session: ", "{", "}, "),
                                        str5,
                                        "PackageInstallerSession");
                                try {
                                    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                                    makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(
                                            false);
                                    intentSender2.sendIntent(
                                            context2,
                                            0,
                                            intent,
                                            null,
                                            null,
                                            null,
                                            makeBasic.toBundle());
                                } catch (IntentSender.SendIntentException unused) {
                                }
                                PackageInstallerSession.notifyKnoxSignatureVerificationFailure(
                                        i10, i12, str4);
                                return true;
                            case 5:
                                PackageInstallerSession.this.onSessionValidationFailure(
                                        message.arg1, (String) message.obj);
                                return true;
                            case 6:
                                PackageInstallerSession packageInstallerSession5 =
                                        PackageInstallerSession.this;
                                if (!packageInstallerSession5.sendPendingUserActionIntentIfNeeded(
                                        true)) {
                                    synchronized (packageInstallerSession5.mLock) {
                                        intentSender =
                                                packageInstallerSession5
                                                        .mPreapprovalRemoteStatusReceiver;
                                    }
                                    Intent intent2 = new Intent();
                                    intent2.putExtra(
                                            "android.content.pm.extra.SESSION_ID",
                                            packageInstallerSession5.sessionId);
                                    intent2.putExtra("android.content.pm.extra.STATUS", 0);
                                    intent2.putExtra("android.content.pm.extra.PRE_APPROVAL", true);
                                    try {
                                        BroadcastOptions makeBasic2 = BroadcastOptions.makeBasic();
                                        makeBasic2.setPendingIntentBackgroundActivityLaunchAllowed(
                                                false);
                                        intentSender.sendIntent(
                                                packageInstallerSession5.mContext,
                                                0,
                                                intent2,
                                                null,
                                                null,
                                                null,
                                                makeBasic2.toBundle());
                                    } catch (IntentSender.SendIntentException unused2) {
                                    }
                                }
                                return true;
                            default:
                                return true;
                        }
                    }
                };
        this.mCallback = internalCallback;
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mInstaller = packageManagerService != null ? packageManagerService.mInstaller : null;
        this.mSessionProvider = packageSessionProvider;
        this.mSilentUpdatePolicy = silentUpdatePolicy;
        this.mHandler = new Handler(looper, callback);
        this.mStagingManager = stagingManager;
        this.sessionId = i;
        this.userId = i2;
        this.mOriginalInstallerUid = i3;
        this.mInstallerUid = i3;
        Objects.requireNonNull(installSource);
        this.mInstallSource = installSource;
        this.mOriginalInstallerPackageName = installSource.mInstallerPackageName;
        this.params = sessionParams;
        this.createdMillis = j;
        this.updatedMillis = j;
        this.committedMillis = j2;
        this.stageDir = file;
        this.stageCid = str;
        this.mShouldBeSealed = z4;
        if (iArr != null) {
            for (int i6 : iArr) {
                this.mChildSessions.put(i6, null);
            }
        }
        this.mParentSessionId = i4;
        if (installationFileArr != null) {
            this.mFiles.ensureCapacity(installationFileArr.length);
            int length = installationFileArr.length;
            for (int i7 = 0; i7 < length; i7++) {
                if (!this.mFiles.add(new FileEntry(i7, installationFileArr[i7]))) {
                    throw new IllegalArgumentException(
                            "Trying to add a duplicate installation file");
                }
            }
        }
        if (arrayMap != null) {
            this.mChecksums.putAll(arrayMap);
        }
        if (!sessionParams.isMultiPackage) {
            if ((file == null) == (str == null)) {
                throw new IllegalArgumentException(
                        "Exactly one of stageDir or stageCid stage must be set");
            }
        }
        this.mPrepared = z;
        this.mCommitted.set(z2);
        this.mDestroyed = z3;
        this.mSessionReady = z5;
        this.mSessionApplied = z7;
        this.mSessionFailed = z6;
        this.mSessionErrorCode = i5;
        this.mSessionErrorMessage = str2 != null ? str2 : "";
        this.mStagedSession = sessionParams.isStaged ? new StagedSession() : null;
        this.mPreVerifiedDomains = domainSet;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.defaultContainerGid =
                    UserHandle.getSharedAppGid(
                            this.mPm
                                    .snapshotComputer()
                                    .getPackageUid("com.samsung.android.container", 1048576L, 0));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (isDataLoaderInstallation(this.params)) {
                if (isApexSession()) {
                    throw new IllegalArgumentException(
                            "DataLoader installation of APEX modules is not allowed.");
                }
                if (isSystemDataLoaderInstallation(this.params)
                        && this.mContext.checkCallingOrSelfPermission(
                                        "com.android.permission.USE_SYSTEM_DATA_LOADERS")
                                != 0) {
                    throw new SecurityException(
                            "You need the com.android.permission.USE_SYSTEM_DATA_LOADERS permission"
                                + " to use system data loaders");
                }
            }
            if (isIncrementalInstallation() && !IncrementalManager.isAllowed()) {
                throw new IllegalArgumentException("Incremental installation not allowed.");
            }
            PackageInstaller.SessionParams sessionParams2 = this.params;
            if ((sessionParams2.installFlags & 134217728) != 0) {
                if (sessionParams.mode != 1) {
                    throw new IllegalArgumentException(
                            "Archived installation can only be full install.");
                }
                if (!isDataLoaderInstallation(sessionParams2)
                        || this.params.dataLoaderParams.getType() != 1
                        || !isSystemDataLoaderInstallation(this.params)) {
                    throw new IllegalArgumentException(
                            "Archived installation can only use Streaming System DataLoader.");
                }
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void copyFiles(List list, File file) {
        for (File file2 : file.listFiles()) {
            if (file2.getName().endsWith(".tmp")) {
                file2.delete();
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            File file3 = (File) it.next();
            File createTempFile = File.createTempFile("inherit", ".tmp", file);
            Slog.d("PackageInstallerSession", "Copying " + file3 + " to " + createTempFile);
            if (!FileUtils.copyFile(file3, createTempFile)) {
                throw new IOException("Failed to copy " + file3 + " to " + createTempFile);
            }
            try {
                Os.chmod(
                        createTempFile.getAbsolutePath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                File file4 = new File(file, file3.getName());
                Slog.d("PackageInstallerSession", "Renaming " + createTempFile + " to " + file4);
                if (!createTempFile.renameTo(file4)) {
                    throw new IOException("Failed to rename " + createTempFile + " to " + file4);
                }
            } catch (ErrnoException unused) {
                throw new IOException(
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                createTempFile, "Failed to chmod "));
            }
        }
        Slog.d("PackageInstallerSession", "Copied " + list.size() + " files into " + file);
    }

    public static ArrayList filterFiles(File file, String[] strArr, FileFilter fileFilter) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            File file2 = new File(file, str);
            if (fileFilter.accept(file2)) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    public static String getRelativePath(File file, File file2) {
        String absolutePath = file.getAbsolutePath();
        String absolutePath2 = file2.getAbsolutePath();
        if (absolutePath.contains("/.")) {
            throw new IOException("Invalid path (was relative) : ".concat(absolutePath));
        }
        if (absolutePath.startsWith(absolutePath2)) {
            return absolutePath.substring(absolutePath2.length());
        }
        throw new IOException(
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "File: ", absolutePath, " outside base: ", absolutePath2));
    }

    public static String getRemoveMarkerName(String str) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".removed");
        if (FileUtils.isValidExtFilename(m$1)) {
            return m$1;
        }
        throw new IllegalArgumentException(
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Invalid marker: ", m$1));
    }

    public static boolean isDataLoaderInstallation(PackageInstaller.SessionParams sessionParams) {
        return sessionParams.dataLoaderParams != null;
    }

    public static boolean isEmergencyInstallerEnabled(
            int i, int i2, Computer computer, String str) {
        PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null
                || packageStateInternal.pkg == null
                || !packageStateInternal.isSystem()) {
            return false;
        }
        int uid = UserHandle.getUid(i, packageStateInternal.mAppId);
        String emergencyInstaller = packageStateInternal.pkg.getEmergencyInstaller();
        if (emergencyInstaller == null
                || !ArrayUtils.contains(computer.getPackagesForUid(i2), emergencyInstaller)) {
            return false;
        }
        return (computer.checkUidPermission("android.permission.INSTALL_PACKAGES", uid) == 0
                        || computer.checkUidPermission(
                                        "android.permission.INSTALL_PACKAGE_UPDATES", uid)
                                == 0
                        || computer.checkUidPermission(
                                        "android.permission.INSTALL_SELF_UPDATES", uid)
                                == 0)
                && computer.checkUidPermission("android.permission.EMERGENCY_INSTALL_PACKAGES", i2)
                        == 0;
    }

    public static boolean isLinkPossible(List list, File file) {
        try {
            StructStat stat = Os.stat(file.getAbsolutePath());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (Os.stat(((File) it.next()).getAbsolutePath()).st_dev != stat.st_dev) {
                    return false;
                }
            }
            return true;
        } catch (ErrnoException e) {
            Slog.w("PackageInstallerSession", "Failed to detect if linking possible: " + e);
            return false;
        }
    }

    public static boolean isSystemDataLoaderInstallation(
            PackageInstaller.SessionParams sessionParams) {
        if (isDataLoaderInstallation(sessionParams)) {
            return "android"
                    .equals(sessionParams.dataLoaderParams.getComponentName().getPackageName());
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void notifyKnoxSignatureVerificationFailure(int r1, int r2, java.lang.String r3) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 == 0) goto L7
            return
        L7:
            r0 = -130(0xffffffffffffff7e, float:NaN)
            if (r1 == r0) goto L1b
            r0 = -118(0xffffffffffffff8a, float:NaN)
            if (r1 == r0) goto L1b
            r0 = -7
            if (r1 == r0) goto L17
            switch(r1) {
                case -105: goto L1b;
                case -104: goto L1b;
                case -103: goto L1b;
                default: goto L15;
            }
        L15:
            r1 = 0
            goto L1d
        L17:
            java.lang.String r1 = "update"
            goto L1d
        L1b:
            java.lang.String r1 = "install"
        L1d:
            if (r1 == 0) goto L28
            r0 = 54
            java.lang.Object[] r1 = new java.lang.Object[]{r3, r1}
            android.sec.enterprise.auditlog.AuditLog.logEventAsUser(r2, r0, r1)
        L28:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.notifyKnoxSignatureVerificationFailure(int,"
                    + " int, java.lang.String):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static PackageInstallerSession readFromXml(
            TypedXmlPullParser typedXmlPullParser,
            PackageInstallerService.InternalCallback internalCallback,
            Context context,
            PackageManagerService packageManagerService,
            Looper looper,
            StagingManager stagingManager,
            File file,
            PackageSessionProvider packageSessionProvider,
            SilentUpdatePolicy silentUpdatePolicy) {
        int i;
        PackageInstaller.SessionParams sessionParams;
        int[] iArr;
        ArrayMap arrayMap;
        int i2;
        String str;
        PackageInstaller.SessionParams sessionParams2;
        char c;
        ArrayMap arrayMap2;
        ArraySet arraySet;
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "sessionId");
        int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "userId");
        String readStringAttribute =
                XmlUtils.readStringAttribute(typedXmlPullParser, "installerPackageName");
        int attributeInt3 =
                typedXmlPullParser.getAttributeInt((String) null, "installerPackageUid", -1);
        String readStringAttribute2 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "updateOwnererPackageName");
        String readStringAttribute3 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "installerAttributionTag");
        int attributeInt4 =
                typedXmlPullParser.getAttributeInt(
                        (String) null,
                        "installerUid",
                        packageManagerService
                                .snapshotComputer()
                                .getPackageUid(readStringAttribute, 8192L, attributeInt2));
        String readStringAttribute4 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "installInitiatingPackageName");
        String readStringAttribute5 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "installOriginatingPackageName");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "createdMillis");
        typedXmlPullParser.getAttributeLong((String) null, "updatedMillis");
        long attributeLong2 =
                typedXmlPullParser.getAttributeLong((String) null, "committedMillis", 0L);
        String readStringAttribute6 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "sessionStageDir");
        File file2 = readStringAttribute6 != null ? new File(readStringAttribute6) : null;
        String readStringAttribute7 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "sessionStageCid");
        boolean attributeBoolean =
                typedXmlPullParser.getAttributeBoolean((String) null, "prepared", true);
        boolean attributeBoolean2 =
                typedXmlPullParser.getAttributeBoolean((String) null, "committed", false);
        boolean attributeBoolean3 =
                typedXmlPullParser.getAttributeBoolean((String) null, "destroyed", false);
        boolean attributeBoolean4 =
                typedXmlPullParser.getAttributeBoolean((String) null, "sealed", false);
        int attributeInt5 =
                typedXmlPullParser.getAttributeInt((String) null, "parentSessionId", -1);
        PackageInstaller.SessionParams sessionParams3 = new PackageInstaller.SessionParams(-1);
        sessionParams3.isMultiPackage =
                typedXmlPullParser.getAttributeBoolean((String) null, "multiPackage", false);
        sessionParams3.isStaged =
                typedXmlPullParser.getAttributeBoolean((String) null, "stagedSession", false);
        sessionParams3.sessionFlags =
                typedXmlPullParser.getAttributeInt((String) null, "sessionFlags", 0);
        String str2 = "mode";
        sessionParams3.mode = typedXmlPullParser.getAttributeInt((String) null, "mode");
        sessionParams3.installFlags =
                typedXmlPullParser.getAttributeInt((String) null, "installFlags");
        sessionParams3.installLocation =
                typedXmlPullParser.getAttributeInt((String) null, "installLocation");
        sessionParams3.sizeBytes = typedXmlPullParser.getAttributeLong((String) null, "sizeBytes");
        sessionParams3.appPackageName =
                XmlUtils.readStringAttribute(typedXmlPullParser, "appPackageName");
        sessionParams3.appIcon = XmlUtils.readBitmapAttribute(typedXmlPullParser, "appIcon");
        sessionParams3.appLabel = XmlUtils.readStringAttribute(typedXmlPullParser, "appLabel");
        sessionParams3.originatingUri =
                XmlUtils.readUriAttribute(typedXmlPullParser, "originatingUri");
        sessionParams3.originatingUid =
                typedXmlPullParser.getAttributeInt((String) null, "originatingUid", -1);
        sessionParams3.referrerUri = XmlUtils.readUriAttribute(typedXmlPullParser, "referrerUri");
        sessionParams3.abiOverride =
                XmlUtils.readStringAttribute(typedXmlPullParser, "abiOverride");
        sessionParams3.volumeUuid = XmlUtils.readStringAttribute(typedXmlPullParser, "volumeUuid");
        sessionParams3.installReason =
                typedXmlPullParser.getAttributeInt((String) null, "installRason");
        sessionParams3.packageSource =
                typedXmlPullParser.getAttributeInt((String) null, "packageSource");
        sessionParams3.applicationEnabledSettingPersistent =
                typedXmlPullParser.getAttributeBoolean(
                        (String) null, "applicationEnabledSettingPersistent", false);
        if (typedXmlPullParser.getAttributeBoolean((String) null, "isDataLoader", false)) {
            sessionParams3.dataLoaderParams =
                    new DataLoaderParams(
                            typedXmlPullParser.getAttributeInt((String) null, "dataLoaderType"),
                            new ComponentName(
                                    XmlUtils.readStringAttribute(
                                            typedXmlPullParser, "dataLoaderPackageName"),
                                    XmlUtils.readStringAttribute(
                                            typedXmlPullParser, "dataLoaderClassName")),
                            XmlUtils.readStringAttribute(
                                    typedXmlPullParser, "dataLoaderArguments"));
        }
        File file3 =
                new File(
                        file,
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                attributeInt, "app_icon.", ".png"));
        if (file3.exists()) {
            sessionParams3.appIcon = BitmapFactory.decodeFile(file3.getAbsolutePath());
            sessionParams3.appIconLastModified = file3.lastModified();
        }
        boolean attributeBoolean5 =
                typedXmlPullParser.getAttributeBoolean((String) null, "isReady", false);
        boolean attributeBoolean6 =
                typedXmlPullParser.getAttributeBoolean((String) null, "isFailed", false);
        boolean attributeBoolean7 =
                typedXmlPullParser.getAttributeBoolean((String) null, "isApplied", false);
        int attributeInt6 = typedXmlPullParser.getAttributeInt((String) null, "errorCode", 0);
        String readStringAttribute8 =
                XmlUtils.readStringAttribute(typedXmlPullParser, "errorMessage");
        if ((attributeBoolean5 || attributeBoolean7 || attributeBoolean6)
                && ((!attributeBoolean5 || attributeBoolean7 || attributeBoolean6)
                        && ((attributeBoolean5 || !attributeBoolean7 || attributeBoolean6)
                                && (attributeBoolean5
                                        || attributeBoolean7
                                        || !attributeBoolean6)))) {
            throw new IllegalArgumentException("Can't restore staged session with invalid state.");
        }
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet2 = new ArraySet();
        ArraySet arraySet3 = new ArraySet();
        ArrayList arrayList2 = new ArrayList();
        IntArray intArray = new IntArray();
        ArrayList arrayList3 = new ArrayList();
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        ArraySet arraySet4 = new ArraySet();
        int depth = typedXmlPullParser.getDepth();
        int i3 = 3;
        while (true) {
            int next = typedXmlPullParser.next();
            i = i3;
            if (next != 1) {
                int i4 = 3;
                if (next == 3) {
                    if (typedXmlPullParser.getDepth() > depth) {
                        i4 = 3;
                    }
                }
                if (next == i4 || next == 4) {
                    i2 = depth;
                    str = str2;
                    sessionParams2 = sessionParams3;
                    arraySet4 = arraySet4;
                    arrayMap3 = arrayMap3;
                    i3 = i;
                } else {
                    String name = typedXmlPullParser.getName();
                    name.getClass();
                    i2 = depth;
                    switch (name.hashCode()) {
                        case -1558680102:
                            sessionParams2 = sessionParams3;
                            if (name.equals("childSession")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1361644609:
                            sessionParams2 = sessionParams3;
                            if (name.equals("sessionChecksumSignature")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -606495946:
                            sessionParams2 = sessionParams3;
                            if (name.equals("granted-runtime-permission")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -43804892:
                            sessionParams2 = sessionParams3;
                            if (name.equals("preVerifiedDomains")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case -22892238:
                            sessionParams2 = sessionParams3;
                            if (name.equals("sessionFile")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 158177050:
                            sessionParams2 = sessionParams3;
                            if (name.equals("whitelisted-restricted-permission")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 641551609:
                            sessionParams2 = sessionParams3;
                            if (name.equals("sessionChecksum")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1529564053:
                            sessionParams2 = sessionParams3;
                            if (name.equals("auto-revoke-permissions-mode")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1620305696:
                            sessionParams2 = sessionParams3;
                            if (name.equals("grant-permission")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 1658008624:
                            sessionParams2 = sessionParams3;
                            if (name.equals("deny-permission")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            sessionParams2 = sessionParams3;
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            intArray.add(
                                    typedXmlPullParser.getAttributeInt(
                                            (String) null, "sessionId", -1));
                            i3 = i;
                            break;
                        case 1:
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            arrayMap4.put(
                                    XmlUtils.readStringAttribute(typedXmlPullParser, "name"),
                                    XmlUtils.readByteArrayAttribute(
                                            typedXmlPullParser, "signature"));
                            i3 = i;
                            break;
                        case 2:
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            arrayList.add(XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            i3 = i;
                            break;
                        case 3:
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            arraySet.add(
                                    XmlUtils.readStringAttribute(typedXmlPullParser, "domain"));
                            i3 = i;
                            break;
                        case 4:
                            str = str2;
                            arrayMap2 = arrayMap3;
                            arrayList3.add(
                                    new InstallationFile(
                                            typedXmlPullParser.getAttributeInt(
                                                    (String) null, "location", 0),
                                            XmlUtils.readStringAttribute(
                                                    typedXmlPullParser, "name"),
                                            typedXmlPullParser.getAttributeLong(
                                                    (String) null, "lengthBytes", -1L),
                                            XmlUtils.readByteArrayAttribute(
                                                    typedXmlPullParser, "metadata"),
                                            XmlUtils.readByteArrayAttribute(
                                                    typedXmlPullParser, "signature")));
                            arraySet = arraySet4;
                            i3 = i;
                            break;
                        case 5:
                            str = str2;
                            arrayList2.add(
                                    XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            arrayMap2 = arrayMap3;
                            arraySet = arraySet4;
                            i3 = i;
                            break;
                        case 6:
                            String readStringAttribute9 =
                                    XmlUtils.readStringAttribute(typedXmlPullParser, "name");
                            str = str2;
                            Checksum checksum =
                                    new Checksum(
                                            typedXmlPullParser.getAttributeInt(
                                                    (String) null, "checksumKind", 0),
                                            XmlUtils.readByteArrayAttribute(
                                                    typedXmlPullParser, "checksumValue"));
                            List list = (List) arrayMap3.get(readStringAttribute9);
                            if (list == null) {
                                list = new ArrayList();
                                arrayMap3.put(readStringAttribute9, list);
                            }
                            list.add(checksum);
                            arrayMap2 = arrayMap3;
                            arraySet = arraySet4;
                            i3 = i;
                            break;
                        case 7:
                            i3 = typedXmlPullParser.getAttributeInt((String) null, str2);
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            break;
                        case '\b':
                            arraySet2.add(XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            i3 = i;
                            break;
                        case '\t':
                            arraySet3.add(XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            i3 = i;
                            break;
                        default:
                            arrayMap2 = arrayMap3;
                            str = str2;
                            arraySet = arraySet4;
                            i3 = i;
                            break;
                    }
                    arraySet4 = arraySet;
                    arrayMap3 = arrayMap2;
                }
                depth = i2;
                sessionParams3 = sessionParams2;
                str2 = str;
            }
        }
        ArrayMap arrayMap5 = arrayMap3;
        PackageInstaller.SessionParams sessionParams4 = sessionParams3;
        ArraySet arraySet5 = arraySet4;
        if (arrayList.size() > 0) {
            sessionParams = sessionParams4;
            sessionParams.setPermissionStates(arrayList, Collections.emptyList());
        } else {
            sessionParams = sessionParams4;
            sessionParams.setPermissionStates(arraySet2, arraySet3);
        }
        if (arrayList2.size() > 0) {
            sessionParams.whitelistedRestrictedPermissions = arrayList2;
        }
        sessionParams.autoRevokePermissionsMode = i;
        if (intArray.size() > 0) {
            iArr = new int[intArray.size()];
            int size = intArray.size();
            for (int i5 = 0; i5 < size; i5++) {
                iArr[i5] = intArray.get(i5);
            }
        } else {
            iArr = EMPTY_CHILD_SESSION_ARRAY;
        }
        InstallationFile[] installationFileArr =
                !arrayList3.isEmpty()
                        ? (InstallationFile[]) arrayList3.toArray(EMPTY_INSTALLATION_FILE_ARRAY)
                        : null;
        if (arrayMap5.isEmpty()) {
            arrayMap = null;
        } else {
            arrayMap = new ArrayMap(arrayMap5.size());
            int size2 = arrayMap5.size();
            for (int i6 = 0; i6 < size2; i6++) {
                String str3 = (String) arrayMap5.keyAt(i6);
                List list2 = (List) arrayMap5.valueAt(i6);
                arrayMap.put(
                        str3,
                        new PerFileChecksum(
                                (Checksum[]) list2.toArray(new Checksum[list2.size()]),
                                (byte[]) arrayMap4.get(str3)));
            }
        }
        return new PackageInstallerSession(
                internalCallback,
                context,
                packageManagerService,
                packageSessionProvider,
                silentUpdatePolicy,
                looper,
                stagingManager,
                attributeInt,
                attributeInt2,
                attributeInt4,
                InstallSource.create(
                        readStringAttribute4,
                        readStringAttribute5,
                        readStringAttribute,
                        attributeInt3,
                        readStringAttribute2,
                        readStringAttribute3,
                        sessionParams.packageSource,
                        false,
                        false),
                sessionParams,
                attributeLong,
                attributeLong2,
                file2,
                readStringAttribute7,
                installationFileArr,
                arrayMap,
                attributeBoolean,
                attributeBoolean2,
                attributeBoolean3,
                attributeBoolean4,
                iArr,
                attributeInt5,
                attributeBoolean5,
                attributeBoolean6,
                attributeBoolean7,
                attributeInt6,
                readStringAttribute8,
                arraySet5.isEmpty() ? null : new DomainSet(arraySet5));
    }

    public static void resizeContainerForSd(long j, String str) {
        try {
            if (PackageHelperExt.isContainerMounted(str)) {
                PackageHelperExt.unMountSdDir(str, true);
            }
            PackageHelperExt.resizeSdDir(j, str, AsecInstallHelper.getEncryptKey());
            PackageHelperExt.mountSdDir(str, AsecInstallHelper.getEncryptKey(), 1000, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PackageManagerException(
                    -18,
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Failed to find mounted ", str));
        }
    }

    public static SigningDetails unsafeGetCertsWithoutVerification(String str) {
        ParseResult unsafeGetCertsWithoutVerification =
                ApkSignatureVerifier.unsafeGetCertsWithoutVerification(
                        ParseTypeImpl.forDefaultParsing(), str, 1);
        if (unsafeGetCertsWithoutVerification.isError()) {
            throw new PackageManagerException(
                    -2,
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Couldn't obtain signatures from APK : ", str));
        }
        return (SigningDetails) unsafeGetCertsWithoutVerification.getResult();
    }

    public final void abandon() {
        synchronized (this.mLock) {
            try {
                assertNotChild("abandon");
                assertCallerIsOwnerOrRootOrSystem();
                if (isInTerminalState()) {
                    return;
                }
                this.mDestroyed = true;
                PackageInstallerSession$$ExternalSyntheticLambda0
                        packageInstallerSession$$ExternalSyntheticLambda0 =
                                new PackageInstallerSession$$ExternalSyntheticLambda0(this);
                if (this.mStageDirInUse) {
                    this.mPendingAbandonCallback =
                            packageInstallerSession$$ExternalSyntheticLambda0;
                    this.mCallback.onSessionChanged(this);
                } else {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        packageInstallerSession$$ExternalSyntheticLambda0.run();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void acquireTransactionLock() {
        if (!this.mTransactionLock.compareAndSet(false, true)) {
            throw new UnsupportedOperationException("Concurrent access not supported");
        }
    }

    public final void addChildSessionId(int i) {
        if (!this.params.isMultiPackage) {
            throw new IllegalStateException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            this.sessionId,
                            new StringBuilder("Single-session "),
                            " can't have child."));
        }
        PackageInstallerSession session =
                ((PackageInstallerService) this.mSessionProvider).getSession(i);
        if (session == null) {
            throw new IllegalStateException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Unable to add child session ", " as it does not exist."));
        }
        PackageInstaller.SessionParams sessionParams = session.params;
        if (sessionParams.isMultiPackage) {
            throw new IllegalStateException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Multi-session ", " can't be a child."));
        }
        PackageInstaller.SessionParams sessionParams2 = this.params;
        if (sessionParams2.isStaged != sessionParams.isStaged) {
            StringBuilder sb = new StringBuilder("Multipackage Inconsistency: session ");
            sb.append(session.sessionId);
            sb.append(" and session ");
            throw new IllegalStateException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            this.sessionId, sb, " have inconsistent staged settings"));
        }
        if (sessionParams2.getEnableRollback() != session.params.getEnableRollback()) {
            StringBuilder sb2 = new StringBuilder("Multipackage Inconsistency: session ");
            sb2.append(session.sessionId);
            sb2.append(" and session ");
            throw new IllegalStateException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            this.sessionId, sb2, " have inconsistent rollback settings"));
        }
        boolean z = true;
        boolean z2 =
                sessionContains(new PackageInstallerSession$$ExternalSyntheticLambda3(1))
                        || !session.isApexSession();
        boolean z3 =
                sessionContains(new PackageInstallerSession$$ExternalSyntheticLambda3(0))
                        || session.isApexSession();
        if (!this.params.isStaged && z2 && z3) {
            throw new IllegalStateException(
                    "Mix of APK and APEX is not supported for non-staged multi-package session");
        }
        try {
            acquireTransactionLock();
            session.acquireTransactionLock();
            int i2 = this.sessionId;
            synchronized (session.mLock) {
                try {
                    if (session.hasParentSessionId()) {
                        if (session.mParentSessionId == i2) {}
                        z = false;
                    }
                    if (!session.mCommitted.get() && !session.mDestroyed) {}
                    z = false;
                } finally {
                }
            }
            if (!z) {
                throw new IllegalStateException(
                        "Unable to add child session " + i + " as it is in an invalid state.");
            }
            synchronized (this.mLock) {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("addChildSessionId");
                if (this.mChildSessions.indexOfKey(i) >= 0) {
                    releaseTransactionLock();
                    session.releaseTransactionLock();
                } else {
                    session.setParentSessionId(this.sessionId);
                    this.mChildSessions.put(i, session);
                    releaseTransactionLock();
                    session.releaseTransactionLock();
                }
            }
        } catch (Throwable th) {
            releaseTransactionLock();
            session.releaseTransactionLock();
            throw th;
        }
    }

    public final void addClientProgress(float f) {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mProgressLock) {
            setClientProgressLocked(this.mClientProgress + f);
        }
    }

    public final void addFile(int i, String str, long j, byte[] bArr, byte[] bArr2) {
        addFile_enforcePermission();
        if (!isDataLoaderInstallation(this.params)) {
            throw new IllegalStateException(
                    "Cannot add files to non-data loader installation session.");
        }
        if (isDataLoaderInstallation(this.params)
                && this.params.dataLoaderParams.getType() == 1
                && i != 0) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Non-incremental installation only supports /data/app placement: ",
                            str));
        }
        if (bArr == null) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "DataLoader installation requires valid metadata: ", str));
        }
        if (!FileUtils.isValidExtFilename(str)) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Invalid name: ", str));
        }
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("addFile");
                ArraySet arraySet = this.mFiles;
                if (!arraySet.add(
                        new FileEntry(
                                arraySet.size(), new InstallationFile(i, str, j, bArr, bArr2)))) {
                    throw new IllegalArgumentException("File already added: " + str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void assertCallerIsOwnerOrRoot() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != this.mInstallerUid) {
            throw new SecurityException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            callingUid, "Session does not belong to uid "));
        }
    }

    public final void assertCallerIsOwnerOrRootOrSystem() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != this.mInstallerUid && callingUid != 1000) {
            throw new SecurityException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            callingUid, "Session does not belong to uid "));
        }
    }

    public final void assertCallerIsOwnerRootOrVerifier() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == this.mInstallerUid) {
            return;
        }
        if (!isSealed()
                || this.mContext.checkCallingOrSelfPermission(
                                "android.permission.PACKAGE_VERIFICATION_AGENT")
                        != 0) {
            throw new SecurityException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            callingUid, "Session does not belong to uid "));
        }
    }

    public final void assertCanWrite(boolean z) {
        int callingUid;
        if (isDataLoaderInstallation(this.params)) {
            throw new IllegalStateException(
                    "Cannot write regular files in a data loader installation session.");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("assertCanWrite");
        }
        if (z
                && (callingUid = Binder.getCallingUid()) != 0
                && callingUid != 1000
                && callingUid != 2000) {
            throw new SecurityException("Reverse mode only supported from shell or system");
        }
    }

    public final void assertNoWriteFileTransfersOpenLocked() {
        Iterator it = this.mFds.iterator();
        while (it.hasNext()) {
            if (!((RevocableFileDescriptor) it.next()).isRevoked()) {
                throw new SecurityException("Files still open");
            }
        }
        Iterator it2 = this.mBridges.iterator();
        while (it2.hasNext()) {
            if (!((FileBridge) it2.next()).isClosed()) {
                throw new SecurityException("Files still open");
            }
        }
    }

    public final void assertNotChild(String str) {
        if (hasParentSessionId()) {
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            str, " can't be called on a child session, id=");
            m.append(this.sessionId);
            m.append(" parentId=");
            m.append(getParentSessionId());
            throw new IllegalStateException(m.toString());
        }
    }

    public final void assertNotLocked(String str) {
        if (Thread.holdsLock(this.mLock)) {
            throw new IllegalStateException(str.concat(" is holding mLock"));
        }
    }

    public final void assertPackageConsistentLocked(String str, long j, String str2) {
        if (!this.mPackageName.equals(str2)) {
            throw new PackageManagerException(
                    -2, str + " package " + str2 + " inconsistent with " + this.mPackageName);
        }
        String str3 = this.params.appPackageName;
        if (str3 != null && !str3.equals(str2)) {
            throw new PackageManagerException(
                    -2,
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            Preconditions$$ExternalSyntheticOutline0.m(str, " specified package "),
                            this.params.appPackageName,
                            " inconsistent with ",
                            str2));
        }
        if (this.mVersionCode == j) {
            return;
        }
        throw new PackageManagerException(
                -2, str + " version code " + j + " inconsistent with " + this.mVersionCode);
    }

    public final void assertPreparedAndNotCommittedOrDestroyedLocked(String str) {
        assertPreparedAndNotDestroyedLocked(str);
        if (this.mCommitted.get()) {
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " not allowed after commit"));
        }
    }

    public final void assertPreparedAndNotDestroyedLocked(String str) {
        if (!this.mPrepared) {
            throw new IllegalStateException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " before prepared"));
        }
        if (this.mDestroyed) {
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " not allowed after destruction"));
        }
    }

    public final void assertPreparedAndNotSealedLocked(String str) {
        assertPreparedAndNotCommittedOrDestroyedLocked(str);
        if (this.mSealed) {
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " not allowed after sealing"));
        }
    }

    public final long calculateInstalledSize() {
        File file;
        Preconditions.checkNotNull(this.mResolvedBaseFile);
        ParseResult parseApkLite =
                ApkLiteParseUtils.parseApkLite(
                        ParseTypeImpl.forDefaultParsing().reset(), this.mResolvedBaseFile, 0);
        if (parseApkLite.isError()) {
            throw new PackageManagerException(
                    parseApkLite.getErrorCode(),
                    parseApkLite.getErrorMessage(),
                    parseApkLite.getException());
        }
        ApkLite apkLite = (ApkLite) parseApkLite.getResult();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mResolvedStagedFiles).iterator();
        long j = 0;
        while (it.hasNext()) {
            File file2 = (File) it.next();
            if (!this.mResolvedBaseFile.equals(file2)) {
                if (PackageParser.isApkFile(file2)) {
                    arrayList.add(file2.getAbsolutePath());
                } else {
                    j = file2.length() + j;
                }
            }
        }
        Iterator it2 = ((ArrayList) this.mResolvedInheritedFiles).iterator();
        while (true) {
            long j2 = j;
            while (it2.hasNext()) {
                file = (File) it2.next();
                if (!this.mResolvedBaseFile.equals(file)) {
                    if (PackageParser.isApkFile(file)) {
                        arrayList.add(file.getAbsolutePath());
                    }
                }
            }
            try {
                return InstallLocationUtils.calculateInstalledSize(
                                new PackageLite(
                                        (String) null,
                                        apkLite.getPath(),
                                        apkLite,
                                        (String[]) null,
                                        (boolean[]) null,
                                        (String[]) null,
                                        (String[]) null,
                                        (String[]) arrayList.toArray(new String[arrayList.size()]),
                                        (int[]) null,
                                        apkLite.getTargetSdkVersion(),
                                        (Set[]) null,
                                        (Set[]) null),
                                this.params.abiOverride)
                        + j2;
            } catch (IOException e) {
                throw new PackageManagerException(-2, "Failed to calculate install size", e);
            }
            j = file.length() + j2;
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            assertCallerIsOwnerOrRoot();
        }
        deactivate();
    }

    public final void commit(IntentSender intentSender, boolean z) {
        assertNotChild("commit");
        if (CompatChanges.isChangeEnabled(240618202L, Binder.getCallingUid())
                && intentSender.isImmutable()) {
            throw new IllegalArgumentException(
                    "The commit() status receiver should come from a mutable PendingIntent");
        }
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                new StringBuilder("START COMMIT SESSION: id{"),
                this.sessionId,
                "}",
                "PackageInstallerSession");
        if (markAsSealed(intentSender, z)) {
            if (this.params.isMultiPackage) {
                synchronized (this.mLock) {
                    try {
                        boolean z2 = false;
                        for (int size = this.mChildSessions.size() - 1; size >= 0; size--) {
                            if (!((PackageInstallerSession) this.mChildSessions.valueAt(size))
                                    .markAsSealed(null, z)) {
                                z2 = true;
                            }
                        }
                        if (z2) {
                            return;
                        }
                    } finally {
                    }
                }
            }
            synchronized (this.mLock) {
                try {
                    if (this.mHasAppMetadataFile) {
                        File stagedAppMetadataFile = getStagedAppMetadataFile();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            long j =
                                    DeviceConfig.getLong(
                                            "package_manager_service",
                                            "app_metadata_byte_size_limit",
                                            32000L);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            if (stagedAppMetadataFile.length() > j) {
                                stagedAppMetadataFile.delete();
                                this.mHasAppMetadataFile = false;
                                throw new IllegalArgumentException(
                                        "App metadata size exceeds the maximum allowed limit of "
                                                + j);
                            }
                            if (isIncrementalInstallation()) {
                                stagedAppMetadataFile.renameTo(getTmpAppMetadataFile());
                            }
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                } finally {
                }
            }
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    public final void computeProgressLocked(boolean z) {
        if (isIncrementalInstallation() && this.mCommitted.get()) {
            float f = this.mIncrementalProgress;
            if (f - this.mProgress >= 0.01d) {
                this.mProgress = f;
            }
        } else {
            this.mProgress =
                    MathUtils.constrain(
                                    this.mInternalProgress * 0.2f,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    0.2f)
                            + MathUtils.constrain(
                                    this.mClientProgress * 0.8f,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    0.8f);
        }
        if (z || this.mProgress - this.mReportedProgress >= 0.01d) {
            float f2 = this.mProgress;
            this.mReportedProgress = f2;
            PackageInstallerService.Callbacks callbacks = PackageInstallerService.this.mCallbacks;
            int i = this.sessionId;
            int i2 = this.userId;
            int i3 = PackageInstallerService.Callbacks.$r8$clinit;
            callbacks.obtainMessage(4, i, i2, Float.valueOf(f2)).sendToTarget();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x011e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00ad  */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.server.pm.PackageInstallerSession$5] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.InstallingSession createInstallingSession(
            final java.util.concurrent.CompletableFuture r22) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.createInstallingSession(java.util.concurrent.CompletableFuture):com.android.server.pm.InstallingSession");
    }

    public final void createOatDirs(String str, List list, File file) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            try {
                Installer installer = this.mInstaller;
                String absolutePath = file.getAbsolutePath();
                if (installer.checkBeforeRemote()) {
                    try {
                        installer.mInstalld.createOatDir(str, absolutePath, str2);
                    } catch (Exception e) {
                        Installer.InstallerException.from(e);
                        throw null;
                    }
                }
            } catch (Installer.InstallerException e2) {
                throw new PackageManagerException(-110, e2.getMessage(), e2.getCause());
            }
        }
    }

    public final void createRemoveSplitMarkerLocked(String str) {
        try {
            File file =
                    new File(
                            PMRune.PM_INSTALL_TO_SDCARD ? resolveStageDirLocked() : this.stageDir,
                            getRemoveMarkerName(str));
            file.createNewFile();
            Os.chmod(file.getAbsolutePath(), 0);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final void deactivate() {
        int decrementAndGet;
        synchronized (this.mLock) {
            decrementAndGet = this.mActiveCount.decrementAndGet();
        }
        if (decrementAndGet == 0) {
            PackageInstallerService.Callbacks callbacks = PackageInstallerService.this.mCallbacks;
            int i = this.sessionId;
            int i2 = this.userId;
            int i3 = PackageInstallerService.Callbacks.$r8$clinit;
            callbacks.obtainMessage(3, i, i2, Boolean.FALSE).sendToTarget();
        }
    }

    public final void destroy(String str) {
        destroyInternal(str);
        Iterator it = getChildSessions().iterator();
        while (it.hasNext()) {
            ((PackageInstallerSession) it.next()).destroyInternal(str);
        }
    }

    public final void destroyInternal(String str) {
        IncrementalFileStorages incrementalFileStorages;
        if (str != null) {
            Slog.i(
                    "PackageInstallerSession",
                    "Session [" + this.sessionId + "] was destroyed because of [" + str + "]");
        }
        synchronized (this.mLock) {
            try {
                this.mSealed = true;
                if (!this.params.isStaged) {
                    this.mDestroyed = true;
                }
                Iterator it = this.mFds.iterator();
                while (it.hasNext()) {
                    ((RevocableFileDescriptor) it.next()).revoke();
                }
                Iterator it2 = this.mBridges.iterator();
                while (it2.hasNext()) {
                    ((FileBridge) it2.next()).forceClose();
                }
                incrementalFileStorages = this.mIncrementalFileStorages;
                this.mIncrementalFileStorages = null;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (incrementalFileStorages != null) {
            try {
                incrementalFileStorages.cleanUpAndMarkComplete();
            } catch (Installer.InstallerException unused) {
            }
        }
        File file = this.stageDir;
        if (file != null) {
            this.mInstaller.rmPackageDir(file.getName(), this.stageDir.getAbsolutePath());
        }
        if (!PMRune.PM_INSTALL_TO_SDCARD || this.stageCid == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageHelperExt.destroySdDir(this.stageCid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dispatchSessionFinished(int i, String str, Bundle bundle) {
        File file;
        long length;
        boolean remove;
        boolean add;
        sendUpdateToRemoteStatusReceiver(
                str, bundle, i, this.mPreapprovalRequested.get() && !this.mCommitted.get());
        synchronized (this.mLock) {
            this.mFinalStatus = i;
            this.mFinalMessage = str;
        }
        final boolean z = i == 1;
        boolean z2 = bundle == null || !bundle.getBoolean("android.intent.extra.REPLACING");
        if (z && z2 && this.mPm.mInstallerService.mOkToSendBroadcasts) {
            PackageManagerService packageManagerService = this.mPm;
            packageManagerService.mBroadcastHelper.sendSessionCommitBroadcast(
                    packageManagerService.snapshotComputer(),
                    generateInfoInternal(true, true),
                    this.userId,
                    packageManagerService.mAppPredictionServicePackage);
        }
        if (this.mUnknownSourceInstallAccepted.get()) {
            final UnknownSourceAppManager unknownSourceAppManager =
                    this.mPm.mCustomInjector.getUnknownSourceAppManager();
            String str2 = this.mPackageName;
            unknownSourceAppManager.getClass();
            if (z) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "addUnknownSourceApp : ", str2, "UnknownSourceAppManager");
                synchronized (unknownSourceAppManager.mUnknownLock) {
                    add = unknownSourceAppManager.mUnknownSourceAppSet.add(str2);
                }
                if (add) {
                    unknownSourceAppManager.mHandler.post(
                            new Runnable() { // from class:
                                             // com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UnknownSourceAppManager.this.writeUnknownPackageXmlSync(
                                            "/data/system/UnknownSourceAppList.xml");
                                }
                            });
                }
            } else {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "removeUnknownSourceApp : ", str2, "UnknownSourceAppManager");
                synchronized (unknownSourceAppManager.mUnknownLock) {
                    try {
                        remove =
                                unknownSourceAppManager.mUnknownSourceAppSet.contains(str2)
                                        ? unknownSourceAppManager.mUnknownSourceAppSet.remove(str2)
                                        : false;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (remove) {
                    unknownSourceAppManager.mHandler.post(
                            new Runnable() { // from class:
                                             // com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UnknownSourceAppManager.this.writeUnknownPackageXmlSync(
                                            "/data/system/UnknownSourceAppList.xml");
                                }
                            });
                }
            }
        }
        final PackageInstallerService.InternalCallback internalCallback = this.mCallback;
        PackageInstallerService packageInstallerService = PackageInstallerService.this;
        if (z) {
            packageInstallerService
                    .mCallbacks
                    .obtainMessage(5, this.sessionId, this.userId, Boolean.valueOf(z))
                    .sendToTarget();
        }
        packageInstallerService.mInstallHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.PackageInstallerService.InternalCallback.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PackageInstallerSession packageInstallerSession = this;
                        if (packageInstallerSession.params.isStaged && !z) {
                            PackageInstallerService.this.mStagingManager.abortSession(
                                    packageInstallerSession.mStagedSession);
                        }
                        synchronized (PackageInstallerService.this.mSessions) {
                            try {
                                if (!this.hasParentSessionId()) {
                                    PackageInstallerSession packageInstallerSession2 = this;
                                    if (packageInstallerSession2.params.isStaged) {
                                        if (!packageInstallerSession2.isDestroyed()) {
                                            if (!this.mCommitted.get()) {}
                                        }
                                    }
                                    PackageInstallerService.this.removeActiveSession(this);
                                }
                                PackageInstallerService packageInstallerService2 =
                                        PackageInstallerService.this;
                                int i2 = this.sessionId;
                                packageInstallerService2.getClass();
                                File file2 =
                                        new File(
                                                packageInstallerService2.mSessionsDir,
                                                "app_icon." + i2 + ".png");
                                if (file2.exists()) {
                                    file2.delete();
                                }
                                RequestThrottle requestThrottle =
                                        PackageInstallerService.this.mSettingsWriteRequest;
                                requestThrottle.mLastRequest.incrementAndGet();
                                requestThrottle.runInternal();
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                        boolean z3 = z;
                        if (z3) {
                            return;
                        }
                        Callbacks callbacks = PackageInstallerService.this.mCallbacks;
                        PackageInstallerSession packageInstallerSession3 = this;
                        callbacks
                                .obtainMessage(
                                        5,
                                        packageInstallerSession3.sessionId,
                                        packageInstallerSession3.userId,
                                        Boolean.valueOf(z3))
                                .sendToTarget();
                    }
                });
        if (isDataLoaderInstallation(this.params)) {
            String packageName = getPackageName();
            String str3 = (this.params.installFlags & 32) == 0 ? packageName : "";
            long currentTimeMillis = System.currentTimeMillis();
            long j = 0;
            int packageUid =
                    i != 1
                            ? -1
                            : this.mPm
                                    .snapshotComputer()
                                    .getPackageUid(packageName, 0L, this.userId);
            boolean isIncrementalInstallation = isIncrementalInstallation();
            long j2 = currentTimeMillis - this.createdMillis;
            PackageStateInternal packageStateInternal =
                    ((PackageManagerInternal)
                                    LocalServices.getService(PackageManagerInternal.class))
                            .getPackageStateInternal(packageName);
            if (packageStateInternal != null
                    && (file = ((PackageSetting) packageStateInternal).mPath) != null) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".apk")) {
                    length = file.length();
                    FrameworkStatsLog.write(
                            263, isIncrementalInstallation, str3, j2, i, length, packageUid);
                } else if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    for (int i2 = 0; i2 < listFiles.length; i2++) {
                        if (listFiles[i2].getName().toLowerCase().endsWith(".apk")) {
                            j = listFiles[i2].length() + j;
                        }
                    }
                }
            }
            length = j;
            FrameworkStatsLog.write(
                    263, isIncrementalInstallation, str3, j2, i, length, packageUid);
        }
    }

    public final ParcelFileDescriptor doWriteInternal(
            String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
        boolean z;
        FileBridge fileBridge;
        RevocableFileDescriptor revocableFileDescriptor;
        File file;
        synchronized (this.mLock) {
            try {
                z = PackageInstaller.ENABLE_REVOCABLE_FD;
                if (z) {
                    RevocableFileDescriptor revocableFileDescriptor2 =
                            new RevocableFileDescriptor();
                    this.mFds.add(revocableFileDescriptor2);
                    revocableFileDescriptor = revocableFileDescriptor2;
                    fileBridge = null;
                } else {
                    FileBridge fileBridge2 = new FileBridge();
                    this.mBridges.add(fileBridge2);
                    fileBridge = fileBridge2;
                    revocableFileDescriptor = null;
                }
            } finally {
            }
        }
        try {
            if (!FileUtils.isValidExtFilename(str)) {
                throw new IllegalArgumentException("Invalid name: " + str);
            }
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                file = new File(resolveStageDirLocked(), str);
            } else {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    File file2 = new File(this.stageDir, str);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    file = file2;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            int i =
                    str.equals("app.metadata")
                            ? FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED
                            : FrameworkStatsLog.VBMETA_DIGEST_REPORTED;
            ParcelFileDescriptor parcelFileDescriptor2 =
                    new ParcelFileDescriptor(
                            Os.open(
                                    file.getAbsolutePath(),
                                    OsConstants.O_CREAT | OsConstants.O_WRONLY,
                                    i));
            Os.chmod(file.getAbsolutePath(), i);
            if (this.stageDir != null && j2 > 0) {
                ((StorageManager) this.mContext.getSystemService(StorageManager.class))
                        .allocateBytes(
                                parcelFileDescriptor2.getFileDescriptor(),
                                j2,
                                InstallLocationUtils.translateAllocateFlags(
                                        this.params.installFlags));
            }
            if (j > 0) {
                Os.lseek(parcelFileDescriptor2.getFileDescriptor(), j, OsConstants.SEEK_SET);
            }
            if (parcelFileDescriptor == null) {
                if (!z) {
                    fileBridge.setTargetFile(parcelFileDescriptor2);
                    fileBridge.start();
                    return fileBridge.getClientSocket();
                }
                int detachFd = parcelFileDescriptor2.detachFd();
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(detachFd);
                revocableFileDescriptor.init(this.mContext, fileDescriptor);
                return revocableFileDescriptor.getRevocableFileDescriptor();
            }
            try {
                final Int64Ref int64Ref = new Int64Ref(0L);
                FileUtils.copy(
                        parcelFileDescriptor.getFileDescriptor(),
                        parcelFileDescriptor2.getFileDescriptor(),
                        j2,
                        null,
                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                        new FileUtils
                                .ProgressListener() { // from class:
                                                      // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda4
                            @Override // android.os.FileUtils.ProgressListener
                            public final void onProgress(long j3) {
                                PackageInstallerSession packageInstallerSession =
                                        PackageInstallerSession.this;
                                Int64Ref int64Ref2 = int64Ref;
                                if (packageInstallerSession.params.sizeBytes > 0) {
                                    long j4 = j3 - int64Ref2.value;
                                    int64Ref2.value = j3;
                                    synchronized (packageInstallerSession.mProgressLock) {
                                        packageInstallerSession.setClientProgressLocked(
                                                (j4 / packageInstallerSession.params.sizeBytes)
                                                        + packageInstallerSession.mClientProgress);
                                    }
                                }
                            }
                        });
                IoUtils.closeQuietly(parcelFileDescriptor2);
                IoUtils.closeQuietly(parcelFileDescriptor);
                synchronized (this.mLock) {
                    try {
                        if (z) {
                            this.mFds.remove(revocableFileDescriptor);
                        } else {
                            fileBridge.forceClose();
                            this.mBridges.remove(fileBridge);
                        }
                    } finally {
                    }
                }
                return null;
            } catch (Throwable th2) {
                IoUtils.closeQuietly(parcelFileDescriptor2);
                IoUtils.closeQuietly(parcelFileDescriptor);
                synchronized (this.mLock) {
                    try {
                        if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                            this.mFds.remove(revocableFileDescriptor);
                        } else {
                            fileBridge.forceClose();
                            this.mBridges.remove(fileBridge);
                        }
                        throw th2;
                    } finally {
                    }
                }
            }
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final void dumpLocked(IndentingPrintWriter indentingPrintWriter) {
        float f;
        float f2;
        indentingPrintWriter.println("Session " + this.sessionId + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("userId", Integer.valueOf(this.userId));
        indentingPrintWriter.printPair(
                "mOriginalInstallerUid", Integer.valueOf(this.mOriginalInstallerUid));
        indentingPrintWriter.printPair(
                "mOriginalInstallerPackageName", this.mOriginalInstallerPackageName);
        indentingPrintWriter.printPair(
                "installerPackageName", this.mInstallSource.mInstallerPackageName);
        indentingPrintWriter.printPair(
                "installInitiatingPackageName", this.mInstallSource.mInitiatingPackageName);
        indentingPrintWriter.printPair(
                "installOriginatingPackageName", this.mInstallSource.mOriginatingPackageName);
        indentingPrintWriter.printPair("mInstallerUid", Integer.valueOf(this.mInstallerUid));
        indentingPrintWriter.printPair("createdMillis", Long.valueOf(this.createdMillis));
        indentingPrintWriter.printPair("updatedMillis", Long.valueOf(this.updatedMillis));
        indentingPrintWriter.printPair("committedMillis", Long.valueOf(this.committedMillis));
        indentingPrintWriter.printPair("stageDir", this.stageDir);
        indentingPrintWriter.printPair("stageCid", this.stageCid);
        indentingPrintWriter.println();
        this.params.dump(indentingPrintWriter);
        synchronized (this.mProgressLock) {
            f = this.mClientProgress;
            f2 = this.mProgress;
        }
        indentingPrintWriter.printPair("mClientProgress", Float.valueOf(f));
        indentingPrintWriter.printPair("mProgress", Float.valueOf(f2));
        indentingPrintWriter.printPair("mCommitted", this.mCommitted);
        indentingPrintWriter.printPair("mPreapprovalRequested", this.mPreapprovalRequested);
        indentingPrintWriter.printPair("mSealed", Boolean.valueOf(this.mSealed));
        indentingPrintWriter.printPair(
                "mPermissionsManuallyAccepted", Boolean.valueOf(this.mPermissionsManuallyAccepted));
        indentingPrintWriter.printPair("mStageDirInUse", Boolean.valueOf(this.mStageDirInUse));
        indentingPrintWriter.printPair("mDestroyed", Boolean.valueOf(this.mDestroyed));
        indentingPrintWriter.printPair("mFds", Integer.valueOf(this.mFds.size()));
        indentingPrintWriter.printPair("mBridges", Integer.valueOf(this.mBridges.size()));
        indentingPrintWriter.printPair("mFinalStatus", Integer.valueOf(this.mFinalStatus));
        indentingPrintWriter.printPair("mFinalMessage", this.mFinalMessage);
        indentingPrintWriter.printPair(
                "params.isMultiPackage", Boolean.valueOf(this.params.isMultiPackage));
        indentingPrintWriter.printPair("params.isStaged", Boolean.valueOf(this.params.isStaged));
        indentingPrintWriter.printPair("mParentSessionId", Integer.valueOf(this.mParentSessionId));
        indentingPrintWriter.printPair("mChildSessionIds", getChildSessionIdsLocked());
        indentingPrintWriter.printPair("mSessionApplied", Boolean.valueOf(this.mSessionApplied));
        indentingPrintWriter.printPair("mSessionFailed", Boolean.valueOf(this.mSessionFailed));
        indentingPrintWriter.printPair("mSessionReady", Boolean.valueOf(this.mSessionReady));
        indentingPrintWriter.printPair(
                "mSessionErrorCode", Integer.valueOf(this.mSessionErrorCode));
        indentingPrintWriter.printPair("mSessionErrorMessage", this.mSessionErrorMessage);
        indentingPrintWriter.printPair("mPreapprovalDetails", this.mPreapprovalDetails);
        DomainSet domainSet = this.mPreVerifiedDomains;
        if (domainSet != null) {
            indentingPrintWriter.printPair("mPreVerifiedDomains", domainSet);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    public final void extractNativeLibraries(
            PackageLite packageLite, File file, String str, boolean z) {
        Objects.requireNonNull(packageLite);
        File file2 = new File(file, "lib");
        if (!z) {
            NativeLibraryHelper.removeNativeBinariesFromDirLI(file2, true);
        }
        if ((this.params.installFlags & 134217728) != 0) {
            return;
        }
        NativeLibraryHelper.Handle handle = null;
        try {
            try {
                handle = NativeLibraryHelper.Handle.create(packageLite);
                int copyNativeBinariesWithOverride =
                        NativeLibraryHelper.copyNativeBinariesWithOverride(
                                handle, file2, str, isIncrementalInstallation());
                if (copyNativeBinariesWithOverride == 1) {
                    return;
                }
                throw new PackageManagerException(
                        copyNativeBinariesWithOverride,
                        "Failed to extract native libraries, res="
                                + copyNativeBinariesWithOverride);
            } catch (IOException e) {
                throw new PackageManagerException(-110, "Failed to extract native libraries", e);
            }
        } finally {
            IoUtils.closeQuietly(handle);
        }
    }

    public final List fetchPackageNames() {
        String packageName;
        assertNotChild("fetchPackageNames");
        assertCallerIsOwnerOrRoot();
        List<PackageInstallerSession> childSessions =
                this.params.isMultiPackage ? getChildSessions() : Collections.singletonList(this);
        ArrayList arrayList = new ArrayList(childSessions.size());
        for (PackageInstallerSession packageInstallerSession : childSessions) {
            if (!packageInstallerSession.isSealed()) {
                throw new IllegalStateException("fetchPackageName".concat(" before sealing"));
            }
            synchronized (packageInstallerSession.mLock) {
                ParseTypeImpl forDefaultParsing = ParseTypeImpl.forDefaultParsing();
                Iterator it = ((ArrayList) packageInstallerSession.getAddedApksLocked()).iterator();
                while (it.hasNext()) {
                    ParseResult parseApkLite =
                            ApkLiteParseUtils.parseApkLite(
                                    forDefaultParsing.reset(), (File) it.next(), 0);
                    if (parseApkLite.isError()) {
                        throw new IllegalStateException(
                                "Can't parse package for session="
                                        + packageInstallerSession.sessionId,
                                parseApkLite.getException());
                    }
                    packageName = ((ApkLite) parseApkLite.getResult()).getPackageName();
                    if (packageName != null) {}
                }
                throw new IllegalStateException(
                        "Can't fetch package name for session="
                                + packageInstallerSession.sessionId);
            }
            arrayList.add(packageName);
        }
        return arrayList;
    }

    public final PackageInstaller.SessionInfo generateInfoForCaller(int i, boolean z) {
        return generateInfoInternal(z, i >= 10000 && getInstallerUid() != i);
    }

    public final PackageInstaller.SessionInfo generateInfoInternal(boolean z, boolean z2) {
        float f;
        String str;
        PackageInstaller.SessionInfo sessionInfo = new PackageInstaller.SessionInfo();
        synchronized (this.mProgressLock) {
            f = this.mProgress;
        }
        synchronized (this.mLock) {
            try {
                sessionInfo.sessionId = this.sessionId;
                sessionInfo.userId = this.userId;
                InstallSource installSource = this.mInstallSource;
                sessionInfo.installerPackageName = installSource.mInstallerPackageName;
                sessionInfo.installerAttributionTag = installSource.mInstallerAttributionTag;
                sessionInfo.resolvedBaseCodePath = null;
                int i = 0;
                if (this.mContext.checkCallingOrSelfPermission(
                                "android.permission.READ_INSTALLED_SESSION_PATHS")
                        == 0) {
                    File file = this.mResolvedBaseFile;
                    if (file == null) {
                        ArrayList arrayList = (ArrayList) getAddedApksLocked();
                        if (arrayList.size() > 0) {
                            file = (File) arrayList.get(0);
                        }
                    }
                    if (file != null) {
                        sessionInfo.resolvedBaseCodePath = file.getAbsolutePath();
                    }
                }
                sessionInfo.progress = f;
                sessionInfo.sealed = this.mSealed;
                sessionInfo.isCommitted = this.mCommitted.get();
                sessionInfo.isPreapprovalRequested = this.mPreapprovalRequested.get();
                sessionInfo.active = this.mActiveCount.get() > 0;
                PackageInstaller.SessionParams sessionParams = this.params;
                sessionInfo.mode = sessionParams.mode;
                sessionInfo.installReason = sessionParams.installReason;
                sessionInfo.installScenario = sessionParams.installScenario;
                sessionInfo.sizeBytes = sessionParams.sizeBytes;
                PackageInstaller.PreapprovalDetails preapprovalDetails = this.mPreapprovalDetails;
                if (preapprovalDetails != null) {
                    str = preapprovalDetails.getPackageName();
                } else {
                    String str2 = this.mPackageName;
                    str = str2 != null ? str2 : sessionParams.appPackageName;
                }
                sessionInfo.appPackageName = str;
                if (z) {
                    PackageInstaller.PreapprovalDetails preapprovalDetails2 =
                            this.mPreapprovalDetails;
                    sessionInfo.appIcon =
                            (preapprovalDetails2 == null || preapprovalDetails2.getIcon() == null)
                                    ? this.params.appIcon
                                    : this.mPreapprovalDetails.getIcon();
                }
                PackageInstaller.PreapprovalDetails preapprovalDetails3 = this.mPreapprovalDetails;
                sessionInfo.appLabel =
                        preapprovalDetails3 != null
                                ? preapprovalDetails3.getLabel()
                                : this.params.appLabel;
                PackageInstaller.SessionParams sessionParams2 = this.params;
                sessionInfo.installLocation = sessionParams2.installLocation;
                if (!z2) {
                    sessionInfo.originatingUri = sessionParams2.originatingUri;
                }
                sessionInfo.originatingUid = sessionParams2.originatingUid;
                if (!z2) {
                    sessionInfo.referrerUri = sessionParams2.referrerUri;
                }
                sessionInfo.grantedRuntimePermissions =
                        sessionParams2.getLegacyGrantedRuntimePermissions();
                PackageInstaller.SessionParams sessionParams3 = this.params;
                sessionInfo.whitelistedRestrictedPermissions =
                        sessionParams3.whitelistedRestrictedPermissions;
                sessionInfo.autoRevokePermissionsMode = sessionParams3.autoRevokePermissionsMode;
                sessionInfo.installFlags = sessionParams3.installFlags;
                sessionInfo.rollbackLifetimeMillis = sessionParams3.rollbackLifetimeMillis;
                sessionInfo.rollbackImpactLevel = sessionParams3.rollbackImpactLevel;
                sessionInfo.isMultiPackage = sessionParams3.isMultiPackage;
                sessionInfo.isStaged = sessionParams3.isStaged;
                sessionInfo.rollbackDataPolicy = sessionParams3.rollbackDataPolicy;
                sessionInfo.parentSessionId = this.mParentSessionId;
                sessionInfo.childSessionIds = getChildSessionIdsLocked();
                sessionInfo.isSessionApplied = this.mSessionApplied;
                sessionInfo.isSessionReady = this.mSessionReady;
                sessionInfo.isSessionFailed = this.mSessionFailed;
                sessionInfo.setSessionErrorCode(this.mSessionErrorCode, this.mSessionErrorMessage);
                sessionInfo.createdMillis = this.createdMillis;
                sessionInfo.updatedMillis = this.updatedMillis;
                sessionInfo.requireUserAction = this.params.requireUserAction;
                sessionInfo.installerUid = this.mInstallerUid;
                PackageInstaller.SessionParams sessionParams4 = this.params;
                sessionInfo.packageSource = sessionParams4.packageSource;
                sessionInfo.applicationEnabledSettingPersistent =
                        sessionParams4.applicationEnabledSettingPersistent;
                if (this.mUserActionRequirement == 3) {
                    i = 2;
                }
                sessionInfo.pendingUserActionReason = i;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sessionInfo;
    }

    public final List getAddedApksLocked() {
        return filterFiles(
                PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir,
                getNamesLocked(),
                sAddedApkFilter);
    }

    public final ParcelFileDescriptor getAppMetadataFd() {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("getAppMetadataFd");
            if (!this.mHasAppMetadataFile) {
                return null;
            }
            try {
                return openReadInternalLocked("app.metadata");
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
    }

    public final int[] getChildSessionIds() {
        int[] childSessionIdsLocked;
        synchronized (this.mLock) {
            childSessionIdsLocked = getChildSessionIdsLocked();
        }
        return childSessionIdsLocked;
    }

    public final int[] getChildSessionIdsLocked() {
        int size = this.mChildSessions.size();
        if (size == 0) {
            return EMPTY_CHILD_SESSION_ARRAY;
        }
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.mChildSessions.keyAt(i);
        }
        return iArr;
    }

    public final List getChildSessions() {
        List childSessionsLocked;
        synchronized (this.mLock) {
            childSessionsLocked = getChildSessionsLocked();
        }
        return childSessionsLocked;
    }

    public final List getChildSessionsLocked() {
        List list = Collections.EMPTY_LIST;
        if (!this.params.isMultiPackage) {
            return list;
        }
        int size = this.mChildSessions.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add((PackageInstallerSession) this.mChildSessions.valueAt(i));
        }
        return arrayList;
    }

    public final DataLoaderParamsParcel getDataLoaderParams() {
        getDataLoaderParams_enforcePermission();
        DataLoaderParams dataLoaderParams = this.params.dataLoaderParams;
        if (dataLoaderParams != null) {
            return dataLoaderParams.getData();
        }
        return null;
    }

    public final int getInstallFlags() {
        return this.params.installFlags;
    }

    public final InstallSource getInstallSource() {
        InstallSource installSource;
        synchronized (this.mLock) {
            installSource = this.mInstallSource;
        }
        return installSource;
    }

    public final InstallationFile[] getInstallationFilesLocked() {
        InstallationFile[] installationFileArr = new InstallationFile[this.mFiles.size()];
        Iterator it = this.mFiles.iterator();
        while (it.hasNext()) {
            FileEntry fileEntry = (FileEntry) it.next();
            installationFileArr[fileEntry.mIndex] = fileEntry.mFile;
        }
        return installationFileArr;
    }

    public final int getInstallerUid() {
        int i;
        synchronized (this.mLock) {
            i = this.mInstallerUid;
        }
        return i;
    }

    public final String[] getNames() {
        String[] removeString;
        assertCallerIsOwnerRootOrVerifier();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotDestroyedLocked("getNames");
                removeString =
                        ArrayUtils.removeString(
                                !this.mCommitted.get()
                                        ? getNamesLocked()
                                        : getStageDirContentsLocked(),
                                "app.metadata");
            } catch (Throwable th) {
                throw th;
            }
        }
        return removeString;
    }

    public final String[] getNamesLocked() {
        String[] list;
        if (!isDataLoaderInstallation(this.params)) {
            if (!PMRune.PM_INSTALL_TO_SDCARD) {
                return getStageDirContentsLocked();
            }
            try {
                File resolveStageDirLocked = resolveStageDirLocked();
                return (resolveStageDirLocked == null
                                || (list = resolveStageDirLocked.list()) == null)
                        ? EmptyArray.STRING
                        : list;
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
        InstallationFile[] installationFilesLocked = getInstallationFilesLocked();
        String[] strArr = new String[installationFilesLocked.length];
        int length = installationFilesLocked.length;
        for (int i = 0; i < length; i++) {
            strArr[i] = installationFilesLocked[i].getName();
        }
        return strArr;
    }

    public final PackageLite getOrParsePackageLiteLocked(File file) {
        PackageLite packageLite = this.mPackageLite;
        if (packageLite != null) {
            return packageLite;
        }
        ParseResult parsePackageLite =
                ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing(), file, 0);
        if (parsePackageLite.isError()) {
            throw new PackageManagerException(
                    -110, parsePackageLite.getErrorMessage(), parsePackageLite.getException());
        }
        return (PackageLite) parsePackageLite.getResult();
    }

    public String getPackageName() {
        String str;
        synchronized (this.mLock) {
            str = this.mPackageName;
        }
        return str;
    }

    public final int getParentSessionId() {
        int i;
        synchronized (this.mLock) {
            i = this.mParentSessionId;
        }
        return i;
    }

    public final DomainSet getPreVerifiedDomains() {
        DomainSet domainSet;
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("getPreVerifiedDomains");
            domainSet = this.mPreVerifiedDomains;
        }
        return domainSet;
    }

    public final IntentSender getRemoteStatusReceiver() {
        IntentSender intentSender;
        synchronized (this.mLock) {
            intentSender = this.mRemoteStatusReceiver;
        }
        return intentSender;
    }

    public final String[] getStageDirContentsLocked() {
        String[] list;
        String[] list2;
        if (!PMRune.PM_INSTALL_TO_SDCARD) {
            File file = this.stageDir;
            return (file == null || (list2 = file.list()) == null) ? EmptyArray.STRING : list2;
        }
        try {
            File resolveStageDirLocked = resolveStageDirLocked();
            return (resolveStageDirLocked == null || (list = resolveStageDirLocked.list()) == null)
                    ? EmptyArray.STRING
                    : list;
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public final File getStagedAppMetadataFile() {
        boolean z = PMRune.PM_INSTALL_TO_SDCARD;
        if (z) {
            try {
                resolveStageDirLocked();
            } catch (IOException e) {
                throw new ParcelableException(
                        new PackageManagerException(-18, "Failed to resolve stage location", e));
            }
        }
        return new File(z ? this.mResolvedStageDir : this.stageDir, "app.metadata");
    }

    public final File getTmpAppMetadataFile() {
        return new File(
                Environment.getDataAppDirectory(this.params.volumeUuid),
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        this.sessionId, new StringBuilder(), "-app.metadata"));
    }

    public final boolean hasParentSessionId() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mParentSessionId != -1;
        }
        return z;
    }

    public final void inheritFileLocked(File file) {
        ((ArrayList) this.mResolvedInheritedFiles).add(file);
        File file2 = new File(VerityUtils.getFsveritySignatureFilePath(file.getPath()));
        if (file2.exists()) {
            ((ArrayList) this.mResolvedInheritedFiles).add(file2);
        }
        if (Flags.extendVbChainToUpdatedApk()) {
            File file3 = new File(file.getPath() + ".idsig");
            if (file3.exists()) {
                ((ArrayList) this.mResolvedInheritedFiles).add(file3);
            }
        }
        File findDexMetadataForFile = DexMetadataHelper.findDexMetadataForFile(file);
        if (findDexMetadataForFile != null) {
            ((ArrayList) this.mResolvedInheritedFiles).add(findDexMetadataForFile);
            File file4 =
                    new File(
                            VerityUtils.getFsveritySignatureFilePath(
                                    findDexMetadataForFile.getPath()));
            if (file4.exists()) {
                ((ArrayList) this.mResolvedInheritedFiles).add(file4);
            }
        }
        int i = ApkChecksums.$r8$clinit;
        File file5 = new File(ApkChecksums.buildDigestsPathForApk(file.getAbsolutePath()));
        if (!file5.exists()) {
            file5 = null;
        }
        if (file5 != null) {
            ((ArrayList) this.mResolvedInheritedFiles).add(file5);
            File file6 =
                    new File(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                    file5.getAbsolutePath(), ".signature"));
            File file7 = file6.exists() ? file6 : null;
            if (file7 != null) {
                ((ArrayList) this.mResolvedInheritedFiles).add(file7);
            }
        }
    }

    public final CompletableFuture install() {
        final ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            CompletableFuture completableFuture = new CompletableFuture();
            arrayList.add(completableFuture);
            InstallingSession createInstallingSession = createInstallingSession(completableFuture);
            if (this.params.isMultiPackage) {
                List childSessions = getChildSessions();
                ArrayList arrayList2 = new ArrayList(childSessions.size());
                for (int i = 0; i < childSessions.size(); i++) {
                    PackageInstallerSession packageInstallerSession =
                            (PackageInstallerSession) childSessions.get(i);
                    if (packageInstallerSession != null
                            && isAttemptSamsungThemeForgery(
                                    packageInstallerSession.getPackageName())) {
                        throw new PackageManagerException(
                                -110, "Install failed with internal error");
                    }
                    CompletableFuture completableFuture2 = new CompletableFuture();
                    arrayList.add(completableFuture2);
                    InstallingSession createInstallingSession2 =
                            packageInstallerSession.createInstallingSession(completableFuture2);
                    if (createInstallingSession2 != null) {
                        arrayList2.add(createInstallingSession2);
                    }
                }
                if (!arrayList2.isEmpty()) {
                    Objects.requireNonNull(createInstallingSession);
                    createInstallingSession.installStage(arrayList2);
                }
            } else if (createInstallingSession != null) {
                PackageLite packageLite = createInstallingSession.mPackageLite;
                if (packageLite != null
                        && isAttemptSamsungThemeForgery(packageLite.getPackageName())) {
                    throw new PackageManagerException(-110, "Install failed with internal error");
                }
                createInstallingSession.installStage();
            }
        } catch (PackageManagerException e) {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(CompletableFuture.failedFuture(e));
            arrayList = arrayList3;
        }
        return CompletableFuture.allOf(
                        (CompletableFuture[])
                                arrayList.toArray(new CompletableFuture[arrayList.size()]))
                .whenComplete(
                        new BiConsumer() { // from class:
                                           // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda9
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                Bundle bundle;
                                PackageInstallerSession packageInstallerSession2 =
                                        PackageInstallerSession.this;
                                List list = arrayList;
                                Throwable th = (Throwable) obj2;
                                if (th != null) {
                                    packageInstallerSession2.getClass();
                                    PackageManagerException packageManagerException =
                                            (PackageManagerException) th.getCause();
                                    int i2 = packageManagerException.error;
                                    packageInstallerSession2.setSessionFailed(
                                            i2,
                                            PackageManager.installStatusToString(
                                                    i2, packageManagerException.getMessage()));
                                    packageInstallerSession2.dispatchSessionFinished(
                                            packageManagerException.error,
                                            packageManagerException.getMessage(),
                                            null);
                                    packageInstallerSession2.maybeFinishChildSessions(
                                            packageManagerException.error,
                                            packageManagerException.getMessage());
                                    return;
                                }
                                synchronized (packageInstallerSession2.mLock) {
                                    if (!packageInstallerSession2.mDestroyed
                                            && !packageInstallerSession2.mSessionFailed) {
                                        packageInstallerSession2.mSessionReady = false;
                                        packageInstallerSession2.mSessionApplied = true;
                                        packageInstallerSession2.mSessionFailed = false;
                                        packageInstallerSession2.mSessionErrorCode = 1;
                                        packageInstallerSession2.mSessionErrorMessage = "";
                                        Slog.d(
                                                "PackageInstallerSession",
                                                "Marking session "
                                                        + packageInstallerSession2.sessionId
                                                        + " as applied");
                                        packageInstallerSession2.destroy(null);
                                        packageInstallerSession2.mCallback.onSessionChanged(
                                                packageInstallerSession2);
                                    }
                                }
                                ArrayList<String> arrayList4 = new ArrayList<>();
                                if (packageInstallerSession2.params.isMultiPackage) {
                                    Iterator it = list.iterator();
                                    while (it.hasNext()) {
                                        PackageInstallerSession.InstallResult installResult =
                                                (PackageInstallerSession.InstallResult)
                                                        ((CompletableFuture) it.next()).join();
                                        if (installResult.session != packageInstallerSession2
                                                && (bundle = installResult.extras) != null) {
                                            ArrayList<String> stringArrayList =
                                                    bundle.getStringArrayList(
                                                            "android.content.pm.extra.WARNINGS");
                                            if (!ArrayUtils.isEmpty(stringArrayList)) {
                                                arrayList4.addAll(stringArrayList);
                                            }
                                        }
                                    }
                                }
                                Iterator it2 = list.iterator();
                                while (it2.hasNext()) {
                                    PackageInstallerSession.InstallResult installResult2 =
                                            (PackageInstallerSession.InstallResult)
                                                    ((CompletableFuture) it2.next()).join();
                                    Bundle bundle2 = installResult2.extras;
                                    if (packageInstallerSession2.params.isMultiPackage
                                            && installResult2.session == packageInstallerSession2
                                            && !arrayList4.isEmpty()) {
                                        if (bundle2 == null) {
                                            bundle2 = new Bundle();
                                        }
                                        bundle2.putStringArrayList(
                                                "android.content.pm.extra.WARNINGS", arrayList4);
                                    }
                                    installResult2.session.dispatchSessionFinished(
                                            1, "Session installed", bundle2);
                                }
                            }
                        });
    }

    public final boolean isApexSession() {
        return (this.params.installFlags & 131072) != 0;
    }

    public final boolean isApplicationEnabledSettingPersistent() {
        return this.params.applicationEnabledSettingPersistent;
    }

    public final boolean isAttemptSamsungThemeForgery(String str) {
        List list = SemSamsungThemeUtils.disableOverlayList;
        if (!"com.samsung.android.themecenter".equals(str)
                || this.mPm.snapshotComputer().getPackageInfo(str, 0L, 0) == null) {
            return this.mInstallerUid == 2000
                    && str != null
                    && str.startsWith("com.samsung.themedesigner");
        }
        return true;
    }

    public final boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public final boolean isInTerminalState() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mSessionApplied || this.mSessionFailed;
            } finally {
            }
        }
        return z;
    }

    public final boolean isIncrementalInstallation() {
        return isDataLoaderInstallation(this.params) && this.params.dataLoaderParams.getType() == 2;
    }

    public final boolean isInstallerDeviceOwnerOrAffiliatedProfileOwner() {
        DevicePolicyManagerInternal devicePolicyManagerInternal;
        assertNotLocked("isInstallerDeviceOwnerOrAffiliatedProfileOwner");
        return this.userId == UserHandle.getUserId(getInstallerUid())
                && (devicePolicyManagerInternal =
                                (DevicePolicyManagerInternal)
                                        LocalServices.getService(DevicePolicyManagerInternal.class))
                        != null
                && devicePolicyManagerInternal.canSilentlyInstallPackage(
                        getInstallSource().mInstallerPackageName, this.mInstallerUid);
    }

    public final boolean isMultiPackage() {
        return this.params.isMultiPackage;
    }

    public final boolean isRequestUpdateOwnership() {
        return (this.params.installFlags & 33554432) != 0;
    }

    public final boolean isSealed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSealed;
        }
        return z;
    }

    public final boolean isStaged() {
        return this.params.isStaged;
    }

    public final boolean isStagedAndInTerminalState() {
        return this.params.isStaged && isInTerminalState();
    }

    public final void linkFiles(String str, List list, File file, File file2) {
        IncrementalFileStorages incrementalFileStorages;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String relativePath = getRelativePath((File) it.next(), file2);
            String absolutePath = file2.getAbsolutePath();
            String absolutePath2 = file.getAbsolutePath();
            try {
                synchronized (this.mLock) {
                    incrementalFileStorages = this.mIncrementalFileStorages;
                }
                if (incrementalFileStorages == null
                        || !incrementalFileStorages.makeLink(
                                relativePath, absolutePath, absolutePath2)) {
                    this.mInstaller.linkFile(str, relativePath, absolutePath, absolutePath2);
                }
            } catch (Installer.InstallerException | IOException e) {
                throw new IOException(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                        "failed linkOrCreateDir(",
                                        relativePath,
                                        ", ",
                                        absolutePath,
                                        ", "),
                                absolutePath2,
                                ")"),
                        e);
            }
        }
        Slog.d("PackageInstallerSession", "Linked " + list.size() + " files into " + file);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005e A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:9:0x001c, B:11:0x0036, B:15:0x005e, B:17:0x0077, B:22:0x008f, B:23:0x0096, B:25:0x0083, B:29:0x0099, B:32:0x00b6, B:33:0x00b8, B:37:0x00bc, B:39:0x00c0, B:42:0x00c2, B:43:0x00c5, B:46:0x00c7, B:51:0x00cb, B:52:0x00a8, B:53:0x00af, B:54:0x00b0, B:56:0x00cc, B:57:0x00d3, B:58:0x0044, B:59:0x004c, B:35:0x00b9, B:36:0x00bb), top: B:8:0x001c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0099 A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:9:0x001c, B:11:0x0036, B:15:0x005e, B:17:0x0077, B:22:0x008f, B:23:0x0096, B:25:0x0083, B:29:0x0099, B:32:0x00b6, B:33:0x00b8, B:37:0x00bc, B:39:0x00c0, B:42:0x00c2, B:43:0x00c5, B:46:0x00c7, B:51:0x00cb, B:52:0x00a8, B:53:0x00af, B:54:0x00b0, B:56:0x00cc, B:57:0x00d3, B:58:0x0044, B:59:0x004c, B:35:0x00b9, B:36:0x00bb), top: B:8:0x001c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b0 A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:9:0x001c, B:11:0x0036, B:15:0x005e, B:17:0x0077, B:22:0x008f, B:23:0x0096, B:25:0x0083, B:29:0x0099, B:32:0x00b6, B:33:0x00b8, B:37:0x00bc, B:39:0x00c0, B:42:0x00c2, B:43:0x00c5, B:46:0x00c7, B:51:0x00cb, B:52:0x00a8, B:53:0x00af, B:54:0x00b0, B:56:0x00cc, B:57:0x00d3, B:58:0x0044, B:59:0x004c, B:35:0x00b9, B:36:0x00bb), top: B:8:0x001c, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean markAsSealed(android.content.IntentSender r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.markAsSealed(android.content.IntentSender,"
                    + " boolean):boolean");
    }

    public final void maybeFinishChildSessions(int i, String str) {
        Iterator it = getChildSessions().iterator();
        while (it.hasNext()) {
            ((PackageInstallerSession) it.next()).dispatchSessionFinished(i, str, null);
        }
    }

    public final void maybeStageFsveritySignatureLocked(File file, File file2, boolean z) {
        if (Flags.deprecateFsvSig()) {
            return;
        }
        File file3 = new File(VerityUtils.getFsveritySignatureFilePath(file.getPath()));
        if (file3.exists()) {
            stageFileLocked(
                    file3, new File(VerityUtils.getFsveritySignatureFilePath(file2.getPath())));
        } else if (z) {
            throw new PackageManagerException(
                    -118,
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            file, "Missing corresponding fs-verity signature to "));
        }
    }

    public final void onSessionValidationFailure(int i, String str) {
        destroyInternal("Failed to validate session, error: " + i + ", " + str);
        dispatchSessionFinished(i, str, null);
    }

    public final void onSessionVerificationFailure(int i, String str) {
        VaultKeeperService$$ExternalSyntheticOutline0.m(
                new StringBuilder("Failed to verify session "),
                this.sessionId,
                "PackageInstallerSession");
        dispatchSessionFinished(i, str, null);
        maybeFinishChildSessions(i, str);
    }

    public final void open() {
        boolean z;
        if (this.mActiveCount.getAndIncrement() == 0) {
            PackageInstallerService.Callbacks callbacks = PackageInstallerService.this.mCallbacks;
            int i = this.sessionId;
            int i2 = this.userId;
            int i3 = PackageInstallerService.Callbacks.$r8$clinit;
            callbacks.obtainMessage(3, i, i2, Boolean.TRUE).sendToTarget();
        }
        synchronized (this.mLock) {
            try {
                z = this.mPrepared;
                if (!z) {
                    File file = this.stageDir;
                    if (file != null) {
                        PackageInstallerService.prepareStageDir(file);
                    } else if (!this.params.isMultiPackage) {
                        if (!PMRune.PM_INSTALL_TO_SDCARD || this.stageCid == null) {
                            throw new IllegalArgumentException(
                                    "Exactly one of stageDir or stageCid stage must be set");
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            String str = this.stageCid;
                            long j = this.params.sizeBytes + 5242880;
                            boolean z2 = PackageInstallerService.LOGD;
                            if (PackageHelperExt.createSdDir(
                                            j, str, AsecInstallHelper.getEncryptKey(), 1000, true)
                                    == null) {
                                throw new IOException("Failed to create session cid: " + str);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            this.mInternalProgress = 0.25f;
                            computeProgressLocked(true);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    this.mPrepared = true;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (z) {
            return;
        }
        PackageInstallerService.this.mSettingsWriteRequest.schedule();
    }

    public final void openQuick(String str) {
        boolean z;
        if (this.mActiveCount.getAndIncrement() == 0) {
            PackageInstallerService.Callbacks callbacks = PackageInstallerService.this.mCallbacks;
            int i = this.sessionId;
            int i2 = this.userId;
            int i3 = PackageInstallerService.Callbacks.$r8$clinit;
            callbacks.obtainMessage(3, i, i2, Boolean.TRUE).sendToTarget();
        }
        synchronized (this.mLock) {
            try {
                z = this.mPrepared;
                if (!z) {
                    File file = this.stageDir;
                    if (file != null) {
                        PackageInstallerService.prepareStageDirQuick(file, str);
                    } else if (!this.params.isMultiPackage) {
                        throw new IllegalArgumentException("stageDir must be set");
                    }
                    this.mPrepared = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            return;
        }
        PackageInstallerService.this.mSettingsWriteRequest.schedule();
    }

    public final ParcelFileDescriptor openRead(String str) {
        ParcelFileDescriptor openReadInternalLocked;
        if (isDataLoaderInstallation(this.params)) {
            throw new IllegalStateException(
                    "Cannot read regular files in a data loader installation session.");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotCommittedOrDestroyedLocked("openRead");
                try {
                    openReadInternalLocked = openReadInternalLocked(str);
                } catch (IOException e) {
                    throw ExceptionUtils.wrap(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return openReadInternalLocked;
    }

    public final ParcelFileDescriptor openReadInternalLocked(String str) {
        try {
            if (FileUtils.isValidExtFilename(str)) {
                return new ParcelFileDescriptor(
                        Os.open(
                                new File(
                                                PMRune.PM_INSTALL_TO_SDCARD
                                                        ? resolveStageDirLocked()
                                                        : this.stageDir,
                                                str)
                                        .getAbsolutePath(),
                                OsConstants.O_RDONLY,
                                0));
            }
            throw new IllegalArgumentException("Invalid name: " + str);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final ParcelFileDescriptor openWrite(String str, long j, long j2) {
        assertCanWrite(false);
        try {
            return doWriteInternal(str, j, j2, null);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public final ParcelFileDescriptor openWriteAppMetadata() {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("openWriteAppMetadata");
        }
        try {
            ParcelFileDescriptor doWriteInternal = doWriteInternal("app.metadata", 0L, -1L, null);
            synchronized (this.mLock) {
                this.mHasAppMetadataFile = true;
            }
            return doWriteInternal;
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00c6 A[Catch: all -> 0x0051, TryCatch #2 {, blocks: (B:4:0x0005, B:6:0x000b, B:8:0x000f, B:10:0x0013, B:12:0x0017, B:14:0x001b, B:16:0x001f, B:18:0x0028, B:20:0x002c, B:22:0x0030, B:24:0x0036, B:78:0x0056, B:27:0x0065, B:28:0x006a, B:30:0x0077, B:32:0x007f, B:33:0x0081, B:38:0x008b, B:65:0x008f, B:40:0x009c, B:42:0x00a0, B:43:0x00a5, B:45:0x00b2, B:47:0x00b8, B:50:0x00bf, B:52:0x00c6, B:54:0x00ce, B:57:0x00d8, B:58:0x00e3, B:60:0x00e4, B:61:0x00ef, B:63:0x00a3, B:68:0x0094, B:69:0x009b, B:73:0x00f2, B:74:0x00f3, B:76:0x0068, B:81:0x005b, B:82:0x0062, B:83:0x006f, B:84:0x00f5, B:85:0x00fc, B:86:0x00fd, B:87:0x0104, B:88:0x0105, B:89:0x010c, B:90:0x010d, B:91:0x0114, B:92:0x0115, B:93:0x011c, B:94:0x011d, B:95:0x0124, B:36:0x0084, B:37:0x008a), top: B:3:0x0005, inners: #0, #1, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseApkAndExtractNativeLibraries() {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.parseApkAndExtractNativeLibraries():void");
    }

    public final boolean prepareDataLoaderLocked() {
        if (!isDataLoaderInstallation(this.params) || this.mDataLoaderFinished) {
            return true;
        }
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        for (InstallationFile installationFile : getInstallationFilesLocked()) {
            if (sAddedFilter.accept(new File(this.stageDir, installationFile.getName()))) {
                arrayList.add(installationFile.getData());
            } else if (sRemovedFilter.accept(new File(this.stageDir, installationFile.getName()))) {
                arrayList2.add(
                        installationFile
                                .getName()
                                .substring(0, installationFile.getName().length() - 8));
            }
        }
        final DataLoaderParams dataLoaderParams = this.params.dataLoaderParams;
        final boolean isIncrementalInstallation = true ^ isIncrementalInstallation();
        final boolean isSystemDataLoaderInstallation = isSystemDataLoaderInstallation(this.params);
        IDataLoaderStatusListener.Stub stub =
                new IDataLoaderStatusListener
                        .Stub() { // from class: com.android.server.pm.PackageInstallerSession.6
                    public final void onStatusChanged(int i, int i2) {
                        if (i2 == 0 || i2 == 1 || i2 == 5) {
                            return;
                        }
                        if (PackageInstallerSession.this.mDestroyed
                                || PackageInstallerSession.this.mDataLoaderFinished) {
                            if (i2 == 9 && isSystemDataLoaderInstallation) {
                                final PackageInstallerSession packageInstallerSession =
                                        PackageInstallerSession.this;
                                final String packageName = packageInstallerSession.getPackageName();
                                if (TextUtils.isEmpty(packageName)) {
                                    return;
                                }
                                packageInstallerSession.mHandler.post(
                                        new Runnable() { // from class:
                                            // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda11
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                PackageInstallerSession packageInstallerSession2 =
                                                        PackageInstallerSession.this;
                                                String str = packageName;
                                                if (packageInstallerSession2.mPm
                                                                .mDeletePackageHelper
                                                                .deletePackageX(
                                                                        0, 2, -1L, str, true)
                                                        != 1) {
                                                    BootReceiver$$ExternalSyntheticOutline0.m(
                                                            "Failed to uninstall package with"
                                                                + " failed dataloader: ",
                                                            str,
                                                            "PackageInstallerSession");
                                                }
                                            }
                                        });
                                return;
                            }
                            return;
                        }
                        try {
                            switch (i2) {
                                case 2:
                                    if (isIncrementalInstallation) {
                                        FileSystemControlParcel fileSystemControlParcel =
                                                new FileSystemControlParcel();
                                        fileSystemControlParcel.callback =
                                                PackageInstallerSession.this
                                                .new FileSystemConnector(arrayList);
                                        PackageInstallerSession.m755$$Nest$mgetDataLoader(
                                                        PackageInstallerSession.this, i)
                                                .create(
                                                        i,
                                                        dataLoaderParams.getData(),
                                                        fileSystemControlParcel,
                                                        this);
                                        return;
                                    }
                                    return;
                                case 3:
                                    if (isIncrementalInstallation) {
                                        PackageInstallerSession.m755$$Nest$mgetDataLoader(
                                                        PackageInstallerSession.this, i)
                                                .start(i);
                                        return;
                                    }
                                    return;
                                case 4:
                                    IDataLoader m755$$Nest$mgetDataLoader =
                                            PackageInstallerSession.m755$$Nest$mgetDataLoader(
                                                    PackageInstallerSession.this, i);
                                    List list = arrayList;
                                    InstallationFileParcel[] installationFileParcelArr =
                                            (InstallationFileParcel[])
                                                    list.toArray(
                                                            new InstallationFileParcel
                                                                    [list.size()]);
                                    List list2 = arrayList2;
                                    m755$$Nest$mgetDataLoader.prepareImage(
                                            i,
                                            installationFileParcelArr,
                                            (String[]) list2.toArray(new String[list2.size()]));
                                    return;
                                case 5:
                                default:
                                    return;
                                case 6:
                                    PackageInstallerSession.this.mDataLoaderFinished = true;
                                    if (PackageInstallerSession.this.hasParentSessionId()) {
                                        PackageInstallerSession packageInstallerSession2 =
                                                PackageInstallerSession.this;
                                        ((PackageInstallerService)
                                                        packageInstallerSession2.mSessionProvider)
                                                .getSession(
                                                        packageInstallerSession2
                                                                .getParentSessionId())
                                                .mHandler
                                                .obtainMessage(1)
                                                .sendToTarget();
                                    } else {
                                        PackageInstallerSession.this
                                                .mHandler
                                                .obtainMessage(1)
                                                .sendToTarget();
                                    }
                                    if (isIncrementalInstallation) {
                                        PackageInstallerSession.m755$$Nest$mgetDataLoader(
                                                        PackageInstallerSession.this, i)
                                                .destroy(i);
                                        return;
                                    }
                                    return;
                                case 7:
                                    PackageInstallerSession.this.mDataLoaderFinished = true;
                                    PackageInstallerSession.this
                                            .mHandler
                                            .obtainMessage(5, -20, -1, "Failed to prepare image.")
                                            .sendToTarget();
                                    if (isIncrementalInstallation) {
                                        PackageInstallerSession.m755$$Nest$mgetDataLoader(
                                                        PackageInstallerSession.this, i)
                                                .destroy(i);
                                        return;
                                    }
                                    return;
                                case 8:
                                    PackageInstallerSession packageInstallerSession3 =
                                            PackageInstallerSession.this;
                                    PackageInstallerSession.m756$$Nest$smsendPendingStreaming(
                                            PackageInstallerSession.this.sessionId,
                                            packageInstallerSession3.mContext,
                                            packageInstallerSession3.getRemoteStatusReceiver(),
                                            "DataLoader unavailable");
                                    return;
                                case 9:
                                    throw new PackageManagerException(
                                            -20, "DataLoader reported unrecoverable failure.");
                            }
                        } catch (RemoteException e) {
                            PackageInstallerSession packageInstallerSession4 =
                                    PackageInstallerSession.this;
                            PackageInstallerSession.m756$$Nest$smsendPendingStreaming(
                                    PackageInstallerSession.this.sessionId,
                                    packageInstallerSession4.mContext,
                                    packageInstallerSession4.getRemoteStatusReceiver(),
                                    e.getMessage());
                        } catch (PackageManagerException e2) {
                            PackageInstallerSession.this.mDataLoaderFinished = true;
                            PackageInstallerSession packageInstallerSession5 =
                                    PackageInstallerSession.this;
                            packageInstallerSession5
                                    .mHandler
                                    .obtainMessage(
                                            5, e2.error, -1, ExceptionUtils.getCompleteMessage(e2))
                                    .sendToTarget();
                        }
                    }
                };
        if (isIncrementalInstallation) {
            DataLoaderManager dataLoaderManager =
                    (DataLoaderManager) this.mContext.getSystemService(DataLoaderManager.class);
            if (dataLoaderManager == null) {
                throw new PackageManagerException(
                        -20, "Failed to find data loader manager service");
            }
            if (dataLoaderManager.bindToDataLoader(
                    this.sessionId, dataLoaderParams.getData(), 0L, stub)) {
                return false;
            }
            throw new PackageManagerException(-20, "Failed to initialize data loader");
        }
        PackageManagerService packageManagerService = this.mPm;
        PerUidReadTimeouts[] perUidReadTimeouts =
                packageManagerService.getPerUidReadTimeouts(
                        packageManagerService.snapshotComputer());
        StorageHealthCheckParams storageHealthCheckParams = new StorageHealthCheckParams();
        storageHealthCheckParams.blockedTimeoutMs = 2000;
        storageHealthCheckParams.unhealthyTimeoutMs = 7000;
        storageHealthCheckParams.unhealthyMonitoringMs = 60000;
        IStorageHealthListener.Stub stub2 =
                new IStorageHealthListener
                        .Stub() { // from class: com.android.server.pm.PackageInstallerSession.7
                    public final void onHealthStatus(int i, int i2) {
                        if (PackageInstallerSession.this.mDestroyed
                                || PackageInstallerSession.this.mDataLoaderFinished) {
                            return;
                        }
                        if (i2 == 1 || i2 == 2) {
                            if (isSystemDataLoaderInstallation) {
                                return;
                            }
                        } else if (i2 != 3) {
                            return;
                        }
                        PackageInstallerSession.this.mDataLoaderFinished = true;
                        PackageInstallerSession.this
                                .mHandler
                                .obtainMessage(
                                        5,
                                        -20,
                                        -1,
                                        "Image is missing pages required for installation.")
                                .sendToTarget();
                    }
                };
        try {
            PackageInfo packageInfo =
                    this.mPm
                            .snapshotComputer()
                            .getPackageInfo(this.params.appPackageName, 0L, this.userId);
            File parentFile =
                    (packageInfo == null || packageInfo.applicationInfo == null)
                            ? null
                            : new File(packageInfo.applicationInfo.getCodePath()).getParentFile();
            IncrementalFileStorages incrementalFileStorages = this.mIncrementalFileStorages;
            if (incrementalFileStorages == null) {
                this.mIncrementalFileStorages =
                        IncrementalFileStorages.initialize(
                                this.mContext,
                                PMRune.PM_INSTALL_TO_SDCARD
                                        ? resolveStageDirLocked()
                                        : this.stageDir,
                                parentFile,
                                dataLoaderParams,
                                stub,
                                storageHealthCheckParams,
                                stub2,
                                arrayList,
                                perUidReadTimeouts,
                                new IPackageLoadingProgressCallback
                                        .Stub() { // from class:
                                                  // com.android.server.pm.PackageInstallerSession.8
                                    public final void onPackageLoadingProgressChanged(float f) {
                                        synchronized (PackageInstallerSession.this.mProgressLock) {
                                            PackageInstallerSession packageInstallerSession =
                                                    PackageInstallerSession.this;
                                            packageInstallerSession.mIncrementalProgress = f;
                                            packageInstallerSession.computeProgressLocked(true);
                                        }
                                    }
                                });
            } else {
                incrementalFileStorages.startLoading(
                        dataLoaderParams,
                        stub,
                        storageHealthCheckParams,
                        stub2,
                        perUidReadTimeouts);
            }
            return false;
        } catch (IOException e) {
            throw new PackageManagerException(-20, e.getMessage(), e.getCause());
        }
    }

    public final void prepareInheritedFiles() {
        if (isApexSession() || this.params.mode != 2) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mStageDirInUse) {
                throw new PackageManagerException(-110, "Session files in use");
            }
            if (this.mDestroyed) {
                throw new PackageManagerException(-110, "Session destroyed");
            }
            if (!this.mSealed) {
                throw new PackageManagerException(-110, "Session not sealed");
            }
            boolean z = PMRune.PM_INSTALL_TO_SDCARD;
            if (z && this.stageCid != null) {
                long calculateInstalledSize = calculateInstalledSize();
                Slog.w("PackageInstallerSession", "Final Size " + calculateInstalledSize);
                resizeContainerForSd(calculateInstalledSize, this.stageCid);
            }
            if (z) {
                try {
                    resolveStageDirLocked();
                } catch (IOException e) {
                    throw new PackageManagerException(-18, "Failed to resolve stage location", e);
                }
            }
            try {
                List list = this.mResolvedInheritedFiles;
                File file = z ? this.mResolvedStageDir : this.stageDir;
                String name = file.getName();
                Slog.d(
                        "PackageInstallerSession",
                        "Inherited files: " + this.mResolvedInheritedFiles);
                if (!((ArrayList) this.mResolvedInheritedFiles).isEmpty()
                        && this.mInheritedFilesBase == null) {
                    throw new IllegalStateException("mInheritedFilesBase == null");
                }
                if (isLinkPossible(list, file)) {
                    if (!((ArrayList) this.mResolvedInstructionSets).isEmpty()) {
                        createOatDirs(name, this.mResolvedInstructionSets, new File(file, "oat"));
                    }
                    if (!((ArrayList) this.mResolvedNativeLibPaths).isEmpty()) {
                        Iterator it = ((ArrayList) this.mResolvedNativeLibPaths).iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            int lastIndexOf = str.lastIndexOf(47);
                            if (lastIndexOf >= 0 && lastIndexOf < str.length() - 1) {
                                File file2 = new File(file, str.substring(1, lastIndexOf));
                                if (!file2.exists()) {
                                    NativeLibraryHelper.createNativeLibrarySubdir(file2);
                                }
                                NativeLibraryHelper.createNativeLibrarySubdir(
                                        new File(file2, str.substring(lastIndexOf + 1)));
                            }
                            Slog.e(
                                    "PackageInstallerSession",
                                    "Skipping native library creation for linking due to invalid"
                                        + " path: "
                                            + str);
                        }
                    }
                    linkFiles(name, list, file, this.mInheritedFilesBase);
                } else {
                    copyFiles(list, file);
                }
            } catch (IOException e2) {
                throw new PackageManagerException(-4, "Failed to inherit existing install", e2);
            }
        }
    }

    public final void releaseTransactionLock() {
        this.mTransactionLock.compareAndSet(true, false);
    }

    public final void removeAppMetadata() {
        synchronized (this.mLock) {
            try {
                if (this.mHasAppMetadataFile) {
                    getStagedAppMetadataFile().delete();
                    this.mHasAppMetadataFile = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeChildSessionId(int i) {
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("removeChildSessionId");
                int indexOfKey = this.mChildSessions.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mChildSessions.valueAt(indexOfKey);
                try {
                    acquireTransactionLock();
                    packageInstallerSession.acquireTransactionLock();
                    packageInstallerSession.setParentSessionId(-1);
                    this.mChildSessions.removeAt(indexOfKey);
                } finally {
                    releaseTransactionLock();
                    packageInstallerSession.releaseTransactionLock();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeFile(int i, String str) {
        removeFile_enforcePermission();
        if (!isDataLoaderInstallation(this.params)) {
            throw new IllegalStateException(
                    "Cannot add files to non-data loader installation session.");
        }
        if (TextUtils.isEmpty(this.params.appPackageName)) {
            throw new IllegalStateException("Must specify package name to remove a split");
        }
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("removeFile");
                ArraySet arraySet = this.mFiles;
                if (!arraySet.add(
                        new FileEntry(
                                arraySet.size(),
                                new InstallationFile(
                                        i,
                                        getRemoveMarkerName(str),
                                        -1L,
                                        (byte[]) null,
                                        (byte[]) null)))) {
                    throw new IllegalArgumentException("File already removed: " + str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeSplit(String str) {
        if (isDataLoaderInstallation(this.params)) {
            throw new IllegalStateException(
                    "Cannot remove splits in a data loader installation session.");
        }
        if (TextUtils.isEmpty(this.params.appPackageName)) {
            throw new IllegalStateException("Must specify package name to remove a split");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotCommittedOrDestroyedLocked("removeSplit");
                try {
                    createRemoveSplitMarkerLocked(str);
                } catch (IOException e) {
                    throw ExceptionUtils.wrap(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportUnarchivalStatus(
            final int i, int i2, final long j, final PendingIntent pendingIntent) {
        if (this.mUnarchivalStatus != -1) {
            throw new IllegalStateException(
                    TextUtils.formatSimple(
                            "Unarchival status for ID %s has already been set or a session has been"
                                + " created for it already by the caller.",
                            new Object[] {Integer.valueOf(i2)}));
        }
        this.mUnarchivalStatus = i;
        this.mPm.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArraySet arraySet;
                        PackageInstallerSession packageInstallerSession =
                                PackageInstallerSession.this;
                        int i3 = i;
                        long j2 = j;
                        PendingIntent pendingIntent2 = pendingIntent;
                        PackageArchiver packageArchiver =
                                packageInstallerSession.mPm.mInstallerService.mPackageArchiver;
                        String str =
                                packageInstallerSession.getInstallSource().mInstallerPackageName;
                        String str2 = packageInstallerSession.params.appPackageName;
                        synchronized (packageInstallerSession.mLock) {
                            arraySet = new ArraySet(packageInstallerSession.mUnarchivalListeners);
                        }
                        packageArchiver.notifyUnarchivalListener(
                                i3,
                                str,
                                str2,
                                j2,
                                pendingIntent2,
                                arraySet,
                                packageInstallerSession.userId);
                    }
                });
        if (i != 0) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda2
                        public final void runOrThrow() {
                            PackageInstallerSession.this.abandon();
                        }
                    });
        }
    }

    public final void requestChecksums(
            String str,
            int i,
            int i2,
            List list,
            IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        assertCallerIsOwnerRootOrVerifier();
        boolean z = PMRune.PM_INSTALL_TO_SDCARD;
        if (z) {
            try {
                resolveStageDirLocked();
            } catch (IOException e) {
                throw new ParcelableException(
                        new PackageManagerException(-18, "Failed to resolve stage location", e));
            }
        }
        try {
            this.mPm.requestFileChecksums(
                    new File(z ? this.mResolvedStageDir : this.stageDir, str),
                    PackageManagerServiceUtils.isInstalledByAdb(
                                    getInstallSource().mInitiatingPackageName)
                            ? getInstallSource().mInstallerPackageName
                            : getInstallSource().mInitiatingPackageName,
                    i,
                    i2,
                    list,
                    iOnChecksumsReadyListener);
        } catch (FileNotFoundException e2) {
            throw new ParcelableException(e2);
        }
    }

    public final void requestUserPreapproval(
            PackageInstaller.PreapprovalDetails preapprovalDetails, IntentSender intentSender) {
        boolean z;
        assertCallerIsOwnerOrRoot();
        if (this.params.isMultiPackage) {
            throw new IllegalStateException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            this.sessionId,
                            new StringBuilder("Session "),
                            " is a parent of multi-package session and requestUserPreapproval on"
                                + " the parent session isn't supported."));
        }
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("request of session " + this.sessionId);
            this.mPreapprovalDetails = preapprovalDetails;
            synchronized (this.mLock) {
                this.mPreapprovalRemoteStatusReceiver = intentSender;
            }
        }
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                new StringBuilder("START PREAPPROVAL SESSION: id{"),
                this.sessionId,
                "}",
                "PackageInstallerSession");
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Resources.getSystem()
                    .getBoolean(
                            R.bool
                                    .config_letterboxIsDisplayRotationImmersiveAppCompatPolicyEnabled)) {
                boolean z3 =
                        DeviceConfig.getBoolean(
                                "package_manager_service", "is_preapproval_available", true);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = z3;
            } else {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            if (!z) {
                sendUpdateToRemoteStatusReceiver(
                        "Request user pre-approval is currently not available.", null, -129, true);
                return;
            }
            synchronized (this.mLock) {
                assertPreparedAndNotSealedLocked("dispatchPreapprovalRequest");
                if (this.mPreapprovalRequested.get()) {
                    throw new IllegalStateException(
                            "dispatchPreapprovalRequest not allowed after requesting");
                }
            }
            this.mPreapprovalRequested.set(true);
            this.mHandler.obtainMessage(6).sendToTarget();
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0180 A[Catch: all -> 0x013c, TryCatch #5 {all -> 0x013c, blocks: (B:61:0x012e, B:63:0x0135, B:65:0x0138, B:66:0x013f, B:67:0x0149, B:71:0x014d, B:75:0x0158, B:76:0x015f, B:78:0x0164, B:81:0x0168, B:83:0x017d, B:84:0x0182, B:85:0x018b, B:89:0x018f, B:93:0x0198, B:94:0x019f, B:95:0x019c, B:99:0x01ac, B:100:0x0180, B:103:0x015c, B:107:0x01b0, B:87:0x018c, B:88:0x018e, B:69:0x014a, B:70:0x014c), top: B:60:0x012e, inners: #4, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x017d A[Catch: all -> 0x013c, TryCatch #5 {all -> 0x013c, blocks: (B:61:0x012e, B:63:0x0135, B:65:0x0138, B:66:0x013f, B:67:0x0149, B:71:0x014d, B:75:0x0158, B:76:0x015f, B:78:0x0164, B:81:0x0168, B:83:0x017d, B:84:0x0182, B:85:0x018b, B:89:0x018f, B:93:0x0198, B:94:0x019f, B:95:0x019c, B:99:0x01ac, B:100:0x0180, B:103:0x015c, B:107:0x01b0, B:87:0x018c, B:88:0x018e, B:69:0x014a, B:70:0x014c), top: B:60:0x012e, inners: #4, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x018c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resolveAndStageFileLocked(java.io.File r9, java.io.File r10) {
        /*
            Method dump skipped, instructions count: 502
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.resolveAndStageFileLocked(java.io.File,"
                    + " java.io.File):void");
    }

    public final File resolveStageDirLocked() {
        if (this.mResolvedStageDir == null) {
            File file = this.stageDir;
            if (file != null) {
                this.mResolvedStageDir = file;
            } else if (this.stageCid != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String sdDir = PackageHelperExt.getSdDir(this.stageCid);
                    if (sdDir == null) {
                        throw new IOException(
                                "Failed to resolve path to container " + this.stageCid);
                    }
                    this.mResolvedStageDir = new File(sdDir);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return this.mResolvedStageDir;
    }

    public final void seal() {
        assertNotChild("seal");
        assertCallerIsOwnerOrRoot();
        try {
            synchronized (this.mLock) {
                sealLocked();
            }
            for (PackageInstallerSession packageInstallerSession : getChildSessions()) {
                synchronized (packageInstallerSession.mLock) {
                    packageInstallerSession.sealLocked();
                }
            }
        } catch (PackageManagerException e) {
            throw new IllegalStateException("Package is not valid", e);
        }
    }

    public final void sealLocked() {
        try {
            assertNoWriteFileTransfersOpenLocked();
            assertPreparedAndNotDestroyedLocked("sealing of session " + this.sessionId);
            this.mSealed = true;
        } catch (Throwable th) {
            PackageManagerException packageManagerException = new PackageManagerException(th);
            onSessionValidationFailure(
                    packageManagerException.error,
                    ExceptionUtils.getCompleteMessage(packageManagerException));
            throw packageManagerException;
        }
    }

    public final void sendPendingUserActionIntent(IntentSender intentSender) {
        Intent intent =
                new Intent(
                        this.mPreapprovalRequested.get() && !this.mCommitted.get()
                                ? "android.content.pm.action.CONFIRM_PRE_APPROVAL"
                                : "android.content.pm.action.CONFIRM_INSTALL");
        intent.setPackage(this.mPm.mRequiredInstallerPackage);
        intent.putExtra("android.content.pm.extra.SESSION_ID", this.sessionId);
        Slog.i(
                "PackageInstallerSession",
                "status of session: pending{"
                        + this.sessionId
                        + "}, User action required"
                        + (TextUtils.isEmpty(null) ? "" : " [null]"));
        Context context = this.mContext;
        int i = this.sessionId;
        Intent intent2 = new Intent();
        intent2.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent2.putExtra("android.content.pm.extra.STATUS", -1);
        intent2.putExtra(
                "android.content.pm.extra.PRE_APPROVAL",
                "android.content.pm.action.CONFIRM_PRE_APPROVAL".equals(intent.getAction()));
        intent2.putExtra("android.intent.extra.INTENT", intent);
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent2, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public final boolean sendPendingUserActionIntentIfNeeded(boolean z) {
        IntentSender remoteStatusReceiver;
        if (this.mCommitted.get()) {
            assertNotChild("PackageInstallerSession#sendPendingUserActionIntentIfNeeded");
        }
        if (z) {
            synchronized (this.mLock) {
                remoteStatusReceiver = this.mPreapprovalRemoteStatusReceiver;
            }
        } else {
            remoteStatusReceiver = getRemoteStatusReceiver();
        }
        return sessionContains(
                new PackageInstallerSession$$ExternalSyntheticLambda10(0, remoteStatusReceiver));
    }

    public final void sendUpdateToRemoteStatusReceiver(
            String str, Bundle bundle, int i, boolean z) {
        IntentSender remoteStatusReceiver;
        if (z) {
            synchronized (this.mLock) {
                remoteStatusReceiver = this.mPreapprovalRemoteStatusReceiver;
            }
        } else {
            remoteStatusReceiver = getRemoteStatusReceiver();
        }
        if (remoteStatusReceiver != null) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = getPackageName();
            obtain.arg2 = str;
            obtain.arg3 = bundle;
            obtain.arg4 = remoteStatusReceiver;
            obtain.argi1 = i;
            obtain.argi2 = (!this.mPreapprovalRequested.get() || this.mCommitted.get()) ? 0 : 1;
            this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }
    }

    public final boolean sessionContains(Predicate predicate) {
        List childSessionsLocked;
        if (!this.params.isMultiPackage) {
            return predicate.test(this);
        }
        synchronized (this.mLock) {
            childSessionsLocked = getChildSessionsLocked();
        }
        Iterator it = childSessionsLocked.iterator();
        while (it.hasNext()) {
            if (predicate.test((PackageInstallerSession) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final void setChecksums(String str, Checksum[] checksumArr, byte[] bArr) {
        if (checksumArr.length == 0) {
            return;
        }
        String str2 = getInstallSource().mInitiatingPackageName;
        if (PackageManagerServiceUtils.isInstalledByAdb(str2)) {
            str2 = getInstallSource().mInstallerPackageName;
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalStateException("Installer package is empty.");
        }
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class))
                .checkPackage(Binder.getCallingUid(), str2);
        if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                        .getPackage(str2)
                == null) {
            throw new IllegalStateException("Can't obtain calling installer's package.");
        }
        if (bArr != null && bArr.length != 0) {
            try {
                ApkChecksums.verifySignature(checksumArr, bArr);
            } catch (IOException | NoSuchAlgorithmException | SignatureException e) {
                throw new IllegalArgumentException("Can't verify signature: " + e.getMessage(), e);
            }
        }
        for (Checksum checksum : checksumArr) {
            if (checksum.getValue() == null || checksum.getValue().length > 64) {
                throw new IllegalArgumentException("Invalid checksum.");
            }
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotCommittedOrDestroyedLocked("addChecksums");
                if (this.mChecksums.containsKey(str)) {
                    throw new IllegalStateException("Duplicate checksums.");
                }
                this.mChecksums.put(str, new PerFileChecksum(checksumArr, bArr));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setClientProgress(float f) {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mProgressLock) {
            setClientProgressLocked(f);
        }
    }

    public final void setClientProgressLocked(float f) {
        boolean z = this.mClientProgress == FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mClientProgress = f;
        computeProgressLocked(z);
    }

    public final void setParentSessionId(int i) {
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    if (this.mParentSessionId != -1) {
                        throw new IllegalStateException(
                                "The parent of "
                                        + this.sessionId
                                        + " is alreadyset to "
                                        + this.mParentSessionId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mParentSessionId = i;
        }
    }

    public final void setPermissionsResult(boolean z) {
        if (!isSealed() && !this.mPreapprovalRequested.get()) {
            throw new SecurityException("Must be sealed to accept permissions");
        }
        PackageInstallerSession session =
                (hasParentSessionId() && this.mCommitted.get())
                        ? ((PackageInstallerService) this.mSessionProvider)
                                .getSession(getParentSessionId())
                        : this;
        if (!z) {
            session.destroy("User rejected permissions");
            session.dispatchSessionFinished(-115, "User rejected permissions", null);
            session.maybeFinishChildSessions(-115, "User rejected permissions");
        } else {
            CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                    new StringBuilder("status of session: accepted{"),
                    this.sessionId,
                    "}, User has confirmed",
                    "PackageInstallerSession");
            synchronized (this.mLock) {
                this.mPermissionsManuallyAccepted = true;
            }
            session.mHandler.obtainMessage(this.mCommitted.get() ? 3 : 6).sendToTarget();
        }
    }

    public final void setPreVerifiedDomains(DomainSet domainSet) {
        if (this.mInstallerUid != 0 && this.mInstallerUid != 2000) {
            Computer snapshotComputer = this.mPm.snapshotComputer();
            if (snapshotComputer.checkUidPermission(
                            "android.permission.ACCESS_INSTANT_APPS", this.mInstallerUid)
                    != 0) {
                throw new SecurityException(
                        "You need android.permission.ACCESS_INSTANT_APPS permission to set"
                            + " pre-verified domains.");
            }
            ComponentName instantAppInstallerComponent =
                    snapshotComputer.getInstantAppInstallerComponent();
            if (instantAppInstallerComponent == null) {
                throw new IllegalStateException(
                        "Instant app installer is not available. Only the instant app installer can"
                            + " call this API.");
            }
            if (!instantAppInstallerComponent
                    .getPackageName()
                    .equals(getInstallSource().mInstallerPackageName)) {
                throw new SecurityException("Only the instant app installer can call this API.");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            long j =
                    DeviceConfig.getLong(
                            "package_manager_service", "pre_verified_domains_count_limit", 1000L);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (domainSet.getDomains().size() > j) {
                throw new IllegalArgumentException(
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                j,
                                "The number of pre-verified domains have exceeded the maximum of"
                                    + " "));
            }
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                long j2 =
                        DeviceConfig.getLong(
                                "package_manager_service",
                                "pre_verified_domain_length_limit",
                                256L);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                for (String str : domainSet.getDomains()) {
                    if (str.length() > j2) {
                        throw new IllegalArgumentException(
                                "Pre-verified domain: ["
                                        + str
                                        + " ] exceeds maximum length allowed: "
                                        + j2);
                    }
                }
                synchronized (this.mLock) {
                    assertCallerIsOwnerOrRoot();
                    assertPreparedAndNotSealedLocked("setPreVerifiedDomains");
                    this.mPreVerifiedDomains = domainSet;
                }
            } finally {
            }
        } finally {
        }
    }

    public final void setSessionFailed(int i, String str) {
        synchronized (this.mLock) {
            if (!this.mDestroyed && !this.mSessionFailed) {
                this.mSessionReady = false;
                this.mSessionApplied = false;
                this.mSessionFailed = true;
                this.mSessionErrorCode = i;
                this.mSessionErrorMessage = str;
                Slog.d(
                        "PackageInstallerSession",
                        "Marking session " + this.sessionId + " as failed: " + str);
                destroy("Session marked as failed: " + str);
                this.mCallback.onSessionChanged(this);
            }
        }
    }

    public final void setSessionReady() {
        synchronized (this.mLock) {
            if (!this.mDestroyed && !this.mSessionFailed) {
                this.mSessionReady = true;
                this.mSessionApplied = false;
                this.mSessionFailed = false;
                this.mSessionErrorCode = 0;
                this.mSessionErrorMessage = "";
                this.mCallback.onSessionChanged(this);
            }
        }
    }

    public final void setUnknownSourceConfirmResult(boolean z) {
        Slog.d(
                "PackageInstallerSession",
                "setUnknownSourceConfirmResult, sessionid: " + this.sessionId + ", accepted: " + z);
        if (!isSealed()) {
            throw new SecurityException("Must be sealed to accept permissions");
        }
        if (!z) {
            destroyInternal("User rejected installing unknown source package");
            dispatchSessionFinished(-115, "User rejected installing unknown source package", null);
        } else {
            synchronized (this.mLock) {
                this.mUnknownSourceInstallAccepted.compareAndSet(false, true);
                this.mHandler.obtainMessage(3).sendToTarget();
            }
        }
    }

    public final void stageFileLocked(File file, File file2) {
        ((ArrayList) this.mResolvedStagedFiles).add(file2);
        if (file.equals(file2) || file.renameTo(file2)) {
            return;
        }
        throw new PackageManagerException(-110, "Could not rename file " + file + " to " + file2);
    }

    public final void stageViaHardLink(String str) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("link() can only be run by the system");
        }
        try {
            File resolveStageDirLocked = resolveStageDirLocked();
            File file = new File(str);
            if (!PMRune.PM_INSTALL_TO_SDCARD) {
                resolveStageDirLocked = this.stageDir;
            }
            File file2 = new File(resolveStageDirLocked, file.getName());
            String absolutePath = file2.getAbsolutePath();
            try {
                try {
                    Os.link(str, absolutePath);
                    Os.chmod(absolutePath, FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                } catch (IOException e) {
                    try {
                        Os.unlink(absolutePath);
                    } catch (Exception unused) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                "Failed to unlink session file: ",
                                absolutePath,
                                "PackageInstallerSession");
                    }
                    throw ExceptionUtils.wrap(e);
                }
            } catch (ErrnoException e2) {
                e2.rethrowAsIOException();
            }
            if (SELinux.restorecon(file2)) {
                return;
            }
            throw new IOException("Can't relabel file: " + file2);
        } catch (IOException e3) {
            throw ExceptionUtils.wrap(e3);
        }
    }

    public final boolean streamValidateAndCommit() {
        try {
            synchronized (this.mLock) {
                try {
                    if (this.mCommitted.get()) {
                        return true;
                    }
                    if (!this.params.isMultiPackage) {
                        if (PMRune.PM_INSTALL_TO_SDCARD) {
                            resolveStageDirLocked();
                        }
                        if (!prepareDataLoaderLocked()) {
                            return false;
                        }
                        if (isApexSession()) {
                            validateApexInstallLocked();
                        } else {
                            validateApkInstallLocked();
                        }
                    }
                    if (this.mDestroyed) {
                        throw new PackageManagerException(-110, "Session destroyed");
                    }
                    if (!isIncrementalInstallation()) {
                        synchronized (this.mProgressLock) {
                            this.mClientProgress = 1.0f;
                            computeProgressLocked(true);
                        }
                    }
                    this.mActiveCount.incrementAndGet();
                    if (!this.mCommitted.compareAndSet(false, true)) {
                        throw new PackageManagerException(
                                -110,
                                TextUtils.formatSimple(
                                        "The mCommitted of session %d should be false originally",
                                        new Object[] {Integer.valueOf(this.sessionId)}));
                    }
                    this.committedMillis = System.currentTimeMillis();
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (PackageManagerException e) {
            throw e;
        } catch (Throwable th2) {
            throw new PackageManagerException(th2);
        }
    }

    public final void transfer(String str) {
        Preconditions.checkArgument(!TextUtils.isEmpty(str));
        Computer snapshotComputer = this.mPm.snapshotComputer();
        ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(str, 0L, this.userId);
        if (applicationInfo == null) {
            throw new ParcelableException(new PackageManager.NameNotFoundException(str));
        }
        if (snapshotComputer.checkUidPermission(
                        "android.permission.INSTALL_PACKAGES", applicationInfo.uid)
                != 0) {
            throw new SecurityException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Destination package ",
                            str,
                            " does not have the android.permission.INSTALL_PACKAGES permission"));
        }
        if (!this.params.areHiddenOptionsSet()) {
            throw new SecurityException("Can only transfer sessions that use public options");
        }
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("transfer");
                try {
                    sealLocked();
                    this.mInstallerUid = applicationInfo.uid;
                    this.mInstallSource =
                            InstallSource.create(
                                    str,
                                    null,
                                    str,
                                    this.mInstallerUid,
                                    str,
                                    null,
                                    this.params.packageSource,
                                    false,
                                    false);
                } catch (PackageManagerException e) {
                    throw new IllegalStateException("Package is not valid", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void validateApexInstallLocked() {
        List addedApksLocked = getAddedApksLocked();
        ArrayList arrayList = (ArrayList) addedApksLocked;
        if (arrayList.isEmpty()) {
            throw new PackageManagerException(
                    -2,
                    TextUtils.formatSimple(
                            "Session: %d. No packages staged in %s",
                            new Object[] {
                                Integer.valueOf(this.sessionId), this.stageDir.getAbsolutePath()
                            }));
        }
        if (ArrayUtils.size(addedApksLocked) > 1) {
            throw new PackageManagerException(-2, "Too many files for apex install");
        }
        File file = (File) arrayList.get(0);
        String name = file.getName();
        if (!name.endsWith(".apex")) {
            name = name.concat(".apex");
        }
        if (!FileUtils.isValidExtFilename(name)) {
            throw new PackageManagerException(
                    -2,
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Invalid filename: ", name));
        }
        File file2 = new File(this.stageDir, name);
        resolveAndStageFileLocked(file, file2);
        this.mResolvedBaseFile = file2;
        this.mPackageName = null;
        ParseResult parseApkLite =
                ApkLiteParseUtils.parseApkLite(
                        ParseTypeImpl.forDefaultParsing().reset(), this.mResolvedBaseFile, 32);
        if (parseApkLite.isError()) {
            throw new PackageManagerException(
                    parseApkLite.getErrorCode(),
                    parseApkLite.getErrorMessage(),
                    parseApkLite.getPackageNameForAudit(),
                    parseApkLite.getException());
        }
        ApkLite apkLite = (ApkLite) parseApkLite.getResult();
        if (this.mPackageName == null) {
            this.mPackageName = apkLite.getPackageName();
            this.mVersionCode = apkLite.getLongVersionCode();
        }
        this.mSigningDetails = apkLite.getSigningDetails();
        this.mHasDeviceAdminReceiver = apkLite.isHasDeviceAdminReceiver();
    }

    /* JADX WARN: Removed duplicated region for block: B:294:0x09a6  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x09ee  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x09f0  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0783  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x082c  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0876  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void validateApkInstallLocked() {
        /*
            Method dump skipped, instructions count: 2638
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.validateApkInstallLocked():void");
    }

    public final void verify() {
        try {
            List<PackageInstallerSession> childSessions = getChildSessions();
            if (this.params.isMultiPackage) {
                for (PackageInstallerSession packageInstallerSession : childSessions) {
                    packageInstallerSession.prepareInheritedFiles();
                    packageInstallerSession.parseApkAndExtractNativeLibraries();
                }
            } else {
                prepareInheritedFiles();
                parseApkAndExtractNativeLibraries();
            }
            verifyNonStaged();
        } catch (PackageManagerException e) {
            String installStatusToString =
                    PackageManager.installStatusToString(
                            e.error, ExceptionUtils.getCompleteMessage(e));
            setSessionFailed(e.error, installStatusToString);
            onSessionVerificationFailure(e.error, installStatusToString);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void verifyNonStaged() {
        /*
            Method dump skipped, instructions count: 537
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.verifyNonStaged():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x02e5 A[Catch: all -> 0x000f, LOOP:2: B:46:0x02e3->B:47:0x02e5, LOOP_END, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008b, B:16:0x008f, B:17:0x0095, B:20:0x0190, B:22:0x0197, B:23:0x01d3, B:24:0x01da, B:26:0x01e0, B:30:0x01f7, B:34:0x0206, B:36:0x020c, B:38:0x0213, B:40:0x022b, B:42:0x025d, B:44:0x0263, B:45:0x02dd, B:47:0x02e5, B:49:0x02fa, B:51:0x0302, B:53:0x0343, B:55:0x034c, B:57:0x0362, B:59:0x038b, B:61:0x038e, B:63:0x0396, B:65:0x03aa, B:70:0x03ae, B:69:0x03c6, B:74:0x03c9, B:76:0x03cd, B:77:0x03d5, B:79:0x03db, B:81:0x03f3, B:84:0x0268, B:86:0x026e, B:88:0x027a, B:93:0x02a0, B:94:0x02d0, B:101:0x02d9, B:102:0x02dc), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0302 A[Catch: all -> 0x000f, LOOP:3: B:50:0x0300->B:51:0x0302, LOOP_END, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008b, B:16:0x008f, B:17:0x0095, B:20:0x0190, B:22:0x0197, B:23:0x01d3, B:24:0x01da, B:26:0x01e0, B:30:0x01f7, B:34:0x0206, B:36:0x020c, B:38:0x0213, B:40:0x022b, B:42:0x025d, B:44:0x0263, B:45:0x02dd, B:47:0x02e5, B:49:0x02fa, B:51:0x0302, B:53:0x0343, B:55:0x034c, B:57:0x0362, B:59:0x038b, B:61:0x038e, B:63:0x0396, B:65:0x03aa, B:70:0x03ae, B:69:0x03c6, B:74:0x03c9, B:76:0x03cd, B:77:0x03d5, B:79:0x03db, B:81:0x03f3, B:84:0x0268, B:86:0x026e, B:88:0x027a, B:93:0x02a0, B:94:0x02d0, B:101:0x02d9, B:102:0x02dc), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x034c A[Catch: all -> 0x000f, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008b, B:16:0x008f, B:17:0x0095, B:20:0x0190, B:22:0x0197, B:23:0x01d3, B:24:0x01da, B:26:0x01e0, B:30:0x01f7, B:34:0x0206, B:36:0x020c, B:38:0x0213, B:40:0x022b, B:42:0x025d, B:44:0x0263, B:45:0x02dd, B:47:0x02e5, B:49:0x02fa, B:51:0x0302, B:53:0x0343, B:55:0x034c, B:57:0x0362, B:59:0x038b, B:61:0x038e, B:63:0x0396, B:65:0x03aa, B:70:0x03ae, B:69:0x03c6, B:74:0x03c9, B:76:0x03cd, B:77:0x03d5, B:79:0x03db, B:81:0x03f3, B:84:0x0268, B:86:0x026e, B:88:0x027a, B:93:0x02a0, B:94:0x02d0, B:101:0x02d9, B:102:0x02dc), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0396 A[Catch: all -> 0x000f, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008b, B:16:0x008f, B:17:0x0095, B:20:0x0190, B:22:0x0197, B:23:0x01d3, B:24:0x01da, B:26:0x01e0, B:30:0x01f7, B:34:0x0206, B:36:0x020c, B:38:0x0213, B:40:0x022b, B:42:0x025d, B:44:0x0263, B:45:0x02dd, B:47:0x02e5, B:49:0x02fa, B:51:0x0302, B:53:0x0343, B:55:0x034c, B:57:0x0362, B:59:0x038b, B:61:0x038e, B:63:0x0396, B:65:0x03aa, B:70:0x03ae, B:69:0x03c6, B:74:0x03c9, B:76:0x03cd, B:77:0x03d5, B:79:0x03db, B:81:0x03f3, B:84:0x0268, B:86:0x026e, B:88:0x027a, B:93:0x02a0, B:94:0x02d0, B:101:0x02d9, B:102:0x02dc), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x03cd A[Catch: all -> 0x000f, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008b, B:16:0x008f, B:17:0x0095, B:20:0x0190, B:22:0x0197, B:23:0x01d3, B:24:0x01da, B:26:0x01e0, B:30:0x01f7, B:34:0x0206, B:36:0x020c, B:38:0x0213, B:40:0x022b, B:42:0x025d, B:44:0x0263, B:45:0x02dd, B:47:0x02e5, B:49:0x02fa, B:51:0x0302, B:53:0x0343, B:55:0x034c, B:57:0x0362, B:59:0x038b, B:61:0x038e, B:63:0x0396, B:65:0x03aa, B:70:0x03ae, B:69:0x03c6, B:74:0x03c9, B:76:0x03cd, B:77:0x03d5, B:79:0x03db, B:81:0x03f3, B:84:0x0268, B:86:0x026e, B:88:0x027a, B:93:0x02a0, B:94:0x02d0, B:101:0x02d9, B:102:0x02dc), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void write(com.android.modules.utils.TypedXmlSerializer r12, java.io.File r13) {
        /*
            Method dump skipped, instructions count: 1021
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerSession.write(com.android.modules.utils.TypedXmlSerializer,"
                    + " java.io.File):void");
    }

    public final void write(
            String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
        assertCanWrite(parcelFileDescriptor != null);
        try {
            doWriteInternal(str, j, j2, parcelFileDescriptor);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }
}
