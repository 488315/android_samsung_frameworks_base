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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowDecorTaskResourceLoader {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        WindowDecorTaskResourceLoader windowDecorTaskResourceLoader2 = WindowDecorTaskResourceLoader.this;
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
                        WindowDecorTaskResourceLoader.this.taskToResourceCache.clear();
                    }
                });
            }
        }, this);
    }

    public final void checkWindowDecorExists(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (!this.existingTasks.contains(Integer.valueOf(runningTaskInfo.taskId))) {
            throw new IllegalStateException("Attempt to obtain resource for non-existent decoration");
        }
    }

    public final Bitmap getHeaderIcon(ActivityManager.RunningTaskInfo runningTaskInfo) {
        checkWindowDecorExists(runningTaskInfo);
        AppResources appResources = (AppResources) this.taskToResourceCache.get(Integer.valueOf(runningTaskInfo.taskId));
        if (appResources != null) {
            return appResources.appIcon;
        }
        AppResources loadAppResources = loadAppResources(runningTaskInfo);
        this.taskToResourceCache.put(Integer.valueOf(runningTaskInfo.taskId), loadAppResources);
        this.localeListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), runningTaskInfo.getConfiguration().getLocales());
        return loadAppResources.appIcon;
    }

    public final CharSequence getName(ActivityManager.RunningTaskInfo runningTaskInfo) {
        checkWindowDecorExists(runningTaskInfo);
        AppResources appResources = (AppResources) this.taskToResourceCache.get(Integer.valueOf(runningTaskInfo.taskId));
        LocaleList localeList = (LocaleList) this.localeListOnCache.get(Integer.valueOf(runningTaskInfo.taskId));
        if (appResources != null && runningTaskInfo.getConfiguration().getLocales().equals(localeList)) {
            return appResources.appName;
        }
        AppResources loadAppResources = loadAppResources(runningTaskInfo);
        this.taskToResourceCache.put(Integer.valueOf(runningTaskInfo.taskId), loadAppResources);
        this.localeListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), runningTaskInfo.getConfiguration().getLocales());
        return loadAppResources.appName;
    }

    public final AppResources loadAppResources(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Context displayContext;
        Trace.beginSection("AppResourceProvider#loadAppResources");
        try {
            UserProfileContexts userProfileContexts = this.userProfilesContexts;
            int i = runningTaskInfo.userId;
            Context context = (Context) userProfileContexts.currentProfilesContext.get(i);
            if (context == null) {
                context = userProfileContexts.baseContext.createContextAsUser(UserHandle.of(i), 0);
                userProfileContexts.currentProfilesContext.set(i, context);
            }
            PackageManager packageManager = context.getPackageManager();
            packageManager.getClass();
            ComponentName component = runningTaskInfo.baseIntent.getComponent();
            component.getClass();
            ActivityInfo activityInfo = packageManager.getActivityInfo(component, 0);
            CharSequence applicationLabel = packageManager.getApplicationLabel(activityInfo.applicationInfo);
            Drawable icon = this.iconProvider.getIcon(activityInfo);
            Bitmap createIconBitmap = this.headerIconFactory.createIconBitmap(packageManager.getUserBadgedIcon(icon, UserHandle.of(runningTaskInfo.userId)), 1.0f, 0);
            if (CoreRune.MW_CAPTION_DESKTOP) {
                int displayId = this.context.getDisplayId();
                int i2 = runningTaskInfo.displayId;
                if (displayId != i2 && (displayContext = this.displayController.getDisplayContext(i2)) != null) {
                    createIconBitmap = WindowDecorTaskResourceLoaderKt.createIconFactory(R.dimen.mw_desktop_open_menu_app_icon_size, displayContext).createIconBitmap(packageManager.getUserBadgedIcon(new IconProvider(displayContext).getIcon(activityInfo, displayContext.getResources().getDisplayMetrics().densityDpi), UserHandle.of(runningTaskInfo.userId)), 1.0f, 0);
                }
            }
            return new AppResources(applicationLabel, createIconBitmap, this.veilIconFactory.createScaledBitmap(icon, 0));
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
