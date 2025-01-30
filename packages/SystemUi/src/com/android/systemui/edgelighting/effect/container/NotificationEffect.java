package com.android.systemui.edgelighting.effect.container;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.Feature;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.android.systemui.edgelighting.effect.utils.VerificationCodeUtils;
import com.android.systemui.edgelighting.effect.view.MorphView;
import com.samsung.android.aod.AODManager;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationEffect extends AbsEdgeLightingView {
    public static final boolean mBlockNotiTouch_for_NA = "US".equals(SystemProperties.get("ro.csc.countryiso_code", ""));
    public float FREEFORM_HEIGHT_RATIO;
    public float FREEFORM_WIDTH_RATIO;
    public final String TAG;
    public IDreamManager dreamManager;
    public AODManager mAODManager;
    public AODBroadcastReceiver mAODTspUpdateReceiver;
    public int mConvertColor;
    public GestureDetector mGestureDetector;
    public final HandlerC13184 mHandler;
    public boolean mInfiniteLighting;
    public boolean mIsActionEnable;
    public boolean mIsActivity;
    public boolean mIsHideBriefPopupForEdgeLightingPlus;
    public boolean mIsShowMorphView;
    public boolean mIsSingleTapDisabledForEdgeLightingPlus;
    public boolean mIsSwipeDownDisabledForEdgeLightingPlus;
    public KeyguardManager mKgm;
    public MorphView mMorphView;
    public RelativeLayout mNotificationContainer;
    public String mNotificationKey;
    public final NotificationEffect$$ExternalSyntheticLambda1 mOnComputeInternalInsetsListener;
    public PendingIntent mPendingIntent;
    public PowerManager mPm;
    public final C13151 mPopupListener;
    public final Rect mTouchableRec;
    public boolean mUsingBlackBG;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.effect.container.NotificationEffect$1 */
    public final class C13151 {
        public C13151() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AODBroadcastReceiver extends BroadcastReceiver {
        public /* synthetic */ AODBroadcastReceiver(NotificationEffect notificationEffect, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (NotificationEffect.this.mPm.isInteractive()) {
                Slog.w(NotificationEffect.this.TAG, "BR_TSP_EVENT :onReceive : return: isInteractive");
            }
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("info", -1);
            Slog.i(NotificationEffect.this.TAG, "receive: " + action + " info : " + intExtra);
            if (intExtra != 11) {
                Slog.w(NotificationEffect.this.TAG, "BR_TSP_EVENT :onReceive : not double tap");
                return;
            }
            float[] floatArrayExtra = intent.getFloatArrayExtra(GlsIntent.Extras.EXTRA_LOCATION);
            if (floatArrayExtra == null || floatArrayExtra.length != 2) {
                Slog.w(NotificationEffect.this.TAG, "BR_TSP_EVENT :onReceive : There is no [x,y position] value");
                return;
            }
            Slog.w(NotificationEffect.this.TAG, "BR_TSP_EVENT : TOUCHED " + NotificationEffect.this.getToastRectCalculated() + ((int) floatArrayExtra[0]) + " : " + ((int) floatArrayExtra[1]));
            if (NotificationEffect.this.getToastRectCalculated().contains((int) floatArrayExtra[0], (int) floatArrayExtra[1])) {
                NotificationEffect.this.launchPendingIntent();
            } else {
                Slog.w(NotificationEffect.this.TAG, "BR_TSP_EVENT :onReceive : out of touch region");
            }
        }

        private AODBroadcastReceiver() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public /* synthetic */ GestureListener(NotificationEffect notificationEffect, int i) {
            this();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTap(MotionEvent motionEvent) {
            Slog.i(NotificationEffect.this.TAG, "onDoubleTap : ");
            Rect rect = NotificationEffect.this.mMorphView.mTouchRect;
            if (rect == null || !rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                PowerManager powerManager = NotificationEffect.this.mPm;
                if (powerManager != null && !powerManager.isInteractive() && NotificationEffect.isDoubleTapToWakeUpEnabled(NotificationEffect.this.getContext())) {
                    Slog.i(NotificationEffect.this.TAG, "wakeUpDevice by double touch to root view");
                    return true;
                }
            } else {
                NotificationEffect notificationEffect = NotificationEffect.this;
                if (notificationEffect.mIsActionEnable) {
                    notificationEffect.launchPendingIntent();
                    return true;
                }
            }
            return super.onDoubleTap(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return !NotificationEffect.this.mPm.isInteractive() || NotificationEffect.this.mTouchableRec.contains((int) motionEvent.getX(), (int) motionEvent.getY());
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            IEdgeLightingWindowCallback iEdgeLightingWindowCallback;
            KeyguardManager keyguardManager;
            PowerManager powerManager = NotificationEffect.this.mPm;
            if (powerManager != null && powerManager.isInteractive() && NotificationEffect.this.mIsActionEnable) {
                float abs = Math.abs(motionEvent2.getX() - motionEvent.getX());
                float y = motionEvent2.getY() - motionEvent.getY();
                if (y > 150.0f && abs < 400.0f) {
                    Slog.i(NotificationEffect.this.TAG, "Fling down ");
                    Slog.i(NotificationEffect.this.TAG, "*****mSwipeDownDisabledForEdgeLightingPlus = " + NotificationEffect.this.mIsSwipeDownDisabledForEdgeLightingPlus);
                    NotificationEffect notificationEffect = NotificationEffect.this;
                    if (!notificationEffect.mIsSwipeDownDisabledForEdgeLightingPlus && (keyguardManager = notificationEffect.mKgm) != null && !keyguardManager.isKeyguardLocked() && !Utils.isLargeCoverFlipFolded()) {
                        NotificationEffect notificationEffect2 = NotificationEffect.this;
                        if (notificationEffect2.isTouchable()) {
                            Rect rect = notificationEffect2.mMorphView.mTouchRect;
                            rect.bottom = rect.top;
                        }
                        if (!Utils.MODEL_NAME.contains("SM-F90") ? false : Utils.isFolded()) {
                            NotificationEffect.this.launchPendingIntent();
                        } else {
                            final NotificationEffect notificationEffect3 = NotificationEffect.this;
                            notificationEffect3.getClass();
                            AnimatorSet animatorSet = new AnimatorSet();
                            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(notificationEffect3.mMorphView, "translationY", -(r11.getHeight() / 2));
                            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(notificationEffect3.mMorphView, "translationY", r12.getHeight() / 2);
                            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(notificationEffect3.mMorphView, "alpha", 1.0f);
                            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(notificationEffect3.mMorphView, "alpha", 0.0f);
                            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat);
                            ofFloat.setDuration(200L);
                            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat);
                            ofFloat.setDuration(200L);
                            ofFloat3.setDuration(100L);
                            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat2);
                            ofFloat2.setDuration(300L);
                            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat4);
                            ofFloat4.setDuration(300L);
                            animatorSet.playTogether(ofFloat2, ofFloat4);
                            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect.5
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    super.onAnimationEnd(animator);
                                    NotificationEffect.m1537$$Nest$mlaunchPopupWindow(NotificationEffect.this, true);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    super.onAnimationStart(animator);
                                }
                            });
                            animatorSet.start();
                        }
                        return true;
                    }
                } else {
                    if (abs > 150.0f && y < 400.0f) {
                        Slog.i(NotificationEffect.this.TAG, "Fling Side ");
                        boolean z = motionEvent2.getX() - motionEvent.getX() < 0.0f;
                        NotificationEffect notificationEffect4 = NotificationEffect.this;
                        if (notificationEffect4.isTouchable()) {
                            Rect rect2 = notificationEffect4.mMorphView.mTouchRect;
                            rect2.bottom = rect2.top;
                        }
                        NotificationEffect notificationEffect5 = NotificationEffect.this;
                        int left = z ? -notificationEffect5.mMorphView.getRight() : notificationEffect5.mScreenWidth - notificationEffect5.mMorphView.getLeft();
                        MorphView morphView = notificationEffect5.mMorphView;
                        morphView.getClass();
                        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(morphView, "translationX", left);
                        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(morphView, "alpha", 0.5f);
                        AnimatorSet animatorSet2 = new AnimatorSet();
                        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.17f, 0.67f, 0.52f, 1.47f, ofFloat5);
                        animatorSet2.setDuration(600L);
                        animatorSet2.playTogether(ofFloat5, ofFloat6);
                        animatorSet2.start();
                        EdgeLightingDialog.C13144 c13144 = notificationEffect5.mEdgeListener;
                        if (c13144 != null && (iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback) != null) {
                            iEdgeLightingWindowCallback.onSwipeToastInWindow();
                        }
                        return true;
                    }
                    if (y < -70.0f) {
                        Slog.d(NotificationEffect.this.TAG, "Fling up ");
                        NotificationEffect notificationEffect6 = NotificationEffect.this;
                        if (notificationEffect6.isTouchable()) {
                            Rect rect3 = notificationEffect6.mMorphView.mTouchRect;
                            rect3.bottom = rect3.top;
                        }
                        NotificationEffect.this.onFlickUpAnimation();
                    } else {
                        String str = NotificationEffect.this.TAG;
                        StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("onFling dx : ", abs, " dy : ", y, "velocityX ");
                        m88m.append(f);
                        m88m.append(" velocityY : ");
                        m88m.append(f2);
                        Slog.i(str, m88m.toString());
                    }
                }
            }
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            Rect rect;
            KeyguardManager keyguardManager;
            Slog.d(NotificationEffect.this.TAG, "onLongPress");
            PowerManager powerManager = NotificationEffect.this.mPm;
            if (powerManager == null || !powerManager.isInteractive() || (rect = NotificationEffect.this.mMorphView.mTouchRect) == null || !rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return;
            }
            NotificationEffect notificationEffect = NotificationEffect.this;
            if (!notificationEffect.mIsActionEnable || (keyguardManager = notificationEffect.mKgm) == null || keyguardManager.isKeyguardLocked() || Utils.isLargeCoverFlipFolded()) {
                return;
            }
            NotificationEffect.m1537$$Nest$mlaunchPopupWindow(NotificationEffect.this, false);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            Slog.i(NotificationEffect.this.TAG, "onSingleTapConfirmed : ");
            Slog.i(NotificationEffect.this.TAG, "*****mSingleTapDisabledForEdgeLightingPlus = " + NotificationEffect.this.mIsSingleTapDisabledForEdgeLightingPlus);
            NotificationEffect notificationEffect = NotificationEffect.this;
            PowerManager powerManager = notificationEffect.mPm;
            if (powerManager != null && !notificationEffect.mIsSingleTapDisabledForEdgeLightingPlus) {
                if (powerManager.isInteractive()) {
                    Rect rect = NotificationEffect.this.mMorphView.mTouchRect;
                    if (rect != null && rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        NotificationEffect notificationEffect2 = NotificationEffect.this;
                        if (notificationEffect2.mIsActionEnable) {
                            notificationEffect2.launchPendingIntent();
                        }
                    }
                } else {
                    Slog.i(NotificationEffect.this.TAG, " Ignore single Tap in screen off");
                }
            }
            return super.onSingleTapConfirmed(motionEvent);
        }

        private GestureListener() {
        }
    }

    public static void $r8$lambda$8SbRsnXG3nQq76BB883ytyHG1_Y(NotificationEffect notificationEffect, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        notificationEffect.getClass();
        Region region = internalInsetsInfo.touchableRegion;
        if (region == null) {
            Slog.i(notificationEffect.TAG, "onComputeInternalInsets touchable region is null");
            return;
        }
        if (!notificationEffect.mPm.isInteractive()) {
            Context context = notificationEffect.getContext();
            String str = Utils.MODEL_NAME;
            if (Settings.System.getInt(context.getContentResolver(), "aod_show_state", 0) != 1) {
                notificationEffect.mTouchableRec.set(notificationEffect.getLeft(), notificationEffect.getTop(), notificationEffect.getRight(), notificationEffect.getBottom());
                region.union(notificationEffect.mTouchableRec);
                internalInsetsInfo.setTouchableInsets(3);
            }
        }
        if (notificationEffect.getAlpha() > 0.0f) {
            notificationEffect.mTouchableRec.set(notificationEffect.mMorphView.mTouchRect);
        } else {
            region.set(0, 0, 0, 0);
        }
        region.union(notificationEffect.mTouchableRec);
        internalInsetsInfo.setTouchableInsets(3);
    }

    /* renamed from: -$$Nest$mfreeformLaunchBounds, reason: not valid java name */
    public static Rect m1536$$Nest$mfreeformLaunchBounds(NotificationEffect notificationEffect) {
        int i = notificationEffect.getContext().getResources().getConfiguration().orientation;
        if (Feature.FEATURE_IS_TABLET_DEVICE) {
            notificationEffect.FREEFORM_WIDTH_RATIO = 0.5f;
            notificationEffect.FREEFORM_HEIGHT_RATIO = 0.5f;
        } else if (Feature.FEATURE_IS_FOLDABLE) {
            notificationEffect.FREEFORM_WIDTH_RATIO = Utils.isFolded() ? 0.975f : 0.45f;
            notificationEffect.FREEFORM_HEIGHT_RATIO = 0.45f;
        } else if (notificationEffect.mScreenWidth < notificationEffect.mScreenHeight) {
            notificationEffect.FREEFORM_WIDTH_RATIO = 0.85f;
            notificationEffect.FREEFORM_HEIGHT_RATIO = 0.5f;
        } else {
            notificationEffect.FREEFORM_WIDTH_RATIO = 0.5f;
            notificationEffect.FREEFORM_HEIGHT_RATIO = 0.85f;
        }
        int i2 = notificationEffect.mScreenWidth;
        int i3 = (int) (i2 * notificationEffect.FREEFORM_WIDTH_RATIO);
        int i4 = notificationEffect.mScreenHeight;
        int i5 = (int) (i4 * notificationEffect.FREEFORM_HEIGHT_RATIO);
        int i6 = (i2 - i3) / 2;
        int i7 = (i4 - i5) / 2;
        return new Rect(i6, i7, i3 + i6, i5 + i7);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mlaunchPopupWindow, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1537$$Nest$mlaunchPopupWindow(NotificationEffect notificationEffect, boolean z) {
        boolean z2;
        EdgeLightingDialog edgeLightingDialog;
        if (!z) {
            notificationEffect.getClass();
            if (Build.VERSION.SEM_PLATFORM_INT >= 120000) {
                if (notificationEffect.mEdgeListener == null) {
                    return;
                }
                PendingIntent pendingIntent = notificationEffect.mPendingIntent;
                if (pendingIntent == null) {
                    Slog.i(notificationEffect.TAG, "isActivity: false : pendingintent is null ");
                } else {
                    if (pendingIntent.isActivity()) {
                        z2 = true;
                        edgeLightingDialog = EdgeLightingDialog.this;
                        if (edgeLightingDialog.mWindowCallback == null) {
                            edgeLightingDialog.getWindow().addFlags(16);
                            edgeLightingDialog.mWindowCallback.onFlingDownInWindow(z2);
                            return;
                        }
                        return;
                    }
                    Slog.i(notificationEffect.TAG, "isActivity: false " + notificationEffect.mPendingIntent.getCreatorPackage());
                }
                z2 = false;
                edgeLightingDialog = EdgeLightingDialog.this;
                if (edgeLightingDialog.mWindowCallback == null) {
                }
            }
        }
        notificationEffect.resetScreenSize();
        new Runnable() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                    Rect m1536$$Nest$mfreeformLaunchBounds = NotificationEffect.m1536$$Nest$mfreeformLaunchBounds(NotificationEffect.this);
                    Slog.i(NotificationEffect.this.TAG, "launchPopupWindow: bounds=" + m1536$$Nest$mfreeformLaunchBounds);
                    NotificationEffect notificationEffect2 = NotificationEffect.this;
                    notificationEffect2.mIsActivity = false;
                    PendingIntent pendingIntent2 = notificationEffect2.mPendingIntent;
                    if (pendingIntent2 != null) {
                        if (pendingIntent2.isActivity()) {
                            NotificationEffect.this.mIsActivity = true;
                        } else {
                            Slog.i(NotificationEffect.this.TAG, "isActivity: false " + NotificationEffect.this.mPendingIntent.getCreatorPackage());
                        }
                        if (!m1536$$Nest$mfreeformLaunchBounds.isEmpty()) {
                            makeBasic.setLaunchBounds(m1536$$Nest$mfreeformLaunchBounds);
                            NotificationEffect.this.mPendingIntent.send(null, 0, null, null, null, null, makeBasic.toBundle());
                        }
                    }
                    NotificationEffect notificationEffect3 = NotificationEffect.this;
                    EdgeLightingDialog.C13144 c13144 = notificationEffect3.mEdgeListener;
                    if (c13144 != null) {
                        boolean z3 = notificationEffect3.mIsActivity;
                        IEdgeLightingWindowCallback iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback;
                        if (iEdgeLightingWindowCallback != null) {
                            iEdgeLightingWindowCallback.onFling(true, z3);
                        }
                    }
                } catch (PendingIntent.CanceledException e) {
                    NotificationEffect notificationEffect4 = NotificationEffect.this;
                    if (notificationEffect4.mPendingIntent != null) {
                        Slog.e(notificationEffect4.TAG, " pending intent from " + NotificationEffect.this.mPendingIntent.getCreatorPackage() + " is canceled " + e.getMessage());
                    }
                }
            }
        }.run();
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.edgelighting.effect.container.NotificationEffect$4] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.edgelighting.effect.container.NotificationEffect$$ExternalSyntheticLambda1] */
    public NotificationEffect(Context context) {
        super(context);
        this.TAG = "NotificationEffect";
        final int i = 1;
        this.mIsShowMorphView = true;
        this.mUsingBlackBG = false;
        this.mConvertColor = -15750429;
        this.mInfiniteLighting = false;
        this.mIsActionEnable = true;
        this.mIsHideBriefPopupForEdgeLightingPlus = false;
        this.mIsSingleTapDisabledForEdgeLightingPlus = false;
        this.mIsSwipeDownDisabledForEdgeLightingPlus = false;
        this.mPopupListener = new C13151();
        this.FREEFORM_WIDTH_RATIO = 0.67f;
        this.FREEFORM_HEIGHT_RATIO = 0.5f;
        this.mHandler = new Handler() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect.4
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                int i2 = message.what;
                if (i2 != 1) {
                    if (i2 == 2) {
                        NotificationEffect.this.mMorphView.hide();
                        return;
                    }
                    if (i2 != 3) {
                        if (i2 != 4) {
                            return;
                        }
                        NotificationEffect.this.requestHideEffectView();
                        return;
                    } else {
                        NotificationEffect.this.setVisibility(4);
                        EdgeLightingDialog.C13144 c13144 = NotificationEffect.this.mEdgeListener;
                        if (c13144 != null) {
                            c13144.onFinishAnimation();
                            return;
                        }
                        return;
                    }
                }
                NotificationEffect.this.mMorphView.show();
                NotificationEffect notificationEffect = NotificationEffect.this;
                if (notificationEffect.mTouchableRec != null) {
                    Slog.i(notificationEffect.TAG, "EdgeLightingTouchableRect=" + NotificationEffect.this.mTouchableRec.left + "," + NotificationEffect.this.mTouchableRec.top + "," + NotificationEffect.this.mTouchableRec.right + "," + NotificationEffect.this.mTouchableRec.bottom);
                }
            }
        };
        this.mTouchableRec = new Rect();
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener(this) { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect$$ExternalSyntheticLambda1
            public final /* synthetic */ NotificationEffect f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                switch (i) {
                }
                NotificationEffect.$r8$lambda$8SbRsnXG3nQq76BB883ytyHG1_Y(this.f$0, internalInsetsInfo);
            }
        };
        init();
    }

    public static boolean isDoubleTapToWakeUpEnabled(Context context) {
        return (InputManager.getInstance() != null && (InputManager.getInstance().semCheckInputFeature() & 1) == 1) && Settings.System.getInt(context.getContentResolver(), "double_tab_to_wake_up", 0) == 1;
    }

    public final void addTouchInsector() {
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        getViewTreeObserver().addOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
    }

    public void dismiss() {
        MorphView morphView = this.mMorphView;
        if (morphView != null) {
            morphView.hide();
        }
        if (this.mAODManager != null) {
            Slog.i(this.TAG, " remove edge  tsp  rect ");
            this.mAODManager.updateAODTspRect(-1, -1, -1, -1, "brief_popup");
        }
        unregisterAODReceiver();
    }

    public final void getDensityScaledRect(Rect rect) {
        float f;
        Resources resources = getContext().getResources();
        if (resources != null) {
            float min = Math.min(resources.getConfiguration().windowConfiguration.getBounds().width(), resources.getConfiguration().windowConfiguration.getBounds().height());
            Point point = new Point();
            SemWindowManager.getInstance().getInitialDisplaySize(point);
            float f2 = point.x;
            f = f2 >= min ? f2 / min : 1.0f;
            Slog.d(this.TAG, "pWidth - " + f2 + " width : " + min);
        } else {
            f = 1.0f;
        }
        if (f != 1.0f) {
            rect.left = (int) (rect.left * f);
            rect.top = (int) (rect.top * f);
            rect.right = (int) (rect.right * f);
            rect.bottom = (int) (rect.bottom * f);
        }
    }

    public final Rect getToastRectCalculated() {
        Rect rect = new Rect();
        int rotation = getContext().getDisplay().getRotation();
        if (Utils.isLargeCoverFlipFolded()) {
            if (rotation == 2) {
                Rect rect2 = this.mMorphView.mTouchRect;
                rect.left = rect2.left;
                int i = this.mScreenHeight;
                rect.top = i - rect2.bottom;
                rect.right = rect2.right;
                rect.bottom = i - rect2.top;
            } else {
                Rect rect3 = this.mMorphView.mTouchRect;
                rect.left = rect3.left;
                rect.top = rect3.top;
                rect.right = rect3.right;
                rect.bottom = rect3.bottom;
            }
            return rect;
        }
        if (!(rotation == 1 || rotation == 3)) {
            Rect rect4 = this.mMorphView.mTouchRect;
            rect.left = rect4.left;
            rect.top = rect4.top;
            rect.right = rect4.right;
            rect.bottom = rect4.bottom;
            getDensityScaledRect(rect);
            return rect;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        if (rotation == 1) {
            int i2 = displayMetrics.heightPixels;
            Rect rect5 = this.mMorphView.mTouchRect;
            rect.left = i2 - rect5.bottom;
            rect.top = rect5.left;
            rect.right = i2 - rect5.top;
            rect.bottom = rect5.right;
        } else if (rotation == 3) {
            Rect rect6 = this.mMorphView.mTouchRect;
            rect.left = rect6.top;
            int i3 = displayMetrics.widthPixels;
            rect.top = i3 - rect6.right;
            rect.right = rect6.bottom;
            rect.bottom = i3 - rect6.left;
        }
        getDensityScaledRect(rect);
        return rect;
    }

    public void init() {
        resetScreenSize();
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.edge_notification_container, this);
        this.mPm = (PowerManager) getContext().getSystemService("power");
        this.mKgm = (KeyguardManager) getContext().getSystemService("keyguard");
        this.mMorphView = (MorphView) findViewById(R.id.noti_morph_view);
        this.mNotificationContainer = (RelativeLayout) findViewById(R.id.notification_container);
        this.mGestureDetector = new GestureDetector(getContext(), new GestureListener(this, 0));
        MorphView morphView = this.mMorphView;
        morphView.mScreenWidth = this.mScreenWidth;
        morphView.mPopupListener = this.mPopupListener;
        this.dreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        getResources().getDimensionPixelSize(R.dimen.toast_root_elevation);
    }

    public final boolean isTouchable() {
        return this.mIsShowMorphView && !this.mIsHideBriefPopupForEdgeLightingPlus && this.mPm.isInteractive();
    }

    public final void launchPendingIntent() {
        IEdgeLightingWindowCallback iEdgeLightingWindowCallback;
        if (!this.mPm.isInteractive()) {
            this.mPm.semWakeUp(SystemClock.uptimeMillis(), QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, "[Brief] Wakeup and launchPendingIntent");
        }
        boolean z = false;
        if (Utils.isLargeCoverFlipFolded()) {
            this.mPm.userActivity(SystemClock.uptimeMillis(), 2, 0);
            EdgeLightingDialog.C13144 c13144 = this.mEdgeListener;
            if (c13144 != null) {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = EdgeLightingDialog.this.mWindowCallback;
                if (iEdgeLightingWindowCallback2 != null) {
                    iEdgeLightingWindowCallback2.onClickToastInWindow();
                    return;
                }
                return;
            }
        }
        if (this.mPendingIntent != null) {
            if (!this.mPm.isInteractive()) {
                KeyguardManager keyguardManager = this.mKgm;
                if (keyguardManager != null && (keyguardManager.isDeviceLocked() || this.mKgm.semIsKeyguardShowingAndNotOccluded())) {
                    this.mPm.semWakeUp(SystemClock.uptimeMillis(), QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, "[Brief] Wakeup and send intent after unlock");
                } else {
                    if (mBlockNotiTouch_for_NA) {
                        PowerManager powerManager = this.mPm;
                        if (powerManager == null || powerManager.isInteractive() || !isDoubleTapToWakeUpEnabled(getContext())) {
                            return;
                        }
                        this.mPm.semWakeUp(SystemClock.uptimeMillis(), QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, "[Brief] Wakeup only");
                        return;
                    }
                    this.mPm.semWakeUp(SystemClock.uptimeMillis(), QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, "[Brief] Wakeup and send intent");
                }
            }
            if (this.mKgm != null) {
                final Intent intent = new Intent();
                if (!this.mKgm.isKeyguardSecure()) {
                    if (mBlockNotiTouch_for_NA) {
                        intent.putExtra("dismissIfInsecure", false);
                    } else {
                        intent.putExtra("dismissIfInsecure", true);
                    }
                }
                String str = this.mNotificationKey;
                if (str != null) {
                    intent.putExtra("notificationKey", str);
                }
                intent.putExtra("ignoreKeyguardState", true);
                try {
                    if (this.mPm.isInteractive() && this.dreamManager.isDreaming()) {
                        this.dreamManager.awaken();
                        z = true;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (z) {
                    postDelayed(new Runnable() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationEffect notificationEffect = NotificationEffect.this;
                            notificationEffect.mKgm.semSetPendingIntentAfterUnlock(notificationEffect.mPendingIntent, intent);
                        }
                    }, 50L);
                } else {
                    this.mKgm.semSetPendingIntentAfterUnlock(this.mPendingIntent, intent);
                }
            }
            EdgeLightingDialog.C13144 c131442 = this.mEdgeListener;
            if (c131442 == null || (iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback) == null) {
                return;
            }
            iEdgeLightingWindowCallback.onClickToastInWindow();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        String str = this.TAG;
        StringBuilder sb = new StringBuilder(" onAttached Window add Touch Insector : mIsShowMorphView=");
        sb.append(this.mIsShowMorphView);
        sb.append(", screen_on=");
        sb.append(this.mPm.isInteractive());
        sb.append(", aod_state=");
        Context context = getContext();
        String str2 = Utils.MODEL_NAME;
        sb.append(Settings.System.getInt(context.getContentResolver(), "aod_show_state", 0));
        Slog.i(str, sb.toString());
        if (isTouchable()) {
            addTouchInsector();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        String str = this.TAG;
        StringBuilder sb = new StringBuilder(" onDetachedFromWindow Window remove Touch Insector : ");
        sb.append(this.mIsShowMorphView);
        sb.append(", screen_on=");
        sb.append(this.mPm.isInteractive());
        sb.append(", aod_state=");
        Context context = getContext();
        String str2 = Utils.MODEL_NAME;
        sb.append(Settings.System.getInt(context.getContentResolver(), "aod_show_state", 0));
        Slog.i(str, sb.toString());
        if (isTouchable()) {
            getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        }
    }

    public void onFlickUpAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mMorphView, "translationY", -(r1.getHeight() / 2));
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mMorphView, "translationY", r3.getHeight() / 2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mMorphView, "alpha", 1.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mMorphView, "alpha", 0.0f);
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat);
        ofFloat.setDuration(200L);
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat);
        ofFloat.setDuration(200L);
        ofFloat3.setDuration(100L);
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat2);
        ofFloat2.setDuration(300L);
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.4f, 1.0f, ofFloat4);
        ofFloat4.setDuration(300L);
        animatorSet.playSequentially(ofFloat2, ofFloat3, ofFloat);
        animatorSet.playTogether(ofFloat, ofFloat4);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback;
                super.onAnimationEnd(animator);
                EdgeLightingDialog.C13144 c13144 = NotificationEffect.this.mEdgeListener;
                if (c13144 == null || (iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback) == null) {
                    return;
                }
                iEdgeLightingWindowCallback.onFling(false, false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
            }
        });
        animatorSet.start();
    }

    @Override // android.view.View
    public final void onSizeChanged(final int i, final int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i3 == 0) {
            return;
        }
        resetScreenSize();
        updateEffectLocation();
        if (!this.mIsShowMorphView || this.mIsHideBriefPopupForEdgeLightingPlus) {
            return;
        }
        String str = this.TAG;
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m(" onSizeChanged w : ", i, " h : ", i2, " oldW : ");
        m45m.append(i3);
        m45m.append(" oldH : ");
        m45m.append(i4);
        Slog.d(str, m45m.toString());
        this.mMorphView.disappear();
        postDelayed(new Runnable(i, i2) { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect$$ExternalSyntheticLambda2
            public final /* synthetic */ int f$1;

            @Override // java.lang.Runnable
            public final void run() {
                NotificationEffect notificationEffect = NotificationEffect.this;
                int i5 = this.f$1;
                final MorphView morphView = notificationEffect.mMorphView;
                morphView.mScreenWidth = i5;
                morphView.initialize();
                morphView.postDelayed(new Runnable() { // from class: com.android.systemui.edgelighting.effect.view.MorphView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MorphView morphView2 = MorphView.this;
                        int i6 = MorphView.$r8$clinit;
                        morphView2.show();
                    }
                }, 130L);
            }
        }, 200L);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (Utils.isLargeCoverFlipFolded()) {
            this.mPm.userActivity(SystemClock.uptimeMillis(), 2, 0);
        }
        if (motionEvent.getDevice() != null && "sec_e-pen".equals(motionEvent.getDevice().getName()) && Settings.System.getInt(getContext().getContentResolver(), "aod_show_state", 0) == 1 && motionEvent.getAction() == 0) {
            launchPendingIntent();
            return false;
        }
        GestureDetector gestureDetector = this.mGestureDetector;
        return gestureDetector != null ? gestureDetector.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
    }

    public void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        StringBuffer stringBuffer;
        boolean z;
        StringBuffer stringBuffer2;
        int i;
        int i2;
        EdgeLightingDialog.C13144 c13144;
        IEdgeLightingWindowCallback iEdgeLightingWindowCallback;
        String str;
        int i3;
        int i4;
        StringBuffer stringBuffer3 = new StringBuffer("setEdgeEffectInfo:  dur=");
        stringBuffer3.append(edgeEffectInfo.mLightingDuration);
        this.mInfiniteLighting = edgeEffectInfo.mInfiniteLighting;
        String[] strArr = edgeEffectInfo.mText;
        Drawable drawable = edgeEffectInfo.mAppIcon;
        boolean z2 = edgeEffectInfo.mIsSupportAppIcon;
        boolean z3 = edgeEffectInfo.mShouldShowAppIcon;
        PendingIntent pendingIntent = edgeEffectInfo.mPendingIntent;
        if (strArr == null || strArr[0].isEmpty()) {
            stringBuffer = stringBuffer3;
            z = true;
            this.mIsShowMorphView = false;
        } else {
            MorphView morphView = this.mMorphView;
            boolean isInteractive = this.mPm.isInteractive();
            morphView.getClass();
            String str2 = strArr[0];
            if (str2 != null && !str2.isEmpty()) {
                morphView.mMainText.setText(strArr[0]);
            }
            String str3 = strArr[1];
            if (str3 == null || str3.isEmpty()) {
                stringBuffer = stringBuffer3;
                if (!isInteractive) {
                    i = 0;
                } else if (VerificationCodeUtils.isVerificationCode(morphView.getContext(), strArr[0])) {
                    Log.i("MorphView", "Verification code start : " + VerificationCodeUtils.code_startIndex + " , end " + VerificationCodeUtils.code_endIndex + " code length : " + VerificationCodeUtils.getVerifyCode().length());
                    if (VerificationCodeUtils.code_startIndex > 15) {
                        StringBuilder sb = new StringBuilder();
                        i = 0;
                        String str4 = strArr[0];
                        int i5 = VerificationCodeUtils.code_startIndex;
                        sb.append(str4.substring(i5 - 15, i5));
                        sb.append("...");
                        strArr[0] = sb.toString();
                    } else {
                        i = 0;
                        strArr[0] = strArr[0].substring(0, VerificationCodeUtils.code_startIndex) + "...";
                    }
                    morphView.mMainText.setText(strArr[i]);
                    morphView.mCodeText.setVisibility(i);
                    SpannableString spannableString = new SpannableString(VerificationCodeUtils.getVerifyCode());
                    spannableString.setSpan(new UnderlineSpan(), i, spannableString.length(), i);
                    morphView.mCodeText.setText(spannableString);
                    i2 = 1;
                } else {
                    i = 0;
                }
                morphView.mCodeText.setVisibility(8);
                i2 = i;
            } else {
                if (isInteractive) {
                    stringBuffer = stringBuffer3;
                    if (VerificationCodeUtils.isVerificationCode(morphView.getContext(), strArr[1])) {
                        Log.i("MorphView", "Verification code start : " + VerificationCodeUtils.code_startIndex + " , end " + VerificationCodeUtils.code_endIndex + " code length : " + VerificationCodeUtils.getVerifyCode().length());
                        int i6 = VerificationCodeUtils.code_endIndex;
                        String str5 = VerificationCodeUtils.Verify_Code;
                        int length = i6 - (str5 == null ? 0 : str5.length());
                        if (length > 15) {
                            strArr[1] = "..." + strArr[1].substring(length - 15, length);
                            i3 = 0;
                        } else {
                            i3 = 0;
                            strArr[1] = strArr[1].substring(0, length);
                        }
                        morphView.mCodeText.setVisibility(i3);
                        SpannableString spannableString2 = new SpannableString(VerificationCodeUtils.getVerifyCode());
                        spannableString2.setSpan(new UnderlineSpan(), i3, spannableString2.length(), i3);
                        morphView.mCodeText.setText(spannableString2);
                        i4 = 1;
                        morphView.mSubText.setVisibility(i3);
                        morphView.mSubText.setText(strArr[1]);
                        i = i3;
                        i2 = i4;
                    }
                } else {
                    stringBuffer = stringBuffer3;
                }
                i3 = 0;
                morphView.mCodeText.setVisibility(8);
                i4 = 0;
                morphView.mSubText.setVisibility(i3);
                morphView.mSubText.setText(strArr[1]);
                i = i3;
                i2 = i4;
            }
            String str6 = strArr[i];
            if (str6 != null && !str6.isEmpty() && ((str = strArr[1]) == null || str.isEmpty())) {
                morphView.mSubText.setVisibility(8);
                morphView.mMainText.setMaxWidth(Integer.MAX_VALUE);
            }
            if (i2 != 0 && (c13144 = this.mEdgeListener) != null && (iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback) != null) {
                iEdgeLightingWindowCallback.onExtendLightingDuration();
            }
            z = true;
            this.mIsShowMorphView = true;
        }
        if (drawable != null) {
            this.mIsShowMorphView = z;
            MorphView morphView2 = this.mMorphView;
            morphView2.mIsSupportAppIcon = z2;
            morphView2.mIsShowAppIcon = z3;
            if (z3) {
                morphView2.mNotiIconBg.setBackground(morphView2.getResources().getDrawable(R.drawable.squircle));
            } else {
                morphView2.mNotiIconBg.setBackground(morphView2.getResources().getDrawable(R.drawable.noti_icon_bg));
            }
            if (z2) {
                morphView2.mNotiIcon.setVisibility(0);
                morphView2.mSmallIcon.setVisibility(8);
                morphView2.mNotiIcon.setImageDrawable(drawable);
            } else {
                morphView2.mNotiIcon.setVisibility(8);
                morphView2.mSmallIcon.setVisibility(0);
                morphView2.mSmallIcon.setImageDrawable(drawable);
            }
        }
        if (pendingIntent != null) {
            this.mPendingIntent = pendingIntent;
        }
        if (!this.mIsShowMorphView || this.mIsHideBriefPopupForEdgeLightingPlus) {
            stringBuffer2 = stringBuffer;
        } else {
            try {
                if (!this.mPm.isInteractive() || !edgeEffectInfo.mHasActionButton || this.dreamManager.isDreaming()) {
                    this.mMorphView.showExpandButton(false);
                } else if (Utils.isLargeCoverFlipFolded()) {
                    this.mMorphView.showExpandButton(false);
                } else {
                    this.mMorphView.showExpandButton(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.mMorphView.mIsGrayScaled = edgeEffectInfo.mIsGrayScaled;
            stringBuffer2 = stringBuffer;
            stringBuffer2.append(" hasActionButton=");
            stringBuffer2.append(edgeEffectInfo.mHasActionButton);
            stringBuffer2.append(" isGrayScaled= ");
            stringBuffer2.append(edgeEffectInfo.mIsGrayScaled);
        }
        setEffectColors(edgeEffectInfo.mEffectColors);
        stringBuffer2.append(" color=");
        stringBuffer2.append(Integer.toHexString(this.mConvertColor));
        this.mMorphView.mToastFullColor = false;
        boolean z4 = this.mUsingBlackBG;
        boolean z5 = edgeEffectInfo.mIsBlackBG;
        if (z4 != z5) {
            this.mUsingBlackBG = z5;
        }
        if (this.mUsingBlackBG) {
            this.mIsShowMorphView = false;
        }
        String str7 = edgeEffectInfo.mNotificationKey;
        if (str7 != null) {
            this.mNotificationKey = str7;
        }
        this.mIsActionEnable = edgeEffectInfo.mEdgeLightingAction;
        stringBuffer2.append(" notificationKey=");
        stringBuffer2.append(edgeEffectInfo.mNotificationKey);
        stringBuffer2.append(" actionEnable= ");
        stringBuffer2.append(this.mIsActionEnable);
        Slog.i(this.TAG, stringBuffer2.toString());
        MorphView morphView3 = this.mMorphView;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationEffect notificationEffect = NotificationEffect.this;
                EdgeLightingDialog.C13144 c131442 = notificationEffect.mEdgeListener;
                if (c131442 != null) {
                    String str8 = notificationEffect.mNotificationKey;
                    EdgeLightingDialog edgeLightingDialog = EdgeLightingDialog.this;
                    IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = edgeLightingDialog.mWindowCallback;
                    if (iEdgeLightingWindowCallback2 != null) {
                        iEdgeLightingWindowCallback2.onClickExpandButton(str8);
                    }
                    EdgeLightingDialog.m1534$$Nest$mdismissInternal(edgeLightingDialog);
                }
                NotificationEffect notificationEffect2 = NotificationEffect.this;
                boolean z6 = NotificationEffect.mBlockNotiTouch_for_NA;
                notificationEffect2.requestHideEffectView();
                MorphView morphView4 = notificationEffect2.mMorphView;
                if (morphView4 != null) {
                    morphView4.disappear();
                }
                if (notificationEffect2.mAODManager != null) {
                    Slog.i(notificationEffect2.TAG, " remove edge  tsp  rect ");
                    notificationEffect2.mAODManager.updateAODTspRect(-1, -1, -1, -1, "brief_popup");
                }
                notificationEffect2.unregisterAODReceiver();
            }
        };
        ImageView imageView = morphView3.mExpandButton;
        if (imageView != null) {
            imageView.setOnClickListener(onClickListener);
        }
    }

    public void setEffectColors(int[] iArr) {
        if (iArr != null && iArr.length > 0) {
            this.mConvertColor = iArr[0] | EmergencyPhoneWidget.BG_COLOR;
        }
        float[] fArr = new float[3];
        Color.colorToHSV(this.mConvertColor, fArr);
        float f = fArr[1];
        if (f <= 0.1f && fArr[2] >= 0.9f) {
            this.mConvertColor = -1639683;
            Slog.i(this.TAG, " White color : H : " + fArr[0] + " S : " + fArr[1] + " B : " + fArr[2]);
        } else if (0.0f <= f && f <= 1.0f && fArr[2] <= 0.1f) {
            this.mConvertColor = -12696757;
            Slog.i(this.TAG, " Black color : H : " + fArr[0] + " S : " + fArr[1] + " B : " + fArr[2]);
        }
        MorphView morphView = this.mMorphView;
        int i = this.mConvertColor;
        if (morphView.mSmallIcon.getDrawable() != null) {
            Drawable background = morphView.mToastRootLayout.getBackground();
            if (background instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                if (morphView.mToastFullColor) {
                    gradientDrawable.setColor(i);
                    gradientDrawable.setStroke(0, i);
                }
            }
        }
        float[] fArr2 = new float[3];
        Color.colorToHSV(i, fArr2);
        int color = morphView.getContext().getColor(R.color.toast_default_text_color);
        double d = fArr2[1];
        if (d > 0.2d || fArr2[2] < 0.95d) {
            double d2 = fArr2[2];
            if (d2 <= 0.05d) {
                color = morphView.getContext().getColor(R.color.lighting_color_low_bound);
            } else {
                float f2 = fArr2[0];
                if (f2 <= 30.0f || (f2 >= 190.0f && f2 <= 360.0f)) {
                    color = ((d < 0.2d || d > 0.45d || d2 < 0.95d) && (d > 0.45d || d2 < 0.85d || d2 > 0.95d) && (d > 0.05d || d2 < 0.7d || d2 > 0.85d)) ? morphView.getContext().getColor(R.color.lighting_color_mid_bound) : morphView.getContext().getColor(R.color.lighting_color_high_bound);
                } else if (f2 >= 50.0f && f2 <= 145.0f) {
                    color = ((d < 0.2d || d2 < 0.95d) && (d2 < 0.85d || d2 > 0.95d) && (d > 0.2d || d2 < 0.7d || d2 > 0.85d)) ? morphView.getContext().getColor(R.color.lighting_color_mid_bound) : morphView.getContext().getColor(R.color.lighting_color_high_bound);
                } else if ((f2 >= 30.0f && f2 <= 50.0f) || (f2 >= 145.0f && f2 <= 190.0f)) {
                    color = ((d < 0.2d || d2 < 0.95d) && (d > 0.45d || d2 < 0.85d || d2 > 0.95d) && (d > 0.15d || d2 < 0.7d || d2 > 0.85d)) ? morphView.getContext().getColor(R.color.lighting_color_mid_bound) : morphView.getContext().getColor(R.color.lighting_color_high_bound);
                }
            }
        } else {
            color = morphView.getContext().getColor(R.color.lighting_color_top_bound);
        }
        if (morphView.mToastFullColor) {
            morphView.mMainText.setTextColor(color);
            morphView.mSubText.setTextColor(color);
        }
        Drawable drawable = morphView.mSmallIcon.getDrawable();
        Drawable background2 = morphView.mNotiIconBg.getBackground();
        if (!morphView.mIsGrayScaled) {
            morphView.mNotiIconBg.setBackground(null);
            return;
        }
        if (morphView.mIsSupportAppIcon) {
            background2.setColorFilter(0, PorterDuff.Mode.SRC_IN);
        } else {
            background2.setColorFilter(i, PorterDuff.Mode.SRC_IN);
        }
        if (drawable == null || morphView.mIsSupportAppIcon) {
            return;
        }
        if (color == 0) {
            color = -11767645;
        }
        TextView textView = morphView.mMainText;
        if (textView == null || TextUtils.isEmpty(textView.getText())) {
            return;
        }
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public void show() {
        int i = 0;
        setVisibility(0);
        this.mMorphView.initialize();
        if (this.mIsActionEnable && !this.mPm.isInteractive()) {
            Rect toastRectCalculated = getToastRectCalculated();
            AODManager aODManager = AODManager.getInstance(getContext());
            this.mAODManager = aODManager;
            aODManager.updateAODTspRect(toastRectCalculated.width(), toastRectCalculated.height(), toastRectCalculated.left, toastRectCalculated.top, "brief_popup");
            Slog.d(this.TAG, "updateAODTspRect - " + toastRectCalculated.left + " : " + toastRectCalculated.top + " : " + toastRectCalculated.width() + " : " + toastRectCalculated.height());
            if (this.mAODTspUpdateReceiver == null) {
                this.mAODTspUpdateReceiver = new AODBroadcastReceiver(this, i);
                getContext().registerReceiver(this.mAODTspUpdateReceiver, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE"), "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER", null);
            }
        }
        setAlpha(1.0f);
        if (isTouchable()) {
            addTouchInsector();
        }
        if (!this.mIsShowMorphView || this.mIsHideBriefPopupForEdgeLightingPlus) {
            return;
        }
        sendEmptyMessageDelayed(1, 400L);
    }

    public final void unregisterAODReceiver() {
        if (this.mAODTspUpdateReceiver != null) {
            try {
                getContext().unregisterReceiver(this.mAODTspUpdateReceiver);
            } catch (IllegalArgumentException unused) {
                Slog.e(this.TAG, "unregisterAODReceiver: unable to unregister Receiver=" + this.mAODTspUpdateReceiver);
            }
            this.mAODTspUpdateReceiver = null;
        }
    }

    public void updateText(boolean z) {
        if (z) {
            show();
        }
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.edgelighting.effect.container.NotificationEffect$4] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.edgelighting.effect.container.NotificationEffect$$ExternalSyntheticLambda1] */
    public NotificationEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "NotificationEffect";
        this.mIsShowMorphView = true;
        final int i = 0;
        this.mUsingBlackBG = false;
        this.mConvertColor = -15750429;
        this.mInfiniteLighting = false;
        this.mIsActionEnable = true;
        this.mIsHideBriefPopupForEdgeLightingPlus = false;
        this.mIsSingleTapDisabledForEdgeLightingPlus = false;
        this.mIsSwipeDownDisabledForEdgeLightingPlus = false;
        this.mPopupListener = new C13151();
        this.FREEFORM_WIDTH_RATIO = 0.67f;
        this.FREEFORM_HEIGHT_RATIO = 0.5f;
        this.mHandler = new Handler() { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect.4
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                int i2 = message.what;
                if (i2 != 1) {
                    if (i2 == 2) {
                        NotificationEffect.this.mMorphView.hide();
                        return;
                    }
                    if (i2 != 3) {
                        if (i2 != 4) {
                            return;
                        }
                        NotificationEffect.this.requestHideEffectView();
                        return;
                    } else {
                        NotificationEffect.this.setVisibility(4);
                        EdgeLightingDialog.C13144 c13144 = NotificationEffect.this.mEdgeListener;
                        if (c13144 != null) {
                            c13144.onFinishAnimation();
                            return;
                        }
                        return;
                    }
                }
                NotificationEffect.this.mMorphView.show();
                NotificationEffect notificationEffect = NotificationEffect.this;
                if (notificationEffect.mTouchableRec != null) {
                    Slog.i(notificationEffect.TAG, "EdgeLightingTouchableRect=" + NotificationEffect.this.mTouchableRec.left + "," + NotificationEffect.this.mTouchableRec.top + "," + NotificationEffect.this.mTouchableRec.right + "," + NotificationEffect.this.mTouchableRec.bottom);
                }
            }
        };
        this.mTouchableRec = new Rect();
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener(this) { // from class: com.android.systemui.edgelighting.effect.container.NotificationEffect$$ExternalSyntheticLambda1
            public final /* synthetic */ NotificationEffect f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                switch (i) {
                }
                NotificationEffect.$r8$lambda$8SbRsnXG3nQq76BB883ytyHG1_Y(this.f$0, internalInsetsInfo);
            }
        };
        init();
    }

    public void setIsMultiResolutionSupoorted(boolean z) {
    }

    public void dismissToastPopup() {
    }

    public void finishToastPopupAnimation() {
    }

    public void requestHideEffectView() {
    }

    public void startToastPopupAnimation() {
    }

    public void update() {
    }

    public void updateEffectLocation() {
    }
}
