package com.android.systemui.keyevent.data.repository;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyEventRepositoryImpl implements KeyEventRepository {
    public final CommandQueue commandQueue;
    public final Flow isPowerButtonDown = FlowConflatedKt.conflatedCallbackFlow(new KeyEventRepositoryImpl$isPowerButtonDown$1(this, null));
    public final Flow isPowerButtonLongPressed = FlowConflatedKt.conflatedCallbackFlow(new KeyEventRepositoryImpl$isPowerButtonLongPressed$1(this, null));

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

    public KeyEventRepositoryImpl(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }
}
