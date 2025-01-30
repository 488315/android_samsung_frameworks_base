package com.android.systemui.statusbar.phone.userswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableLinearLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarUserSwitcherContainer extends LaunchableLinearLayout {
    public ImageView avatar;
    public TextView text;

    public StatusBarUserSwitcherContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.text = (TextView) findViewById(R.id.current_user_name);
        this.avatar = (ImageView) findViewById(R.id.current_user_avatar);
    }
}
