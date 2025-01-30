package com.android.server.appbinding.finders;

import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.appbinding.AppBindingConstants;
import com.android.server.appbinding.AppBindingUtils;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public abstract class AppServiceFinder {
  public final Context mContext;
  public final Handler mHandler;
  public final BiConsumer mListener;
  public final Object mLock = new Object();
  public final SparseArray mTargetPackages = new SparseArray(4);
  public final SparseArray mTargetServices = new SparseArray(4);
  public final SparseArray mLastMessages = new SparseArray(4);

  public abstract IInterface asInterface(IBinder iBinder);

  public abstract String getAppDescription();

  public abstract int getBindFlags(AppBindingConstants appBindingConstants);

  public abstract String getServiceAction();

  public abstract Class getServiceClass();

  public abstract String getServicePermission();

  public abstract String getTargetPackage(int i);

  public abstract boolean isEnabled(AppBindingConstants appBindingConstants);

  public abstract void startMonitoring();

  public abstract String validateService(ServiceInfo serviceInfo);

  public AppServiceFinder(Context context, BiConsumer biConsumer, Handler handler) {
    this.mContext = context;
    this.mListener = biConsumer;
    this.mHandler = handler;
  }

  public void onUserRemoved(int i) {
    synchronized (this.mLock) {
      this.mTargetPackages.delete(i);
      this.mTargetServices.delete(i);
      this.mLastMessages.delete(i);
    }
  }

  public final ServiceInfo findService(
      int i, IPackageManager iPackageManager, AppBindingConstants appBindingConstants) {
    synchronized (this.mLock) {
      this.mTargetPackages.put(i, null);
      this.mTargetServices.put(i, null);
      this.mLastMessages.put(i, null);
      if (!isEnabled(appBindingConstants)) {
        this.mLastMessages.put(i, "feature disabled");
        Slog.i("AppBindingService", getAppDescription() + " feature disabled");
        return null;
      }
      String targetPackage = getTargetPackage(i);
      if (targetPackage == null) {
        this.mLastMessages.put(i, "Target package not found");
        Slog.w("AppBindingService", getAppDescription() + " u" + i + " Target package not found");
        return null;
      }
      this.mTargetPackages.put(i, targetPackage);
      StringBuilder sb = new StringBuilder();
      ServiceInfo findService =
          AppBindingUtils.findService(
              targetPackage,
              i,
              getServiceAction(),
              getServicePermission(),
              getServiceClass(),
              iPackageManager,
              sb);
      if (findService == null) {
        this.mLastMessages.put(i, sb.toString());
        return null;
      }
      String validateService = validateService(findService);
      if (validateService != null) {
        this.mLastMessages.put(i, validateService);
        Log.e("AppBindingService", validateService);
        return null;
      }
      this.mLastMessages.put(i, "Valid service found");
      this.mTargetServices.put(i, findService);
      return findService;
    }
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.print(str);
    printWriter.print("App type: ");
    printWriter.print(getAppDescription());
    printWriter.println();
    synchronized (this.mLock) {
      for (int i = 0; i < this.mTargetPackages.size(); i++) {
        int keyAt = this.mTargetPackages.keyAt(i);
        printWriter.print(str);
        printWriter.print("  User: ");
        printWriter.print(keyAt);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("    Package: ");
        printWriter.print((String) this.mTargetPackages.get(keyAt));
        printWriter.println();
        printWriter.print(str);
        printWriter.print("    Service: ");
        printWriter.print(this.mTargetServices.get(keyAt));
        printWriter.println();
        printWriter.print(str);
        printWriter.print("    Message: ");
        printWriter.print((String) this.mLastMessages.get(keyAt));
        printWriter.println();
      }
    }
  }

  public void dumpSimple(PrintWriter printWriter) {
    synchronized (this.mLock) {
      for (int i = 0; i < this.mTargetPackages.size(); i++) {
        int keyAt = this.mTargetPackages.keyAt(i);
        printWriter.print("finder,");
        printWriter.print(getAppDescription());
        printWriter.print(",");
        printWriter.print(keyAt);
        printWriter.print(",");
        printWriter.print((String) this.mTargetPackages.get(keyAt));
        printWriter.print(",");
        printWriter.print(this.mTargetServices.get(keyAt));
        printWriter.print(",");
        printWriter.print((String) this.mLastMessages.get(keyAt));
        printWriter.println();
      }
    }
  }
}
