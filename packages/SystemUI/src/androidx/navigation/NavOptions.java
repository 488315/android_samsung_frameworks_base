package androidx.navigation;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlinx.serialization.SerializersKt;

/* loaded from: classes.dex */
public final class NavOptions {
    public final int enterAnim;
    public final int exitAnim;
    public final int popEnterAnim;
    public final int popExitAnim;
    public final int popUpToId;
    public final boolean popUpToInclusive;
    public final String popUpToRoute;
    public final KClass popUpToRouteClass;
    public final Object popUpToRouteObject;
    public final boolean popUpToSaveState;
    public final boolean restoreState;
    public final boolean singleTop;

    public final class Builder {
        public boolean popUpToInclusive;
        public boolean popUpToSaveState;
        public boolean restoreState;
        public boolean singleTop;
        public int popUpToId = -1;
        public int enterAnim = -1;
        public int exitAnim = -1;

        public final NavOptions build() {
            return new NavOptions(this.singleTop, this.restoreState, this.popUpToId, this.popUpToInclusive, this.popUpToSaveState, this.enterAnim, this.exitAnim, -1, -1);
        }
    }

    public NavOptions(boolean z, boolean z2, int i, boolean z3, boolean z4, int i2, int i3, int i4, int i5) {
        this.singleTop = z;
        this.restoreState = z2;
        this.popUpToId = i;
        this.popUpToInclusive = z3;
        this.popUpToSaveState = z4;
        this.enterAnim = i2;
        this.exitAnim = i3;
        this.popEnterAnim = i4;
        this.popExitAnim = i5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof NavOptions)) {
            NavOptions navOptions = (NavOptions) obj;
            if (this.singleTop == navOptions.singleTop && this.restoreState == navOptions.restoreState && this.popUpToId == navOptions.popUpToId && Intrinsics.areEqual(this.popUpToRoute, navOptions.popUpToRoute) && Intrinsics.areEqual(this.popUpToRouteClass, navOptions.popUpToRouteClass) && Intrinsics.areEqual(this.popUpToRouteObject, navOptions.popUpToRouteObject) && this.popUpToInclusive == navOptions.popUpToInclusive && this.popUpToSaveState == navOptions.popUpToSaveState && this.enterAnim == navOptions.enterAnim && this.exitAnim == navOptions.exitAnim && this.popEnterAnim == navOptions.popEnterAnim && this.popExitAnim == navOptions.popExitAnim) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = (((((this.singleTop ? 1 : 0) * 31) + (this.restoreState ? 1 : 0)) * 31) + this.popUpToId) * 31;
        String str = this.popUpToRoute;
        int iHashCode = (i + (str != null ? str.hashCode() : 0)) * 31;
        KClass kClass = this.popUpToRouteClass;
        int iHashCode2 = (iHashCode + (kClass != null ? kClass.hashCode() : 0)) * 31;
        Object obj = this.popUpToRouteObject;
        return ((((((((((((iHashCode2 + (obj != null ? obj.hashCode() : 0)) * 31) + (this.popUpToInclusive ? 1 : 0)) * 31) + (this.popUpToSaveState ? 1 : 0)) * 31) + this.enterAnim) * 31) + this.exitAnim) * 31) + this.popEnterAnim) * 31) + this.popExitAnim;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NavOptions(");
        if (this.singleTop) {
            sb.append("launchSingleTop ");
        }
        if (this.restoreState) {
            sb.append("restoreState ");
        }
        int i = this.popUpToId;
        String str = this.popUpToRoute;
        if ((str != null || i != -1) && str != null) {
            sb.append("popUpTo(");
            if (str != null) {
                sb.append(str);
            } else {
                KClass kClass = this.popUpToRouteClass;
                if (kClass != null) {
                    sb.append(kClass);
                } else {
                    Object obj = this.popUpToRouteObject;
                    if (obj != null) {
                        sb.append(obj);
                    } else {
                        sb.append("0x");
                        sb.append(Integer.toHexString(i));
                    }
                }
            }
            if (this.popUpToInclusive) {
                sb.append(" inclusive");
            }
            if (this.popUpToSaveState) {
                sb.append(" saveState");
            }
            sb.append(")");
        }
        int i2 = this.popExitAnim;
        int i3 = this.popEnterAnim;
        int i4 = this.exitAnim;
        int i5 = this.enterAnim;
        if (i5 != -1 || i4 != -1 || i3 != -1 || i2 != -1) {
            sb.append("anim(enterAnim=0x");
            sb.append(Integer.toHexString(i5));
            sb.append(" exitAnim=0x");
            sb.append(Integer.toHexString(i4));
            sb.append(" popEnterAnim=0x");
            sb.append(Integer.toHexString(i3));
            sb.append(" popExitAnim=0x");
            sb.append(Integer.toHexString(i2));
            sb.append(")");
        }
        return sb.toString();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NavOptions(boolean z, boolean z2, String str, boolean z3, boolean z4, int i, int i2, int i3, int i4) {
        this(z, z2, (str != null ? "android-app://androidx.navigation/".concat(str) : "").hashCode(), z3, z4, i, i2, i3, i4);
        NavDestination.Companion.getClass();
        this.popUpToRoute = str;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NavOptions(boolean z, boolean z2, KClass kClass, boolean z3, boolean z4, int i, int i2, int i3, int i4) {
        this(z, z2, SerializersKt.serializer(kClass).hashCode(), z3, z4, i, i2, i3, i4);
        kClass.getClass();
        this.popUpToRouteClass = kClass;
    }

    public NavOptions(boolean z, boolean z2, Object obj, boolean z3, boolean z4, int i, int i2, int i3, int i4) {
        this(z, z2, SerializersKt.serializer(Reflection.getOrCreateKotlinClass(obj.getClass())).hashCode(), z3, z4, i, i2, i3, i4);
        this.popUpToRouteObject = obj;
    }
}
