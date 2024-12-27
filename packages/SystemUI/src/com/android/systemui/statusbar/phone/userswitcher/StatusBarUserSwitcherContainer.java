package com.android.systemui.statusbar.phone.userswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableLinearLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
