package com.samsung.android.sume.core.channel;

import android.util.Log;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class BufferSupplyChannel implements BufferChannel, Supplier<MediaBuffer> {
    private static final String TAG = Def.tagOf((Class<?>) BufferSupplyChannel.class);
    private final BufferChannel channel;
    private Supplier<MediaBuffer> supplier;

    public BufferSupplyChannel(BufferChannel channel) {
        this.channel = channel;
    }

    public void configure(Supplier<MediaBuffer> supplier) {
        this.supplier = supplier;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer data) {
        Log.d(TAG, "send: " + data);
        this.channel.send(data);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        return this.channel.receive();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
        this.channel.close();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
        this.channel.cancel();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        return this.channel.isClosedForSend();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        return this.channel.isClosedForReceive();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public MediaBuffer get() {
        return (MediaBuffer)
                Optional.ofNullable(this.supplier)
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.channel.BufferSupplyChannel$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return (MediaBuffer) ((Supplier) obj).get();
                                    }
                                })
                        .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }
}
