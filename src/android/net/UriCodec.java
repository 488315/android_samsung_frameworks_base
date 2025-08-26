package android.net;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

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

    /* JADX WARN: Code restructure failed: missing block: B:32:0x008c, code lost:
    
        r0.put(r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void appendDecoded(StringBuilder sb, String str, boolean z, Charset charset, boolean z2) {
        CharsetDecoder charsetDecoderOnUnmappableCharacter = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith("�").onUnmappableCharacter(CodingErrorAction.REPORT);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(str.length());
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            i++;
            if (cCharAt == '%') {
                int i2 = 0;
                byte b = 0;
                while (true) {
                    if (i2 >= 2) {
                        break;
                    }
                    try {
                        char nextCharacter = getNextCharacter(str, i, str.length(), null);
                        int i3 = i + 1;
                        int iHexCharToValue = hexCharToValue(nextCharacter);
                        if (iHexCharToValue >= 0) {
                            b = (byte) ((b * 16) + iHexCharToValue);
                            i2++;
                            i = i3;
                        } else {
                            if (z2) {
                                throw new IllegalArgumentException(unexpectedCharacterException(str, null, nextCharacter, i));
                            }
                            flushDecodingByteAccumulator(sb, charsetDecoderOnUnmappableCharacter, byteBufferAllocate, z2);
                            sb.append(INVALID_INPUT_CHARACTER);
                            i = i3;
                        }
                    } catch (URISyntaxException e) {
                        if (z2) {
                            throw new IllegalArgumentException(e);
                        }
                        flushDecodingByteAccumulator(sb, charsetDecoderOnUnmappableCharacter, byteBufferAllocate, z2);
                        sb.append(INVALID_INPUT_CHARACTER);
                        return;
                    }
                }
            } else {
                if (cCharAt == '+') {
                    flushDecodingByteAccumulator(sb, charsetDecoderOnUnmappableCharacter, byteBufferAllocate, z2);
                    sb.append(z ? ' ' : '+');
                } else {
                    flushDecodingByteAccumulator(sb, charsetDecoderOnUnmappableCharacter, byteBufferAllocate, z2);
                    sb.append(cCharAt);
                }
            }
        }
        flushDecodingByteAccumulator(sb, charsetDecoderOnUnmappableCharacter, byteBufferAllocate, z2);
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
