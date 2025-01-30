package androidx.slice.widget;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.CornerDrawable;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.GridContent;
import com.android.systemui.R;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class GridRowView extends SliceChildView implements View.OnClickListener, View.OnTouchListener {
    public final View mForeground;
    public GridContent mGridContent;
    public final int mGutter;
    public final int mIconSize;
    public final int mLargeImageHeight;
    public final int[] mLoc;
    public boolean mMaxCellUpdateScheduled;
    public int mMaxCells;
    public final ViewTreeObserverOnPreDrawListenerC05032 mMaxCellsUpdater;
    public int mRowCount;
    public int mRowIndex;
    public final int mSmallImageMinWidth;
    public final int mSmallImageSize;
    public final int mTextPadding;
    public final LinearLayout mViewContainer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DateSetListener implements DatePickerDialog.OnDateSetListener {
        public final SliceItem mActionItem;
        public final int mRowIndex;

        public DateSetListener(SliceItem sliceItem, int i) {
            this.mActionItem = sliceItem;
            this.mRowIndex = i;
        }

        @Override // android.app.DatePickerDialog.OnDateSetListener
        public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(i, i2, i3);
            Date time = calendar.getTime();
            SliceItem sliceItem = this.mActionItem;
            if (sliceItem != null) {
                try {
                    sliceItem.fireActionInternal(GridRowView.this.getContext(), new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    GridRowView gridRowView = GridRowView.this;
                    if (gridRowView.mObserver != null) {
                        GridRowView.this.mObserver.onSliceAction(new EventInfo(gridRowView.getMode(), 6, 7, this.mRowIndex));
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("GridRowView", "PendingIntent for slice cannot be sent", e);
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        public final SliceItem mActionItem;
        public final int mRowIndex;

        public TimeSetListener(SliceItem sliceItem, int i) {
            this.mActionItem = sliceItem;
            this.mRowIndex = i;
        }

        @Override // android.app.TimePickerDialog.OnTimeSetListener
        public final void onTimeSet(TimePicker timePicker, int i, int i2) {
            Date time = Calendar.getInstance().getTime();
            time.setHours(i);
            time.setMinutes(i2);
            SliceItem sliceItem = this.mActionItem;
            if (sliceItem != null) {
                try {
                    sliceItem.fireActionInternal(GridRowView.this.getContext(), new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    GridRowView gridRowView = GridRowView.this;
                    if (gridRowView.mObserver != null) {
                        GridRowView.this.mObserver.onSliceAction(new EventInfo(gridRowView.getMode(), 7, 8, this.mRowIndex));
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("GridRowView", "PendingIntent for slice cannot be sent", e);
                }
            }
        }
    }

    public GridRowView(Context context) {
        this(context, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02ec A[EDGE_INSN: B:146:0x02ec->B:147:0x02ec BREAK  A[LOOP:2: B:37:0x00a7->B:83:0x02dc], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02f0  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:174:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addCell(GridContent.CellContent cellContent, int i, int i2) {
        int i3;
        ArrayList arrayList;
        SliceItem sliceItem;
        SliceItem sliceItem2;
        SliceItem sliceItem3;
        boolean z;
        String str;
        String str2;
        SliceItem sliceItem4;
        ArrayList arrayList2;
        int i4;
        SliceItem sliceItem5;
        boolean z2;
        SliceActionView sliceActionView;
        SliceItem sliceItem6;
        int i5;
        String str3;
        ArrayList arrayList3;
        String str4;
        int i6;
        int i7;
        boolean z3;
        boolean z4;
        IconCompat iconCompat;
        Drawable loadDrawable;
        LinearLayout.LayoutParams layoutParams;
        LinearLayout.LayoutParams layoutParams2;
        int i8;
        CharSequence sanitizedText;
        boolean z5;
        int subtitleColor;
        if (getMode() == 1) {
            if (this.mGridContent.mFirstImage != null) {
                i3 = 1;
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(1);
                linearLayout.setGravity(1);
                arrayList = cellContent.mCellItems;
                sliceItem = cellContent.mContentIntent;
                sliceItem2 = cellContent.mPicker;
                sliceItem3 = cellContent.mToggleItem;
                z = arrayList.size() != 1;
                str = UniversalCredentialUtil.AGENT_TITLE;
                str2 = "text";
                if (z && getMode() == 1) {
                    arrayList2 = new ArrayList();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        SliceItem sliceItem7 = (SliceItem) it.next();
                        SliceItem sliceItem8 = sliceItem;
                        if ("text".equals(sliceItem7.mFormat)) {
                            arrayList2.add(sliceItem7);
                        }
                        sliceItem = sliceItem8;
                    }
                    sliceItem4 = sliceItem;
                    Iterator it2 = arrayList2.iterator();
                    while (arrayList2.size() > i3) {
                        ArrayList arrayList4 = arrayList2;
                        if (!((SliceItem) it2.next()).hasAnyHints(UniversalCredentialUtil.AGENT_TITLE, "large")) {
                            it2.remove();
                        }
                        arrayList2 = arrayList4;
                    }
                } else {
                    sliceItem4 = sliceItem;
                    arrayList2 = null;
                }
                int i9 = 0;
                SliceItem sliceItem9 = null;
                i4 = 0;
                int i10 = 0;
                boolean z6 = false;
                while (true) {
                    sliceItem5 = sliceItem2;
                    if (i4 < arrayList.size()) {
                        break;
                    }
                    SliceItem sliceItem10 = (SliceItem) arrayList.get(i4);
                    String str5 = sliceItem10.mFormat;
                    ArrayList arrayList5 = arrayList;
                    int determinePadding = determinePadding(sliceItem9);
                    if (i10 < i3) {
                        i5 = i3;
                        if (!str2.equals(str5) && !"long".equals(str5)) {
                            sliceItem6 = sliceItem9;
                        } else if (arrayList2 == null || arrayList2.contains(sliceItem10)) {
                            String str6 = sliceItem10.mFormat;
                            if (str2.equals(str6) || "long".equals(str6)) {
                                str4 = str2;
                                boolean hasAnyHints = SliceQuery.hasAnyHints(sliceItem10, "large", str);
                                arrayList3 = arrayList2;
                                sliceItem6 = sliceItem9;
                                str3 = str;
                                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(hasAnyHints ? R.layout.abc_slice_title : R.layout.abc_slice_secondary_text, (ViewGroup) null);
                                if (this.mSliceStyle != null && this.mRowStyle != null) {
                                    textView.setTextSize(0, hasAnyHints ? r15.mGridTitleSize : r15.mGridSubtitleSize);
                                    if (hasAnyHints) {
                                        RowStyle rowStyle = this.mRowStyle;
                                        Integer num = rowStyle.mTitleColor;
                                        subtitleColor = num != null ? num.intValue() : rowStyle.mSliceStyle.mTitleColor;
                                    } else {
                                        subtitleColor = this.mRowStyle.getSubtitleColor();
                                    }
                                    textView.setTextColor(subtitleColor);
                                }
                                if ("long".equals(str6)) {
                                    i6 = i4;
                                    sanitizedText = SliceViewUtil.getTimestampString(getContext(), sliceItem10.getLong());
                                } else {
                                    i6 = i4;
                                    sanitizedText = sliceItem10.getSanitizedText();
                                }
                                textView.setText(sanitizedText);
                                linearLayout.addView(textView);
                                textView.setPadding(0, determinePadding, 0, 0);
                                z5 = true;
                            } else {
                                sliceItem6 = sliceItem9;
                                str4 = str2;
                                i6 = i4;
                                arrayList3 = arrayList2;
                                str3 = str;
                                z5 = false;
                            }
                            if (z5) {
                                i10++;
                                sliceItem9 = sliceItem10;
                                z6 = true;
                                i4 = i6 + 1;
                                sliceItem2 = sliceItem5;
                                arrayList = arrayList5;
                                str2 = str4;
                                i3 = i5;
                                arrayList2 = arrayList3;
                                str = str3;
                            }
                            i7 = i10;
                            sliceItem9 = sliceItem6;
                            i10 = i7;
                            i4 = i6 + 1;
                            sliceItem2 = sliceItem5;
                            arrayList = arrayList5;
                            str2 = str4;
                            i3 = i5;
                            arrayList2 = arrayList3;
                            str = str3;
                        } else {
                            sliceItem6 = sliceItem9;
                            str4 = str2;
                            i6 = i4;
                            i7 = i10;
                            arrayList3 = arrayList2;
                            str3 = str;
                            sliceItem9 = sliceItem6;
                            i10 = i7;
                            i4 = i6 + 1;
                            sliceItem2 = sliceItem5;
                            arrayList = arrayList5;
                            str2 = str4;
                            i3 = i5;
                            arrayList2 = arrayList3;
                            str = str3;
                        }
                    } else {
                        sliceItem6 = sliceItem9;
                        i5 = i3;
                    }
                    str4 = str2;
                    i6 = i4;
                    arrayList3 = arrayList2;
                    str3 = str;
                    if (i9 < 1 && "image".equals(sliceItem10.mFormat)) {
                        SliceItem sliceItem11 = cellContent.mOverlayItem;
                        int i11 = this.mTintColor;
                        String str7 = sliceItem10.mFormat;
                        SliceStyle sliceStyle = this.mSliceStyle;
                        if (sliceStyle != null) {
                            if (sliceStyle.mImageCornerRadius > 0.0f) {
                                z3 = true;
                                if ("image".equals(str7) || (iconCompat = (IconCompat) sliceItem10.mObj) == null || (loadDrawable = iconCompat.loadDrawable(getContext())) == null) {
                                    i7 = i10;
                                    z4 = false;
                                } else {
                                    ImageView imageView = new ImageView(getContext());
                                    if (z3) {
                                        imageView.setImageDrawable(new CornerDrawable(loadDrawable, this.mSliceStyle.mImageCornerRadius));
                                    } else {
                                        imageView.setImageDrawable(loadDrawable);
                                    }
                                    if (sliceItem10.hasHint("raw")) {
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                        i7 = i10;
                                        layoutParams = new LinearLayout.LayoutParams(this.mGridContent.getFirstImageSize(getContext()).x, this.mGridContent.getFirstImageSize(getContext()).y);
                                    } else {
                                        i7 = i10;
                                        if (sliceItem10.hasHint("large")) {
                                            imageView.setScaleType(z3 ? ImageView.ScaleType.FIT_XY : ImageView.ScaleType.CENTER_CROP);
                                            layoutParams2 = new LinearLayout.LayoutParams(-1, z ? -1 : this.mLargeImageHeight);
                                            i8 = -1;
                                            if (i11 != i8 && !sliceItem10.hasHint("no_tint")) {
                                                imageView.setColorFilter(i11);
                                            }
                                            if (sliceItem11 != null || this.mViewContainer.getChildCount() == this.mMaxCells - 1) {
                                                linearLayout.addView(imageView, layoutParams2);
                                            } else {
                                                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.abc_slice_grid_text_overlay_image, (ViewGroup) linearLayout, false);
                                                frameLayout.addView(imageView, 0, new FrameLayout.LayoutParams(-2, -2));
                                                ((TextView) frameLayout.findViewById(R.id.text_overlay)).setText((CharSequence) sliceItem11.mObj);
                                                View findViewById = frameLayout.findViewById(R.id.tint_overlay);
                                                Context context = getContext();
                                                Object obj = ContextCompat.sLock;
                                                findViewById.setBackground(new CornerDrawable(context.getDrawable(R.drawable.abc_slice_gradient), this.mSliceStyle.mImageCornerRadius));
                                                linearLayout.addView(frameLayout, layoutParams2);
                                            }
                                            z4 = true;
                                        } else {
                                            boolean z7 = !sliceItem10.hasHint("no_tint");
                                            int i12 = !z7 ? this.mSmallImageSize : this.mIconSize;
                                            imageView.setScaleType(z7 ? ImageView.ScaleType.CENTER_INSIDE : ImageView.ScaleType.CENTER_CROP);
                                            layoutParams = new LinearLayout.LayoutParams(i12, i12);
                                        }
                                    }
                                    layoutParams2 = layoutParams;
                                    i8 = -1;
                                    if (i11 != i8) {
                                        imageView.setColorFilter(i11);
                                    }
                                    if (sliceItem11 != null) {
                                    }
                                    linearLayout.addView(imageView, layoutParams2);
                                    z4 = true;
                                }
                                if (z4) {
                                    i9++;
                                    i10 = i7;
                                    sliceItem9 = sliceItem10;
                                    z6 = true;
                                    i4 = i6 + 1;
                                    sliceItem2 = sliceItem5;
                                    arrayList = arrayList5;
                                    str2 = str4;
                                    i3 = i5;
                                    arrayList2 = arrayList3;
                                    str = str3;
                                }
                                sliceItem9 = sliceItem6;
                                i10 = i7;
                                i4 = i6 + 1;
                                sliceItem2 = sliceItem5;
                                arrayList = arrayList5;
                                str2 = str4;
                                i3 = i5;
                                arrayList2 = arrayList3;
                                str = str3;
                            }
                        }
                        z3 = false;
                        if ("image".equals(str7)) {
                        }
                        i7 = i10;
                        z4 = false;
                        if (z4) {
                        }
                        sliceItem9 = sliceItem6;
                        i10 = i7;
                        i4 = i6 + 1;
                        sliceItem2 = sliceItem5;
                        arrayList = arrayList5;
                        str2 = str4;
                        i3 = i5;
                        arrayList2 = arrayList3;
                        str = str3;
                    }
                    i7 = i10;
                    sliceItem9 = sliceItem6;
                    i10 = i7;
                    i4 = i6 + 1;
                    sliceItem2 = sliceItem5;
                    arrayList = arrayList5;
                    str2 = str4;
                    i3 = i5;
                    arrayList2 = arrayList3;
                    str = str3;
                }
                SliceItem sliceItem12 = sliceItem9;
                if (sliceItem5 != null) {
                    if ("date_picker".equals(sliceItem5.mSubType)) {
                        z6 = addPickerItem(sliceItem5, linearLayout, determinePadding(sliceItem12), true);
                    } else if ("time_picker".equals(sliceItem5.mSubType)) {
                        z6 = addPickerItem(sliceItem5, linearLayout, determinePadding(sliceItem12), false);
                    }
                }
                if (sliceItem3 == null) {
                    SliceActionView sliceActionView2 = new SliceActionView(getContext(), this.mSliceStyle, this.mRowStyle);
                    linearLayout.addView(sliceActionView2);
                    sliceActionView = sliceActionView2;
                    z2 = true;
                } else {
                    z2 = z6;
                    sliceActionView = null;
                }
                if (z2) {
                    return;
                }
                SliceItem sliceItem13 = cellContent.mContentDescr;
                CharSequence charSequence = sliceItem13 != null ? (CharSequence) sliceItem13.mObj : null;
                if (charSequence != null) {
                    linearLayout.setContentDescription(charSequence);
                }
                this.mViewContainer.addView(linearLayout, new LinearLayout.LayoutParams(0, -2, 1.0f));
                if (i != i2 - 1) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
                    marginLayoutParams.setMarginEnd(this.mGutter);
                    linearLayout.setLayoutParams(marginLayoutParams);
                }
                if (sliceItem4 != null) {
                    EventInfo eventInfo = new EventInfo(getMode(), 1, 1, this.mRowIndex);
                    eventInfo.actionPosition = 2;
                    eventInfo.actionIndex = i;
                    eventInfo.actionCount = i2;
                    linearLayout.setTag(new Pair(sliceItem4, eventInfo));
                    makeClickable(linearLayout);
                }
                if (sliceItem3 != null) {
                    EventInfo eventInfo2 = new EventInfo(getMode(), 0, 3, this.mRowIndex);
                    sliceActionView.setAction(new SliceActionImpl(sliceItem3), eventInfo2, this.mObserver, this.mTintColor, this.mLoadingListener);
                    eventInfo2.actionPosition = 2;
                    eventInfo2.actionIndex = i;
                    eventInfo2.actionCount = i2;
                    return;
                }
                return;
            }
        }
        i3 = 2;
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(1);
        arrayList = cellContent.mCellItems;
        sliceItem = cellContent.mContentIntent;
        sliceItem2 = cellContent.mPicker;
        sliceItem3 = cellContent.mToggleItem;
        if (arrayList.size() != 1) {
        }
        str = UniversalCredentialUtil.AGENT_TITLE;
        str2 = "text";
        if (z) {
        }
        sliceItem4 = sliceItem;
        arrayList2 = null;
        int i92 = 0;
        SliceItem sliceItem92 = null;
        i4 = 0;
        int i102 = 0;
        boolean z62 = false;
        while (true) {
            sliceItem5 = sliceItem2;
            if (i4 < arrayList.size()) {
            }
            i4 = i6 + 1;
            sliceItem2 = sliceItem5;
            arrayList = arrayList5;
            str2 = str4;
            i3 = i5;
            arrayList2 = arrayList3;
            str = str3;
        }
        SliceItem sliceItem122 = sliceItem92;
        if (sliceItem5 != null) {
        }
        if (sliceItem3 == null) {
        }
        if (z2) {
        }
    }

    public final boolean addPickerItem(final SliceItem sliceItem, ViewGroup viewGroup, int i, final boolean z) {
        SliceItem findSubtype = SliceQuery.findSubtype(sliceItem, "long", "millis");
        if (findSubtype == null) {
            return false;
        }
        long j = findSubtype.getLong();
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.abc_slice_title, (ViewGroup) null);
        if (this.mSliceStyle != null) {
            textView.setTextSize(0, r4.mGridTitleSize);
            textView.setTextColor(this.mSliceStyle.mTitleColor);
        }
        final Date date = new Date(j);
        SliceItem find = SliceQuery.find(sliceItem, "text", UniversalCredentialUtil.AGENT_TITLE);
        if (find != null) {
            textView.setText((CharSequence) find.mObj);
        }
        final int i2 = this.mRowIndex;
        viewGroup.setOnClickListener(new View.OnClickListener() { // from class: androidx.slice.widget.GridRowView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                if (z) {
                    new DatePickerDialog(GridRowView.this.getContext(), R.style.DialogTheme, GridRowView.this.new DateSetListener(sliceItem, i2), calendar.get(1), calendar.get(2), calendar.get(5)).show();
                } else {
                    new TimePickerDialog(GridRowView.this.getContext(), R.style.DialogTheme, GridRowView.this.new TimeSetListener(sliceItem, i2), calendar.get(11), calendar.get(12), false).show();
                }
            }
        });
        viewGroup.setClickable(true);
        viewGroup.setBackground(SliceViewUtil.getDrawable(android.R.attr.selectableItemBackgroundBorderless, getContext()));
        viewGroup.addView(textView);
        textView.setPadding(0, i, 0, 0);
        return true;
    }

    public final int determinePadding(SliceItem sliceItem) {
        SliceStyle sliceStyle;
        if (sliceItem == null) {
            return 0;
        }
        if ("image".equals(sliceItem.mFormat)) {
            return this.mTextPadding;
        }
        if (("text".equals(sliceItem.mFormat) || "long".equals(sliceItem.mFormat)) && (sliceStyle = this.mSliceStyle) != null) {
            return sliceStyle.mVerticalGridTextPadding;
        }
        return 0;
    }

    public final int getExtraBottomPadding() {
        SliceStyle sliceStyle;
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.mAllImages) {
            return 0;
        }
        if ((this.mRowIndex == this.mRowCount - 1 || getMode() == 1) && (sliceStyle = this.mSliceStyle) != null) {
            return sliceStyle.mGridBottomPadding;
        }
        return 0;
    }

    public final int getMaxCells() {
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.isValid() || getWidth() == 0) {
            return -1;
        }
        if (this.mGridContent.mGridContent.size() <= 1) {
            return 1;
        }
        GridContent gridContent2 = this.mGridContent;
        int i = gridContent2.mLargestImageMode;
        return getWidth() / ((i != 2 ? i != 4 ? this.mSmallImageMinWidth : gridContent2.getFirstImageSize(getContext()).x : this.mLargeImageHeight) + this.mGutter);
    }

    public final void makeClickable(View view) {
        view.setOnClickListener(this);
        view.setBackground(SliceViewUtil.getDrawable(android.R.attr.selectableItemBackgroundBorderless, getContext()));
        view.setClickable(true);
    }

    public final void makeEntireGridClickable(boolean z) {
        this.mViewContainer.setOnTouchListener(z ? this : null);
        this.mViewContainer.setOnClickListener(z ? this : null);
        this.mForeground.setBackground(z ? SliceViewUtil.getDrawable(android.R.attr.selectableItemBackground, getContext()) : null);
        this.mViewContainer.setClickable(z);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SliceItem find;
        Pair pair = (Pair) view.getTag();
        SliceItem sliceItem = (SliceItem) pair.first;
        EventInfo eventInfo = (EventInfo) pair.second;
        if (sliceItem == null || (find = SliceQuery.find(sliceItem, "action", (String) null)) == null) {
            return;
        }
        try {
            find.fireActionInternal(null, null);
            VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4 = this.mObserver;
            if (volumePanelDialog$$ExternalSyntheticLambda4 != null) {
                volumePanelDialog$$ExternalSyntheticLambda4.onSliceAction(eventInfo);
            }
        } catch (PendingIntent.CanceledException e) {
            Log.e("GridRowView", "PendingIntent for slice cannot be sent", e);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int height = this.mGridContent.getHeight(this.mSliceStyle, this.mViewPolicy) + this.mInsetTop + this.mInsetBottom;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.mViewContainer.getLayoutParams().height = height;
        super.onMeasure(i, makeMeasureSpec);
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        this.mForeground.getLocationOnScreen(this.mLoc);
        this.mForeground.getBackground().setHotspot((int) (motionEvent.getRawX() - this.mLoc[0]), (int) (motionEvent.getRawY() - this.mLoc[1]));
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mForeground.setPressed(true);
        } else if (actionMasked == 3 || actionMasked == 1 || actionMasked == 2) {
            this.mForeground.setPressed(false);
        }
        return false;
    }

    public final void populateViews() {
        ViewGroup viewGroup;
        TextView textView;
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.isValid()) {
            resetView();
            return;
        }
        if (scheduleMaxCellsUpdate()) {
            return;
        }
        if (this.mGridContent.getLayoutDir() != -1) {
            setLayoutDirection(this.mGridContent.getLayoutDir());
        }
        if (this.mGridContent.mPrimaryAction != null) {
            this.mViewContainer.setTag(new Pair(this.mGridContent.mPrimaryAction, new EventInfo(getMode(), 3, 1, this.mRowIndex)));
            makeEntireGridClickable(true);
        }
        SliceItem sliceItem = this.mGridContent.mContentDescr;
        CharSequence charSequence = sliceItem != null ? (CharSequence) sliceItem.mObj : null;
        if (charSequence != null) {
            this.mViewContainer.setContentDescription(charSequence);
        }
        GridContent gridContent2 = this.mGridContent;
        ArrayList arrayList = gridContent2.mGridContent;
        int i = gridContent2.mLargestImageMode;
        if (i == 2 || i == 4) {
            this.mViewContainer.setGravity(48);
        } else {
            this.mViewContainer.setGravity(16);
        }
        int i2 = this.mMaxCells;
        boolean z = this.mGridContent.mSeeMoreItem != null;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            if (this.mViewContainer.getChildCount() >= i2) {
                int size = arrayList.size() - i2;
                if (z) {
                    LinearLayout linearLayout = this.mViewContainer;
                    View childAt = linearLayout.getChildAt(linearLayout.getChildCount() - 1);
                    this.mViewContainer.removeView(childAt);
                    SliceItem sliceItem2 = this.mGridContent.mSeeMoreItem;
                    int childCount = this.mViewContainer.getChildCount();
                    int i4 = this.mMaxCells;
                    if (("slice".equals(sliceItem2.mFormat) || "action".equals(sliceItem2.mFormat)) && sliceItem2.getSlice().getItems().size() > 0) {
                        addCell(new GridContent.CellContent(sliceItem2), childCount, i4);
                        return;
                    }
                    LayoutInflater from = LayoutInflater.from(getContext());
                    if (this.mGridContent.mAllImages) {
                        viewGroup = (FrameLayout) from.inflate(R.layout.abc_slice_grid_see_more_overlay, (ViewGroup) this.mViewContainer, false);
                        viewGroup.addView(childAt, 0, new FrameLayout.LayoutParams(-1, -1));
                        textView = (TextView) viewGroup.findViewById(R.id.text_see_more_count);
                        viewGroup.findViewById(R.id.overlay_see_more).setBackground(new CornerDrawable(SliceViewUtil.getDrawable(android.R.attr.colorForeground, getContext()), this.mSliceStyle.mImageCornerRadius));
                    } else {
                        viewGroup = (LinearLayout) from.inflate(R.layout.abc_slice_grid_see_more, (ViewGroup) this.mViewContainer, false);
                        textView = (TextView) viewGroup.findViewById(R.id.text_see_more_count);
                        TextView textView2 = (TextView) viewGroup.findViewById(R.id.text_see_more);
                        if (this.mSliceStyle != null && this.mRowStyle != null) {
                            textView2.setTextSize(0, r12.mGridTitleSize);
                            RowStyle rowStyle = this.mRowStyle;
                            Integer num = rowStyle.mTitleColor;
                            textView2.setTextColor(num != null ? num.intValue() : rowStyle.mSliceStyle.mTitleColor);
                        }
                    }
                    this.mViewContainer.addView(viewGroup, new LinearLayout.LayoutParams(0, -1, 1.0f));
                    textView.setText(getResources().getString(R.string.abc_slice_more_content, Integer.valueOf(size)));
                    EventInfo eventInfo = new EventInfo(getMode(), 4, 1, this.mRowIndex);
                    eventInfo.actionPosition = 2;
                    eventInfo.actionIndex = childCount;
                    eventInfo.actionCount = i4;
                    viewGroup.setTag(new Pair(sliceItem2, eventInfo));
                    makeClickable(viewGroup);
                    return;
                }
                return;
            }
            addCell((GridContent.CellContent) arrayList.get(i3), i3, Math.min(arrayList.size(), i2));
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void resetView() {
        if (this.mMaxCellUpdateScheduled) {
            this.mMaxCellUpdateScheduled = false;
            getViewTreeObserver().removeOnPreDrawListener(this.mMaxCellsUpdater);
        }
        this.mViewContainer.removeAllViews();
        setLayoutDirection(2);
        makeEntireGridClickable(false);
    }

    public final boolean scheduleMaxCellsUpdate() {
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.isValid()) {
            return true;
        }
        if (getWidth() != 0) {
            this.mMaxCells = getMaxCells();
            return false;
        }
        this.mMaxCellUpdateScheduled = true;
        getViewTreeObserver().addOnPreDrawListener(this.mMaxCellsUpdater);
        return true;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setInsets(int i, int i2, int i3, int i4) {
        SliceStyle sliceStyle;
        super.setInsets(i, i2, i3, i4);
        LinearLayout linearLayout = this.mViewContainer;
        GridContent gridContent = this.mGridContent;
        linearLayout.setPadding(i, ((gridContent == null || !gridContent.mAllImages || this.mRowIndex != 0 || (sliceStyle = this.mSliceStyle) == null) ? 0 : sliceStyle.mGridTopPadding) + i2, i3, getExtraBottomPadding() + i4);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceItem(SliceContent sliceContent, boolean z, int i, int i2, VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4) {
        SliceStyle sliceStyle;
        resetView();
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda4;
        this.mRowIndex = i;
        this.mRowCount = i2;
        this.mGridContent = (GridContent) sliceContent;
        if (!scheduleMaxCellsUpdate()) {
            populateViews();
        }
        LinearLayout linearLayout = this.mViewContainer;
        int i3 = this.mInsetStart;
        int i4 = this.mInsetTop;
        GridContent gridContent = this.mGridContent;
        linearLayout.setPadding(i3, ((gridContent == null || !gridContent.mAllImages || this.mRowIndex != 0 || (sliceStyle = this.mSliceStyle) == null) ? 0 : sliceStyle.mGridTopPadding) + i4, this.mInsetEnd, getExtraBottomPadding() + this.mInsetBottom);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setTint(int i) {
        this.mTintColor = i;
        if (this.mGridContent != null) {
            resetView();
            populateViews();
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.slice.widget.GridRowView$2] */
    public GridRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLoc = new int[2];
        this.mMaxCells = -1;
        this.mMaxCellsUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: androidx.slice.widget.GridRowView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                GridRowView gridRowView = GridRowView.this;
                gridRowView.mMaxCells = gridRowView.getMaxCells();
                GridRowView.this.populateViews();
                GridRowView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                GridRowView.this.mMaxCellUpdateScheduled = false;
                return true;
            }
        };
        Resources resources = getContext().getResources();
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.mViewContainer = linearLayout;
        linearLayout.setOrientation(0);
        addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
        linearLayout.setGravity(16);
        this.mIconSize = resources.getDimensionPixelSize(R.dimen.abc_slice_icon_size);
        this.mSmallImageSize = resources.getDimensionPixelSize(R.dimen.abc_slice_small_image_size);
        this.mLargeImageHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_image_only_height);
        this.mSmallImageMinWidth = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_image_min_width);
        this.mGutter = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_gutter);
        this.mTextPadding = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_text_padding);
        View view = new View(getContext());
        this.mForeground = view;
        addView(view, new FrameLayout.LayoutParams(-1, -1));
    }
}
