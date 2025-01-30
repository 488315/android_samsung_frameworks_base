package com.android.systemui.plugins.omni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SimpleBroadcastReceiver extends BroadcastReceiver {
    private final Consumer<Intent> mIntentConsumer;

    public SimpleBroadcastReceiver(Consumer<Intent> consumer) {
        this.mIntentConsumer = consumer;
    }

    private static IntentFilter getFilter(String... strArr) {
        IntentFilter intentFilter = new IntentFilter();
        for (String str : strArr) {
            intentFilter.addAction(str);
        }
        return intentFilter;
    }

    public static IntentFilter getPackageFilter(String str, String... strArr) {
        IntentFilter filter = getFilter(strArr);
        filter.addDataScheme("package");
        if (!TextUtils.isEmpty(str)) {
            filter.addDataSchemeSpecificPart(str, 0);
        }
        return filter;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.mIntentConsumer.accept(intent);
    }

    public void register(Context context, String... strArr) {
        context.registerReceiver(this, getFilter(strArr));
    }

    public void registerPkgActions(Context context, String str, String... strArr) {
        context.registerReceiver(this, getPackageFilter(str, strArr), 2);
    }

    public void unregisterReceiverSafely(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (IllegalArgumentException unused) {
        }
    }
}
