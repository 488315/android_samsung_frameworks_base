package android.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public class RingtonePreference extends Preference implements PreferenceManager.OnActivityResultListener {
    private static final String TAG = "RingtonePreference";
    private int mRequestCode;
    private int mRingtoneType;
    private boolean mShowDefault;
    private boolean mShowSilent;

    public RingtonePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RingtonePreference, i, i2);
        this.mRingtoneType = obtainStyledAttributes.getInt(0, 1);
        this.mShowDefault = obtainStyledAttributes.getBoolean(1, true);
        this.mShowSilent = obtainStyledAttributes.getBoolean(2, true);
        obtainStyledAttributes.recycle();
    }

    public RingtonePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RingtonePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842899);
    }

    public RingtonePreference(Context context) {
        this(context, null);
    }

    public int getRingtoneType() {
        return this.mRingtoneType;
    }

    public void setRingtoneType(int i) {
        this.mRingtoneType = i;
    }

    public boolean getShowDefault() {
        return this.mShowDefault;
    }

    public void setShowDefault(boolean z) {
        this.mShowDefault = z;
    }

    public boolean getShowSilent() {
        return this.mShowSilent;
    }

    public void setShowSilent(boolean z) {
        this.mShowSilent = z;
    }

    @Override // android.preference.Preference
    protected void onClick() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        onPrepareRingtonePickerIntent(intent);
        PreferenceFragment fragment = getPreferenceManager().getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, this.mRequestCode);
        } else {
            getPreferenceManager().getActivity().startActivityForResult(intent, this.mRequestCode);
        }
    }

    protected void onPrepareRingtonePickerIntent(Intent intent) {
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, onRestoreRingtone());
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, this.mShowDefault);
        if (this.mShowDefault) {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(getRingtoneType()));
        }
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, this.mShowSilent);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, this.mRingtoneType);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getTitle());
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS, 64);
    }

    protected void onSaveRingtone(Uri uri) {
        persistString(uri != null ? uri.toString() : "");
    }

    protected Uri onRestoreRingtone() {
        String persistedString = getPersistedString(null);
        if (TextUtils.isEmpty(persistedString)) {
            return null;
        }
        return Uri.parse(persistedString);
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        String str = (String) obj;
        if (z || TextUtils.isEmpty(str)) {
            return;
        }
        onSaveRingtone(Uri.parse(str));
    }

    @Override // android.preference.Preference
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        preferenceManager.registerOnActivityResultListener(this);
        this.mRequestCode = preferenceManager.getNextRequestCode();
    }

    @Override // android.preference.PreferenceManager.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != this.mRequestCode) {
            return false;
        }
        if (intent == null) {
            return true;
        }
        Uri uri = (Uri) intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI, Uri.class);
        if (!callChangeListener(uri != null ? uri.toString() : "")) {
            return true;
        }
        onSaveRingtone(uri);
        return true;
    }
}
