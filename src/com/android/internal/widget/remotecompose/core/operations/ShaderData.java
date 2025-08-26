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

    public ShaderData(int i, int i2, HashMap<String, float[]> map, HashMap<String, int[]> map2, HashMap<String, Integer> map3) {
        this.mUniformRawFloatMap = null;
        this.mUniformFloatMap = null;
        this.mUniformBitmapMap = null;
        this.mShaderID = i;
        this.mShaderTextId = i2;
        if (map != null) {
            this.mUniformFloatMap = new HashMap<>();
            this.mUniformRawFloatMap = new HashMap<>();
            for (String str : map.keySet()) {
                this.mUniformRawFloatMap.put(str, map.get(str));
                this.mUniformFloatMap.put(str, map.get(str));
            }
        }
        if (map2 != null) {
            this.mUniformIntMap = new HashMap<>();
            for (String str2 : map2.keySet()) {
                this.mUniformIntMap.put(str2, map2.get(str2));
            }
        }
        if (map3 != null) {
            this.mUniformBitmapMap = new HashMap<>();
            for (String str3 : map3.keySet()) {
                this.mUniformBitmapMap.put(str3, map3.get(str3));
            }
        }
    }

    public int getShaderTextId() {
        return this.mShaderTextId;
    }

    public String[] getUniformFloatNames() {
        HashMap<String, float[]> map = this.mUniformFloatMap;
        return map == null ? new String[0] : (String[]) map.keySet().toArray(new String[0]);
    }

    public float[] getUniformFloats(String str) {
        HashMap<String, float[]> map = this.mUniformFloatMap;
        return map != null ? map.get(str) : new float[0];
    }

    public String[] getUniformIntegerNames() {
        HashMap<String, int[]> map = this.mUniformIntMap;
        return map == null ? new String[0] : (String[]) map.keySet().toArray(new String[0]);
    }

    public int[] getUniformInts(String str) {
        HashMap<String, int[]> map = this.mUniformIntMap;
        return map != null ? map.get(str) : new int[0];
    }

    public String[] getUniformBitmapNames() {
        HashMap<String, Integer> map = this.mUniformBitmapMap;
        return map == null ? new String[0] : (String[]) map.keySet().toArray(new String[0]);
    }

    public int getUniformBitmapId(String str) {
        HashMap<String, Integer> map = this.mUniformBitmapMap;
        if (map != null) {
            return map.get(str).intValue();
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
        HashMap<String, float[]> map = this.mUniformRawFloatMap;
        if (map == null) {
            return;
        }
        for (String str : map.keySet()) {
            float[] fArr = this.mUniformRawFloatMap.get(str);
            float[] fArrCopyOf = null;
            for (int i = 0; i < fArr.length; i++) {
                if (Float.isNaN(fArr[i])) {
                    if (fArrCopyOf == null) {
                        fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
                    }
                    fArrCopyOf[i] = remoteContext.getFloat(Utils.idFromNan(fArr[i]));
                }
            }
            HashMap<String, float[]> map2 = this.mUniformFloatMap;
            if (fArrCopyOf != null) {
                fArr = fArrCopyOf;
            }
            map2.put(str, fArr);
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

    public static void apply(WireBuffer wireBuffer, int i, int i2, HashMap<String, float[]> map, HashMap<String, int[]> map2, HashMap<String, Integer> map3) {
        wireBuffer.start(45);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        int size = map == null ? 0 : map.size();
        int size2 = map2 == null ? 0 : map2.size();
        int size3 = map3 == null ? 0 : map3.size();
        wireBuffer.writeInt((size2 << 8) | size | (size3 << 16));
        if (size > 0) {
            for (String str : map.keySet()) {
                wireBuffer.writeUTF8(str);
                float[] fArr = map.get(str);
                wireBuffer.writeInt(fArr.length);
                for (float f : fArr) {
                    wireBuffer.writeFloat(f);
                }
            }
        }
        if (size2 > 0) {
            for (String str2 : map2.keySet()) {
                wireBuffer.writeUTF8(str2);
                int[] iArr = map2.get(str2);
                wireBuffer.writeInt(iArr.length);
                for (int i3 : iArr) {
                    wireBuffer.writeInt(i3);
                }
            }
        }
        if (size3 > 0) {
            for (String str3 : map3.keySet()) {
                wireBuffer.writeUTF8(str3);
                wireBuffer.writeInt(map3.get(str3).intValue());
            }
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        HashMap map;
        HashMap map2;
        int i = wireBuffer.readInt();
        int i2 = wireBuffer.readInt();
        int i3 = wireBuffer.readInt();
        int i4 = i3 & 255;
        HashMap map3 = null;
        if (i4 > 0) {
            HashMap map4 = new HashMap();
            for (int i5 = 0; i5 < i4; i5++) {
                String utf8 = wireBuffer.readUTF8();
                int i6 = wireBuffer.readInt();
                float[] fArr = new float[i6];
                for (int i7 = 0; i7 < i6; i7++) {
                    fArr[i7] = wireBuffer.readFloat();
                }
                map4.put(utf8, fArr);
            }
            map = map4;
        } else {
            map = null;
        }
        int i8 = (i3 >> 8) & 255;
        if (i8 > 0) {
            map2 = new HashMap();
            for (int i9 = 0; i9 < i8; i9++) {
                String utf82 = wireBuffer.readUTF8();
                int i10 = wireBuffer.readInt();
                int[] iArr = new int[i10];
                for (int i11 = 0; i11 < i10; i11++) {
                    iArr[i11] = wireBuffer.readInt();
                }
                map2.put(utf82, iArr);
            }
        } else {
            map2 = null;
        }
        int i12 = (i3 >> 16) & 255;
        if (i12 > 0) {
            map3 = new HashMap();
            for (int i13 = 0; i13 < i12; i13++) {
                map3.put(wireBuffer.readUTF8(), Integer.valueOf(wireBuffer.readInt()));
            }
        }
        list.add(new ShaderData(i, i2, map, map2, map3));
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
