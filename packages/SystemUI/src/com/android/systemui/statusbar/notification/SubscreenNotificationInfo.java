package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.RemoteInput;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenNotificationInfo implements Parcelable {
    public Drawable mAppIcon;
    public String mAppName;
    public int mAppPrimaryDefaultColor;
    public String mBigText;
    public String mBigTitle;
    public Bitmap mBitmap;
    public int mChildCount;
    public String mContent;
    public PendingIntent mContentIntent;
    public RemoteViews mContentView;
    public final Context mContext;
    public Icon mConversationIcon;
    public boolean mGroupSummary;
    public boolean mHasSemanticCall;
    public Drawable mIcon;
    public boolean mIsCall;
    public boolean mIsGroupConversation;
    public boolean mIsMessagingStyle;
    public boolean mIsMissedCall;
    public String mKey;
    public Drawable mKnoxBadgeDrawable;
    public Icon mLargeIcon;
    public boolean mNeedsOnePhoneIcon;
    public boolean mNeedsTwoPhoneIcon;
    public String mPkg;
    public PendingIntent mRemoteInputActionIntent;
    public boolean mRemoteInputIsSms;
    public int mRemoteInputMaxLength;
    public String mRemoteInputSignature;
    public boolean mRemoteinput;
    public ExpandableNotificationRow mRow;
    public StatusBarNotification mSbn;
    public PendingIntent mSemanticCallPendingIntent;
    public boolean mShowWhen;
    public String mTitle;
    public int mUnreadMessageCnt;
    public boolean mUseSmallIcon;
    public long mWhen;
    public static final Parcelable.Creator<SubscreenNotificationInfo> CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new SubscreenNotificationInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SubscreenNotificationInfo[i];
        }
    };
    public static final Uri CONTENT_URI = Uri.parse("content://com.android.server.notification.provider");
    public final String[] mInBox = {null, null, null, null, null, null, null};
    public final ArrayList mMessageingStyleInfoArray = new ArrayList(25);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MessagingStyleInfo {
        public String mContentText;
        public boolean mIsChecked;
        public boolean mIsReply;
        public long mPostedTime;
        public String mSender;
        public long mTimeStamp;
        public Drawable mUriImage;
    }

    public SubscreenNotificationInfo(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0041  */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable queryContentUriInternal(android.content.Context r9, java.lang.String r10) {
        /*
            java.lang.String r0 = "image"
            java.lang.String r1 = "SQLiteException occurs in deleteContentUri because  "
            r2 = 0
            android.content.ContentResolver r3 = r9.getContentResolver()     // Catch: java.lang.Throwable -> L45 android.database.sqlite.SQLiteException -> L48
            android.net.Uri r4 = com.android.systemui.statusbar.notification.SubscreenNotificationInfo.CONTENT_URI     // Catch: java.lang.Throwable -> L45 android.database.sqlite.SQLiteException -> L48
            java.lang.String[] r5 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L45 android.database.sqlite.SQLiteException -> L48
            java.lang.String r6 = "uri_id=?"
            java.lang.String[] r7 = new java.lang.String[]{r10}     // Catch: java.lang.Throwable -> L45 android.database.sqlite.SQLiteException -> L48
            r8 = 0
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L45 android.database.sqlite.SQLiteException -> L48
            if (r9 == 0) goto L3e
            boolean r10 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            if (r10 == 0) goto L3e
            int r10 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            byte[] r10 = r9.getBlob(r10)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            int r0 = r10.length     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            if (r0 == 0) goto L3e
            int r0 = r10.length     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            r3 = 0
            android.graphics.Bitmap r10 = android.graphics.BitmapFactory.decodeByteArray(r10, r3, r0)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            r0.<init>(r10)     // Catch: java.lang.Throwable -> L3a android.database.sqlite.SQLiteException -> L3c
            goto L3f
        L3a:
            r10 = move-exception
            goto L65
        L3c:
            r10 = move-exception
            goto L4a
        L3e:
            r0 = r2
        L3f:
            if (r9 == 0) goto L64
            r9.close()
            return r0
        L45:
            r10 = move-exception
            r9 = r2
            goto L65
        L48:
            r10 = move-exception
            r9 = r2
        L4a:
            java.lang.String r0 = "SubscreenNotificationInfo"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3a
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r10 = r10.getMessage()     // Catch: java.lang.Throwable -> L3a
            r3.append(r10)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r10 = r3.toString()     // Catch: java.lang.Throwable -> L3a
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L3a
            if (r9 == 0) goto L64
            r9.close()
        L64:
            return r2
        L65:
            if (r9 == 0) goto L6b
            r9.close()
            return r2
        L6b:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.queryContentUriInternal(android.content.Context, java.lang.String):android.graphics.drawable.Drawable");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final CharSequence findConversationTitle(StatusBarNotification statusBarNotification) {
        CharSequence charSequence = statusBarNotification.getNotification().extras.getCharSequence("android.conversationTitle");
        if (charSequence != null && !TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        Person person = (Person) statusBarNotification.getNotification().extras.getParcelable("android.messagingUser", Person.class);
        CharSequence name = person != null ? person.getName() : null;
        ArrayList arrayList = (ArrayList) getHistories(200);
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Bundle bundle = (Bundle) it.next();
                int i = bundle.getInt("type", 0);
                String string = bundle.getString(UniversalCredentialUtil.AGENT_TITLE, "");
                if (i != 1 && name != null && !string.equals(name)) {
                    return string;
                }
            }
        }
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(statusBarNotification.getPackageContext(this.mContext), statusBarNotification.getNotification());
        if (recoverBuilder.getStyle() instanceof Notification.MessagingStyle) {
            for (Notification.MessagingStyle.Message message : ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) {
                if (name != null && !TextUtils.isEmpty(name) && message.getSender() != null && !name.equals(message.getSender())) {
                    return message.getSender();
                }
            }
        }
        Log.d("SubscreenNotificationInfo", "coverscreen can't find conversation title properly so ,, return empty");
        return "";
    }

    public final String getContentHiddenText() {
        int i = (this.mGroupSummary && this.mRow.mIsSummaryWithChildren) ? this.mChildCount : 1;
        return this.mContext.getResources().getQuantityString(R.plurals.plural_notification_count, i, Integer.valueOf(i)).toString();
    }

    public final List getHistories(int i) {
        List semGetNotificationHistoryForPackage = ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).semGetNotificationHistoryForPackage(this.mContext.getPackageName(), this.mContext.getAttributionTag(), this.mSbn.getUserId(), this.mSbn.getPackageName(), this.mSbn.getKey(), i);
        ArrayList arrayList = new ArrayList();
        if (semGetNotificationHistoryForPackage != null) {
            Iterator it = semGetNotificationHistoryForPackage.iterator();
            while (it.hasNext()) {
                arrayList.add((Bundle) it.next());
            }
        }
        return arrayList;
    }

    public final String getTitle() {
        String str = this.mBigTitle;
        return str != null ? str : this.mTitle;
    }

    public final void makeConversation(ExpandableNotificationRow expandableNotificationRow) {
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        if (notificationEntry != null) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            Parcelable[] parcelableArray = notification2.extras.getParcelableArray("android.messages");
            Person person = (Person) notification2.extras.getParcelable("android.messagingUser", Person.class);
            for (Notification.MessagingStyle.Message message : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray)) {
                MessagingStyleInfo messagingStyleInfo = new MessagingStyleInfo();
                String charSequence = message.getText() != null ? message.getText().toString() : "";
                messagingStyleInfo.mContentText = charSequence;
                Uri dataUri = message.getDataUri();
                if (dataUri != null) {
                    Drawable loadImage = expandableNotificationRow.mImageResolver.loadImage(dataUri);
                    if (loadImage == null) {
                        Log.d("SubscreenNotificationInfo", notificationEntry.mKey + " : no drawable for " + dataUri);
                    } else {
                        messagingStyleInfo.mUriImage = loadImage;
                    }
                }
                Person senderPerson = message.getSenderPerson();
                String charSequence2 = (senderPerson == null || senderPerson.getName() == null) ? (person == null || person.getName() == null) ? "" : person.getName().toString() : senderPerson.getName().toString();
                boolean z = senderPerson == null || !(person == null || person.getName() == null || !charSequence2.equals(person.getName().toString()));
                if ("com.viber.voip".equals(notificationEntry.mSbn.getPackageName())) {
                    z = "Me".equals(charSequence2);
                }
                messagingStyleInfo.mIsReply = z;
                messagingStyleInfo.mSender = charSequence2;
                messagingStyleInfo.mTimeStamp = message.getTimestamp();
                this.mMessageingStyleInfoArray.add(messagingStyleInfo);
                this.mContent = charSequence;
                if (!"".equals(findConversationTitle(notificationEntry.mSbn).toString())) {
                    this.mTitle = findConversationTitle(notificationEntry.mSbn).toString();
                }
            }
        }
    }

    public final void setItemsData(ExpandableNotificationRow expandableNotificationRow) {
        int HSVToColor;
        CharSequence charSequence;
        Notification notification2;
        Notification notification3;
        char c;
        Drawable queryContentUriInternal;
        this.mRow = expandableNotificationRow;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        this.mSbn = statusBarNotification;
        this.mKey = statusBarNotification.getKey();
        this.mPkg = this.mSbn.getPackageName();
        this.mSbn.isOngoing();
        Notification notification4 = this.mSbn.getNotification();
        Bundle bundle = notification4.extras;
        SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
        int i = 0;
        this.mNeedsOnePhoneIcon = false;
        this.mNeedsTwoPhoneIcon = false;
        boolean z = true;
        if ("MESSAGE_KT_TWO_PHONE_OPPOSITE_RECEIVED".equals(notification4.getGroup()) || "two_phone_missed_call_group".equals(notification4.getGroup())) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
            Integer valueOf = subscreenDeviceModelParent != null ? Integer.valueOf(subscreenDeviceModelParent.currentUserId) : null;
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
            if (valueOf == (subscreenDeviceModelParent2 != null ? Integer.valueOf(subscreenDeviceModelParent2.bModeUserId) : null)) {
                this.mNeedsOnePhoneIcon = true;
            } else {
                this.mNeedsTwoPhoneIcon = true;
            }
        }
        boolean isGroupSummary = notification4.isGroupSummary();
        this.mGroupSummary = isGroupSummary;
        if (isGroupSummary && expandableNotificationRow.mIsSummaryWithChildren) {
            this.mChildCount = expandableNotificationRow.mChildrenContainer.mUntruncatedChildCount;
        }
        String str = notification4.category;
        if (str != null) {
            this.mIsMissedCall = "missed_call".equals(str);
            this.mIsCall = "call".equals(notification4.category) && notification4.isStyle(Notification.CallStyle.class);
        }
        this.mRemoteinput = false;
        this.mHasSemanticCall = false;
        Notification.Action[] actionArr = notification4.actions;
        String str2 = "";
        if (actionArr != null) {
            int length = actionArr.length;
            int i2 = 0;
            while (i2 < length) {
                Notification.Action action = notification4.actions[i2];
                if (action != null) {
                    RemoteInput[] remoteInputs = action.getRemoteInputs();
                    if (remoteInputs != null) {
                        this.mRemoteinput = z;
                        int length2 = remoteInputs.length;
                        for (int i3 = i; i3 < length2; i3++) {
                            RemoteInput remoteInput = remoteInputs[i3];
                            if (remoteInput.getAllowFreeFormInput()) {
                                this.mRemoteInputMaxLength = remoteInput.getExtras().getInt("maxLength", 200);
                                this.mRemoteInputIsSms = remoteInput.getExtras().getBoolean("isSms", false);
                                this.mRemoteInputSignature = remoteInput.getExtras().getString(Account.SIGNATURE, "");
                                this.mRemoteInputActionIntent = action.actionIntent;
                            }
                        }
                    }
                    if (action.getSemanticAction() == 10) {
                        this.mHasSemanticCall = true;
                        this.mSemanticCallPendingIntent = action.actionIntent;
                    }
                }
                i2++;
                i = 0;
                z = true;
            }
        }
        Drawable loadDrawable = notification4.getSmallIcon().loadDrawable(this.mContext);
        this.mIcon = loadDrawable;
        int i4 = this.mSbn.getNotification().color;
        if (loadDrawable == null) {
            loadDrawable = null;
        } else {
            loadDrawable.mutate();
            if (loadDrawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) loadDrawable;
                int numberOfFrames = animationDrawable.getNumberOfFrames();
                Drawable[] drawableArr = new Drawable[numberOfFrames];
                for (int i5 = 0; i5 < numberOfFrames; i5++) {
                    drawableArr[i5] = animationDrawable.getFrame(i5);
                }
                loadDrawable = new LayerDrawable(drawableArr);
            }
            if (ContrastColorUtil.getInstance(this.mContext).isGrayscaleIcon(loadDrawable)) {
                int red = Color.red(i4);
                int green = Color.green(i4);
                int blue = Color.blue(i4);
                if (Color.red(i4) == 0 && Color.green(i4) == 0 && Color.blue(i4) == 0) {
                    HSVToColor = this.mAppPrimaryDefaultColor;
                } else {
                    float[] fArr = {0.0f, r10, 0.0f};
                    Color.RGBToHSV(red, green, blue, fArr);
                    float f = fArr[1] - 0.15f;
                    fArr[1] = Math.max(0.0f, Math.min(1.0f, f));
                    float f2 = fArr[2] + 0.2f;
                    fArr[2] = f2;
                    fArr[2] = Math.max(0.0f, Math.min(1.0f, f2));
                    HSVToColor = Color.HSVToColor(255, fArr);
                    if (Math.round((255 / 255.0f) * Math.max(Color.red(HSVToColor), Math.max(Color.green(HSVToColor), Color.blue(HSVToColor)))) > 204) {
                        float round = 204 / Math.round((r8 / 255.0f) * Math.max(r12, Math.max(r13, r6)));
                        HSVToColor = Color.argb(Color.alpha(HSVToColor), Math.round(Color.red(HSVToColor) * round), Math.round(Color.green(HSVToColor) * round), Math.round(round * Color.blue(HSVToColor)));
                    }
                }
                loadDrawable.setColorFilter(HSVToColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
        this.mIcon = loadDrawable;
        this.mConversationIcon = (Icon) bundle.getParcelable("android.conversationIcon");
        this.mLargeIcon = (Icon) bundle.getParcelable("android.largeIcon");
        boolean equals = Notification.MessagingStyle.class.equals(notification4.getNotificationStyle());
        this.mIsMessagingStyle = equals;
        this.mUnreadMessageCnt = 0;
        if (equals) {
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(subscreenNotificationController.context, notificationEntry.mSbn.getNotification());
            ConversationNotificationManager conversationNotificationManager = subscreenNotificationController.conversationNotificationManager;
            Object compute = conversationNotificationManager.states.compute(notificationEntry.mKey, new ConversationNotificationManager$getUnreadCount$1(notificationEntry, conversationNotificationManager, recoverBuilder));
            Intrinsics.checkNotNull(compute);
            this.mUnreadMessageCnt = ((ConversationNotificationManager.ConversationState) compute).unreadCount;
            this.mIsGroupConversation = bundle.getBoolean("android.isGroupConversation");
            charSequence = findConversationTitle(this.mSbn);
        } else {
            charSequence = bundle.getCharSequence("android.title");
        }
        if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY || !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationHistoryEnabled()) {
            notification2 = notification4;
            if (equals) {
                makeConversation(expandableNotificationRow);
            }
        } else if (this.mRemoteinput || equals) {
            List histories = getHistories(50);
            SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationController.mDeviceModel;
            subscreenDeviceModelParent3.getClass();
            boolean z2 = (subscreenDeviceModelParent3 instanceof SubscreenDeviceModelB5) && ((ArrayList) histories).size() > 0;
            ArrayList arrayList = (ArrayList) histories;
            if (arrayList.size() > 0) {
                this.mIsMessagingStyle |= this.mRemoteinput;
            }
            if (z2) {
                Iterator it = arrayList.iterator();
                int i6 = 0;
                while (it.hasNext()) {
                    Bundle bundle2 = (Bundle) it.next();
                    int i7 = bundle2.getInt("type", 0);
                    String string = bundle2.getString(UniversalCredentialUtil.AGENT_TITLE, str2);
                    String string2 = bundle2.getString("text", str2);
                    String string3 = bundle2.getString("uri", str2);
                    String string4 = bundle2.getString("sbnKey", str2);
                    int i8 = i6;
                    long j = bundle2.getLong("postedTime", 0L);
                    Iterator it2 = it;
                    long j2 = bundle2.getLong("when", 0L);
                    String str3 = str2;
                    boolean z3 = bundle2.getBoolean("isChecked", false);
                    if (!this.mKey.equals(string4)) {
                        notification3 = notification4;
                        c = 2;
                        i6 = i8;
                    } else {
                        if (i8 >= 25) {
                            break;
                        }
                        boolean z4 = i7 == 1;
                        MessagingStyleInfo messagingStyleInfo = new MessagingStyleInfo();
                        messagingStyleInfo.mContentText = string2;
                        notification3 = notification4;
                        if (this.mContent == null) {
                            this.mContent = string2;
                        }
                        messagingStyleInfo.mSender = string;
                        messagingStyleInfo.mTimeStamp = j2;
                        messagingStyleInfo.mPostedTime = j;
                        messagingStyleInfo.mIsReply = z4;
                        messagingStyleInfo.mIsChecked = z3;
                        c = 2;
                        if (i7 == 2) {
                            try {
                                queryContentUriInternal = queryContentUriInternal(this.mContext, string3);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                            if (queryContentUriInternal != null) {
                                messagingStyleInfo.mUriImage = queryContentUriInternal;
                            } else {
                                i6 = i8;
                                it = it2;
                                str2 = str3;
                                notification4 = notification3;
                            }
                        }
                        this.mMessageingStyleInfoArray.add(messagingStyleInfo);
                        i6 = i8 + 1;
                    }
                    it = it2;
                    str2 = str3;
                    notification4 = notification3;
                }
                notification2 = notification4;
                this.mMessageingStyleInfoArray.sort(new Comparator(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return (int) (((MessagingStyleInfo) obj).mPostedTime - ((MessagingStyleInfo) obj2).mPostedTime);
                    }
                });
            } else {
                notification2 = notification4;
                makeConversation(expandableNotificationRow);
            }
        } else {
            notification2 = notification4;
        }
        String str4 = this.mTitle;
        if (str4 == null) {
            str4 = charSequence == null ? null : charSequence.toString();
        }
        this.mTitle = str4;
        CharSequence charSequence2 = bundle.getCharSequence("android.text");
        String str5 = this.mContent;
        if (str5 == null) {
            str5 = charSequence2 == null ? null : charSequence2.toString();
        }
        this.mContent = str5;
        CharSequence charSequence3 = bundle.getCharSequence("android.bigText");
        this.mBigText = charSequence3 == null ? null : charSequence3.toString();
        CharSequence charSequence4 = bundle.getCharSequence("android.title.big");
        this.mBigTitle = charSequence4 == null ? null : charSequence4.toString();
        CharSequence charSequence5 = bundle.getCharSequence("android.subText");
        if (charSequence5 != null) {
            charSequence5.toString();
        }
        this.mBitmap = (Bitmap) bundle.getParcelable("android.picture");
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            CharSequence charSequence6 = bundle.getCharSequence("android.substName");
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.mSbn.getPackageName(), 4202624);
            this.mAppPrimaryDefaultColor = this.mContext.getColor(R.color.subscreen_notification_primary_default_for_common);
            this.mAppIcon = packageManager.semGetApplicationIconForIconTray(applicationInfo, 33);
            this.mUseSmallIcon = applicationInfo.icon == 0;
            String charSequence7 = charSequence6 == null ? packageManager.getApplicationLabel(applicationInfo).toString() : charSequence6.toString();
            this.mAppName = charSequence7;
            this.mAppName = charSequence7.replace("\n", " ");
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        CharSequence[] charSequenceArray = bundle.getCharSequenceArray("android.textLines");
        if (charSequenceArray != null) {
            for (int i9 = 0; i9 < Math.min(this.mInBox.length, charSequenceArray.length); i9++) {
                String[] strArr = this.mInBox;
                CharSequence charSequence8 = charSequenceArray[i9];
                strArr[i9] = charSequence8 == null ? null : charSequence8.toString();
            }
        }
        Notification notification5 = notification2;
        this.mContentIntent = notification5.contentIntent;
        this.mContentView = this.mSbn.getNotification().contentView;
        this.mWhen = notification5.when;
        this.mShowWhen = bundle.getBoolean("android.showWhen");
        this.mKnoxBadgeDrawable = null;
        int childCount = this.mRow.getChildCount();
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = this.mRow.getChildAt(i10);
            if (childAt instanceof NotificationContentView) {
                NotificationContentView notificationContentView = (NotificationContentView) childAt;
                View view = notificationContentView.mExpandedChild;
                if (view == null) {
                    view = notificationContentView.mContractedChild;
                }
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(android.R.id.secondary);
                    if (imageView == null || imageView.getDrawable() == null) {
                        return;
                    }
                    this.mKnoxBadgeDrawable = imageView.getDrawable().mutate();
                    return;
                }
            }
        }
    }

    public final boolean useSmallIcon() {
        boolean equals = "android".equals(this.mPkg);
        boolean equals2 = "com.android.systemui".equals(this.mPkg);
        boolean z = equals || equals2 || this.mUseSmallIcon;
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mKey);
            sb.append(": use small icon. androidPkg = ");
            sb.append(equals);
            sb.append(", systemuiPkg = ");
            sb.append(equals2);
            sb.append(", mUseSmallIcon = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mUseSmallIcon, "SubscreenNotificationInfo");
        }
        return z;
    }

    public SubscreenNotificationInfo(Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
    }
}
