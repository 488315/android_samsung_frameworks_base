package android.filterfw.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class PatternScanner {
    private Pattern mIgnorePattern;
    private String mInput;
    private int mOffset = 0;
    private int mLineNo = 0;
    private int mStartOfLine = 0;

    public PatternScanner(String str) {
        this.mInput = str;
    }

    public PatternScanner(String str, Pattern pattern) {
        this.mInput = str;
        this.mIgnorePattern = pattern;
        skip(pattern);
    }

    public String tryEat(Pattern pattern) {
        String strSubstring;
        Pattern pattern2;
        Pattern pattern3 = this.mIgnorePattern;
        if (pattern3 != null) {
            skip(pattern3);
        }
        Matcher matcher = pattern.matcher(this.mInput);
        matcher.region(this.mOffset, this.mInput.length());
        if (matcher.lookingAt()) {
            updateLineCount(this.mOffset, matcher.end());
            this.mOffset = matcher.end();
            strSubstring = this.mInput.substring(matcher.start(), matcher.end());
        } else {
            strSubstring = null;
        }
        if (strSubstring != null && (pattern2 = this.mIgnorePattern) != null) {
            skip(pattern2);
        }
        return strSubstring;
    }

    public String eat(Pattern pattern, String str) {
        String strTryEat = tryEat(pattern);
        if (strTryEat != null) {
            return strTryEat;
        }
        throw new RuntimeException(unexpectedTokenMessage(str));
    }

    public boolean peek(Pattern pattern) {
        Pattern pattern2 = this.mIgnorePattern;
        if (pattern2 != null) {
            skip(pattern2);
        }
        Matcher matcher = pattern.matcher(this.mInput);
        matcher.region(this.mOffset, this.mInput.length());
        return matcher.lookingAt();
    }

    public void skip(Pattern pattern) {
        Matcher matcher = pattern.matcher(this.mInput);
        matcher.region(this.mOffset, this.mInput.length());
        if (matcher.lookingAt()) {
            updateLineCount(this.mOffset, matcher.end());
            this.mOffset = matcher.end();
        }
    }

    public boolean atEnd() {
        return this.mOffset >= this.mInput.length();
    }

    public int lineNo() {
        return this.mLineNo;
    }

    public String unexpectedTokenMessage(String str) {
        return "Unexpected token on line " + (this.mLineNo + 1) + " after '" + this.mInput.substring(this.mStartOfLine, this.mOffset) + "' <- Expected " + str + "!";
    }

    public void updateLineCount(int i, int i2) {
        while (i < i2) {
            if (this.mInput.charAt(i) == '\n') {
                this.mLineNo++;
                this.mStartOfLine = i + 1;
            }
            i++;
        }
    }
}
