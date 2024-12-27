package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.hardware.biometrics.PromptContentItem;
import android.hardware.biometrics.PromptContentItemBulletedText;
import android.hardware.biometrics.PromptContentItemPlainText;
import android.hardware.biometrics.PromptContentView;
import android.hardware.biometrics.PromptContentViewWithMoreOptionsButton;
import android.hardware.biometrics.PromptVerticalListContentView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

final class BiometricCustomizedViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptContentView $contentView;
    final /* synthetic */ Spaghetti.Callback $legacyCallback;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricCustomizedViewBinder$bind$1(PromptContentView promptContentView, Spaghetti.Callback callback, Continuation continuation) {
        super(3, continuation);
        this.$contentView = promptContentView;
        this.$legacyCallback = callback;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricCustomizedViewBinder$bind$1 biometricCustomizedViewBinder$bind$1 = new BiometricCustomizedViewBinder$bind$1(this.$contentView, this.$legacyCallback, (Continuation) obj3);
        biometricCustomizedViewBinder$bind$1.L$0 = (View) obj2;
        return biometricCustomizedViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final View view = (View) this.L$0;
        final PromptContentView promptContentView = this.$contentView;
        if (promptContentView == null) {
            view.setVisibility(8);
            return Unit.INSTANCE;
        }
        final Spaghetti.Callback callback = this.$legacyCallback;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.biometrics.ui.binder.BiometricCustomizedViewBinder$bind$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LinearLayout linearLayout;
                LinearLayout linearLayout2;
                String text;
                boolean z;
                String str;
                int i;
                char c;
                ViewGroup viewGroup;
                int intValue = ((Number) obj2).intValue();
                if (intValue != 0) {
                    LinearLayout linearLayout3 = (LinearLayout) view;
                    PromptVerticalListContentView promptVerticalListContentView = promptContentView;
                    Context context = linearLayout3.getContext();
                    final Spaghetti.Callback callback2 = callback;
                    if (promptVerticalListContentView instanceof PromptVerticalListContentView) {
                        PromptVerticalListContentView promptVerticalListContentView2 = promptVerticalListContentView;
                        LayoutInflater from = LayoutInflater.from(context);
                        context.getResources();
                        Intrinsics.checkNotNull(from);
                        LinearLayout inflateContentView = BiometricCustomizedViewBinderKt.inflateContentView(from, R.layout.biometric_prompt_vertical_list_content_layout, promptVerticalListContentView2.getDescription());
                        ArrayList arrayList = new ArrayList(promptVerticalListContentView2.getListItems());
                        boolean isEmpty = arrayList.isEmpty();
                        int i2 = R.dimen.biometric_prompt_content_space_width_between_items;
                        String str2 = "No such PromptContentItem: ";
                        if (!isEmpty) {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                PromptContentItemPlainText promptContentItemPlainText = (PromptContentItem) it.next();
                                Resources resources = context.getResources();
                                boolean z2 = promptContentItemPlainText instanceof PromptContentItemPlainText;
                                if (z2) {
                                    text = promptContentItemPlainText.getText();
                                } else {
                                    if (!(promptContentItemPlainText instanceof PromptContentItemBulletedText)) {
                                        throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText);
                                    }
                                    text = ((PromptContentItemBulletedText) promptContentItemPlainText).getText();
                                }
                                if (!(z2 ? true : promptContentItemPlainText instanceof PromptContentItemBulletedText)) {
                                    throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText);
                                }
                                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.biometric_prompt_content_padding_horizontal);
                                int dimensionPixelSize2 = resources.getDimensionPixelSize(i2) / 2;
                                if (!z2) {
                                    if (!(promptContentItemPlainText instanceof PromptContentItemBulletedText)) {
                                        throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText);
                                    }
                                    dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_gap_width) + (resources.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_radius) * 2) + dimensionPixelSize2;
                                }
                                int i3 = (((intValue / 2) - dimensionPixelSize) - dimensionPixelSize2) - (dimensionPixelSize / 2);
                                TextPaint textPaint = new TextPaint();
                                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.TextAppearance_AuthCredential_ContentViewListItem, new int[]{android.R.attr.textSize});
                                int i4 = intValue;
                                textPaint.setTextSize(obtainStyledAttributes.getDimensionPixelSize(0, 0));
                                float measureText = textPaint.measureText(text);
                                obtainStyledAttributes.recycle();
                                if (!(!(((int) ((float) Math.ceil((double) (measureText / ((float) i3))))) > resources.getInteger(R.integer.biometric_prompt_content_list_item_max_lines_if_two_column)))) {
                                    z = false;
                                    break;
                                }
                                intValue = i4;
                                i2 = R.dimen.biometric_prompt_content_space_width_between_items;
                            }
                        }
                        z = true;
                        if (z && arrayList.size() > 1 && arrayList.size() % 2 == 1) {
                            arrayList.add(new PromptContentItemPlainText(""));
                        }
                        ViewGroup viewGroup2 = null;
                        LinearLayout linearLayout4 = (LinearLayout) from.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                        int size = arrayList.size();
                        int i5 = 0;
                        while (i5 < size) {
                            PromptContentItemPlainText promptContentItemPlainText2 = (PromptContentItem) arrayList.get(i5);
                            Resources resources2 = context.getResources();
                            TextView textView = (TextView) from.inflate(R.layout.biometric_prompt_content_row_item_text_view, viewGroup2);
                            int i6 = size;
                            LinearLayout linearLayout5 = linearLayout3;
                            textView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
                            if (promptContentItemPlainText2 instanceof PromptContentItemPlainText) {
                                textView.setText(promptContentItemPlainText2.getText());
                                str = str2;
                            } else {
                                if (!(promptContentItemPlainText2 instanceof PromptContentItemBulletedText)) {
                                    throw new IllegalStateException(str2 + promptContentItemPlainText2);
                                }
                                PromptContentItemBulletedText promptContentItemBulletedText = (PromptContentItemBulletedText) promptContentItemPlainText2;
                                SpannableString spannableString = new SpannableString(promptContentItemBulletedText.getText());
                                Intrinsics.checkNotNull(resources2);
                                str = str2;
                                spannableString.setSpan(new BulletSpan(resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_gap_width), Utils.getColorAttrDefaultColor(context, android.R.^attr-private.materialColorOnTertiary, 0), resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_radius)), 0, promptContentItemBulletedText.getText().length(), 33);
                                textView.setText(spannableString);
                            }
                            String description = promptVerticalListContentView2.getDescription();
                            if (description == null || description.length() == 0) {
                                i = 1;
                                if (inflateContentView.getChildCount() == 1) {
                                    textView.setPadding(textView.getPaddingLeft(), 0, textView.getPaddingRight(), textView.getPaddingBottom());
                                }
                            } else {
                                i = 1;
                            }
                            if (z && linearLayout4.getChildCount() == i) {
                                linearLayout4.addView(new Space(linearLayout4.getContext()), new LinearLayout.LayoutParams(linearLayout4.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_content_space_width_between_items), -1));
                            }
                            linearLayout4.addView(textView);
                            if (z && linearLayout4.getChildCount() != 3) {
                                if (i5 != arrayList.size() - 1) {
                                    c = ']';
                                    viewGroup = null;
                                    i5++;
                                    viewGroup2 = viewGroup;
                                    size = i6;
                                    linearLayout3 = linearLayout5;
                                    str2 = str;
                                }
                            }
                            inflateContentView.addView(linearLayout4);
                            c = ']';
                            viewGroup = null;
                            linearLayout4 = (LinearLayout) from.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                            i5++;
                            viewGroup2 = viewGroup;
                            size = i6;
                            linearLayout3 = linearLayout5;
                            str2 = str;
                        }
                        linearLayout = linearLayout3;
                        linearLayout2 = inflateContentView;
                    } else {
                        linearLayout = linearLayout3;
                        if (!(promptVerticalListContentView instanceof PromptContentViewWithMoreOptionsButton)) {
                            throw new IllegalStateException("No such PromptContentView: " + promptVerticalListContentView);
                        }
                        LayoutInflater from2 = LayoutInflater.from(context);
                        Intrinsics.checkNotNull(from2);
                        LinearLayout inflateContentView2 = BiometricCustomizedViewBinderKt.inflateContentView(from2, R.layout.biometric_prompt_content_with_button_layout, ((PromptContentViewWithMoreOptionsButton) promptVerticalListContentView).getDescription());
                        ((Button) inflateContentView2.requireViewById(R.id.customized_view_more_options_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricCustomizedViewBinderKt$initLayout$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                Spaghetti.Callback.this.onContentViewMoreOptionsButtonPressed();
                            }
                        });
                        linearLayout2 = inflateContentView2;
                    }
                    linearLayout.addView(linearLayout2, new LinearLayout.LayoutParams(-1, -2));
                    ((LinearLayout) view).setVisibility(0);
                }
                return Unit.INSTANCE;
            }
        };
        if (view.getWidth() == 0) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricCustomizedViewBinderKt$width$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    if (view.getMeasuredWidth() > 0) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    function1.invoke(Integer.valueOf(view.getMeasuredWidth()));
                }
            });
        } else {
            function1.invoke(Integer.valueOf(view.getMeasuredWidth()));
        }
        return Unit.INSTANCE;
    }
}
