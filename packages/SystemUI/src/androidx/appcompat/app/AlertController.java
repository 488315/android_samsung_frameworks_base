package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.core.widget.NestedScrollView;
import androidx.preference.MultiSelectListPreferenceDialogFragmentCompat;
import com.android.systemui.R;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AlertController {
    public ListAdapter mAdapter;
    public final int mAlertDialogLayout;
    public Button mButtonNegative;
    public Message mButtonNegativeMessage;
    public CharSequence mButtonNegativeText;
    public Button mButtonNeutral;
    public Message mButtonNeutralMessage;
    public CharSequence mButtonNeutralText;
    public Button mButtonPositive;
    public Message mButtonPositiveMessage;
    public CharSequence mButtonPositiveText;
    public final Context mContext;
    public View mCustomTitleView;
    public AlertController$$ExternalSyntheticLambda1 mDefaultButtonPanelJob;
    public final AppCompatDialog mDialog;
    public final ButtonHandler mHandler;
    public Drawable mIcon;
    public ImageView mIconView;
    public int mLastOrientation;
    public final int mListItemLayout;
    public final int mListLayout;
    public RecycleListView mListView;
    public CharSequence mMessage;
    public TextView mMessageView;
    public final int mMultiChoiceItemLayout;
    public NestedScrollView mScrollView;
    public final boolean mShowTitle;
    public final int mSingleChoiceItemLayout;
    public CharSequence mTitle;
    public TextView mTitleView;
    public View mView;
    public int mViewLayoutResId;
    public final Window mWindow;
    public boolean mIsBlurEnabled = false;
    public boolean mViewSpacingSpecified = false;
    public int mCheckedItem = -1;
    public final AnonymousClass1 mButtonHandler = new View.OnClickListener() { // from class: androidx.appcompat.app.AlertController.1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            Message message;
            Message message2;
            Message message3;
            AlertController alertController = AlertController.this;
            Message obtain = (view != alertController.mButtonPositive || (message3 = alertController.mButtonPositiveMessage) == null) ? (view != alertController.mButtonNegative || (message2 = alertController.mButtonNegativeMessage) == null) ? (view != alertController.mButtonNeutral || (message = alertController.mButtonNeutralMessage) == null) ? null : Message.obtain(message) : Message.obtain(message2) : Message.obtain(message3);
            if (obtain != null) {
                obtain.sendToTarget();
            }
            AlertController alertController2 = AlertController.this;
            alertController2.mHandler.obtainMessage(1, alertController2.mDialog).sendToTarget();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AlertParams {
        public ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public View mCustomTitleView;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public MultiSelectListPreferenceDialogFragmentCompat.AnonymousClass1 mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mCheckedItem = -1;
        public boolean mCancelable = true;

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ButtonHandler extends Handler {
        public final WeakReference mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference(dialogInterface);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == -3 || i == -2 || i == -1) {
                ((DialogInterface.OnClickListener) message.obj).onClick((DialogInterface) this.mDialog.get(), message.what);
            } else {
                if (i != 1) {
                    return;
                }
                ((DialogInterface) message.obj).dismiss();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CheckedItemAdapter extends ArrayAdapter {
        public CheckedItemAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RecycleListView extends ListView {
        public final int mPaddingBottomNoButtons;
        public final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecycleListView);
            this.mPaddingBottomNoButtons = obtainStyledAttributes.getDimensionPixelOffset(0, -1);
            this.mPaddingTopNoTitle = obtainStyledAttributes.getDimensionPixelOffset(1, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.appcompat.app.AlertController$1] */
    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(appCompatDialog);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.getResourceId(2, 0);
        this.mListLayout = obtainStyledAttributes.getResourceId(5, 0);
        this.mMultiChoiceItemLayout = obtainStyledAttributes.getResourceId(6, 0);
        this.mSingleChoiceItemLayout = obtainStyledAttributes.getResourceId(9, 0);
        this.mListItemLayout = obtainStyledAttributes.getResourceId(4, 0);
        this.mShowTitle = obtainStyledAttributes.getBoolean(8, true);
        obtainStyledAttributes.getDimensionPixelSize(1, 0);
        obtainStyledAttributes.recycle();
        appCompatDialog.getDelegate().requestWindowFeature(1);
    }

    public static boolean canTextInput(View view) {
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

    public static ViewGroup resolvePanel(View view, View view2) {
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

    public final void checkMaxFontScale(TextView textView, int i) {
        float f = this.mContext.getResources().getConfiguration().fontScale;
        if (f > 1.3f) {
            textView.setTextSize(0, (i / f) * 1.3f);
        }
    }

    public final void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        Message obtainMessage = onClickListener != null ? this.mHandler.obtainMessage(i, onClickListener) : null;
        if (i == -3) {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = obtainMessage;
        } else if (i == -2) {
            this.mButtonNegativeText = charSequence;
            this.mButtonNegativeMessage = obtainMessage;
        } else {
            if (i != -1) {
                throw new IllegalArgumentException("Button does not exist");
            }
            this.mButtonPositiveText = charSequence;
            this.mButtonPositiveMessage = obtainMessage;
        }
    }
}
