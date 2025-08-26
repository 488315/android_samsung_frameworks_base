package com.android.internal.globalactions;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public abstract class ToggleAction implements Action {
    private static final String TAG = "ToggleAction";
    protected int mDisabledIconResid;
    protected int mDisabledStatusMessageResId;
    protected int mEnabledIconResId;
    protected int mEnabledStatusMessageResId;
    protected int mMessageResId;
    protected State mState = State.Off;

    public abstract void onToggle(boolean z);

    void willCreate() {
    }

    public enum State {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);

        private final boolean inTransition;

        State(boolean z) {
            this.inTransition = z;
        }

        public boolean inTransition() {
            return this.inTransition;
        }
    }

    public ToggleAction(int i, int i2, int i3, int i4, int i5) {
        this.mEnabledIconResId = i;
        this.mDisabledIconResid = i2;
        this.mMessageResId = i3;
        this.mEnabledStatusMessageResId = i4;
        this.mDisabledStatusMessageResId = i5;
    }

    @Override // com.android.internal.globalactions.Action
    public CharSequence getLabelForAccessibility(Context context) {
        return context.getString(this.mMessageResId);
    }

    @Override // com.android.internal.globalactions.Action
    public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) throws Resources.NotFoundException {
        willCreate();
        View viewInflate = layoutInflater.inflate(R.layout.global_actions_item, viewGroup, false);
        ImageView imageView = (ImageView) viewInflate.findViewById(16908294);
        TextView textView = (TextView) viewInflate.findViewById(16908299);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.status);
        boolean zIsEnabled = isEnabled();
        if (textView != null) {
            textView.setText(this.mMessageResId);
            textView.setEnabled(zIsEnabled);
        }
        boolean z = this.mState == State.On || this.mState == State.TurningOn;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$2(context.getDrawable(z ? this.mEnabledIconResId : this.mDisabledIconResid));
            imageView.setEnabled(zIsEnabled);
        }
        if (textView2 != null) {
            textView2.setText(z ? this.mEnabledStatusMessageResId : this.mDisabledStatusMessageResId);
            textView2.setVisibility(0);
            textView2.setEnabled(zIsEnabled);
        }
        viewInflate.setEnabled(zIsEnabled);
        return viewInflate;
    }

    @Override // com.android.internal.globalactions.Action
    public final void onPress() {
        if (this.mState.inTransition()) {
            Log.w(TAG, "shouldn't be able to toggle when in transition");
            return;
        }
        boolean z = this.mState != State.On;
        onToggle(z);
        changeStateFromPress(z);
    }

    @Override // com.android.internal.globalactions.Action
    public boolean isEnabled() {
        return !this.mState.inTransition();
    }

    protected void changeStateFromPress(boolean z) {
        this.mState = z ? State.On : State.Off;
    }

    public void updateState(State state) {
        this.mState = state;
    }
}
