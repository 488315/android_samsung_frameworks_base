package com.android.wm.shell.common;

import android.content.Context;
import android.graphics.PointF;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FreeformDragPositioningController {
    public static volatile FreeformDragPositioningController sFreeformDragPositioningController;
    public final FreeformDragListener mFreeformDragListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
