package com.android.keyguard;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Presentation;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.bouncer.ui.binder.ComposeBouncerDependencies;
import com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder;
import com.android.systemui.compose.ComposeInitializer;
import com.android.systemui.facewidget.dex.DexClockController;
import com.android.systemui.facewidget.dex.DexClockControllerCallback;
import com.android.systemui.facewidget.dex.DexClockControllerImpl;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class ConnectedDisplayKeyguardPresentation extends Presentation {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Interpolator SINE_OUT_33;
    public View clock;
    public final ConnectedDisplayKeyguardPresentation$clockChangedListener$1 clockChangedListener;
    public final ClockEventController clockEventController;
    public final ClockRegistry clockRegistry;
    public final Lazy composeBouncerDependencies;
    public final kotlin.Lazy composeView$delegate;
    public final ConnectedDisplayKeyguardPresentation$dexClockChangedCallback$1 dexClockChangedCallback;
    public final DexClockController dexClockController;
    public float distance;
    public ClockFaceController faceController;
    public final KeyguardDisplayManager keyguardDisplayManager;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final ConnectedDisplayKeyguardPresentation$keyguardStateCallback$1 keyguardStateCallback;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ConnectedDisplayKeyguardPresentation$layoutChangeListener$1 layoutChangeListener;
    public int mCurrentSecurityMode;
    public AnimatorSet restoreAnimatorSet;
    public FrameLayout rootView;
    public View secClock;
    public final SelectedUserInteractor selectedUserInteractor;
    public int swipeUnlockRadius;
    public final kotlin.Lazy textView$delegate;
    public final PointF touchDownPos;
    public int touchSlop;
    public final ConnectedDisplayKeyguardPresentation$updateMonitorCallback$1 updateMonitorCallback;
    public final ViewMediatorCallback viewMediatorCallback;
    public final WallpaperManager wallpaperManager;

    public interface Factory {
        ConnectedDisplayKeyguardPresentation create(Display display);
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r3v10, types: [com.android.keyguard.ConnectedDisplayKeyguardPresentation$keyguardStateCallback$1] */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.keyguard.ConnectedDisplayKeyguardPresentation$updateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.keyguard.ConnectedDisplayKeyguardPresentation$dexClockChangedCallback$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.keyguard.ConnectedDisplayKeyguardPresentation$clockChangedListener$1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.keyguard.ConnectedDisplayKeyguardPresentation$layoutChangeListener$1] */
    public ConnectedDisplayKeyguardPresentation(Display display, final Context context, ClockRegistry clockRegistry, ClockEventController clockEventController, Lazy lazy, KeyguardUpdateMonitor keyguardUpdateMonitor, ViewMediatorCallback viewMediatorCallback, SelectedUserInteractor selectedUserInteractor, KeyguardStateController keyguardStateController, KeyguardSecurityModel keyguardSecurityModel, DexClockController dexClockController, WallpaperManager wallpaperManager, KeyguardDisplayManager keyguardDisplayManager) {
        super(context, display, R.style.Theme_SystemUI_KeyguardPresentation, 2009);
        this.clockRegistry = clockRegistry;
        this.clockEventController = clockEventController;
        this.composeBouncerDependencies = lazy;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.viewMediatorCallback = viewMediatorCallback;
        this.selectedUserInteractor = selectedUserInteractor;
        this.keyguardStateController = keyguardStateController;
        this.keyguardSecurityModel = keyguardSecurityModel;
        this.dexClockController = dexClockController;
        this.wallpaperManager = wallpaperManager;
        this.keyguardDisplayManager = keyguardDisplayManager;
        this.touchDownPos = new PointF(-1.0f, -1.0f);
        this.restoreAnimatorSet = new AnimatorSet();
        this.SINE_OUT_33 = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
        this.clockChangedListener = new ClockRegistry.ClockChangeListener(this) { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$clockChangedListener$1
            @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
            public final void onCurrentClockChanged() {
            }
        };
        this.layoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$layoutChangeListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = this.this$0;
                View view2 = connectedDisplayKeyguardPresentation.clock;
                if (view2 != null) {
                    ClockFaceController clockFaceController = connectedDisplayKeyguardPresentation.faceController;
                    if (clockFaceController == null) {
                        clockFaceController = null;
                    }
                    clockFaceController.getEvents().onTargetRegionChanged(new Rect(view2.getLeft(), view2.getTop(), view2.getWidth(), view2.getHeight()));
                }
            }
        };
        final int i = 0;
        this.composeView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = i;
                Context context2 = context;
                switch (i2) {
                    case 0:
                        int i3 = ConnectedDisplayKeyguardPresentation.$r8$clinit;
                        return new FrameLayout(context2, null);
                    default:
                        int i4 = ConnectedDisplayKeyguardPresentation.$r8$clinit;
                        return new TextView(context2, null);
                }
            }
        });
        final int i2 = 1;
        this.textView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i2;
                Context context2 = context;
                switch (i22) {
                    case 0:
                        int i3 = ConnectedDisplayKeyguardPresentation.$r8$clinit;
                        return new FrameLayout(context2, null);
                    default:
                        int i4 = ConnectedDisplayKeyguardPresentation.$r8$clinit;
                        return new TextView(context2, null);
                }
            }
        });
        this.keyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$keyguardStateCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = this.this$0;
                if (((KeyguardStateControllerImpl) connectedDisplayKeyguardPresentation.keyguardStateController).mCanDismissLockScreen) {
                    View view = connectedDisplayKeyguardPresentation.clock;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    connectedDisplayKeyguardPresentation.getComposeView().setVisibility(4);
                    FrameLayout frameLayout = connectedDisplayKeyguardPresentation.rootView;
                    if (frameLayout == null) {
                        frameLayout = null;
                    }
                    frameLayout.setClickable(true);
                }
            }
        };
        this.updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$updateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardGoingAway() {
                ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = this.this$0;
                if (connectedDisplayKeyguardPresentation.keyguardUpdateMonitor.mKeyguardGoingAway) {
                    View view = connectedDisplayKeyguardPresentation.secClock;
                    if (view != null) {
                        view.getClass();
                        view.setVisibility(4);
                    }
                    connectedDisplayKeyguardPresentation.getTextView().setVisibility(4);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                Log.i("ConnectedDisplayKeyguardPresentation", "onSecurityViewChanged() : " + securityMode);
                if (securityMode == KeyguardSecurityModel.SecurityMode.ForgotPassword) {
                    return;
                }
                int i3 = ConnectedDisplayKeyguardPresentation.$r8$clinit;
                ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = this.this$0;
                connectedDisplayKeyguardPresentation.getClass();
                int keyguardConstantSecurityMode = ConnectedDisplayKeyguardPresentation.getKeyguardConstantSecurityMode(securityMode);
                if (connectedDisplayKeyguardPresentation.mCurrentSecurityMode != keyguardConstantSecurityMode) {
                    connectedDisplayKeyguardPresentation.mCurrentSecurityMode = keyguardConstantSecurityMode;
                    if (keyguardConstantSecurityMode == 5) {
                        FrameLayout frameLayout = connectedDisplayKeyguardPresentation.rootView;
                        (frameLayout != null ? frameLayout : null).setClickable(false);
                    } else {
                        FrameLayout frameLayout2 = connectedDisplayKeyguardPresentation.rootView;
                        (frameLayout2 != null ? frameLayout2 : null).setClickable(true);
                    }
                    if (connectedDisplayKeyguardPresentation.getComposeView().getVisibility() == 0) {
                        connectedDisplayKeyguardPresentation.getComposeView().setVisibility(8);
                        connectedDisplayKeyguardPresentation.getTextView().setVisibility(0);
                        DexClockControllerImpl dexClockControllerImpl = (DexClockControllerImpl) connectedDisplayKeyguardPresentation.dexClockController;
                        Log.i(dexClockControllerImpl.tag, "show: ");
                        View view = dexClockControllerImpl.dexClockView;
                        if (view != null) {
                            view.setVisibility(0);
                        }
                    }
                    connectedDisplayKeyguardPresentation.setBottomView();
                }
            }
        };
        this.dexClockChangedCallback = new DexClockControllerCallback() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$dexClockChangedCallback$1
            @Override // com.android.systemui.facewidget.dex.DexClockControllerCallback
            public final void onDexClockChanged(View view) {
                Log.i("ConnectedDisplayKeyguardPresentation", "onDexClockChanged: " + view);
                this.this$0.secClock = view;
            }
        };
    }

    public static final void access$updateChildView(ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation) {
        int i = connectedDisplayKeyguardPresentation.swipeUnlockRadius;
        float f = connectedDisplayKeyguardPresentation.distance;
        float f2 = ((i * 0.8f) - f) / (i * 0.8f);
        float f3 = ((f / (i * 0.8f)) * 0.07000005f) + 1.0f;
        View view = connectedDisplayKeyguardPresentation.secClock;
        if (view != null) {
            view.setAlpha(Math.max(0.0f, Math.min(1.0f, f2)));
            View view2 = connectedDisplayKeyguardPresentation.secClock;
            view2.getClass();
            view2.setScaleX(Math.max(1.0f, Math.min(1.07f, f3)));
            View view3 = connectedDisplayKeyguardPresentation.secClock;
            view3.getClass();
            view3.setScaleY(Math.max(1.0f, Math.min(1.07f, f3)));
        }
        connectedDisplayKeyguardPresentation.getTextView().setAlpha(f2 < 1.0f ? 0.0f : 1.0f);
    }

    public static int getKeyguardConstantSecurityMode(KeyguardSecurityModel.SecurityMode securityMode) {
        int i = securityMode == null ? -1 : WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
        int i2 = 1;
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            i2 = 4;
            if (i != 3) {
                if (i != 4) {
                    return i != 5 ? 5 : 3;
                }
                return 2;
            }
        }
        return i2;
    }

    public final FrameLayout getComposeView() {
        return (FrameLayout) this.composeView$delegate.getValue();
    }

    public final int getCurrentSecurityMode() {
        int selectedUserId = this.selectedUserInteractor.getSelectedUserId();
        if (this.keyguardUpdateMonitor.is2StepVerification()) {
            return 5;
        }
        KeyguardSecurityModel.SecurityMode currentSecurityMode = this.keyguardUpdateMonitor.getCurrentSecurityMode();
        if (currentSecurityMode == KeyguardSecurityModel.SecurityMode.Invalid || currentSecurityMode == KeyguardSecurityModel.SecurityMode.ForgotPassword) {
            currentSecurityMode = this.keyguardSecurityModel.getSecurityMode(selectedUserId);
        }
        int keyguardConstantSecurityMode = getKeyguardConstantSecurityMode(currentSecurityMode);
        if (keyguardConstantSecurityMode != 5 && (this.keyguardUpdateMonitor.getUserHasTrust(selectedUserId) || this.keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock())) {
            return 1;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(keyguardConstantSecurityMode, "getCurrentSecurityMode: securityModeForDex = ", "ConnectedDisplayKeyguardPresentation");
        return keyguardConstantSecurityMode;
    }

    public final TextView getTextView() {
        return (TextView) this.textView$delegate.getValue();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(this.keyguardStateCallback);
            this.keyguardUpdateMonitor.registerCallback(this.updateMonitorCallback);
            return;
        }
        ClockRegistry clockRegistry = this.clockRegistry;
        ConnectedDisplayKeyguardPresentation$clockChangedListener$1 connectedDisplayKeyguardPresentation$clockChangedListener$1 = this.clockChangedListener;
        clockRegistry.f104assert.isMainThread();
        ((ArrayList) clockRegistry.clockChangeListeners).add(connectedDisplayKeyguardPresentation$clockChangedListener$1);
        ClockEventController clockEventController = this.clockEventController;
        View view = this.clock;
        view.getClass();
        clockEventController.registerListeners(view);
        ClockFaceController clockFaceController = this.faceController;
        if (clockFaceController == null) {
            clockFaceController = null;
        }
        clockFaceController.getAnimations().enter();
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FrameLayout frameLayout = new FrameLayout(getContext(), null);
        this.rootView = frameLayout;
        frameLayout.setClipChildren(false);
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            this.mCurrentSecurityMode = getCurrentSecurityMode();
            FrameLayout frameLayout2 = this.rootView;
            if (frameLayout2 == null) {
                frameLayout2 = null;
            }
            frameLayout2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$onCreateV2$1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    ComposeInitializer.INSTANCE.getClass();
                    ComposeInitializer.onAttachedToWindow(view);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    ComposeInitializer.INSTANCE.getClass();
                    ComposeInitializer.onDetachedFromWindow(view);
                }
            });
            DisplayInfo displayInfo = new DisplayInfo();
            getContext().getDisplay().getDisplayInfo(displayInfo);
            float fMin = Math.min(displayInfo.logicalWidth / displayInfo.physicalXDpi, displayInfo.logicalHeight / displayInfo.physicalYDpi);
            this.swipeUnlockRadius = (int) (Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight) * (fMin > 4.5f ? 0.3f : fMin > 3.5f ? 0.4f : fMin > 1.8f ? 0.5f : 0.7f));
            this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            FrameLayout frameLayout3 = this.rootView;
            if (frameLayout3 == null) {
                frameLayout3 = null;
            }
            frameLayout3.setClickable(true);
            FrameLayout frameLayout4 = this.rootView;
            if (frameLayout4 == null) {
                frameLayout4 = null;
            }
            frameLayout4.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$onCreateV2$2
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                    this.this$0.showBouncer();
                    return true;
                }
            });
            FrameLayout frameLayout5 = this.rootView;
            if (frameLayout5 == null) {
                frameLayout5 = null;
            }
            frameLayout5.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.ConnectedDisplayKeyguardPresentation$onCreateV2$3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getActionMasked() == 0) {
                        this.this$0.touchDownPos.x = motionEvent.getRawX();
                        this.this$0.touchDownPos.y = motionEvent.getRawY();
                        View view2 = this.this$0.secClock;
                        if (view2 != null) {
                            view2.getClass();
                            view2.setPivotX(view2.getWidth() / 2.0f);
                            View view3 = this.this$0.secClock;
                            view3.getClass();
                            this.this$0.secClock.getClass();
                            view3.setPivotY(r11.getHeight() * 2.0f);
                        }
                    } else if (motionEvent.getActionMasked() == 2) {
                        ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation = this.this$0;
                        PointF pointF = connectedDisplayKeyguardPresentation.touchDownPos;
                        if (pointF.x == -1.0f || pointF.y == -1.0f) {
                            connectedDisplayKeyguardPresentation.distance = 0.0f;
                        } else {
                            connectedDisplayKeyguardPresentation.distance = (float) Math.sqrt(Math.pow(motionEvent.getRawY() - connectedDisplayKeyguardPresentation.touchDownPos.y, 2.0d) + Math.pow(motionEvent.getRawX() - connectedDisplayKeyguardPresentation.touchDownPos.x, 2.0d));
                        }
                        ConnectedDisplayKeyguardPresentation.access$updateChildView(this.this$0);
                    } else if (motionEvent.getActionMasked() == 1) {
                        ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation2 = this.this$0;
                        int i = connectedDisplayKeyguardPresentation2.touchSlop;
                        float f = connectedDisplayKeyguardPresentation2.distance;
                        int i2 = connectedDisplayKeyguardPresentation2.swipeUnlockRadius;
                        StringBuilder sb = new StringBuilder("onTouchEvent T=");
                        sb.append(i);
                        sb.append(", D=");
                        sb.append(f);
                        sb.append(", R=");
                        RecyclerView$$ExternalSyntheticOutline0.m(i2, "ConnectedDisplayKeyguardPresentation", sb);
                        ConnectedDisplayKeyguardPresentation connectedDisplayKeyguardPresentation3 = this.this$0;
                        float f2 = connectedDisplayKeyguardPresentation3.distance;
                        if (f2 >= connectedDisplayKeyguardPresentation3.touchSlop && connectedDisplayKeyguardPresentation3.swipeUnlockRadius >= f2) {
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.setDuration(400L);
                            animatorSet.setInterpolator(connectedDisplayKeyguardPresentation3.SINE_OUT_33);
                            connectedDisplayKeyguardPresentation3.restoreAnimatorSet = animatorSet;
                            if (connectedDisplayKeyguardPresentation3.secClock != null) {
                                SpringAnimation springAnimation = new SpringAnimation(connectedDisplayKeyguardPresentation3.secClock, DynamicAnimation.SCALE_X);
                                springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(250.0f, 0.7f);
                                springAnimation.animateToFinalPosition(1.0f);
                                SpringAnimation springAnimation2 = new SpringAnimation(connectedDisplayKeyguardPresentation3.secClock, DynamicAnimation.SCALE_Y);
                                springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(250.0f, 0.7f);
                                springAnimation2.animateToFinalPosition(1.0f);
                                animatorSet.start();
                            }
                            connectedDisplayKeyguardPresentation3.restoreAnimatorSet.playTogether(ObjectAnimator.ofFloat(connectedDisplayKeyguardPresentation3.getTextView(), (Property<TextView, Float>) View.ALPHA, connectedDisplayKeyguardPresentation3.getTextView().getAlpha(), 1.0f));
                            this.this$0.reset();
                        } else {
                            connectedDisplayKeyguardPresentation3.showBouncer();
                        }
                    }
                    ConnectedDisplayKeyguardPresentation.access$updateChildView(this.this$0);
                    return false;
                }
            });
        }
        FrameLayout frameLayout6 = this.rootView;
        if (frameLayout6 == null) {
            frameLayout6 = null;
        }
        setContentView(frameLayout6);
        Window window = getWindow();
        if (window == null) {
            throw new IllegalStateException("no window available.");
        }
        window.getDecorView().setSystemUiVisibility(1792);
        window.getAttributes().setFitInsetsTypes(0);
        window.setNavigationBarContrastEnforced(false);
        window.setNavigationBarColor(0);
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            setBottomView();
            DexClockController dexClockController = this.dexClockController;
            FrameLayout frameLayout7 = this.rootView;
            ((DexClockControllerImpl) dexClockController).initDexClock(frameLayout7 != null ? frameLayout7 : null, getDisplay(), this.dexClockChangedCallback, false);
        } else {
            ClockController clockControllerCreateCurrentClock = this.clockRegistry.createCurrentClock();
            if (!this.keyguardDisplayManager.isExternalDesktopWindowing()) {
                View view = this.clock;
                if (view != null) {
                    view.removeOnLayoutChangeListener(this.layoutChangeListener);
                }
                FrameLayout frameLayout8 = this.rootView;
                if (frameLayout8 == null) {
                    frameLayout8 = null;
                }
                frameLayout8.removeAllViews();
                ClockFaceController largeClock = clockControllerCreateCurrentClock.getLargeClock();
                this.faceController = largeClock;
                if (largeClock == null) {
                    largeClock = null;
                }
                View view2 = largeClock.getView();
                view2.addOnLayoutChangeListener(this.layoutChangeListener);
                this.clock = view2;
                FrameLayout frameLayout9 = this.rootView;
                if (frameLayout9 == null) {
                    frameLayout9 = null;
                }
                frameLayout9.addView(view2, new FrameLayout.LayoutParams(!this.keyguardDisplayManager.isExternalDesktopWindowing() ? -2 : getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_presentation_width), -2, 17));
                this.clockEventController.setClock(clockControllerCreateCurrentClock);
                ClockEventController clockEventController = this.clockEventController;
                clockEventController.largeClockOnSecondaryDisplay = true;
                clockEventController.updateFontSizes();
                ClockFaceController clockFaceController = this.faceController;
                (clockFaceController != null ? clockFaceController : null).getEvents().onSecondaryDisplayChanged(true);
            }
        }
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            reset();
        }
        Window window2 = getWindow();
        if (window2 == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window2.getAttributes();
        attributes.flags |= 1048576;
        window2.setAttributes(attributes);
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            window2.getDecorView().setBackgroundColor(0);
        } else {
            window2.getDecorView().setBackgroundColor(-16777216);
            window2.getDecorView().semSetRoundedCorners(0);
        }
        if (this.keyguardDisplayManager.isDesktopMode()) {
            attributes.userActivityTimeout = 10000L;
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver;
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            ((ComposeBouncerDependencies) this.composeBouncerDependencies.get()).keyguardInteractor.setDismissActionForDex(null);
        }
        if (this.keyguardDisplayManager.isExternalDesktopWindowing()) {
            ((KeyguardStateControllerImpl) this.keyguardStateController).removeCallback(this.keyguardStateCallback);
            this.keyguardUpdateMonitor.removeCallback(this.updateMonitorCallback);
        } else {
            ClockEventController clockEventController = this.clockEventController;
            if (clockEventController.isRegistered) {
                clockEventController.isRegistered = false;
                RepeatWhenAttachedKt.C09181 c09181 = clockEventController.disposableHandle;
                if (c09181 != null) {
                    c09181.dispose();
                }
                clockEventController.broadcastDispatcher.unregisterReceiver(clockEventController.localeBroadcastReceiver);
                ((ConfigurationControllerImpl) clockEventController.configurationController).removeCallback(clockEventController.configListener);
                ((BatteryControllerImpl) clockEventController.batteryController).removeCallback(clockEventController.batteryCallback);
                clockEventController.keyguardUpdateMonitor.removeCallback(clockEventController.keyguardUpdateMonitorCallback);
                ((ZenModeControllerImpl) clockEventController.zenModeController).removeCallback(clockEventController.zenModeCallback);
                ClockEventController.TimeListener timeListener = clockEventController.smallTimeListener;
                if (timeListener != null) {
                    timeListener.stop();
                }
                ClockEventController.TimeListener timeListener2 = clockEventController.largeTimeListener;
                if (timeListener2 != null) {
                    timeListener2.stop();
                }
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    clockController.getSmallClock().getView().removeOnAttachStateChangeListener(clockEventController.smallClockOnAttachStateChangeListener);
                    clockController.getLargeClock().getView().removeOnAttachStateChangeListener(clockEventController.largeClockOnAttachStateChangeListener);
                }
                ViewGroup viewGroup = clockEventController.smallClockFrame;
                if (viewGroup != null && (viewTreeObserver = viewGroup.getViewTreeObserver()) != null) {
                    viewTreeObserver.removeOnGlobalLayoutListener(clockEventController.onGlobalLayoutListener);
                }
            }
            ClockRegistry clockRegistry = this.clockRegistry;
            ConnectedDisplayKeyguardPresentation$clockChangedListener$1 connectedDisplayKeyguardPresentation$clockChangedListener$1 = this.clockChangedListener;
            clockRegistry.f104assert.isMainThread();
            ((ArrayList) clockRegistry.clockChangeListeners).remove(connectedDisplayKeyguardPresentation$clockChangedListener$1);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.app.Presentation
    public final void onDisplayChanged() {
        Window window = getWindow();
        if (window == null) {
            throw new IllegalStateException("no window available.");
        }
        window.getDecorView().requestLayout();
    }

    public final void reset() {
        View view = this.secClock;
        if (view != null) {
            view.getClass();
            view.setAlpha(1.0f);
            View view2 = this.secClock;
            view2.getClass();
            view2.setScaleX(1.0f);
            View view3 = this.secClock;
            view3.getClass();
            view3.setScaleY(1.0f);
        }
        getTextView().setAlpha(1.0f);
        this.distance = 0.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setBottomView() {
        Integer numValueOf;
        SemWallpaperColors semWallpaperColorsSemGetWallpaperColors = this.wallpaperManager.semGetWallpaperColors(10);
        if (semWallpaperColorsSemGetWallpaperColors == null) {
            semWallpaperColorsSemGetWallpaperColors = null;
        }
        if (semWallpaperColorsSemGetWallpaperColors != null) {
            SemWallpaperColors.Item item = semWallpaperColorsSemGetWallpaperColors.get(128L);
            Log.i("ConnectedDisplayKeyguardPresentation", "setBottomView: bodyBottomItem = " + item);
            numValueOf = item != null ? Integer.valueOf(item.getFontColor()) : null;
        }
        boolean z = numValueOf == null || numValueOf.intValue() == 1;
        TextView textView = getTextView();
        textView.setTextColor(z ? textView.getContext().getColor(R.color.kg_external_dex_bottom_message_whitebg_color) : textView.getContext().getColor(R.color.kg_external_dex_bottom_message_color));
        textView.setGravity(1);
        textView.setTypeface(Typeface.create(Typeface.create(textView.getContext().getString(R.string.pinlock_numeric_font_family), 0), 400, false));
        textView.setTextSize(0, textView.getContext().getResources().getDimension(R.dimen.kg_compose_bottom_message_text_size));
        textView.setText(this.mCurrentSecurityMode == 5 ? textView.getContext().getResources().getString(R.string.kg_compose_bottom_message_unlock_your_phone) : textView.getContext().getResources().getString(R.string.kg_compose_bottom_message_tap_anywhere_to_unlock));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2, 80);
        layoutParams.gravity = 81;
        layoutParams.bottomMargin = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.11f);
        FrameLayout frameLayout = this.rootView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        if (frameLayout.indexOfChild(getTextView()) != -1) {
            getTextView().setVisibility(0);
        } else {
            FrameLayout frameLayout2 = this.rootView;
            (frameLayout2 != null ? frameLayout2 : null).addView(getTextView(), layoutParams);
        }
    }

    public final void showBouncer() {
        ActivityStarter.OnDismissAction onDismissAction;
        FrameLayout frameLayout = this.rootView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        frameLayout.setBackgroundColor(0);
        int currentSecurityMode = getCurrentSecurityMode();
        if (this.mCurrentSecurityMode != currentSecurityMode) {
            this.mCurrentSecurityMode = currentSecurityMode;
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(this.mCurrentSecurityMode, "showBouncer: ", "ConnectedDisplayKeyguardPresentation");
        FrameLayout frameLayout2 = this.rootView;
        if (frameLayout2 == null) {
            frameLayout2 = null;
        }
        if (frameLayout2.indexOfChild(getComposeView()) != -1) {
            Log.i("ConnectedDisplayKeyguardPresentation", "showBouncer: already shown");
            getComposeView().setVisibility(0);
            getTextView().setVisibility(4);
            DexClockControllerImpl dexClockControllerImpl = (DexClockControllerImpl) this.dexClockController;
            Log.i(dexClockControllerImpl.tag, "hide: ");
            View view = dexClockControllerImpl.dexClockView;
            if (view != null) {
                view.setVisibility(4);
                return;
            }
            return;
        }
        FrameLayout frameLayout3 = this.rootView;
        if (frameLayout3 == null) {
            frameLayout3 = null;
        }
        frameLayout3.setClickable(false);
        int i = this.mCurrentSecurityMode;
        if (i == 2 || i == 3 || i == 4) {
            getTextView().setVisibility(4);
            getComposeView().setVisibility(0);
            DexClockControllerImpl dexClockControllerImpl2 = (DexClockControllerImpl) this.dexClockController;
            Log.i(dexClockControllerImpl2.tag, "hide: ");
            View view2 = dexClockControllerImpl2.dexClockView;
            if (view2 != null) {
                view2.setVisibility(4);
            }
            FrameLayout frameLayout4 = this.rootView;
            (frameLayout4 != null ? frameLayout4 : null).addView(getComposeView(), new FrameLayout.LayoutParams(-1, -1, 17));
            ComposeBouncerDependencies composeBouncerDependencies = (ComposeBouncerDependencies) this.composeBouncerDependencies.get();
            ComposeBouncerViewBinder composeBouncerViewBinder = ComposeBouncerViewBinder.INSTANCE;
            FrameLayout composeView = getComposeView();
            CoroutineScope coroutineScope = composeBouncerDependencies.applicationScope;
            composeBouncerViewBinder.getClass();
            ComposeBouncerViewBinder.bind(composeView, coroutineScope, composeBouncerDependencies.legacyInteractor, composeBouncerDependencies.keyguardInteractor, composeBouncerDependencies.selectedUserInteractor, composeBouncerDependencies.viewModelFactory, composeBouncerDependencies.dialogFactory, composeBouncerDependencies.bouncerContainerViewModelFactory, composeBouncerDependencies.authenticationInteractor, composeBouncerDependencies.viewMediatorCallback);
            return;
        }
        if (i == 5) {
            getComposeView().setVisibility(4);
            View view3 = this.secClock;
            if (view3 != null) {
                view3.setVisibility(0);
            }
            getTextView().setVisibility(0);
            getTextView().setText(getContext().getResources().getString(R.string.kg_compose_bottom_message_unlock_your_phone));
            return;
        }
        SelectedUserInteractor selectedUserInteractor = this.selectedUserInteractor;
        BouncerDismissActionModel bouncerDismissActionModel = ((KeyguardBouncerRepositoryImpl) ((ComposeBouncerDependencies) this.composeBouncerDependencies.get()).keyguardInteractor.bouncerRepository).bouncerDismissActionModelForDex;
        if (bouncerDismissActionModel != null && (onDismissAction = bouncerDismissActionModel.onDismissActionForDex) != null) {
            onDismissAction.onDismiss();
        }
        ((ComposeBouncerDependencies) this.composeBouncerDependencies.get()).keyguardInteractor.setDismissActionForDex(null);
        this.viewMediatorCallback.keyguardDone(selectedUserInteractor.getSelectedUserId());
    }
}
