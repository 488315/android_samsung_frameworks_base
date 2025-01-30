package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyLogBuildClient;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogBuildClient;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Utils {
    public static int DMA_VERSION = -1;

    /* renamed from: br */
    public static C47941 f633br;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Depth {
        ONE_DEPTH("\u0002", "\u0003"),
        TWO_DEPTH("\u0004", "\u0005"),
        THREE_DEPTH("\u0006", "\u0007");

        private String collDlm;
        private String keyvalueDlm;

        Depth(String str, String str2) {
            this.collDlm = str;
            this.keyvalueDlm = str2;
        }

        public final String getCollectionDLM() {
            return this.collDlm;
        }

        public final String getKeyValueDLM() {
            return this.keyvalueDlm;
        }
    }

    public static boolean compareDays(int i, Long l) {
        return Long.valueOf(System.currentTimeMillis()).longValue() > (((long) i) * 86400000) + l.longValue();
    }

    public static int getDMAversion(Context context) {
        if (DMA_VERSION == -1) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
                DMA_VERSION = packageInfo.versionCode;
                Debug.LogD("Utils", "dma pkg:" + packageInfo.versionCode);
            } catch (PackageManager.NameNotFoundException unused) {
                Debug.LogD("Utils", "DMA Client is not exist");
                DMA_VERSION = 0;
            }
        }
        return DMA_VERSION;
    }

    public static boolean isDMADataProvideVersion(Context context) {
        return 710000000 <= getDMAversion(context);
    }

    public static String makeDelimiterString(Map map, Depth depth) {
        String sb;
        String str = null;
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            if (str == null) {
                sb = entry.getKey().toString();
            } else {
                StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                m18m.append(depth.getCollectionDLM());
                StringBuilder m18m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m18m.toString());
                m18m2.append(entry.getKey());
                sb = m18m2.toString();
            }
            StringBuilder m18m3 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(sb);
            m18m3.append(depth.getKeyValueDLM());
            StringBuilder m18m4 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m18m3.toString());
            m18m4.append(entry.getValue());
            str = m18m4.toString();
        }
        return str;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.context.sdk.samsunganalytics.internal.util.Utils$1] */
    public static void registerReceiver(Context context, final Configuration configuration) {
        Debug.LogENG("register BR");
        if (f633br != null) {
            Debug.LogENG("BR is already registered");
            return;
        }
        f633br = new BroadcastReceiver() { // from class: com.samsung.context.sdk.samsunganalytics.internal.util.Utils.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                StringBuilder sb = new StringBuilder("receive BR ");
                sb.append(intent != null ? intent.getAction() : "null");
                Debug.LogENG(sb.toString());
                if (intent == null || !"android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                    return;
                }
                SingleThreadExecutor.getInstance().execute(new SettingLogBuildClient(context2, Configuration.this));
                SingleThreadExecutor.getInstance().execute(new PropertyLogBuildClient(context2, Configuration.this));
            }
        };
        context.registerReceiver(f633br, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.intent.action.ACTION_POWER_CONNECTED"));
    }

    public static void throwException(String str) {
        if (Build.TYPE.equals("eng")) {
            throw new AnalyticsException(str);
        }
        Debug.LogE(str);
    }
}
