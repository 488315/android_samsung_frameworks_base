package com.android.systemui.qs.bar.repository;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

public final class BarOrderRepository {
    public final Context context;
    public final TunerService tunerService;
    public final UserTracker userTracker;
    public final List nonEditableBars = CollectionsKt__CollectionsKt.listOf(BarClsName.VIDEOCALL_MICMODE_BAR.getCls(), BarClsName.SECURITY_FOOTER_BAR.getCls());
    public int collapsedBarRow = loadCollapsedBarRow();
    public List barOrder = CollectionsKt___CollectionsKt.toList(StringsKt__StringsKt.split$default(loadBarOrder(), new String[]{","}, 0, 6));

    public final class BarClsName {
        public static final /* synthetic */ BarClsName[] $VALUES;
        public static final BarClsName SECURITY_FOOTER_BAR;
        public static final BarClsName VIDEOCALL_MICMODE_BAR;
        private final String cls;

        static {
            BarClsName barClsName = new BarClsName("VIDEOCALL_MICMODE_BAR", 0, BarType.VIDEO_CALL_MIC_MODE.getCls().getSimpleName());
            VIDEOCALL_MICMODE_BAR = barClsName;
            BarClsName barClsName2 = new BarClsName("SECURITY_FOOTER_BAR", 1, BarType.SECURITY_FOOTER.getCls().getSimpleName());
            SECURITY_FOOTER_BAR = barClsName2;
            BarClsName[] barClsNameArr = {barClsName, barClsName2};
            $VALUES = barClsNameArr;
            EnumEntriesKt.enumEntries(barClsNameArr);
        }

        private BarClsName(String str, int i, String str2) {
            this.cls = str2;
        }

        public static BarClsName valueOf(String str) {
            return (BarClsName) Enum.valueOf(BarClsName.class, str);
        }

        public static BarClsName[] values() {
            return (BarClsName[]) $VALUES.clone();
        }

        public final String getCls() {
            return this.cls;
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BarOrderRepository(TunerService tunerService, Context context, UserTracker userTracker) {
        this.tunerService = tunerService;
        this.context = context;
        this.userTracker = userTracker;
    }

    public final String getDefaultBarOrderList() {
        String string = this.context.getResources().getString(R.string.sec_qs_bar_order_list_default);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("defaultBarOrderList result : ", string, "BarOrderRepository");
        return string;
    }

    public final String loadBarOrder() {
        TunerService tunerService = this.tunerService;
        String value = tunerService.getValue("sysui_quick_bar_order");
        if (value != null) {
            if (value.length() == 0) {
                value = null;
            }
            if (value != null) {
                List split$default = StringsKt__StringsKt.split$default(getDefaultBarOrderList(), new String[]{","}, 0, 6);
                ArrayList arrayList = new ArrayList();
                for (Object obj : split$default) {
                    if (!StringsKt__StringsKt.contains(value, (String) obj, false)) {
                        arrayList.add(obj);
                    }
                }
                Iterator it = arrayList.iterator();
                String str = "";
                while (it.hasNext()) {
                    str = ((Object) str) + "," + ((String) it.next());
                }
                String str2 = value + ((Object) str);
                tunerService.setValue("sysui_quick_bar_order", str2);
                Log.d("BarOrderRepository", "loadBarOrder() result : ".concat(value));
                if (str2 != null) {
                    return str2;
                }
            }
        }
        Log.d("BarOrderRepository", "loadBarOrder() sysui_quick_bar_order is null, load default list");
        String defaultBarOrderList = getDefaultBarOrderList();
        tunerService.setValue("sysui_quick_bar_order", defaultBarOrderList);
        return defaultBarOrderList;
    }

    public final int loadCollapsedBarRow() {
        int i;
        String value = this.tunerService.getValue("sysui_quick_bar_collapsed_row");
        if (value != null) {
            if (value.length() == 0) {
                value = null;
            }
            if (value != null) {
                i = Integer.parseInt(value);
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "loadCollapsedBarRow() result : ", "BarOrderRepository");
                return i;
            }
        }
        Log.d("BarOrderRepository", "loadCollapsedBarRow() sysui_quick_bar_collapsed_row is null, load default value");
        i = 2;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "loadCollapsedBarRow() result : ", "BarOrderRepository");
        return i;
    }

    public final void setBarOrder(List list) {
        if (list.equals(this.barOrder)) {
            return;
        }
        this.tunerService.setValue("sysui_quick_bar_order", TextUtils.join(",", list));
        Log.d("BarOrderRepository", "setBarOrder user:" + ((UserTrackerImpl) this.userTracker).getUserId() + " result : " + list);
        this.barOrder = list;
    }

    public final void setCollapsedBarRow(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i != this.collapsedBarRow) {
            this.tunerService.setValue("sysui_quick_bar_collapsed_row", String.valueOf(i));
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(((UserTrackerImpl) this.userTracker).getUserId(), i, "setCollapsedBarRow user:", " result : ", "BarOrderRepository");
            this.collapsedBarRow = i;
        }
    }
}
