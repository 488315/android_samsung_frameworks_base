package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;

/* loaded from: classes2.dex */
public class LeftShortcutImageCreator extends AbsShortcutImageCreator {
    public LeftShortcutImageCreator(Context context) {
        super(context);
    }

    @Override // com.android.systemui.keyguardimage.ImageCreator
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.keyguard_sec_affordance_view_left, (ViewGroup) null);
        updateCustomShortcutIcon((KeyguardSecAffordanceView) viewInflate.findViewById(R.id.start_button), 0, getShortcutManager().hasShortcut(0));
        Bitmap viewImage = ImageCreator.getViewImage(viewInflate, imageOption, getShortcutManager().isMonotoneIcon(0));
        if (viewImage != null) {
            point.x = this.mShortcutManager.getShortcutSideMargin();
            int height = imageOption.height - viewImage.getHeight();
            KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
            point.y = height - keyguardShortcutManager.getShortcutBottomMargin(keyguardShortcutManager.isNowBarVisible);
        }
        return viewImage;
    }
}
