package kotlin.io;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
    public static final void writeTextImpl(OutputStream outputStream, String str, Charset charset) {
        if (str.length() < 16384) {
            outputStream.write(str.getBytes(charset));
            return;
        }
        CharsetEncoder newEncoder = charset.newEncoder();
        CodingErrorAction codingErrorAction = CodingErrorAction.REPLACE;
        CharsetEncoder onUnmappableCharacter = newEncoder.onMalformedInput(codingErrorAction).onUnmappableCharacter(codingErrorAction);
        CharBuffer allocate = CharBuffer.allocate(8192);
        onUnmappableCharacter.getClass();
        ByteBuffer allocate2 = ByteBuffer.allocate(8192 * ((int) Math.ceil(onUnmappableCharacter.maxBytesPerChar())));
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            int min = Math.min(8192 - i2, str.length() - i);
            int i3 = i + min;
            str.getChars(i, i3, allocate.array(), i2);
            allocate.limit(min + i2);
            i2 = 1;
            if (!onUnmappableCharacter.encode(allocate, allocate2, i3 == str.length()).isUnderflow()) {
                throw new IllegalStateException("Check failed.");
            }
            outputStream.write(allocate2.array(), 0, allocate2.position());
            if (allocate.position() != allocate.limit()) {
                allocate.put(0, allocate.get());
            } else {
                i2 = 0;
            }
            allocate.clear();
            allocate2.clear();
            i = i3;
        }
    }
}
