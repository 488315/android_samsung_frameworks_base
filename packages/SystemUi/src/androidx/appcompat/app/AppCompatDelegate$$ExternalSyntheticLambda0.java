package androidx.appcompat.app;

import android.app.LocaleManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.LocaleList;
import android.util.Log;
import android.util.Xml;
import androidx.core.os.LocaleListCompat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AppCompatDelegate$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;

    public /* synthetic */ AppCompatDelegate$$ExternalSyntheticLambda0(Context context, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0077, code lost:
    
        if (r5 != null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0083, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0081, code lost:
    
        if (r5 == null) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b2  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        Context context;
        ComponentName componentName;
        String str;
        Object localeManagerForApplication;
        FileInputStream openFileInput;
        switch (this.$r8$classId) {
            case 0:
                context = this.f$0;
                componentName = new ComponentName(context, "androidx.appcompat.app.AppLocalesMetadataHolderService");
                if (context.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
                    AppCompatDelegate.sAppContext = context;
                    Object localeManagerForApplication2 = AppCompatDelegate.getLocaleManagerForApplication();
                    if ((localeManagerForApplication2 != null ? LocaleListCompat.wrap(((LocaleManager) localeManagerForApplication2).getApplicationLocales()) : LocaleListCompat.sEmptyLocaleList).mImpl.isEmpty()) {
                        str = "";
                        try {
                            try {
                                openFileInput = context.openFileInput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                                try {
                                    XmlPullParser newPullParser = Xml.newPullParser();
                                    newPullParser.setInput(openFileInput, "UTF-8");
                                    int depth = newPullParser.getDepth();
                                    while (true) {
                                        int next = newPullParser.next();
                                        if (next != 1 && (next != 3 || newPullParser.getDepth() > depth)) {
                                            if (next != 3 && next != 4 && newPullParser.getName().equals("locales")) {
                                                str = newPullParser.getAttributeValue(null, "application_locales");
                                                break;
                                            }
                                        }
                                    }
                                } catch (IOException | XmlPullParserException unused) {
                                    Log.w("AppLocalesStorageHelper", "Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                                    break;
                                }
                            } catch (FileNotFoundException unused2) {
                                Log.w("AppLocalesStorageHelper", "Reading app Locales : Locales record file not found: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                                break;
                            }
                        } catch (Throwable th) {
                            if (openFileInput != null) {
                                try {
                                    openFileInput.close();
                                } catch (IOException unused3) {
                                }
                            }
                            throw th;
                        }
                    }
                    context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
                }
                AppCompatDelegate.sIsFrameworkSyncChecked = true;
            default:
                Context context2 = this.f$0;
                if (AppCompatDelegate.isAutoStorageOptedIn(context2) && !AppCompatDelegate.sIsFrameworkSyncChecked) {
                    AppCompatDelegate.sSerialExecutorForLocalesStorage.execute(new AppCompatDelegate$$ExternalSyntheticLambda0(context2, 0));
                    return;
                }
                return;
        }
        LocaleListCompat forLanguageTags = LocaleListCompat.forLanguageTags(str);
        Objects.requireNonNull(forLanguageTags);
        localeManagerForApplication = AppCompatDelegate.getLocaleManagerForApplication();
        if (localeManagerForApplication != null) {
            ((LocaleManager) localeManagerForApplication).setApplicationLocales(LocaleList.forLanguageTags(forLanguageTags.mImpl.toLanguageTags()));
        }
        context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        AppCompatDelegate.sIsFrameworkSyncChecked = true;
        if (str.isEmpty()) {
            context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
        } else {
            Log.d("AppLocalesStorageHelper", "Reading app Locales : Locales read from file: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file , appLocales: ".concat(str));
        }
        LocaleListCompat forLanguageTags2 = LocaleListCompat.forLanguageTags(str);
        Objects.requireNonNull(forLanguageTags2);
        localeManagerForApplication = AppCompatDelegate.getLocaleManagerForApplication();
        if (localeManagerForApplication != null) {
        }
        context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        AppCompatDelegate.sIsFrameworkSyncChecked = true;
    }
}
