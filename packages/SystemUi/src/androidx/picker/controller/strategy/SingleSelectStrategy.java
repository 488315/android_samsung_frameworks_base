package androidx.picker.controller.strategy;

import androidx.picker.controller.strategy.task.ConvertAppInfoDataTask;
import androidx.picker.controller.strategy.task.ParseAppDataTask;
import androidx.picker.controller.strategy.task.SingleSelectableTask;
import androidx.picker.controller.strategy.task.SortAppInfoViewDataTask;
import androidx.picker.model.AppData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.di.AppPickerContext;
import androidx.picker.repository.ViewDataRepository;
import java.util.Comparator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SingleSelectStrategy extends Strategy {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "SingleSelectStrategy";
    private final ConvertAppInfoDataTask convertAppInfoDataTask;
    private final Function1 parseAppDataTask;
    private final SingleSelectableTask singleAppDataTask;
    private final ViewDataRepository viewDataRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SingleSelectStrategy(AppPickerContext appPickerContext) {
        super(appPickerContext);
        ViewDataRepository viewDataRepository = (ViewDataRepository) appPickerContext.viewDataRepository$delegate.getValue();
        this.viewDataRepository = viewDataRepository;
        this.convertAppInfoDataTask = new ConvertAppInfoDataTask(new SingleSelectStrategy$convertAppInfoDataTask$1(viewDataRepository));
        ParseAppDataTask.Companion companion = ParseAppDataTask.Companion;
        SingleSelectStrategy$parseAppDataTask$1 singleSelectStrategy$parseAppDataTask$1 = new SingleSelectStrategy$parseAppDataTask$1(viewDataRepository);
        SingleSelectStrategy$parseAppDataTask$2 singleSelectStrategy$parseAppDataTask$2 = new SingleSelectStrategy$parseAppDataTask$2(viewDataRepository);
        companion.getClass();
        this.parseAppDataTask = ParseAppDataTask.Companion.provide(singleSelectStrategy$parseAppDataTask$1, singleSelectStrategy$parseAppDataTask$2);
        this.singleAppDataTask = new SingleSelectableTask();
    }

    @Override // androidx.picker.controller.strategy.Strategy
    public List<ViewData> convert(List<? extends AppData> list, final Comparator<ViewData> comparator) {
        List<ViewData> execute = ((ParseAppDataTask) this.parseAppDataTask.invoke(new Function1() { // from class: androidx.picker.controller.strategy.SingleSelectStrategy$convert$convertAppInfoTask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ConvertAppInfoDataTask convertAppInfoDataTask;
                convertAppInfoDataTask = SingleSelectStrategy.this.convertAppInfoDataTask;
                return new SortAppInfoViewDataTask(comparator).execute(convertAppInfoDataTask.execute((List) obj));
            }
        })).execute(list);
        this.singleAppDataTask.execute(execute);
        return execute;
    }
}
