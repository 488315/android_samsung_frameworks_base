package com.android.server.pm;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;

import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.utils.SnapshotCache;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersistentPreferredActivity extends WatchedIntentFilter {
    public final ComponentName mComponent;
    public final boolean mIsSetByDpm;
    public final SnapshotCache mSnapshot;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PersistentPreferredActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            PersistentPreferredActivity persistentPreferredActivity =
                    new PersistentPreferredActivity((PersistentPreferredActivity) this.mSource);
            persistentPreferredActivity.seal();
            return persistentPreferredActivity;
        }
    }

    public PersistentPreferredActivity(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        ComponentName unflattenFromString = ComponentName.unflattenFromString(attributeValue);
        this.mComponent = unflattenFromString;
        if (unflattenFromString == null) {
            String m =
                    CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                            typedXmlPullParser,
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Error in package manager settings: Bad activity name ",
                                    attributeValue,
                                    " at "));
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m);
        }
        this.mIsSetByDpm =
                typedXmlPullParser.getAttributeBoolean((String) null, "set-by-dpm", false);
        int depth = typedXmlPullParser.getDepth();
        String name = typedXmlPullParser.getName();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            name = typedXmlPullParser.getName();
            if (next != 3 && next != 4 && next == 2) {
                if (name.equals("filter")) {
                    break;
                }
                String m2 =
                        CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Unknown element: ", name, " at "));
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(5, m2);
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
        if (name.equals("filter")) {
            this.mFilter.readFromXml(typedXmlPullParser);
        } else {
            String m3 =
                    CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                            typedXmlPullParser, new StringBuilder("Missing element filter at "));
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m3);
            XmlUtils.skipCurrentTag(typedXmlPullParser);
        }
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    public PersistentPreferredActivity(PersistentPreferredActivity persistentPreferredActivity) {
        super(persistentPreferredActivity);
        this.mComponent = persistentPreferredActivity.mComponent;
        this.mIsSetByDpm = persistentPreferredActivity.mIsSetByDpm;
        this.mSnapshot = new SnapshotCache.Auto();
    }

    public PersistentPreferredActivity(
            WatchedIntentFilter watchedIntentFilter, ComponentName componentName) {
        super(watchedIntentFilter.mFilter);
        this.mComponent = componentName;
        this.mIsSetByDpm = true;
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    @Override // com.android.server.pm.WatchedIntentFilter
    public final IntentFilter getIntentFilter$3() {
        return this.mFilter;
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public final WatchedIntentFilter snapshot() {
        return (PersistentPreferredActivity) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public final Object snapshot() {
        return (PersistentPreferredActivity) this.mSnapshot.snapshot();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PersistentPreferredActivity{0x");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.mComponent.flattenToShortString());
        sb.append(", mIsSetByDpm=");
        return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.mIsSetByDpm);
    }
}
