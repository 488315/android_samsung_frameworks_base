package androidx.slice.widget;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda5;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class MessageView extends SliceChildView {
    public TextView mDetails;
    public ImageView mIcon;

    public MessageView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDetails = (TextView) findViewById(R.id.summary);
        this.mIcon = (ImageView) findViewById(R.id.icon);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceItem(SliceContent sliceContent, boolean z, int i, int i2, VolumePanelDialog$$ExternalSyntheticLambda5 volumePanelDialog$$ExternalSyntheticLambda5) {
        IconCompat iconCompat;
        Drawable drawableLoadDrawable;
        SliceItem sliceItem = sliceContent.mSliceItem;
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda5;
        SliceItem sliceItemFindSubtype = SliceQuery.findSubtype(sliceItem, "image", "source");
        int i3 = 0;
        if (sliceItemFindSubtype != null && (iconCompat = (IconCompat) sliceItemFindSubtype.mObj) != null && (drawableLoadDrawable = iconCompat.loadDrawable(getContext())) != null) {
            int iApplyDimension = (int) TypedValue.applyDimension(1, 24.0f, getContext().getResources().getDisplayMetrics());
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iApplyDimension, iApplyDimension, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawableLoadDrawable.setBounds(0, 0, iApplyDimension, iApplyDimension);
            drawableLoadDrawable.draw(canvas);
            this.mIcon.setImageBitmap(SliceViewUtil.getCircularBitmap(bitmapCreateBitmap));
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ArrayList arrayList = (ArrayList) SliceQuery.findAll(sliceItem, "text", null, null);
        int size = arrayList.size();
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            SliceItem sliceItem2 = (SliceItem) obj;
            if (spannableStringBuilder.length() != 0) {
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append(sliceItem2.getSanitizedText());
        }
        this.mDetails.setText(spannableStringBuilder.toString());
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void resetView() {
    }
}
