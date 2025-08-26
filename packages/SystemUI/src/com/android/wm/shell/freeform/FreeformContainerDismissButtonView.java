package com.android.wm.shell.freeform;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.android.wm.shell.common.DismissViewManager;

/* loaded from: classes3.dex */
public class FreeformContainerDismissButtonView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mDismissButtonShowing;
    public final DismissViewManager mDismissViewManager;
    public View mDismissingIconView;

    public FreeformContainerDismissButtonView(Context context) {
        super(context);
        this.mDismissButtonShowing = false;
        DismissViewManager dismissViewManager = new DismissViewManager(context, 3);
        this.mDismissViewManager = dismissViewManager;
        dismissViewManager.createDismissView();
        setVisibility(8);
    }

    public final void show(Rect rect) {
        Log.i("FreeformContainer", "[FreeformContainerDismissButtonView] show()");
        setVisibility(0);
        if (!this.mDismissButtonShowing) {
            this.mDismissButtonShowing = true;
        }
        this.mDismissViewManager.mView.updateView(rect);
        Rect rect2 = new Rect();
        FreeformContainerManager.getInstance(((FrameLayout) this).mContext).getClass();
        FreeformContainerManager.getOverrideStableInsets(rect2);
        DismissViewManager dismissViewManager = this.mDismissViewManager;
        Insets.of(rect2);
        dismissViewManager.show();
    }
}
