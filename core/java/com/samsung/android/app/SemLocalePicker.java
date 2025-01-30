package com.samsung.android.app;

import android.content.Context;
import com.android.internal.app.LocalePicker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SemLocalePicker {
  private SemLocalePicker() {}

  public static class LocaleInfo {
    String label;
    Locale locale;

    private LocaleInfo(LocalePicker.LocaleInfo info) {
      if (info != null) {
        this.label = info.getLabel();
        this.locale = info.getLocale();
      }
    }

    public String getLabel() {
      return this.label;
    }

    public Locale getLocale() {
      return this.locale;
    }
  }

  public static List<LocaleInfo> getAllLocales(Context context) {
    List<LocalePicker.LocaleInfo> localeListFromLocalePicker =
        LocalePicker.getAllAssetLocales(context, false);
    ArrayList<LocaleInfo> localeInfoList = new ArrayList<>();
    if (localeListFromLocalePicker == null || localeListFromLocalePicker.size() == 0) {
      return localeInfoList;
    }
    for (LocalePicker.LocaleInfo info : localeListFromLocalePicker) {
      if (info != null) {
        localeInfoList.add(new LocaleInfo(info));
      }
    }
    return localeInfoList;
  }

  public static void updateLocale(Locale locale) {
    if (locale == null) {
      return;
    }
    LocalePicker.updateLocale(locale);
  }
}
