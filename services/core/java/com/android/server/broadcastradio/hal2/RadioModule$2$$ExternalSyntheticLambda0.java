package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.Announcement;
import android.hardware.radio.ProgramSelector;
import android.util.SparseArray;

import java.util.Objects;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RadioModule$2$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Announcement announcement = (Announcement) obj;
        SparseArray sparseArray = Convert.METADATA_KEYS;
        ProgramSelector programSelectorFromHal =
                Convert.programSelectorFromHal(announcement.selector);
        Objects.requireNonNull(programSelectorFromHal);
        return new android.hardware.radio.Announcement(
                programSelectorFromHal,
                announcement.type,
                Convert.vendorInfoFromHal(announcement.vendorInfo));
    }
}
