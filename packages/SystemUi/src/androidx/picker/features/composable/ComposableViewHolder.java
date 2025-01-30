package androidx.picker.features.composable;

import android.view.View;
import androidx.picker.adapter.AbsAdapter;
import androidx.picker.model.viewdata.ViewData;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ComposableViewHolder {
    private final View frameView;

    public ComposableViewHolder(View view) {
        this.frameView = view;
    }

    public abstract void bindData(ViewData viewData);

    public final View getFrameView() {
        return this.frameView;
    }

    public void bindAdapter(AbsAdapter absAdapter) {
    }

    public void onBind(View view) {
    }

    public void onViewRecycled(View view) {
    }
}
