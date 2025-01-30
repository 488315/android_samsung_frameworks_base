package com.google.android.setupcompat.logging.internal;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.actionresult.ActionResults;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FooterBarMixinMetrics {
    public static final String EXTRA_PRIMARY_BUTTON_VISIBILITY = "PrimaryButtonVisibility";
    public static final String EXTRA_SECONDARY_BUTTON_VISIBILITY = "SecondaryButtonVisibility";
    public String primaryButtonVisibility = "Unknown";
    public String secondaryButtonVisibility = "Unknown";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @Retention(RetentionPolicy.SOURCE)
    public @interface FooterButtonVisibility {
    }

    public static String updateButtonVisibilityState(String str, boolean z) {
        if ("VisibleUsingXml".equals(str) || ActionResults.RESULT_LAUNCHER_VISIBLE.equals(str) || ActionResults.RESULT_LAUNCHER_INVISIBLE.equals(str)) {
            return (z && ActionResults.RESULT_LAUNCHER_INVISIBLE.equals(str)) ? "Invisible_to_Visible" : !z ? "VisibleUsingXml".equals(str) ? "VisibleUsingXml_to_Invisible" : ActionResults.RESULT_LAUNCHER_VISIBLE.equals(str) ? "Visible_to_Invisible" : str : str;
        }
        throw new IllegalStateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Illegal visibility state: ", str));
    }
}
