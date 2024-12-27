package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CameraInFgsFilter implements IFilter {
    public CameraManager mCameraManager;
    public final Set mCameraUsingList = new HashSet();
    public final AnonymousClass1 mCameraDeviceStateCallback = new CameraManager.SemCameraDeviceStateCallback() { // from class: com.android.server.am.mars.filter.filter.CameraInFgsFilter.1
        public final void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
            if ("android.system".equals(str2) || "com.sec.android.app.camera".equals(str2)) {
                return;
            }
            if (i2 == 1) {
                ((HashSet) CameraInFgsFilter.this.mCameraUsingList).add(str2);
            } else if (i2 == 3) {
                ((HashSet) CameraInFgsFilter.this.mCameraUsingList).remove(str2);
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class CameraInFgsFilterHolder {
        public static final CameraInFgsFilter INSTANCE = new CameraInFgsFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        CameraManager cameraManager = this.mCameraManager;
        if (cameraManager != null) {
            cameraManager.unregisterSemCameraDeviceStateCallback(this.mCameraDeviceStateCallback);
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        Set set;
        if (!MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE || !MARsUtils.isChinaPolicyEnabled()) {
            return 0;
        }
        boolean z = MARsPolicyManager.MARs_ENABLE;
        return (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getForegroundServiceStartTime(i2) == 0 || (set = this.mCameraUsingList) == null || !((HashSet) set).contains(str)) ? 0 : 29;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        if (this.mCameraManager == null) {
            this.mCameraManager = (CameraManager) context.getSystemService("camera");
        }
        this.mCameraManager.registerSemCameraDeviceStateCallback(this.mCameraDeviceStateCallback, null);
    }
}
