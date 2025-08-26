package com.android.systemui.keyevent.data.repository;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class KeyEventRepositoryImpl implements KeyEventRepository {
    public final CommandQueue commandQueue;
    public final Flow isPowerButtonDown = FlowConflatedKt.conflatedCallbackFlow(new KeyEventRepositoryImpl$isPowerButtonDown$1(this, null));
    public final Flow isPowerButtonLongPressed = FlowConflatedKt.conflatedCallbackFlow(new KeyEventRepositoryImpl$isPowerButtonLongPressed$1(this, null));

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

    public KeyEventRepositoryImpl(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }
}
