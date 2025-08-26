package android.app;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Objects;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0 {
    public static /* synthetic */ int m(int i, int i2) {
        return (i * 31) + i2;
    }

    public static /* synthetic */ int m(int i, int i2, float f) {
        return (((i * 31) + i2) * 31) + Float.hashCode(f);
    }

    public static /* synthetic */ int m(int i, int i2, float f, float f2) {
        return (((((i * 31) + i2) * 31) + Float.hashCode(f)) * 31) + Float.hashCode(f2);
    }

    public static /* synthetic */ int m(int i, int i2, float f, Object obj) {
        return (((((i * 31) + i2) * 31) + Float.hashCode(f)) * 31) + Objects.hashCode(obj);
    }

    public static /* synthetic */ int m(int i, int i2, Object obj) {
        return (((i * 31) + i2) * 31) + Objects.hashCode(obj);
    }

    public static /* synthetic */ int m(int i, Object obj) {
        return (i * 31) + Objects.hashCode(obj);
    }

    public static /* synthetic */ int m(int i, Object obj, Object obj2) {
        return (((i * 31) + Objects.hashCode(obj)) * 31) + Objects.hashCode(obj2);
    }

    public static /* synthetic */ int m(int i, Object obj, Object obj2, Object obj3) {
        return (((((i * 31) + Objects.hashCode(obj)) * 31) + Objects.hashCode(obj2)) * 31) + Objects.hashCode(obj3);
    }

    public static /* synthetic */ int m(Object obj, Object obj2) {
        return (Objects.hashCode(obj) * 31) + Objects.hashCode(obj2);
    }

    public static /* synthetic */ int m(boolean z, boolean z2, boolean z3, int i, Object obj, Object obj2) {
        return (((((((((Boolean.hashCode(z) * 31) + Boolean.hashCode(z2)) * 31) + Boolean.hashCode(z3)) * 31) + i) * 31) + Objects.hashCode(obj)) * 31) + Objects.hashCode(obj2);
    }

    public static /* synthetic */ int m(boolean z, boolean z2, boolean z3, boolean z4, Object obj) {
        return (((((((Boolean.hashCode(z) * 31) + Boolean.hashCode(z2)) * 31) + Boolean.hashCode(z3)) * 31) + Boolean.hashCode(z4)) * 31) + Objects.hashCode(obj);
    }

    public static /* synthetic */ String m(Object[] objArr, Class cls, String str) {
        String[] strArrSplit = str.length() == 0 ? new String[0] : str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getSimpleName());
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        for (int i = 0; i < strArrSplit.length; i++) {
            sb.append(strArrSplit[i]);
            sb.append("=");
            sb.append(objArr[i]);
            if (i != strArrSplit.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
