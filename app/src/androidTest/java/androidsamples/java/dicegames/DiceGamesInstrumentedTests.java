package androidsamples.java.dicegames;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.matcher.ViewMatchers;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DiceGamesInstrumentedTests {

    private static final String PREF_KEY_BALANCE = "PREFS_KEY_BALANCE";
    private static final String SHARED_PREF_FILE = "androidsamples.java.dicegames.SHARED_PREF_FILE";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_KEY_BALANCE, 30);
        editor.apply();

        DiceGamesPrefs.setBalance(context, 30);

        activityRule.getScenario().onActivity(activity -> {
            if (activity instanceof MainActivity) {
                GamesViewModel viewModel = new ViewModelProvider(activity).get(GamesViewModel.class);
                viewModel.setBalance(30);
            }
        });
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("androidsamples.java.dicegames", appContext.getPackageName());
    }

    @Test
    public void testInitialBalance() {
        activityRule.getScenario().recreate();
        onView(withId(R.id.txt_balance)).check(matches(withText("30")));
    }

    @Test
    public void testWalletDieRollUpdatesBalance() {
        activityRule.getScenario().recreate();
        int initialBalance = Integer.parseInt(getTextFromView(R.id.txt_balance));
        onView(withId(R.id.btn_die)).perform(click());
        int newBalance = Integer.parseInt(getTextFromView(R.id.txt_balance));
        assert(newBalance >= initialBalance);
        assert(newBalance <= initialBalance + 5);
    }

    @Test
    public void testNavigationToGamesFragment() {
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.btn_go)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationBetweenFragments() {
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.btn_go)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_info)).perform(click());
        onView(withId(R.id.txt_info)).check(matches(isDisplayed()));
    }

    @Test
    public void testPersistenceAcrossFragments() {
        onView(withId(R.id.btn_die)).perform(click());
        String walletBalance = getTextFromView(R.id.txt_balance);
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.txt_coins)).check(matches(withText(walletBalance)));
    }


    @Test
    public void testInvalidWager() {
        activityRule.getScenario().recreate();
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.et_wager)).perform(typeText("40"), closeSoftKeyboard());
        onView(withId(R.id.rb_two_alike)).perform(click());
        onView(withId(R.id.btn_go)).perform(click());
        onView(withId(R.id.txt_coins)).check(matches(withText("30"))); // Balance should not change
    }

    @Test
    public void testMaximumWager() {
        activityRule.getScenario().recreate();
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.et_wager)).perform(typeText("15"), closeSoftKeyboard());
        onView(withId(R.id.rb_two_alike)).perform(click());
        onView(withId(R.id.btn_go)).perform(click());
        int newBalance = Integer.parseInt(getTextFromView(R.id.txt_coins));
        assert(newBalance == 0 || newBalance == 60);
    }


    @Test
    public void testBalancePersistenceAfterRestart() {
        onView(withId(R.id.btn_die)).perform(click());
        String initialBalance = getTextFromView(R.id.txt_balance);
        activityRule.getScenario().recreate(); // Restart the activity
        onView(withId(R.id.txt_balance)).check(matches(withText(initialBalance)));
    }

    @Test
    public void testZeroWager() {
        activityRule.getScenario().recreate();
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.et_wager)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.rb_two_alike)).perform(click());
        onView(withId(R.id.rb_two_alike)).perform(click());
        onView(withId(R.id.btn_go)).perform(click());
        onView(withId(R.id.txt_coins)).check(matches(withText("30"))); // Balance should not change
    }




    private String getTextFromView(int viewId) {
        final String[] text = new String[1];
        onView(withId(viewId)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(viewId)).check((view, noViewFoundException) -> {
            if (view instanceof TextView) {
                text[0] = ((TextView) view).getText().toString();
            }
        });
        return text[0];
    }
}