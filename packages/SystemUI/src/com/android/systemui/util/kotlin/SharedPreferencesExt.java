package com.android.systemui.util.kotlin;

import android.content.SharedPreferences;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

public final class SharedPreferencesExt {
    public static final int $stable = 0;
    public static final SharedPreferencesExt INSTANCE = new SharedPreferencesExt();

    private SharedPreferencesExt() {
    }

    public final Flow observe(SharedPreferences sharedPreferences) {
        return FlowConflatedKt.conflatedCallbackFlow(new SharedPreferencesExt$observe$1(sharedPreferences, null));
    }
}
