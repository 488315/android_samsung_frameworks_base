package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.channel.BlockingBufferChannel;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class BufferedConveyorFilter$$ExternalSyntheticLambda1 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return new BlockingBufferChannel();
    }
}
