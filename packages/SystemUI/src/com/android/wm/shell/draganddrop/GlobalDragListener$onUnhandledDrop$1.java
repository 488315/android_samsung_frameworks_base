package com.android.wm.shell.draganddrop;

import android.os.Trace;
import android.window.IUnhandledDragCallback;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalDragListener$onUnhandledDrop$1 implements Consumer {
    public final /* synthetic */ int $traceCookie;
    public final /* synthetic */ IUnhandledDragCallback $wmCallback;

    public GlobalDragListener$onUnhandledDrop$1(IUnhandledDragCallback iUnhandledDragCallback, int i) {
        this.$wmCallback = iUnhandledDragCallback;
        this.$traceCookie = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Boolean bool = (Boolean) obj;
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Notifying onUnhandledDrop complete: %b", new Object[]{bool});
        this.$wmCallback.notifyUnhandledDropComplete(bool.booleanValue());
        Trace.asyncTraceEnd(32L, "GlobalDragListener.onUnhandledDrop", this.$traceCookie);
    }
}
