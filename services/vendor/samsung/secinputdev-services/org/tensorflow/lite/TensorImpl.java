package org.tensorflow.lite;

import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

final class TensorImpl implements Tensor {
    private final DataType dtype;
    private long nativeHandle;
    private final Tensor.QuantizationParams quantizationParamsCopy;
    private int[] shapeCopy;
    private final int[] shapeSignatureCopy;

    private static native ByteBuffer buffer(long handle);

    private static native long create(long interpreterHandle, int tensorIndex, int subgraphIndex);

    private static native long createSignatureInputTensor(
            long signatureRunnerHandle, String inputName);

    private static native long createSignatureOutputTensor(
            long signatureRunnerHandle, String outputName);

    private static native void delete(long handle);

    private static native int dtype(long handle);

    private static native boolean hasDelegateBufferHandle(long handle);

    private static native int index(long handle);

    private static native String name(long handle);

    private static native int numBytes(long handle);

    private static native float quantizationScale(long handle);

    private static native int quantizationZeroPoint(long handle);

    private static native void readMultiDimensionalArray(long handle, Object dst);

    private static native int[] shape(long handle);

    private static native int[] shapeSignature(long handle);

    private static native void writeDirectBuffer(long handle, Buffer src);

    private static native void writeMultiDimensionalArray(long handle, Object src);

    private static native void writeScalar(long handle, Object src);

    static TensorImpl fromIndex(long nativeInterpreterHandle, int tensorIndex) {
        return new TensorImpl(create(nativeInterpreterHandle, tensorIndex, 0));
    }

    static TensorImpl fromSignatureInput(long signatureRunnerHandle, String inputName) {
        return new TensorImpl(createSignatureInputTensor(signatureRunnerHandle, inputName));
    }

    static TensorImpl fromSignatureOutput(long signatureRunnerHandle, String outputName) {
        return new TensorImpl(createSignatureOutputTensor(signatureRunnerHandle, outputName));
    }

    void close() {
        delete(this.nativeHandle);
        this.nativeHandle = 0L;
    }

    @Override // org.tensorflow.lite.Tensor
    public DataType dataType() {
        return this.dtype;
    }

    @Override // org.tensorflow.lite.Tensor
    public int numDimensions() {
        return this.shapeCopy.length;
    }

    @Override // org.tensorflow.lite.Tensor
    public int numBytes() {
        return numBytes(this.nativeHandle);
    }

    @Override // org.tensorflow.lite.Tensor
    public int numElements() {
        return computeNumElements(this.shapeCopy);
    }

    @Override // org.tensorflow.lite.Tensor
    public int[] shape() {
        return this.shapeCopy;
    }

    @Override // org.tensorflow.lite.Tensor
    public int[] shapeSignature() {
        return this.shapeSignatureCopy;
    }

    @Override // org.tensorflow.lite.Tensor
    public int index() {
        return index(this.nativeHandle);
    }

    @Override // org.tensorflow.lite.Tensor
    public String name() {
        return name(this.nativeHandle);
    }

    @Override // org.tensorflow.lite.Tensor
    public Tensor.QuantizationParams quantizationParams() {
        return this.quantizationParamsCopy;
    }

    @Override // org.tensorflow.lite.Tensor
    public ByteBuffer asReadOnlyBuffer() {
        return buffer().asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    }

    void setTo(Object src) {
        if (src == null) {
            if (hasDelegateBufferHandle(this.nativeHandle)) {
                return;
            } else {
                throw new IllegalArgumentException(
                        "Null inputs are allowed only if the Tensor is bound to a buffer handle.");
            }
        }
        throwIfTypeIsIncompatible(src);
        throwIfSrcShapeIsIncompatible(src);
        if (isBuffer(src)) {
            setTo((Buffer) src);
            return;
        }
        if (this.dtype == DataType.STRING && this.shapeCopy.length == 0) {
            writeScalar(this.nativeHandle, src);
        } else if (src.getClass().isArray()) {
            writeMultiDimensionalArray(this.nativeHandle, src);
        } else {
            writeScalar(this.nativeHandle, src);
        }
    }

    private void setTo(Buffer src) {
        if (src instanceof ByteBuffer) {
            ByteBuffer srcBuffer = (ByteBuffer) src;
            if (srcBuffer.isDirect() && srcBuffer.order() == ByteOrder.nativeOrder()) {
                writeDirectBuffer(this.nativeHandle, src);
                return;
            } else {
                buffer().put(srcBuffer);
                return;
            }
        }
        if (src instanceof LongBuffer) {
            LongBuffer srcBuffer2 = (LongBuffer) src;
            if (srcBuffer2.isDirect() && srcBuffer2.order() == ByteOrder.nativeOrder()) {
                writeDirectBuffer(this.nativeHandle, src);
                return;
            } else {
                buffer().asLongBuffer().put(srcBuffer2);
                return;
            }
        }
        if (src instanceof FloatBuffer) {
            FloatBuffer srcBuffer3 = (FloatBuffer) src;
            if (srcBuffer3.isDirect() && srcBuffer3.order() == ByteOrder.nativeOrder()) {
                writeDirectBuffer(this.nativeHandle, src);
                return;
            } else {
                buffer().asFloatBuffer().put(srcBuffer3);
                return;
            }
        }
        if (src instanceof IntBuffer) {
            IntBuffer srcBuffer4 = (IntBuffer) src;
            if (srcBuffer4.isDirect() && srcBuffer4.order() == ByteOrder.nativeOrder()) {
                writeDirectBuffer(this.nativeHandle, src);
                return;
            } else {
                buffer().asIntBuffer().put(srcBuffer4);
                return;
            }
        }
        if (src instanceof ShortBuffer) {
            ShortBuffer srcBuffer5 = (ShortBuffer) src;
            if (srcBuffer5.isDirect() && srcBuffer5.order() == ByteOrder.nativeOrder()) {
                writeDirectBuffer(this.nativeHandle, src);
                return;
            } else {
                buffer().asShortBuffer().put(srcBuffer5);
                return;
            }
        }
        throw new IllegalArgumentException("Unexpected input buffer type: " + src);
    }

    Object copyTo(Object dst) {
        if (dst == null) {
            if (hasDelegateBufferHandle(this.nativeHandle)) {
                return dst;
            }
            throw new IllegalArgumentException(
                    "Null outputs are allowed only if the Tensor is bound to a buffer handle.");
        }
        throwIfTypeIsIncompatible(dst);
        throwIfDstShapeIsIncompatible(dst);
        if (isBuffer(dst)) {
            copyTo((Buffer) dst);
        } else {
            readMultiDimensionalArray(this.nativeHandle, dst);
        }
        return dst;
    }

    private void copyTo(Buffer dst) {
        if (dst instanceof ByteBuffer) {
            ((ByteBuffer) dst).put(buffer());
            return;
        }
        if (dst instanceof FloatBuffer) {
            ((FloatBuffer) dst).put(buffer().asFloatBuffer());
            return;
        }
        if (dst instanceof LongBuffer) {
            ((LongBuffer) dst).put(buffer().asLongBuffer());
        } else if (dst instanceof IntBuffer) {
            ((IntBuffer) dst).put(buffer().asIntBuffer());
        } else {
            if (dst instanceof ShortBuffer) {
                ((ShortBuffer) dst).put(buffer().asShortBuffer());
                return;
            }
            throw new IllegalArgumentException("Unexpected output buffer type: " + dst);
        }
    }

    int[] getInputShapeIfDifferent(Object input) {
        if (input == null || isBuffer(input)) {
            return null;
        }
        throwIfTypeIsIncompatible(input);
        int[] inputShape = computeShapeOf(input);
        if (Arrays.equals(this.shapeCopy, inputShape)) {
            return null;
        }
        return inputShape;
    }

    void refreshShape() {
        this.shapeCopy = shape(this.nativeHandle);
    }

    DataType dataTypeOf(Object o) {
        if (o != null) {
            Class<?> c = o.getClass();
            if (c.isArray()) {
                while (c.isArray()) {
                    c = c.getComponentType();
                }
                if (Float.TYPE.equals(c)) {
                    return DataType.FLOAT32;
                }
                if (Integer.TYPE.equals(c)) {
                    return DataType.INT32;
                }
                if (Short.TYPE.equals(c)) {
                    return DataType.INT16;
                }
                if (Byte.TYPE.equals(c)) {
                    if (this.dtype == DataType.STRING) {
                        return DataType.STRING;
                    }
                    return DataType.UINT8;
                }
                if (Long.TYPE.equals(c)) {
                    return DataType.INT64;
                }
                if (Boolean.TYPE.equals(c)) {
                    return DataType.BOOL;
                }
                if (String.class.equals(c)) {
                    return DataType.STRING;
                }
            } else {
                if (Float.class.equals(c) || (o instanceof FloatBuffer)) {
                    return DataType.FLOAT32;
                }
                if (Integer.class.equals(c) || (o instanceof IntBuffer)) {
                    return DataType.INT32;
                }
                if (Short.class.equals(c) || (o instanceof ShortBuffer)) {
                    return DataType.INT16;
                }
                if (Byte.class.equals(c)) {
                    return DataType.UINT8;
                }
                if (Long.class.equals(c) || (o instanceof LongBuffer)) {
                    return DataType.INT64;
                }
                if (Boolean.class.equals(c)) {
                    return DataType.BOOL;
                }
                if (String.class.equals(c)) {
                    return DataType.STRING;
                }
            }
        }
        throw new IllegalArgumentException(
                "DataType error: cannot resolve DataType of " + o.getClass().getName());
    }

    int[] computeShapeOf(Object o) {
        int size = computeNumDimensions(o);
        if (this.dtype == DataType.STRING) {
            Class<?> c = o.getClass();
            if (c.isArray()) {
                while (c.isArray()) {
                    c = c.getComponentType();
                }
                if (Byte.TYPE.equals(c)) {
                    size--;
                }
            }
        }
        int[] dimensions = new int[size];
        fillShape(o, 0, dimensions);
        return dimensions;
    }

    static int computeNumElements(int[] shape) {
        int n = 1;
        for (int i : shape) {
            n *= i;
        }
        return n;
    }

    static int computeNumDimensions(Object o) {
        if (o == null || !o.getClass().isArray()) {
            return 0;
        }
        if (Array.getLength(o) == 0) {
            throw new IllegalArgumentException("Array lengths cannot be 0.");
        }
        return computeNumDimensions(Array.get(o, 0)) + 1;
    }

    static void fillShape(Object o, int dim, int[] shape) {
        if (shape == null || dim == shape.length) {
            return;
        }
        int len = Array.getLength(o);
        if (shape[dim] == 0) {
            shape[dim] = len;
        } else if (shape[dim] != len) {
            throw new IllegalArgumentException(
                    String.format(
                            "Mismatched lengths (%d and %d) in dimension %d",
                            Integer.valueOf(shape[dim]),
                            Integer.valueOf(len),
                            Integer.valueOf(dim)));
        }
        int nextDim = dim + 1;
        if (nextDim == shape.length) {
            return;
        }
        for (int i = 0; i < len; i++) {
            fillShape(Array.get(o, i), nextDim, shape);
        }
    }

    private void throwIfTypeIsIncompatible(Object o) {
        DataType oType;
        if (isByteBuffer(o)
                || (oType = dataTypeOf(o)) == this.dtype
                || DataTypeUtils.toStringName(oType)
                        .equals(DataTypeUtils.toStringName(this.dtype))) {
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot convert between a TensorFlowLite tensor with type %s and a Java"
                                + " object of type %s (which is compatible with the TensorFlowLite"
                                + " type %s).",
                            this.dtype, o.getClass().getName(), oType));
        }
    }

    private void throwIfSrcShapeIsIncompatible(Object src) {
        if (isBuffer(src)) {
            Buffer srcBuffer = (Buffer) src;
            int bytes = numBytes();
            int srcBytes =
                    isByteBuffer(src)
                            ? srcBuffer.capacity()
                            : srcBuffer.capacity() * this.dtype.byteSize();
            if (bytes != srcBytes) {
                throw new IllegalArgumentException(
                        String.format(
                                "Cannot copy to a TensorFlowLite tensor (%s) with %d bytes from a"
                                        + " Java Buffer with %d bytes.",
                                name(), Integer.valueOf(bytes), Integer.valueOf(srcBytes)));
            }
            return;
        }
        int[] srcShape = computeShapeOf(src);
        if (!Arrays.equals(srcShape, this.shapeCopy)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot copy to a TensorFlowLite tensor (%s) with shape %s from a Java"
                                    + " object with shape %s.",
                            name(), Arrays.toString(this.shapeCopy), Arrays.toString(srcShape)));
        }
    }

    private void throwIfDstShapeIsIncompatible(Object dst) {
        if (isBuffer(dst)) {
            Buffer dstBuffer = (Buffer) dst;
            int bytes = numBytes();
            int dstBytes =
                    isByteBuffer(dst)
                            ? dstBuffer.capacity()
                            : dstBuffer.capacity() * this.dtype.byteSize();
            if (bytes > dstBytes) {
                throw new IllegalArgumentException(
                        String.format(
                                "Cannot copy from a TensorFlowLite tensor (%s) with %d bytes to a"
                                        + " Java Buffer with %d bytes.",
                                name(), Integer.valueOf(bytes), Integer.valueOf(dstBytes)));
            }
            return;
        }
        int[] dstShape = computeShapeOf(dst);
        if (!Arrays.equals(dstShape, this.shapeCopy)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot copy from a TensorFlowLite tensor (%s) with shape %s to a Java"
                                    + " object with shape %s.",
                            name(), Arrays.toString(this.shapeCopy), Arrays.toString(dstShape)));
        }
    }

    private static boolean isBuffer(Object o) {
        return o instanceof Buffer;
    }

    private static boolean isByteBuffer(Object o) {
        return o instanceof ByteBuffer;
    }

    private TensorImpl(long nativeHandle) {
        this.nativeHandle = nativeHandle;
        this.dtype = DataTypeUtils.fromC(dtype(nativeHandle));
        this.shapeCopy = shape(nativeHandle);
        this.shapeSignatureCopy = shapeSignature(nativeHandle);
        this.quantizationParamsCopy =
                new Tensor.QuantizationParams(
                        quantizationScale(nativeHandle), quantizationZeroPoint(nativeHandle));
    }

    private ByteBuffer buffer() {
        return buffer(this.nativeHandle).order(ByteOrder.nativeOrder());
    }
}
