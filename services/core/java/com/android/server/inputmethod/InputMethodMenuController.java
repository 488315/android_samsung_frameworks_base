package com.android.server.inputmethod;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import com.android.internal.R;
import com.android.server.LocalServices;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.widget.SemHoverPopupWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class InputMethodMenuController {
  public static final String TAG = "InputMethodMenuController";
  public AlertDialog.Builder mDialogBuilder;
  public InputMethodDialogWindowContext mDialogWindowContext;
  public InputMethodInfo[] mIms;
  public final ArrayMap mMethodMap;
  public final InputMethodManagerService mService;
  public final InputMethodUtils.InputMethodSettings mSettings;
  public boolean mShowImeWithHardKeyboard;
  public int[] mSubtypeIds;
  public View mSwitchInSelectDialogView;
  public final InputMethodSubtypeSwitchingController mSwitchingController;
  public AlertDialog mSwitchingDialog;
  public View mSwitchingDialogTitleView;
  public final WindowManagerInternal mWindowManagerInternal =
      (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
  public SemInputMethodMenuControllerUtil mSemImcUtil = new SemInputMethodMenuControllerUtil();

  public InputMethodMenuController(InputMethodManagerService inputMethodManagerService) {
    this.mService = inputMethodManagerService;
    this.mSettings = inputMethodManagerService.mSettings;
    this.mSwitchingController = inputMethodManagerService.mSwitchingController;
    this.mMethodMap = inputMethodManagerService.mMethodMap;
  }

  public void showInputMethodMenu(boolean z, int i) {
    int i2;
    InputMethodSubtype currentInputMethodSubtypeLocked;
    if (this.mSemImcUtil.isVoiceInputDisable(this.mService)) {
      z = false;
    }
    boolean isScreenLocked = isScreenLocked();
    String selectedInputMethod = this.mSettings.getSelectedInputMethod();
    int selectedInputMethodSubtypeId =
        this.mSettings.getSelectedInputMethodSubtypeId(selectedInputMethod);
    synchronized (ImfLock.class) {
      List sortedInputMethodAndSubtypeListForImeMenuLocked =
          this.mSwitchingController.getSortedInputMethodAndSubtypeListForImeMenuLocked(
              z, isScreenLocked);
      if (sortedInputMethodAndSubtypeListForImeMenuLocked.isEmpty()) {
        return;
      }
      hideInputMethodMenuLocked();
      if (selectedInputMethodSubtypeId == -1
          && (currentInputMethodSubtypeLocked = this.mService.getCurrentInputMethodSubtypeLocked())
              != null) {
        selectedInputMethodSubtypeId =
            SubtypeUtils.getSubtypeIdFromHashCode(
                (InputMethodInfo) this.mMethodMap.get(this.mService.getSelectedMethodIdLocked()),
                currentInputMethodSubtypeLocked.hashCode());
      }
      InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem =
          (InputMethodSubtypeSwitchingController.ImeSubtypeListItem)
              sortedInputMethodAndSubtypeListForImeMenuLocked.stream()
                  .filter(
                      new Predicate() { // from class:
                                        // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          boolean lambda$showInputMethodMenu$0;
                          lambda$showInputMethodMenu$0 =
                              InputMethodMenuController.lambda$showInputMethodMenu$0(
                                  (InputMethodSubtypeSwitchingController.ImeSubtypeListItem) obj);
                          return lambda$showInputMethodMenu$0;
                        }
                      })
                  .findFirst()
                  .orElse(null);
      sortedInputMethodAndSubtypeListForImeMenuLocked.removeIf(
          new Predicate() { // from class:
                            // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$showInputMethodMenu$1;
              lambda$showInputMethodMenu$1 =
                  InputMethodMenuController.lambda$showInputMethodMenu$1(
                      (InputMethodSubtypeSwitchingController.ImeSubtypeListItem) obj);
              return lambda$showInputMethodMenu$1;
            }
          });
      sortedInputMethodAndSubtypeListForImeMenuLocked.add(imeSubtypeListItem);
      SemDesktopModeManager semDesktopModeManager = this.mService.mDesktopModeManager;
      if (SemDesktopModeManager.isDesktopMode()) {
        sortedInputMethodAndSubtypeListForImeMenuLocked.removeIf(
            new Predicate() { // from class:
                              // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda2
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$showInputMethodMenu$2;
                lambda$showInputMethodMenu$2 =
                    InputMethodMenuController.lambda$showInputMethodMenu$2(
                        (InputMethodSubtypeSwitchingController.ImeSubtypeListItem) obj);
                return lambda$showInputMethodMenu$2;
              }
            });
      }
      int size = sortedInputMethodAndSubtypeListForImeMenuLocked.size();
      this.mIms = new InputMethodInfo[size];
      this.mSubtypeIds = new int[size];
      int i3 = 0;
      for (int i4 = 0; i4 < size; i4++) {
        InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem2 =
            (InputMethodSubtypeSwitchingController.ImeSubtypeListItem)
                sortedInputMethodAndSubtypeListForImeMenuLocked.get(i4);
        if (imeSubtypeListItem2 == null) {
          Slog.i(
              TAG,
              "showInputMethodMenu: index="
                  + i4
                  + ", imList="
                  + sortedInputMethodAndSubtypeListForImeMenuLocked);
        } else {
          InputMethodInfo[] inputMethodInfoArr = this.mIms;
          InputMethodInfo inputMethodInfo = imeSubtypeListItem2.mImi;
          inputMethodInfoArr[i4] = inputMethodInfo;
          this.mSubtypeIds[i4] = imeSubtypeListItem2.mSubtypeId;
          if (inputMethodInfo.getId().equals(selectedInputMethod)
              && ((i2 = this.mSubtypeIds[i4]) == -1
                  || ((selectedInputMethodSubtypeId == -1 && i2 == 0)
                      || i2 == selectedInputMethodSubtypeId
                      || isSamsungIme(this.mIms[i4])))) {
            i3 = i4;
          }
        }
      }
      if (this.mDialogWindowContext == null) {
        this.mDialogWindowContext = new InputMethodDialogWindowContext();
      }
      Context context = this.mDialogWindowContext.get(i);
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      this.mDialogBuilder = builder;
      builder.setOnCancelListener(
          new DialogInterface
              .OnCancelListener() { // from class:
                                    // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
              InputMethodMenuController.this.lambda$showInputMethodMenu$3(dialogInterface);
            }
          });
      Context context2 = this.mDialogBuilder.getContext();
      TypedArray obtainStyledAttributes =
          context2.obtainStyledAttributes(
              null, R.styleable.DialogPreference, android.R.attr.alertDialogStyle, 0);
      Drawable drawable = obtainStyledAttributes.getDrawable(2);
      obtainStyledAttributes.recycle();
      this.mDialogBuilder.setIcon(drawable);
      LayoutInflater layoutInflater =
          (LayoutInflater) context2.getSystemService(LayoutInflater.class);
      this.mDialogBuilder.setCustomTitle(layoutInflater.inflate(17367473, (ViewGroup) null));
      setupSwitchHardKeyboardAndShowKeyboardButton(context2, layoutInflater);
      final ImeSubtypeListAdapter imeSubtypeListAdapter =
          new ImeSubtypeListAdapter(
              context2, 17367475, sortedInputMethodAndSubtypeListForImeMenuLocked, i3);
      imeSubtypeListAdapter.setUserId(this.mSettings.getCurrentUserId());
      this.mDialogBuilder.setSingleChoiceItems(
          imeSubtypeListAdapter,
          i3,
          new DialogInterface
              .OnClickListener() { // from class:
                                   // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i5) {
              InputMethodMenuController.this.lambda$showInputMethodMenu$4(
                  imeSubtypeListAdapter, dialogInterface, i5);
            }
          });
      AlertDialog create = this.mDialogBuilder.create();
      this.mSwitchingDialog = create;
      create.setOnShowListener(
          new DialogInterface
              .OnShowListener() { // from class:
                                  // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
              InputMethodMenuController.this.lambda$showInputMethodMenu$5(dialogInterface);
            }
          });
      if (this.mService.isDEXStandAloneMode()) {
        this.mSwitchingDialog.getListView().setEnabled(false);
      }
      this.mSwitchingDialog.setCanceledOnTouchOutside(true);
      Window window = this.mSwitchingDialog.getWindow();
      WindowManager.LayoutParams attributes = window.getAttributes();
      window.setType(2012);
      window.setHideOverlayWindows(true);
      attributes.token = context.getWindowContextToken();
      attributes.privateFlags |= 16;
      attributes.setTitle("Select input method");
      window.setAttributes(attributes);
      this.mService.updateSystemUiLocked();
      this.mService.sendOnNavButtonFlagsChangedLocked();
      this.mSwitchingDialog.show();
      this.mService.updateImeSwitchStatus("imeSwitcherShown");
    }
  }

  public static /* synthetic */ boolean lambda$showInputMethodMenu$0(
      InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem) {
    return "com.samsung.android.honeyboard/.service.HoneyBoardService"
        .equals(imeSubtypeListItem.mImi.getId());
  }

  public static /* synthetic */ boolean lambda$showInputMethodMenu$1(
      InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem) {
    String id = imeSubtypeListItem.mImi.getId();
    return "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(id)
        || "com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco"
            .equals(id)
        || "com.samsung.android.bixby.service/.dictation.DictationInputMethodService".equals(id);
  }

  public static /* synthetic */ boolean lambda$showInputMethodMenu$2(
      InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem) {
    String packageName = imeSubtypeListItem.mImi.getPackageName();
    return (KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(packageName)
            || "com.google.android.googlequicksearchbox".equals(packageName))
        ? false
        : true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$showInputMethodMenu$3(DialogInterface dialogInterface) {
    hideInputMethodMenu();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$showInputMethodMenu$4(
      ImeSubtypeListAdapter imeSubtypeListAdapter, DialogInterface dialogInterface, int i) {
    int[] iArr;
    synchronized (ImfLock.class) {
      InputMethodInfo[] inputMethodInfoArr = this.mIms;
      if (inputMethodInfoArr != null
          && inputMethodInfoArr.length > i
          && (iArr = this.mSubtypeIds) != null
          && iArr.length > i) {
        InputMethodInfo inputMethodInfo = inputMethodInfoArr[i];
        int i2 = iArr[i];
        imeSubtypeListAdapter.mCheckedItem = i;
        imeSubtypeListAdapter.notifyDataSetChanged();
        hideInputMethodMenu();
        if (inputMethodInfo != null) {
          if (i2 < 0 || i2 >= inputMethodInfo.getSubtypeCount()) {
            i2 = -1;
          }
          this.mService.setInputMethodLocked(inputMethodInfo.getId(), i2);
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$showInputMethodMenu$5(DialogInterface dialogInterface) {
    AlertDialog alertDialog = this.mSwitchingDialog;
    if (alertDialog != null) {
      Button button = alertDialog.getButton(-1);
      float f = this.mService.mRes.getConfiguration().fontScale;
      float textSize = button.getTextSize() / this.mService.mRes.getDisplayMetrics().scaledDensity;
      if (f > 1.2f) {
        f = 1.2f;
      }
      button.setTextSize(1, textSize * f);
    }
  }

  public final boolean isScreenLocked() {
    return this.mWindowManagerInternal.isKeyguardLocked()
        && this.mWindowManagerInternal.isKeyguardSecure(this.mSettings.getCurrentUserId());
  }

  public void updateKeyboardFromSettingsLocked() {
    this.mShowImeWithHardKeyboard = this.mSettings.isShowImeWithHardKeyboardEnabled();
    AlertDialog alertDialog = this.mSwitchingDialog;
    if (alertDialog != null && this.mSwitchingDialogTitleView != null && alertDialog.isShowing()) {
      ((Switch) this.mSwitchingDialogTitleView.findViewById(android.R.id.icon_menu_presenter))
          .setChecked(this.mShowImeWithHardKeyboard);
    }
    AlertDialog alertDialog2 = this.mSwitchingDialog;
    if (alertDialog2 == null
        || this.mSwitchInSelectDialogView == null
        || !alertDialog2.isShowing()) {
      return;
    }
    ((Switch) this.mSwitchInSelectDialogView.findViewById(android.R.id.icon_menu_presenter))
        .setChecked(this.mShowImeWithHardKeyboard);
  }

  public void hideInputMethodMenu() {
    synchronized (ImfLock.class) {
      hideInputMethodMenuLocked();
    }
  }

  public void hideInputMethodMenuLocked() {
    AlertDialog alertDialog = this.mSwitchingDialog;
    if (alertDialog != null) {
      alertDialog.dismiss();
      this.mSwitchingDialog = null;
      this.mSwitchingDialogTitleView = null;
      this.mService.updateSystemUiLocked();
      this.mService.sendOnNavButtonFlagsChangedLocked();
      this.mService.updateImeSwitchStatus("imeSwitcherHidden");
      this.mDialogBuilder = null;
      this.mIms = null;
    }
  }

  public AlertDialog getSwitchingDialogLocked() {
    return this.mSwitchingDialog;
  }

  public boolean getShowImeWithHardKeyboard() {
    return this.mShowImeWithHardKeyboard;
  }

  public boolean isisInputMethodPickerShownForTestLocked() {
    AlertDialog alertDialog = this.mSwitchingDialog;
    if (alertDialog == null) {
      return false;
    }
    return alertDialog.isShowing();
  }

  public void handleHardKeyboardStatusChange(boolean z) {
    synchronized (ImfLock.class) {
      AlertDialog alertDialog = this.mSwitchingDialog;
      int i = 0;
      if (alertDialog != null
          && this.mSwitchingDialogTitleView != null
          && alertDialog.isShowing()) {
        this.mSwitchingDialogTitleView
            .findViewById(android.R.id.icon_menu)
            .setVisibility(z ? 0 : 8);
      }
      AlertDialog alertDialog2 = this.mSwitchingDialog;
      if (alertDialog2 != null
          && this.mSwitchInSelectDialogView != null
          && alertDialog2.isShowing()) {
        View findViewById = this.mSwitchInSelectDialogView.findViewById(android.R.id.icon_menu);
        if (!z) {
          i = 8;
        }
        findViewById.setVisibility(i);
      }
    }
  }

  public class ImeSubtypeListAdapter extends ArrayAdapter {
    public int mCheckedItem;
    public final LayoutInflater mInflater;
    public final List mItemsList;
    public final int mTextViewResourceId;
    public int mUserId;

    public ImeSubtypeListAdapter(Context context, int i, List list, int i2) {
      super(context, i, list);
      this.mTextViewResourceId = i;
      this.mItemsList = list;
      this.mCheckedItem = i2;
      this.mInflater = LayoutInflater.from(context);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
      InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem;
      if (view == null) {
        view = this.mInflater.inflate(this.mTextViewResourceId, (ViewGroup) null);
      }
      if (i < 0
          || i >= this.mItemsList.size()
          || (imeSubtypeListItem =
                  (InputMethodSubtypeSwitchingController.ImeSubtypeListItem) this.mItemsList.get(i))
              == null) {
        return view;
      }
      CharSequence charSequence = imeSubtypeListItem.mImeName;
      CharSequence charSequence2 = imeSubtypeListItem.mSubtypeName;
      TextView textView = (TextView) view.findViewById(android.R.id.text1);
      TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
      clearSettingButton(view);
      if (TextUtils.isEmpty(charSequence2)) {
        textView.setText(charSequence);
        textView2.setVisibility(8);
      } else if (KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(
          imeSubtypeListItem.mImi.getPackageName())) {
        textView.setText(charSequence);
        textView2.setVisibility(8);
        if (!isKidsModeRunning()) {
          addSettingButton(view);
        }
      } else {
        textView.setText(charSequence2);
        textView2.setText(charSequence);
        textView2.setVisibility(0);
      }
      ((RadioButton) view.findViewById(android.R.id.sequential)).setChecked(i == this.mCheckedItem);
      return view;
    }

    public void setUserId(int i) {
      this.mUserId = i;
    }

    public final void clearSettingButton(View view) {
      LinearLayout linearLayout = (LinearLayout) view.findViewById(1452);
      if (linearLayout != null) {
        ((LinearLayout) view).removeView(linearLayout);
      }
    }

    public final void addSettingButton(View view) {
      if (((UserManager) getContext().getSystemService("user")).isUserUnlocked()) {
        KeyguardManager keyguardManager =
            (KeyguardManager) getContext().getSystemService("keyguard");
        if (keyguardManager != null
            && keyguardManager.isKeyguardSecure()
            && keyguardManager.isKeyguardLocked()) {
          return;
        }
        view.setPaddingRelative(
            view.getPaddingLeft(), view.getPaddingTop(), 0, view.getPaddingBottom());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(1452);
        Drawable drawable = getContext().getResources().getDrawable(17304199, null);
        drawable.setTint(
            getContext()
                .getResources()
                .getColor(
                    android.R.color.language_picker_item_text_color_secondary_selected, null));
        ImageView imageView = new ImageView(getContext());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int dimensionPixelSize =
            getContext()
                .getResources()
                .getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_43);
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(drawable);
        imageView.setBackground(
            getContext()
                .getResources()
                .getDrawable(android.R.drawable.quickcontact_badge_overlay_light));
        linearLayout.addView(imageView);
        linearLayout.setGravity(17);
        CharSequence string = getContext().getResources().getString(17042509);
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.width =
            getContext()
                .getResources()
                .getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_44);
        layoutParams2.height = -1;
        linearLayout.setLayoutParams(layoutParams2);
        linearLayout.setContentDescription(string);
        boolean isSettingButtonEnabled = isSettingButtonEnabled(getContext());
        linearLayout.setClickable(isSettingButtonEnabled);
        linearLayout.setEnabled(isSettingButtonEnabled);
        linearLayout.setFocusable(false);
        linearLayout.setOnClickListener(
            new View
                .OnClickListener() { // from class:
                                     // com.android.server.inputmethod.InputMethodMenuController$ImeSubtypeListAdapter$$ExternalSyntheticLambda0
              @Override // android.view.View.OnClickListener
              public final void onClick(View view2) {
                InputMethodMenuController.ImeSubtypeListAdapter.this.lambda$addSettingButton$0(
                    view2);
              }
            });
        ((LinearLayout) view).addView(linearLayout);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addSettingButton$0(View view) {
      InputMethodManagerInternal.get().hideCurrentInputMethod(40);
      ComponentName componentName =
          new ComponentName(
              KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME,
              "com.samsung.android.honeyboard.settings.common.HoneyBoardSettingsActivity");
      Intent intent = new Intent("android.intent.action.MAIN");
      intent.addCategory("android.intent.category.LAUNCHER");
      intent.setComponent(componentName);
      intent.setFlags(872448000);
      intent.putExtra("switcher_setting", true);
      getContext().startActivityAsUser(intent, null, UserHandle.of(this.mUserId));
      getContext()
          .sendBroadcastAsUser(
              new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"),
              UserHandle.of(this.mUserId));
    }

    public final boolean isKidsModeRunning() {
      try {
        ComponentName componentName =
            new ComponentName(
                "com.sec.android.app.kidshome",
                "com.sec.android.app.kidshome.apps.ui.AppsActivity");
        PackageManager packageManager = getContext().getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ActivityInfo activityInfo = packageManager.resolveActivity(intent, 65536).activityInfo;
        return new ComponentName(activityInfo.packageName, activityInfo.name).equals(componentName);
      } catch (Exception unused) {
        Slog.e(InputMethodMenuController.TAG, "isKidsModeRunning: Exception is occurred.");
        return false;
      }
    }

    public final boolean isSettingButtonEnabled(Context context) {
      boolean z = true;
      if (!isHoneyboardSupported()) {
        return true;
      }
      try {
        Cursor query =
            context
                .getContentResolver()
                .query(
                    Uri.parse(KnoxCustomManagerService.SAMSUNG_HONEYBOARD_KEYPAD_SETTINGS_PROVIDER),
                    null,
                    null,
                    new String[] {KnoxCustomManagerService.KEYBOARD_SETTING_ENABLE},
                    null);
        if (query != null) {
          try {
            if (query.getCount() > 0 && query.moveToFirst()) {
              ArrayList arrayList = new ArrayList();
              ContentValues contentValues = new ContentValues();
              DatabaseUtils.cursorRowToContentValues(query, contentValues);
              arrayList.add(contentValues);
              Boolean asBoolean =
                  ((ContentValues) arrayList.get(0))
                      .getAsBoolean(KnoxCustomManagerService.KEYBOARD_SETTING_ENABLE);
              if (asBoolean != null) {
                z = asBoolean.booleanValue();
              }
            }
          } finally {
          }
        }
        if (query != null) {
          query.close();
        }
      } catch (Exception unused) {
        Slog.e(InputMethodMenuController.TAG, "isSettingButtonEnabled: Exception is occurred.");
      }
      return z;
    }

    public final boolean isHoneyboardSupported() {
      String string =
          SemFloatingFeature.getInstance()
              .getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME");
      boolean equals = KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(string);
      Slog.i(
          InputMethodMenuController.TAG,
          "isHoneyboardSupported: supported=" + equals + " packageName=" + string);
      return equals;
    }
  }

  public final void setupSwitchHardKeyboardAndShowKeyboardButton(
      Context context, LayoutInflater layoutInflater) {
    View inflate = layoutInflater.inflate(17367472, (ViewGroup) null);
    this.mSwitchInSelectDialogView = inflate;
    View findViewById = inflate.findViewById(android.R.id.icon_menu);
    boolean z = false;
    findViewById.setVisibility(this.mService.isAccessoryKeyboard() != 0 ? 0 : 8);
    final Switch r0 =
        (Switch) this.mSwitchInSelectDialogView.findViewById(android.R.id.icon_menu_presenter);
    r0.setChecked(this.mShowImeWithHardKeyboard);
    r0.setChecked(this.mService.getShowImeWithHardKeyboardValue());
    r0.semSetSamsungBasicInteraction();
    r0.setOnCheckedChangeListener(
        new CompoundButton
            .OnCheckedChangeListener() { // from class:
                                         // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda6
          @Override // android.widget.CompoundButton.OnCheckedChangeListener
          public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
            InputMethodMenuController.this.lambda$setupSwitchHardKeyboardAndShowKeyboardButton$6(
                compoundButton, z2);
          }
        });
    findViewById.setOnClickListener(
        new View
            .OnClickListener() { // from class:
                                 // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda7
          @Override // android.view.View.OnClickListener
          public final void onClick(View view) {
            r0.toggle();
          }
        });
    View findViewById2 = this.mSwitchInSelectDialogView.findViewById(android.R.id.voice);
    boolean isVisibleShowKeyboardButton =
        this.mSemImcUtil.isVisibleShowKeyboardButton(
            context, this.mService.isDeskTopMode(), isScreenLocked());
    findViewById2.setVisibility(isVisibleShowKeyboardButton ? 0 : 8);
    TextView textView =
        (TextView) this.mSwitchInSelectDialogView.findViewById(android.R.id.locale_native);
    String format =
        String.format(textView.getText().toString(), context.getResources().getString(17042977));
    textView.setContentDescription(format);
    textView.setText(this.mSemImcUtil.applyStringWithIcon(context.getResources()));
    textView.semSetHoverPopupType(1);
    SemHoverPopupWindow semGetHoverPopup = textView.semGetHoverPopup(true);
    if (semGetHoverPopup != null) {
      semGetHoverPopup.setGravity(12849);
      semGetHoverPopup.setContent(format);
    }
    final Switch r1 =
        (Switch) this.mSwitchInSelectDialogView.findViewById(android.R.id.voiceTrigger);
    r1.semSetSamsungBasicInteraction();
    r1.setEnabled(
        !this.mSemImcUtil.disableShowKeyboardButtonSwitch(context, isVisibleShowKeyboardButton));
    if (isVisibleShowKeyboardButton && this.mSettings.isShowKeyboardButton()) {
      z = true;
    }
    r1.setChecked(z);
    r1.setOnCheckedChangeListener(
        new CompoundButton
            .OnCheckedChangeListener() { // from class:
                                         // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda8
          @Override // android.widget.CompoundButton.OnCheckedChangeListener
          public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
            InputMethodMenuController.this.lambda$setupSwitchHardKeyboardAndShowKeyboardButton$8(
                compoundButton, z2);
          }
        });
    findViewById2.setOnClickListener(
        new View
            .OnClickListener() { // from class:
                                 // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda9
          @Override // android.view.View.OnClickListener
          public final void onClick(View view) {
            r1.toggle();
          }
        });
    if (this.mService.isHWAccessoryKeyboard() || isVisibleShowKeyboardButton) {
      this.mDialogBuilder.setView(this.mSwitchInSelectDialogView);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setupSwitchHardKeyboardAndShowKeyboardButton$6(
      CompoundButton compoundButton, boolean z) {
    this.mService.setShowImeWithHardKeyboardValue(z);
    hideInputMethodMenu();
    this.mSemImcUtil.sendSALogging(
        compoundButton.getContext(),
        "com.samsung.android.intent.action.ONSCREENKEYBOARD_SWITCH",
        "switch_checked",
        Boolean.valueOf(z));
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setupSwitchHardKeyboardAndShowKeyboardButton$8(
      CompoundButton compoundButton, boolean z) {
    this.mSettings.setShowKeyboardButton(z);
    hideInputMethodMenu();
    this.mSemImcUtil.sendSALogging(
        compoundButton.getContext(),
        "com.samsung.android.intent.action.KEYBOARDBUTTON_SWITCH",
        "switch_checked",
        Boolean.valueOf(z));
  }

  public final boolean isSamsungIme(InputMethodInfo inputMethodInfo) {
    if (inputMethodInfo == null) {
      return false;
    }
    return "com.samsung.android.honeyboard/.service.HoneyBoardService"
        .equals(inputMethodInfo.getId());
  }
}
