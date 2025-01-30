package com.android.server.tv.tunerresourcemanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class CasResource {
    public int mAvailableSessionNum;
    public int mMaxSessionNum;
    public Map mOwnerClientIdsToSessionNum = new HashMap();
    public final int mSystemId;

    public CasResource(Builder builder) {
        this.mSystemId = builder.mSystemId;
        int i = builder.mMaxSessionNum;
        this.mMaxSessionNum = i;
        this.mAvailableSessionNum = i;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    public int getMaxSessionNum() {
        return this.mMaxSessionNum;
    }

    public int getUsedSessionNum() {
        return this.mMaxSessionNum - this.mAvailableSessionNum;
    }

    public boolean isFullyUsed() {
        return this.mAvailableSessionNum == 0;
    }

    public void updateMaxSessionNum(int i) {
        this.mAvailableSessionNum = Math.max(0, this.mAvailableSessionNum + (i - this.mMaxSessionNum));
        this.mMaxSessionNum = i;
    }

    public void setOwner(int i) {
        this.mOwnerClientIdsToSessionNum.put(Integer.valueOf(i), Integer.valueOf(this.mOwnerClientIdsToSessionNum.get(Integer.valueOf(i)) == null ? 1 : ((Integer) this.mOwnerClientIdsToSessionNum.get(Integer.valueOf(i))).intValue() + 1));
        this.mAvailableSessionNum--;
    }

    public void removeOwner(int i) {
        this.mAvailableSessionNum += ((Integer) this.mOwnerClientIdsToSessionNum.get(Integer.valueOf(i))).intValue();
        this.mOwnerClientIdsToSessionNum.remove(Integer.valueOf(i));
    }

    public Set getOwnerClientIds() {
        return this.mOwnerClientIdsToSessionNum.keySet();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CasResource[systemId=");
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

    public class Builder {
        public int mMaxSessionNum;
        public int mSystemId;

        public Builder(int i) {
            this.mSystemId = i;
        }

        public Builder maxSessionNum(int i) {
            this.mMaxSessionNum = i;
            return this;
        }

        public CasResource build() {
            return new CasResource(this);
        }
    }

    public String ownersMapToString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator it = this.mOwnerClientIdsToSessionNum.keySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            sb.append(" clientId=");
            sb.append(intValue);
            sb.append(", owns session num=");
            sb.append(this.mOwnerClientIdsToSessionNum.get(Integer.valueOf(intValue)));
            sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }
}
