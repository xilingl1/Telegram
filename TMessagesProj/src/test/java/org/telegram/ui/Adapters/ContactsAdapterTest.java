package org.telegram.ui.Adapters;


import android.content.Context;
import android.util.SparseArray;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertArrayEquals;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Components.RecyclerListView;

import java.util.ArrayList;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;



//@RunWith(MockitoJUnitRunner.class)
public class ContactsAdapterTest {

    @Spy
    private ContactsAdapter contactsAdapter;

    private int currentAccount = UserConfig.selectedAccount;
    private Context mContext;
    private int onlyUsers;
    private boolean needPhonebook;
    private SparseArray<User> ignoreUsers;
    private SparseArray<?> checkedMap;
    private ArrayList<TLRPC.TL_contact> onlineContacts;
    private ArrayList<TLRPC.TL_contact> expected;
    private boolean scrolling;
    private boolean isAdmin;
    private int sortType;
    private boolean isChannel;
    private boolean disableSections;
    private boolean hasGps;

    @Spy
    private TLRPC.TL_contact user1 = new TLRPC.TL_contact();

    @Spy
    private TLRPC.TL_contact user2 = new TLRPC.TL_contact();

    @Spy
    private TLRPC.TL_contact user3 = new TLRPC.TL_contact();

    @Before
    public void setUp() {
        //  Mocks are being created.
        mContext = mock(Context.class);
        onlyUsers = 1;
        needPhonebook = true;
        ignoreUsers = new SparseArray<>();
        isAdmin = false;
        isChannel = false;
        hasGps = false;

        contactsAdapter = new ContactsAdapter(mContext, onlyUsers, needPhonebook, ignoreUsers, 0, hasGps);

        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testWhenThereIsZeroContactsAndSortTypeIsOne() {
        onlineContacts = new ArrayList<>();
        doNothing().when(contactsAdapter).notifyDataSetChanged();
        contactsAdapter.setSortType(1);

        expected = new ArrayList<>();

        assertArrayEquals(onlineContacts.toArray(), expected.toArray());
    }


    @Test
    public void testWhenThereIsThreeContactsAndSortTypeIsOne() {
        onlineContacts = new ArrayList<>();
        onlineContacts.add(user1);
        onlineContacts.add(user2);
        onlineContacts.add(user3);

        doNothing().when(contactsAdapter).notifyDataSetChanged();
        contactsAdapter.setSortType(1);

        expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);

        assertArrayEquals(onlineContacts.toArray(), expected.toArray());
    }


    @Test
    public void testWhenThereIsZeroContactsAndSortTypeIsTwo() {
        onlineContacts = new ArrayList<>();
        onlineContacts.add(user1);
        onlineContacts.add(user2);
        onlineContacts.add(user3);

//        when(user1.user_id).thenReturn(1);
//        when(user2.user_id).thenReturn(2);
//        when(user3.user_id).thenReturn(3);

        when(ContactsController.getInstance(currentAccount).contacts).thenReturn(onlineContacts);
        doNothing().when(contactsAdapter).notifyDataSetChanged();
        contactsAdapter.setSortType(2);

        ArrayList<TLRPC.TL_contact> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);

        assertArrayEquals(onlineContacts.toArray(), expected.toArray());
    }
//
//    @Test
//    public void testWhenThereIsThreeContactsAndSortTypeIsTwo() {
//        onlineContacts = new ArrayList<>();
//        onlineContacts.add(user1);
//        onlineContacts.add(user2);
//        onlineContacts.add(user3);
//
////        when(user1.user_id).thenReturn(1);
////        when(user2.user_id).thenReturn(2);
////        when(user3.user_id).thenReturn(3);
//
//        doNothing().when(contactsAdapter).notifyDataSetChanged();
//        contactsAdapter.setSortType(2);
//
//        ArrayList<TLRPC.TL_contact> expected = new ArrayList<>();
//        expected.add(user1);
//        expected.add(user2);
//        expected.add(user3);
//
//        assertArrayEquals(onlineContacts.toArray(), expected.toArray());
//    }
}