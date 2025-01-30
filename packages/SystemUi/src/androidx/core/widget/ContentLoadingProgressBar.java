package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ContentLoadingProgressBar extends ProgressBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ContentLoadingProgressBar$$ExternalSyntheticLambda0 mDelayedHide;
    public final ContentLoadingProgressBar$$ExternalSyntheticLambda0 mDelayedShow;

    public ContentLoadingProgressBar(Context context) {
        this(context, null);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(this.mDelayedHide);
        removeCallbacks(this.mDelayedShow);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mDelayedHide);
        removeCallbacks(this.mDelayedShow);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.core.widget.ContentLoadingProgressBar$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.core.widget.ContentLoadingProgressBar$$ExternalSyntheticLambda0] */
    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        final int i = 0;
        this.mDelayedHide = new Runnable(this) { // from class: androidx.core.widget.ContentLoadingProgressBar$$ExternalSyntheticLambda0
            public final /* synthetic */ ContentLoadingProgressBar f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        ContentLoadingProgressBar contentLoadingProgressBar = this.f$0;
                        int i2 = ContentLoadingProgressBar.$r8$clinit;
                        contentLoadingProgressBar.setVisibility(8);
                        break;
                    default:
                        ContentLoadingProgressBar contentLoadingProgressBar2 = this.f$0;
                        int i3 = ContentLoadingProgressBar.$r8$clinit;
                        contentLoadingProgressBar2.getClass();
                        System.currentTimeMillis();
                        contentLoadingProgressBar2.setVisibility(0);
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mDelayedShow = new Runnable(this) { // from class: androidx.core.widget.ContentLoadingProgressBar$$ExternalSyntheticLambda0
            public final /* synthetic */ ContentLoadingProgressBar f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        ContentLoadingProgressBar contentLoadingProgressBar = this.f$0;
                        int i22 = ContentLoadingProgressBar.$r8$clinit;
                        contentLoadingProgressBar.setVisibility(8);
                        break;
                    default:
                        ContentLoadingProgressBar contentLoadingProgressBar2 = this.f$0;
                        int i3 = ContentLoadingProgressBar.$r8$clinit;
                        contentLoadingProgressBar2.getClass();
                        System.currentTimeMillis();
                        contentLoadingProgressBar2.setVisibility(0);
                        break;
                }
            }
        };
    }
}
