package com.android.server.am;

import android.app.ApplicationStartInfo;

import java.util.Comparator;

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
