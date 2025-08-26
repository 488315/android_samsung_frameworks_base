package kotlin.text;

import java.util.regex.Matcher;

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
        int iEnd = this.matcher.end() + (this.matcher.end() == this.matcher.start() ? 1 : 0);
        if (iEnd > this.input.length()) {
            return null;
        }
        Matcher matcher = this.matcher.pattern().matcher(this.input);
        CharSequence charSequence = this.input;
        if (matcher.find(iEnd)) {
            return new MatcherMatchResult(matcher, charSequence);
        }
        return null;
    }
}
