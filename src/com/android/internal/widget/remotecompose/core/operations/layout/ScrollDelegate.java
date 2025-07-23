package com.android.internal.widget.remotecompose.core.operations.layout;

/* loaded from: classes6.dex */
public interface ScrollDelegate {
    float getScrollX(float f);

    float getScrollY(float f);

    boolean handlesHorizontalScroll();

    boolean handlesVerticalScroll();

    void reset();
}
