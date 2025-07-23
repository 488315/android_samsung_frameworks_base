package com.samsung.android.transcode.unit.decoder;

import android.media.MediaCodec;

/* loaded from: classes6.dex */
public class DecodedFrame {
    public int bufferIndex;
    public int flags;
    public long presentationTimeUs;
    public int size;

    public DecodedFrame(int i, MediaCodec.BufferInfo bufferInfo) {
        this.bufferIndex = i;
        this.size = bufferInfo.size;
        this.presentationTimeUs = bufferInfo.presentationTimeUs;
        this.flags = bufferInfo.flags;
    }
}
