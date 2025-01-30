package com.android.wm.shell.windowdecor;

import android.R;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.SurfaceSyncGroup;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class WindowDecoration implements AutoCloseable {
    public SurfaceControl mCaptionContainerSurface;
    public final Rect mCaptionInsetsRect;
    public WindowlessWindowManager mCaptionWindowManager;
    public final Context mContext;
    public Context mDecorWindowContext;
    public SurfaceControl mDecorationContainerSurface;
    public Display mDisplay;
    public final DisplayController mDisplayController;
    public SurfaceControl mDragResizeInputSurface;
    public boolean mIsDexEnabled;
    public boolean mIsDexMode;
    public boolean mIsNewDexMode;
    public boolean mIsRemoving;
    public int mLayoutResId;
    public final C41851 mOnDisplaysChangedListener;
    public final Binder mOwner;
    public final Supplier mSurfaceControlBuilderSupplier;
    public final Supplier mSurfaceControlTransactionSupplier;
    public final SurfaceControlViewHostFactory mSurfaceControlViewHostFactory;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final SurfaceControl mTaskSurface;
    public final float[] mTmpColor;
    public SurfaceControlViewHost mViewHost;
    public final Supplier mWindowContainerTransactionSupplier;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdditionalWindow {
        public final Supplier mTransactionSupplier;
        public SurfaceControl mWindowSurface;
        public SurfaceControlViewHost mWindowViewHost;

        public AdditionalWindow(SurfaceControl surfaceControl, SurfaceControlViewHost surfaceControlViewHost, Supplier<SurfaceControl.Transaction> supplier) {
            this.mWindowSurface = surfaceControl;
            this.mWindowViewHost = surfaceControlViewHost;
            this.mTransactionSupplier = supplier;
        }

        public final void releaseView() {
            boolean z;
            SurfaceControlViewHost surfaceControlViewHost = this.mWindowViewHost;
            if (surfaceControlViewHost != null) {
                surfaceControlViewHost.getWindowlessWM();
            }
            SurfaceControlViewHost surfaceControlViewHost2 = this.mWindowViewHost;
            if (surfaceControlViewHost2 != null) {
                surfaceControlViewHost2.release();
                this.mWindowViewHost = null;
            }
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionSupplier.get();
            SurfaceControl surfaceControl = this.mWindowSurface;
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
                this.mWindowSurface = null;
                z = true;
            } else {
                z = false;
            }
            if (z) {
                transaction.apply();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RelayoutParams {
        public boolean mApplyStartTransactionOnDraw;
        public int mCaptionHeightId;
        public int mCaptionType;
        public int mCaptionWidthId;
        public int mCaptionX;
        public int mCaptionY;
        public int mCornerRadius;
        public int mHorizontalInset;
        public int mLayoutResId;
        public ActivityManager.RunningTaskInfo mRunningTaskInfo;
        public int mShadowRadiusId;

        public final void reset() {
            this.mLayoutResId = 0;
            this.mCaptionHeightId = 0;
            this.mCaptionWidthId = 0;
            this.mShadowRadiusId = 0;
            this.mCornerRadius = 0;
            this.mCaptionX = 0;
            this.mCaptionY = 0;
            this.mHorizontalInset = 0;
            this.mApplyStartTransactionOnDraw = false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RelayoutResult {
        public int mCaptionHeight;
        public int mDecorContainerOffsetX;
        public int mDecorContainerOffsetY;
        public int mHeight;
        public View mRootView;
        public int mWidth;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SurfaceControlViewHostFactory {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public WindowDecoration(Context context, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        this(context, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl, r6, r7, new Supplier() { // from class: com.android.wm.shell.windowdecor.WindowDecoration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (r1) {
                    case 0:
                        return new SurfaceControl.Builder();
                    case 1:
                        return new SurfaceControl.Transaction();
                    default:
                        return new WindowContainerTransaction();
                }
            }
        }, new SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.windowdecor.WindowDecoration.2
        });
        final int i = 0;
        Supplier supplier = new Supplier() { // from class: com.android.wm.shell.windowdecor.WindowDecoration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i) {
                    case 0:
                        return new SurfaceControl.Builder();
                    case 1:
                        return new SurfaceControl.Transaction();
                    default:
                        return new WindowContainerTransaction();
                }
            }
        };
        final int i2 = 1;
        Supplier supplier2 = new Supplier() { // from class: com.android.wm.shell.windowdecor.WindowDecoration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i2) {
                    case 0:
                        return new SurfaceControl.Builder();
                    case 1:
                        return new SurfaceControl.Transaction();
                    default:
                        return new WindowContainerTransaction();
                }
            }
        };
        final int i3 = 2;
    }

    public static boolean hasBarFocus(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.isFocused) {
            return runningTaskInfo.getWindowingMode() != 1 || MultiWindowManager.getInstance().getMultiWindowModeStates(0) == 1;
        }
        return false;
    }

    static int loadDimensionPixelSize(int i, Resources resources) {
        if (i == 0) {
            return 0;
        }
        return resources.getDimensionPixelSize(i);
    }

    public final AdditionalWindow addWindow(int i, String str, SurfaceControl.Transaction transaction, SurfaceSyncGroup surfaceSyncGroup, int i2, int i3, int i4, int i5, int i6, int i7) {
        SurfaceControl.Builder builder = (SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get();
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, " of Task=");
        m2m.append(this.mTaskInfo.taskId);
        SurfaceControl build = builder.setName(m2m.toString()).setContainerLayer().setParent(this.mDecorationContainerSurface).build();
        final View inflate = LayoutInflater.from(this.mDecorWindowContext).inflate(i, (ViewGroup) null);
        transaction.setPosition(build, i2, i3).setWindowCrop(build, i4, i5).setShadowRadius(build, i6).setCornerRadius(build, i7).show(build);
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i4, i5, 2, 8, -2);
        layoutParams.setTitle("Additional window of Task=" + this.mTaskInfo.taskId);
        layoutParams.setTrustedOverlay();
        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(this.mTaskInfo.configuration, build, (IBinder) null);
        SurfaceControlViewHostFactory surfaceControlViewHostFactory = this.mSurfaceControlViewHostFactory;
        Context context = this.mDecorWindowContext;
        Display display = this.mDisplay;
        surfaceControlViewHostFactory.getClass();
        final SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, display, windowlessWindowManager, "WindowDecoration");
        surfaceSyncGroup.add(surfaceControlViewHost.getSurfacePackage(), new Runnable() { // from class: com.android.wm.shell.windowdecor.WindowDecoration$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                surfaceControlViewHost.setView(inflate, layoutParams);
            }
        });
        return new AdditionalWindow(build, surfaceControlViewHost, this.mSurfaceControlTransactionSupplier);
    }

    public MultitaskingWindowDecoration asMultitaskingWindowDecoration() {
        return null;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mDisplayController.removeDisplayWindowListener(this.mOnDisplaysChangedListener);
        releaseViews();
    }

    public Configuration getConfigurationWithOverrides(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.getConfiguration();
    }

    public String getTag() {
        return "WindowDecoration";
    }

    public abstract void relayout(ActivityManager.RunningTaskInfo runningTaskInfo);

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ae, code lost:
    
        if (((r8.diff(r4) & 4) != 0 || ((com.samsung.android.rune.CoreRune.MW_CAPTION_SHELL_DEX && r8.semDesktopModeEnabled != r4.semDesktopModeEnabled) || r8.windowConfiguration.getWindowingMode() != r4.windowConfiguration.getWindowingMode() || (com.android.wm.shell.windowdecor.CaptionGlobalState.COLOR_THEME_ENABLED && r8.isNightModeActive() != r4.isNightModeActive()))) != false) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03fc  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x050c  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x057b  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x05a2  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void relayout(RelayoutParams relayoutParams, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, WindowContainerTransaction windowContainerTransaction, WindowDecorLinearLayout windowDecorLinearLayout, RelayoutResult relayoutResult, boolean z, boolean z2) {
        boolean z3;
        Display display;
        boolean z4;
        int loadDimensionPixelSize;
        int width;
        SurfaceControlViewHost surfaceControlViewHost;
        int i;
        Display display2;
        relayoutResult.mWidth = 0;
        relayoutResult.mHeight = 0;
        relayoutResult.mRootView = null;
        if (CoreRune.MW_CAPTION_SHELL) {
            relayoutResult.mDecorContainerOffsetX = 0;
            relayoutResult.mDecorContainerOffsetY = 0;
        }
        Configuration configuration = this.mTaskInfo.getConfiguration();
        ActivityManager.RunningTaskInfo runningTaskInfo = relayoutParams.mRunningTaskInfo;
        if (runningTaskInfo != null) {
            this.mTaskInfo = runningTaskInfo;
            if (CoreRune.MW_CAPTION_SHELL_DEX) {
                updateDexStates();
            }
        }
        int i2 = this.mLayoutResId;
        int i3 = relayoutParams.mLayoutResId;
        this.mLayoutResId = i3;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
        if (!runningTaskInfo2.isVisible) {
            releaseViews();
            transaction2.hide(this.mTaskSurface);
            return;
        }
        if (windowDecorLinearLayout == null && i3 == 0) {
            throw new IllegalArgumentException("layoutResId and rootView can't both be invalid.");
        }
        relayoutResult.mRootView = windowDecorLinearLayout;
        Configuration configurationWithOverrides = getConfigurationWithOverrides(runningTaskInfo2);
        if (configuration.densityDpi == configurationWithOverrides.densityDpi && (display2 = this.mDisplay) != null && display2.getDisplayId() == this.mTaskInfo.displayId && i2 == this.mLayoutResId && (!CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE || !z)) {
            if (CoreRune.MW_CAPTION_SHELL) {
            }
            if (CoreRune.MW_CAPTION_SHELL && configurationWithOverrides != configuration) {
                this.mDecorWindowContext.getResources().updateConfiguration(configurationWithOverrides, this.mContext.getResources().getDisplayMetrics());
            }
            if (relayoutResult.mRootView == null) {
                relayoutResult.mRootView = LayoutInflater.from(this.mDecorWindowContext).inflate(relayoutParams.mLayoutResId, (ViewGroup) null);
            }
            Resources resources = this.mDecorWindowContext.getResources();
            Rect bounds = configurationWithOverrides.windowConfiguration.getBounds();
            z4 = this instanceof MultitaskingWindowDecoration;
            if (z4) {
                int i4 = -((MultitaskingWindowDecoration) this).getFreeformThickness$1();
                relayoutResult.mDecorContainerOffsetX = i4;
                relayoutResult.mDecorContainerOffsetY = i4;
            }
            relayoutResult.mWidth = bounds.width();
            relayoutResult.mHeight = bounds.height();
            if (this.mDecorationContainerSurface == null) {
                SurfaceControl build = ((SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get()).setName("Decor container of Task=" + this.mTaskInfo.taskId).setContainerLayer().setParent(this.mTaskSurface).build();
                this.mDecorationContainerSurface = build;
                transaction.setTrustedOverlay(build, true).setLayer(this.mDecorationContainerSurface, 20000);
            }
            boolean z5 = this.mTaskInfo.getWindowingMode() != 5;
            if (CoreRune.MW_CAPTION_SHELL || !z5) {
                transaction.setWindowCrop(this.mDecorationContainerSurface, relayoutResult.mWidth, relayoutResult.mHeight).show(this.mDecorationContainerSurface);
            } else {
                transaction.setWindowCrop(this.mDecorationContainerSurface, -1, -1).show(this.mDecorationContainerSurface);
            }
            if (this.mCaptionContainerSurface == null) {
                this.mCaptionContainerSurface = ((SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get()).setName("Caption container of Task=" + this.mTaskInfo.taskId).setContainerLayer().setParent(this.mDecorationContainerSurface).build();
            }
            loadDimensionPixelSize = loadDimensionPixelSize(relayoutParams.mCaptionHeightId, resources);
            width = bounds.width();
            if (CoreRune.MW_CAPTION_SHELL) {
                transaction.setWindowCrop(this.mCaptionContainerSurface, width, loadDimensionPixelSize).show(this.mCaptionContainerSurface);
            } else {
                relayoutResult.mCaptionHeight = loadDimensionPixelSize;
                int i5 = relayoutParams.mCaptionWidthId;
                if (i5 != 0 && relayoutParams.mCaptionType == 0) {
                    width = loadDimensionPixelSize(i5, resources);
                    relayoutParams.mCaptionX = ((bounds.width() - width) / 2) + relayoutParams.mHorizontalInset;
                    if (z5) {
                        relayoutParams.mCaptionY -= loadDimensionPixelSize(R.dimen.toast_elevation, resources);
                    }
                }
                if (z5) {
                    if (relayoutParams.mCaptionType == 1) {
                        relayoutParams.mCaptionY = -loadDimensionPixelSize;
                    }
                    if (this.mDragResizeInputSurface == null) {
                        SurfaceControl build2 = ((SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get()).setName("DragResizeInput of Task=" + this.mTaskInfo.taskId).setContainerLayer().setParent(this.mTaskSurface).build();
                        this.mDragResizeInputSurface = build2;
                        transaction.setTrustedOverlay(build2, true).setLayer(this.mDragResizeInputSurface, 20001);
                    }
                    i = width;
                    transaction.setPosition(this.mDragResizeInputSurface, 0.0f, -r6).setWindowCrop(this.mDragResizeInputSurface, relayoutResult.mWidth, relayoutResult.mHeight + (z4 ? ((MultitaskingWindowDecoration) this).getCaptionVisibleHeight() : loadDimensionPixelSize)).show(this.mDragResizeInputSurface);
                } else {
                    i = width;
                }
                transaction.setPosition(this.mCaptionContainerSurface, relayoutParams.mCaptionX, relayoutParams.mCaptionY).setWindowCrop(this.mCaptionContainerSurface, -1, -1).show(this.mCaptionContainerSurface);
                width = i;
            }
            if (ViewRootImpl.CAPTION_ON_SHELL) {
                transaction.hide(this.mCaptionContainerSurface);
            } else {
                if (!z4 || !((MultitaskingWindowDecoration) this).mFreeformStashState.isStashed()) {
                    ((TaskFocusStateConsumer) relayoutResult.mRootView).setTaskFocusState(hasBarFocus(this.mTaskInfo));
                }
                this.mCaptionInsetsRect.set(bounds);
                if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && relayoutParams.mCaptionType == 0) {
                    int loadDimensionPixelSize2 = loadDimensionPixelSize(R.dimen.toast_elevation, resources);
                    Rect rect = this.mCaptionInsetsRect;
                    rect.bottom = rect.top + loadDimensionPixelSize2;
                } else {
                    Rect rect2 = this.mCaptionInsetsRect;
                    rect2.bottom = rect2.top + loadDimensionPixelSize + relayoutParams.mCaptionY;
                }
                if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && this.mCaptionInsetsRect.height() < 0) {
                    if (CoreRune.SAFE_DEBUG) {
                        Log.w(getTag(), "relayout: invalid insetRect=" + this.mCaptionInsetsRect);
                    }
                    Rect rect3 = this.mCaptionInsetsRect;
                    rect3.bottom = rect3.top;
                }
                boolean z6 = this.mTaskInfo.getWindowingMode() == 1;
                boolean z7 = z4 && ((MultitaskingWindowDecoration) this).mIsImmersiveMode;
                if ((CoreRune.MW_CAPTION_SHELL_INSETS && (z5 || z7 || ((CoreRune.MT_SUPPORT_SIZE_COMPAT && SizeCompatInfo.shouldRemoveCaptionInsets(this.mTaskInfo.sizeCompatInfo)) || ((this.mTaskInfo.isSplitScreen() || z6) && relayoutParams.mCaptionType == 0)))) || (this.mTaskInfo.getConfiguration().isNewDexMode() && this.mTaskInfo.isCaptionHandlerHidden)) {
                    windowContainerTransaction.removeInsetsSource(this.mTaskInfo.token, this.mOwner, 0, WindowInsets.Type.captionBar());
                    this.mCaptionInsetsRect.setEmpty();
                } else {
                    windowContainerTransaction.addInsetsSource(this.mTaskInfo.token, this.mOwner, 0, WindowInsets.Type.captionBar(), this.mCaptionInsetsRect);
                }
            }
            int i6 = relayoutParams.mShadowRadiusId;
            float dimension = i6 != 0 ? 0.0f : resources.getDimension(i6);
            int backgroundColor = this.mTaskInfo.taskDescription.getBackgroundColor();
            this.mTmpColor[0] = Color.red(backgroundColor) / 255.0f;
            this.mTmpColor[1] = Color.green(backgroundColor) / 255.0f;
            this.mTmpColor[2] = Color.blue(backgroundColor) / 255.0f;
            Point point = this.mTaskInfo.positionInParent;
            if (CoreRune.MW_CAPTION_SHELL) {
                transaction.setWindowCrop(this.mTaskSurface, relayoutResult.mWidth, relayoutResult.mHeight).setShadowRadius(this.mTaskSurface, dimension).setColor(this.mTaskSurface, this.mTmpColor).show(this.mTaskSurface);
                transaction2.setPosition(this.mTaskSurface, point.x, point.y).setWindowCrop(this.mTaskSurface, relayoutResult.mWidth, relayoutResult.mHeight);
            } else {
                if (z5) {
                    int i7 = -1;
                    transaction.setWindowCrop(this.mTaskSurface, -1, -1);
                    MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this;
                    if (this.mIsRemoving || z2) {
                        transaction2.setWindowCrop(this.mTaskSurface, -1, -1);
                    } else {
                        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                            FreeformStashState freeformStashState = multitaskingWindowDecoration.mFreeformStashState;
                            if (((freeformStashState.mAnimType != -1 && freeformStashState.mAnimating) && freeformStashState.mScale != 1.0f) || multitaskingWindowDecoration.isMotionOrBoundsAnimating()) {
                                transaction2.setWindowCrop(this.mTaskSurface, -1, -1);
                            } else {
                                i7 = -1;
                            }
                        }
                        if (multitaskingWindowDecoration.mAdjustState.mAnimating) {
                            transaction2.setWindowCrop(this.mTaskSurface, i7, i7);
                        } else {
                            transaction2.setPosition(this.mTaskSurface, point.x, point.y).setWindowCrop(this.mTaskSurface, i7, i7);
                        }
                    }
                }
                if (WindowConfiguration.isDexTaskDocking(this.mTaskInfo.configuration.windowConfiguration.getDexTaskDockingState())) {
                    transaction2.setCrop(this.mTaskSurface, new Rect(0, -relayoutResult.mCaptionHeight, ((MultitaskingWindowDecoration) this).getFreeformThickness$1() + relayoutResult.mWidth, relayoutResult.mHeight));
                }
                transaction.show(this.mTaskSurface);
            }
            if (this.mTaskInfo.getWindowingMode() == 5) {
                transaction.setCornerRadius(this.mTaskSurface, relayoutParams.mCornerRadius);
                transaction2.setCornerRadius(this.mTaskSurface, relayoutParams.mCornerRadius);
            }
            if (!CoreRune.MW_CAPTION_SHELL && shouldHideHandlerByAppRequest(this.mTaskInfo)) {
                if (CoreRune.SAFE_DEBUG) {
                    Log.w(getTag(), "relayout: do not need to create a caption window=" + this.mTaskInfo.taskId);
                    return;
                }
                return;
            }
            if (this.mCaptionWindowManager == null) {
                if (CoreRune.MW_CAPTION_SHELL) {
                    this.mCaptionWindowManager = new WindowlessWindowManager(this.mTaskInfo.getConfiguration(), this.mCaptionContainerSurface, (IBinder) null, this.mTaskInfo.token, this.mContext.getResources().getConfiguration());
                } else {
                    this.mCaptionWindowManager = new WindowlessWindowManager(this.mTaskInfo.getConfiguration(), this.mCaptionContainerSurface, (IBinder) null);
                }
            }
            this.mCaptionWindowManager.setConfiguration(configurationWithOverrides);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, loadDimensionPixelSize, 2, 8, -2);
            layoutParams.setTitle("Caption of Task=" + this.mTaskInfo.taskId);
            layoutParams.setTrustedOverlay();
            layoutParams.multiwindowFlags = 1;
            surfaceControlViewHost = this.mViewHost;
            if (surfaceControlViewHost == null) {
                if (relayoutParams.mApplyStartTransactionOnDraw) {
                    surfaceControlViewHost.getRootSurfaceControl().applyTransactionOnDraw(transaction);
                }
                this.mViewHost.relayout(layoutParams);
                return;
            }
            SurfaceControlViewHostFactory surfaceControlViewHostFactory = this.mSurfaceControlViewHostFactory;
            Context context = this.mDecorWindowContext;
            Display display3 = this.mDisplay;
            WindowlessWindowManager windowlessWindowManager = this.mCaptionWindowManager;
            surfaceControlViewHostFactory.getClass();
            SurfaceControlViewHost surfaceControlViewHost2 = new SurfaceControlViewHost(context, display3, windowlessWindowManager, "WindowDecoration");
            this.mViewHost = surfaceControlViewHost2;
            if (relayoutParams.mApplyStartTransactionOnDraw) {
                surfaceControlViewHost2.getRootSurfaceControl().applyTransactionOnDraw(transaction);
            }
            this.mViewHost.setView(relayoutResult.mRootView, layoutParams);
            return;
        }
        releaseViews();
        Display display4 = this.mDisplayController.getDisplay(this.mTaskInfo.displayId);
        this.mDisplay = display4;
        if (display4 == null) {
            this.mDisplayController.addDisplayWindowListener(this.mOnDisplaysChangedListener);
            z3 = false;
        } else {
            z3 = true;
        }
        if (!z3) {
            relayoutResult.mRootView = null;
            return;
        }
        this.mDecorWindowContext = this.mContext.createConfigurationContext(configurationWithOverrides);
        if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && (display = this.mDisplay) != null && display.getDisplayId() != this.mDecorWindowContext.getDisplayId()) {
            this.mDecorWindowContext.updateDisplay(this.mDisplay.getDisplayId());
        }
        if (relayoutParams.mLayoutResId != 0) {
            relayoutResult.mRootView = LayoutInflater.from(this.mDecorWindowContext).inflate(relayoutParams.mLayoutResId, (ViewGroup) null);
        }
        if (relayoutResult.mRootView == null) {
        }
        Resources resources2 = this.mDecorWindowContext.getResources();
        Rect bounds2 = configurationWithOverrides.windowConfiguration.getBounds();
        z4 = this instanceof MultitaskingWindowDecoration;
        if (z4) {
        }
        relayoutResult.mWidth = bounds2.width();
        relayoutResult.mHeight = bounds2.height();
        if (this.mDecorationContainerSurface == null) {
        }
        if (this.mTaskInfo.getWindowingMode() != 5) {
        }
        if (CoreRune.MW_CAPTION_SHELL) {
        }
        transaction.setWindowCrop(this.mDecorationContainerSurface, relayoutResult.mWidth, relayoutResult.mHeight).show(this.mDecorationContainerSurface);
        if (this.mCaptionContainerSurface == null) {
        }
        loadDimensionPixelSize = loadDimensionPixelSize(relayoutParams.mCaptionHeightId, resources2);
        width = bounds2.width();
        if (CoreRune.MW_CAPTION_SHELL) {
        }
        if (ViewRootImpl.CAPTION_ON_SHELL) {
        }
        int i62 = relayoutParams.mShadowRadiusId;
        if (i62 != 0) {
        }
        int backgroundColor2 = this.mTaskInfo.taskDescription.getBackgroundColor();
        this.mTmpColor[0] = Color.red(backgroundColor2) / 255.0f;
        this.mTmpColor[1] = Color.green(backgroundColor2) / 255.0f;
        this.mTmpColor[2] = Color.blue(backgroundColor2) / 255.0f;
        Point point2 = this.mTaskInfo.positionInParent;
        if (CoreRune.MW_CAPTION_SHELL) {
        }
        if (this.mTaskInfo.getWindowingMode() == 5) {
        }
        if (!CoreRune.MW_CAPTION_SHELL) {
        }
        if (this.mCaptionWindowManager == null) {
        }
        this.mCaptionWindowManager.setConfiguration(configurationWithOverrides);
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(width, loadDimensionPixelSize, 2, 8, -2);
        layoutParams2.setTitle("Caption of Task=" + this.mTaskInfo.taskId);
        layoutParams2.setTrustedOverlay();
        layoutParams2.multiwindowFlags = 1;
        surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost == null) {
        }
    }

    public void releaseViews() {
        boolean z;
        SurfaceControl surfaceControl;
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            this.mViewHost = null;
        }
        this.mCaptionWindowManager = null;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mSurfaceControlTransactionSupplier.get();
        SurfaceControl surfaceControl2 = this.mCaptionContainerSurface;
        boolean z2 = true;
        if (surfaceControl2 != null) {
            transaction.remove(surfaceControl2);
            this.mCaptionContainerSurface = null;
            z = true;
        } else {
            z = false;
        }
        SurfaceControl surfaceControl3 = this.mDecorationContainerSurface;
        if (surfaceControl3 != null) {
            transaction.remove(surfaceControl3);
            this.mDecorationContainerSurface = null;
            z = true;
        }
        if (!CoreRune.MW_CAPTION_SHELL || (surfaceControl = this.mDragResizeInputSurface) == null) {
            z2 = z;
        } else {
            transaction.remove(surfaceControl);
            this.mDragResizeInputSurface = null;
        }
        if (z2) {
            transaction.apply();
        }
        WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) this.mWindowContainerTransactionSupplier.get();
        windowContainerTransaction.removeInsetsSource(this.mTaskInfo.token, this.mOwner, 0, WindowInsets.Type.captionBar());
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final boolean shouldHideHandlerByAppRequest(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return ((CoreRune.MW_CAPTION_SHELL_DEX && this.mIsDexMode) || runningTaskInfo.isFreeform() || !runningTaskInfo.isCaptionHandlerHidden) ? false : true;
    }

    public final void updateDexStates() {
        this.mIsDexMode = this.mTaskInfo.configuration.isDexMode();
        boolean z = true;
        boolean z2 = CoreRune.MT_NEW_DEX && this.mTaskInfo.configuration.isNewDexMode();
        this.mIsNewDexMode = z2;
        if (!this.mIsDexMode && !z2) {
            z = false;
        }
        this.mIsDexEnabled = z;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.windowdecor.WindowDecoration$1] */
    public WindowDecoration(Context context, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, Supplier<WindowContainerTransaction> supplier3, SurfaceControlViewHostFactory surfaceControlViewHostFactory) {
        Display display;
        this.mOnDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.windowdecor.WindowDecoration.1
            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayAdded(int i) {
                WindowDecoration windowDecoration = WindowDecoration.this;
                if (windowDecoration.mTaskInfo.displayId != i) {
                    return;
                }
                windowDecoration.mDisplayController.removeDisplayWindowListener(this);
                windowDecoration.relayout(windowDecoration.mTaskInfo);
            }
        };
        this.mIsRemoving = false;
        this.mOwner = new Binder();
        this.mCaptionInsetsRect = new Rect();
        this.mTmpColor = new float[3];
        this.mContext = context;
        this.mDisplayController = displayController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mTaskInfo = runningTaskInfo;
        this.mTaskSurface = surfaceControl;
        this.mSurfaceControlBuilderSupplier = supplier;
        this.mSurfaceControlTransactionSupplier = supplier2;
        this.mWindowContainerTransactionSupplier = supplier3;
        this.mSurfaceControlViewHostFactory = surfaceControlViewHostFactory;
        this.mDisplay = displayController.getDisplay(runningTaskInfo.displayId);
        this.mDecorWindowContext = context.createConfigurationContext(getConfigurationWithOverrides(this.mTaskInfo));
        if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && (display = this.mDisplay) != null && display.getDisplayId() != this.mDecorWindowContext.getDisplayId()) {
            this.mDecorWindowContext.updateDisplay(this.mDisplay.getDisplayId());
        }
        if (CoreRune.MW_CAPTION_SHELL_DEX) {
            updateDexStates();
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            this.mIsRemoving = false;
        }
    }
}
