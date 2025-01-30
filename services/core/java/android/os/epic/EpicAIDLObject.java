package android.os.epic;

import android.os.IBinder;
import android.os.ServiceManager;
import android.os.epic.IEpicObject;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class EpicAIDLObject extends IEpicObject.Stub {
  public static final String EPIC_CLASS = "vendor.samsung_slsi.hardware.epic.IEpicRequest";
  public static final String EPIC_CLASS_STUB =
      "vendor.samsung_slsi.hardware.epic.IEpicRequest$Stub";
  public static final String EPIC_HANDLE_CLASS = "vendor.samsung_slsi.hardware.epic.IEpicHandle";
  public static final String IEPIC_AIDL_INTERFACE =
      "vendor.samsung_slsi.hardware.epic.IEpicRequest/default";
  public static final String TAG = "EpicObject";
  public static Method m_epic_asInterface_func = null;
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
  public static Class m_epic_request_stub_cls = null;
  public static boolean m_has_load = false;
  public IBinder m_binder;
  public Object m_handle_obj;
  public Object m_request_obj;

  public EpicAIDLObject() {
    this.m_request_obj = null;
    this.m_handle_obj = null;
    this.m_binder = null;
    create_instance();
    getService();
  }

  public EpicAIDLObject(int i) {
    this();
    Method method;
    IBinder iBinder;
    if (!m_has_load
        || (method = m_epic_asInterface_func) == null
        || (iBinder = this.m_binder) == null) {
      return;
    }
    try {
      Object invoke = method.invoke(null, iBinder);
      this.m_request_obj = invoke;
      if (invoke != null) {
        this.m_handle_obj = m_epic_init_func.invoke(invoke, Integer.valueOf(i));
      } else {
        Log.e("EpicObject", "failed to get request_obj");
      }
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
    }
  }

  public EpicAIDLObject(int[] iArr) {
    this();
    Method method;
    IBinder iBinder;
    if (!m_has_load
        || (method = m_epic_asInterface_func) == null
        || (iBinder = this.m_binder) == null) {
      return;
    }
    try {
      Object invoke = method.invoke(null, iBinder);
      this.m_request_obj = invoke;
      if (invoke != null) {
        this.m_handle_obj = m_epic_init_multi_func.invoke(invoke, iArr);
      } else {
        Log.e("EpicObject", "failed to get request_obj");
      }
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
    }
  }

  public boolean acquire_lock() {
    Object obj;
    if (!m_has_load || (obj = this.m_request_obj) == null) {
      return false;
    }
    try {
      return ((Integer) m_epic_request_func.invoke(obj, this.m_handle_obj)).intValue() != 0;
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
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
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
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
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
      return false;
    }
  }

  public boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) {
    Object obj;
    if (!m_has_load || (obj = this.m_request_obj) == null) {
      return false;
    }
    try {
      return ((Integer) m_epic_request_opt_multi_func.invoke(obj, this.m_handle_obj, iArr, iArr2))
              .intValue()
          != 0;
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
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
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
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
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
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
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
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
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(stringWriter));
      Log.e("EpicObject", stringWriter.toString());
      return false;
    }
  }

  public final void create_instance() {
    synchronized (EpicAIDLObject.class) {
      try {
      } catch (Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        Log.e("EpicObject", stringWriter.toString());
      }
      if (m_has_load) {
        return;
      }
      m_epic_request_cls = Class.forName(EPIC_CLASS);
      m_epic_handle_cls = Class.forName(EPIC_HANDLE_CLASS);
      Class<?> cls = Class.forName(EPIC_CLASS_STUB);
      m_epic_request_stub_cls = cls;
      Class<?> cls2 = Integer.TYPE;
      Class<?> cls3 = m_epic_handle_cls;
      m_epic_asInterface_func = cls.getMethod("asInterface", IBinder.class);
      m_epic_init_func = m_epic_request_cls.getMethod("init", cls2);
      m_epic_init_multi_func = m_epic_request_cls.getMethod("init_multi", int[].class);
      m_epic_request_func = m_epic_request_cls.getMethod("acquire_lock", cls3);
      m_epic_release_func = m_epic_request_cls.getMethod("release_lock", cls3);
      m_epic_request_opt_func =
          m_epic_request_cls.getMethod("acquire_lock_option", cls3, cls2, cls2);
      m_epic_request_opt_multi_func =
          m_epic_request_cls.getMethod("acquire_lock_multi_option", cls3, int[].class, int[].class);
      m_epic_request_conditional_func =
          m_epic_request_cls.getMethod("acquire_lock_conditional", cls3, String.class);
      m_epic_release_conditional_func =
          m_epic_request_cls.getMethod("release_lock_conditional", cls3, String.class);
      m_epic_perf_hint_func = m_epic_request_cls.getMethod("perf_hint", cls3, String.class);
      m_epic_hint_release_func = m_epic_request_cls.getMethod("hint_release", cls3, String.class);
      m_has_load = true;
    }
  }

  public final void getService() {
    IBinder service =
        ServiceManager.getService("vendor.samsung_slsi.hardware.epic.IEpicRequest/default");
    this.m_binder = service;
    if (service == null) {
      Log.e("EpicObject", "failed to get service daemon binder");
    }
  }
}
