package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
public abstract class Settings {
    private final int mType;

    Settings(int type) {
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }
}
