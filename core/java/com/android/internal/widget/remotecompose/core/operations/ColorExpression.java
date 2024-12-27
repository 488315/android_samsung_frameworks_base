package com.android.internal.widget.remotecompose.core.operations;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class ColorExpression implements Operation, VariableSupport {
    public static final Companion COMPANION = new Companion();
    public static final int HSV_MODE = 4;
    public int mAlpha;
    public int mColor1;
    public int mColor2;
    public float mHue;
    public int mId;
    int mMode;
    public int mOutColor1;
    public int mOutColor2;
    public float mOutHue;
    public float mOutSat;
    public float mOutTween;
    public float mOutValue;
    public float mSat;
    public float mTween;
    public float mValue;

    public ColorExpression(int id, float hue, float sat, float value) {
        this.mTween = 0.0f;
        this.mHue = 0.0f;
        this.mSat = 0.0f;
        this.mValue = 0.0f;
        this.mOutHue = 0.0f;
        this.mOutSat = 0.0f;
        this.mOutValue = 0.0f;
        this.mAlpha = 255;
        this.mOutTween = 0.0f;
        this.mMode = 4;
        this.mAlpha = 255;
        this.mHue = hue;
        this.mOutHue = hue;
        this.mSat = sat;
        this.mOutSat = sat;
        this.mValue = value;
        this.mOutValue = value;
        this.mColor1 = Float.floatToRawIntBits(hue);
        this.mColor2 = Float.floatToRawIntBits(sat);
        this.mTween = value;
    }

    public ColorExpression(int id, int alpha, float hue, float sat, float value) {
        this.mTween = 0.0f;
        this.mHue = 0.0f;
        this.mSat = 0.0f;
        this.mValue = 0.0f;
        this.mOutHue = 0.0f;
        this.mOutSat = 0.0f;
        this.mOutValue = 0.0f;
        this.mAlpha = 255;
        this.mOutTween = 0.0f;
        this.mMode = 4;
        this.mAlpha = alpha;
        this.mHue = hue;
        this.mOutHue = hue;
        this.mSat = sat;
        this.mOutSat = sat;
        this.mValue = value;
        this.mOutValue = value;
        this.mColor1 = Float.floatToRawIntBits(hue);
        this.mColor2 = Float.floatToRawIntBits(sat);
        this.mTween = value;
    }

    public ColorExpression(int id, int mode, int color1, int color2, float tween) {
        this.mTween = 0.0f;
        this.mHue = 0.0f;
        this.mSat = 0.0f;
        this.mValue = 0.0f;
        this.mOutHue = 0.0f;
        this.mOutSat = 0.0f;
        this.mOutValue = 0.0f;
        this.mAlpha = 255;
        this.mOutTween = 0.0f;
        this.mId = id;
        this.mMode = mode & 255;
        this.mAlpha = (mode >> 16) & 255;
        if (this.mMode == 4) {
            float intBitsToFloat = Float.intBitsToFloat(color1);
            this.mHue = intBitsToFloat;
            this.mOutHue = intBitsToFloat;
            float intBitsToFloat2 = Float.intBitsToFloat(color2);
            this.mSat = intBitsToFloat2;
            this.mOutSat = intBitsToFloat2;
            this.mValue = tween;
            this.mOutValue = tween;
        }
        this.mColor1 = color1;
        this.mColor2 = color2;
        this.mTween = tween;
        this.mOutTween = tween;
        this.mOutColor1 = color1;
        this.mOutColor2 = color2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        if (this.mMode == 4) {
            if (Float.isNaN(this.mHue)) {
                this.mOutHue = context.getFloat(Utils.idFromNan(this.mHue));
            }
            if (Float.isNaN(this.mSat)) {
                this.mOutSat = context.getFloat(Utils.idFromNan(this.mSat));
            }
            if (Float.isNaN(this.mValue)) {
                this.mOutValue = context.getFloat(Utils.idFromNan(this.mValue));
            }
        }
        if (Float.isNaN(this.mTween)) {
            this.mOutTween = context.getFloat(Utils.idFromNan(this.mTween));
        }
        if ((this.mMode & 1) == 1) {
            this.mOutColor1 = context.getColor(this.mColor1);
        }
        if ((this.mMode & 2) == 2) {
            this.mOutColor2 = context.getColor(this.mColor2);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (this.mMode == 4) {
            if (Float.isNaN(this.mHue)) {
                context.listensTo(Utils.idFromNan(this.mHue), this);
            }
            if (Float.isNaN(this.mSat)) {
                context.listensTo(Utils.idFromNan(this.mSat), this);
            }
            if (Float.isNaN(this.mValue)) {
                context.listensTo(Utils.idFromNan(this.mValue), this);
                return;
            }
            return;
        }
        if (Float.isNaN(this.mTween)) {
            context.listensTo(Utils.idFromNan(this.mTween), this);
        }
        if ((this.mMode & 1) == 1) {
            context.listensTo(this.mColor1, this);
        }
        if ((this.mMode & 2) == 2) {
            context.listensTo(this.mColor2, this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        if (this.mMode == 4) {
            context.loadColor(
                    this.mId,
                    (this.mAlpha << 24)
                            | (Utils.hsvToRgb(this.mOutHue, this.mOutSat, this.mOutValue)
                                    & 16777215));
            return;
        }
        if (this.mOutTween == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            context.loadColor(this.mId, this.mColor1);
            return;
        }
        if ((this.mMode & 1) == 1) {
            this.mOutColor1 = context.getColor(this.mColor1);
        }
        if ((this.mMode & 2) == 2) {
            this.mOutColor2 = context.getColor(this.mColor2);
        }
        context.loadColor(
                this.mId, Utils.interpolateColor(this.mOutColor1, this.mOutColor2, this.mOutTween));
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        int mode = this.mMode | (this.mAlpha << 16);
        COMPANION.apply(buffer, this.mId, mode, this.mColor1, this.mColor2, this.mTween);
    }

    public String toString() {
        if (this.mMode == 4) {
            return "ColorExpression["
                    + this.mId
                    + "] = hsv ("
                    + Utils.floatToString(this.mHue)
                    + ", "
                    + Utils.floatToString(this.mSat)
                    + ", "
                    + Utils.floatToString(this.mValue)
                    + NavigationBarInflaterView.KEY_CODE_END;
        }
        String c1 =
                (this.mMode & 1) == 1
                        ? NavigationBarInflaterView.SIZE_MOD_START
                                + this.mColor1
                                + NavigationBarInflaterView.SIZE_MOD_END
                        : Utils.colorInt(this.mColor1);
        String c2 =
                (this.mMode & 2) == 2
                        ? NavigationBarInflaterView.SIZE_MOD_START
                                + this.mColor2
                                + NavigationBarInflaterView.SIZE_MOD_END
                        : Utils.colorInt(this.mColor2);
        return "ColorExpression["
                + this.mId
                + "] = tween("
                + c1
                + ", "
                + c2
                + ", "
                + Utils.floatToString(this.mTween)
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "ColorExpression";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 134;
        }

        public void apply(
                WireBuffer buffer, int id, int mode, int color1, int color2, float tween) {
            buffer.start(134);
            buffer.writeInt(id);
            buffer.writeInt(mode);
            buffer.writeInt(color1);
            buffer.writeInt(color2);
            buffer.writeFloat(tween);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int id = buffer.readInt();
            int mode = buffer.readInt();
            int color1 = buffer.readInt();
            int color2 = buffer.readInt();
            float tween = buffer.readFloat();
            operations.add(new ColorExpression(id, mode, color1, color2, tween));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
