package com.android.wm.shell.bubbles;

import android.graphics.Bitmap;
import android.graphics.Path;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
