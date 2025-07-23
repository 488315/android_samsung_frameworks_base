package com.android.systemui.common.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContentDescriptionKt {
    public static final String load(ContentDescription contentDescription, Composer composer) {
        String stringResource;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.load (ContentDescription.kt:26)");
        }
        if (contentDescription instanceof ContentDescription.Loaded) {
            stringResource = ((ContentDescription.Loaded) contentDescription).description;
        } else {
            if (!(contentDescription instanceof ContentDescription.Resource)) {
                throw new NoWhenBranchMatchedException();
            }
            stringResource = StringResources_androidKt.stringResource(((ContentDescription.Resource) contentDescription).res, composer);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return stringResource;
    }
}
