package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.descriptor.p044nn.NNDescriptor;
import java.util.function.Consumer;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ModelSelector {

    public static class Item {
        public Consumer<NNDescriptor> descriptorUpdater;
        public String name;
    }

    Item select(MediaBuffer mediaBuffer);
}
