package com.android.server.am.mars.filter.filter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

public final class PredownloadFilter implements IFilter {
    public final ArrayList mTempAllowlist = new ArrayList();
    public Context mContext = null;
    public PredownloadRequestReceiver mReceiver = null;

    public abstract class PredownloadFilterHolder {
        public static final PredownloadFilter INSTANCE = new PredownloadFilter();
    }

    public final class PredownloadRequestReceiver extends BroadcastReceiver {
        public boolean registered = false;

        public PredownloadRequestReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null
                    || intent.getAction() == null
                    || !"com.samsung.action_exempt_for_pre_download".equals(intent.getAction())) {
                return;
            }
            int intExtra = intent.getIntExtra("uid", -1);
            boolean booleanExtra = intent.getBooleanExtra("exempt", false);
            String stringExtra = intent.getStringExtra("package");
            if (intExtra <= 0 || stringExtra == null) {
                return;
            }
            PredownloadFilter predownloadFilter = PredownloadFilter.this;
            predownloadFilter.getClass();
            Slog.d(
                    "MARs:PredownloadFilter",
                    "PreDownload temp exempt: u="
                            + intExtra
                            + ", pkg="
                            + stringExtra
                            + ", exempt="
                            + booleanExtra);
            TargetPackageTuple targetPackageTuple = new TargetPackageTuple(intExtra, stringExtra);
            if (!booleanExtra) {
                predownloadFilter.mTempAllowlist.remove(targetPackageTuple);
            } else {
                if (predownloadFilter.mTempAllowlist.contains(targetPackageTuple)) {
                    return;
                }
                predownloadFilter.mTempAllowlist.add(targetPackageTuple);
            }
        }
    }

    public final class TargetPackageTuple {
        public final String pkgName;
        public final int uid;

        public TargetPackageTuple(int i, String str) {
            this.uid = i;
            this.pkgName = str;
        }

        public final boolean equals(Object obj) {
            String str;
            if (!(obj instanceof TargetPackageTuple)) {
                return false;
            }
            TargetPackageTuple targetPackageTuple = (TargetPackageTuple) obj;
            String str2 = this.pkgName;
            return str2 != null
                    && (str = targetPackageTuple.pkgName) != null
                    && this.uid == targetPackageTuple.uid
                    && str2.equals(str);
        }

        public final int hashCode() {
            return (this.uid + "_" + this.pkgName).hashCode();
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        PredownloadRequestReceiver predownloadRequestReceiver = this.mReceiver;
        if (predownloadRequestReceiver != null && predownloadRequestReceiver.registered) {
            PredownloadFilter.this.mContext.unregisterReceiver(predownloadRequestReceiver);
            predownloadRequestReceiver.registered = false;
        }
        this.mTempAllowlist.clear();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        if (!this.mTempAllowlist.contains(new TargetPackageTuple(i2, str))) {
            return 0;
        }
        Slog.d("MARs:PredownloadFilter", "EXEMPT!");
        return 28;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        this.mTempAllowlist.clear();
        if (this.mReceiver == null) {
            this.mReceiver = new PredownloadRequestReceiver();
        }
        PredownloadRequestReceiver predownloadRequestReceiver = this.mReceiver;
        if (!predownloadRequestReceiver.registered) {
            PredownloadFilter.this.mContext.registerReceiver(
                    predownloadRequestReceiver,
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "com.samsung.action_exempt_for_pre_download"),
                    2);
            predownloadRequestReceiver.registered = true;
        }
        SystemProperties.set("sys.config.mars.predl_filter.enabled", "1");
    }
}
