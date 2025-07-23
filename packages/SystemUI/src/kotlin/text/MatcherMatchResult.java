package kotlin.text;

import java.util.regex.Matcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MatcherMatchResult implements MatchResult {
    public MatcherMatchResult$groupValues$1 groupValues_;
    public final CharSequence input;
    public final Matcher matcher;

    public MatcherMatchResult(Matcher matcher, CharSequence charSequence) {
        this.matcher = matcher;
        this.input = charSequence;
        new MatcherMatchResult$groups$1(this);
    }

    public final MatcherMatchResult next() {
        int end = this.matcher.end() + (this.matcher.end() == this.matcher.start() ? 1 : 0);
        if (end > this.input.length()) {
            return null;
        }
        Matcher matcher = this.matcher.pattern().matcher(this.input);
        CharSequence charSequence = this.input;
        if (matcher.find(end)) {
            return new MatcherMatchResult(matcher, charSequence);
        }
        return null;
    }
}
