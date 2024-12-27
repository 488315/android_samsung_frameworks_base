package android.content.om;

import java.util.ArrayList;
import java.util.Iterator;

public class SamsungThemeUtils {
    private static final String TAG = "SamsungThemeUtils";

    public static String[] removeSamsungThemeOverlays(String[] overlayPaths) {
        if (overlayPaths != null && overlayPaths.length > 0) {
            ArrayList<String> filteredOverlayPaths = new ArrayList<>();
            for (String overlay : overlayPaths) {
                if (overlay != null
                        && !overlay.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE)) {
                    filteredOverlayPaths.add(overlay);
                }
            }
            return (String[]) filteredOverlayPaths.toArray(new String[0]);
        }
        return null;
    }

    public static String[] removeSamsungThemeOverlaysForCover(String[] overlayPaths) {
        if (overlayPaths != null && overlayPaths.length > 0) {
            ArrayList<String> filteredOverlayPaths = new ArrayList<>();
            for (String overlay : overlayPaths) {
                if (overlay != null) {
                    boolean hasPrefix =
                            overlay.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE);
                    if (hasPrefix && hasAllowPostfixforCover(overlay)) {
                        return overlayPaths;
                    }
                    if (hasPrefix && hasAllowSystemUIforCover(overlay)) {
                        return removeOnlySystemUIOverlay(overlayPaths);
                    }
                    if (!hasPrefix) {
                        filteredOverlayPaths.add(overlay);
                    }
                }
            }
            return (String[]) filteredOverlayPaths.toArray(new String[0]);
        }
        return null;
    }

    private static boolean hasAllowPostfixforCover(String overlay) {
        Iterator<String> it = SamsungThemeConstants.allowPostfixForCover.iterator();
        while (it.hasNext()) {
            String postfix = it.next();
            if (overlay != null && overlay.endsWith(postfix)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasAllowSystemUIforCover(String overlay) {
        Iterator<String> it = SamsungThemeConstants.allowSystemUIForCover.iterator();
        while (it.hasNext()) {
            String postfix = it.next();
            if (overlay != null && overlay.endsWith(postfix)) {
                return true;
            }
        }
        return false;
    }

    private static String[] removeOnlySystemUIOverlay(String[] overlayPaths) {
        ArrayList<String> filteredOverlayPaths = new ArrayList<>();
        for (String overlay : overlayPaths) {
            if (overlay != null
                    && (!overlay.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE)
                            || !overlay.endsWith(
                                    SamsungThemeConstants.THEME_OVERLAY_SYSTEMUI_POSTFIX))) {
                filteredOverlayPaths.add(overlay);
            }
        }
        return (String[]) filteredOverlayPaths.toArray(new String[0]);
    }
}
