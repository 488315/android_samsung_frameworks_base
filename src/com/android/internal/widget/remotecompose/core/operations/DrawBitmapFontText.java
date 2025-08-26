package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.BitmapFontData;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawBitmapFontText extends PaintOperation implements VariableSupport {
    private static final String CLASS_NAME = "DrawBitmapFontText";
    private static final int OP_CODE = 48;
    int mBitmapFontID;
    int mEnd;
    float mOutX;
    float mOutY;
    int mStart;
    int mTextID;
    float mX;
    float mY;

    public static int id() {
        return 48;
    }

    public DrawBitmapFontText(int i, int i2, int i3, int i4, float f, float f2) {
        this.mTextID = i;
        this.mBitmapFontID = i2;
        this.mStart = i3;
        this.mEnd = i4;
        this.mX = f;
        this.mOutX = f;
        this.mY = f2;
        this.mOutY = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutX = Float.isNaN(this.mX) ? remoteContext.getFloat(Utils.idFromNan(this.mX)) : this.mX;
        this.mOutY = Float.isNaN(this.mY) ? remoteContext.getFloat(Utils.idFromNan(this.mY)) : this.mY;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mX)) {
            remoteContext.listensTo(Utils.idFromNan(this.mX), this);
        }
        if (Float.isNaN(this.mY)) {
            remoteContext.listensTo(Utils.idFromNan(this.mY), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextID, this.mBitmapFontID, this.mStart, this.mEnd, this.mX, this.mY);
    }

    public String toString() {
        return "DrawBitmapFontText [" + this.mTextID + "] " + this.mBitmapFontID + ", " + this.mStart + ", " + this.mEnd + ", " + Utils.floatToString(this.mX, this.mOutX) + ", " + Utils.floatToString(this.mY, this.mOutY);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawBitmapFontText(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, float f, float f2) {
        wireBuffer.start(48);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", id(), CLASS_NAME).description("Draw a run of bitmap font text, all in a single direction").field(0, "textId", "id of bitmap").field(0, "bitmapFontId", "id of the bitmap font").field(0, "start", "The start of the text to render. -1=end of string").field(0, "end", "The end of the text to render").field(0, "contextStart", "the index of the start of the shaping context").field(0, "contextEnd", "the index of the end of the shaping context").field(1, "x", "The x position at which to draw the text").field(1, "y", "The y position at which to draw the text").field(2, "RTL", "Whether the run is in RTL direction");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        RemoteContext context = paintContext.getContext();
        String text = context.getText(this.mTextID);
        if (text == null) {
            return;
        }
        int i = this.mEnd;
        if (i == -1) {
            int i2 = this.mStart;
            if (i2 != 0) {
                text = text.substring(i2);
            }
        } else if (i > text.length()) {
            text = text.substring(this.mStart);
        } else {
            text = text.substring(this.mStart, this.mEnd);
        }
        BitmapFontData bitmapFontData = (BitmapFontData) context.getObject(this.mBitmapFontID);
        if (bitmapFontData == null) {
            return;
        }
        float f = this.mX;
        int length = 0;
        while (length < text.length()) {
            BitmapFontData.Glyph glyphLookupGlyph = bitmapFontData.lookupGlyph(text, length);
            if (glyphLookupGlyph == null) {
                length++;
            } else {
                length += glyphLookupGlyph.mChars.length();
                if (glyphLookupGlyph.mBitmapId == -1) {
                    f += glyphLookupGlyph.mMarginLeft + glyphLookupGlyph.mMarginRight;
                } else {
                    float f2 = f + glyphLookupGlyph.mMarginLeft;
                    float f3 = f2 + glyphLookupGlyph.mBitmapWidth;
                    PaintContext paintContext2 = paintContext;
                    paintContext2.drawBitmap(glyphLookupGlyph.mBitmapId, f2, this.mY + glyphLookupGlyph.mMarginTop, f3, this.mY + glyphLookupGlyph.mBitmapHeight + glyphLookupGlyph.mMarginTop);
                    f = f3 + glyphLookupGlyph.mMarginRight;
                    paintContext = paintContext2;
                }
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("textId", Integer.valueOf(this.mTextID)).add("bitmapFontId", Integer.valueOf(this.mBitmapFontID)).add("start", Integer.valueOf(this.mStart)).add("end", Integer.valueOf(this.mEnd)).add("x", this.mX, this.mOutX).add("y", this.mY, this.mOutY);
    }
}
