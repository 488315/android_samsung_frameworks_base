package okio;

import java.nio.channels.ReadableByteChannel;

/* loaded from: classes4.dex */
public interface BufferedSource extends Source, ReadableByteChannel {
    Buffer buffer();

    boolean exhausted();

    Buffer getBuffer();

    long indexOfElement(ByteString byteString);

    boolean request(long j);

    int select(Options options);

    void skip(long j);
}
