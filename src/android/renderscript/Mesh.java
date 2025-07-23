package android.renderscript;

import android.renderscript.Element;
import android.renderscript.Type;
import java.util.Vector;

@Deprecated
/* loaded from: classes3.dex */
public class Mesh extends BaseObj {
    Allocation[] mIndexBuffers;
    Primitive[] mPrimitives;
    Allocation[] mVertexBuffers;

    public enum Primitive {
        POINT(0),
        LINE(1),
        LINE_STRIP(2),
        TRIANGLE(3),
        TRIANGLE_STRIP(4),
        TRIANGLE_FAN(5);

        int mID;

        Primitive(int i) {
            this.mID = i;
        }
    }

    Mesh(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public int getVertexAllocationCount() {
        Allocation[] allocationArr = this.mVertexBuffers;
        if (allocationArr == null) {
            return 0;
        }
        return allocationArr.length;
    }

    public Allocation getVertexAllocation(int i) {
        return this.mVertexBuffers[i];
    }

    public int getPrimitiveCount() {
        Allocation[] allocationArr = this.mIndexBuffers;
        if (allocationArr == null) {
            return 0;
        }
        return allocationArr.length;
    }

    public Allocation getIndexSetAllocation(int i) {
        return this.mIndexBuffers[i];
    }

    public Primitive getPrimitive(int i) {
        return this.mPrimitives[i];
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        super.updateFromNative();
        int nMeshGetVertexBufferCount = this.mRS.nMeshGetVertexBufferCount(getID(this.mRS));
        int nMeshGetIndexCount = this.mRS.nMeshGetIndexCount(getID(this.mRS));
        long[] jArr = new long[nMeshGetVertexBufferCount];
        long[] jArr2 = new long[nMeshGetIndexCount];
        int[] iArr = new int[nMeshGetIndexCount];
        this.mRS.nMeshGetVertices(getID(this.mRS), jArr, nMeshGetVertexBufferCount);
        this.mRS.nMeshGetIndices(getID(this.mRS), jArr2, iArr, nMeshGetIndexCount);
        this.mVertexBuffers = new Allocation[nMeshGetVertexBufferCount];
        this.mIndexBuffers = new Allocation[nMeshGetIndexCount];
        this.mPrimitives = new Primitive[nMeshGetIndexCount];
        for (int i = 0; i < nMeshGetVertexBufferCount; i++) {
            if (jArr[i] != 0) {
                this.mVertexBuffers[i] = new Allocation(jArr[i], this.mRS, null, 1);
                this.mVertexBuffers[i].updateFromNative();
            }
        }
        for (int i2 = 0; i2 < nMeshGetIndexCount; i2++) {
            if (jArr2[i2] != 0) {
                this.mIndexBuffers[i2] = new Allocation(jArr2[i2], this.mRS, null, 1);
                this.mIndexBuffers[i2].updateFromNative();
            }
            this.mPrimitives[i2] = Primitive.values()[iArr[i2]];
        }
    }

    public static class Builder {
        RenderScript mRS;
        int mUsage;
        int mVertexTypeCount = 0;
        Entry[] mVertexTypes = new Entry[16];
        Vector mIndexTypes = new Vector();

        class Entry {
            Element e;
            Primitive prim;
            int size;
            Type t;
            int usage;

            Entry(Builder builder) {
            }
        }

        public Builder(RenderScript renderScript, int i) {
            this.mRS = renderScript;
            this.mUsage = i;
        }

        public int getCurrentVertexTypeIndex() {
            return this.mVertexTypeCount - 1;
        }

        public int getCurrentIndexSetIndex() {
            return this.mIndexTypes.size() - 1;
        }

        public Builder addVertexType(Type type) throws IllegalStateException {
            int i = this.mVertexTypeCount;
            Entry[] entryArr = this.mVertexTypes;
            if (i >= entryArr.length) {
                throw new IllegalStateException("Max vertex types exceeded.");
            }
            entryArr[i] = new Entry(this);
            this.mVertexTypes[this.mVertexTypeCount].t = type;
            this.mVertexTypes[this.mVertexTypeCount].e = null;
            this.mVertexTypeCount++;
            return this;
        }

        public Builder addVertexType(Element element, int i) throws IllegalStateException {
            int i2 = this.mVertexTypeCount;
            Entry[] entryArr = this.mVertexTypes;
            if (i2 >= entryArr.length) {
                throw new IllegalStateException("Max vertex types exceeded.");
            }
            entryArr[i2] = new Entry(this);
            this.mVertexTypes[this.mVertexTypeCount].t = null;
            this.mVertexTypes[this.mVertexTypeCount].e = element;
            this.mVertexTypes[this.mVertexTypeCount].size = i;
            this.mVertexTypeCount++;
            return this;
        }

        public Builder addIndexSetType(Type type, Primitive primitive) {
            Entry entry = new Entry(this);
            entry.t = type;
            entry.e = null;
            entry.size = 0;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public Builder addIndexSetType(Primitive primitive) {
            Entry entry = new Entry(this);
            entry.t = null;
            entry.e = null;
            entry.size = 0;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public Builder addIndexSetType(Element element, int i, Primitive primitive) {
            Entry entry = new Entry(this);
            entry.t = null;
            entry.e = element;
            entry.size = i;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        Type newType(Element element, int i) {
            Type.Builder builder = new Type.Builder(this.mRS, element);
            builder.setX(i);
            return builder.create();
        }

        public Mesh create() {
            Allocation createSized;
            Allocation createSized2;
            this.mRS.validate();
            long[] jArr = new long[this.mVertexTypeCount];
            long[] jArr2 = new long[this.mIndexTypes.size()];
            int[] iArr = new int[this.mIndexTypes.size()];
            Allocation[] allocationArr = new Allocation[this.mVertexTypeCount];
            Allocation[] allocationArr2 = new Allocation[this.mIndexTypes.size()];
            Primitive[] primitiveArr = new Primitive[this.mIndexTypes.size()];
            for (int i = 0; i < this.mVertexTypeCount; i++) {
                Entry entry = this.mVertexTypes[i];
                if (entry.t != null) {
                    createSized2 = Allocation.createTyped(this.mRS, entry.t, this.mUsage);
                } else if (entry.e != null) {
                    createSized2 = Allocation.createSized(this.mRS, entry.e, entry.size, this.mUsage);
                } else {
                    throw new IllegalStateException("Builder corrupt, no valid element in entry.");
                }
                allocationArr[i] = createSized2;
                jArr[i] = createSized2.getID(this.mRS);
            }
            for (int i2 = 0; i2 < this.mIndexTypes.size(); i2++) {
                Entry entry2 = (Entry) this.mIndexTypes.elementAt(i2);
                if (entry2.t != null) {
                    createSized = Allocation.createTyped(this.mRS, entry2.t, this.mUsage);
                } else if (entry2.e != null) {
                    createSized = Allocation.createSized(this.mRS, entry2.e, entry2.size, this.mUsage);
                } else {
                    throw new IllegalStateException("Builder corrupt, no valid element in entry.");
                }
                long id = createSized == null ? 0L : createSized.getID(this.mRS);
                allocationArr2[i2] = createSized;
                primitiveArr[i2] = entry2.prim;
                jArr2[i2] = id;
                iArr[i2] = entry2.prim.mID;
            }
            Mesh mesh = new Mesh(this.mRS.nMeshCreate(jArr, jArr2, iArr), this.mRS);
            mesh.mVertexBuffers = allocationArr;
            mesh.mIndexBuffers = allocationArr2;
            mesh.mPrimitives = primitiveArr;
            return mesh;
        }
    }

    public static class AllocationBuilder {
        RenderScript mRS;
        int mVertexTypeCount = 0;
        Entry[] mVertexTypes = new Entry[16];
        Vector mIndexTypes = new Vector();

        class Entry {
            Allocation a;
            Primitive prim;

            Entry(AllocationBuilder allocationBuilder) {
            }
        }

        public AllocationBuilder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public int getCurrentVertexTypeIndex() {
            return this.mVertexTypeCount - 1;
        }

        public int getCurrentIndexSetIndex() {
            return this.mIndexTypes.size() - 1;
        }

        public AllocationBuilder addVertexAllocation(Allocation allocation) throws IllegalStateException {
            int i = this.mVertexTypeCount;
            Entry[] entryArr = this.mVertexTypes;
            if (i >= entryArr.length) {
                throw new IllegalStateException("Max vertex types exceeded.");
            }
            entryArr[i] = new Entry(this);
            this.mVertexTypes[this.mVertexTypeCount].a = allocation;
            this.mVertexTypeCount++;
            return this;
        }

        public AllocationBuilder addIndexSetAllocation(Allocation allocation, Primitive primitive) {
            Entry entry = new Entry(this);
            entry.a = allocation;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public AllocationBuilder addIndexSetType(Primitive primitive) {
            Entry entry = new Entry(this);
            entry.a = null;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public Mesh create() {
            this.mRS.validate();
            long[] jArr = new long[this.mVertexTypeCount];
            long[] jArr2 = new long[this.mIndexTypes.size()];
            int[] iArr = new int[this.mIndexTypes.size()];
            Allocation[] allocationArr = new Allocation[this.mIndexTypes.size()];
            Primitive[] primitiveArr = new Primitive[this.mIndexTypes.size()];
            Allocation[] allocationArr2 = new Allocation[this.mVertexTypeCount];
            for (int i = 0; i < this.mVertexTypeCount; i++) {
                Entry entry = this.mVertexTypes[i];
                allocationArr2[i] = entry.a;
                jArr[i] = entry.a.getID(this.mRS);
            }
            for (int i2 = 0; i2 < this.mIndexTypes.size(); i2++) {
                Entry entry2 = (Entry) this.mIndexTypes.elementAt(i2);
                long id = entry2.a == null ? 0L : entry2.a.getID(this.mRS);
                allocationArr[i2] = entry2.a;
                primitiveArr[i2] = entry2.prim;
                jArr2[i2] = id;
                iArr[i2] = entry2.prim.mID;
            }
            Mesh mesh = new Mesh(this.mRS.nMeshCreate(jArr, jArr2, iArr), this.mRS);
            mesh.mVertexBuffers = allocationArr2;
            mesh.mIndexBuffers = allocationArr;
            mesh.mPrimitives = primitiveArr;
            return mesh;
        }
    }

    public static class TriangleMeshBuilder {
        public static final int COLOR = 1;
        public static final int NORMAL = 2;
        public static final int TEXTURE_0 = 256;
        Element mElement;
        int mFlags;
        RenderScript mRS;
        int mVtxSize;
        float mNX = 0.0f;
        float mNY = 0.0f;
        float mNZ = -1.0f;
        float mS0 = 0.0f;
        float mT0 = 0.0f;
        float mR = 1.0f;
        float mG = 1.0f;
        float mB = 1.0f;
        float mA = 1.0f;
        int mVtxCount = 0;
        int mMaxIndex = 0;
        int mIndexCount = 0;
        float[] mVtxData = new float[128];
        short[] mIndexData = new short[128];

        public TriangleMeshBuilder(RenderScript renderScript, int i, int i2) {
            this.mRS = renderScript;
            this.mVtxSize = i;
            this.mFlags = i2;
            if (i < 2 || i > 3) {
                throw new IllegalArgumentException("Vertex size out of range.");
            }
        }

        private void makeSpace(int i) {
            int i2 = this.mVtxCount + i;
            float[] fArr = this.mVtxData;
            if (i2 >= fArr.length) {
                float[] fArr2 = new float[fArr.length * 2];
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                this.mVtxData = fArr2;
            }
        }

        private void latch() {
            if ((this.mFlags & 1) != 0) {
                makeSpace(4);
                float[] fArr = this.mVtxData;
                int i = this.mVtxCount;
                int i2 = i + 1;
                this.mVtxCount = i2;
                fArr[i] = this.mR;
                int i3 = i + 2;
                this.mVtxCount = i3;
                fArr[i2] = this.mG;
                int i4 = i + 3;
                this.mVtxCount = i4;
                fArr[i3] = this.mB;
                this.mVtxCount = i + 4;
                fArr[i4] = this.mA;
            }
            if ((this.mFlags & 256) != 0) {
                makeSpace(2);
                float[] fArr2 = this.mVtxData;
                int i5 = this.mVtxCount;
                int i6 = i5 + 1;
                this.mVtxCount = i6;
                fArr2[i5] = this.mS0;
                this.mVtxCount = i5 + 2;
                fArr2[i6] = this.mT0;
            }
            if ((this.mFlags & 2) != 0) {
                makeSpace(4);
                float[] fArr3 = this.mVtxData;
                int i7 = this.mVtxCount;
                int i8 = i7 + 1;
                this.mVtxCount = i8;
                fArr3[i7] = this.mNX;
                int i9 = i7 + 2;
                this.mVtxCount = i9;
                fArr3[i8] = this.mNY;
                int i10 = i7 + 3;
                this.mVtxCount = i10;
                fArr3[i9] = this.mNZ;
                this.mVtxCount = i7 + 4;
                fArr3[i10] = 0.0f;
            }
            this.mMaxIndex++;
        }

        public TriangleMeshBuilder addVertex(float f, float f2) {
            if (this.mVtxSize != 2) {
                throw new IllegalStateException("add mistmatch with declared components.");
            }
            makeSpace(2);
            float[] fArr = this.mVtxData;
            int i = this.mVtxCount;
            int i2 = i + 1;
            this.mVtxCount = i2;
            fArr[i] = f;
            this.mVtxCount = i + 2;
            fArr[i2] = f2;
            latch();
            return this;
        }

        public TriangleMeshBuilder addVertex(float f, float f2, float f3) {
            if (this.mVtxSize != 3) {
                throw new IllegalStateException("add mistmatch with declared components.");
            }
            makeSpace(4);
            float[] fArr = this.mVtxData;
            int i = this.mVtxCount;
            int i2 = i + 1;
            this.mVtxCount = i2;
            fArr[i] = f;
            int i3 = i + 2;
            this.mVtxCount = i3;
            fArr[i2] = f2;
            int i4 = i + 3;
            this.mVtxCount = i4;
            fArr[i3] = f3;
            this.mVtxCount = i + 4;
            fArr[i4] = 1.0f;
            latch();
            return this;
        }

        public TriangleMeshBuilder setTexture(float f, float f2) {
            if ((this.mFlags & 256) == 0) {
                throw new IllegalStateException("add mistmatch with declared components.");
            }
            this.mS0 = f;
            this.mT0 = f2;
            return this;
        }

        public TriangleMeshBuilder setNormal(float f, float f2, float f3) {
            if ((this.mFlags & 2) == 0) {
                throw new IllegalStateException("add mistmatch with declared components.");
            }
            this.mNX = f;
            this.mNY = f2;
            this.mNZ = f3;
            return this;
        }

        public TriangleMeshBuilder setColor(float f, float f2, float f3, float f4) {
            if ((this.mFlags & 1) == 0) {
                throw new IllegalStateException("add mistmatch with declared components.");
            }
            this.mR = f;
            this.mG = f2;
            this.mB = f3;
            this.mA = f4;
            return this;
        }

        public TriangleMeshBuilder addTriangle(int i, int i2, int i3) {
            int i4 = this.mMaxIndex;
            if (i >= i4 || i < 0 || i2 >= i4 || i2 < 0 || i3 >= i4 || i3 < 0) {
                throw new IllegalStateException("Index provided greater than vertex count.");
            }
            int i5 = this.mIndexCount + 3;
            short[] sArr = this.mIndexData;
            if (i5 >= sArr.length) {
                short[] sArr2 = new short[sArr.length * 2];
                System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
                this.mIndexData = sArr2;
            }
            short[] sArr3 = this.mIndexData;
            int i6 = this.mIndexCount;
            int i7 = i6 + 1;
            this.mIndexCount = i7;
            sArr3[i6] = (short) i;
            int i8 = i6 + 2;
            this.mIndexCount = i8;
            sArr3[i7] = (short) i2;
            this.mIndexCount = i6 + 3;
            sArr3[i8] = (short) i3;
            return this;
        }

        public Mesh create(boolean z) {
            Element.Builder builder = new Element.Builder(this.mRS);
            builder.add(Element.createVector(this.mRS, Element.DataType.FLOAT_32, this.mVtxSize), "position");
            if ((this.mFlags & 1) != 0) {
                builder.add(Element.F32_4(this.mRS), "color");
            }
            if ((this.mFlags & 256) != 0) {
                builder.add(Element.F32_2(this.mRS), "texture0");
            }
            if ((this.mFlags & 2) != 0) {
                builder.add(Element.F32_3(this.mRS), "normal");
            }
            this.mElement = builder.create();
            Builder builder2 = new Builder(this.mRS, z ? 5 : 1);
            builder2.addVertexType(this.mElement, this.mMaxIndex);
            builder2.addIndexSetType(Element.U16(this.mRS), this.mIndexCount, Primitive.TRIANGLE);
            Mesh create = builder2.create();
            create.getVertexAllocation(0).copy1DRangeFromUnchecked(0, this.mMaxIndex, this.mVtxData);
            if (z) {
                create.getVertexAllocation(0).syncAll(1);
            }
            create.getIndexSetAllocation(0).copy1DRangeFromUnchecked(0, this.mIndexCount, this.mIndexData);
            if (z) {
                create.getIndexSetAllocation(0).syncAll(1);
            }
            return create;
        }
    }
}
