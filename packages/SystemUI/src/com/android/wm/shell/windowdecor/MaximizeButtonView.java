package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.window.DesktopModeFlags;
import androidx.core.content.ContextCompat;
import com.android.systemui.R;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MaximizeButtonView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean hoverDisabled;
    public final AnimatorSet hoverProgressAnimatorSet;
    public final ImageButton maximizeWindow;
    public Function0 onHoverAnimationFinishedListener;
    public final Lazy progressBar$delegate;
    public final ViewStub stubProgressBarContainer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MaximizeButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.hoverProgressAnimatorSet = new AnimatorSet();
        this.progressBar$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewStub viewStub = MaximizeButtonView.this.stubProgressBarContainer;
                if (viewStub == null) {
                    viewStub = null;
                }
                return (ProgressBar) ((FrameLayout) viewStub.inflate()).requireViewById(R.id.progress_bar);
            }
        });
        LayoutInflater.from(context).inflate(R.layout.maximize_menu_button, (ViewGroup) this, true);
        this.stubProgressBarContainer = (ViewStub) requireViewById(R.id.stub_progress_bar_container);
        this.maximizeWindow = (ImageButton) requireViewById(R.id.maximize_window);
    }

    public final void cancelHoverAnimation() {
        ArrayList<Animator> childAnimations = this.hoverProgressAnimatorSet.getChildAnimations();
        int size = childAnimations.size();
        int i = 0;
        while (i < size) {
            Animator animator = childAnimations.get(i);
            i++;
            animator.removeAllListeners();
        }
        this.hoverProgressAnimatorSet.cancel();
        getProgressBar().setVisibility(4);
    }

    public final ProgressBar getProgressBar() {
        return (ProgressBar) this.progressBar$delegate.getValue();
    }

    public final void setAnimationTints(boolean z, ColorStateList colorStateList, final Integer num, Drawable drawable) {
        if (!DesktopModeFlags.ENABLE_THEMED_APP_HEADERS.isTrue()) {
            final ColorStateList valueOf = z ? ColorStateList.valueOf(getResources().getColor(R.color.desktop_mode_maximize_menu_progress_dark)) : ColorStateList.valueOf(getResources().getColor(R.color.desktop_mode_maximize_menu_progress_light));
            valueOf.getClass();
            ColorStateList colorStateList2 = z ? ContextCompat.getColorStateList(R.color.desktop_mode_caption_button_color_selector_dark, getContext()) : ContextCompat.getColorStateList(R.color.desktop_mode_caption_button_color_selector_light, getContext());
            ViewStub viewStub = this.stubProgressBarContainer;
            (viewStub != null ? viewStub : null).setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$setAnimationTints$5
                @Override // android.view.ViewStub.OnInflateListener
                public final void onInflate(ViewStub viewStub2, View view) {
                    ((ProgressBar) ((FrameLayout) view).requireViewById(R.id.progress_bar)).setProgressTintList(valueOf);
                }
            });
            Drawable background = this.maximizeWindow.getBackground();
            if (background != null) {
                background.setTintList(colorStateList2);
                return;
            }
            return;
        }
        if (colorStateList == null) {
            throw new IllegalArgumentException("Icon foreground color must be non-null");
        }
        if (num == null) {
            throw new IllegalArgumentException("Base foreground color must be non-null");
        }
        if (drawable == null) {
            throw new IllegalArgumentException("Background drawable must be non-null");
        }
        this.maximizeWindow.setImageTintList(colorStateList);
        this.maximizeWindow.setBackground(drawable);
        ViewStub viewStub2 = this.stubProgressBarContainer;
        (viewStub2 != null ? viewStub2 : null).setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$setAnimationTints$4
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub3, View view) {
                ProgressBar progressBar = (ProgressBar) ((FrameLayout) view).requireViewById(R.id.progress_bar);
                progressBar.setProgressTintList(ColorStateList.valueOf(num.intValue()).withAlpha(38));
                progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(0));
            }
        });
    }
}
