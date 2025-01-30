package com.android.systemui.util.leak;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.android.systemui.settings.UserTracker;
import com.google.android.collect.Lists;
import java.io.File;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LeakReporter {
    public final Context mContext;
    public final LeakDetector mLeakDetector;
    public final String mLeakReportEmail;
    public final UserTracker mUserTracker;

    public LeakReporter(Context context, UserTracker userTracker, LeakDetector leakDetector, String str) {
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mLeakDetector = leakDetector;
        this.mLeakReportEmail = str;
    }

    public final Intent getIntent(File file, File file2) {
        Context context = this.mContext;
        Uri uriForFile = FileProvider.getUriForFile(context, "com.android.systemui.fileprovider", file2);
        Uri uriForFile2 = FileProvider.getUriForFile(context, "com.android.systemui.fileprovider", file);
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.addFlags(1);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.leakreport");
        intent.putExtra("android.intent.extra.SUBJECT", "SystemUI leak report");
        intent.putExtra("android.intent.extra.TEXT", "Build info: " + SystemProperties.get("ro.build.description"));
        ClipData clipData = new ClipData(null, new String[]{"application/vnd.android.leakreport"}, new ClipData.Item(null, null, null, uriForFile));
        ArrayList<? extends Parcelable> newArrayList = Lists.newArrayList(new Uri[]{uriForFile});
        clipData.addItem(new ClipData.Item(null, null, null, uriForFile2));
        newArrayList.add(uriForFile2);
        intent.setClipData(clipData);
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", newArrayList);
        String str = this.mLeakReportEmail;
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("android.intent.extra.EMAIL", new String[]{str});
        }
        return intent;
    }
}
