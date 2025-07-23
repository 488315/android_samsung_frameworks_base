package com.android.systemui.common.ui.binder;

import android.widget.ImageView;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        } else {
            if (!(icon instanceof Icon.Resource)) {
                throw new NoWhenBranchMatchedException();
            }
            imageView.setImageResource(((Icon.Resource) icon).res);
        }
    }
}
