package com.android.settingslib.media;

import android.media.MediaRoute2Info;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class InfoMediaManager$Api34Impl$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((MediaRoute2Info) obj).isSystemRoute();
    }
}
