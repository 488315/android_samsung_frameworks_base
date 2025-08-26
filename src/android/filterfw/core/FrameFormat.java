package android.filterfw.core;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.widget.SemRemoteViewsValueAnimation;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes.dex */
public class FrameFormat {
    public static final int BYTES_PER_SAMPLE_UNSPECIFIED = 1;
    protected static final int SIZE_UNKNOWN = -1;
    public static final int SIZE_UNSPECIFIED = 0;
    public static final int TARGET_GPU = 3;
    public static final int TARGET_NATIVE = 2;
    public static final int TARGET_RS = 5;
    public static final int TARGET_SIMPLE = 1;
    public static final int TARGET_UNSPECIFIED = 0;
    public static final int TARGET_VERTEXBUFFER = 4;
    public static final int TYPE_BIT = 1;
    public static final int TYPE_BYTE = 2;
    public static final int TYPE_DOUBLE = 6;
    public static final int TYPE_FLOAT = 5;
    public static final int TYPE_INT16 = 3;
    public static final int TYPE_INT32 = 4;
    public static final int TYPE_OBJECT = 8;
    public static final int TYPE_POINTER = 7;
    public static final int TYPE_UNSPECIFIED = 0;
    protected int mBaseType;
    protected int mBytesPerSample;
    protected int[] mDimensions;
    protected KeyValueMap mMetaData;
    protected Class mObjectClass;
    protected int mSize;
    protected int mTarget;

    public static int bytesPerSampleOf(int i) {
        if (i == 3) {
            return 2;
        }
        if (i != 4 && i != 5) {
            if (i == 6) {
                return 8;
            }
            if (i != 7) {
                return 1;
            }
        }
        return 4;
    }

    protected FrameFormat() {
        this.mBaseType = 0;
        this.mBytesPerSample = 1;
        this.mSize = -1;
        this.mTarget = 0;
    }

    public FrameFormat(int i, int i2) {
        this.mBytesPerSample = 1;
        this.mSize = -1;
        this.mBaseType = i;
        this.mTarget = i2;
        initDefaults();
    }

    public static FrameFormat unspecified() {
        return new FrameFormat(0, 0);
    }

    public int getBaseType() {
        return this.mBaseType;
    }

    public boolean isBinaryDataType() {
        int i = this.mBaseType;
        return i >= 1 && i <= 6;
    }

    public int getBytesPerSample() {
        return this.mBytesPerSample;
    }

    public int getValuesPerSample() {
        return this.mBytesPerSample / bytesPerSampleOf(this.mBaseType);
    }

    public int getTarget() {
        return this.mTarget;
    }

    public int[] getDimensions() {
        return this.mDimensions;
    }

    public int getDimension(int i) {
        return this.mDimensions[i];
    }

    public int getDimensionCount() {
        int[] iArr = this.mDimensions;
        if (iArr == null) {
            return 0;
        }
        return iArr.length;
    }

    public boolean hasMetaKey(String str) {
        KeyValueMap keyValueMap = this.mMetaData;
        if (keyValueMap != null) {
            return keyValueMap.containsKey(str);
        }
        return false;
    }

    public boolean hasMetaKey(String str, Class cls) {
        KeyValueMap keyValueMap = this.mMetaData;
        if (keyValueMap == null || !keyValueMap.containsKey(str)) {
            return false;
        }
        if (cls.isAssignableFrom(this.mMetaData.get(str).getClass())) {
            return true;
        }
        throw new RuntimeException("FrameFormat meta-key '" + str + "' is of type " + this.mMetaData.get(str).getClass() + " but expected to be of type " + cls + "!");
    }

    public Object getMetaValue(String str) {
        KeyValueMap keyValueMap = this.mMetaData;
        if (keyValueMap != null) {
            return keyValueMap.get(str);
        }
        return null;
    }

    public int getNumberOfDimensions() {
        int[] iArr = this.mDimensions;
        if (iArr != null) {
            return iArr.length;
        }
        return 0;
    }

    public int getLength() {
        int[] iArr = this.mDimensions;
        if (iArr == null || iArr.length < 1) {
            return -1;
        }
        return iArr[0];
    }

    public int getWidth() {
        return getLength();
    }

    public int getHeight() {
        int[] iArr = this.mDimensions;
        if (iArr == null || iArr.length < 2) {
            return -1;
        }
        return iArr[1];
    }

    public int getDepth() {
        int[] iArr = this.mDimensions;
        if (iArr == null || iArr.length < 3) {
            return -1;
        }
        return iArr[2];
    }

    public int getSize() {
        if (this.mSize == -1) {
            this.mSize = calcSize(this.mDimensions);
        }
        return this.mSize;
    }

    public Class getObjectClass() {
        return this.mObjectClass;
    }

    public MutableFrameFormat mutableCopy() {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat();
        mutableFrameFormat.setBaseType(getBaseType());
        mutableFrameFormat.setTarget(getTarget());
        mutableFrameFormat.setBytesPerSample(getBytesPerSample());
        mutableFrameFormat.setDimensions(getDimensions());
        mutableFrameFormat.setObjectClass(getObjectClass());
        KeyValueMap keyValueMap = this.mMetaData;
        mutableFrameFormat.mMetaData = keyValueMap == null ? null : (KeyValueMap) keyValueMap.clone();
        return mutableFrameFormat;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FrameFormat)) {
            return false;
        }
        FrameFormat frameFormat = (FrameFormat) obj;
        return frameFormat.mBaseType == this.mBaseType && frameFormat.mTarget == this.mTarget && frameFormat.mBytesPerSample == this.mBytesPerSample && Arrays.equals(frameFormat.mDimensions, this.mDimensions) && frameFormat.mMetaData.equals(this.mMetaData);
    }

    public int hashCode() {
        return getSize() ^ ((this.mBaseType ^ 4211) ^ this.mBytesPerSample);
    }

    public boolean isCompatibleWith(FrameFormat frameFormat) {
        if (frameFormat.getBaseType() != 0 && getBaseType() != frameFormat.getBaseType()) {
            return false;
        }
        if (frameFormat.getTarget() != 0 && getTarget() != frameFormat.getTarget()) {
            return false;
        }
        if (frameFormat.getBytesPerSample() != 1 && getBytesPerSample() != frameFormat.getBytesPerSample()) {
            return false;
        }
        if (frameFormat.getDimensionCount() > 0 && getDimensionCount() != frameFormat.getDimensionCount()) {
            return false;
        }
        for (int i = 0; i < frameFormat.getDimensionCount(); i++) {
            int dimension = frameFormat.getDimension(i);
            if (dimension != 0 && getDimension(i) != dimension) {
                return false;
            }
        }
        if (frameFormat.getObjectClass() != null && (getObjectClass() == null || !frameFormat.getObjectClass().isAssignableFrom(getObjectClass()))) {
            return false;
        }
        KeyValueMap keyValueMap = frameFormat.mMetaData;
        if (keyValueMap != null) {
            for (String str : keyValueMap.keySet()) {
                KeyValueMap keyValueMap2 = this.mMetaData;
                if (keyValueMap2 == null || !keyValueMap2.containsKey(str) || !this.mMetaData.get(str).equals(frameFormat.mMetaData.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean mayBeCompatibleWith(FrameFormat frameFormat) {
        if (frameFormat.getBaseType() != 0 && getBaseType() != 0 && getBaseType() != frameFormat.getBaseType()) {
            return false;
        }
        if (frameFormat.getTarget() != 0 && getTarget() != 0 && getTarget() != frameFormat.getTarget()) {
            return false;
        }
        if (frameFormat.getBytesPerSample() != 1 && getBytesPerSample() != 1 && getBytesPerSample() != frameFormat.getBytesPerSample()) {
            return false;
        }
        if (frameFormat.getDimensionCount() > 0 && getDimensionCount() > 0 && getDimensionCount() != frameFormat.getDimensionCount()) {
            return false;
        }
        for (int i = 0; i < frameFormat.getDimensionCount(); i++) {
            int dimension = frameFormat.getDimension(i);
            if (dimension != 0 && getDimension(i) != 0 && getDimension(i) != dimension) {
                return false;
            }
        }
        if (frameFormat.getObjectClass() != null && getObjectClass() != null && !frameFormat.getObjectClass().isAssignableFrom(getObjectClass())) {
            return false;
        }
        KeyValueMap keyValueMap = frameFormat.mMetaData;
        if (keyValueMap != null && this.mMetaData != null) {
            for (String str : keyValueMap.keySet()) {
                if (this.mMetaData.containsKey(str) && !this.mMetaData.get(str).equals(frameFormat.mMetaData.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String dimensionsToString(int[] iArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (iArr != null) {
            int length = iArr.length;
            for (int i = 0; i < length; i++) {
                if (iArr[i] == 0) {
                    stringBuffer.append("[]");
                } else {
                    stringBuffer.append(NavigationBarInflaterView.SIZE_MOD_START + String.valueOf(iArr[i]) + NavigationBarInflaterView.SIZE_MOD_END);
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String baseTypeToString(int i) {
        switch (i) {
            case 0:
                return "unspecified";
            case 1:
                return "bit";
            case 2:
                return "byte";
            case 3:
            case 4:
                return "int";
            case 5:
                return SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT;
            case 6:
                return "double";
            case 7:
                return "pointer";
            case 8:
                return "object";
            default:
                return "unknown";
        }
    }

    public static String targetToString(int i) {
        if (i == 0) {
            return "unspecified";
        }
        if (i == 1) {
            return "simple";
        }
        if (i == 2) {
            return "native";
        }
        if (i == 3) {
            return "gpu";
        }
        if (i == 4) {
            return "vbo";
        }
        if (i == 5) {
            return "renderscript";
        }
        return "unknown";
    }

    public static String metaDataToString(KeyValueMap keyValueMap) {
        if (keyValueMap == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("{ ");
        for (Map.Entry<String, Object> entry : keyValueMap.entrySet()) {
            stringBuffer.append(entry.getKey() + ": " + entry.getValue() + " ");
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static int readTargetString(String str) {
        if (str.equalsIgnoreCase("CPU") || str.equalsIgnoreCase("NATIVE")) {
            return 2;
        }
        if (str.equalsIgnoreCase("GPU")) {
            return 3;
        }
        if (str.equalsIgnoreCase("SIMPLE")) {
            return 1;
        }
        if (str.equalsIgnoreCase("VERTEXBUFFER")) {
            return 4;
        }
        if (str.equalsIgnoreCase("UNSPECIFIED")) {
            return 0;
        }
        throw new RuntimeException("Unknown target type '" + str + "'!");
    }

    public String toString() {
        String str;
        int valuesPerSample = getValuesPerSample();
        String str2 = "";
        String strValueOf = valuesPerSample == 1 ? "" : String.valueOf(valuesPerSample);
        if (this.mTarget == 0) {
            str = "";
        } else {
            str = targetToString(this.mTarget) + " ";
        }
        if (this.mObjectClass != null) {
            str2 = " class(" + this.mObjectClass.getSimpleName() + ") ";
        }
        return str + baseTypeToString(this.mBaseType) + strValueOf + dimensionsToString(this.mDimensions) + str2 + metaDataToString(this.mMetaData);
    }

    private void initDefaults() {
        this.mBytesPerSample = bytesPerSampleOf(this.mBaseType);
    }

    int calcSize(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return 0;
        }
        int bytesPerSample = getBytesPerSample();
        for (int i : iArr) {
            bytesPerSample *= i;
        }
        return bytesPerSample;
    }

    boolean isReplaceableBy(FrameFormat frameFormat) {
        return this.mTarget == frameFormat.mTarget && getSize() == frameFormat.getSize() && Arrays.equals(frameFormat.mDimensions, this.mDimensions);
    }
}
