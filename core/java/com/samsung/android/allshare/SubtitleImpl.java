package com.samsung.android.allshare;

import android.net.Uri;
import android.p009os.Bundle;

/* loaded from: classes5.dex */
final class SubtitleImpl extends Subtitle {
    private Bundle mBundle;

    protected SubtitleImpl(Bundle bundle) {
        this.mBundle = null;
        this.mBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Subtitle
    public Uri getUri() {
        Bundle bundle = this.mBundle;
        return (Uri) (bundle == null ? null : bundle.getParcelable("SUBTITLE_URI"));
    }

    @Override // com.samsung.android.allshare.Subtitle
    public String getType() {
        Bundle bundle = this.mBundle;
        return bundle == null ? "" : bundle.getString("SUBTITLE_TYPE");
    }
}
