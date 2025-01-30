package com.android.server.om;

import android.content.om.CriticalOverlayInfo;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayInfoExt;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.List;

/* loaded from: classes2.dex */
public interface IOverlayManagerExt {
    void addOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i);

    boolean changeOverlayState(String str, int i, boolean z);

    OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2);

    OverlayInfoExt getOverlayForPath(String str, int i);

    OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2);

    String getTargetPath(String str);

    int handleStateUpdate(AndroidPackage androidPackage, CriticalOverlayInfo criticalOverlayInfo, int i, int i2);

    void handleUserSwitch(int i);

    void removeOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i);

    void replaceOverlays(List list, List list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i);

    void setIsInitonBoot(boolean z);
}
