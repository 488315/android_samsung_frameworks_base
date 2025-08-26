package com.android.systemui.statusbar.notification.collection.provider;

import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class DebugModeFilterProvider implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandRegistry commandRegistry;
    public List allowedPackages = EmptyList.INSTANCE;
    public final ListenerSet listeners = new ListenerSet();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "  reset", "     Restore the default system behavior.", "  allowed-pkgs <package> ...", "     Hide all notification except from packages listed here.");
            printWriter.println("     Providing no packages is treated as a reset.");
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            String str = (String) CollectionsKt___CollectionsKt.firstOrNull(list);
            boolean zAreEqual = Intrinsics.areEqual(str, UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
            DebugModeFilterProvider debugModeFilterProvider = DebugModeFilterProvider.this;
            if (zAreEqual) {
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
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "initialized", Boolean.valueOf(!this.listeners.isEmpty()));
        List list = this.allowedPackages;
        indentingPrintWriterAsIndenting.append("allowedPackages").append((CharSequence) ": ").println(list.size());
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
        } finally {
            indentingPrintWriterAsIndenting.decreaseIndent();
        }
    }

    public final boolean shouldFilterOut(NotificationEntry notificationEntry) {
        if (this.allowedPackages.isEmpty()) {
            return false;
        }
        return !this.allowedPackages.contains(notificationEntry.mSbn.getPackageName());
    }
}
