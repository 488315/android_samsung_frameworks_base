package com.android.server.wm;

import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Binder;
import android.os.RemoteCallbackList;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import com.samsung.android.core.IFoldStarCallback;
import com.samsung.android.core.IFoldStarManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FoldStarManagerService extends IFoldStarManager.Stub {
    public static FoldStarManagerService sService;
    public final ActivityTaskManagerService mAtm;

    public FoldStarManagerService(ActivityTaskManagerService activityTaskManagerService) {
        new RemoteCallbackList();
        this.mAtm = activityTaskManagerService;
    }

    public static void getLauncherPackages(int i, List list) {
        LauncherApps launcherApps =
                (LauncherApps) SafetySystemService.getSystemService(LauncherApps.class);
        if (launcherApps == null) {
            return;
        }
        Iterator<LauncherActivityInfo> it =
                launcherApps.getActivityList(null, UserHandle.of(i)).iterator();
        while (it.hasNext()) {
            ((ArrayList) list).add(it.next().getApplicationInfo().packageName);
        }
    }

    public final Map getDisplayCompatPackages(int i, int i2, Map map) {
        return null;
    }

    public final Map getFixedAspectRatioPackages(final int i, int i2, Map map) {
        Function function;
        if (!CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY) {
            return null;
        }
        ActivityTaskManagerService.enforceTaskPermission("getFixedAspectRatioPackages()");
        final MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides =
                this.mAtm.mMultiTaskingAppCompatController.mAspectRatioOverrides;
        ArrayList arrayList = new ArrayList();
        if (i2 == 4) {
            if (map == null || map.isEmpty()) {
                getLauncherPackages(i, arrayList);
            } else {
                arrayList.addAll(map.keySet());
            }
            final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            arrayList.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i3;
                            MultiTaskingAppCompatAspectRatioOverrides
                                    multiTaskingAppCompatAspectRatioOverrides2 =
                                            MultiTaskingAppCompatAspectRatioOverrides.this;
                            int i4 = i;
                            Map map2 = concurrentHashMap;
                            String str = (String) obj;
                            multiTaskingAppCompatAspectRatioOverrides2.getClass();
                            if (CompatChangeableAppsCache.LazyHolder.sCache.query(
                                    new CompatChangeableAppsCache$$ExternalSyntheticLambda0(str, 2),
                                    i4)) {
                                i3 = 0;
                            } else {
                                float userOrSystemMinAspectRatio =
                                        multiTaskingAppCompatAspectRatioOverrides2
                                                .getUserOrSystemMinAspectRatio(i4, str);
                                i3 =
                                        userOrSystemMinAspectRatio == 1.7777778f
                                                ? 2
                                                : userOrSystemMinAspectRatio == 1.3333334f ? 3 : 1;
                            }
                            map2.put(str, Integer.valueOf(i3));
                        }
                    });
            return concurrentHashMap;
        }
        boolean z = true;
        if (i2 == 0) {
            final int i3 = 0;
            function =
                    new Function() { // from class:
                                     // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            switch (i3) {
                                case 0:
                                    return Float.valueOf(
                                            multiTaskingAppCompatAspectRatioOverrides
                                                    .getUserOrSystemMinAspectRatio(
                                                            i, (String) obj));
                                case 1:
                                    multiTaskingAppCompatAspectRatioOverrides.getClass();
                                    int userMinAspectRatioOverrideCode =
                                            MultiTaskingAppCompatAspectRatioOverrides
                                                    .getUserMinAspectRatioOverrideCode(
                                                            i, (String) obj);
                                    return Float.valueOf(
                                            userMinAspectRatioOverrideCode == 4
                                                    ? 1.7777778f
                                                    : userMinAspectRatioOverrideCode == 3
                                                            ? 1.3333334f
                                                            : -1.0f);
                                default:
                                    return Float.valueOf(
                                            multiTaskingAppCompatAspectRatioOverrides
                                                    .getUserOrSystemMinAspectRatio(
                                                            i, (String) obj));
                            }
                        }
                    };
            getLauncherPackages(i, arrayList);
        } else if (i2 == 1) {
            final int i4 = 1;
            function =
                    new Function() { // from class:
                                     // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            switch (i4) {
                                case 0:
                                    return Float.valueOf(
                                            multiTaskingAppCompatAspectRatioOverrides
                                                    .getUserOrSystemMinAspectRatio(
                                                            i, (String) obj));
                                case 1:
                                    multiTaskingAppCompatAspectRatioOverrides.getClass();
                                    int userMinAspectRatioOverrideCode =
                                            MultiTaskingAppCompatAspectRatioOverrides
                                                    .getUserMinAspectRatioOverrideCode(
                                                            i, (String) obj);
                                    return Float.valueOf(
                                            userMinAspectRatioOverrideCode == 4
                                                    ? 1.7777778f
                                                    : userMinAspectRatioOverrideCode == 3
                                                            ? 1.3333334f
                                                            : -1.0f);
                                default:
                                    return Float.valueOf(
                                            multiTaskingAppCompatAspectRatioOverrides
                                                    .getUserOrSystemMinAspectRatio(
                                                            i, (String) obj));
                            }
                        }
                    };
            getLauncherPackages(i, arrayList);
        } else if (i2 == 2) {
            function =
                    new Function() { // from class:
                                     // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(
                                    MultiTaskingAppCompatAspectRatioOverrides.this
                                            .getSystemMinAspectRatio((String) obj));
                        }
                    };
            getLauncherPackages(i, arrayList);
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException(
                        NandswapManager$$ExternalSyntheticOutline0.m(i2, " is an unknown option."));
            }
            if (map == null) {
                throw new IllegalArgumentException("requestedPackages is null");
            }
            final int i5 = 2;
            function =
                    new Function() { // from class:
                                     // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            switch (i5) {
                                case 0:
                                    return Float.valueOf(
                                            multiTaskingAppCompatAspectRatioOverrides
                                                    .getUserOrSystemMinAspectRatio(
                                                            i, (String) obj));
                                case 1:
                                    multiTaskingAppCompatAspectRatioOverrides.getClass();
                                    int userMinAspectRatioOverrideCode =
                                            MultiTaskingAppCompatAspectRatioOverrides
                                                    .getUserMinAspectRatioOverrideCode(
                                                            i, (String) obj);
                                    return Float.valueOf(
                                            userMinAspectRatioOverrideCode == 4
                                                    ? 1.7777778f
                                                    : userMinAspectRatioOverrideCode == 3
                                                            ? 1.3333334f
                                                            : -1.0f);
                                default:
                                    return Float.valueOf(
                                            multiTaskingAppCompatAspectRatioOverrides
                                                    .getUserOrSystemMinAspectRatio(
                                                            i, (String) obj));
                            }
                        }
                    };
            arrayList.addAll(map.keySet());
            z = false;
        }
        ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            multiTaskingAppCompatAspectRatioOverrides.getClass();
            Float f =
                    !CompatChangeableAppsCache.LazyHolder.sCache.query(
                                    new CompatChangeableAppsCache$$ExternalSyntheticLambda0(str, 2),
                                    i)
                            ? (Float) function.apply(str)
                            : null;
            if (f == null) {
                f = Float.valueOf(-1.0f);
            }
            if (!z || f.floatValue() != -1.0f) {
                concurrentHashMap2.put(str, f);
            }
        }
        return concurrentHashMap2;
    }

    public final void initAppContinuityValueWhenReset(boolean z, boolean z2) {}

    public final void registerFoldStarCallback(IFoldStarCallback iFoldStarCallback) {}

    public final void setAllAppContinuityMode(int i, boolean z) {}

    public final void setAppContinuityMode(String str, int i, boolean z) {}

    public final void setDisplayCompatPackages(int i, Map map, boolean z) {}

    public final void setFixedAspectRatioPackages(int i, Map map, boolean z) {
        if (!CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY || map == null || map.isEmpty()) {
            return;
        }
        ActivityTaskManagerService.enforceTaskPermission("setFixedAspectRatioPackages()");
        map.entrySet().removeIf(new FoldStarManagerService$$ExternalSyntheticLambda5());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides =
                    this.mAtm.mMultiTaskingAppCompatController.mAspectRatioOverrides;
            multiTaskingAppCompatAspectRatioOverrides.getClass();
            if (z) {
                Slog.w("MultiTaskingAppCompat", "ReplaceAll is not supported.");
            }
            map.forEach(
                    new MultiTaskingAppCompatAspectRatioOverrides$$ExternalSyntheticLambda0(
                            multiTaskingAppCompatAspectRatioOverrides, i));
            this.mAtm.mMultiTaskingAppCompatController.removeTaskWithoutRemoveFromRecents(
                    i, "setFixedAspectRatioPackages", true, map.keySet().stream().toList());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setFrontScreenOnWhenAppContinuityMode(boolean z) {}

    public final void unregisterFoldStarCallback(IFoldStarCallback iFoldStarCallback) {}
}
