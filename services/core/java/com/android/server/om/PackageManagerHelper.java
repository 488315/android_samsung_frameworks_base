package com.android.server.om;

import android.content.om.OverlayableInfo;
import android.util.ArrayMap;
import com.android.server.pm.pkg.PackageState;
import java.util.Map;

/* loaded from: classes2.dex */
public interface PackageManagerHelper {
    boolean doesTargetDefineOverlayable(String str, int i);

    void enforcePermission(String str, String str2);

    String getConfigSignaturePackage();

    Map getNamedActors();

    OverlayableInfo getOverlayableForTarget(String str, String str2, int i);

    PackageState getPackageStateForUser(String str, int i);

    String[] getPackagesForUid(int i);

    ArrayMap initializeForUser(int i);

    boolean signaturesMatching(String str, String str2, int i);
}
