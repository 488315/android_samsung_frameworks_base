package androidx.slice.widget;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.view.ViewCompat;
import androidx.slice.CornerDrawable;
import androidx.slice.SliceItem;
import androidx.slice.SliceStructure;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.android.systemui.R;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RowView extends SliceChildView implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public final View mActionDivider;
    public final ProgressBar mActionSpinner;
    public final ArrayMap mActions;
    public boolean mAllowTwoLines;
    public final View mBottomDivider;
    public final LinearLayout mContent;
    public final LinearLayout mEndContainer;
    public Handler mHandler;
    public List mHeaderActions;
    public int mIconSize;
    public int mImageSize;
    public boolean mIsHeader;
    public boolean mIsRangeSliding;
    public boolean mIsStarRating;
    public long mLastSentRangeUpdate;
    public final TextView mLastUpdatedText;
    public Set mLoadingActions;
    public int mMeasuredRangeHeight;
    public final TextView mPrimaryText;
    public View mRangeBar;
    public SliceItem mRangeItem;
    public int mRangeMaxValue;
    public int mRangeMinValue;
    public final RunnableC05092 mRangeUpdater;
    public boolean mRangeUpdaterRunning;
    public int mRangeValue;
    public final C05114 mRatingBarChangeListener;
    public final LinearLayout mRootView;
    public SliceActionImpl mRowAction;
    public RowContent mRowContent;
    public int mRowIndex;
    public final TextView mSecondaryText;
    public View mSeeMoreView;
    public final C05103 mSeekBarChangeListener;
    public SliceItem mSelectionItem;
    public ArrayList mSelectionOptionKeys;
    public ArrayList mSelectionOptionValues;
    public Spinner mSelectionSpinner;
    public boolean mShowActionSpinner;
    public final LinearLayout mStartContainer;
    public SliceItem mStartItem;
    public final LinearLayout mSubContent;
    public final ArrayMap mToggles;

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
                    sliceItem.fireActionInternal(RowView.this.getContext(), new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    RowView rowView = RowView.this;
                    if (rowView.mObserver != null) {
                        RowView.this.mObserver.onSliceAction(new EventInfo(rowView.getMode(), 6, 7, this.mRowIndex));
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("RowView", "PendingIntent for slice cannot be sent", e);
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
                    sliceItem.fireActionInternal(RowView.this.getContext(), new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    RowView rowView = RowView.this;
                    if (rowView.mObserver != null) {
                        RowView.this.mObserver.onSliceAction(new EventInfo(rowView.getMode(), 7, 8, this.mRowIndex));
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("RowView", "PendingIntent for slice cannot be sent", e);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.slice.widget.RowView$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.slice.widget.RowView$3] */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.slice.widget.RowView$4] */
    public RowView(Context context) {
        super(context);
        this.mToggles = new ArrayMap();
        this.mActions = new ArrayMap();
        this.mLoadingActions = new HashSet();
        this.mRangeUpdater = new Runnable() { // from class: androidx.slice.widget.RowView.2
            @Override // java.lang.Runnable
            public final void run() {
                RowView.this.sendSliderValue();
                RowView.this.mRangeUpdaterRunning = false;
            }
        };
        this.mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: androidx.slice.widget.RowView.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                RowView rowView = RowView.this;
                rowView.mRangeValue = i + rowView.mRangeMinValue;
                long currentTimeMillis = System.currentTimeMillis();
                RowView rowView2 = RowView.this;
                long j = rowView2.mLastSentRangeUpdate;
                if (j != 0 && currentTimeMillis - j > 200) {
                    rowView2.mRangeUpdaterRunning = false;
                    rowView2.mHandler.removeCallbacks(rowView2.mRangeUpdater);
                    RowView.this.sendSliderValue();
                } else {
                    if (rowView2.mRangeUpdaterRunning) {
                        return;
                    }
                    rowView2.mRangeUpdaterRunning = true;
                    rowView2.mHandler.postDelayed(rowView2.mRangeUpdater, 200L);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                RowView.this.mIsRangeSliding = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                RowView rowView = RowView.this;
                rowView.mIsRangeSliding = false;
                if (rowView.mRangeUpdaterRunning) {
                    rowView.mRangeUpdaterRunning = false;
                    rowView.mHandler.removeCallbacks(rowView.mRangeUpdater);
                    RowView rowView2 = RowView.this;
                    int progress = seekBar.getProgress();
                    RowView rowView3 = RowView.this;
                    rowView2.mRangeValue = progress + rowView3.mRangeMinValue;
                    rowView3.sendSliderValue();
                }
            }
        };
        this.mRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() { // from class: androidx.slice.widget.RowView.4
            @Override // android.widget.RatingBar.OnRatingBarChangeListener
            public final void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
                RowView.this.mRangeValue = Math.round(f + r6.mRangeMinValue);
                long currentTimeMillis = System.currentTimeMillis();
                RowView rowView = RowView.this;
                long j = rowView.mLastSentRangeUpdate;
                if (j != 0 && currentTimeMillis - j > 200) {
                    rowView.mRangeUpdaterRunning = false;
                    rowView.mHandler.removeCallbacks(rowView.mRangeUpdater);
                    RowView.this.sendSliderValue();
                } else {
                    if (rowView.mRangeUpdaterRunning) {
                        return;
                    }
                    rowView.mRangeUpdaterRunning = true;
                    rowView.mHandler.postDelayed(rowView.mRangeUpdater, 200L);
                }
            }
        };
        this.mIconSize = getContext().getResources().getDimensionPixelSize(R.dimen.abc_slice_icon_size);
        this.mImageSize = getContext().getResources().getDimensionPixelSize(R.dimen.abc_slice_small_image_size);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.abc_slice_small_template, (ViewGroup) this, false);
        this.mRootView = linearLayout;
        addView(linearLayout);
        this.mStartContainer = (LinearLayout) findViewById(R.id.icon_frame);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(android.R.id.content);
        this.mContent = linearLayout2;
        this.mSubContent = (LinearLayout) findViewById(R.id.subcontent);
        this.mPrimaryText = (TextView) findViewById(android.R.id.title);
        this.mSecondaryText = (TextView) findViewById(android.R.id.summary);
        this.mLastUpdatedText = (TextView) findViewById(R.id.last_updated);
        this.mBottomDivider = findViewById(R.id.bottom_divider);
        this.mActionDivider = findViewById(R.id.action_divider);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.action_sent_indicator);
        this.mActionSpinner = progressBar;
        int colorAttr = SliceViewUtil.getColorAttr(R.attr.colorControlHighlight, getContext());
        Drawable indeterminateDrawable = progressBar.getIndeterminateDrawable();
        if (indeterminateDrawable != null && colorAttr != 0) {
            indeterminateDrawable.setColorFilter(colorAttr, PorterDuff.Mode.MULTIPLY);
            progressBar.setProgressDrawable(indeterminateDrawable);
        }
        this.mEndContainer = (LinearLayout) findViewById(android.R.id.widget_frame);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 2);
        ViewCompat.Api16Impl.setImportantForAccessibility(linearLayout2, 2);
    }

    public static void setViewSidePaddings(View view, int i, int i2) {
        boolean z = i < 0 && i2 < 0;
        if (view == null || z) {
            return;
        }
        if (i < 0) {
            i = view.getPaddingStart();
        }
        int paddingTop = view.getPaddingTop();
        if (i2 < 0) {
            i2 = view.getPaddingEnd();
        }
        view.setPaddingRelative(i, paddingTop, i2, view.getPaddingBottom());
    }

    public final void addAction(SliceActionImpl sliceActionImpl, int i, ViewGroup viewGroup, boolean z) {
        SliceActionView sliceActionView = new SliceActionView(getContext(), this.mSliceStyle, this.mRowStyle);
        viewGroup.addView(sliceActionView);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(0);
        }
        boolean isToggle = sliceActionImpl.isToggle();
        EventInfo eventInfo = new EventInfo(getMode(), !isToggle ? 1 : 0, isToggle ? 3 : 0, this.mRowIndex);
        if (z) {
            eventInfo.actionPosition = 0;
            eventInfo.actionIndex = 0;
            eventInfo.actionCount = 1;
        }
        sliceActionView.setAction(sliceActionImpl, eventInfo, this.mObserver, i, this.mLoadingListener);
        if (this.mLoadingActions.contains(sliceActionImpl.mSliceItem)) {
            if (sliceActionView.mProgressView == null) {
                ProgressBar progressBar = (ProgressBar) LayoutInflater.from(sliceActionView.getContext()).inflate(R.layout.abc_slice_progress_view, (ViewGroup) sliceActionView, false);
                sliceActionView.mProgressView = progressBar;
                sliceActionView.addView(progressBar);
            }
            Context context = sliceActionView.getContext();
            ProgressBar progressBar2 = sliceActionView.mProgressView;
            int colorAttr = SliceViewUtil.getColorAttr(R.attr.colorControlHighlight, context);
            Drawable indeterminateDrawable = progressBar2.getIndeterminateDrawable();
            if (indeterminateDrawable != null && colorAttr != 0) {
                indeterminateDrawable.setColorFilter(colorAttr, PorterDuff.Mode.MULTIPLY);
                progressBar2.setProgressDrawable(indeterminateDrawable);
            }
            sliceActionView.mActionView.setVisibility(8);
            sliceActionView.mProgressView.setVisibility(0);
        }
        if (isToggle) {
            this.mToggles.put(sliceActionImpl, sliceActionView);
        } else {
            this.mActions.put(sliceActionImpl, sliceActionView);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean addItem(SliceItem sliceItem, int i, boolean z) {
        IconCompat iconCompat;
        SliceItem sliceItem2;
        boolean z2;
        RowStyle rowStyle;
        int i2;
        ViewGroup viewGroup = z ? this.mStartContainer : this.mEndContainer;
        if ("slice".equals(sliceItem.mFormat) || "action".equals(sliceItem.mFormat)) {
            if (sliceItem.hasHint("shortcut")) {
                addAction(new SliceActionImpl(sliceItem), i, viewGroup, z);
                return true;
            }
            if (sliceItem.getSlice().getItems().size() == 0) {
                return false;
            }
            sliceItem = (SliceItem) sliceItem.getSlice().getItems().get(0);
        }
        TextView textView = null;
        if ("image".equals(sliceItem.mFormat)) {
            iconCompat = (IconCompat) sliceItem.mObj;
            sliceItem2 = null;
        } else if ("long".equals(sliceItem.mFormat)) {
            sliceItem2 = sliceItem;
            iconCompat = null;
        } else {
            iconCompat = null;
            sliceItem2 = null;
        }
        if (iconCompat != null) {
            boolean z3 = !sliceItem.hasHint("no_tint");
            boolean hasHint = sliceItem.hasHint("raw");
            float f = getResources().getDisplayMetrics().density;
            ImageView imageView = new ImageView(getContext());
            Drawable loadDrawable = iconCompat.loadDrawable(getContext());
            SliceStyle sliceStyle = this.mSliceStyle;
            if (sliceStyle != null) {
                if (sliceStyle.mImageCornerRadius > 0.0f) {
                    z2 = true;
                    if (z2 || !sliceItem.hasHint("large")) {
                        imageView.setImageDrawable(loadDrawable);
                    } else {
                        imageView.setImageDrawable(new CornerDrawable(loadDrawable, this.mSliceStyle.mImageCornerRadius));
                    }
                    if (z3 && i != -1) {
                        imageView.setColorFilter(i);
                    }
                    if (this.mIsRangeSliding) {
                        viewGroup.addView(imageView);
                    } else {
                        viewGroup.removeAllViews();
                        viewGroup.addView(imageView);
                    }
                    rowStyle = this.mRowStyle;
                    if (rowStyle != null) {
                        int i3 = rowStyle.mIconSize;
                        if (i3 <= 0) {
                            i3 = this.mIconSize;
                        }
                        this.mIconSize = i3;
                        int i4 = rowStyle.mImageSize;
                        if (i4 <= 0) {
                            i4 = this.mImageSize;
                        }
                        this.mImageSize = i4;
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.width = !hasHint ? Math.round(loadDrawable.getIntrinsicWidth() / f) : this.mImageSize;
                    layoutParams.height = !hasHint ? Math.round(loadDrawable.getIntrinsicHeight() / f) : this.mImageSize;
                    imageView.setLayoutParams(layoutParams);
                    if (z3) {
                        i2 = 0;
                    } else {
                        int i5 = this.mImageSize;
                        i2 = i5 == -1 ? this.mIconSize / 2 : (i5 - this.mIconSize) / 2;
                    }
                    imageView.setPadding(i2, i2, i2, i2);
                    textView = imageView;
                }
            }
            z2 = false;
            if (z2) {
            }
            imageView.setImageDrawable(loadDrawable);
            if (z3) {
                imageView.setColorFilter(i);
            }
            if (this.mIsRangeSliding) {
            }
            rowStyle = this.mRowStyle;
            if (rowStyle != null) {
            }
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams2.width = !hasHint ? Math.round(loadDrawable.getIntrinsicWidth() / f) : this.mImageSize;
            layoutParams2.height = !hasHint ? Math.round(loadDrawable.getIntrinsicHeight() / f) : this.mImageSize;
            imageView.setLayoutParams(layoutParams2);
            if (z3) {
            }
            imageView.setPadding(i2, i2, i2, i2);
            textView = imageView;
        } else if (sliceItem2 != null) {
            textView = new TextView(getContext());
            textView.setText(SliceViewUtil.getTimestampString(getContext(), sliceItem.getLong()));
            if (this.mSliceStyle != null) {
                textView.setTextSize(0, r10.mSubtitleSize);
                textView.setTextColor(this.mRowStyle.getSubtitleColor());
            }
            viewGroup.addView(textView);
        }
        return textView != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x014a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addSubtitle(boolean z) {
        SliceItem sliceItem;
        String str;
        boolean z2;
        String str2;
        RowContent rowContent = this.mRowContent;
        if (rowContent != null) {
            if (rowContent.mRange == null || this.mStartItem == null) {
                if (getMode() == 1) {
                    RowContent rowContent2 = this.mRowContent;
                    sliceItem = rowContent2.mSummaryItem;
                    if (sliceItem == null) {
                        sliceItem = rowContent2.mSubtitleItem;
                    }
                } else {
                    sliceItem = this.mRowContent.mSubtitleItem;
                }
                if (this.mShowLastUpdated) {
                    long j = this.mLastUpdated;
                    if (j != -1) {
                        long currentTimeMillis = System.currentTimeMillis() - j;
                        if (currentTimeMillis > 31449600000L) {
                            int i = (int) (currentTimeMillis / 31449600000L);
                            str2 = getResources().getQuantityString(R.plurals.abc_slice_duration_years, i, Integer.valueOf(i));
                        } else if (currentTimeMillis > 86400000) {
                            int i2 = (int) (currentTimeMillis / 86400000);
                            str2 = getResources().getQuantityString(R.plurals.abc_slice_duration_days, i2, Integer.valueOf(i2));
                        } else if (currentTimeMillis > 60000) {
                            int i3 = (int) (currentTimeMillis / 60000);
                            str2 = getResources().getQuantityString(R.plurals.abc_slice_duration_min, i3, Integer.valueOf(i3));
                        } else {
                            str2 = null;
                        }
                        if (str2 != null) {
                            str = getResources().getString(R.string.abc_slice_updated, str2);
                            CharSequence sanitizedText = sliceItem != null ? sliceItem.getSanitizedText() : null;
                            z2 = TextUtils.isEmpty(sanitizedText) || (sliceItem != null && sliceItem.hasHint("partial"));
                            if (z2) {
                                this.mSecondaryText.setText(sanitizedText);
                                if (this.mSliceStyle != null) {
                                    this.mSecondaryText.setTextSize(0, this.mIsHeader ? r4.mHeaderSubtitleSize : r4.mSubtitleSize);
                                    this.mSecondaryText.setTextColor(this.mRowStyle.getSubtitleColor());
                                    this.mSecondaryText.setPadding(0, this.mIsHeader ? this.mSliceStyle.mVerticalHeaderTextPadding : this.mSliceStyle.mVerticalTextPadding, 0, 0);
                                }
                            }
                            if (str != null) {
                                if (!TextUtils.isEmpty(sanitizedText)) {
                                    str = " · " + ((Object) str);
                                }
                                SpannableString spannableString = new SpannableString(str);
                                spannableString.setSpan(new StyleSpan(2), 0, str.length(), 0);
                                this.mLastUpdatedText.setText(spannableString);
                                if (this.mSliceStyle != null) {
                                    this.mLastUpdatedText.setTextSize(0, this.mIsHeader ? r3.mHeaderSubtitleSize : r3.mSubtitleSize);
                                    this.mLastUpdatedText.setTextColor(this.mRowStyle.getSubtitleColor());
                                }
                            }
                            this.mLastUpdatedText.setVisibility(!TextUtils.isEmpty(str) ? 8 : 0);
                            this.mSecondaryText.setVisibility(z2 ? 0 : 8);
                            int i4 = ((this.mRowContent.mIsHeader || this.mAllowTwoLines) || z || !z2 || !TextUtils.isEmpty(str)) ? 1 : 2;
                            this.mSecondaryText.setSingleLine(i4 == 1);
                            this.mSecondaryText.setMaxLines(i4);
                            this.mSecondaryText.requestLayout();
                            this.mLastUpdatedText.requestLayout();
                        }
                    }
                }
                str = null;
                if (sliceItem != null) {
                }
                if (TextUtils.isEmpty(sanitizedText)) {
                }
                if (z2) {
                }
                if (str != null) {
                }
                this.mLastUpdatedText.setVisibility(!TextUtils.isEmpty(str) ? 8 : 0);
                this.mSecondaryText.setVisibility(z2 ? 0 : 8);
                if (this.mRowContent.mIsHeader || this.mAllowTwoLines) {
                }
                this.mSecondaryText.setSingleLine(i4 == 1);
                this.mSecondaryText.setMaxLines(i4);
                this.mSecondaryText.requestLayout();
                this.mLastUpdatedText.requestLayout();
            }
        }
    }

    public final int getRowContentHeight() {
        int height = this.mRowContent.getHeight(this.mSliceStyle, this.mViewPolicy);
        if (this.mRangeBar != null && this.mStartItem == null) {
            height -= this.mSliceStyle.mRowRangeHeight;
        }
        return this.mSelectionSpinner != null ? height - this.mSliceStyle.mRowSelectionHeight : height;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SliceActionView sliceActionView;
        SliceAdapter sliceAdapter;
        SliceActionImpl sliceActionImpl;
        SliceActionImpl sliceActionImpl2 = this.mRowAction;
        if (sliceActionImpl2 == null || sliceActionImpl2.mActionItem == null) {
            return;
        }
        if (sliceActionImpl2.getSubtype() != null) {
            String subtype = this.mRowAction.getSubtype();
            subtype.getClass();
            switch (subtype) {
                case "toggle":
                    sliceActionView = (SliceActionView) this.mToggles.get(this.mRowAction);
                    break;
                case "time_picker":
                    onClickPicker(false);
                    return;
                case "date_picker":
                    onClickPicker(true);
                    return;
                default:
                    sliceActionView = (SliceActionView) this.mActions.get(this.mRowAction);
                    break;
            }
        } else {
            sliceActionView = (SliceActionView) this.mActions.get(this.mRowAction);
        }
        if (sliceActionView != null && !(view instanceof SliceActionView)) {
            SliceActionImpl sliceActionImpl3 = sliceActionView.mSliceAction;
            if (sliceActionImpl3 == null) {
                return;
            }
            if (!sliceActionImpl3.isToggle()) {
                sliceActionView.sendActionInternal();
                return;
            } else {
                if (sliceActionView.mActionView == null || (sliceActionImpl = sliceActionView.mSliceAction) == null || !sliceActionImpl.isToggle()) {
                    return;
                }
                ((Checkable) sliceActionView.mActionView).toggle();
                return;
            }
        }
        if (this.mRowContent.mIsHeader) {
            performClick();
            return;
        }
        try {
            this.mRowAction.mActionItem.fireActionInternal(getContext(), null);
            this.mShowActionSpinner = false;
            if (this.mObserver != null) {
                EventInfo eventInfo = new EventInfo(getMode(), 3, 0, this.mRowIndex);
                VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4 = this.mObserver;
                SliceItem sliceItem = this.mRowAction.mSliceItem;
                volumePanelDialog$$ExternalSyntheticLambda4.onSliceAction(eventInfo);
            }
            if (this.mShowActionSpinner && (sliceAdapter = this.mLoadingListener) != null) {
                sliceAdapter.onSliceActionLoading(this.mRowAction.mSliceItem, this.mRowIndex);
                this.mLoadingActions.add(this.mRowAction.mSliceItem);
            }
            updateActionSpinner();
        } catch (PendingIntent.CanceledException e) {
            Log.e("RowView", "PendingIntent for slice cannot be sent", e);
        }
    }

    public final void onClickPicker(boolean z) {
        if (this.mRowAction == null) {
            return;
        }
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("ASDF", z, ":");
        m49m.append(this.mRowAction.mSliceItem);
        Log.d("ASDF", m49m.toString());
        SliceItem findSubtype = SliceQuery.findSubtype(this.mRowAction.mSliceItem, "long", "millis");
        if (findSubtype == null) {
            return;
        }
        int i = this.mRowIndex;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(findSubtype.getLong()));
        if (z) {
            new DatePickerDialog(getContext(), R.style.DialogTheme, new DateSetListener(this.mRowAction.mSliceItem, i), calendar.get(1), calendar.get(2), calendar.get(5)).show();
        } else {
            new TimePickerDialog(getContext(), R.style.DialogTheme, new TimeSetListener(this.mRowAction.mSliceItem, i), calendar.get(11), calendar.get(12), false).show();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        if (this.mSelectionItem == null || adapterView != this.mSelectionSpinner || i < 0 || i >= this.mSelectionOptionKeys.size()) {
            return;
        }
        if (this.mObserver != null) {
            this.mObserver.onSliceAction(new EventInfo(getMode(), 5, 6, this.mRowIndex));
        }
        try {
            this.mSelectionItem.fireActionInternal(getContext(), new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.SELECTION", (String) this.mSelectionOptionKeys.get(i)));
        } catch (PendingIntent.CanceledException e) {
            Log.e("RowView", "PendingIntent for slice cannot be sent", e);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        LinearLayout linearLayout = this.mRootView;
        linearLayout.layout(paddingLeft, this.mInsetTop, linearLayout.getMeasuredWidth() + paddingLeft, getRowContentHeight() + this.mInsetTop);
        if (this.mRangeBar != null && this.mStartItem == null) {
            int rowContentHeight = getRowContentHeight() + ((this.mSliceStyle.mRowRangeHeight - this.mMeasuredRangeHeight) / 2) + this.mInsetTop;
            int i5 = this.mMeasuredRangeHeight + rowContentHeight;
            View view = this.mRangeBar;
            view.layout(paddingLeft, rowContentHeight, view.getMeasuredWidth() + paddingLeft, i5);
            return;
        }
        if (this.mSelectionSpinner != null) {
            int rowContentHeight2 = getRowContentHeight() + this.mInsetTop;
            int measuredHeight = this.mSelectionSpinner.getMeasuredHeight() + rowContentHeight2;
            Spinner spinner = this.mSelectionSpinner;
            spinner.layout(paddingLeft, rowContentHeight2, spinner.getMeasuredWidth() + paddingLeft, measuredHeight);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int rowContentHeight = getRowContentHeight();
        if (rowContentHeight != 0) {
            this.mRootView.setVisibility(0);
            measureChild(this.mRootView, i, View.MeasureSpec.makeMeasureSpec(rowContentHeight + this.mInsetTop + this.mInsetBottom, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            i3 = this.mRootView.getMeasuredWidth();
        } else {
            this.mRootView.setVisibility(8);
            i3 = 0;
        }
        View view = this.mRangeBar;
        if (view == null || this.mStartItem != null) {
            Spinner spinner = this.mSelectionSpinner;
            if (spinner != null) {
                measureChild(spinner, i, View.MeasureSpec.makeMeasureSpec(this.mSliceStyle.mRowSelectionHeight + this.mInsetTop + this.mInsetBottom, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                i3 = Math.max(i3, this.mSelectionSpinner.getMeasuredWidth());
            }
        } else {
            measureChild(view, i, View.MeasureSpec.makeMeasureSpec(this.mSliceStyle.mRowRangeHeight + this.mInsetTop + this.mInsetBottom, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            this.mMeasuredRangeHeight = this.mRangeBar.getMeasuredHeight();
            i3 = Math.max(i3, this.mRangeBar.getMeasuredWidth());
        }
        int max = Math.max(i3 + this.mInsetStart + this.mInsetEnd, getSuggestedMinimumWidth());
        RowContent rowContent = this.mRowContent;
        setMeasuredDimension(FrameLayout.resolveSizeAndState(max, i, 0), (rowContent != null ? rowContent.getHeight(this.mSliceStyle, this.mViewPolicy) : 0) + this.mInsetTop + this.mInsetBottom);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void populateViews(boolean z) {
        ProgressBar progressBar;
        int i;
        IconCompat iconCompat;
        Drawable loadDrawable;
        int i2;
        boolean z2;
        boolean z3 = z && this.mIsRangeSliding;
        if (!z3) {
            resetViewState();
        }
        if (this.mRowContent.getLayoutDir() != -1) {
            setLayoutDirection(this.mRowContent.getLayoutDir());
        }
        if (this.mRowContent.isDefaultSeeMore()) {
            final Button button = (Button) LayoutInflater.from(getContext()).inflate(R.layout.abc_slice_row_show_more, (ViewGroup) this, false);
            button.setOnClickListener(new View.OnClickListener() { // from class: androidx.slice.widget.RowView.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    try {
                        RowView rowView = RowView.this;
                        if (rowView.mObserver != null) {
                            EventInfo eventInfo = new EventInfo(rowView.getMode(), 4, 0, RowView.this.mRowIndex);
                            RowView rowView2 = RowView.this;
                            VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4 = rowView2.mObserver;
                            SliceItem sliceItem = rowView2.mRowContent.mSliceItem;
                            volumePanelDialog$$ExternalSyntheticLambda4.onSliceAction(eventInfo);
                        }
                        RowView rowView3 = RowView.this;
                        rowView3.mRowContent.mSliceItem.fireActionInternal(rowView3.getContext(), null);
                        rowView3.mShowActionSpinner = false;
                        RowView rowView4 = RowView.this;
                        if (rowView4.mShowActionSpinner) {
                            SliceAdapter sliceAdapter = rowView4.mLoadingListener;
                            if (sliceAdapter != null) {
                                sliceAdapter.onSliceActionLoading(rowView4.mRowContent.mSliceItem, rowView4.mRowIndex);
                            }
                            RowView rowView5 = RowView.this;
                            rowView5.mLoadingActions.add(rowView5.mRowContent.mSliceItem);
                            button.setVisibility(8);
                        }
                        RowView.this.updateActionSpinner();
                    } catch (PendingIntent.CanceledException e) {
                        Log.e("RowView", "PendingIntent for slice cannot be sent", e);
                    }
                }
            });
            int i3 = this.mTintColor;
            if (i3 != -1) {
                button.setTextColor(i3);
            }
            this.mSeeMoreView = button;
            this.mRootView.addView(button);
            if (this.mLoadingActions.contains(this.mRowContent.mSliceItem)) {
                this.mShowActionSpinner = true;
                button.setVisibility(8);
                updateActionSpinner();
                return;
            }
            return;
        }
        SliceItem sliceItem = this.mRowContent.mContentDescr;
        CharSequence charSequence = sliceItem != null ? (CharSequence) sliceItem.mObj : null;
        if (charSequence != null) {
            this.mContent.setContentDescription(charSequence);
        }
        RowContent rowContent = this.mRowContent;
        boolean z4 = rowContent.mIsHeader;
        SliceItem sliceItem2 = (!z4 || rowContent.mShowTitleItems) ? rowContent.mStartItem : null;
        this.mStartItem = sliceItem2;
        boolean z5 = sliceItem2 != null && (!z4 || rowContent.mShowTitleItems);
        if (z5) {
            z5 = addItem(sliceItem2, this.mTintColor, true);
        }
        this.mStartContainer.setVisibility(z5 ? 0 : 8);
        SliceItem sliceItem3 = this.mRowContent.mTitleItem;
        if (sliceItem3 != null) {
            this.mPrimaryText.setText(sliceItem3.getSanitizedText());
        }
        if (this.mSliceStyle != null) {
            this.mPrimaryText.setTextSize(0, this.mIsHeader ? r6.mHeaderTitleSize : r6.mTitleSize);
            TextView textView = this.mPrimaryText;
            RowStyle rowStyle = this.mRowStyle;
            Integer num = rowStyle.mTitleColor;
            textView.setTextColor(num != null ? num.intValue() : rowStyle.mSliceStyle.mTitleColor);
        }
        this.mPrimaryText.setVisibility(sliceItem3 != null ? 0 : 8);
        addSubtitle(sliceItem3 != null);
        this.mBottomDivider.setVisibility(this.mRowContent.mShowBottomDivider ? 0 : 8);
        SliceItem sliceItem4 = this.mRowContent.mPrimaryAction;
        if (sliceItem4 != null && sliceItem4 != this.mStartItem) {
            SliceActionImpl sliceActionImpl = new SliceActionImpl(sliceItem4);
            this.mRowAction = sliceActionImpl;
            if (sliceActionImpl.getSubtype() != null) {
                String subtype = this.mRowAction.getSubtype();
                subtype.getClass();
                switch (subtype.hashCode()) {
                    case -868304044:
                        if (subtype.equals("toggle")) {
                            z2 = false;
                            break;
                        }
                        z2 = -1;
                        break;
                    case 759128640:
                        if (subtype.equals("time_picker")) {
                            z2 = true;
                            break;
                        }
                        z2 = -1;
                        break;
                    case 1250407999:
                        if (subtype.equals("date_picker")) {
                            z2 = 2;
                            break;
                        }
                        z2 = -1;
                        break;
                    default:
                        z2 = -1;
                        break;
                }
                switch (z2) {
                    case false:
                        addAction(this.mRowAction, this.mTintColor, this.mEndContainer, false);
                        setViewClickable(this.mRootView, true);
                        break;
                    case true:
                        setViewClickable(this.mRootView, true);
                        break;
                    case true:
                        setViewClickable(this.mRootView, true);
                        break;
                }
                return;
            }
        }
        SliceItem sliceItem5 = this.mRowContent.mRange;
        if (sliceItem5 != null) {
            if (this.mRowAction != null) {
                setViewClickable(this.mRootView, true);
            }
            this.mRangeItem = sliceItem5;
            SliceItem findSubtype = SliceQuery.findSubtype(sliceItem5, "int", "range_mode");
            if (findSubtype != null) {
                this.mIsStarRating = findSubtype.getInt() == 2;
            }
            if (!z3) {
                SliceItem findSubtype2 = SliceQuery.findSubtype(this.mRangeItem, "int", "min");
                int i4 = findSubtype2 != null ? findSubtype2.getInt() : 0;
                this.mRangeMinValue = i4;
                SliceItem findSubtype3 = SliceQuery.findSubtype(this.mRangeItem, "int", "max");
                int i5 = this.mIsStarRating ? 5 : 100;
                if (findSubtype3 != null) {
                    i5 = findSubtype3.getInt();
                }
                this.mRangeMaxValue = i5;
                SliceItem findSubtype4 = SliceQuery.findSubtype(this.mRangeItem, "int", "value");
                this.mRangeValue = findSubtype4 != null ? findSubtype4.getInt() - i4 : 0;
                if (this.mHandler == null) {
                    this.mHandler = new Handler();
                }
                if (this.mIsStarRating) {
                    RatingBar ratingBar = new RatingBar(getContext());
                    ((LayerDrawable) ratingBar.getProgressDrawable()).getDrawable(2).setColorFilter(this.mTintColor, PorterDuff.Mode.SRC_IN);
                    ratingBar.setStepSize(1.0f);
                    ratingBar.setNumStars(this.mRangeMaxValue);
                    ratingBar.setRating(this.mRangeValue);
                    ratingBar.setVisibility(0);
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setGravity(17);
                    linearLayout.setVisibility(0);
                    linearLayout.addView(ratingBar, new FrameLayout.LayoutParams(-2, -2));
                    addView(linearLayout, new FrameLayout.LayoutParams(-1, -2));
                    ratingBar.setOnRatingBarChangeListener(this.mRatingBarChangeListener);
                    this.mRangeBar = linearLayout;
                } else {
                    SliceItem findSubtype5 = SliceQuery.findSubtype(this.mRangeItem, "int", "range_mode");
                    boolean z6 = findSubtype5 != null && findSubtype5.getInt() == 1;
                    boolean equals = "action".equals(this.mRangeItem.mFormat);
                    boolean z7 = this.mStartItem == null;
                    if (!equals) {
                        if (z7) {
                            progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
                        } else {
                            progressBar = (ProgressBar) LayoutInflater.from(getContext()).inflate(R.layout.abc_slice_progress_inline_view, (ViewGroup) this, false);
                            RowStyle rowStyle2 = this.mRowStyle;
                            if (rowStyle2 != null) {
                                if (progressBar != null && (i = rowStyle2.mProgressBarInlineWidth) >= 0) {
                                    ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
                                    layoutParams.width = i;
                                    progressBar.setLayoutParams(layoutParams);
                                }
                                RowStyle rowStyle3 = this.mRowStyle;
                                setViewSidePaddings(progressBar, rowStyle3.mProgressBarStartPadding, rowStyle3.mProgressBarEndPadding);
                            }
                        }
                        if (z6) {
                            progressBar.setIndeterminate(true);
                        }
                    } else if (z7) {
                        progressBar = new SeekBar(getContext());
                    } else {
                        progressBar = (SeekBar) LayoutInflater.from(getContext()).inflate(R.layout.abc_slice_seekbar_view, (ViewGroup) this, false);
                        RowStyle rowStyle4 = this.mRowStyle;
                        if (rowStyle4 != null && progressBar != null && (i2 = rowStyle4.mSeekBarInlineWidth) >= 0) {
                            ViewGroup.LayoutParams layoutParams2 = progressBar.getLayoutParams();
                            layoutParams2.width = i2;
                            progressBar.setLayoutParams(layoutParams2);
                        }
                    }
                    Drawable indeterminateDrawable = z6 ? progressBar.getIndeterminateDrawable() : progressBar.getProgressDrawable();
                    int i6 = this.mTintColor;
                    if (i6 != -1 && indeterminateDrawable != null) {
                        indeterminateDrawable.setTint(i6);
                        if (z6) {
                            progressBar.setIndeterminateDrawable(indeterminateDrawable);
                        } else {
                            progressBar.setProgressDrawable(indeterminateDrawable);
                        }
                    }
                    progressBar.setMax(this.mRangeMaxValue - this.mRangeMinValue);
                    progressBar.setProgress(this.mRangeValue);
                    progressBar.setVisibility(0);
                    if (this.mStartItem == null) {
                        addView(progressBar, new FrameLayout.LayoutParams(-1, -2));
                    } else {
                        this.mSubContent.setVisibility(8);
                        this.mContent.addView(progressBar, 1);
                    }
                    this.mRangeBar = progressBar;
                    if (equals) {
                        SliceItem inputRangeThumb = this.mRowContent.getInputRangeThumb();
                        SeekBar seekBar = (SeekBar) this.mRangeBar;
                        if (inputRangeThumb != null && (iconCompat = (IconCompat) inputRangeThumb.mObj) != null && (loadDrawable = iconCompat.loadDrawable(getContext())) != null) {
                            seekBar.setThumb(loadDrawable);
                        }
                        Drawable thumb = seekBar.getThumb();
                        int i7 = this.mTintColor;
                        if (i7 != -1 && thumb != null) {
                            thumb.setTint(i7);
                            seekBar.setThumb(thumb);
                        }
                        seekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
                    }
                }
            }
            if (this.mStartItem == null) {
                return;
            }
        }
        SliceItem sliceItem6 = this.mRowContent.mSelection;
        if (sliceItem6 == null) {
            updateEndItems();
            updateActionSpinner();
            return;
        }
        this.mSelectionItem = sliceItem6;
        if (this.mHandler == null) {
            this.mHandler = new Handler();
        }
        this.mSelectionOptionKeys = new ArrayList();
        this.mSelectionOptionValues = new ArrayList();
        List items = sliceItem6.getSlice().getItems();
        for (int i8 = 0; i8 < items.size(); i8++) {
            SliceItem sliceItem7 = (SliceItem) items.get(i8);
            if (sliceItem7.hasHint("selection_option")) {
                SliceItem findSubtype6 = SliceQuery.findSubtype(sliceItem7, "text", "selection_option_key");
                SliceItem findSubtype7 = SliceQuery.findSubtype(sliceItem7, "text", "selection_option_value");
                if (findSubtype6 != null && findSubtype7 != null) {
                    this.mSelectionOptionKeys.add(((CharSequence) findSubtype6.mObj).toString());
                    this.mSelectionOptionValues.add(findSubtype7.getSanitizedText());
                }
            }
        }
        this.mSelectionSpinner = (Spinner) LayoutInflater.from(getContext()).inflate(R.layout.abc_slice_row_selection, (ViewGroup) this, false);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.abc_slice_row_selection_text, this.mSelectionOptionValues);
        arrayAdapter.setDropDownViewResource(R.layout.abc_slice_row_selection_dropdown_text);
        this.mSelectionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        addView(this.mSelectionSpinner);
        this.mSelectionSpinner.setOnItemSelectedListener(this);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void resetView() {
        this.mRowContent = null;
        this.mLoadingActions.clear();
        resetViewState();
    }

    public final void resetViewState() {
        this.mRootView.setVisibility(0);
        setLayoutDirection(2);
        setViewClickable(this.mRootView, false);
        setViewClickable(this.mContent, false);
        this.mStartContainer.removeAllViews();
        this.mEndContainer.removeAllViews();
        this.mEndContainer.setVisibility(8);
        this.mPrimaryText.setText((CharSequence) null);
        this.mSecondaryText.setText((CharSequence) null);
        this.mLastUpdatedText.setText((CharSequence) null);
        this.mLastUpdatedText.setVisibility(8);
        this.mToggles.clear();
        this.mActions.clear();
        this.mRowAction = null;
        this.mBottomDivider.setVisibility(8);
        this.mActionDivider.setVisibility(8);
        View view = this.mSeeMoreView;
        if (view != null) {
            this.mRootView.removeView(view);
            this.mSeeMoreView = null;
        }
        this.mIsRangeSliding = false;
        this.mRangeItem = null;
        this.mRangeMinValue = 0;
        this.mRangeMaxValue = 0;
        this.mRangeValue = 0;
        this.mLastSentRangeUpdate = 0L;
        this.mHandler = null;
        View view2 = this.mRangeBar;
        if (view2 != null) {
            if (this.mStartItem == null) {
                removeView(view2);
            } else {
                this.mContent.removeView(view2);
            }
            this.mRangeBar = null;
        }
        this.mSubContent.setVisibility(0);
        this.mStartItem = null;
        this.mActionSpinner.setVisibility(8);
        Spinner spinner = this.mSelectionSpinner;
        if (spinner != null) {
            removeView(spinner);
            this.mSelectionSpinner = null;
        }
        this.mSelectionItem = null;
    }

    public final void sendSliderValue() {
        if (this.mRangeItem == null) {
            return;
        }
        try {
            this.mLastSentRangeUpdate = System.currentTimeMillis();
            this.mRangeItem.fireActionInternal(getContext(), new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.RANGE_VALUE", this.mRangeValue));
            if (this.mObserver != null) {
                EventInfo eventInfo = new EventInfo(getMode(), 2, 4, this.mRowIndex);
                eventInfo.state = this.mRangeValue;
                this.mObserver.onSliceAction(eventInfo);
            }
        } catch (PendingIntent.CanceledException e) {
            Log.e("RowView", "PendingIntent for slice cannot be sent", e);
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setAllowTwoLines(boolean z) {
        this.mAllowTwoLines = z;
        if (this.mRowContent != null) {
            populateViews(true);
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setInsets(int i, int i2, int i3, int i4) {
        super.setInsets(i, i2, i3, i4);
        setPadding(i, i2, i3, i4);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setLastUpdated(long j) {
        this.mLastUpdated = j;
        RowContent rowContent = this.mRowContent;
        if (rowContent != null) {
            SliceItem sliceItem = rowContent.mTitleItem;
            addSubtitle(sliceItem != null && TextUtils.isEmpty(sliceItem.getSanitizedText()));
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setLoadingActions(Set set) {
        if (set == null) {
            this.mLoadingActions.clear();
            this.mShowActionSpinner = false;
        } else {
            this.mLoadingActions = set;
        }
        updateEndItems();
        updateActionSpinner();
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setShowLastUpdated(boolean z) {
        this.mShowLastUpdated = z;
        if (this.mRowContent != null) {
            populateViews(true);
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceActions(List list) {
        this.mHeaderActions = list;
        if (this.mRowContent != null) {
            updateEndItems();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0044, code lost:
    
        if (r2 != false) goto L27;
     */
    @Override // androidx.slice.widget.SliceChildView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setSliceItem(SliceContent sliceContent, boolean z, int i, int i2, VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4) {
        boolean z2;
        Uri uri;
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda4;
        RowContent rowContent = this.mRowContent;
        if (rowContent != null && rowContent.isValid()) {
            RowContent rowContent2 = this.mRowContent;
            SliceStructure sliceStructure = rowContent2 != null ? new SliceStructure(rowContent2.mSliceItem) : null;
            SliceStructure sliceStructure2 = new SliceStructure(sliceContent.mSliceItem.getSlice());
            z2 = true;
            boolean z3 = sliceStructure != null && sliceStructure.equals(sliceStructure2);
            if ((sliceStructure == null || (uri = sliceStructure.mUri) == null || !uri.equals(sliceStructure2.mUri)) ? false : true) {
            }
        }
        z2 = false;
        this.mShowActionSpinner = false;
        this.mIsHeader = z;
        this.mRowContent = (RowContent) sliceContent;
        this.mRowIndex = i;
        populateViews(z2);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setStyle(SliceStyle sliceStyle, RowStyle rowStyle) {
        this.mSliceStyle = sliceStyle;
        this.mRowStyle = rowStyle;
        if (sliceStyle != null) {
            setViewSidePaddings(this.mStartContainer, rowStyle.mTitleItemStartPadding, rowStyle.mTitleItemEndPadding);
            LinearLayout linearLayout = this.mContent;
            RowStyle rowStyle2 = this.mRowStyle;
            setViewSidePaddings(linearLayout, rowStyle2.mContentStartPadding, rowStyle2.mContentEndPadding);
            TextView textView = this.mPrimaryText;
            RowStyle rowStyle3 = this.mRowStyle;
            setViewSidePaddings(textView, rowStyle3.mTitleStartPadding, rowStyle3.mTitleEndPadding);
            LinearLayout linearLayout2 = this.mSubContent;
            RowStyle rowStyle4 = this.mRowStyle;
            setViewSidePaddings(linearLayout2, rowStyle4.mSubContentStartPadding, rowStyle4.mSubContentEndPadding);
            LinearLayout linearLayout3 = this.mEndContainer;
            RowStyle rowStyle5 = this.mRowStyle;
            setViewSidePaddings(linearLayout3, rowStyle5.mEndItemStartPadding, rowStyle5.mEndItemEndPadding);
            View view = this.mBottomDivider;
            RowStyle rowStyle6 = this.mRowStyle;
            int i = rowStyle6.mBottomDividerStartPadding;
            int i2 = rowStyle6.mBottomDividerEndPadding;
            boolean z = i < 0 && i2 < 0;
            if (view != null && !z) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (i >= 0) {
                    marginLayoutParams.setMarginStart(i);
                }
                if (i2 >= 0) {
                    marginLayoutParams.setMarginEnd(i2);
                }
                view.setLayoutParams(marginLayoutParams);
            }
            View view2 = this.mActionDivider;
            int i3 = this.mRowStyle.mActionDividerHeight;
            if (view2 != null && i3 >= 0) {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                layoutParams.height = i3;
                view2.setLayoutParams(layoutParams);
            }
            RowStyle rowStyle7 = this.mRowStyle;
            Integer num = rowStyle7.mTintColor;
            if ((num != null ? num.intValue() : rowStyle7.mSliceStyle.mTintColor) != -1) {
                RowStyle rowStyle8 = this.mRowStyle;
                Integer num2 = rowStyle8.mTintColor;
                setTint(num2 != null ? num2.intValue() : rowStyle8.mSliceStyle.mTintColor);
            }
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setTint(int i) {
        this.mTintColor = i;
        if (this.mRowContent != null) {
            populateViews(true);
        }
    }

    public final void setViewClickable(View view, boolean z) {
        view.setOnClickListener(z ? this : null);
        view.setBackground(z ? SliceViewUtil.getDrawable(android.R.attr.selectableItemBackground, getContext()) : null);
        view.setClickable(z);
    }

    public final void updateActionSpinner() {
        this.mActionSpinner.setVisibility(this.mShowActionSpinner ? 0 : 8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0139  */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateEndItems() {
        boolean z;
        SliceActionImpl sliceActionImpl;
        RowContent rowContent = this.mRowContent;
        if (rowContent != null) {
            if (rowContent.mRange == null || this.mStartItem != null) {
                this.mEndContainer.removeAllViews();
                RowContent rowContent2 = this.mRowContent;
                ArrayList arrayList = rowContent2.mEndItems;
                ?? r2 = this.mHeaderActions;
                if (r2 != 0) {
                    arrayList = r2;
                }
                if (rowContent2.mIsHeader && this.mStartItem != null && arrayList.isEmpty() && !this.mRowContent.mShowTitleItems) {
                    arrayList.add(this.mStartItem);
                }
                SliceItem sliceItem = null;
                int i = 0;
                boolean z2 = false;
                boolean z3 = false;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    SliceItem sliceItem2 = arrayList.get(i2) instanceof SliceItem ? (SliceItem) arrayList.get(i2) : ((SliceActionImpl) arrayList.get(i2)).mSliceItem;
                    if (i < 3 && addItem(sliceItem2, this.mTintColor, false)) {
                        if (sliceItem == null && SliceQuery.find(sliceItem2, "action", (String[]) null, (String[]) null) != null) {
                            sliceItem = sliceItem2;
                        }
                        i++;
                        if (i == 1) {
                            z2 = !this.mToggles.isEmpty() && SliceQuery.find(sliceItem2.getSlice(), "image", (String[]) null, (String[]) null) == null;
                            z3 = arrayList.size() == 1 && SliceQuery.find(sliceItem2, "action", (String[]) null, (String[]) null) != null;
                        }
                    }
                }
                int i3 = 8;
                this.mEndContainer.setVisibility(i > 0 ? 0 : 8);
                View view = this.mActionDivider;
                if (this.mRowAction != null && (z2 || (this.mRowContent.mShowActionDivider && z3))) {
                    i3 = 0;
                }
                view.setVisibility(i3);
                SliceItem sliceItem3 = this.mStartItem;
                boolean z4 = (sliceItem3 == null || SliceQuery.find(sliceItem3, "action", (String[]) null, (String[]) null) == null) ? false : true;
                boolean z5 = sliceItem != null;
                if (this.mRowAction != null) {
                    setViewClickable(this.mRootView, true);
                } else if (z5 != z4 && (i == 1 || z4)) {
                    if (!this.mToggles.isEmpty()) {
                        this.mRowAction = (SliceActionImpl) this.mToggles.keySet().iterator().next();
                    } else if (!this.mActions.isEmpty() && this.mActions.size() == 1) {
                        this.mRowAction = ((SliceActionView) this.mActions.valueAt(0)).mSliceAction;
                    }
                    setViewClickable(this.mRootView, true);
                    z = true;
                    sliceActionImpl = this.mRowAction;
                    if (sliceActionImpl != null && !z && this.mLoadingActions.contains(sliceActionImpl.mSliceItem)) {
                        this.mShowActionSpinner = true;
                    }
                    LinearLayout linearLayout = this.mRootView;
                    int i4 = linearLayout.isClickable() ? 0 : 2;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.setImportantForAccessibility(linearLayout, i4);
                }
                z = false;
                sliceActionImpl = this.mRowAction;
                if (sliceActionImpl != null) {
                    this.mShowActionSpinner = true;
                }
                LinearLayout linearLayout2 = this.mRootView;
                if (linearLayout2.isClickable()) {
                }
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setImportantForAccessibility(linearLayout2, i4);
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }
}
