package com.android.systemui.statusbar.notification.collection.provider;

import android.os.Build;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider.NotifFilterCommand;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class DebugModeFilterProvider implements Dumpable {
    public final CommandRegistry commandRegistry;
    public List allowedPackages = EmptyList.INSTANCE;
    public final ListenerSet listeners = new ListenerSet();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class NotifFilterCommand implements Command {
        public NotifFilterCommand() {
        }

        public static void invalidCommand(PrintWriter printWriter, String str) {
            printWriter.println("Error: " + str);
            printWriter.println();
            printWriter.println("Usage: adb shell cmd statusbar notif-filter <command>");
            printWriter.println("Available commands:");
            printWriter.println("  reset");
            printWriter.println("     Restore the default system behavior.");
            printWriter.println("  allowed-pkgs <package> ...");
            printWriter.println("     Hide all notification except from packages listed here.");
            printWriter.println("     Providing no packages is treated as a reset.");
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            String str = (String) CollectionsKt___CollectionsKt.firstOrNull(list);
            boolean areEqual = Intrinsics.areEqual(str, UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
            DebugModeFilterProvider debugModeFilterProvider = DebugModeFilterProvider.this;
            if (areEqual) {
                if (list.size() > 1) {
                    invalidCommand(printWriter, "Unexpected arguments for 'reset' command");
                    return;
                }
                debugModeFilterProvider.allowedPackages = EmptyList.INSTANCE;
            } else {
                if (!Intrinsics.areEqual(str, "allowed-pkgs")) {
                    if (str == null) {
                        invalidCommand(printWriter, "Missing command");
                        return;
                    }
                    invalidCommand(printWriter, "Unknown command: " + CollectionsKt___CollectionsKt.firstOrNull(list));
                    return;
                }
                debugModeFilterProvider.allowedPackages = CollectionsKt___CollectionsKt.drop(list, 1);
            }
            Log.d("DebugModeFilterProvider", "Updated allowedPackages: " + debugModeFilterProvider.allowedPackages);
            if (debugModeFilterProvider.allowedPackages.isEmpty()) {
                printWriter.print("Resetting allowedPackages ... ");
            } else {
                printWriter.print("Updating allowedPackages: " + debugModeFilterProvider.allowedPackages + " ... ");
            }
            Iterator<E> it = debugModeFilterProvider.listeners.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
            printWriter.println("DONE");
        }
    }

    static {
        new Companion(null);
    }

    public DebugModeFilterProvider(CommandRegistry commandRegistry, DumpManager dumpManager) {
        this.commandRegistry = commandRegistry;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.println(asIndenting, "initialized", Boolean.valueOf(!this.listeners.isEmpty()));
        List list = this.allowedPackages;
        asIndenting.append("allowedPackages").append((CharSequence) ": ").println(list.size());
        asIndenting.increaseIndent();
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                asIndenting.println(it.next());
            }
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    public final void registerInvalidationListener(Runnable runnable) {
        Assert.isMainThread();
        if (Build.isDebuggable()) {
            ListenerSet listenerSet = this.listeners;
            boolean isEmpty = listenerSet.isEmpty();
            listenerSet.addIfAbsent(runnable);
            if (isEmpty) {
                this.commandRegistry.registerCommand("notif-filter", new Function0() { // from class: com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider$registerInvalidationListener$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return DebugModeFilterProvider.this.new NotifFilterCommand();
                    }
                });
                Log.d("DebugModeFilterProvider", "Registered notif-filter command");
            }
        }
    }
}
