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
import com.android.systemui.R;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
        final Function1 function1 = new Function1() { // from class: com.android.systemui.biometrics.ui.binder.BiometricCustomizedViewBinder$bind$1$$ExternalSyntheticLambda0
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v1, types: [android.widget.LinearLayout] */
            /* JADX WARN: Type inference failed for: r5v1, types: [android.widget.LinearLayout] */
            /* JADX WARN: Type inference failed for: r5v2, types: [android.view.View] */
            /* JADX WARN: Type inference failed for: r5v4, types: [android.widget.LinearLayout] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                ?? inflateContentView;
                String text;
                PromptVerticalListContentView promptVerticalListContentView;
                boolean z;
                Context context;
                int i;
                ViewGroup viewGroup;
                LinearLayout linearLayout;
                View view2 = view;
                PromptVerticalListContentView promptVerticalListContentView2 = promptContentView;
                int intValue = ((Integer) obj2).intValue();
                if (intValue == 0) {
                    return Unit.INSTANCE;
                }
                ?? r1 = (LinearLayout) view2;
                Context context2 = r1.getContext();
                if (promptVerticalListContentView2 instanceof PromptVerticalListContentView) {
                    PromptVerticalListContentView promptVerticalListContentView3 = promptVerticalListContentView2;
                    LayoutInflater from = LayoutInflater.from(context2);
                    context2.getResources();
                    from.getClass();
                    inflateContentView = BiometricCustomizedViewBinderKt.inflateContentView(from, R.layout.biometric_prompt_vertical_list_content_layout, promptVerticalListContentView3.getDescription());
                    ArrayList arrayList = new ArrayList(promptVerticalListContentView3.getListItems());
                    boolean isEmpty = arrayList.isEmpty();
                    int i2 = R.dimen.biometric_prompt_content_space_width_between_items;
                    if (!isEmpty) {
                        int size = arrayList.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj3 = arrayList.get(i3);
                            i3++;
                            PromptContentItemPlainText promptContentItemPlainText = (PromptContentItem) obj3;
                            Resources resources = context2.getResources();
                            boolean z2 = promptContentItemPlainText instanceof PromptContentItemPlainText;
                            if (z2) {
                                text = promptContentItemPlainText.getText();
                            } else {
                                if (!(promptContentItemPlainText instanceof PromptContentItemBulletedText)) {
                                    throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText);
                                }
                                text = ((PromptContentItemBulletedText) promptContentItemPlainText).getText();
                            }
                            if (!z2 && !(promptContentItemPlainText instanceof PromptContentItemBulletedText)) {
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
                            int i4 = (((intValue / 2) - dimensionPixelSize) - dimensionPixelSize2) - (dimensionPixelSize / 2);
                            TextPaint textPaint = new TextPaint();
                            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(R.style.TextAppearance_AuthCredential_ContentViewListItem, new int[]{android.R.attr.textSize});
                            promptVerticalListContentView = promptVerticalListContentView3;
                            textPaint.setTextSize(obtainStyledAttributes.getDimensionPixelSize(0, 0));
                            float measureText = textPaint.measureText(text);
                            obtainStyledAttributes.recycle();
                            if (((int) Math.ceil(measureText / i4)) > resources.getInteger(R.integer.biometric_prompt_content_list_item_max_lines_if_two_column)) {
                                z = false;
                                break;
                            }
                            promptVerticalListContentView3 = promptVerticalListContentView;
                            i2 = R.dimen.biometric_prompt_content_space_width_between_items;
                        }
                    }
                    promptVerticalListContentView = promptVerticalListContentView3;
                    z = true;
                    if (z && arrayList.size() > 1 && arrayList.size() % 2 == 1) {
                        arrayList.add(new PromptContentItemPlainText(""));
                    }
                    ViewGroup viewGroup2 = null;
                    LinearLayout linearLayout2 = (LinearLayout) from.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                    int size2 = arrayList.size();
                    int i5 = 0;
                    while (i5 < size2) {
                        PromptContentItemPlainText promptContentItemPlainText2 = (PromptContentItem) arrayList.get(i5);
                        Resources resources2 = context2.getResources();
                        TextView textView = (TextView) from.inflate(R.layout.biometric_prompt_content_row_item_text_view, viewGroup2);
                        int i6 = size2;
                        textView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
                        int maxEachItemCharacterNumber = PromptVerticalListContentView.getMaxEachItemCharacterNumber();
                        if (promptContentItemPlainText2 instanceof PromptContentItemPlainText) {
                            textView.setText(Utils.ellipsize(maxEachItemCharacterNumber, promptContentItemPlainText2.getText()));
                            context = context2;
                        } else {
                            if (!(promptContentItemPlainText2 instanceof PromptContentItemBulletedText)) {
                                throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText2);
                            }
                            PromptContentItemBulletedText promptContentItemBulletedText = (PromptContentItemBulletedText) promptContentItemPlainText2;
                            SpannableString spannableString = new SpannableString(Utils.ellipsize(maxEachItemCharacterNumber, promptContentItemBulletedText.getText()));
                            resources2.getClass();
                            context = context2;
                            spannableString.setSpan(new BulletSpan(resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_gap_width), context2.getColor(android.R.color.search_url_text_material_light), resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_radius)), 0, promptContentItemBulletedText.getText().length(), 33);
                            textView.setText(spannableString);
                        }
                        String description = promptVerticalListContentView.getDescription();
                        if (description == null || description.length() == 0) {
                            i = 1;
                            if (inflateContentView.getChildCount() == 1) {
                                textView.setPadding(textView.getPaddingLeft(), 0, textView.getPaddingRight(), textView.getPaddingBottom());
                            }
                        } else {
                            i = 1;
                        }
                        if (z && linearLayout2.getChildCount() == i) {
                            linearLayout2.addView(new Space(linearLayout2.getContext()), new LinearLayout.LayoutParams(linearLayout2.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_content_space_width_between_items), -1));
                        }
                        linearLayout2.addView(textView);
                        if (z && linearLayout2.getChildCount() != 3) {
                            if (i5 != arrayList.size() - 1) {
                                linearLayout = linearLayout2;
                                viewGroup = null;
                                i5++;
                                viewGroup2 = viewGroup;
                                linearLayout2 = linearLayout;
                                size2 = i6;
                                context2 = context;
                            }
                        }
                        inflateContentView.addView(linearLayout2);
                        viewGroup = null;
                        linearLayout = (LinearLayout) from.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                        i5++;
                        viewGroup2 = viewGroup;
                        linearLayout2 = linearLayout;
                        size2 = i6;
                        context2 = context;
                    }
                } else {
                    if (!(promptVerticalListContentView2 instanceof PromptContentViewWithMoreOptionsButton)) {
                        throw new IllegalStateException("No such PromptContentView: " + promptVerticalListContentView2);
                    }
                    LayoutInflater from2 = LayoutInflater.from(context2);
                    from2.getClass();
                    inflateContentView = BiometricCustomizedViewBinderKt.inflateContentView(from2, R.layout.biometric_prompt_content_with_button_layout, ((PromptContentViewWithMoreOptionsButton) promptVerticalListContentView2).getDescription());
                    Button button = (Button) inflateContentView.requireViewById(R.id.customized_view_more_options_button);
                    final Spaghetti.Callback callback2 = callback;
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricCustomizedViewBinderKt$initLayout$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            Spaghetti.Callback.this.onContentViewMoreOptionsButtonPressed();
                        }
                    });
                }
                r1.addView(inflateContentView, new LinearLayout.LayoutParams(-1, -2));
                r1.setVisibility(0);
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
                    function1.mo779invoke(Integer.valueOf(view.getMeasuredWidth()));
                }
            });
        } else {
            function1.mo779invoke(Integer.valueOf(view.getMeasuredWidth()));
        }
        return Unit.INSTANCE;
    }
}
