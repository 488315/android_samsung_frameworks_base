package com.android.compose.animation.scene;

import androidx.compose.ui.unit.LayoutDirection;

/* loaded from: classes.dex */
public interface SwipeSource {

    public interface Resolved {
        int hashCode();
    }

    int hashCode();

    Resolved resolve(LayoutDirection layoutDirection);
}
