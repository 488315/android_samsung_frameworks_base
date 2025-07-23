package com.android.systemui.statusbar.commandline;

import android.content.Context;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CommandRegistry {
    public final Map commandMap = new LinkedHashMap();
    public final Context context;
    public boolean initialized;
    public final Executor mainExecutor;

    public CommandRegistry(Context context, Executor executor) {
        this.context = context;
        this.mainExecutor = executor;
    }

    public final void help(PrintWriter printWriter) {
        printWriter.println("Usage: adb shell cmd statusbar <command>");
        printWriter.println("  known commands:");
        Iterator it = ((LinkedHashMap) this.commandMap).keySet().iterator();
        while (it.hasNext()) {
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "   ", (String) it.next());
        }
    }

    public final void onShellCommand(final PrintWriter printWriter, final String[] strArr) {
        if (!this.initialized) {
            this.initialized = true;
            registerCommand("prefs", new Function0() { // from class: com.android.systemui.statusbar.commandline.CommandRegistry$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new PrefsCommand(CommandRegistry.this.context);
                }
            });
        }
        if (strArr.length == 0) {
            help(printWriter);
            return;
        }
        CommandWrapper commandWrapper = (CommandWrapper) ((LinkedHashMap) this.commandMap).get(strArr[0]);
        if (commandWrapper == null) {
            help(printWriter);
            return;
        }
        final Command command = (Command) commandWrapper.commandFactory.invoke();
        final FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.systemui.statusbar.commandline.CommandRegistry$onShellCommand$task$1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Command.this.execute(printWriter, ArraysKt___ArraysKt.drop(1, strArr));
                return Unit.INSTANCE;
            }
        });
        commandWrapper.executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.commandline.CommandRegistry$onShellCommand$1
            @Override // java.lang.Runnable
            public final void run() {
                futureTask.run();
            }
        });
        futureTask.get();
    }

    public final synchronized void registerCommand(String str, Function0 function0, Executor executor) {
        if (((LinkedHashMap) this.commandMap).get(str) != null) {
            throw new IllegalStateException("A command is already registered for (" + str + ")");
        }
        this.commandMap.put(str, new CommandWrapper(function0, executor));
    }

    public final synchronized void registerCommand(String str, Function0 function0) {
        registerCommand(str, function0, this.mainExecutor);
    }
}
