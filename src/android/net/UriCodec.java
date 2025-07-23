package android.net;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/* loaded from: classes3.dex */
public final class UriCodec {
    private static final char INVALID_INPUT_CHARACTER = 65533;

    private static int hexCharToValue(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' <= c && c <= 'f') {
            return c - 'W';
        }
        if ('A' > c || c > 'F') {
            return -1;
        }
        return c - '7';
    }

    private UriCodec() {
    }

    private static URISyntaxException unexpectedCharacterException(String str, String str2, char c, int i) {
        String str3;
        if (str2 == null) {
            str3 = "";
        } else {
            str3 = " in [" + str2 + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return new URISyntaxException(str, "Unexpected character" + str3 + ": " + c, i);
    }

    private static char getNextCharacter(String str, int i, int i2, String str2) throws URISyntaxException {
        String str3;
        if (i >= i2) {
            if (str2 == null) {
                str3 = "";
            } else {
                str3 = " in [" + str2 + NavigationBarInflaterView.SIZE_MOD_END;
            }
            throw new URISyntaxException(str, "Unexpected end of string" + str3, i);
        }
        return str.charAt(i);
    }

    public static String decode(String str, boolean z, Charset charset, boolean z2) {
        StringBuilder sb = new StringBuilder(str.length());
        appendDecoded(sb, str, z, charset, z2);
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x008c, code lost:
    
        r0.put(r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void appendDecoded(java.lang.StringBuilder r10, java.lang.String r11, boolean r12, java.nio.charset.Charset r13, boolean r14) {
        /*
            java.nio.charset.CharsetDecoder r13 = r13.newDecoder()
            java.nio.charset.CodingErrorAction r0 = java.nio.charset.CodingErrorAction.REPLACE
            java.nio.charset.CharsetDecoder r13 = r13.onMalformedInput(r0)
            java.lang.String r0 = "�"
            java.nio.charset.CharsetDecoder r13 = r13.replaceWith(r0)
            java.nio.charset.CodingErrorAction r0 = java.nio.charset.CodingErrorAction.REPORT
            java.nio.charset.CharsetDecoder r13 = r13.onUnmappableCharacter(r0)
            int r0 = r11.length()
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.allocate(r0)
            r1 = 0
            r2 = r1
        L21:
            int r3 = r11.length()
            if (r2 >= r3) goto L90
            char r3 = r11.charAt(r2)
            int r2 = r2 + 1
            r4 = 37
            if (r3 == r4) goto L47
            r4 = 43
            if (r3 == r4) goto L3c
            flushDecodingByteAccumulator(r10, r13, r0, r14)
            r10.append(r3)
            goto L21
        L3c:
            flushDecodingByteAccumulator(r10, r13, r0, r14)
            if (r12 == 0) goto L43
            r4 = 32
        L43:
            r10.append(r4)
            goto L21
        L47:
            r3 = r1
            r4 = r3
        L49:
            r5 = 2
            if (r3 >= r5) goto L8c
            r5 = 65533(0xfffd, float:9.1831E-41)
            int r6 = r11.length()     // Catch: java.net.URISyntaxException -> L7c
            r7 = 0
            char r6 = getNextCharacter(r11, r2, r6, r7)     // Catch: java.net.URISyntaxException -> L7c
            int r8 = r2 + 1
            int r9 = hexCharToValue(r6)
            if (r9 >= 0) goto L74
            if (r14 != 0) goto L6a
            flushDecodingByteAccumulator(r10, r13, r0, r14)
            r10.append(r5)
            r2 = r8
            goto L8c
        L6a:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.net.URISyntaxException r11 = unexpectedCharacterException(r11, r7, r6, r2)
            r10.<init>(r11)
            throw r10
        L74:
            int r4 = r4 * 16
            int r4 = r4 + r9
            byte r4 = (byte) r4
            int r3 = r3 + 1
            r2 = r8
            goto L49
        L7c:
            r11 = move-exception
            if (r14 != 0) goto L86
            flushDecodingByteAccumulator(r10, r13, r0, r14)
            r10.append(r5)
            return
        L86:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            r10.<init>(r11)
            throw r10
        L8c:
            r0.put(r4)
            goto L21
        L90:
            flushDecodingByteAccumulator(r10, r13, r0, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.UriCodec.appendDecoded(java.lang.StringBuilder, java.lang.String, boolean, java.nio.charset.Charset, boolean):void");
    }

    private static void flushDecodingByteAccumulator(StringBuilder sb, CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.position() == 0) {
            return;
        }
        byteBuffer.flip();
        try {
            sb.append((CharSequence) charsetDecoder.decode(byteBuffer));
        } catch (CharacterCodingException e) {
            if (z) {
                throw new IllegalArgumentException(e);
            }
            sb.append(INVALID_INPUT_CHARACTER);
        } finally {
            byteBuffer.flip();
            byteBuffer.limit(byteBuffer.capacity());
        }
    }
}
