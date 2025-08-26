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
            /* JADX WARN: Removed duplicated region for block: B:46:0x0157  */
            /* JADX WARN: Type inference failed for: r1v1, types: [android.widget.LinearLayout] */
            /* JADX WARN: Type inference failed for: r5v1, types: [android.widget.LinearLayout] */
            /* JADX WARN: Type inference failed for: r5v2, types: [android.view.View] */
            /* JADX WARN: Type inference failed for: r5v4, types: [android.widget.LinearLayout] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj2) {
                ?? InflateContentView;
                String text;
                PromptVerticalListContentView promptVerticalListContentView;
                boolean z;
                int size;
                int i;
                Context context;
                int i2;
                ViewGroup viewGroup;
                LinearLayout linearLayout;
                View view2 = view;
                PromptVerticalListContentView promptVerticalListContentView2 = promptContentView;
                int iIntValue = ((Integer) obj2).intValue();
                if (iIntValue == 0) {
                    return Unit.INSTANCE;
                }
                ?? r1 = (LinearLayout) view2;
                Context context2 = r1.getContext();
                if (promptVerticalListContentView2 instanceof PromptVerticalListContentView) {
                    PromptVerticalListContentView promptVerticalListContentView3 = promptVerticalListContentView2;
                    LayoutInflater layoutInflaterFrom = LayoutInflater.from(context2);
                    context2.getResources();
                    layoutInflaterFrom.getClass();
                    InflateContentView = BiometricCustomizedViewBinderKt.inflateContentView(layoutInflaterFrom, R.layout.biometric_prompt_vertical_list_content_layout, promptVerticalListContentView3.getDescription());
                    ArrayList arrayList = new ArrayList(promptVerticalListContentView3.getListItems());
                    boolean zIsEmpty = arrayList.isEmpty();
                    int i3 = R.dimen.biometric_prompt_content_space_width_between_items;
                    if (zIsEmpty) {
                        promptVerticalListContentView = promptVerticalListContentView3;
                        z = true;
                        if (z && arrayList.size() > 1 && arrayList.size() % 2 == 1) {
                            arrayList.add(new PromptContentItemPlainText(""));
                        }
                        ViewGroup viewGroup2 = null;
                        LinearLayout linearLayout2 = (LinearLayout) layoutInflaterFrom.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                        size = arrayList.size();
                        i = 0;
                        while (i < size) {
                            PromptContentItemPlainText promptContentItemPlainText = (PromptContentItem) arrayList.get(i);
                            Resources resources = context2.getResources();
                            TextView textView = (TextView) layoutInflaterFrom.inflate(R.layout.biometric_prompt_content_row_item_text_view, viewGroup2);
                            int i4 = size;
                            textView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
                            int maxEachItemCharacterNumber = PromptVerticalListContentView.getMaxEachItemCharacterNumber();
                            if (promptContentItemPlainText instanceof PromptContentItemPlainText) {
                                textView.setText(Utils.ellipsize(maxEachItemCharacterNumber, promptContentItemPlainText.getText()));
                                context = context2;
                            } else {
                                if (!(promptContentItemPlainText instanceof PromptContentItemBulletedText)) {
                                    throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText);
                                }
                                PromptContentItemBulletedText promptContentItemBulletedText = (PromptContentItemBulletedText) promptContentItemPlainText;
                                SpannableString spannableString = new SpannableString(Utils.ellipsize(maxEachItemCharacterNumber, promptContentItemBulletedText.getText()));
                                resources.getClass();
                                context = context2;
                                spannableString.setSpan(new BulletSpan(resources.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_gap_width), context2.getColor(android.R.color.search_url_text_material_light), resources.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_radius)), 0, promptContentItemBulletedText.getText().length(), 33);
                                textView.setText(spannableString);
                            }
                            String description = promptVerticalListContentView.getDescription();
                            if (description == null || description.length() == 0) {
                                i2 = 1;
                                if (InflateContentView.getChildCount() == 1) {
                                    textView.setPadding(textView.getPaddingLeft(), 0, textView.getPaddingRight(), textView.getPaddingBottom());
                                }
                            } else {
                                i2 = 1;
                            }
                            if (z && linearLayout2.getChildCount() == i2) {
                                linearLayout2.addView(new Space(linearLayout2.getContext()), new LinearLayout.LayoutParams(linearLayout2.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_content_space_width_between_items), -1));
                            }
                            linearLayout2.addView(textView);
                            if (z && linearLayout2.getChildCount() != 3) {
                                if (i != arrayList.size() - 1) {
                                    linearLayout = linearLayout2;
                                    viewGroup = null;
                                }
                                i++;
                                viewGroup2 = viewGroup;
                                linearLayout2 = linearLayout;
                                size = i4;
                                context2 = context;
                            }
                            InflateContentView.addView(linearLayout2);
                            viewGroup = null;
                            linearLayout = (LinearLayout) layoutInflaterFrom.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                            i++;
                            viewGroup2 = viewGroup;
                            linearLayout2 = linearLayout;
                            size = i4;
                            context2 = context;
                        }
                    } else {
                        int size2 = arrayList.size();
                        int i5 = 0;
                        while (i5 < size2) {
                            Object obj3 = arrayList.get(i5);
                            i5++;
                            PromptContentItemPlainText promptContentItemPlainText2 = (PromptContentItem) obj3;
                            Resources resources2 = context2.getResources();
                            boolean z2 = promptContentItemPlainText2 instanceof PromptContentItemPlainText;
                            if (z2) {
                                text = promptContentItemPlainText2.getText();
                            } else {
                                if (!(promptContentItemPlainText2 instanceof PromptContentItemBulletedText)) {
                                    throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText2);
                                }
                                text = ((PromptContentItemBulletedText) promptContentItemPlainText2).getText();
                            }
                            if (!z2 && !(promptContentItemPlainText2 instanceof PromptContentItemBulletedText)) {
                                throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText2);
                            }
                            int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_padding_horizontal);
                            int dimensionPixelSize2 = resources2.getDimensionPixelSize(i3) / 2;
                            if (!z2) {
                                if (!(promptContentItemPlainText2 instanceof PromptContentItemBulletedText)) {
                                    throw new IllegalStateException("No such PromptContentItem: " + promptContentItemPlainText2);
                                }
                                dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_gap_width) + (resources2.getDimensionPixelSize(R.dimen.biometric_prompt_content_list_item_bullet_radius) * 2) + dimensionPixelSize2;
                            }
                            int i6 = (((iIntValue / 2) - dimensionPixelSize) - dimensionPixelSize2) - (dimensionPixelSize / 2);
                            TextPaint textPaint = new TextPaint();
                            TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(R.style.TextAppearance_AuthCredential_ContentViewListItem, new int[]{android.R.attr.textSize});
                            promptVerticalListContentView = promptVerticalListContentView3;
                            textPaint.setTextSize(typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0));
                            float fMeasureText = textPaint.measureText(text);
                            typedArrayObtainStyledAttributes.recycle();
                            if (((int) Math.ceil(fMeasureText / i6)) > resources2.getInteger(R.integer.biometric_prompt_content_list_item_max_lines_if_two_column)) {
                                z = false;
                                break;
                            }
                            promptVerticalListContentView3 = promptVerticalListContentView;
                            i3 = R.dimen.biometric_prompt_content_space_width_between_items;
                        }
                        promptVerticalListContentView = promptVerticalListContentView3;
                        z = true;
                        if (z) {
                            arrayList.add(new PromptContentItemPlainText(""));
                        }
                        ViewGroup viewGroup22 = null;
                        LinearLayout linearLayout22 = (LinearLayout) layoutInflaterFrom.inflate(R.layout.biometric_prompt_content_row_layout, (ViewGroup) null);
                        size = arrayList.size();
                        i = 0;
                        while (i < size) {
                        }
                    }
                } else {
                    if (!(promptVerticalListContentView2 instanceof PromptContentViewWithMoreOptionsButton)) {
                        throw new IllegalStateException("No such PromptContentView: " + promptVerticalListContentView2);
                    }
                    LayoutInflater layoutInflaterFrom2 = LayoutInflater.from(context2);
                    layoutInflaterFrom2.getClass();
                    InflateContentView = BiometricCustomizedViewBinderKt.inflateContentView(layoutInflaterFrom2, R.layout.biometric_prompt_content_with_button_layout, ((PromptContentViewWithMoreOptionsButton) promptVerticalListContentView2).getDescription());
                    Button button = (Button) InflateContentView.requireViewById(R.id.customized_view_more_options_button);
                    final Spaghetti.Callback callback2 = callback;
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricCustomizedViewBinderKt$initLayout$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            callback2.onContentViewMoreOptionsButtonPressed();
                        }
                    });
                }
                r1.addView(InflateContentView, new LinearLayout.LayoutParams(-1, -2));
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
                    function1.mo781invoke(Integer.valueOf(view.getMeasuredWidth()));
                }
            });
        } else {
            function1.mo781invoke(Integer.valueOf(view.getMeasuredWidth()));
        }
        return Unit.INSTANCE;
    }
}
