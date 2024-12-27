package com.android.server.textclassifier;

import java.util.UUID;
import java.util.function.Supplier;

public final /* synthetic */ class IconsUriHelper$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        IconsUriHelper$$ExternalSyntheticLambda0 iconsUriHelper$$ExternalSyntheticLambda0 =
                IconsUriHelper.DEFAULT_ID_SUPPLIER;
        return UUID.randomUUID().toString();
    }
}
