package com.android.server.pm;

import android.content.IntentFilter;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.utils.SnapshotCache;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PreferredIntentResolver extends WatchedIntentResolver {
    public final SnapshotCache mSnapshot;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PreferredIntentResolver$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            return new PreferredIntentResolver((PreferredIntentResolver) this.mSource);
        }
    }

    public PreferredIntentResolver() {
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    public PreferredIntentResolver(PreferredIntentResolver preferredIntentResolver) {
        copyFrom(preferredIntentResolver);
        this.mSnapshot = new SnapshotCache.Auto();
    }

    @Override // com.android.server.IntentResolver
    public final void dumpFilter(PrintWriter printWriter, String str, Object obj) {
        PreferredActivity preferredActivity = (PreferredActivity) obj;
        PreferredComponent preferredComponent = preferredActivity.mPref;
        preferredComponent.getClass();
        printWriter.print(str);
        printWriter.print(Integer.toHexString(System.identityHashCode(preferredActivity)));
        printWriter.print(' ');
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, preferredComponent.mShortComponent, str, " mMatch=0x");
        printWriter.print(Integer.toHexString(preferredComponent.mMatch));
        printWriter.print(" mAlways=");
        printWriter.println(preferredComponent.mAlways);
        String[] strArr = preferredComponent.mSetComponents;
        if (strArr != null) {
            printWriter.print(str);
            printWriter.println("  Selected from:");
            for (String str2 : strArr) {
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.println(str2);
            }
        }
    }

    @Override // com.android.server.IntentResolver
    public final IntentFilter getIntentFilter(Object obj) {
        return ((PreferredActivity) obj).mFilter;
    }

    @Override // com.android.server.IntentResolver
    public final boolean isPackageForFilter(String str, Object obj) {
        return str.equals(((PreferredActivity) obj).mPref.mComponent.getPackageName());
    }

    @Override // com.android.server.IntentResolver
    public final Object[] newArray(int i) {
        return new PreferredActivity[i];
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (PreferredIntentResolver) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.IntentResolver
    public final Object snapshot(Object obj) {
        PreferredActivity preferredActivity = (PreferredActivity) obj;
        if (preferredActivity == null) {
            return null;
        }
        return (PreferredActivity) preferredActivity.mSnapshot.snapshot();
    }
}
