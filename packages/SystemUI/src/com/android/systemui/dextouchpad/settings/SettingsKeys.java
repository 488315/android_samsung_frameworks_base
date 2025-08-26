package com.android.systemui.dextouchpad.settings;

import androidx.collection.ArrayMap;
import com.android.systemui.dextouchpad.util.Features;

/* loaded from: classes2.dex */
public class SettingsKeys {
    public static final ArrayMap STRING_TO_KEY;
    public static final Settings$Key TOUCHPAD_AUTO_RUN_GUIDE_COUNT;
    public static final Settings$Key TOUCHPAD_STARTING_GUIDE;

    static {
        ArrayMap arrayMap = new ArrayMap();
        STRING_TO_KEY = arrayMap;
        Settings$KeyBuilder settings$KeyBuilder = new Settings$KeyBuilder(Integer.class, "touchpad_auto_run_guide_count", Integer.toString(0));
        Class cls = settings$KeyBuilder.mType;
        String str = settings$KeyBuilder.mDefValue;
        String str2 = settings$KeyBuilder.mName;
        Settings$Key settings$Key = new Settings$Key(cls, str2, str);
        boolean z = Features.DEBUG;
        if (z) {
            arrayMap.put(str2, settings$Key);
        }
        TOUCHPAD_AUTO_RUN_GUIDE_COUNT = settings$Key;
        Settings$KeyBuilder settings$KeyBuilder2 = new Settings$KeyBuilder(String.class, "touchpad_starting_guide", null);
        Class cls2 = settings$KeyBuilder2.mType;
        String str3 = settings$KeyBuilder2.mDefValue;
        String str4 = settings$KeyBuilder2.mName;
        Settings$Key settings$Key2 = new Settings$Key(cls2, str4, str3);
        if (z) {
            arrayMap.put(str4, settings$Key2);
        }
        TOUCHPAD_STARTING_GUIDE = settings$Key2;
    }

    private SettingsKeys() {
    }
}
