package com.android.systemui.common.ui.binder;

import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContentDescriptionViewBinder {
    public static final ContentDescriptionViewBinder INSTANCE = new ContentDescriptionViewBinder();

    private ContentDescriptionViewBinder() {
    }

    public static void bind(ContentDescription contentDescription, View view) {
        String string;
        if (contentDescription == null) {
            string = null;
        } else if (contentDescription instanceof ContentDescription.Loaded) {
            string = ((ContentDescription.Loaded) contentDescription).description;
        } else {
            if (!(contentDescription instanceof ContentDescription.Resource)) {
                throw new NoWhenBranchMatchedException();
            }
            string = view.getContext().getResources().getString(((ContentDescription.Resource) contentDescription).res);
        }
        view.setContentDescription(string);
    }
}
