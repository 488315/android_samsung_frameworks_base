package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class DrawTextAnchored extends PaintOperation implements VariableSupport {
    public static final int ANCHOR_MONOSPACE_MEASURE = 2;
    public static final int ANCHOR_TEXT_RTL = 1;
    public static final Companion COMPANION = new Companion();
    float[] mBounds = new float[4];
    int mFlags;
    float mOutPanX;
    float mOutPanY;
    float mOutX;
    float mOutY;
    float mPanX;
    float mPanY;
    int mTextID;
    float mX;
    float mY;

    public DrawTextAnchored(int textID, float x, float y, float panX, float panY, int flags) {
        this.mTextID = textID;
        this.mX = x;
        this.mY = y;
        this.mOutX = this.mX;
        this.mOutY = this.mY;
        this.mFlags = flags;
        this.mPanX = panX;
        this.mOutPanX = panX;
        this.mPanY = panY;
        this.mOutPanY = panY;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        this.mOutX = Float.isNaN(this.mX) ? context.getFloat(Utils.idFromNan(this.mX)) : this.mX;
        this.mOutY = Float.isNaN(this.mY) ? context.getFloat(Utils.idFromNan(this.mY)) : this.mY;
        this.mOutPanX =
                Float.isNaN(this.mPanX)
                        ? context.getFloat(Utils.idFromNan(this.mPanX))
                        : this.mPanX;
        this.mOutPanY =
                Float.isNaN(this.mPanY)
                        ? context.getFloat(Utils.idFromNan(this.mPanY))
                        : this.mPanY;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (Float.isNaN(this.mX)) {
            context.listensTo(Utils.idFromNan(this.mX), this);
        }
        if (Float.isNaN(this.mY)) {
            context.listensTo(Utils.idFromNan(this.mY), this);
        }
        if (Float.isNaN(this.mPanX)) {
            context.listensTo(Utils.idFromNan(this.mPanX), this);
        }
        if (Float.isNaN(this.mPanY) && Utils.idFromNan(this.mPanY) > 0) {
            context.listensTo(Utils.idFromNan(this.mPanY), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(
                buffer, this.mTextID, this.mX, this.mY, this.mPanX, this.mPanY, this.mFlags);
    }

    public String toString() {
        return "DrawTextAnchored ["
                + this.mTextID
                + "] "
                + floatToStr(this.mX)
                + ", "
                + floatToStr(this.mY)
                + ", "
                + floatToStr(this.mPanX)
                + ", "
                + floatToStr(this.mPanY)
                + ", "
                + Integer.toBinaryString(this.mFlags);
    }

    private static String floatToStr(float v) {
        if (Float.isNaN(v)) {
            return NavigationBarInflaterView.SIZE_MOD_START
                    + Utils.idFromNan(v)
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return Float.toString(v);
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int textID = buffer.readInt();
            float x = buffer.readFloat();
            float y = buffer.readFloat();
            float panX = buffer.readFloat();
            float panY = buffer.readFloat();
            int flags = buffer.readInt();
            DrawTextAnchored op = new DrawTextAnchored(textID, x, y, panX, panY, flags);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 0;
        }

        public void apply(
                WireBuffer buffer,
                int textID,
                float x,
                float y,
                float panX,
                float panY,
                int flags) {
            buffer.start(133);
            buffer.writeInt(textID);
            buffer.writeFloat(x);
            buffer.writeFloat(y);
            buffer.writeFloat(panX);
            buffer.writeFloat(panY);
            buffer.writeInt(flags);
        }
    }

    private float getHorizontalOffset() {
        float textWidth = (this.mBounds[2] - this.mBounds[0]) * 1.0f;
        return (((0.0f - textWidth) * (this.mOutPanX + 1.0f)) / 2.0f) - (this.mBounds[0] * 1.0f);
    }

    private float getVerticalOffset() {
        float textHeight = (this.mBounds[3] - this.mBounds[1]) * 1.0f;
        return (((0.0f - textHeight) * (1.0f - this.mOutPanY)) / 2.0f) - (this.mBounds[1] * 1.0f);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.getTextBounds(this.mTextID, 0, -1, (this.mFlags & 2) != 0, this.mBounds);
        float x = this.mOutX + getHorizontalOffset();
        float y = Float.isNaN(this.mOutPanY) ? this.mOutY : this.mOutY + getVerticalOffset();
        context.drawTextRun(this.mTextID, 0, -1, 0, 1, x, y, (this.mFlags & 1) == 1);
    }
}
