package androidx.datastore.preferences.core;

import androidx.datastore.preferences.core.Preferences;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class MutablePreferences$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        Object value = entry.getValue();
        return MutablePreferences$$ExternalSyntheticOutline0.m(new StringBuilder("  "), ((Preferences.Key) entry.getKey()).name, " = ", value instanceof byte[] ? ArraysKt___ArraysKt.joinToString$default((byte[]) value, ", ", null, 56) : String.valueOf(entry.getValue()));
    }
}
