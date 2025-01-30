package com.android.systemui.shared.navigationbar;

import android.graphics.Rect;
import android.os.Handler;
import android.view.CompositionSamplingListener;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.store.SystemBarProxy;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final RunnableC24822 mRemoveDrawRunnable;
    public final View mSampledView;
    public boolean mSamplingEnabled;
    public final C24833 mSamplingListener;
    public boolean mSamplingListenerRegistered;
    public final Rect mSamplingRequestBounds;
    public final ViewTreeObserverOnDrawListenerC24811 mUpdateOnDraw;
    public boolean mWaitingOnDraw;
    public boolean mWindowVisible;
    public SurfaceControl mWrappedStopLayer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class SysuiCompositionSamplingListener {
    }

    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor) {
        this(view, samplingCallback, view.getContext().getMainExecutor(), executor);
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "RegionSamplingHelper:", "\tsampleView isAttached: ");
        m75m.append(this.mSampledView.isAttachedToWindow());
        printWriter.println(m75m.toString());
        StringBuilder sb = new StringBuilder("\tsampleView isScValid: ");
        sb.append(this.mSampledView.isAttachedToWindow() ? Boolean.valueOf(this.mSampledView.getViewRootImpl().getSurfaceControl().isValid()) : "notAttached");
        printWriter.println(sb.toString());
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("\tmSamplingEnabled: "), this.mSamplingEnabled, printWriter, "\tmSamplingListenerRegistered: "), this.mSamplingListenerRegistered, printWriter, "\tmSamplingRequestBounds: ");
        m64m.append(this.mSamplingRequestBounds);
        printWriter.println(m64m.toString());
        printWriter.println("\tmRegisteredSamplingBounds: " + this.mRegisteredSamplingBounds);
        StringBuilder m81m = LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(new StringBuilder("\tmLastMedianLuma: "), this.mLastMedianLuma, printWriter, "\tmCurrentMedianLuma: "), this.mCurrentMedianLuma, printWriter, "\tmWindowVisible: ");
        m81m.append(this.mWindowVisible);
        printWriter.println(m81m.toString());
        printWriter.println("\tmWindowHasBlurs: false");
        StringBuilder m64m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("\tmWaitingOnDraw: "), this.mWaitingOnDraw, printWriter, "\tmRegisteredStopLayer: ");
        m64m2.append(this.mRegisteredStopLayer);
        printWriter.println(m64m2.toString());
        printWriter.println("\tmWrappedStopLayer: " + this.mWrappedStopLayer);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("\tmIsDestroyed: "), this.mIsDestroyed, printWriter);
        if (BasicRuneWrapper.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("\tmIsWindowGone: "), this.mIsWindowGone, printWriter);
        }
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
        final C24833 c24833 = this.mSamplingListener;
        Objects.requireNonNull(c24833);
        executor.execute(new Runnable() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                c24833.destroy();
            }
        });
        this.mIsDestroyed = true;
        this.mSampledView.removeOnAttachStateChangeListener(this);
        this.mSampledView.removeOnLayoutChangeListener(this);
        this.mSampledView.getViewTreeObserver().removeOnDrawListener(this.mUpdateOnDraw);
    }

    public final void start(Rect rect) {
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            int i = ((SamsungNavigationBarProxy) this.mBarProxy).navbarTransitionMode;
            if (i == 4 || i == 3) {
                return;
            }
        }
        if (this.mCallback.isSamplingEnabled()) {
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
                    RegionSamplingHelper.C24833 c24833 = regionSamplingHelper.mSamplingListener;
                    sysuiCompositionSamplingListener.getClass();
                    CompositionSamplingListener.unregister(c24833);
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
        if (this.mSamplingEnabled && !this.mSamplingRequestBounds.isEmpty() && this.mWindowVisible && (this.mSampledView.isAttachedToWindow() || this.mFirstSamplingAfterStart) && !(BasicRuneWrapper.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.mIsWindowGone)) {
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
                this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.navigationbar.RegionSamplingHelper$$ExternalSyntheticLambda1
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
                        RegionSamplingHelper.C24833 c24833 = regionSamplingHelper.mSamplingListener;
                        sysuiCompositionSamplingListener.getClass();
                        CompositionSamplingListener.register(c24833, 0, surfaceControl3, rect2);
                    }
                });
                this.mRegisteredSamplingBounds.set(this.mSamplingRequestBounds);
                this.mRegisteredStopLayer = surfaceControl;
                this.mWrappedStopLayer = wrap;
            }
            this.mFirstSamplingAfterStart = false;
        } else {
            unregisterSamplingListener();
        }
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            this.mCallback.onUpdateSamplingListener(this.mSamplingListenerRegistered);
        }
    }

    public final void updateSamplingRect() {
        Rect sampledRegion = this.mCallback.getSampledRegion();
        if (this.mSamplingRequestBounds.equals(sampledRegion)) {
            return;
        }
        this.mSamplingRequestBounds.set(sampledRegion);
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

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.shared.navigationbar.RegionSamplingHelper$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.shared.navigationbar.RegionSamplingHelper$2] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.shared.navigationbar.RegionSamplingHelper$3] */
    public RegionSamplingHelper(View view, SamplingCallback samplingCallback, Executor executor, Executor executor2, SysuiCompositionSamplingListener sysuiCompositionSamplingListener) {
        this.mHandler = new Handler();
        this.mSamplingRequestBounds = new Rect();
        this.mRegisteredSamplingBounds = new Rect();
        this.mSamplingEnabled = false;
        this.mSamplingListenerRegistered = false;
        this.mIsWindowGone = false;
        this.mRegisteredStopLayer = null;
        this.mWrappedStopLayer = null;
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
                            regionSamplingHelper.mCallback.onRegionDarknessChanged(f < 0.5f);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SamplingCallback {
        Rect getSampledRegion();

        boolean isSamplingEnabled();

        void onRegionDarknessChanged(boolean z);

        default void onUpdateSamplingListener(boolean z) {
        }
    }
}
