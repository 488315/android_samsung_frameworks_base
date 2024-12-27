package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;

import java.io.File;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IdmapManagerWrapper {
    public final IdmapDaemon mIdmapDaemon;
    public final IdmapManager mIdmapManager;

    public IdmapManagerWrapper(IdmapManager idmapManager, IdmapDaemon idmapDaemon) {
        this.mIdmapManager = idmapManager;
        this.mIdmapDaemon = idmapDaemon;
    }

    public final int createIdmap(
            AndroidPackage androidPackage, OverlayInfoExt overlayInfoExt, int i) {
        OverlayInfo overlayInfo;
        if (androidPackage == null
                || androidPackage.getBaseApkPath() == null
                || (overlayInfo = overlayInfoExt.info) == null
                || overlayInfo.baseCodePath == null) {
            Slog.e(
                    "OverlayManagerExt",
                    "Unable to create idmap for " + androidPackage + " ov=" + overlayInfoExt);
            return 0;
        }
        String path = ((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath();
        if (androidPackage.getMetaData() != null
                && androidPackage.getMetaData().containsKey("resource-map")) {
            if (!new File("/data/overlays/remaps/" + path.replace("/", ".") + ".map").exists()) {
                ResourceMapParser.parseResourceMap(androidPackage);
            }
        }
        try {
            try {
                IdmapDaemon idmapDaemon = this.mIdmapDaemon;
                OverlayInfo overlayInfo2 = overlayInfoExt.info;
                if (idmapDaemon.verifyIdmap(
                        path, 1, overlayInfo2.baseCodePath, overlayInfo2.overlayName, i, false)) {
                    return 1;
                }
            } catch (Exception e) {
                Slog.e(
                        "OverlayManagerExt",
                        "Couldnt verify idmap. Will create it - " + e.getMessage());
            }
            IdmapDaemon idmapDaemon2 = this.mIdmapDaemon;
            OverlayInfo overlayInfo3 = overlayInfoExt.info;
            return idmapDaemon2.createIdmap(
                                    path,
                                    1,
                                    overlayInfo3.baseCodePath,
                                    overlayInfo3.overlayName,
                                    i,
                                    false)
                            != null
                    ? 3
                    : 0;
        } catch (RemoteException | TimeoutException e2) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "failed to generate idmap for ", path, " and ");
            m.append(overlayInfoExt.info.baseCodePath);
            Slog.e("OverlayManagerExt", m.toString(), e2);
            return 128;
        }
    }
}
