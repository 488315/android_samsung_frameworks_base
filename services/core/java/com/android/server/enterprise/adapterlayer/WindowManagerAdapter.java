package com.android.server.enterprise.adapterlayer;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapter.IWindowManagerAdapter;
import com.samsung.android.view.SemWindowManager;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WindowManagerAdapter implements IWindowManagerAdapter {
    public static final List PRESS_TYPES_TO_BLOCK = Arrays.asList(2, 1);
    public static WindowManagerAdapter sInstance;
    public final IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();

    public static String keyCustomizationInfoToString(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, "priority/keyCode/action/dispatching/intent : ");
        m.append(keyCustomizationInfo.id);
        m.append("/");
        m.append(keyCustomizationInfo.keyCode);
        m.append("/");
        m.append(keyCustomizationInfo.action);
        m.append("/");
        m.append(keyCustomizationInfo.dispatching);
        m.append("/");
        m.append(keyCustomizationInfo.intent);
        return m.toString();
    }
}
