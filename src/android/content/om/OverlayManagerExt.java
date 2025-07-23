package android.content.om;

import android.app.ActivityThread;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayManagerExt;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.os.Build;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.content.om.OverlayScanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class OverlayManagerExt {
    public static final int CONFIG_ALL_USER_OVERLAY = 16;
    public static final int CONFIG_DELETE_RESOURCE_MAP_ON_STATE_CHANGE = 512;
    public static final int CONFIG_DISABLED_ON_INSTALL = 2048;
    public static final int CONFIG_ONLY_ON_DEFAULT_DISPLAY = 64;
    public static final int CONFIG_ON_ALL_DISPLAYS = 128;
    public static final int CONFIG_PROFILE_USER_OVERLAY = 32;
    public static final int CONFIG_PRUNE_TARGETS = 1024;
    public static final int CONFIG_RECREATE_IDMAP = 8;
    public static final int CONFIG_SKIP_IDMAP_DELETION_FOR_DUAL_APP_ID = 256;
    public static final int CONFIG_SKIP_IDMAP_UPDATE = 4;
    private final OverlayScanner mOverlayScanner;
    private PackageManager mPackageManager;
    private final IOverlayManager mService;
    public static final String TAG = "OverlayInfoExt";
    public static final boolean DEBUG = Build.IS_ENG;
    private static final String[] CATEGORY_PATH = {SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE};

    public static class OverlayParseFailedException extends Throwable {
    }

    public OverlayManagerExt() {
        this(IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")));
    }

    private OverlayManagerExt(IOverlayManager iOverlayManager) {
        this.mService = iOverlayManager;
        this.mOverlayScanner = new OverlayScanner();
    }

    public static boolean hasOverlayInfoExts(final int i, Context context) {
        return Arrays.stream(context.getResources().getAssets().getApkAssets()).anyMatch(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean startsWith;
                startsWith = ((ApkAssets) obj).getAssetPath().startsWith(OverlayManagerExt.CATEGORY_PATH[i]);
                return startsWith;
            }
        });
    }

    public void replaceOverlays(final OverlayStateChangeRequest overlayStateChangeRequest) throws OverlayParseFailedException {
        if (overlayStateChangeRequest.paths == null) {
            overlayStateChangeRequest.paths = new String[0];
        }
        List<OverlayInfoExt> list = (List) ((List) ((Stream) Arrays.stream(overlayStateChangeRequest.paths).filter(new OverlayManagerExt$$ExternalSyntheticLambda0()).parallel()).map(new Function() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                OverlayInfoExt lambda$replaceOverlays$1;
                lambda$replaceOverlays$1 = OverlayManagerExt.this.lambda$replaceOverlays$1(overlayStateChangeRequest, (String) obj);
                return lambda$replaceOverlays$1;
            }
        }).collect(Collectors.toList())).stream().filter(new OverlayManagerExt$$ExternalSyntheticLambda2()).collect(Collectors.toList());
        String[] strArr = overlayStateChangeRequest.paths;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str = strArr[i];
            final String replaceAll = str != null ? str.replaceAll("/+", "/") : null;
            if (!list.stream().anyMatch(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return OverlayManagerExt.lambda$replaceOverlays$2(replaceAll, (OverlayInfoExt) obj);
                }
            }) && overlayStateChangeRequest.callback != null) {
                try {
                    overlayStateChangeRequest.callback.onOverlayStateChanged(replaceAll, "", -1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        OverlayInfoExt[] allOverlays = getAllOverlays(overlayStateChangeRequest.category, overlayStateChangeRequest.userId);
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (OverlayInfoExt overlayInfoExt : allOverlays) {
            if (overlayInfoExt != null && overlayInfoExt.info != null) {
                hashMap.put(overlayInfoExt.info.baseCodePath, overlayInfoExt);
            }
        }
        if (overlayStateChangeRequest.pathsToRemove == null) {
            overlayStateChangeRequest.pathsToRemove = new String[0];
        }
        String[] strArr2 = overlayStateChangeRequest.pathsToRemove;
        int length2 = strArr2.length;
        for (int i2 = 0; i2 < length2; i2++) {
            String str2 = strArr2[i2];
            String replaceAll2 = str2 != null ? str2.replaceAll("/+", "/") : null;
            if (hashMap.containsKey(replaceAll2)) {
                arrayList.add((OverlayInfoExt) hashMap.get(replaceAll2));
            } else {
                OverlayInfoExt overlay = getOverlay(str2, overlayStateChangeRequest.userId);
                if (overlay != null) {
                    arrayList.add(overlay);
                } else if (overlayStateChangeRequest.callback != null) {
                    try {
                        overlayStateChangeRequest.callback.onOverlayStateChanged(replaceAll2, "", -1);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        try {
            this.mService.replaceOverlays(arrayList, list, overlayStateChangeRequest.callback, overlayStateChangeRequest.userId);
        } catch (RemoteException e3) {
            throw e3.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ OverlayInfoExt lambda$replaceOverlays$1(OverlayStateChangeRequest overlayStateChangeRequest, String str) {
        try {
            return parsePathToOverlayInfo(overlayStateChangeRequest.category, str, overlayStateChangeRequest.opsFlags, overlayStateChangeRequest.userId);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    static /* synthetic */ boolean lambda$replaceOverlays$2(String str, OverlayInfoExt overlayInfoExt) {
        return str != null && str.equals(overlayInfoExt.info.baseCodePath);
    }

    public List<OverlayInfoExt> addOverlayPaths(final OverlayStateChangeRequest overlayStateChangeRequest) throws OverlayParseFailedException {
        if (overlayStateChangeRequest.paths == null || overlayStateChangeRequest.paths.length == 0) {
            return new ArrayList();
        }
        List<OverlayInfoExt> list = (List) ((Stream) Arrays.stream(overlayStateChangeRequest.paths).filter(new OverlayManagerExt$$ExternalSyntheticLambda0()).parallel()).map(new Function() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                OverlayInfoExt lambda$addOverlayPaths$3;
                lambda$addOverlayPaths$3 = OverlayManagerExt.this.lambda$addOverlayPaths$3(overlayStateChangeRequest, (String) obj);
                return lambda$addOverlayPaths$3;
            }
        }).filter(new OverlayManagerExt$$ExternalSyntheticLambda2()).collect(Collectors.toList());
        if (overlayStateChangeRequest.callback != null) {
            String[] strArr = overlayStateChangeRequest.paths;
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                final String replaceAll = str != null ? str.replaceAll("/+", "/") : null;
                if (!list.stream().anyMatch(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return OverlayManagerExt.lambda$addOverlayPaths$4(replaceAll, (OverlayInfoExt) obj);
                    }
                })) {
                    try {
                        overlayStateChangeRequest.callback.onOverlayStateChanged(replaceAll, "", -1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            this.mService.addOverlays(list, overlayStateChangeRequest.callback, overlayStateChangeRequest.userId);
            return list;
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ OverlayInfoExt lambda$addOverlayPaths$3(OverlayStateChangeRequest overlayStateChangeRequest, String str) {
        try {
            return parsePathToOverlayInfo(overlayStateChangeRequest.category, str, overlayStateChangeRequest.opsFlags, overlayStateChangeRequest.userId);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    static /* synthetic */ boolean lambda$addOverlayPaths$4(String str, OverlayInfoExt overlayInfoExt) {
        return str != null && str.equals(overlayInfoExt.info.baseCodePath);
    }

    public void removeOverlayPaths(final OverlayStateChangeRequest overlayStateChangeRequest) {
        OverlayInfoExt[] allOverlays = getAllOverlays(overlayStateChangeRequest.category, overlayStateChangeRequest.userId);
        if (allOverlays == null || allOverlays.length == 0) {
            return;
        }
        if (overlayStateChangeRequest.paths == null) {
            overlayStateChangeRequest.paths = new String[0];
        }
        try {
            this.mService.removeOverlays((List) Arrays.stream(allOverlays).filter(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return OverlayManagerExt.lambda$removeOverlayPaths$5(OverlayManagerExt.OverlayStateChangeRequest.this, (OverlayInfoExt) obj);
                }
            }).collect(Collectors.toList()), overlayStateChangeRequest.callback, overlayStateChangeRequest.userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$removeOverlayPaths$5(OverlayStateChangeRequest overlayStateChangeRequest, OverlayInfoExt overlayInfoExt) {
        Stream stream = Arrays.stream(overlayStateChangeRequest.paths);
        String str = overlayInfoExt.info.baseCodePath;
        Objects.requireNonNull(str);
        return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda4(str));
    }

    public OverlayInfoExt[] getAllOverlays(int i, int i2) {
        try {
            return this.mService.getAllOverlaysInCategory(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OverlayInfoExt getOverlay(String str, int i) {
        try {
            return this.mService.getOverlayForPath(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) {
        try {
            return this.mService.getOverlaysForTarget(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setEnabled(OverlayInfoExt overlayInfoExt, int i, boolean z) {
        try {
            return this.mService.changeOverlayState(overlayInfoExt.info.packageName, i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setEnabled(String str, int i, boolean z) {
        return setEnabled(getOverlay(str, i), i, z);
    }

    public OverlayInfoExt parsePathToOverlayInfo(int i, String str, int i2, int i3) {
        if (this.mPackageManager == null) {
            this.mPackageManager = ActivityThread.currentApplication() != null ? ActivityThread.currentApplication().getPackageManager() : null;
        }
        PackageManager packageManager = this.mPackageManager;
        if (packageManager != null) {
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, PackageManager.PackageInfoFlags.of(0L));
            if (packageArchiveInfo != null) {
                String str2 = packageArchiveInfo.overlayTarget;
                if ((i == 0 || i == 2) && SamsungThemeConstants.overlayTargetMap.containsKey(packageArchiveInfo.overlayTarget)) {
                    str2 = SamsungThemeConstants.overlayTargetMap.get(packageArchiveInfo.overlayTarget);
                }
                return new OverlayInfoExt(i, i2, new OverlayInfo(packageArchiveInfo.packageName, null, str2, null, OverlayInfoExt.getFormattedCategory(packageArchiveInfo.overlayCategory, i, i2), str, -1, i3, packageArchiveInfo.overlayPriority, true, false));
            }
        } else {
            Slog.e(TAG, "ActivityThread was " + ActivityThread.currentActivityThread() + " app " + ActivityThread.currentApplication());
            OverlayScanner.ParsedOverlayInfo parseOverlayManifest = this.mOverlayScanner.parseOverlayManifest(new File(str), new ArrayList());
            if (parseOverlayManifest != null) {
                String str3 = parseOverlayManifest.targetPackageName;
                if ((i == 0 || i == 2) && SamsungThemeConstants.overlayTargetMap.containsKey(parseOverlayManifest.targetPackageName)) {
                    str3 = SamsungThemeConstants.overlayTargetMap.get(parseOverlayManifest.targetPackageName);
                }
                return new OverlayInfoExt(i, i2, new OverlayInfo(parseOverlayManifest.packageName, null, str3, null, OverlayInfoExt.getFormattedCategory("", i, i2), parseOverlayManifest.path.getAbsolutePath(), -1, i3, parseOverlayManifest.priority, true, false));
            }
        }
        return null;
    }

    public static class OverlayStateChangeRequest {
        public ISamsungOverlayCallback callback;
        public int category;
        public int opsFlags;
        public String[] paths;
        public String[] pathsToRemove;
        public int userId;

        public OverlayStateChangeRequest(String[] strArr, int i) {
            this(strArr, i, 0, UserHandle.myUserId(), null);
        }

        public OverlayStateChangeRequest(String[] strArr, int i, int i2) {
            this(strArr, i, i2, UserHandle.myUserId(), null);
        }

        public OverlayStateChangeRequest(String[] strArr, int i, int i2, ISamsungOverlayCallback iSamsungOverlayCallback) {
            this(strArr, i, i2, UserHandle.myUserId(), iSamsungOverlayCallback);
        }

        public OverlayStateChangeRequest(String[] strArr, int i, int i2, int i3, ISamsungOverlayCallback iSamsungOverlayCallback) {
            this.pathsToRemove = new String[0];
            this.paths = strArr;
            this.category = i;
            this.opsFlags = i2;
            this.userId = i3;
            this.callback = iSamsungOverlayCallback;
        }

        public OverlayStateChangeRequest addFlag(int i) {
            this.opsFlags = i | this.opsFlags;
            return this;
        }

        public OverlayStateChangeRequest addPathsToRemove(String[] strArr) {
            this.pathsToRemove = strArr;
            return this;
        }
    }
}
