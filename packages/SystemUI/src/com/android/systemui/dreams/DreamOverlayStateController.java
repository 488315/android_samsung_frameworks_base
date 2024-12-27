package com.android.systemui.dreams;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda6;
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
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DreamOverlayStateController implements CallbackController {
    public final ArrayList mCallbacks = new ArrayList();
    public final Collection mComplications = new HashSet();
    public final Executor mExecutor;
    public final DreamLogger mLogger;
    public final boolean mOverlayEnabled;
    public int mState;
    public final WeakReferenceFactory mWeakReferenceFactory;

    public DreamOverlayStateController(Executor executor, boolean z, FeatureFlags featureFlags, LogBuffer logBuffer, WeakReferenceFactory weakReferenceFactory) {
        this.mExecutor = executor;
        this.mOverlayEnabled = z;
        DreamLogger dreamLogger = new DreamLogger(logBuffer, "DreamOverlayStateCtlr");
        this.mLogger = dreamLogger;
        this.mWeakReferenceFactory = weakReferenceFactory;
        ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.ALWAYS_SHOW_HOME_CONTROLS_ON_DREAMS);
        DreamLogger$logDreamOverlayEnabled$1 dreamLogger$logDreamOverlayEnabled$1 = new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logDreamOverlayEnabled$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dream overlay enabled: ", ((LogMessage) obj).getBool1());
            }
        };
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, dreamLogger$logDreamOverlayEnabled$1, null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
    }

    public final boolean containsState(int i) {
        return (this.mState & i) != 0;
    }

    public final Collection getComplications() {
        return (containsState(2) || containsState(64)) ? Collections.emptyList() : Collections.unmodifiableCollection((Collection) this.mComplications.stream().filter(new DreamOverlayStateController$$ExternalSyntheticLambda4(this, 0)).collect(Collectors.toCollection(new TouchMonitor$$ExternalSyntheticLambda6())));
    }

    public final boolean isOverlayActive() {
        return this.mOverlayEnabled && containsState(1);
    }

    public final void modifyState(int i, int i2) {
        int i3 = this.mState;
        if (i == 1) {
            this.mState = (~i2) & i3;
        } else if (i == 2) {
            this.mState = i3 | i2;
        }
        if (i3 != this.mState) {
            this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(this, new DreamOverlayStateController$$ExternalSyntheticLambda0(1), 2));
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

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(this, (Callback) obj, 0));
    }

    public final void setDreamOverlayStatusBarVisible(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logStatusBarVisible$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dream overlay status bar visible: ", ((LogMessage) obj).getBool1());
            }
        }, null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        modifyState(z ? 2 : 1, 32);
    }

    public final void setHasAssistantAttention(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logHasAssistantAttention$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dream overlay has Assistant attention: ", ((LogMessage) obj).getBool1());
            }
        }, null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        modifyState(z ? 2 : 1, 16);
    }

    public final void setLowLightActive(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logLowLightActive$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Low light mode active: ", ((LogMessage) obj).getBool1());
            }
        }, null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        if (containsState(2) && !z) {
            this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(this, new DreamOverlayStateController$$ExternalSyntheticLambda0(0), 2));
        }
        modifyState(z ? 2 : 1, 2);
    }

    public final void setOverlayActive(boolean z) {
        DreamLogger dreamLogger = this.mLogger;
        dreamLogger.getClass();
        LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logOverlayActive$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dream overlay active: ", ((LogMessage) obj).getBool1());
            }
        }, null);
        obtain.setBool1(z);
        dreamLogger.getBuffer().commit(obtain);
        modifyState(z ? 2 : 1, 1);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callback callback) {
        this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(this, callback, 1));
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
