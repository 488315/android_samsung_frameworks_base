package com.android.systemui.statusbar;

import android.util.Pair;
import android.view.DisplayCutout;
import android.view.WindowInsets;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NotificationInsetsController {
    public abstract Pair getinsets(WindowInsets windowInsets, DisplayCutout displayCutout);
}
