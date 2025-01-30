package com.android.server.notification.edgelighting.policy;

import android.os.Debug;
import android.util.Slog;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class EdgeLightingPolicyRepository {
  public static final boolean DEBUG = Debug.semIsProductDev();
  public HashMap mRepository = new HashMap();

  public void updatePolicy(EdgeLightingPolicyInfo edgeLightingPolicyInfo) {
    if (DEBUG) {
      Slog.d("EdgeLightingPolicyRepository", "updatePolicy " + edgeLightingPolicyInfo.toString());
    }
    synchronized (this.mRepository) {
      this.mRepository.put(edgeLightingPolicyInfo.packageName, edgeLightingPolicyInfo);
    }
  }

  public void clear() {
    synchronized (this.mRepository) {
      this.mRepository.clear();
    }
  }

  public EdgeLightingPolicyInfo getPolicyInfoWithPackageName(String str) {
    EdgeLightingPolicyInfo edgeLightingPolicyInfo;
    synchronized (this.mRepository) {
      edgeLightingPolicyInfo = (EdgeLightingPolicyInfo) this.mRepository.get(str);
    }
    return edgeLightingPolicyInfo;
  }

  public int size() {
    int size;
    synchronized (this.mRepository) {
      size = this.mRepository.size();
    }
    return size;
  }

  public void dump(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, String str) {
    printWriter.println("-EdgeLightingPolicyInfo(" + str + ") start");
    synchronized (this.mRepository) {
      Iterator it = this.mRepository.entrySet().iterator();
      while (it.hasNext()) {
        printWriter.println(
            "  " + ((EdgeLightingPolicyInfo) ((Map.Entry) it.next()).getValue()).toString());
      }
      printWriter.println("");
    }
  }
}
