package androidx.picker.controller.strategy;

import androidx.picker.controller.strategy.task.ConvertAppInfoDataTask;
import androidx.picker.controller.strategy.task.ParseAppDataTask;
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
public final class AppItemStrategy extends Strategy {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "AppItemStrategy";
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

    public AppItemStrategy(AppPickerContext appPickerContext) {
        super(appPickerContext);
        ViewDataRepository viewDataRepository = (ViewDataRepository) appPickerContext.viewDataRepository$delegate.getValue();
        this.viewDataRepository = viewDataRepository;
        this.convertAppInfoDataTask = new ConvertAppInfoDataTask(new AppItemStrategy$convertAppInfoDataTask$1(viewDataRepository));
        ParseAppDataTask.Companion companion = ParseAppDataTask.Companion;
        AppItemStrategy$parseAppDataTask$1 appItemStrategy$parseAppDataTask$1 = new AppItemStrategy$parseAppDataTask$1(viewDataRepository);
        AppItemStrategy$parseAppDataTask$2 appItemStrategy$parseAppDataTask$2 = new AppItemStrategy$parseAppDataTask$2(viewDataRepository);
        companion.getClass();
        this.parseAppDataTask = ParseAppDataTask.Companion.provide(appItemStrategy$parseAppDataTask$1, appItemStrategy$parseAppDataTask$2);
    }

    @Override // androidx.picker.controller.strategy.Strategy
    public List<ViewData> convert(List<? extends AppData> list, final Comparator<ViewData> comparator) {
        return ((ParseAppDataTask) this.parseAppDataTask.invoke(new Function1() { // from class: androidx.picker.controller.strategy.AppItemStrategy$convert$convertAppInfoTask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ConvertAppInfoDataTask convertAppInfoDataTask;
                convertAppInfoDataTask = AppItemStrategy.this.convertAppInfoDataTask;
                return new SortAppInfoViewDataTask(comparator).execute(convertAppInfoDataTask.execute((List) obj));
            }
        })).execute(list);
    }
}
