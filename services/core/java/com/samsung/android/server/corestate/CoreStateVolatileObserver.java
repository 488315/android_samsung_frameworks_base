package com.samsung.android.server.corestate;

import android.os.Handler;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoreStateVolatileObserver {
    public final Map mBooleanDefaultKeyMap;
    public final CoreStateObserverController mController;
    public final Map mFloatDefaultKeyMap;
    public final Handler mHandler;
    public final Map mIntegerDefaultKeyMap;
    public final Map mLongDefaultKeyMap;
    public final Map mStringDefaultKeyMap;
    public final Map mVolatileStatesRepository = new HashMap();
    public final Map mVolatileStatesToTypeMapForUser;

    public CoreStateVolatileObserver(Handler handler, CoreStateObserverController coreStateObserverController) {
        HashMap hashMap = new HashMap();
        this.mVolatileStatesToTypeMapForUser = hashMap;
        this.mStringDefaultKeyMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        this.mIntegerDefaultKeyMap = hashMap2;
        HashMap hashMap3 = new HashMap();
        this.mFloatDefaultKeyMap = hashMap3;
        this.mLongDefaultKeyMap = new HashMap();
        this.mBooleanDefaultKeyMap = new HashMap();
        this.mHandler = handler;
        this.mController = coreStateObserverController;
        Class cls = Integer.TYPE;
        hashMap.put("mw_enabled", cls);
        hashMap2.put("mw_enabled", 1);
        hashMap.put("dex_font_scale", Float.TYPE);
        hashMap3.put("dex_font_scale", Float.valueOf(1.0f));
        hashMap.put("mw_navibar_immersive_mode", cls);
        hashMap2.put("mw_navibar_immersive_mode", 0);
        hashMap.put("stay_focus_activity", cls);
        hashMap2.put("stay_focus_activity", 0);
        hashMap.put("stay_top_resumed_activity", cls);
        hashMap2.put("stay_top_resumed_activity", 0);
        hashMap.put("custom_density", cls);
        hashMap2.put("custom_density", 0);
        hashMap.put("mw_blocked_minimized_freeform", cls);
        hashMap2.put("mw_blocked_minimized_freeform", 0);
        hashMap.put("mw_ensure_launch_split", cls);
        hashMap2.put("mw_ensure_launch_split", 0);
        hashMap.put("corner_gesture_custom_value", cls);
        hashMap2.put("corner_gesture_custom_value", 0);
    }
}
