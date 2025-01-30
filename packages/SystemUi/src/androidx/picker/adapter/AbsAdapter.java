package androidx.picker.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import androidx.core.util.Supplier;
import androidx.picker.adapter.viewholder.AppListItemViewHolder;
import androidx.picker.adapter.viewholder.PickerViewHolder;
import androidx.picker.common.log.LogTag;
import androidx.picker.features.composable.ActionableComposableViewHolder;
import androidx.picker.features.observable.UpdateMutableState;
import androidx.picker.features.search.InitialSearchUtils;
import androidx.picker.loader.AppIconFlow;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.AppInfoDataImpl;
import androidx.picker.model.Selectable;
import androidx.picker.model.appdata.CategoryAppData;
import androidx.picker.model.appdata.GroupAppData;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.AppSideViewData;
import androidx.picker.model.viewdata.CategoryViewData;
import androidx.picker.model.viewdata.GroupTitleViewData;
import androidx.picker.model.viewdata.SearchableViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.widget.SeslAppPickerSelectLayout;
import androidx.picker.widget.SeslAppPickerView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsJvmKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbsAdapter extends RecyclerView.Adapter implements Filterable, SectionIndexer, LogTag {
    public final Context mContext;
    public AppPickerAdapter$OnBindListener mOnBindListener;
    public int[] mPositionToSectionIndex;
    public final List mDataSet = new ArrayList();
    public final List mDataSetFiltered = new ArrayList();
    public final Map mSectionMap = new HashMap();
    public String[] mSections = new String[0];
    public String mSearchText = "";
    public C03501 mFilter = null;

    public AbsAdapter(Context context) {
        this.mContext = context;
    }

    public static GroupTitleViewData generateFilterHeader(String str, List list) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            arrayList.add(((AppSideViewData) it.next()).getAppData());
        }
        AppData.GroupAppDataBuilder groupAppDataBuilder = new AppData.GroupAppDataBuilder(str);
        groupAppDataBuilder.label = str;
        groupAppDataBuilder.mAppInfoDataList = arrayList;
        AppInfo.Companion.getClass();
        String str2 = groupAppDataBuilder.key;
        AppInfo appInfo = new AppInfo(str2, "", 0);
        String str3 = groupAppDataBuilder.label;
        if (str3 != null) {
            str2 = str3;
        }
        GroupAppData groupAppData = new GroupAppData(appInfo, str2, (List<? extends AppData>) groupAppDataBuilder.mAppInfoDataList);
        return new GroupTitleViewData(groupAppData, String.valueOf(groupAppData.appDataList.size()));
    }

    public static View inflate(RecyclerView recyclerView, int i) {
        return LayoutInflater.from(recyclerView.getContext()).inflate(i, (ViewGroup) recyclerView, false);
    }

    public final List getAppInfoFilterResult(List list, List list2) {
        boolean z;
        Boolean bool;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            SearchableViewData searchableViewData = (SearchableViewData) it.next();
            Iterator it2 = searchableViewData.getSearchable().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                String str = (String) it2.next();
                String str2 = this.mSearchText;
                if (!TextUtils.isEmpty(str)) {
                    StringTokenizer stringTokenizer = new StringTokenizer(str2.toLowerCase());
                    String replace = str.toLowerCase().trim().replace(" ", "");
                    while (true) {
                        if (!stringTokenizer.hasMoreTokens()) {
                            bool = Boolean.FALSE;
                            break;
                        }
                        if (replace.contains(stringTokenizer.nextToken())) {
                            bool = Boolean.TRUE;
                            break;
                        }
                        if (InitialSearchUtils.getMatchedStringOffset(replace, this.mSearchText.trim().replace(" ", "")) > -1) {
                            bool = Boolean.TRUE;
                            break;
                        }
                    }
                } else {
                    bool = Boolean.FALSE;
                }
                if (bool.booleanValue()) {
                    z = true;
                    break;
                }
            }
            if (!z && (searchableViewData.getKey() instanceof AppInfo)) {
                z = ((ArrayList) list2).contains((AppInfo) searchableViewData.getKey());
            }
            if (z) {
                arrayList.add(searchableViewData);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [android.widget.Filter, androidx.picker.adapter.AbsAdapter$1] */
    @Override // android.widget.Filterable
    public final Filter getFilter() {
        C03501 c03501 = this.mFilter;
        if (c03501 != null) {
            return c03501;
        }
        ?? r0 = new Filter() { // from class: androidx.picker.adapter.AbsAdapter.1
            /* JADX WARN: Code restructure failed: missing block: B:31:0x0094, code lost:
            
                if (r15 != null) goto L34;
             */
            @Override // android.widget.Filter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Filter.FilterResults performFiltering(CharSequence charSequence) {
                String charSequence2 = charSequence.toString();
                Filter.FilterResults filterResults = new Filter.FilterResults();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList(AbsAdapter.this.mDataSet);
                if (charSequence2.isEmpty()) {
                    AbsAdapter absAdapter = AbsAdapter.this;
                    absAdapter.mSearchText = "";
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        ViewData viewData = (ViewData) it.next();
                        if (viewData instanceof CategoryViewData) {
                            arrayList4.addAll(((CategoryViewData) viewData).invisibleChildren);
                        } else if ((viewData instanceof AppInfoViewData) && arrayList4.contains(viewData)) {
                            ((AppInfoViewData) viewData).highlightText.setValue(absAdapter.mSearchText);
                        }
                        arrayList3.add(viewData);
                    }
                    arrayList.addAll(arrayList3);
                } else {
                    AbsAdapter absAdapter2 = AbsAdapter.this;
                    absAdapter2.mSearchText = charSequence2;
                    Context context = absAdapter2.mContext;
                    String[] strArr = InitialSearchUtils.KOREAN_RANGE_PATTERN;
                    ArrayList arrayList5 = new ArrayList();
                    Bundle bundle = new Bundle();
                    bundle.putString("android:query-arg-sql-selection", charSequence2);
                    try {
                        Cursor query = context.getContentResolver().query(InitialSearchUtils.SCS_PROVIDER_URI, null, bundle, null);
                        if (query != null) {
                            try {
                                if (query.moveToFirst()) {
                                    do {
                                        int columnIndex = query.getColumnIndex("label");
                                        int columnIndex2 = query.getColumnIndex("componentName");
                                        int columnIndex3 = query.getColumnIndex("packageName");
                                        int columnIndex4 = query.getColumnIndex("user");
                                        if (columnIndex != -1 && columnIndex2 != -1 && columnIndex3 != -1) {
                                            String string = query.getString(columnIndex2);
                                            String string2 = query.getString(columnIndex3);
                                            String string3 = query.getString(columnIndex4);
                                            AppInfo.Companion companion = AppInfo.Companion;
                                            int parseInt = Integer.parseInt(string3);
                                            companion.getClass();
                                            arrayList5.add(new AppInfo(string2, string, parseInt));
                                        }
                                        Log.e("InitialSearchUtils", String.format("Can't find columnIndex (%s : %d, %s : %d, %s : %d)", "label", Integer.valueOf(columnIndex), "componentName", Integer.valueOf(columnIndex2), "packageName", Integer.valueOf(columnIndex3)));
                                    } while (query.moveToNext());
                                }
                            } finally {
                            }
                        }
                        query.close();
                    } catch (Exception e) {
                        AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Fail to get application query result: ", e, "InitialSearchUtils");
                    }
                    List filterIsInstance = CollectionsKt___CollectionsJvmKt.filterIsInstance(arrayList2, CategoryViewData.class);
                    List filterIsInstance2 = CollectionsKt___CollectionsJvmKt.filterIsInstance(arrayList2, AppInfoViewData.class);
                    List appInfoFilterResult = AbsAdapter.this.getAppInfoFilterResult(filterIsInstance, arrayList5);
                    List appInfoFilterResult2 = AbsAdapter.this.getAppInfoFilterResult(filterIsInstance2, arrayList5);
                    ArrayList arrayList6 = (ArrayList) appInfoFilterResult;
                    if (!arrayList6.isEmpty()) {
                        String string4 = AbsAdapter.this.mContext.getResources().getString(R.string.title_categories);
                        AbsAdapter.this.getClass();
                        arrayList.add(AbsAdapter.generateFilterHeader(string4, appInfoFilterResult));
                        AbsAdapter absAdapter3 = AbsAdapter.this;
                        absAdapter3.getClass();
                        ArrayList arrayList7 = new ArrayList();
                        Iterator it2 = arrayList6.iterator();
                        while (it2.hasNext()) {
                            final CategoryViewData categoryViewData = (CategoryViewData) it2.next();
                            AppData.ListCheckBoxAppDataBuilder listCheckBoxAppDataBuilder = new AppData.ListCheckBoxAppDataBuilder(categoryViewData.appData.appInfo);
                            CategoryAppData categoryAppData = categoryViewData.appData;
                            AppInfoDataImpl appInfoDataImpl = (AppInfoDataImpl) listCheckBoxAppDataBuilder.setLabel(categoryAppData.label).setIcon(categoryAppData.icon).setSelected(categoryViewData.selectableItem.isSelected()).build();
                            arrayList7.add(new AppInfoViewData(appInfoDataImpl, new AppIconFlow(new UpdateMutableState(absAdapter3, appInfoDataImpl, appInfoDataImpl) { // from class: androidx.picker.adapter.AbsAdapter.2
                                public final /* synthetic */ AppInfoDataImpl val$data;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(appInfoDataImpl);
                                    this.val$data = appInfoDataImpl;
                                }

                                @Override // androidx.picker.features.observable.MutableState
                                public final Object getValue() {
                                    return this.val$data.icon;
                                }

                                @Override // androidx.picker.features.observable.MutableState
                                public final void setValue(Object obj) {
                                    this.val$data.icon = (Drawable) obj;
                                }
                            }, new Flow() { // from class: androidx.picker.adapter.AbsAdapter$$ExternalSyntheticLambda0
                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                    return CategoryViewData.this.appData.icon;
                                }
                            }), categoryViewData.selectableItem, -1, null));
                        }
                        arrayList.addAll(arrayList7);
                    }
                    if (!((ArrayList) appInfoFilterResult2).isEmpty()) {
                        if (((ArrayList) filterIsInstance).size() > 0) {
                            String string5 = AbsAdapter.this.mContext.getResources().getString(R.string.title_apps);
                            AbsAdapter.this.getClass();
                            arrayList.add(AbsAdapter.generateFilterHeader(string5, appInfoFilterResult2));
                        }
                        arrayList.addAll(appInfoFilterResult2);
                    }
                }
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override // android.widget.Filter
            public final void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                ArrayList arrayList = (ArrayList) filterResults.values;
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ViewData viewData = (ViewData) it.next();
                    if (viewData instanceof AppInfoViewData) {
                        ((AppInfoViewData) viewData).highlightText.setValue(AbsAdapter.this.mSearchText);
                    }
                }
                AbsAdapter absAdapter = AbsAdapter.this;
                DiffUtil.DiffResult calculateDiff = DiffUtil.calculateDiff(new DiffUtilCallback(absAdapter.mDataSetFiltered, arrayList));
                ((ArrayList) absAdapter.mDataSetFiltered).clear();
                ((ArrayList) absAdapter.mDataSetFiltered).addAll(arrayList);
                calculateDiff.dispatchUpdatesTo(new NearbyListUpdateCallback(absAdapter));
                AbsAdapter.this.getClass();
            }
        };
        this.mFilter = r0;
        return r0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((ArrayList) this.mDataSetFiltered).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return ((ViewData) ((ArrayList) this.mDataSetFiltered).get(i)).getKey().hashCode();
    }

    @Override // androidx.picker.common.log.LogTag
    public final String getLogTag() {
        return "AppPickerViewAdapter";
    }

    @Override // android.widget.SectionIndexer
    public final int getPositionForSection(int i) {
        String[] strArr = this.mSections;
        if (i >= strArr.length) {
            return 0;
        }
        Integer num = (Integer) ((HashMap) this.mSectionMap).get(strArr[i]);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // android.widget.SectionIndexer
    public final int getSectionForPosition(int i) {
        int[] iArr = this.mPositionToSectionIndex;
        if (i >= iArr.length) {
            return 0;
        }
        return iArr[i];
    }

    @Override // android.widget.SectionIndexer
    public final Object[] getSections() {
        return this.mSections;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        PickerViewHolder pickerViewHolder = (PickerViewHolder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder((RecyclerView.ViewHolder) pickerViewHolder, i);
        } else {
            onBindViewHolder(pickerViewHolder, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(final PickerViewHolder pickerViewHolder, int i) {
        final ViewData viewData = (ViewData) ((ArrayList) this.mDataSetFiltered).get(i);
        AppPickerAdapter$OnBindListener appPickerAdapter$OnBindListener = this.mOnBindListener;
        if (appPickerAdapter$OnBindListener != null) {
            final SeslAppPickerView seslAppPickerView = (SeslAppPickerView) appPickerAdapter$OnBindListener;
            if (seslAppPickerView.mOnClickEventListener != null && (viewData instanceof AppInfoViewData)) {
                pickerViewHolder.item.setOnClickListener(new View.OnClickListener() { // from class: androidx.picker.widget.SeslAppPickerView$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Object obj;
                        SelectableItem selectableItem;
                        SeslAppPickerView seslAppPickerView2 = SeslAppPickerView.this;
                        ViewData viewData2 = viewData;
                        PickerViewHolder pickerViewHolder2 = pickerViewHolder;
                        seslAppPickerView2.getClass();
                        AppData appData = seslAppPickerView2.getAppData(((AppInfoViewData) viewData2).getAppInfo());
                        if (appData == null) {
                            return;
                        }
                        SeslAppPickerSelectLayout$$ExternalSyntheticLambda2 seslAppPickerSelectLayout$$ExternalSyntheticLambda2 = seslAppPickerView2.mOnClickEventListener;
                        if (seslAppPickerSelectLayout$$ExternalSyntheticLambda2 == null) {
                            if (pickerViewHolder2 instanceof AppListItemViewHolder) {
                                Iterator it = ((ArrayList) CollectionsKt___CollectionsJvmKt.filterIsInstance(((AppListItemViewHolder) pickerViewHolder2).composableItemViewHolderList, ActionableComposableViewHolder.class)).iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        obj = null;
                                        break;
                                    }
                                    obj = it.next();
                                    Supplier doAction = ((ActionableComposableViewHolder) obj).getDoAction();
                                    if (doAction != null ? Intrinsics.areEqual(doAction.get(), Boolean.TRUE) : false) {
                                        break;
                                    }
                                }
                                if (((ActionableComposableViewHolder) obj) != null) {
                                    return;
                                }
                            }
                            if (seslAppPickerView2.mOnClickEventListener == null) {
                                seslAppPickerView2.setOnClickListener(null);
                                return;
                            }
                            return;
                        }
                        AppInfo appInfo = appData.getAppInfo();
                        SeslAppPickerSelectLayout seslAppPickerSelectLayout = seslAppPickerSelectLayout$$ExternalSyntheticLambda2.f$0;
                        SeslAppPickerSelectLayout.CheckStateManager checkStateManager = seslAppPickerSelectLayout.mCheckStateManager;
                        checkStateManager.getClass();
                        ArrayList arrayList = new ArrayList();
                        arrayList.addAll(checkStateManager.mFixedAppMap.values());
                        arrayList.addAll(checkStateManager.mCheckedMap.values());
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            AppInfoData appInfoData = (AppInfoData) it2.next();
                            if (appInfoData.getAppInfo().equals(appInfo)) {
                                ViewData viewData3 = seslAppPickerSelectLayout.mAppPickerStateView.mViewDataController.getViewData(appInfo);
                                if ((viewData3 instanceof Selectable) && (selectableItem = ((Selectable) viewData3).getSelectableItem()) != null) {
                                    selectableItem.setValue(Boolean.FALSE);
                                }
                                Context context = seslAppPickerSelectLayout$$ExternalSyntheticLambda2.f$1;
                                if (((AccessibilityManager) context.getSystemService("accessibility")).isEnabled()) {
                                    seslAppPickerSelectLayout.mSelectedListView.announceForAccessibility(String.format(context.getResources().getText(R.string.select_layout_unchecked_selected_app).toString(), appInfoData.getLabel()));
                                    return;
                                }
                                return;
                            }
                        }
                    }
                });
            }
        }
        pickerViewHolder.bindData(viewData);
        pickerViewHolder.bindAdapter(this);
    }
}
