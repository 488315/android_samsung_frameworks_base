package com.android.systemui.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import com.android.systemui.res.R$styleable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DelayableMarqueeTextView extends SafeMarqueeTextView {
    public static final long DEFAULT_MARQUEE_DELAY = 2000;
    private final Runnable enableMarquee;
    private boolean marqueeBlocked;
    private long marqueeDelay;
    private boolean wantsMarquee;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DelayableMarqueeTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public final long getMarqueeDelay() {
        return this.marqueeDelay;
    }

    public final void setMarqueeDelay(long j) {
        this.marqueeDelay = j;
    }

    @Override // com.android.systemui.util.SafeMarqueeTextView
    public void startMarquee() {
        if (isSelected()) {
            this.wantsMarquee = true;
            if (!this.marqueeBlocked) {
                super.startMarquee();
                return;
            }
            Handler handler = getHandler();
            if (handler == null || handler.hasCallbacks(this.enableMarquee)) {
                return;
            }
            postDelayed(this.enableMarquee, this.marqueeDelay);
        }
    }

    @Override // com.android.systemui.util.SafeMarqueeTextView
    public void stopMarquee() {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.enableMarquee);
        }
        this.wantsMarquee = false;
        this.marqueeBlocked = true;
        super.stopMarquee();
    }

    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.marqueeDelay = DEFAULT_MARQUEE_DELAY;
        this.marqueeBlocked = true;
        this.enableMarquee = new Runnable() { // from class: com.android.systemui.util.DelayableMarqueeTextView$enableMarquee$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                z = DelayableMarqueeTextView.this.wantsMarquee;
                if (z) {
                    DelayableMarqueeTextView.this.marqueeBlocked = false;
                    DelayableMarqueeTextView.this.startMarquee();
                }
            }
        };
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DelayableMarqueeTextView, i, i2);
        this.marqueeDelay = obtainStyledAttributes.getInteger(0, 2000);
        obtainStyledAttributes.recycle();
    }
}
