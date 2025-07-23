package com.android.systemui.controls.management.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SecStructureViewHolder extends RecyclerView.ViewHolder {
    public /* synthetic */ SecStructureViewHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(StructureElementWrapper structureElementWrapper);

    private SecStructureViewHolder(View view) {
        super(view);
    }
}
