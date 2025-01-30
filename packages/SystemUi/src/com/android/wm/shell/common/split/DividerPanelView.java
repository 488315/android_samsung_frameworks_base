package com.android.wm.shell.common.split;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Slog;
import android.view.SemBlurInfo;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DividerPanelView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LottieAnimationView mAddAppPairIcon;
    public ColorStateList mColorTintList;
    public LinearLayout mContainer;
    public final Context mContext;
    public final Handler mHandler;
    public LottieAnimationView mRotatingIcon;
    public LottieAnimationView mSwitchingIcon;

    public DividerPanelView(Context context) {
        this(context, null);
    }

    public final void createLottieTask(final LottieAnimationView lottieAnimationView, final String str) {
        Slog.d("DividerPanelView", "createLottieTask: asset=".concat(str));
        final LottieTask fromAsset = LottieCompositionFactory.fromAsset(getContext(), str);
        fromAsset.addListener(new LottieListener() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda0
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                final String str2 = str;
                final LottieTask lottieTask = fromAsset;
                final LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                final LottieComposition lottieComposition = (LottieComposition) obj;
                int i = DividerPanelView.$r8$clinit;
                final DividerPanelView dividerPanelView = DividerPanelView.this;
                dividerPanelView.getClass();
                Slog.d("DividerPanelView", "createLottieTask: onResult, asset=" + str2);
                dividerPanelView.mHandler.post(new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DividerPanelView dividerPanelView2 = dividerPanelView;
                        LottieTask lottieTask2 = lottieTask;
                        String str3 = str2;
                        LottieAnimationView lottieAnimationView3 = lottieAnimationView2;
                        LottieComposition lottieComposition2 = lottieComposition;
                        int i2 = DividerPanelView.$r8$clinit;
                        dividerPanelView2.getClass();
                        Slog.d("DividerPanelView", "createLottieTask: setComposition, lottieTask=" + lottieTask2 + ", asset=" + str3);
                        try {
                            Field declaredField = LottieTask.class.getDeclaredField("handler");
                            declaredField.setAccessible(true);
                            declaredField.set(lottieTask2, new Handler(Looper.myLooper()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        lottieAnimationView3.setComposition(lottieComposition2);
                    }
                });
            }
        });
        fromAsset.addFailureListener(new LottieListener() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                int i = DividerPanelView.$r8$clinit;
                Slog.e("DividerPanelView", "createLottieTask: Unable to parse json composition : " + str);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        sendAccessibilityEvent(8);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mContainer = (LinearLayout) findViewById(R.id.divider_panel_container);
        this.mRotatingIcon = (LottieAnimationView) findViewById(R.id.rotating_icon);
        this.mSwitchingIcon = (LottieAnimationView) findViewById(R.id.switching_icon);
        this.mAddAppPairIcon = (LottieAnimationView) findViewById(R.id.add_app_pair_icon);
        int color = this.mContext.getResources().getColor(R.color.mw_divider_panel_button_color_bg);
        this.mContainer.semSetBlurInfo(new SemBlurInfo.Builder(0).setRadius(125).setBackgroundColor(Color.argb(204, Color.red(color), Color.green(color), Color.blue(color))).setBackgroundCornerRadius(this.mContext.getResources().getDimensionPixelSize(R.dimen.mw_divider_panel_buttons_radius)).build());
        boolean isNightModeActive = this.mContext.getResources().getConfiguration().isNightModeActive();
        if (Settings.System.getInt(this.mContext.getContentResolver(), "wallpapertheme_state", 0) == 1) {
            this.mColorTintList = this.mContext.getResources().getColorStateList(17171332, null);
        } else if (isNightModeActive) {
            this.mColorTintList = this.mContext.getResources().getColorStateList(R.color.sec_decor_icon_color_dark, null);
        } else {
            this.mColorTintList = this.mContext.getResources().getColorStateList(R.color.sec_decor_icon_color_light, null);
        }
        int childCount = this.mContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((LottieAnimationView) this.mContainer.getChildAt(i)).addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback() { // from class: com.android.wm.shell.common.split.DividerPanelView.3
                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public final Object getValue() {
                    return new PorterDuffColorFilter(DividerPanelView.this.mColorTintList.getDefaultColor(), PorterDuff.Mode.SRC_ATOP);
                }
            });
        }
        createLottieTask(this.mRotatingIcon, "splitview_divider_option_icon_rotating_layout.json");
        createLottieTask(this.mSwitchingIcon, "splitview_divider_option_icon_switching.json");
        createLottieTask(this.mAddAppPairIcon, "splitview_divider_option_icon_add_apppair.json");
    }

    public DividerPanelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DividerPanelView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DividerPanelView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
        this.mHandler = new Handler();
    }
}
