package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
public interface SharedFilterCallback {
    void onFilterEvent(SharedFilter sharedFilter, FilterEvent[] filterEventArr);

    void onFilterStatusChanged(SharedFilter sharedFilter, int i);
}
