package android.graphics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class MeshSpecification {
    public static final int ALPHA_TYPE_OPAQUE = 1;
    public static final int ALPHA_TYPE_PREMULTIPLIED = 2;
    public static final int ALPHA_TYPE_UNKNOWN = 0;
    public static final int ALPHA_TYPE_UNPREMULTIPLIED = 3;
    public static final int TYPE_FLOAT = 0;
    public static final int TYPE_FLOAT2 = 1;
    public static final int TYPE_FLOAT3 = 2;
    public static final int TYPE_FLOAT4 = 3;
    public static final int TYPE_UBYTE4 = 4;
    long mNativeMeshSpec;

    @Retention(RetentionPolicy.SOURCE)
    private @interface AlphaType {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface Type {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native long nativeMake(Attribute[] attributeArr, int i, Varying[] varyingArr, String str, String str2);

    private static native long nativeMakeWithAlpha(Attribute[] attributeArr, int i, Varying[] varyingArr, String str, String str2, long j, int i2);

    private static native long nativeMakeWithCS(Attribute[] attributeArr, int i, Varying[] varyingArr, String str, String str2, long j);

    public static class Attribute {
        private final String mName;
        private final int mOffset;
        private final int mType;

        public Attribute(int i, int i2, String str) {
            this.mType = i;
            this.mOffset = i2;
            this.mName = str;
        }

        public int getType() {
            return this.mType;
        }

        public int getOffset() {
            return this.mOffset;
        }

        public String getName() {
            return this.mName;
        }

        public String toString() {
            return "Attribute{mType=" + this.mType + ", mOffset=" + this.mOffset + ", mName='" + this.mName + "'}";
        }
    }

    public static class Varying {
        private final String mName;
        private final int mType;

        public Varying(int i, String str) {
            this.mType = i;
            this.mName = str;
        }

        public int getType() {
            return this.mType;
        }

        public String getName() {
            return this.mName;
        }

        public String toString() {
            return "Varying{mType=" + this.mType + ", mName='" + this.mName + "'}";
        }
    }

    private static class MeshSpecificationHolder {
        public static final NativeAllocationRegistry MESH_SPECIFICATION_REGISTRY = NativeAllocationRegistry.createMalloced(MeshSpecification.class.getClassLoader(), MeshSpecification.nativeGetFinalizer());

        private MeshSpecificationHolder() {
        }
    }

    public static MeshSpecification make(Attribute[] attributeArr, int i, Varying[] varyingArr, String str, String str2) {
        long nativeMake = nativeMake(attributeArr, i, varyingArr, str, str2);
        if (nativeMake == 0) {
            throw new IllegalArgumentException("MeshSpecification construction failed");
        }
        return new MeshSpecification(nativeMake);
    }

    public static MeshSpecification make(Attribute[] attributeArr, int i, Varying[] varyingArr, String str, String str2, ColorSpace colorSpace) {
        long nativeMakeWithCS = nativeMakeWithCS(attributeArr, i, varyingArr, str, str2, colorSpace.getNativeInstance());
        if (nativeMakeWithCS == 0) {
            throw new IllegalArgumentException("MeshSpecification construction failed");
        }
        return new MeshSpecification(nativeMakeWithCS);
    }

    public static MeshSpecification make(Attribute[] attributeArr, int i, Varying[] varyingArr, String str, String str2, ColorSpace colorSpace, int i2) {
        long nativeMakeWithAlpha = nativeMakeWithAlpha(attributeArr, i, varyingArr, str, str2, colorSpace.getNativeInstance(), i2);
        if (nativeMakeWithAlpha == 0) {
            throw new IllegalArgumentException("MeshSpecification construction failed");
        }
        return new MeshSpecification(nativeMakeWithAlpha);
    }

    private MeshSpecification(long j) {
        this.mNativeMeshSpec = j;
        MeshSpecificationHolder.MESH_SPECIFICATION_REGISTRY.registerNativeAllocation(this, j);
    }
}
