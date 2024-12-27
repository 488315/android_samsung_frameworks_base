package com.samsung.android.sume.core.channel;

import android.view.Surface;

public interface SurfaceChannel extends BufferChannel {
    void configure(int i, int i2, int i3);

    void configure(Surface surface);

    int getNumberOfFrames();

    Surface getSurface();

    void setNumberOfFrames(int i);

    static SurfaceChannel of(int channelType) {
        return new SurfaceChannelImpl(channelType, new BlockingBufferChannel());
    }

    static SurfaceChannel of(int channelType, BufferChannel bufferChannel) {
        return new SurfaceChannelImpl(channelType, bufferChannel);
    }

    static SurfaceChannel newTransitChannel() {
        return new SurfaceChannelImpl(4, null);
    }

    static SurfaceChannel newReceiveChannel(BufferChannel bufferChannel) {
        return new SurfaceChannelImpl(2, bufferChannel);
    }

    static SurfaceChannel newSendChannel() {
        return new SurfaceChannelImpl(3, null);
    }
}
