package androidx.picker.model.appdata;

import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GroupAppData implements AppData {
    public final List appDataList;
    public final AppInfo appInfo;
    public final String group;

    public GroupAppData(AppInfo appInfo) {
        this(appInfo, (String) null, (List) null, 6, (DefaultConstructorMarker) null);
    }

    @Override // androidx.picker.model.AppData
    public final AppInfo getAppInfo() {
        return this.appInfo;
    }

    public GroupAppData(AppInfo appInfo, String str) {
        this(appInfo, str, (List) null, 4, (DefaultConstructorMarker) null);
    }

    public GroupAppData(AppInfo appInfo, String str, List<? extends AppData> list) {
        this.appInfo = appInfo;
        this.group = str;
        this.appDataList = list;
    }

    public /* synthetic */ GroupAppData(String str, String str2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GroupAppData(String str, String str2, int i) {
        this(new AppInfo(str, str2, i), (String) null, (List) null, 6, (DefaultConstructorMarker) null);
        AppInfo.Companion.getClass();
    }

    public GroupAppData(AppInfo appInfo, String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appInfo, (i & 2) != 0 ? "" : str, (List<? extends AppData>) ((i & 4) != 0 ? EmptyList.INSTANCE : list));
    }
}
