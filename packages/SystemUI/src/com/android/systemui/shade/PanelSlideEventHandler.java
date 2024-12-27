package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.sec.ims.settings.ImsProfile;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PanelSlideEventHandler implements SettingsHelper.OnChangedCallback, ShadeExpansionListener, ConfigurationController.ConfigurationListener, StatusBarStateController.StateListener {
    public static final Uri RATIO_URI;
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
    public Runnable interceptCallback;
    public boolean isInChangeSpotOnDown;
    public boolean isInGestureArea;
    public boolean isInQsScrollerTopMarginArea;
    public boolean isInSlidableAreaOnDown;
    public final ConfigurationState lastConfigurationState;
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
    public final Lazy secQuickSettingsControllerImpl$delegate;
    public final Lazy settingsHelper$delegate;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final Lazy shadeHeaderController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$shadeHeaderController$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
        }
    });
    public ValueAnimator sliderAnimator;
    public boolean slidingInitialized;
    public final Lazy statusBarStateController$delegate;
    public final int touchSlop;
    public boolean tracking;
    public final VelocityTracker velocityTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        RATIO_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_RATIO);
    }

    public PanelSlideEventHandler(Context context, ShadeExpansionStateManager shadeExpansionStateManager, final QuickSettingsControllerImpl quickSettingsControllerImpl) {
        this.context = context;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.secQuickSettingsControllerImpl$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$secQuickSettingsControllerImpl$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return QuickSettingsControllerImpl.this.mSecQuickSettingsControllerImpl;
            }
        });
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$statusBarStateController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
            }
        });
        this.statusBarStateController$delegate = lazy;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$configurationController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
            }
        });
        this.configurationController$delegate = lazy2;
        Lazy lazy3 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$resourcePicker$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            }
        });
        this.resourcePicker$delegate = lazy3;
        Lazy lazy4 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$settingsHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            }
        });
        this.settingsHelper$delegate = lazy4;
        this.headsUpManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.PanelSlideEventHandler$headsUpManager$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
            }
        });
        this.flingAnimationUtils = new FlingAnimationUtils(context.getResources().getDisplayMetrics(), 0.4f);
        this.lastConfigurationState = new ConfigurationState(CollectionsKt__CollectionsKt.listOf(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DENSITY_DPI));
        this.velocityTracker = VelocityTracker.obtain();
        this.logBuilder = new StringBuilder();
        this.direction = Direction.UNDECIDED;
        ((SettingsHelper) lazy4.getValue()).registerCallback(this, RATIO_URI);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.panelWidth = ((SecQSPanelResourcePicker) lazy3.getValue()).getPanelWidth(context);
        this.maxDragWidth = DeviceState.getScreenWidth(context);
        shadeExpansionStateManager.addExpansionListener(this);
        ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).addCallback(this);
        ((StatusBarStateController) lazy.getValue()).addCallback(this);
        updateResource();
    }

    public final void createSlideAnimatorAndRun(float f, float f2) {
        ValueAnimator valueAnimator = this.sliderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.direction.ordinal()];
        boolean z = i == 1 ? f <= 0.0f : !(i == 2 ? f < 0.0f : i == 3 ? f > 0.0f : i != 4 || f < 0.0f);
        boolean z2 = z && (Math.abs(f) > this.flingAnimationUtils.mMinVelocityPxPerSecond || Math.abs(f2) > Math.abs(this.maxDragWidth) * 0.5f);
        float f3 = f >= 0.0f ? this.maxDragWidth : -this.maxDragWidth;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, z2 ? f3 : 0.0f);
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
        } else {
            this.flingAnimationUtils.apply(ofFloat, f2, f3, f, this.maxDragWidth);
        }
        this.sliderAnimator = ofFloat;
        ofFloat.start();
        Log.d("SecPanelSplitHelper", "createSlideAnimatorAndRun change = " + z2 + " draggedDistance = " + f2 + " duration = " + ofFloat.getDuration() + " direction = " + this.direction + " target = " + f3 + " vel = " + f + " isForward = " + z);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0282 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0259  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initiateSlide(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 699
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.PanelSlideEventHandler.initiateSlide(android.view.MotionEvent):void");
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri == null || !uri.equals(RATIO_URI)) {
            return;
        }
        Log.d("SecPanelSplitHelper", "onChanged");
        updateResource();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        ConfigurationState configurationState = this.lastConfigurationState;
        if (configurationState.needToUpdate(configuration)) {
            Intrinsics.checkNotNull(configuration);
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
                if (abs >= abs2 || this.direction != Direction.UNDECIDED || this.initialY >= ((ShadeHeaderController) this.shadeHeaderController$delegate.getValue()).header.getHeight()) {
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
        float displayWidth = DeviceState.getDisplayWidth(this.context);
        float f = this.displayRatioOfDivider;
        int i = (int) (displayWidth * f);
        this.displayWidthOfDivider = i;
        Log.d("SecPanelSplitHelper", "updateResource panelWidth = " + this.panelWidth + " maxDragWidth = " + this.maxDragWidth + " displayRatioOfDivider = " + f + " displayWidthOfDivider = " + i);
    }
}
