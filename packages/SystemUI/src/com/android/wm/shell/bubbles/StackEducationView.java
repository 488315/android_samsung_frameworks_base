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
import com.android.wm.shell.shared.TypefaceUtils;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StackEducationView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy descTextView$delegate;
    public boolean isHiding;
    public final Manager manager;
    public final BubblePositioner positioner;
    public final Lazy titleTextView$delegate;
    public final Lazy view$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Manager {
    }

    static {
        new Companion(null);
    }

    public StackEducationView(Context context, BubblePositioner bubblePositioner, Manager manager) {
        super(context);
        this.positioner = bubblePositioner;
        this.manager = manager;
        final int i = 0;
        this.view$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.wm.shell.bubbles.StackEducationView$$ExternalSyntheticLambda0
            public final /* synthetic */ StackEducationView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StackEducationView stackEducationView = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = StackEducationView.$r8$clinit;
                        return stackEducationView.requireViewById(R.id.stack_education_layout);
                    case 1:
                        int i3 = StackEducationView.$r8$clinit;
                        return (TextView) stackEducationView.requireViewById(R.id.stack_education_title);
                    default:
                        int i4 = StackEducationView.$r8$clinit;
                        return (TextView) stackEducationView.requireViewById(R.id.stack_education_description);
                }
            }
        });
        final int i2 = 1;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.wm.shell.bubbles.StackEducationView$$ExternalSyntheticLambda0
            public final /* synthetic */ StackEducationView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StackEducationView stackEducationView = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = StackEducationView.$r8$clinit;
                        return stackEducationView.requireViewById(R.id.stack_education_layout);
                    case 1:
                        int i3 = StackEducationView.$r8$clinit;
                        return (TextView) stackEducationView.requireViewById(R.id.stack_education_title);
                    default:
                        int i4 = StackEducationView.$r8$clinit;
                        return (TextView) stackEducationView.requireViewById(R.id.stack_education_description);
                }
            }
        });
        this.titleTextView$delegate = lazy;
        final int i3 = 2;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.wm.shell.bubbles.StackEducationView$$ExternalSyntheticLambda0
            public final /* synthetic */ StackEducationView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StackEducationView stackEducationView = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = StackEducationView.$r8$clinit;
                        return stackEducationView.requireViewById(R.id.stack_education_layout);
                    case 1:
                        int i32 = StackEducationView.$r8$clinit;
                        return (TextView) stackEducationView.requireViewById(R.id.stack_education_title);
                    default:
                        int i4 = StackEducationView.$r8$clinit;
                        return (TextView) stackEducationView.requireViewById(R.id.stack_education_description);
                }
            }
        });
        this.descTextView$delegate = lazy2;
        LayoutInflater.from(context).inflate(R.layout.bubble_stack_user_education, this);
        TypefaceUtils.Companion companion = TypefaceUtils.Companion;
        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
        companion.getClass();
        setVisibility(8);
        setElevation(getResources().getDimensionPixelSize(R.dimen.bubble_elevation));
        setLayoutDirection(3);
    }

    public final void hide(boolean z) {
        if (getVisibility() != 0 || this.isHiding) {
            return;
        }
        this.isHiding = true;
        ((BubbleStackViewManager$Companion$fromBubbleController$1) ((BubbleStackView$$ExternalSyntheticLambda18) this.manager).f$0).$controller.updateWindowFlagsForBackpress(false);
        animate().alpha(0.0f).setDuration(z ? 40L : 200L).withEndAction(new Runnable() { // from class: com.android.wm.shell.bubbles.StackEducationView$hide$1
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
        ((BubbleStackViewManager$Companion$fromBubbleController$1) ((BubbleStackView$$ExternalSyntheticLambda18) this.manager).f$0).$controller.updateWindowFlagsForBackpress(false);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setLayoutDirection(getResources().getConfiguration().getLayoutDirection());
        TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.attr.colorAccent, android.R.attr.textColorPrimaryInverse});
        int color = obtainStyledAttributes.getColor(0, -16777216);
        int color2 = obtainStyledAttributes.getColor(1, -1);
        obtainStyledAttributes.recycle();
        int ensureTextContrast = ContrastColorUtil.ensureTextContrast(color2, color, true);
        ((TextView) this.titleTextView$delegate.getValue()).setTextColor(ensureTextContrast);
        ((TextView) this.descTextView$delegate.getValue()).setTextColor(ensureTextContrast);
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        super.setLayoutDirection(i);
        ((View) this.view$delegate.getValue()).setBackgroundResource(i == 0 ? R.drawable.bubble_stack_user_education_bg : R.drawable.bubble_stack_user_education_bg_rtl);
    }
}
