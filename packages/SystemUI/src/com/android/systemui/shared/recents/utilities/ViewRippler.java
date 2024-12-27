package com.android.systemui.shared.recents.utilities;

import android.view.View;

public final class ViewRippler {
    public final AnonymousClass1 mRipple = new Runnable() { // from class: com.android.systemui.shared.recents.utilities.ViewRippler.1
        @Override // java.lang.Runnable
        public final void run() {
            if (ViewRippler.this.mRoot.isAttachedToWindow()) {
                ViewRippler.this.mRoot.setPressed(true);
                ViewRippler.this.mRoot.setPressed(false);
            }
        }
    };
    public View mRoot;
}
