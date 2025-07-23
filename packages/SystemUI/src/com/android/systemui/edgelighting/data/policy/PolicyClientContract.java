package com.android.systemui.edgelighting.data.policy;

import android.net.Uri;
import android.provider.BaseColumns;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PolicyClientContract {
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.policy");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PolicyItems implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_item");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PolicyList implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_list");
    }
}
