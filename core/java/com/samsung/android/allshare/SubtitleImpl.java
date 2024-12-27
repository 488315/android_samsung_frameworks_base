package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;

/* loaded from: classes3.dex */
final class SubtitleImpl extends Subtitle {
    private Bundle mBundle;

    protected SubtitleImpl(Bundle bundle) {
        this.mBundle = null;
        this.mBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Subtitle
    public Uri getUri() {
        return (Uri) (this.mBundle == null ? null : this.mBundle.getParcelable("SUBTITLE_URI"));
    }

    @Override // com.samsung.android.allshare.Subtitle
    public String getType() {
        return this.mBundle == null ? "" : this.mBundle.getString("SUBTITLE_TYPE");
    }
}
