package com.android.systemui.blur.data.repository;

import com.android.systemui.util.SettingsHelper;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecBlurSettingsRepositoryImpl implements SecBlurSettingsRepository {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(SecBlurSettingsRepositoryImpl.class).getSimpleName();
    public final ReadonlyStateFlow blurReduced;
    public final ReadonlyStateFlow minimalBatteryUse;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SecBlurSettingsRepositoryImpl(CoroutineScope coroutineScope, SettingsHelper settingsHelper) {
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new SecBlurSettingsRepositoryImpl$blurReduced$1(settingsHelper, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.blurReduced = FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, startedEagerly, Boolean.valueOf(settingsHelper.isReduceTransparencyEnabled()));
        this.minimalBatteryUse = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new SecBlurSettingsRepositoryImpl$minimalBatteryUse$1(settingsHelper, null)), coroutineScope, startedEagerly, Boolean.valueOf(settingsHelper.isUltraPowerSavingMode()));
    }
}
