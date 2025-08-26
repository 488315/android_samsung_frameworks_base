package androidx.appcompat.app;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.SemBlurCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.widget.SeslAdapterViewReflector;
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.util.SettingsHelper;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    public final AlertController mAlert;

    public class Builder {
        public final AlertController.AlertParams P;
        public final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(0, context));
        }

        public final AlertDialog create() {
            ListAdapter listAdapter;
            final AlertController.AlertParams alertParams = this.P;
            AlertDialog alertDialog = new AlertDialog(alertParams.mContext, this.mTheme);
            final AlertController alertController = alertDialog.mAlert;
            View view = alertParams.mCustomTitleView;
            if (view != null) {
                alertController.mCustomTitleView = view;
            } else {
                CharSequence charSequence = alertParams.mTitle;
                if (charSequence != null) {
                    alertController.mTitle = charSequence;
                    TextView textView = alertController.mTitleView;
                    if (textView != null) {
                        textView.setText(charSequence);
                    }
                    alertController.mWindow.setTitle(charSequence);
                }
                Drawable drawable = alertParams.mIcon;
                if (drawable != null) {
                    alertController.mIcon = drawable;
                    ImageView imageView = alertController.mIconView;
                    if (imageView != null) {
                        imageView.setVisibility(0);
                        alertController.mIconView.setImageDrawable(drawable);
                    }
                }
            }
            CharSequence charSequence2 = alertParams.mMessage;
            if (charSequence2 != null) {
                alertController.mMessage = charSequence2;
                TextView textView2 = alertController.mMessageView;
                if (textView2 != null) {
                    textView2.setText(charSequence2);
                }
            }
            CharSequence charSequence3 = alertParams.mPositiveButtonText;
            if (charSequence3 != null) {
                alertController.setButton(-1, charSequence3, alertParams.mPositiveButtonListener);
            }
            CharSequence charSequence4 = alertParams.mNegativeButtonText;
            if (charSequence4 != null) {
                alertController.setButton(-2, charSequence4, alertParams.mNegativeButtonListener);
            }
            if (alertParams.mItems != null || alertParams.mAdapter != null) {
                final AlertController.RecycleListView recycleListView = (AlertController.RecycleListView) alertParams.mInflater.inflate(alertController.mListLayout, (ViewGroup) null);
                if (alertParams.mIsMultiChoice) {
                    final Context context = alertParams.mContext;
                    final CharSequence[] charSequenceArr = alertParams.mItems;
                    final int i = alertController.mMultiChoiceItemLayout;
                    final int i2 = R.id.text1;
                    listAdapter = new ArrayAdapter(context, i, i2, charSequenceArr) { // from class: androidx.appcompat.app.AlertController.AlertParams.1
                        public final /* synthetic */ RecycleListView val$listView;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(final Context context2, final int i3, final int i22, final CharSequence[] charSequenceArr2, final RecycleListView recycleListView2) {
                            super(context2, i3, i22, charSequenceArr2);
                            recycleListView = recycleListView2;
                        }

                        @Override // android.widget.ArrayAdapter, android.widget.Adapter
                        public final View getView(int i3, View view2, ViewGroup viewGroup) {
                            View view3 = super.getView(i3, view2, viewGroup);
                            boolean[] zArr = AlertParams.this.mCheckedItems;
                            if (zArr != null && zArr[i3]) {
                                recycleListView.setItemChecked(i3, true);
                            }
                            return view3;
                        }
                    };
                } else {
                    int i3 = alertParams.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
                    ListAdapter checkedItemAdapter = alertParams.mAdapter;
                    if (checkedItemAdapter == null) {
                        checkedItemAdapter = new AlertController.CheckedItemAdapter(alertParams.mContext, i3, R.id.text1, alertParams.mItems);
                    }
                    listAdapter = checkedItemAdapter;
                }
                alertController.mAdapter = listAdapter;
                alertController.mCheckedItem = alertParams.mCheckedItem;
                if (alertParams.mOnClickListener != null) {
                    recycleListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.3
                        public final /* synthetic */ AlertController val$dialog;

                        public AnonymousClass3(final AlertController alertController2) {
                            alertController = alertController2;
                        }

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view2, int i4, long j) {
                            AlertParams.this.mOnClickListener.onClick(alertController.mDialog, i4);
                            if (AlertParams.this.mIsSingleChoice) {
                                return;
                            }
                            alertController.mDialog.dismiss();
                        }
                    });
                } else if (alertParams.mOnCheckboxClickListener != null) {
                    recycleListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.4
                        public final /* synthetic */ AlertController val$dialog;
                        public final /* synthetic */ RecycleListView val$listView;

                        public AnonymousClass4(final RecycleListView recycleListView2, final AlertController alertController2) {
                            recycleListView = recycleListView2;
                            alertController = alertController2;
                        }

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view2, int i4, long j) {
                            boolean[] zArr = AlertParams.this.mCheckedItems;
                            if (zArr != null) {
                                zArr[i4] = recycleListView.isItemChecked(i4);
                            }
                            AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialog, i4, recycleListView.isItemChecked(i4));
                        }
                    });
                }
                if (alertParams.mIsSingleChoice) {
                    recycleListView2.setChoiceMode(1);
                } else if (alertParams.mIsMultiChoice) {
                    recycleListView2.setChoiceMode(2);
                }
                alertController2.mListView = recycleListView2;
            }
            View view2 = alertParams.mView;
            if (view2 != null) {
                alertController2.mView = view2;
                alertController2.mViewLayoutResId = 0;
                alertController2.mViewSpacingSpecified = false;
            } else {
                int i4 = alertParams.mViewLayoutResId;
                if (i4 != 0) {
                    alertController2.mView = null;
                    alertController2.mViewLayoutResId = i4;
                    alertController2.mViewSpacingSpecified = false;
                }
            }
            alertDialog.setCancelable(alertParams.mCancelable);
            if (alertParams.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(null);
            alertDialog.setOnDismissListener(alertParams.mOnDismissListener);
            DialogInterface.OnKeyListener onKeyListener = alertParams.mOnKeyListener;
            if (onKeyListener != null) {
                alertDialog.setOnKeyListener(onKeyListener);
            }
            return alertDialog;
        }

        public final void setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mPositiveButtonText = charSequence;
            alertParams.mPositiveButtonListener = onClickListener;
        }

        public final void setView(View view) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mView = view;
            alertParams.mViewLayoutResId = 0;
        }

        public Builder(Context context, int i) {
            this.P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(i, context)));
            this.mTheme = i;
        }
    }

    public AlertDialog(Context context) {
        this(context, 0);
    }

    public static int resolveDialogTheme(int i, Context context) {
        if (((i >>> 24) & 255) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.android.systemui.R.attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public final Button getButton(int i) {
        AlertController alertController = this.mAlert;
        if (i == -3) {
            return alertController.mButtonNeutral;
        }
        if (i == -2) {
            return alertController.mButtonNegative;
        }
        if (i == -1) {
            return alertController.mButtonPositive;
        }
        alertController.getClass();
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x03de  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x043a  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0443  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x04b2  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x04d8  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0563  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x056f  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0578  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x058d  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0596  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02a4  */
    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onCreate(Bundle bundle) throws Resources.NotFoundException, NoSuchMethodException, SecurityException {
        char c;
        char c2;
        boolean z;
        boolean z2;
        TypedValue typedValue;
        int i;
        TypedValue typedValue2;
        View viewFindViewById;
        int i2;
        View viewFindViewById2;
        int i3;
        boolean z3;
        int i4;
        int i5;
        AlertController.RecycleListView recycleListView;
        AlertController.RecycleListView recycleListView2;
        boolean z4;
        String str;
        View decorView;
        ListAdapter listAdapter;
        Method declaredMethod;
        int i6;
        NestedScrollView nestedScrollView;
        ViewGroup viewGroup;
        LinearLayout linearLayout;
        super.onCreate(bundle);
        final AlertController alertController = this.mAlert;
        alertController.mDialog.setContentView(alertController.mAlertDialogLayout);
        final View viewFindViewById3 = alertController.mWindow.findViewById(com.android.systemui.R.id.parentPanel);
        View viewFindViewById4 = alertController.mWindow.findViewById(com.android.systemui.R.id.middlePanel);
        viewFindViewById3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.appcompat.app.AlertController.2
            public final /* synthetic */ View val$parentPanel;

            /* renamed from: androidx.appcompat.app.AlertController$2$1 */
            public class AnonymousClass1 implements Runnable {
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    int i = AlertController.this.mContext.getResources().getConfiguration().orientation;
                    AlertController alertController = AlertController.this;
                    if (i != alertController.mLastOrientation) {
                        View viewFindViewById = alertController.mWindow.findViewById(com.android.systemui.R.id.parentPanel);
                        View viewFindViewById2 = alertController.mWindow.findViewById(com.android.systemui.R.id.middlePanel);
                        View viewFindViewById3 = viewFindViewById.findViewById(com.android.systemui.R.id.title_template);
                        View viewFindViewById4 = viewFindViewById.findViewById(com.android.systemui.R.id.scrollView);
                        View viewFindViewById5 = viewFindViewById.findViewById(com.android.systemui.R.id.topPanel);
                        View viewFindViewById6 = viewFindViewById.findViewById(com.android.systemui.R.id.buttonBarLayout);
                        View viewFindViewById7 = viewFindViewById.findViewById(com.android.systemui.R.id.customPanel);
                        View viewFindViewById8 = viewFindViewById.findViewById(com.android.systemui.R.id.contentPanel);
                        boolean z = (viewFindViewById7 == null || viewFindViewById7.getVisibility() == 8) ? false : true;
                        boolean z2 = (viewFindViewById5 == null || viewFindViewById5.getVisibility() == 8) ? false : true;
                        boolean z3 = (viewFindViewById8 == null || viewFindViewById8.getVisibility() == 8) ? false : true;
                        View view = alertController.mCustomTitleView;
                        boolean z4 = (view == null || view.getVisibility() == 8) ? false : true;
                        Resources resources = alertController.mContext.getResources();
                        if (viewFindViewById2 != null) {
                            if ((!z || z2 || z3) && !z4) {
                                viewFindViewById2.setPadding(0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_padding_top), 0, 0);
                            } else {
                                viewFindViewById2.setPadding(0, 0, 0, 0);
                            }
                        }
                        if (viewFindViewById3 != null) {
                            int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
                            if (z && z2 && !z3) {
                                viewFindViewById3.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                            } else {
                                viewFindViewById3.setPadding(dimensionPixelSize, 0, dimensionPixelSize, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_padding_bottom));
                            }
                        }
                        if (viewFindViewById4 != null) {
                            viewFindViewById4.setPadding(resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_scroll_padding_start), 0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_scroll_padding_end), resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_padding_bottom));
                        }
                        if (viewFindViewById6 != null) {
                            int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_bar_padding_horizontal);
                            viewFindViewById6.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_bar_padding_bottom));
                        }
                        view.requestLayout();
                    }
                    AlertController alertController2 = AlertController.this;
                    alertController2.mLastOrientation = alertController2.mContext.getResources().getConfiguration().orientation;
                }
            }

            public AnonymousClass2(final View viewFindViewById32) {
                view = viewFindViewById32;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14) {
                view.post(new Runnable() { // from class: androidx.appcompat.app.AlertController.2.1
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        int i15 = AlertController.this.mContext.getResources().getConfiguration().orientation;
                        AlertController alertController2 = AlertController.this;
                        if (i15 != alertController2.mLastOrientation) {
                            View viewFindViewById5 = alertController2.mWindow.findViewById(com.android.systemui.R.id.parentPanel);
                            View viewFindViewById22 = alertController2.mWindow.findViewById(com.android.systemui.R.id.middlePanel);
                            View viewFindViewById32 = viewFindViewById5.findViewById(com.android.systemui.R.id.title_template);
                            View viewFindViewById42 = viewFindViewById5.findViewById(com.android.systemui.R.id.scrollView);
                            View viewFindViewById52 = viewFindViewById5.findViewById(com.android.systemui.R.id.topPanel);
                            View viewFindViewById6 = viewFindViewById5.findViewById(com.android.systemui.R.id.buttonBarLayout);
                            View viewFindViewById7 = viewFindViewById5.findViewById(com.android.systemui.R.id.customPanel);
                            View viewFindViewById8 = viewFindViewById5.findViewById(com.android.systemui.R.id.contentPanel);
                            boolean z5 = (viewFindViewById7 == null || viewFindViewById7.getVisibility() == 8) ? false : true;
                            boolean z22 = (viewFindViewById52 == null || viewFindViewById52.getVisibility() == 8) ? false : true;
                            boolean z32 = (viewFindViewById8 == null || viewFindViewById8.getVisibility() == 8) ? false : true;
                            View view2 = alertController2.mCustomTitleView;
                            boolean z42 = (view2 == null || view2.getVisibility() == 8) ? false : true;
                            Resources resources = alertController2.mContext.getResources();
                            if (viewFindViewById22 != null) {
                                if ((!z5 || z22 || z32) && !z42) {
                                    viewFindViewById22.setPadding(0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_padding_top), 0, 0);
                                } else {
                                    viewFindViewById22.setPadding(0, 0, 0, 0);
                                }
                            }
                            if (viewFindViewById32 != null) {
                                int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
                                if (z5 && z22 && !z32) {
                                    viewFindViewById32.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                                } else {
                                    viewFindViewById32.setPadding(dimensionPixelSize, 0, dimensionPixelSize, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_padding_bottom));
                                }
                            }
                            if (viewFindViewById42 != null) {
                                viewFindViewById42.setPadding(resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_scroll_padding_start), 0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_scroll_padding_end), resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_padding_bottom));
                            }
                            if (viewFindViewById6 != null) {
                                int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_bar_padding_horizontal);
                                viewFindViewById6.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_bar_padding_bottom));
                            }
                            view.requestLayout();
                        }
                        AlertController alertController22 = AlertController.this;
                        alertController22.mLastOrientation = alertController22.mContext.getResources().getConfiguration().orientation;
                    }
                });
            }
        });
        View viewFindViewById5 = viewFindViewById32.findViewById(com.android.systemui.R.id.topPanel);
        View viewFindViewById6 = viewFindViewById32.findViewById(com.android.systemui.R.id.contentPanel);
        View viewFindViewById7 = viewFindViewById32.findViewById(com.android.systemui.R.id.buttonPanel);
        ViewGroup viewGroup2 = (ViewGroup) viewFindViewById32.findViewById(com.android.systemui.R.id.customPanel);
        View viewInflate = alertController.mView;
        if (viewInflate == null) {
            viewInflate = alertController.mViewLayoutResId != 0 ? LayoutInflater.from(alertController.mContext).inflate(alertController.mViewLayoutResId, viewGroup2, false) : null;
        }
        boolean z5 = viewInflate != null;
        if (z5 && AlertController.canTextInput(viewInflate)) {
            c = 4;
        } else {
            c = 4;
            alertController.mWindow.setFlags(131072, 131072);
        }
        if (z5) {
            c2 = 2;
            FrameLayout frameLayout = (FrameLayout) alertController.mWindow.findViewById(com.android.systemui.R.id.custom);
            frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
            if (alertController.mViewSpacingSpecified) {
                frameLayout.setPadding(0, 0, 0, 0);
            }
            if (alertController.mListView != null) {
                if (viewGroup2.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) viewGroup2.getLayoutParams()).weight = 0.0f;
                } else {
                    ((LinearLayout.LayoutParams) ((LinearLayoutCompat.LayoutParams) viewGroup2.getLayoutParams())).weight = 0.0f;
                }
            }
        } else {
            c2 = 2;
            viewGroup2.setVisibility(8);
        }
        View viewFindViewById8 = viewGroup2.findViewById(com.android.systemui.R.id.topPanel);
        View viewFindViewById9 = viewGroup2.findViewById(com.android.systemui.R.id.contentPanel);
        View viewFindViewById10 = viewGroup2.findViewById(com.android.systemui.R.id.buttonPanel);
        ViewGroup viewGroupResolvePanel = AlertController.resolvePanel(viewFindViewById8, viewFindViewById5);
        ViewGroup viewGroupResolvePanel2 = AlertController.resolvePanel(viewFindViewById9, viewFindViewById6);
        ViewGroup viewGroupResolvePanel3 = AlertController.resolvePanel(viewFindViewById10, viewFindViewById7);
        alertController.mDefaultButtonPanelJob = viewGroupResolvePanel3 == viewFindViewById7 ? new AlertController$$ExternalSyntheticLambda1(alertController) : null;
        NestedScrollView nestedScrollView2 = (NestedScrollView) alertController.mWindow.findViewById(com.android.systemui.R.id.scrollView);
        alertController.mScrollView = nestedScrollView2;
        nestedScrollView2.setFocusable(false);
        alertController.mScrollView.setNestedScrollingEnabled(false);
        TextView textView = (TextView) viewGroupResolvePanel2.findViewById(R.id.message);
        alertController.mMessageView = textView;
        if (textView != null) {
            CharSequence charSequence = alertController.mMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                alertController.checkMaxFontScale(alertController.mMessageView, alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_size));
            } else {
                textView.setVisibility(8);
                alertController.mScrollView.removeView(alertController.mMessageView);
                if (alertController.mListView != null) {
                    ViewGroup viewGroup3 = (ViewGroup) alertController.mScrollView.getParent();
                    int iIndexOfChild = viewGroup3.indexOfChild(alertController.mScrollView);
                    viewGroup3.removeViewAt(iIndexOfChild);
                    viewGroup3.addView(alertController.mListView, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
                } else {
                    viewGroupResolvePanel2.setVisibility(8);
                }
            }
        }
        ContentResolver contentResolver = alertController.mContext.getContentResolver();
        if (contentResolver != null) {
            z = true;
            if (Settings.System.getInt(contentResolver, SettingsHelper.INDEX_SHOW_BUTTON_BACKGROUND, 0) == 1) {
                z2 = true;
            }
            typedValue = new TypedValue();
            alertController.mContext.getTheme().resolveAttribute(R.attr.colorBackground, typedValue, z);
            int color = typedValue.resourceId <= 0 ? alertController.mContext.getResources().getColor(typedValue.resourceId) : -1;
            Button button = (Button) viewGroupResolvePanel3.findViewById(R.id.button1);
            alertController.mButtonPositive = button;
            AlertController.AnonymousClass1 anonymousClass1 = alertController.mButtonHandler;
            button.setOnClickListener(anonymousClass1);
            if (typedValue.resourceId <= 0) {
                SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonPositive, z2, color);
            } else {
                SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonPositive, z2);
            }
            if (TextUtils.isEmpty(alertController.mButtonPositiveText)) {
                alertController.mButtonPositive.setText(alertController.mButtonPositiveText);
                alertController.mButtonPositive.setVisibility(0);
                i = 1;
            } else {
                alertController.mButtonPositive.setVisibility(8);
                i = 0;
            }
            Button button2 = (Button) viewGroupResolvePanel3.findViewById(R.id.button2);
            alertController.mButtonNegative = button2;
            button2.setOnClickListener(anonymousClass1);
            if (typedValue.resourceId <= 0) {
                SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonNegative, z2, color);
            } else {
                SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonNegative, z2);
            }
            if (TextUtils.isEmpty(alertController.mButtonNegativeText)) {
                alertController.mButtonNegative.setText(alertController.mButtonNegativeText);
                alertController.mButtonNegative.setVisibility(0);
                i |= 2;
            } else {
                alertController.mButtonNegative.setVisibility(8);
            }
            Button button3 = (Button) viewGroupResolvePanel3.findViewById(R.id.button3);
            alertController.mButtonNeutral = button3;
            button3.setOnClickListener(anonymousClass1);
            if (typedValue.resourceId <= 0) {
                SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonNeutral, z2, color);
            } else {
                SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonNeutral, z2);
            }
            if (TextUtils.isEmpty(alertController.mButtonNeutralText)) {
                alertController.mButtonNeutral.setText(alertController.mButtonNeutralText);
                alertController.mButtonNeutral.setVisibility(0);
                i |= 4;
            } else {
                alertController.mButtonNeutral.setVisibility(8);
            }
            Context context = alertController.mContext;
            typedValue2 = new TypedValue();
            context.getTheme().resolveAttribute(com.android.systemui.R.attr.alertDialogCenterButtons, typedValue2, true);
            if (typedValue2.data != 0) {
                if (i == 1) {
                    Button button4 = alertController.mButtonPositive;
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button4.getLayoutParams();
                    layoutParams.gravity = 1;
                    layoutParams.weight = 0.5f;
                    button4.setLayoutParams(layoutParams);
                } else if (i == c2) {
                    Button button5 = alertController.mButtonNegative;
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button5.getLayoutParams();
                    layoutParams2.gravity = 1;
                    layoutParams2.weight = 0.5f;
                    button5.setLayoutParams(layoutParams2);
                } else if (i == c) {
                    Button button6 = alertController.mButtonNeutral;
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button6.getLayoutParams();
                    layoutParams3.gravity = 1;
                    layoutParams3.weight = 0.5f;
                    button6.setLayoutParams(layoutParams3);
                }
            }
            if (i == 0) {
                viewGroupResolvePanel3.setVisibility(8);
            }
            boolean z6 = alertController.mButtonNeutral.getVisibility() != 0;
            boolean z7 = alertController.mButtonPositive.getVisibility() != 0;
            boolean z8 = alertController.mButtonNegative.getVisibility() != 0;
            viewFindViewById = alertController.mWindow.findViewById(com.android.systemui.R.id.sem_divider2);
            if (viewFindViewById == null && ((z6 && z7) || (z6 && z8))) {
                i2 = 0;
                viewFindViewById.setVisibility(0);
            } else {
                i2 = 0;
            }
            viewFindViewById2 = alertController.mWindow.findViewById(com.android.systemui.R.id.sem_divider1);
            if (viewFindViewById2 != null && z7 && z8) {
                viewFindViewById2.setVisibility(i2);
            }
            if (alertController.mDefaultButtonPanelJob != null && (viewGroup = (ViewGroup) viewGroupResolvePanel3.findViewById(com.android.systemui.R.id.buttonBarLayout)) != null) {
                AlertController$$ExternalSyntheticLambda1 alertController$$ExternalSyntheticLambda1 = alertController.mDefaultButtonPanelJob;
                alertController$$ExternalSyntheticLambda1.getClass();
                alertController$$ExternalSyntheticLambda1.f$0.getClass();
                linearLayout = (LinearLayout) viewGroup.findViewById(com.android.systemui.R.id.buttonBarLayout);
                if (linearLayout != null) {
                    linearLayout.post(new AlertController$$ExternalSyntheticLambda2(linearLayout));
                }
            }
            if (alertController.mCustomTitleView == null) {
                viewGroupResolvePanel.addView(alertController.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
                i3 = 8;
                alertController.mWindow.findViewById(com.android.systemui.R.id.title_template).setVisibility(8);
            } else {
                alertController.mIconView = (ImageView) alertController.mWindow.findViewById(R.id.icon);
                if (TextUtils.isEmpty(alertController.mTitle) || !alertController.mShowTitle) {
                    i3 = 8;
                    alertController.mWindow.findViewById(com.android.systemui.R.id.title_template).setVisibility(8);
                    alertController.mIconView.setVisibility(8);
                    viewGroupResolvePanel.setVisibility(8);
                } else {
                    TextView textView2 = (TextView) alertController.mWindow.findViewById(com.android.systemui.R.id.alertTitle);
                    alertController.mTitleView = textView2;
                    textView2.setText(alertController.mTitle);
                    alertController.checkMaxFontScale(alertController.mTitleView, alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_text_size));
                    Drawable drawable = alertController.mIcon;
                    if (drawable != null) {
                        alertController.mIconView.setImageDrawable(drawable);
                        i3 = 8;
                    } else {
                        alertController.mTitleView.setPadding(alertController.mIconView.getPaddingLeft(), alertController.mIconView.getPaddingTop(), alertController.mIconView.getPaddingRight(), alertController.mIconView.getPaddingBottom());
                        i3 = 8;
                        alertController.mIconView.setVisibility(8);
                    }
                }
            }
            z3 = viewGroup2.getVisibility() == i3;
            i4 = (viewGroupResolvePanel != null || viewGroupResolvePanel.getVisibility() == i3) ? 0 : 1;
            boolean z9 = viewGroupResolvePanel3.getVisibility() == i3;
            boolean z10 = viewFindViewById5 == null && viewFindViewById5.getVisibility() != i3;
            boolean z11 = viewFindViewById6 == null && viewFindViewById6.getVisibility() != i3;
            View view = alertController.mCustomTitleView;
            boolean z12 = view == null && view.getVisibility() != i3;
            if ((z3 && !z10 && !z11) || z12) {
                viewFindViewById4.setPadding(0, 0, 0, 0);
            }
            if (z3 && z10 && !z11) {
                View viewFindViewById11 = viewFindViewById32.findViewById(com.android.systemui.R.id.title_template);
                int dimensionPixelSize = alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
                viewFindViewById11.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
            }
            int dimensionPixelSize2 = alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_text_size);
            if (alertController.mButtonPositive.getVisibility() == 8) {
                i5 = 0;
                alertController.mButtonPositive.setTextSize(0, dimensionPixelSize2);
                alertController.checkMaxFontScale(alertController.mButtonPositive, dimensionPixelSize2);
            } else {
                i5 = 0;
            }
            if (alertController.mButtonNegative.getVisibility() != 8) {
                alertController.mButtonNegative.setTextSize(i5, dimensionPixelSize2);
                alertController.checkMaxFontScale(alertController.mButtonNegative, dimensionPixelSize2);
            }
            if (alertController.mButtonNeutral.getVisibility() != 8) {
                alertController.mButtonNeutral.setTextSize(i5, dimensionPixelSize2);
                alertController.checkMaxFontScale(alertController.mButtonNeutral, dimensionPixelSize2);
            }
            if (!viewFindViewById32.isInTouchMode()) {
                if (!z3) {
                    viewGroup2 = viewGroupResolvePanel2;
                }
                if (!viewGroup2.requestFocus()) {
                    AlertController.RecycleListView recycleListView3 = alertController.mListView;
                    if (recycleListView3 != null) {
                        recycleListView3.setSelection(0);
                    } else if (alertController.mButtonPositive.getVisibility() == 0) {
                        alertController.mButtonPositive.requestFocus();
                    } else if (alertController.mButtonNegative.getVisibility() == 0) {
                        alertController.mButtonNegative.requestFocus();
                    } else if (alertController.mButtonNeutral.getVisibility() == 0) {
                        alertController.mButtonNeutral.requestFocus();
                    }
                }
            }
            if (i4 != 0 && (nestedScrollView = alertController.mScrollView) != null) {
                nestedScrollView.setClipToPadding(true);
            }
            recycleListView = alertController.mListView;
            if (recycleListView != null) {
                recycleListView.getClass();
                if (!z9 || i4 == 0) {
                    recycleListView.setPadding(recycleListView.getPaddingLeft(), i4 != 0 ? recycleListView.getPaddingTop() : recycleListView.mPaddingTopNoTitle, recycleListView.getPaddingRight(), z9 ? recycleListView.getPaddingBottom() : recycleListView.mPaddingBottomNoButtons);
                }
            }
            if (!z3) {
                View view2 = alertController.mListView;
                if (view2 == null) {
                    view2 = alertController.mScrollView;
                }
                if (view2 != null) {
                    int i7 = i4 | (z9 ? 2 : 0);
                    View viewFindViewById12 = alertController.mWindow.findViewById(com.android.systemui.R.id.scrollIndicatorUp);
                    View viewFindViewById13 = alertController.mWindow.findViewById(com.android.systemui.R.id.scrollIndicatorDown);
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api23Impl.setScrollIndicators(view2, i7, 3);
                    if (viewFindViewById12 != null) {
                        viewGroupResolvePanel2.removeView(viewFindViewById12);
                    }
                    if (viewFindViewById13 != null) {
                        viewGroupResolvePanel2.removeView(viewFindViewById13);
                    }
                }
            }
            recycleListView2 = alertController.mListView;
            if (recycleListView2 != null && (listAdapter = alertController.mAdapter) != null) {
                recycleListView2.setAdapter(listAdapter);
                declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslAdapterViewReflector.mClass, "hidden_semSetBottomColor", Integer.TYPE);
                if (declaredMethod != null) {
                    SeslBaseReflector.invoke(recycleListView2, declaredMethod, 0);
                }
                i6 = alertController.mCheckedItem;
                if (i6 > -1) {
                    recycleListView2.setItemChecked(i6, true);
                    recycleListView2.setSelectionFromTop(i6, alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_select_dialog_padding_top));
                }
            }
            z4 = true;
            Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod("com.samsung.sesl.feature.SemFloatingFeature", "hidden_getString", String.class, String.class);
            Object objInvoke = declaredMethod2 == null ? SeslBaseReflector.invoke(null, declaredMethod2, "SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG", "FALSE") : null;
            str = !(objInvoke instanceof String) ? (String) objInvoke : "FALSE";
            boolean zIsLightTheme = SeslMisc.isLightTheme(alertController.mContext);
            boolean z13 = Settings.System.getString(alertController.mContext.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE) == null;
            boolean z14 = !z3 ? alertController.mIsBlurEnabled : true;
            Drawable drawable2 = alertController.mContext.getResources().getDrawable(com.android.systemui.R.drawable.sesl_dialog_inset_background, alertController.mContext.getTheme());
            decorView = alertController.mWindow.getDecorView();
            if (decorView != null && decorView.getBackground() != null && drawable2.getConstantState() != null && !drawable2.getConstantState().equals(decorView.getBackground().getConstantState())) {
                z4 = false;
            }
            if ("FALSE".equalsIgnoreCase(str) && z14 && !z13 && z4) {
                if (viewFindViewById4 != null && viewFindViewById4.getBackground() == null && !zIsLightTheme) {
                    viewFindViewById4.setBackground(alertController.mContext.getDrawable(com.android.systemui.R.drawable.sesl_dialog_middle_panel_background));
                }
                SemBlurCompat.setBlurEffectPreset(viewFindViewById32, zIsLightTheme ? 115 : 130, Integer.valueOf(alertController.mContext.getColor(com.android.systemui.R.color.sesl_dialog_blur_background_color)), Float.valueOf(alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_background_corner_radius)));
                return;
            }
            return;
        }
        z = true;
        z2 = false;
        typedValue = new TypedValue();
        alertController.mContext.getTheme().resolveAttribute(R.attr.colorBackground, typedValue, z);
        if (typedValue.resourceId <= 0) {
        }
        Button button7 = (Button) viewGroupResolvePanel3.findViewById(R.id.button1);
        alertController.mButtonPositive = button7;
        AlertController.AnonymousClass1 anonymousClass12 = alertController.mButtonHandler;
        button7.setOnClickListener(anonymousClass12);
        if (typedValue.resourceId <= 0) {
        }
        if (TextUtils.isEmpty(alertController.mButtonPositiveText)) {
        }
        Button button22 = (Button) viewGroupResolvePanel3.findViewById(R.id.button2);
        alertController.mButtonNegative = button22;
        button22.setOnClickListener(anonymousClass12);
        if (typedValue.resourceId <= 0) {
        }
        if (TextUtils.isEmpty(alertController.mButtonNegativeText)) {
        }
        Button button32 = (Button) viewGroupResolvePanel3.findViewById(R.id.button3);
        alertController.mButtonNeutral = button32;
        button32.setOnClickListener(anonymousClass12);
        if (typedValue.resourceId <= 0) {
        }
        if (TextUtils.isEmpty(alertController.mButtonNeutralText)) {
        }
        Context context2 = alertController.mContext;
        typedValue2 = new TypedValue();
        context2.getTheme().resolveAttribute(com.android.systemui.R.attr.alertDialogCenterButtons, typedValue2, true);
        if (typedValue2.data != 0) {
        }
        if (i == 0) {
        }
        if (alertController.mButtonNeutral.getVisibility() != 0) {
        }
        if (alertController.mButtonPositive.getVisibility() != 0) {
        }
        if (alertController.mButtonNegative.getVisibility() != 0) {
        }
        viewFindViewById = alertController.mWindow.findViewById(com.android.systemui.R.id.sem_divider2);
        if (viewFindViewById == null) {
            i2 = 0;
        }
        viewFindViewById2 = alertController.mWindow.findViewById(com.android.systemui.R.id.sem_divider1);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(i2);
        }
        if (alertController.mDefaultButtonPanelJob != null) {
            AlertController$$ExternalSyntheticLambda1 alertController$$ExternalSyntheticLambda12 = alertController.mDefaultButtonPanelJob;
            alertController$$ExternalSyntheticLambda12.getClass();
            alertController$$ExternalSyntheticLambda12.f$0.getClass();
            linearLayout = (LinearLayout) viewGroup.findViewById(com.android.systemui.R.id.buttonBarLayout);
            if (linearLayout != null) {
            }
        }
        if (alertController.mCustomTitleView == null) {
        }
        if (viewGroup2.getVisibility() == i3) {
        }
        if (viewGroupResolvePanel != null) {
        }
        if (viewGroupResolvePanel3.getVisibility() == i3) {
        }
        if (viewFindViewById5 == null) {
        }
        if (viewFindViewById6 == null) {
        }
        View view3 = alertController.mCustomTitleView;
        if (view3 == null) {
        }
        if (z3) {
            viewFindViewById4.setPadding(0, 0, 0, 0);
        } else {
            viewFindViewById4.setPadding(0, 0, 0, 0);
        }
        if (z3) {
            View viewFindViewById112 = viewFindViewById32.findViewById(com.android.systemui.R.id.title_template);
            int dimensionPixelSize3 = alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
            viewFindViewById112.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
        }
        int dimensionPixelSize22 = alertController.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_text_size);
        if (alertController.mButtonPositive.getVisibility() == 8) {
        }
        if (alertController.mButtonNegative.getVisibility() != 8) {
        }
        if (alertController.mButtonNeutral.getVisibility() != 8) {
        }
        if (!viewFindViewById32.isInTouchMode()) {
        }
        if (i4 != 0) {
            nestedScrollView.setClipToPadding(true);
        }
        recycleListView = alertController.mListView;
        if (recycleListView != null) {
        }
        if (!z3) {
        }
        recycleListView2 = alertController.mListView;
        if (recycleListView2 != null) {
            recycleListView2.setAdapter(listAdapter);
            declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslAdapterViewReflector.mClass, "hidden_semSetBottomColor", Integer.TYPE);
            if (declaredMethod != null) {
            }
            i6 = alertController.mCheckedItem;
            if (i6 > -1) {
            }
        }
        z4 = true;
        Method declaredMethod22 = SeslBaseReflector.getDeclaredMethod("com.samsung.sesl.feature.SemFloatingFeature", "hidden_getString", String.class, String.class);
        if (declaredMethod22 == null) {
        }
        if (!(objInvoke instanceof String)) {
        }
        boolean zIsLightTheme2 = SeslMisc.isLightTheme(alertController.mContext);
        if (Settings.System.getString(alertController.mContext.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE) == null) {
        }
        if (!z3) {
        }
        Drawable drawable22 = alertController.mContext.getResources().getDrawable(com.android.systemui.R.drawable.sesl_dialog_inset_background, alertController.mContext.getTheme());
        decorView = alertController.mWindow.getDecorView();
        if (decorView != null) {
            z4 = false;
        }
        if ("FALSE".equalsIgnoreCase(str)) {
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mAlert.mScrollView;
        if (nestedScrollView == null || !nestedScrollView.executeKeyEvent(keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mAlert.mScrollView;
        if (nestedScrollView == null || !nestedScrollView.executeKeyEvent(keyEvent)) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    public final void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        AlertController alertController = this.mAlert;
        alertController.mTitle = charSequence;
        TextView textView = alertController.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
        alertController.mWindow.setTitle(charSequence);
    }

    public AlertDialog(Context context, int i) {
        super(context, resolveDialogTheme(i, context));
        this.mAlert = new AlertController(getContext(), this, getWindow());
    }

    public AlertDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0);
        setCancelable(z);
        setOnCancelListener(onCancelListener);
    }
}
