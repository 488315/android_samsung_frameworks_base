package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;

/* loaded from: classes3.dex */
class IconImpl extends Icon {
    private Bundle mIconBundle;

    protected IconImpl(Bundle bundle) {
        this.mIconBundle = null;
        this.mIconBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Icon
    public int getDepth() {
        if (this.mIconBundle == null) {
            return -1;
        }
        return this.mIconBundle.getInt("ICON_DEPTH");
    }

    @Override // com.samsung.android.allshare.Icon
    public int getWidth() {
        if (this.mIconBundle == null) {
            return -1;
        }
        return this.mIconBundle.getInt("ICON_WIDTH");
    }

    @Override // com.samsung.android.allshare.Icon
    public int getHeight() {
        if (this.mIconBundle == null) {
            return -1;
        }
        return this.mIconBundle.getInt("ICON_HEIGHT");
    }

    @Override // com.samsung.android.allshare.Icon
    public String getMimetype() {
        return this.mIconBundle == null ? "" : this.mIconBundle.getString("ICON_MIMETYPE");
    }

    @Override // com.samsung.android.allshare.Icon
    public Uri getUri() {
        return (Uri) (this.mIconBundle == null ? null : this.mIconBundle.getParcelable("ICON_URI"));
    }
}
