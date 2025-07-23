package com.android.systemui.media.muteawait;

import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli.MuteAwaitCommand;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.PrintWriter;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaMuteAwaitConnectionCli implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioManager audioManager;
    public final CommandRegistry commandRegistry;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MuteAwaitCommand implements Command {
        public MuteAwaitCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            int parseInt = Integer.parseInt((String) list.get(0));
            String str = (String) list.get(1);
            EmptyList emptyList = EmptyList.INSTANCE;
            AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(2, parseInt, "address", str, emptyList, emptyList);
            String str2 = (String) list.get(2);
            boolean areEqual = Intrinsics.areEqual(str2, NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            MediaMuteAwaitConnectionCli mediaMuteAwaitConnectionCli = MediaMuteAwaitConnectionCli.this;
            if (areEqual) {
                mediaMuteAwaitConnectionCli.audioManager.muteAwaitConnection(new int[]{1}, audioDeviceAttributes, 5L, MediaMuteAwaitConnectionCliKt.TIMEOUT_UNITS);
            } else if (Intrinsics.areEqual(str2, "cancel")) {
                mediaMuteAwaitConnectionCli.audioManager.cancelMuteAwaitConnection(audioDeviceAttributes);
            } else {
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Must specify `start` or `cancel`; was ", str2);
            }
        }
    }

    public MediaMuteAwaitConnectionCli(CommandRegistry commandRegistry, AudioManager audioManager) {
        this.commandRegistry = commandRegistry;
        this.audioManager = audioManager;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.commandRegistry.registerCommand("media-mute-await", new Function0() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = MediaMuteAwaitConnectionCli.$r8$clinit;
                return MediaMuteAwaitConnectionCli.this.new MuteAwaitCommand();
            }
        });
    }
}
