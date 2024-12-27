package com.android.systemui.common.ui.binder;

import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
