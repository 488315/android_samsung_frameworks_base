package android.view.textclassifier;

import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.EventLog;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes4.dex */
public final class TextLinksParams {
    private static final Function<TextLinks.TextLink, TextLinks.TextLinkSpan> DEFAULT_SPAN_FACTORY = new Function() { // from class: android.view.textclassifier.TextLinksParams$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return TextLinksParams.lambda$static$0((TextLinks.TextLink) obj);
        }
    };
    private final int mApplyStrategy;
    private final TextClassifier.EntityConfig mEntityConfig;
    private final Function<TextLinks.TextLink, TextLinks.TextLinkSpan> mSpanFactory;

    static /* synthetic */ TextLinks.TextLinkSpan lambda$static$0(TextLinks.TextLink textLink) {
        return new TextLinks.TextLinkSpan(textLink);
    }

    private TextLinksParams(int i, Function<TextLinks.TextLink, TextLinks.TextLinkSpan> function) {
        this.mApplyStrategy = i;
        this.mSpanFactory = function;
        this.mEntityConfig = TextClassifier.EntityConfig.createWithHints(null);
    }

    public static TextLinksParams fromLinkMask(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("url");
        }
        if ((i & 2) != 0) {
            arrayList.add("email");
        }
        if ((i & 4) != 0) {
            arrayList.add("phone");
        }
        if ((i & 8) != 0) {
            arrayList.add("address");
        }
        return new Builder().setEntityConfig(TextClassifier.EntityConfig.createWithExplicitEntityList(arrayList)).build();
    }

    public TextClassifier.EntityConfig getEntityConfig() {
        return this.mEntityConfig;
    }

    public int apply(Spannable spannable, TextLinks textLinks) {
        Objects.requireNonNull(spannable);
        Objects.requireNonNull(textLinks);
        String string = spannable.toString();
        if (Linkify.containsUnsupportedCharacters(string)) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return 4;
        }
        if (!string.startsWith(textLinks.getText().toString())) {
            return 3;
        }
        if (textLinks.getLinks().isEmpty()) {
            return 1;
        }
        int i = 0;
        for (TextLinks.TextLink textLink : textLinks.getLinks()) {
            TextLinks.TextLinkSpan textLinkSpanApply = this.mSpanFactory.apply(textLink);
            if (textLinkSpanApply != null) {
                ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(textLink.getStart(), textLink.getEnd(), ClickableSpan.class);
                if (clickableSpanArr.length > 0) {
                    if (this.mApplyStrategy == 1) {
                        for (ClickableSpan clickableSpan : clickableSpanArr) {
                            spannable.removeSpan(clickableSpan);
                        }
                        spannable.setSpan(textLinkSpanApply, textLink.getStart(), textLink.getEnd(), 33);
                    }
                } else {
                    spannable.setSpan(textLinkSpanApply, textLink.getStart(), textLink.getEnd(), 33);
                }
                i++;
            }
        }
        return i == 0 ? 2 : 0;
    }

    public static final class Builder {
        private int mApplyStrategy = 0;
        private Function<TextLinks.TextLink, TextLinks.TextLinkSpan> mSpanFactory = TextLinksParams.DEFAULT_SPAN_FACTORY;

        public Builder setEntityConfig(TextClassifier.EntityConfig entityConfig) {
            return this;
        }

        public Builder setApplyStrategy(int i) {
            this.mApplyStrategy = TextLinksParams.checkApplyStrategy(i);
            return this;
        }

        public Builder setSpanFactory(Function<TextLinks.TextLink, TextLinks.TextLinkSpan> function) {
            if (function == null) {
                function = TextLinksParams.DEFAULT_SPAN_FACTORY;
            }
            this.mSpanFactory = function;
            return this;
        }

        public TextLinksParams build() {
            return new TextLinksParams(this.mApplyStrategy, this.mSpanFactory);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkApplyStrategy(int i) {
        if (i == 0 || i == 1) {
            return i;
        }
        throw new IllegalArgumentException("Invalid apply strategy. See TextLinksParams.ApplyStrategy for options.");
    }
}
