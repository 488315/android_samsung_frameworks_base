package com.android.server.am;

import android.app.ApplicationExitInfo;

import java.util.Comparator;

public final /* synthetic */ class AppExitInfoTracker$$ExternalSyntheticLambda1
        implements Comparator {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ApplicationExitInfo applicationExitInfo = (ApplicationExitInfo) obj;
        ApplicationExitInfo applicationExitInfo2 = (ApplicationExitInfo) obj2;
        switch (this.$r8$classId) {
        }
        return Long.compare(
                applicationExitInfo2.getTimestamp(), applicationExitInfo.getTimestamp());
    }
}
