package androidx.navigation;

import android.os.Bundle;
import kotlin.text.CharsKt__CharJVMKt;

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
    public final Object parseValue(String str) throws NumberFormatException {
        int i;
        if (str.startsWith("0x")) {
            String strSubstring = str.substring(2);
            CharsKt__CharJVMKt.checkRadix(16);
            i = Integer.parseInt(strSubstring, 16);
        } else {
            i = Integer.parseInt(str);
        }
        return Integer.valueOf(i);
    }

    @Override // androidx.navigation.NavType
    public final void put(Bundle bundle, String str, Object obj) {
        bundle.putInt(str, ((Number) obj).intValue());
    }
}
