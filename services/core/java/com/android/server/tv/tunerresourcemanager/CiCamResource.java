package com.android.server.tv.tunerresourcemanager;


public final class CiCamResource extends CasResource {

    public final class Builder extends CasResource.Builder {}

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
