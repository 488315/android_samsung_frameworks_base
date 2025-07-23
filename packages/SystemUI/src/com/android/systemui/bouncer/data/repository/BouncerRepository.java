package com.android.systemui.bouncer.data.repository;

import android.content.Context;
import com.android.systemui.authentication.shared.model.BouncerInputSide;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.util.settings.GlobalSettings;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerRepository {
    public final Context applicationContext;
    public final FeatureFlagsClassic flags;
    public final GlobalSettings globalSettings;
    public final StateFlowImpl lastRecordedLockscreenTouchPosition;
    public final StateFlowImpl preferredBouncerInputSide;
    public final StateFlowImpl scale = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));

    public BouncerRepository(Context context, GlobalSettings globalSettings, FeatureFlagsClassic featureFlagsClassic) {
        this.applicationContext = context;
        this.globalSettings = globalSettings;
        this.flags = featureFlagsClassic;
        int i = globalSettings.getInt("one_handed_keyguard_side", -1);
        this.preferredBouncerInputSide = StateFlowKt.MutableStateFlow(i != 0 ? i != 1 ? null : BouncerInputSide.RIGHT : BouncerInputSide.LEFT);
        this.lastRecordedLockscreenTouchPosition = StateFlowKt.MutableStateFlow(null);
    }
}
