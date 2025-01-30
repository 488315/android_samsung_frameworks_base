package androidx.picker.controller.strategy;

import androidx.picker.model.appdata.GroupAppData;
import androidx.picker.model.viewdata.GroupTitleViewData;
import androidx.picker.repository.ViewDataRepository;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class SingleSelectStrategy$parseAppDataTask$1 extends FunctionReferenceImpl implements Function1 {
    public SingleSelectStrategy$parseAppDataTask$1(Object obj) {
        super(1, obj, ViewDataRepository.class, "createGroupTitleViewData", "createGroupTitleViewData(Landroidx/picker/model/appdata/GroupAppData;)Landroidx/picker/model/viewdata/GroupTitleViewData;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((ViewDataRepository) this.receiver).getClass();
        return new GroupTitleViewData((GroupAppData) obj, null, 2, null);
    }
}
