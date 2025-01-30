package com.android.systemui.media.taptotransfer.receiver;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaRoute2Info;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.InstanceId;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.R;
import com.android.systemui.common.p004ui.binder.TintedIconViewBinder;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.IconInfo;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils;
import com.android.systemui.media.taptotransfer.common.MediaTttUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController$removeViewFromWindow$1;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.TemporaryViewUiEventLogger;
import com.android.systemui.util.animation.AnimationUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.view.ViewUtil;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaTttChipControllerReceiver extends TemporaryViewDisplayController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long ICON_ALPHA_ANIM_DURATION;
    public final ValueAnimator bounceAnimator;
    public final CommandQueue commandQueue;
    public final MediaTttChipControllerReceiver$commandQueueCallbacks$1 commandQueueCallbacks;
    public final MediaTttChipControllerReceiver$displayListener$1 displayListener;
    public final Map instanceMap;
    public final Handler mainHandler;
    public final MediaTttFlags mediaTttFlags;
    public final MediaTttReceiverRippleController rippleController;
    public final TemporaryViewUiEventLogger temporaryViewUiEventLogger;
    public final MediaTttReceiverUiEventLogger uiEventLogger;
    public final ViewUtil viewUtil;
    public final WindowManager.LayoutParams windowLayoutParams;

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
        AnimationUtil.Companion.getClass();
        ICON_ALPHA_ANIM_DURATION = AnimationUtil.Companion.getFrames(5);
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$displayListener$1] */
    public MediaTttChipControllerReceiver(CommandQueue commandQueue, Context context, MediaTttReceiverLogger mediaTttReceiverLogger, WindowManager windowManager, DelayableExecutor delayableExecutor, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DumpManager dumpManager, PowerManager powerManager, Handler handler, MediaTttFlags mediaTttFlags, MediaTttReceiverUiEventLogger mediaTttReceiverUiEventLogger, ViewUtil viewUtil, WakeLock.Builder builder, SystemClock systemClock, MediaTttReceiverRippleController mediaTttReceiverRippleController, TemporaryViewUiEventLogger temporaryViewUiEventLogger) {
        super(context, mediaTttReceiverLogger, windowManager, delayableExecutor, accessibilityManager, configurationController, dumpManager, powerManager, R.layout.media_ttt_chip_receiver, builder, systemClock, temporaryViewUiEventLogger);
        this.commandQueue = commandQueue;
        this.mainHandler = handler;
        this.mediaTttFlags = mediaTttFlags;
        this.uiEventLogger = mediaTttReceiverUiEventLogger;
        this.viewUtil = viewUtil;
        this.rippleController = mediaTttReceiverRippleController;
        this.temporaryViewUiEventLogger = temporaryViewUiEventLogger;
        WindowManager.LayoutParams layoutParams = this.commonWindowLayoutParams;
        layoutParams.gravity = 81;
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        this.windowLayoutParams = layoutParams;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(2);
        ofFloat.setDuration(750L);
        this.bounceAnimator = ofFloat;
        this.commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void updateMediaTapToTransferReceiverDisplay(int i, final MediaRoute2Info mediaRoute2Info, Icon icon, final CharSequence charSequence) {
                String str;
                int i2;
                int i3 = MediaTttChipControllerReceiver.$r8$clinit;
                final MediaTttChipControllerReceiver mediaTttChipControllerReceiver = MediaTttChipControllerReceiver.this;
                mediaTttChipControllerReceiver.getClass();
                ChipStateReceiver.Companion.getClass();
                try {
                } catch (NoSuchElementException e) {
                    Log.e("ChipStateReceiver", "Could not find requested state " + i, e);
                    r7 = null;
                }
                for (ChipStateReceiver chipStateReceiver : ChipStateReceiver.values()) {
                    if (chipStateReceiver.getStateInt() == i) {
                        if (chipStateReceiver == null || (str = chipStateReceiver.name()) == null) {
                            str = "Invalid";
                        }
                        MediaTttReceiverLogger mediaTttReceiverLogger2 = (MediaTttReceiverLogger) mediaTttChipControllerReceiver.logger;
                        String id = mediaRoute2Info.getId();
                        String clientPackageName = mediaRoute2Info.getClientPackageName();
                        mediaTttReceiverLogger2.getClass();
                        MediaTttLoggerUtils.INSTANCE.getClass();
                        LogBuffer logBuffer = mediaTttReceiverLogger2.buffer;
                        MediaTttLoggerUtils.logStateChange(logBuffer, "MediaTttReceiver", str, id, clientPackageName);
                        if (chipStateReceiver == null) {
                            MediaTttLoggerUtils.logStateChangeError(logBuffer, "MediaTttReceiver", i);
                            return;
                        }
                        Map map = mediaTttChipControllerReceiver.instanceMap;
                        final InstanceId instanceId = (InstanceId) ((LinkedHashMap) map).get(mediaRoute2Info.getId());
                        if (instanceId == null) {
                            instanceId = mediaTttChipControllerReceiver.temporaryViewUiEventLogger.instanceIdSequence.newInstanceId();
                        }
                        mediaTttChipControllerReceiver.uiEventLogger.logger.log(chipStateReceiver.getUiEvent(), instanceId);
                        if (chipStateReceiver != ChipStateReceiver.CLOSE_TO_SENDER) {
                            mediaTttChipControllerReceiver.removeView(mediaRoute2Info.getId(), chipStateReceiver.name());
                            return;
                        }
                        map.put(mediaRoute2Info.getId(), instanceId);
                        if (icon == null) {
                            mediaTttChipControllerReceiver.displayView(new ChipReceiverInfo(mediaRoute2Info, null, charSequence, null, null, mediaRoute2Info.getId(), null, instanceId, 88, null));
                            return;
                        } else {
                            icon.loadDrawableAsync(mediaTttChipControllerReceiver.context, new Icon.OnDrawableLoadedListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$updateMediaTapToTransferReceiverDisplay$1
                                @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
                                public final void onDrawableLoaded(Drawable drawable) {
                                    MediaTttChipControllerReceiver mediaTttChipControllerReceiver2 = MediaTttChipControllerReceiver.this;
                                    MediaRoute2Info mediaRoute2Info2 = mediaRoute2Info;
                                    mediaTttChipControllerReceiver2.displayView(new ChipReceiverInfo(mediaRoute2Info2, drawable, charSequence, null, null, mediaRoute2Info2.getId(), null, instanceId, 88, null));
                                }
                            }, mediaTttChipControllerReceiver.mainHandler);
                            return;
                        }
                    }
                }
                throw new NoSuchElementException("Array contains no element matching the predicate.");
            }
        };
        this.instanceMap = new LinkedHashMap();
        this.displayListener = new TemporaryViewDisplayController.Listener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$displayListener$1
            @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController.Listener
            public final void onInfoPermanentlyRemoved(String str, String str2) {
                MediaTttChipControllerReceiver.this.instanceMap.remove(str);
            }
        };
    }

    public static void animateViewTranslationAndFade$default(MediaTttChipControllerReceiver mediaTttChipControllerReceiver, ViewGroup viewGroup, float f, float f2, TimeInterpolator timeInterpolator, long j, long j2, final MediaTttChipControllerReceiver$animateViewIn$1 mediaTttChipControllerReceiver$animateViewIn$1, int i) {
        if ((i & 8) != 0) {
            timeInterpolator = null;
        }
        if ((i & 16) != 0) {
            j = 500;
        }
        if ((i & 32) != 0) {
            j2 = ICON_ALPHA_ANIM_DURATION;
        }
        if ((i & 64) != 0) {
            mediaTttChipControllerReceiver$animateViewIn$1 = null;
        }
        mediaTttChipControllerReceiver.getClass();
        viewGroup.animate().translationYBy(f).setInterpolator(timeInterpolator).setDuration(j).withEndAction(new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewTranslationAndFade$1
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable = mediaTttChipControllerReceiver$animateViewIn$1;
                if (runnable != null) {
                    runnable.run();
                }
            }
        }).start();
        viewGroup.animate().alpha(f2).setDuration(j2).start();
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewIn$1] */
    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    /* renamed from: animateViewIn$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo159x25db3cb1(ViewGroup viewGroup) {
        final ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.icon_container_view);
        ReceiverChipRippleView receiverChipRippleView = (ReceiverChipRippleView) viewGroup.requireViewById(R.id.icon_glow_ripple);
        ReceiverChipRippleView receiverChipRippleView2 = (ReceiverChipRippleView) viewGroup.requireViewById(R.id.ripple);
        final MediaTttReceiverRippleController mediaTttReceiverRippleController = this.rippleController;
        final float m162x378fc465 = mediaTttReceiverRippleController.m162x378fc465() * 2.0f;
        mediaTttReceiverRippleController.getClass();
        if (!receiverChipRippleView2.animator.isRunning()) {
            final boolean z = false;
            receiverChipRippleView2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttReceiverRippleController$expandRipple$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (view == null) {
                        return;
                    }
                    ReceiverChipRippleView receiverChipRippleView3 = (ReceiverChipRippleView) view;
                    if (z) {
                        MediaTttReceiverRippleController.access$layoutIconRipple(mediaTttReceiverRippleController, receiverChipRippleView3);
                    } else {
                        MediaTttReceiverRippleController mediaTttReceiverRippleController2 = mediaTttReceiverRippleController;
                        int i9 = MediaTttReceiverRippleController.$r8$clinit;
                        mediaTttReceiverRippleController2.layoutRipple(receiverChipRippleView3, false);
                    }
                    receiverChipRippleView3.invalidate();
                }
            });
            receiverChipRippleView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttReceiverRippleController$expandRipple$2
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    if (view == null) {
                        return;
                    }
                    ReceiverChipRippleView receiverChipRippleView3 = (ReceiverChipRippleView) view;
                    if (z) {
                        MediaTttReceiverRippleController.access$layoutIconRipple(mediaTttReceiverRippleController, receiverChipRippleView3);
                    } else {
                        MediaTttReceiverRippleController mediaTttReceiverRippleController2 = mediaTttReceiverRippleController;
                        int i = MediaTttReceiverRippleController.$r8$clinit;
                        mediaTttReceiverRippleController2.layoutRipple(receiverChipRippleView3, false);
                    }
                    int i2 = ReceiverChipRippleView.$r8$clinit;
                    receiverChipRippleView3.duration = 333L;
                    receiverChipRippleView3.isStarted = true;
                    receiverChipRippleView3.startRipple(null);
                    receiverChipRippleView3.removeOnAttachStateChangeListener(this);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
        }
        if (!receiverChipRippleView.animator.isRunning()) {
            final boolean z2 = true;
            receiverChipRippleView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttReceiverRippleController$expandRipple$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (view == null) {
                        return;
                    }
                    ReceiverChipRippleView receiverChipRippleView3 = (ReceiverChipRippleView) view;
                    if (z2) {
                        MediaTttReceiverRippleController.access$layoutIconRipple(mediaTttReceiverRippleController, receiverChipRippleView3);
                    } else {
                        MediaTttReceiverRippleController mediaTttReceiverRippleController2 = mediaTttReceiverRippleController;
                        int i9 = MediaTttReceiverRippleController.$r8$clinit;
                        mediaTttReceiverRippleController2.layoutRipple(receiverChipRippleView3, false);
                    }
                    receiverChipRippleView3.invalidate();
                }
            });
            receiverChipRippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttReceiverRippleController$expandRipple$2
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    if (view == null) {
                        return;
                    }
                    ReceiverChipRippleView receiverChipRippleView3 = (ReceiverChipRippleView) view;
                    if (z2) {
                        MediaTttReceiverRippleController.access$layoutIconRipple(mediaTttReceiverRippleController, receiverChipRippleView3);
                    } else {
                        MediaTttReceiverRippleController mediaTttReceiverRippleController2 = mediaTttReceiverRippleController;
                        int i = MediaTttReceiverRippleController.$r8$clinit;
                        mediaTttReceiverRippleController2.layoutRipple(receiverChipRippleView3, false);
                    }
                    int i2 = ReceiverChipRippleView.$r8$clinit;
                    receiverChipRippleView3.duration = 333L;
                    receiverChipRippleView3.isStarted = true;
                    receiverChipRippleView3.startRipple(null);
                    receiverChipRippleView3.removeOnAttachStateChangeListener(this);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
        }
        viewGroup2.setTranslationY(mediaTttReceiverRippleController.m162x378fc465());
        animateViewTranslationAndFade$default(this, viewGroup2, (-1) * m162x378fc465, 1.0f, Interpolators.EMPHASIZED_DECELERATE, 0L, 0L, new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewIn$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaTttChipControllerReceiver mediaTttChipControllerReceiver = MediaTttChipControllerReceiver.this;
                final ViewGroup viewGroup3 = viewGroup2;
                final float f = m162x378fc465 * 0.15f;
                ValueAnimator valueAnimator = mediaTttChipControllerReceiver.bounceAnimator;
                if (valueAnimator.isStarted()) {
                    return;
                }
                final float translationY = viewGroup3.getTranslationY();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$addViewToBounceAnimation$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        viewGroup3.setTranslationY((f * floatValue) + translationY);
                    }
                });
                viewGroup3.setAccessibilityLiveRegion(0);
                valueAnimator.start();
            }
        }, 48);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    /* renamed from: animateViewOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo160xf3ed518e(ViewGroup viewGroup, String str, final TemporaryViewDisplayController$removeViewFromWindow$1 temporaryViewDisplayController$removeViewFromWindow$1) {
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.icon_container_view);
        final ReceiverChipRippleView receiverChipRippleView = (ReceiverChipRippleView) viewGroup.requireViewById(R.id.ripple);
        MediaTttReceiverRippleController mediaTttReceiverRippleController = this.rippleController;
        float m162x378fc465 = 2.0f * mediaTttReceiverRippleController.m162x378fc465();
        ValueAnimator valueAnimator = this.bounceAnimator;
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.cancel();
        if (Intrinsics.areEqual(str, "TRANSFER_TO_RECEIVER_SUCCEEDED")) {
            MediaTttFlags mediaTttFlags = this.mediaTttFlags;
            mediaTttFlags.getClass();
            Flags.INSTANCE.getClass();
            if (((FeatureFlagsRelease) mediaTttFlags.featureFlags).isEnabled(Flags.MEDIA_TTT_RECEIVER_SUCCESS_RIPPLE)) {
                mediaTttReceiverRippleController.layoutRipple(receiverChipRippleView, true);
                float f = mediaTttReceiverRippleController.maxRippleHeight;
                if (receiverChipRippleView.isStarted) {
                    receiverChipRippleView.animator.removeAllListeners();
                    receiverChipRippleView.animator.removeAllUpdateListeners();
                    RippleShader rippleShader = receiverChipRippleView.getRippleShader();
                    RippleShader.FadeParams fadeParams = rippleShader.baseRingFadeParams;
                    fadeParams.fadeOutStart = 0.3f;
                    fadeParams.fadeOutEnd = 1.0f;
                    RippleShader.FadeParams fadeParams2 = rippleShader.centerFillFadeParams;
                    fadeParams2.fadeInStart = 0.0f;
                    fadeParams2.fadeInEnd = 0.0f;
                    fadeParams2.fadeOutStart = fadeParams.fadeInEnd;
                    fadeParams2.fadeOutEnd = 1.0f;
                    final float pow = 1 - ((float) Math.pow(r14 - (receiverChipRippleView.getRippleShader().rippleSize.currentHeight / f), 0.3333333333333333d));
                    receiverChipRippleView.animator.setDuration(1000L);
                    receiverChipRippleView.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandToFull$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            long currentPlayTime = valueAnimator2.getCurrentPlayTime();
                            float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            ReceiverChipRippleView receiverChipRippleView2 = ReceiverChipRippleView.this;
                            int i = ReceiverChipRippleView.$r8$clinit;
                            RippleShader rippleShader2 = receiverChipRippleView2.getRippleShader();
                            float f2 = pow;
                            float f3 = 1;
                            rippleShader2.setRawProgress(((f3 - f2) * floatValue) + f2);
                            RippleShader rippleShader3 = ReceiverChipRippleView.this.getRippleShader();
                            float f4 = f3 - ReceiverChipRippleView.this.getRippleShader().rawProgress;
                            float f5 = 75;
                            rippleShader3.setFloatUniform("in_distort_radial", rippleShader3.rawProgress * f5 * f4);
                            rippleShader3.setFloatUniform("in_distort_xy", f5 * f4);
                            ReceiverChipRippleView.this.getRippleShader().setPixelDensity(f3 - ReceiverChipRippleView.this.getRippleShader().rawProgress);
                            ReceiverChipRippleView.this.getRippleShader().setFloatUniform("in_time", currentPlayTime);
                            ReceiverChipRippleView.this.invalidate();
                        }
                    });
                    receiverChipRippleView.animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandToFull$2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            if (animator != null) {
                                receiverChipRippleView.setVisibility(8);
                            }
                            Runnable runnable = temporaryViewDisplayController$removeViewFromWindow$1;
                            if (runnable != null) {
                                runnable.run();
                            }
                            receiverChipRippleView.isStarted = false;
                        }
                    });
                    receiverChipRippleView.animator.start();
                }
                animateViewTranslationAndFade$default(this, viewGroup2, m162x378fc465 * (-1), 0.0f, null, 167L, 167L, null, 72);
                return;
            }
        }
        mediaTttReceiverRippleController.getClass();
        if (receiverChipRippleView.isStarted) {
            receiverChipRippleView.duration = 333L;
            receiverChipRippleView.animator.removeAllListeners();
            receiverChipRippleView.animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$collapseRipple$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Runnable runnable = temporaryViewDisplayController$removeViewFromWindow$1;
                    if (runnable != null) {
                        runnable.run();
                    }
                    receiverChipRippleView.isStarted = false;
                }
            });
            receiverChipRippleView.animator.reverse();
        }
        animateViewTranslationAndFade$default(this, viewGroup2, m162x378fc465, 0.0f, null, 0L, 0L, null, 120);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void getTouchableRegion(Rect rect, View view) {
        CachingIconView requireViewById = view.requireViewById(R.id.app_icon);
        this.viewUtil.getClass();
        int[] iArr = new int[2];
        requireViewById.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        rect.set(i, i2, requireViewById.getWidth() + i, requireViewById.getHeight() + i2);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    /* renamed from: getWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final WindowManager.LayoutParams mo161x8e91d99c() {
        return this.windowLayoutParams;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController, com.android.systemui.CoreStartable
    public final void start() {
        super.start();
        MediaTttFlags mediaTttFlags = this.mediaTttFlags;
        mediaTttFlags.getClass();
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsRelease) mediaTttFlags.featureFlags).isEnabled(Flags.MEDIA_TAP_TO_TRANSFER)) {
            this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
        }
        this.listeners.add(this.displayListener);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void updateView(TemporaryViewInfo temporaryViewInfo, ViewGroup viewGroup) {
        ChipReceiverInfo chipReceiverInfo = (ChipReceiverInfo) temporaryViewInfo;
        final String clientPackageName = chipReceiverInfo.routeInfo.getClientPackageName();
        MediaTttUtils.Companion companion = MediaTttUtils.Companion;
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$updateView$iconInfo$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaTttReceiverLogger mediaTttReceiverLogger = (MediaTttReceiverLogger) MediaTttChipControllerReceiver.this.logger;
                String str = clientPackageName;
                mediaTttReceiverLogger.getClass();
                MediaTttLoggerUtils.INSTANCE.getClass();
                MediaTttLoggerUtils.logPackageNotFound(mediaTttReceiverLogger.buffer, "MediaTttReceiver", str);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        Context context = this.context;
        IconInfo iconInfoFromPackageName = MediaTttUtils.Companion.getIconInfoFromPackageName(context, clientPackageName, true, function0);
        CharSequence charSequence = chipReceiverInfo.appNameOverride;
        if (charSequence != null) {
            iconInfoFromPackageName = IconInfo.copy$default(iconInfoFromPackageName, new ContentDescription.Loaded(charSequence.toString()), null, false, 14);
        }
        Drawable drawable = chipReceiverInfo.appIconDrawableOverride;
        if (drawable != null) {
            iconInfoFromPackageName = IconInfo.copy$default(iconInfoFromPackageName, null, new MediaTttIcon.Loaded(drawable), true, 5);
        }
        int dimensionPixelSize = iconInfoFromPackageName.isAppIcon ? 0 : context.getResources().getDimensionPixelSize(R.dimen.media_ttt_generic_icon_padding);
        CachingIconView requireViewById = viewGroup.requireViewById(R.id.app_icon);
        requireViewById.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        TintedIconViewBinder tintedIconViewBinder = TintedIconViewBinder.INSTANCE;
        TintedIcon tintedIcon = iconInfoFromPackageName.toTintedIcon();
        tintedIconViewBinder.getClass();
        TintedIconViewBinder.bind(tintedIcon, requireViewById);
        ((ViewGroup) viewGroup.requireViewById(R.id.icon_container_view)).setAccessibilityLiveRegion(2);
    }
}
