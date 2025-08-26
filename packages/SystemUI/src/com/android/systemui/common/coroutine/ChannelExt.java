package com.android.systemui.common.coroutine;

import android.util.Log;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes.dex */
public final class ChannelExt {
    public static final ChannelExt INSTANCE = new ChannelExt();

    private ChannelExt() {
    }

    public static void trySendWithFailureLogging(SendChannel sendChannel, Object obj, String str, String str2) {
        Object objMo3476trySendJP2dKIU = sendChannel.mo3476trySendJP2dKIU(obj);
        if (objMo3476trySendJP2dKIU instanceof ChannelResult.Failed) {
            Log.e(str, "Failed to send " + str2 + " - downstream canceled or failed.", ChannelResult.m3478exceptionOrNullimpl((ChannelResult.Failed) objMo3476trySendJP2dKIU));
        }
    }

    public static /* synthetic */ void trySendWithFailureLogging$default(ChannelExt channelExt, SendChannel sendChannel, Object obj, String str) {
        channelExt.getClass();
        trySendWithFailureLogging(sendChannel, obj, str, "updated state");
    }
}
