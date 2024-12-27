package com.android.systemui.touch;

import android.view.View;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class TouchInsetManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ TouchInsetManager.TouchInsetSession f$1;

    public /* synthetic */ TouchInsetManager$$ExternalSyntheticLambda0(TouchInsetManager.TouchInsetSession touchInsetSession, View view, int i) {
        this.$r8$classId = i;
        this.f$1 = touchInsetSession;
        this.f$0 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TouchInsetManager touchInsetManager = (TouchInsetManager) this.f$0;
                TouchInsetManager.TouchInsetSession touchInsetSession = this.f$1;
                touchInsetManager.recycleRegions(touchInsetSession);
                touchInsetManager.mSessionRegions.remove(touchInsetSession);
                touchInsetManager.updateTouchInsets();
                break;
            case 1:
                TouchInsetManager.TouchInsetSession touchInsetSession2 = this.f$1;
                View view = (View) this.f$0;
                touchInsetSession2.mTrackedViews.add(view);
                view.addOnAttachStateChangeListener(touchInsetSession2.mAttachListener);
                view.addOnLayoutChangeListener(touchInsetSession2.mOnLayoutChangeListener);
                touchInsetSession2.updateTouchRegions();
                break;
            default:
                TouchInsetManager.TouchInsetSession touchInsetSession3 = this.f$1;
                View view2 = (View) this.f$0;
                touchInsetSession3.mTrackedViews.remove(view2);
                view2.removeOnLayoutChangeListener(touchInsetSession3.mOnLayoutChangeListener);
                view2.removeOnAttachStateChangeListener(touchInsetSession3.mAttachListener);
                touchInsetSession3.updateTouchRegions();
                break;
        }
    }

    public /* synthetic */ TouchInsetManager$$ExternalSyntheticLambda0(TouchInsetManager touchInsetManager, TouchInsetManager.TouchInsetSession touchInsetSession) {
        this.$r8$classId = 0;
        this.f$0 = touchInsetManager;
        this.f$1 = touchInsetSession;
    }
}
