package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class CameraInFgsFilter implements IFilter {
  public final CameraManager.SemCameraDeviceStateCallback mCameraDeviceStateCallback;
  public CameraManager mCameraManager;
  public Set mCameraUsingList;

  public abstract class CameraInFgsFilterHolder {
    public static final CameraInFgsFilter INSTANCE = new CameraInFgsFilter();
  }

  public CameraInFgsFilter() {
    this.mCameraUsingList = new HashSet();
    this.mCameraDeviceStateCallback =
        new CameraManager
            .SemCameraDeviceStateCallback() { // from class:
                                              // com.android.server.am.mars.filter.filter.CameraInFgsFilter.1
          public void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
            if ("android.system".equals(str2) || "com.sec.android.app.camera".equals(str2)) {
              return;
            }
            if (i2 == 1) {
              CameraInFgsFilter.this.mCameraUsingList.add(str2);
            } else if (i2 == 3) {
              CameraInFgsFilter.this.mCameraUsingList.remove(str2);
            }
          }
        };
  }

  public static CameraInFgsFilter getInstance() {
    return CameraInFgsFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    if (this.mCameraManager == null) {
      this.mCameraManager = (CameraManager) context.getSystemService("camera");
    }
    this.mCameraManager.registerSemCameraDeviceStateCallback(this.mCameraDeviceStateCallback, null);
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    CameraManager cameraManager = this.mCameraManager;
    if (cameraManager != null) {
      cameraManager.unregisterSemCameraDeviceStateCallback(this.mCameraDeviceStateCallback);
    }
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    return (FreecessController.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE
            && MARsPolicyManager.getInstance().isChinaPolicyEnabled()
            && MARsPolicyManager.getInstance().isForegroundServicePkg(i2)
            && isUsingCamera(str))
        ? 29
        : 0;
  }

  public boolean isUsingCamera(String str) {
    Set set = this.mCameraUsingList;
    return set != null && set.contains(str);
  }
}
