package com.android.wm.shell.common.split;

import android.util.Log;
import com.android.wm.shell.common.split.DividerResizeLayout;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DividerResizeLayout$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) obj;
        boolean z = DividerResizeLayout.DEBUG;
        if (dividerResizeTarget.mBlurAnimator != null) {
            if (DividerResizeLayout.DEBUG) {
                Log.d("DividerResizeLayout", "clearAllAnimators: " + dividerResizeTarget.mBlurAnimator);
            }
            dividerResizeTarget.mBlurAnimator.end();
            dividerResizeTarget.mBlurAnimator = null;
        }
        if (dividerResizeTarget.mBoundsAnimator != null) {
            if (DividerResizeLayout.DEBUG) {
                Log.d("DividerResizeLayout", "clearAllAnimators: " + dividerResizeTarget.mBoundsAnimator);
            }
            dividerResizeTarget.mBoundsAnimator.end();
            dividerResizeTarget.mBoundsAnimator = null;
        }
        if (dividerResizeTarget.mOutlineInsetsAnimator != null) {
            if (DividerResizeLayout.DEBUG) {
                Log.d("DividerResizeLayout", "clearAllAnimators: " + dividerResizeTarget.mOutlineInsetsAnimator);
            }
            dividerResizeTarget.mOutlineInsetsAnimator.end();
            dividerResizeTarget.mOutlineInsetsAnimator = null;
        }
    }
}
