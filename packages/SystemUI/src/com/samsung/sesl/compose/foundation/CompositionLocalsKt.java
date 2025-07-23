package com.samsung.sesl.compose.foundation;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.InspectionModeKt;
import com.android.systemui.R;
import com.samsung.sesl.compose.SeslPhoneTokenScheme;
import com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme;
import com.samsung.sesl.compose.foundation.theme.SeslTokenScheme;
import com.samsung.sesl.compose.foundation.theme.SeslTokenSchemeImpl;
import com.samsung.sesl.compose.foundation.theme.TokenSchemeKt;
import com.samsung.sesl.platform.settings.AospSettingsDataKt;
import com.samsung.sesl.platform.settings.SettingsData;
import com.samsung.sesl.sep.settings.SepSettingsDataKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CompositionLocalsKt {
    public static final ComputedProvidableCompositionLocal LocalSeslPhoneTokenDarkScheme;
    public static final ComputedProvidableCompositionLocal LocalSeslPhoneTokenLightScheme;

    static {
        final int i = 0;
        LocalSeslPhoneTokenLightScheme = new ComputedProvidableCompositionLocal(new Function1() { // from class: com.samsung.sesl.compose.foundation.CompositionLocalsKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CompositionLocalAccessorScope compositionLocalAccessorScope = (CompositionLocalAccessorScope) obj;
                switch (i) {
                    case 0:
                        SeslTokenSchemeImpl seslTokenSchemeImpl = TokenSchemeKt.SeslLightTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).booleanValue()) {
                            return seslTokenSchemeImpl;
                        }
                        View view = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) view.getTag(R.id.sesl_compose_token_light_scheme_tag);
                        if (seslTokenScheme != null) {
                            return seslTokenScheme;
                        }
                        Context context = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration = new Configuration();
                        configuration.uiMode = 16;
                        Context createConfigurationContext = context.createConfigurationContext(configuration);
                        createConfigurationContext.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext), seslTokenSchemeImpl);
                        view.setTag(R.id.sesl_compose_token_light_scheme_tag, seslMergedTokenScheme);
                        return seslMergedTokenScheme;
                    case 1:
                        SeslTokenSchemeImpl seslTokenSchemeImpl2 = TokenSchemeKt.SeslDarkTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap2 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap2.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap2, staticProvidableCompositionLocal2)).booleanValue()) {
                            return seslTokenSchemeImpl2;
                        }
                        View view2 = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme2 = (SeslTokenScheme) view2.getTag(R.id.sesl_compose_token_dark_scheme_tag);
                        if (seslTokenScheme2 != null) {
                            return seslTokenScheme2;
                        }
                        Context context2 = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration2 = new Configuration();
                        configuration2.uiMode = 32;
                        Context createConfigurationContext2 = context2.createConfigurationContext(configuration2);
                        createConfigurationContext2.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme2 = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext2), seslTokenSchemeImpl2);
                        view2.setTag(R.id.sesl_compose_token_dark_scheme_tag, seslMergedTokenScheme2);
                        return seslMergedTokenScheme2;
                    case 2:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap3 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap3.getClass();
                        ContentResolver contentResolver = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap3, staticProvidableCompositionLocal3)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData = AospSettingsDataKt.ReduceMotionSettingData;
                        contentResolver.getClass();
                        return settingsBooleanData.getSettingValue$phone_release(contentResolver);
                    default:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal4 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap4 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap4.getClass();
                        ContentResolver contentResolver2 = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap4, staticProvidableCompositionLocal4)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData2 = SepSettingsDataKt.ShowButtonBackgroundSettingData;
                        contentResolver2.getClass();
                        return settingsBooleanData2.getSettingValue$phone_release(contentResolver2);
                }
            }
        });
        final int i2 = 1;
        LocalSeslPhoneTokenDarkScheme = new ComputedProvidableCompositionLocal(new Function1() { // from class: com.samsung.sesl.compose.foundation.CompositionLocalsKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CompositionLocalAccessorScope compositionLocalAccessorScope = (CompositionLocalAccessorScope) obj;
                switch (i2) {
                    case 0:
                        SeslTokenSchemeImpl seslTokenSchemeImpl = TokenSchemeKt.SeslLightTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).booleanValue()) {
                            return seslTokenSchemeImpl;
                        }
                        View view = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) view.getTag(R.id.sesl_compose_token_light_scheme_tag);
                        if (seslTokenScheme != null) {
                            return seslTokenScheme;
                        }
                        Context context = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration = new Configuration();
                        configuration.uiMode = 16;
                        Context createConfigurationContext = context.createConfigurationContext(configuration);
                        createConfigurationContext.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext), seslTokenSchemeImpl);
                        view.setTag(R.id.sesl_compose_token_light_scheme_tag, seslMergedTokenScheme);
                        return seslMergedTokenScheme;
                    case 1:
                        SeslTokenSchemeImpl seslTokenSchemeImpl2 = TokenSchemeKt.SeslDarkTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap2 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap2.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap2, staticProvidableCompositionLocal2)).booleanValue()) {
                            return seslTokenSchemeImpl2;
                        }
                        View view2 = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme2 = (SeslTokenScheme) view2.getTag(R.id.sesl_compose_token_dark_scheme_tag);
                        if (seslTokenScheme2 != null) {
                            return seslTokenScheme2;
                        }
                        Context context2 = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration2 = new Configuration();
                        configuration2.uiMode = 32;
                        Context createConfigurationContext2 = context2.createConfigurationContext(configuration2);
                        createConfigurationContext2.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme2 = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext2), seslTokenSchemeImpl2);
                        view2.setTag(R.id.sesl_compose_token_dark_scheme_tag, seslMergedTokenScheme2);
                        return seslMergedTokenScheme2;
                    case 2:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap3 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap3.getClass();
                        ContentResolver contentResolver = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap3, staticProvidableCompositionLocal3)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData = AospSettingsDataKt.ReduceMotionSettingData;
                        contentResolver.getClass();
                        return settingsBooleanData.getSettingValue$phone_release(contentResolver);
                    default:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal4 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap4 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap4.getClass();
                        ContentResolver contentResolver2 = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap4, staticProvidableCompositionLocal4)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData2 = SepSettingsDataKt.ShowButtonBackgroundSettingData;
                        contentResolver2.getClass();
                        return settingsBooleanData2.getSettingValue$phone_release(contentResolver2);
                }
            }
        });
        final int i3 = 2;
        new ComputedProvidableCompositionLocal(new Function1() { // from class: com.samsung.sesl.compose.foundation.CompositionLocalsKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CompositionLocalAccessorScope compositionLocalAccessorScope = (CompositionLocalAccessorScope) obj;
                switch (i3) {
                    case 0:
                        SeslTokenSchemeImpl seslTokenSchemeImpl = TokenSchemeKt.SeslLightTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).booleanValue()) {
                            return seslTokenSchemeImpl;
                        }
                        View view = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) view.getTag(R.id.sesl_compose_token_light_scheme_tag);
                        if (seslTokenScheme != null) {
                            return seslTokenScheme;
                        }
                        Context context = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration = new Configuration();
                        configuration.uiMode = 16;
                        Context createConfigurationContext = context.createConfigurationContext(configuration);
                        createConfigurationContext.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext), seslTokenSchemeImpl);
                        view.setTag(R.id.sesl_compose_token_light_scheme_tag, seslMergedTokenScheme);
                        return seslMergedTokenScheme;
                    case 1:
                        SeslTokenSchemeImpl seslTokenSchemeImpl2 = TokenSchemeKt.SeslDarkTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap2 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap2.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap2, staticProvidableCompositionLocal2)).booleanValue()) {
                            return seslTokenSchemeImpl2;
                        }
                        View view2 = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme2 = (SeslTokenScheme) view2.getTag(R.id.sesl_compose_token_dark_scheme_tag);
                        if (seslTokenScheme2 != null) {
                            return seslTokenScheme2;
                        }
                        Context context2 = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration2 = new Configuration();
                        configuration2.uiMode = 32;
                        Context createConfigurationContext2 = context2.createConfigurationContext(configuration2);
                        createConfigurationContext2.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme2 = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext2), seslTokenSchemeImpl2);
                        view2.setTag(R.id.sesl_compose_token_dark_scheme_tag, seslMergedTokenScheme2);
                        return seslMergedTokenScheme2;
                    case 2:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap3 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap3.getClass();
                        ContentResolver contentResolver = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap3, staticProvidableCompositionLocal3)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData = AospSettingsDataKt.ReduceMotionSettingData;
                        contentResolver.getClass();
                        return settingsBooleanData.getSettingValue$phone_release(contentResolver);
                    default:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal4 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap4 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap4.getClass();
                        ContentResolver contentResolver2 = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap4, staticProvidableCompositionLocal4)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData2 = SepSettingsDataKt.ShowButtonBackgroundSettingData;
                        contentResolver2.getClass();
                        return settingsBooleanData2.getSettingValue$phone_release(contentResolver2);
                }
            }
        });
        final int i4 = 3;
        new ComputedProvidableCompositionLocal(new Function1() { // from class: com.samsung.sesl.compose.foundation.CompositionLocalsKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CompositionLocalAccessorScope compositionLocalAccessorScope = (CompositionLocalAccessorScope) obj;
                switch (i4) {
                    case 0:
                        SeslTokenSchemeImpl seslTokenSchemeImpl = TokenSchemeKt.SeslLightTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).booleanValue()) {
                            return seslTokenSchemeImpl;
                        }
                        View view = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) view.getTag(R.id.sesl_compose_token_light_scheme_tag);
                        if (seslTokenScheme != null) {
                            return seslTokenScheme;
                        }
                        Context context = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration = new Configuration();
                        configuration.uiMode = 16;
                        Context createConfigurationContext = context.createConfigurationContext(configuration);
                        createConfigurationContext.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext), seslTokenSchemeImpl);
                        view.setTag(R.id.sesl_compose_token_light_scheme_tag, seslMergedTokenScheme);
                        return seslMergedTokenScheme;
                    case 1:
                        SeslTokenSchemeImpl seslTokenSchemeImpl2 = TokenSchemeKt.SeslDarkTokenScheme;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InspectionModeKt.LocalInspectionMode;
                        PersistentCompositionLocalMap persistentCompositionLocalMap2 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap2.getClass();
                        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap2, staticProvidableCompositionLocal2)).booleanValue()) {
                            return seslTokenSchemeImpl2;
                        }
                        View view2 = (View) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalView);
                        SeslTokenScheme seslTokenScheme2 = (SeslTokenScheme) view2.getTag(R.id.sesl_compose_token_dark_scheme_tag);
                        if (seslTokenScheme2 != null) {
                            return seslTokenScheme2;
                        }
                        Context context2 = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap2, AndroidCompositionLocals_androidKt.LocalContext);
                        Configuration configuration2 = new Configuration();
                        configuration2.uiMode = 32;
                        Context createConfigurationContext2 = context2.createConfigurationContext(configuration2);
                        createConfigurationContext2.getClass();
                        SeslMergedTokenScheme seslMergedTokenScheme2 = new SeslMergedTokenScheme(new SeslPhoneTokenScheme(createConfigurationContext2), seslTokenSchemeImpl2);
                        view2.setTag(R.id.sesl_compose_token_dark_scheme_tag, seslMergedTokenScheme2);
                        return seslMergedTokenScheme2;
                    case 2:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap3 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap3.getClass();
                        ContentResolver contentResolver = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap3, staticProvidableCompositionLocal3)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData = AospSettingsDataKt.ReduceMotionSettingData;
                        contentResolver.getClass();
                        return settingsBooleanData.getSettingValue$phone_release(contentResolver);
                    default:
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal4 = AndroidCompositionLocals_androidKt.LocalContext;
                        PersistentCompositionLocalMap persistentCompositionLocalMap4 = (PersistentCompositionLocalMap) compositionLocalAccessorScope;
                        persistentCompositionLocalMap4.getClass();
                        ContentResolver contentResolver2 = ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap4, staticProvidableCompositionLocal4)).getContentResolver();
                        SettingsData.SettingsBooleanData settingsBooleanData2 = SepSettingsDataKt.ShowButtonBackgroundSettingData;
                        contentResolver2.getClass();
                        return settingsBooleanData2.getSettingValue$phone_release(contentResolver2);
                }
            }
        });
    }
}
