package android.graphics.fonts;

import android.annotation.SystemApi;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class FontFamilyUpdateRequest {
    private final List<FontFamily> mFontFamilies;
    private final List<FontFileUpdateRequest> mFontFiles;

    public static final class FontFamily {
        private final List<Font> mFonts;
        private final String mName;

        public static final class Builder {
            private final List<Font> mFonts;
            private final String mName;

            public Builder(String str, List<Font> list) {
                Objects.requireNonNull(str);
                Preconditions.checkStringNotEmpty(str);
                Objects.requireNonNull(list);
                Preconditions.checkCollectionElementsNotNull(list, "fonts");
                Preconditions.checkCollectionNotEmpty(list, "fonts");
                this.mName = str;
                this.mFonts = new ArrayList(list);
            }

            public Builder addFont(Font font) {
                this.mFonts.add(font);
                return this;
            }

            public FontFamily build() {
                return new FontFamily(this.mName, this.mFonts);
            }
        }

        private FontFamily(String str, List<Font> list) {
            this.mName = str;
            this.mFonts = list;
        }

        public String getName() {
            return this.mName;
        }

        public List<Font> getFonts() {
            return this.mFonts;
        }
    }

    public static final class Font {
        private final List<FontVariationAxis> mAxes;
        private final int mIndex;
        private final String mPostScriptName;
        private final FontStyle mStyle;

        public static final class Builder {
            private List<FontVariationAxis> mAxes = Collections.EMPTY_LIST;
            private int mIndex = 0;
            private final String mPostScriptName;
            private final FontStyle mStyle;

            public Builder(String str, FontStyle fontStyle) {
                Objects.requireNonNull(str);
                Preconditions.checkStringNotEmpty(str);
                Objects.requireNonNull(fontStyle);
                this.mPostScriptName = str;
                this.mStyle = fontStyle;
            }

            public Builder setAxes(List<FontVariationAxis> list) {
                Objects.requireNonNull(list);
                Preconditions.checkCollectionElementsNotNull(list, "axes");
                this.mAxes = list;
                return this;
            }

            public Builder setIndex(int i) {
                Preconditions.checkArgumentNonnegative(i);
                this.mIndex = i;
                return this;
            }

            public Font build() {
                return new Font(this.mPostScriptName, this.mStyle, this.mIndex, this.mAxes);
            }
        }

        private Font(String str, FontStyle fontStyle, int i, List<FontVariationAxis> list) {
            this.mPostScriptName = str;
            this.mStyle = fontStyle;
            this.mIndex = i;
            this.mAxes = list;
        }

        public String getPostScriptName() {
            return this.mPostScriptName;
        }

        public FontStyle getStyle() {
            return this.mStyle;
        }

        public List<FontVariationAxis> getAxes() {
            return this.mAxes;
        }

        public int getIndex() {
            return this.mIndex;
        }
    }

    public static final class Builder {
        private final List<FontFileUpdateRequest> mFontFileUpdateRequests = new ArrayList();
        private final List<FontFamily> mFontFamilies = new ArrayList();

        public Builder addFontFileUpdateRequest(FontFileUpdateRequest fontFileUpdateRequest) {
            Objects.requireNonNull(fontFileUpdateRequest);
            this.mFontFileUpdateRequests.add(fontFileUpdateRequest);
            return this;
        }

        public Builder addFontFamily(FontFamily fontFamily) {
            Objects.requireNonNull(fontFamily);
            this.mFontFamilies.add(fontFamily);
            return this;
        }

        public FontFamilyUpdateRequest build() {
            return new FontFamilyUpdateRequest(this.mFontFileUpdateRequests, this.mFontFamilies);
        }
    }

    private FontFamilyUpdateRequest(List<FontFileUpdateRequest> list, List<FontFamily> list2) {
        this.mFontFiles = list;
        this.mFontFamilies = list2;
    }

    public List<FontFileUpdateRequest> getFontFileUpdateRequests() {
        return this.mFontFiles;
    }

    public List<FontFamily> getFontFamilies() {
        return this.mFontFamilies;
    }
}
