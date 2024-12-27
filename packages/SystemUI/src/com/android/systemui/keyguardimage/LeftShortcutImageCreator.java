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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LeftShortcutImageCreator extends AbsShortcutImageCreator {
    public LeftShortcutImageCreator(Context context) {
        super(context);
    }

    @Override // com.android.systemui.keyguardimage.ImageCreator
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.keyguard_sec_affordance_view_left, (ViewGroup) null);
        updateCustomShortcutIcon((KeyguardSecAffordanceView) inflate.findViewById(R.id.start_button), 0, getShortcutManager().hasShortcut(0), imageOption);
        Bitmap viewImage = ImageCreator.getViewImage(inflate, imageOption, getShortcutManager().isMonotoneIcon(0));
        if (viewImage != null && point != null) {
            int i = imageOption.width;
            int i2 = i > imageOption.height ? 2 : 1;
            this.mShortcutManager.getClass();
            point.x = KeyguardShortcutManager.getShortcutSideMargin(i, i2);
            int height = imageOption.height - viewImage.getHeight();
            int i3 = imageOption.width;
            int i4 = imageOption.height;
            int i5 = i3 > i4 ? 2 : 1;
            this.mShortcutManager.getClass();
            point.y = height - KeyguardShortcutManager.getShortcutBottomMargin(i4, i5, false);
        }
        return viewImage;
    }
}
