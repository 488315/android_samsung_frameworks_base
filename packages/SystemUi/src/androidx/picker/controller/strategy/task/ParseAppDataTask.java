package androidx.picker.controller.strategy.task;

import android.graphics.drawable.Drawable;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.appdata.CategoryAppData;
import androidx.picker.model.appdata.GroupAppData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ParseAppDataTask {
    public static final Companion Companion = new Companion(null);
    public final Function1 createAppInfoViewDatas;
    public final Function2 createCategoryViewData;
    public final Function1 createGroupTitleViewData;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Function1 provide(final Function1 function1, final Function2 function2) {
            return new Function1() { // from class: androidx.picker.controller.strategy.task.ParseAppDataTask$Companion$provide$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return new ParseAppDataTask((Function1) obj, Function1.this, function2);
                }
            };
        }
    }

    public ParseAppDataTask(Function1 function1, Function1 function12, Function2 function2) {
        this.createAppInfoViewDatas = function1;
        this.createGroupTitleViewData = function12;
        this.createCategoryViewData = function2;
    }

    public final List createViewDatas(List list) {
        Function2 function2;
        Function1 function1;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof CategoryAppData) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (obj2 instanceof AppInfoData) {
                arrayList3.add(obj2);
            }
        }
        Iterator it = arrayList2.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            function2 = this.createCategoryViewData;
            function1 = this.createAppInfoViewDatas;
            if (!hasNext) {
                break;
            }
            CategoryAppData categoryAppData = (CategoryAppData) it.next();
            List list2 = (List) function1.invoke(categoryAppData.appInfoDataList);
            arrayList.addAll(CollectionsKt___CollectionsKt.plus((Iterable) list2, (Collection) Collections.singletonList(function2.invoke(categoryAppData, list2))));
        }
        if ((!arrayList2.isEmpty()) && (!arrayList3.isEmpty())) {
            AppData.CategoryAppDataBuilder categoryAppDataBuilder = new AppData.CategoryAppDataBuilder("");
            categoryAppDataBuilder.appInfoDataList = arrayList3;
            AppInfo.Companion.getClass();
            String str = categoryAppDataBuilder.key;
            AppInfo appInfo = new AppInfo(str, "", 0);
            Drawable drawable = categoryAppDataBuilder.icon;
            String str2 = categoryAppDataBuilder.label;
            if (str2 != null) {
                str = str2;
            }
            List list3 = categoryAppDataBuilder.appInfoDataList;
            Unit unit = Unit.INSTANCE;
            CategoryAppData categoryAppData2 = new CategoryAppData(appInfo, drawable, str, list3);
            List list4 = (List) function1.invoke(categoryAppData2.appInfoDataList);
            arrayList.addAll(CollectionsKt___CollectionsKt.plus((Iterable) list4, (Collection) Collections.singletonList(function2.invoke(categoryAppData2, list4))));
        } else {
            arrayList.addAll((Collection) function1.invoke(arrayList3));
        }
        return arrayList;
    }

    public final List execute(List list) {
        Function1 function1;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof GroupAppData) {
                arrayList2.add(obj);
            }
        }
        List minus = CollectionsKt___CollectionsKt.minus((Iterable) list, (Iterable) CollectionsKt___CollectionsKt.toSet(arrayList2));
        Iterator it = arrayList2.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            function1 = this.createGroupTitleViewData;
            if (!hasNext) {
                break;
            }
            GroupAppData groupAppData = (GroupAppData) it.next();
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(function1.invoke(groupAppData));
            arrayList3.addAll(createViewDatas(groupAppData.appDataList));
            arrayList.addAll(arrayList3);
        }
        if ((!arrayList2.isEmpty()) && (!minus.isEmpty())) {
            AppData.GroupAppDataBuilder groupAppDataBuilder = new AppData.GroupAppDataBuilder("");
            groupAppDataBuilder.mAppInfoDataList = minus;
            AppInfo.Companion.getClass();
            String str = groupAppDataBuilder.key;
            AppInfo appInfo = new AppInfo(str, "", 0);
            String str2 = groupAppDataBuilder.label;
            if (str2 != null) {
                str = str2;
            }
            GroupAppData groupAppData2 = new GroupAppData(appInfo, str, (List<? extends AppData>) groupAppDataBuilder.mAppInfoDataList);
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(function1.invoke(groupAppData2));
            arrayList4.addAll(createViewDatas(groupAppData2.appDataList));
            arrayList.addAll(arrayList4);
        } else {
            arrayList.addAll(createViewDatas(minus));
        }
        return arrayList;
    }
}
