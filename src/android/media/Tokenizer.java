package android.media;

import android.util.Log;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class Tokenizer {
    private static final String TAG = "Tokenizer";
    private int mHandledLen;
    private String mLine;
    private OnTokenListener mListener;
    private TokenizerPhase mPhase;
    private TokenizerPhase mDataTokenizer = new DataTokenizer();
    private TokenizerPhase mTagTokenizer = new TagTokenizer();

    /* compiled from: WebVttRenderer.java */
    interface OnTokenListener {
        void onData(String str);

        void onEnd(String str);

        void onLineEnd();

        void onStart(String str, String[] strArr, String str2);

        void onTimeStamp(long j);
    }

    /* compiled from: WebVttRenderer.java */
    interface TokenizerPhase {
        TokenizerPhase start();

        void tokenize();
    }

    /* compiled from: WebVttRenderer.java */
    class DataTokenizer implements TokenizerPhase {
        private StringBuilder mData;

        DataTokenizer() {
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public TokenizerPhase start() {
            this.mData = new StringBuilder();
            return this;
        }

        private boolean replaceEscape(String str, String str2, int i) {
            if (!Tokenizer.this.mLine.startsWith(str, i)) {
                return false;
            }
            this.mData.append(Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen, i));
            this.mData.append(str2);
            Tokenizer.this.mHandledLen = i + str.length();
            int unused = Tokenizer.this.mHandledLen;
            return true;
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public void tokenize() {
            int length = Tokenizer.this.mLine.length();
            int i = Tokenizer.this.mHandledLen;
            while (true) {
                if (i >= Tokenizer.this.mLine.length()) {
                    break;
                }
                if (Tokenizer.this.mLine.charAt(i) == '&') {
                    if (!replaceEscape("&amp;", "&", i) && !replaceEscape("&lt;", "<", i) && !replaceEscape("&gt;", ">", i) && !replaceEscape("&lrm;", "\u200e", i) && !replaceEscape("&rlm;", "\u200f", i)) {
                        replaceEscape("&nbsp;", " ", i);
                    }
                } else if (Tokenizer.this.mLine.charAt(i) == '<') {
                    Tokenizer tokenizer = Tokenizer.this;
                    tokenizer.mPhase = tokenizer.mTagTokenizer.start();
                    length = i;
                    break;
                }
                i++;
            }
            this.mData.append(Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen, length));
            Tokenizer.this.mListener.onData(this.mData.toString());
            StringBuilder sb = this.mData;
            sb.delete(0, sb.length());
            Tokenizer.this.mHandledLen = length;
        }
    }

    /* compiled from: WebVttRenderer.java */
    class TagTokenizer implements TokenizerPhase {
        private String mAnnotation;
        private boolean mAtAnnotation;
        private String mName;

        TagTokenizer() {
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public TokenizerPhase start() {
            this.mAnnotation = "";
            this.mName = "";
            this.mAtAnnotation = false;
            return this;
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public void tokenize() {
            String[] strArrSplit;
            if (!this.mAtAnnotation) {
                Tokenizer.this.mHandledLen++;
            }
            if (Tokenizer.this.mHandledLen < Tokenizer.this.mLine.length()) {
                if (this.mAtAnnotation || Tokenizer.this.mLine.charAt(Tokenizer.this.mHandledLen) == '/') {
                    strArrSplit = Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen).split(">");
                } else {
                    strArrSplit = Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen).split("[\t\f >]");
                }
                String strSubstring = Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen, Tokenizer.this.mHandledLen + strArrSplit[0].length());
                Tokenizer.this.mHandledLen += strArrSplit[0].length();
                if (this.mAtAnnotation) {
                    this.mAnnotation += " " + strSubstring;
                } else {
                    this.mName = strSubstring;
                }
            }
            this.mAtAnnotation = true;
            if (Tokenizer.this.mHandledLen >= Tokenizer.this.mLine.length() || Tokenizer.this.mLine.charAt(Tokenizer.this.mHandledLen) != '>') {
                return;
            }
            yield_tag();
            Tokenizer tokenizer = Tokenizer.this;
            tokenizer.mPhase = tokenizer.mDataTokenizer.start();
            Tokenizer.this.mHandledLen++;
        }

        private void yield_tag() {
            String[] strArrSplit;
            if (this.mName.startsWith("/")) {
                Tokenizer.this.mListener.onEnd(this.mName.substring(1));
                return;
            }
            if (this.mName.length() > 0 && Character.isDigit(this.mName.charAt(0))) {
                try {
                    Tokenizer.this.mListener.onTimeStamp(WebVttParser.parseTimestampMs(this.mName));
                    return;
                } catch (NumberFormatException unused) {
                    Log.d(Tokenizer.TAG, "invalid timestamp tag: <" + this.mName + ">");
                    return;
                }
            }
            String strReplaceAll = this.mAnnotation.replaceAll("\\s+", " ");
            this.mAnnotation = strReplaceAll;
            if (strReplaceAll.startsWith(" ")) {
                this.mAnnotation = this.mAnnotation.substring(1);
            }
            if (this.mAnnotation.endsWith(" ")) {
                String str = this.mAnnotation;
                this.mAnnotation = str.substring(0, str.length() - 1);
            }
            int iIndexOf = this.mName.indexOf(46);
            if (iIndexOf >= 0) {
                strArrSplit = this.mName.substring(iIndexOf + 1).split("\\.");
                this.mName = this.mName.substring(0, iIndexOf);
            } else {
                strArrSplit = null;
            }
            Tokenizer.this.mListener.onStart(this.mName, strArrSplit, this.mAnnotation);
        }
    }

    Tokenizer(OnTokenListener onTokenListener) {
        reset();
        this.mListener = onTokenListener;
    }

    void reset() {
        this.mPhase = this.mDataTokenizer.start();
    }

    void tokenize(String str) {
        this.mHandledLen = 0;
        this.mLine = str;
        while (this.mHandledLen < this.mLine.length()) {
            this.mPhase.tokenize();
        }
        if (this.mPhase instanceof TagTokenizer) {
            return;
        }
        this.mListener.onLineEnd();
    }
}
