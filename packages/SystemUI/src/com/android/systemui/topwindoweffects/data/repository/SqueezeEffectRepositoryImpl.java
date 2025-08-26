package com.android.systemui.topwindoweffects.data.repository;

import android.os.Handler;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class SqueezeEffectRepositoryImpl implements SqueezeEffectRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler bgHandler;
    public final GlobalSettings globalSettings;
    public final Flow isSqueezeEffectEnabled;

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

    public SqueezeEffectRepositoryImpl(Handler handler, CoroutineContext coroutineContext, GlobalSettings globalSettings) {
        this.bgHandler = handler;
        this.globalSettings = globalSettings;
        this.isSqueezeEffectEnabled = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1(this, null)), coroutineContext);
    }
}
