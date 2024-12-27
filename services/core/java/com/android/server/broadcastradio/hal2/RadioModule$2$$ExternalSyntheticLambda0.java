package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.Announcement;
import android.hardware.radio.ProgramSelector;
import android.util.SparseArray;

import java.util.Objects;
import java.util.function.Function;

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
