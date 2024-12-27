package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class AnnotationLinkSpan$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SpannableStringBuilder f$1;
    public final /* synthetic */ SpannableString f$2;

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda0(SpannableStringBuilder spannableStringBuilder, SpannableString spannableString, Annotation annotation) {
        this.f$1 = spannableStringBuilder;
        this.f$2 = spannableString;
        this.f$0 = annotation;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AnnotationLinkSpan.LinkInfo[] linkInfoArr = (AnnotationLinkSpan.LinkInfo[]) this.f$0;
                SpannableStringBuilder spannableStringBuilder = this.f$1;
                SpannableString spannableString = this.f$2;
                Annotation annotation = (Annotation) obj;
                final String value = annotation.getValue();
                Arrays.asList(linkInfoArr).stream().filter(new Predicate() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        AnnotationLinkSpan.LinkInfo linkInfo = (AnnotationLinkSpan.LinkInfo) obj2;
                        return linkInfo.mAnnotation.isPresent() && ((String) linkInfo.mAnnotation.get()).equals(value);
                    }
                }).findFirst().flatMap(new AnnotationLinkSpan$$ExternalSyntheticLambda3()).ifPresent(new AnnotationLinkSpan$$ExternalSyntheticLambda0(spannableStringBuilder, spannableString, annotation));
                break;
            default:
                AnnotationLinkSpan.$r8$lambda$eF5QuUFfVOFWqtCLusK4BzSqpqA(this.f$1, this.f$2, (Annotation) this.f$0, (View.OnClickListener) obj);
                break;
        }
    }

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda0(AnnotationLinkSpan.LinkInfo[] linkInfoArr, SpannableStringBuilder spannableStringBuilder, SpannableString spannableString) {
        this.f$0 = linkInfoArr;
        this.f$1 = spannableStringBuilder;
        this.f$2 = spannableString;
    }
}
