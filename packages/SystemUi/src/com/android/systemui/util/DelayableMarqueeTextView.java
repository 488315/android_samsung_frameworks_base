package com.android.systemui.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import com.android.systemui.R$styleable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DelayableMarqueeTextView extends SafeMarqueeTextView {
    public final DelayableMarqueeTextView$enableMarquee$1 enableMarquee;
    public boolean marqueeBlocked;
    public final long marqueeDelay;
    public boolean wantsMarquee;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DelayableMarqueeTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // com.android.systemui.util.SafeMarqueeTextView
    public final void startMarquee() {
        if (isSelected()) {
            this.wantsMarquee = true;
            if (!this.marqueeBlocked) {
                super.startMarquee();
                return;
            }
            Handler handler = getHandler();
            if ((handler == null || handler.hasCallbacks(this.enableMarquee)) ? false : true) {
                postDelayed(this.enableMarquee, this.marqueeDelay);
            }
        }
    }

    @Override // com.android.systemui.util.SafeMarqueeTextView
    public final void stopMarquee() {
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

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.util.DelayableMarqueeTextView$enableMarquee$1] */
    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.marqueeDelay = 2000L;
        this.marqueeBlocked = true;
        this.enableMarquee = new Runnable() { // from class: com.android.systemui.util.DelayableMarqueeTextView$enableMarquee$1
            @Override // java.lang.Runnable
            public final void run() {
                DelayableMarqueeTextView delayableMarqueeTextView = DelayableMarqueeTextView.this;
                if (delayableMarqueeTextView.wantsMarquee) {
                    delayableMarqueeTextView.marqueeBlocked = false;
                    delayableMarqueeTextView.startMarquee();
                }
            }
        };
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DelayableMarqueeTextView, i, i2);
        this.marqueeDelay = obtainStyledAttributes.getInteger(0, 2000);
        obtainStyledAttributes.recycle();
    }
}
