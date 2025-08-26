package kotlin.text;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class RegexOption implements FlagEnum {
    public static final /* synthetic */ RegexOption[] $VALUES;
    private final int mask;
    private final int value;

    static {
        RegexOption[] regexOptionArr = {new RegexOption("IGNORE_CASE", 0, 2, 0, 2, null), new RegexOption("MULTILINE", 1, 8, 0, 2, null), new RegexOption("LITERAL", 2, 16, 0, 2, null), new RegexOption("UNIX_LINES", 3, 1, 0, 2, null), new RegexOption("COMMENTS", 4, 4, 0, 2, null), new RegexOption("DOT_MATCHES_ALL", 5, 32, 0, 2, null), new RegexOption("CANON_EQ", 6, 128, 0, 2, null)};
        $VALUES = regexOptionArr;
        EnumEntriesKt.enumEntries(regexOptionArr);
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
