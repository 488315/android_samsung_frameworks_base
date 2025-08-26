package kotlin.text;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public abstract class HexFormatKt {
    public static final boolean access$isCaseSensitive(String str) {
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (Intrinsics.compare(cCharAt, 128) >= 0 || Character.isLetter(cCharAt)) {
                return true;
            }
        }
        return false;
    }
}
