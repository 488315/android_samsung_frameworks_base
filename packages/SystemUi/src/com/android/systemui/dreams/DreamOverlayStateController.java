package com.android.systemui.dreams;

import android.util.Log;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayStateController implements CallbackController {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayStateCtlr", 3);
    public final ArrayList mCallbacks = new ArrayList();
    public final Collection mComplications = new HashSet();
    public final Executor mExecutor;
    public final boolean mOverlayEnabled;
    public int mState;

    public DreamOverlayStateController(Executor executor, boolean z, FeatureFlags featureFlags) {
        this.mExecutor = executor;
        this.mOverlayEnabled = z;
        ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.ALWAYS_SHOW_HOME_CONTROLS_ON_DREAMS);
        if (DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Dream overlay enabled:", z, "DreamOverlayStateCtlr");
        }
    }

    public final boolean containsState(int i) {
        return (this.mState & i) != 0;
    }

    public final Collection getComplications() {
        return containsState(2) ? Collections.emptyList() : Collections.unmodifiableCollection((Collection) this.mComplications.stream().filter(new Predicate() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                DreamOverlayStateController dreamOverlayStateController = DreamOverlayStateController.this;
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
                dreamOverlayStateController.getClass();
                throw null;
            }
        }).collect(Collectors.toCollection(new DreamOverlayStateController$$ExternalSyntheticLambda4())));
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
            this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda2(this, new DreamOverlayStateController$$ExternalSyntheticLambda0(1), 2));
        }
    }

    public final void setLowLightActive(boolean z) {
        if (containsState(2) && !z) {
            this.mCallbacks.forEach(new DreamOverlayStateController$$ExternalSyntheticLambda0(0));
        }
        modifyState(z ? 2 : 1, 2);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callback callback) {
        this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda2(this, callback, 1));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Callback callback) {
        this.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda2(this, callback, 0));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
