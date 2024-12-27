package com.android.server.display.mode;

import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.SurfaceControl;

import com.android.internal.os.BackgroundThread;
import com.android.server.LocalServices;
import com.android.server.sensors.SensorManagerInternal$ProximityActiveListener;
import com.android.server.sensors.SensorService;

import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProximitySensorObserver
        implements SensorManagerInternal$ProximityActiveListener, DisplayManager.DisplayListener {
    public DisplayManagerInternal mDisplayManagerInternal;
    public final DisplayModeDirector.Injector mInjector;
    public final VotesStorage mVotesStorage;
    public final SparseBooleanArray mDozeStateByDisplay = new SparseBooleanArray();
    public final Object mSensorObserverLock = new Object();
    public boolean mIsProxActive = false;

    public ProximitySensorObserver(
            VotesStorage votesStorage, DisplayModeDirector.Injector injector) {
        this.mVotesStorage = votesStorage;
        this.mInjector = injector;
    }

    public final void observe() {
        ((DisplayModeDirector.RealInjector) this.mInjector).getClass();
        this.mDisplayManagerInternal =
                (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        ((DisplayModeDirector.RealInjector) this.mInjector).getClass();
        SensorService.LocalService localService =
                (SensorService.LocalService)
                        LocalServices.getService(SensorService.LocalService.class);
        Executor executor = BackgroundThread.getExecutor();
        localService.getClass();
        Objects.requireNonNull(executor, "executor must not be null");
        SensorService.ProximityListenerProxy proximityListenerProxy =
                new SensorService.ProximityListenerProxy(executor, this);
        synchronized (SensorService.this.mLock) {
            try {
                if (SensorService.this.mProximityListeners.containsKey(this)) {
                    throw new IllegalArgumentException("listener already registered");
                }
                SensorService.this.mProximityListeners.put(this, proximityListenerProxy);
                if (SensorService.this.mProximityListeners.size() == 1) {
                    SensorService.registerProximityActiveListenerNative(SensorService.this.mPtr);
                }
            } finally {
            }
        }
        synchronized (this.mSensorObserverLock) {
            try {
                for (Display display :
                        ((DisplayModeDirector.RealInjector) this.mInjector)
                                .getDisplayManager()
                                .getDisplays(
                                        "android.hardware.display.category.ALL_INCLUDING_DISABLED")) {
                    SparseBooleanArray sparseBooleanArray = this.mDozeStateByDisplay;
                    int displayId = display.getDisplayId();
                    ((DisplayModeDirector.RealInjector) this.mInjector).getClass();
                    sparseBooleanArray.put(displayId, Display.isDozeState(display.getState()));
                }
            } finally {
            }
        }
        ((DisplayModeDirector.RealInjector) this.mInjector)
                .getDisplayManager()
                .registerDisplayListener(this, BackgroundThread.getHandler(), 7L);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
        DisplayModeDirector.RealInjector realInjector =
                (DisplayModeDirector.RealInjector) this.mInjector;
        Display display = realInjector.getDisplayManager().getDisplay(i);
        realInjector.getClass();
        boolean isDozeState = display == null ? false : Display.isDozeState(display.getState());
        synchronized (this.mSensorObserverLock) {
            this.mDozeStateByDisplay.put(i, isDozeState);
            recalculateVotesLocked();
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        synchronized (this.mSensorObserverLock) {
            try {
                boolean z = this.mDozeStateByDisplay.get(i);
                SparseBooleanArray sparseBooleanArray = this.mDozeStateByDisplay;
                DisplayModeDirector.Injector injector = this.mInjector;
                Display display =
                        ((DisplayModeDirector.RealInjector) injector)
                                .getDisplayManager()
                                .getDisplay(i);
                ((DisplayModeDirector.RealInjector) injector).getClass();
                sparseBooleanArray.put(
                        i, display == null ? false : Display.isDozeState(display.getState()));
                if (z != this.mDozeStateByDisplay.get(i)) {
                    recalculateVotesLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
        synchronized (this.mSensorObserverLock) {
            this.mDozeStateByDisplay.delete(i);
            recalculateVotesLocked();
        }
    }

    @Override // com.android.server.sensors.SensorManagerInternal$ProximityActiveListener
    public final void onProximityActive(boolean z) {
        synchronized (this.mSensorObserverLock) {
            try {
                if (this.mIsProxActive != z) {
                    this.mIsProxActive = z;
                    recalculateVotesLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recalculateVotesLocked() {
        SurfaceControl.RefreshRateRange refreshRateForDisplayAndSensor;
        for (Display display :
                ((DisplayModeDirector.RealInjector) this.mInjector)
                        .getDisplayManager()
                        .getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED")) {
            int displayId = display.getDisplayId();
            CombinedVote combinedVote = null;
            if (this.mIsProxActive
                    && !this.mDozeStateByDisplay.get(displayId)
                    && (refreshRateForDisplayAndSensor =
                                    this.mDisplayManagerInternal.getRefreshRateForDisplayAndSensor(
                                            displayId, (String) null, "android.sensor.proximity"))
                            != null) {
                combinedVote =
                        Vote.forPhysicalRefreshRates(
                                refreshRateForDisplayAndSensor.min,
                                refreshRateForDisplayAndSensor.max);
            }
            this.mVotesStorage.updateVote(displayId, 23, combinedVote);
        }
    }
}
