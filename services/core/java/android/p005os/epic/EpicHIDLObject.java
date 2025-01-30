package android.p005os.epic;

import android.os.epic.IEpicObject;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class EpicHIDLObject extends IEpicObject.Stub {
  public static final String EPIC_CLASS = "vendor.samsung_slsi.hardware.epic.V1_0.IEpicRequest";
  public static final String EPIC_HANDLE_CLASS =
      "vendor.samsung_slsi.hardware.epic.V1_0.IEpicHandle";
  public static final String TAG = "EpicObject";
  public static Method m_epic_getservice_func = null;
  public static Class m_epic_handle_cls = null;
  public static Method m_epic_hint_release_func = null;
  public static Method m_epic_init_func = null;
  public static Method m_epic_init_multi_func = null;
  public static Method m_epic_perf_hint_func = null;
  public static Method m_epic_release_conditional_func = null;
  public static Method m_epic_release_func = null;
  public static Class m_epic_request_cls = null;
  public static Method m_epic_request_conditional_func = null;
  public static Method m_epic_request_func = null;
  public static Method m_epic_request_opt_func = null;
  public static Method m_epic_request_opt_multi_func = null;
  public static boolean m_has_load = false;
  public Object m_handle_obj;
  public Object m_request_obj;

  public EpicHIDLObject() {
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
      Object invoke = method.invoke(null, new Object[0]);
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
      Object invoke = method.invoke(null, new Object[0]);
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

  public boolean acquire_lock_option(int i, int i2) {
    Object obj;
    if (!m_has_load || (obj = this.m_request_obj) == null) {
      return false;
    }
    try {
      return ((Integer)
                  m_epic_request_opt_func.invoke(
                      obj, this.m_handle_obj, Integer.valueOf(i), Integer.valueOf(i2)))
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
                      this.m_request_obj, this.m_handle_obj, arrayList, arrayList2))
              .intValue()
          != 0;
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

  public boolean perf_hint(String str) {
    Object obj;
    if (!m_has_load || (obj = this.m_request_obj) == null) {
      return false;
    }
    try {
      return ((Integer) m_epic_perf_hint_func.invoke(obj, this.m_handle_obj, str)).intValue() != 0;
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
      return ((Integer) m_epic_hint_release_func.invoke(obj, this.m_handle_obj, str)).intValue()
          != 0;
    } catch (Exception unused) {
      return false;
    }
  }

  public final void create_instance() {
    synchronized (EpicHIDLObject.class) {
      if (m_has_load) {
        return;
      }
      m_epic_request_cls = Class.forName("vendor.samsung_slsi.hardware.epic.V1_0.IEpicRequest");
      Class<?> cls = Class.forName(EPIC_HANDLE_CLASS);
      m_epic_handle_cls = cls;
      Class<?> cls2 = Integer.TYPE;
      Class<?>[] clsArr = {cls2};
      Class<?>[] clsArr2 = {cls, cls2, cls2};
      m_epic_getservice_func = m_epic_request_cls.getMethod("getService", new Class[0]);
      m_epic_init_func = m_epic_request_cls.getMethod("init", clsArr);
      m_epic_init_multi_func = m_epic_request_cls.getMethod("init_multi", ArrayList.class);
      m_epic_request_func = m_epic_request_cls.getMethod("acquire_lock", cls);
      m_epic_release_func = m_epic_request_cls.getMethod("release_lock", cls);
      m_epic_request_opt_func = m_epic_request_cls.getMethod("acquire_lock_option", clsArr2);
      m_epic_request_opt_multi_func =
          m_epic_request_cls.getMethod(
              "acquire_lock_multi_option", cls, ArrayList.class, ArrayList.class);
      m_epic_request_conditional_func =
          m_epic_request_cls.getMethod("acquire_lock_conditional", cls, String.class);
      m_epic_release_conditional_func =
          m_epic_request_cls.getMethod("release_lock_conditional", cls, String.class);
      m_epic_perf_hint_func = m_epic_request_cls.getMethod("perf_hint", cls, String.class);
      m_epic_hint_release_func = m_epic_request_cls.getMethod("hint_release", cls, String.class);
      m_has_load = true;
    }
  }
}
