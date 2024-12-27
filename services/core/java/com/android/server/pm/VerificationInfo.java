package com.android.server.pm;

import android.net.Uri;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    public final String toString() {
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
