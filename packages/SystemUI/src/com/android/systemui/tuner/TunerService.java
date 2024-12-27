package com.android.systemui.tuner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.Dependency;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class TunerService {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class ClearReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.action.CLEAR_TUNER".equals(intent.getAction())) {
                ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).clearAll();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Tunable {
        void onTuningChanged(String str, String str2);
    }

    public TunerService(Context context) {
    }

    public abstract void addTunable(Tunable tunable, String... strArr);

    public abstract void clearAll();

    public abstract int getValue(int i, String str);

    public abstract String getValue(String str);

    public abstract String getValue(String str, String str2);

    public abstract void removeTunable(Tunable tunable);

    public abstract void setValue(int i, String str);

    public abstract void setValue(String str, String str2);

    public abstract void showResetRequest(TunerFragment$$ExternalSyntheticLambda0 tunerFragment$$ExternalSyntheticLambda0);
}
