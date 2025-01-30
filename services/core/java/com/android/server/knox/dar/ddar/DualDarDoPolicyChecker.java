package com.android.server.knox.dar.ddar;

import android.content.Context;
import android.util.Log;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DualDarDoPolicyChecker {
  public static Context sContext;
  public static DualDarDoPolicyChecker sInstance;
  public List skippedPackages;

  public DualDarDoPolicyChecker() {
    init();
  }

  public static synchronized DualDarDoPolicyChecker getInstance(Context context) {
    DualDarDoPolicyChecker dualDarDoPolicyChecker;
    synchronized (DualDarDoPolicyChecker.class) {
      sContext = context;
      if (sInstance == null) {
        sInstance = new DualDarDoPolicyChecker();
      }
      dualDarDoPolicyChecker = sInstance;
    }
    return dualDarDoPolicyChecker;
  }

  public final void init() {
    this.skippedPackages = loadPackages();
  }

  public final List loadPackages() {
    return new ArrayList();
  }

  public final void addPackageToSkipList(String str) {
    this.skippedPackages.add(str);
  }

  public void saveSkippedPackages(List list) {
    if (list == null && list.isEmpty()) {
      return;
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      addPackageToSkipList((String) it.next());
    }
  }

  public final boolean isSkippedPackage(String str) {
    List list = this.skippedPackages;
    if (list == null || list.isEmpty()) {
      return false;
    }
    return this.skippedPackages.contains(str);
  }

  public void checkDualDarDoPolicy(int i) {
    if (!DualDarManager.isOnDeviceOwner(i)) {
      Log.e("DualDarDoPolicyChecker", "Not a DualDAR at DO user - " + i);
      return;
    }
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    EnterprisePartitionManager enterprisePartitionManager =
        EnterprisePartitionManager.getInstance(sContext);
    try {
      for (File file : new File("/data/user/" + i).listFiles()) {
        if (file.isDirectory()) {
          String name = file.getName();
          if (!isSkippedPackage(name)
              && !enterprisePartitionManager.hasDualDARPolicyRecursively(
                  file.getCanonicalPath(), arrayList)) {
            Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on user directory : " + name);
          }
        }
      }
      if (enterprisePartitionManager.hasDualDARPolicyRecursively(
          "/data/media/" + i + "/Android/data", arrayList2)) {
        return;
      }
      Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on media directory");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
