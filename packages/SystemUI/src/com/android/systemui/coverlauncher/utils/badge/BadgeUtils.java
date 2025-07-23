package com.android.systemui.coverlauncher.utils.badge;

import android.content.Context;
import android.net.Uri;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BadgeUtils {
    public static final Uri BADGE_URI;
    public static final String[] COLUMNS;
    public final Context mContext;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
