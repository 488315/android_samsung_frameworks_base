package com.samsung.android.server.corestate;

import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import com.android.server.display.DisplayPowerController2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class CoreStateVolatileObserver {
    public CoreStateObserverController mController;
    public final Handler mHandler;
    public final Map mVolatileStatesRepository = new HashMap();
    public final Map mVolatileStatesToTypeMapForUser = new HashMap();
    public final Map mStringDefaultKeyMap = new HashMap();
    public final Map mIntegerDefaultKeyMap = new HashMap();
    public final Map mFloatDefaultKeyMap = new HashMap();
    public final Map mLongDefaultKeyMap = new HashMap();
    public final Map mBooleanDefaultKeyMap = new HashMap();

    public CoreStateVolatileObserver(Handler handler, CoreStateObserverController coreStateObserverController) {
        this.mHandler = handler;
        this.mController = coreStateObserverController;
        registerObservingItems();
    }

    public void registerObservingItems() {
        Map map = this.mVolatileStatesToTypeMapForUser;
        Class cls = Integer.TYPE;
        map.put("mw_enabled", cls);
        this.mIntegerDefaultKeyMap.put("mw_enabled", 1);
        this.mVolatileStatesToTypeMapForUser.put("dex_font_scale", Float.TYPE);
        this.mFloatDefaultKeyMap.put("dex_font_scale", Float.valueOf(1.0f));
        this.mVolatileStatesToTypeMapForUser.put("mw_blocked_minimized_freeform", cls);
        this.mIntegerDefaultKeyMap.put("mw_blocked_minimized_freeform", 0);
        this.mVolatileStatesToTypeMapForUser.put("stay_focus_activity", cls);
        this.mIntegerDefaultKeyMap.put("stay_focus_activity", 0);
        this.mVolatileStatesToTypeMapForUser.put("stay_top_resumed_activity", cls);
        this.mIntegerDefaultKeyMap.put("stay_top_resumed_activity", 0);
        this.mVolatileStatesToTypeMapForUser.put("custom_density", cls);
        this.mIntegerDefaultKeyMap.put("custom_density", 0);
        this.mVolatileStatesToTypeMapForUser.put("mw_navibar_immersive_mode", cls);
        this.mIntegerDefaultKeyMap.put("mw_navibar_immersive_mode", 0);
        this.mVolatileStatesToTypeMapForUser.put("mw_ensure_launch_split", cls);
        this.mIntegerDefaultKeyMap.put("mw_ensure_launch_split", 0);
        this.mVolatileStatesToTypeMapForUser.put("corner_gesture_custom_value", cls);
        this.mIntegerDefaultKeyMap.put("corner_gesture_custom_value", 0);
    }

    public int populateState(Bundle bundle, int i) {
        return (populate(bundle, this.mVolatileStatesToTypeMapForUser, i) ? 2 : 0) | 0;
    }

    public final boolean populate(Bundle bundle, Map map, int i) {
        boolean z = false;
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            Class cls = (Class) entry.getValue();
            if (cls == String.class) {
                String str2 = (String) this.mStringDefaultKeyMap.get(str);
                if (str2 == null) {
                    str2 = "";
                }
                SparseArray sparseArray = (SparseArray) this.mVolatileStatesRepository.get(str);
                String str3 = sparseArray != null ? (String) sparseArray.get(i, "") : "";
                if (str3 != null && !str3.equals(bundle.getString(str, str2))) {
                    bundle.putString(str, str3);
                    z = true;
                }
            } else if (cls == Integer.TYPE) {
                Integer num = (Integer) this.mIntegerDefaultKeyMap.get(str);
                int intValue = num != null ? num.intValue() : 0;
                SparseArray sparseArray2 = (SparseArray) this.mVolatileStatesRepository.get(str);
                if (sparseArray2 != null) {
                    intValue = ((Integer) sparseArray2.get(i, Integer.valueOf(intValue))).intValue();
                }
                if (intValue != bundle.getInt(str, 0)) {
                    bundle.putInt(str, intValue);
                    z = true;
                }
            } else if (cls == Float.TYPE) {
                Float f = (Float) this.mFloatDefaultKeyMap.get(str);
                float floatValue = f != null ? f.floatValue() : 0.0f;
                SparseArray sparseArray3 = (SparseArray) this.mVolatileStatesRepository.get(str);
                float floatValue2 = sparseArray3 != null ? ((Float) sparseArray3.get(i, Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON))).floatValue() : floatValue;
                if (floatValue2 != bundle.getFloat(str, floatValue)) {
                    bundle.putFloat(str, floatValue2);
                    z = true;
                }
            } else if (cls == Long.TYPE) {
                Long l = (Long) this.mLongDefaultKeyMap.get(str);
                long longValue = l != null ? l.longValue() : 0L;
                SparseArray sparseArray4 = (SparseArray) this.mVolatileStatesRepository.get(str);
                long longValue2 = sparseArray4 != null ? ((Long) sparseArray4.get(i, 0L)).longValue() : longValue;
                if (longValue2 != bundle.getLong(str, longValue)) {
                    bundle.putLong(str, longValue2);
                    z = true;
                }
            } else if (cls == Boolean.TYPE) {
                Boolean bool = (Boolean) this.mBooleanDefaultKeyMap.get(str);
                boolean booleanValue = bool != null ? bool.booleanValue() : false;
                SparseArray sparseArray5 = (SparseArray) this.mVolatileStatesRepository.get(str);
                boolean booleanValue2 = sparseArray5 != null ? ((Boolean) sparseArray5.get(i, Boolean.FALSE)).booleanValue() : false;
                if (booleanValue2 != bundle.getBoolean(str, booleanValue)) {
                    bundle.putBoolean(str, booleanValue2);
                    z = true;
                }
            }
        }
        return z;
    }

    public void removeStatesForUser(int i) {
        Iterator it = this.mVolatileStatesRepository.entrySet().iterator();
        while (it.hasNext()) {
            SparseArray sparseArray = (SparseArray) ((Map.Entry) it.next()).getValue();
            if (sparseArray != null) {
                sparseArray.remove(i);
            }
        }
    }

    public void setState(String str, Object obj, final int i, boolean z, boolean z2, final Runnable runnable) {
        if (((SparseArray) this.mVolatileStatesRepository.get(str)) == null) {
            if (obj instanceof String) {
                this.mVolatileStatesRepository.put(str, new SparseArray());
            } else if (obj instanceof Integer) {
                this.mVolatileStatesRepository.put(str, new SparseArray());
            } else if (obj instanceof Float) {
                this.mVolatileStatesRepository.put(str, new SparseArray());
            } else if (obj instanceof Long) {
                this.mVolatileStatesRepository.put(str, new SparseArray());
            } else if (!(obj instanceof Boolean)) {
                return;
            } else {
                this.mVolatileStatesRepository.put(str, new SparseArray());
            }
        }
        if (obj instanceof String) {
            ((SparseArray) this.mVolatileStatesRepository.get(str)).put(i, (String) obj);
        } else if (obj instanceof Integer) {
            ((SparseArray) this.mVolatileStatesRepository.get(str)).put(i, (Integer) obj);
        } else if (obj instanceof Float) {
            ((SparseArray) this.mVolatileStatesRepository.get(str)).put(i, (Float) obj);
        } else if (obj instanceof Long) {
            ((SparseArray) this.mVolatileStatesRepository.get(str)).put(i, (Long) obj);
        } else if (obj instanceof Boolean) {
            ((SparseArray) this.mVolatileStatesRepository.get(str)).put(i, (Boolean) obj);
        }
        if (z) {
            if (z2) {
                this.mController.onCoreStateChanged(i, runnable);
            } else {
                this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.corestate.CoreStateVolatileObserver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CoreStateVolatileObserver.this.lambda$setState$0(i, runnable);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setState$0(int i, Runnable runnable) {
        this.mController.onCoreStateChanged(i, runnable);
    }
}
