package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PreferenceManager {
    public final Context mContext;
    public SharedPreferences.Editor mEditor;
    public boolean mNoCommit;
    public OnDisplayPreferenceDialogListener mOnDisplayPreferenceDialogListener;
    public OnNavigateToScreenListener mOnNavigateToScreenListener;
    public OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    public PreferenceScreen mPreferenceScreen;
    public final String mSharedPreferencesName;
    public long mNextId = 0;
    public SharedPreferences mSharedPreferences = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnDisplayPreferenceDialogListener {
        void onDisplayPreferenceDialog(DialogPreference dialogPreference);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnNavigateToScreenListener {
        void onNavigateToScreen(PreferenceScreen preferenceScreen);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnPreferenceTreeClickListener {
        boolean onPreferenceTreeClick(Preference preference);
    }

    public PreferenceManager(Context context) {
        this.mContext = context;
        this.mSharedPreferencesName = getDefaultSharedPreferencesName(context);
    }

    public static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    public final SharedPreferences.Editor getEditor() {
        if (!this.mNoCommit) {
            return getSharedPreferences().edit();
        }
        if (this.mEditor == null) {
            this.mEditor = getSharedPreferences().edit();
        }
        return this.mEditor;
    }

    public final SharedPreferences getSharedPreferences() {
        Context context;
        if (this.mSharedPreferences == null && (context = this.mContext) != null) {
            this.mSharedPreferences = context.getSharedPreferences(this.mSharedPreferencesName, 0);
        }
        return this.mSharedPreferences;
    }

    public final PreferenceScreen inflateFromResource(Context context, int i, PreferenceScreen preferenceScreen) {
        this.mNoCommit = true;
        PreferenceInflater preferenceInflater = new PreferenceInflater(context, this);
        XmlResourceParser xml = preferenceInflater.mContext.getResources().getXml(i);
        try {
            PreferenceGroup inflate = preferenceInflater.inflate(xml, preferenceScreen);
            xml.close();
            PreferenceScreen preferenceScreen2 = (PreferenceScreen) inflate;
            preferenceScreen2.onAttachedToHierarchy(this);
            SharedPreferences.Editor editor = this.mEditor;
            if (editor != null) {
                editor.apply();
            }
            this.mNoCommit = false;
            return preferenceScreen2;
        } catch (Throwable th) {
            xml.close();
            throw th;
        }
    }
}
