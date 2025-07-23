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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class RightShortcutImageCreator extends AbsShortcutImageCreator {
    public RightShortcutImageCreator(Context context) {
        super(context);
    }

    @Override // com.android.systemui.keyguardimage.ImageCreator
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.keyguard_sec_affordance_view_right, (ViewGroup) null);
        updateCustomShortcutIcon((KeyguardSecAffordanceView) inflate.findViewById(R.id.end_button), 1, getShortcutManager().hasShortcut(1));
        Bitmap viewImage = ImageCreator.getViewImage(inflate, imageOption, getShortcutManager().isMonotoneIcon(1));
        if (viewImage != null) {
            point.x = (imageOption.width - viewImage.getWidth()) - this.mShortcutManager.getShortcutSideMargin();
            int height = imageOption.height - viewImage.getHeight();
            KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
            point.y = height - keyguardShortcutManager.getShortcutBottomMargin(keyguardShortcutManager.isNowBarVisible);
        }
        return viewImage;
    }
}
