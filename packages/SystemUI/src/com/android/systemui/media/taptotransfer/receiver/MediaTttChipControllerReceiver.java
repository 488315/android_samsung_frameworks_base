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
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.common.ui.binder.TintedIconViewBinder;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.taptotransfer.common.IconInfo;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils$$ExternalSyntheticLambda0;
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

/* loaded from: classes2.dex */
public class MediaTttChipControllerReceiver extends TemporaryViewDisplayController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long ICON_ALPHA_ANIM_DURATION;
    public final ValueAnimator bounceAnimator;
    public final CommandQueue commandQueue;
    public final MediaTttChipControllerReceiver$commandQueueCallbacks$1 commandQueueCallbacks;
    public final MediaTttChipControllerReceiver$displayListener$1 displayListener;
    public final Map instanceMap;
    public final DelayableExecutor mainExecutor;
    public final Handler mainHandler;
    public final MediaTttReceiverRippleController rippleController;
    public final TemporaryViewUiEventLogger temporaryViewUiEventLogger;
    public final MediaTttReceiverUiEventLogger uiEventLogger;
    public final ViewUtil viewUtil;
    public final WindowManager.LayoutParams windowLayoutParams;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        ICON_ALPHA_ANIM_DURATION = AnimationUtil.Companion.getFrames(5);
    }

    /* JADX WARN: Type inference failed for: r15v10, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$displayListener$1] */
    /* JADX WARN: Type inference failed for: r15v8, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1] */
    public MediaTttChipControllerReceiver(CommandQueue commandQueue, Context context, MediaTttReceiverLogger mediaTttReceiverLogger, WindowManager windowManager, DelayableExecutor delayableExecutor, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DumpManager dumpManager, PowerManager powerManager, Handler handler, MediaTttReceiverUiEventLogger mediaTttReceiverUiEventLogger, ViewUtil viewUtil, WakeLock.Builder builder, SystemClock systemClock, MediaTttReceiverRippleController mediaTttReceiverRippleController, TemporaryViewUiEventLogger temporaryViewUiEventLogger) {
        super(context, mediaTttReceiverLogger, windowManager, delayableExecutor, accessibilityManager, configurationController, dumpManager, powerManager, R.layout.media_ttt_chip_receiver, builder, systemClock, temporaryViewUiEventLogger);
        this.commandQueue = commandQueue;
        this.mainExecutor = delayableExecutor;
        this.mainHandler = handler;
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.setRepeatMode(2);
        valueAnimatorOfFloat.setDuration(750L);
        this.bounceAnimator = valueAnimatorOfFloat;
        this.commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void updateMediaTapToTransferReceiverDisplay(int i, final MediaRoute2Info mediaRoute2Info, Icon icon, final CharSequence charSequence) throws Throwable {
                String strName;
                int i2;
                int i3 = MediaTttChipControllerReceiver.$r8$clinit;
                final MediaTttChipControllerReceiver mediaTttChipControllerReceiver = this.this$0;
                mediaTttChipControllerReceiver.getClass();
                ChipStateReceiver.Companion.getClass();
                try {
                } catch (NoSuchElementException e) {
                    Log.e("ChipStateReceiver", "Could not find requested state " + i, e);
                    chipStateReceiver = null;
                }
                for (ChipStateReceiver chipStateReceiver : ChipStateReceiver.values()) {
                    if (chipStateReceiver.getStateInt() == i) {
                        if (chipStateReceiver == null || (strName = chipStateReceiver.name()) == null) {
                            strName = "Invalid";
                        }
                        MediaTttReceiverLogger mediaTttReceiverLogger2 = (MediaTttReceiverLogger) mediaTttChipControllerReceiver.logger;
                        String id = mediaRoute2Info.getId();
                        String clientPackageName = mediaRoute2Info.getClientPackageName();
                        mediaTttReceiverLogger2.getClass();
                        MediaTttLoggerUtils.INSTANCE.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        MediaTttLoggerUtils$$ExternalSyntheticLambda0 mediaTttLoggerUtils$$ExternalSyntheticLambda0 = new MediaTttLoggerUtils$$ExternalSyntheticLambda0(2);
                        LogBuffer logBuffer = mediaTttReceiverLogger2.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("MediaTttReceiver", logLevel, mediaTttLoggerUtils$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = strName;
                        logMessageImpl.str2 = id;
                        logMessageImpl.str3 = clientPackageName;
                        logBuffer.commit(logMessageObtain);
                        if (chipStateReceiver == null) {
                            LogMessage logMessageObtain2 = logBuffer.obtain("MediaTttReceiver", LogLevel.ERROR, new MediaTttLoggerUtils$$ExternalSyntheticLambda0(0), null);
                            ((LogMessageImpl) logMessageObtain2).int1 = i;
                            logBuffer.commit(logMessageObtain2);
                            return;
                        }
                        final InstanceId instanceIdNewInstanceId = (InstanceId) ((LinkedHashMap) mediaTttChipControllerReceiver.instanceMap).get(mediaRoute2Info.getId());
                        if (instanceIdNewInstanceId == null) {
                            instanceIdNewInstanceId = mediaTttChipControllerReceiver.temporaryViewUiEventLogger.instanceIdSequence.newInstanceId();
                        }
                        mediaTttChipControllerReceiver.uiEventLogger.logger.log(chipStateReceiver.getUiEvent(), instanceIdNewInstanceId);
                        if (chipStateReceiver != ChipStateReceiver.CLOSE_TO_SENDER) {
                            mediaTttChipControllerReceiver.removeView(mediaRoute2Info.getId(), chipStateReceiver.name());
                            return;
                        }
                        mediaTttChipControllerReceiver.instanceMap.put(mediaRoute2Info.getId(), instanceIdNewInstanceId);
                        if (icon == null) {
                            mediaTttChipControllerReceiver.displayView(new ChipReceiverInfo(mediaRoute2Info, null, charSequence, null, null, mediaRoute2Info.getId(), null, instanceIdNewInstanceId, 88, null));
                            return;
                        } else {
                            icon.loadDrawableAsync(mediaTttChipControllerReceiver.context, new Icon.OnDrawableLoadedListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$updateMediaTapToTransferReceiverDisplay$1
                                @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
                                public final void onDrawableLoaded(Drawable drawable) throws Throwable {
                                    MediaTttChipControllerReceiver mediaTttChipControllerReceiver2 = mediaTttChipControllerReceiver;
                                    MediaRoute2Info mediaRoute2Info2 = mediaRoute2Info;
                                    mediaTttChipControllerReceiver2.displayView(new ChipReceiverInfo(mediaRoute2Info2, drawable, charSequence, null, null, mediaRoute2Info2.getId(), null, instanceIdNewInstanceId, 88, null));
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
                this.this$0.instanceMap.remove(str);
            }
        };
    }

    public static void animateViewTranslationAndFade$default(MediaTttChipControllerReceiver mediaTttChipControllerReceiver, ViewGroup viewGroup, float f, float f2, TimeInterpolator timeInterpolator, final MediaTttChipControllerReceiver$animateViewIn$1 mediaTttChipControllerReceiver$animateViewIn$1, int i) {
        if ((i & 8) != 0) {
            timeInterpolator = null;
        }
        long j = (i & 16) != 0 ? 500L : 167L;
        long j2 = (i & 32) != 0 ? ICON_ALPHA_ANIM_DURATION : 167L;
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

    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewIn$1] */
    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void animateViewIn$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ViewGroup viewGroup) {
        final ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.icon_container_view);
        ReceiverChipRippleView receiverChipRippleView = (ReceiverChipRippleView) viewGroup.requireViewById(R.id.icon_glow_ripple);
        ReceiverChipRippleView receiverChipRippleView2 = (ReceiverChipRippleView) viewGroup.requireViewById(R.id.ripple);
        final MediaTttReceiverRippleController mediaTttReceiverRippleController = this.rippleController;
        final float receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaTttReceiverRippleController.getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() * 2.0f;
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
                    final ReceiverChipRippleView receiverChipRippleView3 = (ReceiverChipRippleView) view;
                    if (z) {
                        MediaTttReceiverRippleController.access$layoutIconRipple(mediaTttReceiverRippleController, receiverChipRippleView3);
                    } else {
                        MediaTttReceiverRippleController mediaTttReceiverRippleController2 = mediaTttReceiverRippleController;
                        int i = MediaTttReceiverRippleController.$r8$clinit;
                        mediaTttReceiverRippleController2.layoutRipple(receiverChipRippleView3, false);
                    }
                    final MediaTttReceiverLogger mediaTttReceiverLogger = mediaTttReceiverRippleController.mediaTttReceiverLogger;
                    int i2 = ReceiverChipRippleView.$r8$clinit;
                    receiverChipRippleView3.duration = 333L;
                    receiverChipRippleView3.isStarted = true;
                    final Runnable runnable = null;
                    receiverChipRippleView3.startRipple(new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandRipple$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            mediaTttReceiverLogger.logRippleAnimationEnd(receiverChipRippleView3.getId(), "expand");
                            Runnable runnable2 = runnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        }
                    });
                    mediaTttReceiverLogger.logRippleAnimationStart(receiverChipRippleView3.getId(), "expand");
                    receiverChipRippleView3.removeOnAttachStateChangeListener(this);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    view.setVisibility(8);
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
                    final ReceiverChipRippleView receiverChipRippleView3 = (ReceiverChipRippleView) view;
                    if (z2) {
                        MediaTttReceiverRippleController.access$layoutIconRipple(mediaTttReceiverRippleController, receiverChipRippleView3);
                    } else {
                        MediaTttReceiverRippleController mediaTttReceiverRippleController2 = mediaTttReceiverRippleController;
                        int i = MediaTttReceiverRippleController.$r8$clinit;
                        mediaTttReceiverRippleController2.layoutRipple(receiverChipRippleView3, false);
                    }
                    final MediaTttReceiverLogger mediaTttReceiverLogger = mediaTttReceiverRippleController.mediaTttReceiverLogger;
                    int i2 = ReceiverChipRippleView.$r8$clinit;
                    receiverChipRippleView3.duration = 333L;
                    receiverChipRippleView3.isStarted = true;
                    final Runnable runnable = null;
                    receiverChipRippleView3.startRipple(new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandRipple$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            mediaTttReceiverLogger.logRippleAnimationEnd(receiverChipRippleView3.getId(), "expand");
                            Runnable runnable2 = runnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        }
                    });
                    mediaTttReceiverLogger.logRippleAnimationStart(receiverChipRippleView3.getId(), "expand");
                    receiverChipRippleView3.removeOnAttachStateChangeListener(this);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    view.setVisibility(8);
                }
            });
        }
        viewGroup2.setTranslationY(mediaTttReceiverRippleController.getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
        animateViewTranslationAndFade$default(this, viewGroup2, (-1) * receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 1.0f, Interpolators.EMPHASIZED_DECELERATE, new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewIn$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaTttChipControllerReceiver mediaTttChipControllerReceiver = this.this$0;
                final ViewGroup viewGroup3 = viewGroup2;
                final float f = receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core * 0.15f;
                if (mediaTttChipControllerReceiver.bounceAnimator.isStarted()) {
                    return;
                }
                final float translationY = viewGroup3.getTranslationY();
                mediaTttChipControllerReceiver.bounceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$addViewToBounceAnimation$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        viewGroup3.setTranslationY((f * fFloatValue) + translationY);
                    }
                });
                viewGroup3.setAccessibilityLiveRegion(0);
                mediaTttChipControllerReceiver.bounceAnimator.start();
            }
        }, 48);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void animateViewOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final ViewGroup viewGroup, String str, final TemporaryViewDisplayController$removeViewFromWindow$1 temporaryViewDisplayController$removeViewFromWindow$1) {
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.icon_container_view);
        final ReceiverChipRippleView receiverChipRippleView = (ReceiverChipRippleView) viewGroup.requireViewById(R.id.ripple);
        MediaTttReceiverRippleController mediaTttReceiverRippleController = this.rippleController;
        float receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaTttReceiverRippleController.getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() * 2.0f;
        this.bounceAnimator.removeAllUpdateListeners();
        this.bounceAnimator.cancel();
        if (!Intrinsics.areEqual(str, "TRANSFER_TO_RECEIVER_SUCCEEDED")) {
            mediaTttReceiverRippleController.getClass();
            if (receiverChipRippleView.isStarted) {
                receiverChipRippleView.duration = 333L;
                receiverChipRippleView.animator.removeAllListeners();
                ValueAnimator valueAnimator = receiverChipRippleView.animator;
                final MediaTttReceiverLogger mediaTttReceiverLogger = mediaTttReceiverRippleController.mediaTttReceiverLogger;
                valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$collapseRipple$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        onAnimationEnd(animator);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        ReceiverChipRippleView receiverChipRippleView2 = receiverChipRippleView;
                        MediaTttReceiverLogger mediaTttReceiverLogger2 = mediaTttReceiverLogger;
                        receiverChipRippleView2.setVisibility(8);
                        mediaTttReceiverLogger2.logRippleAnimationEnd(receiverChipRippleView2.getId(), "collapse");
                        Runnable runnable = temporaryViewDisplayController$removeViewFromWindow$1;
                        if (runnable != null) {
                            runnable.run();
                        }
                        receiverChipRippleView.isStarted = false;
                    }
                });
                receiverChipRippleView.animator.reverse();
                mediaTttReceiverLogger.logRippleAnimationStart(receiverChipRippleView.getId(), "collapse");
            }
            animateViewTranslationAndFade$default(this, viewGroup2, receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 0.0f, null, null, 120);
            this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewOut$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (viewGroup.isAttachedToWindow()) {
                        temporaryViewDisplayController$removeViewFromWindow$1.run();
                    }
                }
            }, 500L);
            return;
        }
        mediaTttReceiverRippleController.layoutRipple(receiverChipRippleView, true);
        float f = mediaTttReceiverRippleController.maxRippleHeight;
        if (receiverChipRippleView.isStarted) {
            receiverChipRippleView.animator.removeAllListeners();
            receiverChipRippleView.animator.removeAllUpdateListeners();
            RippleShader rippleShader = receiverChipRippleView.rippleShader;
            RippleShader rippleShader2 = rippleShader != null ? rippleShader : null;
            RippleShader.FadeParams fadeParams = rippleShader2.baseRingFadeParams;
            fadeParams.fadeOutStart = 0.3f;
            fadeParams.fadeOutEnd = 1.0f;
            RippleShader.FadeParams fadeParams2 = rippleShader2.centerFillFadeParams;
            fadeParams2.fadeInStart = 0.0f;
            fadeParams2.fadeInEnd = 0.0f;
            fadeParams2.fadeOutStart = fadeParams.fadeInEnd;
            fadeParams2.fadeOutEnd = 1.0f;
            if (rippleShader == null) {
                rippleShader = null;
            }
            final float fPow = 1 - ((float) Math.pow(r12 - (rippleShader.rippleSize.currentHeight / f), 0.3333333333333333d));
            receiverChipRippleView.animator.setDuration(1000L);
            receiverChipRippleView.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandToFull$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    long currentPlayTime = valueAnimator2.getCurrentPlayTime();
                    float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    ReceiverChipRippleView receiverChipRippleView2 = receiverChipRippleView;
                    int i = ReceiverChipRippleView.$r8$clinit;
                    RippleShader rippleShader3 = receiverChipRippleView2.rippleShader;
                    if (rippleShader3 == null) {
                        rippleShader3 = null;
                    }
                    float f2 = fPow;
                    float f3 = 1;
                    rippleShader3.setRawProgress(((f3 - f2) * fFloatValue) + f2);
                    RippleShader rippleShader4 = receiverChipRippleView.rippleShader;
                    RippleShader rippleShader5 = rippleShader4 != null ? rippleShader4 : null;
                    if (rippleShader4 == null) {
                        rippleShader4 = null;
                    }
                    rippleShader5.setDistortionStrength(f3 - rippleShader4.rawProgress);
                    RippleShader rippleShader6 = receiverChipRippleView.rippleShader;
                    RippleShader rippleShader7 = rippleShader6 != null ? rippleShader6 : null;
                    if (rippleShader6 == null) {
                        rippleShader6 = null;
                    }
                    rippleShader7.setPixelDensity(f3 - rippleShader6.rawProgress);
                    RippleShader rippleShader8 = receiverChipRippleView.rippleShader;
                    (rippleShader8 != null ? rippleShader8 : null).setFloatUniform("in_time", currentPlayTime);
                    receiverChipRippleView.invalidate();
                }
            });
            ValueAnimator valueAnimator2 = receiverChipRippleView.animator;
            final MediaTttReceiverLogger mediaTttReceiverLogger2 = mediaTttReceiverRippleController.mediaTttReceiverLogger;
            valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandToFull$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    onAnimationEnd(animator);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ReceiverChipRippleView receiverChipRippleView2 = receiverChipRippleView;
                    MediaTttReceiverLogger mediaTttReceiverLogger3 = mediaTttReceiverLogger2;
                    receiverChipRippleView2.setVisibility(8);
                    mediaTttReceiverLogger3.logRippleAnimationEnd(receiverChipRippleView2.getId(), "expand to full");
                    Runnable runnable = temporaryViewDisplayController$removeViewFromWindow$1;
                    if (runnable != null) {
                        runnable.run();
                    }
                    receiverChipRippleView.isStarted = false;
                }
            });
            receiverChipRippleView.animator.start();
            mediaTttReceiverLogger2.logRippleAnimationStart(receiverChipRippleView.getId(), "expand to full");
        }
        animateViewTranslationAndFade$default(this, viewGroup2, receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core * (-1), 0.0f, null, null, 72);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void getTouchableRegion(Rect rect, View view) {
        this.viewUtil.setRectToViewWindowLocation((CachingIconView) view.requireViewById(R.id.app_icon), rect);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final WindowManager.LayoutParams getWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.windowLayoutParams;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController, com.android.systemui.CoreStartable
    public final void start() {
        super.start();
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
        this.listeners.add(this.displayListener);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void updateView(TemporaryViewInfo temporaryViewInfo, ViewGroup viewGroup) {
        ChipReceiverInfo chipReceiverInfo = (ChipReceiverInfo) temporaryViewInfo;
        final String clientPackageName = chipReceiverInfo.routeInfo.getClientPackageName();
        MediaTttUtils.Companion companion = MediaTttUtils.Companion;
        Context context = this.context;
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = MediaTttChipControllerReceiver.$r8$clinit;
                String str = clientPackageName;
                if (str != null) {
                    MediaTttReceiverLogger mediaTttReceiverLogger = (MediaTttReceiverLogger) this.logger;
                    mediaTttReceiverLogger.getClass();
                    MediaTttLoggerUtils.INSTANCE.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    MediaTttLoggerUtils$$ExternalSyntheticLambda0 mediaTttLoggerUtils$$ExternalSyntheticLambda0 = new MediaTttLoggerUtils$$ExternalSyntheticLambda0(1);
                    LogBuffer logBuffer = mediaTttReceiverLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("MediaTttReceiver", logLevel, mediaTttLoggerUtils$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) logMessageObtain).str1 = str;
                    logBuffer.commit(logMessageObtain);
                }
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        IconInfo iconInfoFromPackageName = MediaTttUtils.Companion.getIconInfoFromPackageName(context, clientPackageName, true, function0);
        if (chipReceiverInfo.appNameOverride != null) {
            iconInfoFromPackageName = IconInfo.copy$default(iconInfoFromPackageName, new ContentDescription.Loaded(chipReceiverInfo.appNameOverride.toString()), null, 14);
        }
        Drawable drawable = chipReceiverInfo.appIconDrawableOverride;
        if (drawable != null) {
            iconInfoFromPackageName = IconInfo.copy$default(iconInfoFromPackageName, null, new MediaTttIcon.Loaded(drawable), 5);
        }
        int dimensionPixelSize = iconInfoFromPackageName.isAppIcon ? 0 : this.context.getResources().getDimensionPixelSize(R.dimen.media_ttt_generic_icon_padding);
        CachingIconView cachingIconViewRequireViewById = viewGroup.requireViewById(R.id.app_icon);
        cachingIconViewRequireViewById.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        TintedIconViewBinder tintedIconViewBinder = TintedIconViewBinder.INSTANCE;
        TintedIcon tintedIcon = iconInfoFromPackageName.toTintedIcon();
        tintedIconViewBinder.getClass();
        IconViewBinder.INSTANCE.getClass();
        IconViewBinder.bind(tintedIcon.icon, cachingIconViewRequireViewById);
        Integer num = tintedIcon.tint;
        cachingIconViewRequireViewById.setImageTintList(num != null ? Utils.getColorAttr(num.intValue(), cachingIconViewRequireViewById.getContext()) : null);
        ((ViewGroup) viewGroup.requireViewById(R.id.icon_container_view)).setAccessibilityLiveRegion(2);
    }
}
