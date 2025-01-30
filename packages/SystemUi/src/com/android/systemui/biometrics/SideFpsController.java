package com.android.systemui.biometrics;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.ISidefpsController;
import android.os.Handler;
import android.os.Trace;
import android.util.RotationUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SideFpsController implements Dumpable {
    public final ActivityTaskManager activityTaskManager;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final Context context;
    public final DisplayInfo displayInfo;
    public final DisplayStateInteractor displayStateInteractor;
    public final Handler handler;
    public final boolean isReverseDefaultRotation;
    public final LayoutInflater layoutInflater;
    public final DelayableExecutor mainExecutor;
    public final BiometricDisplayListener orientationListener;
    public final OrientationReasonListener orientationReasonListener;
    public SensorLocationInternal overlayOffsets;
    public View overlayView;
    public final WindowManager.LayoutParams overlayViewParams;
    public final HashSet requests = new HashSet();
    public final CoroutineScope scope;
    public final FingerprintSensorPropertiesInternal sensorProps;
    public final WindowManager windowManager;

    public SideFpsController(Context context, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, ActivityTaskManager activityTaskManager, DisplayManager displayManager, DisplayStateInteractor displayStateInteractor, DelayableExecutor delayableExecutor, Handler handler, AlternateBouncerInteractor alternateBouncerInteractor, CoroutineScope coroutineScope, DumpManager dumpManager) {
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal;
        Object obj;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.windowManager = windowManager;
        this.activityTaskManager = activityTaskManager;
        this.displayStateInteractor = displayStateInteractor;
        this.mainExecutor = delayableExecutor;
        this.handler = handler;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.scope = coroutineScope;
        if (fingerprintManager != null) {
            List sensorPropertiesInternal = fingerprintManager.getSensorPropertiesInternal();
            if (sensorPropertiesInternal != null) {
                Iterator it = sensorPropertiesInternal.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    } else {
                        obj = it.next();
                        if (((FingerprintSensorPropertiesInternal) obj).isAnySidefpsType()) {
                            break;
                        }
                    }
                }
                fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) obj;
            } else {
                fingerprintSensorPropertiesInternal = null;
            }
            if (fingerprintSensorPropertiesInternal != null) {
                this.sensorProps = fingerprintSensorPropertiesInternal;
                OrientationReasonListener orientationReasonListener = new OrientationReasonListener(this.context, displayManager, this.handler, fingerprintSensorPropertiesInternal, new Function1() { // from class: com.android.systemui.biometrics.SideFpsController$orientationReasonListener$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        int intValue = ((Number) obj2).intValue();
                        SideFpsController sideFpsController = SideFpsController.this;
                        if (sideFpsController.overlayView != null) {
                            sideFpsController.createOverlayForDisplay(intValue);
                        }
                        return Unit.INSTANCE;
                    }
                }, 0);
                this.orientationReasonListener = orientationReasonListener;
                this.orientationListener = orientationReasonListener.orientationListener;
                this.isReverseDefaultRotation = this.context.getResources().getBoolean(R.bool.config_notificationHeaderClickableForExpand);
                this.overlayOffsets = SensorLocationInternal.DEFAULT;
                this.displayInfo = new DisplayInfo();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 16777512, -3);
                layoutParams.setTitle("SideFpsController");
                layoutParams.setFitInsetsTypes(0);
                layoutParams.gravity = 51;
                layoutParams.layoutInDisplayCutoutMode = 3;
                layoutParams.privateFlags = 536870976;
                this.overlayViewParams = layoutParams;
                fingerprintManager.setSidefpsController(new ISidefpsController.Stub() { // from class: com.android.systemui.biometrics.SideFpsController.1
                    public final void hide(int i) {
                        SideFpsController.this.hide(SideFpsUiRequestSource.AUTO_SHOW);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:13:0x002c, code lost:
                    
                        if (kotlin.jvm.internal.Intrinsics.areEqual(r3, "com.android.settings.biometrics.fingerprint.FingerprintSettings") != false) goto L17;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:16:0x0031  */
                    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void show(int i, int i2) {
                        boolean z;
                        ComponentName componentName;
                        ActivityTaskManager activityTaskManager2 = SideFpsController.this.activityTaskManager;
                        if (i2 != 4) {
                            z = true;
                            if (i2 == 6) {
                                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(activityTaskManager2.getTasks(1));
                                String className = (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName();
                                if (className == null) {
                                    className = "";
                                }
                            }
                            if (z) {
                                SideFpsController.this.hide(SideFpsUiRequestSource.AUTO_SHOW);
                                return;
                            } else {
                                SideFpsController.this.show(SideFpsUiRequestSource.AUTO_SHOW, i2);
                                return;
                            }
                        }
                        z = false;
                        if (z) {
                        }
                    }
                });
                ((KeyguardBouncerRepositoryImpl) this.alternateBouncerInteractor.bouncerRepository)._alternateBouncerUIAvailable.setValue(Boolean.TRUE);
                BuildersKt.launch$default(this.scope, null, null, new SideFpsController$listenForAlternateBouncerVisibility$1(this, null), 3);
                dumpManager.registerDumpable(this);
                return;
            }
        }
        throw new IllegalStateException("no side fingerprint sensor");
    }

    public final void createOverlayForDisplay(final int i) {
        final View inflate = this.layoutInflater.inflate(com.android.systemui.R.layout.sidefps_view, (ViewGroup) null, false);
        setOverlayView(inflate);
        final Context context = this.context;
        final Display display = context.getDisplay();
        Intrinsics.checkNotNull(display);
        DisplayInfo displayInfo = this.displayInfo;
        display.getDisplayInfo(displayInfo);
        String uniqueId = display.getUniqueId();
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.sensorProps;
        SensorLocationInternal location = fingerprintSensorPropertiesInternal.getLocation(uniqueId);
        if (location == null) {
            MotionLayout$$ExternalSyntheticOutline0.m23m("No location specified for display: ", display.getUniqueId(), "SideFpsController");
        }
        if (location == null) {
            location = fingerprintSensorPropertiesInternal.getLocation();
        }
        this.overlayOffsets = location;
        final LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(com.android.systemui.R.id.sidefps_animation);
        boolean z = location.sensorLocationY != 0;
        int i2 = displayInfo.rotation;
        boolean z2 = this.isReverseDefaultRotation;
        if (z2) {
            i2 = (i2 + 1) % 4;
        }
        inflate.setRotation((i2 == 1 ? !z : i2 == 2 || (i2 == 3 && z)) ? 180.0f : 0.0f);
        boolean z3 = location.sensorLocationY != 0;
        int i3 = displayInfo.rotation;
        if (z2) {
            i3 = (i3 + 1) % 4;
        }
        lottieAnimationView.setAnimation((i3 == 0 ? !z3 : i3 == 2 ? !z3 : z3) ? com.android.systemui.R.raw.sfps_pulse_landscape : com.android.systemui.R.raw.sfps_pulse);
        LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener = new LottieOnCompositionLoadedListener() { // from class: com.android.systemui.biometrics.SideFpsController$createOverlayForDisplay$1
            @Override // com.airbnb.lottie.LottieOnCompositionLoadedListener
            public final void onCompositionLoaded(LottieComposition lottieComposition) {
                SideFpsController sideFpsController = SideFpsController.this;
                View view = sideFpsController.overlayView;
                if (view == null || !Intrinsics.areEqual(view, inflate)) {
                    return;
                }
                sideFpsController.updateOverlayParams(display, lottieComposition.bounds);
            }
        };
        LottieComposition lottieComposition = lottieAnimationView.composition;
        if (lottieComposition != null) {
            lottieOnCompositionLoadedListener.onCompositionLoaded(lottieComposition);
        }
        ((HashSet) lottieAnimationView.lottieOnCompositionLoadedListeners).add(lottieOnCompositionLoadedListener);
        this.orientationReasonListener.reason = i;
        if (lottieAnimationView.composition != null) {
            SideFpsControllerKt.addOverlayDynamicColor$update(i, context, lottieAnimationView);
        } else {
            LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener2 = new LottieOnCompositionLoadedListener() { // from class: com.android.systemui.biometrics.SideFpsControllerKt$addOverlayDynamicColor$1
                @Override // com.airbnb.lottie.LottieOnCompositionLoadedListener
                public final void onCompositionLoaded(LottieComposition lottieComposition2) {
                    SideFpsControllerKt.addOverlayDynamicColor$update(i, context, lottieAnimationView);
                }
            };
            LottieComposition lottieComposition2 = lottieAnimationView.composition;
            if (lottieComposition2 != null) {
                lottieOnCompositionLoadedListener2.onCompositionLoaded(lottieComposition2);
            }
            ((HashSet) lottieAnimationView.lottieOnCompositionLoadedListeners).add(lottieOnCompositionLoadedListener2);
        }
        inflate.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.biometrics.SideFpsController$createOverlayForDisplay$2
            @Override // android.view.View.AccessibilityDelegate
            public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                if (accessibilityEvent.getEventType() == 32) {
                    return true;
                }
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ReadonlyStateFlow readonlyStateFlow;
        printWriter.println("requests:");
        Iterator it = this.requests.iterator();
        while (it.hasNext()) {
            printWriter.println("     " + ((SideFpsUiRequestSource) it.next()) + ".name");
        }
        printWriter.println("overlayView:");
        View view = this.overlayView;
        printWriter.println("     width=" + (view != null ? Integer.valueOf(view.getWidth()) : null));
        View view2 = this.overlayView;
        printWriter.println("     height=" + (view2 != null ? Integer.valueOf(view2.getHeight()) : null));
        View view3 = this.overlayView;
        printWriter.println("     boundsOnScreen=" + (view3 != null ? ConvenienceExtensionsKt.getBoundsOnScreen(view3) : null));
        printWriter.println("displayStateInteractor:");
        DisplayStateInteractor displayStateInteractor = this.displayStateInteractor;
        printWriter.println("     isInRearDisplayMode=" + ((displayStateInteractor == null || (readonlyStateFlow = ((DisplayStateInteractorImpl) displayStateInteractor).isInRearDisplayMode) == null) ? null : (Boolean) readonlyStateFlow.getValue()));
        printWriter.println("sensorProps:");
        DisplayInfo displayInfo = this.displayInfo;
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("     displayId=", displayInfo.uniqueId, printWriter);
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.sensorProps;
        printWriter.println("     sensorType=" + (fingerprintSensorPropertiesInternal != null ? Integer.valueOf(fingerprintSensorPropertiesInternal.sensorType) : null));
        printWriter.println("     location=" + (fingerprintSensorPropertiesInternal != null ? fingerprintSensorPropertiesInternal.getLocation(displayInfo.uniqueId) : null));
        printWriter.println("overlayOffsets=" + this.overlayOffsets);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("isReverseDefaultRotation="), this.isReverseDefaultRotation, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("currentRotation=", displayInfo.rotation, printWriter);
    }

    public final void hide(final SideFpsUiRequestSource sideFpsUiRequestSource) {
        this.requests.remove(sideFpsUiRequestSource);
        ((ExecutorImpl) this.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.biometrics.SideFpsController$hide$1
            @Override // java.lang.Runnable
            public final void run() {
                if (SideFpsController.this.requests.isEmpty()) {
                    String m29m = PathParser$$ExternalSyntheticOutline0.m29m("SideFpsController#hide(", sideFpsUiRequestSource.name(), ")");
                    SideFpsController sideFpsController = SideFpsController.this;
                    if (!Trace.isTagEnabled(4096L)) {
                        sideFpsController.setOverlayView(null);
                        return;
                    }
                    Trace.traceBegin(4096L, m29m);
                    try {
                        sideFpsController.setOverlayView(null);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        Trace.traceEnd(4096L);
                    }
                }
            }
        });
    }

    public final void setOverlayView(View view) {
        View view2 = this.overlayView;
        BiometricDisplayListener biometricDisplayListener = this.orientationListener;
        WindowManager windowManager = this.windowManager;
        if (view2 != null) {
            ((LottieAnimationView) view2.findViewById(com.android.systemui.R.id.sidefps_animation)).pauseAnimation();
            windowManager.removeView(view2);
            biometricDisplayListener.displayManager.unregisterDisplayListener(biometricDisplayListener);
        }
        this.overlayView = view;
        if (view != null) {
            windowManager.addView(view, this.overlayViewParams);
            biometricDisplayListener.enable();
        }
    }

    public final void show(final SideFpsUiRequestSource sideFpsUiRequestSource, final int i) {
        if (((Boolean) ((DisplayStateInteractorImpl) this.displayStateInteractor).isInRearDisplayMode.getValue()).booleanValue()) {
            return;
        }
        this.requests.add(sideFpsUiRequestSource);
        ((ExecutorImpl) this.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.biometrics.SideFpsController$show$1
            @Override // java.lang.Runnable
            public final void run() {
                if (SideFpsController.this.overlayView == null) {
                    String str = "SideFpsController#show(request=" + sideFpsUiRequestSource.name() + ", reason=" + i + ")";
                    SideFpsController sideFpsController = SideFpsController.this;
                    int i2 = i;
                    if (!Trace.isTagEnabled(4096L)) {
                        sideFpsController.createOverlayForDisplay(i2);
                        return;
                    }
                    Trace.traceBegin(4096L, str);
                    try {
                        sideFpsController.createOverlayForDisplay(i2);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        Trace.traceEnd(4096L);
                    }
                }
            }
        });
    }

    public final void updateOverlayParams(Display display, Rect rect) {
        Rect rect2;
        boolean z = display.getRotation() == 0 || display.getRotation() == 2;
        boolean z2 = this.isReverseDefaultRotation;
        if (z2) {
            z = !z;
        }
        WindowManager windowManager = this.windowManager;
        Rect bounds = windowManager.getMaximumWindowMetrics().getBounds();
        int width = z ? bounds.width() : bounds.height();
        int height = z ? bounds.height() : bounds.width();
        int width2 = z ? rect.width() : rect.height();
        int height2 = z ? rect.height() : rect.width();
        if (this.overlayOffsets.sensorLocationY != 0) {
            int i = width - width2;
            int i2 = this.overlayOffsets.sensorLocationY;
            rect2 = new Rect(i, i2, width, height2 + i2);
        } else {
            int i3 = this.overlayOffsets.sensorLocationX;
            rect2 = new Rect(i3, 0, width2 + i3, height2);
        }
        Rect rect3 = new Rect(0, 0, width, height);
        int rotation = display.getRotation();
        if (z2) {
            rotation = (rotation + 1) % 4;
        }
        RotationUtils.rotateBounds(rect2, rect3, rotation);
        WindowManager.LayoutParams layoutParams = this.overlayViewParams;
        layoutParams.x = rect2.left;
        layoutParams.y = rect2.top;
        windowManager.updateViewLayout(this.overlayView, layoutParams);
    }

    public static /* synthetic */ void getOrientationListener$annotations() {
    }

    public static /* synthetic */ void getOrientationReasonListener$annotations() {
    }

    public static /* synthetic */ void getOverlayOffsets$annotations() {
    }

    public static /* synthetic */ void getSensorProps$annotations() {
    }
}
