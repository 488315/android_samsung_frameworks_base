package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.core.widget.NestedScrollView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    public final AlertController mAlert;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            r6 = recycleListView2;
                        }

                        @Override // android.widget.ArrayAdapter, android.widget.Adapter
                        public final View getView(int i3, View view2, ViewGroup viewGroup) {
                            View view3 = super.getView(i3, view2, viewGroup);
                            boolean[] zArr = AlertParams.this.mCheckedItems;
                            if (zArr != null && zArr[i3]) {
                                r6.setItemChecked(i3, true);
                            }
                            return view3;
                        }
                    };
                } else {
                    int i3 = alertParams.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
                    ListAdapter listAdapter2 = alertParams.mAdapter;
                    if (listAdapter2 == null) {
                        listAdapter2 = new AlertController.CheckedItemAdapter(alertParams.mContext, i3, R.id.text1, alertParams.mItems);
                    }
                    listAdapter = listAdapter2;
                }
                alertController.mAdapter = listAdapter;
                alertController.mCheckedItem = alertParams.mCheckedItem;
                if (alertParams.mOnClickListener != null) {
                    recycleListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.3
                        public final /* synthetic */ AlertController val$dialog;

                        public AnonymousClass3(final AlertController alertController2) {
                            r2 = alertController2;
                        }

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view2, int i4, long j) {
                            AlertParams.this.mOnClickListener.onClick(r2.mDialog, i4);
                            if (AlertParams.this.mIsSingleChoice) {
                                return;
                            }
                            r2.mDialog.dismiss();
                        }
                    });
                } else if (alertParams.mOnCheckboxClickListener != null) {
                    recycleListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.4
                        public final /* synthetic */ AlertController val$dialog;
                        public final /* synthetic */ RecycleListView val$listView;

                        public AnonymousClass4(final RecycleListView recycleListView2, final AlertController alertController2) {
                            r2 = recycleListView2;
                            r3 = alertController2;
                        }

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view2, int i4, long j) {
                            boolean[] zArr = AlertParams.this.mCheckedItems;
                            if (zArr != null) {
                                zArr[i4] = r2.isItemChecked(i4);
                            }
                            AlertParams.this.mOnCheckboxClickListener.onClick(r3.mDialog, i4, r2.isItemChecked(i4));
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x03fd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0443  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x04a6  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x04b2  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04d8  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0563  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x058d  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x05b2  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x05d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:244:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0596  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0578  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x056f  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x043a  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02af  */
    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate(android.os.Bundle r20) {
        /*
            Method dump skipped, instructions count: 1566
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AlertDialog.onCreate(android.os.Bundle):void");
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
