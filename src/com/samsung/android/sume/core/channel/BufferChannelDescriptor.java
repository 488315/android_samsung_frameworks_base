package com.samsung.android.sume.core.channel;

import java.io.Serializable;

/* loaded from: classes6.dex */
public class BufferChannelDescriptor implements Serializable {
    int capacity;
    int type;

    public BufferChannelDescriptor() {
        this(0, 0);
    }

    public BufferChannelDescriptor(int i) {
        this(0, i);
    }

    public BufferChannelDescriptor(int i, int i2) {
        this.type = i;
        this.capacity = i2;
    }

    public int getType() {
        return this.type;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
