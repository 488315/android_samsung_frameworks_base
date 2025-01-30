package com.samsung.android.sume.core.channel;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class BufferChannelDescriptor implements Serializable {
    int capacity;
    int type;

    public BufferChannelDescriptor() {
        this(0, 0);
    }

    public BufferChannelDescriptor(int capacity) {
        this(0, capacity);
    }

    public BufferChannelDescriptor(int type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public int getType() {
        return this.type;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
