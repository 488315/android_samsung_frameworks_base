package com.samsung.android.sume.core.channel;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.format.MediaFormat;

/* loaded from: classes6.dex */
public class VoidBufferChannel implements BufferChannel {
    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        return false;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        return false;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer mediaBuffer) {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        return MediaBuffer.mutableOf(MediaFormat.mutableImageOf(new Object[0]));
    }
}
