package com.android.systemui.audio.soundcraft.utils;

import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewUtils {
    public static final ViewUtils INSTANCE = new ViewUtils();

    private ViewUtils() {
    }

    public static void addViewIfNotAttached(View view, ViewGroup viewGroup) {
        if (viewGroup.indexOfChild(view) == -1 && view.getParent() == null) {
            viewGroup.addView(view);
        }
    }
}
