package com.android.internal.app;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.flags.Flags;
import com.android.internal.R;
import com.samsung.android.rune.CoreRune;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class AlertController {
    public static final int MICRO = 1;
    private static final long WEAR_MATERIAL3_ALERTDIALOG = 379365266;
    private static boolean sHasPaddingBottomInCustom = false;
    private static boolean sUseWearMaterial3Style;
    private ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private BlurEffect mBlurEffect;
    private Button mButtonNegative;
    private Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    private Button mButtonNeutral;
    private Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelSideLayout;
    private Button mButtonPositive;
    private Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private final Context mContext;
    private View mCustomTitleView;
    private final DialogInterface mDialogInterface;
    private boolean mForceInverseBackground;
    private Handler mHandler;
    private Drawable mIcon;
    private ImageView mIconView;
    private int mLastOrientation;
    private int mListItemLayout;
    private int mListLayout;
    protected ListView mListView;
    protected CharSequence mMessage;
    private Integer mMessageHyphenationFrequency;
    private MovementMethod mMessageMovementMethod;
    protected TextView mMessageView;
    private int mMultiChoiceItemLayout;
    protected ScrollView mScrollView;
    private boolean mShowTitle;
    private int mSingleChoiceItemLayout;
    private final boolean mThemeIsDeviceDefault;
    private boolean mThemeIsDeviceDefaultDark;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private int mViewSpacingTop;
    protected final Window mWindow;
    private boolean mViewSpacingSpecified = false;
    private int mIconId = 0;
    private int mCheckedItem = -1;
    private int mButtonPanelLayoutHint = 0;
    private boolean mIsBlurEnabled = false;
    private boolean mIsDefaultBlurEnabled = true;
    private boolean mIsItemChoiceLayout = false;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() { // from class: com.android.internal.app.AlertController.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Message messageObtain;
            if (view == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null) {
                messageObtain = Message.obtain(AlertController.this.mButtonPositiveMessage);
            } else if (view == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null) {
                messageObtain = Message.obtain(AlertController.this.mButtonNegativeMessage);
            } else {
                messageObtain = (view != AlertController.this.mButtonNeutral || AlertController.this.mButtonNeutralMessage == null) ? null : Message.obtain(AlertController.this.mButtonNeutralMessage);
            }
            if (messageObtain != null) {
                messageObtain.sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialogInterface).sendToTarget();
        }
    };

    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == -3 || i == -2 || i == -1) {
                ((DialogInterface.OnClickListener) message.obj).onClick(this.mDialog.get(), message.what);
            } else {
                if (i != 1) {
                    return;
                }
                ((DialogInterface) message.obj).dismiss();
            }
        }
    }

    private static boolean shouldCenterSingleButton(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public static final AlertController create(Context context, DialogInterface dialogInterface, Window window) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 16974371);
        int i = typedArrayObtainStyledAttributes.getInt(12, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (i == 1) {
            return new MicroAlertController(context, dialogInterface, window);
        }
        return new AlertController(context, dialogInterface, window);
    }

    protected AlertController(Context context, DialogInterface dialogInterface, Window window) {
        this.mContext = context;
        this.mDialogInterface = dialogInterface;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(dialogInterface);
        this.mBlurEffect = new BlurEffect(context);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        boolean z = typedValue.data != 0;
        this.mThemeIsDeviceDefault = z;
        if (z) {
            TypedValue typedValue2 = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue2, true);
            this.mThemeIsDeviceDefaultDark = typedValue2.data != 0;
        }
        this.mLastOrientation = context.getResources().getConfiguration().orientation;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.AlertDialog, getAlertDialogDefStyleAttr(context), getAlertDialogDefStyleRes());
        this.mAlertDialogLayout = typedArrayObtainStyledAttributes.getResourceId(10, R.layout.alert_dialog);
        this.mButtonPanelSideLayout = typedArrayObtainStyledAttributes.getResourceId(11, 0);
        this.mListLayout = typedArrayObtainStyledAttributes.getResourceId(15, R.layout.select_dialog);
        this.mMultiChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(16, 17367059);
        this.mSingleChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(21, 17367058);
        this.mListItemLayout = typedArrayObtainStyledAttributes.getResourceId(14, 17367057);
        this.mShowTitle = typedArrayObtainStyledAttributes.getBoolean(20, true);
        typedArrayObtainStyledAttributes.recycle();
        window.requestFeature(1);
    }

    private int getAlertDialogDefStyleAttr(Context context) {
        boolean zUseWearMaterial3Style = useWearMaterial3Style(context);
        sUseWearMaterial3Style = zUseWearMaterial3Style;
        return zUseWearMaterial3Style ? 0 : 16842845;
    }

    private int getAlertDialogDefStyleRes() {
        if (sUseWearMaterial3Style) {
            return R.style.AlertDialog_Material3;
        }
        return 0;
    }

    private static boolean useWearMaterial3Style(Context context) {
        if (Flags.useWearMaterial3Ui() && CompatChanges.isChangeEnabled(WEAR_MATERIAL3_ALERTDIALOG) && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            return context.getThemeResId() == 16974120 || context.getThemeResId() == 16974545;
        }
        return false;
    }

    static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    public void installContent(AlertParams alertParams) {
        alertParams.apply(this);
        installContent();
    }

    public void installContent() {
        this.mWindow.setContentView(selectContentView());
        setupView();
    }

    private int selectContentView() {
        int i = this.mButtonPanelSideLayout;
        if (i == 0) {
            return this.mAlertDialogLayout;
        }
        return this.mButtonPanelLayoutHint == 1 ? i : this.mAlertDialogLayout;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.lambda$setTextAsync$0(charSequence);
        }
        this.mWindow.setTitle(charSequence);
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.lambda$setTextAsync$0(charSequence);
        }
    }

    public void setMessageMovementMethod(MovementMethod movementMethod) {
        this.mMessageMovementMethod = movementMethod;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setMovementMethod(movementMethod);
        }
    }

    public void setMessageHyphenationFrequency(int i) {
        this.mMessageHyphenationFrequency = Integer.valueOf(i);
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setHyphenationFrequency(i);
        }
    }

    public void setView(int i) {
        this.mView = null;
        this.mViewLayoutResId = i;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = i;
        this.mViewSpacingTop = i2;
        this.mViewSpacingRight = i3;
        this.mViewSpacingBottom = i4;
    }

    public void setButtonPanelLayoutHint(int i) {
        this.mButtonPanelLayoutHint = i;
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener);
        }
        if (i == -3) {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = message;
        } else if (i == -2) {
            this.mButtonNegativeText = charSequence;
            this.mButtonNegativeMessage = message;
        } else {
            if (i == -1) {
                this.mButtonPositiveText = charSequence;
                this.mButtonPositiveMessage = message;
                return;
            }
            throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setIcon(int i) {
        this.mIcon = null;
        this.mIconId = i;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (i != 0) {
                imageView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (drawable != null) {
                imageView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    public int getIconAttributeResId(int i) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public void setInverseBackgroundForced(boolean z) {
        this.mForceInverseBackground = z;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public Button getButton(int i) {
        if (i == -3) {
            return this.mButtonNeutral;
        }
        if (i == -2) {
            return this.mButtonNegative;
        }
        if (i != -1) {
            return null;
        }
        return this.mButtonPositive;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        ScrollView scrollView = this.mScrollView;
        return scrollView != null && scrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        ScrollView scrollView = this.mScrollView;
        return scrollView != null && scrollView.executeKeyEvent(keyEvent);
    }

    private ViewGroup resolvePanel(View view, View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupView() throws Resources.NotFoundException {
        View view;
        ViewGroup viewGroup;
        boolean z;
        View view2;
        boolean z2;
        View viewFindViewById;
        View viewFindViewById2;
        View viewFindViewById3;
        final View viewFindViewById4 = this.mWindow.findViewById(R.id.parentPanel);
        View viewFindViewById5 = this.mWindow.findViewById(R.id.middlePanel);
        if (this.mThemeIsDeviceDefault) {
            viewFindViewById4.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.internal.app.AlertController$$ExternalSyntheticLambda2
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    this.f$0.lambda$setupView$1(viewFindViewById4, view3, i, i2, i3, i4, i5, i6, i7, i8);
                }
            });
        }
        View viewFindViewById6 = viewFindViewById4.findViewById(R.id.topPanel);
        View viewFindViewById7 = viewFindViewById4.findViewById(R.id.contentPanel);
        View viewFindViewById8 = viewFindViewById4.findViewById(R.id.buttonPanel);
        ViewGroup viewGroup2 = (ViewGroup) viewFindViewById4.findViewById(R.id.customPanel);
        setupCustomContent(viewGroup2);
        View viewFindViewById9 = viewGroup2.findViewById(R.id.topPanel);
        View viewFindViewById10 = viewGroup2.findViewById(R.id.contentPanel);
        View viewFindViewById11 = viewGroup2.findViewById(R.id.buttonPanel);
        ViewGroup viewGroupResolvePanel = resolvePanel(viewFindViewById9, viewFindViewById6);
        ViewGroup viewGroupResolvePanel2 = resolvePanel(viewFindViewById10, viewFindViewById7);
        ViewGroup viewGroupResolvePanel3 = resolvePanel(viewFindViewById11, viewFindViewById8);
        setupContent(viewGroupResolvePanel2);
        setupButtons(viewGroupResolvePanel3);
        setupTitle(viewGroupResolvePanel);
        if (viewGroup2 == null || viewGroup2.getVisibility() == 8) {
            view = viewFindViewById7;
            viewGroup = viewGroup2;
            z = false;
        } else {
            view = viewFindViewById7;
            viewGroup = viewGroup2;
            z = true;
        }
        boolean z3 = (viewGroupResolvePanel == null || viewGroupResolvePanel.getVisibility() == 8) ? 0 : 1;
        if (viewGroupResolvePanel3 == null || viewGroupResolvePanel3.getVisibility() == 8) {
            view2 = view;
            z2 = false;
        } else {
            view2 = view;
            z2 = true;
        }
        boolean z4 = (viewFindViewById6 == null || viewFindViewById6.getVisibility() == 8) ? false : true;
        boolean z5 = (view2 == null || view2.getVisibility() == 8) ? false : true;
        View view3 = this.mCustomTitleView;
        boolean z6 = (view3 == null || view3.getVisibility() == 8) ? false : true;
        if (this.mThemeIsDeviceDefault) {
            if ((z && !z4 && !z5) || z6) {
                semAdjustParentPanelPadding(viewFindViewById5);
            }
            if (z && z4 && !z5) {
                semAdjustTopPanelPadding(viewFindViewById4);
            }
            if (!z && z3 != 0 && this.mIsItemChoiceLayout) {
                semAdjustContentPanelPadding(viewGroupResolvePanel2);
            }
        }
        if (!viewFindViewById4.isInTouchMode()) {
            if (!requestFocusForContent(z ? viewGroup : viewGroupResolvePanel2)) {
                requestFocusForDefaultButton();
            }
        }
        sHasPaddingBottomInCustom = z && this.mThemeIsDeviceDefault;
        if (!z2) {
            if (viewGroupResolvePanel2 != null && (viewFindViewById3 = viewGroupResolvePanel2.findViewById(R.id.textSpacerNoButtons)) != null) {
                viewFindViewById3.setVisibility(0);
            }
            this.mWindow.setCloseOnTouchOutsideIfNotSet(true);
        }
        if (this.mThemeIsDeviceDefault) {
            semSetupButtonsPadding();
        }
        if (z3 != 0) {
            ScrollView scrollView = this.mScrollView;
            if (scrollView != null) {
                scrollView.setClipToPadding(true);
            }
            if (this.mMessage != null || this.mListView != null || z) {
                viewFindViewById2 = !z ? viewGroupResolvePanel.findViewById(R.id.titleDividerNoCustom) : null;
                if (viewFindViewById2 == null) {
                    viewFindViewById2 = viewGroupResolvePanel.findViewById(R.id.titleDivider);
                }
            } else {
                viewFindViewById2 = viewGroupResolvePanel.findViewById(R.id.titleDividerTop);
            }
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(0);
            }
        } else if (viewGroupResolvePanel2 != null && (viewFindViewById = viewGroupResolvePanel2.findViewById(R.id.textSpacerNoTitle)) != null) {
            viewFindViewById.setVisibility(0);
        }
        ListView listView = this.mListView;
        if (listView instanceof RecycleListView) {
            ((RecycleListView) listView).setHasDecor(z3, z2);
        }
        if (!z) {
            View view4 = this.mListView;
            if (view4 == null) {
                view4 = this.mScrollView;
            }
            if (view4 != null) {
                int i = (z2 ? 2 : 0) | z3;
                if (this.mIsItemChoiceLayout) {
                    View viewFindViewById12 = this.mWindow.findViewById(R.id.sem_scrollIndicatorUp);
                    if (viewFindViewById12 != null && z3 != 0) {
                        viewFindViewById12.setVisibility(0);
                    }
                    view4.setScrollIndicators(i, 2);
                } else {
                    view4.setScrollIndicators(i, 3);
                }
            }
        }
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        setBackground(typedArrayObtainStyledAttributes, viewGroupResolvePanel, viewGroupResolvePanel2, viewGroup, viewGroupResolvePanel3, z3, z, z2);
        if (this.mThemeIsDeviceDefault && this.mWindow.getAttributes().type != 2011) {
            this.mWindow.setElevation(this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_alert_dialog_window_elevation));
        }
        if (this.mThemeIsDeviceDefault) {
            boolean z7 = z ? this.mIsBlurEnabled : this.mIsDefaultBlurEnabled;
            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.tw_dialog_background_material, this.mContext.getTheme());
            View decorView = this.mWindow.getDecorView();
            boolean z8 = decorView == null || decorView.getBackground() == null || drawable.getConstantState() == null || drawable.getConstantState().equals(decorView.getBackground().getConstantState());
            boolean z9 = Settings.System.getString(this.mContext.getContentResolver(), "current_sec_active_themepackage") != null;
            if (viewFindViewById4 != null && CoreRune.FW_WINDOW_BLUR_SUPPORTED && z7 && !z9 && this.mWindow.getAttributes().type != 2011 && z8) {
                if (viewFindViewById5 != null && viewFindViewById5.getBackground() == null && this.mThemeIsDeviceDefaultDark) {
                    viewFindViewById5.setBackground(this.mContext.getResources().getDrawable(R.drawable.tw_dialog_middle_panel_background_material));
                }
                this.mBlurEffect.setWindowBlur(viewFindViewById4, this.mContext.getColor(R.color.sem_dialog_panel_bg_color_blur), this.mContext.getResources().getDimension(R.dimen.sem_dialog_background_corner_radius));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupView$1(final View view, View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        view2.post(new Runnable() { // from class: com.android.internal.app.AlertController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setupView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupView$0(View view) {
        if (this.mContext.getResources().getConfiguration().orientation != this.mLastOrientation) {
            semSetupPaddings();
            view.requestLayout();
        }
        this.mLastOrientation = this.mContext.getResources().getConfiguration().orientation;
    }

    private boolean requestFocusForContent(View view) {
        if (view != null && view.requestFocus()) {
            return true;
        }
        ListView listView = this.mListView;
        if (listView == null) {
            return false;
        }
        listView.setSelection(0);
        return true;
    }

    private void requestFocusForDefaultButton() {
        if (this.mButtonPositive.getVisibility() == 0) {
            this.mButtonPositive.requestFocus();
        } else if (this.mButtonNegative.getVisibility() == 0) {
            this.mButtonNegative.requestFocus();
        } else if (this.mButtonNeutral.getVisibility() == 0) {
            this.mButtonNeutral.requestFocus();
        }
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        View viewInflate = this.mView;
        if (viewInflate == null) {
            viewInflate = this.mViewLayoutResId != 0 ? LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null;
        }
        boolean z = viewInflate != null;
        if (!z || !canTextInput(viewInflate)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (z) {
            FrameLayout frameLayout = (FrameLayout) this.mWindow.findViewById(16908331);
            frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
            if (this.mViewSpacingSpecified) {
                frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
            if (this.mListView != null) {
                ((LinearLayout.LayoutParams) viewGroup.getLayoutParams()).weight = 0.0f;
                return;
            }
            return;
        }
        viewGroup.setVisibility(8);
    }

    protected void setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null && this.mShowTitle) {
            viewGroup.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (ImageView) this.mWindow.findViewById(16908294);
        if (!TextUtils.isEmpty(this.mTitle) && this.mShowTitle) {
            TextView textView = (TextView) this.mWindow.findViewById(R.id.alertTitle);
            this.mTitleView = textView;
            textView.lambda$setTextAsync$0(this.mTitle);
            semCheckMaxFontScale(this.mTitleView, this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_dialog_title_text_size));
            int i = this.mIconId;
            if (i != 0) {
                this.mIconView.setImageResource(i);
                return;
            }
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                this.mIconView.setImageDrawable(drawable);
                return;
            } else {
                this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                this.mIconView.setVisibility(8);
                return;
            }
        }
        this.mWindow.findViewById(R.id.title_template).setVisibility(8);
        this.mIconView.setVisibility(8);
        viewGroup.setVisibility(8);
    }

    protected void setupContent(ViewGroup viewGroup) throws Resources.NotFoundException {
        ScrollView scrollView = (ScrollView) viewGroup.findViewById(R.id.scrollView);
        this.mScrollView = scrollView;
        scrollView.setFocusable(false);
        TextView textView = (TextView) viewGroup.findViewById(16908299);
        this.mMessageView = textView;
        if (textView == null) {
            return;
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            textView.lambda$setTextAsync$0(charSequence);
            semCheckMaxFontScale(this.mMessageView, this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_dialog_body_text_size));
            MovementMethod movementMethod = this.mMessageMovementMethod;
            if (movementMethod != null) {
                this.mMessageView.setMovementMethod(movementMethod);
            }
            Integer num = this.mMessageHyphenationFrequency;
            if (num != null) {
                this.mMessageView.setHyphenationFrequency(num.intValue());
                return;
            }
            return;
        }
        textView.setVisibility(8);
        this.mScrollView.removeView(this.mMessageView);
        if (this.mListView != null) {
            ViewGroup viewGroup2 = (ViewGroup) this.mScrollView.getParent();
            int iIndexOfChild = viewGroup2.indexOfChild(this.mScrollView);
            viewGroup2.removeViewAt(iIndexOfChild);
            viewGroup2.addView(this.mListView, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
            return;
        }
        viewGroup.setVisibility(8);
    }

    private static void manageScrollIndicators(View view, View view2, View view3) {
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4);
        }
        if (view3 != null) {
            view3.setVisibility(view.canScrollVertically(1) ? 0 : 4);
        }
    }

    protected void setupButtons(ViewGroup viewGroup) throws Resources.NotFoundException {
        int i;
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "show_button_background", 0) == 1;
        Button button = (Button) viewGroup.findViewById(16908313);
        this.mButtonPositive = button;
        button.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
            i = 0;
        } else {
            this.mButtonPositive.lambda$setTextAsync$0(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            i = 1;
        }
        Button button2 = (Button) viewGroup.findViewById(16908314);
        this.mButtonNegative = button2;
        button2.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.lambda$setTextAsync$0(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            i |= 2;
        }
        Button button3 = (Button) viewGroup.findViewById(16908315);
        this.mButtonNeutral = button3;
        button3.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.lambda$setTextAsync$0(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            i |= 4;
        }
        if (this.mThemeIsDeviceDefault) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(16842801, typedValue, true);
            if (typedValue.resourceId > 0) {
                int color = this.mContext.getResources().getColor(typedValue.resourceId);
                this.mButtonPositive.semSetButtonShapeEnabled(z, color);
                this.mButtonNegative.semSetButtonShapeEnabled(z, color);
                this.mButtonNeutral.semSetButtonShapeEnabled(z, color);
            } else {
                this.mButtonPositive.semSetButtonShapeEnabled(z);
                this.mButtonNegative.semSetButtonShapeEnabled(z);
                this.mButtonNeutral.semSetButtonShapeEnabled(z);
            }
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (i == 1) {
                centerButton(this.mButtonPositive);
            } else if (i == 2) {
                centerButton(this.mButtonNegative);
            } else if (i == 4) {
                centerButton(this.mButtonNeutral);
            }
        }
        if (i == 0) {
            viewGroup.setVisibility(8);
        }
        if (this.mThemeIsDeviceDefault) {
            View viewFindViewById = this.mWindow.findViewById(R.id.sem_divider1);
            View viewFindViewById2 = this.mWindow.findViewById(R.id.sem_divider2);
            boolean z2 = this.mButtonNeutral.getVisibility() == 0;
            boolean z3 = this.mButtonPositive.getVisibility() == 0;
            boolean z4 = this.mButtonNegative.getVisibility() == 0;
            if (viewFindViewById2 != null && ((z2 && z3) || (z2 && z4))) {
                viewFindViewById2.setVisibility(0);
            }
            if (viewFindViewById != null && z3 && z4) {
                viewFindViewById.setVisibility(0);
            }
        }
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
        View viewFindViewById = this.mWindow.findViewById(R.id.leftSpacer);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(0);
        }
        View viewFindViewById2 = this.mWindow.findViewById(R.id.rightSpacer);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(0);
        }
    }

    private void setBackground(TypedArray typedArray, View view, View view2, View view3, View view4, boolean z, boolean z2, boolean z3) throws Resources.NotFoundException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z4;
        int i10;
        ListAdapter listAdapter;
        boolean z5;
        if (typedArray.getBoolean(17, true)) {
            i = R.drawable.popup_full_dark;
            i2 = R.drawable.popup_top_dark;
            i3 = R.drawable.popup_center_dark;
            i4 = R.drawable.popup_bottom_dark;
            i5 = R.drawable.popup_full_bright;
            i6 = R.drawable.popup_top_bright;
            i7 = R.drawable.popup_center_bright;
            i8 = R.drawable.popup_bottom_bright;
            i9 = R.drawable.popup_bottom_medium;
        } else {
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
            i8 = 0;
            i9 = 0;
        }
        int resourceId = typedArray.getResourceId(5, i6);
        int resourceId2 = typedArray.getResourceId(1, i2);
        int resourceId3 = typedArray.getResourceId(6, i7);
        int resourceId4 = typedArray.getResourceId(2, i3);
        View[] viewArr = new View[4];
        boolean[] zArr = new boolean[4];
        if (z) {
            viewArr[0] = view;
            zArr[0] = false;
            i10 = 1;
            z4 = true;
        } else {
            z4 = true;
            i10 = 0;
        }
        viewArr[i10] = view2.getVisibility() == 8 ? null : view2;
        zArr[i10] = this.mListView != null ? z4 : false;
        int i11 = i10 + 1;
        if (z2) {
            viewArr[i11] = view3;
            zArr[i11] = this.mForceInverseBackground;
            i11 = i10 + 2;
        }
        if (z3) {
            viewArr[i11] = view4;
            zArr[i11] = z4;
        }
        View view5 = null;
        boolean z6 = false;
        int i12 = 0;
        boolean z7 = false;
        for (int i13 = 4; i12 < i13; i13 = 4) {
            View view6 = viewArr[i12];
            if (view6 == null) {
                view6 = view5;
                z5 = z6;
            } else {
                if (view5 != null) {
                    if (!z6) {
                        view5.setBackgroundResource(z7 ? resourceId : resourceId2);
                    } else {
                        view5.setBackgroundResource(z7 ? resourceId3 : resourceId4);
                    }
                    z5 = z4;
                } else {
                    z5 = z6;
                }
                z7 = zArr[i12];
            }
            i12++;
            z6 = z5;
            view5 = view6;
        }
        if (view5 != null) {
            if (z6) {
                int resourceId5 = typedArray.getResourceId(7, i8);
                int resourceId6 = typedArray.getResourceId(8, i9);
                int resourceId7 = typedArray.getResourceId(3, i4);
                if (!z7) {
                    resourceId5 = resourceId7;
                } else if (z3) {
                    resourceId5 = resourceId6;
                }
                view5.setBackgroundResource(resourceId5);
            } else {
                int resourceId8 = typedArray.getResourceId(4, i5);
                int resourceId9 = typedArray.getResourceId(0, i);
                if (!z7) {
                    resourceId8 = resourceId9;
                }
                view5.setBackgroundResource(resourceId8);
            }
        }
        ListView listView = this.mListView;
        if (listView == null || (listAdapter = this.mAdapter) == null) {
            return;
        }
        listView.setAdapter(listAdapter);
        if (View.sIsSamsungBasicInteraction) {
            listView.semSetBottomColor(0);
        }
        int i14 = this.mCheckedItem;
        if (i14 > -1) {
            listView.setItemChecked(i14, z4);
            listView.setSelectionFromTop(i14, typedArray.getDimensionPixelSize(19, 0));
        }
    }

    private void semSetupPaddings() {
        View viewFindViewById = this.mWindow.findViewById(R.id.parentPanel);
        View viewFindViewById2 = this.mWindow.findViewById(R.id.middlePanel);
        View viewFindViewById3 = viewFindViewById.findViewById(R.id.title_template);
        View viewFindViewById4 = viewFindViewById.findViewById(R.id.scrollView);
        View viewFindViewById5 = viewFindViewById.findViewById(R.id.sem_buttonBarLayout);
        View viewFindViewById6 = viewFindViewById.findViewById(R.id.contentPanel);
        Resources resources = this.mContext.getResources();
        ViewGroup viewGroup = (ViewGroup) viewFindViewById.findViewById(R.id.customPanel);
        View viewFindViewById7 = viewFindViewById.findViewById(R.id.topPanel);
        boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true;
        boolean z2 = (viewFindViewById7 == null || viewFindViewById7.getVisibility() == 8) ? false : true;
        boolean z3 = (viewFindViewById6 == null || viewFindViewById6.getVisibility() == 8) ? false : true;
        View view = this.mCustomTitleView;
        boolean z4 = (view == null || view.getVisibility() == 8) ? false : true;
        if (viewFindViewById2 != null) {
            if ((z && !z2 && !z3) || z4) {
                viewFindViewById2.setPadding(0, 0, 0, 0);
            } else {
                viewFindViewById2.setPadding(0, resources.getDimensionPixelSize(R.dimen.sem_dialog_title_padding_top), 0, 0);
            }
        }
        if (viewFindViewById3 != null) {
            if (z && z2 && !z3) {
                viewFindViewById3.setPadding(resources.getDimensionPixelSize(R.dimen.sem_dialog_padding_horizontal), 0, resources.getDimensionPixelSize(R.dimen.sem_dialog_padding_horizontal), 0);
            } else {
                viewFindViewById3.setPadding(resources.getDimensionPixelSize(R.dimen.sem_dialog_padding_horizontal), 0, resources.getDimensionPixelSize(R.dimen.sem_dialog_padding_horizontal), resources.getDimensionPixelSize(R.dimen.sem_dialog_title_padding_bottom));
            }
        }
        if (viewFindViewById4 != null) {
            viewFindViewById4.setPadding(resources.getDimensionPixelSize(R.dimen.sem_dialog_body_text_scroll_padding_start), 0, resources.getDimensionPixelSize(R.dimen.sem_dialog_body_text_scroll_padding_end), resources.getDimensionPixelSize(R.dimen.sem_dialog_body_text_padding_bottom));
        }
        if (viewFindViewById5 != null) {
            viewFindViewById5.setPadding(resources.getDimensionPixelSize(R.dimen.sem_dialog_button_bar_padding_horizontal), 0, resources.getDimensionPixelSize(R.dimen.sem_dialog_button_bar_padding_horizontal), resources.getDimensionPixelSize(R.dimen.sem_dialog_button_bar_padding_bottom));
        }
    }

    private void semAdjustParentPanelPadding(View view) {
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
        }
    }

    private void semAdjustTopPanelPadding(View view) throws Resources.NotFoundException {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_dialog_padding_horizontal);
        view.findViewById(R.id.title_template).setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
    }

    private void semAdjustContentPanelPadding(View view) throws Resources.NotFoundException {
        view.setPadding(view.getPaddingStart(), this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_select_dialog_padding_top_item_material), view.getPaddingRight(), view.getPaddingBottom());
    }

    private void semSetupButtonsPadding() throws Resources.NotFoundException {
        final int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_dialog_button_text_size);
        Arrays.asList(this.mButtonPositive, this.mButtonNegative, this.mButtonNeutral).forEach(new Consumer() { // from class: com.android.internal.app.AlertController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$semSetupButtonsPadding$2(dimensionPixelSize, (Button) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$semSetupButtonsPadding$2(int i, Button button) {
        if (button.getVisibility() != 8) {
            button.setTextSize(0, i);
            semCheckMaxFontScale(button, i);
        }
    }

    private void semCheckMaxFontScale(TextView textView, int i) {
        float f = this.mContext.getResources().getConfiguration().fontScale;
        if (!this.mThemeIsDeviceDefault || f <= 1.3f) {
            return;
        }
        textView.setTextSize(0, (i / f) * 1.3f);
    }

    public void semSetBackgroundBlurEnabled(boolean z) {
        this.mIsBlurEnabled = z;
        this.mIsDefaultBlurEnabled = z;
    }

    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;
        boolean mRecycleOnMeasure;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mRecycleOnMeasure = true;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, -1);
            this.mPaddingTopNoTitle = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, -1);
        }

        public void setHasDecor(boolean z, boolean z2) {
            int paddingBottom;
            if (z2 && z) {
                return;
            }
            int paddingLeft = getPaddingLeft();
            int paddingTop = z ? getPaddingTop() : this.mPaddingTopNoTitle;
            int paddingRight = getPaddingRight();
            if (z2 || AlertController.sHasPaddingBottomInCustom) {
                paddingBottom = getPaddingBottom();
            } else {
                paddingBottom = this.mPaddingBottomNoButtons;
            }
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }

        @Override // android.widget.ListView
        protected boolean recycleOnMeasure() {
            return this.mRecycleOnMeasure;
        }
    }

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public int mViewSpacingTop;
        public int mIconId = 0;
        public int mIconAttrId = 0;
        public boolean mViewSpacingSpecified = false;
        public int mCheckedItem = -1;
        public boolean mRecycleOnMeasure = true;
        public boolean mCancelable = true;

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void apply(AlertController alertController) {
            View view = this.mCustomTitleView;
            if (view != null) {
                alertController.setCustomTitle(view);
            } else {
                CharSequence charSequence = this.mTitle;
                if (charSequence != null) {
                    alertController.setTitle(charSequence);
                }
                Drawable drawable = this.mIcon;
                if (drawable != null) {
                    alertController.setIcon(drawable);
                }
                int i = this.mIconId;
                if (i != 0) {
                    alertController.setIcon(i);
                }
                int i2 = this.mIconAttrId;
                if (i2 != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(i2));
                }
            }
            CharSequence charSequence2 = this.mMessage;
            if (charSequence2 != null) {
                alertController.setMessage(charSequence2);
            }
            CharSequence charSequence3 = this.mPositiveButtonText;
            if (charSequence3 != null) {
                alertController.setButton(-1, charSequence3, this.mPositiveButtonListener, null);
            }
            CharSequence charSequence4 = this.mNegativeButtonText;
            if (charSequence4 != null) {
                alertController.setButton(-2, charSequence4, this.mNegativeButtonListener, null);
            }
            CharSequence charSequence5 = this.mNeutralButtonText;
            if (charSequence5 != null) {
                alertController.setButton(-3, charSequence5, this.mNeutralButtonListener, null);
            }
            if (this.mForceInverseBackground) {
                alertController.setInverseBackgroundForced(true);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController);
            }
            View view2 = this.mView;
            if (view2 != null) {
                if (this.mViewSpacingSpecified) {
                    alertController.setView(view2, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                    return;
                } else {
                    alertController.setView(view2);
                    return;
                }
            }
            int i3 = this.mViewLayoutResId;
            if (i3 != 0) {
                alertController.setView(i3);
            }
        }

        private void createListView(final AlertController alertController) {
            int i;
            ListAdapter checkedItemAdapter;
            final RecycleListView recycleListView = (RecycleListView) this.mInflater.inflate(alertController.mListLayout, (ViewGroup) null);
            if (this.mIsMultiChoice) {
                if (this.mCursor == null) {
                    checkedItemAdapter = new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, 16908308, this.mItems) { // from class: com.android.internal.app.AlertController.AlertParams.1
                        @Override // android.widget.ArrayAdapter, android.widget.Adapter
                        public View getView(int i2, View view, ViewGroup viewGroup) {
                            View view2 = super.getView(i2, view, viewGroup);
                            if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[i2]) {
                                recycleListView.setItemChecked(i2, true);
                            }
                            return view2;
                        }
                    };
                    recycleListView = recycleListView;
                } else {
                    checkedItemAdapter = new CursorAdapter(this.mContext, this.mCursor, false) { // from class: com.android.internal.app.AlertController.AlertParams.2
                        private final int mIsCheckedIndex;
                        private final int mLabelIndex;

                        {
                            Cursor cursor = getCursor();
                            this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                            this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                        }

                        @Override // android.widget.CursorAdapter
                        public void bindView(View view, Context context, Cursor cursor) {
                            ((CheckedTextView) view.findViewById(16908308)).lambda$setTextAsync$0(cursor.getString(this.mLabelIndex));
                            recycleListView.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1);
                        }

                        @Override // android.widget.CursorAdapter
                        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                            return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                        }
                    };
                }
            } else {
                if (this.mIsSingleChoice) {
                    i = alertController.mSingleChoiceItemLayout;
                } else {
                    if (alertController.mThemeIsDeviceDefault) {
                        alertController.mIsItemChoiceLayout = true;
                    }
                    i = alertController.mListItemLayout;
                }
                int i2 = i;
                if (this.mCursor != null) {
                    checkedItemAdapter = new SimpleCursorAdapter(this.mContext, i2, this.mCursor, new String[]{this.mLabelColumn}, new int[]{16908308});
                } else {
                    checkedItemAdapter = this.mAdapter;
                    if (checkedItemAdapter == null) {
                        checkedItemAdapter = new CheckedItemAdapter(this.mContext, i2, 16908308, this.mItems);
                    }
                }
            }
            OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
            if (onPrepareListViewListener != null) {
                onPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.mAdapter = checkedItemAdapter;
            alertController.mCheckedItem = this.mCheckedItem;
            if (this.mOnClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.internal.app.AlertController.AlertParams.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                        AlertParams.this.mOnClickListener.onClick(alertController.mDialogInterface, i3);
                        if (AlertParams.this.mIsSingleChoice) {
                            return;
                        }
                        alertController.mDialogInterface.dismiss();
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.internal.app.AlertController.AlertParams.4
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[i3] = recycleListView.isItemChecked(i3);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialogInterface, i3, recycleListView.isItemChecked(i3));
                    }
                });
            }
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                recycleListView.setOnItemSelectedListener(onItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                recycleListView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                recycleListView.setChoiceMode(2);
            }
            recycleListView.mRecycleOnMeasure = this.mRecycleOnMeasure;
            alertController.mListView = recycleListView;
        }
    }

    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        public CheckedItemAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
        }
    }

    private class BlurEffect {
        private static final int RADIUS = 150;
        private final Context context;

        public BlurEffect(Context context) {
            this.context = context;
        }

        public void setWindowBlur(View view, int i, float f) {
            setWindowBlur(view, i, f, AlertController.this.mThemeIsDeviceDefaultDark ? 130 : 115);
        }

        public void setWindowBlur(View view, int i, float f, int i2) {
            setBlurEffect(view, new SemBlurInfo.Builder(0).setBackgroundColor(i).setColorCurvePreset(i2).setBackgroundCornerRadius(f).build());
        }

        private void setBlurEffect(View view, SemBlurInfo semBlurInfo) {
            view.semSetBlurInfo(semBlurInfo);
            view.setVisibility(0);
            view.setClipToOutline(true);
        }
    }
}
