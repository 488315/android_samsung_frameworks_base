package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Warning extends SIPHeader {
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
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.text, "\"");
    }

    public final void setAgent(String str) {
        if (str == null) {
            throw new NullPointerException("the host parameter in the Warning header is null");
        }
        this.agent = str;
    }

    public final void setCode(int i) {
        if (i <= 99 || i >= 1000) {
            throw new InvalidArgumentException(AbstractC0000x2c234b15.m0m("Code parameter in the Warning header is invalid: code=", i));
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
