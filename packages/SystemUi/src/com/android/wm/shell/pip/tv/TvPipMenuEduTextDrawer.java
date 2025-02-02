package com.android.wm.shell.pip.tv;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipMenuEduTextDrawer extends FrameLayout {
    public final TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0 mCloseDrawerRunnable;
    public final TextView mEduTextView;
    public final Listener mListener;
    public final Handler mMainHandler;
    public final float mMarqueeAnimSpeed;
    public final TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0 mStartScrollEduTextRunnable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
    }

    public static void $r8$lambda$2GDlmZOpGSSk_mkyIVJnZDt_4Mk(final TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer) {
        tvPipMenuEduTextDrawer.getClass();
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m231i(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2127023102, "%s: closeDrawer()", "TvPipMenuEduTextDrawer");
        }
        int integer = ((FrameLayout) tvPipMenuEduTextDrawer).mContext.getResources().getInteger(R.integer.pip_edu_text_view_exit_animation_duration);
        int integer2 = ((FrameLayout) tvPipMenuEduTextDrawer).mContext.getResources().getInteger(R.integer.pip_edu_text_window_exit_animation_duration);
        tvPipMenuEduTextDrawer.mEduTextView.animate().alpha(0.0f).setInterpolator(TvPipInterpolators.EXIT).setDuration(integer).start();
        ValueAnimator ofInt = ValueAnimator.ofInt(tvPipMenuEduTextDrawer.getHeight(), 0);
        ofInt.setDuration(integer2);
        ofInt.setInterpolator(TvPipInterpolators.BROWSE);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuEduTextDrawer$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer2 = TvPipMenuEduTextDrawer.this;
                ViewGroup.LayoutParams layoutParams = tvPipMenuEduTextDrawer2.getLayoutParams();
                layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                tvPipMenuEduTextDrawer2.setLayoutParams(layoutParams);
            }
        });
        ofInt.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuEduTextDrawer.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer2 = TvPipMenuEduTextDrawer.this;
                tvPipMenuEduTextDrawer2.setVisibility(8);
                TvPipMenuView tvPipMenuView = (TvPipMenuView) tvPipMenuEduTextDrawer2.mListener;
                tvPipMenuView.mPipFrameView.setVisibility(8);
                tvPipMenuView.mEduTextContainer.setVisibility(8);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer2 = TvPipMenuEduTextDrawer.this;
                tvPipMenuEduTextDrawer2.setVisibility(8);
                TvPipMenuView tvPipMenuView = (TvPipMenuView) tvPipMenuEduTextDrawer2.mListener;
                tvPipMenuView.mPipFrameView.setVisibility(8);
                tvPipMenuView.mEduTextContainer.setVisibility(8);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofInt.start();
        TvPipMenuController tvPipMenuController = (TvPipMenuController) ((TvPipMenuView) tvPipMenuEduTextDrawer.mListener).mListener;
        tvPipMenuController.getClass();
        tvPipMenuController.mTvPipBoundsState.mPipMenuTemporaryDecorInsets = Insets.NONE;
        TvPipController tvPipController = (TvPipController) tvPipMenuController.mDelegate;
        tvPipController.updatePinnedStackBounds(tvPipController.mEduTextWindowExitAnimationDuration, false);
    }

    /* renamed from: $r8$lambda$lPOhimvo28UhDjb5MHyCarI-R2c, reason: not valid java name */
    public static /* synthetic */ void m2756$r8$lambda$lPOhimvo28UhDjb5MHyCarIR2c(TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer, SpannableString spannableString, SpannedString spannedString, Annotation annotation) {
        Drawable drawable = tvPipMenuEduTextDrawer.getResources().getDrawable(R.drawable.home_icon, ((FrameLayout) tvPipMenuEduTextDrawer).mContext.getTheme());
        if (drawable != null) {
            drawable.mutate();
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            spannableString.setSpan(new CenteredImageSpan(drawable), spannedString.getSpanStart(annotation), spannedString.getSpanEnd(annotation), 33);
        }
    }

    public TvPipMenuEduTextDrawer(Context context, Handler handler, Listener listener) {
        super(context, null, 0, 0);
        this.mCloseDrawerRunnable = new TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0(this, 0);
        this.mStartScrollEduTextRunnable = new TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0(this, 1);
        this.mListener = listener;
        this.mMainHandler = handler;
        this.mMarqueeAnimSpeed = (context.getResources().getDisplayMetrics().density * 30.0f) / 1000.0f;
        TextView textView = new TextView(((FrameLayout) this).mContext);
        this.mEduTextView = textView;
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.pip_menu_edu_text_view_height);
        int integer = ((FrameLayout) this).mContext.getResources().getInteger(R.integer.pip_edu_text_scroll_times);
        textView.setLayoutParams(new FrameLayout.LayoutParams(-1, dimensionPixelSize, 81));
        textView.setGravity(17);
        textView.setClickable(false);
        final SpannedString spannedString = (SpannedString) getResources().getText(R.string.pip_edu_text);
        final SpannableString spannableString = new SpannableString(spannedString);
        Arrays.stream((Annotation[]) spannedString.getSpans(0, spannedString.length(), Annotation.class)).findFirst().ifPresent(new Consumer() { // from class: com.android.wm.shell.pip.tv.TvPipMenuEduTextDrawer$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TvPipMenuEduTextDrawer.m2756$r8$lambda$lPOhimvo28UhDjb5MHyCarIR2c(TvPipMenuEduTextDrawer.this, spannableString, spannedString, (Annotation) obj);
            }
        });
        textView.setText(spannableString);
        textView.setSingleLine();
        textView.setTextAppearance(R.style.TvPipEduText);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setMarqueeRepeatLimit(integer);
        textView.setHorizontallyScrolling(true);
        textView.setHorizontalFadingEdgeEnabled(true);
        textView.setSelected(false);
        addView(textView);
        setLayoutParams(new FrameLayout.LayoutParams(-1, dimensionPixelSize, 17));
        setClipChildren(true);
    }

    public final void closeIfNeeded() {
        if (this.mMainHandler.hasCallbacks(this.mCloseDrawerRunnable)) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -152425190, 0, "%s: close(), closing the edu text drawer because of user action", "TvPipMenuEduTextDrawer");
            }
            this.mMainHandler.removeCallbacks(this.mCloseDrawerRunnable);
            this.mCloseDrawerRunnable.run();
        }
    }

    public final void init() {
        int integer;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m231i(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1015459141, "%s: init()", "TvPipMenuEduTextDrawer");
        }
        int integer2 = ((FrameLayout) this).mContext.getResources().getInteger(R.integer.pip_edu_text_start_scroll_delay);
        if (isEduTextMarqueed()) {
            this.mMainHandler.postDelayed(this.mStartScrollEduTextRunnable, integer2);
        }
        Handler handler = this.mMainHandler;
        TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0 tvPipMenuEduTextDrawer$$ExternalSyntheticLambda0 = this.mCloseDrawerRunnable;
        if (isEduTextMarqueed()) {
            integer = this.mEduTextView.getMarqueeRepeatLimit() * ((int) (((((int) this.mEduTextView.getLayout().getLineWidth(0)) + (this.mEduTextView.getWidth() / 3.0f)) / this.mMarqueeAnimSpeed) + 1200.0f));
        } else {
            integer = ((FrameLayout) this).mContext.getResources().getInteger(R.integer.pip_edu_text_non_scroll_show_duration);
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1315133481, 4, "%s: getEduTextShowDuration(), showDuration=%d", "TvPipMenuEduTextDrawer", Long.valueOf(integer));
        }
        handler.postDelayed(tvPipMenuEduTextDrawer$$ExternalSyntheticLambda0, integer2 + integer);
        this.mEduTextView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuEduTextDrawer.1
            @Override // android.view.ViewTreeObserver.OnWindowAttachListener
            public final void onWindowDetached() {
                TvPipMenuEduTextDrawer.this.mEduTextView.getViewTreeObserver().removeOnWindowAttachListener(this);
                TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer = TvPipMenuEduTextDrawer.this;
                tvPipMenuEduTextDrawer.mMainHandler.removeCallbacks(tvPipMenuEduTextDrawer.mStartScrollEduTextRunnable);
                TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer2 = TvPipMenuEduTextDrawer.this;
                tvPipMenuEduTextDrawer2.mMainHandler.removeCallbacks(tvPipMenuEduTextDrawer2.mCloseDrawerRunnable);
            }

            @Override // android.view.ViewTreeObserver.OnWindowAttachListener
            public final void onWindowAttached() {
            }
        });
    }

    public final boolean isEduTextMarqueed() {
        return (this.mEduTextView.getWidth() - this.mEduTextView.getCompoundPaddingLeft()) - this.mEduTextView.getCompoundPaddingRight() < ((int) this.mEduTextView.getLayout().getLineWidth(0));
    }
}
