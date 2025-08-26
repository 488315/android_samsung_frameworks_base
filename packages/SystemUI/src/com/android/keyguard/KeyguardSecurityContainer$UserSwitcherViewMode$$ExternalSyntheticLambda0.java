package com.android.keyguard;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.systemui.R;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ KeyguardSecurityContainer.UserSwitcherViewMode f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        final KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode = this.f$0;
        final Drawable drawable = (Drawable) obj;
        userSwitcherViewMode.mView.post(new Runnable() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode2 = userSwitcherViewMode;
                Drawable drawable2 = drawable;
                ImageView imageView = (ImageView) userSwitcherViewMode2.mView.findViewById(R.id.user_icon);
                if (imageView != null) {
                    imageView.setImageDrawable(drawable2);
                }
            }
        });
    }
}
