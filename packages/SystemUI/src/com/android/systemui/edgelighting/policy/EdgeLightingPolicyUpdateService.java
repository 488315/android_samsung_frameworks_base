package com.android.systemui.edgelighting.policy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    @Override // android.app.IntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onHandleIntent(android.content.Intent r13) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService.onHandleIntent(android.content.Intent):void");
    }
}
