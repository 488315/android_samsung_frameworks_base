package com.android.systemui.statusbar.phone.userswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableLinearLayout;

public final class StatusBarUserSwitcherContainer extends LaunchableLinearLayout {
    public ImageView avatar;
    public TextView text;

    public StatusBarUserSwitcherContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.text = (TextView) requireViewById(R.id.current_user_name);
        this.avatar = (ImageView) requireViewById(R.id.current_user_avatar);
    }
}
