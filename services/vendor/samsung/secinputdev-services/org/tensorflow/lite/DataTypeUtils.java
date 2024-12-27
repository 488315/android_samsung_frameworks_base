package org.tensorflow.lite;

class DataTypeUtils {
    private DataTypeUtils() {}

    static String toStringName(DataType dataType) {
        switch (dataType) {
            case FLOAT32:
                return "float";
            case INT32:
                return "int";
            case INT16:
                return "short";
            case INT8:
            case UINT8:
                return "byte";
            case INT64:
                return "long";
            case BOOL:
                return "bool";
            case STRING:
                return "string";
            default:
                throw new IllegalArgumentException(
                        "DataType error: DataType " + dataType + " is not supported yet");
        }
    }

    static DataType fromC(int c) {
        switch (c) {
            case 1:
                return DataType.FLOAT32;
            case 2:
                return DataType.INT32;
            case 3:
                return DataType.UINT8;
            case 4:
                return DataType.INT64;
            case 5:
                return DataType.STRING;
            case 6:
                return DataType.BOOL;
            case 7:
                return DataType.INT16;
            case 8:
            default:
                throw new IllegalArgumentException(
                        "DataType error: DataType " + c + " is not recognized in Java.");
            case 9:
                return DataType.INT8;
        }
    }
}
