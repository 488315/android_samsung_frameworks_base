package androidx.picker.controller.strategy;

import androidx.picker.loader.select.AllAppsSelectableItem;
import androidx.picker.loader.select.CategorySelectableItem;
import androidx.picker.loader.select.SelectStateLoader;
import androidx.picker.model.AppData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.di.AppPickerContext;
import androidx.picker.repository.ViewDataRepository;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Strategy {
    private final AppPickerContext appPickerContext;

    public Strategy(AppPickerContext appPickerContext) {
        this.appPickerContext = appPickerContext;
    }

    public final void clear() {
        SelectStateLoader selectStateLoader = ((ViewDataRepository) this.appPickerContext.viewDataRepository$delegate.getValue()).selectStateLoader;
        AllAppsSelectableItem allAppsSelectableItem = selectStateLoader.allAppsSelectableItem;
        if (allAppsSelectableItem != null) {
            allAppsSelectableItem.dispose();
        }
        selectStateLoader.allAppsSelectableItem = null;
        LinkedHashMap linkedHashMap = (LinkedHashMap) selectStateLoader.categorySelectableItemMap;
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            ((CategorySelectableItem) ((Map.Entry) it.next()).getValue()).dispose();
        }
        linkedHashMap.clear();
    }

    public abstract List<ViewData> convert(List<? extends AppData> list, Comparator<ViewData> comparator);

    public final AppPickerContext getAppPickerContext() {
        return this.appPickerContext;
    }
}
