package androidx.compose.ui.platform;

import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class JvmActuals_jvmKt {
    public static final String simpleIdentityToString(Object obj) {
        String name = obj.getClass().isAnonymousClass() ? obj.getClass().getName() : obj.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append('@');
        int i = StringCompanionObject.$r8$clinit;
        sb.append(String.format("%07x", Arrays.copyOf(new Object[]{Integer.valueOf(System.identityHashCode(obj))}, 1)));
        return sb.toString();
    }
}
