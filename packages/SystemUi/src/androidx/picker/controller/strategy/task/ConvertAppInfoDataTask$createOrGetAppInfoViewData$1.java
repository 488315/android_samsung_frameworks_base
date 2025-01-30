package androidx.picker.controller.strategy.task;

import android.graphics.drawable.Drawable;
import androidx.picker.loader.AppIconFlow;
import androidx.picker.loader.select.AppDataSelectableItem;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.viewdata.AppInfoViewData;
import java.util.LinkedHashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConvertAppInfoDataTask$createOrGetAppInfoViewData$1 extends Lambda implements Function1 {
    final /* synthetic */ Function1 $createAppInfoViewData;
    final /* synthetic */ ConvertAppInfoDataTask this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConvertAppInfoDataTask$createOrGetAppInfoViewData$1(ConvertAppInfoDataTask convertAppInfoDataTask, Function1 function1) {
        super(1);
        this.this$0 = convertAppInfoDataTask;
        this.$createAppInfoViewData = function1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0062, code lost:
    
        if (r0 != null) goto L25;
     */
    @Override // kotlin.jvm.functions.Function1
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj) {
        AppInfoData appInfoData = (AppInfoData) obj;
        AppInfoViewData appInfoViewData = (AppInfoViewData) ((LinkedHashMap) this.this$0.cachedAppInfoViewDataMap).get(appInfoData.getAppInfo());
        if (appInfoViewData != null) {
            AppInfoData appInfoData2 = appInfoViewData.appInfoData;
            if (appInfoData2 != appInfoData) {
                if (appInfoData2.equals(appInfoData)) {
                    appInfoViewData = null;
                } else {
                    Drawable icon = appInfoData.getIcon();
                    if (icon == null) {
                        icon = appInfoViewData.getIcon();
                    }
                    appInfoData.setIcon(icon);
                    String label = appInfoData.getLabel();
                    if (label == null) {
                        label = appInfoViewData.getLabel();
                    }
                    appInfoData.setLabel(label);
                    SelectableItem selectableItem = appInfoViewData.selectableItem;
                    AppDataSelectableItem appDataSelectableItem = selectableItem instanceof AppDataSelectableItem ? (AppDataSelectableItem) selectableItem : null;
                    if (appDataSelectableItem != null) {
                        appDataSelectableItem.updateBase(appInfoData);
                    }
                    appInfoViewData.dimmedItem.update(appInfoData);
                    AppIconFlow appIconFlow = appInfoViewData.iconFlow;
                    appIconFlow.base.base = appInfoData;
                    appInfoViewData = new AppInfoViewData(appInfoData, appIconFlow, appInfoViewData.selectableItem, appInfoViewData.spanCount, appInfoViewData.onActionClick);
                }
            }
        }
        appInfoViewData = (AppInfoViewData) this.$createAppInfoViewData.invoke(appInfoData);
        this.this$0.cachedAppInfoViewDataMap.put(appInfoData.getAppInfo(), appInfoViewData);
        return appInfoViewData;
    }
}
