package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.OpenThemeSpriteView;
import com.android.systemui.wallpaper.theme.particle.Sprite;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class FrameAnimationView extends OpenThemeSpriteView {
    public FrameAnimationView(Context context, Resources resources, int i, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, ArrayList<Float> arrayList3, ArrayList<Float> arrayList4, ArrayList<Float> arrayList5, ArrayList<Integer> arrayList6) {
        super(context, resources, i);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Sprite sprite = new Sprite(DensityUtil.dip2px(context, arrayList3.get(i2).floatValue()), DensityUtil.dip2px(context, arrayList4.get(i2).floatValue()), 0.0f, 0.0f);
            int iIntValue = arrayList.get(i2).intValue();
            int iIntValue2 = arrayList2.get(i2).intValue();
            float fFloatValue = arrayList5.get(i2).floatValue();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inDither = true;
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), iIntValue, options);
            sprite.frameSize = iIntValue2;
            sprite.mScale = fFloatValue;
            sprite.mBitmap = bitmapDecodeResource;
            sprite.width = bitmapDecodeResource.getWidth() / iIntValue2;
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
