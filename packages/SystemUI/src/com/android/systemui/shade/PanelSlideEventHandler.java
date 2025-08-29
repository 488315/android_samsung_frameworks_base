package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.NonInterceptingScrollView;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.qs.bar.BrightnessVolumeBar;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.qs.bar.VolumeBar;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.sec.ims.settings.ImsProfile;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PanelSlideEventHandler implements ShadeExpansionListener, ConfigurationController.ConfigurationListener, StatusBarStateController.StateListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean canScrollDownOnDown;
    public final Lazy configurationController$delegate;
    public final Context context;
    public Direction direction;
    public float displayRatioOfDivider;
    public int displayWidthOfDivider;
    public final FlingAnimationUtils flingAnimationUtils;
    public boolean fullyExpandedOnDown;
    public Insets gestureInsets;
    public final Lazy headsUpManager$delegate;
    public float initialX;
    public float initialY;
    public SecNotificationPanelViewController$panelSplitHelper$1$1 interceptCallback;
    public boolean isInChangeSpotOnDown;
    public boolean isInGestureArea;
    public boolean isInQsScrollerTopMarginArea;
    public boolean isInSlidableAreaOnDown;
    public final ConfigurationState lastConfigurationState;
    public Locale locale;
    public final StringBuilder logBuilder;
    public float maxDragWidth;
    public float panelExpandFraction;
    public boolean panelExpanded;
    public boolean panelFullyExpanded;
    public SecPanelSplitHelper$panelSlideEventHandler$1$1 panelSlideEventCallback;
    public boolean panelSliderIntercepted;
    public boolean panelSplitEnabled;
    public int panelWidth;
    public final Lazy resourcePicker$delegate;
    public final Lazy secPanelSAStatusLogInteractor$delegate;
    public final SecPanelSplitHelper secPanelSplitHelper;
    public final Lazy secQuickSettingsControllerImpl$delegate;
    public final Lazy settingsHelper$delegate;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final Lazy shadeHeaderController$delegate;
    public ValueAnimator sliderAnimator;
    public boolean slidingInitialized;
    public final Lazy statusBarStateController$delegate;
    public final int touchSlop;
    public boolean tracking;
    public final VelocityTracker velocityTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Direction {
        public static final /* synthetic */ Direction[] $VALUES;
        public static final Direction DOWN;
        public static final Direction LEFT;
        public static final Direction RIGHT;
        public static final Direction UNDECIDED;
        public static final Direction UP;

        static {
            Direction direction = new Direction("LEFT", 0);
            LEFT = direction;
            Direction direction2 = new Direction("RIGHT", 1);
            RIGHT = direction2;
            Direction direction3 = new Direction("DOWN", 2);
            DOWN = direction3;
            Direction direction4 = new Direction(ImsProfile.RCS_PROFILE_UP, 3);
            UP = direction4;
            Direction direction5 = new Direction("UNDECIDED", 4);
            UNDECIDED = direction5;
            Direction[] directionArr = {direction, direction2, direction3, direction4, direction5};
            $VALUES = directionArr;
            EnumEntriesKt.enumEntries(directionArr);
        }

        private Direction(String str, int i) {
        }

        public static Direction valueOf(String str) {
            return (Direction) Enum.valueOf(Direction.class, str);
        }

        public static Direction[] values() {
            return (Direction[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Direction.values().length];
            try {
                iArr[Direction.UP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Direction.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Direction.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Direction.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public PanelSlideEventHandler(Context context, ShadeExpansionStateManager shadeExpansionStateManager, final QuickSettingsControllerImpl quickSettingsControllerImpl, SecPanelSplitHelper secPanelSplitHelper) {
        this.context = context;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.secPanelSplitHelper = secPanelSplitHelper;
        final int i = 0;
        this.shadeHeaderController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i3 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i4 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i5 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i6 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i7 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        this.secQuickSettingsControllerImpl$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = PanelSlideEventHandler.$r8$clinit;
                return quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
            }
        });
        final int i2 = 1;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i3 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i4 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i5 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i6 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i7 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        this.statusBarStateController$delegate = lazy;
        final int i3 = 2;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i32 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i4 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i5 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i6 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i7 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        this.configurationController$delegate = lazy2;
        final int i4 = 3;
        Lazy lazy3 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        int i22 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i32 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i42 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i5 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i6 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i7 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        this.resourcePicker$delegate = lazy3;
        final int i5 = 4;
        this.settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        int i22 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i32 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i42 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i52 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i6 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i7 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        final int i6 = 5;
        this.headsUpManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        int i22 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i32 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i42 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i52 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i62 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i7 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        final int i7 = 6;
        this.secPanelSAStatusLogInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        int i22 = PanelSlideEventHandler.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    case 1:
                        int i32 = PanelSlideEventHandler.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                    case 2:
                        int i42 = PanelSlideEventHandler.$r8$clinit;
                        return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
                    case 3:
                        int i52 = PanelSlideEventHandler.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 4:
                        int i62 = PanelSlideEventHandler.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 5:
                        int i72 = PanelSlideEventHandler.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    default:
                        int i8 = PanelSlideEventHandler.$r8$clinit;
                        return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                }
            }
        });
        this.flingAnimationUtils = new FlingAnimationUtils(context.getResources().getDisplayMetrics(), 0.4f);
        this.lastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DENSITY_DPI));
        this.velocityTracker = VelocityTracker.obtain();
        this.logBuilder = new StringBuilder();
        this.locale = Locale.ENGLISH;
        this.direction = Direction.UNDECIDED;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.locale = context.getResources().getConfiguration().getLocales().get(0);
        this.panelWidth = ((SecQSPanelResourcePicker) lazy3.getValue()).getPanelWidth(context);
        this.maxDragWidth = DeviceState.getScreenWidth(context);
        updateResource();
        shadeExpansionStateManager.addExpansionListener(this);
        ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).addCallback(this);
        ((StatusBarStateController) lazy.getValue()).addCallback(this);
    }

    public final void createSlideAnimatorAndRun(float f, float f2) {
        float f3;
        float f4;
        ValueAnimator valueAnimator = this.sliderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.direction.ordinal()];
        boolean z = i == 1 ? f <= 0.0f : !(i == 2 ? f < 0.0f : i == 3 ? f > 0.0f : i != 4 || f < 0.0f);
        boolean z2 = z && (Math.abs(f) > this.flingAnimationUtils.mMinVelocityPxPerSecond || Math.abs(f2) > Math.abs(this.maxDragWidth) * 0.5f);
        float f5 = f >= 0.0f ? this.maxDragWidth : -this.maxDragWidth;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, z2 ? f5 : 0.0f);
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.PanelSlideEventHandler$createSlideAnimatorAndRun$animator$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                if (QsAnimatorState.isSliding) {
                    Log.d("SecPanelSplitHelper", "Animation end: isSliding true to false");
                    QsAnimatorState.isSliding = false;
                }
                this.this$0.sliderAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (QsAnimatorState.isSliding) {
                    Log.d("SecPanelSplitHelper", "Animation end: isSliding true to false");
                    QsAnimatorState.isSliding = false;
                }
                this.this$0.sliderAnimator = null;
            }
        });
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.PanelSlideEventHandler$createSlideAnimatorAndRun$animator$1$2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                PanelSlideEventHandler panelSlideEventHandler = this.this$0;
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback;
                if (secPanelSplitHelper$panelSlideEventHandler$1$1 != null) {
                    secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.slide(fFloatValue, panelSlideEventHandler.direction, panelSlideEventHandler.tracking);
                }
            }
        });
        if (f == 0.0f) {
            valueAnimatorOfFloat.setDuration((long) (((f2 / this.maxDragWidth) * 100) + 200));
            f3 = f;
            f4 = f2;
        } else {
            f3 = f;
            f4 = f2;
            this.flingAnimationUtils.apply(valueAnimatorOfFloat, f4, f5, f3, this.maxDragWidth);
        }
        this.sliderAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.start();
        Log.d("SecPanelSplitHelper", "createSlideAnimatorAndRun change = " + z2 + " draggedDistance = " + f4 + " duration = " + valueAnimatorOfFloat.getDuration() + " direction = " + this.direction + " target = " + f5 + " vel = " + f3 + " isForward = " + z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0144  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initiateSlide(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        QSImpl qSImpl;
        SecQSImpl secQSImpl;
        BarController barController;
        Object[] objArr;
        Object[] objArr2;
        boolean z5;
        boolean z6;
        SecMediaHost secMediaHost;
        NonInterceptingScrollView nonInterceptingScrollView;
        int iIntValue;
        int i;
        if (this.panelSplitEnabled) {
            int value = this.lastConfigurationState.getValue(ConfigurationState.ConfigurationField.ORIENTATION);
            Integer numValueOf = Integer.valueOf(value);
            if (value == -100) {
                numValueOf = null;
            }
            if (numValueOf != null && (iIntValue = numValueOf.intValue()) != (i = this.context.getResources().getConfiguration().orientation)) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(iIntValue, i, "initiateSlide forced updateResource lastConfig.orientation = ", ", context.orientation = ", "SecPanelSplitHelper");
                updateResource();
            }
            this.initialX = motionEvent.getX();
            this.initialY = motionEvent.getY();
            this.direction = Direction.UNDECIDED;
            this.panelSliderIntercepted = false;
            this.fullyExpandedOnDown = this.panelFullyExpanded;
            SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1 = this.panelSlideEventCallback;
            if (secPanelSplitHelper$panelSlideEventHandler$1$1 != null) {
                SecPanelSplitHelper secPanelSplitHelper = secPanelSplitHelper$panelSlideEventHandler$1$1.this$0;
                secPanelSplitHelper.stateOnDown = secPanelSplitHelper.currentState;
            }
            Lazy lazy = this.secQuickSettingsControllerImpl$delegate;
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = (SecQuickSettingsControllerImpl) lazy.getValue();
            this.canScrollDownOnDown = (secQuickSettingsControllerImpl == null || (nonInterceptingScrollView = secQuickSettingsControllerImpl.getNonInterceptingScrollView()) == null) ? false : nonInterceptingScrollView.canScrollVertically(-1);
            float f = this.initialX;
            if (this.initialY > ((ShadeHeaderController) this.shadeHeaderController$delegate.getValue()).header.getMeasuredHeight()) {
                z = false;
            } else {
                boolean zIsReversed = this.secPanelSplitHelper.isReversed();
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12 = this.panelSlideEventCallback;
                Integer numValueOf2 = secPanelSplitHelper$panelSlideEventHandler$1$12 != null ? Integer.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.currentState) : null;
                boolean z7 = !zIsReversed;
                if (numValueOf2 != null && numValueOf2.intValue() == z7 ? f >= this.displayWidthOfDivider : !(numValueOf2 == null || numValueOf2.intValue() != zIsReversed || f >= this.displayWidthOfDivider)) {
                    z = true;
                }
            }
            this.isInChangeSpotOnDown = z;
            float f2 = this.initialX;
            float f3 = this.initialY;
            if (((StatusBarStateController) this.statusBarStateController$delegate.getValue()).getState() == 2 || !((SettingsHelper) this.settingsHelper$delegate.getValue()).isNavigationBarGestureWhileHidden()) {
                z2 = false;
            } else {
                int iWidth = this.context.getResources().getConfiguration().windowConfiguration.getBounds().width();
                int iHeight = this.context.getResources().getConfiguration().windowConfiguration.getBounds().height();
                Insets insets = this.gestureInsets;
                int i2 = insets != null ? insets.left : 0;
                int i3 = insets != null ? insets.right : iWidth;
                int i4 = insets != null ? insets.top : 0;
                int i5 = insets != null ? insets.bottom : iHeight;
                if ((f2 < i2 || f2 > iWidth - i3) && f3 > i4 && f3 < iHeight - i5) {
                    z2 = true;
                }
            }
            this.isInGestureArea = z2;
            this.isInQsScrollerTopMarginArea = this.initialY < ((float) ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getQsScrollerTopMargin(this.context));
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl2 = (SecQuickSettingsControllerImpl) lazy.getValue();
            if (secQuickSettingsControllerImpl2 != null) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                Object obj = secQuickSettingsControllerImpl2.qsSupplier.get();
                QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
                if (qSFragmentLegacy == null || (qSImpl = qSFragmentLegacy.mQsImpl) == null || (secQSImpl = qSImpl.mSecQSImpl) == null || (barController = secQSImpl.barController) == null) {
                    z3 = false;
                } else {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    int[] iArr3 = new int[2];
                    int[] iArr4 = new int[2];
                    BrightnessVolumeBar brightnessVolumeBar = (BrightnessVolumeBar) barController.getBarInExpanded(BarType.BRIGHTNESS_VOLUME);
                    BrightnessBar brightnessBar = brightnessVolumeBar.mBrightnessBar;
                    QSMediaPlayerBar qSMediaPlayerBar = (QSMediaPlayerBar) barController.getBarInExpanded(BarType.QS_MEDIA_PLAYER);
                    BarItemImpl barInExpanded = barController.getBarInExpanded(BarType.QUICK_CONTROL);
                    View viewFindViewById = brightnessBar.mBarRootView.findViewById(R.id.slider);
                    VolumeBar volumeBar = brightnessVolumeBar.mVolumeBar;
                    z3 = false;
                    View viewFindViewById2 = volumeBar.mBarRootView.findViewById(R.id.slider);
                    View viewFindViewById3 = qSMediaPlayerBar.mBarRootView.findViewById(R.id.media_player_container);
                    View viewFindViewById4 = barInExpanded.mBarRootView.findViewById(R.id.quick_control_container);
                    viewFindViewById.getLocationInWindow(iArr);
                    viewFindViewById2.getLocationInWindow(iArr2);
                    viewFindViewById3.getLocationInWindow(iArr3);
                    viewFindViewById4.getLocationInWindow(iArr4);
                    if (brightnessBar.mShowing) {
                        if (rawX <= iArr[0] || rawX >= viewFindViewById.getWidth() + r12) {
                            objArr = false;
                            if (!volumeBar.mShowing) {
                                if (rawX <= iArr2[0] || rawX >= viewFindViewById2.getWidth() + r11) {
                                    objArr2 = false;
                                    if (qSMediaPlayerBar.mShowing || (secMediaHost = qSMediaPlayerBar.mMediaHost) == null) {
                                        z5 = false;
                                        if (barInExpanded.mShowing) {
                                            if (rawX <= iArr4[0] || rawX >= viewFindViewById4.getWidth() + r3) {
                                                z6 = false;
                                                if (objArr == false || objArr2 != false || z5 || z6) {
                                                    z4 = true;
                                                }
                                            } else {
                                                if (rawY > iArr4[1] && rawY < viewFindViewById4.getHeight() + r3) {
                                                    z6 = true;
                                                }
                                                if (objArr == false) {
                                                }
                                                z4 = true;
                                            }
                                        }
                                    } else {
                                        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(MediaType.QS);
                                        if ((secMediaPlayerData != null ? secMediaPlayerData.m2627getMediaData().size() : 0) > 1) {
                                            if (rawX > iArr3[0] && rawX < viewFindViewById3.getWidth() + r8) {
                                                if (rawY > iArr3[1] && rawY < viewFindViewById3.getHeight() + r8) {
                                                    z5 = true;
                                                }
                                                if (barInExpanded.mShowing) {
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (rawY > iArr2[1] && rawY < viewFindViewById2.getHeight() + r8) {
                                        objArr2 = true;
                                    }
                                    if (qSMediaPlayerBar.mShowing) {
                                        z5 = false;
                                        if (barInExpanded.mShowing) {
                                        }
                                    }
                                }
                            }
                        } else {
                            if (rawY > iArr[1] && rawY < viewFindViewById.getHeight() + r4) {
                                objArr = true;
                            }
                            if (!volumeBar.mShowing) {
                            }
                        }
                    }
                }
                z4 = z3;
            }
            this.isInSlidableAreaOnDown = z4;
            boolean z8 = z3;
            this.tracking = z8;
            this.velocityTracker.addMovement(motionEvent);
            this.slidingInitialized = true;
            this.logBuilder.setLength(z8 ? 1 : 0);
            RecyclerView$$ExternalSyntheticOutline0.m(this.displayWidthOfDivider, "SecPanelSplitHelper", CubicBezierEasing$$ExternalSyntheticOutline0.m("initiateSlide (", motionEvent.getX(), ", ", motionEvent.getY(), ") displayWidthOfDivider = "));
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        ConfigurationState configurationState = this.lastConfigurationState;
        if (configurationState.needToUpdate(configuration) || !Intrinsics.areEqual(this.locale, configuration.getLocales().get(0))) {
            this.locale = configuration.getLocales().get(0);
            configurationState.update(configuration);
            updateResource();
        }
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        this.panelExpanded = shadeExpansionChangeEvent.expanded;
        float f = shadeExpansionChangeEvent.fraction;
        this.panelExpandFraction = f;
        this.panelFullyExpanded = f == 1.0f;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        super.onStateChanged(i);
        ValueAnimator valueAnimator = this.sliderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public final void updateDirection(float f, float f2) {
        float fAbs = Math.abs(f);
        float fAbs2 = Math.abs(f2);
        float f3 = this.touchSlop;
        if (fAbs > f3 || fAbs2 > f3) {
            if (fAbs > fAbs2 && this.direction != Direction.DOWN) {
                this.direction = f > 0.0f ? Direction.RIGHT : Direction.LEFT;
            } else {
                if (fAbs >= fAbs2 || this.direction != Direction.UNDECIDED || this.initialY >= ((ShadeHeaderController) this.shadeHeaderController$delegate.getValue()).header.getMeasuredHeight()) {
                    return;
                }
                this.direction = f2 > 0.0f ? Direction.DOWN : Direction.UP;
            }
        }
    }

    public final void updateResource() {
        this.panelWidth = ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getPanelWidth(this.context);
        this.maxDragWidth = DeviceState.getScreenWidth(this.context);
        int panelSplitRatio = ((SettingsHelper) this.settingsHelper$delegate.getValue()).getPanelSplitRatio();
        Integer numValueOf = Integer.valueOf(panelSplitRatio);
        ListPopupWindow$$ExternalSyntheticOutline0.m(panelSplitRatio, "updateResources: panelSplitRatio: ", "SecPanelSplitHelper");
        if (panelSplitRatio < 0) {
            numValueOf = null;
        }
        this.displayRatioOfDivider = numValueOf != null ? numValueOf.intValue() * 0.01f : 0.7f;
        boolean zIsReversed = this.secPanelSplitHelper.isReversed();
        int displayWidth = (int) (DeviceState.getDisplayWidth(this.context) * (zIsReversed ? 1 - this.displayRatioOfDivider : this.displayRatioOfDivider));
        this.displayWidthOfDivider = displayWidth;
        int i = this.panelWidth;
        float f = this.maxDragWidth;
        float f2 = this.displayRatioOfDivider;
        StringBuilder sb = new StringBuilder("updateResource panelWidth = ");
        sb.append(i);
        sb.append(" maxDragWidth = ");
        sb.append(f);
        sb.append(" displayRatioOfDivider = ");
        sb.append(f2);
        sb.append(" displayWidthOfDivider = ");
        sb.append(displayWidth);
        sb.append(" isReversed = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, zIsReversed, "SecPanelSplitHelper");
    }
}
