package android.p009os;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class LazyService extends ILazyService.Stub {
  private static final String TAG = "LazyService";
  protected static List<String> historyServices = Collections.synchronizedList(new ArrayList());
  private Context mContext;
  private Map<String, IServiceCreator> serviceCreators = new ArrayMap();
  private List<String> services = new ArrayList();

  public LazyService(Context context) {
    this.mContext = context;
  }

  @Override // android.p009os.ILazyService
  public IBinder getService(String name) {
    long time = System.currentTimeMillis();
    synchronized (this) {
      IServiceCreator creator = this.serviceCreators.get(name);
      if (creator == null) {
        return null;
      }
      this.serviceCreators.remove(name);
      Log.m94d(TAG, name + " getService");
      long token = Binder.clearCallingIdentity();
      IBinder binder = creator.createService(this.mContext);
      Binder.restoreCallingIdentity(token);
      try {
        ServiceManager.addService(name, binder);
      } catch (Throwable e) {
        Log.m97e(TAG, "Failure adding " + name + " Service", e);
        historyServices.add("Failure adding " + name + " Service, Exception : " + e.toString());
        binder = null;
      }
      Log.m94d(TAG, (System.currentTimeMillis() - time) + "ms");
      historyServices.add(
          "GetService " + name + " Service : " + (System.currentTimeMillis() - time) + "ms");
      return binder;
    }
  }

  public synchronized void addService(String name, IServiceCreator creator) {
    if (this.serviceCreators.get(name) != null) {
      Log.m96e(TAG, name + " is already in lazy service manager");
      throw new IllegalArgumentException("DuplicatedName");
    }
    this.services.add(name);
    this.serviceCreators.put(name, creator);
  }

  public void addService(String name, Class type) {
    try {
      addService(name, new DefaultServiceCreator(type));
      Log.m94d(TAG, name + " addService");
    } catch (NoSuchMethodException e) {
      Log.m97e(TAG, name + " error in addService", e);
      historyServices.add(name + " error in addService : " + e.toString());
    }
  }

  @Override // android.p009os.Binder
  protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
    pw.print("# Service(lazy) List : ");
    pw.println();
    for (String item : this.services) {
      pw.print(item + " ");
      pw.println();
    }
    pw.println();
    pw.print("# Uninitialized services : ");
    pw.println();
    synchronized (this) {
      for (String key : this.serviceCreators.keySet()) {
        pw.print(key + " ");
        pw.println();
      }
    }
    pw.println();
    pw.print("# Logs :");
    pw.println();
    for (String log : historyServices) {
      pw.print(log);
      pw.println();
    }
  }

  public static class DefaultServiceCreator implements IServiceCreator {
    private Constructor mConstructor;

    public DefaultServiceCreator(Class type) throws NoSuchMethodException {
      this.mConstructor = type.getConstructor(Context.class);
    }

    @Override // android.p009os.IServiceCreator
    public IBinder createService(Context context) {
      try {
        IBinder binder = (IBinder) this.mConstructor.newInstance(context);
        return binder;
      } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
        Log.m97e(LazyService.TAG, "Error in createService (DefaultServiceCreator)", e);
        LazyService.historyServices.add("Failure creating Exception : " + e.toString());
        return null;
      }
    }
  }
}
