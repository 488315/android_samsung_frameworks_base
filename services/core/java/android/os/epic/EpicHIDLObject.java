package android.os.epic;

import java.lang.reflect.Method;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EpicHIDLObject extends IEpicObject.Stub {
    private static final String EPIC_CLASS = "vendor.samsung_slsi.hardware.epic.V1_0.IEpicRequest";
    private static final String EPIC_HANDLE_CLASS =
            "vendor.samsung_slsi.hardware.epic.V1_0.IEpicHandle";
    private static final String TAG = "EpicObject";
    private static Method m_epic_getservice_func;
    private static Class m_epic_handle_cls;
    private static Method m_epic_hint_release_func;
    private static Method m_epic_init_func;
    private static Method m_epic_init_multi_func;
    private static Method m_epic_perf_hint_func;
    private static Method m_epic_release_conditional_func;
    private static Method m_epic_release_func;
    private static Class m_epic_request_cls;
    private static Method m_epic_request_conditional_func;
    private static Method m_epic_request_func;
    private static Method m_epic_request_opt_func;
    private static Method m_epic_request_opt_multi_func;
    private static boolean m_has_load;
    private Object m_handle_obj;
    private Object m_request_obj;

    private EpicHIDLObject() {
        this.m_request_obj = null;
        this.m_handle_obj = null;
        create_instance();
    }

    public EpicHIDLObject(int i) {
        this();
        Method method;
        if (!m_has_load || (method = m_epic_getservice_func) == null) {
            return;
        }
        try {
            Object invoke = method.invoke(null, null);
            this.m_request_obj = invoke;
            if (invoke != null) {
                this.m_handle_obj = m_epic_init_func.invoke(invoke, Integer.valueOf(i));
            }
        } catch (Exception unused) {
        }
    }

    public EpicHIDLObject(int[] iArr) {
        this();
        Method method;
        if (!m_has_load || (method = m_epic_getservice_func) == null) {
            return;
        }
        try {
            Object invoke = method.invoke(null, null);
            this.m_request_obj = invoke;
            if (invoke != null) {
                ArrayList arrayList = new ArrayList();
                for (int i : iArr) {
                    arrayList.add(Integer.valueOf(i));
                }
                this.m_handle_obj = m_epic_init_multi_func.invoke(this.m_request_obj, arrayList);
            }
        } catch (Exception unused) {
        }
    }

    private void create_instance() {
        synchronized (EpicHIDLObject.class) {
            if (m_has_load) {
                return;
            }
            m_epic_request_cls = Class.forName(EPIC_CLASS);
            Class<?> cls = Class.forName(EPIC_HANDLE_CLASS);
            m_epic_handle_cls = cls;
            Class[] clsArr = new Class[0];
            Class<?> cls2 = Integer.TYPE;
            m_epic_getservice_func = m_epic_request_cls.getMethod("getService", null);
            m_epic_init_func = m_epic_request_cls.getMethod("init", cls2);
            m_epic_init_multi_func = m_epic_request_cls.getMethod("init_multi", ArrayList.class);
            m_epic_request_func = m_epic_request_cls.getMethod("acquire_lock", cls);
            m_epic_release_func = m_epic_request_cls.getMethod("release_lock", cls);
            m_epic_request_opt_func =
                    m_epic_request_cls.getMethod("acquire_lock_option", cls, cls2, cls2);
            m_epic_request_opt_multi_func =
                    m_epic_request_cls.getMethod(
                            "acquire_lock_multi_option", cls, ArrayList.class, ArrayList.class);
            m_epic_request_conditional_func =
                    m_epic_request_cls.getMethod("acquire_lock_conditional", cls, String.class);
            m_epic_release_conditional_func =
                    m_epic_request_cls.getMethod("release_lock_conditional", cls, String.class);
            m_epic_perf_hint_func = m_epic_request_cls.getMethod("perf_hint", cls, String.class);
            m_epic_hint_release_func =
                    m_epic_request_cls.getMethod("hint_release", cls, String.class);
            m_has_load = true;
        }
    }

    public boolean acquire_lock() {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer) m_epic_request_func.invoke(obj, this.m_handle_obj)).intValue() != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean acquire_lock_conditional(String str) {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer) m_epic_request_conditional_func.invoke(obj, this.m_handle_obj, str))
                            .intValue()
                    != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean acquire_lock_option(int i, int i2) {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer)
                                    m_epic_request_opt_func.invoke(
                                            obj,
                                            this.m_handle_obj,
                                            Integer.valueOf(i),
                                            Integer.valueOf(i2)))
                            .intValue()
                    != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) {
        if (!m_has_load || this.m_request_obj == null) {
            return false;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (int i : iArr) {
                arrayList.add(Integer.valueOf(i));
            }
            ArrayList arrayList2 = new ArrayList();
            for (int i2 : iArr2) {
                arrayList2.add(Integer.valueOf(i2));
            }
            return ((Integer)
                                    m_epic_request_opt_multi_func.invoke(
                                            this.m_request_obj,
                                            this.m_handle_obj,
                                            arrayList,
                                            arrayList2))
                            .intValue()
                    != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean hint_release(String str) {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer) m_epic_hint_release_func.invoke(obj, this.m_handle_obj, str))
                            .intValue()
                    != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean perf_hint(String str) {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer) m_epic_perf_hint_func.invoke(obj, this.m_handle_obj, str)).intValue()
                    != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean release_lock() {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer) m_epic_release_func.invoke(obj, this.m_handle_obj)).intValue() != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean release_lock_conditional(String str) {
        Object obj;
        if (!m_has_load || (obj = this.m_request_obj) == null) {
            return false;
        }
        try {
            return ((Integer) m_epic_release_conditional_func.invoke(obj, this.m_handle_obj, str))
                            .intValue()
                    != 0;
        } catch (Exception unused) {
            return false;
        }
    }
}
