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
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final void setSliceItem(SliceContent sliceContent, boolean z, int i, int i2, VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4) {
        IconCompat iconCompat;
        Drawable loadDrawable;
        SliceItem sliceItem = sliceContent.mSliceItem;
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda4;
        SliceItem findSubtype = SliceQuery.findSubtype(sliceItem, "image", "source");
        if (findSubtype != null && (iconCompat = (IconCompat) findSubtype.mObj) != null && (loadDrawable = iconCompat.loadDrawable(getContext())) != null) {
            int applyDimension = (int) TypedValue.applyDimension(1, 24.0f, getContext().getResources().getDisplayMetrics());
            Bitmap createBitmap = Bitmap.createBitmap(applyDimension, applyDimension, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            loadDrawable.setBounds(0, 0, applyDimension, applyDimension);
            loadDrawable.draw(canvas);
            this.mIcon.setImageBitmap(SliceViewUtil.getCircularBitmap(createBitmap));
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        Iterator it = ((ArrayList) SliceQuery.findAll(sliceItem, "text", null, null)).iterator();
        while (it.hasNext()) {
            SliceItem sliceItem2 = (SliceItem) it.next();
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
