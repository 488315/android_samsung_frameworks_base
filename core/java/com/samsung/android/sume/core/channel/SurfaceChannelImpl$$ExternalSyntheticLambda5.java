package com.samsung.android.sume.core.channel;

import com.samsung.android.sume.core.buffer.MediaBuffer;

import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes6.dex */
public final /* synthetic */ class SurfaceChannelImpl$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ BufferChannel f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.send((MediaBuffer) obj);
    }
}
