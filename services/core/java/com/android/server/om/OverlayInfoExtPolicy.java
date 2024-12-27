package com.android.server.om;

import android.content.om.SamsungThemeConstants;
import android.content.pm.overlay.OverlayPaths;

import java.util.function.Predicate;

public final class OverlayInfoExtPolicy implements OverlayPolicyManager.OverlayPackagePolicy {
    @Override // com.android.server.om.OverlayPolicyManager.OverlayPackagePolicy
    public final boolean retainOverlay(String str, OverlayPaths overlayPaths, String str2) {
        if (!str.startsWith("/data/overlays")
                || "android".equals(str2)
                || overlayPaths.getOverlayPaths().contains(str)
                || overlayPaths.getResourceDirs().contains(str)
                || SamsungThemeConstants.changeableApps.contains(str2)) {
            return true;
        }
        final int i = 0;
        boolean anyMatch =
                overlayPaths.getOverlayPaths().stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.android.server.om.OverlayInfoExtPolicy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        String str3 = (String) obj;
                                        switch (i) {
                                        }
                                        return str3.startsWith("/data/overlays/currentstyle");
                                    }
                                });
        final int i2 = 1;
        return anyMatch
                | overlayPaths.getResourceDirs().stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.android.server.om.OverlayInfoExtPolicy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        String str3 = (String) obj;
                                        switch (i2) {
                                        }
                                        return str3.startsWith("/data/overlays/currentstyle");
                                    }
                                });
    }
}
