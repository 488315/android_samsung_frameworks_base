package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.view.WindowManager;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaTttReceiverRippleController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public float maxRippleHeight;
    public float maxRippleWidth;
    public final WindowManager windowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        float m162x378fc465 = mediaTttReceiverRippleController.m162x378fc465() * 0.8f;
        receiverChipRippleView.getRippleShader().rippleSize.setMaxSize(m162x378fc465, m162x378fc465);
        float m162x378fc4652 = height - (mediaTttReceiverRippleController.m162x378fc465() * 0.5f);
        Context context = mediaTttReceiverRippleController.context;
        receiverChipRippleView.setCenter(width * 0.5f, m162x378fc4652 - context.getResources().getDimensionPixelSize(R.dimen.media_ttt_receiver_icon_bottom_margin));
        receiverChipRippleView.setColor(ColorStateList.valueOf(Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColorAccent, context, 0)).withLStar(95.0f).getDefaultColor(), 70);
    }

    /* renamed from: getReceiverIconSize$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final int m162x378fc465() {
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
            this.maxRippleHeight = m162x378fc465() * 4.0f;
            this.maxRippleWidth = m162x378fc465() * 4.0f;
        }
        receiverChipRippleView.getRippleShader().rippleSize.setMaxSize(this.maxRippleWidth, this.maxRippleHeight);
        receiverChipRippleView.setCenter(width * 0.5f, height);
        receiverChipRippleView.setColor(ColorStateList.valueOf(Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColorAccent, this.context, 0)).withLStar(95.0f).getDefaultColor(), 70);
    }
}
