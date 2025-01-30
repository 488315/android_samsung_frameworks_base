package com.android.server.pm;

import android.content.ComponentName;
import android.content.IntentFilter;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.PreferredComponent;
import com.android.server.utils.SnapshotCache;

/* loaded from: classes3.dex */
public class PreferredActivity extends WatchedIntentFilter implements PreferredComponent.Callbacks {
    public final PreferredComponent mPref;
    public final SnapshotCache mSnapshot;

    public final SnapshotCache makeCache() {
        return new SnapshotCache(this, this) { // from class: com.android.server.pm.PreferredActivity.1
            @Override // com.android.server.utils.SnapshotCache
            public PreferredActivity createSnapshot() {
                PreferredActivity preferredActivity = new PreferredActivity();
                preferredActivity.seal();
                return preferredActivity;
            }
        };
    }

    public PreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, boolean z) {
        super(intentFilter);
        this.mPref = new PreferredComponent(this, i, componentNameArr, componentName, z);
        this.mSnapshot = makeCache();
    }

    public PreferredActivity(WatchedIntentFilter watchedIntentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, boolean z) {
        this(watchedIntentFilter.mFilter, i, componentNameArr, componentName, z);
    }

    public PreferredActivity(PreferredActivity preferredActivity) {
        super(preferredActivity);
        this.mPref = preferredActivity.mPref;
        this.mSnapshot = new SnapshotCache.Sealed();
    }

    public PreferredActivity(TypedXmlPullParser typedXmlPullParser) {
        this.mPref = new PreferredComponent(this, typedXmlPullParser);
        this.mSnapshot = makeCache();
    }

    public void writeToXml(TypedXmlSerializer typedXmlSerializer, boolean z) {
        this.mPref.writeToXml(typedXmlSerializer, z);
        typedXmlSerializer.startTag((String) null, "filter");
        this.mFilter.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, "filter");
    }

    @Override // com.android.server.pm.PreferredComponent.Callbacks
    public boolean onReadTag(String str, TypedXmlPullParser typedXmlPullParser) {
        if (str.equals("filter")) {
            this.mFilter.readFromXml(typedXmlPullParser);
            return true;
        }
        PackageManagerService.reportSettingsProblem(5, "Unknown element under <preferred-activities>: " + typedXmlPullParser.getName());
        XmlUtils.skipCurrentTag(typedXmlPullParser);
        return true;
    }

    public String toString() {
        return "PreferredActivity{0x" + Integer.toHexString(System.identityHashCode(this)) + " " + this.mPref.mComponent.flattenToShortString() + "}";
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public PreferredActivity snapshot() {
        return (PreferredActivity) this.mSnapshot.snapshot();
    }
}
