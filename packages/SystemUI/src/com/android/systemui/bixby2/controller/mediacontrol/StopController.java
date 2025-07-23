package com.android.systemui.bixby2.controller.mediacontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.UserHandle;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StopController extends MediaCommandType {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);
    private static final String[] NOISY_INTENT_DENIED_LIST = {"com.netease.cloudmusic", "com.kugou.android"};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final boolean isAllowedAppReceiveNoisyIntent(String str) {
        return !ArraysKt___ArraysKt.contains(NOISY_INTENT_DENIED_LIST, str);
    }

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        MediaCommandType.Companion companion = MediaCommandType.Companion;
        if (!companion.isMediaControlActive(companion.getMediaInfo().isMediaActive)) {
            return new CommandActionResponse(2, ActionResults.RESULT_NO_MEDIA_EXISTS);
        }
        companion.getMediaController().getTransportControls().pause();
        if (isAllowedAppReceiveNoisyIntent(companion.getMediaController().getPackageName())) {
            stopMedia();
        }
        return new CommandActionResponse(1, "success");
    }

    public final void stopMedia() {
        Intent intent = new Intent("android.media.AUDIO_BECOMING_NOISY_SEC");
        intent.addFlags(67108864);
        intent.addFlags(268435456);
        Intent intent2 = new Intent("android.media.AUDIO_BECOMING_NOISY");
        intent2.addFlags(67108864);
        intent2.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MediaCommandType.Companion companion = MediaCommandType.Companion;
            Context context = companion.getContext();
            UserHandle userHandle = UserHandle.ALL;
            context.sendBroadcastAsUser(intent, userHandle);
            companion.getContext().sendBroadcastAsUser(intent2, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
