package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Warning extends SIPHeader implements Header {
    private static final long serialVersionUID = -3433328864230783899L;
    protected String agent;
    protected int code;
    protected String text;

    public Warning() {
        super("Warning");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        if (this.text == null) {
            return Integer.toString(this.code) + " " + this.agent;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.code));
        sb.append(" ");
        sb.append(this.agent);
        sb.append(" \"");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.text, "\"");
    }

    public final void setAgent(String str) {
        if (str == null) {
            throw new NullPointerException("the host parameter in the Warning header is null");
        }
        this.agent = str;
    }

    public final void setCode(int i) {
        if (i <= 99 || i >= 1000) {
            throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Code parameter in the Warning header is invalid: code="));
        }
        this.code = i;
    }

    public final void setText(String str) {
        if (str == null) {
            throw new ParseException("The text parameter in the Warning header is null", 0);
        }
        this.text = str;
    }
}
