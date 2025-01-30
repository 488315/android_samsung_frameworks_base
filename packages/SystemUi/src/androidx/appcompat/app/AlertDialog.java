package androidx.appcompat.app;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.util.Consumer;
import androidx.core.view.SeslTouchDelegateFactory$$ExternalSyntheticLambda0;
import androidx.core.view.SeslTouchTargetDelegate;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.widget.SeslAdapterViewReflector;
import androidx.reflect.widget.SeslTextViewReflector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    public final AlertController mAlert;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {

        /* renamed from: P */
        public final AlertController.AlertParams f0P;
        public final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(0, context));
        }

        public final AlertDialog create() {
            ListAdapter listAdapter;
            final AlertController.AlertParams alertParams = this.f0P;
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
                }
                Drawable drawable = alertParams.mIcon;
                if (drawable != null) {
                    alertController.mIcon = drawable;
                    alertController.mIconId = 0;
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
                    final int i = alertController.mMultiChoiceItemLayout;
                    final int i2 = R.id.text1;
                    final CharSequence[] charSequenceArr = alertParams.mItems;
                    listAdapter = new ArrayAdapter(context, i, i2, charSequenceArr) { // from class: androidx.appcompat.app.AlertController.AlertParams.1
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
                    listAdapter = alertParams.mAdapter;
                    if (listAdapter == null) {
                        listAdapter = new AlertController.CheckedItemAdapter(alertParams.mContext, i3, R.id.text1, alertParams.mItems);
                    }
                }
                alertController.mAdapter = listAdapter;
                alertController.mCheckedItem = alertParams.mCheckedItem;
                if (alertParams.mOnClickListener != null) {
                    recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.3
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
                    recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.4
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
                    recycleListView.setChoiceMode(1);
                } else if (alertParams.mIsMultiChoice) {
                    recycleListView.setChoiceMode(2);
                }
                alertController.mListView = recycleListView;
            }
            View view2 = alertParams.mView;
            if (view2 != null) {
                alertController.mView = view2;
                alertController.mViewLayoutResId = 0;
                alertController.mViewSpacingSpecified = false;
            } else {
                int i4 = alertParams.mViewLayoutResId;
                if (i4 != 0) {
                    alertController.mView = null;
                    alertController.mViewLayoutResId = i4;
                    alertController.mViewSpacingSpecified = false;
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

        public final void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.f0P;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(i);
            alertParams.mNegativeButtonListener = onClickListener;
        }

        public final void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.f0P;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(i);
            alertParams.mPositiveButtonListener = onClickListener;
        }

        public final void setView(View view) {
            AlertController.AlertParams alertParams = this.f0P;
            alertParams.mView = view;
            alertParams.mViewLayoutResId = 0;
        }

        public Builder(Context context, int i) {
            this.f0P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(i, context)));
            this.mTheme = i;
        }

        public final void setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.f0P;
            alertParams.mPositiveButtonText = charSequence;
            alertParams.mPositiveButtonListener = onClickListener;
        }
    }

    public AlertDialog(Context context) {
        this(context, 0);
    }

    static int resolveDialogTheme(int i, Context context) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x040e  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0489  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x04c8  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x03fc  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02ef  */
    /* JADX WARN: Type inference failed for: r8v33, types: [androidx.appcompat.app.AlertController$$ExternalSyntheticLambda0] */
    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onCreate(Bundle bundle) {
        boolean z;
        boolean z2;
        TypedValue typedValue;
        boolean z3;
        boolean isEmpty;
        View view;
        Drawable drawable;
        View view2;
        int i;
        Drawable drawable2;
        ViewGroup viewGroup;
        Drawable drawable3;
        TypedValue typedValue2;
        View findViewById;
        int i2;
        View findViewById2;
        int i3;
        boolean z4;
        int i4;
        View view3;
        int i5;
        int i6;
        AlertController.RecycleListView recycleListView;
        AlertController.RecycleListView recycleListView2;
        ListAdapter listAdapter;
        NestedScrollView nestedScrollView;
        boolean z5;
        ViewGroup viewGroup2;
        super.onCreate(bundle);
        final AlertController alertController = this.mAlert;
        int i7 = alertController.mButtonPanelSideLayout;
        alertController.mDialog.setContentView(alertController.mAlertDialogLayout);
        Window window = alertController.mWindow;
        final View findViewById3 = window.findViewById(com.android.systemui.R.id.parentPanel);
        findViewById3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.appcompat.app.AlertController.2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view4, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {
                view4.post(new Runnable() { // from class: androidx.appcompat.app.AlertController.2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i16 = AlertController.this.mContext.getResources().getConfiguration().orientation;
                        AlertController alertController2 = AlertController.this;
                        if (i16 != alertController2.mLastOrientation) {
                            View findViewById4 = alertController2.mWindow.findViewById(com.android.systemui.R.id.parentPanel);
                            View findViewById5 = findViewById4.findViewById(com.android.systemui.R.id.title_template);
                            View findViewById6 = findViewById4.findViewById(com.android.systemui.R.id.scrollView);
                            View findViewById7 = findViewById4.findViewById(com.android.systemui.R.id.topPanel);
                            View findViewById8 = findViewById4.findViewById(com.android.systemui.R.id.buttonBarLayout);
                            View findViewById9 = findViewById4.findViewById(com.android.systemui.R.id.customPanel);
                            View findViewById10 = findViewById4.findViewById(com.android.systemui.R.id.contentPanel);
                            boolean z6 = (findViewById9 == null || findViewById9.getVisibility() == 8) ? false : true;
                            boolean z7 = (findViewById7 == null || findViewById7.getVisibility() == 8) ? false : true;
                            boolean z8 = (findViewById10 == null || findViewById10.getVisibility() == 8) ? false : true;
                            View view5 = alertController2.mCustomTitleView;
                            boolean z9 = (view5 == null || view5.getVisibility() == 8) ? false : true;
                            Resources resources = alertController2.mContext.getResources();
                            if ((!z6 || z7 || z8) && !z9) {
                                findViewById4.setPadding(0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_padding_top), 0, 0);
                            } else {
                                findViewById4.setPadding(0, 0, 0, 0);
                            }
                            if (findViewById5 != null) {
                                int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
                                if (z6 && z7 && !z8) {
                                    findViewById5.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                                } else {
                                    findViewById5.setPadding(dimensionPixelSize, 0, dimensionPixelSize, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_padding_bottom));
                                }
                            }
                            if (findViewById6 != null) {
                                findViewById6.setPadding(resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_scroll_padding_start), 0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_scroll_padding_end), resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_padding_bottom));
                            }
                            if (findViewById8 != null) {
                                int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_bar_padding_horizontal);
                                findViewById8.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_bar_padding_bottom));
                            }
                            findViewById3.requestLayout();
                        }
                        AlertController alertController3 = AlertController.this;
                        alertController3.mLastOrientation = alertController3.mContext.getResources().getConfiguration().orientation;
                    }
                });
            }
        });
        View findViewById4 = findViewById3.findViewById(com.android.systemui.R.id.topPanel);
        View findViewById5 = findViewById3.findViewById(com.android.systemui.R.id.contentPanel);
        View findViewById6 = findViewById3.findViewById(com.android.systemui.R.id.buttonPanel);
        ViewGroup viewGroup3 = (ViewGroup) findViewById3.findViewById(com.android.systemui.R.id.customPanel);
        View view4 = alertController.mView;
        Context context = alertController.mContext;
        if (view4 == null) {
            view4 = alertController.mViewLayoutResId != 0 ? LayoutInflater.from(context).inflate(alertController.mViewLayoutResId, viewGroup3, false) : null;
        }
        boolean z6 = view4 != null;
        if (!z6 || !AlertController.canTextInput(view4)) {
            window.setFlags(131072, 131072);
        }
        if (z6) {
            FrameLayout frameLayout = (FrameLayout) window.findViewById(com.android.systemui.R.id.custom);
            frameLayout.addView(view4, new ViewGroup.LayoutParams(-1, -1));
            if (alertController.mViewSpacingSpecified) {
                frameLayout.setPadding(0, 0, 0, 0);
            }
            if (alertController.mListView != null) {
                if (viewGroup3.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) viewGroup3.getLayoutParams()).weight = 0.0f;
                } else {
                    ((LinearLayout.LayoutParams) ((LinearLayoutCompat.LayoutParams) viewGroup3.getLayoutParams())).weight = 0.0f;
                }
            }
        } else {
            viewGroup3.setVisibility(8);
        }
        View findViewById7 = viewGroup3.findViewById(com.android.systemui.R.id.topPanel);
        View findViewById8 = viewGroup3.findViewById(com.android.systemui.R.id.contentPanel);
        View findViewById9 = viewGroup3.findViewById(com.android.systemui.R.id.buttonPanel);
        ViewGroup resolvePanel = AlertController.resolvePanel(findViewById7, findViewById4);
        ViewGroup resolvePanel2 = AlertController.resolvePanel(findViewById8, findViewById5);
        ViewGroup resolvePanel3 = AlertController.resolvePanel(findViewById9, findViewById6);
        alertController.mDefaultButtonPanelJob = resolvePanel3 == findViewById6 ? new Consumer() { // from class: androidx.appcompat.app.AlertController$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                final LinearLayout linearLayout;
                ViewGroup viewGroup4 = (ViewGroup) obj;
                AlertController.this.getClass();
                if (viewGroup4 == null || (linearLayout = (LinearLayout) viewGroup4.findViewById(com.android.systemui.R.id.buttonBarLayout)) == null) {
                    return;
                }
                final ArrayList arrayList = new ArrayList();
                int childCount = linearLayout.getChildCount();
                for (int i8 = 0; i8 < childCount; i8++) {
                    View childAt = linearLayout.getChildAt(i8);
                    if ((childAt instanceof Button) && childAt.getVisibility() != 8) {
                        arrayList.add(childAt);
                    }
                }
                linearLayout.post(new Runnable() { // from class: androidx.appcompat.app.AlertController$$ExternalSyntheticLambda1
                    /* JADX WARN: Type inference failed for: r1v14, types: [androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0] */
                    @Override // java.lang.Runnable
                    public final void run() {
                        final SeslTouchTargetDelegate.Builder builder;
                        final SeslTouchTargetDelegate.ExtraInsets m33of;
                        LinearLayout linearLayout2 = linearLayout;
                        ArrayList arrayList2 = arrayList;
                        if (arrayList2.size() == 0) {
                            builder = null;
                        } else {
                            int height = linearLayout2.getHeight();
                            int width = linearLayout2.getWidth();
                            Rect rect = new Rect(0, 0, width, height);
                            ArrayList arrayList3 = new ArrayList();
                            Iterator it = arrayList2.iterator();
                            while (it.hasNext()) {
                                arrayList3.add(SeslTouchTargetDelegate.calculateViewBounds(linearLayout2, (View) it.next()));
                            }
                            SeslTouchDelegateFactory$$ExternalSyntheticLambda0 seslTouchDelegateFactory$$ExternalSyntheticLambda0 = linearLayout2.getOrientation() == 0 ? new SeslTouchDelegateFactory$$ExternalSyntheticLambda0(rect, 0) : new SeslTouchDelegateFactory$$ExternalSyntheticLambda0(rect, 1);
                            Rect rect2 = (Rect) arrayList3.get(arrayList3.size() - 1);
                            arrayList3.add(new Rect(Math.max(0, width - rect2.right) + width, Math.max(0, height - rect2.bottom) + height, width, height));
                            Rect rect3 = new Rect(0, 0, 0, 0);
                            SeslTouchTargetDelegate.Builder builder2 = new SeslTouchTargetDelegate.Builder(linearLayout2);
                            int i9 = 0;
                            while (i9 < arrayList2.size()) {
                                Rect rect4 = (Rect) arrayList3.get(i9);
                                int i10 = i9 + 1;
                                Rect rect5 = (Rect) arrayList3.get(i10);
                                int i11 = seslTouchDelegateFactory$$ExternalSyntheticLambda0.$r8$classId;
                                Rect rect6 = seslTouchDelegateFactory$$ExternalSyntheticLambda0.f$0;
                                switch (i11) {
                                    case 0:
                                        m33of = SeslTouchTargetDelegate.ExtraInsets.m33of(rect4.left - rect3.right, rect4.top - rect6.top, Math.max(0, rect5.left - rect4.right) / 2, rect6.bottom - rect4.bottom);
                                        break;
                                    default:
                                        m33of = SeslTouchTargetDelegate.ExtraInsets.m33of(rect4.left - rect6.left, rect4.top - rect3.bottom, rect6.right - rect4.right, Math.max(0, rect5.top - rect4.bottom) / 2);
                                        break;
                                }
                                final View view5 = (View) arrayList2.get(i9);
                                ((LinkedList) builder2.mQueue).add(new Consumer() { // from class: androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda1
                                    @Override // androidx.core.util.Consumer
                                    public final void accept(Object obj2) {
                                        ((SeslTouchTargetDelegate) obj2).addTouchDelegate(view5, m33of);
                                    }
                                });
                                rect3 = rect4;
                                i9 = i10;
                            }
                            builder = builder2;
                        }
                        if (builder != null) {
                            final View view6 = builder.mAnchorView;
                            Objects.requireNonNull(view6);
                            final ?? r1 = new Consumer() { // from class: androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0
                                @Override // androidx.core.util.Consumer
                                public final void accept(Object obj2) {
                                    view6.setTouchDelegate((SeslTouchTargetDelegate) obj2);
                                }
                            };
                            view6.post(new Runnable() { // from class: androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SeslTouchTargetDelegate.Builder builder3 = SeslTouchTargetDelegate.Builder.this;
                                    Consumer consumer = r1;
                                    builder3.getClass();
                                    SeslTouchTargetDelegate seslTouchTargetDelegate = new SeslTouchTargetDelegate(builder3.mAnchorView);
                                    Iterator it2 = builder3.mQueue.iterator();
                                    while (it2.hasNext()) {
                                        ((Consumer) it2.next()).accept(seslTouchTargetDelegate);
                                    }
                                    consumer.accept(seslTouchTargetDelegate);
                                    int i12 = SeslTouchTargetDelegate.$r8$clinit;
                                }
                            });
                        }
                    }
                });
            }
        } : null;
        NestedScrollView nestedScrollView2 = (NestedScrollView) window.findViewById(com.android.systemui.R.id.scrollView);
        alertController.mScrollView = nestedScrollView2;
        nestedScrollView2.setFocusable(false);
        alertController.mScrollView.setNestedScrollingEnabled(false);
        TextView textView = (TextView) resolvePanel2.findViewById(R.id.message);
        alertController.mMessageView = textView;
        if (textView != null) {
            CharSequence charSequence = alertController.mMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                alertController.checkMaxFontScale(context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_body_text_size), alertController.mMessageView);
            } else {
                textView.setVisibility(8);
                alertController.mScrollView.removeView(alertController.mMessageView);
                if (alertController.mListView != null) {
                    ViewGroup viewGroup4 = (ViewGroup) alertController.mScrollView.getParent();
                    int indexOfChild = viewGroup4.indexOfChild(alertController.mScrollView);
                    viewGroup4.removeViewAt(indexOfChild);
                    viewGroup4.addView(alertController.mListView, indexOfChild, new ViewGroup.LayoutParams(-1, -1));
                } else {
                    resolvePanel2.setVisibility(8);
                }
            }
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null) {
            z = true;
            if (Settings.System.getInt(contentResolver, "show_button_background", 0) == 1) {
                z2 = true;
                typedValue = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.colorBackground, typedValue, z);
                int color = typedValue.resourceId <= 0 ? context.getResources().getColor(typedValue.resourceId) : -1;
                z3 = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") == null;
                TypedValue typedValue3 = new TypedValue();
                context.getTheme().resolveAttribute(com.android.systemui.R.attr.colorPrimaryDark, typedValue3, true);
                int color2 = typedValue3.resourceId == 0 ? context.getResources().getColor(typedValue3.resourceId) : typedValue3.data;
                Button button = (Button) resolvePanel3.findViewById(R.id.button1);
                alertController.mButtonPositive = button;
                AlertController.ViewOnClickListenerC00211 viewOnClickListenerC00211 = alertController.mButtonHandler;
                button.setOnClickListener(viewOnClickListenerC00211);
                if (z3) {
                    alertController.mButtonPositive.setTextColor(color2);
                }
                if (typedValue.resourceId <= 0) {
                    SeslTextViewReflector.semSetButtonShapeEnabled(color, alertController.mButtonPositive, z2);
                } else {
                    SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonPositive, z2);
                }
                isEmpty = TextUtils.isEmpty(alertController.mButtonPositiveText);
                int i8 = alertController.mButtonIconDimen;
                if (isEmpty || alertController.mButtonPositiveIcon != null) {
                    view = findViewById5;
                    alertController.mButtonPositive.setText(alertController.mButtonPositiveText);
                    drawable = alertController.mButtonPositiveIcon;
                    if (drawable == null) {
                        drawable.setBounds(0, 0, i8, i8);
                        view2 = findViewById4;
                        alertController.mButtonPositive.setCompoundDrawables(alertController.mButtonPositiveIcon, null, null, null);
                    } else {
                        view2 = findViewById4;
                    }
                    alertController.mButtonPositive.setVisibility(0);
                    i = 1;
                } else {
                    view = findViewById5;
                    alertController.mButtonPositive.setVisibility(8);
                    view2 = findViewById4;
                    i = 0;
                }
                Button button2 = (Button) resolvePanel3.findViewById(R.id.button2);
                alertController.mButtonNegative = button2;
                button2.setOnClickListener(viewOnClickListenerC00211);
                if (z3) {
                    alertController.mButtonNegative.setTextColor(color2);
                }
                if (typedValue.resourceId <= 0) {
                    SeslTextViewReflector.semSetButtonShapeEnabled(color, alertController.mButtonNegative, z2);
                } else {
                    SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonNegative, z2);
                }
                if (TextUtils.isEmpty(alertController.mButtonNegativeText) || alertController.mButtonNegativeIcon != null) {
                    alertController.mButtonNegative.setText(alertController.mButtonNegativeText);
                    drawable2 = alertController.mButtonNegativeIcon;
                    if (drawable2 == null) {
                        drawable2.setBounds(0, 0, i8, i8);
                        viewGroup = viewGroup3;
                        alertController.mButtonNegative.setCompoundDrawables(alertController.mButtonNegativeIcon, null, null, null);
                    } else {
                        viewGroup = viewGroup3;
                    }
                    alertController.mButtonNegative.setVisibility(0);
                    i |= 2;
                } else {
                    alertController.mButtonNegative.setVisibility(8);
                    viewGroup = viewGroup3;
                }
                Button button3 = (Button) resolvePanel3.findViewById(R.id.button3);
                alertController.mButtonNeutral = button3;
                button3.setOnClickListener(viewOnClickListenerC00211);
                if (z3) {
                    alertController.mButtonNeutral.setTextColor(color2);
                }
                if (typedValue.resourceId <= 0) {
                    SeslTextViewReflector.semSetButtonShapeEnabled(color, alertController.mButtonNeutral, z2);
                } else {
                    SeslTextViewReflector.semSetButtonShapeEnabled(alertController.mButtonNeutral, z2);
                }
                if (TextUtils.isEmpty(alertController.mButtonNeutralText) || alertController.mButtonNeutralIcon != null) {
                    alertController.mButtonNeutral.setText(alertController.mButtonNeutralText);
                    drawable3 = alertController.mButtonNeutralIcon;
                    if (drawable3 != null) {
                        drawable3.setBounds(0, 0, i8, i8);
                        alertController.mButtonNeutral.setCompoundDrawables(alertController.mButtonNeutralIcon, null, null, null);
                    }
                    alertController.mButtonNeutral.setVisibility(0);
                    i |= 4;
                } else {
                    alertController.mButtonNeutral.setVisibility(8);
                }
                typedValue2 = new TypedValue();
                context.getTheme().resolveAttribute(com.android.systemui.R.attr.alertDialogCenterButtons, typedValue2, true);
                if (typedValue2.data == 0) {
                    if (i == 1) {
                        AlertController.centerButton(alertController.mButtonPositive);
                    } else if (i == 2) {
                        AlertController.centerButton(alertController.mButtonNegative);
                    } else if (i == 4) {
                        AlertController.centerButton(alertController.mButtonNeutral);
                    }
                }
                if (!(i == 0)) {
                    resolvePanel3.setVisibility(8);
                }
                boolean z7 = alertController.mButtonNeutral.getVisibility() != 0;
                boolean z8 = alertController.mButtonPositive.getVisibility() != 0;
                boolean z9 = alertController.mButtonNegative.getVisibility() != 0;
                findViewById = window.findViewById(com.android.systemui.R.id.sem_divider2);
                if (findViewById == null && ((z7 && z8) || (z7 && z9))) {
                    i2 = 0;
                    findViewById.setVisibility(0);
                } else {
                    i2 = 0;
                }
                findViewById2 = window.findViewById(com.android.systemui.R.id.sem_divider1);
                if (findViewById2 != null && z8 && z9) {
                    findViewById2.setVisibility(i2);
                }
                if (alertController.mDefaultButtonPanelJob != null && (viewGroup2 = (ViewGroup) resolvePanel3.findViewById(com.android.systemui.R.id.buttonBarLayout)) != null) {
                    alertController.mDefaultButtonPanelJob.accept(viewGroup2);
                }
                if (alertController.mCustomTitleView == null) {
                    resolvePanel.addView(alertController.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
                    i3 = 8;
                    window.findViewById(com.android.systemui.R.id.title_template).setVisibility(8);
                } else {
                    alertController.mIconView = (ImageView) window.findViewById(R.id.icon);
                    if ((!TextUtils.isEmpty(alertController.mTitle)) && alertController.mShowTitle) {
                        TextView textView2 = (TextView) window.findViewById(com.android.systemui.R.id.alertTitle);
                        alertController.mTitleView = textView2;
                        textView2.setText(alertController.mTitle);
                        alertController.checkMaxFontScale(context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_title_text_size), alertController.mTitleView);
                        int i9 = alertController.mIconId;
                        if (i9 != 0) {
                            alertController.mIconView.setImageResource(i9);
                        } else {
                            Drawable drawable4 = alertController.mIcon;
                            if (drawable4 != null) {
                                alertController.mIconView.setImageDrawable(drawable4);
                            } else {
                                alertController.mTitleView.setPadding(alertController.mIconView.getPaddingLeft(), alertController.mIconView.getPaddingTop(), alertController.mIconView.getPaddingRight(), alertController.mIconView.getPaddingBottom());
                                i3 = 8;
                                alertController.mIconView.setVisibility(8);
                            }
                        }
                        i3 = 8;
                    } else {
                        i3 = 8;
                        window.findViewById(com.android.systemui.R.id.title_template).setVisibility(8);
                        alertController.mIconView.setVisibility(8);
                        resolvePanel.setVisibility(8);
                    }
                }
                z4 = viewGroup.getVisibility() == i3;
                i4 = (resolvePanel != null || resolvePanel.getVisibility() == i3) ? 0 : 1;
                boolean z10 = resolvePanel3.getVisibility() == i3;
                boolean z11 = view2 == null && view2.getVisibility() != i3;
                boolean z12 = view == null && view.getVisibility() != i3;
                View view5 = alertController.mCustomTitleView;
                boolean z13 = view5 == null && view5.getVisibility() != i3;
                if ((z4 || z11 || z12) && !z13) {
                    view3 = findViewById3;
                    i5 = 0;
                } else {
                    view3 = findViewById3;
                    i5 = 0;
                    view3.setPadding(0, 0, 0, 0);
                }
                if (z4 && z11 && !z12) {
                    View findViewById10 = view3.findViewById(com.android.systemui.R.id.title_template);
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
                    findViewById10.setPadding(dimensionPixelSize, i5, dimensionPixelSize, i5);
                }
                int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_text_size);
                if (alertController.mButtonPositive.getVisibility() == 8) {
                    i6 = 0;
                    alertController.mButtonPositive.setTextSize(0, dimensionPixelSize2);
                    alertController.checkMaxFontScale(dimensionPixelSize2, alertController.mButtonPositive);
                } else {
                    i6 = 0;
                }
                if (alertController.mButtonNegative.getVisibility() != 8) {
                    alertController.mButtonNegative.setTextSize(i6, dimensionPixelSize2);
                    alertController.checkMaxFontScale(dimensionPixelSize2, alertController.mButtonNegative);
                }
                if (alertController.mButtonNeutral.getVisibility() != 8) {
                    alertController.mButtonNeutral.setTextSize(i6, dimensionPixelSize2);
                    alertController.checkMaxFontScale(dimensionPixelSize2, alertController.mButtonNeutral);
                }
                if (!view3.isInTouchMode()) {
                    if (!(z4 ? viewGroup : resolvePanel2).requestFocus()) {
                        AlertController.RecycleListView recycleListView3 = alertController.mListView;
                        if (recycleListView3 != null) {
                            recycleListView3.setSelection(0);
                        } else {
                            z5 = false;
                            if (!z5) {
                                if (alertController.mButtonPositive.getVisibility() == 0) {
                                    alertController.mButtonPositive.requestFocus();
                                } else if (alertController.mButtonNegative.getVisibility() == 0) {
                                    alertController.mButtonNegative.requestFocus();
                                } else if (alertController.mButtonNeutral.getVisibility() == 0) {
                                    alertController.mButtonNeutral.requestFocus();
                                }
                            }
                        }
                    }
                    z5 = true;
                    if (!z5) {
                    }
                }
                if (i4 != 0 && (nestedScrollView = alertController.mScrollView) != null) {
                    nestedScrollView.setClipToPadding(true);
                }
                recycleListView = alertController.mListView;
                if (recycleListView instanceof AlertController.RecycleListView) {
                    recycleListView.getClass();
                    if (!z10 || i4 == 0) {
                        recycleListView.setPadding(recycleListView.getPaddingLeft(), i4 != 0 ? recycleListView.getPaddingTop() : recycleListView.mPaddingTopNoTitle, recycleListView.getPaddingRight(), z10 ? recycleListView.getPaddingBottom() : recycleListView.mPaddingBottomNoButtons);
                    }
                }
                if (!z4) {
                    View view6 = alertController.mListView;
                    if (view6 == null) {
                        view6 = alertController.mScrollView;
                    }
                    if (view6 != null) {
                        int i10 = z10 ? 2 : 0;
                        View findViewById11 = window.findViewById(com.android.systemui.R.id.scrollIndicatorUp);
                        View findViewById12 = window.findViewById(com.android.systemui.R.id.scrollIndicatorDown);
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api23Impl.setScrollIndicators(view6, i4 | i10, 3);
                        if (findViewById11 != null) {
                            resolvePanel2.removeView(findViewById11);
                        }
                        if (findViewById12 != null) {
                            resolvePanel2.removeView(findViewById12);
                        }
                    }
                }
                recycleListView2 = alertController.mListView;
                if (recycleListView2 != null || (listAdapter = alertController.mAdapter) == null) {
                }
                recycleListView2.setAdapter(listAdapter);
                Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslAdapterViewReflector.mClass, "hidden_semSetBottomColor", Integer.TYPE);
                if (declaredMethod != null) {
                    SeslBaseReflector.invoke(recycleListView2, declaredMethod, 0);
                }
                int i11 = alertController.mCheckedItem;
                if (i11 > -1) {
                    recycleListView2.setItemChecked(i11, true);
                    recycleListView2.setSelectionFromTop(i11, context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_select_dialog_padding_top));
                    return;
                }
                return;
            }
        } else {
            z = true;
        }
        z2 = false;
        typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorBackground, typedValue, z);
        if (typedValue.resourceId <= 0) {
        }
        if (Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") == null) {
        }
        TypedValue typedValue32 = new TypedValue();
        context.getTheme().resolveAttribute(com.android.systemui.R.attr.colorPrimaryDark, typedValue32, true);
        if (typedValue32.resourceId == 0) {
        }
        Button button4 = (Button) resolvePanel3.findViewById(R.id.button1);
        alertController.mButtonPositive = button4;
        AlertController.ViewOnClickListenerC00211 viewOnClickListenerC002112 = alertController.mButtonHandler;
        button4.setOnClickListener(viewOnClickListenerC002112);
        if (z3) {
        }
        if (typedValue.resourceId <= 0) {
        }
        isEmpty = TextUtils.isEmpty(alertController.mButtonPositiveText);
        int i82 = alertController.mButtonIconDimen;
        if (isEmpty) {
        }
        view = findViewById5;
        alertController.mButtonPositive.setText(alertController.mButtonPositiveText);
        drawable = alertController.mButtonPositiveIcon;
        if (drawable == null) {
        }
        alertController.mButtonPositive.setVisibility(0);
        i = 1;
        Button button22 = (Button) resolvePanel3.findViewById(R.id.button2);
        alertController.mButtonNegative = button22;
        button22.setOnClickListener(viewOnClickListenerC002112);
        if (z3) {
        }
        if (typedValue.resourceId <= 0) {
        }
        if (TextUtils.isEmpty(alertController.mButtonNegativeText)) {
        }
        alertController.mButtonNegative.setText(alertController.mButtonNegativeText);
        drawable2 = alertController.mButtonNegativeIcon;
        if (drawable2 == null) {
        }
        alertController.mButtonNegative.setVisibility(0);
        i |= 2;
        Button button32 = (Button) resolvePanel3.findViewById(R.id.button3);
        alertController.mButtonNeutral = button32;
        button32.setOnClickListener(viewOnClickListenerC002112);
        if (z3) {
        }
        if (typedValue.resourceId <= 0) {
        }
        if (TextUtils.isEmpty(alertController.mButtonNeutralText)) {
        }
        alertController.mButtonNeutral.setText(alertController.mButtonNeutralText);
        drawable3 = alertController.mButtonNeutralIcon;
        if (drawable3 != null) {
        }
        alertController.mButtonNeutral.setVisibility(0);
        i |= 4;
        typedValue2 = new TypedValue();
        context.getTheme().resolveAttribute(com.android.systemui.R.attr.alertDialogCenterButtons, typedValue2, true);
        if (typedValue2.data == 0) {
        }
        if (!(i == 0)) {
        }
        if (alertController.mButtonNeutral.getVisibility() != 0) {
        }
        if (alertController.mButtonPositive.getVisibility() != 0) {
        }
        if (alertController.mButtonNegative.getVisibility() != 0) {
        }
        findViewById = window.findViewById(com.android.systemui.R.id.sem_divider2);
        if (findViewById == null) {
        }
        i2 = 0;
        findViewById2 = window.findViewById(com.android.systemui.R.id.sem_divider1);
        if (findViewById2 != null) {
            findViewById2.setVisibility(i2);
        }
        if (alertController.mDefaultButtonPanelJob != null) {
            alertController.mDefaultButtonPanelJob.accept(viewGroup2);
        }
        if (alertController.mCustomTitleView == null) {
        }
        if (viewGroup.getVisibility() == i3) {
        }
        if (resolvePanel != null) {
        }
        if (resolvePanel3.getVisibility() == i3) {
        }
        if (view2 == null) {
        }
        if (view == null) {
        }
        View view52 = alertController.mCustomTitleView;
        if (view52 == null) {
        }
        if (z4) {
        }
        view3 = findViewById3;
        i5 = 0;
        if (z4) {
            View findViewById102 = view3.findViewById(com.android.systemui.R.id.title_template);
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_padding_horizontal);
            findViewById102.setPadding(dimensionPixelSize3, i5, dimensionPixelSize3, i5);
        }
        int dimensionPixelSize22 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_dialog_button_text_size);
        if (alertController.mButtonPositive.getVisibility() == 8) {
        }
        if (alertController.mButtonNegative.getVisibility() != 8) {
        }
        if (alertController.mButtonNeutral.getVisibility() != 8) {
        }
        if (!view3.isInTouchMode()) {
        }
        if (i4 != 0) {
            nestedScrollView.setClipToPadding(true);
        }
        recycleListView = alertController.mListView;
        if (recycleListView instanceof AlertController.RecycleListView) {
        }
        if (!z4) {
        }
        recycleListView2 = alertController.mListView;
        if (recycleListView2 != null) {
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mAlert.mScrollView;
        if (nestedScrollView != null && nestedScrollView.executeKeyEvent(keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mAlert.mScrollView;
        if (nestedScrollView != null && nestedScrollView.executeKeyEvent(keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
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
