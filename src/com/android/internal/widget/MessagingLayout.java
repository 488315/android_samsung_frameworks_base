package com.android.internal.widget;

import android.app.Flags;
import android.app.Notification;
import android.app.Person;
import android.app.RemoteInputHistoryItem;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.widget.MessagingLinearLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class MessagingLayout extends FrameLayout implements ImageMessageConsumer, IMessagingLayout {
    private static final int MAX_SUMMARIZATION_LINES = 3;
    private final ArrayList<MessagingGroup> mAddedGroups;
    private Icon mAvatarReplacement;
    private CharSequence mConversationTitle;
    private final ArrayList<MessagingGroup> mGroups;
    private List<MessagingMessage> mHistoricMessages;
    private MessagingLinearLayout mImageMessageContainer;
    private ImageResolver mImageResolver;
    private boolean mIsCollapsed;
    private boolean mIsOneToOne;
    private int mLayoutColor;
    private int mMessageTextColor;
    private List<MessagingMessage> mMessages;
    private Rect mMessagingClipRect;
    private MessagingLinearLayout mMessagingLinearLayout;
    private CharSequence mNameReplacement;
    private final PeopleHelper mPeopleHelper;
    private boolean mPrecomputedTextEnabled;
    private ImageView mRightIconView;
    private int mSenderTextColor;
    private boolean mShowHistoricMessages;
    private CharSequence mSummarizedContent;
    private final ArrayList<MessagingLinearLayout.MessagingChild> mToRecycle;
    private Person mUser;
    public static final Interpolator LINEAR_OUT_SLOW_IN = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator FAST_OUT_LINEAR_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public static final View.OnLayoutChangeListener MESSAGING_PROPERTY_ANIMATOR = new MessagingPropertyAnimator();

    static /* synthetic */ void lambda$setAvatarReplacementAsync$0() {
    }

    static /* synthetic */ void lambda$setConversationTitleAsync$3() {
    }

    static /* synthetic */ void lambda$setIsCollapsedAsync$2() {
    }

    static /* synthetic */ void lambda$setIsOneToOneAsync$8() {
    }

    static /* synthetic */ void lambda$setLayoutColorAsync$7() {
    }

    static /* synthetic */ void lambda$setMessageTextColorAsync$10() {
    }

    static /* synthetic */ void lambda$setNameReplacementAsync$1() {
    }

    static /* synthetic */ void lambda$setSenderTextColorAsync$9() {
    }

    @RemotableViewMethod
    public void setLargeIcon(Icon icon) {
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
    }

    public MessagingLayout(Context context) {
        super(context);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public MessagingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public MessagingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public MessagingLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mMessagingLinearLayout = (MessagingLinearLayout) findViewById(R.id.notification_messaging);
        this.mImageMessageContainer = (MessagingLinearLayout) findViewById(R.id.conversation_image_message_container);
        this.mRightIconView = (ImageView) findViewById(R.id.right_icon);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int max = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMessagingClipRect = new Rect(0, 0, max, max);
        setMessagingClippingDisabled(false);
    }

    @RemotableViewMethod(asyncImpl = "setAvatarReplacementAsync")
    public void setAvatarReplacement(Icon icon) {
        this.mAvatarReplacement = icon;
    }

    public Runnable setAvatarReplacementAsync(Icon icon) {
        this.mAvatarReplacement = icon;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setAvatarReplacementAsync$0();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setNameReplacementAsync")
    public void setNameReplacement(CharSequence charSequence) {
        this.mNameReplacement = charSequence;
    }

    public Runnable setNameReplacementAsync(CharSequence charSequence) {
        this.mNameReplacement = charSequence;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setNameReplacementAsync$1();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setIsCollapsedAsync")
    public void setIsCollapsed(boolean z) {
        this.mIsCollapsed = z;
        if (z) {
            this.mMessagingLinearLayout.setMaxDisplayedLines(Flags.nmCollapsedLines() ? 2 : 1);
        }
    }

    public Runnable setIsCollapsedAsync(boolean z) {
        this.mIsCollapsed = z;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setIsCollapsedAsync$2();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setConversationTitleAsync")
    public void setConversationTitle(CharSequence charSequence) {
        this.mConversationTitle = charSequence;
    }

    public Runnable setConversationTitleAsync(CharSequence charSequence) {
        this.mConversationTitle = charSequence;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setConversationTitleAsync$3();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$4(Bundle bundle) {
        bind(parseMessagingData(bundle, false));
    }

    private MessagingData parseMessagingData(Bundle bundle, boolean z) {
        List<MessagingMessage> createMessages;
        List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_MESSAGES));
        List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES));
        setUser((Person) bundle.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class));
        addRemoteInputHistoryToMessages(messagesFromBundleArray, (RemoteInputHistoryItem[]) bundle.getParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class));
        Person person = (Person) bundle.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
        boolean z2 = bundle.getBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, false);
        List<MessagingMessage> createMessages2 = createMessages(messagesFromBundleArray2, true, z);
        CharSequence charSequence = bundle.getCharSequence(Notification.EXTRA_SUMMARIZED_CONTENT);
        this.mSummarizedContent = charSequence;
        if (!TextUtils.isEmpty(charSequence) && this.mIsCollapsed) {
            this.mMessagingLinearLayout.setMaxDisplayedLines(3);
            createMessages = createMessages(List.of(new Notification.MessagingStyle.Message(this.mSummarizedContent, 0L, "")), false, z);
        } else {
            createMessages = createMessages(messagesFromBundleArray, false, z);
        }
        List<MessagingMessage> list = createMessages;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        findGroups(createMessages2, list, arrayList, arrayList2);
        return new MessagingData(person, z2, createMessages2, list, arrayList, arrayList2, this.mSummarizedContent);
    }

    public Runnable setDataAsync(final Bundle bundle) {
        if (!this.mPrecomputedTextEnabled) {
            return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MessagingLayout.this.lambda$setDataAsync$4(bundle);
                }
            };
        }
        final MessagingData parseMessagingData = parseMessagingData(bundle, true);
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.this.lambda$setDataAsync$5(parseMessagingData);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDataAsync$5(MessagingData messagingData) {
        finalizeInflate(messagingData.getHistoricMessagingMessages());
        finalizeInflate(messagingData.getNewMessagingMessages());
        bind(messagingData);
    }

    public void setPrecomputedTextEnabled(boolean z) {
        this.mPrecomputedTextEnabled = z;
    }

    private void finalizeInflate(List<MessagingMessage> list) {
        Iterator<MessagingMessage> it = list.iterator();
        while (it.hasNext()) {
            it.next().finalizeInflate();
        }
    }

    @Override // com.android.internal.widget.ImageMessageConsumer
    public void setImageResolver(ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    private void addRemoteInputHistoryToMessages(List<Notification.MessagingStyle.Message> list, RemoteInputHistoryItem[] remoteInputHistoryItemArr) {
        if (remoteInputHistoryItemArr == null || remoteInputHistoryItemArr.length == 0) {
            return;
        }
        for (int length = remoteInputHistoryItemArr.length - 1; length >= 0; length--) {
            RemoteInputHistoryItem remoteInputHistoryItem = remoteInputHistoryItemArr[length];
            Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(remoteInputHistoryItem.getText(), 0L, null, true);
            if (remoteInputHistoryItem.getUri() != null) {
                message.setData(remoteInputHistoryItem.getMimeType(), remoteInputHistoryItem.getUri());
            }
            list.add(message);
        }
    }

    private void bind(MessagingData messagingData) {
        setUser(messagingData.getUser());
        ArrayList<MessagingGroup> arrayList = new ArrayList<>(this.mGroups);
        createGroupViews(messagingData.getGroups(), messagingData.getSenders(), messagingData.getShowSpinner());
        removeGroups(arrayList);
        Iterator<MessagingMessage> it = this.mMessages.iterator();
        while (it.hasNext()) {
            it.next().removeMessage(this.mToRecycle);
        }
        Iterator<MessagingMessage> it2 = this.mHistoricMessages.iterator();
        while (it2.hasNext()) {
            it2.next().removeMessage(this.mToRecycle);
        }
        this.mMessages = messagingData.getNewMessagingMessages();
        this.mHistoricMessages = messagingData.getHistoricMessagingMessages();
        updateHistoricMessageVisibility();
        updateTitleAndNamesDisplay();
        this.mPeopleHelper.maybeHideFirstSenderName(this.mGroups, this.mIsOneToOne, this.mConversationTitle);
        updateImageMessages();
        Iterator<MessagingLinearLayout.MessagingChild> it3 = this.mToRecycle.iterator();
        while (it3.hasNext()) {
            it3.next().recycle();
        }
        this.mToRecycle.clear();
    }

    private void updateImageMessages() {
        ImageView imageView;
        if (this.mImageMessageContainer == null) {
            return;
        }
        View newImageMessage = getNewImageMessage();
        View childAt = this.mImageMessageContainer.getChildAt(0);
        if (childAt != newImageMessage) {
            this.mImageMessageContainer.removeView(childAt);
            if (newImageMessage != null) {
                this.mImageMessageContainer.addView(newImageMessage);
            }
        }
        this.mImageMessageContainer.setVisibility(newImageMessage == null ? 8 : 0);
        if (newImageMessage == null || (imageView = this.mRightIconView) == null || imageView.getDrawable() == null) {
            return;
        }
        this.mRightIconView.lambda$setImageURIAsync$0(null);
        this.mRightIconView.setVisibility(8);
    }

    private View getNewImageMessage() {
        MessagingImageMessage isolatedMessage;
        if (!this.mIsCollapsed || this.mGroups.isEmpty() || (isolatedMessage = ((MessagingGroup) this.mGroups.getLast()).getIsolatedMessage()) == null) {
            return null;
        }
        return isolatedMessage.getView();
    }

    private void removeGroups(ArrayList<MessagingGroup> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            final MessagingGroup messagingGroup = arrayList.get(i);
            if (!this.mGroups.contains(messagingGroup)) {
                List<MessagingMessage> messages = messagingGroup.getMessages();
                boolean isShown = messagingGroup.isShown();
                this.mMessagingLinearLayout.removeView(messagingGroup);
                if (isShown && !MessagingLinearLayout.isGone(messagingGroup)) {
                    this.mMessagingLinearLayout.addTransientView(messagingGroup, 0);
                    messagingGroup.removeGroupAnimated(new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            MessagingLayout.this.lambda$removeGroups$6(messagingGroup);
                        }
                    });
                } else {
                    this.mToRecycle.add(messagingGroup);
                }
                this.mMessages.removeAll(messages);
                this.mHistoricMessages.removeAll(messages);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeGroups$6(MessagingGroup messagingGroup) {
        this.mMessagingLinearLayout.removeTransientView(messagingGroup);
        messagingGroup.recycle();
    }

    private void updateTitleAndNamesDisplay() {
        Icon avatarSymbolIfMatching;
        Map<CharSequence, String> mapUniqueNamesToPrefix = this.mPeopleHelper.mapUniqueNamesToPrefix(this.mGroups);
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < this.mGroups.size(); i++) {
            MessagingGroup messagingGroup = this.mGroups.get(i);
            boolean z = messagingGroup.getSender() == this.mUser;
            CharSequence senderName = messagingGroup.getSenderName();
            if (messagingGroup.needsGeneratedAvatar() && !TextUtils.isEmpty(senderName) && ((!this.mIsOneToOne || this.mAvatarReplacement == null || z) && (avatarSymbolIfMatching = messagingGroup.getAvatarSymbolIfMatching(senderName, mapUniqueNamesToPrefix.get(senderName), this.mLayoutColor)) != null)) {
                arrayMap.put(senderName, avatarSymbolIfMatching);
            }
        }
        for (int i2 = 0; i2 < this.mGroups.size(); i2++) {
            MessagingGroup messagingGroup2 = this.mGroups.get(i2);
            CharSequence senderName2 = messagingGroup2.getSenderName();
            if (messagingGroup2.needsGeneratedAvatar() && !TextUtils.isEmpty(senderName2)) {
                if (this.mIsOneToOne && this.mAvatarReplacement != null && messagingGroup2.getSender() != this.mUser) {
                    messagingGroup2.setAvatar(this.mAvatarReplacement);
                } else {
                    Icon icon = (Icon) arrayMap.get(senderName2);
                    if (icon == null) {
                        icon = createAvatarSymbol(senderName2, mapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                        arrayMap.put(senderName2, icon);
                    }
                    messagingGroup2.setCreatedAvatar(icon, senderName2, mapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                }
            }
        }
    }

    public Icon createAvatarSymbol(CharSequence charSequence, String str, int i) {
        return this.mPeopleHelper.createAvatarSymbol(charSequence, str, i);
    }

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    public void setLayoutColor(int i) {
        this.mLayoutColor = i;
    }

    public Runnable setLayoutColorAsync(int i) {
        this.mLayoutColor = i;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setLayoutColorAsync$7();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setIsOneToOneAsync")
    public void setIsOneToOne(boolean z) {
        this.mIsOneToOne = z;
    }

    public Runnable setIsOneToOneAsync(boolean z) {
        this.mIsOneToOne = z;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setIsOneToOneAsync$8();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setSenderTextColorAsync")
    public void setSenderTextColor(int i) {
        this.mSenderTextColor = i;
    }

    public Runnable setSenderTextColorAsync(int i) {
        this.mSenderTextColor = i;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setSenderTextColorAsync$9();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setMessageTextColorAsync")
    public void setMessageTextColor(int i) {
        this.mMessageTextColor = i;
    }

    public Runnable setMessageTextColorAsync(int i) {
        this.mMessageTextColor = i;
        return new Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                MessagingLayout.lambda$setMessageTextColorAsync$10();
            }
        };
    }

    public void setUser(Person person) {
        this.mUser = person;
        if (person.getIcon() == null) {
            Icon createWithResource = Icon.createWithResource(getContext(), R.drawable.messaging_user);
            createWithResource.setTint(this.mLayoutColor);
            this.mUser = this.mUser.toBuilder().setIcon(createWithResource).build();
        }
    }

    private void createGroupViews(List<List<MessagingMessage>> list, List<Person> list2, boolean z) {
        CharSequence charSequence;
        this.mGroups.clear();
        int i = 0;
        while (i < list.size()) {
            List<MessagingMessage> list3 = list.get(i);
            CharSequence charSequence2 = null;
            MessagingGroup messagingGroup = null;
            for (int size = list3.size() - 1; size >= 0; size--) {
                messagingGroup = list3.get(size).getGroup();
                if (messagingGroup != null) {
                    break;
                }
            }
            if (messagingGroup == null) {
                messagingGroup = MessagingGroup.createGroup(this.mMessagingLinearLayout);
                this.mAddedGroups.add(messagingGroup);
            } else if (messagingGroup.getParent() != this.mMessagingLinearLayout) {
                throw new IllegalStateException("group parent was " + messagingGroup.getParent() + " but expected " + this.mMessagingLinearLayout);
            }
            messagingGroup.setImageDisplayLocation(this.mIsCollapsed ? 2 : 0);
            messagingGroup.setIsInConversation(false);
            messagingGroup.setLayoutColor(this.mLayoutColor);
            messagingGroup.setTextColors(this.mSenderTextColor, this.mMessageTextColor);
            Person person = list2.get(i);
            if (person != this.mUser && (charSequence = this.mNameReplacement) != null) {
                charSequence2 = charSequence;
            }
            messagingGroup.setSingleLine(this.mIsCollapsed && !Flags.nmCollapsedLines() && TextUtils.isEmpty(this.mSummarizedContent));
            messagingGroup.setShowingAvatar(!this.mIsCollapsed);
            messagingGroup.setIsCollapsed(this.mIsCollapsed);
            messagingGroup.setSender(person, charSequence2);
            messagingGroup.setSending(i == list.size() - 1 && z);
            this.mGroups.add(messagingGroup);
            if (this.mMessagingLinearLayout.indexOfChild(messagingGroup) != i) {
                this.mMessagingLinearLayout.removeView(messagingGroup);
                this.mMessagingLinearLayout.addView(messagingGroup, i);
            }
            messagingGroup.setMessages(list3);
            i++;
        }
        if (android.widget.flags.Flags.dropNonExistingMessages()) {
            this.mAddedGroups.removeIf(new Predicate() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$createGroupViews$11;
                    lambda$createGroupViews$11 = MessagingLayout.this.lambda$createGroupViews$11((MessagingGroup) obj);
                    return lambda$createGroupViews$11;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createGroupViews$11(MessagingGroup messagingGroup) {
        return !this.mGroups.contains(messagingGroup);
    }

    private void findGroups(List<MessagingMessage> list, List<MessagingMessage> list2, List<List<MessagingMessage>> list3, List<Person> list4) {
        MessagingMessage messagingMessage;
        CharSequence name;
        int size = list.size();
        ArrayList arrayList = null;
        CharSequence charSequence = null;
        for (int i = 0; i < list2.size() + size; i++) {
            if (i < size) {
                messagingMessage = list.get(i);
            } else {
                messagingMessage = list2.get(i - size);
            }
            boolean z = arrayList == null;
            Person senderPerson = messagingMessage.getMessage() == null ? null : messagingMessage.getMessage().getSenderPerson();
            if (senderPerson == null) {
                name = null;
            } else {
                name = senderPerson.getKey() == null ? senderPerson.getName() : senderPerson.getKey();
            }
            if ((true ^ TextUtils.equals(name, charSequence)) | z) {
                arrayList = new ArrayList();
                list3.add(arrayList);
                if (senderPerson == null) {
                    senderPerson = this.mUser;
                }
                list4.add(senderPerson);
                charSequence = name;
            }
            arrayList.add(messagingMessage);
        }
    }

    private List<MessagingMessage> createMessages(List<Notification.MessagingStyle.Message> list, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Notification.MessagingStyle.Message message = list.get(i);
            MessagingMessage findAndRemoveMatchingMessage = findAndRemoveMatchingMessage(message);
            if (findAndRemoveMatchingMessage == null) {
                findAndRemoveMatchingMessage = MessagingMessage.createMessage(this, message, this.mImageResolver, z2);
            }
            findAndRemoveMatchingMessage.setIsHistoric(z);
            arrayList.add(findAndRemoveMatchingMessage);
        }
        return arrayList;
    }

    private MessagingMessage findAndRemoveMatchingMessage(Notification.MessagingStyle.Message message) {
        for (int i = 0; i < this.mMessages.size(); i++) {
            MessagingMessage messagingMessage = this.mMessages.get(i);
            if (messagingMessage.sameAs(message)) {
                this.mMessages.remove(i);
                return messagingMessage;
            }
        }
        for (int i2 = 0; i2 < this.mHistoricMessages.size(); i2++) {
            MessagingMessage messagingMessage2 = this.mHistoricMessages.get(i2);
            if (messagingMessage2.sameAs(message)) {
                this.mHistoricMessages.remove(i2);
                return messagingMessage2;
            }
        }
        return null;
    }

    public void showHistoricMessages(boolean z) {
        this.mShowHistoricMessages = z;
        updateHistoricMessageVisibility();
    }

    private void updateHistoricMessageVisibility() {
        int size = this.mHistoricMessages.size();
        int i = 0;
        while (true) {
            int i2 = 8;
            if (i >= size) {
                break;
            }
            MessagingMessage messagingMessage = this.mHistoricMessages.get(i);
            if (this.mShowHistoricMessages) {
                i2 = 0;
            }
            messagingMessage.setVisibility(i2);
            i++;
        }
        int size2 = this.mGroups.size();
        for (int i3 = 0; i3 < size2; i3++) {
            MessagingGroup messagingGroup = this.mGroups.get(i3);
            List<MessagingMessage> messages = messagingGroup.getMessages();
            int size3 = messages.size();
            int i4 = 0;
            for (int i5 = 0; i5 < size3; i5++) {
                if (messages.get(i5).getVisibility() != 8) {
                    i4++;
                }
            }
            if (i4 > 0 && messagingGroup.getVisibility() == 8) {
                messagingGroup.setVisibility(0);
            } else if (i4 == 0 && messagingGroup.getVisibility() != 8) {
                messagingGroup.setVisibility(8);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mAddedGroups.isEmpty()) {
            return;
        }
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.widget.MessagingLayout.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                Iterator it = MessagingLayout.this.mAddedGroups.iterator();
                while (it.hasNext()) {
                    MessagingGroup messagingGroup = (MessagingGroup) it.next();
                    if (messagingGroup.isShown()) {
                        MessagingPropertyAnimator.fadeIn(messagingGroup.getAvatar());
                        MessagingPropertyAnimator.fadeIn(messagingGroup.getSenderView());
                        MessagingPropertyAnimator.startLocalTranslationFrom(messagingGroup, messagingGroup.getHeight(), MessagingLayout.LINEAR_OUT_SLOW_IN);
                    }
                }
                MessagingLayout.this.mAddedGroups.clear();
                MessagingLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public MessagingLinearLayout getMessagingLinearLayout() {
        return this.mMessagingLinearLayout;
    }

    public ViewGroup getImageMessageContainer() {
        return this.mImageMessageContainer;
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public ArrayList<MessagingGroup> getMessagingGroups() {
        return this.mGroups;
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public void setMessagingClippingDisabled(boolean z) {
        this.mMessagingLinearLayout.setClipBounds(z ? null : this.mMessagingClipRect);
    }
}
