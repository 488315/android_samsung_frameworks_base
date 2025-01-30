package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BitmapInfo {
    public static final Bitmap LOW_RES_ICON;
    public BitmapInfo badgeInfo;
    public final int color;
    public int flags;
    public final Bitmap icon;
    public Bitmap mMono;
    public Bitmap mWhiteShadowLayer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Extender {
    }

    static {
        Bitmap createBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        LOW_RES_ICON = createBitmap;
        new BitmapInfo(createBitmap, 0);
    }

    public BitmapInfo(Bitmap bitmap, int i) {
        this.icon = bitmap;
        this.color = i;
    }

    public final void applyFlags(Context context, FastBitmapDrawable fastBitmapDrawable) {
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.disabledIconAlpha});
        float f = obtainStyledAttributes.getFloat(0, 1.0f);
        obtainStyledAttributes.recycle();
        fastBitmapDrawable.mDisabledAlpha = f;
        BitmapInfo bitmapInfo = this.badgeInfo;
        if (bitmapInfo != null) {
            fastBitmapDrawable.setBadge(bitmapInfo.newIcon(context));
            return;
        }
        int i = this.flags;
        if ((i & 2) != 0) {
            fastBitmapDrawable.setBadge(context.getDrawable(fastBitmapDrawable.isThemed() ? R.drawable.ic_instant_app_badge_themed : R.drawable.ic_instant_app_badge));
        } else if ((i & 1) != 0) {
            fastBitmapDrawable.setBadge(context.getDrawable(fastBitmapDrawable.isThemed() ? R.drawable.ic_work_app_badge_themed : R.drawable.ic_work_app_badge));
        } else if ((i & 4) != 0) {
            fastBitmapDrawable.setBadge(context.getDrawable(fastBitmapDrawable.isThemed() ? R.drawable.ic_clone_app_badge_themed : R.drawable.ic_clone_app_badge));
        }
    }

    public FastBitmapDrawable newIcon(Context context) {
        FastBitmapDrawable placeHolderIconDrawable = LOW_RES_ICON == this.icon ? new PlaceHolderIconDrawable(this, context) : new FastBitmapDrawable(this);
        applyFlags(context, placeHolderIconDrawable);
        return placeHolderIconDrawable;
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BitmapInfo mo364clone() {
        BitmapInfo bitmapInfo = new BitmapInfo(this.icon, this.color);
        bitmapInfo.mMono = this.mMono;
        bitmapInfo.mWhiteShadowLayer = this.mWhiteShadowLayer;
        bitmapInfo.flags = this.flags;
        bitmapInfo.badgeInfo = this.badgeInfo;
        return bitmapInfo;
    }
}
