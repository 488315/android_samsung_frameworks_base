package com.android.systemui.plugins.aod;

import android.util.SparseArray;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginAODSystemUIConfiguration {
    public static final int KEY_EMERGENCY_MODE = 1;
    public static final int KEY_FOLDER_CLOSED = 3;
    public static final int KEY_FULLSCREEN_BOUNCER_STATE = 7;
    public static final int KEY_LAST_SLEEP_REASON = 10;
    public static final int KEY_NOTI_MAP = 8;
    public static final int KEY_OCCLUDED_STATE = 5;
    public static final int KEY_PHONE_STATE = 2;
    public static final int KEY_SHOULD_CONTROL_SCREEN_OFF = 9;
    public static final int KEY_SOM_STATE = 6;
    public static final int KEY_UNLOCKED_STATE = 4;
    private final SparseArray<Object> mData = new SparseArray<>();

    public int get(int i, int i2) {
        synchronized (this.mData) {
            try {
                Object obj = this.mData.get(i);
                if (obj == null || !(obj instanceof Integer)) {
                    return i2;
                }
                return ((Integer) obj).intValue();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public HashMap getNotiMap() {
        synchronized (this.mData) {
            try {
                Object obj = this.mData.get(8);
                if (obj == null || !(obj instanceof HashMap)) {
                    return null;
                }
                return (HashMap) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void set(int i, int i2) {
        synchronized (this.mData) {
            this.mData.put(i, Integer.valueOf(i2));
        }
    }

    public void setNotiMap(HashMap hashMap) {
        synchronized (this.mData) {
            this.mData.put(8, hashMap);
        }
    }

    public void set(int i, boolean z) {
        synchronized (this.mData) {
            this.mData.put(i, Boolean.valueOf(z));
        }
    }

    public boolean get(int i, boolean z) {
        synchronized (this.mData) {
            try {
                Object obj = this.mData.get(i);
                if (obj == null || !(obj instanceof Boolean)) {
                    return z;
                }
                return ((Boolean) obj).booleanValue();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void set(int i, String str) {
        synchronized (this.mData) {
            this.mData.put(i, str);
        }
    }

    public String get(int i, String str) {
        synchronized (this.mData) {
            try {
                Object obj = this.mData.get(i);
                if (obj == null || !(obj instanceof String)) {
                    return str;
                }
                return (String) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
