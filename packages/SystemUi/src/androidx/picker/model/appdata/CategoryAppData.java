package androidx.picker.model.appdata;

import android.graphics.drawable.Drawable;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CategoryAppData implements AppData {
    public final AppInfo appInfo;
    public final List appInfoDataList;
    public final Drawable icon;
    public final String label;

    public CategoryAppData(AppInfo appInfo) {
        this(appInfo, null, null, null, 14, null);
    }

    @Override // androidx.picker.model.AppData
    public final AppInfo getAppInfo() {
        return this.appInfo;
    }

    public final boolean getSelected() {
        List list = this.appInfoDataList;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (!((AppInfoData) it.next()).getSelected()) {
                return false;
            }
        }
        return true;
    }

    public CategoryAppData(AppInfo appInfo, Drawable drawable) {
        this(appInfo, drawable, null, null, 12, null);
    }

    public CategoryAppData(AppInfo appInfo, Drawable drawable, String str) {
        this(appInfo, drawable, str, null, 8, null);
    }

    public CategoryAppData(AppInfo appInfo, Drawable drawable, String str, List<? extends AppInfoData> list) {
        this.appInfo = appInfo;
        this.icon = drawable;
        this.label = str;
        this.appInfoDataList = list;
    }

    public /* synthetic */ CategoryAppData(String str, String str2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CategoryAppData(String str, String str2, int i) {
        this(new AppInfo(str, str2, i), null, null, null, 14, null);
        AppInfo.Companion.getClass();
    }

    public CategoryAppData(AppInfo appInfo, Drawable drawable, String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appInfo, (i & 2) != 0 ? null : drawable, (i & 4) != 0 ? "" : str, (i & 8) != 0 ? EmptyList.INSTANCE : list);
    }
}
