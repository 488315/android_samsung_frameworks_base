package com.android.wm.shell.common;

import android.content.Context;
import android.graphics.PointF;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformDragPositioningController {
    public static volatile FreeformDragPositioningController sFreeformDragPositioningController;
    public final FreeformDragListener mFreeformDragListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FreeformDragListener {
        public final DismissButtonManager mDismissButtonManager;
        public final PointF mTmpPoint = new PointF();

        public FreeformDragListener(Context context) {
            DismissButtonManager dismissButtonManager = new DismissButtonManager(context, 3);
            this.mDismissButtonManager = dismissButtonManager;
            dismissButtonManager.mTitle = "dismiss-button-freeform";
            dismissButtonManager.createDismissButtonView();
            dismissButtonManager.createOrUpdateWrapper();
        }
    }

    public FreeformDragPositioningController(Context context) {
        this.mFreeformDragListener = new FreeformDragListener(context);
    }

    public static FreeformDragPositioningController getInstance(Context context) {
        if (sFreeformDragPositioningController == null) {
            synchronized (FreeformDragPositioningController.class) {
                if (sFreeformDragPositioningController == null) {
                    sFreeformDragPositioningController = new FreeformDragPositioningController(context);
                }
            }
        }
        return sFreeformDragPositioningController;
    }
}
