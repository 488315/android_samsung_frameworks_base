package com.android.server.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.SemEnterpriseDeviceManager;

/* loaded from: classes2.dex */
public class SemInputMethodMenuControllerUtil {
  public static final String TAG = "SemInputMethodMenuControllerUtil";
  public static String VAL_NO_MICROPHONE = "noMicrophoneKey";
  public static String VAL_NO_MICROPHONE_COMPAT = "nm";

  public boolean isVoiceInputDisable(InputMethodManagerService inputMethodManagerService) {
    EditorInfo editorInfo = inputMethodManagerService.mCurEditorInfo;
    if (editorInfo != null) {
      String str = editorInfo.privateImeOptions;
      r0 =
          (str != null
                  && (str.contains(VAL_NO_MICROPHONE_COMPAT) || str.contains(VAL_NO_MICROPHONE)))
              || inputMethodManagerService.isPasswordInputType(editorInfo.inputType);
      Log.d(TAG, "Check voice input Disable : " + r0);
    }
    return r0;
  }

  public boolean isVisibleShowKeyboardButton(Context context, boolean z, boolean z2) {
    Bundle bundle =
        SemEnterpriseDeviceManager.getInstance(context)
            .getApplicationRestrictions("com.android.settings")
            .getBundle("key_show_keyboard_button");
    return (z
            || (bundle != null && bundle.getBoolean("hide"))
            || SemEmergencyManager.isEmergencyMode(context)
            || z2)
        ? false
        : true;
  }

  public boolean disableShowKeyboardButtonSwitch(Context context, boolean z) {
    Bundle bundle =
        SemEnterpriseDeviceManager.getInstance(context)
            .getApplicationRestrictions("com.android.settings")
            .getBundle("key_show_keyboard_button");
    return z && bundle != null && bundle.getBoolean("grayout");
  }

  public SpannableString applyStringWithIcon(Resources resources) {
    String string = resources.getString(17042819);
    SpannableString spannableString = new SpannableString(string);
    int indexOf = string.indexOf("%s");
    int color = resources.getColor(17171537, null);
    Drawable drawable = resources.getDrawable(17304467, null);
    drawable.setTint(color);
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    spannableString.setSpan(new ImageSpan(drawable), indexOf, indexOf + 2, 17);
    return spannableString;
  }

  public void sendSALogging(
      final Context context, final String str, final String str2, final Boolean bool) {
    new Thread(
            new Runnable() { // from class:
                             // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemInputMethodMenuControllerUtil.lambda$sendSALogging$0(str, str2, bool, context);
              }
            })
        .start();
  }

  public static /* synthetic */ void lambda$sendSALogging$0(
      String str, String str2, Boolean bool, Context context) {
    Intent intent = new Intent(str);
    intent.addFlags(536870912);
    intent.putExtra(str2, bool);
    context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
  }
}
