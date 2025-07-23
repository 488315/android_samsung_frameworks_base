package androidx.navigation.serialization;

import android.os.Bundle;
import androidx.navigation.NavType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UNKNOWN extends NavType {
    public static final UNKNOWN INSTANCE = new UNKNOWN();

    private UNKNOWN() {
        super(false);
    }

    @Override // androidx.navigation.NavType
    public final /* bridge */ /* synthetic */ Object get(Bundle bundle, String str) {
        return null;
    }

    @Override // androidx.navigation.NavType
    public final String getName() {
        return "unknown";
    }

    @Override // androidx.navigation.NavType
    public final /* bridge */ /* synthetic */ Object parseValue(String str) {
        return "null";
    }

    @Override // androidx.navigation.NavType
    public final /* bridge */ /* synthetic */ void put(Bundle bundle, String str, Object obj) {
    }
}
