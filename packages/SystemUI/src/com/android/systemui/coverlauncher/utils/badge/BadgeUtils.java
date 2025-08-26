package com.android.systemui.coverlauncher.utils.badge;

import android.content.Context;
import android.net.Uri;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class BadgeUtils {
    public static final Uri BADGE_URI;
    public static final String[] COLUMNS;
    public final Context mContext;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
