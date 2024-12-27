package com.android.systemui.audio.soundcraft.utils;

import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
