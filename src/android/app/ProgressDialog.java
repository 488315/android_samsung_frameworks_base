package android.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.R;
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
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mThemeIsDeviceDefault = typedValue.data != 0;
        initFormats();
    }

    public ProgressDialog(Context context, int i) {
        super(context, i);
        this.mProgressStyle = 0;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mThemeIsDeviceDefault = typedValue.data != 0;
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

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2) {
        return show(context, charSequence, charSequence2, false);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return show(context, charSequence, charSequence2, z, false, null);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2) {
        return show(context, charSequence, charSequence2, z, z2, null);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2, DialogInterface.OnCancelListener onCancelListener) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(charSequence);
        progressDialog.setMessage(charSequence2);
        progressDialog.setIndeterminate(z);
        progressDialog.setCancelable(z2);
        progressDialog.setOnCancelListener(onCancelListener);
        progressDialog.show();
        return progressDialog;
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        View viewInflate;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        int i = this.mProgressStyle;
        if (i == 1) {
            this.mViewUpdateHandler = new Handler() { // from class: android.app.ProgressDialog.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    int progress = ProgressDialog.this.mProgress.getProgress();
                    int max = ProgressDialog.this.mProgress.getMax();
                    if (ProgressDialog.this.mProgressNumberFormat != null) {
                        String str = ProgressDialog.this.mProgressNumberFormat;
                        if (ProgressDialog.this.mProgressNumber.isLayoutRtl()) {
                            ProgressDialog.this.mProgressNumber.lambda$setTextAsync$0(String.format(str, Integer.valueOf(max), Integer.valueOf(progress)));
                        } else {
                            ProgressDialog.this.mProgressNumber.lambda$setTextAsync$0(String.format(str, Integer.valueOf(progress), Integer.valueOf(max)));
                        }
                    } else {
                        ProgressDialog.this.mProgressNumber.lambda$setTextAsync$0("");
                    }
                    if (ProgressDialog.this.mProgressPercentFormat != null) {
                        SpannableString spannableString = new SpannableString(ProgressDialog.this.mProgressPercentFormat.format(progress / max));
                        spannableString.setSpan(new StyleSpan(!ProgressDialog.this.mThemeIsDeviceDefault ? 1 : 0), 0, spannableString.length(), 33);
                        ProgressDialog.this.mProgressPercent.lambda$setTextAsync$0(spannableString);
                        return;
                    }
                    ProgressDialog.this.mProgressPercent.lambda$setTextAsync$0("");
                }
            };
            View viewInflate2 = layoutInflaterFrom.inflate(typedArrayObtainStyledAttributes.getResourceId(13, R.layout.alert_dialog_progress), (ViewGroup) null);
            this.mProgress = (ProgressBar) viewInflate2.findViewById(16908301);
            this.mProgressNumber = (TextView) viewInflate2.findViewById(R.id.progress_number);
            this.mProgressPercent = (TextView) viewInflate2.findViewById(R.id.progress_percent);
            if (this.mThemeIsDeviceDefault) {
                this.mMessageView = (TextView) viewInflate2.findViewById(16908299);
            }
            setView(viewInflate2);
        } else if (i == 1000 && this.mThemeIsDeviceDefault) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
            setTitle((CharSequence) null);
            getWindow().setBackgroundDrawableResource(typedValue.data == 0 ? R.drawable.tw_dialog_circle_progress_background_material_shape : R.drawable.tw_dialog_circle_progress_background_material_shape_dark);
            View viewInflate3 = layoutInflaterFrom.inflate(R.layout.tw_progress_dialog_circle_material, (ViewGroup) null);
            this.mProgress = (ProgressBar) viewInflate3.findViewById(16908301);
            this.mMessageView = (TextView) viewInflate3.findViewById(16908299);
            setView(viewInflate3);
        } else {
            if (this instanceof BootProgressDialog) {
                viewInflate = layoutInflaterFrom.inflate(R.layout.boot_progress_dialog, (ViewGroup) null);
            } else {
                viewInflate = layoutInflaterFrom.inflate(typedArrayObtainStyledAttributes.getResourceId(18, R.layout.progress_dialog), (ViewGroup) null);
            }
            this.mProgress = (ProgressBar) viewInflate.findViewById(16908301);
            this.mMessageView = (TextView) viewInflate.findViewById(16908299);
            setView(viewInflate);
        }
        typedArrayObtainStyledAttributes.recycle();
        int i2 = this.mMax;
        if (i2 > 0) {
            setMax(i2);
        }
        int i3 = this.mProgressVal;
        if (i3 > 0) {
            setProgress(i3);
        }
        int i4 = this.mSecondaryProgressVal;
        if (i4 > 0) {
            setSecondaryProgress(i4);
        }
        int i5 = this.mIncrementBy;
        if (i5 > 0) {
            incrementProgressBy(i5);
        }
        int i6 = this.mIncrementSecondaryBy;
        if (i6 > 0) {
            incrementSecondaryProgressBy(i6);
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
        super.onCreate(bundle);
        if (this.mProgressStyle == 1000) {
            getWindow().setLayout(getContext().getResources().getDimensionPixelSize(R.dimen.tw_progress_circle_dialog_size), getContext().getResources().getDimensionPixelSize(R.dimen.tw_progress_circle_dialog_size));
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

    public void setProgress(int i) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(i);
            onProgressChanged();
        } else {
            this.mProgressVal = i;
        }
    }

    public void setSecondaryProgress(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setSecondaryProgress(i);
            onProgressChanged();
        } else {
            this.mSecondaryProgressVal = i;
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

    public void setMax(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setMax(i);
            onProgressChanged();
        } else {
            this.mMax = i;
        }
    }

    public void incrementProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementProgressBy(i);
            onProgressChanged();
        } else {
            this.mIncrementBy += i;
        }
    }

    public void incrementSecondaryProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementSecondaryProgressBy(i);
            onProgressChanged();
        } else {
            this.mIncrementSecondaryBy += i;
        }
    }

    public void setProgressDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setProgressDrawable(drawable);
        } else {
            this.mProgressDrawable = drawable;
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(drawable);
        } else {
            this.mIndeterminateDrawable = drawable;
        }
    }

    public void setIndeterminate(boolean z) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminate(z);
        } else {
            this.mIndeterminate = z;
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
    public void setMessage(CharSequence charSequence) {
        TextView textView;
        TextView textView2;
        if (this.mProgress != null) {
            int i = this.mProgressStyle;
            if (i == 1) {
                if (this.mThemeIsDeviceDefault && (textView2 = this.mMessageView) != null) {
                    textView2.lambda$setTextAsync$0(charSequence);
                    this.mMessageView.setVisibility(charSequence.equals("") ? 8 : 0);
                    return;
                } else {
                    super.setMessage(charSequence);
                    return;
                }
            }
            if (this.mThemeIsDeviceDefault && (textView = this.mMessageView) != null && i == 1000) {
                textView.lambda$setTextAsync$0(charSequence);
                this.mMessageView.setVisibility(charSequence.equals("") ? 8 : 0);
                return;
            } else {
                TextView textView3 = this.mMessageView;
                if (textView3 != null) {
                    textView3.lambda$setTextAsync$0(charSequence);
                    return;
                }
                return;
            }
        }
        this.mMessage = charSequence;
    }

    public void setProgressStyle(int i) {
        this.mProgressStyle = i;
    }

    public void setProgressNumberFormat(String str) {
        this.mProgressNumberFormat = str;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat numberFormat) {
        this.mProgressPercentFormat = numberFormat;
        onProgressChanged();
    }

    private void onProgressChanged() {
        Handler handler;
        if (this.mProgressStyle != 1 || (handler = this.mViewUpdateHandler) == null || handler.hasMessages(0)) {
            return;
        }
        this.mViewUpdateHandler.sendEmptyMessage(0);
    }

    public int getCurrentProgressStyle() {
        return this.mProgressStyle;
    }
}
