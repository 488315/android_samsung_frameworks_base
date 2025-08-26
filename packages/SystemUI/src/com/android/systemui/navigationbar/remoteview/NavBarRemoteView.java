package com.android.systemui.navigationbar.remoteview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.shared.navigationbar.SamsungKeyButtonRipple;

/* loaded from: classes2.dex */
public final class NavBarRemoteView {
    public final int priority;
    public final RemoteViews remoteViews;
    public final String requestClass;
    public final SamsungKeyButtonRipple ripple;
    public final View view;

    public NavBarRemoteView(Context context, String str, RemoteViews remoteViews, int i) {
        this.requestClass = str;
        this.remoteViews = remoteViews;
        this.priority = i;
        View viewApply = remoteViews.apply(context, null);
        this.view = viewApply;
        SamsungKeyButtonRipple samsungKeyButtonRipple = new SamsungKeyButtonRipple(context, viewApply, R.dimen.key_button_ripple_max_width, 0.0f, 8, null);
        this.ripple = samsungKeyButtonRipple;
        viewApply.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteView.1
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                SamsungKeyButtonRipple samsungKeyButtonRipple2;
                if (motionEvent == null) {
                    return false;
                }
                int action = motionEvent.getAction();
                NavBarRemoteView navBarRemoteView = NavBarRemoteView.this;
                if (action != 9) {
                    if (action != 10 || (samsungKeyButtonRipple2 = navBarRemoteView.ripple) == null) {
                        return false;
                    }
                    samsungKeyButtonRipple2.exitHoverAnim();
                    return false;
                }
                SamsungKeyButtonRipple samsungKeyButtonRipple3 = navBarRemoteView.ripple;
                if (samsungKeyButtonRipple3 == null) {
                    return false;
                }
                samsungKeyButtonRipple3.startHoverAnim();
                return false;
            }
        });
        viewApply.setBackground(samsungKeyButtonRipple);
        viewApply.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    }
}
