package com.airbnb.lottie.parser.moshi;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import okio.Buffer;
import okio.ByteString;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class JsonReader implements Closeable {
    public static final String[] REPLACEMENT_CHARS = new String[128];
    public int stackSize;
    public int[] scopes = new int[32];
    public String[] pathNames = new String[32];
    public int[] pathIndices = new int[32];

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Options {
        public final okio.Options doubleQuoteSuffix;
        public final String[] strings;

        private Options(String[] strArr, okio.Options options) {
            this.strings = strArr;
            this.doubleQuoteSuffix = options;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x003a A[Catch: IOException -> 0x006d, TryCatch #0 {IOException -> 0x006d, blocks: (B:2:0x0000, B:3:0x000a, B:5:0x000d, B:7:0x001e, B:9:0x0026, B:13:0x0046, B:15:0x003a, B:16:0x003d, B:27:0x004b, B:29:0x004e, B:32:0x005d), top: B:1:0x0000 }] */
        /* renamed from: of */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static Options m50of(String... strArr) {
            String str;
            try {
                ByteString[] byteStringArr = new ByteString[strArr.length];
                Buffer buffer = new Buffer();
                for (int i = 0; i < strArr.length; i++) {
                    String str2 = strArr[i];
                    String[] strArr2 = JsonReader.REPLACEMENT_CHARS;
                    buffer.writeByte(34);
                    int length = str2.length();
                    int i2 = 0;
                    for (int i3 = 0; i3 < length; i3++) {
                        char charAt = str2.charAt(i3);
                        if (charAt < 128) {
                            str = strArr2[charAt];
                            if (str == null) {
                            }
                            if (i2 < i3) {
                                buffer.writeUtf8(i2, i3, str2);
                            }
                            buffer.writeUtf8(0, str.length(), str);
                            i2 = i3 + 1;
                        } else {
                            if (charAt == 8232) {
                                str = "\\u2028";
                            } else if (charAt == 8233) {
                                str = "\\u2029";
                            }
                            if (i2 < i3) {
                            }
                            buffer.writeUtf8(0, str.length(), str);
                            i2 = i3 + 1;
                        }
                    }
                    if (i2 < length) {
                        buffer.writeUtf8(i2, length, str2);
                    }
                    buffer.writeByte(34);
                    buffer.readByte();
                    byteStringArr[i] = buffer.readByteString();
                }
                return new Options((String[]) strArr.clone(), okio.Options.m299of(byteStringArr));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Token {
        BEGIN_ARRAY,
        END_ARRAY,
        BEGIN_OBJECT,
        END_OBJECT,
        NAME,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL,
        END_DOCUMENT
    }

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = REPLACEMENT_CHARS;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    public abstract void beginArray();

    public abstract void beginObject();

    public abstract void endArray();

    public abstract void endObject();

    public final String getPath() {
        int i = this.stackSize;
        int[] iArr = this.scopes;
        String[] strArr = this.pathNames;
        int[] iArr2 = this.pathIndices;
        StringBuilder sb = new StringBuilder("$");
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            if (i3 == 1 || i3 == 2) {
                sb.append('[');
                sb.append(iArr2[i2]);
                sb.append(']');
            } else if (i3 == 3 || i3 == 4 || i3 == 5) {
                sb.append('.');
                String str = strArr[i2];
                if (str != null) {
                    sb.append(str);
                }
            }
        }
        return sb.toString();
    }

    public abstract boolean hasNext();

    public abstract boolean nextBoolean();

    public abstract double nextDouble();

    public abstract int nextInt();

    public abstract String nextString();

    public abstract Token peek();

    public final void pushScope(int i) {
        int i2 = this.stackSize;
        int[] iArr = this.scopes;
        if (i2 == iArr.length) {
            if (i2 == 256) {
                throw new JsonDataException("Nesting too deep at " + getPath());
            }
            this.scopes = Arrays.copyOf(iArr, iArr.length * 2);
            String[] strArr = this.pathNames;
            this.pathNames = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
            int[] iArr2 = this.pathIndices;
            this.pathIndices = Arrays.copyOf(iArr2, iArr2.length * 2);
        }
        int[] iArr3 = this.scopes;
        int i3 = this.stackSize;
        this.stackSize = i3 + 1;
        iArr3[i3] = i;
    }

    public abstract int selectName(Options options);

    public abstract void skipName();

    public abstract void skipValue();

    public final void syntaxError(String str) {
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, " at path ");
        m2m.append(getPath());
        throw new JsonEncodingException(m2m.toString());
    }
}
