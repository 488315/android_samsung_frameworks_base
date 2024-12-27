package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

public final class UserAutoAddRepository {
    public static final Companion Companion = null;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final SharedFlowImpl changeEvents;
    public final QSPipelineLogger logger;
    public final SecureSettings secureSettings;
    public final int userId;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public interface Factory {
    }

    static {
        new Companion(null);
    }

    public UserAutoAddRepository(int i, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.secureSettings = secureSettings;
        SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    }
}
