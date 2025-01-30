package androidx.picker.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DataController {
    public final List currentList;
    public final List dataList;
    public final List listeners;

    public DataController() {
        ArrayList arrayList = new ArrayList();
        this.dataList = arrayList;
        this.currentList = Collections.unmodifiableList(arrayList);
        this.listeners = new ArrayList();
    }
}
