package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Trace;
import android.view.Display;
import android.view.InsetsState;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.InputTransferToken;
import android.window.SurfaceSyncGroup;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.InsetsStateKt;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public abstract class WindowDecoration implements AutoCloseable {
    public Context mContext;
    public Context mDecorWindowContext;
    public SurfaceControl mDecorationContainerSurface;
    public final DesktopModeEventLogger mDesktopModeEventLogger;
    public Display mDisplay;
    public final DisplayController mDisplayController;
    public final Region mExclusionRegion;
    public boolean mHasGlobalFocus;
    public boolean mIsCaptionVisible;
    public boolean mIsKeyguardVisibleAndOccluded;
    public boolean mIsStatusBarVisible;
    public int mLayoutResId;
    public final AnonymousClass1 mOnDisplaysChangedListener;
    public final Binder mOwner;
    public SplitScreenController mSplitController;
    public final Supplier mSurfaceControlBuilderSupplier;
    public final Supplier mSurfaceControlTransactionSupplier;
    public final SurfaceControlViewHostFactory mSurfaceControlViewHostFactory;
    public TaskPositioner mTaskDragResizer;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final SurfaceControl mTaskSurface;
    public final float[] mTmpColor;
    public final Context mUserContext;
    public WindowDecorViewHost mViewHost;
    public final Supplier mWindowContainerTransactionSupplier;
    public Configuration mWindowDecorConfig;
    public final WindowDecorViewHostSupplier mWindowDecorViewHostSupplier;
    public WindowDecorationInsets mWindowDecorationInsets;

    public class RelayoutParams {
        public boolean mApplyStartTransactionOnDraw;
        public boolean mAsyncViewHost;
        public int mCaptionHeightId;
        public int mCaptionTopPadding;
        public int mCaptionType;
        public int mCaptionWidthId;
        public int mDisplayTopInset;
        public boolean mHasGlobalFocus;
        public int mInputFeatures;
        public int mInsetSourceFlags;
        public boolean mIsCaptionVisible;
        public int mLayoutResId;
        public boolean mLimitTouchRegionToSystemAreas;
        public ActivityManager.RunningTaskInfo mRunningTaskInfo;
        public boolean mSetTaskVisibilityPositionAndCrop;
        public boolean mShouldSetAppBounds;
        public boolean mShouldSetBackground;
        public Configuration mWindowDecorConfig;
        public final List mOccludingCaptionElements = new ArrayList();
        public boolean mIsInsetSource = true;
        public final Region mDisplayExclusionRegion = Region.obtain();
        public int mShadowRadius = -1;
        public int mCornerRadius = -1;
        public int mShadowRadiusId = 0;
        public int mCornerRadiusId = 0;

        public class OccludingCaptionElement {
            public Alignment mAlignment;
            public int mWidthResId;

            enum Alignment {
                START,
                END
            }
        }

        public final void reset() {
            this.mLayoutResId = 0;
            this.mCaptionHeightId = 0;
            this.mCaptionWidthId = 0;
            ((ArrayList) this.mOccludingCaptionElements).clear();
            this.mLimitTouchRegionToSystemAreas = false;
            this.mInputFeatures = 0;
            this.mIsInsetSource = true;
            this.mInsetSourceFlags = 0;
            this.mDisplayExclusionRegion.setEmpty();
            if (DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX.isTrue()) {
                this.mShadowRadiusId = 0;
                this.mCornerRadiusId = 0;
            } else {
                this.mShadowRadius = -1;
                this.mCornerRadius = -1;
            }
            this.mCaptionTopPadding = 0;
            this.mIsCaptionVisible = false;
            this.mApplyStartTransactionOnDraw = false;
            this.mSetTaskVisibilityPositionAndCrop = false;
            this.mWindowDecorConfig = null;
            this.mAsyncViewHost = false;
            this.mHasGlobalFocus = false;
            this.mShouldSetAppBounds = false;
            this.mShouldSetBackground = false;
            if (CoreRune.MW_CAPTION_TYPE) {
                this.mDisplayTopInset = 0;
            }
        }
    }

    public class RelayoutResult {
        public int mCaptionHeight;
        public int mCaptionTopPadding;
        public int mCaptionTouchableHeight;
        public int mCaptionWidth;
        public int mCaptionX;
        public int mCaptionY;
        public int mCornerRadius;
        public final Region mCustomizableCaptionRegion = Region.obtain();
        public int mHeight;
        public View mRootView;
        public int mShadowRadius;
        public int mWidth;
    }

    public interface SurfaceControlViewHostFactory {
    }

    public class WindowDecorationInsets {
        public final Rect[] mBoundingRects;
        public final boolean mExcludedFromAppBounds;
        public final int mFlags;
        public final Rect mFrame;
        public final Binder mOwner;
        public final boolean mShouldAddCaptionInset;
        public final Rect mTaskFrame;
        public final WindowContainerToken mToken;

        public /* synthetic */ WindowDecorationInsets(WindowContainerToken windowContainerToken, Binder binder, Rect rect, Rect rect2, Rect[] rectArr, int i, boolean z, boolean z2, int i2) {
            this(windowContainerToken, binder, rect, rect2, rectArr, i, z, z2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof WindowDecorationInsets) {
                WindowDecorationInsets windowDecorationInsets = (WindowDecorationInsets) obj;
                if (Objects.equals(this.mToken, windowDecorationInsets.mToken) && Objects.equals(this.mOwner, windowDecorationInsets.mOwner) && Objects.equals(this.mFrame, windowDecorationInsets.mFrame) && Objects.equals(this.mTaskFrame, windowDecorationInsets.mTaskFrame) && Objects.deepEquals(this.mBoundingRects, windowDecorationInsets.mBoundingRects) && this.mFlags == windowDecorationInsets.mFlags && this.mShouldAddCaptionInset == windowDecorationInsets.mShouldAddCaptionInset && this.mExcludedFromAppBounds == windowDecorationInsets.mExcludedFromAppBounds) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.mToken, this.mOwner, this.mFrame, Integer.valueOf(Arrays.hashCode(this.mBoundingRects)), Integer.valueOf(this.mFlags));
        }

        public final void remove(WindowContainerTransaction windowContainerTransaction) {
            windowContainerTransaction.removeInsetsSource(this.mToken, this.mOwner, 0, WindowInsets.Type.captionBar());
            windowContainerTransaction.removeInsetsSource(this.mToken, this.mOwner, 0, WindowInsets.Type.mandatorySystemGestures());
            if (this.mExcludedFromAppBounds) {
                windowContainerTransaction.setAppBounds(this.mToken, new Rect());
            }
        }

        public final void update(WindowContainerTransaction windowContainerTransaction) {
            if (this.mShouldAddCaptionInset) {
                windowContainerTransaction.addInsetsSource(this.mToken, this.mOwner, 0, WindowInsets.Type.captionBar(), this.mFrame, this.mBoundingRects, this.mFlags);
                windowContainerTransaction.addInsetsSource(this.mToken, this.mOwner, 0, WindowInsets.Type.mandatorySystemGestures(), this.mFrame, this.mBoundingRects, 0);
                if (this.mExcludedFromAppBounds) {
                    Rect rect = new Rect(this.mTaskFrame);
                    rect.top = this.mFrame.height() + rect.top;
                    windowContainerTransaction.setAppBounds(this.mToken, rect);
                }
            }
        }

        private WindowDecorationInsets(WindowContainerToken windowContainerToken, Binder binder, Rect rect, Rect rect2, Rect[] rectArr, int i, boolean z, boolean z2) {
            this.mToken = windowContainerToken;
            this.mOwner = binder;
            this.mFrame = rect;
            this.mTaskFrame = rect2;
            this.mBoundingRects = rectArr;
            this.mFlags = i;
            this.mShouldAddCaptionInset = z;
            this.mExcludedFromAppBounds = z2;
        }
    }

    public WindowDecoration(Context context, Context context2, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, WindowDecorViewHostSupplier windowDecorViewHostSupplier) {
        this(context, context2, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl, new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(0), new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(1), new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(2), new SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.windowdecor.WindowDecoration.2
        }, windowDecorViewHostSupplier, new DesktopModeEventLogger());
    }

    public static int loadDimensionPixelSize(Resources resources, int i) {
        if (i == 0) {
            return 0;
        }
        return resources.getDimensionPixelSize(i);
    }

    public final AdditionalViewHostViewContainer addWindow(final View view, SurfaceControl.Transaction transaction, SurfaceSyncGroup surfaceSyncGroup, int i, int i2, int i3, int i4, boolean z) {
        int i5;
        int i6;
        SurfaceControl surfaceControlBuild = ((SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get()).setName("Handle Menu of Task=" + this.mTaskInfo.taskId).setContainerLayer().setParent(this.mDecorationContainerSurface).setCallsite("WindowDecoration.addWindow").build();
        boolean z2 = CoreRune.MW_CAPTION_POPUP;
        if (z2) {
            i5 = i3;
            i6 = i4;
        } else {
            i5 = i3;
            i6 = i4;
            transaction.setPosition(surfaceControlBuild, i, i2).setWindowCrop(surfaceControlBuild, i5, i6).show(surfaceControlBuild);
        }
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i5, i6, 2, 262152, -2);
        layoutParams.setTitle("Additional window of Task=" + this.mTaskInfo.taskId);
        layoutParams.setTrustedOverlay();
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(runningTaskInfo.configuration, surfaceControlBuild, (InputTransferToken) null, z2 ? runningTaskInfo.token : null);
        SurfaceControlViewHostFactory surfaceControlViewHostFactory = this.mSurfaceControlViewHostFactory;
        Context context = this.mDecorWindowContext;
        Display display = this.mDisplay;
        surfaceControlViewHostFactory.getClass();
        final SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, display, windowlessWindowManager, "WindowDecoration");
        if (z2) {
            layoutParams.setSurfaceInsets(view, true, false);
            Rect rect = layoutParams.surfaceInsets;
            transaction.setPosition(surfaceControlBuild, i - rect.left, i2 - rect.top).show(surfaceControlBuild);
            if (z) {
                layoutParams.multiWindowFlags = 2;
            }
        }
        surfaceSyncGroup.add(surfaceControlViewHost.getSurfacePackage(), new Runnable() { // from class: com.android.wm.shell.windowdecor.WindowDecoration$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                surfaceControlViewHost.setView(view, layoutParams);
            }
        });
        return new AdditionalViewHostViewContainer(surfaceControlBuild, surfaceControlViewHost, this.mSurfaceControlTransactionSupplier);
    }

    public final Rect calculateBoundingRectLocal(RelayoutParams.OccludingCaptionElement occludingCaptionElement, int i, Rect rect) {
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mDecorWindowContext) == 1;
        int iOrdinal = occludingCaptionElement.mAlignment.ordinal();
        if (iOrdinal == 0) {
            return z ? new Rect(rect.width() - i, 0, rect.width(), rect.height()) : new Rect(0, 0, i, rect.height());
        }
        if (iOrdinal == 1) {
            return z ? new Rect(0, 0, i, rect.height()) : new Rect(rect.width() - i, 0, rect.width(), rect.height());
        }
        throw new IllegalArgumentException("Unexpected alignment " + occludingCaptionElement.mAlignment);
    }

    public int calculateCaptionPositionX(int i, int i2) {
        return (i - i2) / 2;
    }

    public int calculateCaptionPositionY() {
        return 0;
    }

    public int calculateScreenBurnOffset(int i, int i2) {
        return 0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        Trace.beginSection("WindowDecoration#close");
        this.mDisplayController.removeDisplayWindowListener(this.mOnDisplaysChangedListener);
        TaskPositioner taskPositioner = this.mTaskDragResizer;
        if (taskPositioner != null) {
            taskPositioner.close();
        }
        WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) this.mWindowContainerTransactionSupplier.get();
        releaseViews(windowContainerTransaction);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        this.mTaskSurface.release();
        Trace.endSection();
    }

    public int getCaptionViewId() {
        return 0;
    }

    public View inflateLayout(Context context, int i) {
        return LayoutInflater.from(context).inflate(i, (ViewGroup) null);
    }

    public abstract void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, Region region);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x017b  */
    /* JADX WARN: Type inference failed for: r11v16, types: [com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost] */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7, types: [android.graphics.Region] */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void relayout(RelayoutParams relayoutParams, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, WindowContainerTransaction windowContainerTransaction, View view, RelayoutResult relayoutResult) {
        boolean z;
        SurfaceControl.Transaction transaction3;
        int iLoadDimensionPixelSize;
        ?? r15;
        Display display;
        Configuration configuration;
        Trace.beginSection("WindowDecoration#relayout");
        relayoutResult.mWidth = 0;
        relayoutResult.mHeight = 0;
        relayoutResult.mCaptionHeight = 0;
        relayoutResult.mCaptionWidth = 0;
        relayoutResult.mCaptionX = 0;
        relayoutResult.mCaptionY = 0;
        relayoutResult.mCaptionTopPadding = 0;
        relayoutResult.mCustomizableCaptionRegion.setEmpty();
        relayoutResult.mRootView = null;
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX;
        if (desktopExperienceFlags.isTrue()) {
            relayoutResult.mCornerRadius = -1;
            relayoutResult.mShadowRadius = -1;
        }
        boolean z2 = CoreRune.MW_CAPTION_HANDLE;
        if (z2) {
            relayoutResult.mCaptionTouchableHeight = 0;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = relayoutParams.mRunningTaskInfo;
        if (runningTaskInfo != null) {
            this.mTaskInfo = runningTaskInfo;
        }
        this.mHasGlobalFocus = relayoutParams.mHasGlobalFocus;
        this.mExclusionRegion.set(relayoutParams.mDisplayExclusionRegion);
        int i = this.mLayoutResId;
        this.mLayoutResId = relayoutParams.mLayoutResId;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
        if (!runningTaskInfo2.isVisible && (!CoreRune.MW_CAPTION_BUG_FIX || !runningTaskInfo2.isVisibleRequested)) {
            releaseViews(windowContainerTransaction);
            if (relayoutParams.mSetTaskVisibilityPositionAndCrop) {
                transaction2.hide(this.mTaskSurface);
            }
            Trace.endSection();
            return;
        }
        Trace.beginSection("WindowDecoration#relayout-inflateIfNeeded");
        if (view == null && relayoutParams.mLayoutResId == 0) {
            throw new IllegalArgumentException("layoutResId and rootView can't both be invalid.");
        }
        relayoutResult.mRootView = view;
        Configuration configuration2 = this.mWindowDecorConfig;
        boolean z3 = (configuration2 == null || configuration2.fontScale == this.mTaskInfo.configuration.fontScale) ? false : true;
        boolean z4 = (configuration2 == null || configuration2.getLocales().equals(this.mTaskInfo.getConfiguration().getLocales())) ? false : true;
        Configuration configuration3 = this.mWindowDecorConfig;
        int i2 = configuration3 != null ? configuration3.densityDpi : 0;
        int i3 = configuration3 != null ? configuration3.uiMode & 48 : 0;
        Configuration configuration4 = this.mWindowDecorConfig;
        if (configuration4 == null) {
            configuration4 = this.mTaskInfo.getConfiguration();
        }
        Configuration configuration5 = new Configuration(configuration4);
        Configuration configuration6 = relayoutParams.mWindowDecorConfig;
        if (configuration6 == null) {
            configuration6 = this.mTaskInfo.getConfiguration();
        }
        this.mWindowDecorConfig = configuration6;
        boolean z5 = CoreRune.MW_CAPTION;
        if (z5 && this.mTaskInfo.getDisplayId() == 0 && MultiWindowUtils.hasCustomDensity() && (configuration = this.mWindowDecorConfig) != null) {
            z = z4;
            if (configuration.densityDpi != this.mContext.getResources().getConfiguration().densityDpi) {
                this.mWindowDecorConfig.densityDpi = this.mContext.getResources().getConfiguration().densityDpi;
            }
        } else {
            z = z4;
        }
        Configuration configuration7 = this.mWindowDecorConfig;
        int i4 = configuration7.densityDpi;
        int i5 = configuration7.uiMode & 48;
        if (i2 != i4 || (display = this.mDisplay) == null || display.getDisplayId() != this.mTaskInfo.displayId || i != this.mLayoutResId || i3 != i5 || this.mDecorWindowContext == null || z3 || z) {
            releaseViews(windowContainerTransaction);
            Display display2 = this.mDisplayController.mDisplayManager.getDisplay(this.mTaskInfo.displayId);
            this.mDisplay = display2;
            if (display2 == null) {
                if (CoreRune.MW_CAPTION_BUG_FIX) {
                    this.mDisplayController.addDisplayWindowListener(this.mOnDisplaysChangedListener, this.mTaskInfo.displayId);
                } else {
                    this.mDisplayController.addDisplayWindowListener(this.mOnDisplaysChangedListener, -1);
                }
                transaction3 = null;
                relayoutResult.mRootView = null;
            } else {
                transaction3 = null;
                Context contextCreateConfigurationContext = this.mContext.createConfigurationContext(this.mWindowDecorConfig);
                this.mDecorWindowContext = contextCreateConfigurationContext;
                contextCreateConfigurationContext.setTheme(this.mContext.getThemeResId());
                int i6 = relayoutParams.mLayoutResId;
                if (i6 != 0) {
                    relayoutResult.mRootView = inflateLayout(this.mDecorWindowContext, i6);
                }
                if (relayoutResult.mRootView == null) {
                    relayoutResult.mRootView = inflateLayout(this.mDecorWindowContext, relayoutParams.mLayoutResId);
                }
            }
        } else {
            if (z5) {
                Configuration configuration8 = this.mWindowDecorConfig;
                if ((configuration5.diff(configuration8) & 4) == 0 && configuration5.windowConfiguration.getWindowingMode() == configuration8.windowConfiguration.getWindowingMode() && configuration5.windowConfiguration.getStagePosition() == configuration8.windowConfiguration.getStagePosition() && configuration5.getLayoutDirection() == configuration8.getLayoutDirection()) {
                }
                if (relayoutResult.mRootView == null) {
                }
            }
            transaction3 = null;
            if (relayoutResult.mRootView == null) {
            }
        }
        Trace.endSection();
        if (relayoutResult.mRootView == null) {
            Trace.endSection();
            return;
        }
        Trace.beginSection("WindowDecoration#relayout-updateCaptionVisibility");
        View view2 = relayoutResult.mRootView;
        this.mIsCaptionVisible = relayoutParams.mIsCaptionVisible;
        if (!CoreRune.MW_CAPTION_HANDLE_ANIM && !DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
            setCaptionVisibility(view2, this.mIsCaptionVisible);
        }
        Trace.endSection();
        Rect bounds = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && this.mTaskInfo.isSplitScreen() && this.mSplitController != null && (bounds.width() == this.mTaskInfo.minWidth || bounds.height() == this.mTaskInfo.minHeight)) {
            int stageType = this.mTaskInfo.getConfiguration().windowConfiguration.getStageType();
            int i7 = stageType != 1 ? stageType != 2 ? stageType != 4 ? -1 : 5 : 1 : 0;
            iLoadDimensionPixelSize = -1;
            if (i7 != -1) {
                bounds = this.mSplitController.getStageBounds(i7);
            }
        } else {
            iLoadDimensionPixelSize = -1;
        }
        relayoutResult.mWidth = bounds.width();
        relayoutResult.mHeight = bounds.height();
        if (!CoreRune.MW_CAPTION_FREEFORM_STASH || !(this instanceof DesktopModeWindowDecoration) || !((DesktopModeWindowDecoration) this).mFreeformStashState.isStashed()) {
            ((TaskFocusStateConsumer) relayoutResult.mRootView).setTaskFocusState(this.mHasGlobalFocus);
        }
        ((TaskFocusStateConsumer) relayoutResult.mRootView).setTaskFocusState(this.mHasGlobalFocus);
        Resources resources = this.mDecorWindowContext.getResources();
        relayoutResult.mCaptionHeight = loadDimensionPixelSize(resources, relayoutParams.mCaptionHeightId) + relayoutParams.mCaptionTopPadding;
        int i8 = relayoutParams.mCaptionWidthId;
        int iLoadDimensionPixelSize2 = i8 != 0 ? loadDimensionPixelSize(resources, i8) : bounds.width();
        relayoutResult.mCaptionWidth = iLoadDimensionPixelSize2;
        relayoutResult.mCaptionX = z2 ? calculateScreenBurnOffset(-3, 3) + calculateCaptionPositionX(relayoutResult.mWidth, iLoadDimensionPixelSize2) : (relayoutResult.mWidth - iLoadDimensionPixelSize2) / 2;
        relayoutResult.mCaptionY = z2 ? calculateScreenBurnOffset(-5, 5) + calculateCaptionPositionY() : 0;
        relayoutResult.mCaptionTopPadding = relayoutParams.mCaptionTopPadding;
        if (desktopExperienceFlags.isTrue()) {
            int i9 = relayoutParams.mCornerRadiusId;
            relayoutResult.mCornerRadius = i9 == 0 ? iLoadDimensionPixelSize : loadDimensionPixelSize(resources, i9);
            int i10 = relayoutParams.mShadowRadiusId;
            if (i10 != 0) {
                iLoadDimensionPixelSize = loadDimensionPixelSize(resources, i10);
            }
            relayoutResult.mShadowRadius = iLoadDimensionPixelSize;
        }
        if (z2) {
            relayoutResult.mCaptionTouchableHeight = (this.mTaskInfo.isFreeform() && relayoutParams.mCaptionType == 0) ? loadDimensionPixelSize(resources, R.dimen.mw_handle_height) : relayoutResult.mCaptionHeight;
        }
        Trace.beginSection("relayout-createViewHostIfNeeded");
        Context context = this.mDecorWindowContext;
        Display display3 = this.mDisplay;
        if (this.mViewHost == null) {
            this.mViewHost = this.mWindowDecorViewHostSupplier.acquire(context, display3);
        }
        Trace.endSection();
        Trace.beginSection("WindowDecoration#relayout-updateSurfacesAndInsets");
        SurfaceControl surfaceControl = this.mViewHost.getSurfaceControl();
        if (this.mDecorationContainerSurface == null) {
            SurfaceControl surfaceControlBuild = ((SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get()).setName("Decor container of Task=" + this.mTaskInfo.taskId).setContainerLayer().setParent(this.mTaskSurface).setCallsite("WindowDecoration.updateDecorationContainerSurface").build();
            this.mDecorationContainerSurface = surfaceControlBuild;
            transaction.setTrustedOverlay(surfaceControlBuild, true).setLayer(this.mDecorationContainerSurface, PluginLockInstancePolicy.DISABLED_BY_SUB_USER);
        }
        transaction.setWindowCrop(this.mDecorationContainerSurface, relayoutResult.mWidth, relayoutResult.mHeight).show(this.mDecorationContainerSurface);
        updateCaptionContainerSurface(surfaceControl, transaction, relayoutResult);
        updateCaptionInsets(relayoutParams, windowContainerTransaction, relayoutResult, bounds);
        updateTaskSurface(relayoutParams, transaction, transaction2, relayoutResult);
        Trace.endSection();
        Trace.beginSection("WindowDecoration#relayout-updateViewHost");
        View view3 = relayoutResult.mRootView;
        view3.setPadding(view3.getPaddingLeft(), relayoutParams.mCaptionTopPadding, relayoutResult.mRootView.getPaddingRight(), relayoutResult.mRootView.getPaddingBottom());
        int i11 = relayoutResult.mCaptionX;
        int i12 = relayoutResult.mCaptionY;
        Rect rect = new Rect(i11, i12, relayoutResult.mCaptionWidth + i11, relayoutResult.mCaptionHeight + i12);
        if (relayoutParams.mLimitTouchRegionToSystemAreas) {
            Point point = relayoutParams.mRunningTaskInfo.positionInParent;
            Rect rect2 = new Rect(rect);
            rect2.offsetTo(point.x, point.y);
            int size = ((ArrayList) relayoutParams.mOccludingCaptionElements).size();
            Region regionObtain = Region.obtain();
            if (size == 0) {
                regionObtain.set(rect2);
            } else {
                Resources resources2 = this.mDecorWindowContext.getResources();
                for (int i13 = 0; i13 < size; i13++) {
                    RelayoutParams.OccludingCaptionElement occludingCaptionElement = (RelayoutParams.OccludingCaptionElement) ((ArrayList) relayoutParams.mOccludingCaptionElements).get(i13);
                    Rect rectCalculateBoundingRectLocal = calculateBoundingRectLocal(occludingCaptionElement, resources2.getDimensionPixelSize(occludingCaptionElement.mWidthResId), rect2);
                    rectCalculateBoundingRectLocal.offset(rect2.left, rect2.top);
                    regionObtain.union(rectCalculateBoundingRectLocal);
                }
            }
            Region regionObtain2 = Region.obtain();
            regionObtain2.set(rect2);
            Region.Op op = Region.Op.DIFFERENCE;
            regionObtain2.op(regionObtain, op);
            regionObtain2.op(relayoutParams.mDisplayExclusionRegion, Region.Op.INTERSECT);
            Region regionObtain3 = Region.obtain();
            regionObtain3.set(rect2);
            regionObtain3.op(regionObtain2, op);
            regionObtain3.translate(-point.x, -point.y);
            regionObtain.recycle();
            regionObtain2.recycle();
            r15 = regionObtain3;
        } else {
            r15 = transaction3;
        }
        Trace.beginSection("WindowDecoration#updateViewHierarchy");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(relayoutResult.mCaptionWidth, CoreRune.MW_CAPTION_HANDLE ? relayoutResult.mCaptionTouchableHeight : relayoutResult.mCaptionHeight, 2, 8, -2);
        layoutParams.setTitle("Caption of Task=" + this.mTaskInfo.taskId);
        layoutParams.setTrustedOverlay();
        if (CoreRune.MW_CAPTION_TOOLTIP) {
            layoutParams.multiWindowFlags = 1;
        }
        layoutParams.inputFeatures = relayoutParams.mInputFeatures;
        if (!relayoutParams.mAsyncViewHost) {
            this.mViewHost.updateView(relayoutResult.mRootView, layoutParams, this.mTaskInfo.configuration, r15, relayoutParams.mApplyStartTransactionOnDraw ? transaction : transaction3);
        } else {
            if (relayoutParams.mApplyStartTransactionOnDraw) {
                throw new IllegalArgumentException("Cannot use sync draw tx with async relayout");
            }
            this.mViewHost.updateViewAsync(relayoutResult.mRootView, layoutParams, this.mTaskInfo.configuration, r15);
        }
        Trace.endSection();
        Trace.endSection();
        Trace.endSection();
    }

    public void releaseViews(WindowContainerTransaction windowContainerTransaction) {
        boolean z;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mSurfaceControlTransactionSupplier.get();
        WindowDecorViewHost windowDecorViewHost = this.mViewHost;
        boolean z2 = true;
        if (windowDecorViewHost != null) {
            this.mWindowDecorViewHostSupplier.release(windowDecorViewHost, transaction);
            this.mViewHost = null;
            z = true;
        } else {
            z = false;
        }
        SurfaceControl surfaceControl = this.mDecorationContainerSurface;
        if (surfaceControl != null) {
            transaction.remove(surfaceControl);
            this.mDecorationContainerSurface = null;
        } else {
            z2 = z;
        }
        if (z2) {
            transaction.apply();
        }
        WindowDecorationInsets windowDecorationInsets = this.mWindowDecorationInsets;
        if (windowDecorationInsets != null) {
            windowDecorationInsets.remove(windowContainerTransaction);
            this.mWindowDecorationInsets = null;
        }
    }

    public final void setCaptionVisibility(View view, boolean z) {
        if (view == null) {
            return;
        }
        view.findViewById(getCaptionViewId()).setVisibility(z ? 0 : 8);
    }

    public void updateCaptionContainerSurface(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, RelayoutResult relayoutResult) {
        transaction.reparent(surfaceControl, this.mDecorationContainerSurface).setWindowCrop(surfaceControl, relayoutResult.mCaptionWidth, relayoutResult.mCaptionHeight).setPosition(surfaceControl, relayoutResult.mCaptionX, 0.0f).setLayer(surfaceControl, -1).show(surfaceControl);
    }

    public final void updateCaptionInsets(RelayoutParams relayoutParams, WindowContainerTransaction windowContainerTransaction, RelayoutResult relayoutResult, Rect rect) {
        Rect[] rectArr;
        if (!this.mIsCaptionVisible || !relayoutParams.mIsInsetSource) {
            WindowDecorationInsets windowDecorationInsets = this.mWindowDecorationInsets;
            if (windowDecorationInsets != null) {
                windowDecorationInsets.remove(windowContainerTransaction);
                this.mWindowDecorationInsets = null;
                return;
            }
            return;
        }
        Rect rect2 = new Rect(rect);
        rect2.bottom = rect2.top + relayoutResult.mCaptionHeight;
        int size = ((ArrayList) relayoutParams.mOccludingCaptionElements).size();
        if (size == 0) {
            rectArr = null;
        } else {
            if ((relayoutParams.mInputFeatures & 4) != 0) {
                relayoutResult.mCustomizableCaptionRegion.set(rect2);
            }
            Resources resources = this.mDecorWindowContext.getResources();
            Rect[] rectArr2 = new Rect[size];
            for (int i = 0; i < size; i++) {
                RelayoutParams.OccludingCaptionElement occludingCaptionElement = (RelayoutParams.OccludingCaptionElement) ((ArrayList) relayoutParams.mOccludingCaptionElements).get(i);
                Rect rectCalculateBoundingRectLocal = calculateBoundingRectLocal(occludingCaptionElement, resources.getDimensionPixelSize(occludingCaptionElement.mWidthResId), rect2);
                rectArr2[i] = rectCalculateBoundingRectLocal;
                if ((relayoutParams.mInputFeatures & 4) != 0) {
                    relayoutResult.mCustomizableCaptionRegion.op(rectCalculateBoundingRectLocal, Region.Op.DIFFERENCE);
                }
            }
            rectArr = rectArr2;
        }
        WindowDecorationInsets windowDecorationInsets2 = new WindowDecorationInsets(this.mTaskInfo.token, this.mOwner, rect2, rect, rectArr, relayoutParams.mInsetSourceFlags, relayoutParams.mIsInsetSource, relayoutParams.mShouldSetAppBounds, 0);
        if (windowDecorationInsets2.equals(this.mWindowDecorationInsets)) {
            return;
        }
        this.mWindowDecorationInsets = windowDecorationInsets2;
        windowDecorationInsets2.update(windowContainerTransaction);
    }

    public void updateTaskSurface(RelayoutParams relayoutParams, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, RelayoutResult relayoutResult) {
        if (relayoutParams.mSetTaskVisibilityPositionAndCrop) {
            Point point = this.mTaskInfo.positionInParent;
            transaction.setWindowCrop(this.mTaskSurface, relayoutResult.mWidth, relayoutResult.mHeight);
            transaction2.setWindowCrop(this.mTaskSurface, relayoutResult.mWidth, relayoutResult.mHeight).setPosition(this.mTaskSurface, point.x, point.y);
        }
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX;
        if (desktopExperienceFlags.isTrue()) {
            int i = relayoutResult.mShadowRadius;
            if (i != -1) {
                transaction.setShadowRadius(this.mTaskSurface, i);
                transaction2.setShadowRadius(this.mTaskSurface, relayoutResult.mShadowRadius);
            }
        } else {
            int i2 = relayoutParams.mShadowRadius;
            if (i2 != -1) {
                transaction.setShadowRadius(this.mTaskSurface, i2);
                transaction2.setShadowRadius(this.mTaskSurface, relayoutParams.mShadowRadius);
            }
        }
        if (relayoutParams.mSetTaskVisibilityPositionAndCrop) {
            transaction.show(this.mTaskSurface);
        }
        if (relayoutParams.mShouldSetBackground) {
            ActivityManager.TaskDescription taskDescription = this.mTaskInfo.taskDescription;
            int backgroundColor = taskDescription != null ? taskDescription.getBackgroundColor() : -16777216;
            this.mTmpColor[0] = Color.red(backgroundColor) / 255.0f;
            this.mTmpColor[1] = Color.green(backgroundColor) / 255.0f;
            this.mTmpColor[2] = Color.blue(backgroundColor) / 255.0f;
            transaction.setColor(this.mTaskSurface, this.mTmpColor);
        } else {
            transaction.unsetColor(this.mTaskSurface);
        }
        if (desktopExperienceFlags.isTrue()) {
            int i3 = relayoutResult.mCornerRadius;
            if (i3 != -1) {
                transaction.setCornerRadius(this.mTaskSurface, i3);
                transaction2.setCornerRadius(this.mTaskSurface, relayoutResult.mCornerRadius);
                return;
            }
            return;
        }
        int i4 = relayoutParams.mCornerRadius;
        if (i4 != -1) {
            transaction.setCornerRadius(this.mTaskSurface, i4);
            transaction2.setCornerRadius(this.mTaskSurface, relayoutParams.mCornerRadius);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.windowdecor.WindowDecoration$1] */
    public WindowDecoration(Context context, Context context2, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, Supplier<WindowContainerTransaction> supplier3, Supplier<SurfaceControl> supplier4, SurfaceControlViewHostFactory surfaceControlViewHostFactory, WindowDecorViewHostSupplier windowDecorViewHostSupplier, DesktopModeEventLogger desktopModeEventLogger) {
        this.mOnDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.windowdecor.WindowDecoration.1
            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayAdded(int i) {
                WindowDecoration windowDecoration = WindowDecoration.this;
                if (windowDecoration.mTaskInfo.displayId != i) {
                    return;
                }
                windowDecoration.mDisplayController.removeDisplayWindowListener(this);
                windowDecoration.relayout(windowDecoration.mTaskInfo, windowDecoration.mHasGlobalFocus, windowDecoration.mExclusionRegion);
            }
        };
        this.mExclusionRegion = Region.obtain();
        this.mOwner = new Binder();
        this.mTmpColor = new float[3];
        this.mContext = context;
        this.mUserContext = context2;
        this.mDisplayController = displayController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mTaskInfo = runningTaskInfo;
        SurfaceControl surfaceControl2 = supplier4.get();
        surfaceControl2.copyFrom(surfaceControl, "WindowDecoration");
        this.mTaskSurface = surfaceControl2;
        this.mDesktopModeEventLogger = desktopModeEventLogger;
        this.mSurfaceControlBuilderSupplier = supplier;
        this.mSurfaceControlTransactionSupplier = supplier2;
        this.mWindowContainerTransactionSupplier = supplier3;
        this.mSurfaceControlViewHostFactory = surfaceControlViewHostFactory;
        this.mWindowDecorViewHostSupplier = windowDecorViewHostSupplier;
        this.mDisplay = displayController.mDisplayManager.getDisplay(this.mTaskInfo.displayId);
        InsetsState insetsState = displayController.getInsetsState(this.mTaskInfo.displayId);
        this.mIsStatusBarVisible = insetsState != null && InsetsStateKt.isVisible(WindowInsets.Type.statusBars(), insetsState);
    }
}
