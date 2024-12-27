package com.android.systemui.temporarydisplay;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.systemui.util.ViewController;
import kotlin.jvm.functions.Function2;

public final class TouchableRegionViewController extends ViewController {
    public final TouchableRegionViewController$internalInsetsListener$1 internalInsetsListener;
    public final Rect tempRect;

    public TouchableRegionViewController(View view, final Function2 function2) {
        super(view);
        this.tempRect = new Rect();
        this.internalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.temporarydisplay.TouchableRegionViewController$internalInsetsListener$1
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                View view2;
                internalInsetsInfo.setTouchableInsets(3);
                TouchableRegionViewController.this.tempRect.setEmpty();
                Function2 function22 = function2;
                view2 = ((ViewController) TouchableRegionViewController.this).mView;
                function22.invoke(view2, TouchableRegionViewController.this.tempRect);
                internalInsetsInfo.touchableRegion.set(TouchableRegionViewController.this.tempRect);
            }
        };
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.internalInsetsListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.internalInsetsListener);
    }
}
