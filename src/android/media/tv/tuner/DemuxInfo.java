package android.media.tv.tuner;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DemuxInfo {
    private int mFilterTypes;

    public DemuxInfo(int i) {
        setFilterTypes(i);
    }

    public int getFilterTypes() {
        return this.mFilterTypes;
    }

    public void setFilterTypes(int i) {
        this.mFilterTypes = i;
    }
}
