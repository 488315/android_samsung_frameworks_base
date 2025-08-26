package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.AnimatableValue;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes6.dex */
public class GraphicsLayerModifierOperation extends DecoratorModifierOperation {
    public static final int ALPHA = 11;
    public static final int AMBIENT_SHADOW_COLOR = 15;
    public static final int BLUR_RADIUS_X = 17;
    public static final int BLUR_RADIUS_Y = 18;
    public static final int BLUR_TILE_MODE = 19;
    public static final int CAMERA_DISTANCE = 12;
    public static final String CLASS_NAME = "GraphicsLayerModifierOperation";
    public static final int COMPOSITING_STRATEGY = 13;
    private static final short DATA_TYPE_FLOAT = 1;
    private static final short DATA_TYPE_INT = 0;
    static final int FLOAT_VALUE = 0;
    public static final int HAS_BLUR = 16;
    static final int INT_VALUE = 1;
    private static final int OP_CODE = 224;
    public static final int ROTATION_X = 2;
    public static final int ROTATION_Y = 3;
    public static final int ROTATION_Z = 4;
    public static final int SCALE_X = 0;
    public static final int SCALE_Y = 1;
    public static final int SHADOW_ELEVATION = 10;
    public static final int SHAPE = 20;
    public static final int SHAPE_CIRCLE = 2;
    public static final int SHAPE_RADIUS = 21;
    public static final int SHAPE_RECT = 0;
    public static final int SHAPE_ROUND_RECT = 1;
    public static final int SPOT_SHADOW_COLOR = 14;
    public static final int TILE_MODE_CLAMP = 0;
    public static final int TILE_MODE_DECAL = 3;
    public static final int TILE_MODE_MIRROR = 2;
    public static final int TILE_MODE_REPEATED = 1;
    public static final int TRANSFORM_ORIGIN_X = 5;
    public static final int TRANSFORM_ORIGIN_Y = 6;
    public static final int TRANSLATION_X = 7;
    public static final int TRANSLATION_Y = 8;
    public static final int TRANSLATION_Z = 9;
    AttributeValue[] mValues = {new AttributeValue(0, "SCALE_X", 1.0f), new AttributeValue(1, "SCALE_Y", 1.0f), new AttributeValue(2, "ROTATION_X", 0.0f), new AttributeValue(3, "ROTATION_Y", 0.0f), new AttributeValue(4, "ROTATION_Z", 0.0f), new AttributeValue(5, "TRANSFORM_ORIGIN_X", 0.0f), new AttributeValue(6, "TRANSFORM_ORIGIN_Y", 0.0f), new AttributeValue(7, "TRANSLATION_X", 0.0f), new AttributeValue(8, "TRANSLATION_Y", 0.0f), new AttributeValue(9, "TRANSLATION_Z", 0.0f), new AttributeValue(10, "SHADOW_ELEVATION", 0.0f), new AttributeValue(11, "ALPHA", 1.0f), new AttributeValue(12, "CAMERA_DISTANCE", 8.0f), new AttributeValue(13, "COMPOSITING_STRATEGY", 0), new AttributeValue(14, "SPOT_SHADOW_COLOR", 0), new AttributeValue(15, "AMBIENT_SHADOW_COLOR", 0), new AttributeValue(16, "HAS_BLUR", 0), new AttributeValue(17, "BLUR_RADIUS_X", 0.0f), new AttributeValue(18, "BLUR_RADIUS_Y", 0.0f), new AttributeValue(19, "BLUR_TILE_MODE", 0), new AttributeValue(20, "SHAPE", -1), new AttributeValue(21, "SHAPE_RADIUS", 0.0f)};
    boolean mHasBlurEffect = false;

    public static int id() {
        return 224;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
    }

    public void fillInAttributes(HashMap<Integer, Object> map) {
        int i = 0;
        while (true) {
            AttributeValue[] attributeValueArr = this.mValues;
            if (i >= attributeValueArr.length) {
                return;
            }
            if (attributeValueArr[i].needsToWrite()) {
                map.put(Integer.valueOf(i), this.mValues[i].getObjectValue());
            }
            i++;
        }
    }

    static class AttributeValue {
        AnimatableValue mAnimatableValue;
        float mDefaultValue;
        int mId;
        int mIntDefaultValue;
        int mIntValue;
        String mName;
        int mType;

        AttributeValue(int i, String str, float f) {
            this.mIntValue = 0;
            this.mIntDefaultValue = 0;
            this.mId = i;
            this.mName = str;
            this.mDefaultValue = f;
            this.mType = 0;
        }

        AttributeValue(int i, String str, int i2) {
            this.mDefaultValue = 0.0f;
            this.mIntValue = 0;
            this.mId = i;
            this.mName = str;
            this.mIntDefaultValue = i2;
            this.mType = 1;
        }

        public float getValue() {
            if (this.mType == 0) {
                AnimatableValue animatableValue = this.mAnimatableValue;
                if (animatableValue != null) {
                    return animatableValue.getValue();
                }
                return this.mDefaultValue;
            }
            return this.mIntValue;
        }

        public int getIntValue() {
            int i = this.mType;
            if (i != 0) {
                if (i == 1) {
                    return this.mIntValue;
                }
                return 0;
            }
            AnimatableValue animatableValue = this.mAnimatableValue;
            if (animatableValue != null) {
                return (int) animatableValue.getValue();
            }
            return 0;
        }

        public void evaluate(PaintContext paintContext) {
            AnimatableValue animatableValue = this.mAnimatableValue;
            if (animatableValue != null) {
                animatableValue.evaluate(paintContext);
            }
        }

        public boolean needsToWrite() {
            int i = this.mType;
            if (i != 0) {
                return i == 1 && this.mIntValue != this.mIntDefaultValue;
            }
            AnimatableValue animatableValue = this.mAnimatableValue;
            return (animatableValue == null || animatableValue.getValue() == this.mDefaultValue) ? false : true;
        }

        public void write(WireBuffer wireBuffer) {
            wireBuffer.writeInt(this.mId);
            int i = this.mType;
            if (i == 0) {
                wireBuffer.writeFloat(getValue());
            } else if (i == 1) {
                wireBuffer.writeInt(getIntValue());
            }
        }

        public Object getObjectValue() {
            if (this.mType == 0) {
                return Float.valueOf(getValue());
            }
            return Integer.valueOf(getIntValue());
        }

        public void setValue(float f) {
            this.mAnimatableValue = new AnimatableValue(f);
        }

        public void setValue(int i) {
            this.mIntValue = i;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        wireBuffer.start(224);
        wireBuffer.writeInt(this.mValues.length);
        int i = 0;
        while (true) {
            AttributeValue[] attributeValueArr = this.mValues;
            if (i >= attributeValueArr.length) {
                return;
            }
            AttributeValue attributeValue = attributeValueArr[i];
            if (attributeValue.needsToWrite()) {
                attributeValue.write(wireBuffer);
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "GRAPHICS_LAYER = [" + this.mValues[0].getValue() + ", " + this.mValues[1].getValue() + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        int i = 0;
        while (true) {
            AttributeValue[] attributeValueArr = this.mValues;
            if (i >= attributeValueArr.length) {
                return;
            }
            attributeValueArr[i].evaluate(paintContext);
            i++;
        }
    }

    public String toString() {
        return "GraphicsLayerModifierOperation(" + this.mValues[0].getValue() + ", " + this.mValues[1].getValue() + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, HashMap<Integer, Object> map) {
        wireBuffer.start(224);
        wireBuffer.writeInt(map.size());
        for (Integer num : map.keySet()) {
            Object obj = map.get(num);
            if (obj instanceof Integer) {
                writeIntAttribute(wireBuffer, num.intValue(), ((Integer) obj).intValue());
            } else if (obj instanceof Float) {
                writeFloatAttribute(wireBuffer, num.intValue(), ((Float) obj).floatValue());
            }
        }
    }

    private static void writeIntAttribute(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    private static void writeFloatAttribute(WireBuffer wireBuffer, int i, float f) {
        wireBuffer.writeInt(i | 1024);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int i = wireBuffer.readInt();
        GraphicsLayerModifierOperation graphicsLayerModifierOperation = new GraphicsLayerModifierOperation();
        for (int i2 = 0; i2 < i; i2++) {
            graphicsLayerModifierOperation.readAttributeValue(wireBuffer);
        }
        list.add(graphicsLayerModifierOperation);
    }

    private void readAttributeValue(WireBuffer wireBuffer) {
        int i = wireBuffer.readInt();
        int i2 = i >> 10;
        short s = (short) (i & 63);
        if (s == 17 || s == 18) {
            this.mHasBlurEffect = true;
            this.mValues[16].setValue(1);
        }
        if (i2 == 1) {
            this.mValues[s].setValue(wireBuffer.readFloat());
        } else if (i2 == 0) {
            this.mValues[s].setValue(wireBuffer.readInt());
        }
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 224, CLASS_NAME).description("define the GraphicsLayer Modifier");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("scaleX", Float.valueOf(this.mValues[0].getValue())).add("scaleY", Float.valueOf(this.mValues[1].getValue())).add("rotationX", Float.valueOf(this.mValues[2].getValue())).add("rotationY", Float.valueOf(this.mValues[3].getValue())).add("rotationZ", Float.valueOf(this.mValues[4].getValue())).add("shadowElevation", Float.valueOf(this.mValues[10].getValue())).add("transformOriginX", Float.valueOf(this.mValues[5].getValue())).add("transformOriginY", Float.valueOf(this.mValues[6].getValue())).add("translationX", Float.valueOf(this.mValues[7].getValue())).add("translationY", Float.valueOf(this.mValues[8].getValue())).add("translationZ", Float.valueOf(this.mValues[9].getValue())).add("alpha", Float.valueOf(this.mValues[11].getValue())).add("cameraDistance", Float.valueOf(this.mValues[12].getValue())).add("compositingStrategy", Integer.valueOf(this.mValues[13].getIntValue())).add("spotShadowColorId", Integer.valueOf(this.mValues[14].getIntValue())).add("ambientShadowColorId", Integer.valueOf(this.mValues[15].getIntValue()));
    }
}
