package androidx.navigation;

import android.os.Bundle;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class NavType$Companion$BoolType$1 extends NavType {
    public NavType$Companion$BoolType$1() {
        super(false);
    }

    @Override // androidx.navigation.NavType
    public final Object get(Bundle bundle, String str) {
        return (Boolean) bundle.get(str);
    }

    @Override // androidx.navigation.NavType
    public final String getName() {
        return "boolean";
    }

    @Override // androidx.navigation.NavType
    public final Object parseValue(String str) {
        boolean z;
        if (Intrinsics.areEqual(str, "true")) {
            z = true;
        } else {
            if (!Intrinsics.areEqual(str, "false")) {
                throw new IllegalArgumentException("A boolean NavType only accepts \"true\" or \"false\" values.");
            }
            z = false;
        }
        return Boolean.valueOf(z);
    }

    @Override // androidx.navigation.NavType
    public final void put(Bundle bundle, String str, Object obj) {
        bundle.putBoolean(str, ((Boolean) obj).booleanValue());
    }
}
