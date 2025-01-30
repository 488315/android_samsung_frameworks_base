package com.android.systemui.statusbar.commandline;

import android.content.Context;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("   ", (String) it.next(), printWriter);
        }
    }

    public final void onShellCommand(final PrintWriter printWriter, final String[] strArr) {
        if (!this.initialized) {
            this.initialized = true;
            registerCommand("prefs", new Function0() { // from class: com.android.systemui.statusbar.commandline.CommandRegistry$initializeCommands$1
                {
                    super(0);
                }

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
                List list;
                String[] strArr2 = strArr;
                int length = strArr2.length - 1;
                if (length < 0) {
                    length = 0;
                }
                if (!(length >= 0)) {
                    throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Requested element count ", length, " is less than zero.").toString());
                }
                if (length == 0) {
                    list = EmptyList.INSTANCE;
                } else {
                    int length2 = strArr2.length;
                    if (length >= length2) {
                        list = ArraysKt___ArraysKt.toList(strArr2);
                    } else if (length == 1) {
                        list = Collections.singletonList(strArr2[length2 - 1]);
                    } else {
                        ArrayList arrayList = new ArrayList(length);
                        for (int i = length2 - length; i < length2; i++) {
                            arrayList.add(strArr2[i]);
                        }
                        list = arrayList;
                    }
                }
                Command.this.execute(printWriter, list);
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
