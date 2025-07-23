package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.BitmapFontData;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes6.dex */
public class BitmapFontData extends Operation implements Serializable {
    private static final String CLASS_NAME = "BitmapFontData";
    private static final int OP_CODE = 167;
    Glyph[] mFontGlyphs;
    int mId;

    public static int id() {
        return 167;
    }

    public static class Glyph {
        public short mBitmapHeight;
        public int mBitmapId;
        public short mBitmapWidth;
        public String mChars;
        public short mMarginBottom;
        public short mMarginLeft;
        public short mMarginRight;
        public short mMarginTop;

        public Glyph() {
        }

        public Glyph(String str, int i, short s, short s2, short s3, short s4, short s5, short s6) {
            this.mChars = str;
            this.mBitmapId = i;
            this.mMarginLeft = s;
            this.mMarginTop = s2;
            this.mMarginRight = s3;
            this.mMarginBottom = s4;
            this.mBitmapWidth = s5;
            this.mBitmapHeight = s6;
        }
    }

    public BitmapFontData(int i, Glyph[] glyphArr) {
        this.mId = i;
        this.mFontGlyphs = glyphArr;
        Arrays.sort(glyphArr, new Comparator() { // from class: com.android.internal.widget.remotecompose.core.operations.BitmapFontData$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BitmapFontData.lambda$new$0((BitmapFontData.Glyph) obj, (BitmapFontData.Glyph) obj2);
            }
        });
    }

    static /* synthetic */ int lambda$new$0(Glyph glyph, Glyph glyph2) {
        return glyph2.mChars.length() - glyph.mChars.length();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mFontGlyphs);
    }

    public String toString() {
        return "BITMAP FONT DATA " + this.mId;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, Glyph[] glyphArr) {
        wireBuffer.start(167);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(glyphArr.length);
        for (Glyph glyph : glyphArr) {
            wireBuffer.writeUTF8(glyph.mChars);
            wireBuffer.writeInt(glyph.mBitmapId);
            wireBuffer.writeShort(glyph.mMarginLeft);
            wireBuffer.writeShort(glyph.mMarginTop);
            wireBuffer.writeShort(glyph.mMarginRight);
            wireBuffer.writeShort(glyph.mMarginBottom);
            wireBuffer.writeShort(glyph.mBitmapWidth);
            wireBuffer.writeShort(glyph.mBitmapHeight);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        Glyph[] glyphArr = new Glyph[readInt2];
        for (int i = 0; i < readInt2; i++) {
            Glyph glyph = new Glyph();
            glyphArr[i] = glyph;
            glyph.mChars = wireBuffer.readUTF8();
            glyphArr[i].mBitmapId = wireBuffer.readInt();
            glyphArr[i].mMarginLeft = (short) wireBuffer.readShort();
            glyphArr[i].mMarginTop = (short) wireBuffer.readShort();
            glyphArr[i].mMarginRight = (short) wireBuffer.readShort();
            glyphArr[i].mMarginBottom = (short) wireBuffer.readShort();
            glyphArr[i].mBitmapWidth = (short) wireBuffer.readShort();
            glyphArr[i].mBitmapHeight = (short) wireBuffer.readShort();
        }
        list.add(new BitmapFontData(readInt, glyphArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 167, CLASS_NAME).description("Bitmap font data").field(0, "id", "id of bitmap font data").field(11, "glyphNodes", "list used to greedily convert strings into glyphs").field(11, "glyphElements", "");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.putObject(this.mId, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    public Glyph lookupGlyph(String str, int i) {
        for (Glyph glyph : this.mFontGlyphs) {
            if (str.startsWith(glyph.mChars, i)) {
                return glyph;
            }
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId));
    }
}
