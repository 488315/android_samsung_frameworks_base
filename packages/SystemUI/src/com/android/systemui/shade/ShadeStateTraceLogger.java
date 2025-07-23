package com.android.systemui.shade;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeStateTraceLogger implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConfigurationRepository configurationRepository;
    public final LogBuffer logBuffer;
    public final CoroutineScope scope;
    public final Lazy shadeDisplaysRepository;
    public final ShadeInteractor shadeInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

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

    public ShadeStateTraceLogger(ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, Lazy lazy, ConfigurationRepository configurationRepository, CoroutineScope coroutineScope, LogBuffer logBuffer) {
        this.shadeInteractor = shadeInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
        this.shadeDisplaysRepository = lazy;
        this.configurationRepository = configurationRepository;
        this.scope = coroutineScope;
        this.logBuffer = logBuffer;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new ShadeStateTraceLogger$start$1(this, null), 6);
    }
}
