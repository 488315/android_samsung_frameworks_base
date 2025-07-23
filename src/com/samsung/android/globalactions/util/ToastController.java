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

    public void setInterceptor(Consumer consumer) {
        this.mInterceptor = consumer;
    }

    public void showToast(String str, int i) {
        Consumer<String> consumer = this.mInterceptor;
        if (consumer != null) {
            consumer.accept(str);
        } else {
            Toast.makeText(this.mContext, str, i).show();
        }
    }

    public void showToast(int i, int i2) {
        Toast.makeText(this.mContext, i, i2).show();
    }
}
