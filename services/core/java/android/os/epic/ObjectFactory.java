package android.os.epic;

import android.os.ServiceManager;
import android.util.Log;

import java.lang.reflect.Method;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class ObjectFactory {
    private static final String EPIC_AIDL_CLASS =
            "vendor.samsung_slsi.hardware.epic.IEpicRequest/default";
    private static final String EPIC_HIDL_CLASS =
            "vendor.samsung_slsi.hardware.epic.V1_0.IEpicRequest";
    private static boolean m_has_load;
    private static Method m_hidl_epic_getService_func;
    private static Class m_hidl_epic_request_cls;

    public static IEpicObject create(int i) {
        if (loadAIDL()) {
            return new EpicAIDLObject(i);
        }
        if (loadHIDL()) {
            return new EpicHIDLObject(i);
        }
        return null;
    }

    public static IEpicObject create(int[] iArr) {
        if (loadAIDL()) {
            return new EpicAIDLObject(iArr);
        }
        if (loadHIDL()) {
            return new EpicHIDLObject(iArr);
        }
        return null;
    }

    public static boolean loadAIDL() {
        return ServiceManager.getService(EPIC_AIDL_CLASS) != null;
    }

    public static boolean loadHIDL() {
        synchronized (ObjectFactory.class) {
            try {
                if (m_hidl_epic_request_cls == null && !m_has_load) {
                    m_has_load = true;
                    Class[] clsArr = new Class[0];
                    Class<?> cls = Class.forName(EPIC_HIDL_CLASS);
                    m_hidl_epic_request_cls = cls;
                    m_hidl_epic_getService_func = cls.getMethod("getService", null);
                }
            } catch (Exception unused) {
            }
        }
        try {
            Method method = m_hidl_epic_getService_func;
            if (method == null) {
                return false;
            }
            return method.invoke(null, null) != null;
        } catch (Exception unused2) {
            Log.e("ObjectFactory", "failed to get HIDL request");
            return false;
        }
    }
}
