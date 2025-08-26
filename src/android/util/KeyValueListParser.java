package android.util;

import android.hardware.gnss.GnssSignalType;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;

/* loaded from: classes4.dex */
public class KeyValueListParser {
    private final TextUtils.StringSplitter mSplitter;
    private final ArrayMap<String, String> mValues = new ArrayMap<>();

    public KeyValueListParser(char c) {
        this.mSplitter = new TextUtils.SimpleStringSplitter(c);
    }

    public void setString(String str) throws IllegalArgumentException {
        this.mValues.clear();
        if (str != null) {
            this.mSplitter.setString(str);
            for (String str2 : this.mSplitter) {
                int iIndexOf = str2.indexOf(61);
                if (iIndexOf < 0) {
                    this.mValues.clear();
                    throw new IllegalArgumentException("'" + str2 + "' in '" + str + "' is not a valid key-value pair");
                }
                this.mValues.put(str2.substring(0, iIndexOf).trim(), str2.substring(iIndexOf + 1).trim());
            }
        }
    }

    public int getInt(String str, int i) {
        String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return Integer.parseInt(str2);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public long getLong(String str, long j) {
        String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return Long.parseLong(str2);
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    public float getFloat(String str, float f) {
        String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return Float.parseFloat(str2);
            } catch (NumberFormatException unused) {
            }
        }
        return f;
    }

    public String getString(String str, String str2) {
        String str3 = this.mValues.get(str);
        return str3 != null ? str3 : str2;
    }

    public boolean getBoolean(String str, boolean z) {
        String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return Boolean.parseBoolean(str2);
            } catch (NumberFormatException unused) {
            }
        }
        return z;
    }

    public int[] getIntArray(String str, int[] iArr) {
        String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                String[] strArrSplit = str2.split(":");
                if (strArrSplit.length > 0) {
                    int[] iArr2 = new int[strArrSplit.length];
                    for (int i = 0; i < strArrSplit.length; i++) {
                        iArr2[i] = Integer.parseInt(strArrSplit[i]);
                    }
                    return iArr2;
                }
            } catch (NumberFormatException unused) {
            }
        }
        return iArr;
    }

    public int size() {
        return this.mValues.size();
    }

    public String keyAt(int i) {
        return this.mValues.keyAt(i);
    }

    public long getDurationMillis(String str, long j) {
        String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                if (!str2.startsWith(GnssSignalType.CODE_TYPE_P) && !str2.startsWith("p")) {
                    return Long.parseLong(str2);
                }
                return java.time.Duration.parse(str2).toMillis();
            } catch (NumberFormatException | DateTimeParseException unused) {
            }
        }
        return j;
    }

    public static class IntValue {
        private final int mDefaultValue;
        private final String mKey;
        private int mValue;

        public IntValue(String str, int i) {
            this.mKey = str;
            this.mDefaultValue = i;
            this.mValue = i;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getInt(this.mKey, this.mDefaultValue);
        }

        public String getKey() {
            return this.mKey;
        }

        public int getDefaultValue() {
            return this.mDefaultValue;
        }

        public int getValue() {
            return this.mValue;
        }

        public void setValue(int i) {
            this.mValue = i;
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }

    public static class LongValue {
        private final long mDefaultValue;
        private final String mKey;
        private long mValue;

        public LongValue(String str, long j) {
            this.mKey = str;
            this.mDefaultValue = j;
            this.mValue = j;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getLong(this.mKey, this.mDefaultValue);
        }

        public String getKey() {
            return this.mKey;
        }

        public long getDefaultValue() {
            return this.mDefaultValue;
        }

        public long getValue() {
            return this.mValue;
        }

        public void setValue(long j) {
            this.mValue = j;
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }

    public static class StringValue {
        private final String mDefaultValue;
        private final String mKey;
        private String mValue;

        public StringValue(String str, String str2) {
            this.mKey = str;
            this.mDefaultValue = str2;
            this.mValue = str2;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getString(this.mKey, this.mDefaultValue);
        }

        public String getKey() {
            return this.mKey;
        }

        public String getDefaultValue() {
            return this.mDefaultValue;
        }

        public String getValue() {
            return this.mValue;
        }

        public void setValue(String str) {
            this.mValue = str;
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }

    public static class FloatValue {
        private final float mDefaultValue;
        private final String mKey;
        private float mValue;

        public FloatValue(String str, float f) {
            this.mKey = str;
            this.mDefaultValue = f;
            this.mValue = f;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getFloat(this.mKey, this.mDefaultValue);
        }

        public String getKey() {
            return this.mKey;
        }

        public float getDefaultValue() {
            return this.mDefaultValue;
        }

        public float getValue() {
            return this.mValue;
        }

        public void setValue(float f) {
            this.mValue = f;
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }
}
