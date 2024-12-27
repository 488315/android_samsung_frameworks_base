package com.android.server.om;

import android.content.pm.overlay.OverlayPaths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class OverlayPolicyManager {
    public static final List policies = new ArrayList();
    public static final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OverlayPackagePolicy {
        boolean retainOverlay(String str, OverlayPaths overlayPaths, String str2);
    }

    public static OverlayPaths filterByPolicy(OverlayPaths overlayPaths, OverlayPaths overlayPaths2, String str, int i) {
        if (overlayPaths == null) {
            return new OverlayPaths.Builder().build();
        }
        OverlayPaths.Builder builder = new OverlayPaths.Builder();
        Iterator it = overlayPaths.getOverlayPaths().iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            Iterator it2 = ((ArrayList) policies).iterator();
            while (it2.hasNext()) {
                z &= ((OverlayPackagePolicy) it2.next()).retainOverlay(str2, overlayPaths2, str);
            }
            if (z) {
                builder.addNonApkPath(str2);
            }
        }
        for (String str3 : overlayPaths.getResourceDirs()) {
            Iterator it3 = ((ArrayList) policies).iterator();
            boolean z2 = true;
            while (it3.hasNext()) {
                z2 &= ((OverlayPackagePolicy) it3.next()).retainOverlay(str3, overlayPaths2, str);
            }
            if (z2) {
                builder.addApkPath(str3);
            }
        }
        return builder.build();
    }
}
