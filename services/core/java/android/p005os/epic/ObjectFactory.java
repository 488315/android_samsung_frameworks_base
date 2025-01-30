package android.p005os.epic;

import android.os.ServiceManager;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ObjectFactory {
  public static final String EPIC_AIDL_CLASS =
      "vendor.samsung_slsi.hardware.epic.IEpicRequest/default";
  public static final String EPIC_HIDL_CLASS =
      "vendor.samsung_slsi.hardware.epic.V1_0.IEpicRequest";
  public static boolean m_has_load = false;
  public static Method m_hidl_epic_getService_func;
  public static Class m_hidl_epic_request_cls;

  public static boolean loadAIDL() {
    return ServiceManager.getService("vendor.samsung_slsi.hardware.epic.IEpicRequest/default")
        != null;
  }

  public static boolean loadHIDL() {
    synchronized (ObjectFactory.class) {
      try {
        if (m_hidl_epic_request_cls == null && !m_has_load) {
          m_has_load = true;
          Class<?> cls = Class.forName("vendor.samsung_slsi.hardware.epic.V1_0.IEpicRequest");
          m_hidl_epic_request_cls = cls;
          m_hidl_epic_getService_func = cls.getMethod("getService", new Class[0]);
        }
      } catch (Exception unused) {
      }
    }
    try {
      Method method = m_hidl_epic_getService_func;
      if (method == null) {
        return false;
      }
      return method.invoke(null, new Object[0]) != null;
    } catch (Exception unused2) {
      Log.e("ObjectFactory", "failed to get HIDL request");
      return false;
    }
  }

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
}
