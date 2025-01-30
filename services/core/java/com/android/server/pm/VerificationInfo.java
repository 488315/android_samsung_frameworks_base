package com.android.server.pm;

import android.net.Uri;

/* loaded from: classes3.dex */
public final class VerificationInfo {
    public final int mInstallerUid;
    public final int mOriginatingUid;
    public final Uri mOriginatingUri;
    public final Uri mReferrer;

    public VerificationInfo(Uri uri, Uri uri2, int i, int i2) {
        this.mOriginatingUri = uri;
        this.mReferrer = uri2;
        this.mOriginatingUid = i;
        this.mInstallerUid = i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        if (this.mOriginatingUri != null) {
            sb.append("mOriginatingUri=");
            sb.append(this.mOriginatingUri.toString());
        }
        if (this.mReferrer != null) {
            sb.append(",mReferrer=");
            sb.append(this.mReferrer.toString());
        }
        sb.append(",mOriginatingUid=");
        sb.append(this.mOriginatingUid);
        sb.append(",mInstallerUid=");
        sb.append(this.mInstallerUid);
        return sb.toString();
    }
}
