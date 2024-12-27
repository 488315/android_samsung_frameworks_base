package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class PromptVerticalListContentView implements PromptContentViewParcelable {
    public static final Parcelable.Creator<PromptVerticalListContentView> CREATOR =
            new Parcelable.Creator<PromptVerticalListContentView>() { // from class:
                // android.hardware.biometrics.PromptVerticalListContentView.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptVerticalListContentView createFromParcel(Parcel in) {
                    return new PromptVerticalListContentView(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptVerticalListContentView[] newArray(int size) {
                    return new PromptVerticalListContentView[size];
                }
            };
    static final int MAX_DESCRIPTION_CHARACTER_NUMBER = 225;
    static final int MAX_EACH_ITEM_CHARACTER_NUMBER = 640;
    static final int MAX_ITEM_NUMBER = 20;
    private final List<PromptContentItemParcelable> mContentList;
    private final String mDescription;

    private PromptVerticalListContentView(
            List<PromptContentItemParcelable> contentList, String description) {
        this.mContentList = contentList;
        this.mDescription = description;
    }

    private PromptVerticalListContentView(Parcel in) {
        this.mContentList =
                in.readArrayList(
                        PromptContentItemParcelable.class.getClassLoader(),
                        PromptContentItemParcelable.class);
        this.mDescription = in.readString();
    }

    public static int getMaxItemCount() {
        return 20;
    }

    public static int getMaxEachItemCharacterNumber() {
        return 640;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List<PromptContentItem> getListItems() {
        return new ArrayList(this.mContentList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mContentList);
        dest.writeString(this.mDescription);
    }

    public static final class Builder {
        private final List<PromptContentItemParcelable> mContentList = new ArrayList();
        private String mDescription;

        public Builder setDescription(String description) {
            if (description.length() > 225) {
                throw new IllegalArgumentException(
                        "The character number of description exceeds 225");
            }
            this.mDescription = description;
            return this;
        }

        public Builder addListItem(PromptContentItem listItem) {
            this.mContentList.add((PromptContentItemParcelable) listItem);
            checkItemLimits(listItem);
            return this;
        }

        public Builder addListItem(PromptContentItem listItem, int index) {
            this.mContentList.add(index, (PromptContentItemParcelable) listItem);
            checkItemLimits(listItem);
            return this;
        }

        private void checkItemLimits(PromptContentItem listItem) {
            if (doesListItemExceedsCharLimit(listItem)) {
                throw new IllegalArgumentException("The character number of list item exceeds 640");
            }
            if (this.mContentList.size() > 20) {
                throw new IllegalArgumentException("The number of list items exceeds 20");
            }
        }

        private boolean doesListItemExceedsCharLimit(PromptContentItem listItem) {
            return listItem instanceof PromptContentItemPlainText
                    ? ((PromptContentItemPlainText) listItem).getText().length() > 640
                    : (listItem instanceof PromptContentItemBulletedText)
                            && ((PromptContentItemBulletedText) listItem).getText().length() > 640;
        }

        public PromptVerticalListContentView build() {
            return new PromptVerticalListContentView(this.mContentList, this.mDescription);
        }
    }
}
