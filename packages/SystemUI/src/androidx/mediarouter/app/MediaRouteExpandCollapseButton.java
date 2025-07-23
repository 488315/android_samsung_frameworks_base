package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class MediaRouteExpandCollapseButton extends AppCompatImageButton {
    public final AnimationDrawable mCollapseAnimationDrawable;
    public final String mCollapseGroupDescription;
    public final AnimationDrawable mExpandAnimationDrawable;
    public final String mExpandGroupDescription;
    public boolean mIsGroupExpanded;
    public View.OnClickListener mListener;

    public MediaRouteExpandCollapseButton(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }

    public MediaRouteExpandCollapseButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0030, code lost:
    
        if (r4 != 0) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaRouteExpandCollapseButton(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            r7.<init>(r8, r9, r10)
            r9 = 2131234176(0x7f080d80, float:1.808451E38)
            android.graphics.drawable.Drawable r9 = r8.getDrawable(r9)
            android.graphics.drawable.AnimationDrawable r9 = (android.graphics.drawable.AnimationDrawable) r9
            r7.mExpandAnimationDrawable = r9
            r0 = 2131234175(0x7f080d7f, float:1.8084508E38)
            android.graphics.drawable.Drawable r0 = r8.getDrawable(r0)
            android.graphics.drawable.AnimationDrawable r0 = (android.graphics.drawable.AnimationDrawable) r0
            r7.mCollapseAnimationDrawable = r0
            android.graphics.PorterDuffColorFilter r1 = new android.graphics.PorterDuffColorFilter
            r2 = 0
            r3 = 2130968930(0x7f040162, float:1.7546528E38)
            if (r10 == 0) goto L33
            int[] r4 = new int[]{r3}
            android.content.res.TypedArray r10 = r8.obtainStyledAttributes(r10, r4)
            int r4 = r10.getColor(r2, r2)
            r10.recycle()
            if (r4 == 0) goto L33
            goto L51
        L33:
            android.util.TypedValue r10 = new android.util.TypedValue
            r10.<init>()
            android.content.res.Resources$Theme r4 = r8.getTheme()
            r5 = 1
            r4.resolveAttribute(r3, r10, r5)
            int r3 = r10.resourceId
            if (r3 == 0) goto L4f
            android.content.res.Resources r3 = r8.getResources()
            int r10 = r10.resourceId
            int r4 = r3.getColor(r10)
            goto L51
        L4f:
            int r4 = r10.data
        L51:
            r10 = -1
            double r3 = androidx.core.graphics.ColorUtils.calculateContrast(r10, r4)
            r5 = 4613937818241073152(0x4008000000000000, double:3.0)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 < 0) goto L5d
            goto L5f
        L5d:
            r10 = -570425344(0xffffffffde000000, float:-2.305843E18)
        L5f:
            android.graphics.PorterDuff$Mode r3 = android.graphics.PorterDuff.Mode.SRC_IN
            r1.<init>(r10, r3)
            r9.setColorFilter(r1)
            r0.setColorFilter(r1)
            r10 = 2131955126(0x7f130db6, float:1.954677E38)
            java.lang.String r10 = r8.getString(r10)
            r7.mExpandGroupDescription = r10
            r0 = 2131955124(0x7f130db4, float:1.9546767E38)
            java.lang.String r8 = r8.getString(r0)
            r7.mCollapseGroupDescription = r8
            android.graphics.drawable.Drawable r8 = r9.getFrame(r2)
            r7.setImageDrawable(r8)
            r7.setContentDescription(r10)
            androidx.mediarouter.app.MediaRouteExpandCollapseButton$1 r8 = new androidx.mediarouter.app.MediaRouteExpandCollapseButton$1
            r8.<init>()
            super.setOnClickListener(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteExpandCollapseButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }
}
