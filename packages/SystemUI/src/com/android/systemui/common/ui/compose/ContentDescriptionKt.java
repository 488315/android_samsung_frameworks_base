package com.android.systemui.common.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public abstract class ContentDescriptionKt {
    public static final String load(ContentDescription contentDescription, Composer composer) {
        String strStringResource;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.load (ContentDescription.kt:26)");
        }
        if (contentDescription instanceof ContentDescription.Loaded) {
            strStringResource = ((ContentDescription.Loaded) contentDescription).description;
        } else {
            if (!(contentDescription instanceof ContentDescription.Resource)) {
                throw new NoWhenBranchMatchedException();
            }
            strStringResource = StringResources_androidKt.stringResource(((ContentDescription.Resource) contentDescription).res, composer);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return strStringResource;
    }
}
