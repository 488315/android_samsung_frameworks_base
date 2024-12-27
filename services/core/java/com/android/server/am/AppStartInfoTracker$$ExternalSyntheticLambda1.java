package com.android.server.am;

import android.app.ApplicationStartInfo;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppStartInfoTracker$$ExternalSyntheticLambda1
        implements Comparator {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ApplicationStartInfo applicationStartInfo = (ApplicationStartInfo) obj;
        ApplicationStartInfo applicationStartInfo2 = (ApplicationStartInfo) obj2;
        switch (this.$r8$classId) {
        }
        return Long.compare(
                AppStartInfoTracker.getStartTimestamp(applicationStartInfo2),
                AppStartInfoTracker.getStartTimestamp(applicationStartInfo));
    }
}
