package com.samsung.android.localeoverlaymanager;

import android.content.om.OverlayInfo;

import java.util.function.Predicate;

public final /* synthetic */ class OMSHelper$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        String str = ((OverlayInfo) obj).category;
        return str == null || !str.startsWith("zipped-overlay");
    }
}
