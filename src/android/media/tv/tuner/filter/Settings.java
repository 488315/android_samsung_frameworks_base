package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public abstract class Settings {
    private final int mType;

    Settings(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}
