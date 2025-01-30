package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.om.OverlayManagerExt;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.om.OverlayManagerService;
import com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda1;
import com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda7;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class OMSHelper {
    public static final String TAG = "OMSHelper";
    public final OverlayManagerService mService = (OverlayManagerService) LocalServices.getService(OverlayManagerService.class);

    public void applySamsungConfigChangeOverlays(List list, List list2, int i, ISamsungOverlayCallback iSamsungOverlayCallback) {
        String str = TAG;
        Log.e(str, "Applying change disabled - " + list);
        Log.e(str, "Applying change enabled - " + list2);
        OverlayManagerExt overlayManagerExt = new OverlayManagerExt();
        String[] strArr = (String[]) ((List) ((List) Optional.ofNullable(list).orElseGet(new OverlayManagerServiceExt$$ExternalSyntheticLambda7())).stream().filter(new OverlayManagerServiceExt$$ExternalSyntheticLambda1()).map(new Function() { // from class: com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String lambda$applySamsungConfigChangeOverlays$0;
                lambda$applySamsungConfigChangeOverlays$0 = OMSHelper.lambda$applySamsungConfigChangeOverlays$0((String) obj);
                return lambda$applySamsungConfigChangeOverlays$0;
            }
        }).collect(Collectors.toList())).toArray(new String[0]);
        OverlayManagerExt.OverlayStateChangeRequest overlayStateChangeRequest = new OverlayManagerExt.OverlayStateChangeRequest((String[]) ((List) ((List) Optional.ofNullable(list2).orElseGet(new OverlayManagerServiceExt$$ExternalSyntheticLambda7())).stream().filter(new OverlayManagerServiceExt$$ExternalSyntheticLambda1()).map(new Function() { // from class: com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String lambda$applySamsungConfigChangeOverlays$1;
                lambda$applySamsungConfigChangeOverlays$1 = OMSHelper.lambda$applySamsungConfigChangeOverlays$1((String) obj);
                return lambda$applySamsungConfigChangeOverlays$1;
            }
        }).collect(Collectors.toList())).toArray(new String[0]), 1, FrameworkStatsLog.f644xd8079e8d, i, iSamsungOverlayCallback);
        overlayStateChangeRequest.addPathsToRemove(strArr);
        try {
            overlayManagerExt.replaceOverlays(overlayStateChangeRequest);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static /* synthetic */ String lambda$applySamsungConfigChangeOverlays$0(String str) {
        return "/data/overlays/current_locale_apks/files/" + str + ".apk";
    }

    public static /* synthetic */ String lambda$applySamsungConfigChangeOverlays$1(String str) {
        return "/data/overlays/current_locale_apks/files/" + str + ".apk";
    }

    public void updatePackageCache(String str, int i) {
        this.mService.updatePackageCache(str, i);
    }

    public List getLocaleOverlaysForUser(int i) {
        Map allOverlays = this.mService.getAllOverlays(i);
        final ArrayList arrayList = new ArrayList();
        if (allOverlays.isEmpty()) {
            return arrayList;
        }
        Iterator it = allOverlays.entrySet().iterator();
        while (it.hasNext()) {
            ((List) ((Map.Entry) it.next()).getValue()).forEach(new Consumer() { // from class: com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OMSHelper.lambda$getLocaleOverlaysForUser$2(arrayList, (OverlayInfo) obj);
                }
            });
        }
        return arrayList;
    }

    public static /* synthetic */ void lambda$getLocaleOverlaysForUser$2(List list, OverlayInfo overlayInfo) {
        if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, 1)) {
            list.add(overlayInfo);
        }
    }

    public Map getLocaleOverlaysMap(int i) {
        Map allOverlays = this.mService.getAllOverlays(i);
        if (allOverlays.isEmpty()) {
            return allOverlays;
        }
        Iterator it = allOverlays.entrySet().iterator();
        while (it.hasNext()) {
            ((List) ((Map.Entry) it.next()).getValue()).removeIf(new Predicate() { // from class: com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getLocaleOverlaysMap$3;
                    lambda$getLocaleOverlaysMap$3 = OMSHelper.lambda$getLocaleOverlaysMap$3((OverlayInfo) obj);
                    return lambda$getLocaleOverlaysMap$3;
                }
            });
        }
        return allOverlays;
    }

    public static /* synthetic */ boolean lambda$getLocaleOverlaysMap$3(OverlayInfo overlayInfo) {
        String str = overlayInfo.category;
        return str == null || !str.startsWith("zipped-overlay");
    }

    public String getTargetPath(String str) {
        return this.mService.getTargetPath(str);
    }

    public List getUnReqLocaleOverlaysForUser(int i, final Set set) {
        Map allOverlays = this.mService.getAllOverlays(i);
        final ArrayList arrayList = new ArrayList();
        if (allOverlays.isEmpty()) {
            return arrayList;
        }
        Iterator it = allOverlays.entrySet().iterator();
        while (it.hasNext()) {
            ((List) ((Map.Entry) it.next()).getValue()).forEach(new Consumer() { // from class: com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OMSHelper.lambda$getUnReqLocaleOverlaysForUser$4(set, arrayList, (OverlayInfo) obj);
                }
            });
        }
        return arrayList;
    }

    public static /* synthetic */ void lambda$getUnReqLocaleOverlaysForUser$4(Set set, List list, OverlayInfo overlayInfo) {
        if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, 1) && overlayInfo.isEnabled()) {
            String str = overlayInfo.packageName;
            if (set.contains(str.substring(str.lastIndexOf(46) + 1))) {
                return;
            }
            list.add(overlayInfo.packageName);
        }
    }

    public List getDisabledOverlaysPackages(Set set, Context context) {
        ApplicationInfo applicationInfo;
        Log.i(TAG, "getDisabledOverlaysPackages, localeOverlayTargetApks: " + set);
        List<OverlayInfo> localeOverlaysForUser = getLocaleOverlaysForUser(0);
        ArrayList arrayList = new ArrayList();
        if (localeOverlaysForUser != null && localeOverlaysForUser.size() > 0) {
            for (OverlayInfo overlayInfo : localeOverlaysForUser) {
                if (overlayInfo != null && !set.contains(overlayInfo.targetPackageName)) {
                    String str = TAG;
                    Log.i(str, "package name: " + overlayInfo.targetPackageName + ", overPackage: " + overlayInfo.packageName);
                    try {
                        applicationInfo = context.getPackageManager().getApplicationInfo(overlayInfo.targetPackageName, 41472);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                    if (applicationInfo != null) {
                        Log.i(str, "app hidden: skip disable " + applicationInfo);
                    } else {
                        arrayList.add(overlayInfo.packageName);
                    }
                }
            }
        }
        return arrayList;
    }

    public List getLocaleOverlayInfosForTarget(String str) {
        ArrayList arrayList = new ArrayList();
        List<OverlayInfo> overlayInfosForTarget = this.mService.getOverlayInfosForTarget(str, 0);
        String str2 = TAG;
        Log.i(str2, "getLocaleOverlayInfosForTarget " + overlayInfosForTarget);
        if (overlayInfosForTarget != null && overlayInfosForTarget.size() > 0) {
            Log.i(str2, "Overlays List size : " + overlayInfosForTarget.size());
            for (OverlayInfo overlayInfo : overlayInfosForTarget) {
                if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, 1)) {
                    arrayList.add(overlayInfo);
                }
            }
        }
        return arrayList;
    }
}
