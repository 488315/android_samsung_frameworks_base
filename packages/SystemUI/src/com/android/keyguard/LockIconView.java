package com.android.keyguard;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class LockIconView extends FrameLayout implements Dumpable {
    public final boolean mAod;
    public final ImageView mBgView;
    public final int mIconType;
    public final ImageView mLockIcon;
    public final Point mLockIconCenter;

    public LockIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLockIconCenter = new Point(0, 0);
        new RectF();
        ImageView imageView = new ImageView(context, attributeSet);
        this.mBgView = imageView;
        imageView.setId(R.id.lock_icon_bg);
        this.mBgView.setImageDrawable(context.getDrawable(R.drawable.fingerprint_bg));
        this.mBgView.setVisibility(8);
        addView(this.mBgView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBgView.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = -1;
        this.mBgView.setLayoutParams(layoutParams);
        ImageView imageView2 = new ImageView(context, attributeSet);
        this.mLockIcon = imageView2;
        imageView2.setId(R.id.lock_icon);
        this.mLockIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mLockIcon.setImageDrawable(context.getDrawable(R.drawable.super_lock_icon));
        addView(this.mLockIcon);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mLockIcon.getLayoutParams();
        layoutParams2.height = -1;
        layoutParams2.width = -1;
        layoutParams2.gravity = 17;
        this.mLockIcon.setLayoutParams(layoutParams2);
        this.mLockIcon.setVisibility(8);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "Lock Icon View Parameters:", "    Center in px (x, y)= (");
        m.append(this.mLockIconCenter.x);
        m.append(", ");
        m.append(this.mLockIconCenter.y);
        m.append(")");
        printWriter.println(m.toString());
        printWriter.println("    Radius in pixels: 0.0");
        printWriter.println("    Drawable padding: 0");
        int i = this.mIconType;
        printWriter.println("    mIconType=".concat(i != -1 ? i != 0 ? i != 1 ? i != 2 ? "invalid" : "unlock" : "fingerprint" : "lock" : SignalSeverity.NONE));
        printWriter.println("    mAod=" + this.mAod);
        printWriter.println("Lock Icon View actual measurements:");
        printWriter.println("    topLeft= (" + getX() + ", " + getY() + ")");
        StringBuilder sb = new StringBuilder("    width=");
        sb.append(getWidth());
        sb.append(" height=");
        sb.append(getHeight());
        printWriter.println(sb.toString());
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }
}
