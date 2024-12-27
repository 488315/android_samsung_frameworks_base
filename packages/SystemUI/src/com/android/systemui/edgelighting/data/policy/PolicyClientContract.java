package com.android.systemui.edgelighting.data.policy;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PolicyClientContract {
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.policy");

    public final class PolicyItems implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_item");
    }

    public final class PolicyList implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_list");
    }
}
