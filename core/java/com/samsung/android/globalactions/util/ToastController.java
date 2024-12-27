package com.samsung.android.globalactions.util;

import android.content.Context;
import android.widget.Toast;

import java.util.function.Consumer;

/* loaded from: classes6.dex */
public class ToastController {
    private final Context mContext;
    private Consumer<String> mInterceptor = null;

    public ToastController(Context context) {
        this.mContext = context;
    }

    public void setInterceptor(Consumer interceptor) {
        this.mInterceptor = interceptor;
    }

    public void showToast(String msg, int duration) {
        if (this.mInterceptor != null) {
            this.mInterceptor.accept(msg);
        } else {
            Toast.makeText(this.mContext, msg, duration).show();
        }
    }

    public void showToast(int stringID, int duration) {
        Toast.makeText(this.mContext, stringID, duration).show();
    }
}
