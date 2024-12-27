package com.android.server.appfunctions.agentpolicy;

import android.R;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppFunctionAgentPolicyManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppFunctionAgentPolicyManager$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InputStream openRawResource;
        BufferedReader bufferedReader;
        StringBuilder sb;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AppFunctionAgentPolicyManager appFunctionAgentPolicyManager = (AppFunctionAgentPolicyManager) obj;
                appFunctionAgentPolicyManager.getClass();
                Slog.d("AppFunctionAgentPolicyManager", "start agent policy loading");
                try {
                    openRawResource = appFunctionAgentPolicyManager.mContext.getResources().openRawResource(R.raw.default_ringtone_vibration_effect);
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(openRawResource, "UTF-8"));
                        try {
                            sb = new StringBuilder();
                        } finally {
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    Slog.e("AppFunctionAgentPolicyManager", "loadDefaultPolicy", e);
                }
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        AppFunctionAgentPolicyManager.updateAgentList(sb, appFunctionAgentPolicyManager.defaultAgentPolicyInfo, true);
                        bufferedReader.close();
                        if (openRawResource != null) {
                            openRawResource.close();
                        }
                        appFunctionAgentPolicyManager.loadUpdatedAgentPolicy();
                        appFunctionAgentPolicyManager.loadAppliedAgentPolicy();
                        Slog.d("AppFunctionAgentPolicyManager", "end agent policy loading");
                        return;
                    }
                    sb.append(readLine);
                }
            case 1:
                AppFunctionAgentPolicyManager.m229$$Nest$mtryScpmRegister(AppFunctionAgentPolicyManager.this);
                return;
            default:
                AppFunctionAgentPolicyManager.m229$$Nest$mtryScpmRegister(AppFunctionAgentPolicyManager.this);
                return;
        }
    }
}
