package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class SettingLogReader {
    public final Set appPrefNames;
    public final Context context;
    public final String threeDepthCollectionDelimiter;
    public final String twoDepthCollectionDelimiter;
    public final String twoDepthKeyValueDelimiter;

    public SettingLogReader(Context context) {
        this.context = context;
        this.appPrefNames = Preferences.getPreferences(context).getStringSet("AppPrefs", new HashSet());
        Utils.Depth depth = Utils.Depth.TWO_DEPTH;
        this.twoDepthKeyValueDelimiter = depth.getKeyValueDLM();
        this.twoDepthCollectionDelimiter = depth.getCollectionDLM();
        this.threeDepthCollectionDelimiter = Utils.Depth.THREE_DEPTH.getCollectionDLM();
    }
}
