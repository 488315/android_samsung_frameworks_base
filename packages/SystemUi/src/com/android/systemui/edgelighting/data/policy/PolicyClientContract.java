package com.android.systemui.edgelighting.data.policy;

import android.net.Uri;
import android.provider.BaseColumns;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PolicyClientContract {
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.policy");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PolicyItems implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_item");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PolicyList implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_list");
    }
}
