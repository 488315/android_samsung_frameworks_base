package android.os.logcat;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class SecurityLogEvent$$ExternalSyntheticTypeSwitch1 {
    public static /* synthetic */ Object[] switchCases;

    public static /* synthetic */ Object[] getSwitchCases() {
        Object[] objArr = switchCases;
        if (objArr != null) {
            return objArr;
        }
        Object[] objArr2 = {Integer.class, Long.class, Float.class, String.class};
        switchCases = objArr2;
        return objArr2;
    }

    public static /* synthetic */ int m(Object obj, int i, Object[] objArr) {
        if (obj == null) {
            return -1;
        }
        while (i < objArr.length) {
            Object obj2 = objArr[i];
            if (obj2 instanceof Class) {
                if (((Class) obj2).isInstance(obj)) {
                    return i;
                }
                i++;
            } else {
                if (obj.equals(obj2)) {
                    return i;
                }
                i++;
            }
        }
        return -2;
    }
}
