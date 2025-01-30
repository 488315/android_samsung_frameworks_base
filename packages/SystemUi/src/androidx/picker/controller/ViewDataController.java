package androidx.picker.controller;

import android.icu.text.AlphabeticIndex;
import android.os.LocaleList;
import android.text.TextUtils;
import androidx.picker.adapter.AbsAdapter;
import androidx.picker.adapter.HeaderFooterAdapter;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.controller.strategy.Strategy;
import androidx.picker.model.AppInfo;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.widget.SeslAppPickerView$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewDataController extends DataController {
    public List appDataList = new ArrayList();
    public Comparator order;
    public final Strategy strategy;

    public ViewDataController(Strategy strategy) {
        this.strategy = strategy;
    }

    public final ViewData getViewData(AppInfo appInfo) {
        List list = this.dataList;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Object obj : list) {
            linkedHashMap.put(((ViewData) obj).getKey(), obj);
        }
        return (ViewData) linkedHashMap.get(appInfo);
    }

    public final void submit(List list, Comparator comparator) {
        Strategy strategy = this.strategy;
        strategy.clear();
        List<ViewData> convert = strategy.convert(list, comparator);
        ArrayList arrayList = (ArrayList) this.dataList;
        arrayList.clear();
        arrayList.addAll(convert);
        Iterator it = ((ArrayList) this.listeners).iterator();
        while (it.hasNext()) {
            SeslAppPickerView$$ExternalSyntheticLambda2 seslAppPickerView$$ExternalSyntheticLambda2 = (SeslAppPickerView$$ExternalSyntheticLambda2) it.next();
            HeaderFooterAdapter headerFooterAdapter = seslAppPickerView$$ExternalSyntheticLambda2.f$0.mAdapter;
            if (headerFooterAdapter != null) {
                AbsAdapter absAdapter = headerFooterAdapter.wrappedAdapter;
                absAdapter.getClass();
                LogTagHelperKt.info(absAdapter, "submitList list=" + arrayList.size());
                ((ArrayList) absAdapter.mDataSet).clear();
                ((ArrayList) absAdapter.mDataSet).addAll(arrayList);
                ((HashMap) absAdapter.mSectionMap).clear();
                ArrayList arrayList2 = new ArrayList();
                LocaleList locales = absAdapter.mContext.getResources().getConfiguration().getLocales();
                if (locales.size() == 0) {
                    locales = new LocaleList(Locale.ENGLISH);
                }
                AlphabeticIndex alphabeticIndex = new AlphabeticIndex(locales.get(0));
                int size = locales.size();
                for (int i = 1; i < size; i++) {
                    alphabeticIndex.addLabels(locales.get(i));
                }
                alphabeticIndex.addLabels(Locale.ENGLISH);
                AlphabeticIndex.ImmutableIndex buildImmutableIndex = alphabeticIndex.buildImmutableIndex();
                absAdapter.mPositionToSectionIndex = new int[((ArrayList) absAdapter.mDataSetFiltered).size()];
                for (int i2 = 0; i2 < ((ArrayList) absAdapter.mDataSetFiltered).size(); i2++) {
                    ViewData viewData = (ViewData) ((ArrayList) absAdapter.mDataSetFiltered).get(i2);
                    if (viewData instanceof AppInfoViewData) {
                        String label = ((AppInfoViewData) viewData).getLabel();
                        if (TextUtils.isEmpty(label)) {
                            label = "";
                        }
                        String label2 = buildImmutableIndex.getBucket(buildImmutableIndex.getBucketIndex(label)).getLabel();
                        if (!((HashMap) absAdapter.mSectionMap).containsKey(label2)) {
                            arrayList2.add(label2);
                            ((HashMap) absAdapter.mSectionMap).put(label2, Integer.valueOf(i2));
                        }
                        absAdapter.mPositionToSectionIndex[i2] = arrayList2.size() - 1;
                    }
                }
                String[] strArr = new String[arrayList2.size()];
                absAdapter.mSections = strArr;
                arrayList2.toArray(strArr);
                absAdapter.getFilter().filter(absAdapter.mSearchText);
            }
            seslAppPickerView$$ExternalSyntheticLambda2.f$1.run();
        }
    }
}
