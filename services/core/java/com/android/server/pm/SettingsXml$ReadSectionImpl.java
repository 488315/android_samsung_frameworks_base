package com.android.server.pm;

import android.util.Slog;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.util.Stack;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SettingsXml$ReadSectionImpl implements AutoCloseable {
    public final /* synthetic */ int $r8$classId = 0;
    public final Stack mDepthStack = new Stack();
    public final Object mParser;

    public SettingsXml$ReadSectionImpl(TypedXmlPullParser typedXmlPullParser) {
        int next;
        this.mParser = typedXmlPullParser;
        if (typedXmlPullParser.getEventType() == 2) {
            return;
        }
        do {
            next = ((TypedXmlPullParser) this.mParser).next();
            if (next == 2) {
                return;
            }
        } while (next != 1);
    }

    public SettingsXml$ReadSectionImpl(TypedXmlSerializer typedXmlSerializer) {
        this.mParser = typedXmlSerializer;
    }

    public void attribute(int i, String str) {
        if (i != -1) {
            ((TypedXmlSerializer) this.mParser).attributeInt((String) null, str, i);
        }
    }

    public void attribute(String str, String str2) {
        if (str2 != null) {
            ((TypedXmlSerializer) this.mParser).attribute((String) null, str, str2);
        }
    }

    public SettingsXml$ReadSectionImpl children() {
        this.mDepthStack.push(Integer.valueOf(((TypedXmlPullParser) this.mParser).getDepth()));
        return this;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        switch (this.$r8$classId) {
            case 0:
                if (this.mDepthStack.isEmpty()) {
                    Slog.wtf("SettingsXml", "Children depth stack was not empty, data may have been lost", new Exception());
                    break;
                }
                break;
            default:
                ((TypedXmlSerializer) this.mParser).endTag((String) null, (String) this.mDepthStack.pop());
                break;
        }
    }

    public int getInt(int i, String str) {
        return ((TypedXmlPullParser) this.mParser).getAttributeInt((String) null, str, i);
    }

    public String getString(String str) {
        return ((TypedXmlPullParser) this.mParser).getAttributeValue((String) null, str);
    }

    public boolean moveToNextInternal(String str) {
        try {
            int intValue = ((Integer) this.mDepthStack.peek()).intValue();
            boolean z = false;
            while (!z) {
                int next = ((TypedXmlPullParser) this.mParser).next();
                if (next == 1 || (next == 3 && ((TypedXmlPullParser) this.mParser).getDepth() <= intValue)) {
                    break;
                }
                if (next == 2 && (str == null || str.equals(((TypedXmlPullParser) this.mParser).getName()))) {
                    z = true;
                }
            }
            if (!z) {
                this.mDepthStack.pop();
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public void startSection(String str) {
        ((TypedXmlSerializer) this.mParser).startTag((String) null, str);
        this.mDepthStack.push(str);
    }
}
