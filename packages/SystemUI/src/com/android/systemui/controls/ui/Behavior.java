package com.android.systemui.controls.ui;

public interface Behavior {
    void bind(ControlWithState controlWithState, int i);

    void initialize(ControlViewHolder controlViewHolder);
}
