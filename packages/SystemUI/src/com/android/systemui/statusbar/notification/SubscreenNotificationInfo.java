package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.RemoteInput;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
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
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public class SubscreenNotificationInfo implements Parcelable {
    public SubscreenNotificationController controller;
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
    public View mOngoingView;
    public String mPkg;
    public PendingIntent mRemoteInputActionIntent;
    public boolean mRemoteInputIsSms;
    public int mRemoteInputMaxLength;
    public String mRemoteInputSignature;
    public boolean mRemoteinput;
    public ExpandableNotificationRow mRow;
    public StatusBarNotification mSbn;
    public PendingIntent mSemanticCallPendingIntent;
    public boolean mShowSmallIcon;
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

    public class MessagingStyleInfo {
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
    /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006f  */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Drawable queryContentUriInternal(Context context, String str) throws Throwable {
        Throwable th;
        ?? r9;
        SQLiteException sQLiteException;
        Cursor cursorQuery;
        try {
            try {
                cursorQuery = context.getContentResolver().query(CONTENT_URI, new String[]{"image"}, "uri_id=?", new String[]{str}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            byte[] blob = cursorQuery.getBlob(cursorQuery.getColumnIndex("image"));
                            BitmapDrawable bitmapDrawable = blob.length != 0 ? new BitmapDrawable(BitmapFactory.decodeByteArray(blob, 0, blob.length)) : null;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                                return bitmapDrawable;
                            }
                        }
                    } catch (SQLiteException e) {
                        sQLiteException = e;
                        Log.e("SubscreenNotificationInfo", "SQLiteException occurs in deleteContentUri because  " + sQLiteException.getMessage());
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                r9 = context;
                if (r9 != 0) {
                    throw th;
                }
                r9.close();
                return null;
            }
        } catch (SQLiteException e2) {
            sQLiteException = e2;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            r9 = 0;
            if (r9 != 0) {
            }
        }
        return null;
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
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Bundle bundle = (Bundle) obj;
                int i2 = bundle.getInt("type", 0);
                String string = bundle.getString(UniversalCredentialUtil.AGENT_TITLE, "");
                if (i2 != 1 && name != null && !string.equals(name)) {
                    return string;
                }
            }
        }
        Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(statusBarNotification.getPackageContext(this.mContext), statusBarNotification.getNotification());
        if (builderRecoverBuilder.getStyle() instanceof Notification.MessagingStyle) {
            for (Notification.MessagingStyle.Message message : ((Notification.MessagingStyle) builderRecoverBuilder.getStyle()).getMessages()) {
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
        List listSemGetNotificationHistoryForPackage = ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).semGetNotificationHistoryForPackage(this.mContext.getPackageName(), this.mContext.getAttributionTag(), this.mSbn.getUserId(), this.mSbn.getPackageName(), this.mSbn.getKey(), i);
        ArrayList arrayList = new ArrayList();
        if (listSemGetNotificationHistoryForPackage != null) {
            Iterator it = listSemGetNotificationHistoryForPackage.iterator();
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

    public final boolean isSportsOngoing() {
        return this.mRow != null && "com.samsung.android.app.aodservice".equals(this.mPkg) && this.mRow.mEntry.isOngoingActivity();
    }

    public final void makeConversation(ExpandableNotificationRow expandableNotificationRow) {
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        if (notificationEntry != null) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            Parcelable[] parcelableArray = notification2.extras.getParcelableArray("android.messages");
            Person person = (Person) notification2.extras.getParcelable("android.messagingUser", Person.class);
            for (Notification.MessagingStyle.Message message : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray)) {
                MessagingStyleInfo messagingStyleInfo = new MessagingStyleInfo();
                String string = message.getText() != null ? message.getText().toString() : "";
                messagingStyleInfo.mContentText = string;
                Uri dataUri = message.getDataUri();
                if (dataUri != null) {
                    Drawable drawableLoadImage = expandableNotificationRow.mImageResolver.loadImage(dataUri);
                    if (drawableLoadImage == null) {
                        Log.d("SubscreenNotificationInfo", notificationEntry.mKey + " : no drawable for " + dataUri);
                    } else {
                        messagingStyleInfo.mUriImage = drawableLoadImage;
                    }
                }
                Person senderPerson = message.getSenderPerson();
                String string2 = (senderPerson == null || senderPerson.getName() == null) ? (person == null || person.getName() == null) ? "" : person.getName().toString() : senderPerson.getName().toString();
                boolean zEquals = senderPerson == null || !(person == null || person.getName() == null || !string2.equals(person.getName().toString()));
                if ("com.viber.voip".equals(notificationEntry.mSbn.getPackageName())) {
                    zEquals = "Me".equals(string2);
                }
                messagingStyleInfo.mIsReply = zEquals;
                messagingStyleInfo.mSender = string2;
                messagingStyleInfo.mTimeStamp = message.getTimestamp();
                this.mMessageingStyleInfoArray.add(messagingStyleInfo);
                this.mContent = string;
                if (!"".equals(findConversationTitle(notificationEntry.mSbn).toString())) {
                    this.mTitle = findConversationTitle(notificationEntry.mSbn).toString();
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:190:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x04f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setItemsData(ExpandableNotificationRow expandableNotificationRow) throws Throwable {
        int iHSVToColor;
        CharSequence charSequence;
        Notification notification2;
        CharSequence charSequence2;
        ExpandableNotificationRow expandableNotificationRow2;
        int i;
        CharSequence[] charSequenceArray;
        int childCount;
        int i2;
        NotificationChildrenContainer notificationChildrenContainer;
        char c;
        Drawable drawableQueryContentUriInternal;
        this.mRow = expandableNotificationRow;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        this.mSbn = statusBarNotification;
        this.mKey = statusBarNotification.getKey();
        this.mPkg = this.mSbn.getPackageName();
        this.mSbn.isOngoing();
        Notification notification3 = this.mSbn.getNotification();
        Bundle bundle = notification3.extras;
        this.controller = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
        int i3 = 0;
        this.mNeedsOnePhoneIcon = false;
        this.mNeedsTwoPhoneIcon = false;
        boolean z = true;
        if ("MESSAGE_KT_TWO_PHONE_OPPOSITE_RECEIVED".equals(notification3.getGroup()) || "two_phone_missed_call_group".equals(notification3.getGroup())) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.controller.mDeviceModel;
            Integer numValueOf = subscreenDeviceModelParent != null ? Integer.valueOf(subscreenDeviceModelParent.currentUserId) : null;
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.controller.mDeviceModel;
            if (numValueOf == (subscreenDeviceModelParent2 != null ? Integer.valueOf(subscreenDeviceModelParent2.bModeUserId) : null)) {
                this.mNeedsOnePhoneIcon = true;
            } else {
                this.mNeedsTwoPhoneIcon = true;
            }
        }
        boolean zIsGroupSummary = notification3.isGroupSummary();
        this.mGroupSummary = zIsGroupSummary;
        if (zIsGroupSummary && expandableNotificationRow.mIsSummaryWithChildren) {
            this.mChildCount = expandableNotificationRow.mChildrenContainer.mUntruncatedChildCount;
        }
        String str = notification3.category;
        if (str != null) {
            this.mIsMissedCall = "missed_call".equals(str);
            this.mIsCall = "call".equals(notification3.category) && notification3.isStyle(Notification.CallStyle.class);
        }
        this.mRemoteinput = false;
        this.mHasSemanticCall = false;
        Notification.Action[] actionArr = notification3.actions;
        String str2 = "";
        if (actionArr != null) {
            int length = actionArr.length;
            int i4 = 0;
            while (i4 < length) {
                Notification.Action action = notification3.actions[i4];
                if (action != null) {
                    RemoteInput[] remoteInputs = action.getRemoteInputs();
                    if (remoteInputs != null) {
                        this.mRemoteinput = z;
                        for (RemoteInput remoteInput : remoteInputs) {
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
                i4++;
                z = true;
            }
        }
        Drawable drawableLoadDrawable = notification3.getSmallIcon().loadDrawable(this.mContext);
        this.mIcon = drawableLoadDrawable;
        int i5 = this.mSbn.getNotification().color;
        if (drawableLoadDrawable == null) {
            drawableLoadDrawable = null;
        } else {
            drawableLoadDrawable.mutate();
            if (drawableLoadDrawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawableLoadDrawable;
                int numberOfFrames = animationDrawable.getNumberOfFrames();
                Drawable[] drawableArr = new Drawable[numberOfFrames];
                for (int i6 = 0; i6 < numberOfFrames; i6++) {
                    drawableArr[i6] = animationDrawable.getFrame(i6);
                }
                drawableLoadDrawable = new LayerDrawable(drawableArr);
            }
            if (ContrastColorUtil.getInstance(this.mContext).isGrayscaleIcon(drawableLoadDrawable)) {
                int iRed = Color.red(i5);
                int iGreen = Color.green(i5);
                int iBlue = Color.blue(i5);
                if (Color.red(i5) == 0 && Color.green(i5) == 0 && Color.blue(i5) == 0) {
                    iHSVToColor = this.mAppPrimaryDefaultColor;
                } else {
                    float[] fArr = {0.0f, f, 0.0f};
                    Color.RGBToHSV(iRed, iGreen, iBlue, fArr);
                    float f = fArr[1] - 0.15f;
                    fArr[1] = Math.max(0.0f, Math.min(1.0f, f));
                    float f2 = fArr[2] + 0.2f;
                    fArr[2] = f2;
                    fArr[2] = Math.max(0.0f, Math.min(1.0f, f2));
                    iHSVToColor = Color.HSVToColor(255, fArr);
                    if (Math.round((255 / 255.0f) * Math.max(Color.red(iHSVToColor), Math.max(Color.green(iHSVToColor), Color.blue(iHSVToColor)))) > 204) {
                        float fRound = 204 / Math.round((r9 / 255.0f) * Math.max(r12, Math.max(r13, r6)));
                        iHSVToColor = Color.argb(Color.alpha(iHSVToColor), Math.round(Color.red(iHSVToColor) * fRound), Math.round(Color.green(iHSVToColor) * fRound), Math.round(fRound * Color.blue(iHSVToColor)));
                    }
                }
                drawableLoadDrawable.setColorFilter(iHSVToColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
        this.mIcon = drawableLoadDrawable;
        this.mConversationIcon = (Icon) bundle.getParcelable("android.conversationIcon");
        this.mLargeIcon = (Icon) bundle.getParcelable("android.largeIcon");
        boolean zEquals = Notification.MessagingStyle.class.equals(notification3.getNotificationStyle());
        this.mIsMessagingStyle = zEquals;
        this.mUnreadMessageCnt = 0;
        if (zEquals) {
            ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) this.controller.conversationNotificationManager.states.get(expandableNotificationRow.mEntry.mKey);
            this.mUnreadMessageCnt = conversationState != null ? conversationState.unreadCount : 1;
            this.mIsGroupConversation = bundle.getBoolean("android.isGroupConversation");
            charSequence = findConversationTitle(this.mSbn);
        } else {
            charSequence = bundle.getCharSequence("android.title");
        }
        if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY || !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationHistoryEnabled()) {
            notification2 = notification3;
            charSequence2 = charSequence;
            if (zEquals) {
                makeConversation(expandableNotificationRow);
            }
        } else if (this.mRemoteinput || zEquals) {
            List histories = getHistories(50);
            boolean z2 = this.controller.mDeviceModel.isLargeSubscreen() && ((ArrayList) histories).size() > 0;
            ArrayList arrayList = (ArrayList) histories;
            if (arrayList.size() > 0) {
                this.mIsMessagingStyle |= this.mRemoteinput;
            }
            if (z2) {
                int size = arrayList.size();
                int i7 = 0;
                int i8 = 0;
                while (true) {
                    if (i7 >= size) {
                        notification2 = notification3;
                        charSequence2 = charSequence;
                        break;
                    }
                    int i9 = i7 + 1;
                    Bundle bundle2 = (Bundle) arrayList.get(i7);
                    int i10 = bundle2.getInt("type", i3);
                    String string = bundle2.getString(UniversalCredentialUtil.AGENT_TITLE, str2);
                    String string2 = bundle2.getString("text", str2);
                    String string3 = bundle2.getString("uri", str2);
                    String string4 = bundle2.getString("sbnKey", str2);
                    charSequence2 = charSequence;
                    String str3 = str2;
                    int i11 = size;
                    notification2 = notification3;
                    long j = bundle2.getLong("postedTime", 0L);
                    long j2 = bundle2.getLong("when", 0L);
                    ArrayList arrayList2 = arrayList;
                    boolean z3 = bundle2.getBoolean("isChecked", false);
                    if (!this.mKey.equals(string4)) {
                        c = 2;
                    } else {
                        if (i8 >= 25) {
                            break;
                        }
                        boolean z4 = i10 == 1;
                        MessagingStyleInfo messagingStyleInfo = new MessagingStyleInfo();
                        messagingStyleInfo.mContentText = string2;
                        if (this.mContent == null) {
                            this.mContent = string2;
                        }
                        messagingStyleInfo.mSender = string;
                        messagingStyleInfo.mTimeStamp = j2;
                        messagingStyleInfo.mPostedTime = j;
                        messagingStyleInfo.mIsReply = z4;
                        messagingStyleInfo.mIsChecked = z3;
                        c = 2;
                        if (i10 == 2) {
                            try {
                                drawableQueryContentUriInternal = queryContentUriInternal(this.mContext, string3);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                            if (drawableQueryContentUriInternal != null) {
                                messagingStyleInfo.mUriImage = drawableQueryContentUriInternal;
                                this.mMessageingStyleInfoArray.add(messagingStyleInfo);
                                i8++;
                            }
                        } else {
                            this.mMessageingStyleInfoArray.add(messagingStyleInfo);
                            i8++;
                        }
                    }
                    i7 = i9;
                    charSequence = charSequence2;
                    str2 = str3;
                    size = i11;
                    notification3 = notification2;
                    arrayList = arrayList2;
                    i3 = 0;
                }
                this.mMessageingStyleInfoArray.sort(new Comparator(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return (int) (((MessagingStyleInfo) obj).mPostedTime - ((MessagingStyleInfo) obj2).mPostedTime);
                    }
                });
            } else {
                notification2 = notification3;
                charSequence2 = charSequence;
                makeConversation(expandableNotificationRow);
            }
        } else {
            notification2 = notification3;
            charSequence2 = charSequence;
        }
        String string5 = this.mTitle;
        if (string5 == null) {
            string5 = charSequence2 == null ? null : charSequence2.toString();
        }
        this.mTitle = string5;
        CharSequence charSequence3 = bundle.getCharSequence("android.text");
        if (this.mContent != null) {
            expandableNotificationRow2 = expandableNotificationRow;
            if (((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.isKnoxSecurity(expandableNotificationRow2.mEntry) && expandableNotificationRow2.mEntry.mUserPublic && !expandableNotificationRow2.needsRedaction()) {
                this.mContent = null;
            }
        } else {
            expandableNotificationRow2 = expandableNotificationRow;
        }
        String string6 = this.mContent;
        if (string6 == null) {
            string6 = charSequence3 == null ? null : charSequence3.toString();
        }
        this.mContent = string6;
        CharSequence charSequence4 = bundle.getCharSequence("android.bigText");
        this.mBigText = charSequence4 == null ? null : charSequence4.toString();
        CharSequence charSequence5 = bundle.getCharSequence("android.title.big");
        this.mBigTitle = charSequence5 == null ? null : charSequence5.toString();
        CharSequence charSequence6 = bundle.getCharSequence("android.subText");
        if (charSequence6 != null) {
            charSequence6.toString();
        }
        this.mBitmap = (Bitmap) bundle.getParcelable("android.picture");
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            if (!this.mRow.isInsignificantSummary() || (notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer) == null || ((ArrayList) notificationChildrenContainer.mAttachedChildren).isEmpty()) {
                i = 0;
            } else {
                i = 0;
                try {
                    this.mPkg = ((ExpandableNotificationRow) ((ArrayList) expandableNotificationRow2.mChildrenContainer.mAttachedChildren).get(0)).mEntry.mSbn.getPackageName();
                } catch (PackageManager.NameNotFoundException e2) {
                    e = e2;
                    e.printStackTrace();
                    charSequenceArray = bundle.getCharSequenceArray("android.textLines");
                    if (charSequenceArray != null) {
                    }
                    Notification notification4 = notification2;
                    this.mContentIntent = notification4.contentIntent;
                    this.mContentView = this.mSbn.getNotification().contentView;
                    this.mWhen = notification4.when;
                    this.mShowWhen = bundle.getBoolean("android.showWhen");
                    this.mShowSmallIcon = bundle.getBoolean("android.showSmallIcon");
                    this.mKnoxBadgeDrawable = null;
                    childCount = this.mRow.getChildCount();
                    while (i2 < childCount) {
                    }
                }
            }
            CharSequence charSequence7 = bundle.getCharSequence("android.substName");
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.mPkg, 4202624);
            this.mAppPrimaryDefaultColor = this.mContext.getColor(R.color.subscreen_notification_primary_default_for_common);
            this.mAppIcon = packageManager.semGetApplicationIconForIconTray(applicationInfo, 48);
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_full_popup_icon_size_b5);
            this.mAppIcon = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).resizeDrawable(this.mAppIcon, dimensionPixelSize, dimensionPixelSize);
            this.mUseSmallIcon = applicationInfo.icon == 0 ? 1 : i;
            String string7 = charSequence7 == null ? packageManager.getApplicationLabel(applicationInfo).toString() : charSequence7.toString();
            this.mAppName = string7;
            this.mAppName = string7.replace("\n", " ");
        } catch (PackageManager.NameNotFoundException e3) {
            e = e3;
            i = 0;
        }
        charSequenceArray = bundle.getCharSequenceArray("android.textLines");
        if (charSequenceArray != null) {
            for (int i12 = i; i12 < Math.min(this.mInBox.length, charSequenceArray.length); i12++) {
                String[] strArr = this.mInBox;
                CharSequence charSequence8 = charSequenceArray[i12];
                strArr[i12] = charSequence8 == null ? null : charSequence8.toString();
            }
        }
        Notification notification42 = notification2;
        this.mContentIntent = notification42.contentIntent;
        this.mContentView = this.mSbn.getNotification().contentView;
        this.mWhen = notification42.when;
        this.mShowWhen = bundle.getBoolean("android.showWhen");
        this.mShowSmallIcon = bundle.getBoolean("android.showSmallIcon");
        this.mKnoxBadgeDrawable = null;
        childCount = this.mRow.getChildCount();
        for (i2 = i; i2 < childCount; i2++) {
            View childAt = this.mRow.getChildAt(i2);
            if (childAt instanceof NotificationContentView) {
                NotificationContentView notificationContentView = (NotificationContentView) childAt;
                View view = notificationContentView.mExpandedChild;
                if (view == null) {
                    view = notificationContentView.mContractedChild;
                }
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(android.R.id.smallIcon);
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
        boolean zEquals = "android".equals(this.mPkg);
        boolean zEquals2 = "com.android.systemui".equals(this.mPkg);
        boolean z = zEquals || zEquals2 || this.mUseSmallIcon || this.mShowSmallIcon;
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mKey);
            sb.append(": use small icon. androidPkg = ");
            sb.append(zEquals);
            sb.append(", systemuiPkg = ");
            sb.append(zEquals2);
            sb.append(", mUseSmallIcon = ");
            sb.append(this.mUseSmallIcon);
            sb.append(", mShowSmallIcon = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mShowSmallIcon, "SubscreenNotificationInfo");
        }
        return z;
    }

    public SubscreenNotificationInfo(Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
    }
}
