package okio;

import java.nio.channels.ReadableByteChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface BufferedSource extends Source, ReadableByteChannel {
    Buffer buffer();

    Buffer getBuffer();

    long indexOfElement(ByteString byteString);

    boolean request(long j);

    int select(Options options);
}
