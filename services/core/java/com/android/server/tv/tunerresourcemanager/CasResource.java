package com.android.server.tv.tunerresourcemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class CasResource {
    public int mAvailableSessionNum;
    public int mMaxSessionNum;
    public final Map mOwnerClientIdsToSessionNum = new HashMap();
    public final int mSystemId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Builder {
        public int mMaxSessionNum;
        public final int mSystemId;

        public Builder(int i) {
            this.mSystemId = i;
        }
    }

    public CasResource(Builder builder) {
        this.mSystemId = builder.mSystemId;
        int i = builder.mMaxSessionNum;
        this.mMaxSessionNum = i;
        this.mAvailableSessionNum = i;
    }

    public final Set getOwnerClientIds() {
        return ((HashMap) this.mOwnerClientIdsToSessionNum).keySet();
    }

    public final String ownersMapToString() {
        StringBuilder sb = new StringBuilder("{");
        for (Integer num : ((HashMap) this.mOwnerClientIdsToSessionNum).keySet()) {
            int intValue = num.intValue();
            sb.append(" clientId=");
            sb.append(intValue);
            sb.append(", owns session num=");
            sb.append(((HashMap) this.mOwnerClientIdsToSessionNum).get(num));
            sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    public final void removeOwner(int i) {
        if (((HashMap) this.mOwnerClientIdsToSessionNum).containsKey(Integer.valueOf(i))) {
            this.mAvailableSessionNum =
                    ((Integer) ((HashMap) this.mOwnerClientIdsToSessionNum).get(Integer.valueOf(i)))
                                    .intValue()
                            + this.mAvailableSessionNum;
            ((HashMap) this.mOwnerClientIdsToSessionNum).remove(Integer.valueOf(i));
        }
    }

    public final void setOwner(int i) {
        int intValue;
        if (((HashMap) this.mOwnerClientIdsToSessionNum).get(Integer.valueOf(i)) == null) {
            intValue = 1;
        } else {
            intValue =
                    ((Integer) ((HashMap) this.mOwnerClientIdsToSessionNum).get(Integer.valueOf(i)))
                                    .intValue()
                            + 1;
        }
        ((HashMap) this.mOwnerClientIdsToSessionNum)
                .put(Integer.valueOf(i), Integer.valueOf(intValue));
        this.mAvailableSessionNum--;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CasResource[systemId=");
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
