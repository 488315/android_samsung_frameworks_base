package com.samsung.android.sume.core.channel;

import android.view.Surface;

/* loaded from: classes4.dex */
public interface SurfaceChannel extends BufferChannel {
    void configure(int i, int i2, int i3);

    void configure(Surface surface);

    int getNumberOfFrames();

    Surface getSurface();

    void setNumberOfFrames(int i);

    /* renamed from: of */
    static SurfaceChannel m347of(int channelType) {
        return new SurfaceChannelImpl(channelType, new BlockingBufferChannel());
    }

    /* renamed from: of */
    static SurfaceChannel m348of(int channelType, BufferChannel bufferChannel) {
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
