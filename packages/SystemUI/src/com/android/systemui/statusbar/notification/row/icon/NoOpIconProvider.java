package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.Collection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NoOpIconProvider implements AppIconProvider {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.AppIconProvider
    public final Drawable getOrFetchAppIcon(Context context, String str, boolean z) {
        Log.wtf("NoOpIconProvider", "NoOpIconProvider should not be used anywhere.");
        return new ColorDrawable(-1);
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.AppIconProvider
    public final void purgeCache(Collection collection) {
        Log.wtf("NoOpIconProvider", "NoOpIconProvider should not be used anywhere.");
    }
}
