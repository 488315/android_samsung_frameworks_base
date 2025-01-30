package androidx.picker.loader;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.picker.features.scs.AbstractAppDataListFactory;
import androidx.picker.helper.PackageManagerHelper;
import androidx.picker.helper.PackageManagerHelperImpl;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.reflect.SeslBaseReflector;
import com.android.systemui.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DataLoaderImpl implements DataLoader {
    public final AbstractAppDataListFactory factory;
    public final PackageManagerHelper packageManagerHelper;
    public final Lazy labelMap$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.loader.DataLoaderImpl$labelMap$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            List<AppInfoData> dataList = DataLoaderImpl.this.factory.getDataList();
            HashMap hashMap = new HashMap();
            for (AppInfoData appInfoData : dataList) {
                hashMap.put(appInfoData.getAppInfo(), appInfoData.getLabel());
            }
            return hashMap;
        }
    });
    public final DataLoaderImpl$iconLoader$1 iconLoader = new CachedLoader() { // from class: androidx.picker.loader.DataLoaderImpl$iconLoader$1
        /* JADX WARN: Removed duplicated region for block: B:12:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x00ac  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x009d  */
        @Override // androidx.picker.loader.CachedLoader
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object createValue(Object obj) {
            Drawable drawable;
            AppInfo appInfo = (AppInfo) obj;
            DataLoaderImpl dataLoaderImpl = DataLoaderImpl.this;
            dataLoaderImpl.getClass();
            boolean z = !StringsKt__StringsJVMKt.isBlank(appInfo.activityName);
            Drawable drawable2 = null;
            PackageManagerHelper packageManagerHelper = dataLoaderImpl.packageManagerHelper;
            try {
                if (!z || !(!StringsKt__StringsJVMKt.isBlank(appInfo.activityName))) {
                    String str = appInfo.packageName;
                    PackageManagerHelperImpl packageManagerHelperImpl = (PackageManagerHelperImpl) packageManagerHelper;
                    PackageManager packageManager = packageManagerHelperImpl.getPackageManager(appInfo.user, str);
                    Method method = SeslBaseReflector.getMethod("android.app.ApplicationPackageManager", "semGetApplicationIconForIconTray", String.class, Integer.TYPE);
                    if (method != null) {
                        Object invoke = SeslBaseReflector.invoke(packageManager, method, str, 1);
                        if (invoke instanceof Drawable) {
                            drawable = (Drawable) invoke;
                            if (drawable == null) {
                                String str2 = appInfo.packageName;
                                drawable2 = packageManagerHelperImpl.getPackageManager(appInfo.user, str2).getApplicationIcon(str2);
                                drawable = drawable2;
                            }
                            if (drawable == null) {
                                PackageManagerHelperImpl packageManagerHelperImpl2 = (PackageManagerHelperImpl) packageManagerHelper;
                                packageManagerHelperImpl2.getClass();
                                Object obj2 = ContextCompat.sLock;
                                drawable = packageManagerHelperImpl2.context.getDrawable(R.drawable.sesl_search_icon_background_borderless);
                                Intrinsics.checkNotNull(drawable);
                            }
                            Context context = ((PackageManagerHelperImpl) packageManagerHelper).context;
                            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.picker_app_grid_icon_size);
                            return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(DrawableKt.toBitmap$default(drawable), dimensionPixelSize, dimensionPixelSize, true));
                        }
                    }
                    drawable = null;
                    if (drawable == null) {
                    }
                    if (drawable == null) {
                    }
                    Context context2 = ((PackageManagerHelperImpl) packageManagerHelper).context;
                    int dimensionPixelSize2 = context2.getResources().getDimensionPixelSize(R.dimen.picker_app_grid_icon_size);
                    return new BitmapDrawable(context2.getResources(), Bitmap.createScaledBitmap(DrawableKt.toBitmap$default(drawable), dimensionPixelSize2, dimensionPixelSize2, true));
                }
                String str3 = appInfo.packageName;
                String str4 = appInfo.activityName;
                int i = appInfo.user;
                PackageManagerHelperImpl packageManagerHelperImpl3 = (PackageManagerHelperImpl) packageManagerHelper;
                packageManagerHelperImpl3.getClass();
                ComponentName componentName = new ComponentName(str3, str4);
                PackageManager packageManager2 = packageManagerHelperImpl3.getPackageManager(i, str3);
                Method method2 = SeslBaseReflector.getMethod("android.app.ApplicationPackageManager", "semGetActivityIconForIconTray", ComponentName.class, Integer.TYPE);
                if (method2 != null) {
                    Object invoke2 = SeslBaseReflector.invoke(packageManager2, method2, componentName, 1);
                    if (invoke2 instanceof Drawable) {
                        drawable = (Drawable) invoke2;
                        if (drawable == null) {
                            String str5 = appInfo.packageName;
                            String str6 = appInfo.activityName;
                            drawable2 = packageManagerHelperImpl3.getPackageManager(appInfo.user, str5).getActivityIcon(new ComponentName(str5, str6));
                            drawable = drawable2;
                        }
                        if (drawable == null) {
                        }
                        Context context22 = ((PackageManagerHelperImpl) packageManagerHelper).context;
                        int dimensionPixelSize22 = context22.getResources().getDimensionPixelSize(R.dimen.picker_app_grid_icon_size);
                        return new BitmapDrawable(context22.getResources(), Bitmap.createScaledBitmap(DrawableKt.toBitmap$default(drawable), dimensionPixelSize22, dimensionPixelSize22, true));
                    }
                }
                drawable = null;
                if (drawable == null) {
                }
                if (drawable == null) {
                }
                Context context222 = ((PackageManagerHelperImpl) packageManagerHelper).context;
                int dimensionPixelSize222 = context222.getResources().getDimensionPixelSize(R.dimen.picker_app_grid_icon_size);
                return new BitmapDrawable(context222.getResources(), Bitmap.createScaledBitmap(DrawableKt.toBitmap$default(drawable), dimensionPixelSize222, dimensionPixelSize222, true));
                return new BitmapDrawable(context222.getResources(), Bitmap.createScaledBitmap(DrawableKt.toBitmap$default(drawable), dimensionPixelSize222, dimensionPixelSize222, true));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return drawable;
            }
            drawable = drawable2;
            if (drawable == null) {
            }
            Context context2222 = ((PackageManagerHelperImpl) packageManagerHelper).context;
            int dimensionPixelSize2222 = context2222.getResources().getDimensionPixelSize(R.dimen.picker_app_grid_icon_size);
        }
    };

    /* JADX WARN: Type inference failed for: r1v3, types: [androidx.picker.loader.DataLoaderImpl$iconLoader$1] */
    public DataLoaderImpl(AbstractAppDataListFactory abstractAppDataListFactory, PackageManagerHelper packageManagerHelper) {
        this.factory = abstractAppDataListFactory;
        this.packageManagerHelper = packageManagerHelper;
    }

    public final Flow loadIcon(AppInfo appInfo) {
        DataLoaderImpl$iconLoader$1 dataLoaderImpl$iconLoader$1 = this.iconLoader;
        dataLoaderImpl$iconLoader$1.getClass();
        return FlowKt.flowOn(new SafeFlow(new CachedLoader$load$1(dataLoaderImpl$iconLoader$1, appInfo, null)), Dispatchers.Default);
    }
}
