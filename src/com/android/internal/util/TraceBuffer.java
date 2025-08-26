package com.android.internal.util;

import android.util.proto.ProtoOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
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
        public /* bridge */ /* synthetic */ void write(Object obj, Queue queue, OutputStream outputStream) throws IOException {
            write((ProtoOutputStream) obj, (Queue<ProtoOutputStream>) queue, outputStream);
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public int getItemSize(ProtoOutputStream protoOutputStream) {
            return protoOutputStream.getRawSize();
        }

        @Override // com.android.internal.util.TraceBuffer.ProtoProvider
        public byte[] getBytes(ProtoOutputStream protoOutputStream) {
            return protoOutputStream.getBytes();
        }

        public void write(ProtoOutputStream protoOutputStream, Queue<ProtoOutputStream> queue, OutputStream outputStream) throws IOException {
            outputStream.write(protoOutputStream.getBytes());
            Iterator<ProtoOutputStream> it = queue.iterator();
            while (it.hasNext()) {
                outputStream.write(it.next().getBytes());
            }
        }
    }

    public TraceBuffer(int i) {
        this(i, new ProtoOutputStreamProvider(), null);
    }

    public TraceBuffer(int i, Consumer<T> consumer) {
        this(i, new ProtoOutputStreamProvider(), consumer);
    }

    public TraceBuffer(int i, ProtoProvider protoProvider, Consumer<T> consumer) {
        this.mBuffer = new ArrayDeque();
        this.mBufferCapacity = i;
        this.mProtoProvider = protoProvider;
        this.mProtoDequeuedCallback = consumer;
        resetBuffer();
    }

    public synchronized int getAvailableSpace() {
        return this.mBufferCapacity - this.mBufferUsedSize;
    }

    public synchronized int size() {
        return this.mBuffer.size();
    }

    public synchronized void setCapacity(int i) {
        this.mBufferCapacity = i;
    }

    public synchronized void add(T t) {
        int itemSize = this.mProtoProvider.getItemSize(t);
        if (itemSize > this.mBufferCapacity) {
            throw new IllegalStateException("Trace object too large for the buffer. Buffer size:" + this.mBufferCapacity + " Object size: " + itemSize);
        }
        discardOldest(itemSize);
        this.mBuffer.add(t);
        this.mBufferUsedSize += itemSize;
    }

    public synchronized boolean contains(final byte[] bArr) {
        return this.mBuffer.stream().anyMatch(new Predicate() { // from class: com.android.internal.util.TraceBuffer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.lambda$contains$0(bArr, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$contains$0(byte[] bArr, Object obj) {
        return Arrays.equals(this.mProtoProvider.getBytes(obj), bArr);
    }

    public synchronized void writeTraceToFile(File file, S s) throws IOException {
        file.delete();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            file.setReadable(true, false);
            this.mProtoProvider.write(s, this.mBuffer, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } finally {
        }
    }

    private void discardOldest(int i) {
        long availableSpace = getAvailableSpace();
        while (availableSpace < i) {
            T tPoll = this.mBuffer.poll();
            if (tPoll == null) {
                throw new IllegalStateException("No element to discard from buffer");
            }
            this.mBufferUsedSize -= this.mProtoProvider.getItemSize(tPoll);
            long availableSpace2 = getAvailableSpace();
            Consumer consumer = this.mProtoDequeuedCallback;
            if (consumer != null) {
                consumer.accept(tPoll);
            }
            availableSpace = availableSpace2;
        }
    }

    public synchronized void resetBuffer() {
        if (this.mProtoDequeuedCallback != null) {
            Iterator<T> it = this.mBuffer.iterator();
            while (it.hasNext()) {
                this.mProtoDequeuedCallback.accept(it.next());
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
