package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Test
    public void dummyAccessibilityTest() {
        onView(withId(R.id.btn_die)).perform(click());
    }

    @Test
    public void testWalletBalanceAccessibility() {
        onView(withId(R.id.txt_balance)).check(matches(isDisplayed())).check(matches(anyOf(hasContentDescription(), withContentDescription(not("")), withText(not("")))));
    }

    @Test
    public void testToGamesButtonAccessibility() {
        onView(withId(R.id.btn_games)).check(matches(isDisplayed())).check(matches(isClickable())).check(matches(anyOf(hasContentDescription(), withContentDescription(not("")))));
    }

    @Test
    public void testNavigationToGamesFragment() {
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.btn_go)).check(matches(isDisplayed()));
    }

    @Test
    public void testGamesFragmentCoinsAccessibility() {
        onView(withId(R.id.btn_games)).perform(click());onView(withId(R.id.txt_coins)).check(matches(isDisplayed())).check(matches(anyOf(hasContentDescription(), withContentDescription(not("")), withText(not("")))));
    }

    @Test
    public void testRadioButtonsAccessibility() {
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.rb_two_alike)).check(matches(isDisplayed())).check(matches(isClickable()));
        onView(withId(R.id.rb_three_alike)).check(matches(isDisplayed())).check(matches(isClickable()));
        onView(withId(R.id.rb_four_alike)).check(matches(isDisplayed())).check(matches(isClickable()));
    }

    @Test
    public void testGoButtonAccessibility() {
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.btn_go)).check(matches(isDisplayed())).check(matches(isClickable())).check(matches(anyOf(hasContentDescription(), withContentDescription(not("")))));
    }

    @Test
    public void testInfoButtonAccessibility() {
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.btn_info)).check(matches(isDisplayed())).check(matches(isClickable())).check(matches(anyOf(hasContentDescription(), withContentDescription(not("")))));
    }
}