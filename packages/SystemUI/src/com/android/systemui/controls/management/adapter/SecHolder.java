package com.android.systemui.controls.management.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.management.model.SecElementWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class SecHolder extends RecyclerView.ViewHolder {
    public /* synthetic */ SecHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(SecElementWrapper secElementWrapper);

    private SecHolder(View view) {
        super(view);
    }

    public void updateFavorite(boolean z) {
    }
}
