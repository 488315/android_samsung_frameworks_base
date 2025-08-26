package com.android.settingslib.notification;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.IconDrawableFactory;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class ConversationIconFactory extends BaseIconFactory {
    public final LauncherApps mLauncherApps;
    public final PackageManager mPackageManager;

    static {
        Math.sqrt(288.0d);
    }

    public ConversationIconFactory(Context context, LauncherApps launcherApps, PackageManager packageManager, IconDrawableFactory iconDrawableFactory, int i) throws Resources.NotFoundException {
        super(context, context.getResources().getConfiguration().densityDpi, i);
        this.mLauncherApps = launcherApps;
        this.mPackageManager = packageManager;
        context.getResources().getColor(R.color.important_conversation, null);
    }
}
