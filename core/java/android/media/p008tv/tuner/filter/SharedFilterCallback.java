package android.media.p008tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public interface SharedFilterCallback {
    void onFilterEvent(SharedFilter sharedFilter, FilterEvent[] filterEventArr);

    void onFilterStatusChanged(SharedFilter sharedFilter, int i);
}
