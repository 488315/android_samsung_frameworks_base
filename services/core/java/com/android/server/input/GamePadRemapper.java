package com.android.server.input;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GamePadRemapper {
    public static final Set SIMPLE_BUTTON_LIST;
    public static final Set SIMPLE_STICK_LIST;
    public final PersistentDataStore mDataStore;
    public final Object mDeviceToProfileLock = new Object();
    public final Map mDeviceToProfile = new HashMap();

    static {
        ArraySet arraySet = new ArraySet();
        SIMPLE_BUTTON_LIST = arraySet;
        arraySet.add(96);
        arraySet.add(97);
        arraySet.add(99);
        arraySet.add(100);
        arraySet.add(102);
        arraySet.add(103);
        arraySet.add(106);
        arraySet.add(107);
        arraySet.add(2065);
        arraySet.add(2066);
        ArraySet arraySet2 = new ArraySet();
        SIMPLE_STICK_LIST = arraySet2;
        arraySet2.add(2048);
        arraySet2.add(2059);
        arraySet2.add(2063);
    }

    public GamePadRemapper(PersistentDataStore persistentDataStore) {
        this.mDataStore = persistentDataStore;
    }

    public static final String getButtonString(int i) {
        return i != 96 ? i != 97 ? i != 99 ? i != 100 ? i != 102 ? i != 103 ? i != 106 ? i != 107 ? i != 2048 ? i != 2059 ? i != 2063 ? i != 2065 ? i != 2066 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Wrong button ") : "BUTTON_RTRIGGER" : "BUTTON_LTRIGGER" : "STICK_DPAD" : "STICK_RIGHT" : "STICK_LEFT" : "BUTTON_THUMBR" : "BUTTON_THUMBL" : "BUTTON_R2" : "BUTTON_L1" : "BUTTON_Y" : "BUTTON_X" : "BUTTON_B" : "BUTTON_A";
    }

    public static boolean isValidButton(int i) {
        return i == 96 || i == 97 || i == 99 || i == 100 || i == 102 || i == 103 || i == 106 || i == 107 || i == 2065 || i == 2066;
    }

    public static boolean isValidId(int i) {
        return i >= 0 && i < 5;
    }

    public final void systemRunning() {
        synchronized (this.mDeviceToProfileLock) {
            ((HashMap) this.mDeviceToProfile).clear();
        }
        synchronized (this.mDataStore) {
            this.mDataStore.loadIfNeededGamePadProfiles();
        }
    }
}
