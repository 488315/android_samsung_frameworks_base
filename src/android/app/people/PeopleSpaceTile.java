package android.app.people;

import android.app.Person;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class PeopleSpaceTile implements Parcelable {
    public static final int BLOCK_CONVERSATIONS = 2;
    public static final Parcelable.Creator<PeopleSpaceTile> CREATOR = new Parcelable.Creator<PeopleSpaceTile>() { // from class: android.app.people.PeopleSpaceTile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PeopleSpaceTile createFromParcel(Parcel parcel) {
            return new PeopleSpaceTile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PeopleSpaceTile[] newArray(int i) {
            return new PeopleSpaceTile[i];
        }
    };
    public static final int SHOW_CONTACTS = 16;
    public static final int SHOW_CONVERSATIONS = 1;
    public static final int SHOW_IMPORTANT_CONVERSATIONS = 4;
    public static final int SHOW_STARRED_CONTACTS = 8;
    private String mBirthdayText;
    private boolean mCanBypassDnd;
    private float mContactAffinity;
    private Uri mContactUri;
    private String mId;
    private Intent mIntent;
    private boolean mIsImportantConversation;
    private boolean mIsPackageSuspended;
    private boolean mIsUserQuieted;
    private long mLastInteractionTimestamp;
    private int mMessagesCount;
    private String mNotificationCategory;
    private CharSequence mNotificationContent;
    private Uri mNotificationDataUri;
    private String mNotificationKey;
    private int mNotificationPolicyState;
    private CharSequence mNotificationSender;
    private long mNotificationTimestamp;
    private String mPackageName;
    private List<ConversationStatus> mStatuses;
    private UserHandle mUserHandle;
    private Icon mUserIcon;
    private CharSequence mUserName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PeopleSpaceTile(Builder builder) {
        this.mId = builder.mId;
        this.mUserName = builder.mUserName;
        this.mUserIcon = builder.mUserIcon;
        this.mContactUri = builder.mContactUri;
        this.mUserHandle = builder.mUserHandle;
        this.mPackageName = builder.mPackageName;
        this.mBirthdayText = builder.mBirthdayText;
        this.mLastInteractionTimestamp = builder.mLastInteractionTimestamp;
        this.mIsImportantConversation = builder.mIsImportantConversation;
        this.mNotificationKey = builder.mNotificationKey;
        this.mNotificationContent = builder.mNotificationContent;
        this.mNotificationSender = builder.mNotificationSender;
        this.mNotificationCategory = builder.mNotificationCategory;
        this.mNotificationDataUri = builder.mNotificationDataUri;
        this.mMessagesCount = builder.mMessagesCount;
        this.mIntent = builder.mIntent;
        this.mNotificationTimestamp = builder.mNotificationTimestamp;
        this.mStatuses = builder.mStatuses;
        this.mCanBypassDnd = builder.mCanBypassDnd;
        this.mIsPackageSuspended = builder.mIsPackageSuspended;
        this.mIsUserQuieted = builder.mIsUserQuieted;
        this.mNotificationPolicyState = builder.mNotificationPolicyState;
        this.mContactAffinity = builder.mContactAffinity;
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getUserName() {
        return this.mUserName;
    }

    public Icon getUserIcon() {
        return this.mUserIcon;
    }

    public Uri getContactUri() {
        return this.mContactUri;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getBirthdayText() {
        return this.mBirthdayText;
    }

    public long getLastInteractionTimestamp() {
        return this.mLastInteractionTimestamp;
    }

    public boolean isImportantConversation() {
        return this.mIsImportantConversation;
    }

    public String getNotificationKey() {
        return this.mNotificationKey;
    }

    public CharSequence getNotificationContent() {
        return this.mNotificationContent;
    }

    public CharSequence getNotificationSender() {
        return this.mNotificationSender;
    }

    public String getNotificationCategory() {
        return this.mNotificationCategory;
    }

    public Uri getNotificationDataUri() {
        return this.mNotificationDataUri;
    }

    public int getMessagesCount() {
        return this.mMessagesCount;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public long getNotificationTimestamp() {
        return this.mNotificationTimestamp;
    }

    public List<ConversationStatus> getStatuses() {
        return this.mStatuses;
    }

    public boolean canBypassDnd() {
        return this.mCanBypassDnd;
    }

    public boolean isPackageSuspended() {
        return this.mIsPackageSuspended;
    }

    public boolean isUserQuieted() {
        return this.mIsUserQuieted;
    }

    public int getNotificationPolicyState() {
        return this.mNotificationPolicyState;
    }

    public float getContactAffinity() {
        return this.mContactAffinity;
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.mId, this.mUserName, this.mUserIcon, this.mIntent);
        builder.setContactUri(this.mContactUri);
        builder.setUserHandle(this.mUserHandle);
        builder.setPackageName(this.mPackageName);
        builder.setBirthdayText(this.mBirthdayText);
        builder.setLastInteractionTimestamp(this.mLastInteractionTimestamp);
        builder.setIsImportantConversation(this.mIsImportantConversation);
        builder.setNotificationKey(this.mNotificationKey);
        builder.setNotificationContent(this.mNotificationContent);
        builder.setNotificationSender(this.mNotificationSender);
        builder.setNotificationCategory(this.mNotificationCategory);
        builder.setNotificationDataUri(this.mNotificationDataUri);
        builder.setMessagesCount(this.mMessagesCount);
        builder.setIntent(this.mIntent);
        builder.setNotificationTimestamp(this.mNotificationTimestamp);
        builder.setStatuses(this.mStatuses);
        builder.setCanBypassDnd(this.mCanBypassDnd);
        builder.setIsPackageSuspended(this.mIsPackageSuspended);
        builder.setIsUserQuieted(this.mIsUserQuieted);
        builder.setNotificationPolicyState(this.mNotificationPolicyState);
        builder.setContactAffinity(this.mContactAffinity);
        return builder;
    }

    public static class Builder {
        private String mBirthdayText;
        private boolean mCanBypassDnd;
        private float mContactAffinity;
        private Uri mContactUri;
        private String mId;
        private Intent mIntent;
        private boolean mIsImportantConversation;
        private boolean mIsPackageSuspended;
        private boolean mIsUserQuieted;
        private long mLastInteractionTimestamp;
        private int mMessagesCount;
        private String mNotificationCategory;
        private CharSequence mNotificationContent;
        private Uri mNotificationDataUri;
        private String mNotificationKey;
        private int mNotificationPolicyState;
        private CharSequence mNotificationSender;
        private long mNotificationTimestamp;
        private String mPackageName;
        private List<ConversationStatus> mStatuses;
        private UserHandle mUserHandle;
        private Icon mUserIcon;
        private CharSequence mUserName;

        public Builder(String str, CharSequence charSequence, Icon icon, Intent intent) {
            this.mId = str;
            this.mUserName = charSequence;
            this.mUserIcon = icon;
            this.mIntent = intent;
            this.mPackageName = intent == null ? null : intent.getPackage();
            this.mNotificationPolicyState = 1;
        }

        public Builder(ShortcutInfo shortcutInfo, LauncherApps launcherApps) {
            this.mId = shortcutInfo.getId();
            this.mUserName = shortcutInfo.getLabel();
            this.mUserIcon = PeopleSpaceTile.convertDrawableToIcon(launcherApps.getShortcutIconDrawable(shortcutInfo, 0));
            this.mUserHandle = shortcutInfo.getUserHandle();
            this.mPackageName = shortcutInfo.getPackage();
            this.mContactUri = getContactUri(shortcutInfo);
            this.mNotificationPolicyState = 1;
        }

        public Builder(ConversationChannel conversationChannel, LauncherApps launcherApps) {
            ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
            this.mId = shortcutInfo.getId();
            this.mUserName = shortcutInfo.getLabel();
            boolean z = false;
            this.mUserIcon = PeopleSpaceTile.convertDrawableToIcon(launcherApps.getShortcutIconDrawable(shortcutInfo, 0));
            this.mUserHandle = shortcutInfo.getUserHandle();
            this.mPackageName = shortcutInfo.getPackage();
            this.mContactUri = getContactUri(shortcutInfo);
            this.mStatuses = conversationChannel.getStatuses();
            this.mLastInteractionTimestamp = conversationChannel.getLastEventTimestamp();
            this.mIsImportantConversation = conversationChannel.getNotificationChannel() != null && conversationChannel.getNotificationChannel().isImportantConversation();
            if (conversationChannel.getNotificationChannel() != null && conversationChannel.getNotificationChannel().canBypassDnd()) {
                z = true;
            }
            this.mCanBypassDnd = z;
            this.mNotificationPolicyState = 1;
        }

        public Uri getContactUri(ShortcutInfo shortcutInfo) {
            if (shortcutInfo.getPersons() == null || shortcutInfo.getPersons().length != 1) {
                return null;
            }
            Person person = shortcutInfo.getPersons()[0];
            if (person.getUri() == null) {
                return null;
            }
            return Uri.parse(person.getUri());
        }

        public Builder setId(String str) {
            this.mId = str;
            return this;
        }

        public Builder setUserName(CharSequence charSequence) {
            this.mUserName = charSequence;
            return this;
        }

        public Builder setUserIcon(Icon icon) {
            this.mUserIcon = icon;
            return this;
        }

        public Builder setContactUri(Uri uri) {
            this.mContactUri = uri;
            return this;
        }

        public Builder setUserHandle(UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }

        public Builder setPackageName(String str) {
            this.mPackageName = str;
            return this;
        }

        public Builder setBirthdayText(String str) {
            this.mBirthdayText = str;
            return this;
        }

        public Builder setLastInteractionTimestamp(long j) {
            this.mLastInteractionTimestamp = j;
            return this;
        }

        public Builder setIsImportantConversation(boolean z) {
            this.mIsImportantConversation = z;
            return this;
        }

        public Builder setNotificationKey(String str) {
            this.mNotificationKey = str;
            return this;
        }

        public Builder setNotificationContent(CharSequence charSequence) {
            this.mNotificationContent = charSequence;
            return this;
        }

        public Builder setNotificationSender(CharSequence charSequence) {
            this.mNotificationSender = charSequence;
            return this;
        }

        public Builder setNotificationCategory(String str) {
            this.mNotificationCategory = str;
            return this;
        }

        public Builder setNotificationDataUri(Uri uri) {
            this.mNotificationDataUri = uri;
            return this;
        }

        public Builder setMessagesCount(int i) {
            this.mMessagesCount = i;
            return this;
        }

        public Builder setIntent(Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public Builder setNotificationTimestamp(long j) {
            this.mNotificationTimestamp = j;
            return this;
        }

        public Builder setStatuses(List<ConversationStatus> list) {
            this.mStatuses = list;
            return this;
        }

        public Builder setCanBypassDnd(boolean z) {
            this.mCanBypassDnd = z;
            return this;
        }

        public Builder setIsPackageSuspended(boolean z) {
            this.mIsPackageSuspended = z;
            return this;
        }

        public Builder setIsUserQuieted(boolean z) {
            this.mIsUserQuieted = z;
            return this;
        }

        public Builder setNotificationPolicyState(int i) {
            this.mNotificationPolicyState = i;
            return this;
        }

        public Builder setContactAffinity(float f) {
            this.mContactAffinity = f;
            return this;
        }

        public PeopleSpaceTile build() {
            return new PeopleSpaceTile(this);
        }
    }

    public PeopleSpaceTile(Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserName = parcel.readCharSequence();
        this.mUserIcon = (Icon) parcel.readParcelable(Icon.class.getClassLoader(), Icon.class);
        this.mContactUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
        this.mUserHandle = (UserHandle) parcel.readParcelable(UserHandle.class.getClassLoader(), UserHandle.class);
        this.mPackageName = parcel.readString();
        this.mBirthdayText = parcel.readString();
        this.mLastInteractionTimestamp = parcel.readLong();
        this.mIsImportantConversation = parcel.readBoolean();
        this.mNotificationKey = parcel.readString();
        this.mNotificationContent = parcel.readCharSequence();
        this.mNotificationSender = parcel.readCharSequence();
        this.mNotificationCategory = parcel.readString();
        this.mNotificationDataUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
        this.mMessagesCount = parcel.readInt();
        this.mIntent = (Intent) parcel.readParcelable(Intent.class.getClassLoader(), Intent.class);
        this.mNotificationTimestamp = parcel.readLong();
        ArrayList arrayList = new ArrayList();
        this.mStatuses = arrayList;
        parcel.readParcelableList(arrayList, ConversationStatus.class.getClassLoader(), ConversationStatus.class);
        this.mCanBypassDnd = parcel.readBoolean();
        this.mIsPackageSuspended = parcel.readBoolean();
        this.mIsUserQuieted = parcel.readBoolean();
        this.mNotificationPolicyState = parcel.readInt();
        this.mContactAffinity = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeCharSequence(this.mUserName);
        parcel.writeParcelable(this.mUserIcon, i);
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeParcelable(this.mUserHandle, i);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mBirthdayText);
        parcel.writeLong(this.mLastInteractionTimestamp);
        parcel.writeBoolean(this.mIsImportantConversation);
        parcel.writeString(this.mNotificationKey);
        parcel.writeCharSequence(this.mNotificationContent);
        parcel.writeCharSequence(this.mNotificationSender);
        parcel.writeString(this.mNotificationCategory);
        parcel.writeParcelable(this.mNotificationDataUri, i);
        parcel.writeInt(this.mMessagesCount);
        parcel.writeParcelable(this.mIntent, i);
        parcel.writeLong(this.mNotificationTimestamp);
        parcel.writeParcelableList(this.mStatuses, i);
        parcel.writeBoolean(this.mCanBypassDnd);
        parcel.writeBoolean(this.mIsPackageSuspended);
        parcel.writeBoolean(this.mIsUserQuieted);
        parcel.writeInt(this.mNotificationPolicyState);
        parcel.writeFloat(this.mContactAffinity);
    }

    public static Icon convertDrawableToIcon(Drawable drawable) {
        Bitmap bitmapCreateBitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return Icon.createWithBitmap(bitmapDrawable.getBitmap());
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return Icon.createWithBitmap(bitmapCreateBitmap);
    }
}
