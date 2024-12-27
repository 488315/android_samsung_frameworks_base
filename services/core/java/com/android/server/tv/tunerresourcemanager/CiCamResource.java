package com.android.server.tv.tunerresourcemanager;

import com.android.server.tv.tunerresourcemanager.CasResource;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CiCamResource extends CasResource {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder extends CasResource.Builder {
    }

    @Override // com.android.server.tv.tunerresourcemanager.CasResource
    public final String toString() {
        StringBuilder sb = new StringBuilder("CiCamResource[systemId=");
        sb.append(this.mSystemId);
        sb.append(", isFullyUsed=");
        sb.append(this.mAvailableSessionNum == 0);
        sb.append(", maxSessionNum=");
        sb.append(this.mMaxSessionNum);
        sb.append(", ownerClients=");
        sb.append(ownersMapToString());
        sb.append("]");
        return sb.toString();
    }
}
