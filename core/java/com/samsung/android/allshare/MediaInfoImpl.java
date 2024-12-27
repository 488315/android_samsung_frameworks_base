package com.samsung.android.allshare;

import android.os.Bundle;

import com.samsung.android.allshare.media.MediaInfo;
import com.sec.android.allshare.iface.message.AllShareKey;

/* loaded from: classes3.dex */
final class MediaInfoImpl extends MediaInfo {
    private Bundle mBundle;
    private long mMediaDuration = -1;

    MediaInfoImpl(Bundle bundle) {
        this.mBundle = null;
        this.mBundle = bundle;
    }

    @Override // com.samsung.android.allshare.media.MediaInfo
    public long getDuration() {
        if (this.mMediaDuration == -1) {
            this.mMediaDuration =
                    this.mBundle != null
                            ? this.mBundle.getLong(AllShareKey.BUNDLE_LONG_DURATION)
                            : -1L;
        }
        return this.mMediaDuration;
    }
}
