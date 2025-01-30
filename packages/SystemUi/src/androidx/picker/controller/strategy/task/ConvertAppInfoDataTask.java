package androidx.picker.controller.strategy.task;

import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.viewdata.AppInfoViewData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConvertAppInfoDataTask {
    public final Map cachedAppInfoViewDataMap = new LinkedHashMap();
    public final Function1 createOrGetAppInfoViewData;

    public ConvertAppInfoDataTask(Function1 function1) {
        this.createOrGetAppInfoViewData = new ConvertAppInfoDataTask$createOrGetAppInfoViewData$1(this, function1);
    }

    public final List execute(List list) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppData) it.next()).getAppInfo());
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
        Map map = this.cachedAppInfoViewDataMap;
        Set keySet = ((LinkedHashMap) map).keySet();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : keySet) {
            if (!set.contains((AppInfo) obj)) {
                arrayList2.add(obj);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            map.remove((AppInfo) it2.next());
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            arrayList3.add((AppInfoViewData) ((ConvertAppInfoDataTask$createOrGetAppInfoViewData$1) this.createOrGetAppInfoViewData).invoke((AppInfoData) it3.next()));
        }
        return arrayList3;
    }
}
