package com.android.systemui.temporarydisplay.chipbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.PowerManager;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.CachingIconView;
import com.android.settingslib.Utils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.common.ui.binder.TintedIconViewBinder;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController$removeViewFromWindow$1;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import com.android.systemui.temporarydisplay.TemporaryViewLogger$$ExternalSyntheticLambda0;
import com.android.systemui.temporarydisplay.TemporaryViewUiEvent;
import com.android.systemui.temporarydisplay.TemporaryViewUiEventLogger;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarEndItem;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.view.ViewUtil;
import com.android.systemui.util.wakelock.WakeLock;
import java.time.Duration;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public class ChipbarCoordinator extends TemporaryViewDisplayController {
    public static final VibrationAttributes VIBRATION_ATTRIBUTES;
    public final ChipbarAnimator chipbarAnimator;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public LoadingDetails loadingDetails;
    public final SwipeChipbarAwayGestureHandler swipeChipbarAwayGestureHandler;
    public final VibratorHelper vibratorHelper;
    public final ViewUtil viewUtil;
    public final WindowManager.LayoutParams windowLayoutParams;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class LoadingDetails {
        public final ObjectAnimator animator;
        public final View loadingView;

        public LoadingDetails(View view, ObjectAnimator objectAnimator) {
            this.loadingView = view;
            this.animator = objectAnimator;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LoadingDetails)) {
                return false;
            }
            LoadingDetails loadingDetails = (LoadingDetails) obj;
            return Intrinsics.areEqual(this.loadingView, loadingDetails.loadingView) && Intrinsics.areEqual(this.animator, loadingDetails.animator);
        }

        public final int hashCode() {
            return this.animator.hashCode() + (this.loadingView.hashCode() * 31);
        }

        public final String toString() {
            return "LoadingDetails(loadingView=" + this.loadingView + ", animator=" + this.animator + ")";
        }
    }

    /* renamed from: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$updateView$1, reason: invalid class name */
    public final class AnonymousClass1 implements Gefingerpoken {
        public AnonymousClass1() {
        }
    }

    static {
        new Companion(null);
        VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(50);
    }

    public ChipbarCoordinator(Context context, ChipbarLogger chipbarLogger, WindowManager windowManager, DelayableExecutor delayableExecutor, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DumpManager dumpManager, PowerManager powerManager, ChipbarAnimator chipbarAnimator, FalsingManager falsingManager, FalsingCollector falsingCollector, SwipeChipbarAwayGestureHandler swipeChipbarAwayGestureHandler, ViewUtil viewUtil, VibratorHelper vibratorHelper, WakeLock.Builder builder, SystemClock systemClock, TemporaryViewUiEventLogger temporaryViewUiEventLogger) {
        super(context, chipbarLogger, windowManager, delayableExecutor, accessibilityManager, configurationController, dumpManager, powerManager, R.layout.chipbar, builder, systemClock, temporaryViewUiEventLogger);
        this.chipbarAnimator = chipbarAnimator;
        this.falsingManager = falsingManager;
        this.falsingCollector = falsingCollector;
        this.swipeChipbarAwayGestureHandler = swipeChipbarAwayGestureHandler;
        this.viewUtil = viewUtil;
        this.vibratorHelper = vibratorHelper;
        WindowManager.LayoutParams layoutParams = this.commonWindowLayoutParams;
        layoutParams.gravity = 49;
        this.windowLayoutParams = layoutParams;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void animateViewIn$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final ViewGroup viewGroup) {
        Runnable runnable = new Runnable() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewIn$onAnimationEnd$1
            @Override // java.lang.Runnable
            public final void run() {
                ChipbarCoordinator chipbarCoordinator = this.this$0;
                ChipbarInfo chipbarInfo = (ChipbarInfo) viewGroup.getTag(R.id.tag_chipbar_info);
                ViewGroup viewGroup2 = viewGroup;
                VibrationAttributes vibrationAttributes = ChipbarCoordinator.VIBRATION_ATTRIBUTES;
                chipbarCoordinator.getClass();
                if ((chipbarInfo != null ? chipbarInfo.endItem : null) instanceof ChipbarEndItem.Button) {
                    ((ViewGroup) viewGroup2.requireViewById(R.id.chipbar_inner)).requestAccessibilityFocus();
                } else {
                    ((ViewGroup) viewGroup2.requireViewById(R.id.chipbar_inner)).clearAccessibilityFocus();
                }
            }
        };
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        this.chipbarAnimator.getClass();
        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
        ViewHierarchyAnimator.Hotspot hotspot = ViewHierarchyAnimator.Hotspot.TOP;
        Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
        Interpolator interpolator2 = ViewHierarchyAnimator.DEFAULT_FADE_IN_INTERPOLATOR;
        companion.getClass();
        if (ViewHierarchyAnimator.Companion.occupiesSpace(viewGroup2.getVisibility(), viewGroup2.getLeft(), viewGroup2.getTop(), viewGroup2.getRight(), viewGroup2.getBottom())) {
            ChipbarLogger chipbarLogger = (ChipbarLogger) this.logger;
            chipbarLogger.getClass();
            LogLevel logLevel = LogLevel.WARNING;
            TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = chipbarLogger.buffer;
            logBuffer.commit(logBuffer.obtain(chipbarLogger.tag, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null));
            ChipbarAnimator.forceDisplayView((ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner));
            runnable.run();
            return;
        }
        ViewHierarchyAnimator.Companion.addListener$default(companion, viewGroup2, new ViewHierarchyAnimator$Companion$createListener$1(hotspot, false, interpolator, 500L, true, runnable), true);
        long j = 500 / 6;
        ViewHierarchyAnimator.Companion.createAndStartFadeInAnimator(viewGroup2, j, 0L, interpolator2);
        long j2 = 500 / 3;
        int childCount = viewGroup2.getChildCount();
        for (int i = 0; i < childCount; i++) {
            long j3 = j;
            View childAt = viewGroup2.getChildAt(i);
            childAt.getClass();
            ViewHierarchyAnimator.Companion.createAndStartFadeInAnimator(childAt, j2, j3, interpolator2);
            j = j3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1] */
    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void animateViewOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ViewGroup viewGroup, String str, final TemporaryViewDisplayController$removeViewFromWindow$1 temporaryViewDisplayController$removeViewFromWindow$1) {
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        viewGroup2.setAccessibilityLiveRegion(0);
        ?? r5 = new Runnable() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                ObjectAnimator objectAnimator;
                ChipbarCoordinator chipbarCoordinator = this.this$0;
                VibrationAttributes vibrationAttributes = ChipbarCoordinator.VIBRATION_ATTRIBUTES;
                ChipbarCoordinator.LoadingDetails loadingDetails = chipbarCoordinator.loadingDetails;
                if (loadingDetails != null && (objectAnimator = loadingDetails.animator) != null) {
                    objectAnimator.cancel();
                }
                chipbarCoordinator.loadingDetails = null;
                temporaryViewDisplayController$removeViewFromWindow$1.run();
            }
        };
        this.chipbarAnimator.getClass();
        if (!ViewHierarchyAnimator.Companion.animateRemoval(viewGroup2, ViewHierarchyAnimator.Hotspot.TOP, Interpolators.EMPHASIZED_ACCELERATE, r5)) {
            ChipbarLogger chipbarLogger = (ChipbarLogger) this.logger;
            chipbarLogger.getClass();
            LogLevel logLevel = LogLevel.WARNING;
            TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = chipbarLogger.buffer;
            logBuffer.commit(logBuffer.obtain(chipbarLogger.tag, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null));
            r5.run();
        }
        updateGestureListening$1();
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void getTouchableRegion(Rect rect, View view) {
        this.viewUtil.setRectToViewWindowLocation(view, rect);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final WindowManager.LayoutParams getWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.windowLayoutParams;
    }

    public final void updateGestureListening$1() {
        final TemporaryViewDisplayController.DisplayInfo displayInfo = (TemporaryViewDisplayController.DisplayInfo) CollectionsKt___CollectionsKt.getOrNull(0, this.activeViews);
        SwipeChipbarAwayGestureHandler swipeChipbarAwayGestureHandler = this.swipeChipbarAwayGestureHandler;
        if (displayInfo != null && ((ChipbarInfo) displayInfo.info).allowSwipeToDismiss) {
            swipeChipbarAwayGestureHandler.viewFetcher = new Function0() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    VibrationAttributes vibrationAttributes = ChipbarCoordinator.VIBRATION_ATTRIBUTES;
                    return displayInfo.view;
                }
            };
            swipeChipbarAwayGestureHandler.addOnGestureDetectedCallback("ChipbarCoordinator", new Function1() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    VibrationAttributes vibrationAttributes = ChipbarCoordinator.VIBRATION_ATTRIBUTES;
                    ChipbarCoordinator chipbarCoordinator = this.f$0;
                    TemporaryViewDisplayController.DisplayInfo displayInfo2 = (TemporaryViewDisplayController.DisplayInfo) CollectionsKt___CollectionsKt.getOrNull(0, chipbarCoordinator.activeViews);
                    TemporaryViewLogger temporaryViewLogger = chipbarCoordinator.logger;
                    if (displayInfo2 == null) {
                        ChipbarLogger chipbarLogger = (ChipbarLogger) temporaryViewLogger;
                        chipbarLogger.getClass();
                        LogLevel logLevel = LogLevel.WARNING;
                        ChipbarLogger$$ExternalSyntheticLambda0 chipbarLogger$$ExternalSyntheticLambda0 = new ChipbarLogger$$ExternalSyntheticLambda0(1);
                        LogBuffer logBuffer = chipbarLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain(chipbarLogger.tag, logLevel, chipbarLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = null;
                        logMessageImpl.str2 = "No info is being displayed";
                        logBuffer.commit(logMessageObtain);
                    } else {
                        ChipbarInfo chipbarInfo = (ChipbarInfo) displayInfo2.info;
                        if (chipbarInfo.allowSwipeToDismiss) {
                            chipbarCoordinator.tempViewUiEventLogger.logger.log(TemporaryViewUiEvent.TEMPORARY_VIEW_MANUALLY_DISMISSED, chipbarInfo.instanceId);
                            chipbarCoordinator.removeView(((ChipbarInfo) displayInfo2.info).id, "SWIPE_UP_GESTURE_DETECTED");
                            chipbarCoordinator.updateGestureListening$1();
                        } else {
                            ChipbarLogger chipbarLogger2 = (ChipbarLogger) temporaryViewLogger;
                            chipbarLogger2.getClass();
                            LogLevel logLevel2 = LogLevel.WARNING;
                            ChipbarLogger$$ExternalSyntheticLambda0 chipbarLogger$$ExternalSyntheticLambda02 = new ChipbarLogger$$ExternalSyntheticLambda0(1);
                            LogBuffer logBuffer2 = chipbarLogger2.buffer;
                            LogMessage logMessageObtain2 = logBuffer2.obtain(chipbarLogger2.tag, logLevel2, chipbarLogger$$ExternalSyntheticLambda02, null);
                            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                            logMessageImpl2.str1 = chipbarInfo.id;
                            logMessageImpl2.str2 = "This view prohibits swipe-to-dismiss";
                            logBuffer2.commit(logMessageObtain2);
                        }
                    }
                    return Unit.INSTANCE;
                }
            });
        } else {
            swipeChipbarAwayGestureHandler.getClass();
            swipeChipbarAwayGestureHandler.viewFetcher = new Function0() { // from class: com.android.systemui.temporarydisplay.chipbar.SwipeChipbarAwayGestureHandler$resetViewFetcher$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return null;
                }
            };
            swipeChipbarAwayGestureHandler.removeOnGestureDetectedCallback("ChipbarCoordinator");
        }
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void updateView(TemporaryViewInfo temporaryViewInfo, ViewGroup viewGroup) {
        String strM;
        ObjectAnimator objectAnimator;
        String strM2;
        ObjectAnimator objectAnimator2;
        int i = 0;
        final ChipbarInfo chipbarInfo = (ChipbarInfo) temporaryViewInfo;
        updateGestureListening$1();
        ChipbarLogger chipbarLogger = (ChipbarLogger) this.logger;
        String str = chipbarInfo.windowTitle;
        Text.Companion companion = Text.Companion;
        Context context = this.context;
        companion.getClass();
        Text text = chipbarInfo.text;
        String strLoadText = Text.Companion.loadText(text, context);
        ChipbarEndItem chipbarEndItem = chipbarInfo.endItem;
        if (chipbarEndItem == null) {
            strM = "null";
        } else if (chipbarEndItem instanceof ChipbarEndItem.Loading) {
            strM = "loading";
        } else if (chipbarEndItem instanceof ChipbarEndItem.Error) {
            strM = "error";
        } else {
            if (!(chipbarEndItem instanceof ChipbarEndItem.Button)) {
                throw new NoWhenBranchMatchedException();
            }
            strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("button(", Text.Companion.loadText(((ChipbarEndItem.Button) chipbarEndItem).text, this.context), ")");
        }
        chipbarLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ChipbarLogger$$ExternalSyntheticLambda0 chipbarLogger$$ExternalSyntheticLambda0 = new ChipbarLogger$$ExternalSyntheticLambda0(i);
        String str2 = chipbarLogger.tag;
        LogBuffer logBuffer = chipbarLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str2, logLevel, chipbarLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = strLoadText;
        logMessageImpl.str3 = strM;
        logBuffer.commit(logMessageObtain);
        viewGroup.setTag(R.id.tag_chipbar_info, chipbarInfo);
        ChipbarRootView chipbarRootView = (ChipbarRootView) viewGroup.requireViewById(R.id.chipbar_root_view);
        if (chipbarRootView == null) {
            chipbarRootView = null;
        }
        chipbarRootView.touchHandler = new AnonymousClass1();
        CachingIconView cachingIconViewRequireViewById = viewGroup.requireViewById(R.id.start_icon);
        TintedIconViewBinder.INSTANCE.getClass();
        IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
        TintedIcon tintedIcon = chipbarInfo.startIcon;
        Icon icon = tintedIcon.icon;
        iconViewBinder.getClass();
        IconViewBinder.bind(icon, cachingIconViewRequireViewById);
        Integer num = tintedIcon.tint;
        cachingIconViewRequireViewById.setImageTintList(num != null ? Utils.getColorAttr(num.intValue(), cachingIconViewRequireViewById.getContext()) : null);
        TextView textView = (TextView) viewGroup.requireViewById(R.id.text);
        TextViewBinder.INSTANCE.getClass();
        TextViewBinder.bind(textView, text);
        textView.requestLayout();
        boolean zAreEqual = Intrinsics.areEqual(chipbarEndItem, ChipbarEndItem.Loading.INSTANCE);
        ImageView imageView = (ImageView) viewGroup.requireViewById(R.id.loading);
        imageView.setVisibility(zAreEqual ? 0 : 8);
        if (zAreEqual) {
            LoadingDetails loadingDetails = this.loadingDetails;
            if (loadingDetails == null || !Intrinsics.areEqual(loadingDetails.loadingView, imageView)) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) View.ROTATION, 0.0f, 360.0f);
                objectAnimatorOfFloat.setDuration(1000L);
                objectAnimatorOfFloat.setRepeatCount(-1);
                objectAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
                LoadingDetails loadingDetails2 = new LoadingDetails(imageView, objectAnimatorOfFloat);
                loadingDetails2.animator.start();
                LoadingDetails loadingDetails3 = this.loadingDetails;
                if (loadingDetails3 != null && (objectAnimator2 = loadingDetails3.animator) != null) {
                    objectAnimator2.cancel();
                }
                this.loadingDetails = loadingDetails2;
            }
        } else {
            LoadingDetails loadingDetails4 = this.loadingDetails;
            if (loadingDetails4 != null && (objectAnimator = loadingDetails4.animator) != null) {
                objectAnimator.cancel();
            }
            this.loadingDetails = null;
        }
        viewGroup.requireViewById(R.id.error).setVisibility(Intrinsics.areEqual(chipbarEndItem, ChipbarEndItem.Error.INSTANCE) ? 0 : 8);
        TextView textView2 = (TextView) viewGroup.requireViewById(R.id.end_button);
        boolean z = chipbarEndItem instanceof ChipbarEndItem.Button;
        if (z) {
            TextViewBinder.bind(textView2, ((ChipbarEndItem.Button) chipbarEndItem).text);
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$updateView$onClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (this.this$0.falsingManager.isFalseTap(1)) {
                        return;
                    }
                    ((ChipbarEndItem.Button) chipbarInfo.endItem).onClickListener.onClick(view);
                }
            });
            textView2.setVisibility(0);
        } else {
            textView2.setVisibility(8);
        }
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        viewGroup2.setPaddingRelative(viewGroup2.getPaddingStart(), viewGroup2.getPaddingTop(), viewGroup2.getContext().getResources().getDimensionPixelSize(z ? R.dimen.chipbar_outer_padding_half : R.dimen.chipbar_outer_padding), viewGroup2.getPaddingBottom());
        ContentDescription contentDescription = tintedIcon.icon.getContentDescription();
        if (contentDescription != null) {
            ContentDescription.Companion companion2 = ContentDescription.Companion;
            Context context2 = this.context;
            companion2.getClass();
            strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(ContentDescription.Companion.loadContentDescription(contentDescription, context2), " ");
        } else {
            strM2 = "";
        }
        String strM3 = chipbarEndItem instanceof ChipbarEndItem.Loading ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m(". ", this.context.getResources().getString(R.string.media_transfer_loading), ".") : "";
        ViewGroup viewGroup3 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        viewGroup3.setContentDescription(strM2 + Text.Companion.loadText(text, this.context) + strM3);
        viewGroup3.setAccessibilityLiveRegion(2);
        viewGroup3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator.updateView.2
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setMinDurationBetweenContentChanges(Duration.ofMillis(1000L));
            }
        });
        if (chipbarEndItem instanceof ChipbarEndItem.Button) {
            ((ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner)).requestAccessibilityFocus();
        } else {
            ((ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner)).clearAccessibilityFocus();
        }
        final VibrationEffect vibrationEffect = chipbarInfo.vibrationEffect;
        if (vibrationEffect != null) {
            final int iMyUid = Process.myUid();
            final String packageName = this.context.getApplicationContext().getPackageName();
            final VibrationAttributes vibrationAttributes = VIBRATION_ATTRIBUTES;
            final VibratorHelper vibratorHelper = this.vibratorHelper;
            final String str3 = chipbarInfo.windowTitle;
            if (vibratorHelper.hasVibrator()) {
                vibratorHelper.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        VibratorHelper vibratorHelper2 = vibratorHelper;
                        vibratorHelper2.mVibrator.vibrate(iMyUid, packageName, vibrationEffect, str3, vibrationAttributes);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void getLoadingDetails$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
