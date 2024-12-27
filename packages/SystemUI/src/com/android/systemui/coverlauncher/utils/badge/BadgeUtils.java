package com.android.systemui.coverlauncher.utils.badge;

import android.content.Context;
import android.net.Uri;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BadgeUtils {
    public static final Uri BADGE_URI;
    public static final String[] COLUMNS;
    public final Context mContext;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        BADGE_URI = Uri.parse("content://com.sec.badge/apps");
        COLUMNS = new String[]{"package", "class", "badgecount"};
    }

    public BadgeUtils(Context context) {
        this.mContext = context;
    }
}
