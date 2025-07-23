package com.samsung.android.knox.container;

import android.os.Parcel;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class KnoxConfigurationType$$ExternalSyntheticOutline0 {
    public static Iterator m(Parcel parcel, List list) {
        parcel.writeInt(list.size());
        return list.iterator();
    }
}
