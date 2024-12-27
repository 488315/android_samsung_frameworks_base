package com.android.systemui.common.coroutine;

import android.util.Log;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ChannelExt {
    public static final ChannelExt INSTANCE = new ChannelExt();

    private ChannelExt() {
    }

    public static void trySendWithFailureLogging(SendChannel sendChannel, Object obj, String str, String str2) {
        Object mo2552trySendJP2dKIU = sendChannel.mo2552trySendJP2dKIU(obj);
        if (mo2552trySendJP2dKIU instanceof ChannelResult.Failed) {
            Log.e(str, "Failed to send " + str2 + " - downstream canceled or failed.", ChannelResult.m2554exceptionOrNullimpl(mo2552trySendJP2dKIU));
        }
    }

    public static /* synthetic */ void trySendWithFailureLogging$default(ChannelExt channelExt, SendChannel sendChannel, Object obj, String str) {
        channelExt.getClass();
        trySendWithFailureLogging(sendChannel, obj, str, "updated state");
    }
}
