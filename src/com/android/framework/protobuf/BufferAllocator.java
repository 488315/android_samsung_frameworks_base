package com.android.framework.protobuf;

import java.nio.ByteBuffer;

@CheckReturnValue
/* loaded from: classes5.dex */
abstract class BufferAllocator {
    private static final BufferAllocator UNPOOLED = new BufferAllocator() { // from class: com.android.framework.protobuf.BufferAllocator.1
        @Override // com.android.framework.protobuf.BufferAllocator
        public AllocatedBuffer allocateHeapBuffer(int i) {
            return AllocatedBuffer.wrap(new byte[i]);
        }

        @Override // com.android.framework.protobuf.BufferAllocator
        public AllocatedBuffer allocateDirectBuffer(int i) {
            return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(i));
        }
    };

    public abstract AllocatedBuffer allocateDirectBuffer(int i);

    public abstract AllocatedBuffer allocateHeapBuffer(int i);

    BufferAllocator() {
    }

    public static BufferAllocator unpooled() {
        return UNPOOLED;
    }
}
