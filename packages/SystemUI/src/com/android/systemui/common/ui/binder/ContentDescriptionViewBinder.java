package com.android.systemui.common.ui.binder;

import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

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
