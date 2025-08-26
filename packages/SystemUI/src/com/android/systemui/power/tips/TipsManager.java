package com.android.systemui.power.tips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.PowerUiRune;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class TipsManager {
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        SharedPreferences.Editor editorEdit = sharedPreferences != null ? sharedPreferences.edit() : null;
        if (!PowerUiRune.TIPS_NOTIFICATION || editorEdit == null || action == null) {
            return;
        }
        int iHashCode = action.hashCode();
        if (iHashCode == -2069161002) {
            if (action.equals("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI")) {
                editorEdit.putBoolean("ignoreRUT", true);
                editorEdit.commit();
                return;
            }
            return;
        }
        if (iHashCode == -2015668905) {
            if (action.equals("com.samsung.android.sm.CLEAR_TIPS_NOTI")) {
                editorEdit.putBoolean("tipsNotiConfirmed", false);
                editorEdit.putBoolean("ignoreRUT", false);
                editorEdit.putInt("tipsNotiRegisteredCount", 0);
                editorEdit.putLong("tipsNotiLastTime", 0L);
                editorEdit.commit();
                return;
            }
            return;
        }
        if (iHashCode == 420653048 && action.equals("android.intent.action.tips.noti.confirmed") && (stringExtra = intent.getStringExtra("tips_action_confirmed_id")) != null && StringsKt__StringsKt.contains(stringExtra, "120999", false)) {
            Log.i("PowerUI.TipsManager", "TIPS_NOTI_HIGH_REFRESH was clicked, so we set preference !!");
            editorEdit.putBoolean("tipsNotiConfirmed", true);
            editorEdit.commit();
        }
    }
}
