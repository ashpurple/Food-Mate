package com.example.foodmate;

import android.view.Menu;
import android.view.MenuItem;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class HomeTest {

    private BottomNavigationView mBottomNavigation;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class);


    private static final int[] MENU_CONTENT_ITEM_IDS = {
            R.id.nav_recruiting, R.id.nav_recruited, R.id.nav_settings
    };

    @Test
    @SmallTest
    public void testBasics() {
        // Check the contents of the Menu object
        final Menu menu = mBottomNavigation.getMenu();
        assertNotNull("Menu should not be null", menu);
        assertEquals("Should have matching number of items", MENU_CONTENT_ITEM_IDS.length, menu.size());
        for (int i = 0; i < MENU_CONTENT_ITEM_IDS.length; i++) {
            final MenuItem currItem = menu.getItem(i);
            assertEquals("ID for Item #" + i, MENU_CONTENT_ITEM_IDS[i], currItem.getItemId());
        }
    }
}
