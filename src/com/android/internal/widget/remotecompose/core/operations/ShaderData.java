package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ShaderData extends Operation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "ShaderData";
    private static final int OP_CODE = 45;
    int mShaderID;
    int mShaderTextId;
    private boolean mShaderValid = false;
    HashMap<String, Integer> mUniformBitmapMap;
    HashMap<String, float[]> mUniformFloatMap;
    HashMap<String, int[]> mUniformIntMap;
    HashMap<String, float[]> mUniformRawFloatMap;

    public static int id() {
        return 45;
    }

    public ShaderData(int i, int i2, HashMap<String, float[]> hashMap, HashMap<String, int[]> hashMap2, HashMap<String, Integer> hashMap3) {
        this.mUniformRawFloatMap = null;
        this.mUniformFloatMap = null;
        this.mUniformBitmapMap = null;
        this.mShaderID = i;
        this.mShaderTextId = i2;
        if (hashMap != null) {
            this.mUniformFloatMap = new HashMap<>();
            this.mUniformRawFloatMap = new HashMap<>();
            for (String str : hashMap.keySet()) {
                this.mUniformRawFloatMap.put(str, hashMap.get(str));
                this.mUniformFloatMap.put(str, hashMap.get(str));
            }
        }
        if (hashMap2 != null) {
            this.mUniformIntMap = new HashMap<>();
            for (String str2 : hashMap2.keySet()) {
                this.mUniformIntMap.put(str2, hashMap2.get(str2));
            }
        }
        if (hashMap3 != null) {
            this.mUniformBitmapMap = new HashMap<>();
            for (String str3 : hashMap3.keySet()) {
                this.mUniformBitmapMap.put(str3, hashMap3.get(str3));
            }
        }
    }

    public int getShaderTextId() {
        return this.mShaderTextId;
    }

    public String[] getUniformFloatNames() {
        HashMap<String, float[]> hashMap = this.mUniformFloatMap;
        return hashMap == null ? new String[0] : (String[]) hashMap.keySet().toArray(new String[0]);
    }

    public float[] getUniformFloats(String str) {
        HashMap<String, float[]> hashMap = this.mUniformFloatMap;
        return hashMap != null ? hashMap.get(str) : new float[0];
    }

    public String[] getUniformIntegerNames() {
        HashMap<String, int[]> hashMap = this.mUniformIntMap;
        return hashMap == null ? new String[0] : (String[]) hashMap.keySet().toArray(new String[0]);
    }

    public int[] getUniformInts(String str) {
        HashMap<String, int[]> hashMap = this.mUniformIntMap;
        return hashMap != null ? hashMap.get(str) : new int[0];
    }

    public String[] getUniformBitmapNames() {
        HashMap<String, Integer> hashMap = this.mUniformBitmapMap;
        return hashMap == null ? new String[0] : (String[]) hashMap.keySet().toArray(new String[0]);
    }

    public int getUniformBitmapId(String str) {
        HashMap<String, Integer> hashMap = this.mUniformBitmapMap;
        if (hashMap != null) {
            return hashMap.get(str).intValue();
        }
        return -1;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mShaderID, this.mShaderTextId, this.mUniformFloatMap, this.mUniformIntMap, this.mUniformBitmapMap);
    }

    public String toString() {
        return "SHADER DATA " + this.mShaderID;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        HashMap<String, float[]> hashMap = this.mUniformRawFloatMap;
        if (hashMap == null) {
            return;
        }
        for (String str : hashMap.keySet()) {
            float[] fArr = this.mUniformRawFloatMap.get(str);
            float[] fArr2 = null;
            for (int i = 0; i < fArr.length; i++) {
                if (Float.isNaN(fArr[i])) {
                    if (fArr2 == null) {
                        fArr2 = Arrays.copyOf(fArr, fArr.length);
                    }
                    fArr2[i] = remoteContext.getFloat(Utils.idFromNan(fArr[i]));
                }
            }
            HashMap<String, float[]> hashMap2 = this.mUniformFloatMap;
            if (fArr2 != null) {
                fArr = fArr2;
            }
            hashMap2.put(str, fArr);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (this.mUniformFloatMap == null) {
            return;
        }
        Iterator<String> it = this.mUniformRawFloatMap.keySet().iterator();
        while (it.hasNext()) {
            for (float f : this.mUniformRawFloatMap.get(it.next())) {
                if (Float.isNaN(f)) {
                    remoteContext.listensTo(Utils.idFromNan(f), this);
                }
            }
        }
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, HashMap<String, float[]> hashMap, HashMap<String, int[]> hashMap2, HashMap<String, Integer> hashMap3) {
        wireBuffer.start(45);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        int size = hashMap == null ? 0 : hashMap.size();
        int size2 = hashMap2 == null ? 0 : hashMap2.size();
        int size3 = hashMap3 == null ? 0 : hashMap3.size();
        wireBuffer.writeInt((size2 << 8) | size | (size3 << 16));
        if (size > 0) {
            for (String str : hashMap.keySet()) {
                wireBuffer.writeUTF8(str);
                float[] fArr = hashMap.get(str);
                wireBuffer.writeInt(fArr.length);
                for (float f : fArr) {
                    wireBuffer.writeFloat(f);
                }
            }
        }
        if (size2 > 0) {
            for (String str2 : hashMap2.keySet()) {
                wireBuffer.writeUTF8(str2);
                int[] iArr = hashMap2.get(str2);
                wireBuffer.writeInt(iArr.length);
                for (int i3 : iArr) {
                    wireBuffer.writeInt(i3);
                }
            }
        }
        if (size3 > 0) {
            for (String str3 : hashMap3.keySet()) {
                wireBuffer.writeUTF8(str3);
                wireBuffer.writeInt(hashMap3.get(str3).intValue());
            }
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        HashMap hashMap;
        HashMap hashMap2;
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        int readInt3 = wireBuffer.readInt();
        int i = readInt3 & 255;
        HashMap hashMap3 = null;
        if (i > 0) {
            HashMap hashMap4 = new HashMap();
            for (int i2 = 0; i2 < i; i2++) {
                String readUTF8 = wireBuffer.readUTF8();
                int readInt4 = wireBuffer.readInt();
                float[] fArr = new float[readInt4];
                for (int i3 = 0; i3 < readInt4; i3++) {
                    fArr[i3] = wireBuffer.readFloat();
                }
                hashMap4.put(readUTF8, fArr);
            }
            hashMap = hashMap4;
        } else {
            hashMap = null;
        }
        int i4 = (readInt3 >> 8) & 255;
        if (i4 > 0) {
            hashMap2 = new HashMap();
            for (int i5 = 0; i5 < i4; i5++) {
                String readUTF82 = wireBuffer.readUTF8();
                int readInt5 = wireBuffer.readInt();
                int[] iArr = new int[readInt5];
                for (int i6 = 0; i6 < readInt5; i6++) {
                    iArr[i6] = wireBuffer.readInt();
                }
                hashMap2.put(readUTF82, iArr);
            }
        } else {
            hashMap2 = null;
        }
        int i7 = (readInt3 >> 16) & 255;
        if (i7 > 0) {
            hashMap3 = new HashMap();
            for (int i8 = 0; i8 < i7; i8++) {
                hashMap3.put(wireBuffer.readUTF8(), Integer.valueOf(wireBuffer.readInt()));
            }
        }
        list.add(new ShaderData(readInt, readInt2, hashMap, hashMap2, hashMap3));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 45, CLASS_NAME).description("Shader").field(0, "shaderID", "id of shader").field(6, " floatSize", "number of float uniforms").field(6, " intSize", "number of int uniform").field(9, " intSize", "number of int uniform").field(5, "floatName", "name of float uniform").field(0, Contract.CompressedEvents.Field.LENGTH, Contract.CompressedEvents.Field.LENGTH).field(10, "VALUE", "float uniform (max 4)").field(5, "IntName", "id of shader text").field(0, Contract.CompressedEvents.Field.LENGTH, "length of uniform").field(11, "VALUE", "int uniform (max 4)").field(5, "bitmapName", "name of bitmap").field(0, "VALUE", "id of bitmap");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        if (this.mShaderValid) {
            remoteContext.loadShader(this.mShaderID, this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    public void enable(boolean z) {
        this.mShaderValid = z;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("shaderTextId", Integer.valueOf(this.mShaderTextId)).add("shaderID", Integer.valueOf(this.mShaderID)).add("uniformRawFloatMap", this.mUniformRawFloatMap).add("uniformFloatMap", this.mUniformFloatMap).add("uniformBitmapMap", this.mUniformBitmapMap);
    }
}
