package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.OpenThemeSpriteView;
import com.android.systemui.wallpaper.theme.particle.Sprite;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FrameAnimationView extends OpenThemeSpriteView {
    public FrameAnimationView(Context context, Resources resources, int i, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, ArrayList<Float> arrayList3, ArrayList<Float> arrayList4, ArrayList<Float> arrayList5, ArrayList<Integer> arrayList6) {
        super(context, resources, i);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Sprite sprite = new Sprite(DensityUtil.dip2px(arrayList3.get(i2).floatValue(), context), DensityUtil.dip2px(arrayList4.get(i2).floatValue(), context), 0.0f, 0.0f);
            int intValue = arrayList.get(i2).intValue();
            int intValue2 = arrayList2.get(i2).intValue();
            float floatValue = arrayList5.get(i2).floatValue();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inDither = true;
            Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), intValue, options);
            sprite.frameSize = intValue2;
            sprite.mScale = floatValue;
            sprite.mBitmap = decodeResource;
            sprite.width = decodeResource.getWidth() / intValue2;
            sprite.height = sprite.mBitmap.getHeight();
            Sprite.SimpleModifier simpleModifier = new Sprite.SimpleModifier();
            simpleModifier.mCurrentFrameIndex = arrayList6.get(i2).intValue();
            int i3 = sprite.mModifierCount;
            if (i3 != 5) {
                sprite.mModifierCount = i3 + 1;
                sprite.mModifiers[i3] = simpleModifier;
            }
            this.mSprites.add(sprite);
        }
    }
}
