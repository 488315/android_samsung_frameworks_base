package com.android.systemui.media.muteawait;

import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.presence.ServiceTuple;
import java.io.PrintWriter;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaMuteAwaitConnectionCli {
    public final Context context;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            AudioManager audioManager = (AudioManager) MediaMuteAwaitConnectionCli.this.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
            if (Intrinsics.areEqual(str2, NetworkAnalyticsConstants.DataPoints.OPEN_TIME)) {
                audioManager.muteAwaitConnection(new int[]{1}, audioDeviceAttributes, 5L, MediaMuteAwaitConnectionCliKt.TIMEOUT_UNITS);
            } else if (Intrinsics.areEqual(str2, "cancel")) {
                audioManager.cancelMuteAwaitConnection(audioDeviceAttributes);
            } else {
                FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("Must specify `start` or `cancel`; was ", str2, printWriter);
            }
        }
    }

    public MediaMuteAwaitConnectionCli(CommandRegistry commandRegistry, Context context) {
        this.context = context;
        commandRegistry.registerCommand("media-mute-await", new Function0() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MediaMuteAwaitConnectionCli.this.new MuteAwaitCommand();
            }
        });
    }
}
