package com.samsung.android.allshare;

import android.net.Uri;
import android.p009os.Bundle;

/* loaded from: classes5.dex */
class IconImpl extends Icon {
    private Bundle mIconBundle;

    protected IconImpl(Bundle bundle) {
        this.mIconBundle = null;
        this.mIconBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Icon
    public int getDepth() {
        Bundle bundle = this.mIconBundle;
        if (bundle == null) {
            return -1;
        }
        return bundle.getInt("ICON_DEPTH");
    }

    @Override // com.samsung.android.allshare.Icon
    public int getWidth() {
        Bundle bundle = this.mIconBundle;
        if (bundle == null) {
            return -1;
        }
        return bundle.getInt("ICON_WIDTH");
    }

    @Override // com.samsung.android.allshare.Icon
    public int getHeight() {
        Bundle bundle = this.mIconBundle;
        if (bundle == null) {
            return -1;
        }
        return bundle.getInt("ICON_HEIGHT");
    }

    @Override // com.samsung.android.allshare.Icon
    public String getMimetype() {
        Bundle bundle = this.mIconBundle;
        return bundle == null ? "" : bundle.getString("ICON_MIMETYPE");
    }

    @Override // com.samsung.android.allshare.Icon
    public Uri getUri() {
        Bundle bundle = this.mIconBundle;
        return (Uri) (bundle == null ? null : bundle.getParcelable("ICON_URI"));
    }
}
