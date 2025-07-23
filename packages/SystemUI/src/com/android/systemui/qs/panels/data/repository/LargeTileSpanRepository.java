package com.android.systemui.qs.panels.data.repository;

import android.content.res.Resources;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LargeTileSpanRepository {
    public final Resources resources;
    public final ReadonlyStateFlow span;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LargeTileSpanRepository(CoroutineScope coroutineScope, Resources resources, ConfigurationRepository configurationRepository) {
        this.resources = resources;
        this.span = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), ((ConfigurationRepositoryImpl) configurationRepository).onConfigurationChange), new LargeTileSpanRepository$span$1(this, null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 2);
    }
}
