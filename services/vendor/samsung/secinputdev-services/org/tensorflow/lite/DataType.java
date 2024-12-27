package org.tensorflow.lite;

public enum DataType {
    FLOAT32(1),
    INT32(2),
    UINT8(3),
    INT64(4),
    STRING(5),
    BOOL(6),
    INT16(7),
    INT8(9);

    private static final DataType[] values = values();
    private final int value;

    DataType(int value) {
        this.value = value;
    }

    public int byteSize() {
        switch (this) {
            case FLOAT32:
            case INT32:
                return 4;
            case INT16:
                return 2;
            case INT8:
            case UINT8:
                return 1;
            case INT64:
                return 8;
            case BOOL:
                return -1;
            case STRING:
                return -1;
            default:
                throw new IllegalArgumentException(
                        "DataType error: DataType " + this + " is not supported yet");
        }
    }

    int c() {
        return this.value;
    }
}
