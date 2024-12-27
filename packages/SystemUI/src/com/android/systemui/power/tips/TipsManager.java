package com.android.systemui.power.tips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.PowerUiRune;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TipsManager {
    public final Context context;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TipsManager(Context context) {
        this.context = context;
    }

    public final void checkIntentAction(Intent intent) {
        String stringExtra;
        String action = intent.getAction();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        SharedPreferences.Editor edit = sharedPreferences != null ? sharedPreferences.edit() : null;
        if (!PowerUiRune.TIPS_NOTIFICATION || edit == null || action == null) {
            return;
        }
        int hashCode = action.hashCode();
        if (hashCode == -2069161002) {
            if (action.equals("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI")) {
                edit.putBoolean("ignoreRUT", true);
                edit.commit();
                return;
            }
            return;
        }
        if (hashCode == -2015668905) {
            if (action.equals("com.samsung.android.sm.CLEAR_TIPS_NOTI")) {
                edit.putBoolean("tipsNotiConfirmed", false);
                edit.putBoolean("ignoreRUT", false);
                edit.putInt("tipsNotiRegisteredCount", 0);
                edit.putLong("tipsNotiLastTime", 0L);
                edit.commit();
                return;
            }
            return;
        }
        if (hashCode == 420653048 && action.equals("android.intent.action.tips.noti.confirmed") && (stringExtra = intent.getStringExtra("tips_action_confirmed_id")) != null && StringsKt__StringsKt.contains(stringExtra, "120999", false)) {
            Log.i("PowerUI.TipsManager", "TIPS_NOTI_HIGH_REFRESH was clicked, so we set preference !!");
            edit.putBoolean("tipsNotiConfirmed", true);
            edit.commit();
        }
    }
}
