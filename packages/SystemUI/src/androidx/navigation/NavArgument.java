package androidx.navigation;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavArgument {
    public final Object defaultValue;
    public final boolean isDefaultValuePresent;
    public final boolean isDefaultValueUnknown;
    public final boolean isNullable;
    public final NavType type;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public boolean isNullable;
        public NavType type;
        public boolean unknownDefaultValuePresent;
    }

    public NavArgument(NavType navType, boolean z, Object obj, boolean z2, boolean z3) {
        if (!navType.isNullableAllowed && z) {
            throw new IllegalArgumentException((navType.getName() + " does not allow nullable values").toString());
        }
        if (!z && z2 && obj == null) {
            throw new IllegalArgumentException(("Argument with type " + navType.getName() + " has null value but is not nullable.").toString());
        }
        this.type = navType;
        this.isNullable = z;
        this.defaultValue = obj;
        this.isDefaultValuePresent = z2 || z3;
        this.isDefaultValueUnknown = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && NavArgument.class.equals(obj.getClass())) {
            NavArgument navArgument = (NavArgument) obj;
            if (this.isNullable != navArgument.isNullable || this.isDefaultValuePresent != navArgument.isDefaultValuePresent || !Intrinsics.areEqual(this.type, navArgument.type)) {
                return false;
            }
            Object obj2 = navArgument.defaultValue;
            Object obj3 = this.defaultValue;
            if (obj3 != null) {
                return Intrinsics.areEqual(obj3, obj2);
            }
            if (obj2 == null) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = ((((this.type.hashCode() * 31) + (this.isNullable ? 1 : 0)) * 31) + (this.isDefaultValuePresent ? 1 : 0)) * 31;
        Object obj = this.defaultValue;
        return hashCode + (obj != null ? obj.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NavArgument");
        sb.append(" Type: " + this.type);
        sb.append(" Nullable: " + this.isNullable);
        if (this.isDefaultValuePresent) {
            sb.append(" DefaultValue: " + this.defaultValue);
        }
        return sb.toString();
    }
}
