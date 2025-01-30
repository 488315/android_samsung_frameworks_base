package android.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Message;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.C4337R;
import java.text.NumberFormat;

@Deprecated
/* loaded from: classes.dex */
public class ProgressDialog extends AlertDialog {
    public static final int SEM_STYLE_CIRCLE = 1000;
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    private boolean mHasStarted;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private int mMax;
    private CharSequence mMessage;
    private TextView mMessageView;
    private ProgressBar mProgress;
    private Drawable mProgressDrawable;
    private TextView mProgressNumber;
    private String mProgressNumberFormat;
    private TextView mProgressPercent;
    private NumberFormat mProgressPercentFormat;
    private int mProgressStyle;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private boolean mThemeIsDeviceDefault;
    private Handler mViewUpdateHandler;

    public ProgressDialog(Context context) {
        super(context);
        this.mProgressStyle = 0;
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
        this.mThemeIsDeviceDefault = outValue.data != 0;
        initFormats();
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        this.mProgressStyle = 0;
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
        this.mThemeIsDeviceDefault = outValue.data != 0;
        initFormats();
    }

    private void initFormats() {
        if (this.mThemeIsDeviceDefault) {
            this.mProgressNumberFormat = "%1d/%1d";
        } else {
            this.mProgressNumberFormat = "%1d/%2d";
        }
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        this.mProgressPercentFormat = percentInstance;
        percentInstance.setMaximumFractionDigits(0);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        View view;
        int i;
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        TypedArray a = this.mContext.obtainStyledAttributes(null, C4337R.styleable.AlertDialog, 16842845, 0);
        int i2 = this.mProgressStyle;
        if (i2 == 1) {
            this.mViewUpdateHandler = new Handler() { // from class: android.app.ProgressDialog.1
                @Override // android.p009os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    int progress = ProgressDialog.this.mProgress.getProgress();
                    int max = ProgressDialog.this.mProgress.getMax();
                    if (ProgressDialog.this.mProgressNumberFormat != null) {
                        String str = ProgressDialog.this.mProgressNumberFormat;
                        if (ProgressDialog.this.mProgressNumber.isLayoutRtl()) {
                            ProgressDialog.this.mProgressNumber.setText(String.format(str, Integer.valueOf(max), Integer.valueOf(progress)));
                        } else {
                            ProgressDialog.this.mProgressNumber.setText(String.format(str, Integer.valueOf(progress), Integer.valueOf(max)));
                        }
                    } else {
                        ProgressDialog.this.mProgressNumber.setText("");
                    }
                    if (ProgressDialog.this.mProgressPercentFormat != null) {
                        SpannableString spannableString = new SpannableString(ProgressDialog.this.mProgressPercentFormat.format(progress / max));
                        spannableString.setSpan(new StyleSpan(!ProgressDialog.this.mThemeIsDeviceDefault ? 1 : 0), 0, spannableString.length(), 33);
                        ProgressDialog.this.mProgressPercent.setText(spannableString);
                        return;
                    }
                    ProgressDialog.this.mProgressPercent.setText("");
                }
            };
            View view2 = inflater.inflate(a.getResourceId(13, C4337R.layout.alert_dialog_progress), (ViewGroup) null);
            this.mProgress = (ProgressBar) view2.findViewById(16908301);
            this.mProgressNumber = (TextView) view2.findViewById(C4337R.id.progress_number);
            this.mProgressPercent = (TextView) view2.findViewById(C4337R.id.progress_percent);
            if (this.mThemeIsDeviceDefault) {
                this.mMessageView = (TextView) view2.findViewById(16908299);
            }
            setView(view2);
        } else if (i2 == 1000 && this.mThemeIsDeviceDefault) {
            TypedValue colorValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefaultDark, colorValue, true);
            setTitle((CharSequence) null);
            Window window = getWindow();
            if (colorValue.data == 0) {
                i = C4337R.drawable.tw_dialog_circle_progress_background_material_shape;
            } else {
                i = C4337R.drawable.tw_dialog_circle_progress_background_material_shape_dark;
            }
            window.setBackgroundDrawableResource(i);
            View view3 = inflater.inflate(C4337R.layout.tw_progress_dialog_circle_material, (ViewGroup) null);
            this.mProgress = (ProgressBar) view3.findViewById(16908301);
            this.mMessageView = (TextView) view3.findViewById(16908299);
            setView(view3);
        } else {
            if (this instanceof BootProgressDialog) {
                view = inflater.inflate(C4337R.layout.boot_progress_dialog, (ViewGroup) null);
            } else {
                view = inflater.inflate(a.getResourceId(18, C4337R.layout.progress_dialog), (ViewGroup) null);
            }
            this.mProgress = (ProgressBar) view.findViewById(16908301);
            this.mMessageView = (TextView) view.findViewById(16908299);
            setView(view);
        }
        a.recycle();
        int i3 = this.mMax;
        if (i3 > 0) {
            setMax(i3);
        }
        int i4 = this.mProgressVal;
        if (i4 > 0) {
            setProgress(i4);
        }
        int i5 = this.mSecondaryProgressVal;
        if (i5 > 0) {
            setSecondaryProgress(i5);
        }
        int i6 = this.mIncrementBy;
        if (i6 > 0) {
            incrementProgressBy(i6);
        }
        int i7 = this.mIncrementSecondaryBy;
        if (i7 > 0) {
            incrementSecondaryProgressBy(i7);
        }
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            setProgressDrawable(drawable);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            setIndeterminateDrawable(drawable2);
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            setMessage(charSequence);
        }
        setIndeterminate(this.mIndeterminate);
        onProgressChanged();
        super.onCreate(savedInstanceState);
        if (this.mProgressStyle == 1000) {
            getWindow().setLayout(getContext().getResources().getDimensionPixelSize(C4337R.dimen.tw_progress_circle_dialog_size), getContext().getResources().getDimensionPixelSize(C4337R.dimen.tw_progress_circle_dialog_size));
        }
    }

    @Override // android.app.Dialog
    public void onStart() {
        super.onStart();
        this.mHasStarted = true;
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        this.mHasStarted = false;
    }

    public void setProgress(int value) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(value);
            onProgressChanged();
        } else {
            this.mProgressVal = value;
        }
    }

    public void setSecondaryProgress(int secondaryProgress) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setSecondaryProgress(secondaryProgress);
            onProgressChanged();
        } else {
            this.mSecondaryProgressVal = secondaryProgress;
        }
    }

    public int getProgress() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getProgress();
        }
        return this.mProgressVal;
    }

    public int getSecondaryProgress() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getSecondaryProgress();
        }
        return this.mSecondaryProgressVal;
    }

    public int getMax() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getMax();
        }
        return this.mMax;
    }

    public void setMax(int max) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setMax(max);
            onProgressChanged();
        } else {
            this.mMax = max;
        }
    }

    public void incrementProgressBy(int diff) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementProgressBy(diff);
            onProgressChanged();
        } else {
            this.mIncrementBy += diff;
        }
    }

    public void incrementSecondaryProgressBy(int diff) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementSecondaryProgressBy(diff);
            onProgressChanged();
        } else {
            this.mIncrementSecondaryBy += diff;
        }
    }

    public void setProgressDrawable(Drawable d) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setProgressDrawable(d);
        } else {
            this.mProgressDrawable = d;
        }
    }

    public void setIndeterminateDrawable(Drawable d) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(d);
        } else {
            this.mIndeterminateDrawable = d;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminate(indeterminate);
        } else {
            this.mIndeterminate = indeterminate;
        }
    }

    public boolean isIndeterminate() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.isIndeterminate();
        }
        return this.mIndeterminate;
    }

    @Override // android.app.AlertDialog
    public void setMessage(CharSequence message) {
        TextView textView;
        TextView textView2;
        if (this.mProgress != null) {
            int i = this.mProgressStyle;
            if (i == 1) {
                if (this.mThemeIsDeviceDefault && (textView2 = this.mMessageView) != null) {
                    textView2.setText(message);
                    this.mMessageView.setVisibility(message.equals("") ? 8 : 0);
                    return;
                } else {
                    super.setMessage(message);
                    return;
                }
            }
            if (this.mThemeIsDeviceDefault && (textView = this.mMessageView) != null && i == 1000) {
                textView.setText(message);
                this.mMessageView.setVisibility(message.equals("") ? 8 : 0);
                return;
            } else {
                TextView textView3 = this.mMessageView;
                if (textView3 != null) {
                    textView3.setText(message);
                    return;
                }
                return;
            }
        }
        this.mMessage = message;
    }

    public void setProgressStyle(int style) {
        this.mProgressStyle = style;
    }

    public void setProgressNumberFormat(String format) {
        this.mProgressNumberFormat = format;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat format) {
        this.mProgressPercentFormat = format;
        onProgressChanged();
    }

    private void onProgressChanged() {
        Handler handler;
        if (this.mProgressStyle == 1 && (handler = this.mViewUpdateHandler) != null && !handler.hasMessages(0)) {
            this.mViewUpdateHandler.sendEmptyMessage(0);
        }
    }

    public int getCurrentProgressStyle() {
        return this.mProgressStyle;
    }
}
