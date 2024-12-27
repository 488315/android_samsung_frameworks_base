package com.android.systemui.statusbar;

import android.util.Pair;
import android.view.DisplayCutout;
import android.view.WindowInsets;

public abstract class NotificationInsetsController {
    public abstract Pair getinsets(WindowInsets windowInsets, DisplayCutout displayCutout);
}
