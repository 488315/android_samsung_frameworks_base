package com.android.wm.shell.common;

import android.content.Context;
import android.graphics.PointF;

/* loaded from: classes3.dex */
public class FreeformDragPositioningController {
    public static volatile FreeformDragPositioningController sFreeformDragPositioningController;
    public final FreeformDragListener mFreeformDragListener;

    public final class FreeformDragListener {
        public final DismissViewManager mDismissViewManager;
        public final PointF mTmpPoint = new PointF();

        public FreeformDragListener(Context context) {
            DismissViewManager dismissViewManager = new DismissViewManager(context, 3);
            this.mDismissViewManager = dismissViewManager;
            dismissViewManager.mTitle = "dismiss-button-freeform";
            dismissViewManager.createDismissView();
            dismissViewManager.createOrUpdateWrapper();
        }
    }

    public FreeformDragPositioningController(Context context) {
        this.mFreeformDragListener = new FreeformDragListener(context);
    }

    public static FreeformDragPositioningController getInstance(Context context) {
        if (sFreeformDragPositioningController == null) {
            synchronized (FreeformDragPositioningController.class) {
                try {
                    if (sFreeformDragPositioningController == null) {
                        sFreeformDragPositioningController = new FreeformDragPositioningController(context);
                    }
                } finally {
                }
            }
        }
        return sFreeformDragPositioningController;
    }
}
