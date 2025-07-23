package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                return QuickSettingsControllerImpl.this.mSecQuickSettingsControllerImpl;
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, z2 ? f5 : 0.0f);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.PanelSlideEventHandler$createSlideAnimatorAndRun$animator$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                PanelSlideEventHandler.this.sliderAnimator = null;
            }
        });
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.PanelSlideEventHandler$createSlideAnimatorAndRun$animator$1$2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                PanelSlideEventHandler panelSlideEventHandler = PanelSlideEventHandler.this;
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback;
                if (secPanelSplitHelper$panelSlideEventHandler$1$1 != null) {
                    secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.slide(floatValue, panelSlideEventHandler.direction, panelSlideEventHandler.tracking);
                }
            }
        });
        if (f == 0.0f) {
            ofFloat.setDuration((long) (((f2 / this.maxDragWidth) * 100) + 200));
            f3 = f;
            f4 = f2;
        } else {
            f3 = f;
            f4 = f2;
            this.flingAnimationUtils.apply(ofFloat, f4, f5, f3, this.maxDragWidth);
        }
        this.sliderAnimator = ofFloat;
        ofFloat.start();
        Log.d("SecPanelSplitHelper", "createSlideAnimatorAndRun change = " + z2 + " draggedDistance = " + f4 + " duration = " + ofFloat.getDuration() + " direction = " + this.direction + " target = " + f5 + " vel = " + f3 + " isForward = " + z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02af A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x025b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initiateSlide(android.view.MotionEvent r20) {
        /*
            Method dump skipped, instructions count: 744
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.PanelSlideEventHandler.initiateSlide(android.view.MotionEvent):void");
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
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        float f3 = this.touchSlop;
        if (abs > f3 || abs2 > f3) {
            if (abs > abs2 && this.direction != Direction.DOWN) {
                this.direction = f > 0.0f ? Direction.RIGHT : Direction.LEFT;
            } else {
                if (abs >= abs2 || this.direction != Direction.UNDECIDED || this.initialY >= ((ShadeHeaderController) this.shadeHeaderController$delegate.getValue()).header.getMeasuredHeight()) {
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
        Integer valueOf = Integer.valueOf(panelSplitRatio);
        ListPopupWindow$$ExternalSyntheticOutline0.m(panelSplitRatio, "updateResources: panelSplitRatio: ", "SecPanelSplitHelper");
        if (panelSplitRatio < 0) {
            valueOf = null;
        }
        this.displayRatioOfDivider = valueOf != null ? valueOf.intValue() * 0.01f : 0.7f;
        boolean isReversed = this.secPanelSplitHelper.isReversed();
        int displayWidth = (int) (DeviceState.getDisplayWidth(this.context) * (isReversed ? 1 - this.displayRatioOfDivider : this.displayRatioOfDivider));
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
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, isReversed, "SecPanelSplitHelper");
    }
}
