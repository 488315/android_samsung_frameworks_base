package com.android.systemui.shared.recents.utilities;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ViewRippler {
    public final RunnableC24881 mRipple = new Runnable() { // from class: com.android.systemui.shared.recents.utilities.ViewRippler.1
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
