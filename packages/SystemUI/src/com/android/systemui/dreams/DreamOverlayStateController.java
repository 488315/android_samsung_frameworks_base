package com.android.systemui.dreams;

import com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda10;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.reference.WeakReferenceFactory;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DreamOverlayStateController implements CallbackController {
    public final Executor mExecutor;
    public final DreamLogger mLogger;
    public int mState;
    public final int mSupportedTypes;
    public final WeakReferenceFactory mWeakReferenceFactory;
    public final ArrayList mCallbacks = new ArrayList();
    public int mAvailableComplicationTypes = 0;
    public boolean mShouldShowComplications = false;
    public final Collection mComplications = new HashSet();

    public DreamOverlayStateController(Executor executor, FeatureFlags featureFlags, LogBuffer logBuffer, WeakReferenceFactory weakReferenceFactory) {
        this.mExecutor = executor;
        this.mLogger = new DreamLogger(logBuffer, "DreamOverlayStateCtlr");
        this.mWeakReferenceFactory = weakReferenceFactory;
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.ALWAYS_SHOW_HOME_CONTROLS_ON_DREAMS)) {
            this.mSupportedTypes = 32;
        } else {
            this.mSupportedTypes = 0;
        }
    }

    public final boolean containsState(int i) {
        return (this.mState & i) != 0;
    }

    public final Collection getComplications() {
        return (containsState(2) || containsState(64)) ? Collections.EMPTY_LIST : Collections.unmodifiableCollection((Collection) this.mComplications.stream().filter(new DreamOverlayStateController$$ExternalSyntheticLambda3(this, 0)).collect(Collectors.toCollection(new TouchMonitor$$ExternalSyntheticLambda10())));
    }

    public final void modifyState(int i, int i2) {
        int i3 = this.mState;
        if (i == 1) {
            this.mState = (~i2) & i3;
        } else if (i == 2) {
            this.mState = i3 | i2;
        }
        if (i3 != this.mState) {
            this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda1(this, new DreamOverlayStateController$$ExternalSyntheticLambda0(0), 1));
        }
    }

    public final void notifyCallbacksLocked(Consumer consumer) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            Callback callback = (Callback) ((WeakReference) it.next()).get();
            if (callback == null) {
                it.remove();
            } else {
                consumer.accept(callback);
            }
        }
    }

    public final void setDreamOverlayStatusBarVisible(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new DreamLogger$$ExternalSyntheticLambda0(0), null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        modifyState(z ? 2 : 1, 32);
    }

    public final void setLowLightActive(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new DreamLogger$$ExternalSyntheticLambda0(4), null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        if (containsState(2) && !z) {
            this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda1(this, new DreamOverlayStateController$$ExternalSyntheticLambda0(2), 1));
        }
        modifyState(z ? 2 : 1, 2);
    }

    public final void setOverlayActive(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new DreamLogger$$ExternalSyntheticLambda0(3), null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        modifyState(z ? 2 : 1, 1);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callback callback) {
        this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda2(this, callback, 1));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Callback callback) {
        this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda2(this, callback, 0));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        default void onAvailableComplicationTypesChanged() {
        }

        default void onComplicationsChanged() {
        }

        default void onExitLowLight() {
        }

        default void onStateChanged() {
        }
    }
}
