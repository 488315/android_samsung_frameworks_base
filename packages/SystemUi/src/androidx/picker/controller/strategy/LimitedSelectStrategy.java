package androidx.picker.controller.strategy;

import androidx.picker.controller.strategy.task.ConvertAppInfoDataTask;
import androidx.picker.controller.strategy.task.LimitedSelectableTask;
import androidx.picker.controller.strategy.task.ParseAppDataTask;
import androidx.picker.controller.strategy.task.SortAppInfoViewDataTask;
import androidx.picker.model.AppData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.p000di.AppPickerContext;
import androidx.picker.repository.ViewDataRepository;
import java.util.Comparator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LimitedSelectStrategy extends Strategy {
    private static final int DEFAULT_LIMIT = 5;
    private final ConvertAppInfoDataTask convertAppInfoDataTask;
    private final Lazy limitedSelectableTask$delegate;
    private final Function1 parseAppDataTask;
    private final ViewDataRepository viewDataRepository;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "LimitedSelectStrategy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LimitedSelectStrategy(AppPickerContext appPickerContext) {
        super(appPickerContext);
        ViewDataRepository viewDataRepository = (ViewDataRepository) appPickerContext.viewDataRepository$delegate.getValue();
        this.viewDataRepository = viewDataRepository;
        this.convertAppInfoDataTask = new ConvertAppInfoDataTask(new LimitedSelectStrategy$convertAppInfoDataTask$1(viewDataRepository));
        ParseAppDataTask.Companion companion = ParseAppDataTask.Companion;
        LimitedSelectStrategy$parseAppDataTask$1 limitedSelectStrategy$parseAppDataTask$1 = new LimitedSelectStrategy$parseAppDataTask$1(viewDataRepository);
        LimitedSelectStrategy$parseAppDataTask$2 limitedSelectStrategy$parseAppDataTask$2 = new LimitedSelectStrategy$parseAppDataTask$2(viewDataRepository);
        companion.getClass();
        this.parseAppDataTask = ParseAppDataTask.Companion.provide(limitedSelectStrategy$parseAppDataTask$1, limitedSelectStrategy$parseAppDataTask$2);
        this.limitedSelectableTask$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.controller.strategy.LimitedSelectStrategy$limitedSelectableTask$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new LimitedSelectableTask(LimitedSelectStrategy.this.getItemLimitedSize());
            }
        });
    }

    private final LimitedSelectableTask getLimitedSelectableTask() {
        return (LimitedSelectableTask) this.limitedSelectableTask$delegate.getValue();
    }

    @Override // androidx.picker.controller.strategy.Strategy
    public List<ViewData> convert(List<? extends AppData> list, final Comparator<ViewData> comparator) {
        List<ViewData> execute = ((ParseAppDataTask) this.parseAppDataTask.invoke(new Function1() { // from class: androidx.picker.controller.strategy.LimitedSelectStrategy$convert$convertAppInfoTask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ConvertAppInfoDataTask convertAppInfoDataTask;
                convertAppInfoDataTask = LimitedSelectStrategy.this.convertAppInfoDataTask;
                return new SortAppInfoViewDataTask(comparator).execute(convertAppInfoDataTask.execute((List) obj));
            }
        })).execute(list);
        getLimitedSelectableTask().execute(execute);
        return execute;
    }

    public int getItemLimitedSize() {
        return 5;
    }
}
