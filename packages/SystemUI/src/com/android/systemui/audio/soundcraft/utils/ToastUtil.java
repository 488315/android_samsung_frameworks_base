package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public final class ToastUtil {
    public static final ToastUtil INSTANCE = new ToastUtil();
    public static Toast toast;

    private ToastUtil() {
    }

    public static void makeToast(final Context context, final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.audio.soundcraft.utils.ToastUtil$makeToast$1
            @Override // java.lang.Runnable
            public final void run() {
                Toast toast2 = ToastUtil.toast;
                if (toast2 != null) {
                    toast2.cancel();
                }
                Toast makeText = Toast.makeText(context, str, 0);
                ToastUtil.toast = makeText;
                if (makeText != null) {
                    makeText.show();
                }
            }
        });
    }
}
