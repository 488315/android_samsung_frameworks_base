package androidx.picker.controller.strategy;

import androidx.picker.model.AppInfoData;
import androidx.picker.repository.ViewDataRepository;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class SingleSelectStrategy$convertAppInfoDataTask$1 extends FunctionReferenceImpl implements Function1 {
    public SingleSelectStrategy$convertAppInfoDataTask$1(Object obj) {
        super(1, obj, ViewDataRepository.class, "createAppInfoViewData", "createAppInfoViewData(Landroidx/picker/model/AppInfoData;)Landroidx/picker/model/viewdata/AppInfoViewData;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return ((ViewDataRepository) this.receiver).createAppInfoViewData((AppInfoData) obj);
    }
}
