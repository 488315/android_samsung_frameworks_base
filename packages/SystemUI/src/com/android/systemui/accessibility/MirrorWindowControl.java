package com.android.systemui.accessibility;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class MirrorWindowControl {
    public static final boolean DBG = Log.isLoggable("MirrorWindowControl", 3);
    public final Context mContext;
    public View mControlsView;
    public final WindowManager mWindowManager;
    public final Rect mDraggableBound = new Rect();
    public final Point mTmpPoint = new Point();
    public final Point mControlPosition = new Point();

    public MirrorWindowControl(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }

    public abstract String getWindowTitle();

    public abstract View onCreateView();
}
