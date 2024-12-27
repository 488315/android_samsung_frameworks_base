package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.role.RoleManager;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.FeatureInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageManager;
import android.content.pm.InstrumentationInfo;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.parsing.ApkLite;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.content.rollback.RollbackManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IUserManager;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.incremental.V4Signature;
import android.os.storage.StorageManager;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.art.ArtManagerLocal;
import com.android.server.art.model.DexoptParams;
import com.android.server.baiducarlife.BaiduCarlifeADBConnectUtils;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.ApexManager;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.android.server.pm.permission.PermissionAllowlist;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.verify.domain.DomainVerificationShell;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.pm.cmd.ShellRestrictionsHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import libcore.io.IoUtils;
import libcore.io.Streams;
import libcore.util.HexEncoding;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageManagerShellCommand extends ShellCommand {
    public static final Set ART_SERVICE_COMMANDS;
    public static final SecureRandom RANDOM;
    public static final Map SUPPORTED_PERMISSION_FLAGS;
    public static final List SUPPORTED_PERMISSION_FLAGS_LIST;
    public static final Set UNSUPPORTED_INSTALL_CMD_OPTS = Set.of("--multi-package");
    public static final Set UNSUPPORTED_SESSION_CREATE_OPTS = Collections.emptySet();
    public boolean mBrief;
    public boolean mComponents;
    public final Context mContext;
    public final DomainVerificationShell mDomainVerificationShell;
    public final IPackageManager mInterface;
    public final PermissionManager mPermissionManager;
    public int mQueryFlags;
    public int mTargetUser;
    public final WeakHashMap mResourceCache = new WeakHashMap();
    public final ShellRestrictionsHelper shellRestrictionsHelper = new ShellRestrictionsHelper();
    public final PackageManagerInternal mPm = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    public final LegacyPermissionManagerService.Internal mLegacyPermissionManager = (LegacyPermissionManagerService.Internal) LocalServices.getService(LegacyPermissionManagerService.Internal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClearDataObserver extends IPackageDataObserver.Stub {
        public boolean finished;
        public boolean result;

        public final void onRemoveCompleted(String str, boolean z) {
            synchronized (this) {
                this.finished = true;
                this.result = z;
                notifyAll();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InstallParams {
        public String installerPackageName;
        public PackageInstaller.SessionParams sessionParams;
        public long stagedReadyTimeoutMs;
        public int userId;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalIntentReceiver {
        public final LinkedBlockingQueue mResult = new LinkedBlockingQueue();
        public final AnonymousClass1 mLocalSender = new IIntentSender.Stub() { // from class: com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver.1
            public final void send(int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) {
                try {
                    LocalIntentReceiver.this.mResult.offer(intent, 5L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        public final IntentSender getIntentSender() {
            return new IntentSender(this.mLocalSender);
        }

        public final Intent getResult() {
            try {
                return (Intent) this.mResult.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionDump {
        public boolean onlyParent;
        public boolean onlyReady;
        public boolean onlySessionId;
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        SUPPORTED_PERMISSION_FLAGS = arrayMap;
        SUPPORTED_PERMISSION_FLAGS_LIST = List.of("review-required", "revoked-compat", "revoke-when-requested", "user-fixed", "user-set");
        arrayMap.put("user-set", 1);
        arrayMap.put("user-fixed", 2);
        arrayMap.put("revoked-compat", 8);
        arrayMap.put("review-required", 64);
        arrayMap.put("revoke-when-requested", 128);
        ART_SERVICE_COMMANDS = Set.of("compile", "reconcile-secondary-dex-files", "force-dex-opt", "bg-dexopt-job", "cancel-bg-dexopt-job", "delete-dexopt", "dump-profiles", "snapshot-profile", "art");
        RANDOM = new SecureRandom();
    }

    public PackageManagerShellCommand(IPackageManager iPackageManager, Context context, DomainVerificationShell domainVerificationShell) {
        this.mInterface = iPackageManager;
        this.mPermissionManager = (PermissionManager) context.getSystemService(PermissionManager.class);
        this.mContext = context;
        this.mDomainVerificationShell = domainVerificationShell;
    }

    public static String enabledSettingToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "unknown" : "disabled-until-used" : "disabled-user" : "disabled" : "enabled" : "default";
    }

    public static String getDataSizeDisplay(long j) {
        double d = j;
        double d2 = d / 1024.0d;
        double d3 = d / 1048576.0d;
        double d4 = d / 1.073741824E9d;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String concat = d4 > 1.0d ? decimalFormat.format(d4).concat(" Gb") : d3 > 1.0d ? decimalFormat.format(d3).concat(" Mb") : d2 > 1.0d ? decimalFormat.format(d2).concat(" Kb") : "";
        if (!concat.isEmpty()) {
            concat = XmlUtils$$ExternalSyntheticOutline0.m(" (", concat, ")");
        }
        return Long.toString(j) + " bytes" + concat;
    }

    public static void printResolveInfo(PrintWriterPrinter printWriterPrinter, String str, ResolveInfo resolveInfo, boolean z, boolean z2) {
        ComponentName componentName;
        if (z || z2) {
            if (resolveInfo.activityInfo != null) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
            } else if (resolveInfo.serviceInfo != null) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
            } else if (resolveInfo.providerInfo != null) {
                ProviderInfo providerInfo = resolveInfo.providerInfo;
                componentName = new ComponentName(providerInfo.packageName, providerInfo.name);
            } else {
                componentName = null;
            }
            if (componentName != null) {
                if (!z2) {
                    StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "priority=");
                    m.append(resolveInfo.priority);
                    m.append(" preferredOrder=");
                    m.append(resolveInfo.preferredOrder);
                    m.append(" match=0x");
                    BatteryService$$ExternalSyntheticOutline0.m(resolveInfo.match, m, " specificIndex=");
                    m.append(resolveInfo.specificIndex);
                    m.append(" isDefault=");
                    m.append(resolveInfo.isDefault);
                    printWriterPrinter.println(m.toString());
                }
                StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(str);
                m2.append(componentName.flattenToShortString());
                printWriterPrinter.println(m2.toString());
                return;
            }
        }
        resolveInfo.dump(printWriterPrinter, str);
    }

    public static void printSession(PrintWriter printWriter, PackageInstaller.SessionInfo sessionInfo, SessionDump sessionDump) {
        if (sessionDump.onlySessionId) {
            printWriter.println(sessionInfo.getSessionId());
            return;
        }
        StringBuilder sb = new StringBuilder("sessionId = ");
        sb.append(sessionInfo.getSessionId());
        sb.append("; appPackageName = ");
        sb.append(sessionInfo.getAppPackageName());
        sb.append("; isStaged = ");
        sb.append(sessionInfo.isStaged());
        sb.append("; isReady = ");
        sb.append(sessionInfo.isStagedSessionReady());
        sb.append("; isApplied = ");
        sb.append(sessionInfo.isStagedSessionApplied());
        sb.append("; isFailed = ");
        sb.append(sessionInfo.isStagedSessionFailed());
        sb.append("; errorMsg = ");
        sb.append(sessionInfo.getStagedSessionErrorMessage());
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, ";", printWriter);
    }

    public static void printSessionList(IndentingPrintWriter indentingPrintWriter, List list, SessionDump sessionDump) {
        SparseArray sparseArray = new SparseArray(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInstaller.SessionInfo sessionInfo = (PackageInstaller.SessionInfo) it.next();
            sparseArray.put(sessionInfo.getSessionId(), sessionInfo);
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            PackageInstaller.SessionInfo sessionInfo2 = (PackageInstaller.SessionInfo) it2.next();
            if (!sessionDump.onlyReady || sessionInfo2.isStagedSessionReady()) {
                if (sessionInfo2.getParentSessionId() == -1) {
                    printSession(indentingPrintWriter, sessionInfo2, sessionDump);
                    if (sessionInfo2.isMultiPackage() && !sessionDump.onlyParent) {
                        indentingPrintWriter.increaseIndent();
                        int[] childSessionIds = sessionInfo2.getChildSessionIds();
                        for (int i = 0; i < childSessionIds.length; i++) {
                            PackageInstaller.SessionInfo sessionInfo3 = (PackageInstaller.SessionInfo) sparseArray.get(childSessionIds[i]);
                            if (sessionInfo3 != null) {
                                printSession(indentingPrintWriter, sessionInfo3, sessionDump);
                            } else if (sessionDump.onlySessionId) {
                                indentingPrintWriter.println(childSessionIds[i]);
                            } else {
                                indentingPrintWriter.println("sessionId = " + childSessionIds[i] + "; not found");
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            }
        }
    }

    public static int translateUserId(int i, int i2, String str) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, i2 != -10000, true, str, "pm command");
        return handleIncomingUser == -1 ? i2 : handleIncomingUser;
    }

    public final int displayPackageFilePath(int i, String str) {
        PackageInfo packageInfo = this.mInterface.getPackageInfo(str, 1073741824L, i);
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return 1;
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.print("package:");
        outPrintWriter.println(packageInfo.applicationInfo.sourceDir);
        if (!ArrayUtils.isEmpty(packageInfo.applicationInfo.splitSourceDirs)) {
            for (String str2 : packageInfo.applicationInfo.splitSourceDirs) {
                outPrintWriter.print("package:");
                outPrintWriter.println(str2);
            }
        }
        return 0;
    }

    public final void doAbandonSession(int i, boolean z) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        PackageInstaller.Session session = null;
        try {
            PackageInstaller.Session session2 = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
            try {
                session2.abandon();
                if (z) {
                    outPrintWriter.println("Success");
                }
                IoUtils.closeQuietly(session2);
            } catch (Throwable th) {
                th = th;
                session = session2;
                IoUtils.closeQuietly(session);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final int doAddFiles(int i, long j, ArrayList arrayList, boolean z, boolean z2) {
        PackageManagerShellCommandDataLoader.Metadata forArchived;
        long j2;
        PackageInstaller.Session session = null;
        try {
            try {
                PackageInstaller.Session session2 = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
                try {
                    try {
                        if (!arrayList.isEmpty() && !PackageManagerShellCommandDataLoader.STDIN_PATH.equals(arrayList.get(0))) {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                String str = (String) it.next();
                                if (str.indexOf(58) == -1) {
                                    processArgForLocalFile(str, session2, z2);
                                } else {
                                    if (z2) {
                                        getOutPrintWriter().println("Error: can't install with size from STDIN for Archival install");
                                        IoUtils.closeQuietly(session2);
                                        return 1;
                                    }
                                    if (processArgForStdin(str, session2) != 0) {
                                        IoUtils.closeQuietly(session2);
                                        return 1;
                                    }
                                }
                            }
                            IoUtils.closeQuietly(session2);
                            return 0;
                        }
                        StringBuilder sb = new StringBuilder("base");
                        sb.append(RANDOM.nextInt());
                        sb.append(".");
                        sb.append(z ? "apex" : "apk");
                        String sb2 = sb.toString();
                        if (z2) {
                            forArchived = PackageManagerShellCommandDataLoader.Metadata.forArchived(getArchivedPackage(j, PackageManagerShellCommandDataLoader.STDIN_PATH));
                            j2 = -1;
                        } else {
                            forArchived = new PackageManagerShellCommandDataLoader.Metadata((byte) 0, sb2, (String) null);
                            j2 = j;
                        }
                        session2.addFile(0, sb2, j2, forArchived.toByteArray(), null);
                        IoUtils.closeQuietly(session2);
                        return 0;
                    } catch (IOException | IllegalArgumentException e) {
                        e = e;
                        session = session2;
                        getErrPrintWriter().println("Failed to add file(s), reason: " + e);
                        getOutPrintWriter().println("Failure [failed to add file(s)]");
                        IoUtils.closeQuietly(session);
                        return 1;
                    }
                } catch (Throwable th) {
                    th = th;
                    session = session2;
                    IoUtils.closeQuietly(session);
                    throw th;
                }
            } catch (IOException | IllegalArgumentException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final int doCommitSession(int i) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        PackageInstaller.Session session = null;
        try {
            PackageInstaller.Session session2 = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
            try {
                if (!session2.isMultiPackage() && !session2.isStaged()) {
                    try {
                        DexMetadataHelper.validateDexPaths(session2.getNames());
                    } catch (IOException | IllegalStateException e) {
                        outPrintWriter.println("Warning [Could not validate the dex paths: " + e.getMessage() + "]");
                    }
                }
                LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                session2.commit(localIntentReceiver.getIntentSender());
                if (session2.isStaged()) {
                    IoUtils.closeQuietly(session2);
                    return 0;
                }
                Intent result = localIntentReceiver.getResult();
                int i2 = 1;
                int intExtra = result.getIntExtra("android.content.pm.extra.STATUS", 1);
                ArrayList<String> stringArrayListExtra = result.getStringArrayListExtra("android.content.pm.extra.WARNINGS");
                if (intExtra != 0) {
                    outPrintWriter.println("Failure [" + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE") + "]");
                } else if (!ArrayUtils.isEmpty(stringArrayListExtra)) {
                    Iterator<String> it = stringArrayListExtra.iterator();
                    while (it.hasNext()) {
                        outPrintWriter.println("Warning: " + it.next());
                    }
                    outPrintWriter.println("Completed with warning(s)");
                    IoUtils.closeQuietly(session2);
                    return i2;
                }
                i2 = intExtra;
                IoUtils.closeQuietly(session2);
                return i2;
            } catch (Throwable th) {
                th = th;
                session = session2;
                IoUtils.closeQuietly(session);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final int doCreateSession(PackageInstaller.SessionParams sessionParams, String str, int i) {
        if (i == -1) {
            sessionParams.installFlags |= 64;
        }
        return this.mInterface.getPackageInstaller().createSession(sessionParams, str, (String) null, translateUserId(i, 0, "doCreateSession"));
    }

    public final void doListPermissions(ArrayList arrayList, boolean z, boolean z2, boolean z3, int i, int i2) {
        String str;
        int i3;
        String str2;
        String str3;
        ArrayList arrayList2 = arrayList;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int size = arrayList.size();
        int i4 = 0;
        int i5 = 0;
        while (i5 < size) {
            String str4 = (String) arrayList2.get(i5);
            String str5 = "  label:";
            if (z) {
                if (i5 > 0) {
                    outPrintWriter.println("");
                }
                if (str4 != null) {
                    PermissionGroupInfo permissionGroupInfo = this.mInterface.getPermissionGroupInfo(str4, i4);
                    if (!z3) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(z2 ? "+ " : "");
                        sb.append("group:");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, permissionGroupInfo.name, outPrintWriter);
                        if (z2) {
                            outPrintWriter.println("  package:" + permissionGroupInfo.packageName);
                            if (getResources(permissionGroupInfo) != null) {
                                StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(outPrintWriter, "  label:" + loadText(permissionGroupInfo, permissionGroupInfo.labelRes, permissionGroupInfo.nonLocalizedLabel), "  description:");
                                m$1.append(loadText(permissionGroupInfo, permissionGroupInfo.descriptionRes, permissionGroupInfo.nonLocalizedDescription));
                                outPrintWriter.println(m$1.toString());
                            }
                        }
                    } else if (getResources(permissionGroupInfo) != null) {
                        outPrintWriter.print(loadText(permissionGroupInfo, permissionGroupInfo.labelRes, permissionGroupInfo.nonLocalizedLabel) + ": ");
                    } else {
                        outPrintWriter.print(permissionGroupInfo.name + ": ");
                    }
                } else {
                    ProxyManager$$ExternalSyntheticOutline0.m(outPrintWriter, (!z2 || z3) ? "" : "+ ", "ungrouped:", new StringBuilder());
                }
                str = "  ";
            } else {
                str = "";
            }
            int i6 = 0;
            List queryPermissionsByGroup = this.mPermissionManager.queryPermissionsByGroup((String) arrayList2.get(i5), 0);
            int size2 = queryPermissionsByGroup == null ? 0 : queryPermissionsByGroup.size();
            boolean z4 = true;
            while (i6 < size2) {
                PermissionInfo permissionInfo = (PermissionInfo) queryPermissionsByGroup.get(i6);
                if (z && str4 == null) {
                    i3 = size;
                    if (permissionInfo.group != null) {
                        str2 = str4;
                        str3 = str5;
                        i6++;
                        size = i3;
                        str4 = str2;
                        str5 = str3;
                    }
                } else {
                    i3 = size;
                }
                int i7 = permissionInfo.protectionLevel & 15;
                str2 = str4;
                if (i7 >= i && i7 <= i2) {
                    if (z3) {
                        if (z4) {
                            z4 = false;
                        } else {
                            outPrintWriter.print(", ");
                        }
                        if (getResources(permissionInfo) != null) {
                            outPrintWriter.print(loadText(permissionInfo, permissionInfo.labelRes, permissionInfo.nonLocalizedLabel));
                        } else {
                            outPrintWriter.print(permissionInfo.name);
                        }
                    } else {
                        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                        m.append(z2 ? "+ " : "");
                        m.append("permission:");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(m, permissionInfo.name, outPrintWriter);
                        if (z2) {
                            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, "  package:");
                            m2.append(permissionInfo.packageName);
                            outPrintWriter.println(m2.toString());
                            if (getResources(permissionInfo) != null) {
                                StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str, str5);
                                str3 = str5;
                                m3.append(loadText(permissionInfo, permissionInfo.labelRes, permissionInfo.nonLocalizedLabel));
                                outPrintWriter.println(m3.toString());
                                outPrintWriter.println(str + "  description:" + loadText(permissionInfo, permissionInfo.descriptionRes, permissionInfo.nonLocalizedDescription));
                            } else {
                                str3 = str5;
                            }
                            StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(str, "  protectionLevel:");
                            m4.append(PermissionInfo.protectionToString(permissionInfo.protectionLevel));
                            outPrintWriter.println(m4.toString());
                            i6++;
                            size = i3;
                            str4 = str2;
                            str5 = str3;
                        }
                    }
                }
                str3 = str5;
                i6++;
                size = i3;
                str4 = str2;
                str5 = str3;
            }
            int i8 = size;
            if (z3) {
                outPrintWriter.println("");
            }
            i5++;
            arrayList2 = arrayList;
            size = i8;
            i4 = 0;
        }
    }

    public final int doRemoveSplits(int i, Collection collection, boolean z) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        PackageInstaller.Session session = null;
        try {
            try {
                PackageInstaller.Session session2 = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
                try {
                    Iterator it = ((ArrayList) collection).iterator();
                    while (it.hasNext()) {
                        session2.removeSplit((String) it.next());
                    }
                    if (z) {
                        outPrintWriter.println("Success");
                    }
                    IoUtils.closeQuietly(session2);
                    return 0;
                } catch (IOException e) {
                    e = e;
                    session = session2;
                    outPrintWriter.println("Error: failed to remove split; " + e.getMessage());
                    IoUtils.closeQuietly(session);
                    return 1;
                } catch (Throwable th) {
                    th = th;
                    session = session2;
                    IoUtils.closeQuietly(session);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final int doRunInstall(InstallParams installParams) {
        boolean z;
        boolean z2;
        String concat;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = installParams.userId;
        if (i != -1 && i != -2 && ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(i) == null) {
            ActiveServices$$ExternalSyntheticOutline0.m(i, outPrintWriter, "Failure [user ", " doesn't exist]");
            return 1;
        }
        PackageInstaller.SessionParams sessionParams = installParams.sessionParams;
        boolean z3 = sessionParams.dataLoaderParams != null;
        int i2 = sessionParams.installFlags;
        boolean z4 = (131072 & i2) != 0;
        boolean z5 = (i2 & 134217728) != 0;
        ArrayList remainingArgs = getRemainingArgs();
        boolean z6 = remainingArgs.isEmpty() || PackageManagerShellCommandDataLoader.STDIN_PATH.equals(remainingArgs.get(0));
        boolean z7 = remainingArgs.size() > 1;
        if (z6) {
            z = z4;
            if (installParams.sessionParams.sizeBytes == -1) {
                outPrintWriter.println("Error: must either specify a package size or an APK file");
                return 1;
            }
        } else {
            z = z4;
        }
        if (z && z7) {
            outPrintWriter.println("Error: can't specify SPLIT(s) for APEX");
            return 1;
        }
        if (z5 && z7) {
            outPrintWriter.println("Error: can't have SPLIT(s) for Archival install");
            return 1;
        }
        if (!z3) {
            if (z6 && z7) {
                outPrintWriter.println("Error: can't specify SPLIT(s) along with STDIN");
                return 1;
            }
            if (remainingArgs.isEmpty()) {
                remainingArgs.add(PackageManagerShellCommandDataLoader.STDIN_PATH);
            } else if (installParams.sessionParams.sizeBytes == -1 && !PackageManagerShellCommandDataLoader.STDIN_PATH.equals(remainingArgs.get(0))) {
                ParseTypeImpl forDefaultParsing = ParseTypeImpl.forDefaultParsing();
                Iterator it = remainingArgs.iterator();
                long j = 0;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "r");
                    if (openFileForSystem == null) {
                        getErrPrintWriter().println("Error: Can't open file: " + str);
                        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Error: Can't open file: ", str));
                    }
                    try {
                        try {
                            ParseResult parseApkLite = ApkLiteParseUtils.parseApkLite(forDefaultParsing.reset(), openFileForSystem.getFileDescriptor(), str, 0);
                            if (parseApkLite.isError()) {
                                throw new IllegalArgumentException("Error: Failed to parse APK file: " + str + ": " + parseApkLite.getErrorMessage(), parseApkLite.getException());
                            }
                            ApkLite apkLite = (ApkLite) parseApkLite.getResult();
                            j += InstallLocationUtils.calculateInstalledSize(new PackageLite((String) null, apkLite.getPath(), apkLite, (String[]) null, (boolean[]) null, (String[]) null, (String[]) null, (String[]) null, (int[]) null, apkLite.getTargetSdkVersion(), (Set[]) null, (Set[]) null), installParams.sessionParams.abiOverride, openFileForSystem.getFileDescriptor());
                        } catch (IOException e) {
                            getErrPrintWriter().println("Error: Failed to parse APK file: " + str);
                            throw new IllegalArgumentException("Error: Failed to parse APK file: " + str, e);
                        }
                    } finally {
                        try {
                            openFileForSystem.close();
                        } catch (IOException unused) {
                        }
                    }
                }
                installParams.sessionParams.setSize(j);
            }
        }
        int doCreateSession = doCreateSession(installParams.sessionParams, installParams.installerPackageName, installParams.userId);
        try {
            if (!z3) {
                long j2 = installParams.sessionParams.sizeBytes;
                boolean z8 = remainingArgs.size() > 1;
                Iterator it2 = remainingArgs.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    if (z8) {
                        concat = new File(str2).getName();
                    } else {
                        concat = "base.".concat(z ? "apex" : "apk");
                    }
                    if (doWriteSplit(doCreateSession, str2, j2, concat, false) != 0) {
                        try {
                            doAbandonSession(doCreateSession, false);
                            return 1;
                        } catch (Exception unused2) {
                            return 1;
                        }
                    }
                }
            } else if (doAddFiles(doCreateSession, installParams.sessionParams.sizeBytes, remainingArgs, z, z5) != 0) {
                try {
                    doAbandonSession(doCreateSession, false);
                    return 1;
                } catch (Exception unused3) {
                    return 1;
                }
            }
            if (doCommitSession(doCreateSession) != 0) {
                try {
                    doAbandonSession(doCreateSession, false);
                    return 1;
                } catch (Exception unused4) {
                    return 1;
                }
            }
            try {
                if (installParams.sessionParams.isStaged) {
                    long j3 = installParams.stagedReadyTimeoutMs;
                    if (j3 > 0) {
                        return doWaitForStagedSessionReady(doCreateSession, j3, outPrintWriter);
                    }
                }
                outPrintWriter.println("Success");
                return 0;
            } catch (Throwable th) {
                th = th;
                z2 = false;
                if (z2) {
                    try {
                        doAbandonSession(doCreateSession, false);
                    } catch (Exception unused5) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z2 = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0065, code lost:
    
        r14.println("Failure [failed to retrieve SessionInfo]");
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006a, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int doWaitForStagedSessionReady(int r11, long r12, java.io.PrintWriter r14) {
        /*
            r10 = this;
            r0 = 0
            int r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto La
            r0 = r2
            goto Lb
        La:
            r0 = r1
        Lb:
            com.android.internal.util.Preconditions.checkArgument(r0)
            android.content.pm.IPackageManager r0 = r10.mInterface
            android.content.pm.IPackageInstaller r0 = r0.getPackageInstaller()
            android.content.pm.PackageInstaller$SessionInfo r0 = r0.getSessionInfo(r11)
            java.lang.String r3 = "]"
            if (r0 != 0) goto L22
            java.lang.String r10 = "Failure [Unknown session "
            com.android.server.am.ActiveServices$$ExternalSyntheticOutline0.m(r11, r14, r10, r3)
            return r2
        L22:
            boolean r4 = r0.isStaged()
            if (r4 != 0) goto L30
            java.lang.String r10 = "Failure [Session "
            java.lang.String r12 = " is not a staged session]"
            com.android.server.am.ActiveServices$$ExternalSyntheticOutline0.m(r11, r14, r10, r12)
            return r2
        L30:
            long r4 = java.lang.System.currentTimeMillis()
            long r6 = r4 + r12
        L36:
            if (r0 == 0) goto L63
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L63
            boolean r8 = r0.isStagedSessionReady()
            if (r8 != 0) goto L63
            boolean r8 = r0.isStagedSessionFailed()
            if (r8 == 0) goto L49
            goto L63
        L49:
            long r4 = r6 - r4
            r8 = 100
            long r4 = java.lang.Math.min(r4, r8)
            android.os.SystemClock.sleep(r4)
            long r4 = java.lang.System.currentTimeMillis()
            android.content.pm.IPackageManager r0 = r10.mInterface
            android.content.pm.IPackageInstaller r0 = r0.getPackageInstaller()
            android.content.pm.PackageInstaller$SessionInfo r0 = r0.getSessionInfo(r11)
            goto L36
        L63:
            if (r0 != 0) goto L6b
            java.lang.String r10 = "Failure [failed to retrieve SessionInfo]"
            r14.println(r10)
            return r2
        L6b:
            boolean r10 = r0.isStagedSessionReady()
            if (r10 != 0) goto L8e
            boolean r10 = r0.isStagedSessionFailed()
            if (r10 != 0) goto L8e
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "Failure [timed out after "
            r10.<init>(r11)
            r10.append(r12)
            java.lang.String r11 = " ms]"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r14.println(r10)
            return r2
        L8e:
            boolean r10 = r0.isStagedSessionReady()
            if (r10 != 0) goto Lb9
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "Error ["
            r10.<init>(r11)
            int r11 = r0.getStagedSessionErrorCode()
            r10.append(r11)
            java.lang.String r11 = "] ["
            r10.append(r11)
            java.lang.String r11 = r0.getStagedSessionErrorMessage()
            r10.append(r11)
            r10.append(r3)
            java.lang.String r10 = r10.toString()
            r14.println(r10)
            return r2
        Lb9:
            java.lang.String r10 = "Success. Reboot device to apply staged session"
            r14.println(r10)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerShellCommand.doWaitForStagedSessionReady(int, long, java.io.PrintWriter):int");
    }

    public final int doWriteSplit(int i, String str, long j, String str2, boolean z) {
        PackageInstaller.Session session = null;
        try {
            try {
                PackageInstaller.Session session2 = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
                try {
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    Pair openInFile = openInFile(j, str);
                    Object obj = openInFile.first;
                    if (obj == null) {
                        int longValue = (int) ((Long) openInFile.second).longValue();
                        IoUtils.closeQuietly(session2);
                        return longValue;
                    }
                    long longValue2 = ((Long) openInFile.second).longValue();
                    session2.write(str2, 0L, longValue2, (ParcelFileDescriptor) obj);
                    if (z) {
                        outPrintWriter.println("Success: streamed " + longValue2 + " bytes");
                    }
                    IoUtils.closeQuietly(session2);
                    return 0;
                } catch (IOException e) {
                    e = e;
                    session = session2;
                    getErrPrintWriter().println("Error: failed to write; " + e.getMessage());
                    IoUtils.closeQuietly(session);
                    return 1;
                } catch (Throwable th) {
                    th = th;
                    session = session2;
                    IoUtils.closeQuietly(session);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    public final int getApplicationCategoryHint() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        ApplicationInfo applicationInfo = this.mInterface.getApplicationInfo(nextArg, 0L, 0);
        if (applicationInfo == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "app: ", nextArg, " not found!");
            return 1;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("app: ", nextArg, ", category: "), applicationInfo.category, outPrintWriter);
        return 0;
    }

    public final ArchivedPackageParcel getArchivedPackage(long j, String str) {
        Pair openInFile = openInFile(j, str);
        Object obj = openInFile.first;
        if (obj == null) {
            throw new IllegalArgumentException("Error: Can't open file: ".concat(str));
        }
        ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obj;
        int longValue = (int) ((Long) openInFile.second).longValue();
        try {
            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            try {
                byte[] bArr = new byte[longValue];
                Streams.readFully(autoCloseInputStream, bArr);
                String str2 = new String(bArr);
                autoCloseInputStream.close();
                ArchivedPackageParcel readArchivedPackageParcel = PackageManagerShellCommandDataLoader.Metadata.readArchivedPackageParcel(HexEncoding.decode(str2));
                if (readArchivedPackageParcel != null) {
                    return readArchivedPackageParcel;
                }
                throw new IllegalArgumentException("Error: Can't parse archived package from: ".concat(str));
            } finally {
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error: Can't load archived package from: ".concat(str), e);
        }
    }

    public final String getPrivAppPermissionsString(String str, boolean z) {
        ArrayMap arrayMap;
        PermissionAllowlist permissionAllowlist = SystemConfig.getInstance().mPermissionAllowlist;
        if (ApexManager.getInstance().getActiveApexPackageNameContainingPackage(str) != null) {
            arrayMap = (ArrayMap) permissionAllowlist.mApexPrivilegedAppAllowlists.get(ApexManager.getInstance().getApexModuleNameForPackageName(ApexManager.getInstance().getActiveApexPackageNameContainingPackage(str)));
        } else {
            try {
                PackageInfo packageInfo = this.mInterface.getPackageInfo(str, 4194304L, 0);
                if (packageInfo != null) {
                    if (packageInfo.applicationInfo.isVendor()) {
                        arrayMap = permissionAllowlist.mVendorPrivilegedAppAllowlist;
                    }
                }
            } catch (RemoteException unused) {
            }
            try {
                PackageInfo packageInfo2 = this.mInterface.getPackageInfo(str, 4194304L, 0);
                if (packageInfo2 != null) {
                    if (packageInfo2.applicationInfo.isProduct()) {
                        arrayMap = permissionAllowlist.mProductPrivilegedAppAllowlist;
                    }
                }
            } catch (RemoteException unused2) {
            }
            try {
                PackageInfo packageInfo3 = this.mInterface.getPackageInfo(str, 4194304L, 0);
                if (packageInfo3 != null) {
                    if (packageInfo3.applicationInfo.isSystemExt()) {
                        arrayMap = permissionAllowlist.mSystemExtPrivilegedAppAllowlist;
                    }
                }
            } catch (RemoteException unused3) {
            }
            arrayMap = permissionAllowlist.mPrivilegedAppAllowlist;
        }
        ArrayMap arrayMap2 = arrayMap != null ? (ArrayMap) arrayMap.get(str) : null;
        if (arrayMap2 == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        int size = arrayMap2.size();
        boolean z2 = true;
        for (int i = 0; i < size; i++) {
            if (((Boolean) arrayMap2.valueAt(i)).booleanValue() == z) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(", ");
                }
                sb.append((String) arrayMap2.keyAt(i));
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public final ArrayList getRemainingArgs() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            String nextArg = getNextArg();
            if (nextArg == null) {
                return arrayList;
            }
            arrayList.add(nextArg);
        }
    }

    public final Resources getResources(PackageItemInfo packageItemInfo) {
        Resources resources = (Resources) this.mResourceCache.get(packageItemInfo.packageName);
        if (resources != null) {
            return resources;
        }
        ApplicationInfo applicationInfo = this.mInterface.getApplicationInfo(packageItemInfo.packageName, 536904192L, 0);
        if (applicationInfo == null) {
            Slog.e("PackageManagerShellCommand", "Failed to get ApplicationInfo for package name(" + packageItemInfo.packageName + ").");
            return null;
        }
        AssetManager assetManager = new AssetManager();
        assetManager.addAssetPath(applicationInfo.publicSourceDir);
        Resources resources2 = new Resources(assetManager, null, null);
        this.mResourceCache.put(packageItemInfo.packageName, resources2);
        return resources2;
    }

    public final String loadText(PackageItemInfo packageItemInfo, int i, CharSequence charSequence) {
        Resources resources;
        if (charSequence != null) {
            return charSequence.toString();
        }
        if (i == 0 || (resources = getResources(packageItemInfo)) == null) {
            return null;
        }
        try {
            return resources.getString(i);
        } catch (Resources.NotFoundException unused) {
            return null;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r18v0, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r4v100 */
    /* JADX WARN: Type inference failed for: r4v103 */
    /* JADX WARN: Type inference failed for: r4v106 */
    /* JADX WARN: Type inference failed for: r4v109 */
    /* JADX WARN: Type inference failed for: r4v112 */
    /* JADX WARN: Type inference failed for: r4v115 */
    /* JADX WARN: Type inference failed for: r4v118 */
    /* JADX WARN: Type inference failed for: r4v121 */
    /* JADX WARN: Type inference failed for: r4v124 */
    /* JADX WARN: Type inference failed for: r4v127 */
    /* JADX WARN: Type inference failed for: r4v130 */
    /* JADX WARN: Type inference failed for: r4v133 */
    /* JADX WARN: Type inference failed for: r4v136 */
    /* JADX WARN: Type inference failed for: r4v139 */
    /* JADX WARN: Type inference failed for: r4v142 */
    /* JADX WARN: Type inference failed for: r4v145 */
    /* JADX WARN: Type inference failed for: r4v146 */
    /* JADX WARN: Type inference failed for: r4v149 */
    /* JADX WARN: Type inference failed for: r4v150 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v34 */
    /* JADX WARN: Type inference failed for: r4v37 */
    /* JADX WARN: Type inference failed for: r4v40 */
    /* JADX WARN: Type inference failed for: r4v43 */
    /* JADX WARN: Type inference failed for: r4v46 */
    /* JADX WARN: Type inference failed for: r4v49 */
    /* JADX WARN: Type inference failed for: r4v52 */
    /* JADX WARN: Type inference failed for: r4v55 */
    /* JADX WARN: Type inference failed for: r4v58 */
    /* JADX WARN: Type inference failed for: r4v61 */
    /* JADX WARN: Type inference failed for: r4v64 */
    /* JADX WARN: Type inference failed for: r4v67 */
    /* JADX WARN: Type inference failed for: r4v70 */
    /* JADX WARN: Type inference failed for: r4v73 */
    /* JADX WARN: Type inference failed for: r4v76 */
    /* JADX WARN: Type inference failed for: r4v79 */
    /* JADX WARN: Type inference failed for: r4v82 */
    /* JADX WARN: Type inference failed for: r4v85 */
    /* JADX WARN: Type inference failed for: r4v88 */
    /* JADX WARN: Type inference failed for: r4v91 */
    /* JADX WARN: Type inference failed for: r4v94 */
    /* JADX WARN: Type inference failed for: r4v97 */
    public final InstallParams makeInstallParams(Set set) {
        int i;
        int parseInt;
        boolean z = false;
        char c = 65535;
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        InstallParams installParams = new InstallParams();
        installParams.userId = -1;
        installParams.stagedReadyTimeoutMs = 60000L;
        installParams.sessionParams = sessionParams;
        sessionParams.installFlags |= 4194304;
        sessionParams.setPackageSource(1);
        boolean z2 = false;
        boolean z3 = true;
        Boolean bool = null;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (bool == null) {
                    bool = Boolean.valueOf((sessionParams.installFlags & 131072) != 0);
                }
                if (z3) {
                    sessionParams.installFlags |= 2;
                }
                if (z2) {
                    sessionParams.isStaged = false;
                    sessionParams.developmentInstallFlags |= 1;
                } else if (bool.booleanValue()) {
                    sessionParams.setStaged();
                }
                int i2 = sessionParams.installFlags;
                if ((131072 & i2) == 0 || (262144 & i2) == 0 || sessionParams.rollbackDataPolicy != 1) {
                    return installParams;
                }
                throw new IllegalArgumentException("Data policy 'wipe' is not supported for apex.");
            }
            if (set.contains(nextOption)) {
                throw new IllegalArgumentException("Unsupported option ".concat(nextOption));
            }
            ?? r4 = c;
            switch (nextOption.hashCode()) {
                case -2119202362:
                    if (nextOption.equals("--non-staged")) {
                        r4 = z;
                        break;
                    }
                    r4 = -1;
                    break;
                case -2091380650:
                    if (nextOption.equals("--install-reason")) {
                        r4 = 1;
                        break;
                    }
                    r4 = -1;
                    break;
                case -2041347087:
                    if (nextOption.equals("--skip-enable")) {
                        r4 = 2;
                        break;
                    }
                    r4 = -1;
                    break;
                case -1950997763:
                    if (nextOption.equals("--force-uuid")) {
                        r4 = 3;
                        break;
                    }
                    r4 = -1;
                    break;
                case -1816313368:
                    if (nextOption.equals("--force-non-staged")) {
                        r4 = 4;
                        break;
                    }
                    r4 = -1;
                    break;
                case -1777984902:
                    if (nextOption.equals("--dont-kill")) {
                        r4 = 5;
                        break;
                    }
                    r4 = -1;
                    break;
                case -1313152697:
                    if (nextOption.equals("--install-location")) {
                        r4 = 6;
                        break;
                    }
                    r4 = -1;
                    break;
                case -1137116608:
                    if (nextOption.equals("--instantapp")) {
                        r4 = 7;
                        break;
                    }
                    r4 = -1;
                    break;
                case -951415743:
                    if (nextOption.equals("--instant")) {
                        r4 = 8;
                        break;
                    }
                    r4 = -1;
                    break;
                case -706813505:
                    if (nextOption.equals("--referrer")) {
                        r4 = 9;
                        break;
                    }
                    r4 = -1;
                    break;
                case -653924786:
                    if (nextOption.equals("--enable-rollback")) {
                        r4 = 10;
                        break;
                    }
                    r4 = -1;
                    break;
                case -365988597:
                    if (nextOption.equals("--update-ownership")) {
                        r4 = 11;
                        break;
                    }
                    r4 = -1;
                    break;
                case -311388442:
                    if (nextOption.equals("--rollback-impact-level")) {
                        r4 = 12;
                        break;
                    }
                    r4 = -1;
                    break;
                case -170474990:
                    if (nextOption.equals("--multi-package")) {
                        r4 = 13;
                        break;
                    }
                    r4 = -1;
                    break;
                case -158482320:
                    if (nextOption.equals("--staged-ready-timeout")) {
                        r4 = 14;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1477:
                    if (nextOption.equals("-R")) {
                        r4 = 15;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1478:
                    if (nextOption.equals("-S")) {
                        r4 = 16;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1495:
                    if (nextOption.equals("-d")) {
                        r4 = 17;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1497:
                    if (nextOption.equals("-f")) {
                        r4 = 18;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1498:
                    if (nextOption.equals("-g")) {
                        r4 = 19;
                        break;
                    }
                    r4 = -1;
                    break;
                case NetworkConstants.ETHER_MTU /* 1500 */:
                    if (nextOption.equals("-i")) {
                        r4 = 20;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1507:
                    if (nextOption.equals("-p")) {
                        r4 = 21;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1509:
                    if (nextOption.equals("-r")) {
                        r4 = 22;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1511:
                    if (nextOption.equals("-t")) {
                        r4 = 23;
                        break;
                    }
                    r4 = -1;
                    break;
                case 42995400:
                    if (nextOption.equals("--abi")) {
                        r4 = 24;
                        break;
                    }
                    r4 = -1;
                    break;
                case 43010092:
                    if (nextOption.equals("--pkg")) {
                        r4 = 25;
                        break;
                    }
                    r4 = -1;
                    break;
                case 50011004:
                    if (nextOption.equals("--bypass-low-target-sdk-block")) {
                        r4 = 26;
                        break;
                    }
                    r4 = -1;
                    break;
                case 77141024:
                    if (nextOption.equals("--force-queryable")) {
                        r4 = 27;
                        break;
                    }
                    r4 = -1;
                    break;
                case 148207464:
                    if (nextOption.equals("--originating-uri")) {
                        r4 = 28;
                        break;
                    }
                    r4 = -1;
                    break;
                case 458776531:
                    if (nextOption.equals("--ignore-dexopt-profile")) {
                        r4 = 29;
                        break;
                    }
                    r4 = -1;
                    break;
                case 577311010:
                    if (nextOption.equals("--package-source")) {
                        r4 = 30;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1051781117:
                    if (nextOption.equals("--ephemeral")) {
                        r4 = 31;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1067504745:
                    if (nextOption.equals("--preload")) {
                        r4 = 32;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1289149813:
                    if (nextOption.equals("--dexopt-compiler-filter")) {
                        r4 = 33;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1332870850:
                    if (nextOption.equals("--apex")) {
                        r4 = 34;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1333024815:
                    if (nextOption.equals("--full")) {
                        r4 = 35;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        r4 = 36;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1494514835:
                    if (nextOption.equals("--restrict-permissions")) {
                        r4 = 37;
                        break;
                    }
                    r4 = -1;
                    break;
                case 1507519174:
                    if (nextOption.equals("--staged")) {
                        r4 = 38;
                        break;
                    }
                    r4 = -1;
                    break;
                case 2015272120:
                    if (nextOption.equals("--force-sdk")) {
                        r4 = 39;
                        break;
                    }
                    r4 = -1;
                    break;
                case 2037590537:
                    if (nextOption.equals("--skip-verification")) {
                        r4 = 40;
                        break;
                    }
                    r4 = -1;
                    break;
            }
            switch (r4) {
                case 0:
                    bool = Boolean.FALSE;
                    continue;
                    z = false;
                    c = 65535;
                case 1:
                    sessionParams.installReason = Integer.parseInt(getNextArg());
                    continue;
                    z = false;
                    c = 65535;
                case 2:
                    sessionParams.setApplicationEnabledSettingPersistent();
                    continue;
                    z = false;
                    c = 65535;
                case 3:
                    sessionParams.installFlags |= 512;
                    String nextArg = getNextArg();
                    sessionParams.volumeUuid = nextArg;
                    if ("internal".equals(nextArg)) {
                        sessionParams.volumeUuid = null;
                    } else {
                        continue;
                    }
                    z = false;
                    c = 65535;
                case 4:
                    z2 = true;
                    continue;
                    z = false;
                    c = 65535;
                case 5:
                    sessionParams.installFlags |= 4096;
                    continue;
                    z = false;
                    c = 65535;
                case 6:
                    sessionParams.installLocation = Integer.parseInt(getNextArg());
                    continue;
                    z = false;
                    c = 65535;
                case 7:
                case 8:
                case 31:
                    sessionParams.setInstallAsInstantApp(true);
                    continue;
                    z = false;
                    c = 65535;
                case 9:
                    sessionParams.referrerUri = Uri.parse(getNextArg());
                    continue;
                    z = false;
                    c = 65535;
                case 10:
                    if (installParams.installerPackageName == null) {
                        installParams.installerPackageName = "com.android.shell";
                    }
                    try {
                        i = Integer.parseInt(peekNextArg());
                    } catch (NumberFormatException unused) {
                        i = 0;
                    }
                    if (i < 0 || i > 2) {
                        throw new IllegalArgumentException(i + " is not a valid rollback data policy.");
                        break;
                    } else {
                        try {
                            getNextArg();
                        } catch (NumberFormatException unused2) {
                        }
                        sessionParams.setEnableRollback(true, i);
                        continue;
                        z = false;
                        c = 65535;
                    }
                    sessionParams.setEnableRollback(true, i);
                    continue;
                    z = false;
                    c = 65535;
                case 11:
                    if (installParams.installerPackageName == null) {
                        installParams.installerPackageName = "com.android.shell";
                    }
                    sessionParams.installFlags |= 33554432;
                    continue;
                    z = false;
                    c = 65535;
                case 12:
                    if (!Flags.recoverabilityDetection()) {
                        throw new IllegalArgumentException("Unknown option ".concat(nextOption));
                    }
                    parseInt = Integer.parseInt(peekNextArg());
                    if (parseInt >= 0 && parseInt <= 2) {
                        sessionParams.setRollbackImpactLevel(parseInt);
                        break;
                    }
                    break;
                case 13:
                    sessionParams.setMultiPackage();
                    continue;
                    z = false;
                    c = 65535;
                case 14:
                    break;
                case 15:
                    z3 = false;
                    continue;
                    z = false;
                    c = 65535;
                case 16:
                    long parseLong = Long.parseLong(getNextArg());
                    if (parseLong <= 0) {
                        throw new IllegalArgumentException("Size must be positive");
                    }
                    sessionParams.setSize(parseLong);
                    continue;
                    z = false;
                    c = 65535;
                case 17:
                    sessionParams.installFlags |= 128;
                    continue;
                    z = false;
                    c = 65535;
                case 18:
                    sessionParams.installFlags |= 16;
                    continue;
                    z = false;
                    c = 65535;
                case 19:
                    sessionParams.installFlags |= 256;
                    continue;
                    z = false;
                    c = 65535;
                case 20:
                    String nextArg2 = getNextArg();
                    installParams.installerPackageName = nextArg2;
                    if (nextArg2 == null) {
                        throw new IllegalArgumentException("Missing installer package");
                    }
                    continue;
                    z = false;
                    c = 65535;
                case 21:
                    sessionParams.mode = 2;
                    String nextArg3 = getNextArg();
                    sessionParams.appPackageName = nextArg3;
                    if (nextArg3 == null) {
                        throw new IllegalArgumentException("Missing inherit package name");
                    }
                    continue;
                    z = false;
                    c = 65535;
                case 22:
                case 39:
                    z = false;
                    c = 65535;
                case 23:
                    sessionParams.installFlags |= 4;
                    continue;
                    z = false;
                    c = 65535;
                case 24:
                    String nextArg4 = getNextArg();
                    if (TextUtils.isEmpty(nextArg4)) {
                        throw new IllegalArgumentException("Missing ABI argument");
                    }
                    if (!PackageManagerShellCommandDataLoader.STDIN_PATH.equals(nextArg4)) {
                        String[] strArr = Build.SUPPORTED_ABIS;
                        int length = strArr.length;
                        for (?? r15 = z; r15 < length; r15++) {
                            if (!strArr[r15].equals(nextArg4)) {
                            }
                        }
                        throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("ABI ", nextArg4, " not supported on this device"));
                    }
                    sessionParams.abiOverride = nextArg4;
                    continue;
                    z = false;
                    c = 65535;
                case 25:
                    String nextArg5 = getNextArg();
                    sessionParams.appPackageName = nextArg5;
                    if (nextArg5 == null) {
                        throw new IllegalArgumentException("Missing package name");
                    }
                    continue;
                    z = false;
                    c = 65535;
                case 26:
                    sessionParams.installFlags |= 16777216;
                    continue;
                    z = false;
                    c = 65535;
                case 27:
                    sessionParams.setForceQueryable();
                    continue;
                    z = false;
                    c = 65535;
                case 28:
                    sessionParams.originatingUri = Uri.parse(getNextArg());
                    continue;
                    z = false;
                    c = 65535;
                case 29:
                    sessionParams.installFlags |= 268435456;
                    continue;
                    z = false;
                    c = 65535;
                case 30:
                    sessionParams.setPackageSource(Integer.parseInt(getNextArg()));
                    continue;
                    z = false;
                    c = 65535;
                case 32:
                    sessionParams.setInstallAsVirtualPreload();
                    continue;
                    z = false;
                    c = 65535;
                case 33:
                    sessionParams.dexoptCompilerFilter = getNextArgRequired();
                    new DexoptParams.Builder("install").setCompilerFilter(sessionParams.dexoptCompilerFilter).build();
                    continue;
                    z = false;
                    c = 65535;
                case 34:
                    sessionParams.setInstallAsApex();
                    continue;
                    z = false;
                    c = 65535;
                case 35:
                    sessionParams.setInstallAsInstantApp(z);
                    continue;
                    z = false;
                    c = 65535;
                case 36:
                    installParams.userId = UserHandle.parseUserArg(getNextArgRequired());
                    continue;
                    z = false;
                    c = 65535;
                case 37:
                    sessionParams.installFlags &= -4194305;
                    continue;
                    z = false;
                    c = 65535;
                case 38:
                    bool = Boolean.TRUE;
                    continue;
                    z = false;
                    c = 65535;
                case 40:
                    sessionParams.installFlags |= 524288;
                    continue;
                    z = false;
                    c = 65535;
                default:
                    throw new IllegalArgumentException("Unknown option ".concat(nextOption));
            }
            installParams.stagedReadyTimeoutMs = Long.parseLong(getNextArgRequired());
            z = false;
            c = 65535;
        }
        throw new IllegalArgumentException(NandswapManager$$ExternalSyntheticOutline0.m(parseInt, " is not a valid rollback impact level."));
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -2102802879:
                    if (str.equals("set-harmful-app-warning")) {
                        c = 'F';
                        break;
                    }
                    c = 65535;
                    break;
                case -2084745732:
                    if (str.equals("getAppCategoryInfos")) {
                        c = '[';
                        break;
                    }
                    c = 65535;
                    break;
                case -1987936121:
                    if (str.equals("wait-for-background-handler")) {
                        c = 'T';
                        break;
                    }
                    c = 65535;
                    break;
                case -1967190973:
                    if (str.equals("install-abandon")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1938648150:
                    if (str.equals("get-archived-package-metadata")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -1937348290:
                    if (str.equals("get-install-location")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case -1867202415:
                    if (str.equals("install-set-pre-verified-domains")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -1852006340:
                    if (str.equals("suspend")) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case -1847285281:
                    if (str.equals("getApplicationCategoryHint")) {
                        c = 'Y';
                        break;
                    }
                    c = 65535;
                    break;
                case -1846646502:
                    if (str.equals("get-max-running-users")) {
                        c = 'A';
                        break;
                    }
                    c = 65535;
                    break;
                case -1838891168:
                    if (str.equals("clear-package-preferred-activities")) {
                        c = 'R';
                        break;
                    }
                    c = 65535;
                    break;
                case -1772364145:
                    if (str.equals("setAppCategoryHintUser")) {
                        c = 'X';
                        break;
                    }
                    c = 65535;
                    break;
                case -1741208611:
                    if (str.equals("set-installer")) {
                        c = 'C';
                        break;
                    }
                    c = 65535;
                    break;
                case -1534455582:
                    if (str.equals("set-silent-updates-policy")) {
                        c = 'P';
                        break;
                    }
                    c = 65535;
                    break;
                case -1445787154:
                    if (str.equals("wait-for-handler")) {
                        c = 'S';
                        break;
                    }
                    c = 65535;
                    break;
                case -1347307837:
                    if (str.equals("has-feature")) {
                        c = 'E';
                        break;
                    }
                    c = 65535;
                    break;
                case -1298848381:
                    if (str.equals("enable")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case -1267782244:
                    if (str.equals("get-instantapp-resolver")) {
                        c = 'D';
                        break;
                    }
                    c = 65535;
                    break;
                case -1231004208:
                    if (str.equals("resolve-activity")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1102348235:
                    if (str.equals("get-privapp-deny-permissions")) {
                        c = '4';
                        break;
                    }
                    c = 65535;
                    break;
                case -1091400553:
                    if (str.equals("get-oem-permissions")) {
                        c = '5';
                        break;
                    }
                    c = 65535;
                    break;
                case -1070704814:
                    if (str.equals("get-privapp-permissions")) {
                        c = '3';
                        break;
                    }
                    c = 65535;
                    break;
                case -1063121379:
                    if (str.equals("install-get-pre-verified-domains")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -1032029296:
                    if (str.equals("disable-user")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case -999678881:
                    if (str.equals("suspend-quarantine")) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case -944325712:
                    if (str.equals("set-distracting-restriction")) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case -934343034:
                    if (str.equals("revoke")) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case -905841467:
                    if (str.equals("get-domain-verification-agent")) {
                        c = 'W';
                        break;
                    }
                    c = 65535;
                    break;
                case -840566949:
                    if (str.equals("unhide")) {
                        c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                        break;
                    }
                    c = 65535;
                    break;
                case -840228325:
                    if (str.equals("unstop")) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case -761393825:
                    if (str.equals("disable-verification-for-uid")) {
                        c = 'O';
                        break;
                    }
                    c = 65535;
                    break;
                case -748101438:
                    if (str.equals("archive")) {
                        c = 'U';
                        break;
                    }
                    c = 65535;
                    break;
                case -740352344:
                    if (str.equals("install-incremental")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -703497631:
                    if (str.equals("bypass-staged-installer-check")) {
                        c = 'M';
                        break;
                    }
                    c = 65535;
                    break;
                case -625596190:
                    if (str.equals("uninstall")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -539710980:
                    if (str.equals("create-user")) {
                        c = '9';
                        break;
                    }
                    c = 65535;
                    break;
                case -458695741:
                    if (str.equals("query-services")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -440994401:
                    if (str.equals("query-receivers")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -416698598:
                    if (str.equals("get-stagedsessions")) {
                        c = 'H';
                        break;
                    }
                    c = 65535;
                    break;
                case -339687564:
                    if (str.equals("remove-user")) {
                        c = ':';
                        break;
                    }
                    c = 65535;
                    break;
                case -220055275:
                    if (str.equals("set-permission-enforced")) {
                        c = '2';
                        break;
                    }
                    c = 65535;
                    break;
                case -174281478:
                    if (str.equals("rename-user")) {
                        c = '<';
                        break;
                    }
                    c = 65535;
                    break;
                case -140205181:
                    if (str.equals("unsuspend")) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case -132384343:
                    if (str.equals("install-commit")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -129863314:
                    if (str.equals("install-create")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -115000827:
                    if (str.equals("default-state")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case -87258188:
                    if (str.equals("move-primary-storage")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -34095911:
                    if (str.equals("get-shared-uid-allowlist")) {
                        c = '7';
                        break;
                    }
                    c = 65535;
                    break;
                case -18738613:
                    if (str.equals("request-unarchive")) {
                        c = 'V';
                        break;
                    }
                    c = 65535;
                    break;
                case 3292:
                    if (str.equals("gc")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 3095028:
                    if (str.equals("dump")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals("help")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3202370:
                    if (str.equals("hide")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3433509:
                    if (str.equals("path")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 18936394:
                    if (str.equals("move-package")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 26877293:
                    if (str.equals("dump-package")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 86600360:
                    if (str.equals("get-max-users")) {
                        c = '@';
                        break;
                    }
                    c = 65535;
                    break;
                case 88069748:
                    if (str.equals("supports-multiple-users")) {
                        c = '?';
                        break;
                    }
                    c = 65535;
                    break;
                case 93657776:
                    if (str.equals("install-streaming")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 94746189:
                    if (str.equals("clear")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 98615580:
                    if (str.equals("grant")) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case 107262333:
                    if (str.equals("install-existing")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 139892533:
                    if (str.equals("get-harmful-app-warning")) {
                        c = 'G';
                        break;
                    }
                    c = 65535;
                    break;
                case 237392952:
                    if (str.equals("install-add-session")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 287820022:
                    if (str.equals("install-remove")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 359572742:
                    if (str.equals("reset-permissions")) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case 377019320:
                    if (str.equals("rollback-app")) {
                        c = 'J';
                        break;
                    }
                    c = 65535;
                    break;
                case 401207972:
                    if (str.equals("get-distracting-restriction")) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case 513651668:
                    if (str.equals("install-archived")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case 798023112:
                    if (str.equals("install-destroy")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 826473335:
                    if (str.equals("uninstall-system-updates")) {
                        c = 'I';
                        break;
                    }
                    c = 65535;
                    break;
                case 925176533:
                    if (str.equals("set-user-restriction")) {
                        c = '=';
                        break;
                    }
                    c = 65535;
                    break;
                case 984478785:
                    if (str.equals("getAppCategoryHintUserMap")) {
                        c = 'Z';
                        break;
                    }
                    c = 65535;
                    break;
                case 1053409810:
                    if (str.equals("query-activities")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1177857340:
                    if (str.equals("trim-caches")) {
                        c = '8';
                        break;
                    }
                    c = 65535;
                    break;
                case 1396442249:
                    if (str.equals("clear-permission-flags")) {
                        c = '1';
                        break;
                    }
                    c = 65535;
                    break;
                case 1429366290:
                    if (str.equals("set-home-activity")) {
                        c = 'B';
                        break;
                    }
                    c = 65535;
                    break;
                case 1536099937:
                    if (str.equals("get-user-restriction")) {
                        c = '>';
                        break;
                    }
                    c = 65535;
                    break;
                case 1538306349:
                    if (str.equals("install-write")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 1585252978:
                    if (str.equals("get-app-metadata")) {
                        c = 'Q';
                        break;
                    }
                    c = 65535;
                    break;
                case 1595958325:
                    if (str.equals("get-signature-permission-allowlist")) {
                        c = '6';
                        break;
                    }
                    c = 65535;
                    break;
                case 1661039911:
                    if (str.equals("mark-guest-for-deletion")) {
                        c = ';';
                        break;
                    }
                    c = 65535;
                    break;
                case 1671308008:
                    if (str.equals("disable")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case 1697997009:
                    if (str.equals("disable-until-used")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case 1738820372:
                    if (str.equals("set-permission-flags")) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case 1746695602:
                    if (str.equals("set-install-location")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1757370437:
                    if (str.equals("bypass-allowed-apex-update-check")) {
                        c = 'N';
                        break;
                    }
                    c = 65535;
                    break;
                case 1824799035:
                    if (str.equals("log-visibility")) {
                        c = 'L';
                        break;
                    }
                    c = 65535;
                    break;
                case 1858863089:
                    if (str.equals("get-moduleinfo")) {
                        c = 'K';
                        break;
                    }
                    c = 65535;
                    break;
                case 1957569947:
                    if (str.equals("install")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 2007457359:
                    if (str.equals("get-package-storage-stats")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    onHelp();
                    return 0;
                case 1:
                    return runPath();
                case 2:
                    return runDump();
                case 3:
                    return runDumpPackage();
                case 4:
                    return runList();
                case 5:
                    runGc();
                    return 0;
                case 6:
                    runResolveActivity();
                    return 0;
                case 7:
                    runQueryIntentActivities();
                    return 0;
                case '\b':
                    runQueryIntentServices();
                    return 0;
                case '\t':
                    runQueryIntentReceivers();
                    return 0;
                case '\n':
                    return doRunInstall(makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS));
                case 11:
                    return runStreamingInstall();
                case '\f':
                    return runIncrementalInstall();
                case '\r':
                case 14:
                    runInstallAbandon();
                    return 0;
                case 15:
                    return runInstallCommit();
                case 16:
                    runInstallCreate();
                    return 0;
                case 17:
                    return runInstallRemove();
                case 18:
                    return runInstallWrite();
                case 19:
                    return runInstallExisting();
                case 20:
                    return runSetInstallLocation();
                case 21:
                    runGetInstallLocation();
                    return 0;
                case 22:
                    return runInstallAddSession();
                case 23:
                    runInstallSetPreVerifiedDomains();
                    return 0;
                case 24:
                    runInstallGetPreVerifiedDomains();
                    return 0;
                case 25:
                    return runMovePackage();
                case 26:
                    return runMovePrimaryStorage();
                case 27:
                    return runUninstall();
                case 28:
                    return runClear();
                case 29:
                    return runGetArchivedPackageMetadata();
                case 30:
                    return runGetPackageStorageStats();
                case 31:
                    return runArchivedInstall();
                case ' ':
                    return runSetEnabledSetting(1);
                case '!':
                    return runSetEnabledSetting(2);
                case '\"':
                    return runSetEnabledSetting(3);
                case '#':
                    return runSetEnabledSetting(4);
                case '$':
                    return runSetEnabledSetting(0);
                case '%':
                    return runSetHiddenSetting(true);
                case '&':
                    return runSetHiddenSetting(false);
                case '\'':
                    return runSetStoppedState();
                case '(':
                    return runSuspend(0, true);
                case ')':
                    return runSuspend(1, true);
                case '*':
                    return runSuspend(0, false);
                case '+':
                    return runSetDistractingRestriction();
                case ',':
                    return runGetDistractingRestriction();
                case '-':
                    return runGrantRevokePermission(true);
                case '.':
                    return runGrantRevokePermission(false);
                case '/':
                    runResetPermissions();
                    return 0;
                case '0':
                    return setOrClearPermissionFlags(true);
                case '1':
                    return setOrClearPermissionFlags(false);
                case '2':
                    return runSetPermissionEnforced();
                case '3':
                    return runGetPrivappPermissions();
                case '4':
                    return runGetPrivappDenyPermissions();
                case '5':
                    return runGetOemPermissions();
                case '6':
                    return runGetSignaturePermissionAllowlist();
                case '7':
                    ArrayMap arrayMap = SystemConfig.getInstance().mPackageToSharedUidAllowList;
                    PrintWriter outPrintWriter2 = getOutPrintWriter();
                    int size = arrayMap.size();
                    for (int i = 0; i < size; i++) {
                        String str2 = (String) arrayMap.keyAt(i);
                        String str3 = (String) arrayMap.valueAt(i);
                        outPrintWriter2.print(str2);
                        outPrintWriter2.print(" ");
                        outPrintWriter2.println(str3);
                    }
                    return 0;
                case '8':
                    return runTrimCaches();
                case '9':
                    return runCreateUser();
                case ':':
                    return runRemoveUser();
                case ';':
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        getErrPrintWriter().println("Error: no user id specified.");
                        return 1;
                    }
                    int parseUserArg = UserHandle.parseUserArg(nextArg);
                    if (parseUserArg == -2) {
                        parseUserArg = ActivityManager.getCurrentUser();
                    }
                    if (IUserManager.Stub.asInterface(ServiceManager.getService("user")).markGuestForDeletion(parseUserArg)) {
                        return 0;
                    }
                    getErrPrintWriter().println("Error: could not mark guest for deletion");
                    return 1;
                case '<':
                    return runRenameUser();
                case '=':
                    return runSetUserRestriction();
                case '>':
                    runGetUserRestriction();
                    return 0;
                case '?':
                    getOutPrintWriter().println("Is multiuser supported: " + UserManager.supportsMultipleUsers());
                    return 0;
                case '@':
                    getOutPrintWriter().println("Maximum supported users: " + UserManager.getMaxSupportedUsers());
                    return 0;
                case 'A':
                    ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                    getOutPrintWriter().println("Maximum supported running users: " + activityManagerInternal.getMaxRunningUsers());
                    return 0;
                case 'B':
                    return runSetHomeActivity();
                case 'C':
                    return runSetInstaller();
                case 'D':
                    PrintWriter outPrintWriter3 = getOutPrintWriter();
                    try {
                        ComponentName instantAppResolverComponent = this.mInterface.getInstantAppResolverComponent();
                        if (instantAppResolverComponent == null) {
                            return 1;
                        }
                        outPrintWriter3.println(instantAppResolverComponent.flattenToString());
                        return 0;
                    } catch (Exception e) {
                        outPrintWriter3.println(e.toString());
                        return 1;
                    }
                case 'E':
                    return runHasFeature();
                case 'F':
                    return runSetHarmfulAppWarning();
                case 'G':
                    return runGetHarmfulAppWarning();
                case 'H':
                    return runListStagedSessions();
                case 'I':
                    return uninstallSystemUpdates(getNextArg());
                case 'J':
                    return runRollbackApp();
                case 'K':
                    return runGetModuleInfo();
                case 'L':
                    return runLogVisibility();
                case 'M':
                    return runBypassStagedInstallerCheck();
                case 'N':
                    return runBypassAllowedApexUpdateCheck();
                case 'O':
                    return runDisableVerificationForUid();
                case 'P':
                    return runSetSilentUpdatesPolicy();
                case 'Q':
                    return runGetAppMetadata();
                case 'R':
                    PrintWriter errPrintWriter = getErrPrintWriter();
                    String nextArg2 = getNextArg();
                    if (nextArg2 == null) {
                        errPrintWriter.println("Error: package name not specified");
                        return 1;
                    }
                    try {
                        this.mContext.getPackageManager().clearPackagePreferredActivities(nextArg2);
                        return 0;
                    } catch (Exception e2) {
                        errPrintWriter.println(e2.toString());
                        return 1;
                    }
                case 'S':
                    return runWaitForHandler(false);
                case 'T':
                    return runWaitForHandler(true);
                case 'U':
                    return runArchive();
                case 'V':
                    return runUnarchive();
                case 'W':
                    return runGetDomainVerificationAgent();
                case 'X':
                    return setAppCategoryHintUser();
                case 'Y':
                    return getApplicationCategoryHint();
                case 'Z':
                    this.mInterface.getAppCategoryHintUserMap().forEach(new PackageManagerShellCommand$$ExternalSyntheticLambda1(1, getOutPrintWriter()));
                    return 0;
                case '[':
                    this.mInterface.getAppCategoryInfos((String) null).forEach(new PackageManagerShellCommand$$ExternalSyntheticLambda1(0, getOutPrintWriter()));
                    return 0;
                default:
                    if (ART_SERVICE_COMMANDS.contains(str)) {
                        return runArtServiceCommand();
                    }
                    Boolean runCommand = this.mDomainVerificationShell.runCommand(this, str);
                    if (runCommand != null) {
                        return !runCommand.booleanValue() ? 1 : 0;
                    }
                    String nextArg3 = getNextArg();
                    if (nextArg3 == null) {
                        if (str.equalsIgnoreCase("-l")) {
                            return runListPackages(false, false);
                        }
                        if (str.equalsIgnoreCase("-lf")) {
                            return runListPackages(true, false);
                        }
                    } else if (getNextArg() == null && str.equalsIgnoreCase("-p")) {
                        return displayPackageFilePath(0, nextArg3);
                    }
                    return handleDefaultCommands(str);
            }
        } catch (RemoteException e3) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m("Remote exception: ", e3, outPrintWriter);
            return -1;
        }
        UiModeManagerService$13$$ExternalSyntheticOutline0.m("Remote exception: ", e3, outPrintWriter);
        return -1;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Package manager (package) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  path [--user USER_ID] PACKAGE", "    Print the path to the .apk of the given PACKAGE.", "", "  dump PACKAGE");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Print various system state associated with the given PACKAGE.", "", "  dump-package PACKAGE", "    Print package manager state associated with the given PACKAGE.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  has-feature FEATURE_NAME [version]", "    Prints true and returns exit status 0 when system has a FEATURE_NAME,", "    otherwise prints false and returns exit status 1");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  list features", "    Prints all features of the system.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  list instrumentation [-f] [TARGET-PACKAGE]", "    Prints all test packages; optionally only those targeting TARGET-PACKAGE", "    Options:", "      -f: dump the name of the .apk file containing the test package");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  list libraries [-v]", "    Prints all system libraries.", "    Options:");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -v: shows the location of the library in the device's filesystem", "", "  list packages [-f] [-d] [-e] [-s] [-q] [-3] [-i] [-l] [-u] [-U] ", "      [--show-versioncode] [--apex-only] [--factory-only]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      [--uid UID] [--user USER_ID] [FILTER]", "    Prints all packages; optionally only those whose name contains", "    the text in FILTER.  Options are:", "      -f: see their associated file");
        outPrintWriter.println("      -a: all known packages (but excluding APEXes)");
        outPrintWriter.println("      -d: filter to only show disabled packages");
        outPrintWriter.println("      -e: filter to only show enabled packages");
        outPrintWriter.println("      -s: filter to only show system packages");
        if (Flags.quarantinedEnabled()) {
            outPrintWriter.println("      -q: filter to only show quarantined packages");
        }
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -3: filter to only show third party packages", "      -i: see the installer for the packages", "      -l: ignored (used for compatibility with older releases)", "      -U: also show the package UID");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -u: also include uninstalled packages", "      --show-versioncode: also show the version code", "      --apex-only: only show APEX packages", "      --factory-only: only show system packages excluding updates");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --uid UID: filter to only show packages with the given UID", "      --user USER_ID: only list packages belonging to the given user", "      --match-libraries: include packages that declare static shared and SDK libraries", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  list permission-groups", "    Prints all known permission groups.", "", "  list permissions [-g] [-f] [-d] [-u] [GROUP]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Prints all known permissions; optionally only those in GROUP.  Options are:", "      -g: organize by group", "      -f: print all information", "      -s: short summary");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -d: only list dangerous permissions", "      -u: list only the permissions users will see", "", "  list staged-sessions [--only-ready] [--only-sessionid] [--only-parent]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Prints all staged sessions.", "      --only-ready: show only staged sessions that are ready", "      --only-sessionid: show only sessionId of each session", "      --only-parent: hide all children sessions");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  list users", "    Prints all users.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  resolve-activity [--brief] [--components] [--query-flags FLAGS]", "       [--user USER_ID] INTENT", "    Prints the activity that resolves to the given INTENT.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  query-activities [--brief] [--components] [--query-flags FLAGS]", "       [--user USER_ID] INTENT", "    Prints all activities that can handle the given INTENT.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  query-services [--brief] [--components] [--query-flags FLAGS]", "       [--user USER_ID] INTENT", "    Prints all services that can handle the given INTENT.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  query-receivers [--brief] [--components] [--query-flags FLAGS]", "       [--user USER_ID] INTENT", "    Prints all broadcast receivers that can handle the given INTENT.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  install [-rtfdg] [-i PACKAGE] [--user USER_ID|all|current]", "       [-p INHERIT_PACKAGE] [--install-location 0/1/2]", "       [--install-reason 0/1/2/3/4] [--originating-uri URI]", "       [--referrer URI] [--abi ABI_NAME] [--force-sdk]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "       [--preload] [--instant] [--full] [--dont-kill]", "       [--enable-rollback [0/1/2]]", "       [--force-uuid internal|UUID] [--pkg PACKAGE] [-S BYTES]", "       [--apex] [--non-staged] [--force-non-staged]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "       [--staged-ready-timeout TIMEOUT] [--ignore-dexopt-profile]", "       [--dexopt-compiler-filter FILTER]", "       [PATH [SPLIT...]|-]", "    Install an application.  Must provide the apk data to install, either as");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    file path(s) or '-' to read from stdin.  Options are:", "      -R: disallow replacement of existing application", "      -t: allow test packages", "      -i: specify package name of installer owning the app");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -f: install application on internal flash", "      -d: allow version code downgrade (debuggable packages only)", "      -p: partial application install (new split on top of existing pkg)", "      -g: grant all runtime permissions");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -S: size in bytes of package, required for stdin", "      --user: install under the given user.", "      --dont-kill: installing a new feature split, don't kill running app", "      --restrict-permissions: don't whitelist restricted permissions at install");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --originating-uri: set URI where app was downloaded from", "      --referrer: set URI that instigated the install of the app", "      --pkg: specify expected package name of app being installed", "      --abi: override the default ABI of the platform");
        outPrintWriter.println("      --instant: cause the app to be installed as an ephemeral install app");
        outPrintWriter.println("      --full: cause the app to be installed as a non-ephemeral full app");
        outPrintWriter.println("      --enable-rollback: enable rollbacks for the upgrade.");
        outPrintWriter.println("          0=restore (default), 1=wipe, 2=retain");
        if (Flags.recoverabilityDetection()) {
            outPrintWriter.println("      --rollback-impact-level: set device impact required for rollback.");
            outPrintWriter.println("          0=low (default), 1=high, 2=manual only");
        }
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --install-location: force the install location:", "          0=auto, 1=internal only, 2=prefer external", "      --install-reason: indicates why the app is being installed:", "          0=unknown, 1=admin policy, 2=device restore,");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          3=device setup, 4=user request", "      --update-ownership: request the update ownership enforcement", "      --force-uuid: force install on to disk volume with given UUID", "      --apex: install an .apex file, not an .apk");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --non-staged: explicitly set this installation to be non-staged.", "          This flag is only useful for APEX installs that are implicitly", "          assumed to be staged.", "      --force-non-staged: force the installation to run under a non-staged");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          session, which may complete without requiring a reboot. This will", "          force a rebootless update even for APEXes that don't support it", "      --staged-ready-timeout: By default, staged sessions wait 60000", "          milliseconds for pre-reboot verification to complete when");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          performing staged install. This flag is used to alter the waiting", "          time. You can skip the waiting time by specifying a TIMEOUT of '0'", "      --ignore-dexopt-profile: if set, all profiles are ignored by dexopt", "          during the installation, including the profile in the DM file and");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          the profile embedded in the APK file. If an invalid profile is", "          provided during installation, no warning will be reported by `adb", "          install`.", "          This option does not affect later dexopt operations (e.g.,");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          background dexopt and manual `pm compile` invocations).", "      --dexopt-compiler-filter: the target compiler filter for dexopt during", "          the installation. The filter actually used may be different.", "          Valid values: one of the values documented in");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          https://source.android.com/docs/core/runtime/configure#compiler_filters", "          or 'skip'", "", "  install-existing [--user USER_ID|all|current]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "       [--instant] [--full] [--wait] [--restrict-permissions] PACKAGE", "    Installs an existing application for a new user.  Options are:", "      --user: install for the given user.", "      --instant: install as an instant app");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --full: install as a full app", "      --wait: wait until the package is installed", "      --restrict-permissions: don't whitelist restricted permissions", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  install-create [-lrtsfdg] [-i PACKAGE] [--user USER_ID|all|current]", "       [-p INHERIT_PACKAGE] [--install-location 0/1/2]", "       [--install-reason 0/1/2/3/4] [--originating-uri URI]", "       [--referrer URI] [--abi ABI_NAME] [--force-sdk]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "       [--preload] [--instant] [--full] [--dont-kill]", "       [--force-uuid internal|UUID] [--pkg PACKAGE] [--apex] [-S BYTES]", "       [--multi-package] [--staged] [--update-ownership]", "    Like \"install\", but starts an install session.  Use \"install-write\"");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    to push data into the session, and \"install-commit\" to finish.", "", "  install-write [-S BYTES] SESSION_ID SPLIT_NAME [PATH|-]", "    Write an apk into the given install session.  If the path is '-', data");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    will be read from stdin.  Options are:", "      -S: size in bytes of package, required for stdin", "", "  install-remove SESSION_ID SPLIT...");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Mark SPLIT(s) as removed in the given install session.", "", "  install-add-session MULTI_PACKAGE_SESSION_ID CHILD_SESSION_IDs", "    Add one or more session IDs to a multi-package session.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  install-set-pre-verified-domains SESSION_ID PRE_VERIFIED_DOMAIN... ", "    Specify a comma separated list of pre-verified domains for a session.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  install-get-pre-verified-domains SESSION_ID", "    List all the pre-verified domains that are specified in a session.", "    The result list is comma separated.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  install-commit SESSION_ID", "    Commit the given active install session, installing the app.", "", "  install-abandon SESSION_ID");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Delete the given active install session.", "", "  set-install-location LOCATION", "    Changes the default install location.  NOTE this is only intended for debugging;");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    using this can cause applications to break and other undersireable behavior.", "    LOCATION is one of:", "    0 [auto]: Let system decide the best location", "    1 [internal]: Install on internal device storage");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    2 [external]: Install on external media", "", "  get-install-location", "    Returns the current install location: 0, 1 or 2 as per set-install-location.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  move-package PACKAGE [internal|UUID]", "", "  move-primary-storage [internal|UUID]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  uninstall [-k] [--user USER_ID] [--versionCode VERSION_CODE]", "       PACKAGE [SPLIT...]", "    Remove the given package name from the system.  May remove an entire app");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    if no SPLIT names specified, otherwise will remove only the splits of the", "    given app.  Options are:", "      -k: keep the data and cache directories around after package removal.", "      --user: remove the app from the given user.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --versionCode: only uninstall if the app has the given version code.", "", "  clear [--user USER_ID] [--cache-only] PACKAGE", "    Deletes data associated with a package. Options are:");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    --user: specifies the user for which we need to clear data", "    --cache-only: a flag which tells if we only need to clear cache data", "", "  enable [--user USER_ID] PACKAGE_OR_COMPONENT");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  disable [--user USER_ID] PACKAGE_OR_COMPONENT", "  disable-user [--user USER_ID] PACKAGE_OR_COMPONENT", "  disable-until-used [--user USER_ID] PACKAGE_OR_COMPONENT", "  default-state [--user USER_ID] PACKAGE_OR_COMPONENT");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    These commands change the enabled state of a given package or", "    component (written as \"package/class\").", "", "  hide [--user USER_ID] PACKAGE_OR_COMPONENT");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  unhide [--user USER_ID] PACKAGE_OR_COMPONENT", "", "  unstop [--user USER_ID] PACKAGE", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  suspend [--user USER_ID] PACKAGE [PACKAGE...]", "    Suspends the specified package(s) (as user).", "", "  unsuspend [--user USER_ID] PACKAGE [PACKAGE...]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Unsuspends the specified package(s) (as user).", "", "  set-distracting-restriction [--user USER_ID] [--flag FLAG ...]", "      PACKAGE [PACKAGE...]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Sets the specified restriction flags to given package(s) (for user).", "    Flags are:", "      hide-notifications: Hides notifications from this package", "      hide-from-suggestions: Hides this package from suggestions");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        (by the launcher, etc.)", "    Any existing flags are overwritten, which also means that if no flags are", "    specified then all existing flags will be cleared.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-distracting-restriction [--user USER_ID] PACKAGE [PACKAGE...]", "    Gets the specified restriction flags of given package(s) (of the user).", "", "  grant [--user USER_ID] [--all-permissions] PACKAGE PERMISSION");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  revoke [--user USER_ID] [--all-permissions] PACKAGE PERMISSION", "    These commands either grant or revoke permissions to apps.  The permissions", "    must be declared as used in the app's manifest, be runtime permissions", "    (protection level dangerous), and the app targeting SDK greater than Lollipop MR1.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Flags are:", "    --user: Specifies the user for which the operation needs to be performed", "    --all-permissions: If specified all the missing runtime permissions will", "       be granted to the PACKAGE or to all the packages if none is specified.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  set-permission-flags [--user USER_ID] PACKAGE PERMISSION [FLAGS..]", "  clear-permission-flags [--user USER_ID] PACKAGE PERMISSION [FLAGS..]", "    These commands either set or clear permission flags on apps.  The permissions");
        outPrintWriter.println("    must be declared as used in the app's manifest, be runtime permissions");
        outPrintWriter.println("    (protection level dangerous), and the app targeting SDK greater than Lollipop MR1.");
        outPrintWriter.println("    The flags must be one or more of " + SUPPORTED_PERMISSION_FLAGS_LIST);
        outPrintWriter.println("");
        outPrintWriter.println("  reset-permissions");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Revert all runtime permissions to their default state.", "", "  set-permission-enforced PERMISSION [true|false]", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-privapp-permissions TARGET-PACKAGE", "    Prints all privileged permissions for a package.", "", "  get-privapp-deny-permissions TARGET-PACKAGE");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Prints all privileged permissions that are denied for a package.", "", "  get-oem-permissions TARGET-PACKAGE", "    Prints all OEM permissions for a package.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  get-signature-permission-allowlist PARTITION", "    Prints the signature permission allowlist for a partition.", "    PARTITION is one of system, vendor, product, system-ext and apex");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  get-shared-uid-allowlist", "    Prints the shared UID allowlist.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  trim-caches DESIRED_FREE_SPACE [internal|UUID]", "    Trim cache files to reach the given free space.", "", "  list users");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Lists the current users.", "", "  create-user [--profileOf USER_ID] [--managed] [--restricted] [--guest]", "       [--user-type USER_TYPE] [--ephemeral] [--for-testing] [--pre-create-only]   USER_NAME");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Create a new user with the given USER_NAME, printing the new user identifier", "    of the user.", "    USER_TYPE is the name of a user type, e.g. android.os.usertype.profile.MANAGED.", "      If not specified, the default user type is android.os.usertype.full.SECONDARY.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --managed is shorthand for '--user-type android.os.usertype.profile.MANAGED'.", "      --restricted is shorthand for '--user-type android.os.usertype.full.RESTRICTED'.", "      --guest is shorthand for '--user-type android.os.usertype.full.GUEST'.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  remove-user [--set-ephemeral-if-in-use | --wait] USER_ID", "    Remove the user with the given USER_IDENTIFIER, deleting all data", "    associated with that user.", "      --set-ephemeral-if-in-use: If the user is currently running and");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        therefore cannot be removed immediately, mark the user as ephemeral", "        so that it will be automatically removed when possible (after user", "        switch or reboot)", "      --wait: Wait until user is removed. Ignored if set-ephemeral-if-in-use");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  mark-guest-for-deletion USER_ID", "    Mark the guest user for deletion. After this, it is possible to create a", "    new guest user and switch to it. This allows resetting the guest user");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    without switching to another user.", "", "  rename-user USER_ID [USER_NAME]", "    Rename USER_ID with USER_NAME (or null when [USER_NAME] is not set)");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  set-user-restriction [--user USER_ID] RESTRICTION VALUE", "", "  get-user-restriction [--user USER_ID] [--all] RESTRICTION_KEY");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Display the value of restriction for the given restriction key if the", "    given user is valid.", "      --all: display all restrictions for the given user", "          This option is used without restriction key");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  get-max-users", "", "  get-max-running-users");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  set-home-activity [--user USER_ID] TARGET-COMPONENT", "    Set the default home activity (aka launcher).", "    TARGET-COMPONENT can be a package name (com.package.my) or a full");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    component (com.package.my/component.name). However, only the package name", "    matters: the actual component used will be determined automatically from", "    the package.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  set-installer PACKAGE INSTALLER", "    Set installer package name", "", "  get-instantapp-resolver");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Return the name of the component that is the current instant app installer.", "", "  set-harmful-app-warning [--user <USER_ID>] <PACKAGE> [<WARNING>]", "    Mark the app as harmful with the given warning message.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-harmful-app-warning [--user <USER_ID>] <PACKAGE>");
        outPrintWriter.println("    Return the harmful app warning message for the given app, if present");
        outPrintWriter.println();
        outPrintWriter.println("  uninstall-system-updates [<PACKAGE>]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Removes updates to the given system application and falls back to its", "    /system version. Does nothing if the given package is not a system app.", "    If no package is specified, removes updates to all system applications.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-moduleinfo [--all | --installed] [module-name]", "    Displays module info. If module-name is specified only that info is shown", "    By default, without any argument only installed modules are shown.", "      --all: show all module info");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --installed: show only installed modules", "", "  log-visibility [--enable|--disable] <PACKAGE>", "    Turns on debug logging when visibility is blocked for the given package.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --enable: turn on debug logging (default)", "      --disable: turn off debug logging", "", "  set-silent-updates-policy [--allow-unlimited-silent-updates <INSTALLER>]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "                            [--throttle-time <SECONDS>] [--reset]", "    Sets the policies of the silent updates.", "      --allow-unlimited-silent-updates: allows unlimited silent updated", "        installation requests from the installer without the throttle time.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --throttle-time: update the silent updates throttle time in seconds.", "      --reset: restore the installer and throttle time to the default, and", "        clear tracks of silent updates in the system.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  clear-package-preferred-activities <PACKAGE>", "    Remove the preferred activity mappings for the given package.", "  wait-for-handler --timeout <MILLIS>", "    Wait for a given amount of time till the package manager handler finishes");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    handling all pending messages.", "      --timeout: wait for a given number of milliseconds. If the handler(s)", "        fail to finish before the timeout, the command returns error.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  wait-for-background-handler --timeout <MILLIS>", "    Wait for a given amount of time till the package manager's background", "    handler finishes handling all pending messages.", "      --timeout: wait for a given number of milliseconds. If the handler(s)");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        fail to finish before the timeout, the command returns error.", "", "  archive [--user USER_ID] PACKAGE ", "    During the archival process, the apps APKs and cache are removed from the");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    device while the user data is kept. Options are:", "      --user: archive the app from the given user.", "", "  request-unarchive [--user USER_ID] PACKAGE ");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Requests to unarchive a currently archived package by sending a request", "    to unarchive an app to the responsible installer. Options are:", "      --user: request unarchival of the app from the given user.", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-domain-verification-agent [--user USER_ID]", "    Displays the component name of the domain verification agent on device.", "    If the component isn't enabled, an error message will be displayed.", "      --user: return the agent of the given user (SYSTEM_USER if unspecified)");
        outPrintWriter.println("  get-package-storage-stats [--user <USER_ID>] <PACKAGE>");
        outPrintWriter.println("    Return the storage stats for the given app, if present");
        outPrintWriter.println("");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(getOutPrintWriter(), "  ");
        indentingPrintWriter.increaseIndent();
        try {
            ((ArtManagerLocal) LocalManagerRegistry.getManagerOrThrow(ArtManagerLocal.class)).printShellCommandHelp(indentingPrintWriter);
        } catch (LocalManagerRegistry.ManagerNotFoundException unused) {
            indentingPrintWriter.println("ART Service is not ready. Please try again later");
        }
        indentingPrintWriter.decreaseIndent();
        outPrintWriter.println("");
        this.mDomainVerificationShell.getClass();
        outPrintWriter.println("  get-app-links [--user <USER_ID>] [<PACKAGE>]");
        outPrintWriter.println("    Prints the domain verification state for the given package, or for all");
        outPrintWriter.println("    packages if none is specified. State codes are defined as follows:");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        - none: nothing has been recorded for this domain", "        - verified: the domain has been successfully verified", "        - approved: force approved, usually through shell", "        - denied: force denied, usually through shell");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        - migrated: preserved verification from a legacy response", "        - restored: preserved verification from a user data restore", "        - legacy_failure: rejected by a legacy verifier, unknown reason", "        - system_configured: automatically approved by the device config");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        - pre_verified: the domain was pre-verified by the installer", "        - >= 1024: Custom error code which is specific to the device verifier", "      --user <USER_ID>: include user selections (includes all domains, not", "        just autoVerify ones)");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  reset-app-links [--user <USER_ID>] [<PACKAGE>]", "    Resets domain verification state for the given package, or for all", "    packages if none is specified.", "      --user <USER_ID>: clear user selection state instead; note this means");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        domain verification state will NOT be cleared", "      <PACKAGE>: the package to reset, or \"all\" to reset all packages", "  verify-app-links [--re-verify] [<PACKAGE>]", "    Broadcasts a verification request for the given package, or for all");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    packages if none is specified. Only sends if the package has previously", "    not recorded a response.", "      --re-verify: send even if the package has recorded a response", "  set-app-links [--package <PACKAGE>] <STATE> <DOMAINS>...");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Manually set the state of a domain for a package. The domain must be", "    declared by the package as autoVerify for this to work. This command", "    will not report a failure for domains that could not be applied.", "      --package <PACKAGE>: the package to set, or \"all\" to set all packages");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      <STATE>: the code to set the domains to, valid values are:", "        STATE_NO_RESPONSE (0): reset as if no response was ever recorded.", "        STATE_SUCCESS (1): treat domain as successfully verified by domain.", "          verification agent. Note that the domain verification agent can");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          override this.", "        STATE_APPROVED (2): treat domain as always approved, preventing the", "           domain verification agent from changing it.", "        STATE_DENIED (3): treat domain as always denied, preveting the domain");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "          verification agent from changing it.", "      <DOMAINS>: space separated list of domains to change, or \"all\" to", "        change every domain.", "  set-app-links-user-selection --user <USER_ID> [--package <PACKAGE>]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      <ENABLED> <DOMAINS>...", "    Manually set the state of a host user selection for a package. The domain", "    must be declared by the package for this to work. This command will not", "    report a failure for domains that could not be applied.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --user <USER_ID>: the user to change selections for", "      --package <PACKAGE>: the package to set", "      <ENABLED>: whether or not to approve the domain", "      <DOMAINS>: space separated list of domains to change, or \"all\" to");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        change every domain.", "  set-app-links-allowed --user <USER_ID> [--package <PACKAGE>] <ALLOWED>", "    Toggle the auto verified link handling setting for a package.", "      --user <USER_ID>: the user to change selections for");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --package <PACKAGE>: the package to set, or \"all\" to set all packages", "        packages will be reset if no one package is specified.", "      <ALLOWED>: true to allow the package to open auto verified links, false", "        to disable");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-app-link-owners [--user <USER_ID>] [--package <PACKAGE>] [<DOMAINS>]", "    Print the owners for a specific domain for a given user in low to high", "    priority order.", "      --user <USER_ID>: the user to query for");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      --package <PACKAGE>: optionally also print for all web domains declared", "        by a package, or \"all\" to print all packages", "      --<DOMAINS>: space separated list of domains to query for", "");
        Intent.printIntentArgsHelp(outPrintWriter, "");
    }

    public final Pair openInFile(long j, String str) {
        ParcelFileDescriptor dup;
        if (PackageManagerShellCommandDataLoader.STDIN_PATH.equals(str)) {
            dup = ParcelFileDescriptor.dup(getInFileDescriptor());
        } else if (str != null) {
            ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "r");
            if (openFileForSystem == null) {
                return Pair.create(null, -1L);
            }
            long statSize = openFileForSystem.getStatSize();
            if (statSize < 0) {
                openFileForSystem.close();
                getErrPrintWriter().println("Unable to get size of: ".concat(str));
                return Pair.create(null, -1L);
            }
            dup = openFileForSystem;
            j = statSize;
        } else {
            dup = ParcelFileDescriptor.dup(getInFileDescriptor());
        }
        if (j > 0) {
            return Pair.create(dup, Long.valueOf(j));
        }
        getErrPrintWriter().println("Error: must specify an APK size");
        return Pair.create(null, 1L);
    }

    public final Intent parseIntentAndUser() {
        this.mTargetUser = -2;
        this.mBrief = false;
        this.mComponents = false;
        Intent parseCommandArgs = Intent.parseCommandArgs(this, new Intent.CommandOptionHandler() { // from class: com.android.server.pm.PackageManagerShellCommand.3
            public final boolean handleOption(String str, ShellCommand shellCommand) {
                if ("--user".equals(str)) {
                    PackageManagerShellCommand.this.mTargetUser = UserHandle.parseUserArg(shellCommand.getNextArgRequired());
                    return true;
                }
                if ("--brief".equals(str)) {
                    PackageManagerShellCommand.this.mBrief = true;
                    return true;
                }
                if ("--components".equals(str)) {
                    PackageManagerShellCommand.this.mComponents = true;
                    return true;
                }
                if (!"--query-flags".equals(str)) {
                    return false;
                }
                PackageManagerShellCommand.this.mQueryFlags = Integer.decode(shellCommand.getNextArgRequired()).intValue();
                return true;
            }
        });
        this.mTargetUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), this.mTargetUser, false, false, null, null);
        return parseCommandArgs;
    }

    public final void processArgForLocalFile(String str, PackageInstaller.Session session, boolean z) {
        ParcelFileDescriptor openFileForSystem;
        PackageManagerShellCommandDataLoader.Metadata metadata;
        long j;
        byte[] bArr;
        File file = new File(str);
        String name = file.getName();
        if (z) {
            metadata = PackageManagerShellCommandDataLoader.Metadata.forArchived(getArchivedPackage(-1L, str));
            j = 0;
        } else {
            PackageManagerShellCommandDataLoader.Metadata forLocalFile = PackageManagerShellCommandDataLoader.Metadata.forLocalFile(str);
            openFileForSystem = openFileForSystem(file.getPath(), "r");
            if (openFileForSystem == null) {
                throw new IllegalArgumentException("Error: Can't open file: " + file.getPath());
            }
            try {
                long statSize = openFileForSystem.getStatSize();
                IoUtils.closeQuietly(openFileForSystem);
                metadata = forLocalFile;
                j = statSize;
            } finally {
            }
        }
        if (!z && (openFileForSystem = openFileForSystem(str.concat(".idsig"), "r")) != null) {
            try {
                byte[] byteArray = V4Signature.readFrom(openFileForSystem).toByteArray();
                IoUtils.closeQuietly(openFileForSystem);
                bArr = byteArray;
            } catch (IOException e) {
                Slog.e("PackageManagerShellCommand", "V4 signature file exists but failed to be parsed.", e);
            } finally {
            }
            session.addFile(0, name, j, metadata.toByteArray(), bArr);
        }
        bArr = null;
        session.addFile(0, name, j, metadata.toByteArray(), bArr);
    }

    public final int processArgForStdin(String str, PackageInstaller.Session session) {
        int i;
        PackageManagerShellCommandDataLoader.Metadata metadata;
        String[] split = str.split(":");
        try {
            if (split.length < 2) {
                getErrPrintWriter().println("Must specify file name and size");
                return 1;
            }
            String str2 = split[0];
            long parseUnsignedLong = Long.parseUnsignedLong(split[1]);
            String str3 = (split.length <= 2 || TextUtils.isEmpty(split[2])) ? str2 : split[2];
            byte[] decode = split.length > 3 ? Base64.getDecoder().decode(split[3]) : null;
            if (split.length > 4) {
                i = Integer.parseUnsignedInt(split[4]);
                if (i < 0 || i > 1) {
                    getErrPrintWriter().println("Unsupported streaming version: " + i);
                    return 1;
                }
            } else {
                i = 0;
            }
            if (TextUtils.isEmpty(str2)) {
                getErrPrintWriter().println("Empty file name in: ".concat(str));
                return 1;
            }
            if (decode != null) {
                metadata = i == 0 ? new PackageManagerShellCommandDataLoader.Metadata((byte) 2, str3, (String) null) : new PackageManagerShellCommandDataLoader.Metadata((byte) 3, str3, (String) null);
                try {
                    if (decode.length > 0 && V4Signature.readFrom(decode) == null) {
                        getErrPrintWriter().println("V4 signature is invalid in: ".concat(str));
                        return 1;
                    }
                } catch (Exception e) {
                    getErrPrintWriter().println("V4 signature is invalid: " + e + " in " + str);
                    return 1;
                }
            } else {
                metadata = new PackageManagerShellCommandDataLoader.Metadata((byte) 0, str3, (String) null);
            }
            session.addFile(0, str2, parseUnsignedLong, metadata.toByteArray(), decode);
            return 0;
        } catch (IllegalArgumentException e2) {
            getErrPrintWriter().println("Unable to parse file parameters: " + str + ", reason: " + e2);
            return 1;
        }
    }

    public final int runArchive() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                int i2 = i == -1 ? 2 : 0;
                int translateUserId = translateUserId(i, 0, "runArchive");
                LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                try {
                    this.mInterface.getPackageInstaller().requestArchive(nextArg, "", i2, localIntentReceiver.getIntentSender(), new UserHandle(translateUserId));
                    Intent result = localIntentReceiver.getResult();
                    if (result.getIntExtra("android.content.pm.extra.STATUS", 1) == 0) {
                        outPrintWriter.println("Success");
                        return 0;
                    }
                    outPrintWriter.println("Failure [" + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE") + "]");
                    return 1;
                } catch (Exception e) {
                    outPrintWriter.println("Failure [" + e.getMessage() + "]");
                    return 1;
                }
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
            if (i != -1 && i != -2 && ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(i) == null) {
                ActiveServices$$ExternalSyntheticOutline0.m(i, outPrintWriter, "Failure [user ", " doesn't exist]");
                return 1;
            }
        }
    }

    public final int runArchivedInstall() {
        InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS);
        PackageInstaller.SessionParams sessionParams = makeInstallParams.sessionParams;
        sessionParams.installFlags |= 134217728;
        if (sessionParams.dataLoaderParams == null) {
            sessionParams.setDataLoaderParams(PackageManagerShellCommandDataLoader.getStreamingDataLoaderParams(this));
        }
        return doRunInstall(makeInstallParams);
    }

    public final int runArtServiceCommand() {
        try {
            ParcelFileDescriptor dup = ParcelFileDescriptor.dup(getInFileDescriptor());
            try {
                ParcelFileDescriptor dup2 = ParcelFileDescriptor.dup(getOutFileDescriptor());
                try {
                    ParcelFileDescriptor dup3 = ParcelFileDescriptor.dup(getErrFileDescriptor());
                    try {
                        int handleShellCommand = ((ArtManagerLocal) LocalManagerRegistry.getManagerOrThrow(ArtManagerLocal.class)).handleShellCommand(getTarget(), dup, dup2, dup3, getAllArgs());
                        if (dup3 != null) {
                            dup3.close();
                        }
                        if (dup2 != null) {
                            dup2.close();
                        }
                        if (dup != null) {
                            dup.close();
                        }
                        return handleShellCommand;
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (dup != null) {
                    try {
                        dup.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (LocalManagerRegistry.ManagerNotFoundException unused) {
            getErrPrintWriter().println("ART Service is not ready. Please try again later");
            return -1;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int runBypassAllowedApexUpdateCheck() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            this.mInterface.getPackageInstaller().bypassNextAllowedApexUpdateCheck(Boolean.parseBoolean(getNextArg()));
            return 0;
        } catch (RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return -1;
        }
    }

    public final int runBypassStagedInstallerCheck() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            this.mInterface.getPackageInstaller().bypassNextStagedInstallerCheck(Boolean.parseBoolean(getNextArg()));
            return 0;
        } catch (RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return -1;
        }
    }

    public final int runClear() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    getErrPrintWriter().println("Error: no package specified");
                    return 1;
                }
                if (this.shellRestrictionsHelper.isRestrictedPackage(5, nextArg)) {
                    getErrPrintWriter().println("Error: package cmd restricted - package: ".concat(nextArg));
                    return 1;
                }
                int translateUserId = translateUserId(i, -10000, "runClear");
                ClearDataObserver clearDataObserver = new ClearDataObserver();
                if (z) {
                    this.mInterface.deleteApplicationCacheFilesAsUser(nextArg, translateUserId, clearDataObserver);
                } else {
                    ActivityManager.getService().clearApplicationUserData(nextArg, false, clearDataObserver, translateUserId);
                }
                synchronized (clearDataObserver) {
                    while (!clearDataObserver.finished) {
                        try {
                            clearDataObserver.wait();
                        } catch (InterruptedException unused) {
                        }
                    }
                }
                if (clearDataObserver.result) {
                    getOutPrintWriter().println("Success");
                    return 0;
                }
                getErrPrintWriter().println("Failed");
                return 1;
            }
            if (nextOption.equals("--cache-only")) {
                z = true;
            } else {
                if (!nextOption.equals("--user")) {
                    outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    return 1;
                }
                i = UserHandle.parseUserArg(getNextArgRequired());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0007 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0084 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runCreateUser() {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerShellCommand.runCreateUser():int");
    }

    public final int runDisableVerificationForUid() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            int parseInt = Integer.parseInt(getNextArgRequired());
            if (((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getInstrumentationSourceUid(parseInt) != -1) {
                this.mInterface.getPackageInstaller().disableVerificationForUid(parseInt);
                return 0;
            }
            outPrintWriter.println("Error: must specify an instrumented uid");
            return -1;
        } catch (RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return -1;
        }
    }

    public final int runDump() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        ActivityManager.dumpPackageStateStatic(getOutFileDescriptor(), nextArg);
        return 0;
    }

    public final int runDumpPackage() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        try {
            this.mInterface.dump(getOutFileDescriptor(), new String[]{nextArg});
            return 0;
        } catch (Throwable th) {
            PrintWriter errPrintWriter = getErrPrintWriter();
            errPrintWriter.println("Failure dumping service:");
            th.printStackTrace(errPrintWriter);
            errPrintWriter.flush();
            return 0;
        }
    }

    public final void runGc() {
        Runtime.getRuntime().gc();
        getOutPrintWriter().println("Ok");
    }

    public final int runGetAppMetadata() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.GET_APP_METADATA", "getAppMetadataFd");
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            ParcelFileDescriptor appMetadataFd = this.mInterface.getAppMetadataFd(getNextArgRequired(), this.mContext.getUserId());
            if (appMetadataFd == null) {
                return 1;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ParcelFileDescriptor.AutoCloseInputStream(appMetadataFd)));
                while (bufferedReader.ready()) {
                    try {
                        outPrintWriter.println(bufferedReader.readLine());
                    } finally {
                    }
                }
                bufferedReader.close();
                return 1;
            } catch (IOException e) {
                outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                return -1;
            }
        } catch (RemoteException e2) {
            outPrintWriter.println("Failure [" + e2.getClass().getName() + " - " + e2.getMessage() + "]");
            return -1;
        }
    }

    public final int runGetArchivedPackageMetadata() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    Parcelable archivedPackage = this.mInterface.getArchivedPackage(nextArg, translateUserId(i, -10000, "runGetArchivedPackageMetadata"));
                    if (archivedPackage == null) {
                        outPrintWriter.write("Package not found " + nextArg);
                        return -1;
                    }
                    Parcel obtain = Parcel.obtain();
                    try {
                        obtain.writeParcelable(archivedPackage, 0);
                        byte[] marshall = obtain.marshall();
                        obtain.recycle();
                        outPrintWriter.write(HexEncoding.encodeToString(marshall));
                        return 0;
                    } catch (Throwable th) {
                        obtain.recycle();
                        throw th;
                    }
                } catch (Exception e) {
                    getErrPrintWriter().println("Failed to get archived package, reason: " + e);
                    outPrintWriter.println("Failure [failed to get archived package], reason: " + e);
                    return -1;
                }
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runGetDistractingRestriction() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                ArrayList remainingArgs = getRemainingArgs();
                if (remainingArgs.isEmpty()) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(outPrintWriter, "Distracting restrictions state for user ", i);
                int translateUserId = translateUserId(i, -10000, "get-distracting");
                String[] strArr = (String[]) remainingArgs.toArray(new String[0]);
                PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) this.mPm;
                packageManagerInternalImpl.getClass();
                int callingUid = Binder.getCallingUid();
                PackageManagerService packageManagerService = PackageManagerService.this;
                Computer snapshotComputer = packageManagerService.snapshotComputer();
                Objects.requireNonNull(strArr, "packageNames cannot be null");
                packageManagerService.mDistractingPackageHelper.getClass();
                int length = strArr.length;
                int[] iArr = new int[length];
                Arrays.fill(iArr, -1);
                if (!ArrayUtils.isEmpty(strArr)) {
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, translateUserId, strArr[i2]);
                        if (packageStateForInstalledAndFiltered != null) {
                            iArr[i2] = packageStateForInstalledAndFiltered.getUserStateOrDefault(translateUserId).getDistractionFlags();
                        }
                    }
                }
                for (int i3 = 0; i3 < length; i3++) {
                    int i4 = iArr[i3];
                    if (i4 == -1) {
                        ProxyManager$$ExternalSyntheticOutline0.m(outPrintWriter, strArr[i3], " not found ...", new StringBuilder());
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(strArr[i3]);
                        sb.append("  state: ");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, i4 != 0 ? i4 != 1 ? i4 != 2 ? "UNKNOWN" : "HIDE_NOTIFICATIONS" : "HIDE_FROM_SUGGESTIONS" : "NONE", outPrintWriter);
                    }
                }
                return 0;
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runGetDomainVerificationAgent() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                try {
                    ComponentName domainVerificationAgent = this.mInterface.getDomainVerificationAgent(translateUserId(i, 0, "runGetDomainVerificationAgent"));
                    outPrintWriter.println(domainVerificationAgent == null ? "No Domain Verifier available!" : domainVerificationAgent.flattenToString());
                    return 0;
                } catch (Exception e) {
                    outPrintWriter.println("Failure [" + e.getMessage() + "]");
                    return 1;
                }
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
            if (i != -1 && i != -2 && ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(i) == null) {
                ActiveServices$$ExternalSyntheticOutline0.m(i, outPrintWriter, "Failure [user ", " doesn't exist]");
                return 1;
            }
        }
    }

    public final int runGetHarmfulAppWarning() {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int translateUserId = translateUserId(i, -10000, "runGetHarmfulAppWarning");
                CharSequence harmfulAppWarning = this.mInterface.getHarmfulAppWarning(getNextArgRequired(), translateUserId);
                if (TextUtils.isEmpty(harmfulAppWarning)) {
                    return 1;
                }
                getOutPrintWriter().println(harmfulAppWarning);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void runGetInstallLocation() {
        int installLocation = this.mInterface.getInstallLocation();
        String str = installLocation == 0 ? "auto" : installLocation == 1 ? "internal" : installLocation == 2 ? "external" : "invalid";
        getOutPrintWriter().println(installLocation + "[" + str + "]");
    }

    public final int runGetModuleInfo() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                try {
                    if (nextArg != null) {
                        ModuleInfo moduleInfo = this.mInterface.getModuleInfo(nextArg, i);
                        outPrintWriter.println(moduleInfo.toString() + " packageName: " + moduleInfo.getPackageName());
                        return 1;
                    }
                    for (ModuleInfo moduleInfo2 : this.mInterface.getInstalledModules(i)) {
                        outPrintWriter.println(moduleInfo2.toString() + " packageName: " + moduleInfo2.getPackageName());
                    }
                    return 1;
                } catch (RemoteException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
            if (nextOption.equals("--all")) {
                i = 131072;
            } else if (!nextOption.equals("--installed")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
        }
    }

    public final int runGetOemPermissions() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified.");
            return 1;
        }
        Map map = (Map) SystemConfig.getInstance().mPermissionAllowlist.mOemAppAllowlist.get(nextArg);
        if (map == null || map.isEmpty()) {
            getOutPrintWriter().println("{}");
            return 0;
        }
        map.forEach(new PackageManagerShellCommand$$ExternalSyntheticLambda1(2, this));
        return 0;
    }

    public final int runGetPackageStorageStats() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (!Flags.getPackageStorageStats()) {
            outPrintWriter.println("Error: get_package_storage_stats flag is not enabled");
            return 1;
        }
        if (!android.app.usage.Flags.getAppBytesByDataTypeApi()) {
            outPrintWriter.println("Error: get_app_bytes_by_data_type_api flag is not enabled");
            return 1;
        }
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    StorageStats queryStatsForPackage = ((StorageStatsManager) this.mContext.getSystemService(StorageStatsManager.class)).queryStatsForPackage(StorageManager.UUID_DEFAULT, nextArg, UserHandle.of(translateUserId(i, -10000, "runGetPackageStorageStats")));
                    outPrintWriter.println("code: " + getDataSizeDisplay(queryStatsForPackage.getAppBytes()));
                    outPrintWriter.println("data: " + getDataSizeDisplay(queryStatsForPackage.getDataBytes()));
                    outPrintWriter.println("cache: " + getDataSizeDisplay(queryStatsForPackage.getCacheBytes()));
                    outPrintWriter.println("apk: " + getDataSizeDisplay(queryStatsForPackage.getAppBytesByDataType(3)));
                    outPrintWriter.println("lib: " + getDataSizeDisplay(queryStatsForPackage.getAppBytesByDataType(5)));
                    outPrintWriter.println("dm: " + getDataSizeDisplay(queryStatsForPackage.getAppBytesByDataType(4)));
                    outPrintWriter.println("dexopt artifacts: " + getDataSizeDisplay(queryStatsForPackage.getAppBytesByDataType(0)));
                    outPrintWriter.println("current profile : " + getDataSizeDisplay(queryStatsForPackage.getAppBytesByDataType(2)));
                    outPrintWriter.println("reference profile: " + getDataSizeDisplay(queryStatsForPackage.getAppBytesByDataType(1)));
                    outPrintWriter.println("external cache: " + getDataSizeDisplay(queryStatsForPackage.getExternalCacheBytes()));
                    return 0;
                } catch (Exception e) {
                    getErrPrintWriter().println("Failed to get storage stats, reason: " + e);
                    outPrintWriter.println("Failure [failed to get storage stats], reason: " + e);
                    return -1;
                }
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runGetPrivappDenyPermissions() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified.");
            return 1;
        }
        getOutPrintWriter().println(getPrivAppPermissionsString(nextArg, false));
        return 0;
    }

    public final int runGetPrivappPermissions() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified.");
            return 1;
        }
        getOutPrintWriter().println(getPrivAppPermissionsString(nextArg, true));
        return 0;
    }

    public final int runGetSignaturePermissionAllowlist() {
        PermissionAllowlist permissionAllowlist;
        ArrayMap arrayMap;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no partition specified.");
            return 1;
        }
        permissionAllowlist = SystemConfig.getInstance().mPermissionAllowlist;
        switch (nextArg) {
            case "system":
                arrayMap = permissionAllowlist.mSignatureAppAllowlist;
                break;
            case "vendor":
                arrayMap = permissionAllowlist.mVendorSignatureAppAllowlist;
                break;
            case "product":
                arrayMap = permissionAllowlist.mProductSignatureAppAllowlist;
                break;
            case "apex":
                arrayMap = permissionAllowlist.mApexSignatureAppAllowlist;
                break;
            case "system-ext":
                arrayMap = permissionAllowlist.mSystemExtSignatureAppAllowlist;
                break;
            default:
                getErrPrintWriter().println("Error: unknown partition: ".concat(nextArg));
                return 1;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(getOutPrintWriter(), "  ");
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayMap.keyAt(i);
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i);
            indentingPrintWriter.print("Package: ");
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
            int size2 = arrayMap2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                String str2 = (String) arrayMap2.keyAt(i2);
                if (((Boolean) arrayMap2.valueAt(i2)).booleanValue()) {
                    indentingPrintWriter.print("Permission: ");
                    indentingPrintWriter.println(str2);
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
        return 0;
    }

    public final void runGetUserRestriction() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int translateUserId = translateUserId(i, -10000, "runGetUserRestriction");
                IUserManager asInterface = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
                if (z) {
                    Bundle userRestrictions = asInterface.getUserRestrictions(translateUserId);
                    outPrintWriter.println("All restrictions:");
                    outPrintWriter.println(userRestrictions.toString());
                    return;
                } else {
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        throw new IllegalArgumentException("No restriction key specified");
                    }
                    if (getNextArg() != null) {
                        throw new IllegalArgumentException("Argument unexpected after restriction key");
                    }
                    outPrintWriter.println(asInterface.hasUserRestriction(nextArg, translateUserId));
                    return;
                }
            }
            if (nextOption.equals("--all")) {
                if (getNextArg() != null) {
                    throw new IllegalArgumentException("Argument unexpected after \"--all\"");
                }
                z = true;
            } else {
                if (!nextOption.equals("--user")) {
                    throw new IllegalArgumentException("Unknown option ".concat(nextOption));
                }
                i = UserHandle.parseUserArg(getNextArgRequired());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v17, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v25, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v26, types: [java.util.ArrayList] */
    public final int runGrantRevokePermission(boolean z) {
        List<PackageInfo> singletonList;
        PermissionInfo permissionInfo;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i2 = UserHandle.parseUserArg(getNextArgRequired());
            }
            if (nextOption.equals("--all-permissions")) {
                z2 = true;
            }
        }
        String nextArg = getNextArg();
        if (!z2 && nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        String nextArg2 = getNextArg();
        if (!z2 && nextArg2 == null) {
            getErrPrintWriter().println("Error: no permission specified");
            return 1;
        }
        if (z2 && nextArg2 != null) {
            getErrPrintWriter().println("Error: permission specified but not expected");
            return 1;
        }
        UserHandle of = UserHandle.of(translateUserId(i2, -10000, "runGrantRevokePermission"));
        PackageManager packageManager = this.mContext.createContextAsUser(of, 0).getPackageManager();
        if (nextArg == null) {
            singletonList = packageManager.getInstalledPackages(4096);
        } else {
            try {
                singletonList = Collections.singletonList(packageManager.getPackageInfo(nextArg, 4096));
            } catch (PackageManager.NameNotFoundException unused) {
                getErrPrintWriter().println("Error: package not found");
                getOutPrintWriter().println("Failure [package not found]");
                return 1;
            }
        }
        for (PackageInfo packageInfo : singletonList) {
            if (this.shellRestrictionsHelper.isRestrictedPackage(1, nextArg)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Error: package cmd restricted - package: "), packageInfo.packageName, getErrPrintWriter());
            } else {
                ?? singletonList2 = Collections.singletonList(nextArg2);
                if (z2) {
                    if (packageInfo.requestedPermissions == null) {
                        singletonList2 = new ArrayList();
                    } else {
                        singletonList2 = new ArrayList();
                        PackageManager packageManager2 = this.mContext.getPackageManager();
                        String[] strArr = packageInfo.requestedPermissions;
                        int length = strArr.length;
                        int i3 = i;
                        while (i3 < length) {
                            String str = strArr[i3];
                            try {
                                permissionInfo = packageManager2.getPermissionInfo(str, i);
                            } catch (PackageManager.NameNotFoundException unused2) {
                                permissionInfo = null;
                            }
                            if (permissionInfo != null && permissionInfo.getProtection() == 1) {
                                singletonList2.add(str);
                            }
                            i3++;
                            i = 0;
                        }
                    }
                }
                for (String str2 : singletonList2) {
                    if (z) {
                        try {
                            this.mPermissionManager.grantRuntimePermission(packageInfo.packageName, str2, of);
                        } catch (Exception e) {
                            if (!z2) {
                                throw e;
                            }
                            Slog.w("PackageManagerShellCommand", "Could not grant permission " + str2, e);
                        }
                    } else {
                        try {
                            this.mPermissionManager.revokeRuntimePermission(packageInfo.packageName, str2, of, (String) null);
                        } catch (Exception e2) {
                            if (!z2) {
                                throw e2;
                            }
                            Slog.w("PackageManagerShellCommand", "Could not grant permission " + str2, e2);
                        }
                    }
                }
                i = 0;
            }
        }
        return i;
    }

    public final int runHasFeature() {
        int parseInt;
        PrintWriter errPrintWriter = getErrPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            errPrintWriter.println("Error: expected FEATURE name");
            return 1;
        }
        String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            parseInt = 0;
        } else {
            try {
                parseInt = Integer.parseInt(nextArg2);
            } catch (RemoteException e) {
                errPrintWriter.println(e.toString());
                return 1;
            } catch (NumberFormatException unused) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(errPrintWriter, "Error: illegal version number ", nextArg2);
                return 1;
            }
        }
        boolean hasSystemFeature = this.mInterface.hasSystemFeature(nextArg, parseInt);
        getOutPrintWriter().println(hasSystemFeature);
        return !hasSystemFeature ? 1 : 0;
    }

    public final int runIncrementalInstall() {
        InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS);
        PackageInstaller.SessionParams sessionParams = makeInstallParams.sessionParams;
        if (sessionParams.dataLoaderParams == null) {
            sessionParams.setDataLoaderParams(PackageManagerShellCommandDataLoader.getIncrementalDataLoaderParams(this));
        }
        return doRunInstall(makeInstallParams);
    }

    public final void runInstallAbandon() {
        doAbandonSession(Integer.parseInt(getNextArg()), true);
    }

    public final int runInstallAddSession() {
        PackageInstaller.Session session;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = Integer.parseInt(getNextArg());
        IntArray intArray = new IntArray();
        while (true) {
            String nextArg = getNextArg();
            if (nextArg == null) {
                break;
            }
            intArray.add(Integer.parseInt(nextArg));
        }
        int i = 1;
        if (intArray.size() == 0) {
            outPrintWriter.println("Error: At least two sessions are required.");
            return 1;
        }
        int[] array = intArray.toArray();
        PrintWriter outPrintWriter2 = getOutPrintWriter();
        PackageInstaller.Session session2 = null;
        try {
            session = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(parseInt));
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (session.isMultiPackage()) {
                i = 0;
                for (int i2 : array) {
                    session.addChildSessionId(i2);
                }
                outPrintWriter2.println("Success");
            } else {
                getErrPrintWriter().println("Error: parent session ID is not a multi-package session");
            }
            IoUtils.closeQuietly(session);
            return i;
        } catch (Throwable th2) {
            th = th2;
            session2 = session;
            IoUtils.closeQuietly(session2);
            throw th;
        }
    }

    public final int runInstallCommit() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        long j = 60000;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = Integer.parseInt(getNextArg());
                if (doCommitSession(parseInt) != 0) {
                    return 1;
                }
                PackageInstaller.SessionInfo sessionInfo = this.mInterface.getPackageInstaller().getSessionInfo(parseInt);
                if (sessionInfo != null && sessionInfo.isStaged() && j > 0) {
                    return doWaitForStagedSessionReady(parseInt, j, outPrintWriter);
                }
                outPrintWriter.println("Success");
                return 0;
            }
            if (!nextOption.equals("--staged-ready-timeout")) {
                throw new IllegalArgumentException("Unknown option: ".concat(nextOption));
            }
            j = Long.parseLong(getNextArgRequired());
        }
    }

    public final void runInstallCreate() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_SESSION_CREATE_OPTS);
        ActiveServices$$ExternalSyntheticOutline0.m(doCreateSession(makeInstallParams.sessionParams, makeInstallParams.installerPackageName, makeInstallParams.userId), outPrintWriter, "Success: created install session [", "]");
    }

    public final int runInstallExisting() {
        int i;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i2 = -2;
        int i3 = 4194304;
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                int translateUserId = translateUserId(i2, -10000, "runInstallExisting");
                try {
                    if (z) {
                        LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                        IPackageInstaller packageInstaller = this.mInterface.getPackageInstaller();
                        outPrintWriter.println("Installing package " + nextArg + " for user: " + translateUserId);
                        packageInstaller.installExistingPackage(nextArg, i3, 0, localIntentReceiver.getIntentSender(), translateUserId, (List) null);
                        int intExtra = localIntentReceiver.getResult().getIntExtra("android.content.pm.extra.STATUS", 1);
                        outPrintWriter.println("Received intent for package install");
                        return intExtra == 0 ? 0 : 1;
                    }
                    if (this.mInterface.installExistingPackageAsUser(nextArg, translateUserId, i3, 0, (List) null) == -3) {
                        throw new PackageManager.NameNotFoundException("Package " + nextArg + " doesn't exist");
                    }
                    outPrintWriter.println("Package " + nextArg + " installed for user: " + translateUserId);
                    return 0;
                } catch (PackageManager.NameNotFoundException | RemoteException e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
            switch (nextOption) {
                case "--instant":
                case "--ephemeral":
                    i = (i3 | 2048) & (-16385);
                    break;
                case "--full":
                    i = (i3 & (-2049)) | EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                    break;
                case "--user":
                    i2 = UserHandle.parseUserArg(getNextArgRequired());
                    continue;
                case "--wait":
                    z = true;
                    continue;
                case "--restrict-permissions":
                    i = (-4194305) & i3;
                    break;
                default:
                    outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    return 1;
            }
            i3 = i;
        }
    }

    public final void runInstallGetPreVerifiedDomains() {
        PackageInstaller.Session session;
        PrintWriter outPrintWriter = getOutPrintWriter();
        PackageInstaller.Session session2 = null;
        try {
            session = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(Integer.parseInt(getNextArg())));
        } catch (Throwable th) {
            th = th;
        }
        try {
            Set preVerifiedDomains = session.getPreVerifiedDomains();
            if (preVerifiedDomains.isEmpty()) {
                outPrintWriter.println("The session doesn't have any pre-verified domains specified.");
            } else {
                outPrintWriter.println(String.join(",", preVerifiedDomains));
            }
            IoUtils.closeQuietly(session);
        } catch (Throwable th2) {
            th = th2;
            session2 = session;
            IoUtils.closeQuietly(session2);
            throw th;
        }
    }

    public final int runInstallRemove() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = Integer.parseInt(getNextArg());
        ArrayList remainingArgs = getRemainingArgs();
        if (!remainingArgs.isEmpty()) {
            return doRemoveSplits(parseInt, remainingArgs, true);
        }
        outPrintWriter.println("Error: split name not specified");
        return 1;
    }

    public final void runInstallSetPreVerifiedDomains() {
        PackageInstaller.Session session;
        getOutPrintWriter();
        int parseInt = Integer.parseInt(getNextArg());
        String[] split = getNextArg().split(",");
        PackageInstaller.Session session2 = null;
        try {
            session = new PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(parseInt));
        } catch (Throwable th) {
            th = th;
        }
        try {
            session.setPreVerifiedDomains(new ArraySet(split));
            IoUtils.closeQuietly(session);
        } catch (Throwable th2) {
            th = th2;
            session2 = session;
            IoUtils.closeQuietly(session2);
            throw th;
        }
    }

    public final int runInstallWrite() {
        long j = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                return doWriteSplit(Integer.parseInt(getNextArg()), getNextArg(), j, getNextArg(), true);
            }
            if (!nextOption.equals("-S")) {
                throw new IllegalArgumentException("Unknown option: ".concat(nextOption));
            }
            j = Long.parseLong(getNextArg());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runList() {
        char c;
        char c2;
        final int i = 0;
        final int i2 = 1;
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify type of data to list");
            return -1;
        }
        String str = null;
        switch (nextArg.hashCode()) {
            case -1126096540:
                if (nextArg.equals("staged-sessions")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -997447790:
                if (nextArg.equals("permission-groups")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (nextArg.equals("package")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -290659267:
                if (nextArg.equals(com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig.JSON_KEY_FEATURES)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3525497:
                if (nextArg.equals("sdks")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 111578632:
                if (nextArg.equals("users")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (nextArg.equals("instrumentation")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 750867693:
                if (nextArg.equals("packages")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 812757657:
                if (nextArg.equals("libraries")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1133704324:
                if (nextArg.equals("permissions")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1786251458:
                if (nextArg.equals("initial-non-stopped-system-packages")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                break;
            case 1:
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                List allPermissionGroups = this.mPermissionManager.getAllPermissionGroups(0);
                int size = allPermissionGroups.size();
                for (int i3 = 0; i3 < size; i3++) {
                    PermissionGroupInfo permissionGroupInfo = (PermissionGroupInfo) allPermissionGroups.get(i3);
                    outPrintWriter2.print("permission group:");
                    outPrintWriter2.println(permissionGroupInfo.name);
                }
                break;
            case 2:
            case 7:
                break;
            case 3:
                PrintWriter outPrintWriter3 = getOutPrintWriter();
                List list = this.mInterface.getSystemAvailableFeatures().getList();
                Collections.sort(list, new Comparator() { // from class: com.android.server.pm.PackageManagerShellCommand.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                String str2 = ((FeatureInfo) obj).name;
                                String str3 = ((FeatureInfo) obj2).name;
                                if (str2 == str3) {
                                    return 0;
                                }
                                if (str2 == null) {
                                    return -1;
                                }
                                if (str3 == null) {
                                    return 1;
                                }
                                return str2.compareTo(str3);
                            default:
                                return ((InstrumentationInfo) obj).targetPackage.compareTo(((InstrumentationInfo) obj2).targetPackage);
                        }
                    }
                });
                int size2 = list != null ? list.size() : 0;
                for (int i4 = 0; i4 < size2; i4++) {
                    FeatureInfo featureInfo = (FeatureInfo) list.get(i4);
                    outPrintWriter3.print("feature:");
                    String str2 = featureInfo.name;
                    if (str2 != null) {
                        outPrintWriter3.print(str2);
                        if (featureInfo.version > 0) {
                            outPrintWriter3.print("=");
                            outPrintWriter3.print(featureInfo.version);
                        }
                        outPrintWriter3.println();
                    } else {
                        outPrintWriter3.println("reqGlEsVersion=0x" + Integer.toHexString(featureInfo.reqGlEsVersion));
                    }
                }
                break;
            case 4:
                break;
            case 5:
                ServiceManager.getService("user").shellCommand(getInFileDescriptor(), getOutFileDescriptor(), getErrFileDescriptor(), new String[]{"list"}, getShellCallback(), adoptResultReceiver());
                break;
            case 6:
                PrintWriter outPrintWriter4 = getOutPrintWriter();
                boolean z = false;
                while (true) {
                    try {
                        String nextArg2 = getNextArg();
                        if (nextArg2 == null) {
                            List list2 = this.mInterface.queryInstrumentationAsUser(str, 4202496, 0).getList();
                            Collections.sort(list2, new Comparator() { // from class: com.android.server.pm.PackageManagerShellCommand.1
                                @Override // java.util.Comparator
                                public final int compare(Object obj, Object obj2) {
                                    switch (i2) {
                                        case 0:
                                            String str22 = ((FeatureInfo) obj).name;
                                            String str3 = ((FeatureInfo) obj2).name;
                                            if (str22 == str3) {
                                                return 0;
                                            }
                                            if (str22 == null) {
                                                return -1;
                                            }
                                            if (str3 == null) {
                                                return 1;
                                            }
                                            return str22.compareTo(str3);
                                        default:
                                            return ((InstrumentationInfo) obj).targetPackage.compareTo(((InstrumentationInfo) obj2).targetPackage);
                                    }
                                }
                            });
                            int size3 = list2 != null ? list2.size() : 0;
                            for (int i5 = 0; i5 < size3; i5++) {
                                InstrumentationInfo instrumentationInfo = (InstrumentationInfo) list2.get(i5);
                                outPrintWriter4.print("instrumentation:");
                                if (z) {
                                    outPrintWriter4.print(instrumentationInfo.sourceDir);
                                    outPrintWriter4.print("=");
                                }
                                outPrintWriter4.print(new ComponentName(instrumentationInfo.packageName, instrumentationInfo.name).flattenToShortString());
                                outPrintWriter4.print(" (target=");
                                outPrintWriter4.print(instrumentationInfo.targetPackage);
                                outPrintWriter4.println(")");
                            }
                            break;
                        } else {
                            if (nextArg2.hashCode() == 1497 && nextArg2.equals("-f")) {
                                z = true;
                            }
                            if (nextArg2.charAt(0) != '-') {
                                str = nextArg2;
                            } else {
                                outPrintWriter4.println("Error: Unknown option: " + nextArg2);
                            }
                        }
                    } catch (RuntimeException e) {
                        outPrintWriter4.println("Error: " + e.toString());
                    }
                }
                break;
            case '\b':
                PrintWriter outPrintWriter5 = getOutPrintWriter();
                boolean z2 = false;
                while (true) {
                    String nextArg3 = getNextArg();
                    if (nextArg3 == null) {
                        Map systemSharedLibraryNamesAndPaths = this.mInterface.getSystemSharedLibraryNamesAndPaths();
                        if (!systemSharedLibraryNamesAndPaths.isEmpty()) {
                            ArrayList arrayList = new ArrayList(systemSharedLibraryNamesAndPaths.keySet());
                            Collections.sort(arrayList, new PackageManagerShellCommand$$ExternalSyntheticLambda3());
                            for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                String str3 = (String) arrayList.get(i6);
                                outPrintWriter5.print("library:");
                                outPrintWriter5.print(str3);
                                if (z2) {
                                    outPrintWriter5.print(" path:");
                                    outPrintWriter5.print((String) systemSharedLibraryNamesAndPaths.get(str3));
                                }
                                outPrintWriter5.println();
                            }
                            break;
                        }
                    } else if (!nextArg3.equals("-v")) {
                        outPrintWriter5.println("Error: Unknown option: ".concat(nextArg3));
                        break;
                    } else {
                        z2 = true;
                    }
                }
                break;
            case '\t':
                PrintWriter outPrintWriter6 = getOutPrintWriter();
                boolean z3 = false;
                boolean z4 = false;
                boolean z5 = false;
                boolean z6 = false;
                boolean z7 = false;
                while (true) {
                    String nextOption = getNextOption();
                    if (nextOption != null) {
                        switch (nextOption.hashCode()) {
                            case 1495:
                                if (nextOption.equals("-d")) {
                                    c2 = 0;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1497:
                                if (nextOption.equals("-f")) {
                                    c2 = 1;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1498:
                                if (nextOption.equals("-g")) {
                                    c2 = 2;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1510:
                                if (nextOption.equals("-s")) {
                                    c2 = 3;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1512:
                                if (nextOption.equals("-u")) {
                                    c2 = 4;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            default:
                                c2 = 65535;
                                break;
                        }
                        switch (c2) {
                            case 0:
                                z3 = true;
                                break;
                            case 1:
                                z5 = true;
                                break;
                            case 2:
                                z4 = true;
                                break;
                            case 3:
                                z4 = true;
                                z5 = true;
                                z6 = true;
                                break;
                            case 4:
                                z7 = true;
                                break;
                            default:
                                outPrintWriter6.println("Error: Unknown option: ".concat(nextOption));
                                break;
                        }
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        if (z4) {
                            List allPermissionGroups2 = this.mPermissionManager.getAllPermissionGroups(0);
                            int size4 = allPermissionGroups2.size();
                            for (int i7 = 0; i7 < size4; i7++) {
                                arrayList2.add(((PermissionGroupInfo) allPermissionGroups2.get(i7)).name);
                            }
                            arrayList2.add(null);
                        } else {
                            arrayList2.add(getNextArg());
                        }
                        if (z3) {
                            outPrintWriter6.println("Dangerous Permissions:");
                            outPrintWriter6.println("");
                            doListPermissions(arrayList2, z4, z5, z6, 1, 1);
                            if (z7) {
                                outPrintWriter6.println("Normal Permissions:");
                                outPrintWriter6.println("");
                                doListPermissions(arrayList2, z4, z5, z6, 0, 0);
                                break;
                            }
                        } else if (z7) {
                            outPrintWriter6.println("Dangerous and Normal Permissions:");
                            outPrintWriter6.println("");
                            doListPermissions(arrayList2, z4, z5, z6, 0, 1);
                            break;
                        } else {
                            outPrintWriter6.println("All Permissions:");
                            outPrintWriter6.println("");
                            doListPermissions(arrayList2, z4, z5, z6, -10000, 10000);
                            break;
                        }
                    }
                }
                break;
            case '\n':
                PrintWriter outPrintWriter7 = getOutPrintWriter();
                List<String> initialNonStoppedSystemPackages = this.mInterface.getInitialNonStoppedSystemPackages();
                Collections.sort(initialNonStoppedSystemPackages);
                for (String str4 : initialNonStoppedSystemPackages) {
                    outPrintWriter7.print("package:");
                    outPrintWriter7.print(str4);
                    outPrintWriter7.println();
                }
                break;
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "Error: unknown list type '", nextArg, "'");
                break;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:200:0x02d9, code lost:
    
        if (r0 == null) goto L148;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0108 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x011e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0122 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0126 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x012e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0132 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x013c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0140 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0146 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x014a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x014d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0151 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0154 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0157 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x015b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0164 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0167 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0026 A[SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runListPackages(boolean r43, boolean r44) {
        /*
            Method dump skipped, instructions count: 1176
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerShellCommand.runListPackages(boolean, boolean):int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runListStagedSessions() {
        char c;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(getOutPrintWriter(), "  ", 120);
        try {
            SessionDump sessionDump = new SessionDump();
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    try {
                        printSessionList(indentingPrintWriter, this.mInterface.getPackageInstaller().getStagedSessions().getList(), sessionDump);
                        indentingPrintWriter.close();
                        return 1;
                    } catch (RemoteException e) {
                        indentingPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                        indentingPrintWriter.close();
                        return -1;
                    }
                }
                switch (nextOption.hashCode()) {
                    case -2056597429:
                        if (nextOption.equals("--only-parent")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1847964944:
                        if (nextOption.equals("--only-sessionid")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1321081314:
                        if (nextOption.equals("--only-ready")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        sessionDump.onlyParent = true;
                        break;
                    case 1:
                        sessionDump.onlySessionId = true;
                        break;
                    case 2:
                        sessionDump.onlyReady = true;
                        break;
                    default:
                        indentingPrintWriter.println("Error: Unknown option: " + nextOption);
                        indentingPrintWriter.close();
                        return -1;
                }
            }
        } catch (Throwable th) {
            try {
                indentingPrintWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final int runLogVisibility() {
        String nextOption;
        PrintWriter outPrintWriter = getOutPrintWriter();
        do {
            boolean z = true;
            while (true) {
                nextOption = getNextOption();
                if (nextOption == null) {
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        getErrPrintWriter().println("Error: no package specified");
                        return -1;
                    }
                    PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class));
                    packageManagerInternalImpl.getClass();
                    boolean z2 = PackageManagerServiceUtils.DEBUG;
                    if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
                        throw new SecurityException("Only the system or shell can set visibility logging.");
                    }
                    PackageSetting packageStateInternal = packageManagerInternalImpl.mService.snapshotComputer().getPackageStateInternal(nextArg);
                    if (packageStateInternal == null) {
                        throw new IllegalStateException("No package found for ".concat(nextArg));
                    }
                    PackageManagerService.this.mAppsFilter.mFeatureConfig.enableLogging(packageStateInternal.mAppId, z);
                    return 1;
                }
                if (!nextOption.equals("--disable")) {
                    break;
                }
                z = false;
            }
        } while (nextOption.equals("--enable"));
        outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
        return -1;
    }

    public final int runMovePackage() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: package name not specified");
            return 1;
        }
        String nextArg2 = getNextArg();
        if ("internal".equals(nextArg2)) {
            nextArg2 = null;
        }
        int movePackage = this.mInterface.movePackage(nextArg, nextArg2);
        int moveStatus = this.mInterface.getMoveStatus(movePackage);
        while (!PackageManager.isMoveStatusFinished(moveStatus)) {
            SystemClock.sleep(1000L);
            moveStatus = this.mInterface.getMoveStatus(movePackage);
        }
        if (moveStatus == -100) {
            getOutPrintWriter().println("Success");
            return 0;
        }
        ActiveServices$$ExternalSyntheticOutline0.m(moveStatus, getErrPrintWriter(), "Failure [", "]");
        return 1;
    }

    public final int runMovePrimaryStorage() {
        String nextArg = getNextArg();
        if ("internal".equals(nextArg)) {
            nextArg = null;
        }
        int movePrimaryStorage = this.mInterface.movePrimaryStorage(nextArg);
        int moveStatus = this.mInterface.getMoveStatus(movePrimaryStorage);
        while (!PackageManager.isMoveStatusFinished(moveStatus)) {
            SystemClock.sleep(1000L);
            moveStatus = this.mInterface.getMoveStatus(movePrimaryStorage);
        }
        if (moveStatus == -100) {
            getOutPrintWriter().println("Success");
            return 0;
        }
        ActiveServices$$ExternalSyntheticOutline0.m(moveStatus, getErrPrintWriter(), "Failure [", "]");
        return 1;
    }

    public final int runPath() {
        String nextOption = getNextOption();
        int parseUserArg = (nextOption == null || !nextOption.equals("--user")) ? 0 : UserHandle.parseUserArg(getNextArgRequired());
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        if (!CoreRune.BAIDU_CARLIFE || !BaiduCarlifeADBConnectUtils.isCarlifeForceConnect() || !"com.baidu.carlife".equals(nextArgRequired)) {
            return displayPackageFilePath(translateUserId(parseUserArg, -10000, "runPath"), nextArgRequired);
        }
        getOutPrintWriter().print("package:/data/app/~~iNjKNe-7WKMgdZXJDtvyIw==/com.baidu.carlife-TV26JIouWEDzMsoGghKuXg==/base.apk");
        return 0;
    }

    public final void runQueryIntentActivities() {
        try {
            Intent parseIntentAndUser = parseIntentAndUser();
            try {
                List list = this.mInterface.queryIntentActivities(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser).getList();
                PrintWriter outPrintWriter = getOutPrintWriter();
                if (list != null && list.size() > 0) {
                    int i = 0;
                    if (this.mComponents) {
                        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(outPrintWriter);
                        while (i < list.size()) {
                            printResolveInfo(printWriterPrinter, "", (ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                            i++;
                        }
                        return;
                    }
                    outPrintWriter.print(list.size());
                    outPrintWriter.println(" activities found:");
                    PrintWriterPrinter printWriterPrinter2 = new PrintWriterPrinter(outPrintWriter);
                    while (i < list.size()) {
                        outPrintWriter.print("  Activity #");
                        outPrintWriter.print(i);
                        outPrintWriter.println(":");
                        printResolveInfo(printWriterPrinter2, "    ", (ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                        i++;
                    }
                    return;
                }
                outPrintWriter.println("No activities found");
            } catch (RemoteException e) {
                throw new RuntimeException("Failed calling service", e);
            }
        } catch (URISyntaxException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public final void runQueryIntentReceivers() {
        try {
            Intent parseIntentAndUser = parseIntentAndUser();
            try {
                List list = this.mInterface.queryIntentReceivers(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser).getList();
                PrintWriter outPrintWriter = getOutPrintWriter();
                if (list != null && list.size() > 0) {
                    int i = 0;
                    if (this.mComponents) {
                        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(outPrintWriter);
                        while (i < list.size()) {
                            printResolveInfo(printWriterPrinter, "", (ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                            i++;
                        }
                        return;
                    }
                    outPrintWriter.print(list.size());
                    outPrintWriter.println(" receivers found:");
                    PrintWriterPrinter printWriterPrinter2 = new PrintWriterPrinter(outPrintWriter);
                    while (i < list.size()) {
                        outPrintWriter.print("  Receiver #");
                        outPrintWriter.print(i);
                        outPrintWriter.println(":");
                        printResolveInfo(printWriterPrinter2, "    ", (ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                        i++;
                    }
                    return;
                }
                outPrintWriter.println("No receivers found");
            } catch (RemoteException e) {
                throw new RuntimeException("Failed calling service", e);
            }
        } catch (URISyntaxException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public final void runQueryIntentServices() {
        try {
            Intent parseIntentAndUser = parseIntentAndUser();
            try {
                List list = this.mInterface.queryIntentServices(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser).getList();
                PrintWriter outPrintWriter = getOutPrintWriter();
                if (list != null && list.size() > 0) {
                    int i = 0;
                    if (this.mComponents) {
                        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(outPrintWriter);
                        while (i < list.size()) {
                            printResolveInfo(printWriterPrinter, "", (ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                            i++;
                        }
                        return;
                    }
                    outPrintWriter.print(list.size());
                    outPrintWriter.println(" services found:");
                    PrintWriterPrinter printWriterPrinter2 = new PrintWriterPrinter(outPrintWriter);
                    while (i < list.size()) {
                        outPrintWriter.print("  Service #");
                        outPrintWriter.print(i);
                        outPrintWriter.println(":");
                        printResolveInfo(printWriterPrinter2, "    ", (ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                        i++;
                    }
                    return;
                }
                outPrintWriter.println("No services found");
            } catch (RemoteException e) {
                throw new RuntimeException("Failed calling service", e);
            }
        } catch (URISyntaxException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.server.pm.PackageManagerShellCommand$4, com.android.server.pm.UserManagerInternal$UserLifecycleListener] */
    public final int runRemoveUser() {
        boolean z;
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    getErrPrintWriter().println("Error: no user id specified.");
                    return 1;
                }
                final int parseUserArg = UserHandle.parseUserArg(nextArg);
                IUserManager asInterface = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
                if (z2) {
                    BootReceiver$$ExternalSyntheticOutline0.m(parseUserArg, "Removing ", " or set as ephemeral if in use.", "PackageManagerShellCommand");
                    int removeUserWhenPossible = asInterface.removeUserWhenPossible(parseUserArg, false);
                    if (removeUserWhenPossible == -5) {
                        getErrPrintWriter().printf("Error: user %d is a permanent admin main user\n", Integer.valueOf(parseUserArg));
                        return 1;
                    }
                    if (removeUserWhenPossible == 0) {
                        getOutPrintWriter().printf("Success: user %d removed\n", Integer.valueOf(parseUserArg));
                    } else if (removeUserWhenPossible == 1) {
                        getOutPrintWriter().printf("Success: user %d set as ephemeral\n", Integer.valueOf(parseUserArg));
                    } else {
                        if (removeUserWhenPossible != 2) {
                            getErrPrintWriter().printf("Error: couldn't remove or mark ephemeral user id %d\n", Integer.valueOf(parseUserArg));
                            return 1;
                        }
                        getOutPrintWriter().printf("Success: user %d is already being removed\n", Integer.valueOf(parseUserArg));
                    }
                    return 0;
                }
                if (!z3) {
                    Slog.i("PackageManagerShellCommand", "Removing user " + parseUserArg);
                    if (!asInterface.removeUser(parseUserArg)) {
                        AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: couldn't remove user id ", parseUserArg);
                        return 1;
                    }
                    getOutPrintWriter().println("Success: removed user");
                    return 0;
                }
                Slog.i("PackageManagerShellCommand", "Removing (and waiting for completion) user " + parseUserArg);
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                ?? r5 = new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.pm.PackageManagerShellCommand.4
                    @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
                    public final void onUserRemoved(UserInfo userInfo) {
                        if (parseUserArg == userInfo.id) {
                            countDownLatch.countDown();
                        }
                    }
                };
                UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                userManagerInternal.addUserLifecycleListener(r5);
                try {
                    try {
                        if (!asInterface.removeUser(parseUserArg)) {
                            getErrPrintWriter().println("Error: couldn't remove user id " + parseUserArg);
                        } else {
                            if (countDownLatch.await(10L, TimeUnit.MINUTES)) {
                                getOutPrintWriter().println("Success: removed user");
                                return 0;
                            }
                            getErrPrintWriter().printf("Error: Remove user %d timed out\n", Integer.valueOf(parseUserArg));
                        }
                    } catch (InterruptedException e) {
                        getErrPrintWriter().printf("Error: Remove user %d wait interrupted: %s\n", Integer.valueOf(parseUserArg), e);
                        Thread.currentThread().interrupt();
                    }
                    return 1;
                } finally {
                    userManagerInternal.removeUserLifecycleListener(r5);
                }
            }
            switch (nextOption.hashCode()) {
                case -1095309356:
                    if (nextOption.equals("--set-ephemeral-if-in-use")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 1514:
                    if (nextOption.equals("-w")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1333511957:
                    if (nextOption.equals("--wait")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    z2 = true;
                    break;
                case true:
                case true:
                    z3 = true;
                    break;
                default:
                    getErrPrintWriter().println("Error: unknown option: ".concat(nextOption));
                    return -1;
            }
        }
    }

    public final int runRenameUser() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no user id specified.");
            return 1;
        }
        int parseUserArg = UserHandle.parseUserArg(nextArg);
        if (parseUserArg == -2) {
            parseUserArg = ActivityManager.getCurrentUser();
        }
        String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            HermesService$3$$ExternalSyntheticOutline0.m(parseUserArg, "Resetting name of user ", "PackageManagerShellCommand");
        } else {
            Slog.i("PackageManagerShellCommand", AccountManagerService$$ExternalSyntheticOutline0.m(parseUserArg, "Renaming user ", " to '", nextArg2, "'"));
        }
        IUserManager.Stub.asInterface(ServiceManager.getService("user")).setUserName(parseUserArg, nextArg2);
        return 0;
    }

    public final void runResetPermissions() {
        LegacyPermissionManagerService legacyPermissionManagerService = LegacyPermissionManagerService.this;
        legacyPermissionManagerService.mContext.enforceCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", "revokeRuntimePermission");
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0) {
            legacyPermissionManagerService.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "resetRuntimePermissions");
        }
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        final PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = (PermissionManagerService.PermissionManagerServiceInternalImpl) LocalServices.getService(PermissionManagerService.PermissionManagerServiceInternalImpl.class);
        for (final int i : UserManagerService.getInstance().getUserIds()) {
            packageManagerInternal.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$Internal$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl2 = PermissionManagerService.PermissionManagerServiceInternalImpl.this;
                    int i2 = i;
                    AndroidPackage androidPackage = (AndroidPackage) obj;
                    if (androidPackage.getUid() != -1) {
                        PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissions(androidPackage, i2);
                    }
                }
            });
        }
    }

    public final void runResolveActivity() {
        try {
            Intent parseIntentAndUser = parseIntentAndUser();
            try {
                ResolveInfo resolveIntent = this.mInterface.resolveIntent(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser);
                PrintWriter outPrintWriter = getOutPrintWriter();
                if (resolveIntent == null) {
                    outPrintWriter.println("No activity found");
                } else {
                    printResolveInfo(new PrintWriterPrinter(outPrintWriter), "", resolveIntent, this.mBrief, this.mComponents);
                }
            } catch (RemoteException e) {
                throw new RuntimeException("Failed calling service", e);
            }
        } catch (URISyntaxException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public final int runRollbackApp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        long j = 60000;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                if (nextArgRequired == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    Context createPackageContextAsUser = this.mContext.createPackageContextAsUser("com.android.shell", 0, Binder.getCallingUserHandle());
                    LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                    RollbackManager rollbackManager = (RollbackManager) createPackageContextAsUser.getSystemService(RollbackManager.class);
                    RollbackInfo rollbackInfo = null;
                    for (RollbackInfo rollbackInfo2 : rollbackManager.getAvailableRollbacks()) {
                        Iterator it = rollbackInfo2.getPackages().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (nextArgRequired.equals(((PackageRollbackInfo) it.next()).getPackageName())) {
                                rollbackInfo = rollbackInfo2;
                                break;
                            }
                        }
                    }
                    if (rollbackInfo == null) {
                        outPrintWriter.println("No available rollbacks for: ".concat(nextArgRequired));
                        return 1;
                    }
                    rollbackManager.commitRollback(rollbackInfo.getRollbackId(), Collections.emptyList(), localIntentReceiver.getIntentSender());
                    Intent result = localIntentReceiver.getResult();
                    if (result.getIntExtra("android.content.rollback.extra.STATUS", 1) != 0) {
                        outPrintWriter.println("Failure [" + result.getStringExtra("android.content.rollback.extra.STATUS_MESSAGE") + "]");
                        return 1;
                    }
                    if (rollbackInfo.isStaged() && j > 0) {
                        return doWaitForStagedSessionReady(rollbackInfo.getCommittedSessionId(), j, outPrintWriter);
                    }
                    outPrintWriter.println("Success");
                    return 0;
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!nextOption.equals("--staged-ready-timeout")) {
                throw new IllegalArgumentException("Unknown option: ".concat(nextOption));
            }
            j = Long.parseLong(getNextArgRequired());
        }
    }

    public final int runSetDistractingRestriction() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        int i2 = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                ArrayList remainingArgs = getRemainingArgs();
                if (remainingArgs.isEmpty()) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    String[] distractingPackageRestrictionsAsUser = this.mInterface.setDistractingPackageRestrictionsAsUser((String[]) remainingArgs.toArray(new String[0]), i2, translateUserId(i, -10000, "set-distracting"));
                    if (distractingPackageRestrictionsAsUser.length <= 0) {
                        return 0;
                    }
                    outPrintWriter.println("Could not set restriction for: " + Arrays.toString(distractingPackageRestrictionsAsUser));
                    return 1;
                } catch (RemoteException | IllegalArgumentException e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
            if (nextOption.equals("--flag")) {
                String nextArgRequired = getNextArgRequired();
                nextArgRequired.getClass();
                if (nextArgRequired.equals("hide-notifications")) {
                    i2 |= 2;
                } else {
                    if (!nextArgRequired.equals("hide-from-suggestions")) {
                        outPrintWriter.println("Unrecognized flag: ".concat(nextArgRequired));
                        return 1;
                    }
                    i2 |= 1;
                }
            } else {
                if (!nextOption.equals("--user")) {
                    outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    return 1;
                }
                i = UserHandle.parseUserArg(getNextArgRequired());
            }
        }
    }

    public final int runSetEnabledSetting(int i) {
        String nextOption = getNextOption();
        int parseUserArg = (nextOption == null || !nextOption.equals("--user")) ? 0 : UserHandle.parseUserArg(getNextArgRequired());
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package or component specified");
            return 1;
        }
        if (this.shellRestrictionsHelper.isRestrictedPackage(3, nextArg)) {
            getErrPrintWriter().println("Failed to change state of package: ".concat(nextArg));
            return 1;
        }
        int translateUserId = translateUserId(parseUserArg, -10000, "runSetEnabledSetting");
        ComponentName unflattenFromString = ComponentName.unflattenFromString(nextArg);
        if (unflattenFromString != null) {
            this.mInterface.setComponentEnabledSetting(unflattenFromString, i, 0, translateUserId, "shell");
            getOutPrintWriter().println("Component " + unflattenFromString.toShortString() + " new state: " + enabledSettingToString(this.mInterface.getComponentEnabledSetting(unflattenFromString, translateUserId)));
            return 0;
        }
        this.mInterface.setApplicationEnabledSetting(nextArg, i, 0, translateUserId, "shell:" + Process.myUid());
        PrintWriter outPrintWriter = getOutPrintWriter();
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Package ", nextArg, " new state: ");
        m.append(enabledSettingToString(this.mInterface.getApplicationEnabledSetting(nextArg, translateUserId)));
        outPrintWriter.println(m.toString());
        return 0;
    }

    public final int runSetHarmfulAppWarning() {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int translateUserId = translateUserId(i, -10000, "runSetHarmfulAppWarning");
                this.mInterface.setHarmfulAppWarning(getNextArgRequired(), getNextArg(), translateUserId);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runSetHiddenSetting(boolean z) {
        String nextOption = getNextOption();
        int parseUserArg = (nextOption == null || !nextOption.equals("--user")) ? 0 : UserHandle.parseUserArg(getNextArgRequired());
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package or component specified");
            return 1;
        }
        int translateUserId = translateUserId(parseUserArg, -10000, "runSetHiddenSetting");
        this.mInterface.setApplicationHiddenSettingAsUser(nextArg, z, translateUserId);
        PrintWriter outPrintWriter = getOutPrintWriter();
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Package ", nextArg, " new hidden state: ");
        m.append(this.mInterface.getApplicationHiddenSettingAsUser(nextArg, translateUserId));
        outPrintWriter.println(m.toString());
        return 0;
    }

    public final int runSetHomeActivity() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg.indexOf(47) >= 0) {
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(nextArg);
                    if (unflattenFromString == null) {
                        outPrintWriter.println("Error: invalid component name");
                        return 1;
                    }
                    nextArg = unflattenFromString.getPackageName();
                }
                String str = nextArg;
                int translateUserId = translateUserId(i, -10000, "runSetHomeActivity");
                final CompletableFuture completableFuture = new CompletableFuture();
                try {
                    ((RoleManager) this.mContext.getSystemService(RoleManager.class)).addRoleHolderAsUser("android.app.role.HOME", str, 0, UserHandle.of(translateUserId), FgThread.getExecutor(), new Consumer() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            completableFuture.complete((Boolean) obj);
                        }
                    });
                    if (((Boolean) completableFuture.get()).booleanValue()) {
                        outPrintWriter.println("Success");
                        return 0;
                    }
                    outPrintWriter.println("Error: Failed to set default home.");
                    return 1;
                } catch (Exception e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runSetInstallLocation() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no install location specified.");
            return 1;
        }
        try {
            if (this.mInterface.setInstallLocation(Integer.parseInt(nextArg))) {
                return 0;
            }
            getErrPrintWriter().println("Error: install location has to be a number.");
            return 1;
        } catch (NumberFormatException unused) {
            getErrPrintWriter().println("Error: install location has to be a number.");
            return 1;
        }
    }

    public final int runSetInstaller() {
        String nextArg = getNextArg();
        String nextArg2 = getNextArg();
        if (nextArg == null || nextArg2 == null) {
            getErrPrintWriter().println("Must provide both target and installer package names");
            return 1;
        }
        List list = SemSamsungThemeUtils.disableOverlayList;
        if ("com.samsung.android.themecenter".equals(nextArg2)) {
            getErrPrintWriter().println("Set installer failed with internal error");
            return 1;
        }
        this.mInterface.setInstallerPackageName(nextArg, nextArg2);
        getOutPrintWriter().println("Success");
        return 0;
    }

    public final int runSetPermissionEnforced() {
        if (getNextArg() == null) {
            getErrPrintWriter().println("Error: no permission specified");
            return 1;
        }
        if (getNextArg() != null) {
            return 0;
        }
        getErrPrintWriter().println("Error: no enforcement specified");
        return 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetSilentUpdatesPolicy() {
        char c;
        PrintWriter outPrintWriter = getOutPrintWriter();
        Long l = null;
        String str = null;
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (l != null && l.longValue() < 0) {
                    outPrintWriter.println("Error: Invalid value for \"--throttle-time\":" + l);
                    return -1;
                }
                try {
                    IPackageInstaller packageInstaller = this.mInterface.getPackageInstaller();
                    if (z) {
                        packageInstaller.setAllowUnlimitedSilentUpdates((String) null);
                        packageInstaller.setSilentUpdatesThrottleTime(-1L);
                    } else {
                        if (str != null) {
                            packageInstaller.setAllowUnlimitedSilentUpdates(str);
                        }
                        if (l != null) {
                            packageInstaller.setSilentUpdatesThrottleTime(l.longValue());
                        }
                    }
                    return 1;
                } catch (RemoteException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
            switch (nextOption.hashCode()) {
                case -1615291473:
                    if (nextOption.equals("--reset")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 771584496:
                    if (nextOption.equals("--throttle-time")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1002172770:
                    if (nextOption.equals("--allow-unlimited-silent-updates")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    z = true;
                    break;
                case 1:
                    l = Long.valueOf(Long.parseLong(getNextArgRequired()));
                    break;
                case 2:
                    str = getNextArgRequired();
                    break;
                default:
                    outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    return -1;
            }
        }
    }

    public final int runSetStoppedState() {
        String nextOption = getNextOption();
        int parseUserArg = (nextOption == null || !nextOption.equals("--user")) ? 0 : UserHandle.parseUserArg(getNextArgRequired());
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        int translateUserId = translateUserId(parseUserArg, -10000, "runSetStoppedState");
        this.mInterface.setPackageStoppedState(nextArg, false, translateUserId);
        PrintWriter outPrintWriter = getOutPrintWriter();
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Package ", nextArg, " new stopped state: ");
        m.append(this.mInterface.isPackageStoppedForUser(nextArg, translateUserId));
        outPrintWriter.println(m.toString());
        return 0;
    }

    public final int runSetUserRestriction() {
        String nextOption = getNextOption();
        int parseUserArg = (nextOption == null || !"--user".equals(nextOption)) ? 0 : UserHandle.parseUserArg(getNextArgRequired());
        String nextArg = getNextArg();
        String nextArg2 = getNextArg();
        boolean z = true;
        if (!"1".equals(nextArg2)) {
            if (!"0".equals(nextArg2)) {
                getErrPrintWriter().println("Error: valid value not specified");
                return 1;
            }
            z = false;
        }
        IUserManager.Stub.asInterface(ServiceManager.getService("user")).setUserRestriction(nextArg, z, translateUserId(parseUserArg, -10000, "runSetUserRestriction"));
        return 0;
    }

    public final int runStreamingInstall() {
        InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS);
        PackageInstaller.SessionParams sessionParams = makeInstallParams.sessionParams;
        if (sessionParams.dataLoaderParams == null) {
            sessionParams.setDataLoaderParams(PackageManagerShellCommandDataLoader.getStreamingDataLoaderParams(this));
        }
        return doRunInstall(makeInstallParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0146 A[Catch: RemoteException | IllegalArgumentException -> 0x015f, TryCatch #0 {RemoteException | IllegalArgumentException -> 0x015f, blocks: (B:82:0x0118, B:86:0x0146, B:88:0x0161, B:91:0x0177, B:94:0x0182, B:95:0x018e, B:97:0x0194, B:104:0x012f, B:106:0x0135, B:108:0x0142), top: B:81:0x0118 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0161 A[Catch: RemoteException | IllegalArgumentException -> 0x015f, TryCatch #0 {RemoteException | IllegalArgumentException -> 0x015f, blocks: (B:82:0x0118, B:86:0x0146, B:88:0x0161, B:91:0x0177, B:94:0x0182, B:95:0x018e, B:97:0x0194, B:104:0x012f, B:106:0x0135, B:108:0x0142), top: B:81:0x0118 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runSuspend(int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 510
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerShellCommand.runSuspend(int, boolean):int");
    }

    public final int runTrimCaches() {
        long j;
        long j2;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no size specified");
            return 1;
        }
        int length = nextArg.length() - 1;
        char charAt = nextArg.charAt(length);
        if (charAt < '0' || charAt > '9') {
            if (charAt == 'K' || charAt == 'k') {
                j = 1024;
            } else if (charAt == 'M' || charAt == 'm') {
                j = 1048576;
            } else {
                if (charAt != 'G' && charAt != 'g') {
                    getErrPrintWriter().println("Invalid suffix: " + charAt);
                    return 1;
                }
                j = 1073741824;
            }
            nextArg = nextArg.substring(0, length);
            j2 = j;
        } else {
            j2 = 1;
        }
        try {
            long parseLong = Long.parseLong(nextArg) * j2;
            String nextArg2 = getNextArg();
            if ("internal".equals(nextArg2)) {
                nextArg2 = null;
            }
            ClearDataObserver clearDataObserver = new ClearDataObserver();
            this.mInterface.freeStorageAndNotify(nextArg2, parseLong, 2, clearDataObserver);
            synchronized (clearDataObserver) {
                while (!clearDataObserver.finished) {
                    try {
                        clearDataObserver.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            return 0;
        } catch (NumberFormatException unused2) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: expected number at: ", nextArg);
            return 1;
        }
    }

    public final int runUnarchive() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    this.mInterface.getPackageInstaller().requestUnarchive(nextArg, this.mContext.getPackageName(), new LocalIntentReceiver().getIntentSender(), new UserHandle(translateUserId(i, 0, "runArchive")));
                    outPrintWriter.println("Success");
                    return 0;
                } catch (Exception e) {
                    outPrintWriter.println("Failure [" + e.getMessage() + "]");
                    return 1;
                }
            }
            if (!nextOption.equals("--user")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
            if (i != -1 && i != -2 && ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(i) == null) {
                ActiveServices$$ExternalSyntheticOutline0.m(i, outPrintWriter, "Failure [user ", " doesn't exist]");
                return 1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runUninstall() {
        boolean z;
        boolean z2;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        int i2 = -1;
        long j = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1502:
                        if (nextOption.equals("-k")) {
                            z2 = false;
                            break;
                        }
                        z2 = -1;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            z2 = true;
                            break;
                        }
                        z2 = -1;
                        break;
                    case 1884113221:
                        if (nextOption.equals("--versionCode")) {
                            z2 = 2;
                            break;
                        }
                        z2 = -1;
                        break;
                    default:
                        z2 = -1;
                        break;
                }
                switch (z2) {
                    case false:
                        i = 1;
                        break;
                    case true:
                        i2 = UserHandle.parseUserArg(getNextArgRequired());
                        if (i2 != -1 && i2 != -2 && ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(i2) == null) {
                            ActiveServices$$ExternalSyntheticOutline0.m(i2, outPrintWriter, "Failure [user ", " doesn't exist]");
                            return 1;
                        }
                        break;
                    case true:
                        j = Long.parseLong(getNextArgRequired());
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                        return 1;
                }
            } else {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                if (this.shellRestrictionsHelper.isRestrictedPackage(3, nextArg)) {
                    getErrPrintWriter().println("Failed to uninstall a package: ".concat(nextArg));
                    return 1;
                }
                ArrayList remainingArgs = getRemainingArgs();
                if (remainingArgs.isEmpty()) {
                    if (i2 == -1) {
                        i |= 2;
                    }
                    int translateUserId = translateUserId(i2, 0, "runUninstall");
                    LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                    PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class));
                    if (packageManagerInternalImpl.mService.snapshotComputer().isApexPackage(nextArg)) {
                        IntentSender intentSender = localIntentReceiver.getIntentSender();
                        if (!PackageManagerServiceUtils.isRootOrShell(Binder.getCallingUid())) {
                            throw new SecurityException("Not allowed to uninstall apexes");
                        }
                        PackageManagerService packageManagerService = PackageManagerService.this;
                        PackageInstallerService.PackageDeleteObserverAdapter packageDeleteObserverAdapter = new PackageInstallerService.PackageDeleteObserverAdapter(translateUserId, packageManagerService.mContext, intentSender, nextArg);
                        if ((2 & i) == 0) {
                            packageDeleteObserverAdapter.onPackageDeleted(nextArg, -5, "Can't uninstall an apex for a single user");
                        } else {
                            PackageInfo packageInfo = packageManagerInternalImpl.mService.snapshotComputer().getPackageInfo(nextArg, 1073741824L, 0);
                            if (packageInfo == null) {
                                packageDeleteObserverAdapter.onPackageDeleted(nextArg, -5, nextArg.concat(" is not an apex package"));
                            } else if (j == -1 || packageInfo.getLongVersionCode() == j) {
                                String str = packageInfo.applicationInfo.sourceDir;
                                ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) packageManagerService.mApexManager;
                                apexManagerImpl.getClass();
                                try {
                                    apexManagerImpl.waitForApexService().unstagePackages(Collections.singletonList(str));
                                    packageDeleteObserverAdapter.onPackageDeleted(nextArg, 1, null);
                                } catch (Exception unused) {
                                    packageDeleteObserverAdapter.onPackageDeleted(nextArg, -5, "Failed to uninstall apex ".concat(nextArg));
                                }
                            } else {
                                packageDeleteObserverAdapter.onPackageDeleted(nextArg, -5, "Active version " + packageInfo.getLongVersionCode() + " is not equal to " + j + "]");
                            }
                        }
                    } else {
                        if ((2 & i) == 0) {
                            PackageInfo packageInfo2 = this.mInterface.getPackageInfo(nextArg, 67108864L, translateUserId);
                            if (packageInfo2 == null) {
                                ActiveServices$$ExternalSyntheticOutline0.m(translateUserId, outPrintWriter, "Failure [not installed for ", "]");
                                return 1;
                            }
                            if ((packageInfo2.applicationInfo.flags & 1) != 0) {
                                i |= 4;
                            }
                        }
                        this.mInterface.getPackageInstaller().uninstall(new VersionedPackage(nextArg, j), (String) null, i, localIntentReceiver.getIntentSender(), translateUserId);
                    }
                    Intent result = localIntentReceiver.getResult();
                    if (result.getIntExtra("android.content.pm.extra.STATUS", 1) == 0) {
                        outPrintWriter.println("Success");
                        return 0;
                    }
                    outPrintWriter.println("Failure [" + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE") + "]");
                    return 1;
                }
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(2);
                sessionParams.installFlags = 2 | sessionParams.installFlags;
                sessionParams.appPackageName = nextArg;
                int doCreateSession = doCreateSession(sessionParams, null, -1);
                try {
                    if (doRemoveSplits(doCreateSession, remainingArgs, false) == 0) {
                        if (doCommitSession(doCreateSession) == 0) {
                            try {
                                outPrintWriter2.println("Success");
                                return 0;
                            } catch (Throwable th) {
                                th = th;
                                z = false;
                                if (z) {
                                    try {
                                        doAbandonSession(doCreateSession, false);
                                    } catch (RuntimeException unused2) {
                                    }
                                }
                                throw th;
                            }
                        }
                    }
                    try {
                        doAbandonSession(doCreateSession, false);
                    } catch (RuntimeException unused3) {
                    }
                    return 1;
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                }
            }
        }
    }

    public final int runWaitForHandler(boolean z) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        long j = 60000;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (j <= 0) {
                    outPrintWriter.println("Error: --timeout value must be positive: " + j);
                    return -1;
                }
                try {
                    if (this.mInterface.waitForHandler(j, z)) {
                        outPrintWriter.println("Success");
                        return 0;
                    }
                    outPrintWriter.println("Timeout. PackageManager handlers are still busy.");
                    return -1;
                } catch (RemoteException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
            if (!nextOption.equals("--timeout")) {
                outPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            j = Long.parseLong(getNextArgRequired());
        }
    }

    public final int setAppCategoryHintUser() {
        String nextArg = getNextArg();
        String nextArg2 = getNextArg();
        if (Integer.toString(-1).equals(nextArg2)) {
            this.mInterface.clearAppCategoryHintUser(nextArg);
        } else {
            this.mInterface.setAppCategoryHintUser(nextArg, Integer.parseInt(nextArg2));
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        ApplicationInfo applicationInfo = this.mInterface.getApplicationInfo(nextArg, 0L, 0);
        if (applicationInfo == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "app: ", nextArg, " not found!");
            return 1;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("app: ", nextArg, ", category: "), applicationInfo.category, outPrintWriter);
        return 0;
    }

    public final int setOrClearPermissionFlags(boolean z) {
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i = UserHandle.parseUserArg(getNextArgRequired());
            }
        }
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            getErrPrintWriter().println("Error: no permission specified");
            return 1;
        }
        String nextArg3 = getNextArg();
        if (nextArg3 == null) {
            getErrPrintWriter().println("Error: no permission flags specified");
            return 1;
        }
        int i2 = 0;
        while (nextArg3 != null) {
            ArrayMap arrayMap = (ArrayMap) SUPPORTED_PERMISSION_FLAGS;
            if (!arrayMap.containsKey(nextArg3)) {
                PrintWriter errPrintWriter = getErrPrintWriter();
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Error: specified flag ", nextArg3, " is not one of ");
                m.append(SUPPORTED_PERMISSION_FLAGS_LIST);
                errPrintWriter.println(m.toString());
                return 1;
            }
            i2 |= ((Integer) arrayMap.get(nextArg3)).intValue();
            nextArg3 = getNextArg();
        }
        this.mPermissionManager.updatePermissionFlags(nextArg, nextArg2, i2, z ? i2 : 0, UserHandle.of(translateUserId(i, -10000, "runGrantRevokePermission")));
        return 0;
    }

    public final int uninstallSystemUpdates(String str) {
        List<ApplicationInfo> list;
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            IPackageInstaller packageInstaller = this.mInterface.getPackageInstaller();
            if (str == null) {
                list = this.mInterface.getInstalledApplications(1056768L, 0).getList();
            } else {
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(this.mInterface.getApplicationInfo(str, 1056768L, 0));
                list = arrayList;
            }
            boolean z = false;
            for (ApplicationInfo applicationInfo : list) {
                if (applicationInfo.isUpdatedSystemApp()) {
                    outPrintWriter.println("Uninstalling updates to " + applicationInfo.packageName + "...");
                    LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
                    packageInstaller.uninstall(new VersionedPackage(applicationInfo.packageName, applicationInfo.versionCode), (String) null, 0, localIntentReceiver.getIntentSender(), 0);
                    if (localIntentReceiver.getResult().getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {
                        outPrintWriter.println("Couldn't uninstall package: " + applicationInfo.packageName);
                        z = true;
                    }
                }
            }
            if (z) {
                return 0;
            }
            outPrintWriter.println("Success");
            return 1;
        } catch (RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return 0;
        }
    }
}
