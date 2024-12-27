package com.android.server.pm;

import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.utils.SnapshotCache;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CrossProfileIntentFilter extends WatchedIntentFilter {
    public final int mAccessControlLevel;
    public final int mFlags;
    public final String mOwnerPackage;
    public final SnapshotCache mSnapshot;
    public final int mTargetUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.CrossProfileIntentFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            CrossProfileIntentFilter crossProfileIntentFilter = new CrossProfileIntentFilter((CrossProfileIntentFilter) this.mSource);
            crossProfileIntentFilter.seal();
            return crossProfileIntentFilter;
        }
    }

    public CrossProfileIntentFilter(TypedXmlPullParser typedXmlPullParser) {
        this.mTargetUserId = typedXmlPullParser.getAttributeInt((String) null, "targetUserId", -10000);
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "ownerPackage");
        if (attributeValue == null) {
            String m = CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Missing element under CrossProfileIntentFilter: ownerPackage at "));
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m);
            attributeValue = "";
        }
        this.mOwnerPackage = attributeValue;
        this.mAccessControlLevel = typedXmlPullParser.getAttributeInt((String) null, "accessControl", 0);
        this.mFlags = typedXmlPullParser.getAttributeInt((String) null, "flags", 0);
        this.mSnapshot = new AnonymousClass1(this, this, null);
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
                String m2 = CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(typedXmlPullParser, DumpUtils$$ExternalSyntheticOutline0.m("Unknown element under crossProfile-intent-filters: ", name, " at "));
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(5, m2);
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
        if (name.equals("filter")) {
            this.mFilter.readFromXml(typedXmlPullParser);
            return;
        }
        String m3 = CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Missing element under CrossProfileIntentFilter: filter at "));
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        PackageManagerServiceUtils.logCriticalInfo(5, m3);
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public CrossProfileIntentFilter(CrossProfileIntentFilter crossProfileIntentFilter) {
        super(crossProfileIntentFilter);
        this.mTargetUserId = crossProfileIntentFilter.mTargetUserId;
        this.mOwnerPackage = crossProfileIntentFilter.mOwnerPackage;
        this.mFlags = crossProfileIntentFilter.mFlags;
        this.mAccessControlLevel = crossProfileIntentFilter.mAccessControlLevel;
        this.mSnapshot = new SnapshotCache.Auto();
    }

    public CrossProfileIntentFilter(WatchedIntentFilter watchedIntentFilter, String str, int i, int i2, int i3) {
        super(watchedIntentFilter.mFilter);
        this.mTargetUserId = i;
        this.mOwnerPackage = str;
        this.mFlags = i2;
        this.mAccessControlLevel = i3;
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public final WatchedIntentFilter snapshot() {
        return (CrossProfileIntentFilter) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public final Object snapshot() {
        return (CrossProfileIntentFilter) this.mSnapshot.snapshot();
    }

    public final String toString() {
        return "CrossProfileIntentFilter{0x" + Integer.toHexString(System.identityHashCode(this)) + " " + Integer.toString(this.mTargetUserId) + "}";
    }
}
