package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class HorizontalGridView extends BaseGridView {
    public HorizontalGridView(Context context) {
        this(context, null);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        new Paint();
        new Rect();
        this.mLayoutManager.setOrientation(0);
        initBaseGridViewAttributes(context, attributeSet);
        int[] iArr = R$styleable.lbHorizontalGridView;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, 0, 0);
        if (typedArrayObtainStyledAttributes.peekValue(1) != null) {
            this.mLayoutManager.setRowHeight(typedArrayObtainStyledAttributes.getLayoutDimension(1, 0));
            requestLayout();
        }
        int i2 = typedArrayObtainStyledAttributes.getInt(0, 1);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (i2 >= 0) {
            gridLayoutManager.mNumRowsRequested = i2;
            requestLayout();
            typedArrayObtainStyledAttributes.recycle();
            setLayerType(0, null);
            setWillNotDraw(true);
            new Paint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            return;
        }
        gridLayoutManager.getClass();
        throw new IllegalArgumentException();
    }
}
