package android.content.p001om;

import android.app.ActivityThread;
import android.content.Context;
import android.content.p001om.IOverlayManager;
import android.content.p001om.OverlayManagerExt;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.content.res.ApkAssets;
import android.p009os.Build;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.util.Slog;
import com.android.internal.content.p015om.OverlayScanner;
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
    public static final String TAG = OverlayInfoExt.class.getSimpleName();
    public static final boolean DEBUG = Build.IS_ENG;
    private static final String[] CATEGORY_PATH = {SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE, SamsungThemeConstants.PATH_OVERLAY_CURRENT_LOCALE_APKS};

    public static class OverlayParseFailedException extends Throwable {
    }

    public OverlayManagerExt() {
        this(IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")));
    }

    private OverlayManagerExt(IOverlayManager service) {
        this.mService = service;
        this.mOverlayScanner = new OverlayScanner();
    }

    public static boolean hasOverlayInfoExts(final int category, Context context) {
        return Arrays.stream(context.getResources().getAssets().getApkAssets()).anyMatch(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean startsWith;
                startsWith = ((ApkAssets) obj).getAssetPath().startsWith(OverlayManagerExt.CATEGORY_PATH[category]);
                return startsWith;
            }
        });
    }

    public void replaceOverlays(final OverlayStateChangeRequest request) throws OverlayParseFailedException {
        char c;
        OverlayInfoExt[] allOverlays;
        char c2;
        int i = 0;
        if (request.paths == null) {
            request.paths = new String[0];
        }
        List<OverlayInfoExt> overlaysToAdd = (List) ((List) ((Stream) Arrays.stream(request.paths).filter(new OverlayManagerExt$$ExternalSyntheticLambda2()).parallel()).map(new Function() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                OverlayInfoExt lambda$replaceOverlays$1;
                lambda$replaceOverlays$1 = OverlayManagerExt.this.lambda$replaceOverlays$1(request, (String) obj);
                return lambda$replaceOverlays$1;
            }
        }).collect(Collectors.toList())).stream().filter(new OverlayManagerExt$$ExternalSyntheticLambda4()).collect(Collectors.toList());
        String[] strArr = request.paths;
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            c = 65535;
            if (i2 >= length) {
                break;
            }
            String path = strArr[i2];
            final String normalizedPath = path != null ? path.replaceAll("/+", "/") : null;
            boolean isExists = overlaysToAdd.stream().anyMatch(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return OverlayManagerExt.lambda$replaceOverlays$2(normalizedPath, (OverlayInfoExt) obj);
                }
            });
            if (!isExists && request.callback != null) {
                try {
                    request.callback.onOverlayStateChanged(normalizedPath, "", -1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            i2++;
        }
        OverlayInfoExt[] allOverlays2 = getAllOverlays(request.category, request.userId);
        List<OverlayInfoExt> overlaysToRemove = new ArrayList<>();
        HashMap<String, OverlayInfoExt> overlayMap = new HashMap<>();
        for (OverlayInfoExt overlay : allOverlays2) {
            if (overlay != null && overlay.info != null) {
                overlayMap.put(overlay.info.baseCodePath, overlay);
            }
        }
        if (request.pathsToRemove == null) {
            request.pathsToRemove = new String[0];
        }
        String[] strArr2 = request.pathsToRemove;
        int length2 = strArr2.length;
        while (i < length2) {
            String path2 = strArr2[i];
            String normalizedPath2 = path2 != null ? path2.replaceAll("/+", "/") : null;
            if (!overlayMap.containsKey(normalizedPath2)) {
                OverlayInfoExt overlay2 = getOverlay(path2, request.userId);
                if (overlay2 != null) {
                    overlaysToRemove.add(overlay2);
                    allOverlays = allOverlays2;
                    c2 = 65535;
                } else if (request.callback != null) {
                    try {
                        allOverlays = allOverlays2;
                        c2 = 65535;
                        try {
                            request.callback.onOverlayStateChanged(normalizedPath2, "", -1);
                        } catch (RemoteException e2) {
                            e = e2;
                            e.printStackTrace();
                            i++;
                            c = c2;
                            allOverlays2 = allOverlays;
                        }
                    } catch (RemoteException e3) {
                        e = e3;
                        allOverlays = allOverlays2;
                        c2 = 65535;
                    }
                } else {
                    allOverlays = allOverlays2;
                    c2 = 65535;
                }
            } else {
                overlaysToRemove.add(overlayMap.get(normalizedPath2));
                allOverlays = allOverlays2;
                c2 = c;
            }
            i++;
            c = c2;
            allOverlays2 = allOverlays;
        }
        try {
            this.mService.replaceOverlays(overlaysToRemove, overlaysToAdd, request.callback, request.userId);
        } catch (RemoteException e4) {
            throw e4.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ OverlayInfoExt lambda$replaceOverlays$1(OverlayStateChangeRequest request, String path) {
        try {
            return parsePathToOverlayInfo(request.category, path, request.opsFlags, request.userId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    static /* synthetic */ boolean lambda$replaceOverlays$2(String normalizedPath, OverlayInfoExt infoExt) {
        return normalizedPath != null && normalizedPath.equals(infoExt.info.baseCodePath);
    }

    public List<OverlayInfoExt> addOverlayPaths(final OverlayStateChangeRequest request) throws OverlayParseFailedException {
        if (request.paths == null || request.paths.length == 0) {
            return new ArrayList();
        }
        List<OverlayInfoExt> overlayInfoExts = (List) ((Stream) Arrays.stream(request.paths).filter(new OverlayManagerExt$$ExternalSyntheticLambda2()).parallel()).map(new Function() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                OverlayInfoExt lambda$addOverlayPaths$3;
                lambda$addOverlayPaths$3 = OverlayManagerExt.this.lambda$addOverlayPaths$3(request, (String) obj);
                return lambda$addOverlayPaths$3;
            }
        }).filter(new OverlayManagerExt$$ExternalSyntheticLambda4()).collect(Collectors.toList());
        if (request.callback != null) {
            String[] strArr = request.paths;
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String path = strArr[i];
                final String normalizedPath = path != null ? path.replaceAll("/+", "/") : null;
                boolean isExists = overlayInfoExts.stream().anyMatch(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return OverlayManagerExt.lambda$addOverlayPaths$4(normalizedPath, (OverlayInfoExt) obj);
                    }
                });
                if (!isExists) {
                    try {
                        request.callback.onOverlayStateChanged(normalizedPath, "", -1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            this.mService.addOverlays(overlayInfoExts, request.callback, request.userId);
            return overlayInfoExts;
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ OverlayInfoExt lambda$addOverlayPaths$3(OverlayStateChangeRequest request, String path) {
        try {
            return parsePathToOverlayInfo(request.category, path, request.opsFlags, request.userId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    static /* synthetic */ boolean lambda$addOverlayPaths$4(String normalizedPath, OverlayInfoExt infoExt) {
        return normalizedPath != null && normalizedPath.equals(infoExt.info.baseCodePath);
    }

    public void removeOverlayPaths(final OverlayStateChangeRequest request) {
        OverlayInfoExt[] allOverlays = getAllOverlays(request.category, request.userId);
        if (allOverlays == null || allOverlays.length == 0) {
            return;
        }
        if (request.paths == null) {
            request.paths = new String[0];
        }
        List<OverlayInfoExt> overlayInfoExts = (List) Arrays.stream(allOverlays).filter(new Predicate() { // from class: android.content.om.OverlayManagerExt$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return OverlayManagerExt.lambda$removeOverlayPaths$5(OverlayManagerExt.OverlayStateChangeRequest.this, (OverlayInfoExt) obj);
            }
        }).collect(Collectors.toList());
        try {
            this.mService.removeOverlays(overlayInfoExts, request.callback, request.userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$removeOverlayPaths$5(OverlayStateChangeRequest request, OverlayInfoExt overlay) {
        Stream stream = Arrays.stream(request.paths);
        String str = overlay.info.baseCodePath;
        Objects.requireNonNull(str);
        return stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda1(str));
    }

    public OverlayInfoExt[] getAllOverlays(int category, int userId) {
        try {
            return this.mService.getAllOverlaysInCategory(category, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OverlayInfoExt getOverlay(String path, int userId) {
        try {
            return this.mService.getOverlayForPath(path, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OverlayInfoExt[] getOverlaysForTarget(String packageName, int category, int userId) {
        try {
            return this.mService.getOverlaysForTarget(packageName, category, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setEnabled(OverlayInfoExt overlay, int userId, boolean enabled) {
        try {
            return this.mService.changeOverlayState(overlay.info.packageName, userId, enabled);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setEnabled(String path, int userId, boolean enabled) {
        return setEnabled(getOverlay(path, userId), userId, enabled);
    }

    public OverlayInfoExt parsePathToOverlayInfo(int category, String path, int opsFlags, int userId) {
        String overlayTargetPackageName;
        if (this.mPackageManager == null) {
            this.mPackageManager = ActivityThread.currentApplication() != null ? ActivityThread.currentApplication().getPackageManager() : null;
        }
        PackageManager packageManager = this.mPackageManager;
        if (packageManager != null) {
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.PackageInfoFlags.m11of(0L));
            if (packageInfo != null) {
                String overlayTargetPackageName2 = packageInfo.overlayTarget;
                if ((category == 0 || category == 2) && SamsungThemeConstants.overlayTargetMap.containsKey(packageInfo.overlayTarget)) {
                    overlayTargetPackageName = SamsungThemeConstants.overlayTargetMap.get(packageInfo.overlayTarget);
                } else {
                    overlayTargetPackageName = overlayTargetPackageName2;
                }
                OverlayInfo info = new OverlayInfo(packageInfo.packageName, null, overlayTargetPackageName, null, OverlayInfoExt.getFormattedCategory(packageInfo.overlayCategory, category, opsFlags), path, -1, userId, packageInfo.overlayPriority, true, false);
                return new OverlayInfoExt(category, opsFlags, info);
            }
        } else {
            Slog.m115e(TAG, "ActivityThread was " + ActivityThread.currentActivityThread() + " app " + ActivityThread.currentApplication());
            OverlayScanner.ParsedOverlayInfo parsedOverlayInfo = this.mOverlayScanner.parseOverlayManifest(new File(path), new ArrayList());
            if (parsedOverlayInfo != null) {
                String overlayTargetPackageName3 = parsedOverlayInfo.targetPackageName;
                if ((category == 0 || category == 2) && SamsungThemeConstants.overlayTargetMap.containsKey(parsedOverlayInfo.targetPackageName)) {
                    overlayTargetPackageName3 = SamsungThemeConstants.overlayTargetMap.get(parsedOverlayInfo.targetPackageName);
                }
                OverlayInfo info2 = new OverlayInfo(parsedOverlayInfo.packageName, null, overlayTargetPackageName3, null, OverlayInfoExt.getFormattedCategory("", category, opsFlags), parsedOverlayInfo.path.getAbsolutePath(), -1, userId, parsedOverlayInfo.priority, true, false);
                return new OverlayInfoExt(category, opsFlags, info2);
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

        public OverlayStateChangeRequest(String[] paths, int category) {
            this(paths, category, 0, UserHandle.myUserId(), null);
        }

        public OverlayStateChangeRequest(String[] paths, int category, int opsFlags) {
            this(paths, category, opsFlags, UserHandle.myUserId(), null);
        }

        public OverlayStateChangeRequest(String[] paths, int category, int opsFlags, ISamsungOverlayCallback callback) {
            this(paths, category, opsFlags, UserHandle.myUserId(), callback);
        }

        public OverlayStateChangeRequest(String[] paths, int category, int opsFlags, int userId, ISamsungOverlayCallback callback) {
            this.paths = new String[0];
            this.pathsToRemove = new String[0];
            this.paths = paths;
            this.category = category;
            this.opsFlags = opsFlags;
            this.userId = userId;
            this.callback = callback;
        }

        public OverlayStateChangeRequest addFlag(int flag) {
            this.opsFlags |= flag;
            return this;
        }

        public OverlayStateChangeRequest addPathsToRemove(String[] paths) {
            this.pathsToRemove = paths;
            return this;
        }
    }
}
