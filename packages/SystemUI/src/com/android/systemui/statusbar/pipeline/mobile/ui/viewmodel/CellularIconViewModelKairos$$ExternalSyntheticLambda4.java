package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final /* synthetic */ class CellularIconViewModelKairos$$ExternalSyntheticLambda4 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) obj2;
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        Icon.Resource resource = networkTypeIconModel.getIconId() != 0 ? new Icon.Resource(networkTypeIconModel.getIconId(), networkTypeIconModel.getContentDescription() != 0 ? new ContentDescription.Resource(networkTypeIconModel.getContentDescription()) : null) : null;
        if (zBooleanValue) {
            return resource;
        }
        return null;
    }
}
