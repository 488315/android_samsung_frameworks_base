package androidx.navigation;

import android.os.Bundle;
import kotlin.text.CharsKt__CharJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object parseValue(String str) {
        long parseLong;
        String substring = str.endsWith("L") ? str.substring(0, str.length() - 1) : str;
        if (str.startsWith("0x")) {
            String substring2 = substring.substring(2);
            CharsKt__CharJVMKt.checkRadix(16);
            parseLong = Long.parseLong(substring2, 16);
        } else {
            parseLong = Long.parseLong(substring);
        }
        return Long.valueOf(parseLong);
    }

    @Override // androidx.navigation.NavType
    public final void put(Bundle bundle, String str, Object obj) {
        bundle.putLong(str, ((Number) obj).longValue());
    }
}
