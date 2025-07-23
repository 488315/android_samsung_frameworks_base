package android.graphics.text;

import android.os.Parcel;
import android.os.Parcelable;
import dalvik.system.VMRuntime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes.dex */
public final class LineBreakConfig implements Parcelable {
    public static final int HYPHENATION_DISABLED = 0;
    public static final int HYPHENATION_ENABLED = 1;
    public static final int HYPHENATION_UNSPECIFIED = -1;
    public static final int LINE_BREAK_STYLE_AUTO = 5;
    public static final int LINE_BREAK_STYLE_LOOSE = 1;
    public static final int LINE_BREAK_STYLE_NONE = 0;
    public static final int LINE_BREAK_STYLE_NORMAL = 2;
    public static final int LINE_BREAK_STYLE_NO_BREAK = 4;
    public static final int LINE_BREAK_STYLE_STRICT = 3;
    public static final int LINE_BREAK_STYLE_UNSPECIFIED = -1;
    public static final int LINE_BREAK_WORD_STYLE_AUTO = 2;
    public static final int LINE_BREAK_WORD_STYLE_NONE = 0;
    public static final int LINE_BREAK_WORD_STYLE_PHRASE = 1;
    public static final int LINE_BREAK_WORD_STYLE_UNSPECIFIED = -1;
    private final int mHyphenation;
    private final int mLineBreakStyle;
    private final int mLineBreakWordStyle;
    public static final LineBreakConfig NONE = new Builder().setLineBreakStyle(0).setLineBreakWordStyle(0).build();
    public static final Parcelable.Creator<LineBreakConfig> CREATOR = new Parcelable.Creator<LineBreakConfig>() { // from class: android.graphics.text.LineBreakConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LineBreakConfig createFromParcel(Parcel parcel) {
            return new LineBreakConfig(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LineBreakConfig[] newArray(int i) {
            return new LineBreakConfig[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Hyphenation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LineBreakStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LineBreakWordStyle {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mLineBreakStyle = -1;
        private int mLineBreakWordStyle = -1;
        private int mHyphenation = -1;

        public Builder() {
            reset(null);
        }

        public Builder merge(LineBreakConfig lineBreakConfig) {
            if (lineBreakConfig.mLineBreakStyle != -1) {
                this.mLineBreakStyle = lineBreakConfig.mLineBreakStyle;
            }
            if (lineBreakConfig.mLineBreakWordStyle != -1) {
                this.mLineBreakWordStyle = lineBreakConfig.mLineBreakWordStyle;
            }
            if (lineBreakConfig.mHyphenation != -1) {
                this.mHyphenation = lineBreakConfig.mHyphenation;
            }
            return this;
        }

        public Builder reset(LineBreakConfig lineBreakConfig) {
            if (lineBreakConfig == null) {
                this.mLineBreakStyle = -1;
                this.mLineBreakWordStyle = -1;
                this.mHyphenation = -1;
                return this;
            }
            this.mLineBreakStyle = lineBreakConfig.mLineBreakStyle;
            this.mLineBreakWordStyle = lineBreakConfig.mLineBreakWordStyle;
            this.mHyphenation = lineBreakConfig.mHyphenation;
            return this;
        }

        public Builder setLineBreakStyle(int i) {
            this.mLineBreakStyle = i;
            return this;
        }

        public Builder setLineBreakWordStyle(int i) {
            if ("ko".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
                i = 1;
            }
            this.mLineBreakWordStyle = i;
            return this;
        }

        public Builder setHyphenation(int i) {
            this.mHyphenation = i;
            return this;
        }

        public LineBreakConfig build() {
            return new LineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle, this.mHyphenation);
        }
    }

    public static LineBreakConfig getLineBreakConfig(int i, int i2) {
        return new Builder().setLineBreakStyle(i).setLineBreakWordStyle(i2).build();
    }

    public LineBreakConfig(int i, int i2, int i3) {
        this.mLineBreakStyle = i;
        this.mLineBreakWordStyle = i2;
        this.mHyphenation = i3;
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public static int getResolvedLineBreakStyle(LineBreakConfig lineBreakConfig) {
        int i;
        return (lineBreakConfig == null || (i = lineBreakConfig.mLineBreakStyle) == -1) ? VMRuntime.getRuntime().getTargetSdkVersion() >= 35 ? 5 : 0 : i;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public static int getResolvedLineBreakWordStyle(LineBreakConfig lineBreakConfig) {
        int i;
        return (lineBreakConfig == null || (i = lineBreakConfig.mLineBreakWordStyle) == -1) ? VMRuntime.getRuntime().getTargetSdkVersion() >= 35 ? 2 : 0 : i;
    }

    public int getHyphenation() {
        return this.mHyphenation;
    }

    public static int getResolvedHyphenation(LineBreakConfig lineBreakConfig) {
        int i;
        if (lineBreakConfig == null || (i = lineBreakConfig.mHyphenation) == -1) {
            return 1;
        }
        return i;
    }

    public LineBreakConfig merge(LineBreakConfig lineBreakConfig) {
        int i = lineBreakConfig.mLineBreakStyle;
        if (i == -1) {
            i = this.mLineBreakStyle;
        }
        int i2 = lineBreakConfig.mLineBreakWordStyle;
        if (i2 == -1) {
            i2 = this.mLineBreakWordStyle;
        }
        int i3 = lineBreakConfig.mHyphenation;
        if (i3 == -1) {
            i3 = this.mHyphenation;
        }
        return new LineBreakConfig(i, i2, i3);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineBreakConfig)) {
            return false;
        }
        LineBreakConfig lineBreakConfig = (LineBreakConfig) obj;
        return this.mLineBreakStyle == lineBreakConfig.mLineBreakStyle && this.mLineBreakWordStyle == lineBreakConfig.mLineBreakWordStyle && this.mHyphenation == lineBreakConfig.mHyphenation;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mLineBreakStyle), Integer.valueOf(this.mLineBreakWordStyle), Integer.valueOf(this.mHyphenation));
    }

    public String toString() {
        return "LineBreakConfig{mLineBreakStyle=" + this.mLineBreakStyle + ", mLineBreakWordStyle=" + this.mLineBreakWordStyle + ", mHyphenation= " + this.mHyphenation + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLineBreakStyle);
        parcel.writeInt(this.mLineBreakWordStyle);
        parcel.writeInt(this.mHyphenation);
    }
}
