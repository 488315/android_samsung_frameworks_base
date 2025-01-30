package androidx.leanback.widget;

import android.graphics.Outline;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewOutlineProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RoundedRectHelperApi21 {
    public static SparseArray sRoundedRectProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RoundedRectOutlineProvider extends ViewOutlineProvider {
        public final int mRadius;

        public RoundedRectOutlineProvider(int i) {
            this.mRadius = i;
        }

        @Override // android.view.ViewOutlineProvider
        public final void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.mRadius);
            outline.setAlpha(1.0f);
        }
    }

    private RoundedRectHelperApi21() {
    }
}
