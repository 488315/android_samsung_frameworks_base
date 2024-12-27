package com.android.systemui.shared.navigationbar;

import android.graphics.Rect;
import android.os.Handler;
import android.view.CompositionSamplingListener;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.store.SystemBarProxy;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.util.Utils;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RegionSamplingHelper implements View.OnAttachStateChangeListener, View.OnLayoutChangeListener {
    public final Executor mBackgroundExecutor;
    public SystemBarProxy mBarProxy;
    public final SamplingCallback mCallback;
    public final SysuiCompositionSamplingListener mCompositionSamplingListener;
    public float mCurrentMedianLuma;
    public boolean mFirstSamplingAfterStart;
    public final Handler mHandler;
    public boolean mIsDestroyed;
    public boolean mIsWindowGone;
    public float mLastMedianLuma;
    public final Rect mRegisteredSamplingBounds;
    public SurfaceControl mRegisteredStopLayer;
    public final AnonymousClass2 mRemoveDrawRunnable;
    public final View mSampledView;
    public boolean mSamplingEnabled;
    public final AnonymousClass3 mSamplingListener;
    public boolean mSamplingListenerRegistered;
    public final Rect mSamplingRequestBounds;
    public final AnonymousClass1 mUpdateOnDraw;
    public boolean mWaitingOnDraw;
    public boolean mWindowHasBlurs;
    public boolean mWindowVisible;
    public SurfaceControl mWrappedStopLayer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SamplingCallback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class SysuiCompositionSamplingListener {
    }

    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor) {
        this(view, samplingCallback, view.getContext().getMainExecutor(), executor);
    }

    public SamplingCallback getCallback() {
        return this.mCallback;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateSamplingRect();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        updateSamplingListener();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        stop();
        Executor executor = this.mBackgroundExecutor;
        final AnonymousClass3 anonymousClass3 = this.mSamplingListener;
        Objects.requireNonNull(anonymousClass3);
        executor.execute(new Runnable() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                anonymousClass3.destroy();
            }
        });
        this.mIsDestroyed = true;
        this.mSampledView.removeOnAttachStateChangeListener(this);
        this.mSampledView.removeOnLayoutChangeListener(this);
        this.mSampledView.getViewTreeObserver().removeOnDrawListener(this.mUpdateOnDraw);
    }

    public final void start(Rect rect) {
        NavBarStateManager navBarStateManager;
        NavBarStateManager navBarStateManager2;
        int i;
        if (BasicRuneWrapper.NAVBAR_ENABLED && ((i = ((SamsungNavigationBarProxy) this.mBarProxy).navbarTransitionMode) == 4 || i == 3)) {
            return;
        }
        NavigationBar.AnonymousClass10 anonymousClass10 = (NavigationBar.AnonymousClass10) this.mCallback;
        anonymousClass10.getClass();
        boolean z = BasicRune.NAVBAR_SETUP_WIZARD;
        boolean z2 = false;
        NavigationBar navigationBar = NavigationBar.this;
        if (!z || (navBarStateManager2 = navigationBar.mNavBarStateManager) == null || !((NavBarStateManagerImpl) navBarStateManager2).shouldShowSUWStyle()) {
            if (!BasicRune.NAVBAR_POLICY_VISIBILITY || (navBarStateManager = navigationBar.mNavBarStateManager) == null) {
                z2 = (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && navigationBar.mContext.getDisplayId() == 1) ? QuickStepContract.isGesturalMode(navigationBar.mNavBarMode) : Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode);
            } else if (!((NavBarStateManagerImpl) navBarStateManager).isTaskBarEnabled(false)) {
                if (Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode)) {
                    z2 = true;
                }
            }
        }
        if (z2) {
            if (rect != null) {
                this.mSamplingRequestBounds.set(rect);
            }
            this.mSamplingEnabled = true;
            this.mLastMedianLuma = -1.0f;
            this.mFirstSamplingAfterStart = true;
            updateSamplingListener();
        }
    }

    public final void stop() {
        this.mSamplingEnabled = false;
        updateSamplingListener();
    }

    public final void unregisterSamplingListener() {
        if (this.mSamplingListenerRegistered) {
            this.mSamplingListenerRegistered = false;
            final SurfaceControl surfaceControl = this.mWrappedStopLayer;
            this.mRegisteredStopLayer = null;
            this.mWrappedStopLayer = null;
            this.mRegisteredSamplingBounds.setEmpty();
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    RegionSamplingHelper regionSamplingHelper = RegionSamplingHelper.this;
                    SurfaceControl surfaceControl2 = surfaceControl;
                    RegionSamplingHelper.SysuiCompositionSamplingListener sysuiCompositionSamplingListener = regionSamplingHelper.mCompositionSamplingListener;
                    RegionSamplingHelper.AnonymousClass3 anonymousClass3 = regionSamplingHelper.mSamplingListener;
                    sysuiCompositionSamplingListener.getClass();
                    CompositionSamplingListener.unregister(anonymousClass3);
                    if (surfaceControl2 != null && surfaceControl2.isValid()) {
                        surfaceControl2.release();
                    }
                    if (BasicRuneWrapper.NAVBAR_ENABLED) {
                        regionSamplingHelper.mLastMedianLuma = -1.0f;
                    }
                }
            });
        }
    }

    public final void updateSamplingListener() {
        if (!this.mSamplingEnabled || this.mSamplingRequestBounds.isEmpty() || !this.mWindowVisible || this.mWindowHasBlurs || (!(this.mSampledView.isAttachedToWindow() || this.mFirstSamplingAfterStart) || (BasicRuneWrapper.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.mIsWindowGone))) {
            unregisterSamplingListener();
        } else {
            ViewRootImpl viewRootImpl = this.mSampledView.getViewRootImpl();
            SurfaceControl surfaceControl = null;
            SurfaceControl surfaceControl2 = viewRootImpl != null ? viewRootImpl.getSurfaceControl() : null;
            if (surfaceControl2 != null && surfaceControl2.isValid()) {
                surfaceControl = surfaceControl2;
            } else if (!this.mWaitingOnDraw) {
                this.mWaitingOnDraw = true;
                if (this.mHandler.hasCallbacks(this.mRemoveDrawRunnable)) {
                    this.mHandler.removeCallbacks(this.mRemoveDrawRunnable);
                } else {
                    this.mSampledView.getViewTreeObserver().addOnDrawListener(this.mUpdateOnDraw);
                }
            }
            if (!this.mSamplingRequestBounds.equals(this.mRegisteredSamplingBounds) || this.mRegisteredStopLayer != surfaceControl) {
                unregisterSamplingListener();
                this.mSamplingListenerRegistered = true;
                final SurfaceControl wrap = wrap(surfaceControl);
                final Rect rect = new Rect(this.mSamplingRequestBounds);
                this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        RegionSamplingHelper regionSamplingHelper = RegionSamplingHelper.this;
                        SurfaceControl surfaceControl3 = wrap;
                        Rect rect2 = rect;
                        if (surfaceControl3 != null) {
                            regionSamplingHelper.getClass();
                            if (!surfaceControl3.isValid()) {
                                return;
                            }
                        }
                        RegionSamplingHelper.SysuiCompositionSamplingListener sysuiCompositionSamplingListener = regionSamplingHelper.mCompositionSamplingListener;
                        RegionSamplingHelper.AnonymousClass3 anonymousClass3 = regionSamplingHelper.mSamplingListener;
                        sysuiCompositionSamplingListener.getClass();
                        CompositionSamplingListener.register(anonymousClass3, 0, surfaceControl3, rect2);
                    }
                });
                this.mRegisteredSamplingBounds.set(this.mSamplingRequestBounds);
                this.mRegisteredStopLayer = surfaceControl;
                this.mWrappedStopLayer = wrap;
            }
            this.mFirstSamplingAfterStart = false;
        }
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            SamplingCallback samplingCallback = this.mCallback;
            boolean z = this.mSamplingListenerRegistered;
            NavigationBar navigationBar = NavigationBar.this;
            ((NavBarStoreImpl) navigationBar.mNavBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnUpdateRegionSamplingListener(z));
        }
    }

    public final void updateSamplingRect() {
        NavigationBar navigationBar = NavigationBar.this;
        Rect rect = navigationBar.mOrientedHandleSamplingRegion;
        if (rect == null) {
            rect = NavigationBar.m1985$$Nest$mcalculateSamplingRect(navigationBar);
        }
        if (this.mSamplingRequestBounds.equals(rect)) {
            return;
        }
        this.mSamplingRequestBounds.set(rect);
        updateSamplingListener();
    }

    public SurfaceControl wrap(SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            return null;
        }
        return new SurfaceControl(surfaceControl, "regionSampling");
    }

    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor, Executor executor2) {
        this(view, samplingCallback, executor, executor2, new SysuiCompositionSamplingListener());
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.shared.navigationbar.RegionSamplingHelper$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.shared.navigationbar.RegionSamplingHelper$2] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.shared.navigationbar.RegionSamplingHelper$3] */
    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor, Executor executor2, SysuiCompositionSamplingListener sysuiCompositionSamplingListener) {
        this.mHandler = new Handler();
        this.mSamplingRequestBounds = new Rect();
        this.mRegisteredSamplingBounds = new Rect();
        this.mSamplingEnabled = false;
        this.mSamplingListenerRegistered = false;
        this.mRegisteredStopLayer = null;
        this.mWrappedStopLayer = null;
        this.mIsWindowGone = false;
        this.mUpdateOnDraw = new ViewTreeObserver.OnDrawListener() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper.1
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public final void onDraw() {
                RegionSamplingHelper regionSamplingHelper = RegionSamplingHelper.this;
                regionSamplingHelper.mHandler.post(regionSamplingHelper.mRemoveDrawRunnable);
                RegionSamplingHelper regionSamplingHelper2 = RegionSamplingHelper.this;
                if (regionSamplingHelper2.mWaitingOnDraw) {
                    regionSamplingHelper2.mWaitingOnDraw = false;
                    regionSamplingHelper2.updateSamplingListener();
                }
            }
        };
        this.mRemoveDrawRunnable = new Runnable() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper.2
            @Override // java.lang.Runnable
            public final void run() {
                RegionSamplingHelper.this.mSampledView.getViewTreeObserver().removeOnDrawListener(RegionSamplingHelper.this.mUpdateOnDraw);
            }
        };
        this.mBackgroundExecutor = executor2;
        this.mCompositionSamplingListener = sysuiCompositionSamplingListener;
        this.mSamplingListener = new CompositionSamplingListener(executor) { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper.3
            public final void onSampleCollected(float f) {
                RegionSamplingHelper regionSamplingHelper = RegionSamplingHelper.this;
                if (regionSamplingHelper.mSamplingEnabled) {
                    if (!BasicRuneWrapper.NAVBAR_ENABLED || regionSamplingHelper.mSamplingListenerRegistered) {
                        regionSamplingHelper.mCurrentMedianLuma = f;
                        if (Math.abs(f - regionSamplingHelper.mLastMedianLuma) > 0.05f) {
                            NavigationBar.this.mNavigationBarTransitions.mLightTransitionsController.setIconsDark(!(f < 0.5f), true);
                            regionSamplingHelper.mLastMedianLuma = f;
                        }
                    }
                }
            }
        };
        this.mSampledView = view;
        view.addOnAttachStateChangeListener(this);
        view.addOnLayoutChangeListener(this);
        this.mCallback = samplingCallback;
    }
}
