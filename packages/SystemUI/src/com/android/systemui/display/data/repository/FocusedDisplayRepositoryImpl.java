package com.android.systemui.display.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.wm.shell.shared.ShellTransitions;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FocusedDisplayRepositoryImpl implements FocusedDisplayRepository {
    public final Executor backgroundExecutor;
    public final CoroutineScope backgroundScope;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 focusedTask;

    public FocusedDisplayRepositoryImpl(CoroutineScope coroutineScope, Executor executor, ShellTransitions shellTransitions, LogBuffer logBuffer) {
        this.backgroundScope = coroutineScope;
        this.backgroundExecutor = executor;
        this.focusedTask = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(new FocusedDisplayRepositoryImpl$focusedTask$1(shellTransitions, this, null)), new FocusedDisplayRepositoryImpl$focusedTask$2(logBuffer, null));
    }
}
