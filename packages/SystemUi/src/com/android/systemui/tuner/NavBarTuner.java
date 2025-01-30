package com.android.systemui.tuner;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavigationBarInflaterView;
import com.android.systemui.tuner.TunerService;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes2.dex */
public class NavBarTuner extends TunerPreferenceFragment {
    public static final int[][] ICONS = {new int[]{R.drawable.ic_qs_circle, R.string.tuner_circle}, new int[]{R.drawable.ic_add, R.string.tuner_plus}, new int[]{R.drawable.ic_remove, R.string.tuner_minus}, new int[]{R.drawable.ic_left, R.string.tuner_left}, new int[]{R.drawable.ic_right, R.string.tuner_right}, new int[]{R.drawable.ic_menu, R.string.tuner_menu}};
    public Handler mHandler;
    public final ArrayList mTunables = new ArrayList();

    public static void setValue(String str, ListPreference listPreference, Preference preference, ListPreference listPreference2) {
        int i;
        String str2 = listPreference.mValue;
        if ("key".equals(str2)) {
            String str3 = listPreference2.mValue;
            try {
                i = Integer.parseInt(preference.getSummary().toString());
            } catch (Exception unused) {
                i = 66;
            }
            str2 = str2 + "(" + i + ":" + str3 + ")";
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue(str, str2);
    }

    public final void bindButton(String str, final String str2, String str3) {
        final ListPreference listPreference = (ListPreference) findPreference("type_".concat(str3));
        final Preference findPreference = findPreference("keycode_".concat(str3));
        final ListPreference listPreference2 = (ListPreference) findPreference("icon_".concat(str3));
        CharSequence[] charSequenceArr = new CharSequence[6];
        CharSequence[] charSequenceArr2 = new CharSequence[6];
        int applyDimension = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
        for (int i = 0; i < 6; i++) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            String packageName = getContext().getPackageName();
            int[][] iArr = ICONS;
            Drawable loadDrawable = Icon.createWithResource(packageName, iArr[i][0]).loadDrawable(getContext());
            loadDrawable.setTint(EmergencyPhoneWidget.BG_COLOR);
            loadDrawable.setBounds(0, 0, applyDimension, applyDimension);
            spannableStringBuilder.append("  ", new ImageSpan(loadDrawable, 1), 0);
            spannableStringBuilder.append((CharSequence) " ");
            spannableStringBuilder.append((CharSequence) getString(iArr[i][1]));
            charSequenceArr[i] = spannableStringBuilder;
            charSequenceArr2[i] = getContext().getPackageName() + "/" + iArr[i][0];
        }
        listPreference2.setEntries(charSequenceArr);
        listPreference2.mEntryValues = charSequenceArr2;
        TunerService.Tunable tunable = new TunerService.Tunable() { // from class: com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda3
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str4, final String str5) {
                final String str6 = str2;
                final ListPreference listPreference3 = listPreference;
                final ListPreference listPreference4 = listPreference2;
                final Preference preference = findPreference;
                final NavBarTuner navBarTuner = this;
                navBarTuner.mHandler.post(new Runnable() { // from class: com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarTuner navBarTuner2 = NavBarTuner.this;
                        String str7 = str5;
                        String str8 = str6;
                        ListPreference listPreference5 = listPreference3;
                        ListPreference listPreference6 = listPreference4;
                        Preference preference2 = preference;
                        int[][] iArr2 = NavBarTuner.ICONS;
                        navBarTuner2.getClass();
                        if (str7 == null) {
                            str7 = str8;
                        }
                        String extractButton = NavigationBarInflaterView.extractButton(str7);
                        if (!extractButton.startsWith("key")) {
                            listPreference5.setValue(extractButton);
                            preference2.setVisible(false);
                            listPreference6.setVisible(false);
                            return;
                        }
                        listPreference5.setValue("key");
                        String extractImage = NavigationBarInflaterView.extractImage(extractButton);
                        int extractKeycode = NavigationBarInflaterView.extractKeycode(extractButton);
                        listPreference6.setValue(extractImage);
                        navBarTuner2.updateSummary(listPreference6);
                        preference2.setSummary(extractKeycode + "");
                        preference2.setVisible(true);
                        listPreference6.setVisible(true);
                    }
                });
            }
        };
        this.mTunables.add(tunable);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(tunable, str);
        NavBarTuner$$ExternalSyntheticLambda4 navBarTuner$$ExternalSyntheticLambda4 = new NavBarTuner$$ExternalSyntheticLambda4(listPreference, listPreference2, findPreference, this, str);
        listPreference.mOnChangeListener = navBarTuner$$ExternalSyntheticLambda4;
        listPreference2.mOnChangeListener = navBarTuner$$ExternalSyntheticLambda4;
        findPreference.mOnClickListener = new NavBarTuner$$ExternalSyntheticLambda4(listPreference, listPreference2, findPreference, this, str);
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override // androidx.preference.PreferenceFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        this.mHandler = new Handler();
        super.onCreate(bundle);
    }

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        addPreferencesFromResource(R.xml.nav_bar_tuner);
        final ListPreference listPreference = (ListPreference) findPreference("layout");
        TunerService.Tunable tunable = new TunerService.Tunable() { // from class: com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda0
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str2, final String str3) {
                Handler handler = NavBarTuner.this.mHandler;
                final ListPreference listPreference2 = listPreference;
                handler.post(new Runnable() { // from class: com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str4 = str3;
                        ListPreference listPreference3 = listPreference2;
                        int[][] iArr = NavBarTuner.ICONS;
                        if (str4 == null) {
                            str4 = "default";
                        }
                        listPreference3.setValue(str4);
                    }
                });
            }
        };
        this.mTunables.add(tunable);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(tunable, "sysui_nav_bar");
        listPreference.mOnChangeListener = new NavBarTuner$$ExternalSyntheticLambda1();
        bindButton("sysui_nav_bar_left", "space", "left");
        bindButton("sysui_nav_bar_right", "menu_ime", "right");
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(new NavBarTuner$$ExternalSyntheticLambda2());
    }

    public final void updateSummary(ListPreference listPreference) {
        try {
            int applyDimension = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
            String str = listPreference.mValue.split("/")[0];
            int parseInt = Integer.parseInt(listPreference.mValue.split("/")[1]);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            Drawable loadDrawable = Icon.createWithResource(str, parseInt).loadDrawable(getContext());
            loadDrawable.setTint(EmergencyPhoneWidget.BG_COLOR);
            loadDrawable.setBounds(0, 0, applyDimension, applyDimension);
            spannableStringBuilder.append("  ", new ImageSpan(loadDrawable, 1), 0);
            spannableStringBuilder.append((CharSequence) " ");
            int i = 0;
            while (true) {
                int[][] iArr = ICONS;
                if (i >= 6) {
                    listPreference.setSummary(spannableStringBuilder);
                    return;
                }
                int[] iArr2 = iArr[i];
                if (iArr2[0] == parseInt) {
                    spannableStringBuilder.append((CharSequence) getString(iArr2[1]));
                }
                i++;
            }
        } catch (Exception e) {
            Log.d("NavButton", "Problem with summary", e);
            listPreference.setSummary(null);
        }
    }
}
