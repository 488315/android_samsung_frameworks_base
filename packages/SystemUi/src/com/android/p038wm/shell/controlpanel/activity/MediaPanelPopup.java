package com.android.p038wm.shell.controlpanel.activity;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import com.android.p038wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.systemui.R;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MediaPanelPopup extends FloatingUI {
    public final boolean mIsNext;
    public LottieAnimationView mLottieAnimationView;
    public TextView mSeekTextView;

    public MediaPanelPopup(Context context, boolean z) {
        super(context);
        this.mIsNext = z;
    }

    @Override // com.android.p038wm.shell.controlpanel.activity.FloatingUI
    public final void connectUIObject() {
        boolean z = this.mIsNext;
        Context context = this.mContext;
        if (z) {
            View inflate = View.inflate(context, R.layout.media_popup_next_layout, null);
            this.mOverlayView = inflate;
            final LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.lottie_view);
            this.mLottieAnimationView = lottieAnimationView;
            final LottieTask fromAsset = LottieCompositionFactory.fromAsset(context, "flex_panel_seek_media_popup_forward.json");
            fromAsset.addListener(new LottieListener() { // from class: com.android.wm.shell.controlpanel.activity.MediaPanelPopup$$ExternalSyntheticLambda1
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    final LottieComposition lottieComposition = (LottieComposition) obj;
                    final MediaPanelPopup mediaPanelPopup = MediaPanelPopup.this;
                    mediaPanelPopup.getClass();
                    Handler handler = new Handler();
                    final LottieTask lottieTask = fromAsset;
                    final LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                    handler.post(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.MediaPanelPopup$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaPanelPopup mediaPanelPopup2 = MediaPanelPopup.this;
                            LottieTask lottieTask2 = lottieTask;
                            LottieAnimationView lottieAnimationView3 = lottieAnimationView2;
                            LottieComposition lottieComposition2 = lottieComposition;
                            mediaPanelPopup2.getClass();
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
            fromAsset.addFailureListener(new MediaPanelPopup$$ExternalSyntheticLambda2());
        } else {
            View inflate2 = View.inflate(context, R.layout.media_popup_prev_layout, null);
            this.mOverlayView = inflate2;
            final LottieAnimationView lottieAnimationView2 = (LottieAnimationView) inflate2.findViewById(R.id.lottie_view);
            this.mLottieAnimationView = lottieAnimationView2;
            final LottieTask fromAsset2 = LottieCompositionFactory.fromAsset(context, "flex_panel_seek_media_popup_rewind.json");
            fromAsset2.addListener(new LottieListener() { // from class: com.android.wm.shell.controlpanel.activity.MediaPanelPopup$$ExternalSyntheticLambda1
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    final LottieComposition lottieComposition = (LottieComposition) obj;
                    final MediaPanelPopup mediaPanelPopup = MediaPanelPopup.this;
                    mediaPanelPopup.getClass();
                    Handler handler = new Handler();
                    final LottieTask lottieTask = fromAsset2;
                    final LottieAnimationView lottieAnimationView22 = lottieAnimationView2;
                    handler.post(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.MediaPanelPopup$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaPanelPopup mediaPanelPopup2 = MediaPanelPopup.this;
                            LottieTask lottieTask2 = lottieTask;
                            LottieAnimationView lottieAnimationView3 = lottieAnimationView22;
                            LottieComposition lottieComposition2 = lottieComposition;
                            mediaPanelPopup2.getClass();
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
            fromAsset2.addFailureListener(new MediaPanelPopup$$ExternalSyntheticLambda2());
        }
        this.mLottieAnimationView.setRepeatCount(-1);
        this.mSeekTextView = (TextView) this.mOverlayView.findViewById(R.id.seek_text);
    }

    @Override // com.android.p038wm.shell.controlpanel.activity.FloatingUI
    public final void fadeInAnimation() {
        final Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.sliderin);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.MediaPanelPopup$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaPanelPopup mediaPanelPopup = MediaPanelPopup.this;
                mediaPanelPopup.mOverlayView.startAnimation(loadAnimation);
            }
        }, 200L);
    }

    @Override // com.android.p038wm.shell.controlpanel.activity.FloatingUI
    public final void setAppendLayoutParams() {
        this.mLayoutParam.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams = this.mLayoutParam;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.type = VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F;
        Point point = new Point();
        this.mWindowManager.getDefaultDisplay().getRealSize(point);
        WindowManager.LayoutParams layoutParams2 = this.mLayoutParam;
        Context context = this.mContext;
        layoutParams2.height = context.getResources().getDimensionPixelSize(R.dimen.media_panel_popup_height);
        this.mLayoutParam.width = -2;
        int height = context.getResources().getConfiguration().windowConfiguration.getBounds().height();
        WindowManager.LayoutParams layoutParams3 = this.mLayoutParam;
        layoutParams3.y = ((point.y - height) / 2) - (layoutParams3.height / 2);
        if (ControlPanelUtils.isTypeFold()) {
            this.mLayoutParam.x = 200;
        } else {
            this.mLayoutParam.x = 100;
        }
        if (this.mIsNext) {
            this.mLayoutParam.gravity = 53;
        } else {
            this.mLayoutParam.gravity = 51;
        }
    }
}
