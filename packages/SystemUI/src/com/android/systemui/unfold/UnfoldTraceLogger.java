package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.unfold.data.repository.FoldStateRepository;
import com.android.systemui.unfold.system.DeviceStateRepository;
import com.android.systemui.util.Utils;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UnfoldTraceLogger implements CoreStartable {
    public final ContextScope bgScope;
    public final DeviceStateRepository deviceStateRepository;
    public final FoldStateRepository foldStateRepository;
    public final boolean isFoldable;

    public UnfoldTraceLogger(Context context, FoldStateRepository foldStateRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext, DeviceStateRepository deviceStateRepository, DeviceStateManager deviceStateManager) {
        this.foldStateRepository = foldStateRepository;
        this.deviceStateRepository = deviceStateRepository;
        this.isFoldable = Utils.isDeviceFoldable(context.getResources(), deviceStateManager);
        this.bgScope = new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.isFoldable) {
            UnfoldTraceLogger$start$1 unfoldTraceLogger$start$1 = new UnfoldTraceLogger$start$1(this, null);
            ContextScope contextScope = this.bgScope;
            CoroutineTracingKt.launchTraced$default(contextScope, null, null, unfoldTraceLogger$start$1, 7);
            CoroutineTracingKt.launchTraced$default(contextScope, null, null, new UnfoldTraceLogger$start$2(this, null), 7);
            CoroutineTracingKt.launchTraced$default(contextScope, null, null, new UnfoldTraceLogger$start$3(this, null), 7);
        }
    }
}
