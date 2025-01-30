package androidx.picker.controller.strategy;

import androidx.picker.controller.strategy.task.AddAllAppsTask;
import androidx.picker.controller.strategy.task.ConvertAppInfoDataTask;
import androidx.picker.controller.strategy.task.ParseAppDataTask;
import androidx.picker.controller.strategy.task.SortAppInfoViewDataTask;
import androidx.picker.model.AppData;
import androidx.picker.model.viewdata.AllAppsViewData;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.p000di.AppPickerContext;
import androidx.picker.repository.ViewDataRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AllSelectStrategy extends Strategy {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "AllSelectStrategy";
    private final AddAllAppsTask addAllAppsTask;
    private final ConvertAppInfoDataTask convertAppInfoDataTask;
    private final Function1 parseAppDataTask;
    private final ViewDataRepository viewDataRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AllSelectStrategy(AppPickerContext appPickerContext) {
        super(appPickerContext);
        ViewDataRepository viewDataRepository = (ViewDataRepository) appPickerContext.viewDataRepository$delegate.getValue();
        this.viewDataRepository = viewDataRepository;
        this.convertAppInfoDataTask = new ConvertAppInfoDataTask(new AllSelectStrategy$convertAppInfoDataTask$1(viewDataRepository));
        ParseAppDataTask.Companion companion = ParseAppDataTask.Companion;
        AllSelectStrategy$parseAppDataTask$1 allSelectStrategy$parseAppDataTask$1 = new AllSelectStrategy$parseAppDataTask$1(viewDataRepository);
        AllSelectStrategy$parseAppDataTask$2 allSelectStrategy$parseAppDataTask$2 = new AllSelectStrategy$parseAppDataTask$2(viewDataRepository);
        companion.getClass();
        this.parseAppDataTask = ParseAppDataTask.Companion.provide(allSelectStrategy$parseAppDataTask$1, allSelectStrategy$parseAppDataTask$2);
        this.addAllAppsTask = new AddAllAppsTask(new AllSelectStrategy$addAllAppsTask$1(viewDataRepository));
    }

    @Override // androidx.picker.controller.strategy.Strategy
    public List<ViewData> convert(List<? extends AppData> list, final Comparator<ViewData> comparator) {
        List execute = ((ParseAppDataTask) this.parseAppDataTask.invoke(new Function1() { // from class: androidx.picker.controller.strategy.AllSelectStrategy$convert$convertAppInfoTask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ConvertAppInfoDataTask convertAppInfoDataTask;
                convertAppInfoDataTask = AllSelectStrategy.this.convertAppInfoDataTask;
                return new SortAppInfoViewDataTask(comparator).execute(convertAppInfoDataTask.execute((List) obj));
            }
        })).execute(list);
        AddAllAppsTask addAllAppsTask = this.addAllAppsTask;
        addAllAppsTask.getClass();
        ArrayList arrayList = new ArrayList(execute);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof AppInfoViewData) {
                arrayList2.add(next);
            }
        }
        arrayList.add(0, (AllAppsViewData) addAllAppsTask.createAllAppsViewData.invoke(arrayList2));
        return arrayList;
    }
}
