package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AnnotationLinkSpan extends ClickableSpan {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Optional mClickListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LinkInfo {
        public final Optional mAnnotation;
        public final Optional mListener;

        public LinkInfo(String str, View.OnClickListener onClickListener) {
            this.mAnnotation = Optional.of(str);
            this.mListener = Optional.ofNullable(onClickListener);
        }
    }

    public static /* synthetic */ void $r8$lambda$eF5QuUFfVOFWqtCLusK4BzSqpqA(SpannableStringBuilder spannableStringBuilder, SpannableString spannableString, Annotation annotation, View.OnClickListener onClickListener) {
        AnnotationLinkSpan annotationLinkSpan = new AnnotationLinkSpan(onClickListener);
        spannableStringBuilder.setSpan(annotationLinkSpan, spannableString.getSpanStart(annotation), spannableString.getSpanEnd(annotation), spannableString.getSpanFlags(annotationLinkSpan));
    }

    private AnnotationLinkSpan(View.OnClickListener onClickListener) {
        this.mClickListener = Optional.ofNullable(onClickListener);
    }

    public static CharSequence linkify(CharSequence charSequence, LinkInfo... linkInfoArr) {
        SpannableString spannableString = new SpannableString(charSequence);
        Annotation[] annotationArr = (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
        Arrays.asList(annotationArr).forEach(new AnnotationLinkSpan$$ExternalSyntheticLambda0(linkInfoArr, spannableStringBuilder, spannableString));
        return spannableStringBuilder;
    }

    @Override // android.text.style.ClickableSpan
    public final void onClick(final View view) {
        this.mClickListener.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                View view2 = view;
                int i = AnnotationLinkSpan.$r8$clinit;
                ((View.OnClickListener) obj).onClick(view2);
            }
        });
    }
}
