package android.view.inspector;

import android.graphics.Color;

/* loaded from: classes4.dex */
public interface PropertyReader {
    void readBoolean(int i, boolean z);

    void readByte(int i, byte b);

    void readChar(int i, char c);

    void readColor(int i, int i2);

    void readColor(int i, long j);

    void readColor(int i, Color color);

    void readDouble(int i, double d);

    void readFloat(int i, float f);

    void readGravity(int i, int i2);

    void readInt(int i, int i2);

    void readIntEnum(int i, int i2);

    void readIntFlag(int i, int i2);

    void readLong(int i, long j);

    void readObject(int i, Object obj);

    void readResourceId(int i, int i2);

    void readShort(int i, short s);

    public static class PropertyTypeMismatchException extends RuntimeException {
        public PropertyTypeMismatchException(int i, String str, String str2, String str3) {
            super(formatMessage(i, str, str2, str3));
        }

        public PropertyTypeMismatchException(int i, String str, String str2) {
            super(formatMessage(i, str, str2, null));
        }

        private static String formatMessage(int i, String str, String str2, String str3) {
            if (str3 == null) {
                return String.format("Attempted to read property with ID 0x%08X as type %s, but the ID is of type %s.", Integer.valueOf(i), str, str2);
            }
            return String.format("Attempted to read property \"%s\" with ID 0x%08X as type %s, but the ID is of type %s.", str3, Integer.valueOf(i), str, str2);
        }
    }
}
