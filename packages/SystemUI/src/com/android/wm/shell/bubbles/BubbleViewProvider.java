package com.android.wm.shell.bubbles;

import android.graphics.Bitmap;
import android.graphics.Path;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;

/* loaded from: classes3.dex */
public interface BubbleViewProvider {
    Bitmap getAppBadge();

    BubbleBarExpandedView getBubbleBarExpandedView();

    Bitmap getBubbleIcon();

    int getDotColor();

    Path getDotPath();

    BubbleExpandedView getExpandedView();

    BadgedImageView getIconView$1();

    String getKey();

    int getTaskId();

    void setTaskViewVisibility();

    boolean showDot();
}
