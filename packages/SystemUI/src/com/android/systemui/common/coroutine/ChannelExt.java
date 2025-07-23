package com.android.systemui.common.coroutine;

import android.util.Log;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChannelExt {
    public static final ChannelExt INSTANCE = new ChannelExt();

    private ChannelExt() {
    }

    public static void trySendWithFailureLogging(SendChannel sendChannel, Object obj, String str, String str2) {
        Object mo3456trySendJP2dKIU = sendChannel.mo3456trySendJP2dKIU(obj);
        if (mo3456trySendJP2dKIU instanceof ChannelResult.Failed) {
            Log.e(str, "Failed to send " + str2 + " - downstream canceled or failed.", ChannelResult.m3458exceptionOrNullimpl((ChannelResult.Failed) mo3456trySendJP2dKIU));
        }
    }

    public static /* synthetic */ void trySendWithFailureLogging$default(ChannelExt channelExt, SendChannel sendChannel, Object obj, String str) {
        channelExt.getClass();
        trySendWithFailureLogging(sendChannel, obj, str, "updated state");
    }
}
