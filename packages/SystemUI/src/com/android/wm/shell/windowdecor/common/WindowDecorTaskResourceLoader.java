package com.android.wm.shell.windowdecor.common;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.LocaleList;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Slog;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.UserProfileContexts;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class WindowDecorTaskResourceLoader {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final ConcurrentHashMap densityListOnCache;
    public final DisplayController displayController;
    public final Set existingTasks;
    public final BaseIconFactory headerIconFactory;
    public final IconProvider iconProvider;
    public final ConcurrentHashMap localeListOnCache;
    public final ShellCommandHandler shellCommandHandler;
    public final ShellController shellController;
    public final ConcurrentHashMap taskToResourceCache;
    public final UserProfileContexts userProfilesContexts;
    public final BaseIconFactory veilIconFactory;

    public final class AppResources {
        public final Bitmap appIcon;
        public final CharSequence appName;
        public final Bitmap veilIcon;

        public AppResources(CharSequence charSequence, Bitmap bitmap, Bitmap bitmap2) {
            this.appName = charSequence;
            this.appIcon = bitmap;
            this.veilIcon = bitmap2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AppResources)) {
                return false;
            }
            AppResources appResources = (AppResources) obj;
            return Intrinsics.areEqual(this.appName, appResources.appName) && Intrinsics.areEqual(this.appIcon, appResources.appIcon) && Intrinsics.areEqual(this.veilIcon, appResources.veilIcon);
        }

        public final int hashCode() {
            return this.veilIcon.hashCode() + ((this.appIcon.hashCode() + (this.appName.hashCode() * 31)) * 31);
        }

        public final String toString() {
            CharSequence charSequence = this.appName;
            return "AppResources(appName=" + ((Object) charSequence) + ", appIcon=" + this.appIcon + ", veilIcon=" + this.veilIcon + ")";
        }
    }

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

    public WindowDecorTaskResourceLoader(ShellInit shellInit, Context context, DisplayController displayController, ShellController shellController, ShellCommandHandler shellCommandHandler, UserProfileContexts userProfileContexts, IconProvider iconProvider, BaseIconFactory baseIconFactory, BaseIconFactory baseIconFactory2) {
        this.context = context;
        this.displayController = displayController;
        this.shellController = shellController;
        this.shellCommandHandler = shellCommandHandler;
        this.userProfilesContexts = userProfileContexts;
        this.iconProvider = iconProvider;
        this.headerIconFactory = baseIconFactory;
        this.veilIconFactory = baseIconFactory2;
        this.taskToResourceCache = new ConcurrentHashMap();
        this.existingTasks = new LinkedHashSet();
        this.localeListOnCache = new ConcurrentHashMap();
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader.1
            @Override // java.lang.Runnable
            public final void run() {
                final WindowDecorTaskResourceLoader windowDecorTaskResourceLoader = WindowDecorTaskResourceLoader.this;
                int i = WindowDecorTaskResourceLoader.$r8$clinit;
                windowDecorTaskResourceLoader.getClass();
                windowDecorTaskResourceLoader.shellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader$onInit$1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        WindowDecorTaskResourceLoader windowDecorTaskResourceLoader2 = windowDecorTaskResourceLoader;
                        int i2 = WindowDecorTaskResourceLoader.$r8$clinit;
                        windowDecorTaskResourceLoader2.getClass();
                        String str2 = str + "  ";
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "AppResourceProvider");
                        printWriter.println(str2 + "appResourceCache=" + windowDecorTaskResourceLoader2.taskToResourceCache);
                        printWriter.println(str2 + "existingTasks=" + windowDecorTaskResourceLoader2.existingTasks);
                    }
                }, windowDecorTaskResourceLoader);
                windowDecorTaskResourceLoader.shellController.addUserChangeListener(new UserChangeListener() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader$onInit$2
                    @Override // com.android.wm.shell.sysui.UserChangeListener
                    public final void onUserChanged(int i2, Context context2) {
                        WindowDecorTaskResourceLoader windowDecorTaskResourceLoader2 = windowDecorTaskResourceLoader;
                        windowDecorTaskResourceLoader2.taskToResourceCache.clear();
                        if (CoreRune.MW_CAPTION_BUG_FIX) {
                            windowDecorTaskResourceLoader2.densityListOnCache.clear();
                        }
                    }
                });
            }
        }, this);
        this.densityListOnCache = new ConcurrentHashMap();
    }

    public static Bitmap createDisplayContextAppIcon(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, ActivityInfo activityInfo, PackageManager packageManager) {
        return WindowDecorTaskResourceLoaderKt.createIconFactory(R.dimen.mw_desktop_open_menu_app_icon_size, context).createIconBitmap(packageManager.getUserBadgedIcon(new IconProvider(context).getIcon(activityInfo, context.getResources().getDisplayMetrics().densityDpi), UserHandle.of(runningTaskInfo.userId)), 1.0f, 0);
    }

    public final void checkWindowDecorExists(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (!this.existingTasks.contains(Integer.valueOf(runningTaskInfo.taskId))) {
            throw new IllegalStateException("Attempt to obtain resource for non-existent decoration");
        }
    }

    public final Bitmap getHeaderIcon(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean z = CoreRune.MW_CAPTION_BUG_FIX;
        if (!z) {
            checkWindowDecorExists(runningTaskInfo);
        } else if (!this.existingTasks.contains(Integer.valueOf(runningTaskInfo.taskId))) {
            return this.headerIconFactory.createIconBitmap(this.userProfilesContexts.getOrCreate(runningTaskInfo.userId).getPackageManager().getDefaultActivityIcon(), 1.0f, 0);
        }
        AppResources appResources = (AppResources) this.taskToResourceCache.get(Integer.valueOf(runningTaskInfo.taskId));
        if (appResources != null) {
            if (z) {
                int i = runningTaskInfo.getConfiguration().densityDpi;
                Integer num = (Integer) this.densityListOnCache.get(Integer.valueOf(runningTaskInfo.taskId));
                if (num == null || i != num.intValue()) {
                }
            }
            return appResources.appIcon;
        }
        AppResources appResourcesLoadAppResources = loadAppResources(runningTaskInfo);
        this.localeListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), runningTaskInfo.getConfiguration().getLocales());
        if (z) {
            this.densityListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(runningTaskInfo.getConfiguration().densityDpi));
        }
        return appResourcesLoadAppResources.appIcon;
    }

    public final CharSequence getName(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean z = CoreRune.MW_CAPTION_BUG_FIX;
        if (!z) {
            checkWindowDecorExists(runningTaskInfo);
        } else if (!this.existingTasks.contains(Integer.valueOf(runningTaskInfo.taskId))) {
            return "";
        }
        AppResources appResources = (AppResources) this.taskToResourceCache.get(Integer.valueOf(runningTaskInfo.taskId));
        LocaleList localeList = (LocaleList) this.localeListOnCache.get(Integer.valueOf(runningTaskInfo.taskId));
        if (appResources != null && runningTaskInfo.getConfiguration().getLocales().equals(localeList)) {
            return appResources.appName;
        }
        AppResources appResourcesLoadAppResources = loadAppResources(runningTaskInfo);
        this.localeListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), runningTaskInfo.getConfiguration().getLocales());
        if (z) {
            this.densityListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(runningTaskInfo.getConfiguration().densityDpi));
        }
        return appResourcesLoadAppResources.appName;
    }

    public final AppResources loadAppResources(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Bitmap bitmapCreateIconBitmap;
        BaseIconFactory baseIconFactory = this.veilIconFactory;
        BaseIconFactory baseIconFactory2 = this.headerIconFactory;
        UserProfileContexts userProfileContexts = this.userProfilesContexts;
        Trace.beginSection("AppResourceProvider#loadAppResources");
        try {
            PackageManager packageManager = userProfileContexts.getOrCreate(runningTaskInfo.userId).getPackageManager();
            packageManager.getClass();
            ComponentName component = runningTaskInfo.baseIntent.getComponent();
            component.getClass();
            ActivityInfo activityInfo = packageManager.getActivityInfo(component, 0);
            CharSequence applicationLabel = packageManager.getApplicationLabel(activityInfo.applicationInfo);
            Drawable drawableSemGetDrawableForIconTray = packageManager.semGetDrawableForIconTray(this.iconProvider.getIcon(activityInfo), 1);
            Drawable userBadgedIcon = packageManager.getUserBadgedIcon(drawableSemGetDrawableForIconTray, UserHandle.of(runningTaskInfo.userId));
            Context displayContext = this.displayController.getDisplayContext(runningTaskInfo.displayId);
            if (!CoreRune.MW_CAPTION_DESKTOP || displayContext == null || this.context.getDisplayId() == runningTaskInfo.displayId) {
                bitmapCreateIconBitmap = baseIconFactory2.createIconBitmap(userBadgedIcon, 1.0f, 0);
                bitmapCreateIconBitmap.getClass();
            } else {
                bitmapCreateIconBitmap = createDisplayContextAppIcon(runningTaskInfo, displayContext, activityInfo, packageManager);
            }
            AppResources appResources = new AppResources(applicationLabel, bitmapCreateIconBitmap, baseIconFactory.createScaledBitmap(drawableSemGetDrawableForIconTray, 0));
            this.taskToResourceCache.put(Integer.valueOf(runningTaskInfo.taskId), appResources);
            return appResources;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e("AppResourceProvider", "Failed to get app resources");
            Drawable defaultActivityIcon = userProfileContexts.getOrCreate(runningTaskInfo.userId).getPackageManager().getDefaultActivityIcon();
            return new AppResources("", baseIconFactory2.createIconBitmap(defaultActivityIcon, 1.0f, 0), baseIconFactory.createScaledBitmap(defaultActivityIcon, 0));
        } finally {
            Trace.endSection();
        }
    }

    public WindowDecorTaskResourceLoader(Context context, ShellInit shellInit, DisplayController displayController, ShellController shellController, ShellCommandHandler shellCommandHandler, UserProfileContexts userProfileContexts) {
        this(shellInit, context, displayController, shellController, shellCommandHandler, userProfileContexts, new IconProvider(context), WindowDecorTaskResourceLoaderKt.createIconFactory(R.dimen.desktop_mode_caption_icon_radius, context), WindowDecorTaskResourceLoaderKt.createIconFactory(R.dimen.desktop_mode_resize_veil_icon_size, context));
    }

    public static /* synthetic */ void getLocaleListOnCache$annotations() {
    }

    public static /* synthetic */ void getTaskToResourceCache$annotations() {
    }
}
