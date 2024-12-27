package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import java.util.Optional;
import java.util.function.Consumer;

public final class AnnotationLinkSpan extends ClickableSpan {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Optional mClickListener;

    public final class LinkInfo {
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

    @Override // android.text.style.ClickableSpan
    public final void onClick(final View view) {
        this.mClickListener.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((View.OnClickListener) obj).onClick(view);
            }
        });
    }
}
