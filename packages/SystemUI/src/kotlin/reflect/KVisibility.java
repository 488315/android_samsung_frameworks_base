package kotlin.reflect;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class KVisibility {
    public static final /* synthetic */ KVisibility[] $VALUES;

    static {
        KVisibility[] kVisibilityArr = {new KVisibility("PUBLIC", 0), new KVisibility("PROTECTED", 1), new KVisibility(PeripheralConstants.ConnectivityType.INTERNAL, 2), new KVisibility("PRIVATE", 3)};
        $VALUES = kVisibilityArr;
        EnumEntriesKt.enumEntries(kVisibilityArr);
    }

    private KVisibility(String str, int i) {
    }

    public static KVisibility valueOf(String str) {
        return (KVisibility) Enum.valueOf(KVisibility.class, str);
    }

    public static KVisibility[] values() {
        return (KVisibility[]) $VALUES.clone();
    }
}
