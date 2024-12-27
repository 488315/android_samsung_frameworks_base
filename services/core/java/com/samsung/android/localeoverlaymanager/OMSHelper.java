package com.samsung.android.localeoverlaymanager;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.om.OverlayManagerExt;
import android.util.Log;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.om.OverlayManagerService;
import com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda2;
import com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class OMSHelper {
    public final OverlayManagerService mService =
            (OverlayManagerService) LocalServices.getService(OverlayManagerService.class);

    public static void applySamsungConfigChangeOverlays(
            List list, List list2, int i, LocaleOverlayManager.ApplyObserver applyObserver) {
        Log.e("OMSHelper", "Applying change disabled - " + list);
        Log.e("OMSHelper", "Applying change enabled - " + list2);
        OverlayManagerExt overlayManagerExt = new OverlayManagerExt();
        final int i2 = 0;
        String[] strArr =
                (String[])
                        ((List)
                                        ((List)
                                                        Optional.ofNullable(list)
                                                                .orElseGet(
                                                                        new OverlayManagerServiceExt$$ExternalSyntheticLambda6()))
                                                .stream()
                                                        .filter(
                                                                new OverlayManagerServiceExt$$ExternalSyntheticLambda2(
                                                                        1))
                                                        .map(
                                                                new Function() { // from class:
                                                                    // com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda0
                                                                    @Override // java.util.function.Function
                                                                    public final Object apply(
                                                                            Object obj) {
                                                                        String str = (String) obj;
                                                                        switch (i2) {
                                                                        }
                                                                        return XmlUtils$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        "/data/overlays/current_locale_apks/files/",
                                                                                        str,
                                                                                        ".apk");
                                                                    }
                                                                })
                                                        .collect(Collectors.toList()))
                                .toArray(new String[0]);
        final int i3 = 1;
        OverlayManagerExt.OverlayStateChangeRequest overlayStateChangeRequest =
                new OverlayManagerExt.OverlayStateChangeRequest(
                        (String[])
                                ((List)
                                                ((List)
                                                                Optional.ofNullable(list2)
                                                                        .orElseGet(
                                                                                new OverlayManagerServiceExt$$ExternalSyntheticLambda6()))
                                                        .stream()
                                                                .filter(
                                                                        new OverlayManagerServiceExt$$ExternalSyntheticLambda2(
                                                                                1))
                                                                .map(
                                                                        new Function() { // from
                                                                            // class:
                                                                            // com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda0
                                                                            @Override // java.util.function.Function
                                                                            public final Object
                                                                                    apply(
                                                                                            Object
                                                                                                    obj) {
                                                                                String str =
                                                                                        (String)
                                                                                                obj;
                                                                                switch (i3) {
                                                                                }
                                                                                return XmlUtils$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                "/data/overlays/current_locale_apks/files/",
                                                                                                str,
                                                                                                ".apk");
                                                                            }
                                                                        })
                                                                .collect(Collectors.toList()))
                                        .toArray(new String[0]),
                        1,
                        FrameworkStatsLog
                                .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED,
                        i,
                        applyObserver);
        overlayStateChangeRequest.addPathsToRemove(strArr);
        try {
            overlayManagerExt.replaceOverlays(overlayStateChangeRequest);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public final List getLocaleOverlayInfosForTarget(String str) {
        ArrayList arrayList = new ArrayList();
        List<OverlayInfo> overlayInfosForTarget = this.mService.getOverlayInfosForTarget(str, 0);
        Log.i("OMSHelper", "getLocaleOverlayInfosForTarget " + overlayInfosForTarget);
        if (overlayInfosForTarget != null && overlayInfosForTarget.size() > 0) {
            Log.i("OMSHelper", "Overlays List size : " + overlayInfosForTarget.size());
            for (OverlayInfo overlayInfo : overlayInfosForTarget) {
                if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, 1)) {
                    arrayList.add(overlayInfo);
                }
            }
        }
        return arrayList;
    }

    public final List getLocaleOverlaysForUser(int i) {
        Map allOverlays = this.mService.getAllOverlays(i);
        final ArrayList arrayList = new ArrayList();
        if (allOverlays.isEmpty()) {
            return arrayList;
        }
        Iterator it = allOverlays.entrySet().iterator();
        while (it.hasNext()) {
            ((List) ((Map.Entry) it.next()).getValue())
                    .forEach(
                            new Consumer() { // from class:
                                             // com.samsung.android.localeoverlaymanager.OMSHelper$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    List list = arrayList;
                                    OverlayInfo overlayInfo = (OverlayInfo) obj;
                                    if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, 1)) {
                                        list.add(overlayInfo);
                                    }
                                }
                            });
        }
        return arrayList;
    }
}
