package com.android.systemui.common.ui.binder;

import android.content.res.Resources;
import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public final class ContentDescriptionViewBinder {
    public static final ContentDescriptionViewBinder INSTANCE = new ContentDescriptionViewBinder();

    private ContentDescriptionViewBinder() {
    }

    public static void bind(ContentDescription contentDescription, View view) throws Resources.NotFoundException {
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
