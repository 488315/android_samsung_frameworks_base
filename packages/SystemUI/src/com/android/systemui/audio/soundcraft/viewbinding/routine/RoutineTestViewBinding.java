package com.android.systemui.audio.soundcraft.viewbinding.routine;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.routine.RoutineTestView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class RoutineTestViewBinding {
    public final RoutineTestView root;
    public final TextView routineCountText;
    public final View startButton;
    public final View stopButton;
    public final View updateButton;
    public final View view;

    public RoutineTestViewBinding(ViewStub viewStub) {
        View inflate = viewStub.inflate();
        this.view = inflate;
        this.root = (RoutineTestView) inflate.requireViewById(R.id.routine_test_root);
        this.startButton = inflate.requireViewById(R.id.routine_test_start_button);
        this.updateButton = inflate.requireViewById(R.id.routine_test_update_button);
        this.stopButton = inflate.requireViewById(R.id.routine_test_stop_button);
        this.routineCountText = (TextView) inflate.requireViewById(R.id.routine_test_routine_count_text);
    }
}
