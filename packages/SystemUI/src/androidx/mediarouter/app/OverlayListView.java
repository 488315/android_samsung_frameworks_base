package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class OverlayListView extends ListView {
    public final List mOverlayObjects;

    public class OverlayObject {
        public final BitmapDrawable mBitmap;
        public float mCurrentAlpha = 1.0f;
        public final Rect mCurrentBounds;
        public final Rect mStartRect;

        public OverlayObject(BitmapDrawable bitmapDrawable, Rect rect) {
            this.mBitmap = bitmapDrawable;
            this.mStartRect = rect;
            Rect rect2 = new Rect(rect);
            this.mCurrentBounds = rect2;
            if (bitmapDrawable != null) {
                bitmapDrawable.setAlpha((int) (this.mCurrentAlpha * 255.0f));
                bitmapDrawable.setBounds(rect2);
            }
        }
    }

    public OverlayListView(Context context) {
        super(context);
        this.mOverlayObjects = new ArrayList();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (((ArrayList) this.mOverlayObjects).size() > 0) {
            ArrayList arrayList = (ArrayList) this.mOverlayObjects;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                OverlayObject overlayObject = (OverlayObject) obj;
                BitmapDrawable bitmapDrawable = overlayObject.mBitmap;
                if (bitmapDrawable != null) {
                    bitmapDrawable.draw(canvas);
                }
                Math.max(0.0f, Math.min(1.0f, getDrawingTime() / 0));
                int i2 = (int) (0 * 0.0f);
                Rect rect = overlayObject.mCurrentBounds;
                Rect rect2 = overlayObject.mStartRect;
                rect.top = rect2.top + i2;
                rect.bottom = rect2.bottom + i2;
                overlayObject.mCurrentAlpha = 1.0f;
                BitmapDrawable bitmapDrawable2 = overlayObject.mBitmap;
                if (bitmapDrawable2 != null) {
                    bitmapDrawable2.setAlpha((int) 255.0f);
                    overlayObject.mBitmap.setBounds(overlayObject.mCurrentBounds);
                }
            }
        }
    }

    public OverlayListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOverlayObjects = new ArrayList();
    }

    public OverlayListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOverlayObjects = new ArrayList();
    }
}
