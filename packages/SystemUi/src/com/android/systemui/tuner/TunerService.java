package com.android.systemui.tuner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.Dependency;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TunerService {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ClearReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.action.CLEAR_TUNER".equals(intent.getAction())) {
                ((TunerService) Dependency.get(TunerService.class)).clearAll();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Tunable {
        void onTuningChanged(String str, String str2);
    }

    public TunerService(Context context) {
    }

    public abstract void addTunable(Tunable tunable, String... strArr);

    public abstract void clearAll();

    public abstract int getValue(int i, String str);

    public abstract String getValue(String str);

    public abstract void removeTunable(Tunable tunable);

    public abstract void setValue(int i, String str);

    public abstract void setValue(String str, String str2);

    public abstract void showResetRequest(TunerFragment$$ExternalSyntheticLambda0 tunerFragment$$ExternalSyntheticLambda0);
}
