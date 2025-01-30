package androidx.preference;

import android.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.DialogPreference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class PreferenceDialogFragmentCompat extends DialogFragment implements DialogInterface.OnClickListener {
    public BitmapDrawable mDialogIcon;
    public int mDialogLayoutRes;
    public CharSequence mDialogMessage;
    public CharSequence mDialogTitle;
    public CharSequence mNegativeButtonText;
    public CharSequence mPositiveButtonText;
    public DialogPreference mPreference;
    public int mWhichButtonClicked;

    public final DialogPreference getPreference() {
        if (this.mPreference == null) {
            this.mPreference = (DialogPreference) ((DialogPreference.TargetFragment) getTargetFragment(true)).findPreference(requireArguments().getString("key"));
        }
        return this.mPreference;
    }

    public void onBindDialogView(View view) {
        int i;
        View findViewById = view.findViewById(R.id.message);
        if (findViewById != null) {
            CharSequence charSequence = this.mDialogMessage;
            if (TextUtils.isEmpty(charSequence)) {
                i = 8;
            } else {
                if (findViewById instanceof TextView) {
                    ((TextView) findViewById).setText(charSequence);
                }
                i = 0;
            }
            if (findViewById.getVisibility() != i) {
                findViewById.setVisibility(i);
            }
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.mWhichButtonClicked = i;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LifecycleOwner targetFragment = getTargetFragment(true);
        if (!(targetFragment instanceof DialogPreference.TargetFragment)) {
            throw new IllegalStateException("Target fragment must implement TargetFragment interface");
        }
        DialogPreference.TargetFragment targetFragment2 = (DialogPreference.TargetFragment) targetFragment;
        String string = requireArguments().getString("key");
        if (bundle != null) {
            this.mDialogTitle = bundle.getCharSequence("PreferenceDialogFragment.title");
            this.mPositiveButtonText = bundle.getCharSequence("PreferenceDialogFragment.positiveText");
            this.mNegativeButtonText = bundle.getCharSequence("PreferenceDialogFragment.negativeText");
            this.mDialogMessage = bundle.getCharSequence("PreferenceDialogFragment.message");
            this.mDialogLayoutRes = bundle.getInt("PreferenceDialogFragment.layout", 0);
            Bitmap bitmap = (Bitmap) bundle.getParcelable("PreferenceDialogFragment.icon");
            if (bitmap != null) {
                this.mDialogIcon = new BitmapDrawable(getResources(), bitmap);
                return;
            }
            return;
        }
        DialogPreference dialogPreference = (DialogPreference) targetFragment2.findPreference(string);
        this.mPreference = dialogPreference;
        this.mDialogTitle = dialogPreference.mDialogTitle;
        this.mPositiveButtonText = dialogPreference.mPositiveButtonText;
        this.mNegativeButtonText = dialogPreference.mNegativeButtonText;
        this.mDialogMessage = dialogPreference.mDialogMessage;
        this.mDialogLayoutRes = dialogPreference.mDialogLayoutResId;
        Drawable drawable = dialogPreference.mDialogIcon;
        if (drawable == null || (drawable instanceof BitmapDrawable)) {
            this.mDialogIcon = (BitmapDrawable) drawable;
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        this.mDialogIcon = new BitmapDrawable(getResources(), createBitmap);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog() {
        this.mWhichButtonClicked = -2;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        CharSequence charSequence = this.mDialogTitle;
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mTitle = charSequence;
        alertParams.mIcon = this.mDialogIcon;
        builder.setPositiveButton(this.mPositiveButtonText, this);
        alertParams.mNegativeButtonText = this.mNegativeButtonText;
        alertParams.mNegativeButtonListener = this;
        requireContext();
        int i = this.mDialogLayoutRes;
        View inflate = i != 0 ? getLayoutInflater().inflate(i, (ViewGroup) null) : null;
        if (inflate != null) {
            onBindDialogView(inflate);
            builder.setView(inflate);
        } else {
            alertParams.mMessage = this.mDialogMessage;
        }
        onPrepareDialogBuilder(builder);
        AlertDialog create = builder.create();
        if (this instanceof EditTextPreferenceDialogFragmentCompat) {
            create.getWindow().getDecorView().getWindowInsetsController().show(WindowInsets.Type.ime());
        }
        return create;
    }

    public abstract void onDialogClosed(boolean z);

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        onDialogClosed(this.mWhichButtonClicked == -1);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putCharSequence("PreferenceDialogFragment.title", this.mDialogTitle);
        bundle.putCharSequence("PreferenceDialogFragment.positiveText", this.mPositiveButtonText);
        bundle.putCharSequence("PreferenceDialogFragment.negativeText", this.mNegativeButtonText);
        bundle.putCharSequence("PreferenceDialogFragment.message", this.mDialogMessage);
        bundle.putInt("PreferenceDialogFragment.layout", this.mDialogLayoutRes);
        BitmapDrawable bitmapDrawable = this.mDialogIcon;
        if (bitmapDrawable != null) {
            bundle.putParcelable("PreferenceDialogFragment.icon", bitmapDrawable.getBitmap());
        }
    }

    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
    }
}
