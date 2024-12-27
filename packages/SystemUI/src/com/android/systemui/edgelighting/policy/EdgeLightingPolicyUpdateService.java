package com.android.systemui.edgelighting.policy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class EdgeLightingPolicyUpdateService extends IntentService {
    public EdgeLightingPolicyUpdateService() {
        super("EdgeLightingPolicyUpdateService");
    }

    public static void startActionUpdate(Context context) {
        Slog.d("ELPolicyUpdateService", "startActionUpdate");
        Intent intent = new Intent(context, (Class<?>) EdgeLightingPolicyUpdateService.class);
        intent.setAction("com.android.systemui.edgelighting.action.UPDATE_POLICY");
        context.startService(intent);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    @Override // android.app.IntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onHandleIntent(android.content.Intent r13) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService.onHandleIntent(android.content.Intent):void");
    }
}
