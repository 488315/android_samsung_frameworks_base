package com.android.systemui.subscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView;
import com.android.systemui.qp.SubscreenParentLayout;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QsCoverAnimator;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeader;
import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeaderController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.subscreen.dagger.SubScreenQuickPanelComponent;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SubScreenQuickPanelWindowController implements PanelScreenShotLogger.LogProvider, CommandQueue.Callbacks {
    public final QsCoverAnimator mAnimator;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DisplayManager mDisplayManager;
    public float mExpandedFraction;
    public float mExpandedHeight;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public WindowManager.LayoutParams mLp;
    public float mMaxExpandedHeight;
    public boolean mPanelExpanded;
    public boolean mPanelFullyExpanded;
    public ValueAnimator mPanelHeightAnimator;
    public final SecQSPanelResourcePicker mPanelResourcePicker;
    public View mQSPanel;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public final SubScreenQuickPanelComponent.Factory mSubScreenComponent;
    public final SubScreenQSEventHandler mSubScreenQSEventHandler;
    public SubScreenQuickPanelWindowView mSubScreenQsWindowView;
    public SubScreenQuickPanelHeaderController mSubScreenQuickPanelHeaderController;
    public SubRoom.StateChangeListener mSubScreenStateChangedListener;
    public SubroomQuickSettingsQSPanelBaseView mSubroomQuickSettingsQSPanelBaseView;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public final SysUiState mSysUiState;
    public WindowManager mWindowManager;
    public int mDisabled1 = 0;
    public int mDisabled2 = 0;
    public boolean mPanelDisabled = false;
    public boolean mIsAnnounced = false;
    public boolean mIsRotation180 = false;
    public final AnonymousClass1 mFoldStateChangedListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = SubScreenQuickPanelWindowController.this;
            if (z && subScreenQuickPanelWindowController.mPanelExpanded) {
                subScreenQuickPanelWindowController.collapsePanel();
            }
            subScreenQuickPanelWindowController.mPanelDisabled = z;
        }
    };
    public final AnonymousClass2 mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.2
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i != SubScreenQuickPanelWindowController.this.mContext.getDisplay().getDisplayId()) {
                return;
            }
            boolean z = SubScreenQuickPanelWindowController.this.mDisplayManager.getDisplay(i).getRotation() == 2;
            SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = SubScreenQuickPanelWindowController.this;
            if (z != subScreenQuickPanelWindowController.mIsRotation180) {
                subScreenQuickPanelWindowController.mIsRotation180 = z;
                if (QpRune.QUICK_SUBSCREEN_FULLSCREEN_PANEL) {
                    SubScreenQuickPanelHeaderController subScreenQuickPanelHeaderController = subScreenQuickPanelWindowController.mSubScreenQuickPanelHeaderController;
                    if (subScreenQuickPanelHeaderController != null) {
                        SubScreenQuickPanelHeader subScreenQuickPanelHeader = subScreenQuickPanelHeaderController.mSubScreenQuickPanelHeader;
                        ViewGroup.LayoutParams layoutParams = subScreenQuickPanelHeader.getLayoutParams();
                        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                        if (marginLayoutParams != null) {
                            marginLayoutParams.topMargin = z ? 0 : subScreenQuickPanelHeader.getContext().getResources().getDimensionPixelSize(R.dimen.subscreen_qp_header_top_margin);
                            subScreenQuickPanelHeader.setLayoutParams(marginLayoutParams);
                        }
                    }
                    View view = subScreenQuickPanelWindowController.mQSPanel;
                    if (view instanceof SubscreenParentLayout) {
                        SubscreenParentLayout subscreenParentLayout = (SubscreenParentLayout) view;
                        subscreenParentLayout.mIsRotation180 = subScreenQuickPanelWindowController.mIsRotation180;
                        FrameLayout frameLayout = subscreenParentLayout.mMediaPanelView;
                        (frameLayout != null ? frameLayout : null).setTranslationY(subscreenParentLayout.calculateYPos(subscreenParentLayout.mContext.getResources()));
                        SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = subScreenQuickPanelWindowController.mSubroomQuickSettingsQSPanelBaseView;
                        if (subroomQuickSettingsQSPanelBaseView != null) {
                            boolean z2 = subScreenQuickPanelWindowController.mIsRotation180;
                            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) subroomQuickSettingsQSPanelBaseView.mQuickSettingsContainer.getLayoutParams();
                            if (z2) {
                                marginLayoutParams2.topMargin = subroomQuickSettingsQSPanelBaseView.getResources().getDimensionPixelSize(R.dimen.subscreen_qp_tilelayout_top_margin_flexmode);
                            } else {
                                marginLayoutParams2.topMargin = subroomQuickSettingsQSPanelBaseView.getResources().getDimensionPixelSize(R.dimen.cover_screen_page_layout_top_margin);
                            }
                            subroomQuickSettingsQSPanelBaseView.mQuickSettingsContainer.setLayoutParams(marginLayoutParams2);
                            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) subroomQuickSettingsQSPanelBaseView.mBrightnessView.getLayoutParams();
                            if (z2) {
                                marginLayoutParams3.topMargin = subroomQuickSettingsQSPanelBaseView.getResources().getDimensionPixelSize(R.dimen.subscreen_brightness_qp_layout_top_margin_flexmode);
                            } else {
                                marginLayoutParams3.topMargin = 0;
                            }
                            subroomQuickSettingsQSPanelBaseView.mBrightnessView.setLayoutParams(marginLayoutParams3);
                        }
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    };

    public class PanelExpandedFractionProvider implements SubRoom {
        public /* synthetic */ PanelExpandedFractionProvider(SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final View getView(Context context) {
            return null;
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final Bundle request(String str, Bundle bundle) {
            return null;
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
            SubScreenQuickPanelWindowController.this.mSubScreenStateChangedListener = stateChangeListener;
        }

        private PanelExpandedFractionProvider() {
        }

        @Override // com.android.systemui.plugins.subscreen.SubRoom
        public final void removeListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.subscreen.SubScreenQuickPanelWindowController$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.subscreen.SubScreenQuickPanelWindowController$2] */
    public SubScreenQuickPanelWindowController(SubscreenQsPanelController subscreenQsPanelController, CommandQueue commandQueue, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, DisplayLifecycle displayLifecycle, DisplayManager displayManager, SysUiState sysUiState, ScreenRecordingStateProvider screenRecordingStateProvider, DelayableExecutor delayableExecutor, Context context, FlingAnimationUtils.Builder builder, DeviceStateManager deviceStateManager, NavigationModeController navigationModeController, SubScreenQuickPanelComponent.Factory factory, SecQSPanelResourcePicker secQSPanelResourcePicker, SubscreenUtil subscreenUtil, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mSubscreenQsPanelController = subscreenQsPanelController;
        this.mCommandQueue = commandQueue;
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        subscreenQsPanelController.init();
        this.mDisplayLifecycle = displayLifecycle;
        this.mSysUiState = sysUiState;
        this.mSubScreenComponent = factory;
        this.mPanelResourcePicker = secQSPanelResourcePicker;
        Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        Context contextCreateDisplayContext = displays.length > 1 ? context.createDisplayContext(displays[1]) : context;
        this.mContext = contextCreateDisplayContext;
        this.mDisplayManager = displayManager;
        contextCreateDisplayContext.getDisplayId();
        builder.reset();
        builder.mMaxLengthSeconds = 0.5f;
        builder.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtils = builder.build();
        this.mAnimator = new QsCoverAnimator(contextCreateDisplayContext, subscreenQsPanelController);
        subscreenUtil.mSubScreenQuickPanelWindowController = this;
        final int i = 0;
        Runnable runnable = new Runnable(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda0
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i2) {
                    case 0:
                        ValueAnimator valueAnimator = subScreenQuickPanelWindowController.mPanelHeightAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                            break;
                        }
                        break;
                    default:
                        subScreenQuickPanelWindowController.collapsePanel();
                        break;
                }
            }
        };
        final int i2 = 1;
        Runnable runnable2 = new Runnable(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda0
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i22) {
                    case 0:
                        ValueAnimator valueAnimator = subScreenQuickPanelWindowController.mPanelHeightAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                            break;
                        }
                        break;
                    default:
                        subScreenQuickPanelWindowController.collapsePanel();
                        break;
                }
            }
        };
        final int i3 = 2;
        Supplier supplier = new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i4 = i3;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i4) {
                    case 0:
                        return subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                    case 1:
                        return subScreenQuickPanelWindowController.mWindowManager;
                    case 2:
                        return subScreenQuickPanelWindowController.mContext;
                    default:
                        return subScreenQuickPanelWindowController.mLp;
                }
            }
        };
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.createPanelHeightAnimatorAndRun(((Float) obj).floatValue(), ((Boolean) obj2).booleanValue());
            }
        };
        final int i4 = 0;
        BooleanSupplier booleanSupplier = new BooleanSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i5 = i4;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i5) {
                    case 0:
                        return subScreenQuickPanelWindowController.mPanelDisabled;
                    case 1:
                        return subScreenQuickPanelWindowController.mPanelExpanded;
                    default:
                        return subScreenQuickPanelWindowController.mPanelFullyExpanded;
                }
            }
        };
        DoubleSupplier doubleSupplier = new DoubleSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda8
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i5 = i4;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i5) {
                    case 0:
                        return subScreenQuickPanelWindowController.mExpandedHeight;
                    default:
                        return subScreenQuickPanelWindowController.mMaxExpandedHeight;
                }
            }
        };
        final int i5 = 3;
        Supplier supplier2 = new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i42 = i5;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i42) {
                    case 0:
                        return subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                    case 1:
                        return subScreenQuickPanelWindowController.mWindowManager;
                    case 2:
                        return subScreenQuickPanelWindowController.mContext;
                    default:
                        return subScreenQuickPanelWindowController.mLp;
                }
            }
        };
        final int i6 = 1;
        DoubleSupplier doubleSupplier2 = new DoubleSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda8
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i52 = i6;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i52) {
                    case 0:
                        return subScreenQuickPanelWindowController.mExpandedHeight;
                    default:
                        return subScreenQuickPanelWindowController.mMaxExpandedHeight;
                }
            }
        };
        BooleanSupplier booleanSupplier2 = new BooleanSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i52 = i6;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i52) {
                    case 0:
                        return subScreenQuickPanelWindowController.mPanelDisabled;
                    case 1:
                        return subScreenQuickPanelWindowController.mPanelExpanded;
                    default:
                        return subScreenQuickPanelWindowController.mPanelFullyExpanded;
                }
            }
        };
        final int i7 = 2;
        final int i8 = 0;
        final int i9 = 1;
        this.mSubScreenQSEventHandler = new SubScreenQSEventHandler(runnable, runnable2, supplier, biConsumer, deviceStateManager, booleanSupplier, displayManager, doubleSupplier, supplier2, delayableExecutor, doubleSupplier2, navigationModeController, booleanSupplier2, new BooleanSupplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i52 = i7;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i52) {
                    case 0:
                        return subScreenQuickPanelWindowController.mPanelDisabled;
                    case 1:
                        return subScreenQuickPanelWindowController.mPanelExpanded;
                    default:
                        return subScreenQuickPanelWindowController.mPanelFullyExpanded;
                }
            }
        }, secQSPanelResourcePicker, screenRecordingStateProvider, new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i42 = i8;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i42) {
                    case 0:
                        return subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                    case 1:
                        return subScreenQuickPanelWindowController.mWindowManager;
                    case 2:
                        return subScreenQuickPanelWindowController.mContext;
                    default:
                        return subScreenQuickPanelWindowController.mLp;
                }
            }
        }, sysUiState, new DoubleConsumer() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda2
            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                this.f$0.updatePanelExpansion((float) d, false);
            }
        }, wakefulnessLifecycle, new Supplier(this) { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda1
            public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i42 = i9;
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                switch (i42) {
                    case 0:
                        return subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                    case 1:
                        return subScreenQuickPanelWindowController.mWindowManager;
                    case 2:
                        return subScreenQuickPanelWindowController.mContext;
                    default:
                        return subScreenQuickPanelWindowController.mLp;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateCollapsePanels(int i, boolean z) {
        if (!checkNotInitialized() && this.mPanelExpanded) {
            collapsePanel();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandSettingsPanel(String str) {
        if (checkNotInitialized() || this.mPanelExpanded || checkNotInitialized()) {
            return;
        }
        createPanelHeightAnimatorAndRun(0.0f, true);
    }

    public final boolean checkNotInitialized() {
        return this.mSubScreenQsWindowView == null;
    }

    public final void collapsePanel() {
        if (checkNotInitialized()) {
            return;
        }
        createPanelHeightAnimatorAndRun(0.0f, false);
    }

    public final void createPanelHeightAnimatorAndRun(float f, final boolean z) {
        float f2 = z ? this.mMaxExpandedHeight : 0.0f;
        final boolean z2 = this.mPanelFullyExpanded;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mExpandedHeight, f2);
        this.mPanelHeightAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda13
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                boolean z3 = z;
                boolean z4 = z2;
                subScreenQuickPanelWindowController.getClass();
                subScreenQuickPanelWindowController.updatePanelExpansion(((Float) valueAnimator.getAnimatedValue()).floatValue(), !z3 && z4);
            }
        });
        this.mPanelHeightAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.4
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SubScreenQuickPanelWindowController.this.mPanelHeightAnimator = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
        if (flingAnimationUtils != null) {
            ValueAnimator valueAnimator = this.mPanelHeightAnimator;
            float f3 = this.mExpandedHeight;
            float f4 = z ? this.mMaxExpandedHeight : 0.0f;
            flingAnimationUtils.apply(valueAnimator, f3, f4, f, Math.abs(f4 - f3));
        }
        this.mPanelHeightAnimator.start();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (checkNotInitialized()) {
            return;
        }
        this.mRemoteInputQuickSettingsDisabler.getClass();
        int i4 = this.mDisabled1 ^ i2;
        this.mDisabled1 = i2;
        int i5 = this.mDisabled2 ^ i3;
        this.mDisabled2 = i3;
        if ((i4 & 65536) != 0 && (i2 & 65536) != 0) {
            collapsePanel();
        }
        if ((i5 & 1) != 0) {
            collapsePanel();
        }
        if ((i5 & 4) != 0 && (i3 & 4) != 0) {
            collapsePanel();
        }
        boolean z2 = ((i2 & 65536) == 0 && (i3 & 1) == 0 && (i3 & 4) == 0) ? false : true;
        if (this.mPanelDisabled != z2) {
            this.mPanelDisabled = z2;
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SubScreenQuickPanelWindowController ============================================= ");
        arrayList.add("    mMaxExpandedHeight = " + this.mMaxExpandedHeight);
        arrayList.add("    mExpandedHeight = " + this.mExpandedHeight);
        arrayList.add("    mPanelExpanded = " + this.mPanelExpanded);
        arrayList.add("    mPanelFullyExpanded = " + this.mPanelFullyExpanded);
        arrayList.add("    mPanelDisabled = " + this.mPanelDisabled);
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onCameraLaunchGestureDetected(int i) {
        if (!checkNotInitialized() && this.mPanelExpanded) {
            collapsePanel();
        }
    }

    public final void updatePanelExpansion(float f, boolean z) {
        if (f < 0.0f || f > this.mMaxExpandedHeight) {
            f = Math.min(this.mMaxExpandedHeight, Math.max(0.0f, f));
        }
        this.mExpandedHeight = f;
        boolean z2 = f > 0.0f;
        if (z2 != this.mPanelExpanded) {
            this.mPanelExpanded = z2;
            final Intent intent = new Intent(z2 ? "com.android.systemui.subscreen.EXPANDED" : "com.android.systemui.subscreen.COLLAPSED");
            ((UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class)).execute(new Runnable() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
                    subScreenQuickPanelWindowController.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                }
            });
            if (!checkNotInitialized()) {
                this.mSubScreenQsWindowView.setVisibility(z2 ? 0 : 8);
            }
            if (NotiRune.NOTI_SUBSCREEN_ALL) {
                ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).panelExpanded = this.mPanelExpanded;
            }
        }
        float f2 = this.mExpandedHeight / this.mMaxExpandedHeight;
        this.mExpandedFraction = f2;
        QsCoverAnimator qsCoverAnimator = this.mAnimator;
        if (qsCoverAnimator != null) {
            TouchAnimator touchAnimator = qsCoverAnimator.mPanelViewTranslationAnimator;
            if (touchAnimator != null) {
                touchAnimator.setPosition(f2);
            }
            if (z) {
                qsCoverAnimator.mPanelViewAlphaAnimator.setPosition(f2);
            } else {
                qsCoverAnimator.mQSPanel.setAlpha(1.0f);
            }
        }
        float f3 = this.mExpandedFraction;
        if (f3 == 0.0f) {
            this.mIsAnnounced = false;
        }
        if (f3 == 1.0f && !checkNotInitialized()) {
            if (!this.mIsAnnounced) {
                this.mSubScreenQsWindowView.announceForAccessibility(this.mContext.getString(R.string.subscreen_accessibility_quick_settings));
            }
            this.mIsAnnounced = true;
        }
        this.mPanelFullyExpanded = this.mExpandedFraction == 1.0f;
        if (this.mSubScreenStateChangedListener != null) {
            Bundle bundle = new Bundle();
            bundle.putFloat(SubRoom.EXTRA_KEY_QUICK_PANEL_SWIPE_FRACTION, this.mExpandedFraction);
            this.mSubScreenStateChangedListener.onStateChanged(bundle);
        }
        SysUiState sysUiState = this.mSysUiState;
        if (sysUiState == null) {
            return;
        }
        ((SysUiStateImpl) sysUiState.setFlag(2048L, this.mExpandedFraction > 0.0f)).commitUpdate();
    }
}
