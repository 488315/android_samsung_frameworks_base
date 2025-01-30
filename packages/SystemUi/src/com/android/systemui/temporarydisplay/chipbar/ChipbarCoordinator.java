package com.android.systemui.temporarydisplay.chipbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.PowerManager;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.CachingIconView;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.common.p004ui.binder.TextViewBinder;
import com.android.systemui.common.p004ui.binder.TintedIconViewBinder;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController$removeViewFromWindow$1;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import com.android.systemui.temporarydisplay.TemporaryViewUiEvent;
import com.android.systemui.temporarydisplay.TemporaryViewUiEventLogger;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarEndItem;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.view.ViewUtil;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChipbarCoordinator extends TemporaryViewDisplayController {
    public static final VibrationAttributes VIBRATION_ATTRIBUTES;
    public final ChipbarAnimator chipbarAnimator;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public LoadingDetails loadingDetails;
    public final SwipeChipbarAwayGestureHandler swipeChipbarAwayGestureHandler;
    public final VibratorHelper vibratorHelper;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static void maybeGetAccessibilityFocus(ChipbarInfo chipbarInfo, ViewGroup viewGroup) {
        if ((chipbarInfo != null ? chipbarInfo.endItem : null) instanceof ChipbarEndItem.Button) {
            ((ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner)).requestAccessibilityFocus();
        } else {
            ((ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner)).clearAccessibilityFocus();
        }
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    /* renamed from: animateViewIn$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo159x25db3cb1(final ViewGroup viewGroup) {
        Runnable runnable = new Runnable() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewIn$onAnimationEnd$1
            @Override // java.lang.Runnable
            public final void run() {
                ChipbarCoordinator chipbarCoordinator = ChipbarCoordinator.this;
                ChipbarInfo chipbarInfo = (ChipbarInfo) viewGroup.getTag(R.id.tag_chipbar_info);
                ViewGroup viewGroup2 = viewGroup;
                VibrationAttributes vibrationAttributes = ChipbarCoordinator.VIBRATION_ATTRIBUTES;
                chipbarCoordinator.getClass();
                ChipbarCoordinator.maybeGetAccessibilityFocus(chipbarInfo, viewGroup2);
            }
        };
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        this.chipbarAnimator.getClass();
        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
        ViewHierarchyAnimator.Hotspot hotspot = ViewHierarchyAnimator.Hotspot.TOP;
        Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
        Interpolator interpolator2 = ViewHierarchyAnimator.DEFAULT_FADE_IN_INTERPOLATOR;
        companion.getClass();
        boolean z = false;
        boolean z2 = true;
        if (!((viewGroup2.getVisibility() == 8 || viewGroup2.getLeft() == viewGroup2.getRight() || viewGroup2.getTop() == viewGroup2.getBottom()) ? false : true)) {
            ViewHierarchyAnimator.Companion.addListener(viewGroup2, new ViewHierarchyAnimator$Companion$createListener$1(hotspot, false, interpolator, 500L, true, runnable), true);
            long j = 500 / 6;
            ViewHierarchyAnimator.Companion.createAndStartFadeInAnimator(viewGroup2, j, 0L, interpolator2);
            long j2 = 500 / 3;
            int childCount = viewGroup2.getChildCount();
            int i = 0;
            while (i < childCount) {
                ViewHierarchyAnimator.Companion.createAndStartFadeInAnimator(viewGroup2.getChildAt(i), j2, j, interpolator2);
                i++;
                z2 = z2;
            }
            z = z2;
        }
        if (z) {
            return;
        }
        ((ChipbarLogger) this.logger).logAnimateInFailure();
        ChipbarAnimator.forceDisplayView((ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner));
        runnable.run();
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1] */
    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    /* renamed from: animateViewOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo160xf3ed518e(ViewGroup viewGroup, String str, final TemporaryViewDisplayController$removeViewFromWindow$1 temporaryViewDisplayController$removeViewFromWindow$1) {
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        viewGroup2.setAccessibilityLiveRegion(0);
        ?? r4 = new Runnable() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                ObjectAnimator objectAnimator;
                ChipbarCoordinator chipbarCoordinator = ChipbarCoordinator.this;
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
        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
        ViewHierarchyAnimator.Hotspot hotspot = ViewHierarchyAnimator.Hotspot.TOP;
        Interpolator interpolator = Interpolators.EMPHASIZED_ACCELERATE;
        companion.getClass();
        if (!ViewHierarchyAnimator.Companion.animateRemoval(viewGroup2, hotspot, interpolator, r4)) {
            ((ChipbarLogger) this.logger).logAnimateOutFailure();
            r4.run();
        }
        updateGestureListening();
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void getTouchableRegion(Rect rect, View view) {
        this.viewUtil.getClass();
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        rect.set(i, i2, view.getWidth() + i, view.getHeight() + i2);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    /* renamed from: getWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final WindowManager.LayoutParams mo161x8e91d99c() {
        return this.windowLayoutParams;
    }

    public final void updateGestureListening() {
        SwipeChipbarAwayGestureHandler swipeChipbarAwayGestureHandler = this.swipeChipbarAwayGestureHandler;
        if (swipeChipbarAwayGestureHandler == null) {
            return;
        }
        final TemporaryViewDisplayController.DisplayInfo displayInfo = (TemporaryViewDisplayController.DisplayInfo) CollectionsKt___CollectionsKt.getOrNull(0, this.activeViews);
        if (displayInfo == null || !((ChipbarInfo) displayInfo.info).allowSwipeToDismiss) {
            swipeChipbarAwayGestureHandler.viewFetcher = new Function0() { // from class: com.android.systemui.temporarydisplay.chipbar.SwipeChipbarAwayGestureHandler$resetViewFetcher$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return null;
                }
            };
            swipeChipbarAwayGestureHandler.removeOnGestureDetectedCallback("ChipbarCoordinator");
            return;
        }
        swipeChipbarAwayGestureHandler.viewFetcher = new Function0() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$updateGestureListening$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TemporaryViewDisplayController.DisplayInfo.this.view;
            }
        };
        Function1 function1 = new Function1() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$updateGestureListening$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ChipbarCoordinator chipbarCoordinator = ChipbarCoordinator.this;
                VibrationAttributes vibrationAttributes = ChipbarCoordinator.VIBRATION_ATTRIBUTES;
                TemporaryViewDisplayController.DisplayInfo displayInfo2 = (TemporaryViewDisplayController.DisplayInfo) CollectionsKt___CollectionsKt.getOrNull(0, chipbarCoordinator.activeViews);
                TemporaryViewLogger temporaryViewLogger = chipbarCoordinator.logger;
                if (displayInfo2 == null) {
                    ChipbarLogger chipbarLogger = (ChipbarLogger) temporaryViewLogger;
                    chipbarLogger.getClass();
                    LogLevel logLevel = LogLevel.WARNING;
                    ChipbarLogger$logSwipeGestureError$2 chipbarLogger$logSwipeGestureError$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarLogger$logSwipeGestureError$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            LogMessage logMessage = (LogMessage) obj2;
                            return FontProvider$$ExternalSyntheticOutline0.m32m("Chipbar swipe gesture detected for incorrect state. id=", logMessage.getStr1(), " error=", logMessage.getStr2());
                        }
                    };
                    LogBuffer logBuffer = chipbarLogger.buffer;
                    LogMessage obtain = logBuffer.obtain(chipbarLogger.tag, logLevel, chipbarLogger$logSwipeGestureError$2, null);
                    CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, null, "No info is being displayed", logBuffer, obtain);
                } else {
                    ChipbarInfo chipbarInfo = (ChipbarInfo) displayInfo2.info;
                    if (chipbarInfo.allowSwipeToDismiss) {
                        chipbarCoordinator.tempViewUiEventLogger.logger.log(TemporaryViewUiEvent.TEMPORARY_VIEW_MANUALLY_DISMISSED, chipbarInfo.instanceId);
                        chipbarCoordinator.removeView(((ChipbarInfo) displayInfo2.info).f362id, "SWIPE_UP_GESTURE_DETECTED");
                        chipbarCoordinator.updateGestureListening();
                    } else {
                        ChipbarLogger chipbarLogger2 = (ChipbarLogger) temporaryViewLogger;
                        chipbarLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.WARNING;
                        ChipbarLogger$logSwipeGestureError$2 chipbarLogger$logSwipeGestureError$22 = new Function1() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarLogger$logSwipeGestureError$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                return FontProvider$$ExternalSyntheticOutline0.m32m("Chipbar swipe gesture detected for incorrect state. id=", logMessage.getStr1(), " error=", logMessage.getStr2());
                            }
                        };
                        LogBuffer logBuffer2 = chipbarLogger2.buffer;
                        LogMessage obtain2 = logBuffer2.obtain(chipbarLogger2.tag, logLevel2, chipbarLogger$logSwipeGestureError$22, null);
                        CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain2, chipbarInfo.f362id, "This view prohibits swipe-to-dismiss", logBuffer2, obtain2);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        Map map = swipeChipbarAwayGestureHandler.callbacks;
        boolean isEmpty = map.isEmpty();
        map.put("ChipbarCoordinator", function1);
        if (isEmpty) {
            swipeChipbarAwayGestureHandler.mo200x8843713a();
        }
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController
    public final void updateView(TemporaryViewInfo temporaryViewInfo, ViewGroup viewGroup) {
        String m29m;
        ObjectAnimator objectAnimator;
        String str;
        String string;
        ObjectAnimator objectAnimator2;
        final ChipbarInfo chipbarInfo = (ChipbarInfo) temporaryViewInfo;
        updateGestureListening();
        ChipbarLogger chipbarLogger = (ChipbarLogger) this.logger;
        Text.Companion companion = Text.Companion;
        Text text = chipbarInfo.text;
        companion.getClass();
        Context context = this.context;
        String loadText = Text.Companion.loadText(text, context);
        ChipbarEndItem chipbarEndItem = chipbarInfo.endItem;
        if (chipbarEndItem == null) {
            m29m = "null";
        } else if (chipbarEndItem instanceof ChipbarEndItem.Loading) {
            m29m = "loading";
        } else if (chipbarEndItem instanceof ChipbarEndItem.Error) {
            m29m = "error";
        } else {
            if (!(chipbarEndItem instanceof ChipbarEndItem.Button)) {
                throw new NoWhenBranchMatchedException();
            }
            m29m = PathParser$$ExternalSyntheticOutline0.m29m("button(", Text.Companion.loadText(((ChipbarEndItem.Button) chipbarEndItem).text, context), ")");
        }
        chipbarLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ChipbarLogger$logViewUpdate$2 chipbarLogger$logViewUpdate$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarLogger$logViewUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Chipbar updated. window=", str1, " text=", str2, " endItem=");
                m87m.append(str3);
                return m87m.toString();
            }
        };
        LogBuffer logBuffer = chipbarLogger.buffer;
        LogMessage obtain = logBuffer.obtain(chipbarLogger.tag, logLevel, chipbarLogger$logViewUpdate$2, null);
        obtain.setStr1(chipbarInfo.windowTitle);
        obtain.setStr2(loadText);
        obtain.setStr3(m29m);
        logBuffer.commit(obtain);
        viewGroup.setTag(R.id.tag_chipbar_info, chipbarInfo);
        ((ChipbarRootView) viewGroup.requireViewById(R.id.chipbar_root_view)).touchHandler = new Gefingerpoken() { // from class: com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$updateView$1
            @Override // com.android.systemui.Gefingerpoken
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                ((FalsingCollectorImpl) ChipbarCoordinator.this.falsingCollector).onTouchEvent(motionEvent);
                return false;
            }
        };
        CachingIconView requireViewById = viewGroup.requireViewById(R.id.start_icon);
        TintedIconViewBinder.INSTANCE.getClass();
        TintedIcon tintedIcon = chipbarInfo.startIcon;
        TintedIconViewBinder.bind(tintedIcon, requireViewById);
        TextView textView = (TextView) viewGroup.requireViewById(R.id.text);
        TextViewBinder.INSTANCE.getClass();
        TextViewBinder.bind(textView, text);
        textView.requestLayout();
        boolean areEqual = Intrinsics.areEqual(chipbarEndItem, ChipbarEndItem.Loading.INSTANCE);
        ImageView imageView = (ImageView) viewGroup.requireViewById(R.id.loading);
        imageView.setVisibility(areEqual ? 0 : 8);
        if (areEqual) {
            LoadingDetails loadingDetails = this.loadingDetails;
            if (loadingDetails == null || !Intrinsics.areEqual(loadingDetails.loadingView, imageView)) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) View.ROTATION, 0.0f, 360.0f);
                ofFloat.setDuration(1000L);
                ofFloat.setRepeatCount(-1);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                LoadingDetails loadingDetails2 = new LoadingDetails(imageView, ofFloat);
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
                    if (ChipbarCoordinator.this.falsingManager.isFalseTap(1)) {
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
            ContentDescription.Companion.getClass();
            if (contentDescription instanceof ContentDescription.Loaded) {
                string = ((ContentDescription.Loaded) contentDescription).description;
            } else {
                if (!(contentDescription instanceof ContentDescription.Resource)) {
                    throw new NoWhenBranchMatchedException();
                }
                string = context.getString(((ContentDescription.Resource) contentDescription).res);
            }
            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(string, " ");
        } else {
            str = "";
        }
        String m29m2 = chipbarEndItem instanceof ChipbarEndItem.Loading ? PathParser$$ExternalSyntheticOutline0.m29m(". ", context.getResources().getString(R.string.media_transfer_loading), ".") : "";
        ViewGroup viewGroup3 = (ViewGroup) viewGroup.requireViewById(R.id.chipbar_inner);
        viewGroup3.setContentDescription(str + Text.Companion.loadText(text, context) + m29m2);
        viewGroup3.setAccessibilityLiveRegion(2);
        maybeGetAccessibilityFocus(chipbarInfo, viewGroup);
        VibrationEffect vibrationEffect = chipbarInfo.vibrationEffect;
        if (vibrationEffect != null) {
            this.vibratorHelper.vibrate(Process.myUid(), context.getApplicationContext().getPackageName(), vibrationEffect, chipbarInfo.windowTitle, VIBRATION_ATTRIBUTES);
        }
    }

    /* renamed from: getLoadingDetails$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m217x4c956722() {
    }
}
