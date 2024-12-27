package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes5.dex */
public class ShaderData implements Operation, VariableSupport {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_IMAGE_DIMENSION = 8000;
    int mShaderID;
    int mShaderTextId;
    HashMap<String, Integer> mUniformBitmapMap;
    HashMap<String, float[]> mUniformFloatMap;
    HashMap<String, int[]> mUniformIntMap;
    HashMap<String, float[]> mUniformRawFloatMap;

    public ShaderData(int shaderID, int shaderTextId, HashMap<String, float[]> floatMap, HashMap<String, int[]> intMap, HashMap<String, Integer> bitmapMap) {
        this.mUniformRawFloatMap = null;
        this.mUniformFloatMap = null;
        this.mUniformIntMap = null;
        this.mUniformBitmapMap = null;
        this.mShaderID = shaderID;
        this.mShaderTextId = shaderTextId;
        if (floatMap != null) {
            this.mUniformFloatMap = new HashMap<>();
            this.mUniformRawFloatMap = new HashMap<>();
            for (String name : floatMap.keySet()) {
                this.mUniformRawFloatMap.put(name, floatMap.get(name));
                this.mUniformFloatMap.put(name, floatMap.get(name));
            }
        }
        if (intMap != null) {
            this.mUniformIntMap = new HashMap<>();
            for (String name2 : intMap.keySet()) {
                this.mUniformIntMap.put(name2, intMap.get(name2));
            }
        }
        if (bitmapMap != null) {
            this.mUniformBitmapMap = new HashMap<>();
            for (String name3 : bitmapMap.keySet()) {
                this.mUniformBitmapMap.put(name3, bitmapMap.get(name3));
            }
        }
    }

    public int getShaderTextId() {
        return this.mShaderTextId;
    }

    public String[] getUniformFloatNames() {
        return this.mUniformFloatMap == null ? new String[0] : (String[]) this.mUniformFloatMap.keySet().toArray(new String[0]);
    }

    public float[] getUniformFloats(String name) {
        return this.mUniformFloatMap.get(name);
    }

    public String[] getUniformIntegerNames() {
        return this.mUniformIntMap == null ? new String[0] : (String[]) this.mUniformIntMap.keySet().toArray(new String[0]);
    }

    public int[] getUniformInts(String name) {
        return this.mUniformIntMap.get(name);
    }

    public String[] getUniformBitmapNames() {
        return this.mUniformBitmapMap == null ? new String[0] : (String[]) this.mUniformBitmapMap.keySet().toArray(new String[0]);
    }

    public int getUniformBitmapId(String name) {
        return this.mUniformBitmapMap.get(name).intValue();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mShaderID, this.mShaderTextId, this.mUniformFloatMap, this.mUniformIntMap, this.mUniformBitmapMap);
    }

    public String toString() {
        return "SHADER DATA " + this.mShaderID;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        for (String name : this.mUniformRawFloatMap.keySet()) {
            float[] value = this.mUniformRawFloatMap.get(name);
            float[] out = null;
            for (int i = 0; i < value.length; i++) {
                if (Float.isNaN(value[i])) {
                    if (out == null) {
                        out = Arrays.copyOf(value, value.length);
                    }
                    out[i] = context.getFloat(Utils.idFromNan(value[i]));
                }
            }
            this.mUniformFloatMap.put(name, out == null ? value : out);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        for (String name : this.mUniformRawFloatMap.keySet()) {
            float[] value = this.mUniformRawFloatMap.get(name);
            for (int i = 0; i < value.length; i++) {
                if (Float.isNaN(value[i])) {
                    context.listensTo(Utils.idFromNan(value[i]), this);
                }
            }
        }
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "BitmapData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 45;
        }

        public void apply(WireBuffer buffer, int shaderID, int shaderTextId, HashMap<String, float[]> floatMap, HashMap<String, int[]> intMap, HashMap<String, Integer> bitmapMap) {
            buffer.start(45);
            buffer.writeInt(shaderID);
            buffer.writeInt(shaderTextId);
            int floatSize = floatMap == null ? 0 : floatMap.size();
            int intSize = intMap == null ? 0 : intMap.size();
            int bitmapSize = bitmapMap != null ? bitmapMap.size() : 0;
            int sizes = (intSize << 8) | floatSize | (bitmapSize << 16);
            buffer.writeInt(sizes);
            if (floatSize > 0) {
                for (String name : floatMap.keySet()) {
                    buffer.writeUTF8(name);
                    float[] values = floatMap.get(name);
                    buffer.writeInt(values.length);
                    for (float f : values) {
                        buffer.writeFloat(f);
                    }
                }
            }
            if (intSize > 0) {
                for (String name2 : intMap.keySet()) {
                    buffer.writeUTF8(name2);
                    int[] values2 = intMap.get(name2);
                    buffer.writeInt(values2.length);
                    for (int i : values2) {
                        buffer.writeInt(i);
                    }
                }
            }
            if (bitmapSize > 0) {
                for (String name3 : bitmapMap.keySet()) {
                    buffer.writeUTF8(name3);
                    int value = bitmapMap.get(name3).intValue();
                    buffer.writeInt(value);
                }
            }
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            HashMap<String, float[]> floatMap;
            HashMap<String, int[]> intMap;
            HashMap<String, Integer> bitmapMap;
            int shaderID = buffer.readInt();
            int shaderTextId = buffer.readInt();
            int sizes = buffer.readInt();
            int floatMapSize = sizes & 255;
            if (floatMapSize <= 0) {
                floatMap = null;
            } else {
                HashMap<String, float[]> floatMap2 = new HashMap<>();
                for (int i = 0; i < floatMapSize; i++) {
                    String name = buffer.readUTF8();
                    int len = buffer.readInt();
                    float[] val = new float[len];
                    for (int j = 0; j < len; j++) {
                        val[j] = buffer.readFloat();
                    }
                    floatMap2.put(name, val);
                }
                floatMap = floatMap2;
            }
            int intMapSize = (sizes >> 8) & 255;
            if (intMapSize <= 0) {
                intMap = null;
            } else {
                HashMap<String, int[]> intMap2 = new HashMap<>();
                for (int i2 = 0; i2 < intMapSize; i2++) {
                    String name2 = buffer.readUTF8();
                    int len2 = buffer.readInt();
                    int[] val2 = new int[len2];
                    for (int j2 = 0; j2 < len2; j2++) {
                        val2[j2] = buffer.readInt();
                    }
                    intMap2.put(name2, val2);
                }
                intMap = intMap2;
            }
            int bitmapMapSize = (sizes >> 16) & 255;
            if (bitmapMapSize <= 0) {
                bitmapMap = null;
            } else {
                HashMap<String, Integer> bitmapMap2 = new HashMap<>();
                for (int i3 = 0; i3 < bitmapMapSize; i3++) {
                    String name3 = buffer.readUTF8();
                    bitmapMap2.put(name3, Integer.valueOf(buffer.readInt()));
                }
                bitmapMap = bitmapMap2;
            }
            operations.add(new ShaderData(shaderID, shaderTextId, floatMap, intMap, bitmapMap));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.loadShader(this.mShaderID, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
