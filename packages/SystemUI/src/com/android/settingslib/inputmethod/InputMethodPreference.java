package com.android.settingslib.inputmethod;

import android.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.PrimarySwitchPreference;

/* loaded from: classes.dex */
public class InputMethodPreference extends PrimarySwitchPreference implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mDialog;
    public final InputMethodInfo mImi;
    public final int mUserId;

    public interface OnSavePreferenceListener {
    }

    public InputMethodPreference(Context context, InputMethodInfo inputMethodInfo, boolean z, OnSavePreferenceListener onSavePreferenceListener, int i) {
        this(context, inputMethodInfo, inputMethodInfo.loadLabel(context.getPackageManager()), z, onSavePreferenceListener, i);
    }

    public final boolean isTv$2() {
        return (this.mContext.getResources().getConfiguration().uiMode & 15) == 4;
    }

    @Override // com.android.settingslib.PrimarySwitchPreference, com.android.settingslib.RestrictedPreference, com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) throws Resources.NotFoundException {
        super.onBindViewHolder(preferenceViewHolder);
        final CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InputMethodPreference inputMethodPreference = this.f$0;
                    CompoundButton compoundButton2 = compoundButton;
                    int i = InputMethodPreference.$r8$clinit;
                    inputMethodPreference.getClass();
                    if (compoundButton2.isEnabled()) {
                        CompoundButton compoundButton3 = inputMethodPreference.mSwitch;
                        boolean z = false;
                        boolean z2 = !(compoundButton3 != null && inputMethodPreference.mChecked);
                        if (compoundButton3 != null && inputMethodPreference.mChecked) {
                            z = true;
                        }
                        compoundButton2.setChecked(z);
                        inputMethodPreference.callChangeListener(Boolean.valueOf(z2));
                    }
                }
            });
        }
        ImageView imageView = (ImageView) preferenceViewHolder.itemView.findViewById(R.id.icon);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.secondary_app_icon_size);
        if (imageView == null || dimensionPixelSize <= 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        imageView.setLayoutParams(layoutParams);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mSwitch != null && this.mChecked) {
            setChecked(false);
            throw null;
        }
        if (this.mImi.isSystem()) {
            if (this.mImi.getServiceInfo().directBootAware || isTv$2()) {
                setChecked(true);
                throw null;
            }
            if (!isTv$2()) {
                showDirectBootWarnDialog();
            }
            return false;
        }
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        Context context = this.mContext;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(R.string.dialog_alert_title);
        builder.setMessage(context.getString(com.android.systemui.R.string.ime_security_warning, this.mImi.getServiceInfo().applicationInfo.loadLabel(context.getPackageManager())));
        builder.setPositiveButton(R.string.ok, new InputMethodPreference$$ExternalSyntheticLambda0(this, 2));
        builder.setNegativeButton(R.string.cancel, new InputMethodPreference$$ExternalSyntheticLambda0(this, 3));
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                InputMethodPreference inputMethodPreference = this.f$0;
                int i = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setChecked(false);
                throw null;
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        this.mDialog = alertDialogCreate;
        alertDialogCreate.show();
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final void onPreferenceClick(Preference preference) {
        Context context = this.mContext;
        try {
            Intent intent = this.mIntent;
            if (intent != null) {
                context.startActivityAsUser(intent, UserHandle.of(this.mUserId));
            }
        } catch (ActivityNotFoundException e) {
            Log.d("InputMethodPreference", "IME's Settings Activity Not Found", e);
            Toast.makeText(context, context.getString(com.android.systemui.R.string.failed_to_open_app_settings_toast, this.mImi.loadLabel(context.getPackageManager())), 1).show();
        }
    }

    public final void showDirectBootWarnDialog() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        Context context = this.mContext;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage(context.getText(com.android.systemui.R.string.direct_boot_unaware_dialog_message));
        builder.setPositiveButton(R.string.ok, new InputMethodPreference$$ExternalSyntheticLambda0(this, 0));
        builder.setNegativeButton(R.string.cancel, new InputMethodPreference$$ExternalSyntheticLambda0(this, 1));
        AlertDialog alertDialogCreate = builder.create();
        this.mDialog = alertDialogCreate;
        alertDialogCreate.show();
    }

    public InputMethodPreference(Context context, InputMethodInfo inputMethodInfo, CharSequence charSequence, boolean z, OnSavePreferenceListener onSavePreferenceListener, int i) {
        super(context);
        this.mDialog = null;
        this.mPersistent = false;
        this.mImi = inputMethodInfo;
        setKey(inputMethodInfo.getId());
        setTitle(charSequence);
        String settingsActivity = inputMethodInfo.getSettingsActivity();
        if (TextUtils.isEmpty(settingsActivity)) {
            this.mIntent = null;
        } else {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName(inputMethodInfo.getPackageName(), settingsActivity);
            this.mIntent = intent;
        }
        context = i != UserHandle.myUserId() ? this.mContext.createContextAsUser(UserHandle.of(i), 0) : context;
        Object obj = InputMethodSettingValuesWrapper.sInstanceMapLock;
        int userId = context.getUserId();
        synchronized (InputMethodSettingValuesWrapper.sInstanceMapLock) {
            try {
                SparseArray sparseArray = InputMethodSettingValuesWrapper.sInstanceMap;
                if (sparseArray.size() == 0) {
                    sparseArray.put(userId, new InputMethodSettingValuesWrapper(context));
                } else if (sparseArray.indexOfKey(userId) >= 0) {
                } else {
                    sparseArray.put(context.getUserId(), new InputMethodSettingValuesWrapper(context));
                }
            } finally {
            }
        }
        this.mUserId = i;
        if (inputMethodInfo.isSystem()) {
            int i2 = InputMethodAndSubtypeUtil.$r8$clinit;
            if (!inputMethodInfo.isAuxiliaryIme()) {
                int subtypeCount = inputMethodInfo.getSubtypeCount();
                for (int i3 = 0; i3 < subtypeCount; i3++) {
                    InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i3);
                    if ("keyboard".equalsIgnoreCase(subtypeAt.getMode()) && subtypeAt.isAsciiCapable()) {
                        break;
                    }
                }
            }
        }
        this.mOnClickListener = this;
        this.mOnChangeListener = this;
    }
}
