package androidx.navigation;

import android.os.Bundle;
import kotlin.text.CharsKt__CharJVMKt;

/* loaded from: classes.dex */
public final class NavType$Companion$LongType$1 extends NavType {
    public NavType$Companion$LongType$1() {
        super(false);
    }

    @Override // androidx.navigation.NavType
    public final Object get(Bundle bundle, String str) {
        return (Long) bundle.get(str);
    }

    @Override // androidx.navigation.NavType
    public final String getName() {
        return "long";
    }

    @Override // androidx.navigation.NavType
    public final Object parseValue(String str) throws NumberFormatException {
        long j;
        String strSubstring = str.endsWith("L") ? str.substring(0, str.length() - 1) : str;
        if (str.startsWith("0x")) {
            String strSubstring2 = strSubstring.substring(2);
            CharsKt__CharJVMKt.checkRadix(16);
            j = Long.parseLong(strSubstring2, 16);
        } else {
            j = Long.parseLong(strSubstring);
        }
        return Long.valueOf(j);
    }

    @Override // androidx.navigation.NavType
    public final void put(Bundle bundle, String str, Object obj) {
        bundle.putLong(str, ((Number) obj).longValue());
    }
}
