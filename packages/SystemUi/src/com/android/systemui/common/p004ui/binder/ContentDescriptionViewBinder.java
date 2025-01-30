package com.android.systemui.common.p004ui.binder;

import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
