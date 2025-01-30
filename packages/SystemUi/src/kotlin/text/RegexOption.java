package kotlin.text;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'EF10' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:343)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RegexOption implements FlagEnum {
    public static final /* synthetic */ RegexOption[] $VALUES;
    private final int mask;
    private final int value;

    /* JADX INFO: Fake field, exist only in values array */
    RegexOption EF10;

    static {
        int i = 2;
        int i2 = 0;
        int i3 = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        int i4 = 0;
        int i5 = 2;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        $VALUES = new RegexOption[]{new RegexOption("IGNORE_CASE", 0, i, 0, 2, null), new RegexOption("MULTILINE", 1, 8, i2, i3, defaultConstructorMarker), new RegexOption("LITERAL", 2, 16, i4, i5, defaultConstructorMarker2), new RegexOption("UNIX_LINES", 3, 1, i2, i3, defaultConstructorMarker), new RegexOption("COMMENTS", 4, 4, i4, i5, defaultConstructorMarker2), new RegexOption("DOT_MATCHES_ALL", 5, 32, 0, 2, null), new RegexOption("CANON_EQ", 6, 128, 0, i, null)};
    }

    private RegexOption(String str, int i, int i2, int i3) {
        this.value = i2;
        this.mask = i3;
    }

    public static RegexOption valueOf(String str) {
        return (RegexOption) Enum.valueOf(RegexOption.class, str);
    }

    public static RegexOption[] values() {
        return (RegexOption[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }

    public /* synthetic */ RegexOption(String str, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, (i4 & 2) != 0 ? i2 : i3);
    }
}
