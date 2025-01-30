package com.android.systemui.common.ui.binder;

import android.widget.ImageView;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IconViewBinder {
    public static final IconViewBinder INSTANCE = new IconViewBinder();

    private IconViewBinder() {
    }

    public static void bind(Icon icon, ImageView imageView) {
        ContentDescriptionViewBinder contentDescriptionViewBinder = ContentDescriptionViewBinder.INSTANCE;
        ContentDescription contentDescription = icon.getContentDescription();
        contentDescriptionViewBinder.getClass();
        ContentDescriptionViewBinder.bind(contentDescription, imageView);
        if (icon instanceof Icon.Loaded) {
            imageView.setImageDrawable(((Icon.Loaded) icon).drawable);
        } else if (icon instanceof Icon.Resource) {
            imageView.setImageResource(((Icon.Resource) icon).res);
        }
    }
}
