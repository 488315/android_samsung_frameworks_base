package com.android.server.slice;

import android.app.AppOpsManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.slice.ISliceManager;
import android.app.slice.SliceSpec;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.app.AssistUtils;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.pm.PackageManagerService;
import com.android.server.slice.PinnedSliceState;
import com.android.server.slice.SliceClientPermissions;
import com.android.server.slice.SlicePermissionManager;
import com.android.server.slice.SliceProviderPermissions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public final class SliceManagerService extends ISliceManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppOpsManager mAppOps;
    public final UsageStatsManagerInternal mAppUsageStats;
    public final AssistUtils mAssistUtils;
    public final Context mContext;
    public final Handler mHandler;
    public final PackageManagerInternal mPackageManagerInternal;
    public final SlicePermissionManager mPermissions;
    public final AnonymousClass1 mReceiver;
    public final Object mLock = new Object();
    public final ArrayMap mPinnedSlicesByUri = new ArrayMap();
    public final SparseArray mAssistantLookup = new SparseArray();
    public final SparseArray mHomeLookup = new SparseArray();
    public String mCachedDefaultHome = null;

    public final class Lifecycle extends SystemService {
        public SliceManagerService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 550) {
                SliceManagerService sliceManagerService = this.mService;
                int i2 = SliceManagerService.$r8$clinit;
                sliceManagerService.getClass();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.slice.SliceManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            Context context = getContext();
            ServiceThread serviceThread = new ServiceThread(10, "SliceManagerService", true);
            serviceThread.start();
            ?? sliceManagerService = new SliceManagerService(context, serviceThread.getLooper());
            this.mService = sliceManagerService;
            publishBinderService("slice", sliceManagerService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            SliceManagerService sliceManagerService = this.mService;
            final int userIdentifier = targetUser.getUserIdentifier();
            synchronized (sliceManagerService.mLock) {
                sliceManagerService.mPinnedSlicesByUri.values().removeIf(new Predicate() { // from class: com.android.server.slice.SliceManagerService$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ContentProvider.getUserIdFromUri(((PinnedSliceState) obj).mUri) == userIdentifier;
                    }
                });
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            SliceManagerService sliceManagerService = this.mService;
            targetUser.getUserIdentifier();
            int i = SliceManagerService.$r8$clinit;
            sliceManagerService.getClass();
        }
    }

    public final class PackageMatchingCache {
        public String mCurrentPkg;
        public final Supplier mPkgSource;

        public PackageMatchingCache(Supplier supplier) {
            this.mPkgSource = supplier;
        }
    }

    public final class RoleObserver implements OnRoleHoldersChangedListener {
        public final Executor mExecutor;

        public RoleObserver() {
            Executor mainExecutor = SliceManagerService.this.mContext.getMainExecutor();
            this.mExecutor = mainExecutor;
            RoleManager roleManager = (RoleManager) SliceManagerService.this.mContext.getSystemService(RoleManager.class);
            if (roleManager != null) {
                roleManager.addOnRoleHoldersChangedListenerAsUser(mainExecutor, this, UserHandle.ALL);
                SliceManagerService.this.mCachedDefaultHome = null;
            }
        }

        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            if ("android.app.role.HOME".equals(str)) {
                SliceManagerService.this.mCachedDefaultHome = null;
            }
        }
    }

    public SliceManagerService(Context context, Looper looper) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.slice.SliceManagerService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (intExtra == -10000) {
                    Slog.w("SliceManagerService", "Intent broadcast does not contain user handle: " + intent);
                    return;
                }
                Uri data = intent.getData();
                String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                if (schemeSpecificPart == null) {
                    Slog.w("SliceManagerService", "Intent broadcast does not contain package name: " + intent);
                    return;
                }
                String action = intent.getAction();
                action.getClass();
                if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                    SliceManagerService.this.mPermissions.removePkg(intExtra, schemeSpecificPart);
                } else if (action.equals("android.intent.action.PACKAGE_REMOVED") && !intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    SliceManagerService.this.mPermissions.removePkg(intExtra, schemeSpecificPart);
                }
            }
        };
        this.mContext = context;
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Objects.requireNonNull(packageManagerInternal);
        this.mPackageManagerInternal = packageManagerInternal;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAssistUtils = new AssistUtils(context);
        Handler handler = new Handler(looper);
        this.mHandler = handler;
        this.mAppUsageStats = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        this.mPermissions = new SlicePermissionManager(context, looper, new File(Environment.getDataDirectory(), "system/slice"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        new RoleObserver();
        context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, handler);
    }

    public final void applyRestore(byte[] bArr, int i) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Caller must be system");
        }
        if (bArr == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "applyRestore: no payload to restore for user ", "SliceManagerService");
            return;
        }
        if (i != 0) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "applyRestore: cannot restore policy for user ", "SliceManagerService");
            return;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(byteArrayInputStream, Xml.Encoding.UTF_8.name());
            this.mPermissions.readRestore(newPullParser);
        } catch (IOException | NumberFormatException | XmlPullParserException e) {
            Slog.w("SliceManagerService", "applyRestore: error reading payload", e);
        }
    }

    public final int checkSlicePermission(Uri uri, String str, int i, int i2, String[] strArr) {
        return checkSlicePermissionInternal(uri, str, null, i, i2, strArr);
    }

    public final int checkSlicePermissionInternal(Uri uri, String str, String str2, int i, int i2, String[] strArr) {
        boolean z;
        int userId = UserHandle.getUserId(i2);
        if (str2 == null) {
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i2);
            int length = packagesForUid.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = i3;
                if (checkSlicePermissionInternal(uri, str, packagesForUid[i3], i, i2, strArr) == 0) {
                    return 0;
                }
                i3 = i4 + 1;
            }
            return -1;
        }
        if (hasFullSliceAccess(userId, str2)) {
            return 0;
        }
        SlicePermissionManager slicePermissionManager = this.mPermissions;
        slicePermissionManager.getClass();
        SliceClientPermissions client = slicePermissionManager.getClient(new SlicePermissionManager.PkgUser(str2, userId));
        int userIdFromUri = ContentProvider.getUserIdFromUri(uri, userId);
        boolean z2 = true;
        if (!client.mHasFullAccess) {
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
            synchronized (client) {
                if ("content".equals(uriWithoutUserId.getScheme())) {
                    SlicePermissionManager.PkgUser pkgUser = new SlicePermissionManager.PkgUser(uriWithoutUserId.getAuthority(), userIdFromUri);
                    synchronized (client) {
                        SliceClientPermissions.SliceAuthority sliceAuthority = (SliceClientPermissions.SliceAuthority) client.mAuths.get(pkgUser);
                        if (sliceAuthority != null) {
                            List<String> pathSegments = uriWithoutUserId.getPathSegments();
                            Iterator it = sliceAuthority.mPaths.iterator();
                            while (it.hasNext()) {
                                if (SliceClientPermissions.SliceAuthority.isPathPrefixMatch((String[]) it.next(), (String[]) pathSegments.toArray(new String[pathSegments.size()]))) {
                                    z = true;
                                    break;
                                }
                            }
                        }
                        z = false;
                    }
                } else {
                    z = false;
                }
            }
            if (!z) {
                z2 = false;
            }
        }
        if (z2) {
            return 0;
        }
        if (strArr != null && str != null) {
            enforceOwner(str, uri, userId);
            verifyCaller(str);
            for (String str3 : strArr) {
                if (this.mContext.checkPermission(str3, i, i2) == 0) {
                    int userIdFromUri2 = ContentProvider.getUserIdFromUri(uri, userId);
                    this.mPermissions.grantSliceAccess(str2, userId, getProviderPkg(userIdFromUri2, uri), userIdFromUri2, uri);
                    return 0;
                }
            }
        }
        return -1;
    }

    public PinnedSliceState createPinnedSlice(Uri uri, String str) {
        return new PinnedSliceState(this, uri, str);
    }

    public final void enforceAccess(Uri uri, String str) {
        if (checkSlicePermissionInternal(uri, null, str, Binder.getCallingPid(), Binder.getCallingUid(), null) != 0 && !Objects.equals(str, getProviderPkg(ContentProvider.getUserIdFromUri(uri, Binder.getCallingUserHandle().getIdentifier()), uri))) {
            throw new SecurityException("Access to slice " + uri + " is required");
        }
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        if (ContentProvider.getUserIdFromUri(uri, identifier) != identifier) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Slice interaction across users requires INTERACT_ACROSS_USERS_FULL");
        }
    }

    public final void enforceOwner(String str, Uri uri, int i) {
        if (!Objects.equals(getProviderPkg(i, uri), str) || str == null) {
            throw new SecurityException("Caller must own " + uri);
        }
    }

    public final String[] getAllPackagesGranted(String str) {
        ArrayList arrayList;
        ArraySet arraySet;
        String providerPkg = getProviderPkg(0, new Uri.Builder().scheme("content").authority(str).build());
        if (providerPkg == null) {
            return new String[0];
        }
        SlicePermissionManager slicePermissionManager = this.mPermissions;
        slicePermissionManager.getClass();
        ArraySet arraySet2 = new ArraySet();
        SliceProviderPermissions provider = slicePermissionManager.getProvider(new SlicePermissionManager.PkgUser(providerPkg, 0));
        synchronized (provider) {
            arrayList = new ArrayList(provider.mAuths.values());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SliceProviderPermissions.SliceAuthority sliceAuthority = (SliceProviderPermissions.SliceAuthority) it.next();
            synchronized (sliceAuthority) {
                arraySet = new ArraySet(sliceAuthority.mPkgs);
            }
            Iterator it2 = arraySet.iterator();
            while (it2.hasNext()) {
                arraySet2.add(((SlicePermissionManager.PkgUser) it2.next()).mPkg);
            }
        }
        return (String[]) arraySet2.toArray(new String[arraySet2.size()]);
    }

    public final byte[] getBackupPayload(int i) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Caller must be system");
        }
        if (i != 0) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "getBackupPayload: cannot backup policy for user ", "SliceManagerService");
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            XmlSerializer newSerializer = XmlPullParserFactory.newInstance().newSerializer();
            newSerializer.setOutput(byteArrayOutputStream, Xml.Encoding.UTF_8.name());
            this.mPermissions.writeBackup(newSerializer);
            newSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | XmlPullParserException e) {
            Slog.w("SliceManagerService", "getBackupPayload: error writing payload for user " + i, e);
            return null;
        }
    }

    public String getDefaultHome(int i) {
        String str = this.mCachedDefaultHome;
        if (str != null) {
            return str;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            ComponentName homeActivitiesAsUser = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).mService.snapshotComputer().getHomeActivitiesAsUser(i, arrayList);
            this.mCachedDefaultHome = homeActivitiesAsUser != null ? homeActivitiesAsUser.getPackageName() : null;
            if (homeActivitiesAsUser == null) {
                int size = arrayList.size();
                int i2 = Integer.MIN_VALUE;
                for (int i3 = 0; i3 < size; i3++) {
                    ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i3);
                    if (resolveInfo.activityInfo.applicationInfo.isSystemApp() && resolveInfo.priority >= i2) {
                        homeActivitiesAsUser = resolveInfo.activityInfo.getComponentName();
                        i2 = resolveInfo.priority;
                    }
                }
            }
            String packageName = homeActivitiesAsUser != null ? homeActivitiesAsUser.getPackageName() : null;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return packageName;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final PinnedSliceState getPinnedSlice(Uri uri) {
        PinnedSliceState pinnedSliceState;
        synchronized (this.mLock) {
            try {
                pinnedSliceState = (PinnedSliceState) this.mPinnedSlicesByUri.get(uri);
                if (pinnedSliceState == null) {
                    throw new IllegalStateException("Slice " + uri.toString() + " not pinned");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return pinnedSliceState;
    }

    public final Uri[] getPinnedSlices(String str) {
        verifyCaller(str);
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                for (PinnedSliceState pinnedSliceState : this.mPinnedSlicesByUri.values()) {
                    if (Objects.equals(str, pinnedSliceState.mPkg)) {
                        Uri uri = pinnedSliceState.mUri;
                        if (ContentProvider.getUserIdFromUri(uri, identifier) == identifier) {
                            arrayList.add(ContentProvider.getUriWithoutUserId(uri));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
    }

    public final SliceSpec[] getPinnedSpecs(Uri uri, String str) {
        verifyCaller(str);
        enforceAccess(uri, str);
        return getPinnedSlice(ContentProvider.maybeAddUserId(uri, Binder.getCallingUserHandle().getIdentifier())).mSupportedSpecs;
    }

    public final String getProviderPkg(int i, Uri uri) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ProviderInfo resolveContentProviderAsUser = this.mContext.getPackageManager().resolveContentProviderAsUser(ContentProvider.getUriWithoutUserId(uri).getAuthority(), 0, ContentProvider.getUserIdFromUri(uri, i));
            return resolveContentProviderAsUser == null ? null : resolveContentProviderAsUser.packageName;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantPermissionFromUser(Uri uri, String str, String str2, boolean z) {
        verifyCaller(str2);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_SLICE_PERMISSIONS", "Slice granting requires MANAGE_SLICE_PERMISSIONS");
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        if (z) {
            SlicePermissionManager slicePermissionManager = this.mPermissions;
            slicePermissionManager.getClass();
            SliceClientPermissions client = slicePermissionManager.getClient(new SlicePermissionManager.PkgUser(str, identifier));
            if (!client.mHasFullAccess) {
                client.mHasFullAccess = true;
                client.mTracker.onPersistableDirty(client);
            }
        } else {
            Uri build = uri.buildUpon().path("").build();
            int userIdFromUri = ContentProvider.getUserIdFromUri(build, identifier);
            this.mPermissions.grantSliceAccess(str, identifier, getProviderPkg(userIdFromUri, build), userIdFromUri, build);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.getContentResolver().notifyChange(uri, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantSlicePermission(String str, String str2, Uri uri) {
        verifyCaller(str);
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        enforceOwner(str, uri, identifier);
        this.mPermissions.grantSliceAccess(str2, identifier, str, identifier, uri);
    }

    public final boolean hasFullSliceAccess(int i, String str) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!isDefaultHomeApp(i, str) && !isAssistant(i, str)) {
                SlicePermissionManager slicePermissionManager = this.mPermissions;
                slicePermissionManager.getClass();
                if (!slicePermissionManager.getClient(new SlicePermissionManager.PkgUser(str, i)).mHasFullAccess) {
                    z = false;
                    return z;
                }
            }
            z = true;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasSliceAccess(String str) {
        verifyCaller(str);
        return hasFullSliceAccess(Binder.getCallingUserHandle().getIdentifier(), str);
    }

    public final boolean isAssistant(int i, String str) {
        PackageMatchingCache packageMatchingCache = (PackageMatchingCache) this.mAssistantLookup.get(i);
        if (packageMatchingCache == null) {
            packageMatchingCache = new PackageMatchingCache(new SliceManagerService$$ExternalSyntheticLambda0(this, i, 0));
            this.mAssistantLookup.put(i, packageMatchingCache);
        }
        if (str == null) {
            return false;
        }
        if (str.equals(packageMatchingCache.mCurrentPkg)) {
            return true;
        }
        String str2 = (String) packageMatchingCache.mPkgSource.get();
        packageMatchingCache.mCurrentPkg = str2;
        return str.equals(str2);
    }

    public final boolean isDefaultHomeApp(int i, String str) {
        PackageMatchingCache packageMatchingCache = (PackageMatchingCache) this.mHomeLookup.get(i);
        if (packageMatchingCache == null) {
            packageMatchingCache = new PackageMatchingCache(new SliceManagerService$$ExternalSyntheticLambda0(this, i, 1));
            this.mHomeLookup.put(i, packageMatchingCache);
        }
        if (str == null) {
            return false;
        }
        if (str.equals(packageMatchingCache.mCurrentPkg)) {
            return true;
        }
        String str2 = (String) packageMatchingCache.mPkgSource.get();
        packageMatchingCache.mCurrentPkg = str2;
        return str.equals(str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new SliceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void pinSlice(final String str, Uri uri, SliceSpec[] sliceSpecArr, IBinder iBinder) {
        PinnedSliceState pinnedSliceState;
        verifyCaller(str);
        enforceAccess(uri, str);
        final int identifier = Binder.getCallingUserHandle().getIdentifier();
        Uri maybeAddUserId = ContentProvider.maybeAddUserId(uri, identifier);
        final String providerPkg = getProviderPkg(identifier, maybeAddUserId);
        synchronized (this.mLock) {
            try {
                pinnedSliceState = (PinnedSliceState) this.mPinnedSlicesByUri.get(maybeAddUserId);
                if (pinnedSliceState == null) {
                    pinnedSliceState = createPinnedSlice(maybeAddUserId, providerPkg);
                    this.mPinnedSlicesByUri.put(maybeAddUserId, pinnedSliceState);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (pinnedSliceState.mLock) {
            ArrayMap arrayMap = pinnedSliceState.mListeners;
            Binder.getCallingUid();
            Binder.getCallingPid();
            PinnedSliceState.ListenerInfo listenerInfo = new PinnedSliceState.ListenerInfo();
            listenerInfo.token = iBinder;
            arrayMap.put(iBinder, listenerInfo);
            try {
                iBinder.linkToDeath(pinnedSliceState.mDeathRecipient, 0);
            } catch (RemoteException unused) {
            }
            pinnedSliceState.mergeSpecs(sliceSpecArr);
            pinnedSliceState.setSlicePinned(true);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.slice.SliceManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SliceManagerService sliceManagerService = SliceManagerService.this;
                String str2 = providerPkg;
                String str3 = str;
                int i = identifier;
                sliceManagerService.getClass();
                if (str2 == null || Objects.equals(str3, str2)) {
                    return;
                }
                sliceManagerService.mAppUsageStats.reportEvent(i, (sliceManagerService.isAssistant(i, str3) || sliceManagerService.isDefaultHomeApp(i, str3)) ? 13 : 14, str2);
            }
        });
    }

    public final void removePinnedSlice(Uri uri) {
        synchronized (this.mLock) {
            ((PinnedSliceState) this.mPinnedSlicesByUri.remove(uri)).setSlicePinned(false);
        }
    }

    public final void revokeSlicePermission(String str, String str2, Uri uri) {
        verifyCaller(str);
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        enforceOwner(str, uri, identifier);
        SlicePermissionManager slicePermissionManager = this.mPermissions;
        slicePermissionManager.getClass();
        SliceClientPermissions.SliceAuthority orCreateAuthority = slicePermissionManager.getClient(new SlicePermissionManager.PkgUser(str2, identifier)).getOrCreateAuthority(new SlicePermissionManager.PkgUser(uri.getAuthority(), identifier), new SlicePermissionManager.PkgUser(str, identifier));
        List<String> pathSegments = uri.getPathSegments();
        String[] strArr = (String[]) pathSegments.toArray(new String[pathSegments.size()]);
        boolean z = false;
        for (int size = orCreateAuthority.mPaths.size() - 1; size >= 0; size--) {
            if (SliceClientPermissions.SliceAuthority.isPathPrefixMatch(strArr, (String[]) orCreateAuthority.mPaths.valueAt(size))) {
                orCreateAuthority.mPaths.removeAt(size);
                z = true;
            }
        }
        if (z) {
            orCreateAuthority.mTracker.onPersistableDirty(orCreateAuthority);
        }
    }

    public final void unpinSlice(String str, Uri uri, IBinder iBinder) {
        verifyCaller(str);
        enforceAccess(uri, str);
        Uri maybeAddUserId = ContentProvider.maybeAddUserId(uri, Binder.getCallingUserHandle().getIdentifier());
        try {
            PinnedSliceState pinnedSlice = getPinnedSlice(maybeAddUserId);
            synchronized (pinnedSlice.mLock) {
                iBinder.unlinkToDeath(pinnedSlice.mDeathRecipient, 0);
                pinnedSlice.mListeners.remove(iBinder);
            }
            if (!pinnedSlice.hasPinOrListener()) {
                removePinnedSlice(maybeAddUserId);
            }
        } catch (IllegalStateException e) {
            Slog.w("SliceManagerService", e.getMessage());
        }
    }

    public final void verifyCaller(String str) {
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
    }
}
