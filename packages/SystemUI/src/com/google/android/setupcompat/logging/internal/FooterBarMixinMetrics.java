package com.google.android.setupcompat.logging.internal;

import com.android.systemui.bixby2.actionresult.ActionResults;
import com.google.android.setupcompat.util.Logger;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class FooterBarMixinMetrics {
    public static final String EXTRA_PRIMARY_BUTTON_VISIBILITY = "PrimaryButtonVisibility";
    public static final String EXTRA_SECONDARY_BUTTON_VISIBILITY = "SecondaryButtonVisibility";
    public static final Logger LOG = new Logger("FooterBarMixinMetrics");
    public String primaryButtonVisibility = C2paManifestList.UNKNOWN_VALUE;
    public String secondaryButtonVisibility = C2paManifestList.UNKNOWN_VALUE;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FooterButtonVisibility {
    }

    public static String updateButtonVisibilityState(String str, boolean z) {
        if (!"VisibleUsingXml".equals(str) && !ActionResults.RESULT_LAUNCHER_VISIBLE.equals(str) && !ActionResults.RESULT_LAUNCHER_INVISIBLE.equals(str)) {
            LOG.w("Illegal visibility state: " + str);
        }
        return (z && ActionResults.RESULT_LAUNCHER_INVISIBLE.equals(str)) ? "Invisible_to_Visible" : !z ? "VisibleUsingXml".equals(str) ? "VisibleUsingXml_to_Invisible" : ActionResults.RESULT_LAUNCHER_VISIBLE.equals(str) ? "Visible_to_Invisible" : str : str;
    }
}
