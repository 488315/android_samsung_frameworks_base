package com.android.framework.protobuf.nano;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
class FieldData implements Cloneable {
    private Extension<?, ?> cachedExtension;
    private List<UnknownFieldData> unknownFieldData;
    private Object value;

    /* JADX WARN: Multi-variable type inference failed */
    <T> FieldData(Extension<?, T> extension, T newValue) {
        this.cachedExtension = extension;
        this.value = newValue;
    }

    FieldData() {
        this.unknownFieldData = new ArrayList();
    }

    void addUnknownField(UnknownFieldData unknownField) {
        this.unknownFieldData.add(unknownField);
    }

    UnknownFieldData getUnknownField(int index) {
        if (this.unknownFieldData != null && index < this.unknownFieldData.size()) {
            return this.unknownFieldData.get(index);
        }
        return null;
    }

    int getUnknownFieldSize() {
        if (this.unknownFieldData == null) {
            return 0;
        }
        return this.unknownFieldData.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T> T getValue(Extension<?, T> extension) {
        if (this.value != null) {
            if (this.cachedExtension != extension) {
                throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
            }
        } else {
            this.cachedExtension = extension;
            this.value = extension.getValueFrom(this.unknownFieldData);
            this.unknownFieldData = null;
        }
        return (T) this.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T> void setValue(Extension<?, T> extension, T newValue) {
        this.cachedExtension = extension;
        this.value = newValue;
        this.unknownFieldData = null;
    }

    int computeSerializedSize() {
        int size = 0;
        if (this.value != null) {
            int size2 = this.cachedExtension.computeSerializedSize(this.value);
            return size2;
        }
        for (UnknownFieldData unknownField : this.unknownFieldData) {
            size += unknownField.computeSerializedSize();
        }
        return size;
    }

    void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.value != null) {
            this.cachedExtension.writeTo(this.value, output);
            return;
        }
        for (UnknownFieldData unknownField : this.unknownFieldData) {
            unknownField.writeTo(output);
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FieldData)) {
            return false;
        }
        FieldData other = (FieldData) o;
        if (this.value != null && other.value != null) {
            if (this.cachedExtension != other.cachedExtension) {
                return false;
            }
            if (!this.cachedExtension.clazz.isArray()) {
                return this.value.equals(other.value);
            }
            if (this.value instanceof byte[]) {
                return Arrays.equals((byte[]) this.value, (byte[]) other.value);
            }
            if (this.value instanceof int[]) {
                return Arrays.equals((int[]) this.value, (int[]) other.value);
            }
            if (this.value instanceof long[]) {
                return Arrays.equals((long[]) this.value, (long[]) other.value);
            }
            if (this.value instanceof float[]) {
                return Arrays.equals((float[]) this.value, (float[]) other.value);
            }
            if (this.value instanceof double[]) {
                return Arrays.equals((double[]) this.value, (double[]) other.value);
            }
            if (this.value instanceof boolean[]) {
                return Arrays.equals((boolean[]) this.value, (boolean[]) other.value);
            }
            return Arrays.deepEquals((Object[]) this.value, (Object[]) other.value);
        }
        if (this.unknownFieldData != null && other.unknownFieldData != null) {
            return this.unknownFieldData.equals(other.unknownFieldData);
        }
        try {
            return Arrays.equals(toByteArray(), other.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public int hashCode() {
        try {
            int result = (17 * 31) + Arrays.hashCode(toByteArray());
            return result;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] toByteArray() throws IOException {
        byte[] result = new byte[computeSerializedSize()];
        CodedOutputByteBufferNano output = CodedOutputByteBufferNano.newInstance(result);
        writeTo(output);
        return result;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final FieldData m7420clone() {
        FieldData clone = new FieldData();
        try {
            clone.cachedExtension = this.cachedExtension;
            if (this.unknownFieldData == null) {
                clone.unknownFieldData = null;
            } else {
                clone.unknownFieldData.addAll(this.unknownFieldData);
            }
            if (this.value != null) {
                if (this.value instanceof MessageNano) {
                    clone.value = ((MessageNano) this.value).mo7418clone();
                } else if (this.value instanceof byte[]) {
                    clone.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] valueArray = (byte[][]) this.value;
                    byte[][] cloneArray = new byte[valueArray.length][];
                    clone.value = cloneArray;
                    for (int i = 0; i < valueArray.length; i++) {
                        cloneArray[i] = (byte[]) valueArray[i].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    clone.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    clone.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    clone.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    clone.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    clone.value = ((double[]) this.value).clone();
                } else if (this.value instanceof MessageNano[]) {
                    MessageNano[] valueArray2 = (MessageNano[]) this.value;
                    MessageNano[] cloneArray2 = new MessageNano[valueArray2.length];
                    clone.value = cloneArray2;
                    for (int i2 = 0; i2 < valueArray2.length; i2++) {
                        cloneArray2[i2] = valueArray2[i2].mo7418clone();
                    }
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
