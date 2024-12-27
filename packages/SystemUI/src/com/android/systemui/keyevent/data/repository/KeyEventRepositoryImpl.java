package com.android.systemui.keyevent.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

public final class KeyEventRepositoryImpl implements KeyEventRepository {
    public final CommandQueue commandQueue;
    public final Flow isPowerButtonDown;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyEventRepositoryImpl(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyEventRepositoryImpl$isPowerButtonDown$1 keyEventRepositoryImpl$isPowerButtonDown$1 = new KeyEventRepositoryImpl$isPowerButtonDown$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isPowerButtonDown = FlowConflatedKt.conflatedCallbackFlow(keyEventRepositoryImpl$isPowerButtonDown$1);
    }
}
