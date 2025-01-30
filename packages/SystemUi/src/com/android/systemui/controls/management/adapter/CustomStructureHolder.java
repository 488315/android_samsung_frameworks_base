package com.android.systemui.controls.management.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CustomStructureHolder extends RecyclerView.ViewHolder {
    public /* synthetic */ CustomStructureHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(StructureElementWrapper structureElementWrapper);

    private CustomStructureHolder(View view) {
        super(view);
    }
}
