package com.android.systemui.controls.management;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Holder extends RecyclerView.ViewHolder {
    public /* synthetic */ Holder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(ElementWrapper elementWrapper);

    private Holder(View view) {
        super(view);
    }

    public void updateFavorite(boolean z) {
    }
}
