package com.android.systemui.statusbar.commandline;

import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CommandWrapper {
    public final Function0 commandFactory;
    public final Executor executor;

    public CommandWrapper(Function0 function0, Executor executor) {
        this.commandFactory = function0;
        this.executor = executor;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommandWrapper)) {
            return false;
        }
        CommandWrapper commandWrapper = (CommandWrapper) obj;
        return Intrinsics.areEqual(this.commandFactory, commandWrapper.commandFactory) && Intrinsics.areEqual(this.executor, commandWrapper.executor);
    }

    public final int hashCode() {
        return this.executor.hashCode() + (this.commandFactory.hashCode() * 31);
    }

    public final String toString() {
        return "CommandWrapper(commandFactory=" + this.commandFactory + ", executor=" + this.executor + ")";
    }
}
