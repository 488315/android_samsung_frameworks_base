package com.android.wm.shell.onehanded;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import android.window.WindowContainerToken;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.onehanded.OneHandedAnimationController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class OneHandedDisplayAreaOrganizer extends DisplayAreaOrganizer {
    public final OneHandedAnimationController mAnimationController;
    public final Context mContext;
    public final Rect mDefaultDisplayBounds;
    public final ArrayMap mDisplayAreaTokenMap;
    public final DisplayLayout mDisplayLayout;
    public final int mEnterExitAnimationDurationMs;
    public final Handler mHandler;
    public boolean mIsReady;
    public final InteractionJankMonitor mJankMonitor;
    public final Rect mLastVisualDisplayBounds;
    public float mLastVisualOffset;
    OneHandedAnimationCallback mOneHandedAnimationCallback;
    public final ViewCompat$$ExternalSyntheticLambda0 mSurfaceControlTransactionFactory;
    public final List mTransitionCallbacks;
    public OneHandedTutorialHandler mTutorialHandler;

    public OneHandedDisplayAreaOrganizer(Context context, DisplayLayout displayLayout, OneHandedSettingsUtil oneHandedSettingsUtil, OneHandedAnimationController oneHandedAnimationController, OneHandedTutorialHandler oneHandedTutorialHandler, InteractionJankMonitor interactionJankMonitor, ShellExecutor shellExecutor, Handler handler) {
        super(shellExecutor);
        this.mDisplayLayout = new DisplayLayout();
        this.mLastVisualDisplayBounds = new Rect();
        this.mDefaultDisplayBounds = new Rect();
        this.mLastVisualOffset = 0.0f;
        this.mDisplayAreaTokenMap = new ArrayMap();
        this.mTransitionCallbacks = new ArrayList();
        this.mOneHandedAnimationCallback = new OneHandedAnimationCallback() { // from class: com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer.1
            @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
            public final void onOneHandedAnimationCancel(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = OneHandedDisplayAreaOrganizer.this;
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator2 = (OneHandedAnimationController.OneHandedTransitionAnimator) oneHandedDisplayAreaOrganizer.mAnimationController.mAnimatorMap.remove(oneHandedTransitionAnimator.mToken);
                if (oneHandedTransitionAnimator2 != null && oneHandedTransitionAnimator2.isRunning()) {
                    oneHandedTransitionAnimator2.cancel();
                }
                boolean z = oneHandedTransitionAnimator.mTransitionDirection == 1;
                if (oneHandedDisplayAreaOrganizer.mAnimationController.mAnimatorMap.isEmpty()) {
                    oneHandedDisplayAreaOrganizer.mJankMonitor.cancel(z ? 42 : 43);
                    oneHandedDisplayAreaOrganizer.finishOffset((int) (oneHandedTransitionAnimator.mEndValue - oneHandedTransitionAnimator.mStartValue), oneHandedTransitionAnimator.mTransitionDirection);
                }
            }

            @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
            public final void onOneHandedAnimationEnd(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = OneHandedDisplayAreaOrganizer.this;
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator2 = (OneHandedAnimationController.OneHandedTransitionAnimator) oneHandedDisplayAreaOrganizer.mAnimationController.mAnimatorMap.remove(oneHandedTransitionAnimator.mToken);
                if (oneHandedTransitionAnimator2 != null && oneHandedTransitionAnimator2.isRunning()) {
                    oneHandedTransitionAnimator2.cancel();
                }
                boolean z = oneHandedTransitionAnimator.mTransitionDirection == 1;
                if (oneHandedDisplayAreaOrganizer.mAnimationController.mAnimatorMap.isEmpty()) {
                    oneHandedDisplayAreaOrganizer.mJankMonitor.end(z ? 42 : 43);
                    oneHandedDisplayAreaOrganizer.finishOffset((int) (oneHandedTransitionAnimator.mEndValue - oneHandedTransitionAnimator.mStartValue), oneHandedTransitionAnimator.mTransitionDirection);
                }
            }

            @Override // com.android.wm.shell.onehanded.OneHandedAnimationCallback
            public final void onOneHandedAnimationStart(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = OneHandedDisplayAreaOrganizer.this;
                if (((ArrayList) oneHandedDisplayAreaOrganizer.mTransitionCallbacks).isEmpty()) {
                    return;
                }
                for (int size = ((ArrayList) oneHandedDisplayAreaOrganizer.mTransitionCallbacks).size() - 1; size >= 0; size--) {
                    ((OneHandedTransitionCallback) ((ArrayList) oneHandedDisplayAreaOrganizer.mTransitionCallbacks).get(size)).onStartTransition();
                }
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        setDisplayLayout(displayLayout);
        this.mAnimationController = oneHandedAnimationController;
        this.mJankMonitor = interactionJankMonitor;
        this.mEnterExitAnimationDurationMs = SystemProperties.getInt("persist.debug.one_handed_translate_animation_duration", context.getResources().getInteger(R.integer.config_one_handed_translate_animation_duration));
        this.mSurfaceControlTransactionFactory = new ViewCompat$$ExternalSyntheticLambda0();
        this.mTutorialHandler = oneHandedTutorialHandler;
    }

    public final void beginCUJTracing(int i, String str) {
        InteractionJankMonitor.Configuration.Builder builderWithSurface = InteractionJankMonitor.Configuration.Builder.withSurface(i, this.mContext, getDisplayAreaTokenMap().entrySet().iterator().next().getValue(), this.mHandler);
        if (!TextUtils.isEmpty(str)) {
            builderWithSurface.setTag(str);
        }
        this.mJankMonitor.begin(builderWithSurface);
    }

    public void finishOffset(int i, int i2) {
        if (i2 == 2) {
            resetWindowsOffset();
        }
        float f = i2 == 1 ? i : 0.0f;
        this.mLastVisualOffset = f;
        this.mLastVisualDisplayBounds.offsetTo(0, Math.round(f));
        for (int size = ((ArrayList) this.mTransitionCallbacks).size() - 1; size >= 0; size--) {
            OneHandedTransitionCallback oneHandedTransitionCallback = (OneHandedTransitionCallback) ((ArrayList) this.mTransitionCallbacks).get(size);
            if (i2 == 1) {
                oneHandedTransitionCallback.onStartFinished(this.mLastVisualDisplayBounds);
            } else {
                oneHandedTransitionCallback.onStopFinished(this.mLastVisualDisplayBounds);
            }
        }
    }

    public ArrayMap<WindowContainerToken, SurfaceControl> getDisplayAreaTokenMap() {
        return this.mDisplayAreaTokenMap;
    }

    public Rect getLastDisplayBounds() {
        return this.mLastVisualDisplayBounds;
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        surfaceControl.setUnreleasedWarningCallSite("OneHandedSiaplyAreaOrganizer.onDisplayAreaAppeared");
        this.mDisplayAreaTokenMap.put(displayAreaInfo.token, surfaceControl);
    }

    public final void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) {
        SurfaceControl surfaceControl = (SurfaceControl) this.mDisplayAreaTokenMap.get(displayAreaInfo.token);
        if (surfaceControl != null) {
            surfaceControl.release();
        }
        this.mDisplayAreaTokenMap.remove(displayAreaInfo.token);
    }

    public final List registerOrganizer(int i) {
        List listRegisterOrganizer = super.registerOrganizer(i);
        for (int i2 = 0; i2 < listRegisterOrganizer.size(); i2++) {
            DisplayAreaAppearedInfo displayAreaAppearedInfo = (DisplayAreaAppearedInfo) listRegisterOrganizer.get(i2);
            onDisplayAreaAppeared(displayAreaAppearedInfo.getDisplayAreaInfo(), displayAreaAppearedInfo.getLeash());
        }
        this.mIsReady = true;
        updateDisplayBounds();
        return listRegisterOrganizer;
    }

    public void resetWindowsOffset() {
        getClass();
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        this.mDisplayAreaTokenMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.f$0;
                SurfaceControl.Transaction transaction2 = transaction;
                SurfaceControl surfaceControl = (SurfaceControl) obj2;
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = (OneHandedAnimationController.OneHandedTransitionAnimator) oneHandedDisplayAreaOrganizer.mAnimationController.mAnimatorMap.remove((WindowContainerToken) obj);
                if (oneHandedTransitionAnimator != null && oneHandedTransitionAnimator.isRunning()) {
                    oneHandedTransitionAnimator.cancel();
                }
                transaction2.setPosition(surfaceControl, 0.0f, 0.0f).setWindowCrop(surfaceControl, -1, -1).setCornerRadius(surfaceControl, -1.0f);
            }
        });
        transaction.apply();
        this.mLastVisualOffset = 0.0f;
        this.mLastVisualDisplayBounds.offsetTo(0, 0);
    }

    public final void scheduleOffset(final int i) {
        final float f = this.mLastVisualOffset;
        final int i2 = i > 0 ? 1 : 2;
        if (i2 == 1) {
            beginCUJTracing(42, "enterOneHanded");
        } else {
            beginCUJTracing(43, "stopOneHanded");
        }
        this.mDisplayAreaTokenMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.f$0;
                float f2 = f;
                int i3 = i;
                int i4 = i2;
                WindowContainerToken windowContainerToken = (WindowContainerToken) obj;
                SurfaceControl surfaceControl = (SurfaceControl) obj2;
                float f3 = i3;
                int i5 = oneHandedDisplayAreaOrganizer.mEnterExitAnimationDurationMs;
                OneHandedAnimationController oneHandedAnimationController = oneHandedDisplayAreaOrganizer.mAnimationController;
                Rect rect = oneHandedDisplayAreaOrganizer.mLastVisualDisplayBounds;
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = (OneHandedAnimationController.OneHandedTransitionAnimator) oneHandedAnimationController.mAnimatorMap.get(windowContainerToken);
                OneHandedAnimationController.OneHandedInterpolator oneHandedInterpolator = oneHandedAnimationController.mInterpolator;
                OneHandedSurfaceTransactionHelper oneHandedSurfaceTransactionHelper = oneHandedAnimationController.mSurfaceTransactionHelper;
                if (oneHandedTransitionAnimator == null) {
                    HashMap map = oneHandedAnimationController.mAnimatorMap;
                    OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimatorOfYOffset = OneHandedAnimationController.OneHandedTransitionAnimator.ofYOffset(windowContainerToken, surfaceControl, f2, f3, rect);
                    oneHandedTransitionAnimatorOfYOffset.mSurfaceTransactionHelper = oneHandedSurfaceTransactionHelper;
                    oneHandedTransitionAnimatorOfYOffset.setInterpolator(oneHandedInterpolator);
                    oneHandedTransitionAnimatorOfYOffset.setFloatValues(0.0f, 1.0f);
                    map.put(windowContainerToken, oneHandedTransitionAnimatorOfYOffset);
                } else if (oneHandedTransitionAnimator.isRunning()) {
                    oneHandedTransitionAnimator.mEndValue = f3;
                } else {
                    oneHandedTransitionAnimator.cancel();
                    HashMap map2 = oneHandedAnimationController.mAnimatorMap;
                    OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimatorOfYOffset2 = OneHandedAnimationController.OneHandedTransitionAnimator.ofYOffset(windowContainerToken, surfaceControl, f2, f3, rect);
                    oneHandedTransitionAnimatorOfYOffset2.mSurfaceTransactionHelper = oneHandedSurfaceTransactionHelper;
                    oneHandedTransitionAnimatorOfYOffset2.setInterpolator(oneHandedInterpolator);
                    oneHandedTransitionAnimatorOfYOffset2.setFloatValues(0.0f, 1.0f);
                    map2.put(windowContainerToken, oneHandedTransitionAnimatorOfYOffset2);
                }
                OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator2 = (OneHandedAnimationController.OneHandedTransitionAnimator) oneHandedAnimationController.mAnimatorMap.get(windowContainerToken);
                if (oneHandedTransitionAnimator2 != null) {
                    oneHandedTransitionAnimator2.mTransitionDirection = i4;
                    OneHandedAnimationCallback oneHandedAnimationCallback = oneHandedDisplayAreaOrganizer.mOneHandedAnimationCallback;
                    if (oneHandedAnimationCallback != null) {
                        ((ArrayList) oneHandedTransitionAnimator2.mOneHandedAnimationCallbacks).add(oneHandedAnimationCallback);
                    }
                    OneHandedTutorialHandler oneHandedTutorialHandler = oneHandedDisplayAreaOrganizer.mTutorialHandler;
                    if (oneHandedTutorialHandler != null) {
                        ((ArrayList) oneHandedTransitionAnimator2.mOneHandedAnimationCallbacks).add(oneHandedTutorialHandler);
                    }
                    oneHandedTransitionAnimator2.setDuration(i5).start();
                }
            }
        });
        this.mLastVisualOffset = i;
    }

    public void setDisplayLayout(DisplayLayout displayLayout) {
        this.mDisplayLayout.set(displayLayout);
        updateDisplayBounds();
    }

    public final void unregisterOrganizer() {
        super.unregisterOrganizer();
        this.mIsReady = false;
        resetWindowsOffset();
    }

    public void updateDisplayBounds() {
        Rect rect = this.mDefaultDisplayBounds;
        DisplayLayout displayLayout = this.mDisplayLayout;
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        this.mLastVisualDisplayBounds.set(this.mDefaultDisplayBounds);
    }
}
