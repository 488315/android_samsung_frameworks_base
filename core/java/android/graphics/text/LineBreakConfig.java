package android.graphics.text;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes.dex */
public final class LineBreakConfig {
  public static final int LINE_BREAK_STYLE_LOOSE = 1;
  public static final int LINE_BREAK_STYLE_NONE = 0;
  public static final int LINE_BREAK_STYLE_NORMAL = 2;
  public static final int LINE_BREAK_STYLE_STRICT = 3;
  public static final int LINE_BREAK_WORD_STYLE_NONE = 0;
  public static final int LINE_BREAK_WORD_STYLE_PHRASE = 1;
  public static final LineBreakConfig NONE =
      new Builder().setLineBreakStyle(0).setLineBreakWordStyle(0).build();
  private final int mLineBreakStyle;
  private final int mLineBreakWordStyle;

  @Retention(RetentionPolicy.SOURCE)
  public @interface LineBreakStyle {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface LineBreakWordStyle {}

  public static final class Builder {
    private int mLineBreakStyle = 0;
    private int mLineBreakWordStyle = 0;

    public Builder setLineBreakStyle(int lineBreakStyle) {
      this.mLineBreakStyle = lineBreakStyle;
      return this;
    }

    public Builder setLineBreakWordStyle(int lineBreakWordStyle) {
      this.mLineBreakWordStyle =
          "ko".equalsIgnoreCase(Locale.getDefault().getLanguage()) ? 1 : lineBreakWordStyle;
      return this;
    }

    public LineBreakConfig build() {
      return new LineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle);
    }
  }

  public static LineBreakConfig getLineBreakConfig(int lineBreakStyle, int lineBreakWordStyle) {
    Builder builder = new Builder();
    return builder
        .setLineBreakStyle(lineBreakStyle)
        .setLineBreakWordStyle(lineBreakWordStyle)
        .build();
  }

  private LineBreakConfig(int lineBreakStyle, int lineBreakWordStyle) {
    this.mLineBreakStyle = lineBreakStyle;
    this.mLineBreakWordStyle = lineBreakWordStyle;
  }

  public int getLineBreakStyle() {
    return this.mLineBreakStyle;
  }

  public int getLineBreakWordStyle() {
    return this.mLineBreakWordStyle;
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
        || this.mLineBreakWordStyle != that.mLineBreakWordStyle) {
      return false;
    }
    return true;
  }

  public int hashCode() {
    return Objects.hash(
        Integer.valueOf(this.mLineBreakStyle), Integer.valueOf(this.mLineBreakWordStyle));
  }
}
