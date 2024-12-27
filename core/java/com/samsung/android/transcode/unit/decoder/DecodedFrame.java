package com.samsung.android.transcode.unit.decoder;

import android.media.MediaCodec;

public class DecodedFrame {
    public int bufferIndex;
    public int flags;
    public long presentationTimeUs;
    public int size;

    public DecodedFrame(int bufferIndex, MediaCodec.BufferInfo info) {
        this.bufferIndex = bufferIndex;
        this.size = info.size;
        this.presentationTimeUs = info.presentationTimeUs;
        this.flags = info.flags;
    }
}
