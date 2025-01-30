package com.android.systemui.common.coroutine;

import android.util.Log;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ChannelExt {
    public static final ChannelExt INSTANCE = new ChannelExt();

    private ChannelExt() {
    }

    public static void trySendWithFailureLogging(SendChannel sendChannel, Object obj, String str, String str2) {
        Object mo2872trySendJP2dKIU = sendChannel.mo2872trySendJP2dKIU(obj);
        if (mo2872trySendJP2dKIU instanceof ChannelResult.Failed) {
            ChannelResult.Companion companion = ChannelResult.Companion;
            ChannelResult.Closed closed = mo2872trySendJP2dKIU instanceof ChannelResult.Closed ? (ChannelResult.Closed) mo2872trySendJP2dKIU : null;
            Log.e(str, "Failed to send " + str2 + " - downstream canceled or failed.", closed != null ? closed.cause : null);
        }
    }

    public static /* synthetic */ void trySendWithFailureLogging$default(ChannelExt channelExt, SendChannel sendChannel, Object obj, String str) {
        channelExt.getClass();
        trySendWithFailureLogging(sendChannel, obj, str, "updated state");
    }
}
