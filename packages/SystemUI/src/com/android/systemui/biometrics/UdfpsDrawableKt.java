package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.PathParser;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;

public abstract class UdfpsDrawableKt {
    public static final Function1 defaultFactory = new Function1() { // from class: com.android.systemui.biometrics.UdfpsDrawableKt$defaultFactory$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(PathParser.createPathFromPathData(((Context) obj).getResources().getString(R.string.config_udfpsIcon)), 72.0f, 72.0f));
            shapeDrawable.mutate();
            shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
            shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
            shapeDrawable.getPaint().setStrokeWidth(3.0f);
            return shapeDrawable;
        }
    };
}
