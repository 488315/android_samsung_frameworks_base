package androidx.compose.ui.graphics.vector.compat;

import android.content.res.TypedArray;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.vector.PathParser;
import androidx.core.content.res.TypedArrayUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidVectorParser {
    public int config;
    public final PathParser pathParser;
    public final XmlPullParser xmlParser;

    public AndroidVectorParser(XmlPullParser xmlPullParser, int i) {
        this.xmlParser = xmlPullParser;
        this.config = i;
        this.pathParser = new PathParser();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AndroidVectorParser)) {
            return false;
        }
        AndroidVectorParser androidVectorParser = (AndroidVectorParser) obj;
        return Intrinsics.areEqual(this.xmlParser, androidVectorParser.xmlParser) && this.config == androidVectorParser.config;
    }

    public final float getNamedFloat(TypedArray typedArray, String str, int i, float f) {
        if (TypedArrayUtils.hasAttribute(this.xmlParser, str)) {
            f = typedArray.getFloat(i, f);
        }
        updateConfig(typedArray.getChangingConfigurations());
        return f;
    }

    public final int hashCode() {
        return Integer.hashCode(this.config) + (this.xmlParser.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AndroidVectorParser(xmlParser=");
        sb.append(this.xmlParser);
        sb.append(", config=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.config, ')');
    }

    public final void updateConfig(int i) {
        this.config = i | this.config;
    }

    public /* synthetic */ AndroidVectorParser(XmlPullParser xmlPullParser, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(xmlPullParser, (i2 & 2) != 0 ? 0 : i);
    }
}
