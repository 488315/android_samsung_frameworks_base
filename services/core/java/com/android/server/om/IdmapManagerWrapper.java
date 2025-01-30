package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import java.io.File;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public final class IdmapManagerWrapper {
    public IdmapDaemon mIdmapDaemon;
    public final IdmapManager mIdmapManager;

    public IdmapManagerWrapper(IdmapManager idmapManager, IdmapDaemon idmapDaemon) {
        this.mIdmapManager = idmapManager;
        this.mIdmapDaemon = idmapDaemon;
    }

    public int createIdmap(AndroidPackage androidPackage, OverlayInfoExt overlayInfoExt, int i) {
        OverlayInfo overlayInfo;
        if (androidPackage == null || androidPackage.getBaseApkPath() == null || (overlayInfo = overlayInfoExt.info) == null || overlayInfo.baseCodePath == null) {
            Slog.e("OverlayManagerExt", "Unable to create idmap for " + androidPackage + " ov=" + overlayInfoExt);
            return 0;
        }
        String path = ((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath();
        checkForResourceMapping(androidPackage, path);
        try {
            try {
                IdmapDaemon idmapDaemon = this.mIdmapDaemon;
                OverlayInfo overlayInfo2 = overlayInfoExt.info;
                if (idmapDaemon.verifyIdmap(path, overlayInfo2.baseCodePath, overlayInfo2.overlayName, 1, false, i)) {
                    return 1;
                }
            } catch (Exception e) {
                Slog.e("OverlayManagerExt", "Couldnt verify idmap. Will create it - " + e.getMessage());
            }
            IdmapDaemon idmapDaemon2 = this.mIdmapDaemon;
            OverlayInfo overlayInfo3 = overlayInfoExt.info;
            return idmapDaemon2.createIdmap(path, overlayInfo3.baseCodePath, overlayInfo3.overlayName, 1, false, i) != null ? 3 : 0;
        } catch (RemoteException | TimeoutException e2) {
            Slog.e("OverlayManagerExt", "failed to generate idmap for " + path + " and " + overlayInfoExt.info.baseCodePath, e2);
            return 0;
        }
    }

    public boolean removeIdmap(OverlayInfo overlayInfo, int i) {
        return this.mIdmapManager.removeIdmap(overlayInfo, i);
    }

    public boolean idmapExists(OverlayInfo overlayInfo) {
        return this.mIdmapDaemon.idmapExists(overlayInfo.baseCodePath, overlayInfo.userId);
    }

    public static void checkForResourceMapping(AndroidPackage androidPackage, String str) {
        if (androidPackage == null || androidPackage.getMetaData() == null || !androidPackage.getMetaData().containsKey("resource-map")) {
            return;
        }
        if (new File("/data/overlays/remaps/" + str.replace("/", ".") + ".map").exists()) {
            return;
        }
        ResourceMapParser.parseResourceMap(androidPackage);
    }

    public String getTargetPath(String str) {
        Slog.d("OverlayManagerExt", "getTargetPath for " + str);
        try {
            return this.mIdmapDaemon.getTargetPath(str);
        } catch (Exception e) {
            Slog.w("OverlayManagerExt", "failed to getTargetPath for " + str, e);
            return null;
        }
    }
}
