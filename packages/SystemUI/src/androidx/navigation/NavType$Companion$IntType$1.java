package androidx.navigation;

import android.os.Bundle;
import kotlin.text.CharsKt__CharJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavType$Companion$IntType$1 extends NavType {
    public NavType$Companion$IntType$1() {
        super(false);
    }

    @Override // androidx.navigation.NavType
    public final Object get(Bundle bundle, String str) {
        return (Integer) bundle.get(str);
    }

    @Override // androidx.navigation.NavType
    public final String getName() {
        return "integer";
    }

    @Override // androidx.navigation.NavType
    public final Object parseValue(String str) {
        int parseInt;
        if (str.startsWith("0x")) {
            String substring = str.substring(2);
            CharsKt__CharJVMKt.checkRadix(16);
            parseInt = Integer.parseInt(substring, 16);
        } else {
            parseInt = Integer.parseInt(str);
        }
        return Integer.valueOf(parseInt);
    }

    @Override // androidx.navigation.NavType
    public final void put(Bundle bundle, String str, Object obj) {
        bundle.putInt(str, ((Number) obj).intValue());
    }
}
