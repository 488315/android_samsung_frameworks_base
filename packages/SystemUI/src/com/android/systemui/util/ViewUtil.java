package com.android.systemui.util;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.sec.ims.settings.ImsProfile;

public final class ViewUtil {
    public static final int $stable = 0;
    public static final ViewUtil INSTANCE = new ViewUtil();

    private ViewUtil() {
    }

    public static final SecQuickStatusBarHeader getSecQuickStatusBarHeader(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (viewGroup.getChildAt(i) instanceof SecQuickStatusBarHeader) {
                return (SecQuickStatusBarHeader) viewGroup.getChildAt(i);
            }
        }
        return null;
    }

    private final String toResIdString(View view) {
        try {
            if (view.getId() == -1) {
                return "";
            }
            int id = view.getId() & (-16777216);
            String resourcePackageName = id != 16777216 ? id != 2130706432 ? view.getResources().getResourcePackageName(view.getId()) : SystemUIAnalytics.QPNE_KEY_APP : "android";
            return ", #" + Integer.toHexString(view.getId()) + ", " + resourcePackageName + ":" + view.getResources().getResourceTypeName(view.getId()) + "/" + view.getResources().getResourceEntryName(view.getId());
        } catch (Resources.NotFoundException unused) {
            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(", ResNotFound : ", Integer.toHexString(view.getId()));
        }
    }

    private final String toVisibilityString(View view) {
        int visibility = view.getVisibility();
        return visibility != 0 ? visibility != 4 ? visibility != 8 ? "U" : ImsProfile.TIMER_NAME_G : ImsProfile.TIMER_NAME_I : "V";
    }

    public final String toIdSting(View view) {
        return toShortIdSting(view) + toResIdString(view) + " " + toVisibilityString(view);
    }

    public final String toShortIdSting(Object obj) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("@", Integer.toHexString(System.identityHashCode(obj)));
    }
}
