package com.android.systemui.util.kotlin;

import android.content.SharedPreferences;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SharedPreferencesExt {
    public static final int $stable = 0;
    public static final SharedPreferencesExt INSTANCE = new SharedPreferencesExt();

    private SharedPreferencesExt() {
    }

    public final Flow observe(SharedPreferences sharedPreferences) {
        return FlowConflatedKt.conflatedCallbackFlow(new SharedPreferencesExt$observe$1(sharedPreferences, null));
    }
}
