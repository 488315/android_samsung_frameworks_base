package androidx.navigation;

import android.os.Bundle;
import java.util.Collection;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavAction {
    public final Bundle defaultArguments;
    public final int destinationId;
    public final NavOptions navOptions;

    public NavAction(int i) {
        this(i, null, null, 6, null);
    }

    public final boolean equals(Object obj) {
        Set<String> keySet;
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof NavAction)) {
            NavAction navAction = (NavAction) obj;
            if (this.destinationId == navAction.destinationId && Intrinsics.areEqual(this.navOptions, navAction.navOptions)) {
                if (!Intrinsics.areEqual(this.defaultArguments, navAction.defaultArguments)) {
                    Bundle bundle = this.defaultArguments;
                    if (bundle != null && (keySet = bundle.keySet()) != null) {
                        Set<String> set = keySet;
                        if (!(set instanceof Collection) || !set.isEmpty()) {
                            for (String str : set) {
                                Bundle bundle2 = this.defaultArguments;
                                Object obj2 = bundle2 != null ? bundle2.get(str) : null;
                                Bundle bundle3 = navAction.defaultArguments;
                                if (!Intrinsics.areEqual(obj2, bundle3 != null ? bundle3.get(str) : null)) {
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        Set<String> keySet;
        int hashCode = Integer.hashCode(this.destinationId) * 31;
        NavOptions navOptions = this.navOptions;
        int hashCode2 = hashCode + (navOptions != null ? navOptions.hashCode() : 0);
        Bundle bundle = this.defaultArguments;
        if (bundle != null && (keySet = bundle.keySet()) != null) {
            for (String str : keySet) {
                int i = hashCode2 * 31;
                Bundle bundle2 = this.defaultArguments;
                Object obj = bundle2 != null ? bundle2.get(str) : null;
                hashCode2 = i + (obj != null ? obj.hashCode() : 0);
            }
        }
        return hashCode2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NavAction(0x");
        sb.append(Integer.toHexString(this.destinationId));
        sb.append(")");
        NavOptions navOptions = this.navOptions;
        if (navOptions != null) {
            sb.append(" navOptions=");
            sb.append(navOptions);
        }
        return sb.toString();
    }

    public NavAction(int i, NavOptions navOptions) {
        this(i, navOptions, null, 4, null);
    }

    public NavAction(int i, NavOptions navOptions, Bundle bundle) {
        this.destinationId = i;
        this.navOptions = navOptions;
        this.defaultArguments = bundle;
    }

    public /* synthetic */ NavAction(int i, NavOptions navOptions, Bundle bundle, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : navOptions, (i2 & 4) != 0 ? null : bundle);
    }
}
