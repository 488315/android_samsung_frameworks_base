package com.android.systemui.common.ui.binder;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import com.android.settingslib.Utils;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.TintedIcon;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TintedIconViewBinder {
    public static final TintedIconViewBinder INSTANCE = new TintedIconViewBinder();

    private TintedIconViewBinder() {
    }

    public static void bind(TintedIcon tintedIcon, ImageView imageView) {
        ColorStateList colorStateList;
        IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
        Icon icon = tintedIcon.icon;
        iconViewBinder.getClass();
        IconViewBinder.bind(icon, imageView);
        Integer num = tintedIcon.tint;
        if (num != null) {
            colorStateList = Utils.getColorAttr(num.intValue(), imageView.getContext());
        } else {
            colorStateList = null;
        }
        imageView.setImageTintList(colorStateList);
    }
}
