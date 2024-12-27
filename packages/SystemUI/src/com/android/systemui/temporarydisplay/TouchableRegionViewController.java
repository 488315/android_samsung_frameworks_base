package com.android.systemui.temporarydisplay;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.systemui.util.ViewController;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TouchableRegionViewController extends ViewController {
    public final TouchableRegionViewController$internalInsetsListener$1 internalInsetsListener;
    public final Rect tempRect;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.temporarydisplay.TouchableRegionViewController$internalInsetsListener$1] */
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
