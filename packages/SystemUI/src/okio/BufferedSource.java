package okio;

import java.nio.channels.ReadableByteChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
