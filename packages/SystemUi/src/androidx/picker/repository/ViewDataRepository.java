package androidx.picker.repository;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.features.observable.UpdateMutableState;
import androidx.picker.helper.PackageManagerHelperImpl;
import androidx.picker.loader.AppIconFlow;
import androidx.picker.loader.DataLoader;
import androidx.picker.loader.DataLoaderImpl;
import androidx.picker.loader.select.SelectStateLoader;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.appdata.CategoryAppData;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.CategoryViewData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.EmptyList;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewDataRepository {
    public final DataLoader dataLoader;
    public final SelectStateLoader selectStateLoader;

    public ViewDataRepository(DataLoader dataLoader, SelectStateLoader selectStateLoader) {
        this.dataLoader = dataLoader;
        this.selectStateLoader = selectStateLoader;
    }

    public final AppInfoViewData createAppInfoViewData(final AppInfoData appInfoData) {
        String str;
        AppInfo appInfo = appInfoData.getAppInfo();
        UpdateMutableState updateMutableState = new UpdateMutableState(appInfoData) { // from class: androidx.picker.repository.ViewDataRepository$createAppInfoViewData$1
            @Override // androidx.picker.features.observable.MutableState
            public final Object getValue() {
                return ((AppInfoData) this.base).getIcon();
            }

            @Override // androidx.picker.features.observable.MutableState
            public final void setValue(Object obj) {
                ((AppInfoData) this.base).setIcon((Drawable) obj);
            }
        };
        DataLoaderImpl dataLoaderImpl = (DataLoaderImpl) this.dataLoader;
        AppInfoViewData appInfoViewData = new AppInfoViewData(appInfoData, new AppIconFlow(updateMutableState, dataLoaderImpl.loadIcon(appInfo)), this.selectStateLoader.createSelectableItem(appInfoData), 0, null, 24, null);
        String label = appInfoData.getLabel();
        if (label == null) {
            Map map = (Map) dataLoaderImpl.labelMap$delegate.getValue();
            Object obj = map.get(appInfo);
            Object obj2 = obj;
            if (obj == null) {
                PackageManagerHelperImpl packageManagerHelperImpl = (PackageManagerHelperImpl) dataLoaderImpl.packageManagerHelper;
                packageManagerHelperImpl.getClass();
                if (!StringsKt__StringsJVMKt.isBlank(appInfo.activityName)) {
                    String str2 = appInfo.packageName;
                    String str3 = appInfo.activityName;
                    int i = appInfo.user;
                    ComponentName componentName = new ComponentName(str2, str3);
                    PackageManager packageManager = packageManagerHelperImpl.getPackageManager(i, str2);
                    try {
                        str = packageManager.getActivityInfo(componentName, 0).loadLabel(packageManager).toString();
                    } catch (PackageManager.NameNotFoundException unused) {
                        LogTagHelperKt.info(packageManagerHelperImpl, "can't find label for " + componentName);
                        str = "Unknown";
                        LogTagHelperKt.debug(packageManagerHelperImpl, "getAppLabel key=" + appInfo.packageName + ", value=" + str);
                        map.put(appInfo, str);
                        obj2 = str;
                        label = (String) obj2;
                        appInfoViewData.setLabel(label);
                        return appInfoViewData;
                    }
                } else {
                    String str4 = appInfo.packageName;
                    PackageManager packageManager2 = packageManagerHelperImpl.getPackageManager(appInfo.user, str4);
                    try {
                        str = (String) packageManager2.getApplicationLabel(packageManager2.getApplicationInfo(str4, 0));
                    } catch (PackageManager.NameNotFoundException unused2) {
                        LogTagHelperKt.info(packageManagerHelperImpl, "can't find label for " + str4);
                        str = "Unknown";
                        LogTagHelperKt.debug(packageManagerHelperImpl, "getAppLabel key=" + appInfo.packageName + ", value=" + str);
                        map.put(appInfo, str);
                        obj2 = str;
                        label = (String) obj2;
                        appInfoViewData.setLabel(label);
                        return appInfoViewData;
                    }
                }
                LogTagHelperKt.debug(packageManagerHelperImpl, "getAppLabel key=" + appInfo.packageName + ", value=" + str);
                map.put(appInfo, str);
                obj2 = str;
            }
            label = (String) obj2;
        }
        appInfoViewData.setLabel(label);
        return appInfoViewData;
    }

    public final CategoryViewData createCategoryViewData(CategoryAppData categoryAppData, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SelectableItem selectableItem = ((AppInfoViewData) it.next()).selectableItem;
            if (selectableItem != null) {
                arrayList.add(selectableItem);
            }
        }
        return new CategoryViewData(categoryAppData, this.selectStateLoader.createCategorySelectableItem(categoryAppData, arrayList), new ArrayList(EmptyList.INSTANCE));
    }
}
