package com.samsung.android.transcode.unit.decoder;

public interface DecoderReleaseListener {
    void notifyFrameDecoded(DecodedFrame decodedFrame);
}
