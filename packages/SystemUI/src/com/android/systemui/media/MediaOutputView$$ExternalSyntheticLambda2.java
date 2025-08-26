package com.android.systemui.media;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputView$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ MediaOutputView f$0;

    public /* synthetic */ MediaOutputView$$ExternalSyntheticLambda2(MediaOutputView mediaOutputView) {
        this.f$0 = mediaOutputView;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Integer num = (Integer) obj;
        int iIntValue = num.intValue();
        String str = (String) obj2;
        MediaOutputView mediaOutputView = this.f$0;
        Feature feature = mediaOutputView.feature;
        if (feature == null) {
            feature = null;
        }
        if (feature.isWindow) {
            num = null;
        }
        if (num != null) {
            View viewFindViewById = iIntValue > 0 ? mediaOutputView.getRootView().findViewById(num.intValue()) : (str == null || StringsKt__StringsKt.isBlank(str)) ? null : mediaOutputView.getRootView().findViewWithTag(str);
            if (viewFindViewById != null) {
                Rect rect = new Rect();
                viewFindViewById.getGlobalVisibleRect(rect);
                rect.right = viewFindViewById.getMeasuredWidth() + rect.left;
                rect.bottom = viewFindViewById.getMeasuredHeight() + rect.top;
                Rect rect2 = new Rect();
                ((View) mediaOutputView.getParent()).getGlobalVisibleRect(rect2);
                Pair pair = new Pair(Integer.valueOf(rect2.left), Integer.valueOf(rect2.top));
                rect.offset(-((Number) pair.component1()).intValue(), -((Number) pair.component2()).intValue());
                Log.d("MediaOutputView", "onAttachedToWindow() - rect = " + rect);
                return rect;
            }
        }
        return null;
    }
}
