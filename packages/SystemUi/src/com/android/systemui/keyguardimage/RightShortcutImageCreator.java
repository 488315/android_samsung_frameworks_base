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
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RightShortcutImageCreator extends AbsShortcutImageCreator {
    public RightShortcutImageCreator(Context context) {
        super(context);
    }

    @Override // com.android.systemui.keyguardimage.ImageCreator
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.keyguard_sec_affordance_view_right, (ViewGroup) null);
        KeyguardSecAffordanceView keyguardSecAffordanceView = (KeyguardSecAffordanceView) inflate.findViewById(R.id.end_button);
        keyguardSecAffordanceView.mRectangleColor = getShortcutManager().isShortcutForCamera(1) ? EmergencyPhoneWidget.BG_COLOR : -1;
        updateCustomShortcutIcon(keyguardSecAffordanceView, 1, getShortcutManager().hasShortcut(1));
        Bitmap viewImage = ImageCreator.getViewImage(inflate, imageOption);
        if (viewImage != null && point != null) {
            point.x = (imageOption.width - viewImage.getWidth()) - getSideMargin(imageOption);
            point.y = (imageOption.height - viewImage.getHeight()) - getBottomMargin(imageOption);
        }
        return viewImage;
    }
}
