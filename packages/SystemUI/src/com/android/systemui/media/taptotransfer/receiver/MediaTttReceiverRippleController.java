package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.view.WindowManager;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaTttReceiverRippleController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public float maxRippleHeight;
    public float maxRippleWidth;
    public final WindowManager windowManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaTttReceiverRippleController(Context context, WindowManager windowManager) {
        this.context = context;
        this.windowManager = windowManager;
    }

    public static final void access$layoutIconRipple(MediaTttReceiverRippleController mediaTttReceiverRippleController, ReceiverChipRippleView receiverChipRippleView) {
        Rect bounds = mediaTttReceiverRippleController.windowManager.getCurrentWindowMetrics().getBounds();
        float height = bounds.height();
        float width = bounds.width();
        float receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaTttReceiverRippleController.getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() * 0.8f;
        RippleShader rippleShader = receiverChipRippleView.rippleShader;
        if (rippleShader == null) {
            rippleShader = null;
        }
        RippleShader.RippleSize rippleSize = rippleShader.rippleSize;
        rippleSize.getClass();
        rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core, receiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core));
        receiverChipRippleView.setCenter(width * 0.5f, (height - (mediaTttReceiverRippleController.getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() * 0.5f)) - mediaTttReceiverRippleController.context.getResources().getDimensionPixelSize(R.dimen.media_ttt_receiver_icon_bottom_margin));
        receiverChipRippleView.setColor(ColorStateList.valueOf(Utils.getColorAttrDefaultColor(mediaTttReceiverRippleController.context, R.attr.wallpaperTextColorAccent, 0)).withLStar(95.0f).getDefaultColor(), 70);
    }

    public final int getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.context.getResources().getDimensionPixelSize(R.dimen.media_ttt_icon_size_receiver);
    }

    public final void layoutRipple(ReceiverChipRippleView receiverChipRippleView, boolean z) {
        Rect bounds = this.windowManager.getCurrentWindowMetrics().getBounds();
        float height = bounds.height();
        float width = bounds.width();
        if (z) {
            this.maxRippleHeight = height * 2.0f;
            this.maxRippleWidth = 2.0f * width;
        } else {
            this.maxRippleHeight = getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() * 4.0f;
            this.maxRippleWidth = getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core() * 4.0f;
        }
        float f = this.maxRippleWidth;
        float f2 = this.maxRippleHeight;
        RippleShader rippleShader = receiverChipRippleView.rippleShader;
        if (rippleShader == null) {
            rippleShader = null;
        }
        RippleShader.RippleSize rippleSize = rippleShader.rippleSize;
        rippleSize.getClass();
        rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, f, f2));
        receiverChipRippleView.setCenter(width * 0.5f, height);
        receiverChipRippleView.setColor(ColorStateList.valueOf(Utils.getColorAttrDefaultColor(this.context, R.attr.wallpaperTextColorAccent, 0)).withLStar(95.0f).getDefaultColor(), 70);
    }
}
