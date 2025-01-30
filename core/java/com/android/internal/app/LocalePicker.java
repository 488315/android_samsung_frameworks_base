package com.android.internal.app;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.app.ListFragment;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.p009os.Bundle;
import android.p009os.LocaleList;
import android.p009os.RemoteException;
import android.provider.Settings;
import android.sysprop.LocalizationProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.C4337R;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes4.dex */
public class LocalePicker extends ListFragment {
    private static final boolean DEBUG = false;
    private static final String TAG = "LocalePicker";
    private static final String[] pseudoLocales = {"en-XA", "ar-XB"};
    LocaleSelectionListener mListener;

    public interface LocaleSelectionListener {
        void onLocaleSelected(Locale locale);
    }

    public static class LocaleInfo implements Comparable<LocaleInfo> {
        static final Collator sCollator = Collator.getInstance();
        String label;
        final Locale locale;

        public LocaleInfo(String label, Locale locale) {
            this.label = label;
            this.locale = locale;
        }

        public String getLabel() {
            return this.label;
        }

        public Locale getLocale() {
            return this.locale;
        }

        public String toString() {
            return this.label;
        }

        @Override // java.lang.Comparable
        public int compareTo(LocaleInfo another) {
            return sCollator.compare(this.label, another.label);
        }
    }

    public static String[] getSystemAssetLocales() {
        return Resources.getSystem().getAssets().getLocales();
    }

    public static String[] getSupportedLocales(Context context) {
        if (context == null) {
            return new String[0];
        }
        String[] allLocales = context.getResources().getStringArray(C4337R.array.supported_locales);
        Predicate<String> localeFilter = getLocaleFilter();
        if (localeFilter == null) {
            return allLocales;
        }
        List<String> result = new ArrayList<>(allLocales.length);
        for (String locale : allLocales) {
            if (localeFilter.test(locale)) {
                result.add(locale);
            }
        }
        int localeCount = result.size();
        return localeCount == allLocales.length ? allLocales : (String[]) result.toArray(new String[localeCount]);
    }

    public static String[] getSpecificCustomerSupportedLocales(Context context) {
        return context.getResources().getStringArray(C4337R.array.specific_customer_supported_locales);
    }

    public static String[] getPseudoLocales() {
        return pseudoLocales;
    }

    public static String[] getDIDLocale(Context context) {
        return context.getResources().getStringArray(C4337R.array.sem_did_supported_locale);
    }

    private static Predicate<String> getLocaleFilter() {
        try {
            return (Predicate) LocalizationProperties.locale_filter().map(new Function() { // from class: com.android.internal.app.LocalePicker$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Predicate asPredicate;
                    asPredicate = Pattern.compile((String) obj).asPredicate();
                    return asPredicate;
                }
            }).orElse(null);
        } catch (SecurityException e) {
            Log.m97e(TAG, "Failed to read locale filter.", e);
            return null;
        } catch (PatternSyntaxException e2) {
            Log.m96e(TAG, "Bad locale filter format (\"" + e2.getPattern() + "\"), skipping.");
            return null;
        }
    }

    public static List<LocaleInfo> getAllAssetLocales(Context context, boolean isInDeveloperMode) {
        return LocaleStore.getAllLocaleInfos(context);
    }

    public static ArrayAdapter<LocaleInfo> constructAdapter(Context context) {
        return constructAdapter(context, C4337R.layout.locale_picker_item, C4337R.id.locale);
    }

    public static ArrayAdapter<LocaleInfo> constructAdapter(Context context, final int layoutId, final int fieldId) {
        boolean isInDeveloperMode = Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) != 0;
        List<LocaleInfo> localeInfos = getAllAssetLocales(context, isInDeveloperMode);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ArrayAdapter<LocaleInfo>(context, layoutId, fieldId, localeInfos) { // from class: com.android.internal.app.LocalePicker.1
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                TextView text;
                if (convertView == null) {
                    view = inflater.inflate(layoutId, parent, false);
                    text = (TextView) view.findViewById(fieldId);
                    view.setTag(text);
                } else {
                    view = convertView;
                    text = (TextView) view.getTag();
                }
                LocaleInfo item = getItem(position);
                text.setText(item.toString());
                text.setTextLocale(item.getLocale());
                return view;
            }
        };
    }

    private static String toTitleCase(String s) {
        if (s.length() == 0) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static String getDisplayName(Locale l, String[] specialLocaleCodes, String[] specialLocaleNames) {
        String code = l.toString();
        for (int i = 0; i < specialLocaleCodes.length; i++) {
            if (specialLocaleCodes[i].equals(code)) {
                return specialLocaleNames[i];
            }
        }
        return l.getDisplayName(l);
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<LocaleInfo> adapter = constructAdapter(getActivity());
        setListAdapter(adapter);
    }

    public void setLocaleSelectionListener(LocaleSelectionListener listener) {
        this.mListener = listener;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        getListView().requestFocus();
    }

    @Override // android.app.ListFragment
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (this.mListener != null) {
            Locale locale = ((LocaleInfo) getListAdapter().getItem(position)).locale;
            this.mListener.onLocaleSelected(locale);
        }
    }

    public static void updateLocale(Locale locale) {
        updateLocales(new LocaleList(locale));
    }

    public static void updateLocales(LocaleList locales) {
        if (locales != null) {
            locales = removeExcludedLocales(locales);
        }
        try {
            IActivityManager am = ActivityManager.getService();
            Configuration config = new Configuration();
            config.setLocales(locales);
            config.userSetLocale = true;
            am.updatePersistentConfigurationAndLocaleOverlays(config, ActivityThread.currentOpPackageName(), null, locales);
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (RemoteException e) {
        }
    }

    private static LocaleList removeExcludedLocales(LocaleList locales) {
        Predicate<String> localeFilter = getLocaleFilter();
        if (localeFilter == null) {
            return locales;
        }
        int localeCount = locales.size();
        ArrayList<Locale> filteredLocales = new ArrayList<>(localeCount);
        for (int i = 0; i < localeCount; i++) {
            Locale locale = locales.get(i);
            if (localeFilter.test(locale.toString())) {
                filteredLocales.add(locale);
            }
        }
        int i2 = filteredLocales.size();
        return localeCount == i2 ? locales : new LocaleList((Locale[]) filteredLocales.toArray(new Locale[0]));
    }

    public static LocaleList getLocales() {
        try {
            return ActivityManager.getService().getGlobalConfiguration().getLocales();
        } catch (RemoteException e) {
            return LocaleList.getDefault();
        }
    }
}
