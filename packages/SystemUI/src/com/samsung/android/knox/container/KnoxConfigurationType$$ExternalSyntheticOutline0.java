package com.samsung.android.knox.container;

import android.os.Parcel;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class KnoxConfigurationType$$ExternalSyntheticOutline0 {
    public static Iterator m(Parcel parcel, List list) {
        parcel.writeInt(list.size());
        return list.iterator();
    }
}
