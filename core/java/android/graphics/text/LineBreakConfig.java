package android.graphics.text;

import android.app.ActivityThread;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.Objects;

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
    public static final LineBreakConfig NONE =
            new Builder().setLineBreakStyle(0).setLineBreakWordStyle(0).build();
    public static final Parcelable.Creator<LineBreakConfig> CREATOR =
            new Parcelable.Creator<
                    LineBreakConfig>() { // from class: android.graphics.text.LineBreakConfig.1
                @Override // android.os.Parcelable.Creator
                public LineBreakConfig createFromParcel(Parcel source) {
                    int lineBreakStyle = source.readInt();
                    int lineBreakWordStyle = source.readInt();
                    int hyphenation = source.readInt();
                    return new LineBreakConfig(lineBreakStyle, lineBreakWordStyle, hyphenation);
                }

                @Override // android.os.Parcelable.Creator
                public LineBreakConfig[] newArray(int size) {
                    return new LineBreakConfig[size];
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Hyphenation {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface LineBreakStyle {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface LineBreakWordStyle {}

    public static final class Builder {
        private int mLineBreakStyle = -1;
        private int mLineBreakWordStyle = -1;
        private int mHyphenation = -1;

        public Builder() {
            reset(null);
        }

        public Builder merge(LineBreakConfig config) {
            if (config.mLineBreakStyle != -1) {
                this.mLineBreakStyle = config.mLineBreakStyle;
            }
            if (config.mLineBreakWordStyle != -1) {
                this.mLineBreakWordStyle = config.mLineBreakWordStyle;
            }
            if (config.mHyphenation != -1) {
                this.mHyphenation = config.mHyphenation;
            }
            return this;
        }

        public Builder reset(LineBreakConfig config) {
            if (config == null) {
                this.mLineBreakStyle = -1;
                this.mLineBreakWordStyle = -1;
                this.mHyphenation = -1;
            } else {
                this.mLineBreakStyle = config.mLineBreakStyle;
                this.mLineBreakWordStyle = config.mLineBreakWordStyle;
                this.mHyphenation = config.mHyphenation;
            }
            return this;
        }

        public Builder setLineBreakStyle(int lineBreakStyle) {
            this.mLineBreakStyle = lineBreakStyle;
            return this;
        }

        public Builder setLineBreakWordStyle(int lineBreakWordStyle) {
            this.mLineBreakWordStyle =
                    "ko".equalsIgnoreCase(Locale.getDefault().getLanguage())
                            ? 1
                            : lineBreakWordStyle;
            return this;
        }

        public Builder setHyphenation(int hyphenation) {
            this.mHyphenation = hyphenation;
            return this;
        }

        public LineBreakConfig build() {
            return new LineBreakConfig(
                    this.mLineBreakStyle, this.mLineBreakWordStyle, this.mHyphenation);
        }
    }

    public static LineBreakConfig getLineBreakConfig(int lineBreakStyle, int lineBreakWordStyle) {
        Builder builder = new Builder();
        return builder.setLineBreakStyle(lineBreakStyle)
                .setLineBreakWordStyle(lineBreakWordStyle)
                .build();
    }

    public LineBreakConfig(int lineBreakStyle, int lineBreakWordStyle, int hyphenation) {
        this.mLineBreakStyle = lineBreakStyle;
        this.mLineBreakWordStyle = lineBreakWordStyle;
        this.mHyphenation = hyphenation;
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public static int getResolvedLineBreakStyle(LineBreakConfig config) {
        int defaultStyle;
        int targetSdkVersion =
                ActivityThread.currentApplication().getApplicationInfo().targetSdkVersion;
        if (targetSdkVersion >= 35) {
            defaultStyle = 5;
        } else {
            defaultStyle = 0;
        }
        if (config == null) {
            return defaultStyle;
        }
        return config.mLineBreakStyle == -1 ? defaultStyle : config.mLineBreakStyle;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public static int getResolvedLineBreakWordStyle(LineBreakConfig config) {
        int defaultWordStyle;
        int targetSdkVersion =
                ActivityThread.currentApplication().getApplicationInfo().targetSdkVersion;
        if (targetSdkVersion >= 35) {
            defaultWordStyle = 2;
        } else {
            defaultWordStyle = 0;
        }
        if (config == null) {
            return defaultWordStyle;
        }
        return config.mLineBreakWordStyle == -1 ? defaultWordStyle : config.mLineBreakWordStyle;
    }

    public int getHyphenation() {
        return this.mHyphenation;
    }

    public static int getResolvedHyphenation(LineBreakConfig config) {
        if (config == null || config.mHyphenation == -1) {
            return 1;
        }
        return config.mHyphenation;
    }

    public LineBreakConfig merge(LineBreakConfig config) {
        return new LineBreakConfig(
                config.mLineBreakStyle == -1 ? this.mLineBreakStyle : config.mLineBreakStyle,
                config.mLineBreakWordStyle == -1
                        ? this.mLineBreakWordStyle
                        : config.mLineBreakWordStyle,
                config.mHyphenation == -1 ? this.mHyphenation : config.mHyphenation);
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineBreakConfig)) {
            return false;
        }
        LineBreakConfig that = (LineBreakConfig) o;
        if (this.mLineBreakStyle != that.mLineBreakStyle
                || this.mLineBreakWordStyle != that.mLineBreakWordStyle
                || this.mHyphenation != that.mHyphenation) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mLineBreakStyle),
                Integer.valueOf(this.mLineBreakWordStyle),
                Integer.valueOf(this.mHyphenation));
    }

    public String toString() {
        return "LineBreakConfig{mLineBreakStyle="
                + this.mLineBreakStyle
                + ", mLineBreakWordStyle="
                + this.mLineBreakWordStyle
                + ", mHyphenation= "
                + this.mHyphenation
                + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mLineBreakStyle);
        dest.writeInt(this.mLineBreakWordStyle);
        dest.writeInt(this.mHyphenation);
    }
}
