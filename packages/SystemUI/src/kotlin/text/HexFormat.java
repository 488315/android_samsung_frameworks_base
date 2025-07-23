package kotlin.text;

import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class HexFormat {
    public static final Companion Companion = new Companion(null);
    public static final HexFormat Default;
    public final BytesHexFormat bytes;
    public final NumberHexFormat number;
    public final boolean upperCase;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BytesHexFormat {
        public static final Companion Companion = new Companion(null);
        public static final BytesHexFormat Default = new BytesHexFormat(Integer.MAX_VALUE, Integer.MAX_VALUE, "  ", "", "", "");
        public final String bytePrefix;
        public final String byteSeparator;
        public final String byteSuffix;
        public final int bytesPerGroup;
        public final int bytesPerLine;
        public final String groupSeparator;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public BytesHexFormat(int i, int i2, String str, String str2, String str3, String str4) {
            this.bytesPerLine = i;
            this.bytesPerGroup = i2;
            this.groupSeparator = str;
            this.byteSeparator = str2;
            this.bytePrefix = str3;
            this.byteSuffix = str4;
            if (str3.length() == 0 && str4.length() == 0) {
                str2.length();
            }
            if (HexFormatKt.access$isCaseSensitive(str) || HexFormatKt.access$isCaseSensitive(str2) || HexFormatKt.access$isCaseSensitive(str3)) {
                return;
            }
            HexFormatKt.access$isCaseSensitive(str4);
        }

        public final void appendOptionsTo$kotlin_stdlib(StringBuilder sb, String str) {
            sb.append(str);
            sb.append("bytesPerLine = ");
            sb.append(this.bytesPerLine);
            sb.append(",");
            sb.append('\n');
            sb.append(str);
            sb.append("bytesPerGroup = ");
            sb.append(this.bytesPerGroup);
            sb.append(",");
            sb.append('\n');
            sb.append(str);
            sb.append("groupSeparator = \"");
            sb.append(this.groupSeparator);
            sb.append("\",");
            sb.append('\n');
            sb.append(str);
            sb.append("byteSeparator = \"");
            sb.append(this.byteSeparator);
            sb.append("\",");
            sb.append('\n');
            sb.append(str);
            sb.append("bytePrefix = \"");
            sb.append(this.bytePrefix);
            sb.append("\",");
            sb.append('\n');
            sb.append(str);
            sb.append("byteSuffix = \"");
            sb.append(this.byteSuffix);
            sb.append("\"");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("BytesHexFormat(\n");
            appendOptionsTo$kotlin_stdlib(sb, "    ");
            sb.append('\n');
            sb.append(")");
            return sb.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NumberHexFormat {
        public static final Companion Companion = new Companion(null);
        public static final NumberHexFormat Default = new NumberHexFormat("", "", false, 1);
        public final boolean ignoreCase;
        public final boolean isDigitsOnly;
        public final boolean isDigitsOnlyAndNoPadding;
        public final int minLength;
        public final String prefix;
        public final boolean removeLeadingZeros;
        public final String suffix;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public NumberHexFormat(String str, String str2, boolean z, int i) {
            this.prefix = str;
            this.suffix = str2;
            this.removeLeadingZeros = z;
            this.minLength = i;
            boolean z2 = str.length() == 0 && str2.length() == 0;
            this.isDigitsOnly = z2;
            this.isDigitsOnlyAndNoPadding = z2 && i == 1;
            this.ignoreCase = HexFormatKt.access$isCaseSensitive(str) || HexFormatKt.access$isCaseSensitive(str2);
        }

        public final void appendOptionsTo$kotlin_stdlib(StringBuilder sb, String str) {
            sb.append(str);
            sb.append("prefix = \"");
            sb.append(this.prefix);
            sb.append("\",");
            sb.append('\n');
            sb.append(str);
            sb.append("suffix = \"");
            sb.append(this.suffix);
            sb.append("\",");
            sb.append('\n');
            sb.append(str);
            sb.append("removeLeadingZeros = ");
            sb.append(this.removeLeadingZeros);
            sb.append(',');
            sb.append('\n');
            sb.append(str);
            sb.append("minLength = ");
            sb.append(this.minLength);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("NumberHexFormat(\n");
            appendOptionsTo$kotlin_stdlib(sb, "    ");
            sb.append('\n');
            sb.append(")");
            return sb.toString();
        }
    }

    static {
        BytesHexFormat.Companion companion = BytesHexFormat.Companion;
        companion.getClass();
        BytesHexFormat bytesHexFormat = BytesHexFormat.Default;
        NumberHexFormat.Companion companion2 = NumberHexFormat.Companion;
        companion2.getClass();
        NumberHexFormat numberHexFormat = NumberHexFormat.Default;
        Default = new HexFormat(false, bytesHexFormat, numberHexFormat);
        companion.getClass();
        companion2.getClass();
        new HexFormat(true, bytesHexFormat, numberHexFormat);
    }

    public HexFormat(boolean z, BytesHexFormat bytesHexFormat, NumberHexFormat numberHexFormat) {
        this.upperCase = z;
        this.bytes = bytesHexFormat;
        this.number = numberHexFormat;
    }

    public final String toString() {
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m("HexFormat(\n    upperCase = ");
        m.append(this.upperCase);
        m.append(",\n    bytes = BytesHexFormat(\n");
        this.bytes.appendOptionsTo$kotlin_stdlib(m, "        ");
        m.append('\n');
        m.append("    ),");
        m.append('\n');
        m.append("    number = NumberHexFormat(");
        m.append('\n');
        this.number.appendOptionsTo$kotlin_stdlib(m, "        ");
        m.append('\n');
        m.append("    )");
        m.append('\n');
        m.append(")");
        return m.toString();
    }
}
