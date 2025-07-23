package com.android.systemui.statusbar.notification.row.icon;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotifCollectionCache;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AppIconProviderImpl implements AppIconProvider, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final NotifCollectionCache cache;
    public final Context sysuiContext;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotificationIcons extends BaseIconFactory {
        public NotificationIcons(Context context, int i, int i2) {
            super(context, i, i2);
        }
    }

    static {
        new Companion(null);
    }

    public AppIconProviderImpl(Context context, DumpManager dumpManager) {
        this.sysuiContext = context;
        dumpManager.registerNormalDumpable("AppIconProviderImpl", this);
        this.cache = new NotifCollectionCache(0, 0L, null, 7, null);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("cache information:");
        asIndenting.increaseIndent();
        try {
            this.cache.dump(asIndenting, strArr);
            asIndenting.decreaseIndent();
            getIconFactory();
            asIndenting.println("icon factory information:");
            asIndenting.increaseIndent();
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    public final NotificationIcons getIconFactory() {
        boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
        Resources resources = this.sysuiContext.getResources();
        return new NotificationIcons(this.sysuiContext, resources.getConfiguration().densityDpi, resources.getDimensionPixelSize(isLowRamDeviceStatic ? 17105816 : 17105815));
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.AppIconProvider
    public final Drawable getOrFetchAppIcon(final Context context, final String str, final boolean z) {
        return (Drawable) this.cache.getOrFetch(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, z ? "|WORK" : ""), new Function1() { // from class: com.android.systemui.statusbar.notification.row.icon.AppIconProviderImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context2 = context;
                int i = AppIconProviderImpl.$r8$clinit;
                AppIconProviderImpl appIconProviderImpl = AppIconProviderImpl.this;
                appIconProviderImpl.getClass();
                PackageManager packageManager = context2.getPackageManager();
                Drawable loadUnbadgedIcon = packageManager.getApplicationInfo(str, 0).loadUnbadgedIcon(packageManager);
                BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
                iconOptions.mUserIconInfo = new UserIconInfo(UserHandle.of(context2.getUserId()), z ? 1 : 0);
                iconOptions.mGenerationMode = 3;
                iconOptions.mExtractedColor = -16776961;
                return appIconProviderImpl.getIconFactory().createBadgedIconBitmap(loadUnbadgedIcon, iconOptions).newIcon$1(0, appIconProviderImpl.sysuiContext);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.AppIconProvider
    public final void purgeCache(Collection collection) {
        ArrayList arrayList = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            CollectionsKt__MutableCollectionsKt.addAll(Arrays.asList(str, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "|WORK")), arrayList);
        }
        this.cache.purge(arrayList);
    }
}
