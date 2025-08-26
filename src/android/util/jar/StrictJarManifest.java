package android.util.jar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import libcore.io.Streams;

/* loaded from: classes4.dex */
public class StrictJarManifest implements Cloneable {
    static final int LINE_LENGTH_LIMIT = 72;
    private HashMap<String, Chunk> chunks;
    private final HashMap<String, Attributes> entries;
    private final Attributes mainAttributes;
    private int mainEnd;
    private static final byte[] LINE_SEPARATOR = {13, 10};
    private static final byte[] VALUE_SEPARATOR = {58, 32};
    static final Attributes.Name ATTRIBUTE_NAME_NAME = new Attributes.Name("Name");

    static final class Chunk {
        final int end;
        final int start;

        Chunk(int i, int i2) {
            this.start = i;
            this.end = i2;
        }
    }

    public StrictJarManifest() {
        this.entries = new HashMap<>();
        this.mainAttributes = new Attributes();
    }

    public StrictJarManifest(InputStream inputStream) throws IOException {
        this();
        read(Streams.readFully(inputStream));
    }

    public StrictJarManifest(StrictJarManifest strictJarManifest) {
        this.mainAttributes = (Attributes) strictJarManifest.mainAttributes.clone();
        this.entries = (HashMap) ((HashMap) strictJarManifest.getEntries()).clone();
    }

    StrictJarManifest(byte[] bArr, boolean z) throws IOException {
        this();
        if (z) {
            this.chunks = new HashMap<>();
        }
        read(bArr);
    }

    public void clear() {
        this.entries.clear();
        this.mainAttributes.clear();
    }

    public Attributes getAttributes(String str) {
        return getEntries().get(str);
    }

    public Map<String, Attributes> getEntries() {
        return this.entries;
    }

    public Attributes getMainAttributes() {
        return this.mainAttributes;
    }

    public Object clone() {
        return new StrictJarManifest(this);
    }

    public void write(OutputStream outputStream) throws IOException {
        write(this, outputStream);
    }

    public void read(InputStream inputStream) throws IOException {
        read(Streams.readFullyNoClose(inputStream));
    }

    private void read(byte[] bArr) throws IOException {
        if (bArr.length == 0) {
            return;
        }
        StrictJarManifestReader strictJarManifestReader = new StrictJarManifestReader(bArr, this.mainAttributes);
        this.mainEnd = strictJarManifestReader.getEndOfMainSection();
        strictJarManifestReader.readEntries(this.entries, this.chunks);
    }

    public int hashCode() {
        return getEntries().hashCode() ^ this.mainAttributes.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        StrictJarManifest strictJarManifest = (StrictJarManifest) obj;
        if (this.mainAttributes.equals(strictJarManifest.mainAttributes)) {
            return getEntries().equals(strictJarManifest.getEntries());
        }
        return false;
    }

    Chunk getChunk(String str) {
        return this.chunks.get(str);
    }

    void removeChunks() {
        this.chunks = null;
    }

    int getMainAttributesEnd() {
        return this.mainEnd;
    }

    static void write(StrictJarManifest strictJarManifest, OutputStream outputStream) throws IOException {
        CharsetEncoder charsetEncoderNewEncoder = StandardCharsets.UTF_8.newEncoder();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(72);
        Attributes.Name name = Attributes.Name.MANIFEST_VERSION;
        String value = strictJarManifest.mainAttributes.getValue(name);
        if (value == null) {
            name = Attributes.Name.SIGNATURE_VERSION;
            value = strictJarManifest.mainAttributes.getValue(name);
        }
        if (value != null) {
            writeEntry(outputStream, name, value, charsetEncoderNewEncoder, byteBufferAllocate);
            Iterator<Object> it = strictJarManifest.mainAttributes.keySet().iterator();
            while (it.hasNext()) {
                Attributes.Name name2 = (Attributes.Name) it.next();
                if (!name2.equals(name)) {
                    writeEntry(outputStream, name2, strictJarManifest.mainAttributes.getValue(name2), charsetEncoderNewEncoder, byteBufferAllocate);
                }
            }
        }
        outputStream.write(LINE_SEPARATOR);
        for (String str : strictJarManifest.getEntries().keySet()) {
            writeEntry(outputStream, ATTRIBUTE_NAME_NAME, str, charsetEncoderNewEncoder, byteBufferAllocate);
            Attributes attributes = strictJarManifest.entries.get(str);
            Iterator<Object> it2 = attributes.keySet().iterator();
            while (it2.hasNext()) {
                Attributes.Name name3 = (Attributes.Name) it2.next();
                writeEntry(outputStream, name3, attributes.getValue(name3), charsetEncoderNewEncoder, byteBufferAllocate);
            }
            outputStream.write(LINE_SEPARATOR);
        }
    }

    private static void writeEntry(OutputStream outputStream, Attributes.Name name, String str, CharsetEncoder charsetEncoder, ByteBuffer byteBuffer) throws IOException {
        String string = name.toString();
        outputStream.write(string.getBytes(StandardCharsets.US_ASCII));
        outputStream.write(VALUE_SEPARATOR);
        charsetEncoder.reset();
        byteBuffer.clear().limit(70 - string.length());
        CharBuffer charBufferWrap = CharBuffer.wrap(str);
        while (true) {
            CoderResult coderResultEncode = charsetEncoder.encode(charBufferWrap, byteBuffer, true);
            if (CoderResult.UNDERFLOW == coderResultEncode) {
                coderResultEncode = charsetEncoder.flush(byteBuffer);
            }
            outputStream.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position());
            outputStream.write(LINE_SEPARATOR);
            if (CoderResult.UNDERFLOW == coderResultEncode) {
                return;
            }
            outputStream.write(32);
            byteBuffer.clear().limit(71);
        }
    }
}
