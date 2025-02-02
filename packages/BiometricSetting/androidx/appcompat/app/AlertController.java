package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AlertController {
    ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final int mButtonIconDimen;
    Button mButtonNegative;
    Button mButtonNeutral;
    private int mButtonPanelSideLayout;
    Button mButtonPositive;
    private final Context mContext;
    private View mCustomTitleView;
    final AppCompatDialog mDialog;
    Handler mHandler;
    private Drawable mIcon;
    private ImageView mIconView;
    int mListItemLayout;
    int mListLayout;
    RecycleListView mListView;
    private TextView mMessageView;
    int mMultiChoiceItemLayout;
    NestedScrollView mScrollView;
    private boolean mShowTitle;
    int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private final Window mWindow;
    private boolean mViewSpacingSpecified = false;
    private int mIconId = 0;
    int mCheckedItem = -1;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() { // from class: androidx.appcompat.app.AlertController.1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            AlertController alertController = AlertController.this;
            Button button = alertController.mButtonPositive;
            Button button2 = alertController.mButtonNegative;
            Button button3 = alertController.mButtonNeutral;
            alertController.mHandler.obtainMessage(1, alertController.mDialog).sendToTarget();
        }
    };

    public static class AlertParams {
        public ListAdapter mAdapter;
        public int mCheckedItem = -1;
        public final Context mContext;
        public View mCustomTitleView;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public boolean mIsSingleChoice;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public CharSequence mTitle;

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }
    }

    private static final class ButtonHandler extends Handler {
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
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

    /* JADX INFO: Access modifiers changed from: private */
    static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int i) {
            super(context, i, R.id.text1, (Object[]) null);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return true;
        }
    }

    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public final void setHasDecor(boolean z, boolean z2) {
            if (z2 && z) {
                return;
            }
            setPadding(getPaddingLeft(), z ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), z2 ? getPaddingBottom() : this.mPaddingBottomNoButtons);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecycleListView);
            this.mPaddingBottomNoButtons = obtainStyledAttributes.getDimensionPixelOffset(0, -1);
            this.mPaddingTopNoTitle = obtainStyledAttributes.getDimensionPixelOffset(1, -1);
        }
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(appCompatDialog);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.AlertDialog, com.samsung.android.biometrics.app.setting.R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = obtainStyledAttributes.getResourceId(0, 0);
        this.mButtonPanelSideLayout = obtainStyledAttributes.getResourceId(2, 0);
        this.mListLayout = obtainStyledAttributes.getResourceId(4, 0);
        this.mMultiChoiceItemLayout = obtainStyledAttributes.getResourceId(5, 0);
        this.mSingleChoiceItemLayout = obtainStyledAttributes.getResourceId(7, 0);
        this.mListItemLayout = obtainStyledAttributes.getResourceId(3, 0);
        this.mShowTitle = obtainStyledAttributes.getBoolean(6, true);
        this.mButtonIconDimen = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        obtainStyledAttributes.recycle();
        appCompatDialog.getDelegate().requestWindowFeature(1);
    }

    private static void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }

    private static ViewGroup resolvePanel(View view, View view2) {
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
    public final void installContent() {
        int i;
        ListAdapter listAdapter;
        View findViewById;
        this.mDialog.setContentView(this.mButtonPanelSideLayout == 0 ? this.mAlertDialogLayout : this.mAlertDialogLayout);
        Window window = this.mWindow;
        View findViewById2 = window.findViewById(com.samsung.android.biometrics.app.setting.R.id.parentPanel);
        View findViewById3 = findViewById2.findViewById(com.samsung.android.biometrics.app.setting.R.id.topPanel);
        View findViewById4 = findViewById2.findViewById(com.samsung.android.biometrics.app.setting.R.id.contentPanel);
        View findViewById5 = findViewById2.findViewById(com.samsung.android.biometrics.app.setting.R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) findViewById2.findViewById(com.samsung.android.biometrics.app.setting.R.id.customPanel);
        window.setFlags(131072, 131072);
        viewGroup.setVisibility(8);
        View findViewById6 = viewGroup.findViewById(com.samsung.android.biometrics.app.setting.R.id.topPanel);
        View findViewById7 = viewGroup.findViewById(com.samsung.android.biometrics.app.setting.R.id.contentPanel);
        View findViewById8 = viewGroup.findViewById(com.samsung.android.biometrics.app.setting.R.id.buttonPanel);
        ViewGroup resolvePanel = resolvePanel(findViewById6, findViewById3);
        ViewGroup resolvePanel2 = resolvePanel(findViewById7, findViewById4);
        ViewGroup resolvePanel3 = resolvePanel(findViewById8, findViewById5);
        NestedScrollView nestedScrollView = (NestedScrollView) window.findViewById(com.samsung.android.biometrics.app.setting.R.id.scrollView);
        this.mScrollView = nestedScrollView;
        nestedScrollView.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        TextView textView = (TextView) resolvePanel2.findViewById(R.id.message);
        this.mMessageView = textView;
        if (textView != null) {
            textView.setVisibility(8);
            this.mScrollView.removeView(this.mMessageView);
            if (this.mListView != null) {
                ViewGroup viewGroup2 = (ViewGroup) this.mScrollView.getParent();
                int indexOfChild = viewGroup2.indexOfChild(this.mScrollView);
                viewGroup2.removeViewAt(indexOfChild);
                viewGroup2.addView(this.mListView, indexOfChild, new ViewGroup.LayoutParams(-1, -1));
            } else {
                resolvePanel2.setVisibility(8);
            }
        }
        Button button = (Button) resolvePanel3.findViewById(R.id.button1);
        this.mButtonPositive = button;
        View.OnClickListener onClickListener = this.mButtonHandler;
        button.setOnClickListener(onClickListener);
        if (TextUtils.isEmpty(null)) {
            this.mButtonPositive.setVisibility(8);
            i = 0;
        } else {
            this.mButtonPositive.setText((CharSequence) null);
            this.mButtonPositive.setVisibility(0);
            i = 1;
        }
        Button button2 = (Button) resolvePanel3.findViewById(R.id.button2);
        this.mButtonNegative = button2;
        button2.setOnClickListener(onClickListener);
        if (TextUtils.isEmpty(null)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText((CharSequence) null);
            this.mButtonNegative.setVisibility(0);
            i |= 2;
        }
        Button button3 = (Button) resolvePanel3.findViewById(R.id.button3);
        this.mButtonNeutral = button3;
        button3.setOnClickListener(onClickListener);
        if (TextUtils.isEmpty(null)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText((CharSequence) null);
            this.mButtonNeutral.setVisibility(0);
            i |= 4;
        }
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.alertDialogCenterButtons, typedValue, true);
        if (typedValue.data != 0) {
            if (i == 1) {
                centerButton(this.mButtonPositive);
            } else if (i == 2) {
                centerButton(this.mButtonNegative);
            } else if (i == 4) {
                centerButton(this.mButtonNeutral);
            }
        }
        if (!(i != 0)) {
            resolvePanel3.setVisibility(8);
        }
        if (this.mCustomTitleView != null) {
            resolvePanel.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            window.findViewById(com.samsung.android.biometrics.app.setting.R.id.title_template).setVisibility(8);
        } else {
            this.mIconView = (ImageView) window.findViewById(R.id.icon);
            if ((!TextUtils.isEmpty(this.mTitle)) && this.mShowTitle) {
                TextView textView2 = (TextView) window.findViewById(com.samsung.android.biometrics.app.setting.R.id.alertTitle);
                this.mTitleView = textView2;
                textView2.setText(this.mTitle);
                int i2 = this.mIconId;
                if (i2 != 0) {
                    this.mIconView.setImageResource(i2);
                } else {
                    Drawable drawable = this.mIcon;
                    if (drawable != null) {
                        this.mIconView.setImageDrawable(drawable);
                    } else {
                        this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                        this.mIconView.setVisibility(8);
                    }
                }
            } else {
                window.findViewById(com.samsung.android.biometrics.app.setting.R.id.title_template).setVisibility(8);
                this.mIconView.setVisibility(8);
                resolvePanel.setVisibility(8);
            }
        }
        boolean z = viewGroup.getVisibility() != 8;
        boolean z2 = (resolvePanel == null || resolvePanel.getVisibility() == 8) ? 0 : 1;
        boolean z3 = resolvePanel3.getVisibility() != 8;
        if (!z3 && (findViewById = resolvePanel2.findViewById(com.samsung.android.biometrics.app.setting.R.id.textSpacerNoButtons)) != null) {
            findViewById.setVisibility(0);
        }
        if (z2 != 0) {
            NestedScrollView nestedScrollView2 = this.mScrollView;
            if (nestedScrollView2 != null) {
                nestedScrollView2.setClipToPadding(true);
            }
            View findViewById9 = this.mListView != null ? resolvePanel.findViewById(com.samsung.android.biometrics.app.setting.R.id.titleDividerNoCustom) : null;
            if (findViewById9 != null) {
                findViewById9.setVisibility(0);
            }
        } else {
            View findViewById10 = resolvePanel2.findViewById(com.samsung.android.biometrics.app.setting.R.id.textSpacerNoTitle);
            if (findViewById10 != null) {
                findViewById10.setVisibility(0);
            }
        }
        RecycleListView recycleListView = this.mListView;
        if (recycleListView instanceof RecycleListView) {
            recycleListView.setHasDecor(z2, z3);
        }
        if (!z) {
            View view = this.mListView;
            if (view == null) {
                view = this.mScrollView;
            }
            if (view != null) {
                int i3 = z3 ? 2 : 0;
                View findViewById11 = window.findViewById(com.samsung.android.biometrics.app.setting.R.id.scrollIndicatorUp);
                View findViewById12 = window.findViewById(com.samsung.android.biometrics.app.setting.R.id.scrollIndicatorDown);
                ViewCompat.setScrollIndicators(view, z2 | i3);
                if (findViewById11 != null) {
                    resolvePanel2.removeView(findViewById11);
                }
                if (findViewById12 != null) {
                    resolvePanel2.removeView(findViewById12);
                }
            }
        }
        RecycleListView recycleListView2 = this.mListView;
        if (recycleListView2 == null || (listAdapter = this.mAdapter) == null) {
            return;
        }
        recycleListView2.setAdapter(listAdapter);
        int i4 = this.mCheckedItem;
        if (i4 > -1) {
            recycleListView2.setItemChecked(i4, true);
            recycleListView2.setSelection(i4);
        }
    }

    public final void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public final void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (drawable == null) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable);
            }
        }
    }

    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }
}
