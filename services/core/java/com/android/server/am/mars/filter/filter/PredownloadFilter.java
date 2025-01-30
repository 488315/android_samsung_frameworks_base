package com.android.server.am.mars.filter.filter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PredownloadFilter implements IFilter {
    public final String TAG;
    public Context mContext;
    public PredownloadRequestReceiver mReceiver;
    public ArrayList mTempAllowlist;

    public abstract class PredownloadFilterHolder {
        public static final PredownloadFilter INSTANCE = new PredownloadFilter();
    }

    public PredownloadFilter() {
        this.TAG = "MARs:" + PredownloadFilter.class.getSimpleName();
        this.mTempAllowlist = new ArrayList();
        this.mContext = null;
        this.mReceiver = null;
    }

    public static PredownloadFilter getInstance() {
        return PredownloadFilterHolder.INSTANCE;
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        setContext(context);
        this.mTempAllowlist.clear();
        PredownloadRequestReceiver predownloadRequestReceiver = new PredownloadRequestReceiver();
        this.mReceiver = predownloadRequestReceiver;
        predownloadRequestReceiver.registerPredownloadRequestReceiver();
        SystemProperties.set("sys.config.mars.predl_filter.enabled", "1");
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        this.mTempAllowlist.clear();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        if (!this.mTempAllowlist.contains(new TargetPackageTuple(i2, str))) {
            return 0;
        }
        Slog.d(this.TAG, "EXEMPT!");
        return 28;
    }

    public final void setTempExempt(int i, String str, boolean z) {
        Slog.d(this.TAG, "PreDownload temp exempt: u=" + i + ", pkg=" + str + ", exempt=" + z);
        TargetPackageTuple targetPackageTuple = new TargetPackageTuple(i, str);
        if (z) {
            if (this.mTempAllowlist.contains(targetPackageTuple)) {
                return;
            }
            this.mTempAllowlist.add(targetPackageTuple);
            return;
        }
        this.mTempAllowlist.remove(targetPackageTuple);
    }

    public final class PredownloadRequestReceiver extends BroadcastReceiver {
        public PredownloadRequestReceiver() {
        }

        public void registerPredownloadRequestReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.action_exempt_for_pre_download");
            PredownloadFilter.this.mContext.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null || !"com.samsung.action_exempt_for_pre_download".equals(intent.getAction())) {
                return;
            }
            int intExtra = intent.getIntExtra("uid", -1);
            boolean booleanExtra = intent.getBooleanExtra("exempt", false);
            String stringExtra = intent.getStringExtra("package");
            if (intExtra <= 0 || stringExtra == null) {
                return;
            }
            PredownloadFilter.this.setTempExempt(intExtra, stringExtra, booleanExtra);
        }
    }

    public class TargetPackageTuple {
        public String pkgName;
        public int uid;

        public TargetPackageTuple(int i, String str) {
            this.uid = i;
            this.pkgName = str;
        }

        public boolean equals(Object obj) {
            String str;
            if (!(obj instanceof TargetPackageTuple)) {
                return false;
            }
            TargetPackageTuple targetPackageTuple = (TargetPackageTuple) obj;
            String str2 = this.pkgName;
            return str2 != null && (str = targetPackageTuple.pkgName) != null && this.uid == targetPackageTuple.uid && str2.equals(str);
        }

        public int hashCode() {
            return (this.uid + "_" + this.pkgName).hashCode();
        }
    }
}
