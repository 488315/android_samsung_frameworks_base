package android.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ResourceId;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.method.MovementMethod;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.internal.C4337R;
import com.android.internal.app.AlertController;

/* loaded from: classes.dex */
public class AlertDialog extends Dialog implements DialogInterface {
  public static final int LAYOUT_HINT_NONE = 0;
  public static final int LAYOUT_HINT_SIDE = 1;

  @Deprecated public static final int THEME_DEVICE_DEFAULT_DARK = 4;

  @Deprecated public static final int THEME_DEVICE_DEFAULT_LIGHT = 5;

  @Deprecated public static final int THEME_HOLO_DARK = 2;

  @Deprecated public static final int THEME_HOLO_LIGHT = 3;

  @Deprecated public static final int THEME_TRADITIONAL = 1;
  private AlertController mAlert;

  protected AlertDialog(Context context) {
    this(context, 0);
  }

  protected AlertDialog(
      Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
    this(context, 0);
    setCancelable(cancelable);
    setOnCancelListener(cancelListener);
  }

  protected AlertDialog(Context context, int themeResId) {
    this(context, themeResId, true);
  }

  AlertDialog(Context context, int themeResId, boolean createContextThemeWrapper) {
    super(
        context,
        createContextThemeWrapper ? resolveDialogTheme(context, themeResId) : 0,
        createContextThemeWrapper);
    this.mWindow.alwaysReadCloseOnTouchAttr();
    this.mAlert = AlertController.create(getContext(), this, getWindow());
  }

  static int resolveDialogTheme(Context context, int themeResId) {
    if (themeResId == 1) {
      return C4337R.style.Theme_Dialog_Alert;
    }
    if (themeResId == 2) {
      return C4337R.style.Theme_Holo_Dialog_Alert;
    }
    if (themeResId == 3) {
      return C4337R.style.Theme_Holo_Light_Dialog_Alert;
    }
    if (themeResId == 4) {
      return 16974545;
    }
    if (themeResId == 5) {
      return 16974546;
    }
    if (ResourceId.isValid(themeResId)) {
      return themeResId;
    }
    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDarkActionBar, outValue, false);
    if (outValue.data != 0) {
      mIsDarkActionBar = true;
    }
    context.getTheme().resolveAttribute(16843529, outValue, true);
    return outValue.resourceId;
  }

  public Button getButton(int whichButton) {
    return this.mAlert.getButton(whichButton);
  }

  public ListView getListView() {
    return this.mAlert.getListView();
  }

  @Override // android.app.Dialog
  public void setTitle(CharSequence title) {
    super.setTitle(title);
    this.mAlert.setTitle(title);
  }

  public void setCustomTitle(View customTitleView) {
    this.mAlert.setCustomTitle(customTitleView);
  }

  public void setMessage(CharSequence message) {
    this.mAlert.setMessage(message);
  }

  public void setMessageMovementMethod(MovementMethod movementMethod) {
    this.mAlert.setMessageMovementMethod(movementMethod);
  }

  public void setMessageHyphenationFrequency(int hyphenationFrequency) {
    this.mAlert.setMessageHyphenationFrequency(hyphenationFrequency);
  }

  public void setView(View view) {
    this.mAlert.setView(view);
  }

  public void setView(
      View view,
      int viewSpacingLeft,
      int viewSpacingTop,
      int viewSpacingRight,
      int viewSpacingBottom) {
    this.mAlert.setView(view, viewSpacingLeft, viewSpacingTop, viewSpacingRight, viewSpacingBottom);
  }

  void setButtonPanelLayoutHint(int layoutHint) {
    this.mAlert.setButtonPanelLayoutHint(layoutHint);
  }

  public void setButton(int whichButton, CharSequence text, Message msg) {
    this.mAlert.setButton(whichButton, text, null, msg);
  }

  public void setButton(
      int whichButton, CharSequence text, DialogInterface.OnClickListener listener) {
    this.mAlert.setButton(whichButton, text, listener, null);
  }

  @Deprecated
  public void setButton(CharSequence text, Message msg) {
    setButton(-1, text, msg);
  }

  @Deprecated
  public void setButton2(CharSequence text, Message msg) {
    setButton(-2, text, msg);
  }

  @Deprecated
  public void setButton3(CharSequence text, Message msg) {
    setButton(-3, text, msg);
  }

  @Deprecated
  public void setButton(CharSequence text, DialogInterface.OnClickListener listener) {
    setButton(-1, text, listener);
  }

  @Deprecated
  public void setButton2(CharSequence text, DialogInterface.OnClickListener listener) {
    setButton(-2, text, listener);
  }

  @Deprecated
  public void setButton3(CharSequence text, DialogInterface.OnClickListener listener) {
    setButton(-3, text, listener);
  }

  public void setIcon(int resId) {
    this.mAlert.setIcon(resId);
  }

  public void setIcon(Drawable icon) {
    this.mAlert.setIcon(icon);
  }

  public void setIconAttribute(int attrId) {
    TypedValue out = new TypedValue();
    this.mContext.getTheme().resolveAttribute(attrId, out, true);
    this.mAlert.setIcon(out.resourceId);
  }

  public void setInverseBackgroundForced(boolean forceInverseBackground) {
    this.mAlert.setInverseBackgroundForced(forceInverseBackground);
  }

  @Override // android.app.Dialog
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mAlert.installContent();
  }

  @Override // android.app.Dialog, android.view.KeyEvent.Callback
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (this.mAlert.onKeyDown(keyCode, event)) {
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override // android.app.Dialog, android.view.KeyEvent.Callback
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (this.mAlert.onKeyUp(keyCode, event)) {
      return true;
    }
    return super.onKeyUp(keyCode, event);
  }

  public static class Builder {

    /* renamed from: P */
    private final AlertController.AlertParams f18P;
    private boolean mIsDeviceDefault;
    private int mThemeResId;

    public Builder(Context context) {
      this(context, AlertDialog.resolveDialogTheme(context, 0));
    }

    public Builder(Context context, int themeResId) {
      this.f18P =
          new AlertController.AlertParams(
              new ContextThemeWrapper(
                  context, AlertDialog.resolveDialogTheme(context, themeResId)));
      TypedValue themeValue = new TypedValue();
      context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, themeValue, false);
      if (themeValue.data != 0) {
        this.mIsDeviceDefault = true;
      }
      this.mThemeResId = themeResId;
    }

    public Context getContext() {
      return this.f18P.mContext;
    }

    public Builder setTitle(int titleId) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mTitle = alertParams.mContext.getText(titleId);
      return this;
    }

    public Builder setTitle(CharSequence title) {
      this.f18P.mTitle = title;
      return this;
    }

    public Builder setCustomTitle(View customTitleView) {
      this.f18P.mCustomTitleView = customTitleView;
      return this;
    }

    public Builder setMessage(int messageId) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mMessage = alertParams.mContext.getText(messageId);
      return this;
    }

    public Builder setMessage(CharSequence message) {
      this.f18P.mMessage = message;
      return this;
    }

    public Builder setIcon(int iconId) {
      this.f18P.mIconId = iconId;
      return this;
    }

    public Builder setIcon(Drawable icon) {
      this.f18P.mIcon = icon;
      return this;
    }

    public Builder setIconAttribute(int attrId) {
      TypedValue out = new TypedValue();
      this.f18P.mContext.getTheme().resolveAttribute(attrId, out, true);
      this.f18P.mIconId = out.resourceId;
      return this;
    }

    public Builder setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mPositiveButtonText = alertParams.mContext.getText(textId);
      this.f18P.mPositiveButtonListener = listener;
      return this;
    }

    public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
      this.f18P.mPositiveButtonText = text;
      this.f18P.mPositiveButtonListener = listener;
      return this;
    }

    public Builder setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mNegativeButtonText = alertParams.mContext.getText(textId);
      this.f18P.mNegativeButtonListener = listener;
      return this;
    }

    public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
      this.f18P.mNegativeButtonText = text;
      this.f18P.mNegativeButtonListener = listener;
      return this;
    }

    public Builder setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mNeutralButtonText = alertParams.mContext.getText(textId);
      this.f18P.mNeutralButtonListener = listener;
      return this;
    }

    public Builder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
      this.f18P.mNeutralButtonText = text;
      this.f18P.mNeutralButtonListener = listener;
      return this;
    }

    public Builder setCancelable(boolean cancelable) {
      this.f18P.mCancelable = cancelable;
      return this;
    }

    public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
      this.f18P.mOnCancelListener = onCancelListener;
      return this;
    }

    public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
      this.f18P.mOnDismissListener = onDismissListener;
      return this;
    }

    public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
      this.f18P.mOnKeyListener = onKeyListener;
      return this;
    }

    public Builder setItems(int itemsId, DialogInterface.OnClickListener listener) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
      this.f18P.mOnClickListener = listener;
      return this;
    }

    public Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
      this.f18P.mItems = items;
      this.f18P.mOnClickListener = listener;
      return this;
    }

    public Builder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
      this.f18P.mAdapter = adapter;
      this.f18P.mOnClickListener = listener;
      return this;
    }

    public Builder setCursor(
        Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
      this.f18P.mCursor = cursor;
      this.f18P.mLabelColumn = labelColumn;
      this.f18P.mOnClickListener = listener;
      return this;
    }

    public Builder setMultiChoiceItems(
        int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
      this.f18P.mOnCheckboxClickListener = listener;
      this.f18P.mCheckedItems = checkedItems;
      this.f18P.mIsMultiChoice = true;
      return this;
    }

    public Builder setMultiChoiceItems(
        CharSequence[] items,
        boolean[] checkedItems,
        DialogInterface.OnMultiChoiceClickListener listener) {
      this.f18P.mItems = items;
      this.f18P.mOnCheckboxClickListener = listener;
      this.f18P.mCheckedItems = checkedItems;
      this.f18P.mIsMultiChoice = true;
      return this;
    }

    public Builder setMultiChoiceItems(
        Cursor cursor,
        String isCheckedColumn,
        String labelColumn,
        DialogInterface.OnMultiChoiceClickListener listener) {
      this.f18P.mCursor = cursor;
      this.f18P.mOnCheckboxClickListener = listener;
      this.f18P.mIsCheckedColumn = isCheckedColumn;
      this.f18P.mLabelColumn = labelColumn;
      this.f18P.mIsMultiChoice = true;
      return this;
    }

    public Builder setSingleChoiceItems(
        int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
      AlertController.AlertParams alertParams = this.f18P;
      alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
      this.f18P.mOnClickListener = listener;
      this.f18P.mCheckedItem = checkedItem;
      this.f18P.mIsSingleChoice = true;
      return this;
    }

    public Builder setSingleChoiceItems(
        Cursor cursor,
        int checkedItem,
        String labelColumn,
        DialogInterface.OnClickListener listener) {
      this.f18P.mCursor = cursor;
      this.f18P.mOnClickListener = listener;
      this.f18P.mCheckedItem = checkedItem;
      this.f18P.mLabelColumn = labelColumn;
      this.f18P.mIsSingleChoice = true;
      return this;
    }

    public Builder setSingleChoiceItems(
        CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
      this.f18P.mItems = items;
      this.f18P.mOnClickListener = listener;
      this.f18P.mCheckedItem = checkedItem;
      this.f18P.mIsSingleChoice = true;
      return this;
    }

    public Builder setSingleChoiceItems(
        ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
      this.f18P.mAdapter = adapter;
      this.f18P.mOnClickListener = listener;
      this.f18P.mCheckedItem = checkedItem;
      this.f18P.mIsSingleChoice = true;
      return this;
    }

    public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
      this.f18P.mOnItemSelectedListener = listener;
      return this;
    }

    public Builder setView(int layoutResId) {
      this.f18P.mView = null;
      this.f18P.mViewLayoutResId = layoutResId;
      this.f18P.mViewSpacingSpecified = false;
      return this;
    }

    public Builder setView(View view) {
      this.f18P.mView = view;
      this.f18P.mViewLayoutResId = 0;
      this.f18P.mViewSpacingSpecified = false;
      return this;
    }

    @Deprecated
    public Builder setView(
        View view,
        int viewSpacingLeft,
        int viewSpacingTop,
        int viewSpacingRight,
        int viewSpacingBottom) {
      this.f18P.mView = view;
      this.f18P.mViewLayoutResId = 0;
      this.f18P.mViewSpacingSpecified = true;
      this.f18P.mViewSpacingLeft = viewSpacingLeft;
      this.f18P.mViewSpacingTop = viewSpacingTop;
      this.f18P.mViewSpacingRight = viewSpacingRight;
      this.f18P.mViewSpacingBottom = viewSpacingBottom;
      return this;
    }

    @Deprecated
    public Builder setInverseBackgroundForced(boolean useInverseBackground) {
      this.f18P.mForceInverseBackground = useInverseBackground;
      return this;
    }

    public Builder setRecycleOnMeasureEnabled(boolean enabled) {
      this.f18P.mRecycleOnMeasure = enabled;
      return this;
    }

    public AlertDialog create() {
      AlertDialog dialog =
          new AlertDialog(this.f18P.mContext, this.mIsDeviceDefault ? this.mThemeResId : 0, false);
      this.f18P.apply(dialog.mAlert);
      dialog.setCancelable(this.f18P.mCancelable);
      if (this.f18P.mCancelable) {
        dialog.setCanceledOnTouchOutside(true);
      }
      dialog.setOnCancelListener(this.f18P.mOnCancelListener);
      dialog.setOnDismissListener(this.f18P.mOnDismissListener);
      if (this.f18P.mOnKeyListener != null) {
        dialog.setOnKeyListener(this.f18P.mOnKeyListener);
      }
      return dialog;
    }

    public AlertDialog show() {
      AlertDialog dialog = create();
      dialog.show();
      return dialog;
    }
  }
}
