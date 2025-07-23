package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class PromptVerticalListContentView implements PromptContentViewParcelable {
    public static final Parcelable.Creator<PromptVerticalListContentView> CREATOR = new Parcelable.Creator<PromptVerticalListContentView>() { // from class: android.hardware.biometrics.PromptVerticalListContentView.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptVerticalListContentView createFromParcel(Parcel parcel) {
            return new PromptVerticalListContentView(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptVerticalListContentView[] newArray(int i) {
            return new PromptVerticalListContentView[i];
        }
    };
    static final int MAX_DESCRIPTION_CHARACTER_NUMBER = 225;
    static final int MAX_EACH_ITEM_CHARACTER_NUMBER = 640;
    static final int MAX_ITEM_NUMBER = 20;
    private static final String TAG = "PromptVerticalListContentView";
    private final List<PromptContentItemParcelable> mContentList;
    private final String mDescription;

    public static int getMaxEachItemCharacterNumber() {
        return 640;
    }

    public static int getMaxItemCount() {
        return 20;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PromptVerticalListContentView(List<PromptContentItemParcelable> list, String str) {
        this.mContentList = list;
        this.mDescription = str;
    }

    private PromptVerticalListContentView(Parcel parcel) {
        this.mContentList = parcel.readArrayList(PromptContentItemParcelable.class.getClassLoader(), PromptContentItemParcelable.class);
        this.mDescription = parcel.readString();
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List<PromptContentItem> getListItems() {
        return new ArrayList(this.mContentList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.mContentList);
        parcel.writeString(this.mDescription);
    }

    public static final class Builder {
        private final List<PromptContentItemParcelable> mContentList = new ArrayList();
        private String mDescription;

        public Builder setDescription(String str) {
            if (str.length() > 225) {
                Log.w(PromptVerticalListContentView.TAG, "The character number of description exceeds 225");
            }
            this.mDescription = str;
            return this;
        }

        public Builder addListItem(PromptContentItem promptContentItem) {
            this.mContentList.add((PromptContentItemParcelable) promptContentItem);
            checkItemLimits(promptContentItem);
            return this;
        }

        public Builder addListItem(PromptContentItem promptContentItem, int i) {
            this.mContentList.add(i, (PromptContentItemParcelable) promptContentItem);
            checkItemLimits(promptContentItem);
            return this;
        }

        private void checkItemLimits(PromptContentItem promptContentItem) {
            if (doesListItemExceedsCharLimit(promptContentItem)) {
                Log.w(PromptVerticalListContentView.TAG, "The character number of list item exceeds 640");
            }
            if (this.mContentList.size() > 20) {
                throw new IllegalArgumentException("The number of list items exceeds 20");
            }
        }

        private boolean doesListItemExceedsCharLimit(PromptContentItem promptContentItem) {
            return promptContentItem instanceof PromptContentItemPlainText ? ((PromptContentItemPlainText) promptContentItem).getText().length() > 640 : (promptContentItem instanceof PromptContentItemBulletedText) && ((PromptContentItemBulletedText) promptContentItem).getText().length() > 640;
        }

        public PromptVerticalListContentView build() {
            return new PromptVerticalListContentView(this.mContentList, this.mDescription);
        }
    }
}
