package com.android.systemui.media.taptotransfer;

import android.app.StatusBarManager;
import android.content.Context;
import android.media.MediaRoute2Info;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper.ReceiverCommand;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper.SenderCommand;
import com.android.systemui.media.taptotransfer.receiver.ChipStateReceiver;
import com.android.systemui.media.taptotransfer.sender.ChipStateSender;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaTttCommandLineHelper implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandRegistry commandRegistry;
    public final Context context;
    public final Executor mainExecutor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReceiverCommand implements Command {
        public ReceiverCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            if (list.isEmpty()) {
                printWriter.println("Usage: adb shell cmd statusbar media-ttt-chip-receiver <chipState> useAppIcon=[true|false] <id>");
                return;
            }
            String str = (String) list.get(0);
            try {
                ChipStateReceiver.Companion.getClass();
                int stateInt = ChipStateReceiver.valueOf(str).getStateInt();
                StatusBarManager statusBarManager = (StatusBarManager) MediaTttCommandLineHelper.this.context.getSystemService("statusbar");
                MediaRoute2Info.Builder addFeature = new MediaRoute2Info.Builder(list.size() >= 3 ? (String) list.get(2) : "id", "Test Name").addFeature("feature");
                if (list.size() < 2 || !Intrinsics.areEqual(list.get(1), "useAppIcon=false")) {
                    addFeature.setClientPackageName("com.android.systemui");
                }
                statusBarManager.updateMediaTapToTransferReceiverDisplay(stateInt, addFeature.build(), null, null);
            } catch (IllegalArgumentException unused) {
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command name ", str);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SenderCommand implements Command {
        public SenderCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            Executor executor;
            Runnable runnable;
            if (list.size() < 2) {
                printWriter.println("Usage: adb shell cmd statusbar media-ttt-chip-sender <deviceName> <chipState> useAppIcon=[true|false] id=<id> showUndo=[true|false]");
                printWriter.println("Note: useAppIcon, id, and showUndo are optional additional commands.");
                return;
            }
            SenderArgs senderArgs = new SenderArgs((String) list.get(0), (String) list.get(1), null, false, false, 28, null);
            String str = senderArgs.commandName;
            if (list.size() != 2) {
                for (String str2 : list.subList(2, list.size())) {
                    if (Intrinsics.areEqual(str2, "useAppIcon=false")) {
                        senderArgs.useAppIcon = false;
                    } else if (Intrinsics.areEqual(str2, "showUndo=false")) {
                        senderArgs.showUndo = false;
                    } else {
                        str2.getClass();
                        if (Intrinsics.areEqual(str2.substring(0, 3), "id=")) {
                            senderArgs.id = str2.substring(3);
                        }
                    }
                }
            }
            try {
                ChipStateSender.Companion.getClass();
                int stateInt = ChipStateSender.valueOf(str).getStateInt();
                final Integer valueOf = Integer.valueOf(stateInt);
                MediaTttCommandLineHelper mediaTttCommandLineHelper = MediaTttCommandLineHelper.this;
                StatusBarManager statusBarManager = (StatusBarManager) mediaTttCommandLineHelper.context.getSystemService("statusbar");
                MediaRoute2Info.Builder addFeature = new MediaRoute2Info.Builder(senderArgs.id, senderArgs.deviceName).addFeature("feature");
                if (senderArgs.useAppIcon) {
                    addFeature.setClientPackageName("com.android.systemui");
                }
                if ((stateInt == 4 || stateInt == 5) && senderArgs.showUndo) {
                    executor = mediaTttCommandLineHelper.mainExecutor;
                    runnable = new Runnable() { // from class: com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper$SenderCommand$execute$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.i("MediaTransferCli", "Undo triggered for " + valueOf);
                        }
                    };
                } else {
                    executor = null;
                    runnable = null;
                }
                statusBarManager.updateMediaTapToTransferSenderDisplay(stateInt, addFeature.build(), executor, runnable);
            } catch (IllegalArgumentException unused) {
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command name ", str);
            }
        }
    }

    public MediaTttCommandLineHelper(CommandRegistry commandRegistry, Context context, Executor executor) {
        this.commandRegistry = commandRegistry;
        this.context = context;
        this.mainExecutor = executor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final int i = 0;
        Function0 function0 = new Function0(this) { // from class: com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaTttCommandLineHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaTttCommandLineHelper mediaTttCommandLineHelper = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = MediaTttCommandLineHelper.$r8$clinit;
                        return mediaTttCommandLineHelper.new SenderCommand();
                    default:
                        int i3 = MediaTttCommandLineHelper.$r8$clinit;
                        return mediaTttCommandLineHelper.new ReceiverCommand();
                }
            }
        };
        CommandRegistry commandRegistry = this.commandRegistry;
        commandRegistry.registerCommand("media-ttt-chip-sender", function0);
        final int i2 = 1;
        commandRegistry.registerCommand("media-ttt-chip-receiver", new Function0(this) { // from class: com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaTttCommandLineHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaTttCommandLineHelper mediaTttCommandLineHelper = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = MediaTttCommandLineHelper.$r8$clinit;
                        return mediaTttCommandLineHelper.new SenderCommand();
                    default:
                        int i3 = MediaTttCommandLineHelper.$r8$clinit;
                        return mediaTttCommandLineHelper.new ReceiverCommand();
                }
            }
        });
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SenderArgs {
        public final String commandName;
        public final String deviceName;
        public String id;
        public boolean showUndo;
        public boolean useAppIcon;

        public SenderArgs(String str, String str2, String str3, boolean z, boolean z2) {
            this.deviceName = str;
            this.commandName = str2;
            this.id = str3;
            this.useAppIcon = z;
            this.showUndo = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SenderArgs)) {
                return false;
            }
            SenderArgs senderArgs = (SenderArgs) obj;
            return Intrinsics.areEqual(this.deviceName, senderArgs.deviceName) && Intrinsics.areEqual(this.commandName, senderArgs.commandName) && Intrinsics.areEqual(this.id, senderArgs.id) && this.useAppIcon == senderArgs.useAppIcon && this.showUndo == senderArgs.showUndo;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.showUndo) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceName.hashCode() * 31, 31, this.commandName), 31, this.id), 31, this.useAppIcon);
        }

        public final String toString() {
            String str = this.id;
            boolean z = this.useAppIcon;
            boolean z2 = this.showUndo;
            StringBuilder sb = new StringBuilder("SenderArgs(deviceName=");
            sb.append(this.deviceName);
            sb.append(", commandName=");
            MoveResult$$ExternalSyntheticOutline0.m(sb, this.commandName, ", id=", str, ", useAppIcon=");
            sb.append(z);
            sb.append(", showUndo=");
            sb.append(z2);
            sb.append(")");
            return sb.toString();
        }

        public /* synthetic */ SenderArgs(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, (i & 4) != 0 ? "id" : str3, (i & 8) != 0 ? true : z, (i & 16) != 0 ? true : z2);
        }
    }
}
