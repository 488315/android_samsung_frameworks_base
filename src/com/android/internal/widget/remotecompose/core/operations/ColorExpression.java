package com.android.internal.widget.remotecompose.core.operations;

import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.quality.MediaQualityContract;
import android.security.keystore.KeyProperties;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class ColorExpression extends Operation implements VariableSupport, Serializable {
    public static final byte ARGB_MODE = 5;
    private static final String CLASS_NAME = "ColorExpression";
    public static final byte COLOR_COLOR_INTERPOLATE = 0;
    public static final byte COLOR_ID_INTERPOLATE = 2;
    public static final byte HSV_MODE = 4;
    public static final byte IDARGB_MODE = 6;
    public static final byte ID_COLOR_INTERPOLATE = 1;
    public static final byte ID_ID_INTERPOLATE = 3;
    private static final int OP_CODE = 134;
    public int mAlpha;
    private float mArgbAlpha;
    private float mArgbBlue;
    private float mArgbGreen;
    private float mArgbRed;
    public int mColor1;
    public int mColor2;
    public float mHue;
    public int mId;
    int mMode;
    private float mOutArgbAlpha;
    private float mOutArgbBlue;
    private float mOutArgbGreen;
    private float mOutArgbRed;
    public int mOutColor1;
    public int mOutColor2;
    public float mOutHue;
    public float mOutSat;
    public float mOutTween;
    public float mOutValue;
    public float mSat;
    public float mTween;
    public float mValue;

    public static int id() {
        return 134;
    }

    public ColorExpression(int i, float f, float f2, float f3) {
        this.mTween = 0.0f;
        this.mArgbAlpha = 0.0f;
        this.mArgbRed = 0.0f;
        this.mArgbGreen = 0.0f;
        this.mArgbBlue = 0.0f;
        this.mOutArgbAlpha = 0.0f;
        this.mOutArgbRed = 0.0f;
        this.mOutArgbGreen = 0.0f;
        this.mOutArgbBlue = 0.0f;
        this.mOutTween = 0.0f;
        this.mMode = 4;
        this.mAlpha = 255;
        this.mHue = f;
        this.mOutHue = f;
        this.mSat = f2;
        this.mOutSat = f2;
        this.mValue = f3;
        this.mOutValue = f3;
        this.mColor1 = Float.floatToRawIntBits(f);
        this.mColor2 = Float.floatToRawIntBits(f2);
        this.mTween = f3;
    }

    public ColorExpression(int i, byte b, int i2, float f, float f2, float f3) {
        this.mTween = 0.0f;
        this.mHue = 0.0f;
        this.mSat = 0.0f;
        this.mValue = 0.0f;
        this.mOutHue = 0.0f;
        this.mOutSat = 0.0f;
        this.mOutValue = 0.0f;
        this.mAlpha = 255;
        this.mArgbAlpha = 0.0f;
        this.mArgbRed = 0.0f;
        this.mArgbGreen = 0.0f;
        this.mArgbBlue = 0.0f;
        this.mOutArgbAlpha = 0.0f;
        this.mOutArgbRed = 0.0f;
        this.mOutArgbGreen = 0.0f;
        this.mOutArgbBlue = 0.0f;
        this.mOutTween = 0.0f;
        if (b != 4) {
            throw new RuntimeException("Invalid mode " + ((int) b));
        }
        this.mId = i;
        this.mMode = 4;
        this.mAlpha = i2;
        this.mHue = f;
        this.mOutHue = f;
        this.mSat = f2;
        this.mOutSat = f2;
        this.mValue = f3;
        this.mOutValue = f3;
        this.mColor1 = Float.floatToRawIntBits(f);
        this.mColor2 = Float.floatToRawIntBits(f2);
        this.mTween = f3;
    }

    public ColorExpression(int i, int i2, int i3, int i4, float f) {
        this.mTween = 0.0f;
        this.mHue = 0.0f;
        this.mSat = 0.0f;
        this.mValue = 0.0f;
        this.mOutHue = 0.0f;
        this.mOutSat = 0.0f;
        this.mOutValue = 0.0f;
        this.mArgbAlpha = 0.0f;
        this.mArgbRed = 0.0f;
        this.mArgbGreen = 0.0f;
        this.mArgbBlue = 0.0f;
        this.mOutArgbAlpha = 0.0f;
        this.mOutArgbRed = 0.0f;
        this.mOutArgbGreen = 0.0f;
        this.mOutArgbBlue = 0.0f;
        this.mOutTween = 0.0f;
        this.mId = i;
        int i5 = i2 & 255;
        this.mMode = i5;
        this.mAlpha = (i2 >> 16) & 255;
        if (i5 == 4) {
            float intBitsToFloat = Float.intBitsToFloat(i3);
            this.mHue = intBitsToFloat;
            this.mOutHue = intBitsToFloat;
            float intBitsToFloat2 = Float.intBitsToFloat(i4);
            this.mSat = intBitsToFloat2;
            this.mOutSat = intBitsToFloat2;
            this.mValue = f;
            this.mOutValue = f;
        }
        this.mColor1 = i3;
        this.mColor2 = i4;
        this.mTween = f;
        this.mOutTween = f;
        this.mOutColor1 = i3;
        this.mOutColor2 = i4;
    }

    public ColorExpression(int i, byte b, float f, float f2, float f3, float f4) {
        this.mTween = 0.0f;
        this.mHue = 0.0f;
        this.mSat = 0.0f;
        this.mValue = 0.0f;
        this.mOutHue = 0.0f;
        this.mOutSat = 0.0f;
        this.mOutValue = 0.0f;
        this.mAlpha = 255;
        this.mArgbAlpha = 0.0f;
        this.mArgbRed = 0.0f;
        this.mArgbGreen = 0.0f;
        this.mArgbBlue = 0.0f;
        this.mOutArgbAlpha = 0.0f;
        this.mOutArgbRed = 0.0f;
        this.mOutArgbGreen = 0.0f;
        this.mOutArgbBlue = 0.0f;
        this.mOutTween = 0.0f;
        if (b != 5) {
            throw new RuntimeException("Invalid mode " + ((int) b));
        }
        this.mMode = 5;
        this.mId = i;
        this.mArgbAlpha = f;
        this.mOutArgbAlpha = f;
        this.mArgbRed = f2;
        this.mOutArgbRed = f2;
        this.mArgbGreen = f3;
        this.mOutArgbGreen = f3;
        this.mArgbBlue = f4;
        this.mOutArgbBlue = f4;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        if (this.mMode == 4) {
            if (Float.isNaN(this.mHue)) {
                this.mOutHue = remoteContext.getFloat(Utils.idFromNan(this.mHue));
            }
            if (Float.isNaN(this.mSat)) {
                this.mOutSat = remoteContext.getFloat(Utils.idFromNan(this.mSat));
            }
            if (Float.isNaN(this.mValue)) {
                this.mOutValue = remoteContext.getFloat(Utils.idFromNan(this.mValue));
            }
        }
        if (this.mMode == 5) {
            if (Float.isNaN(this.mArgbAlpha)) {
                this.mOutArgbAlpha = remoteContext.getFloat(Utils.idFromNan(this.mArgbAlpha));
            }
            if (Float.isNaN(this.mArgbRed)) {
                this.mOutArgbRed = remoteContext.getFloat(Utils.idFromNan(this.mArgbRed));
            }
            if (Float.isNaN(this.mArgbGreen)) {
                this.mOutArgbGreen = remoteContext.getFloat(Utils.idFromNan(this.mArgbGreen));
            }
            if (Float.isNaN(this.mArgbBlue)) {
                this.mOutArgbBlue = remoteContext.getFloat(Utils.idFromNan(this.mArgbBlue));
            }
        }
        if (Float.isNaN(this.mTween)) {
            this.mOutTween = remoteContext.getFloat(Utils.idFromNan(this.mTween));
        }
        if ((this.mMode & 1) == 1) {
            this.mOutColor1 = remoteContext.getColor(this.mColor1);
        }
        if ((this.mMode & 2) == 2) {
            this.mOutColor2 = remoteContext.getColor(this.mColor2);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        int i = this.mMode;
        if (i == 4) {
            if (Float.isNaN(this.mHue)) {
                remoteContext.listensTo(Utils.idFromNan(this.mHue), this);
            }
            if (Float.isNaN(this.mSat)) {
                remoteContext.listensTo(Utils.idFromNan(this.mSat), this);
            }
            if (Float.isNaN(this.mValue)) {
                remoteContext.listensTo(Utils.idFromNan(this.mValue), this);
                return;
            }
            return;
        }
        if (i == 5) {
            if (Float.isNaN(this.mArgbAlpha)) {
                remoteContext.listensTo(Utils.idFromNan(this.mArgbAlpha), this);
            }
            if (Float.isNaN(this.mArgbRed)) {
                remoteContext.listensTo(Utils.idFromNan(this.mArgbRed), this);
            }
            if (Float.isNaN(this.mArgbGreen)) {
                remoteContext.listensTo(Utils.idFromNan(this.mArgbGreen), this);
            }
            if (Float.isNaN(this.mArgbBlue)) {
                remoteContext.listensTo(Utils.idFromNan(this.mArgbBlue), this);
                return;
            }
            return;
        }
        if (Float.isNaN(this.mTween)) {
            remoteContext.listensTo(Utils.idFromNan(this.mTween), this);
        }
        if ((this.mMode & 1) == 1) {
            remoteContext.listensTo(this.mColor1, this);
        }
        if ((this.mMode & 2) == 2) {
            remoteContext.listensTo(this.mColor2, this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        int i = this.mMode;
        if (i == 4) {
            remoteContext.loadColor(this.mId, (Utils.hsvToRgb(this.mOutHue, this.mOutSat, this.mOutValue) & 16777215) | (this.mAlpha << 24));
        } else {
            if (i == 5) {
                remoteContext.loadColor(this.mId, Utils.toARGB(this.mOutArgbAlpha, this.mOutArgbRed, this.mOutArgbGreen, this.mOutArgbBlue));
                return;
            }
            if (this.mOutTween == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                if ((i & 1) == 1) {
                    this.mOutColor1 = remoteContext.getColor(this.mColor1);
                }
                remoteContext.loadColor(this.mId, this.mOutColor1);
            } else {
                if ((i & 1) == 1) {
                    this.mOutColor1 = remoteContext.getColor(this.mColor1);
                }
                if ((this.mMode & 2) == 2) {
                    this.mOutColor2 = remoteContext.getColor(this.mColor2);
                }
                remoteContext.loadColor(this.mId, Utils.interpolateColor(this.mOutColor1, this.mOutColor2, this.mOutTween));
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        int i = this.mMode;
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            apply(wireBuffer, this.mId, i, this.mColor1, this.mColor2, this.mTween);
            return;
        }
        if (i != 4) {
            if (i == 5) {
                apply(wireBuffer, this.mId, this.mArgbAlpha, this.mArgbRed, this.mArgbGreen, this.mArgbBlue);
                return;
            }
            throw new RuntimeException("Invalid mode ");
        }
        this.mOutValue = this.mValue;
        this.mColor1 = Float.floatToRawIntBits(this.mHue);
        int floatToRawIntBits = Float.floatToRawIntBits(this.mSat);
        this.mColor2 = floatToRawIntBits;
        apply(wireBuffer, this.mId, this.mMode | (this.mAlpha << 16), this.mColor1, floatToRawIntBits, this.mTween);
    }

    public String toString() {
        String colorInt;
        String colorInt2;
        if (this.mMode == 4) {
            return "ColorExpression[" + this.mId + "] = hsv (" + Utils.floatToString(this.mHue) + ", " + Utils.floatToString(this.mSat) + ", " + Utils.floatToString(this.mValue) + NavigationBarInflaterView.KEY_CODE_END;
        }
        Utils.log(" ColorExpression toString" + this.mId + " " + this.mMode);
        int i = this.mMode;
        if (i == 5) {
            return "ColorExpression[" + this.mId + "] = rgb (" + Utils.floatToString(this.mArgbAlpha) + ", " + Utils.floatToString(this.mArgbRed) + ", " + Utils.floatToString(this.mArgbGreen) + ", " + Utils.floatToString(this.mArgbRed) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if ((i & 1) == 1) {
            colorInt = NavigationBarInflaterView.SIZE_MOD_START + this.mColor1 + NavigationBarInflaterView.SIZE_MOD_END;
        } else {
            colorInt = Utils.colorInt(this.mColor1);
        }
        if ((this.mMode & 2) == 2) {
            colorInt2 = NavigationBarInflaterView.SIZE_MOD_START + this.mColor2 + NavigationBarInflaterView.SIZE_MOD_END;
        } else {
            colorInt2 = Utils.colorInt(this.mColor2);
        }
        return "ColorExpression[" + this.mId + "] = tween(" + colorInt + ", " + colorInt2 + ", " + Utils.floatToString(this.mTween) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, float f) {
        apply(wireBuffer, i, i2, i3, i4, Float.floatToRawIntBits(f));
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3, float f4) {
        apply(wireBuffer, i, (Float.isNaN(f) ? 6 : 5) | ((Float.isNaN(f) ? Utils.idFromNan(f) : (int) (f * 1024.0f)) << 16), Float.floatToRawIntBits(f2), Float.floatToRawIntBits(f3), Float.floatToRawIntBits(f4));
    }

    private static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, int i5) {
        wireBuffer.start(134);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeInt(i5);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        int readInt3 = wireBuffer.readInt();
        int readInt4 = wireBuffer.readInt();
        int readInt5 = wireBuffer.readInt();
        int i = readInt2 & 255;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                list.add(new ColorExpression(readInt, i, readInt3, readInt4, Float.intBitsToFloat(readInt5)));
                return;
            case 4:
                list.add(new ColorExpression(readInt, (byte) 4, readInt2 >> 16, Float.intBitsToFloat(readInt3), Float.intBitsToFloat(readInt4), Float.intBitsToFloat(readInt5)));
                return;
            case 5:
                list.add(new ColorExpression(readInt, (byte) 5, (readInt2 >> 16) / 1024.0f, Float.intBitsToFloat(readInt3), Float.intBitsToFloat(readInt4), Float.intBitsToFloat(readInt5)));
                return;
            case 6:
                list.add(new ColorExpression(readInt, (byte) 5, Utils.asNan(readInt2 >> 16), Float.intBitsToFloat(readInt3), Float.intBitsToFloat(readInt4), Float.intBitsToFloat(readInt5)));
                return;
            default:
                throw new RuntimeException("Invalid mode " + i);
        }
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 134, CLASS_NAME).description("A Color defined by an expression").field(0, "id", "Id of the color").field(0, "mode", "The use of the next 3 fields").possibleValues("COLOR_COLOR_INTERPOLATE", 0).possibleValues("COLOR_ID_INTERPOLATE", 1).possibleValues("ID_COLOR_INTERPOLATE", 2).possibleValues("ID_ID_INTERPOLATE", 3).possibleValues("HSV", 4).field(0, "color1", "32 bit ARGB color").field(0, "color2", "32 bit ARGB color").field(1, "tween", "32 bit ARGB color");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId));
        switch (this.mMode) {
            case 0:
            case 1:
            case 2:
            case 3:
                mapSerializer.add("mode", "TWEEN");
                mapSerializer.add("startColor", this.mColor1, this.mOutColor1);
                mapSerializer.add("endColor", this.mColor2, this.mOutColor2);
                mapSerializer.add("startColor", this.mTween, this.mOutTween);
                break;
            case 4:
                mapSerializer.add("mode", "HSV");
                mapSerializer.add(MediaQualityContract.PictureQuality.PARAMETER_HUE, this.mHue, this.mOutHue);
                mapSerializer.add("sat", this.mSat, this.mOutSat);
                mapSerializer.add("val", this.mValue, this.mOutValue);
                break;
            case 5:
            case 6:
                mapSerializer.add("mode", "ARGB");
                mapSerializer.add(FullBackup.APK_TREE_TOKEN, this.mArgbAlpha, this.mOutArgbAlpha);
                mapSerializer.add("r", this.mArgbRed, this.mOutArgbRed);
                mapSerializer.add("g", this.mArgbGreen, this.mOutArgbGreen);
                mapSerializer.add(XmlTags.TAG_BLOB, this.mArgbBlue, this.mOutArgbBlue);
                break;
            default:
                mapSerializer.add("mode", KeyProperties.DIGEST_NONE);
                break;
        }
    }
}
