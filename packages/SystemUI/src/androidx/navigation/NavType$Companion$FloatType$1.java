package androidx.navigation;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavType$Companion$FloatType$1 extends NavType {
    public NavType$Companion$FloatType$1() {
        super(false);
    }

    @Override // androidx.navigation.NavType
    public final Object get(Bundle bundle, String str) {
        return (Float) bundle.get(str);
    }

    @Override // androidx.navigation.NavType
    public final String getName() {
        return "float";
    }

    @Override // androidx.navigation.NavType
    public final Object parseValue(String str) {
        return Float.valueOf(Float.parseFloat(str));
    }

    @Override // androidx.navigation.NavType
    public final void put(Bundle bundle, String str, Object obj) {
        bundle.putFloat(str, ((Number) obj).floatValue());
    }
}
