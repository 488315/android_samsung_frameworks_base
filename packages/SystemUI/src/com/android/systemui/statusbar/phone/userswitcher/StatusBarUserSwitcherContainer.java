package com.android.systemui.statusbar.phone.userswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableLinearLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
