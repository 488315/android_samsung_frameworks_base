package com.android.systemui.audio.soundcraft.utils;

import android.view.View;
import android.view.ViewGroup;

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
