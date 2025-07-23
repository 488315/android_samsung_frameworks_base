package androidx.leanback.preference.internal;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class OutlineOnlyWithChildrenFrameLayout extends FrameLayout {
    public ViewOutlineProvider mInnerOutlineProvider;
    public AnonymousClass1 mMagicalOutlineProvider;

    public OutlineOnlyWithChildrenFrameLayout(Context context) {
        super(context);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        invalidateOutline();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [androidx.leanback.preference.internal.OutlineOnlyWithChildrenFrameLayout$1] */
    @Override // android.view.View
    public final void setOutlineProvider(ViewOutlineProvider viewOutlineProvider) {
        this.mInnerOutlineProvider = viewOutlineProvider;
        if (this.mMagicalOutlineProvider == null) {
            this.mMagicalOutlineProvider = new ViewOutlineProvider() { // from class: androidx.leanback.preference.internal.OutlineOnlyWithChildrenFrameLayout.1
                @Override // android.view.ViewOutlineProvider
                public final void getOutline(View view, Outline outline) {
                    if (OutlineOnlyWithChildrenFrameLayout.this.getChildCount() > 0) {
                        OutlineOnlyWithChildrenFrameLayout.this.mInnerOutlineProvider.getOutline(view, outline);
                    } else {
                        ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
                    }
                }
            };
        }
        super.setOutlineProvider(this.mMagicalOutlineProvider);
    }

    public OutlineOnlyWithChildrenFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OutlineOnlyWithChildrenFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public OutlineOnlyWithChildrenFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
