package com.android.server.companion.datatransfer.contextsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CallMetadataSyncData {
    public final Map mCalls = new HashMap();
    public final List mCallCreateRequests = new ArrayList();
    public final List mCallControlRequests = new ArrayList();
    public final List mCallFacilitators = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Call {
        public byte[] mAppIcon;
        public String mCallerId;
        public final Set mControls = new HashSet();
        public int mDirection;
        public CallFacilitator mFacilitator;
        public String mId;
        public int mStatus;

        public final boolean equals(Object obj) {
            String str;
            return (obj instanceof Call)
                    && (str = this.mId) != null
                    && str.equals(((Call) obj).mId);
        }

        public final boolean hasControl(int i) {
            return ((HashSet) this.mControls).contains(Integer.valueOf(i));
        }

        public final int hashCode() {
            return Objects.hashCode(this.mId);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallControlRequest {
        public int mControl;
        public String mId;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallCreateRequest {
        public String mAddress;
        public CallFacilitator mFacilitator;
        public String mId;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallFacilitator {
        public String mExtendedIdentifier;
        public String mIdentifier;
        public boolean mIsTel;
        public String mName;

        public CallFacilitator(String str, String str2, String str3) {
            this.mName = str;
            this.mIdentifier = str2;
            this.mExtendedIdentifier = str3;
        }
    }
}
