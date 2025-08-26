package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.MenuPopupAnimator;
import com.android.wm.shell.windowdecor.widget.CaptionAnimationButton;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import com.android.wm.shell.windowdecor.widget.CaptionButtonDivider;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class PopupButtonPolicy extends CaptionButtonStateManager implements WindowDecorButtonPolicy {
    public CaptionAnimationButton mAnimButton;
    public final Context mContext;
    public MenuPopupAnimator mMenuPopupAnimator;
    public int mPopupHeight;
    public int mPopupWidth;
    public ViewGroup mRootView;

    public PopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController) {
        this(runningTaskInfo, context, displayController, false);
    }

    public final void adjustLeftMostButtonPadding(ViewGroup viewGroup) {
        CaptionButton captionButton = (CaptionButton) viewGroup.findViewById(getLeftMostPopupButtonId());
        if (captionButton != null) {
            captionButton.setPaddingRelative(WindowDecorButtonPolicy.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.mw_caption_edge_button_padding), captionButton.getPaddingTop(), captionButton.getPaddingEnd(), captionButton.getPaddingBottom());
        }
    }

    public void animateOpenMenu() {
        MenuPopupAnimator menuPopupAnimator = this.mMenuPopupAnimator;
        if (menuPopupAnimator == null) {
            return;
        }
        menuPopupAnimator.animateOpen(this.mAnimButton);
    }

    public int calculatePopupHeight() {
        return WindowDecorButtonPolicy.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.mw_handle_menu_height);
    }

    public int calculatePopupWidth() {
        Resources resources = this.mContext.getResources();
        return (WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_caption_edge_button_size) * 2) + ((getVisibleButtonCount() - 2) * WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_caption_button_width)) + (isVerticalDividerSupported() ? WindowDecorButtonPolicy.loadDimensionPixelSize(resources, R.dimen.mw_caption_divider_size) : 0);
    }

    public CaptionAnimationButton getAnimationButton(ViewGroup viewGroup) {
        return null;
    }

    public int getLayoutResId() {
        return 0;
    }

    public int getLeftMostPopupButtonId() {
        return -1;
    }

    public int getVisibleButtonCount() {
        return 0;
    }

    public boolean isVerticalDividerSupported() {
        return false;
    }

    @Override // com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager
    public void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        super.setupCaptionButtonState(context, viewGroup, onTouchListener, onClickListener);
        ColorStateList buttonColor = getButtonColor();
        final CaptionAnimationButton animationButton = getAnimationButton(viewGroup);
        this.mAnimButton = animationButton;
        if (animationButton != null) {
            boolean z = this instanceof CaptionPopupButtonPolicy;
            animationButton.mColorStateList = buttonColor;
            if (z) {
                final String str = "mw_popup_option_btn_header_handle.json";
                final LottieTask lottieTaskFromAsset = LottieCompositionFactory.fromAsset(animationButton.mContext, "mw_popup_option_btn_header_handle.json");
                lottieTaskFromAsset.addListener(new LottieListener() { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton$$ExternalSyntheticLambda0
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        final LottieComposition lottieComposition = (LottieComposition) obj;
                        final CaptionAnimationButton captionAnimationButton = animationButton;
                        Handler handler = captionAnimationButton.mHandler;
                        final LottieTask lottieTask = lottieTaskFromAsset;
                        handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
                                CaptionAnimationButton captionAnimationButton2 = captionAnimationButton;
                                LottieTask lottieTask2 = lottieTask;
                                LottieComposition lottieComposition2 = lottieComposition;
                                int i = CaptionAnimationButton.$r8$clinit;
                                captionAnimationButton2.getClass();
                                try {
                                    Field declaredField = LottieTask.class.getDeclaredField("handler");
                                    declaredField.setAccessible(true);
                                    declaredField.set(lottieTask2, new Handler(Looper.myLooper()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                captionAnimationButton2.setComposition(lottieComposition2);
                            }
                        });
                    }
                });
                lottieTaskFromAsset.addFailureListener(new LottieListener() { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton$$ExternalSyntheticLambda1
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        int i = CaptionAnimationButton.$r8$clinit;
                        Slog.e("CaptionAnimationButton", "createLottieTask: Unable to parse json composition : ".concat(str));
                    }
                });
            } else {
                final String str2 = "mw_popup_option_btn_handle_header.json";
                final LottieTask lottieTaskFromAsset2 = LottieCompositionFactory.fromAsset(animationButton.mContext, "mw_popup_option_btn_handle_header.json");
                lottieTaskFromAsset2.addListener(new LottieListener() { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton$$ExternalSyntheticLambda0
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        final LottieComposition lottieComposition = (LottieComposition) obj;
                        final CaptionAnimationButton captionAnimationButton = animationButton;
                        Handler handler = captionAnimationButton.mHandler;
                        final LottieTask lottieTask = lottieTaskFromAsset2;
                        handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
                                CaptionAnimationButton captionAnimationButton2 = captionAnimationButton;
                                LottieTask lottieTask2 = lottieTask;
                                LottieComposition lottieComposition2 = lottieComposition;
                                int i = CaptionAnimationButton.$r8$clinit;
                                captionAnimationButton2.getClass();
                                try {
                                    Field declaredField = LottieTask.class.getDeclaredField("handler");
                                    declaredField.setAccessible(true);
                                    declaredField.set(lottieTask2, new Handler(Looper.myLooper()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                captionAnimationButton2.setComposition(lottieComposition2);
                            }
                        });
                    }
                });
                lottieTaskFromAsset2.addFailureListener(new LottieListener() { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton$$ExternalSyntheticLambda1
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        int i = CaptionAnimationButton.$r8$clinit;
                        Slog.e("CaptionAnimationButton", "createLottieTask: Unable to parse json composition : ".concat(str2));
                    }
                });
            }
            animationButton.applyIconColor(buttonColor);
            this.mAnimButton.setOnTouchListener(onTouchListener);
            this.mAnimButton.setOnClickListener(onClickListener);
            this.mAnimButton.setBackground(getRippleDrawable(context, this.mAnimButton));
        }
        CaptionButton captionButton = (CaptionButton) viewGroup.findViewById(R.id.external_display);
        if (captionButton != null) {
            captionButton.setVisibility(this.mIsExternalDisplayConnected ? 0 : 8);
        }
        CaptionButtonDivider captionButtonDivider = (CaptionButtonDivider) viewGroup.findViewById(R.id.divider);
        if (captionButtonDivider != null) {
            if (!isVerticalDividerSupported()) {
                captionButtonDivider.setVisibility(8);
            } else {
                captionButtonDivider.setVisibility(0);
                captionButtonDivider.setBackgroundTintList(this.mContext.getColorStateList(this.mIsNightMode ? R.color.mw_caption_button_divider_color_dark : R.color.mw_caption_button_divider_color_light));
            }
        }
    }

    public void setupRootView(Context context, View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) throws Resources.NotFoundException {
        ViewGroup viewGroup = (ViewGroup) view;
        this.mRootView = viewGroup;
        viewGroup.setElevation(WindowDecorButtonPolicy.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.mw_handle_menu_shadow));
        ViewGroup viewGroup2 = this.mRootView;
        int color = context.getResources().getColor(this.mIsNightMode ? R.color.mw_caption_background_color_dark : R.color.mw_caption_background_color_light, null);
        GradientDrawable gradientDrawable = (GradientDrawable) viewGroup2.getBackground();
        if (gradientDrawable != null) {
            gradientDrawable.setColor(color);
        }
        setupCaptionButtonState(context, this.mRootView, onTouchListener, onClickListener);
        this.mPopupWidth = calculatePopupWidth();
        this.mPopupHeight = calculatePopupHeight();
        this.mMenuPopupAnimator = new MenuPopupAnimator(this.mRootView, this.mPopupWidth, this.mPopupHeight);
    }

    public PopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z) {
        super(runningTaskInfo, context, displayController, true, z);
        this.mContext = context;
    }
}
