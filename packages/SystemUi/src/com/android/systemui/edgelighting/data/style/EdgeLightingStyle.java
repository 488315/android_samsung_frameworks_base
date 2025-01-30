package com.android.systemui.edgelighting.data.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import com.android.systemui.R;
import com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle;
import com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity;
import com.android.systemui.edgelighting.utils.DrawableUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingStyle implements IEdgeLightingStyle {
    public final boolean mColorOption;
    public final boolean mIsSupport;
    public final String mKey;
    public final int mPreviewImgResID;
    public final int mTitleStrID;
    public final boolean mTransparencyOption;
    public final boolean mWidthOption;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.data.style.EdgeLightingStyle$1 */
    public abstract /* synthetic */ class AbstractC13071 {

        /* renamed from: $SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption */
        public static final /* synthetic */ int[] f282x29d78fa2;

        static {
            int[] iArr = new int[EdgeLightingStyleOption.values().length];
            f282x29d78fa2 = iArr;
            try {
                iArr[EdgeLightingStyleOption.COLOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f282x29d78fa2[EdgeLightingStyleOption.WIDTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f282x29d78fa2[EdgeLightingStyleOption.TRANSPARENCY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f282x29d78fa2[EdgeLightingStyleOption.EFFECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f282x29d78fa2[EdgeLightingStyleOption.DURATION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public EdgeLightingStyle(String str, boolean z, boolean z2, int i, int i2, int i3) {
        this.mTransparencyOption = true;
        this.mTitleStrID = 0;
        this.mPreviewImgResID = 0;
        this.mKey = str;
        this.mColorOption = z;
        this.mWidthOption = z2;
        this.mTitleStrID = i;
        this.mPreviewImgResID = i3;
        this.mIsSupport = true;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final String getKey() {
        return this.mKey;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final Drawable getRoundedIcon(EdgeLightingStyleActivity edgeLightingStyleActivity) {
        RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(edgeLightingStyleActivity.getResources(), DrawableUtils.drawableToBitmap(edgeLightingStyleActivity.getResources().getDrawable(this.mPreviewImgResID, null)));
        roundedBitmapDrawable21.setCornerRadius(edgeLightingStyleActivity.getResources().getDimensionPixelSize(R.dimen.settings_edge_lighting_style_item_radius));
        roundedBitmapDrawable21.mPaint.setAntiAlias(true);
        roundedBitmapDrawable21.invalidateSelf();
        return roundedBitmapDrawable21;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final CharSequence getTitle(Context context) {
        return context.getText(this.mTitleStrID);
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final boolean isSupportEffect() {
        return this.mIsSupport;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final boolean isSupportOption(EdgeLightingStyleOption edgeLightingStyleOption) {
        int i = AbstractC13071.f282x29d78fa2[edgeLightingStyleOption.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i == 4 || i == 5 : this.mTransparencyOption : this.mWidthOption : this.mColorOption;
    }

    public EdgeLightingStyle(String str, boolean z, boolean z2, int i, int i2, int i3, boolean z3) {
        this.mTransparencyOption = true;
        this.mTitleStrID = 0;
        this.mPreviewImgResID = 0;
        this.mKey = str;
        this.mColorOption = z;
        this.mWidthOption = z2;
        this.mTitleStrID = i;
        this.mPreviewImgResID = i3;
        this.mIsSupport = z3;
    }

    public EdgeLightingStyle(String str, boolean z, boolean z2, boolean z3, int i, int i2, int i3, boolean z4) {
        this.mTransparencyOption = true;
        this.mTitleStrID = 0;
        this.mPreviewImgResID = 0;
        this.mKey = str;
        this.mColorOption = z;
        this.mWidthOption = z2;
        this.mTransparencyOption = z3;
        this.mTitleStrID = i;
        this.mPreviewImgResID = i3;
        this.mIsSupport = z4;
    }
}
