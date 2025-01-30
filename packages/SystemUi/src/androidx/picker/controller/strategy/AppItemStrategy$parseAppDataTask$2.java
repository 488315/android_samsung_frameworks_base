package androidx.picker.controller.strategy;

import androidx.picker.model.appdata.CategoryAppData;
import androidx.picker.repository.ViewDataRepository;
import java.util.List;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class AppItemStrategy$parseAppDataTask$2 extends FunctionReferenceImpl implements Function2 {
    public AppItemStrategy$parseAppDataTask$2(Object obj) {
        super(2, obj, ViewDataRepository.class, "createCategoryViewData", "createCategoryViewData(Landroidx/picker/model/appdata/CategoryAppData;Ljava/util/List;)Landroidx/picker/model/viewdata/CategoryViewData;", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ViewDataRepository) this.receiver).createCategoryViewData((CategoryAppData) obj, (List) obj2);
    }
}
