package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StackEducationView extends LinearLayout {
    public final long ANIMATE_DURATION;
    public final long ANIMATE_DURATION_SHORT;
    public final BubbleController controller;
    public final Lazy descTextView$delegate;
    public boolean isHiding;
    public final BubblePositioner positioner;
    public final Lazy titleTextView$delegate;
    public final Lazy view$delegate;

    public StackEducationView(Context context, BubblePositioner bubblePositioner, BubbleController bubbleController) {
        super(context);
        this.ANIMATE_DURATION = 200L;
        this.ANIMATE_DURATION_SHORT = 40L;
        this.positioner = bubblePositioner;
        this.controller = bubbleController;
        this.view$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.bubbles.StackEducationView$view$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return StackEducationView.this.findViewById(R.id.stack_education_layout);
            }
        });
        this.titleTextView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.bubbles.StackEducationView$titleTextView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) StackEducationView.this.findViewById(R.id.stack_education_title);
            }
        });
        this.descTextView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.bubbles.StackEducationView$descTextView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) StackEducationView.this.findViewById(R.id.stack_education_description);
            }
        });
        LayoutInflater.from(context).inflate(R.layout.bubble_stack_user_education, this);
        setVisibility(8);
        setElevation(getResources().getDimensionPixelSize(R.dimen.bubble_elevation));
        setLayoutDirection(3);
    }

    public final void hide(boolean z) {
        if (getVisibility() != 0 || this.isHiding) {
            return;
        }
        this.isHiding = true;
        this.controller.updateWindowFlagsForBackpress(false);
        animate().alpha(0.0f).setDuration(z ? this.ANIMATE_DURATION_SHORT : this.ANIMATE_DURATION).withEndAction(new Runnable() { // from class: com.android.wm.shell.bubbles.StackEducationView$hide$1
            @Override // java.lang.Runnable
            public final void run() {
                StackEducationView.this.setVisibility(8);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setFocusableInTouchMode(true);
        setOnKeyListener(new View.OnKeyListener() { // from class: com.android.wm.shell.bubbles.StackEducationView$onAttachedToWindow$1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && i == 4) {
                    StackEducationView stackEducationView = StackEducationView.this;
                    if (!stackEducationView.isHiding) {
                        stackEducationView.hide(false);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnKeyListener(null);
        this.controller.updateWindowFlagsForBackpress(false);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setLayoutDirection(getResources().getConfiguration().getLayoutDirection());
        TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.attr.colorAccent, android.R.attr.textColorPrimaryInverse});
        int color = obtainStyledAttributes.getColor(0, EmergencyPhoneWidget.BG_COLOR);
        int color2 = obtainStyledAttributes.getColor(1, -1);
        obtainStyledAttributes.recycle();
        int ensureTextContrast = ContrastColorUtil.ensureTextContrast(color2, color, true);
        ((TextView) this.titleTextView$delegate.getValue()).setTextColor(ensureTextContrast);
        ((TextView) this.descTextView$delegate.getValue()).setTextColor(ensureTextContrast);
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        super.setLayoutDirection(i);
        ((View) this.view$delegate.getValue()).setBackgroundResource(getResources().getConfiguration().getLayoutDirection() == 0 ? R.drawable.bubble_stack_user_education_bg : R.drawable.bubble_stack_user_education_bg_rtl);
    }
}
