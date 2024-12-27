package com.android.internal.util;

import android.util.proto.ProtoOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class TraceBuffer<P, S extends P, T extends P> {
    private final Queue<T> mBuffer;
    private int mBufferCapacity;
    private int mBufferUsedSize;
    private final Consumer mProtoDequeuedCallback;
    private final ProtoProvider<P, S, T> mProtoProvider;

    public interface ProtoProvider<P, S extends P, T extends P> {
        byte[] getBytes(P p);

        int getItemSize(P p);

        void write(S s, Queue<T> queue, OutputStream outputStream) throws IOException;
    }

    private static class ProtoOutputStreamProvider implements ProtoProvider<ProtoOutputStream, ProtoOutputStream, ProtoOutputStream> {
        private ProtoOutputStreamProvider() {
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public int getItemSize(ProtoOutputStream proto) {
            return proto.getRawSize();
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public byte[] getBytes(ProtoOutputStream proto) {
            return proto.getBytes();
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public void write(ProtoOutputStream encapsulatingProto, Queue<ProtoOutputStream> buffer, OutputStream os) throws IOException {
            os.write(encapsulatingProto.getBytes());
            for (ProtoOutputStream protoOutputStream : buffer) {
                byte[] protoBytes = protoOutputStream.getBytes();
                os.write(protoBytes);
            }
        }
    }

    public TraceBuffer(int bufferCapacity) {
        this(bufferCapacity, new ProtoOutputStreamProvider(), null);
    }

    public TraceBuffer(int bufferCapacity, Consumer<T> protoDequeuedCallback) {
        this(bufferCapacity, new ProtoOutputStreamProvider(), protoDequeuedCallback);
    }

    public TraceBuffer(int bufferCapacity, ProtoProvider protoProvider, Consumer<T> protoDequeuedCallback) {
        this.mBuffer = new ArrayDeque();
        this.mBufferCapacity = bufferCapacity;
        this.mProtoProvider = protoProvider;
        this.mProtoDequeuedCallback = protoDequeuedCallback;
        resetBuffer();
    }

    public synchronized int getAvailableSpace() {
        return this.mBufferCapacity - this.mBufferUsedSize;
    }

    public synchronized int size() {
        return this.mBuffer.size();
    }

    public synchronized void setCapacity(int capacity) {
        this.mBufferCapacity = capacity;
    }

    public synchronized void add(T proto) {
        int protoLength = this.mProtoProvider.getItemSize(proto);
        if (protoLength > this.mBufferCapacity) {
            throw new IllegalStateException("Trace object too large for the buffer. Buffer size:" + this.mBufferCapacity + " Object size: " + protoLength);
        }
        discardOldest(protoLength);
        this.mBuffer.add(proto);
        this.mBufferUsedSize += protoLength;
    }

    public synchronized boolean contains(final byte[] other) {
        return this.mBuffer.stream().anyMatch(new Predicate() { // from class: com.android.internal.util.TraceBuffer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$contains$0;
                lambda$contains$0 = TraceBuffer.this.lambda$contains$0(other, obj);
                return lambda$contains$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$contains$0(byte[] other, Object p) {
        return Arrays.equals(this.mProtoProvider.getBytes(p), other);
    }

    public synchronized void writeTraceToFile(File traceFile, S encapsulatingProto) throws IOException {
        traceFile.delete();
        OutputStream os = new FileOutputStream(traceFile);
        try {
            traceFile.setReadable(true, false);
            this.mProtoProvider.write(encapsulatingProto, this.mBuffer, os);
            os.flush();
            os.close();
        } finally {
        }
    }

    private void discardOldest(int protoLength) {
        long availableSpace = getAvailableSpace();
        while (availableSpace < protoLength) {
            T poll = this.mBuffer.poll();
            if (poll == null) {
                throw new IllegalStateException("No element to discard from buffer");
            }
            this.mBufferUsedSize -= this.mProtoProvider.getItemSize(poll);
            availableSpace = getAvailableSpace();
            if (this.mProtoDequeuedCallback != null) {
                this.mProtoDequeuedCallback.accept(poll);
            }
        }
    }

    public synchronized void resetBuffer() {
        if (this.mProtoDequeuedCallback != null) {
            for (T item : this.mBuffer) {
                this.mProtoDequeuedCallback.accept(item);
            }
        }
        this.mBuffer.clear();
        this.mBufferUsedSize = 0;
    }

    public synchronized int getBufferSize() {
        return this.mBufferUsedSize;
    }

    public synchronized String getStatus() {
        return "Buffer size: " + this.mBufferCapacity + " bytes\nBuffer usage: " + this.mBufferUsedSize + " bytes\nElements in the buffer: " + this.mBuffer.size();
    }
}
