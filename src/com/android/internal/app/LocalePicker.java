package com.android.internal.app;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.app.ListFragment;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.RemoteException;
import android.provider.Settings;
import android.sysprop.LocalizationProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes5.dex */
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

        public LocaleInfo(String str, Locale locale) {
            this.label = str;
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
        public int compareTo(LocaleInfo localeInfo) {
            return sCollator.compare(this.label, localeInfo.label);
        }
    }

    public static String[] getSystemAssetLocales() {
        return Resources.getSystem().getAssets().getLocales();
    }

    public static String[] getSupportedLocales(Context context) {
        if (context == null) {
            return new String[0];
        }
        String[] stringArray = context.getResources().getStringArray(R.array.supported_locales);
        Predicate<String> localeFilter = getLocaleFilter();
        if (localeFilter != null) {
            ArrayList arrayList = new ArrayList(stringArray.length);
            for (String str : stringArray) {
                if (localeFilter.test(str)) {
                    arrayList.add(str);
                }
            }
            int size = arrayList.size();
            if (size != stringArray.length) {
                return (String[]) arrayList.toArray(new String[size]);
            }
        }
        return stringArray;
    }

    public static String[] getSpecificCustomerSupportedLocales(Context context) {
        return context.getResources().getStringArray(R.array.specific_customer_supported_locales);
    }

    public static String[] getPseudoLocales() {
        return pseudoLocales;
    }

    public static String[] getDIDLocale(Context context) {
        return context.getResources().getStringArray(R.array.sem_did_supported_locale);
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
            Log.e(TAG, "Failed to read locale filter.", e);
            return null;
        } catch (PatternSyntaxException e2) {
            Log.e(TAG, "Bad locale filter format (\"" + e2.getPattern() + "\"), skipping.");
            return null;
        }
    }

    public static List<LocaleInfo> getAllAssetLocales(Context context, boolean z) {
        return LocaleStore.getAllLocaleInfos(context);
    }

    public static ArrayAdapter<LocaleInfo> constructAdapter(Context context) {
        return constructAdapter(context, R.layout.locale_picker_item, R.id.locale);
    }

    public static ArrayAdapter<LocaleInfo> constructAdapter(Context context, final int i, final int i2) {
        List<LocaleInfo> allAssetLocales = getAllAssetLocales(context, Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) != 0);
        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ArrayAdapter<LocaleInfo>(context, i, i2, allAssetLocales) { // from class: com.android.internal.app.LocalePicker.1
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int i3, View view, ViewGroup viewGroup) {
                TextView textView;
                if (view == null) {
                    view = layoutInflater.inflate(i, viewGroup, false);
                    textView = (TextView) view.findViewById(i2);
                    view.setTag(textView);
                } else {
                    textView = (TextView) view.getTag();
                }
                LocaleInfo item = getItem(i3);
                textView.lambda$setTextAsync$0(item.toString());
                textView.setTextLocale(item.getLocale());
                return view;
            }
        };
    }

    private static String toTitleCase(String str) {
        if (str.length() == 0) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static String getDisplayName(Locale locale, String[] strArr, String[] strArr2) {
        String locale2 = locale.toString();
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(locale2)) {
                return strArr2[i];
            }
        }
        return locale.getDisplayName(locale);
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setListAdapter(constructAdapter(getActivity()));
    }

    public void setLocaleSelectionListener(LocaleSelectionListener localeSelectionListener) {
        this.mListener = localeSelectionListener;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        getListView().requestFocus();
    }

    @Override // android.app.ListFragment
    public void onListItemClick(ListView listView, View view, int i, long j) {
        if (this.mListener != null) {
            this.mListener.onLocaleSelected(((LocaleInfo) getListAdapter().getItem(i)).locale);
        }
    }

    public static void updateLocale(Locale locale) {
        updateLocales(new LocaleList(locale));
    }

    public static void updateLocales(LocaleList localeList) {
        if (localeList != null) {
            localeList = removeExcludedLocales(localeList);
        }
        try {
            IActivityManager service = ActivityManager.getService();
            Configuration configuration = new Configuration();
            configuration.setLocales(localeList);
            configuration.userSetLocale = true;
            service.updatePersistentConfigurationWithAttribution(configuration, ActivityThread.currentOpPackageName(), null);
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (RemoteException unused) {
        }
    }

    private static LocaleList removeExcludedLocales(LocaleList localeList) {
        Predicate<String> localeFilter = getLocaleFilter();
        if (localeFilter != null) {
            int size = localeList.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Locale locale = localeList.get(i);
                if (localeFilter.test(locale.toString())) {
                    arrayList.add(locale);
                }
            }
            if (size != arrayList.size()) {
                return new LocaleList((Locale[]) arrayList.toArray(new Locale[0]));
            }
        }
        return localeList;
    }

    public static LocaleList getLocales() {
        try {
            return ActivityManager.getService().getGlobalConfiguration().getLocales();
        } catch (RemoteException unused) {
            return LocaleList.getDefault();
        }
    }
}
