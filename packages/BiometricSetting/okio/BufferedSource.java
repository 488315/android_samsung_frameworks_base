package okio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/* loaded from: classes.dex */
public interface BufferedSource extends Source, ReadableByteChannel {
    @Deprecated
    Buffer buffer();

    long indexOfElement(ByteString byteString) throws IOException;

    InputStream inputStream();

    BufferedSource peek();

    byte readByte() throws IOException;

    boolean request(long j) throws IOException;

    int select(Options options) throws IOException;
}
