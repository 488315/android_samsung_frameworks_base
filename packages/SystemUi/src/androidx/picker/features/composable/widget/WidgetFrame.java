package androidx.picker.features.composable.widget;

import androidx.picker.features.composable.ComposableFrame;
import androidx.picker.features.composable.ComposableViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum WidgetFrame implements ComposableFrame {
    Switch(R.layout.picker_app_composable_frame_switch, ComposableSwitchViewHolder.class),
    AllAppsSwitch(R.layout.picker_app_composable_frame_switch, ComposableAllAppSwitchViewHolder.class),
    Action(R.layout.picker_app_composable_frame_actionbutton, ComposableActionViewHolder.class),
    Expander(R.layout.picker_app_composable_frame_expander, ComposableExpanderViewHolder.class);

    private final int layoutResId;
    private final Class<? extends ComposableViewHolder> viewHolderClass;

    WidgetFrame(int i, Class cls) {
        this.layoutResId = i;
        this.viewHolderClass = cls;
    }

    @Override // androidx.picker.features.composable.ComposableFrame
    public final int getLayoutResId() {
        return this.layoutResId;
    }

    @Override // androidx.picker.features.composable.ComposableFrame
    public final Class getViewHolderClass() {
        return this.viewHolderClass;
    }
}
